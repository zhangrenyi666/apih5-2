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
import com.apih5.mybatis.dao.ZjTzThreeShareholderBillMapper;
import com.apih5.mybatis.dao.ZjTzThreeShareholderMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzThreeShareholder;
import com.apih5.mybatis.pojo.ZjTzThreeShareholderBill;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzThreeShareholderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zjTzThreeShareholderService")
public class ZjTzThreeShareholderServiceImpl implements ZjTzThreeShareholderService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzThreeShareholderMapper zjTzThreeShareholderMapper;
    
    @Autowired(required = true)
    private ZjTzThreeShareholderBillMapper zjTzThreeShareholderBillMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private BaseCodeService baseCodeService;

    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
    @Override
    public ResponseEntity getZjTzThreeShareholderListByCondition(ZjTzThreeShareholder zjTzThreeShareholder) {
    	 HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
         String userId = TokenUtils.getUserId(request);
         String ext1 = TokenUtils.getExt1(request);
    	if (zjTzThreeShareholder == null) {
            zjTzThreeShareholder = new ZjTzThreeShareholder();
        }
        // ????????????
        PageHelper.startPage(zjTzThreeShareholder.getPage(),zjTzThreeShareholder.getLimit());
     // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            // ???????????????????????????????????????sql?????????
            if(StrUtil.equals("all", zjTzThreeShareholder.getProjectId(), true)) {
            	zjTzThreeShareholder.setProjectId("");
            	zjTzThreeShareholder.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // ???????????????
            if(StrUtil.equals("all", zjTzThreeShareholder.getProjectId(), true)) {
            	zjTzThreeShareholder.setProjectId("");
            }
        }
        if(zjTzThreeShareholder.getMeetDateList() != null && zjTzThreeShareholder.getMeetDateList().size() >1) {
        	zjTzThreeShareholder.setStartTime(zjTzThreeShareholder.getMeetDateList().get(0));
        	zjTzThreeShareholder.setEndTime(zjTzThreeShareholder.getMeetDateList().get(1));
        }
        // ????????????
        List<ZjTzThreeShareholder> zjTzThreeShareholderList = zjTzThreeShareholderMapper.selectByZjTzThreeShareholderList(zjTzThreeShareholder);
        for (ZjTzThreeShareholder zjTzThreeShareholder2 : zjTzThreeShareholderList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzThreeShareholder2.getThreeShareholderId());
        	//1
        	zjTzFileSelect.setOtherType("1");
        	List<ZjTzFile> zjTzFileList1 = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzThreeShareholder2.setZjTzFileList1(zjTzFileList1);
        	//2
         	zjTzFileSelect.setOtherType("2");
        	List<ZjTzFile> zjTzFileList2 = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzThreeShareholder2.setZjTzFileList2(zjTzFileList2);
        	//3
         	zjTzFileSelect.setOtherType("3");
        	List<ZjTzFile> zjTzFileList3 = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzThreeShareholder2.setZjTzFileList3(zjTzFileList3);
        	
        	
        	ZjTzThreeShareholderBill bill = new ZjTzThreeShareholderBill();
        	bill.setThreeShareholderId(zjTzThreeShareholder2.getThreeShareholderId());
        	List<ZjTzThreeShareholderBill> billList = zjTzThreeShareholderBillMapper.selectByZjTzThreeShareholderBillList(bill);
        	zjTzThreeShareholder2.setBillList(billList);
		}
        // ??????????????????
        PageInfo<ZjTzThreeShareholder> p = new PageInfo<>(zjTzThreeShareholderList);

        return repEntity.okList(zjTzThreeShareholderList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzThreeShareholderDetails(ZjTzThreeShareholder zjTzThreeShareholder) {
    	if (zjTzThreeShareholder == null) {
            zjTzThreeShareholder = new ZjTzThreeShareholder();
        }
    	if(StrUtil.isNotEmpty(zjTzThreeShareholder.getThreeShareholderId())){
        	zjTzThreeShareholder = zjTzThreeShareholderMapper.selectByPrimaryKey(zjTzThreeShareholder.getThreeShareholderId());
        }
        // ????????????
        if (zjTzThreeShareholder != null && StrUtil.isNotEmpty(zjTzThreeShareholder.getThreeShareholderId())) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzThreeShareholder.getThreeShareholderId());
        	//1
        	zjTzFileSelect.setOtherType("1");
        	List<ZjTzFile> zjTzFileList1 = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzThreeShareholder.setZjTzFileList1(zjTzFileList1);
        	//2
         	zjTzFileSelect.setOtherType("2");
        	List<ZjTzFile> zjTzFileList2 = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzThreeShareholder.setZjTzFileList2(zjTzFileList2);
        	//3
         	zjTzFileSelect.setOtherType("3");
        	List<ZjTzFile> zjTzFileList3 = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzThreeShareholder.setZjTzFileList3(zjTzFileList3);
        	//
        	ZjTzThreeShareholderBill bill = new ZjTzThreeShareholderBill();
        	bill.setThreeShareholderId(zjTzThreeShareholder.getThreeShareholderId());
        	List<ZjTzThreeShareholderBill> billList = zjTzThreeShareholderBillMapper.selectByZjTzThreeShareholderBillList(bill);
        	zjTzThreeShareholder.setBillList(billList);
            return repEntity.ok(zjTzThreeShareholder);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzThreeShareholder(ZjTzThreeShareholder zjTzThreeShareholder) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	boolean addFlag = false;
    	String yearStr = "";
    	int flag = 0;
    	
    	//??????????????????????????????????????????
    	if(StrUtil.isNotEmpty(zjTzThreeShareholder.getMeetNumberId())) {
    		int number = Integer.parseInt(zjTzThreeShareholder.getMeetNumberId());
    		//?????????????????????===????????????
    		if(StrUtil.equals(zjTzThreeShareholder.getMeetNumberId(), "0")) {
    			ZjTzThreeShareholder zjTzThreeShareholderSelect = new ZjTzThreeShareholder();
    			zjTzThreeShareholderSelect.setProjectId(zjTzThreeShareholder.getProjectId());
    			zjTzThreeShareholderSelect.setMeetNumberId(zjTzThreeShareholder.getMeetNumberId());
    			List<ZjTzThreeShareholder> zjTzThreeShareholderSelectList = zjTzThreeShareholderMapper.selectByZjTzThreeShareholderList(zjTzThreeShareholderSelect);
    			if(zjTzThreeShareholderSelectList != null && zjTzThreeShareholderSelectList.size() >0) {
    				return repEntity.layerMessage("no", "??????????????????????????????????????????????????????");
    			}else {
    				addFlag = true;
    			}
    		}else {//???????????????????????????1.2.3.4.....?????????
    			if(zjTzThreeShareholder.getYearDate() != null) {
    				yearStr = DateUtil.format(zjTzThreeShareholder.getYearDate(), "yyyy");
    				addFlag = true;
    			}else {
    				return repEntity.layerMessage("no", "??????????????????");
    			}
    			//????????????????????????????????????????????????????????????
				ZjTzThreeShareholder other = new ZjTzThreeShareholder();
				other.setYearStr(yearStr);
				other.setMeetNumberId(number+"");
				other.setProjectId(zjTzThreeShareholder.getProjectId());
				List<ZjTzThreeShareholder> otherList = zjTzThreeShareholderMapper.selectByZjTzThreeShareholderList(other);
				if(otherList != null && otherList.size() >0) {
					return repEntity.layerMessage("no", "??????????????????????????????????????????");
				}else {
					addFlag = true;
				}
    		}
    	}
    	if(addFlag) {
    		zjTzThreeShareholder.setThreeShareholderId(UuidUtil.generate());
    		//????????????
    		if (StrUtil.isNotEmpty(zjTzThreeShareholder.getMeetNumberId())) {
    			String openBankName = baseCodeService.getBaseCodeItemName("huiYiQiCi", zjTzThreeShareholder.getMeetNumberId());
    			zjTzThreeShareholder.setMeetNumberName(openBankName);
    		}
    		//????????????
    		if (StrUtil.isNotEmpty(zjTzThreeShareholder.getMeetTypeId())) {
    			String openBankName = baseCodeService.getBaseCodeItemName("huiYiLeiXing", zjTzThreeShareholder.getMeetTypeId());
    			zjTzThreeShareholder.setMeetTypeName(openBankName);
    		}
    		//??????????????????
    		if (StrUtil.isNotEmpty(zjTzThreeShareholder.getMeetVoteId())) {
    			String openBankName = baseCodeService.getBaseCodeItemName("huiYiBiaoJueFangShi", zjTzThreeShareholder.getMeetVoteId());
    			zjTzThreeShareholder.setMeetVoteName(openBankName);
    		}
    		//??????????????????
    		if (StrUtil.equals(zjTzThreeShareholder.getOriginalId(), "0")) {
    			zjTzThreeShareholder.setOriginalName("???");
    		}else if (StrUtil.equals(zjTzThreeShareholder.getOriginalId(), "1")) {
    			zjTzThreeShareholder.setOriginalName("???");
    		}
    		zjTzThreeShareholder.setYearStr(yearStr);
    		if(StrUtil.isNotEmpty(yearStr)) {
    			zjTzThreeShareholder.setMeetNumberName(yearStr+"???"+zjTzThreeShareholder.getMeetNumberName());
    		}
    		zjTzThreeShareholder.setCreateUserInfo(userKey, realName);
    		flag = zjTzThreeShareholderMapper.insert(zjTzThreeShareholder);
    		//??????
    		List<ZjTzFile> zjTzFileList1 = zjTzThreeShareholder.getZjTzFileList1();
    		if(zjTzFileList1 != null && zjTzFileList1.size()>0) {
    			for(ZjTzFile zjTzFile:zjTzFileList1) {
    				zjTzFile.setUid(UuidUtil.generate());
    				zjTzFile.setOtherId(zjTzThreeShareholder.getThreeShareholderId());
    				zjTzFile.setOtherType("1");
    				zjTzFile.setCreateUserInfo(userKey, realName);
    				zjTzFileMapper.insert(zjTzFile);
    			}
    		}
    		List<ZjTzFile> zjTzFileList2 = zjTzThreeShareholder.getZjTzFileList2();
    		if(zjTzFileList2 != null && zjTzFileList2.size()>0) {
    			for(ZjTzFile zjTzFile:zjTzFileList2) {
    				zjTzFile.setUid(UuidUtil.generate());
    				zjTzFile.setOtherId(zjTzThreeShareholder.getThreeShareholderId());
    				zjTzFile.setOtherType("2");
    				zjTzFile.setCreateUserInfo(userKey, realName);
    				zjTzFileMapper.insert(zjTzFile);
    			}
    		}
    		List<ZjTzFile> zjTzFileList3 = zjTzThreeShareholder.getZjTzFileList3();
    		if(zjTzFileList3 != null && zjTzFileList3.size()>0) {
    			for(ZjTzFile zjTzFile:zjTzFileList3) {
    				zjTzFile.setUid(UuidUtil.generate());
    				zjTzFile.setOtherId(zjTzThreeShareholder.getThreeShareholderId());
    				zjTzFile.setOtherType("3");
    				zjTzFile.setCreateUserInfo(userKey, realName);
    				zjTzFileMapper.insert(zjTzFile);
    			}
    		}
    	}else {
    		return repEntity.layerMessage("no", "?????????????????????");
    	}
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.sava", zjTzThreeShareholder);
    	}
    }

    @Override
    public ResponseEntity updateZjTzThreeShareholder(ZjTzThreeShareholder zjTzThreeShareholder) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzThreeShareholder dbzjTzThreeShareholder = zjTzThreeShareholderMapper.selectByPrimaryKey(zjTzThreeShareholder.getThreeShareholderId());
        if (dbzjTzThreeShareholder != null && StrUtil.isNotEmpty(dbzjTzThreeShareholder.getThreeShareholderId())) {
           // ????????????id
           dbzjTzThreeShareholder.setMeetTypeId(zjTzThreeShareholder.getMeetTypeId());
           // ????????????name
           if (StrUtil.isNotEmpty(zjTzThreeShareholder.getMeetTypeId())) {
           	String openBankName = baseCodeService.getBaseCodeItemName("huiYiLeiXing", zjTzThreeShareholder.getMeetTypeId());
           	dbzjTzThreeShareholder.setMeetTypeName(openBankName);
           }
           // ????????????
           dbzjTzThreeShareholder.setMeetDate(zjTzThreeShareholder.getMeetDate());
           // ????????????
           dbzjTzThreeShareholder.setMeetPlace(zjTzThreeShareholder.getMeetPlace());
           // ??????????????????id
           dbzjTzThreeShareholder.setMeetVoteId(zjTzThreeShareholder.getMeetVoteId());
           // ??????????????????name
           if (StrUtil.isNotEmpty(zjTzThreeShareholder.getMeetVoteId())) {
              	String openBankName = baseCodeService.getBaseCodeItemName("huiYiBiaoJueFangShi", zjTzThreeShareholder.getMeetVoteId());
              	dbzjTzThreeShareholder.setMeetVoteName(openBankName);
              }
           // ??????????????????id
           dbzjTzThreeShareholder.setOriginalId(zjTzThreeShareholder.getOriginalId());
           // ??????????????????name
           if (StrUtil.equals(zjTzThreeShareholder.getOriginalId(), "0")) {
        	   dbzjTzThreeShareholder.setOriginalName("???");
              }else if (StrUtil.equals(zjTzThreeShareholder.getOriginalId(), "1")) {
           	   dbzjTzThreeShareholder.setOriginalName("???");
              }
           // ??????
           dbzjTzThreeShareholder.setModifyUserInfo(userKey, realName);
           flag = zjTzThreeShareholderMapper.updateByPrimaryKey(dbzjTzThreeShareholder);
           
        // ?????????????????????
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzThreeShareholder.getThreeShareholderId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        	   zjTzFileSelect.setModifyUserInfo(userKey, realName);
        	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           List<ZjTzFile> zjTzFileList1 = zjTzThreeShareholder.getZjTzFileList1();
           if(zjTzFileList1 != null && zjTzFileList1.size()>0) {
        	   for(ZjTzFile zjTzFile:zjTzFileList1) {
        		   zjTzFile.setUid(UuidUtil.generate());
        		   zjTzFile.setOtherId(dbzjTzThreeShareholder.getThreeShareholderId());
        		   zjTzFile.setOtherType("1");
        		   zjTzFile.setCreateUserInfo(userKey, realName);
        		   zjTzFileMapper.insert(zjTzFile);
        	   }
           }
           List<ZjTzFile> zjTzFileList2 = zjTzThreeShareholder.getZjTzFileList2();
           if(zjTzFileList2 != null && zjTzFileList2.size()>0) {
        	   for(ZjTzFile zjTzFile:zjTzFileList2) {
        		   zjTzFile.setUid(UuidUtil.generate());
        		   zjTzFile.setOtherId(dbzjTzThreeShareholder.getThreeShareholderId());
        		   zjTzFile.setOtherType("2");
        		   zjTzFile.setCreateUserInfo(userKey, realName);
        		   zjTzFileMapper.insert(zjTzFile);
        	   }
           }
           List<ZjTzFile> zjTzFileList3 = zjTzThreeShareholder.getZjTzFileList3();
           if(zjTzFileList3 != null && zjTzFileList3.size()>0) {
        	   for(ZjTzFile zjTzFile:zjTzFileList3) {
        		   zjTzFile.setUid(UuidUtil.generate());
        		   zjTzFile.setOtherId(dbzjTzThreeShareholder.getThreeShareholderId());
        		   zjTzFile.setOtherType("3");
        		   zjTzFile.setCreateUserInfo(userKey, realName);
        		   zjTzFileMapper.insert(zjTzFile);
        	   }
           }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzThreeShareholder);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzThreeShareholder(List<ZjTzThreeShareholder> zjTzThreeShareholderList) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zjTzThreeShareholderList != null && zjTzThreeShareholderList.size() > 0) {
    		//??????
    		for (ZjTzThreeShareholder ThreeShareholder : zjTzThreeShareholderList) {

    			//del??????
    			ZjTzFile file = new ZjTzFile();
    			file.setOtherId(ThreeShareholder.getThreeShareholderId());
    			List<ZjTzFile> deFileList= zjTzFileMapper.selectByZjTzFileList(file);
    			if(deFileList != null && deFileList.size() >0) {
    				file.setModifyUserInfo(userKey, realName);
    				flag = zjTzFileMapper.batchDeleteUpdateZjTzFile(deFileList, file);
    			}
    			//del??????
    			ZjTzThreeShareholderBill opinion = new ZjTzThreeShareholderBill();
    			opinion.setThreeShareholderId(ThreeShareholder.getThreeShareholderId());
    			List<ZjTzThreeShareholderBill> delOpinionList = zjTzThreeShareholderBillMapper.selectByZjTzThreeShareholderBillList(opinion);
    			if(delOpinionList != null && delOpinionList.size() >0) {
    				opinion.setModifyUserInfo(userKey, realName);
    				for (ZjTzThreeShareholderBill cycleOpinion : delOpinionList) {
    					ZjTzFile opinionFile = new ZjTzFile();
    					opinionFile.setOtherId(cycleOpinion.getThreeShareholderBillId());
    					List<ZjTzFile> delOpinionFileList = zjTzFileMapper.selectByZjTzFileList(opinionFile);
    					if(delOpinionFileList != null && delOpinionFileList.size() >0) {
    						opinionFile.setModifyUserInfo(userKey, realName);
    						zjTzFileMapper.batchDeleteUpdateZjTzFile(delOpinionFileList, opinionFile);
    					}
    					zjTzThreeShareholderBillMapper.batchDeleteUpdateZjTzThreeShareholderBill(delOpinionList, opinion);
    				}
    			}
    		}
    		ZjTzThreeShareholder zjTzThreeShareholder = new ZjTzThreeShareholder();
    		zjTzThreeShareholder.setModifyUserInfo(userKey, realName);
    		flag = zjTzThreeShareholderMapper.batchDeleteUpdateZjTzThreeShareholder(zjTzThreeShareholderList, zjTzThreeShareholder);
    	}
    	// ??????
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.delete",zjTzThreeShareholderList);
    	}
    }

}