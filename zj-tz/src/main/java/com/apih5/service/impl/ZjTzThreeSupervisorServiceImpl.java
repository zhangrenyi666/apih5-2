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
import com.apih5.mybatis.dao.ZjTzThreeSupervisorBillMapper;
import com.apih5.mybatis.dao.ZjTzThreeSupervisorMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzThreeSupervisor;
import com.apih5.mybatis.pojo.ZjTzThreeSupervisorBill;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzThreeSupervisorService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzThreeSupervisorService")
public class ZjTzThreeSupervisorServiceImpl implements ZjTzThreeSupervisorService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzThreeSupervisorMapper zjTzThreeSupervisorMapper;
    
    @Autowired(required = true)
    private ZjTzThreeSupervisorBillMapper zjTzThreeSupervisorBillMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private BaseCodeService baseCodeService;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;

    @Override
    public ResponseEntity getZjTzThreeSupervisorListByCondition(ZjTzThreeSupervisor zjTzThreeSupervisor) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userId = TokenUtils.getUserId(request);
    	String ext1 = TokenUtils.getExt1(request);
    	if (zjTzThreeSupervisor == null) {
    		zjTzThreeSupervisor = new ZjTzThreeSupervisor();
        }
    	
    	if(zjTzThreeSupervisor.getMeetDateList() != null && zjTzThreeSupervisor.getMeetDateList().size() >1) {
    		zjTzThreeSupervisor.setStartTime(zjTzThreeSupervisor.getMeetDateList().get(0));
    		zjTzThreeSupervisor.setEndTime(zjTzThreeSupervisor.getMeetDateList().get(1));
    	}
    	
        // 分页查询
        PageHelper.startPage(zjTzThreeSupervisor.getPage(),zjTzThreeSupervisor.getLimit());
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzThreeSupervisor.getProjectId(), true)) {
            	zjTzThreeSupervisor.setProjectId("");
            	zjTzThreeSupervisor.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzThreeSupervisor.getProjectId(), true)) {
            	zjTzThreeSupervisor.setProjectId("");
            }
        }
        // 获取数据
        List<ZjTzThreeSupervisor> zjTzThreeSupervisorList = zjTzThreeSupervisorMapper.selectByZjTzThreeSupervisorList(zjTzThreeSupervisor);
        for (ZjTzThreeSupervisor zjTzThreeSupervisor2 : zjTzThreeSupervisorList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzThreeSupervisor2.getThreeSupervisorId());
        	//1
        	zjTzFileSelect.setOtherType("1");
        	List<ZjTzFile> zjTzFileList1 = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzThreeSupervisor2.setZjTzFileList1(zjTzFileList1);
        	//2
         	zjTzFileSelect.setOtherType("2");
        	List<ZjTzFile> zjTzFileList2 = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzThreeSupervisor2.setZjTzFileList2(zjTzFileList2);
        	//3
         	zjTzFileSelect.setOtherType("3");
        	List<ZjTzFile> zjTzFileList3 = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzThreeSupervisor2.setZjTzFileList3(zjTzFileList3);
        	
        	ZjTzThreeSupervisorBill bill = new ZjTzThreeSupervisorBill();
        	bill.setThreeSupervisorId(zjTzThreeSupervisor2.getThreeSupervisorId());
        	List<ZjTzThreeSupervisorBill> billList = zjTzThreeSupervisorBillMapper.selectByZjTzThreeSupervisorBillList(bill);
        	zjTzThreeSupervisor2.setBillList(billList);
		}
        // 得到分页信息
        PageInfo<ZjTzThreeSupervisor> p = new PageInfo<>(zjTzThreeSupervisorList);

        return repEntity.okList(zjTzThreeSupervisorList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzThreeSupervisorDetails(ZjTzThreeSupervisor zjTzThreeSupervisor) {
    	if (zjTzThreeSupervisor == null) {
            zjTzThreeSupervisor = new ZjTzThreeSupervisor();
        }
    	if(StrUtil.isNotEmpty(zjTzThreeSupervisor.getThreeSupervisorId())){
        	zjTzThreeSupervisor = zjTzThreeSupervisorMapper.selectByPrimaryKey(zjTzThreeSupervisor.getThreeSupervisorId());
        }
        // 数据存在
        if (zjTzThreeSupervisor != null && StrUtil.isNotEmpty(zjTzThreeSupervisor.getThreeSupervisorId())) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzThreeSupervisor.getThreeSupervisorId());
        	//1
        	zjTzFileSelect.setOtherType("1");
        	List<ZjTzFile> zjTzFileList1 = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzThreeSupervisor.setZjTzFileList1(zjTzFileList1);
        	//2
         	zjTzFileSelect.setOtherType("2");
        	List<ZjTzFile> zjTzFileList2 = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzThreeSupervisor.setZjTzFileList2(zjTzFileList2);
        	//3
         	zjTzFileSelect.setOtherType("3");
        	List<ZjTzFile> zjTzFileList3 = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzThreeSupervisor.setZjTzFileList3(zjTzFileList3);
        	//
        	ZjTzThreeSupervisorBill bill = new ZjTzThreeSupervisorBill();
        	bill.setThreeSupervisorId(zjTzThreeSupervisor.getThreeSupervisorId());
        	List<ZjTzThreeSupervisorBill> billList = zjTzThreeSupervisorBillMapper.selectByZjTzThreeSupervisorBillList(bill);
        	zjTzThreeSupervisor.setBillList(billList);
            return repEntity.ok(zjTzThreeSupervisor);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzThreeSupervisor(ZjTzThreeSupervisor zjTzThreeSupervisor) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	boolean addFlag = false;
    	int flag = 0;
      	//
    	if(StrUtil.isNotEmpty(zjTzThreeSupervisor.getPeriodId()) && StrUtil.isNotEmpty(zjTzThreeSupervisor.getMeetNumberId())) {
    		ZjTzThreeSupervisor zjTzThreeSupervisorSelect = new ZjTzThreeSupervisor();
    		zjTzThreeSupervisorSelect.setProjectId(zjTzThreeSupervisor.getProjectId());
    		zjTzThreeSupervisorSelect.setPeriodId(zjTzThreeSupervisor.getPeriodId());
    		zjTzThreeSupervisorSelect.setMeetNumberId(zjTzThreeSupervisor.getMeetNumberId());
    		List<ZjTzThreeSupervisor> zjTzThreeSupervisorSelectList = zjTzThreeSupervisorMapper.selectByZjTzThreeSupervisorList(zjTzThreeSupervisorSelect);
    		if(zjTzThreeSupervisorSelectList != null && zjTzThreeSupervisorSelectList.size() >0) {
    			return repEntity.layerMessage("no", "该项目本次会议已经添加，请重新选择！");
    		}else {
    			zjTzThreeSupervisorSelect.setMeetNumberId("1");
    			List<ZjTzThreeSupervisor> SupervisorFirstList = zjTzThreeSupervisorMapper.selectByZjTzThreeSupervisorList(zjTzThreeSupervisorSelect);
    			if(StrUtil.equals(zjTzThreeSupervisor.getMeetNumberId(), "1")) {
    				if(SupervisorFirstList != null && SupervisorFirstList.size() >0) {
    					return repEntity.layerMessage("no", "该项目本次会议已经添加，请重新选择！");
    				}else {
    					addFlag = true;
    				}
    			}else {
    				addFlag = true;
    			}
    		}
    	}
    	if(addFlag) {
    		zjTzThreeSupervisor.setThreeSupervisorId(UuidUtil.generate());
    		//会议届次
    		if (StrUtil.isNotEmpty(zjTzThreeSupervisor.getPeriodId())) {
    			String openBankName = baseCodeService.getBaseCodeItemName("huiYiJieCi", zjTzThreeSupervisor.getPeriodId());
    			zjTzThreeSupervisor.setPeriodName(openBankName);
    		}
    		//会议期次
    		if (StrUtil.isNotEmpty(zjTzThreeSupervisor.getMeetNumberId())) {
    			String openBankName = baseCodeService.getBaseCodeItemName("huiYiQiCiOther", zjTzThreeSupervisor.getMeetNumberId());
    			zjTzThreeSupervisor.setMeetNumberName(openBankName);
    		}
    		//会议类型
    		if (StrUtil.isNotEmpty(zjTzThreeSupervisor.getMeetTypeId())) {
    			String openBankName = baseCodeService.getBaseCodeItemName("huiYiLeiXing", zjTzThreeSupervisor.getMeetTypeId());
    			zjTzThreeSupervisor.setMeetTypeName(openBankName);
    		}
    		//会议表决方式
    		if (StrUtil.isNotEmpty(zjTzThreeSupervisor.getMeetVoteId())) {
    			String openBankName = baseCodeService.getBaseCodeItemName("huiYiBiaoJueFangShi", zjTzThreeSupervisor.getMeetVoteId());
    			zjTzThreeSupervisor.setMeetVoteName(openBankName);
    		}
    		//原件是否备案
    		if (StrUtil.equals(zjTzThreeSupervisor.getOriginalId(), "0")) {
    			zjTzThreeSupervisor.setOriginalName("否");
    		}else if (StrUtil.equals(zjTzThreeSupervisor.getOriginalId(), "1")) {
    			zjTzThreeSupervisor.setOriginalName("是");
    		}
    		zjTzThreeSupervisor.setCreateUserInfo(userKey, realName);
    		if(StrUtil.isNotEmpty(zjTzThreeSupervisor.getPeriodId())) {
    			zjTzThreeSupervisor.setMeetNumberName(zjTzThreeSupervisor.getPeriodName()+""+zjTzThreeSupervisor.getMeetNumberName());
        	}
    		flag = zjTzThreeSupervisorMapper.insert(zjTzThreeSupervisor);
    		//附件
    		List<ZjTzFile> zjTzFileList1 = zjTzThreeSupervisor.getZjTzFileList1();
    		if(zjTzFileList1 != null && zjTzFileList1.size()>0) {
    			for(ZjTzFile zjTzFile:zjTzFileList1) {
    				zjTzFile.setUid(UuidUtil.generate());
    				zjTzFile.setOtherId(zjTzThreeSupervisor.getThreeSupervisorId());
    				zjTzFile.setOtherType("1");
    				zjTzFile.setCreateUserInfo(userKey, realName);
    				zjTzFileMapper.insert(zjTzFile);
    			}
    		}
    		List<ZjTzFile> zjTzFileList2 = zjTzThreeSupervisor.getZjTzFileList2();
    		if(zjTzFileList2 != null && zjTzFileList2.size()>0) {
    			for(ZjTzFile zjTzFile:zjTzFileList2) {
    				zjTzFile.setUid(UuidUtil.generate());
    				zjTzFile.setOtherId(zjTzThreeSupervisor.getThreeSupervisorId());
    				zjTzFile.setOtherType("2");
    				zjTzFile.setCreateUserInfo(userKey, realName);
    				zjTzFileMapper.insert(zjTzFile);
    			}
    		}
    		List<ZjTzFile> zjTzFileList3 = zjTzThreeSupervisor.getZjTzFileList3();
    		if(zjTzFileList3 != null && zjTzFileList3.size()>0) {
    			for(ZjTzFile zjTzFile:zjTzFileList3) {
    				zjTzFile.setUid(UuidUtil.generate());
    				zjTzFile.setOtherId(zjTzThreeSupervisor.getThreeSupervisorId());
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
    		return repEntity.ok("sys.data.sava", zjTzThreeSupervisor);
    	}
    }

    @Override
    public ResponseEntity updateZjTzThreeSupervisor(ZjTzThreeSupervisor zjTzThreeSupervisor) {
    	 HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
         String userKey = TokenUtils.getUserKey(request);
         String realName = TokenUtils.getRealName(request);
         int flag = 0;
         ZjTzThreeSupervisor dbzjTzThreeSupervisor = zjTzThreeSupervisorMapper.selectByPrimaryKey(zjTzThreeSupervisor.getThreeSupervisorId());
         if (dbzjTzThreeSupervisor != null && StrUtil.isNotEmpty(dbzjTzThreeSupervisor.getThreeSupervisorId())) {
            // 会议类型id
            dbzjTzThreeSupervisor.setMeetTypeId(zjTzThreeSupervisor.getMeetTypeId());
            // 会议类型name
            if (StrUtil.isNotEmpty(zjTzThreeSupervisor.getMeetTypeId())) {
            	String openBankName = baseCodeService.getBaseCodeItemName("huiYiLeiXing", zjTzThreeSupervisor.getMeetTypeId());
            	dbzjTzThreeSupervisor.setMeetTypeName(openBankName);
            }
            // 会议时间
            dbzjTzThreeSupervisor.setMeetDate(zjTzThreeSupervisor.getMeetDate());
            // 会议地点
            dbzjTzThreeSupervisor.setMeetPlace(zjTzThreeSupervisor.getMeetPlace());
            // 会议表决方式id
            dbzjTzThreeSupervisor.setMeetVoteId(zjTzThreeSupervisor.getMeetVoteId());
            // 会议表决方式name
            if (StrUtil.isNotEmpty(zjTzThreeSupervisor.getMeetVoteId())) {
               	String openBankName = baseCodeService.getBaseCodeItemName("huiYiBiaoJueFangShi", zjTzThreeSupervisor.getMeetVoteId());
               	dbzjTzThreeSupervisor.setMeetVoteName(openBankName);
               }
            // 原件是否备案id
            dbzjTzThreeSupervisor.setOriginalId(zjTzThreeSupervisor.getOriginalId());
            // 原件是否备案name
            if (StrUtil.equals(zjTzThreeSupervisor.getOriginalId(), "0")) {
            	dbzjTzThreeSupervisor.setOriginalName("否");
            }else if (StrUtil.equals(zjTzThreeSupervisor.getOriginalId(), "1")) {
            	dbzjTzThreeSupervisor.setOriginalName("是");
            }
            // 共通
            dbzjTzThreeSupervisor.setModifyUserInfo(userKey, realName);
            flag = zjTzThreeSupervisorMapper.updateByPrimaryKey(dbzjTzThreeSupervisor);
            
         // 删除附件再新增
            ZjTzFile zjTzFileSelect = new ZjTzFile();
            zjTzFileSelect.setOtherId(dbzjTzThreeSupervisor.getThreeSupervisorId());
            List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
            if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
         	   zjTzFileSelect.setModifyUserInfo(userKey, realName);
         	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
            }
            List<ZjTzFile> zjTzFileList1 = zjTzThreeSupervisor.getZjTzFileList1();
            if(zjTzFileList1 != null && zjTzFileList1.size()>0) {
         	   for(ZjTzFile zjTzFile:zjTzFileList1) {
         		   zjTzFile.setUid(UuidUtil.generate());
         		   zjTzFile.setOtherId(dbzjTzThreeSupervisor.getThreeSupervisorId());
         		   zjTzFile.setOtherType("1");
         		   zjTzFile.setCreateUserInfo(userKey, realName);
         		   zjTzFileMapper.insert(zjTzFile);
         	   }
            }
            List<ZjTzFile> zjTzFileList2 = zjTzThreeSupervisor.getZjTzFileList2();
            if(zjTzFileList2 != null && zjTzFileList2.size()>0) {
         	   for(ZjTzFile zjTzFile:zjTzFileList2) {
         		   zjTzFile.setUid(UuidUtil.generate());
         		   zjTzFile.setOtherId(dbzjTzThreeSupervisor.getThreeSupervisorId());
         		   zjTzFile.setOtherType("2");
         		   zjTzFile.setCreateUserInfo(userKey, realName);
         		   zjTzFileMapper.insert(zjTzFile);
         	   }
            }
            List<ZjTzFile> zjTzFileList3 = zjTzThreeSupervisor.getZjTzFileList3();
            if(zjTzFileList3 != null && zjTzFileList3.size()>0) {
         	   for(ZjTzFile zjTzFile:zjTzFileList3) {
         		   zjTzFile.setUid(UuidUtil.generate());
         		   zjTzFile.setOtherId(dbzjTzThreeSupervisor.getThreeSupervisorId());
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
             return repEntity.ok("sys.data.update",zjTzThreeSupervisor);
         }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzThreeSupervisor(List<ZjTzThreeSupervisor> zjTzThreeSupervisorList) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zjTzThreeSupervisorList != null && zjTzThreeSupervisorList.size() > 0) {
    		//删除
    		for (ZjTzThreeSupervisor ThreeSupervisor : zjTzThreeSupervisorList) {
    			//del附件
				ZjTzFile file = new ZjTzFile();
				file.setOtherId(ThreeSupervisor.getThreeSupervisorId());
				List<ZjTzFile> deFileList= zjTzFileMapper.selectByZjTzFileList(file);
				if(deFileList != null && deFileList.size() >0) {
					file.setModifyUserInfo(userKey, realName);
					flag = zjTzFileMapper.batchDeleteUpdateZjTzFile(deFileList, file);
				}
				//del意见
				ZjTzThreeSupervisorBill opinion = new ZjTzThreeSupervisorBill();
				opinion.setThreeSupervisorId(ThreeSupervisor.getThreeSupervisorId());
				List<ZjTzThreeSupervisorBill> delOpinionList = zjTzThreeSupervisorBillMapper.selectByZjTzThreeSupervisorBillList(opinion);
				if(delOpinionList != null && delOpinionList.size() >0) {
					opinion.setModifyUserInfo(userKey, realName);
					for (ZjTzThreeSupervisorBill cycleOpinion : delOpinionList) {
						ZjTzFile opinionFile = new ZjTzFile();
						opinionFile.setOtherId(cycleOpinion.getThreeSupervisorBillId());
						List<ZjTzFile> delOpinionFileList = zjTzFileMapper.selectByZjTzFileList(opinionFile);
						if(delOpinionFileList != null && delOpinionFileList.size() >0) {
							opinionFile.setModifyUserInfo(userKey, realName);
							zjTzFileMapper.batchDeleteUpdateZjTzFile(delOpinionFileList, opinionFile);
						}
						zjTzThreeSupervisorBillMapper.batchDeleteUpdateZjTzThreeSupervisorBill(delOpinionList, opinion);
					}
				}

    		}
    		ZjTzThreeSupervisor zjTzThreeSupervisor = new ZjTzThreeSupervisor();
			zjTzThreeSupervisor.setModifyUserInfo(userKey, realName);
			flag = zjTzThreeSupervisorMapper.batchDeleteUpdateZjTzThreeSupervisor(zjTzThreeSupervisorList, zjTzThreeSupervisor);
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.delete",zjTzThreeSupervisorList);
    	}
    }

}