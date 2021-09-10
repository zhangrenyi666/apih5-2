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
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	// 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzExecutiveMeet.getProjectId(), true)) {
            	zjTzExecutiveMeet.setProjectId("");
            	zjTzExecutiveMeet.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzExecutiveMeet.getProjectId(), true)) {
            	zjTzExecutiveMeet.setProjectId("");
            }
        }
        
        
        if(zjTzExecutiveMeet.getBusinessDateList() != null && zjTzExecutiveMeet.getBusinessDateList().size() >1) {
        	zjTzExecutiveMeet.setStartTime(zjTzExecutiveMeet.getBusinessDateList().get(0));
        	zjTzExecutiveMeet.setEndTime(zjTzExecutiveMeet.getBusinessDateList().get(1));
        }
        // 分页查询
        PageHelper.startPage(zjTzExecutiveMeet.getPage(),zjTzExecutiveMeet.getLimit());
        // 获取数据
        List<ZjTzExecutiveMeet> zjTzExecutiveMeetList = zjTzExecutiveMeetMapper.selectByZjTzExecutiveMeetList(zjTzExecutiveMeet);
        for (ZjTzExecutiveMeet zjTzExecutiveMeet2 : zjTzExecutiveMeetList) {
        	//附件
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzExecutiveMeet2.getExecutiveMeetId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzExecutiveMeet2.setZjTzFileList(zjTzFileList);
        	//人员
        	ZjTzExecutivePersonnel personnel = new ZjTzExecutivePersonnel();
        	personnel.setExecutiveMeetId(zjTzExecutiveMeet2.getExecutiveMeetId());
        	List<ZjTzExecutivePersonnel> personnelList = zjTzExecutivePersonnelMapper.selectByZjTzExecutivePersonnelList(personnel);
        	zjTzExecutiveMeet2.setPersonnelList(personnelList);
		}
        // 得到分页信息
        PageInfo<ZjTzExecutiveMeet> p = new PageInfo<>(zjTzExecutiveMeetList);

        return repEntity.okList(zjTzExecutiveMeetList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzExecutiveMeetDetails(ZjTzExecutiveMeet zjTzExecutiveMeet) {
        if (zjTzExecutiveMeet == null) {
            zjTzExecutiveMeet = new ZjTzExecutiveMeet();
        }
        // 获取数据
        ZjTzExecutiveMeet dbZjTzExecutiveMeet = zjTzExecutiveMeetMapper.selectByPrimaryKey(zjTzExecutiveMeet.getExecutiveMeetId());
        // 数据存在
        if (dbZjTzExecutiveMeet != null) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(dbZjTzExecutiveMeet.getExecutiveMeetId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzExecutiveMeet.setZjTzFileList(zjTzFileList);
        	return repEntity.ok(dbZjTzExecutiveMeet);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
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
        	return repEntity.layerMessage("no", "该项目下这个届次已经添加，请去变更吧！");
        }
        
        zjTzExecutiveMeet.setExecutiveMeetId(UuidUtil.generate());
        zjTzExecutiveMeet.setChangeNum(0);
        zjTzExecutiveMeet.setStatusId("0");
        zjTzExecutiveMeet.setStatusName("未审核");
        zjTzExecutiveMeet.setCreateUserInfo(userKey, realName);
        if (StrUtil.isNotEmpty(zjTzExecutiveMeet.getPeriodId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("huiYiJieCi", zjTzExecutiveMeet.getPeriodId());
        	zjTzExecutiveMeet.setPeriodName(openBankName);
        }
        int flag = zjTzExecutiveMeetMapper.insert(zjTzExecutiveMeet);
       
        //同时新增一条变更记录==  人员是双倍增加,附件也是双倍增加
        ZjTzExecutiveMeetChangeRecord changeRecord = new ZjTzExecutiveMeetChangeRecord();
        changeRecord.setExecutiveMeetChangeRecordId(UuidUtil.generate());
        changeRecord.setExecutiveMeetId(zjTzExecutiveMeet.getExecutiveMeetId());
        // 项目id
        changeRecord.setProjectId(zjTzExecutiveMeet.getProjectId());
        // 董事会届次id
        changeRecord.setPeriodId(zjTzExecutiveMeet.getPeriodId());
        // 届次name
        changeRecord.setPeriodName(zjTzExecutiveMeet.getPeriodName());
        // 填报日期
        changeRecord.setBusinessDate(zjTzExecutiveMeet.getBusinessDate());
        // 任职期限开始时间
        changeRecord.setStartDate(zjTzExecutiveMeet.getStartDate());
        // 任职期限结束时间
        changeRecord.setEndDate(zjTzExecutiveMeet.getEndDate());
        // 备注
        changeRecord.setRemarks(zjTzExecutiveMeet.getRemarks());
        // 评审状态id
        changeRecord.setStatusId(zjTzExecutiveMeet.getStatusId());
        // 评审状态name
        changeRecord.setStatusName(zjTzExecutiveMeet.getStatusName());
        // 上报内容
        changeRecord.setConentDesc(zjTzExecutiveMeet.getConentDesc());
        // 变更次数
        changeRecord.setChangeNum(zjTzExecutiveMeet.getChangeNum());
        //法定代表人
        changeRecord.setLegal(zjTzExecutiveMeet.getLegal());
        changeRecord.setCreateUserInfo(userKey, realName);
        flag = zjTzExecutiveMeetChangeRecordMapper.insert(changeRecord);
        //新增人员
        //人员的list
        if(zjTzExecutiveMeet.getPersonnelList() != null && zjTzExecutiveMeet.getPersonnelList().size() >0) {
        	for (ZjTzExecutivePersonnel personnel : zjTzExecutiveMeet.getPersonnelList()) {
        		//主键id
        		personnel.setExecutivePersonnelId(UuidUtil.generate());
        		//
        		personnel.setExecutiveMeetId(zjTzExecutiveMeet.getExecutiveMeetId());
                // 董事长旧
        		personnel.setDirectorzOld(personnel.getDirectorz());
                // 董事旧
        		personnel.setDirectorOld(personnel.getDirector());
                // 监事长旧
        		personnel.setSupervisorzOld(personnel.getSupervisorz());
                // 监事旧
        		personnel.setSupervisorOld(personnel.getSupervisor());
                // 项目公司总经理旧
        		personnel.setManagerOld(personnel.getManager()); 
        		//股东类型Id
        		personnel.setShareTypeId(personnel.getShareTypeId());
        		//股东类型
        		if (StrUtil.isNotEmpty(personnel.getShareTypeId())) {
        			String openBankName = baseCodeService.getBaseCodeItemName("guDongLeiXing", personnel.getShareTypeId());
        			personnel.setShareType(openBankName);
        		}
                personnel.setCreateUserInfo(userKey, realName);
                flag = zjTzExecutivePersonnelMapper.insert(personnel);
                //主键id==copy
        		personnel.setExecutivePersonnelId(UuidUtil.generate());
        		personnel.setExecutiveMeetId(changeRecord.getExecutiveMeetChangeRecordId());
        		flag = zjTzExecutivePersonnelMapper.insert(personnel);
        	}
        }
        // 附件list
        List<ZjTzFile> zjTzFileList = zjTzExecutiveMeet.getZjTzFileList();
        if(zjTzFileList != null && zjTzFileList.size()>0) {
        	for(ZjTzFile zjTzFile:zjTzFileList) {
        		zjTzFile.setUid(UuidUtil.generate());
        		zjTzFile.setOtherId(zjTzExecutiveMeet.getExecutiveMeetId());
        		zjTzFile.setCreateUserInfo(userKey, realName);
        		zjTzFileMapper.insert(zjTzFile);
        		//主键id = copy
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
           //人员先删除再新增
           ZjTzExecutivePersonnel personnelSelect = new ZjTzExecutivePersonnel();
           personnelSelect.setExecutiveMeetId(dbzjTzExecutiveMeet.getExecutiveMeetId());
           List<ZjTzExecutivePersonnel> deletePersonnelList = zjTzExecutivePersonnelMapper.selectByZjTzExecutivePersonnelList(personnelSelect);
           if(deletePersonnelList != null && deletePersonnelList.size()>0) {
        	   personnelSelect.setModifyUserInfo(userKey, realName);
        	   flag = zjTzExecutivePersonnelMapper.batchDeleteUpdateZjTzExecutivePersonnel(deletePersonnelList, personnelSelect);
           }
           //查记录表最新一条数据==进行人员先删除再新增
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
           // 人员list
           List<ZjTzExecutivePersonnel> personnelList = zjTzExecutiveMeet.getPersonnelList();
           if(personnelList != null && personnelList.size()>0) {
        	   for(ZjTzExecutivePersonnel personnel:personnelList) {
        		   if(StrUtil.isEmpty(personnel.getExecutiveMeetId())) {
        			   personnel.setDirectorzOld(personnel.getDirectorz());
        			   personnel.setDirectorOld(personnel.getDirector());
        			   personnel.setSupervisorzOld(personnel.getSupervisorz());
        			   personnel.setSupervisorOld(personnel.getSupervisor());
        			   personnel.setManagerOld(personnel.getManager()); 
        			   //股东类型Id
        			   personnel.setShareTypeId(personnel.getShareTypeId());
        			   //股东类型
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
        // 失败
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
        		// 删除附件
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzExecutiveMeet.getExecutiveMeetId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        		//删除人员
        		ZjTzExecutivePersonnel delPersonnel = new ZjTzExecutivePersonnel();
        		delPersonnel.setExecutiveMeetId(zjTzExecutiveMeet.getExecutiveMeetId());
        		List<ZjTzExecutivePersonnel> delPersonList = zjTzExecutivePersonnelMapper.selectByZjTzExecutivePersonnelList(delPersonnel);
        		if(delPersonList != null &&delPersonList.size() >0) {
        			delPersonnel.setModifyUserInfo(userKey, realName);
        			flag = zjTzExecutivePersonnelMapper.batchDeleteUpdateZjTzExecutivePersonnel(delPersonList, delPersonnel);
        		}
        		//删除变更记录表
        		ZjTzExecutiveMeetChangeRecord changeRecord = new ZjTzExecutiveMeetChangeRecord();
        		changeRecord.setExecutiveMeetId(zjTzExecutiveMeet.getExecutiveMeetId());
        		List<ZjTzExecutiveMeetChangeRecord> changeRecords = zjTzExecutiveMeetChangeRecordMapper.selectByZjTzExecutiveMeetChangeRecordList(changeRecord);
        		if(changeRecords != null && changeRecords.size() >0) {
        			for (ZjTzExecutiveMeetChangeRecord delRecord : changeRecords) {
        				//删除附件
                		ZjTzFile zjTzFileRecordSelect = new ZjTzFile();
                		zjTzFileRecordSelect.setOtherId(delRecord.getExecutiveMeetChangeRecordId());
                		List<ZjTzFile> deleteZjTzFileRecordList = zjTzFileMapper.selectByZjTzFileList(zjTzFileRecordSelect);
                		if(deleteZjTzFileRecordList != null && deleteZjTzFileRecordList.size()>0) {
                			zjTzFileRecordSelect.setModifyUserInfo(userKey, realName);
                			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileRecordList, zjTzFileRecordSelect);
                		}
        				//删除人员
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
        // 失败
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
			// 分页查询
			PageHelper.startPage(zjTzExecutiveMeet.getPage(),zjTzExecutiveMeet.getLimit());
			//股东人员
			ZjTzProShareholderInfo record = new ZjTzProShareholderInfo();
			record.setProjectId(zjTzExecutiveMeet.getProjectId());
			List<ZjTzProShareholderInfo> infos = zjTzProShareholderInfoMapper.selectByZjTzProShareholderInfoList(record);
			for (ZjTzProShareholderInfo zjTzProShareholderInfo : infos) {
				zjTzProShareholderInfo.setExecutivePersonnelId(zjTzProShareholderInfo.getShareholderInfoId());
			}
			// 得到分页信息
			PageInfo<ZjTzProShareholderInfo> p = new PageInfo<>(infos);
			return repEntity.okList(infos, p.getTotal());
		}else if (StrUtil.isNotEmpty(zjTzExecutiveMeet.getExecutiveMeetId())) {
			// 分页查询
			PageHelper.startPage(zjTzExecutiveMeet.getPage(),zjTzExecutiveMeet.getLimit());
			// 获取数据
			ZjTzExecutivePersonnel personnel = new ZjTzExecutivePersonnel();
			personnel.setExecutiveMeetId(zjTzExecutiveMeet.getExecutiveMeetId());
			List<ZjTzExecutivePersonnel> personnelList = zjTzExecutivePersonnelMapper.selectByZjTzExecutivePersonnelList(personnel);
			// 得到分页信息
			PageInfo<ZjTzExecutivePersonnel> p = new PageInfo<>(personnelList);
			return repEntity.okList(personnelList, p.getTotal());
		}else {
			return repEntity.layerMessage("no", "项目id或者主键id没有传");
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
			// 董事会届次id
			addRecord.setPeriodId(dbzjTzExecutiveMeet.getPeriodId());
			// 届次name
			if (StrUtil.isNotEmpty(dbzjTzExecutiveMeet.getPeriodId())) {
				String openBankName = baseCodeService.getBaseCodeItemName("huiYiJieCi", zjTzExecutiveMeet.getPeriodId());
				addRecord.setPeriodName(openBankName);
			}
			// 填报日期
			addRecord.setBusinessDate(dbzjTzExecutiveMeet.getBusinessDate());
			// 任职期限开始时间
			addRecord.setStartDate(dbzjTzExecutiveMeet.getStartDate());
			// 任职期限结束时间
			addRecord.setEndDate(dbzjTzExecutiveMeet.getEndDate());
			// 备注
			addRecord.setRemarks(dbzjTzExecutiveMeet.getRemarks());
			// 上报内容
			addRecord.setConentDesc(dbzjTzExecutiveMeet.getConentDesc());
			addRecord.setChangeNum(changeNum);
			addRecord.setCreateUserInfo(userKey, realName);
			flag = zjTzExecutiveMeetChangeRecordMapper.insert(addRecord);
			
			// 项目id
			dbzjTzExecutiveMeet.setProjectId(zjTzExecutiveMeet.getProjectId());
			// 董事会届次id
			dbzjTzExecutiveMeet.setPeriodId(zjTzExecutiveMeet.getPeriodId());
			// 届次name
			if (StrUtil.isNotEmpty(dbzjTzExecutiveMeet.getPeriodId())) {
				String openBankName = baseCodeService.getBaseCodeItemName("huiYiJieCi", zjTzExecutiveMeet.getPeriodId());
				dbzjTzExecutiveMeet.setPeriodName(openBankName);
			}
			// 填报日期
			dbzjTzExecutiveMeet.setBusinessDate(zjTzExecutiveMeet.getBusinessDate());
			// 任职期限开始时间
			dbzjTzExecutiveMeet.setStartDate(zjTzExecutiveMeet.getStartDate());
			// 任职期限结束时间
			dbzjTzExecutiveMeet.setEndDate(zjTzExecutiveMeet.getEndDate());
			// 备注
			dbzjTzExecutiveMeet.setRemarks(zjTzExecutiveMeet.getRemarks());
			// 上报内容
			dbzjTzExecutiveMeet.setConentDesc(zjTzExecutiveMeet.getConentDesc());
			dbzjTzExecutiveMeet.setChangeNum(changeNum);
			dbzjTzExecutiveMeet.setModifyUserInfo(userKey, realName);
			flag = zjTzExecutiveMeetMapper.updateByPrimaryKey(dbzjTzExecutiveMeet);
			// 删除附件再新增
	           ZjTzFile zjTzFileSelect = new ZjTzFile();
	           zjTzFileSelect.setOtherId(dbzjTzExecutiveMeet.getExecutiveMeetId());
	           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
	           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
	               zjTzFileSelect.setModifyUserInfo(userKey, realName);
	               zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
	           }
	           // 附件list
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

	           //人员先删除再新增
	           ZjTzExecutivePersonnel personnelSelect = new ZjTzExecutivePersonnel();
	           personnelSelect.setExecutiveMeetId(dbzjTzExecutiveMeet.getExecutiveMeetId());
	           List<ZjTzExecutivePersonnel> deletePersonnelList = zjTzExecutivePersonnelMapper.selectByZjTzExecutivePersonnelList(personnelSelect);
	           if(deletePersonnelList != null && deletePersonnelList.size()>0) {
	        	   personnelSelect.setModifyUserInfo(userKey, realName);
	        	   zjTzExecutivePersonnelMapper.batchDeleteUpdateZjTzExecutivePersonnel(deletePersonnelList, personnelSelect);
	           }
	           // 人员list
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
		// 失败
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
        	dbzjTzExecutiveMeet.setStatusName("上报");
        	dbzjTzExecutiveMeet.setModifyUserInfo(userKey, realName);
        	flag = zjTzExecutiveMeetMapper.updateByPrimaryKey(dbzjTzExecutiveMeet);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzExecutiveMeet);
        }
	}
}