package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.sysdb.service.BaseCodeService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzThreeDirectorMapper;
import com.apih5.mybatis.dao.ZjTzThreeDirectorBillMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzThreeDirector;
import com.apih5.mybatis.pojo.ZjTzThreeDirectorBill;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzThreeDirectorService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzThreeDirectorService")
public class ZjTzThreeDirectorServiceImpl implements ZjTzThreeDirectorService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzThreeDirectorMapper zjTzThreeDirectorMapper;
    
    @Autowired(required = true)
    private ZjTzThreeDirectorBillMapper zjTzThreeDirectorBillMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private BaseCodeService baseCodeService;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;

    @Override
    public ResponseEntity getZjTzThreeDirectorListByCondition(ZjTzThreeDirector zjTzThreeDirector) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userId = TokenUtils.getUserId(request);
    	String ext1 = TokenUtils.getExt1(request);
    	if (zjTzThreeDirector == null) {
    		zjTzThreeDirector = new ZjTzThreeDirector();
    	}
    	
    	if(zjTzThreeDirector.getMeetDateList() != null && zjTzThreeDirector.getMeetDateList().size() >1) {
    		zjTzThreeDirector.setStartTime(zjTzThreeDirector.getMeetDateList().get(0));
    		zjTzThreeDirector.setEndTime(zjTzThreeDirector.getMeetDateList().get(1));
    	}

    	
        // 分页查询
        PageHelper.startPage(zjTzThreeDirector.getPage(),zjTzThreeDirector.getLimit());
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzThreeDirector.getProjectId(), true)) {
            	zjTzThreeDirector.setProjectId("");
            	zjTzThreeDirector.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzThreeDirector.getProjectId(), true)) {
            	zjTzThreeDirector.setProjectId("");
            }
        }
        // 获取数据
        List<ZjTzThreeDirector> zjTzThreeDirectorList = zjTzThreeDirectorMapper.selectByZjTzThreeDirectorList(zjTzThreeDirector);
        for (ZjTzThreeDirector zjTzThreeDirector2 : zjTzThreeDirectorList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzThreeDirector2.getThreeDirectorId());
        	//1
        	zjTzFileSelect.setOtherType("1");
        	List<ZjTzFile> zjTzFileList1 = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzThreeDirector2.setZjTzFileList1(zjTzFileList1);
        	//2
         	zjTzFileSelect.setOtherType("2");
        	List<ZjTzFile> zjTzFileList2 = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzThreeDirector2.setZjTzFileList2(zjTzFileList2);
        	//3
         	zjTzFileSelect.setOtherType("3");
        	List<ZjTzFile> zjTzFileList3 = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzThreeDirector2.setZjTzFileList3(zjTzFileList3);
        	
        	
        	ZjTzThreeDirectorBill bill = new ZjTzThreeDirectorBill();
        	bill.setThreeDirectorId(zjTzThreeDirector2.getThreeDirectorId());
        	List<ZjTzThreeDirectorBill> billList = zjTzThreeDirectorBillMapper.selectByZjTzThreeDirectorBillList(bill);
        	zjTzThreeDirector2.setBillList(billList);
		}
        // 得到分页信息
        PageInfo<ZjTzThreeDirector> p = new PageInfo<>(zjTzThreeDirectorList);

        return repEntity.okList(zjTzThreeDirectorList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzThreeDirectorDetails(ZjTzThreeDirector zjTzThreeDirector) {
    	if (zjTzThreeDirector == null) {
            zjTzThreeDirector = new ZjTzThreeDirector();
        }
    	if(StrUtil.isNotEmpty(zjTzThreeDirector.getThreeDirectorId())){
        	zjTzThreeDirector = zjTzThreeDirectorMapper.selectByPrimaryKey(zjTzThreeDirector.getThreeDirectorId());
        }
        // 数据存在
        if (zjTzThreeDirector != null && StrUtil.isNotEmpty(zjTzThreeDirector.getThreeDirectorId())) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzThreeDirector.getThreeDirectorId());
        	//1
        	zjTzFileSelect.setOtherType("1");
        	List<ZjTzFile> zjTzFileList1 = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzThreeDirector.setZjTzFileList1(zjTzFileList1);
        	//2
         	zjTzFileSelect.setOtherType("2");
        	List<ZjTzFile> zjTzFileList2 = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzThreeDirector.setZjTzFileList2(zjTzFileList2);
        	//3
         	zjTzFileSelect.setOtherType("3");
        	List<ZjTzFile> zjTzFileList3 = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzThreeDirector.setZjTzFileList3(zjTzFileList3);
        	//
        	ZjTzThreeDirectorBill bill = new ZjTzThreeDirectorBill();
        	bill.setThreeDirectorId(zjTzThreeDirector.getThreeDirectorId());
        	List<ZjTzThreeDirectorBill> billList = zjTzThreeDirectorBillMapper.selectByZjTzThreeDirectorBillList(bill);
        	zjTzThreeDirector.setBillList(billList);
            return repEntity.ok(zjTzThreeDirector);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzThreeDirector(ZjTzThreeDirector zjTzThreeDirector) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	boolean addFlag = false;
    	int flag = 0;
    	//
    	if(StrUtil.isNotEmpty(zjTzThreeDirector.getPeriodId()) && StrUtil.isNotEmpty(zjTzThreeDirector.getMeetNumberId())) {
    		ZjTzThreeDirector zjTzThreeDirectorSelect = new ZjTzThreeDirector();
    		zjTzThreeDirectorSelect.setProjectId(zjTzThreeDirector.getProjectId());
    		zjTzThreeDirectorSelect.setPeriodId(zjTzThreeDirector.getPeriodId());
    		zjTzThreeDirectorSelect.setMeetNumberId(zjTzThreeDirector.getMeetNumberId());
    		List<ZjTzThreeDirector> zjTzThreeDirectorSelectList = zjTzThreeDirectorMapper.selectByZjTzThreeDirectorList(zjTzThreeDirectorSelect);
    		if(zjTzThreeDirectorSelectList != null && zjTzThreeDirectorSelectList.size() >0) {
    			return repEntity.layerMessage("no", "该项目本次会议已经添加，请重新选择！");
    		}else {
    			addFlag = true;
    		}
    	}
    	if(addFlag) {
    		zjTzThreeDirector.setThreeDirectorId(UuidUtil.generate());
    		//会议届次
    		if (StrUtil.isNotEmpty(zjTzThreeDirector.getPeriodId())) {
    			String openBankName = baseCodeService.getBaseCodeItemName("huiYiJieCi", zjTzThreeDirector.getPeriodId());
    			zjTzThreeDirector.setPeriodName(openBankName);
    		}
    		//会议期次
    		if (StrUtil.isNotEmpty(zjTzThreeDirector.getMeetNumberId())) {
    			String openBankName = baseCodeService.getBaseCodeItemName("huiYiQiCiOther", zjTzThreeDirector.getMeetNumberId());
    			zjTzThreeDirector.setMeetNumberName(openBankName);
    		}
    		//会议类型
    		if (StrUtil.isNotEmpty(zjTzThreeDirector.getMeetTypeId())) {
    			String openBankName = baseCodeService.getBaseCodeItemName("huiYiLeiXing", zjTzThreeDirector.getMeetTypeId());
    			zjTzThreeDirector.setMeetTypeName(openBankName);
    		}
    		//会议表决方式
    		if (StrUtil.isNotEmpty(zjTzThreeDirector.getMeetVoteId())) {
    			String openBankName = baseCodeService.getBaseCodeItemName("huiYiBiaoJueFangShi", zjTzThreeDirector.getMeetVoteId());
    			zjTzThreeDirector.setMeetVoteName(openBankName);
    		}
    		//原件是否备案
    		if (StrUtil.equals(zjTzThreeDirector.getOriginalId(), "0")) {
    			zjTzThreeDirector.setOriginalName("否");
    		}else if (StrUtil.equals(zjTzThreeDirector.getOriginalId(), "1")) {
    			zjTzThreeDirector.setOriginalName("是");
    		}
        	if(StrUtil.isNotEmpty(zjTzThreeDirector.getPeriodId())) {
        		zjTzThreeDirector.setMeetNumberName(zjTzThreeDirector.getPeriodName()+""+zjTzThreeDirector.getMeetNumberName());
        	}
    		zjTzThreeDirector.setCreateUserInfo(userKey, realName);
    		flag = zjTzThreeDirectorMapper.insert(zjTzThreeDirector);
    		//附件
    		List<ZjTzFile> zjTzFileList1 = zjTzThreeDirector.getZjTzFileList1();
    		if(zjTzFileList1 != null && zjTzFileList1.size()>0) {
    			for(ZjTzFile zjTzFile:zjTzFileList1) {
    				zjTzFile.setUid(UuidUtil.generate());
    				zjTzFile.setOtherId(zjTzThreeDirector.getThreeDirectorId());
    				zjTzFile.setOtherType("1");
    				zjTzFile.setCreateUserInfo(userKey, realName);
    				zjTzFileMapper.insert(zjTzFile);
    			}
    		}
    		List<ZjTzFile> zjTzFileList2 = zjTzThreeDirector.getZjTzFileList2();
    		if(zjTzFileList2 != null && zjTzFileList2.size()>0) {
    			for(ZjTzFile zjTzFile:zjTzFileList2) {
    				zjTzFile.setUid(UuidUtil.generate());
    				zjTzFile.setOtherId(zjTzThreeDirector.getThreeDirectorId());
    				zjTzFile.setOtherType("2");
    				zjTzFile.setCreateUserInfo(userKey, realName);
    				zjTzFileMapper.insert(zjTzFile);
    			}
    		}
    		List<ZjTzFile> zjTzFileList3 = zjTzThreeDirector.getZjTzFileList3();
    		if(zjTzFileList3 != null && zjTzFileList3.size()>0) {
    			for(ZjTzFile zjTzFile:zjTzFileList3) {
    				zjTzFile.setUid(UuidUtil.generate());
    				zjTzFile.setOtherId(zjTzThreeDirector.getThreeDirectorId());
    				zjTzFile.setOtherType("3");
    				zjTzFile.setCreateUserInfo(userKey, realName);
    				zjTzFileMapper.insert(zjTzFile);
    			}
    		}
    	}else {
    		return repEntity.layerMessage("no", "上次会议未填写");
    	}
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.sava", zjTzThreeDirector);
    	}
    }

    @Override
    public ResponseEntity updateZjTzThreeDirector(ZjTzThreeDirector zjTzThreeDirector) {
    	 HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
         String userKey = TokenUtils.getUserKey(request);
         String realName = TokenUtils.getRealName(request);
         int flag = 0;
         ZjTzThreeDirector dbzjTzThreeDirector = zjTzThreeDirectorMapper.selectByPrimaryKey(zjTzThreeDirector.getThreeDirectorId());
         if (dbzjTzThreeDirector != null && StrUtil.isNotEmpty(dbzjTzThreeDirector.getThreeDirectorId())) {
            // 会议类型id
            dbzjTzThreeDirector.setMeetTypeId(zjTzThreeDirector.getMeetTypeId());
            // 会议类型name
            if (StrUtil.isNotEmpty(zjTzThreeDirector.getMeetTypeId())) {
            	String openBankName = baseCodeService.getBaseCodeItemName("huiYiLeiXing", zjTzThreeDirector.getMeetTypeId());
            	dbzjTzThreeDirector.setMeetTypeName(openBankName);
            }
            // 会议时间
            dbzjTzThreeDirector.setMeetDate(zjTzThreeDirector.getMeetDate());
            // 会议地点
            dbzjTzThreeDirector.setMeetPlace(zjTzThreeDirector.getMeetPlace());
            // 会议表决方式id
            dbzjTzThreeDirector.setMeetVoteId(zjTzThreeDirector.getMeetVoteId());
            // 会议表决方式name
            if (StrUtil.isNotEmpty(zjTzThreeDirector.getMeetVoteId())) {
               	String openBankName = baseCodeService.getBaseCodeItemName("huiYiBiaoJueFangShi", zjTzThreeDirector.getMeetVoteId());
               	dbzjTzThreeDirector.setMeetVoteName(openBankName);
               }
            // 原件是否备案id
            dbzjTzThreeDirector.setOriginalId(zjTzThreeDirector.getOriginalId());
            // 原件是否备案name
            if (StrUtil.equals(zjTzThreeDirector.getOriginalId(), "0")) {
            	dbzjTzThreeDirector.setOriginalName("否");
               }else if (StrUtil.equals(zjTzThreeDirector.getOriginalId(), "1")) {
            	   dbzjTzThreeDirector.setOriginalName("是");
               }
            // 共通
            dbzjTzThreeDirector.setModifyUserInfo(userKey, realName);
            flag = zjTzThreeDirectorMapper.updateByPrimaryKey(dbzjTzThreeDirector);
            
         // 删除附件再新增
            ZjTzFile zjTzFileSelect = new ZjTzFile();
            zjTzFileSelect.setOtherId(dbzjTzThreeDirector.getThreeDirectorId());
            List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
            if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
         	   zjTzFileSelect.setModifyUserInfo(userKey, realName);
         	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
            }
            List<ZjTzFile> zjTzFileList1 = zjTzThreeDirector.getZjTzFileList1();
            if(zjTzFileList1 != null && zjTzFileList1.size()>0) {
         	   for(ZjTzFile zjTzFile:zjTzFileList1) {
         		   zjTzFile.setUid(UuidUtil.generate());
         		   zjTzFile.setOtherId(dbzjTzThreeDirector.getThreeDirectorId());
         		   zjTzFile.setOtherType("1");
         		   zjTzFile.setCreateUserInfo(userKey, realName);
         		   zjTzFileMapper.insert(zjTzFile);
         	   }
            }
            List<ZjTzFile> zjTzFileList2 = zjTzThreeDirector.getZjTzFileList2();
            if(zjTzFileList2 != null && zjTzFileList2.size()>0) {
         	   for(ZjTzFile zjTzFile:zjTzFileList2) {
         		   zjTzFile.setUid(UuidUtil.generate());
         		   zjTzFile.setOtherId(dbzjTzThreeDirector.getThreeDirectorId());
         		   zjTzFile.setOtherType("2");
         		   zjTzFile.setCreateUserInfo(userKey, realName);
         		   zjTzFileMapper.insert(zjTzFile);
         	   }
            }
            List<ZjTzFile> zjTzFileList3 = zjTzThreeDirector.getZjTzFileList3();
            if(zjTzFileList3 != null && zjTzFileList3.size()>0) {
         	   for(ZjTzFile zjTzFile:zjTzFileList3) {
         		   zjTzFile.setUid(UuidUtil.generate());
         		   zjTzFile.setOtherId(dbzjTzThreeDirector.getThreeDirectorId());
         		   zjTzFile.setOtherType("3");
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
             return repEntity.ok("sys.data.update",zjTzThreeDirector);
         }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzThreeDirector(List<ZjTzThreeDirector> zjTzThreeDirectorList) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zjTzThreeDirectorList != null && zjTzThreeDirectorList.size() > 0) {
    		//删除
    		for (ZjTzThreeDirector ThreeDirector : zjTzThreeDirectorList) {
    			//del附件
				ZjTzFile file = new ZjTzFile();
				file.setOtherId(ThreeDirector.getThreeDirectorId());
				List<ZjTzFile> deFileList= zjTzFileMapper.selectByZjTzFileList(file);
				if(deFileList != null && deFileList.size() >0) {
					file.setModifyUserInfo(userKey, realName);
					flag = zjTzFileMapper.batchDeleteUpdateZjTzFile(deFileList, file);
				}
				//del意见
				ZjTzThreeDirectorBill opinion = new ZjTzThreeDirectorBill();
				opinion.setThreeDirectorId(ThreeDirector.getThreeDirectorId());
				List<ZjTzThreeDirectorBill> delOpinionList = zjTzThreeDirectorBillMapper.selectByZjTzThreeDirectorBillList(opinion);
				if(delOpinionList != null && delOpinionList.size() >0) {
					opinion.setModifyUserInfo(userKey, realName);
					for (ZjTzThreeDirectorBill cycleOpinion : delOpinionList) {
						ZjTzFile opinionFile = new ZjTzFile();
						opinionFile.setOtherId(cycleOpinion.getThreeDirectorBillId());
						List<ZjTzFile> delOpinionFileList = zjTzFileMapper.selectByZjTzFileList(opinionFile);
						if(delOpinionFileList != null && delOpinionFileList.size() >0) {
							opinionFile.setModifyUserInfo(userKey, realName);
							zjTzFileMapper.batchDeleteUpdateZjTzFile(delOpinionFileList, opinionFile);
						}
						zjTzThreeDirectorBillMapper.batchDeleteUpdateZjTzThreeDirectorBill(delOpinionList, opinion);
					}
				}
    		}
    		ZjTzThreeDirector zjTzThreeDirector = new ZjTzThreeDirector();
    		zjTzThreeDirector.setModifyUserInfo(userKey, realName);
    		flag = zjTzThreeDirectorMapper.batchDeleteUpdateZjTzThreeDirector(zjTzThreeDirectorList, zjTzThreeDirector);
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.delete",zjTzThreeDirectorList);
    	}
    }
    
}