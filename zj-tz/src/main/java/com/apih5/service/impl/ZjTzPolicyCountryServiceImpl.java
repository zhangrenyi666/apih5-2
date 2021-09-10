package com.apih5.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.sysdb.service.BaseCodeService;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzPolicyCountryMapper;
import com.apih5.mybatis.dao.ZjTzPolicyCountryReplyMapper;
import com.apih5.mybatis.dao.ZjTzPolicyLocalMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzPolicyCountry;
import com.apih5.mybatis.pojo.ZjTzPolicyCountryReply;
import com.apih5.mybatis.pojo.ZjTzPolicyLocal;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzPolicyCountryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;

@Service("zjTzPolicyCountryService")
public class ZjTzPolicyCountryServiceImpl implements ZjTzPolicyCountryService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzPolicyCountryMapper zjTzPolicyCountryMapper;

    @Autowired(required = true)
    private ZjTzPolicyCountryReplyMapper zjTzPolicyCountryReplyMapper;
    
    @Autowired(required = true) 
    private ZjTzFileMapper zjTzFileMapper;
    
	@Autowired(required = true)
	private BaseCodeService baseCodeService;

	@Autowired(required = true)
	private ZjTzPolicyLocalMapper zjTzPolicyLocalMapper;
	
	@Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
	 
    @Override
    public ResponseEntity getZjTzPolicyCountryListByCondition(ZjTzPolicyCountry zjTzPolicyCountry) {
        if (zjTzPolicyCountry == null) {
            zjTzPolicyCountry = new ZjTzPolicyCountry();
        }
        
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        // 权限（1：公司级
        String ext1 = TokenUtils.getExt1(request);
        String userId = TokenUtils.getUserId(request);
        // 增加权限查询条件
        if(StrUtil.equals("admin", userId)|| StrUtil.equals("1", ext1)) {
       
        }else {
        	zjTzPolicyCountry.setReleaseId("1");
        }
        
        
        // 分页查询
        PageHelper.startPage(zjTzPolicyCountry.getPage(),zjTzPolicyCountry.getLimit());
        // 获取数据
        List<ZjTzPolicyCountry> zjTzPolicyCountryList = zjTzPolicyCountryMapper.selectByZjTzPolicyCountryList(zjTzPolicyCountry);
        if(zjTzPolicyCountryList != null && zjTzPolicyCountryList.size()>0) {
            for(ZjTzPolicyCountry dbZjTzPolicyCountry:zjTzPolicyCountryList) {
                ZjTzFile zjTzFileSelect = new ZjTzFile();
                zjTzFileSelect.setOtherId(dbZjTzPolicyCountry.getPolicyId());
                List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
                dbZjTzPolicyCountry.setZjTzFileList(zjTzFileList);
                
                ZjTzPolicyCountryReply record = new ZjTzPolicyCountryReply();
                record.setPolicyId(dbZjTzPolicyCountry.getPolicyId());
                List<ZjTzPolicyCountryReply> zjTzPolicyCountryReplyList = zjTzPolicyCountryReplyMapper.selectByZjTzPolicyCountryReplyList(record);
                dbZjTzPolicyCountry.setZjTzPolicyCountryReplyList(zjTzPolicyCountryReplyList);
                
                
            }
        }
        // 得到分页信息
        PageInfo<ZjTzPolicyCountry> p = new PageInfo<>(zjTzPolicyCountryList);

        return repEntity.okList(zjTzPolicyCountryList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzPolicyCountryDetails(ZjTzPolicyCountry zjTzPolicyCountry) {
        if (zjTzPolicyCountry == null) {
            zjTzPolicyCountry = new ZjTzPolicyCountry();
        }
        // 获取数据
        ZjTzPolicyCountry dbZjTzPolicyCountry = zjTzPolicyCountryMapper.selectByPrimaryKey(zjTzPolicyCountry.getPolicyId());
        // 数据存在
        if (dbZjTzPolicyCountry != null) {
            return repEntity.ok(dbZjTzPolicyCountry);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzPolicyCountry(ZjTzPolicyCountry zjTzPolicyCountry) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzPolicyCountry.setPolicyId(UuidUtil.generate());
        if(StrUtil.equals("1", zjTzPolicyCountry.getEffectiveId())) {
            zjTzPolicyCountry.setEffectiveName("是");
        } else {
            zjTzPolicyCountry.setEffectiveName("否");
        }
        //发文层级
        if (StrUtil.isNotEmpty(zjTzPolicyCountry.getReleaseRankId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("faWenCengJi", zjTzPolicyCountry.getReleaseRankId());
        	zjTzPolicyCountry.setReleaseRankName(openBankName);
        }
        zjTzPolicyCountry.setReleaseId("0");
        zjTzPolicyCountry.setReleaseName("未发布");
        zjTzPolicyCountry.setHomeShow("0");
        zjTzPolicyCountry.setCreateUserInfo(userKey, realName);
        int flag = zjTzPolicyCountryMapper.insert(zjTzPolicyCountry);

        // 附件list
        List<ZjTzFile> zjTzFileList = zjTzPolicyCountry.getZjTzFileList();
        if(zjTzFileList != null && zjTzFileList.size()>0) {
            for(ZjTzFile zjTzFile:zjTzFileList) {
                zjTzFile.setUid(UuidUtil.generate());
                zjTzFile.setOtherId(zjTzPolicyCountry.getPolicyId());
                zjTzFile.setOtherType("1");
                zjTzFile.setCreateUserInfo(userKey, realName);
                zjTzFileMapper.insert(zjTzFile);
            }
        }
        
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzPolicyCountry);
        }
    }

    @Override
    public ResponseEntity updateZjTzPolicyCountry(ZjTzPolicyCountry zjTzPolicyCountry) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzPolicyCountry dbzjTzPolicyCountry = zjTzPolicyCountryMapper.selectByPrimaryKey(zjTzPolicyCountry.getPolicyId());
        if (dbzjTzPolicyCountry != null && StrUtil.isNotEmpty(dbzjTzPolicyCountry.getPolicyId())) {
        	if(StrUtil.equals(dbzjTzPolicyCountry.getReleaseId(), "1")) {
         	   return repEntity.layerMessage("no", "已经发布，不能修改！");
            }
        	if(StrUtil.equals("1", zjTzPolicyCountry.getEffectiveId())) {
               zjTzPolicyCountry.setEffectiveName("是");
           } else {
               zjTzPolicyCountry.setEffectiveName("否");
           }
           // 标题
           dbzjTzPolicyCountry.setTitle(zjTzPolicyCountry.getTitle());
           // 文号
           dbzjTzPolicyCountry.setSymbolNo(zjTzPolicyCountry.getSymbolNo());
           //发文层级id
           dbzjTzPolicyCountry.setReleaseRankId(zjTzPolicyCountry.getReleaseRankId());
           //发文层级name
           if (StrUtil.isNotEmpty(zjTzPolicyCountry.getReleaseRankId())) {
        	   String openBankName = baseCodeService.getBaseCodeItemName("faWenCengJi", zjTzPolicyCountry.getReleaseRankId());
        	   dbzjTzPolicyCountry.setReleaseRankName(openBankName);
           }
           // 系统发布日期
           dbzjTzPolicyCountry.setSysDate(zjTzPolicyCountry.getSysDate());
           // 发文部门
           dbzjTzPolicyCountry.setDepartmentName(zjTzPolicyCountry.getDepartmentName());
           // 原文发布日期
           dbzjTzPolicyCountry.setReleaseDate(zjTzPolicyCountry.getReleaseDate());
           // 原文生效日期
           dbzjTzPolicyCountry.setEffectDate(zjTzPolicyCountry.getEffectDate());
           // 登记用户
           dbzjTzPolicyCountry.setRegisterUser(zjTzPolicyCountry.getRegisterUser());
           // 有效文件
           dbzjTzPolicyCountry.setEffectiveId(zjTzPolicyCountry.getEffectiveId());
           // 有效文件名称
           dbzjTzPolicyCountry.setEffectiveName(zjTzPolicyCountry.getEffectiveName());
           // 分析报告
           dbzjTzPolicyCountry.setReport(zjTzPolicyCountry.getReport());
           // 预案推送情况
           dbzjTzPolicyCountry.setPushInfo(zjTzPolicyCountry.getPushInfo());
           // 共通
           dbzjTzPolicyCountry.setModifyUserInfo(userKey, realName);
           flag = zjTzPolicyCountryMapper.updateByPrimaryKey(dbzjTzPolicyCountry);
           
           // 删除附件再新增
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzPolicyCountry.getPolicyId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
               zjTzFileSelect.setModifyUserInfo(userKey, realName);
               zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           // 附件list
           List<ZjTzFile> zjTzFileList = zjTzPolicyCountry.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
               for(ZjTzFile zjTzFile:zjTzFileList) {
                   zjTzFile.setUid(UuidUtil.generate());
                   zjTzFile.setOtherId(dbzjTzPolicyCountry.getPolicyId());
                   zjTzFile.setOtherType("1");
                   zjTzFile.setCreateUserInfo(userKey, realName);
                   zjTzFileMapper.insert(zjTzFile);
               }
           }
           
           // 预案回复表(只能增加，想删除的数据单独再回复中维护，不然逻辑走不通）
           List<ZjTzPolicyCountryReply> zjTzPolicyCountryReplyList = zjTzPolicyCountry.getZjTzPolicyCountryReplyList();
           if(zjTzPolicyCountryReplyList != null && zjTzPolicyCountryReplyList.size()>0) {
               for(ZjTzPolicyCountryReply zjTzPolicyCountryReply:zjTzPolicyCountryReplyList) {
                   zjTzPolicyCountryReply.setPolicyReplyId(UuidUtil.generate());
                   BeanUtil.copyProperties(dbzjTzPolicyCountry, zjTzPolicyCountryReply);
                   zjTzPolicyCountryReply.setCreateUserInfo(userKey, realName);
                   zjTzPolicyCountryReplyMapper.insert(zjTzPolicyCountryReply);
               }
           }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzPolicyCountry);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzPolicyCountry(List<ZjTzPolicyCountry> zjTzPolicyCountryList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzPolicyCountryList != null && zjTzPolicyCountryList.size() > 0) {
        	for (ZjTzPolicyCountry zjTzPolicyCountry : zjTzPolicyCountryList) {
        		if(StrUtil.equals(zjTzPolicyCountry.getReleaseId(), "1")) {
        			return repEntity.layerMessage("no", "已经发布，不能删除！");
        		}
        	}
        	ZjTzPolicyCountry zjTzPolicyCountry = new ZjTzPolicyCountry();
           zjTzPolicyCountry.setModifyUserInfo(userKey, realName);
           flag = zjTzPolicyCountryMapper.batchDeleteUpdateZjTzPolicyCountry(zjTzPolicyCountryList, zjTzPolicyCountry);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzPolicyCountryList);
        }
    }
    
    //--扩展
    @Override
    public ResponseEntity updateZjTzPolicyCountryPush(ZjTzPolicyCountry zjTzPolicyCountry) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzPolicyCountry dbzjTzPolicyCountry = zjTzPolicyCountryMapper.selectByPrimaryKey(zjTzPolicyCountry.getPolicyId());
        if (dbzjTzPolicyCountry != null && StrUtil.isNotEmpty(dbzjTzPolicyCountry.getPolicyId())) {           
           // 预案回复表(只能增加，想删除的数据单独再回复中维护，不然逻辑走不通）
           List<ZjTzPolicyCountryReply> zjTzPolicyCountryReplyList = zjTzPolicyCountry.getZjTzPolicyCountryReplyList();
           int size = 0;
           if(zjTzPolicyCountryReplyList != null && zjTzPolicyCountryReplyList.size()>0) {
        	 //同一个政策推送给 同一个人的时候，不增加数据和数量
         	  size = zjTzPolicyCountryReplyList.size();
         	  for(ZjTzPolicyCountryReply zjTzPolicyCountryReply:zjTzPolicyCountryReplyList) {
         		  ZjTzPolicyCountryReply reply = new ZjTzPolicyCountryReply();
         		  reply.setValue(zjTzPolicyCountryReply.getValue());
         		  reply.setPolicyId(zjTzPolicyCountry.getPolicyId());
         		  List<ZjTzPolicyCountryReply> replies = zjTzPolicyCountryReplyMapper.selectByZjTzPolicyCountryReplyList(reply);
         		  if(replies != null && replies.size() >0) {
         			  size = size-1;  
         		  }else {
         			  zjTzPolicyCountryReply.setPolicyReplyId(UuidUtil.generate());
         			  BeanUtil.copyProperties(dbzjTzPolicyCountry, zjTzPolicyCountryReply);
         			  zjTzPolicyCountryReply.setCreateUserInfo(userKey, realName);
         			  zjTzPolicyCountryReplyMapper.insert(zjTzPolicyCountryReply);
         		  }
         	  }
               // 数量更新
               int count = dbzjTzPolicyCountry.getPushInfoAll() + size;
               dbzjTzPolicyCountry.setPushInfoAll(count);
               dbzjTzPolicyCountry.setModifyUserInfo(userKey, realName);
               flag = zjTzPolicyCountryMapper.updateByPrimaryKey(dbzjTzPolicyCountry);
           }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzPolicyCountry);
        }
    }

    @Override
    public ResponseEntity updateZjTzPolicyCountryHomeShow(ZjTzPolicyCountry zjTzPolicyCountry) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzPolicyCountry dbzjTzPolicyCountry = zjTzPolicyCountryMapper.selectByPrimaryKey(zjTzPolicyCountry.getPolicyId());
        if (dbzjTzPolicyCountry != null && StrUtil.isNotEmpty(dbzjTzPolicyCountry.getPolicyId())) {   
        	if(StrUtil.equals(dbzjTzPolicyCountry.getReleaseId(), "0")) {
    			return repEntity.layerMessage("no", "未发布的不能广而告之，请先发布");
    		}
        	dbzjTzPolicyCountry.setHomeShow("1");
            dbzjTzPolicyCountry.setHomeShowStartDate(zjTzPolicyCountry.getHomeShowStartDate());
            dbzjTzPolicyCountry.setHomeShowEndDate(zjTzPolicyCountry.getHomeShowEndDate());
            dbzjTzPolicyCountry.setModifyUserInfo(userKey, realName);
            flag = zjTzPolicyCountryMapper.updateByPrimaryKey(dbzjTzPolicyCountry);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzPolicyCountry);
        }
    }

	@Override
	public ResponseEntity batchDeleteReleaseZjTzPolicyCountry(List<ZjTzPolicyCountry> zjTzPolicyCountryList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzPolicyCountryList != null && zjTzPolicyCountryList.size() > 0) {
        	for (ZjTzPolicyCountry zjTzRules : zjTzPolicyCountryList) {
				if(StrUtil.equals(zjTzRules.getReleaseId(), "1")) {
					 return repEntity.layerMessage("no", "包含已经发布的，请重新选择！");
				}
			}
        	for (ZjTzPolicyCountry zjTzPolicyCountry : zjTzPolicyCountryList) {
        		ZjTzPolicyCountryReply record = new ZjTzPolicyCountryReply();
        		record.setPolicyId(zjTzPolicyCountry.getPolicyId());
        		List<ZjTzPolicyCountryReply> countryReplies = zjTzPolicyCountryReplyMapper.selectByZjTzPolicyCountryReplyList(record);
        		for (ZjTzPolicyCountryReply country : countryReplies) {
        			country.setReleaseId("1");
        			country.setReleaseName("发布");
        			zjTzPolicyCountryReplyMapper.updateByPrimaryKey(country);
        		}
        	}
        	ZjTzPolicyCountry zjTzRules = new ZjTzPolicyCountry();
           zjTzRules.setReleaseName("发布");
           zjTzRules.setModifyUserInfo(userKey, realName);
           flag = zjTzPolicyCountryMapper.batchDeleteReleaseZjTzPolicyCountry(zjTzPolicyCountryList, zjTzRules);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzPolicyCountryList);
        }
	}

	@Override
	public ResponseEntity batchDeleteRecallZjTzPolicyCountry(List<ZjTzPolicyCountry> zjTzPolicyCountryList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzPolicyCountryList != null && zjTzPolicyCountryList.size() > 0) {
        	for (ZjTzPolicyCountry zjTzRules : zjTzPolicyCountryList) {
        		if(StrUtil.equals(zjTzRules.getReleaseId(), "0")) {
        			return repEntity.layerMessage("no", "包含未发布的，请重新选择！");
        		}
        	}
        	for (ZjTzPolicyCountry zjTzPolicyCountry : zjTzPolicyCountryList) {
        		ZjTzPolicyCountryReply record = new ZjTzPolicyCountryReply();
        		record.setPolicyId(zjTzPolicyCountry.getPolicyId());
        		List<ZjTzPolicyCountryReply> countryReplies = zjTzPolicyCountryReplyMapper.selectByZjTzPolicyCountryReplyList(record);
        		for (ZjTzPolicyCountryReply country : countryReplies) {
        			country.setReleaseId("0");
        			country.setReleaseName("未发布");
        			zjTzPolicyCountryReplyMapper.updateByPrimaryKey(country);
				}
        	}
        	ZjTzPolicyCountry zjTzRules = new ZjTzPolicyCountry();
        	zjTzRules.setReleaseName("未发布");
        	zjTzRules.setHomeShow("0");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzPolicyCountryMapper.batchDeleteRecallZjTzPolicyCountry(zjTzPolicyCountryList, zjTzRules);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzPolicyCountryList);
        }
	}

	@Override
	public ResponseEntity batchExportZjTzPolicyCountryFile(List<ZjTzPolicyCountry> zjTzPolicyCountryList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		// 要生成的压缩文件地址和文件名称
		String zipName = "国家政策" + DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN) + ".zip";
		List<ZjTzFile> fileList = new ArrayList<>();
		for (ZjTzPolicyCountry total : zjTzPolicyCountryList) {
			ZjTzFile file = new ZjTzFile();
			file.setOtherId(total.getPolicyId());
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
	/**
	 * 
	 * 首页宏观政策
	 */
	@Override
	public ResponseEntity getZjTzPolicyCountryHomeAdvertMacro(ZjTzPolicyCountry zjTzPolicyCountry) {
		if(zjTzPolicyCountry == null) {
			zjTzPolicyCountry = new ZjTzPolicyCountry();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        // 权限（1：公司级
        String ext1 = TokenUtils.getExt1(request);
        String userId = TokenUtils.getUserId(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	// 选择全部项目是，通过拼接的sql去查询
        	if(StrUtil.equals("all", zjTzPolicyCountry.getProjectId(), true)) {
        		zjTzPolicyCountry.setProjectId("");
        		zjTzPolicyCountry.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
        	}
        } else {
        	// 集团人员时
        	if(StrUtil.equals("all", zjTzPolicyCountry.getProjectId(), true)) {
        		zjTzPolicyCountry.setProjectId("");
        	}
        }
		List<Object> returnList = Lists.newArrayList();
		List<Object> dbreturnList = Lists.newArrayList();
		List<ZjTzPolicyCountry> zjTzPolicyCountryList = zjTzPolicyCountryMapper.selectZjTzHomeMacroPolicyCountry(zjTzPolicyCountry);
		if(zjTzPolicyCountryList != null && zjTzPolicyCountryList.size() >0) {
			for (ZjTzPolicyCountry dbzjTzPolicyCountry : zjTzPolicyCountryList) {
				dbzjTzPolicyCountry.setTypeName("国家政策");
			}
			returnList.addAll(zjTzPolicyCountryList);
		}
		List<ZjTzPolicyLocal> zjTzPolicyLocalList = zjTzPolicyLocalMapper.selectZjTzHomeMacroPolicyLocal(zjTzPolicyCountry.getZjTzPolicyLocal());
		if (zjTzPolicyLocalList != null && zjTzPolicyLocalList.size() > 0) {
			for (ZjTzPolicyLocal dbzjTzPolicyLocal : zjTzPolicyLocalList) {
				dbzjTzPolicyLocal.setTypeName("地方政策");
			}
			returnList.addAll(zjTzPolicyLocalList);
			
		}
		if (returnList != null && returnList.size() >= 4) {
			for (int i = 0; i < 4; i++) {
				dbreturnList.add(returnList.get(i));
			}
		}else {
			dbreturnList.addAll(returnList);
		}
		return repEntity.ok(dbreturnList);
	}
}
