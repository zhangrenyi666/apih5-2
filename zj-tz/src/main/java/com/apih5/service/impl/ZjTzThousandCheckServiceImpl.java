package com.apih5.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.dao.ZjTzThousandCheckBaseMapper;
import com.apih5.mybatis.dao.ZjTzThousandCheckMapper;
import com.apih5.mybatis.dao.ZjTzThousandDeductMapper;
import com.apih5.mybatis.pojo.ZjTzThousandCheck;
import com.apih5.mybatis.pojo.ZjTzThousandCheckBase;
import com.apih5.mybatis.pojo.ZjTzThousandDeduct;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzThousandCheckService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;

@Service("zjTzThousandCheckService")
public class ZjTzThousandCheckServiceImpl implements ZjTzThousandCheckService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzThousandCheckMapper zjTzThousandCheckMapper;
    
    @Autowired(required = true)
    private ZjTzThousandDeductMapper zjTzThousandDeductMapper;
    
    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private ZjTzThousandCheckBaseMapper zjTzThousandCheckBaseMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;

    @Override
    public ResponseEntity getZjTzThousandCheckListByCondition(ZjTzThousandCheck zjTzThousandCheck) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userId = TokenUtils.getUserId(request);
    	String ext1 = TokenUtils.getExt1(request);
    	if (zjTzThousandCheck == null) {
    		zjTzThousandCheck = new ZjTzThousandCheck();
        }
    	 // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	zjTzThousandCheck.setUnExt1SeeFlag("不是投资事业部时只可见已上报和被退回的数据");
            // 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzThousandCheck.getProjectId(), true)) {
            	zjTzThousandCheck.setProjectId("");
            	zjTzThousandCheck.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzThousandCheck.getProjectId(), true)) {
            	zjTzThousandCheck.setProjectId("");
            }
        }
        // 分页查询
        PageHelper.startPage(zjTzThousandCheck.getPage(),zjTzThousandCheck.getLimit());
        // 获取数据
        List<ZjTzThousandCheck> zjTzThousandCheckList = zjTzThousandCheckMapper.selectByZjTzThousandCheckList(zjTzThousandCheck);
        for (ZjTzThousandCheck zjTzThousandCheck2 : zjTzThousandCheckList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzThousandCheck2.getThousandCheckId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzThousandCheck2.setZjTzFileList(zjTzFileList);
        	
        	//千分制详情得分(千分制得分)
        	BigDecimal detailMarks = new BigDecimal("0");
        	//项目得分
        	BigDecimal proScore = new BigDecimal("0");
        	ZjTzThousandDeduct deduct = new ZjTzThousandDeduct();
        	deduct.setThousandCheckId(zjTzThousandCheck2.getThousandCheckId());
        	List<ZjTzThousandDeduct> deducts = zjTzThousandDeductMapper.selectByZjTzThousandDeductList(deduct);
        	for (ZjTzThousandDeduct actual : deducts) {
        		detailMarks = CalcUtils.calcAdd(detailMarks, actual.getActualScore());
        		proScore = CalcUtils.calcAdd(proScore, actual.getActualScore());
        	}
        	//项目得分=加分+原来的项目得分
        	//项目得分=千分制检查得分+额外加分值-额外扣分值
        	if(zjTzThousandCheck2.getAddMarks() != null) {
        		proScore = CalcUtils.calcAdd(zjTzThousandCheck2.getAddMarks(), proScore);
        	}
        	if(zjTzThousandCheck2.getReduceMarks() != null) {
        		proScore = CalcUtils.calcSubtract(proScore, zjTzThousandCheck2.getReduceMarks());
        	}
        	zjTzThousandCheck2.setProScore(proScore);
        	zjTzThousandCheck2.setDetailMarks(detailMarks);
        	
		}
        // 得到分页信息
        PageInfo<ZjTzThousandCheck> p = new PageInfo<>(zjTzThousandCheckList);

        return repEntity.okList(zjTzThousandCheckList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzThousandCheckDetails(ZjTzThousandCheck zjTzThousandCheck) {
        if (zjTzThousandCheck == null) {
            zjTzThousandCheck = new ZjTzThousandCheck();
        }
        // 获取数据
        ZjTzThousandCheck dbZjTzThousandCheck = zjTzThousandCheckMapper.selectByPrimaryKey(zjTzThousandCheck.getThousandCheckId());
        // 数据存在
        if (dbZjTzThousandCheck != null) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(dbZjTzThousandCheck.getThousandCheckId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzThousandCheck.setZjTzFileList(zjTzFileList);
            return repEntity.ok(dbZjTzThousandCheck);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzThousandCheck(ZjTzThousandCheck zjTzThousandCheck) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        //千分制详情得分(千分制得分)
    	BigDecimal detailMarks = new BigDecimal("0");
    	//项目得分
    	BigDecimal proScore = new BigDecimal("0");
       
        ZjTzProManage manage = zjTzProManageMapper.selectByPrimaryKey(zjTzThousandCheck.getProjectId());
        if(manage != null && StrUtil.isNotEmpty(manage.getProjectId())){
        	zjTzThousandCheck.setProjectName(manage.getProjectName());
        }
        zjTzThousandCheck.setThousandCheckId(UuidUtil.generate());
        if(StrUtil.equals(zjTzThousandCheck.getVoteDownId(), "1")) {
        	zjTzThousandCheck.setVoteDownName("是");
        }else {
        	zjTzThousandCheck.setVoteDownName("否");
        }
        zjTzThousandCheck.setReleaseId("0");
        zjTzThousandCheck.setReleaseName("未下达");
        zjTzThousandCheck.setCreateUserInfo(userKey, realName);
        //新增减分表
        ZjTzThousandCheckBase base = new ZjTzThousandCheckBase();
        List<ZjTzThousandCheckBase> baseItemList = zjTzThousandCheckBaseMapper.getZjTzThousandCheckBaseItemList(base);
        if(baseItemList != null && baseItemList.size() >0) {
        	for (ZjTzThousandCheckBase zjTzThousandCheckBase : baseItemList) {
        		detailMarks = CalcUtils.calcAdd(detailMarks, zjTzThousandCheckBase.getScore());
        		ZjTzThousandDeduct record = new ZjTzThousandDeduct();
        		record.setThousandDeductId(UuidUtil.generate());
        		record.setThousandCheckId(zjTzThousandCheck.getThousandCheckId());
        		record.setThousandCheckBaseId(zjTzThousandCheckBase.getCodePid());
        		record.setEvalPro(zjTzThousandCheckBase.getEvalProName());
        		record.setEvalIndex(zjTzThousandCheckBase.getEvalIndex());
        		record.setEvalContent(zjTzThousandCheckBase.getEvalContent());
        		record.setScore(zjTzThousandCheckBase.getScore());
        		record.setOrderFlag(zjTzThousandCheckBase.getOrderFlag());
        		record.setActualScore(zjTzThousandCheckBase.getScore());
        		record.setDeduct(new BigDecimal("0"));
        		record.setCreateUserInfo(userKey, realName);
        		zjTzThousandDeductMapper.insert(record);
        	}
        }
    	if(zjTzThousandCheck.getAddMarks() != null) {
    		proScore = CalcUtils.calcAdd(zjTzThousandCheck.getAddMarks(), proScore);
    	}
    	if(zjTzThousandCheck.getReduceMarks() != null) {
    		proScore = CalcUtils.calcSubtract(proScore, zjTzThousandCheck.getReduceMarks());
    	}
    	zjTzThousandCheck.setProScore(proScore);
    	
        int flag = zjTzThousandCheckMapper.insert(zjTzThousandCheck);
        // 附件list
        List<ZjTzFile> zjTzFileList = zjTzThousandCheck.getZjTzFileList();
        if(zjTzFileList != null && zjTzFileList.size()>0) {
        	for(ZjTzFile zjTzFile:zjTzFileList) {
        		zjTzFile.setUid(UuidUtil.generate());
        		zjTzFile.setOtherId(zjTzThousandCheck.getThousandCheckId());
        		zjTzFile.setCreateUserInfo(userKey, realName);
        		zjTzFileMapper.insert(zjTzFile);
        	}
        }
        

        if (flag == 0) {
        	return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzThousandCheck);
        }
    }

    @Override
    public ResponseEntity updateZjTzThousandCheck(ZjTzThousandCheck zjTzThousandCheck) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzThousandCheck dbzjTzThousandCheck = zjTzThousandCheckMapper.selectByPrimaryKey(zjTzThousandCheck.getThousandCheckId());
        if (dbzjTzThousandCheck != null && StrUtil.isNotEmpty(dbzjTzThousandCheck.getThousandCheckId())) {
//           // 项目id
//           dbzjTzThousandCheck.setProjectId(zjTzThousandCheck.getProjectId());
//           // 项目name
//           dbzjTzThousandCheck.setProjectName(zjTzThousandCheck.getProjectName());
           // 检查日期
           dbzjTzThousandCheck.setCheckDate(zjTzThousandCheck.getCheckDate());
           // 检查人
           dbzjTzThousandCheck.setCheckPerson(zjTzThousandCheck.getCheckPerson());
           // 是否否决Id
           dbzjTzThousandCheck.setVoteDownId(zjTzThousandCheck.getVoteDownId());
           // 是否否决name
           if(StrUtil.equals(zjTzThousandCheck.getVoteDownId(), "1")) {
        	   dbzjTzThousandCheck.setVoteDownName("是");
           }else {
        	   dbzjTzThousandCheck.setVoteDownName("否");
           }
           // 否决情况说明
           dbzjTzThousandCheck.setScoreTotal(zjTzThousandCheck.getScoreTotal());
           // 登记日期
           dbzjTzThousandCheck.setRegisterDate(zjTzThousandCheck.getRegisterDate());
           // 登记用户
           dbzjTzThousandCheck.setRegisterPerson(zjTzThousandCheck.getRegisterPerson());
           //加分
           dbzjTzThousandCheck.setAddMarks(zjTzThousandCheck.getAddMarks());
           //加分原因
           dbzjTzThousandCheck.setAddMarksReason(zjTzThousandCheck.getAddMarksReason());
           //减分
           dbzjTzThousandCheck.setReduceMarks(zjTzThousandCheck.getReduceMarks());
           //减分原因
           dbzjTzThousandCheck.setReduceMarksReason(zjTzThousandCheck.getReduceMarksReason());
           // 项目得分==加分+原来的项目得分-扣分
           dbzjTzThousandCheck.setProScore(CalcUtils.calcDivide(CalcUtils.calcAdd(zjTzThousandCheck.getAddMarks(), dbzjTzThousandCheck.getProScore()), zjTzThousandCheck.getReduceMarks()));
           // 共通
           dbzjTzThousandCheck.setModifyUserInfo(userKey, realName);
           flag = zjTzThousandCheckMapper.updateByPrimaryKey(dbzjTzThousandCheck);
           
           // 删除附件再新增
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzThousandCheck.getThousandCheckId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        	   zjTzFileSelect.setModifyUserInfo(userKey, realName);
        	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           List<ZjTzFile> zjTzFileList = zjTzThousandCheck.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
        	   for(ZjTzFile zjTzFile:zjTzFileList) {
        		   zjTzFile.setUid(UuidUtil.generate());
        		   zjTzFile.setOtherId(dbzjTzThousandCheck.getThousandCheckId());
        		   zjTzFile.setCreateUserInfo(userKey, realName);
        		   zjTzFileMapper.insert(zjTzFile);
        	   }
           }
           
           
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzThousandCheck);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzThousandCheck(List<ZjTzThousandCheck> zjTzThousandCheckList) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zjTzThousandCheckList != null && zjTzThousandCheckList.size() > 0) {
    		for (ZjTzThousandCheck zjTzThousandCheck : zjTzThousandCheckList) {
    			if(StrUtil.equals(zjTzThousandCheck.getReleaseId(), "1")) {
    				return repEntity.layerMessage("no", "已下达的数据，不能删除！");
    			}
    		}
    		for (ZjTzThousandCheck zjTzThousandCheck : zjTzThousandCheckList) {
    			// 删除附件
    			ZjTzFile zjTzFileSelect = new ZjTzFile();
    			zjTzFileSelect.setOtherId(zjTzThousandCheck.getThousandCheckId());
    			List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
    			if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
    				zjTzFileSelect.setModifyUserInfo(userKey, realName);
    				zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
    			}
    			//删除扣分
    			ZjTzThousandDeduct deduct = new ZjTzThousandDeduct();
    			deduct.setThousandCheckId(zjTzThousandCheck.getThousandCheckId());
    			List<ZjTzThousandDeduct> delDeductList = zjTzThousandDeductMapper.selectByZjTzThousandDeductList(deduct);
    			if(delDeductList != null && delDeductList.size() >0) {
    				deduct.setModifyUserInfo(userKey, realName);
    				zjTzThousandDeductMapper.batchDeleteUpdateZjTzThousandDeduct(delDeductList, deduct);
    			}
    		}
    		ZjTzThousandCheck zjTzThousandCheck = new ZjTzThousandCheck();
    		zjTzThousandCheck.setModifyUserInfo(userKey, realName);
    		flag = zjTzThousandCheckMapper.batchDeleteUpdateZjTzThousandCheck(zjTzThousandCheckList, zjTzThousandCheck);
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzThousandCheckList);
        }
    }

	@Override
	public ResponseEntity batchReleaseZjTzThousandCheck(List<ZjTzThousandCheck> zjTzThousandCheckList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzThousandCheckList != null && zjTzThousandCheckList.size() > 0) {
        	for (ZjTzThousandCheck zjTzRules : zjTzThousandCheckList) {
				if(StrUtil.equals(zjTzRules.getReleaseId(), "1")) {
					 return repEntity.layerMessage("no", "包含已下达的，请重新选择！");
				}
			}
        	ZjTzThousandCheck zjTzRules = new ZjTzThousandCheck();
           zjTzRules.setReleaseName("已下达");
           zjTzRules.setModifyUserInfo(userKey, realName);
           flag = zjTzThousandCheckMapper.batchReleaseZjTzThousandCheck(zjTzThousandCheckList, zjTzRules);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzThousandCheckList);
        }
	}

	@Override
	public ResponseEntity batchRecallZjTzThousandCheck(List<ZjTzThousandCheck> zjTzThousandCheckList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzThousandCheckList != null && zjTzThousandCheckList.size() > 0) {
        	for (ZjTzThousandCheck zjTzRules : zjTzThousandCheckList) {
        		if(StrUtil.equals(zjTzRules.getReleaseId(), "0")) {
        			return repEntity.layerMessage("no", "包含未下达的，请重新选择！");
        		}
        	}
        	ZjTzThousandCheck zjTzRules = new ZjTzThousandCheck();
        	zjTzRules.setReleaseName("未下达");//被撤回
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzThousandCheckMapper.batchRecallZjTzThousandCheck(zjTzThousandCheckList, zjTzRules);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzThousandCheckList);
        }
	}

	@Override
	public ResponseEntity batchExportZjTzThousandCheckFile(List<ZjTzThousandCheck> zjTzThousandCheckList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		// 要生成的压缩文件地址和文件名称
		String zipName =DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN) + ".zip";
		List<ZjTzFile> fileList = new ArrayList<>();
		for (ZjTzThousandCheck total : zjTzThousandCheckList) {
			ZjTzFile file = new ZjTzFile();
			file.setOtherId(total.getThousandCheckId());
			List<ZjTzFile> files = zjTzFileMapper.selectByZjTzFileList(file);
			if(files != null && files.size() >0) {
				fileList.addAll(files);
			}
		}
		if(fileList != null && fileList.size() >0) {
		    String uuidFolder = "temp/" + UuidUtil.generate();
		    // 实际文件
            File[] files = new File[fileList.size()];
            for (int i=0; i<fileList.size(); i++) {
                // 随机数，添加到文件后面，防止名称相同
                Random rd = new Random();
                int r = rd.nextInt(200)%(111) + 100;
                String fileUrl = fileList.get(i).getRelativeUrl();
                String pathFile = Apih5Properties.getFilePath() + fileUrl;
                FileUtil.mkdir(Apih5Properties.getFilePath() + uuidFolder);
                String newPathFile = Apih5Properties.getFilePath() + uuidFolder + "/"  + r + "-" + fileList.get(i).getName();
                try {
                    FileUtil.copy(pathFile, newPathFile, true);
                    files[i] = FileUtil.file(newPathFile);
                }catch (Exception e) {
                	
                }
            }
            // zip文件输出
            ZipUtil.zip(FileUtil.file(HttpUtil.getUploadPath(uuidFolder) + zipName), false, files);
            String returnPath = HttpUtil.getUploadPathWeb(request, uuidFolder) + zipName;
            return repEntity.ok(returnPath);
		}else {
			return repEntity.layerMessage("no", "无导出数据！");
		}
	}

	@Override
	public ResponseEntity saveZjTzThousandCheckAllDeduct(ZjTzThousandCheck zjTzThousandCheck) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zjTzThousandCheck != null && StrUtil.isNotEmpty(zjTzThousandCheck.getThousandCheckId())) {
    		if (zjTzThousandCheck.getZjTzThousandDeductList() != null && zjTzThousandCheck.getZjTzThousandDeductList().size() >0) {
    			for (ZjTzThousandDeduct termBase : zjTzThousandCheck.getZjTzThousandDeductList()) {
    				//前台传过来的实际得分如果大于 总分==返回提示
    				if(termBase.getActualScore().compareTo(termBase.getScore()) >0) {
    					return repEntity.layerMessage("no", "实际得分大于总分，请重新打分");
    				}
    				if(termBase.getDeduct().compareTo(termBase.getScore()) >0) {
    					return repEntity.layerMessage("no", "扣分大于总分，请重新打分");
    				}
    			}
    			//先删除再新增
    			ZjTzThousandDeduct delDeduct = new ZjTzThousandDeduct();
    			delDeduct.setThousandCheckId(zjTzThousandCheck.getThousandCheckId());
    			List<ZjTzThousandDeduct> delRelpyList = zjTzThousandDeductMapper.selectByZjTzThousandDeductList(delDeduct);
    			if(delRelpyList != null && delRelpyList.size() >0) {
    				delDeduct.setModifyUserInfo(userKey, realName);
    				flag = zjTzThousandDeductMapper.batchDeleteUpdateZjTzThousandDeduct(delRelpyList, delDeduct);
    			}
    			for (ZjTzThousandDeduct termBase : zjTzThousandCheck.getZjTzThousandDeductList()) {
    				ZjTzThousandDeduct dbzjTzThousandDeduct = new ZjTzThousandDeduct();
    				// 主键id
    				dbzjTzThousandDeduct.setThousandDeductId(UuidUtil.generate());
    				// id
    				dbzjTzThousandDeduct.setThousandCheckId(zjTzThousandCheck.getThousandCheckId());
    				//baseid
    				dbzjTzThousandDeduct.setThousandCheckBaseId(termBase.getThousandCheckBaseId());
    				//
    				dbzjTzThousandDeduct.setEvalPro(termBase.getEvalPro());
    				dbzjTzThousandDeduct.setEvalIndex(termBase.getEvalIndex());
    				dbzjTzThousandDeduct.setEvalContent(termBase.getEvalContent());
    				dbzjTzThousandDeduct.setDeductDesc(termBase.getDeductDesc());
    				dbzjTzThousandDeduct.setScore(termBase.getScore());
    				dbzjTzThousandDeduct.setActualScore(termBase.getActualScore());
    				dbzjTzThousandDeduct.setDeduct(termBase.getDeduct());
    				//排序
    				dbzjTzThousandDeduct.setOrderFlag(termBase.getOrderFlag());
    				// 共通
    				dbzjTzThousandDeduct.setCreateUserInfo(userKey, realName);
    				flag = zjTzThousandDeductMapper.insert(dbzjTzThousandDeduct);
    			}
    		}
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zjTzThousandCheck);
    	}
	}
}
