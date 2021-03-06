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
import com.apih5.mybatis.dao.ZjTzExecutiveMeetChangeRecordMapper;
import com.apih5.mybatis.dao.ZjTzExecutiveMeetMapper;
import com.apih5.mybatis.dao.ZjTzExecutivePersonnelMapper;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzProShareholderInfoMapper;
import com.apih5.mybatis.pojo.ZjTzExecutiveMeet;
import com.apih5.mybatis.pojo.ZjTzExecutiveMeetChangeRecord;
import com.apih5.mybatis.pojo.ZjTzExecutivePersonnel;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzProShareholderInfo;
import com.apih5.service.ZjTzExecutiveMeetService;
import com.apih5.service.ZjTzPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzExecutiveMeetService")
public class ZjTzExecutiveMeetServiceImpl implements ZjTzExecutiveMeetService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzExecutiveMeetMapper zjTzExecutiveMeetMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private ZjTzExecutivePersonnelMapper zjTzExecutivePersonnelMapper;
    
    @Autowired(required = true)
    private ZjTzProShareholderInfoMapper zjTzProShareholderInfoMapper;
    
    @Autowired(required = true)
    private ZjTzExecutiveMeetChangeRecordMapper zjTzExecutiveMeetChangeRecordMapper;
    
    @Autowired(required = true)
    private BaseCodeService baseCodeService;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    

    @Override
    public ResponseEntity getZjTzExecutiveMeetListByCondition(ZjTzExecutiveMeet zjTzExecutiveMeet) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userId = TokenUtils.getUserId(request);
    	String ext1 = TokenUtils.getExt1(request);
    	if (zjTzExecutiveMeet == null) {
            zjTzExecutiveMeet = new ZjTzExecutiveMeet();
        } 
        // ????????????????????????
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	// ???????????????????????????????????????sql?????????
            if(StrUtil.equals("all", zjTzExecutiveMeet.getProjectId(), true)) {
            	zjTzExecutiveMeet.setProjectId("");
            	zjTzExecutiveMeet.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // ???????????????
            if(StrUtil.equals("all", zjTzExecutiveMeet.getProjectId(), true)) {
            	zjTzExecutiveMeet.setProjectId("");
            }
        }
        
        
        if(zjTzExecutiveMeet.getBusinessDateList() != null && zjTzExecutiveMeet.getBusinessDateList().size() >1) {
        	zjTzExecutiveMeet.setStartTime(zjTzExecutiveMeet.getBusinessDateList().get(0));
        	zjTzExecutiveMeet.setEndTime(zjTzExecutiveMeet.getBusinessDateList().get(1));
        }
        // ????????????
        PageHelper.startPage(zjTzExecutiveMeet.getPage(),zjTzExecutiveMeet.getLimit());
        // ????????????
        List<ZjTzExecutiveMeet> zjTzExecutiveMeetList = zjTzExecutiveMeetMapper.selectByZjTzExecutiveMeetList(zjTzExecutiveMeet);
        for (ZjTzExecutiveMeet zjTzExecutiveMeet2 : zjTzExecutiveMeetList) {
        	//??????
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzExecutiveMeet2.getExecutiveMeetId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzExecutiveMeet2.setZjTzFileList(zjTzFileList);
        	//??????
        	ZjTzExecutivePersonnel personnel = new ZjTzExecutivePersonnel();
        	personnel.setExecutiveMeetId(zjTzExecutiveMeet2.getExecutiveMeetId());
        	List<ZjTzExecutivePersonnel> personnelList = zjTzExecutivePersonnelMapper.selectByZjTzExecutivePersonnelList(personnel);
        	zjTzExecutiveMeet2.setPersonnelList(personnelList);
		}
        // ??????????????????
        PageInfo<ZjTzExecutiveMeet> p = new PageInfo<>(zjTzExecutiveMeetList);

        return repEntity.okList(zjTzExecutiveMeetList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzExecutiveMeetDetails(ZjTzExecutiveMeet zjTzExecutiveMeet) {
        if (zjTzExecutiveMeet == null) {
            zjTzExecutiveMeet = new ZjTzExecutiveMeet();
        }
        // ????????????
        ZjTzExecutiveMeet dbZjTzExecutiveMeet = zjTzExecutiveMeetMapper.selectByPrimaryKey(zjTzExecutiveMeet.getExecutiveMeetId());
        // ????????????
        if (dbZjTzExecutiveMeet != null) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(dbZjTzExecutiveMeet.getExecutiveMeetId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzExecutiveMeet.setZjTzFileList(zjTzFileList);
        	return repEntity.ok(dbZjTzExecutiveMeet);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzExecutiveMeet(ZjTzExecutiveMeet zjTzExecutiveMeet) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        
        ZjTzExecutiveMeet meet = new ZjTzExecutiveMeet();
        meet.setPeriodId(zjTzExecutiveMeet.getPeriodId());
        meet.setProjectId(zjTzExecutiveMeet.getProjectId());
        List<ZjTzExecutiveMeet> meetList = zjTzExecutiveMeetMapper.selectByZjTzExecutiveMeetList(meet);
        if(meetList != null && meetList.size() >0) {
        	return repEntity.layerMessage("no", "?????????????????????????????????????????????????????????");
        }
        
        zjTzExecutiveMeet.setExecutiveMeetId(UuidUtil.generate());
        zjTzExecutiveMeet.setChangeNum(0);
        zjTzExecutiveMeet.setStatusId("0");
        zjTzExecutiveMeet.setStatusName("?????????");
        zjTzExecutiveMeet.setCreateUserInfo(userKey, realName);
        if (StrUtil.isNotEmpty(zjTzExecutiveMeet.getPeriodId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("huiYiJieCi", zjTzExecutiveMeet.getPeriodId());
        	zjTzExecutiveMeet.setPeriodName(openBankName);
        }
        int flag = zjTzExecutiveMeetMapper.insert(zjTzExecutiveMeet);
       
        //??????????????????????????????==  ?????????????????????,????????????????????????
        ZjTzExecutiveMeetChangeRecord changeRecord = new ZjTzExecutiveMeetChangeRecord();
        changeRecord.setExecutiveMeetChangeRecordId(UuidUtil.generate());
        changeRecord.setExecutiveMeetId(zjTzExecutiveMeet.getExecutiveMeetId());
        // ??????id
        changeRecord.setProjectId(zjTzExecutiveMeet.getProjectId());
        // ???????????????id
        changeRecord.setPeriodId(zjTzExecutiveMeet.getPeriodId());
        // ??????name
        changeRecord.setPeriodName(zjTzExecutiveMeet.getPeriodName());
        // ????????????
        changeRecord.setBusinessDate(zjTzExecutiveMeet.getBusinessDate());
        // ????????????????????????
        changeRecord.setStartDate(zjTzExecutiveMeet.getStartDate());
        // ????????????????????????
        changeRecord.setEndDate(zjTzExecutiveMeet.getEndDate());
        // ??????
        changeRecord.setRemarks(zjTzExecutiveMeet.getRemarks());
        // ????????????id
        changeRecord.setStatusId(zjTzExecutiveMeet.getStatusId());
        // ????????????name
        changeRecord.setStatusName(zjTzExecutiveMeet.getStatusName());
        // ????????????
        changeRecord.setConentDesc(zjTzExecutiveMeet.getConentDesc());
        // ????????????
        changeRecord.setChangeNum(zjTzExecutiveMeet.getChangeNum());
        //???????????????
        changeRecord.setLegal(zjTzExecutiveMeet.getLegal());
        changeRecord.setCreateUserInfo(userKey, realName);
        flag = zjTzExecutiveMeetChangeRecordMapper.insert(changeRecord);
        //????????????
        //?????????list
        if(zjTzExecutiveMeet.getPersonnelList() != null && zjTzExecutiveMeet.getPersonnelList().size() >0) {
        	for (ZjTzExecutivePersonnel personnel : zjTzExecutiveMeet.getPersonnelList()) {
        		//??????id
        		personnel.setExecutivePersonnelId(UuidUtil.generate());
        		//
        		personnel.setExecutiveMeetId(zjTzExecutiveMeet.getExecutiveMeetId());
                // ????????????
        		personnel.setDirectorzOld(personnel.getDirectorz());
                // ?????????
        		personnel.setDirectorOld(personnel.getDirector());
                // ????????????
        		personnel.setSupervisorzOld(personnel.getSupervisorz());
                // ?????????
        		personnel.setSupervisorOld(personnel.getSupervisor());
                // ????????????????????????
        		personnel.setManagerOld(personnel.getManager()); 
        		//????????????Id
        		personnel.setShareTypeId(personnel.getShareTypeId());
        		//????????????
        		if (StrUtil.isNotEmpty(personnel.getShareTypeId())) {
        			String openBankName = baseCodeService.getBaseCodeItemName("guDongLeiXing", personnel.getShareTypeId());
        			personnel.setShareType(openBankName);
        		}
                personnel.setCreateUserInfo(userKey, realName);
                flag = zjTzExecutivePersonnelMapper.insert(personnel);
                //??????id==copy
        		personnel.setExecutivePersonnelId(UuidUtil.generate());
        		personnel.setExecutiveMeetId(changeRecord.getExecutiveMeetChangeRecordId());
        		flag = zjTzExecutivePersonnelMapper.insert(personnel);
        	}
        }
        // ??????list
        List<ZjTzFile> zjTzFileList = zjTzExecutiveMeet.getZjTzFileList();
        if(zjTzFileList != null && zjTzFileList.size()>0) {
        	for(ZjTzFile zjTzFile:zjTzFileList) {
        		zjTzFile.setUid(UuidUtil.generate());
        		zjTzFile.setOtherId(zjTzExecutiveMeet.getExecutiveMeetId());
        		zjTzFile.setCreateUserInfo(userKey, realName);
        		zjTzFileMapper.insert(zjTzFile);
        		//??????id = copy
        		zjTzFile.setUid(UuidUtil.generate());
        		zjTzFile.setOtherId(changeRecord.getExecutiveMeetChangeRecordId());
        		zjTzFileMapper.insert(zjTzFile);
        	}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzExecutiveMeet);
        }
    }

    @Override
    public ResponseEntity updateZjTzExecutiveMeet(ZjTzExecutiveMeet zjTzExecutiveMeet) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzExecutiveMeet dbzjTzExecutiveMeet = zjTzExecutiveMeetMapper.selectByPrimaryKey(zjTzExecutiveMeet.getExecutiveMeetId());
        if (dbzjTzExecutiveMeet != null && StrUtil.isNotEmpty(dbzjTzExecutiveMeet.getExecutiveMeetId())) {
           //????????????????????????
           ZjTzExecutivePersonnel personnelSelect = new ZjTzExecutivePersonnel();
           personnelSelect.setExecutiveMeetId(dbzjTzExecutiveMeet.getExecutiveMeetId());
           List<ZjTzExecutivePersonnel> deletePersonnelList = zjTzExecutivePersonnelMapper.selectByZjTzExecutivePersonnelList(personnelSelect);
           if(deletePersonnelList != null && deletePersonnelList.size()>0) {
        	   personnelSelect.setModifyUserInfo(userKey, realName);
        	   flag = zjTzExecutivePersonnelMapper.batchDeleteUpdateZjTzExecutivePersonnel(deletePersonnelList, personnelSelect);
           }
           //??????????????????????????????==??????????????????????????????
           ZjTzExecutiveMeetChangeRecord record = new ZjTzExecutiveMeetChangeRecord();
           record.setExecutiveMeetId(dbzjTzExecutiveMeet.getExecutiveMeetId());
           List<ZjTzExecutiveMeetChangeRecord> records = zjTzExecutiveMeetChangeRecordMapper.selectByZjTzExecutiveMeetChangeRecordList(record);
           String executiveMeetChangeRecordId = "";
           if(records != null &&records.size() >0) {
        	   executiveMeetChangeRecordId = records.get(0).getExecutiveMeetChangeRecordId();
        	   ZjTzExecutivePersonnel personnelRecordSelect = new ZjTzExecutivePersonnel();
        	   personnelRecordSelect.setExecutiveMeetId(records.get(0).getExecutiveMeetChangeRecordId());
        	   List<ZjTzExecutivePersonnel> deletePersonnelRecordList = zjTzExecutivePersonnelMapper.selectByZjTzExecutivePersonnelList(personnelRecordSelect);
        	   if(deletePersonnelRecordList != null && deletePersonnelRecordList.size()>0) {
        		   personnelRecordSelect.setModifyUserInfo(userKey, realName);
        		   flag = zjTzExecutivePersonnelMapper.batchDeleteUpdateZjTzExecutivePersonnel(deletePersonnelRecordList, personnelRecordSelect);
        	   }
           }
           // ??????list
           List<ZjTzExecutivePersonnel> personnelList = zjTzExecutiveMeet.getPersonnelList();
           if(personnelList != null && personnelList.size()>0) {
        	   for(ZjTzExecutivePersonnel personnel:personnelList) {
        		   if(StrUtil.isEmpty(personnel.getExecutiveMeetId())) {
        			   personnel.setDirectorzOld(personnel.getDirectorz());
        			   personnel.setDirectorOld(personnel.getDirector());
        			   personnel.setSupervisorzOld(personnel.getSupervisorz());
        			   personnel.setSupervisorOld(personnel.getSupervisor());
        			   personnel.setManagerOld(personnel.getManager()); 
        			   //????????????Id
        			   personnel.setShareTypeId(personnel.getShareTypeId());
        			   //????????????
        			   if (StrUtil.isNotEmpty(personnel.getShareTypeId())) {
        				   String openBankName = baseCodeService.getBaseCodeItemName("guDongLeiXing", personnel.getShareTypeId());
        				   personnel.setShareType(openBankName);
        			   }
        		   }
        		   personnel.setExecutivePersonnelId(UuidUtil.generate());
        		   personnel.setExecutiveMeetId(zjTzExecutiveMeet.getExecutiveMeetId());
        		   personnel.setCreateUserInfo(userKey, realName);
        		   flag = zjTzExecutivePersonnelMapper.insert(personnel);
        		   //doubleADD===
        		   personnel.setExecutivePersonnelId(UuidUtil.generate());
        		   personnel.setExecutiveMeetId(executiveMeetChangeRecordId);
        		   personnel.setCreateUserInfo(userKey, realName);
        		   flag = zjTzExecutivePersonnelMapper.insert(personnel);
        	   }
           }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzExecutiveMeet);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzExecutiveMeet(List<ZjTzExecutiveMeet> zjTzExecutiveMeetList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzExecutiveMeetList != null && zjTzExecutiveMeetList.size() > 0) {
        	for (ZjTzExecutiveMeet zjTzExecutiveMeet : zjTzExecutiveMeetList) {
        		// ????????????
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzExecutiveMeet.getExecutiveMeetId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        		//????????????
        		ZjTzExecutivePersonnel delPersonnel = new ZjTzExecutivePersonnel();
        		delPersonnel.setExecutiveMeetId(zjTzExecutiveMeet.getExecutiveMeetId());
        		List<ZjTzExecutivePersonnel> delPersonList = zjTzExecutivePersonnelMapper.selectByZjTzExecutivePersonnelList(delPersonnel);
        		if(delPersonList != null &&delPersonList.size() >0) {
        			delPersonnel.setModifyUserInfo(userKey, realName);
        			flag = zjTzExecutivePersonnelMapper.batchDeleteUpdateZjTzExecutivePersonnel(delPersonList, delPersonnel);
        		}
        		//?????????????????????
        		ZjTzExecutiveMeetChangeRecord changeRecord = new ZjTzExecutiveMeetChangeRecord();
        		changeRecord.setExecutiveMeetId(zjTzExecutiveMeet.getExecutiveMeetId());
        		List<ZjTzExecutiveMeetChangeRecord> changeRecords = zjTzExecutiveMeetChangeRecordMapper.selectByZjTzExecutiveMeetChangeRecordList(changeRecord);
        		if(changeRecords != null && changeRecords.size() >0) {
        			for (ZjTzExecutiveMeetChangeRecord delRecord : changeRecords) {
        				//????????????
                		ZjTzFile zjTzFileRecordSelect = new ZjTzFile();
                		zjTzFileRecordSelect.setOtherId(delRecord.getExecutiveMeetChangeRecordId());
                		List<ZjTzFile> deleteZjTzFileRecordList = zjTzFileMapper.selectByZjTzFileList(zjTzFileRecordSelect);
                		if(deleteZjTzFileRecordList != null && deleteZjTzFileRecordList.size()>0) {
                			zjTzFileRecordSelect.setModifyUserInfo(userKey, realName);
                			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileRecordList, zjTzFileRecordSelect);
                		}
        				//????????????
                		ZjTzExecutivePersonnel delPersonnelRecord = new ZjTzExecutivePersonnel();
                		delPersonnelRecord.setExecutiveMeetId(delRecord.getExecutiveMeetChangeRecordId());
                		List<ZjTzExecutivePersonnel> delPersonRecordList = zjTzExecutivePersonnelMapper.selectByZjTzExecutivePersonnelList(delPersonnelRecord);
                		if(delPersonRecordList != null &&delPersonRecordList.size() >0) {
                			delPersonnelRecord.setModifyUserInfo(userKey, realName);
                			flag = zjTzExecutivePersonnelMapper.batchDeleteUpdateZjTzExecutivePersonnel(delPersonRecordList, delPersonnelRecord);
                		}
					}
        			changeRecord.setModifyUserInfo(userKey, realName);
        			flag = zjTzExecutiveMeetChangeRecordMapper.batchDeleteUpdateZjTzExecutiveMeetChangeRecord(changeRecords, changeRecord);
        		}
        	}
        	ZjTzExecutiveMeet zjTzExecutiveMeet = new ZjTzExecutiveMeet();
        	zjTzExecutiveMeet.setModifyUserInfo(userKey, realName);
        	flag = zjTzExecutiveMeetMapper.batchDeleteUpdateZjTzExecutiveMeet(zjTzExecutiveMeetList, zjTzExecutiveMeet);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzExecutiveMeetList);
        }
    }

	@Override
	public ResponseEntity getZjTzExecutiveMeetListFromProjectShareholder(ZjTzExecutiveMeet zjTzExecutiveMeet) {
		if(StrUtil.isNotEmpty(zjTzExecutiveMeet.getProjectId())) {
			// ????????????
			PageHelper.startPage(zjTzExecutiveMeet.getPage(),zjTzExecutiveMeet.getLimit());
			//????????????
			ZjTzProShareholderInfo record = new ZjTzProShareholderInfo();
			record.setProjectId(zjTzExecutiveMeet.getProjectId());
			List<ZjTzProShareholderInfo> infos = zjTzProShareholderInfoMapper.selectByZjTzProShareholderInfoList(record);
			for (ZjTzProShareholderInfo zjTzProShareholderInfo : infos) {
				zjTzProShareholderInfo.setExecutivePersonnelId(zjTzProShareholderInfo.getShareholderInfoId());
			}
			// ??????????????????
			PageInfo<ZjTzProShareholderInfo> p = new PageInfo<>(infos);
			return repEntity.okList(infos, p.getTotal());
		}else if (StrUtil.isNotEmpty(zjTzExecutiveMeet.getExecutiveMeetId())) {
			// ????????????
			PageHelper.startPage(zjTzExecutiveMeet.getPage(),zjTzExecutiveMeet.getLimit());
			// ????????????
			ZjTzExecutivePersonnel personnel = new ZjTzExecutivePersonnel();
			personnel.setExecutiveMeetId(zjTzExecutiveMeet.getExecutiveMeetId());
			List<ZjTzExecutivePersonnel> personnelList = zjTzExecutivePersonnelMapper.selectByZjTzExecutivePersonnelList(personnel);
			// ??????????????????
			PageInfo<ZjTzExecutivePersonnel> p = new PageInfo<>(personnelList);
			return repEntity.okList(personnelList, p.getTotal());
		}else {
			return repEntity.layerMessage("no", "??????id????????????id?????????");
		}
	}

	@Override
	public ResponseEntity changeZjTzExecutiveMeet(ZjTzExecutiveMeet zjTzExecutiveMeet) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		int changeNum = 1;
		ZjTzExecutiveMeet dbzjTzExecutiveMeet = zjTzExecutiveMeetMapper.selectByPrimaryKey(zjTzExecutiveMeet.getExecutiveMeetId());
		if (dbzjTzExecutiveMeet != null && StrUtil.isNotEmpty(dbzjTzExecutiveMeet.getExecutiveMeetId())) {
			ZjTzExecutiveMeetChangeRecord record = new ZjTzExecutiveMeetChangeRecord();
			record.setExecutiveMeetId(zjTzExecutiveMeet.getExecutiveMeetId());
			List<ZjTzExecutiveMeetChangeRecord> changeRecords = zjTzExecutiveMeetChangeRecordMapper.selectByZjTzExecutiveMeetChangeRecordList(record);
			if(changeRecords != null && changeRecords.size() >0) {
				changeNum = changeRecords.size();	
			}
			ZjTzExecutiveMeetChangeRecord addRecord = new ZjTzExecutiveMeetChangeRecord();
			addRecord.setExecutiveMeetChangeRecordId(UuidUtil.generate());
			addRecord.setExecutiveMeetId(dbzjTzExecutiveMeet.getExecutiveMeetId());
			addRecord.setProjectId(dbzjTzExecutiveMeet.getProjectId());
			// ???????????????id
			addRecord.setPeriodId(dbzjTzExecutiveMeet.getPeriodId());
			// ??????name
			if (StrUtil.isNotEmpty(dbzjTzExecutiveMeet.getPeriodId())) {
				String openBankName = baseCodeService.getBaseCodeItemName("huiYiJieCi", zjTzExecutiveMeet.getPeriodId());
				addRecord.setPeriodName(openBankName);
			}
			// ????????????
			addRecord.setBusinessDate(dbzjTzExecutiveMeet.getBusinessDate());
			// ????????????????????????
			addRecord.setStartDate(dbzjTzExecutiveMeet.getStartDate());
			// ????????????????????????
			addRecord.setEndDate(dbzjTzExecutiveMeet.getEndDate());
			// ??????
			addRecord.setRemarks(dbzjTzExecutiveMeet.getRemarks());
			// ????????????
			addRecord.setConentDesc(dbzjTzExecutiveMeet.getConentDesc());
			addRecord.setChangeNum(changeNum);
			addRecord.setCreateUserInfo(userKey, realName);
			flag = zjTzExecutiveMeetChangeRecordMapper.insert(addRecord);
			
			// ??????id
			dbzjTzExecutiveMeet.setProjectId(zjTzExecutiveMeet.getProjectId());
			// ???????????????id
			dbzjTzExecutiveMeet.setPeriodId(zjTzExecutiveMeet.getPeriodId());
			// ??????name
			if (StrUtil.isNotEmpty(dbzjTzExecutiveMeet.getPeriodId())) {
				String openBankName = baseCodeService.getBaseCodeItemName("huiYiJieCi", zjTzExecutiveMeet.getPeriodId());
				dbzjTzExecutiveMeet.setPeriodName(openBankName);
			}
			// ????????????
			dbzjTzExecutiveMeet.setBusinessDate(zjTzExecutiveMeet.getBusinessDate());
			// ????????????????????????
			dbzjTzExecutiveMeet.setStartDate(zjTzExecutiveMeet.getStartDate());
			// ????????????????????????
			dbzjTzExecutiveMeet.setEndDate(zjTzExecutiveMeet.getEndDate());
			// ??????
			dbzjTzExecutiveMeet.setRemarks(zjTzExecutiveMeet.getRemarks());
			// ????????????
			dbzjTzExecutiveMeet.setConentDesc(zjTzExecutiveMeet.getConentDesc());
			dbzjTzExecutiveMeet.setChangeNum(changeNum);
			dbzjTzExecutiveMeet.setModifyUserInfo(userKey, realName);
			flag = zjTzExecutiveMeetMapper.updateByPrimaryKey(dbzjTzExecutiveMeet);
			// ?????????????????????
	           ZjTzFile zjTzFileSelect = new ZjTzFile();
	           zjTzFileSelect.setOtherId(dbzjTzExecutiveMeet.getExecutiveMeetId());
	           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
	           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
	               zjTzFileSelect.setModifyUserInfo(userKey, realName);
	               zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
	           }
	           // ??????list
	           List<ZjTzFile> zjTzFileList = zjTzExecutiveMeet.getZjTzFileList();
	           if(zjTzFileList != null && zjTzFileList.size()>0) {
	        	   for(ZjTzFile zjTzFile:zjTzFileList) {
	        		   zjTzFile.setUid(UuidUtil.generate());
	        		   zjTzFile.setOtherId(zjTzExecutiveMeet.getExecutiveMeetId());
	        		   zjTzFile.setCreateUserInfo(userKey, realName);
	        		   zjTzFileMapper.insert(zjTzFile);
	        		   //
	        		   zjTzFile.setUid(UuidUtil.generate());
	        		   zjTzFile.setOtherId(addRecord.getExecutiveMeetChangeRecordId());
	        		   zjTzFileMapper.insert(zjTzFile);
	        	   }
	           }

	           //????????????????????????
	           ZjTzExecutivePersonnel personnelSelect = new ZjTzExecutivePersonnel();
	           personnelSelect.setExecutiveMeetId(dbzjTzExecutiveMeet.getExecutiveMeetId());
	           List<ZjTzExecutivePersonnel> deletePersonnelList = zjTzExecutivePersonnelMapper.selectByZjTzExecutivePersonnelList(personnelSelect);
	           if(deletePersonnelList != null && deletePersonnelList.size()>0) {
	        	   personnelSelect.setModifyUserInfo(userKey, realName);
	        	   zjTzExecutivePersonnelMapper.batchDeleteUpdateZjTzExecutivePersonnel(deletePersonnelList, personnelSelect);
	           }
	           // ??????list
	           List<ZjTzExecutivePersonnel> personnelList = zjTzExecutiveMeet.getPersonnelList();
	           if(personnelList != null && personnelList.size()>0) {
	        	   for(ZjTzExecutivePersonnel personnel:personnelList) {
	        		   if(StrUtil.isEmpty(personnel.getExecutiveMeetId())) {
	        			   personnel.setDirectorzOld(personnel.getDirectorz());
	        			   personnel.setDirectorOld(personnel.getDirector());
	        			   personnel.setSupervisorzOld(personnel.getSupervisorz());
	        			   personnel.setSupervisorOld(personnel.getSupervisor());
	        			   personnel.setManagerOld(personnel.getManager());  
	        		   }
	        		   personnel.setExecutivePersonnelId(UuidUtil.generate());
	        		   personnel.setExecutiveMeetId(zjTzExecutiveMeet.getExecutiveMeetId());
	        		   personnel.setCreateUserInfo(userKey, realName);
	        		   zjTzExecutivePersonnelMapper.insert(personnel);
	        		   personnel.setExecutivePersonnelId(UuidUtil.generate());
	        		   personnel.setExecutiveMeetId(addRecord.getExecutiveMeetChangeRecordId());
	        		   zjTzExecutivePersonnelMapper.insert(personnel);
	        		
	        	   }
	           }
		}
		// ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzExecutiveMeet);
        }
	}

	@Override
	public ResponseEntity correctiveZjTzExecutiveMeet(ZjTzExecutiveMeet zjTzExecutiveMeet) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzExecutiveMeet dbzjTzExecutiveMeet = zjTzExecutiveMeetMapper.selectByPrimaryKey(zjTzExecutiveMeet.getExecutiveMeetId());
        if (dbzjTzExecutiveMeet != null && StrUtil.isNotEmpty(dbzjTzExecutiveMeet.getExecutiveMeetId())) {
        	dbzjTzExecutiveMeet.setConentDesc(zjTzExecutiveMeet.getConentDesc());
        	dbzjTzExecutiveMeet.setStatusId("1");
        	dbzjTzExecutiveMeet.setStatusName("??????");
        	dbzjTzExecutiveMeet.setModifyUserInfo(userKey, realName);
        	flag = zjTzExecutiveMeetMapper.updateByPrimaryKey(dbzjTzExecutiveMeet);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzExecutiveMeet);
        }
	}
}