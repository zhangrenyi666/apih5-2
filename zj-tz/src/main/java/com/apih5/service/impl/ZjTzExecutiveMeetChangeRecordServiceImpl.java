package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzExecutiveMeetChangeRecordMapper;
import com.apih5.mybatis.dao.ZjTzExecutiveMeetMapper;
import com.apih5.mybatis.dao.ZjTzExecutivePersonnelMapper;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.pojo.ZjTzExecutiveMeet;
import com.apih5.mybatis.pojo.ZjTzExecutiveMeetChangeRecord;
import com.apih5.mybatis.pojo.ZjTzExecutivePersonnel;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.service.ZjTzExecutiveMeetChangeRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzExecutiveMeetChangeRecordService")
public class ZjTzExecutiveMeetChangeRecordServiceImpl implements ZjTzExecutiveMeetChangeRecordService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzExecutiveMeetChangeRecordMapper zjTzExecutiveMeetChangeRecordMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private ZjTzExecutivePersonnelMapper zjTzExecutivePersonnelMapper;
    
    @Autowired(required = true)
    private ZjTzExecutiveMeetMapper zjTzExecutiveMeetMapper;
    
    @Override
    public ResponseEntity getZjTzExecutiveMeetChangeRecordListByCondition(ZjTzExecutiveMeetChangeRecord zjTzExecutiveMeetChangeRecord) {
        if (zjTzExecutiveMeetChangeRecord == null) {
            zjTzExecutiveMeetChangeRecord = new ZjTzExecutiveMeetChangeRecord();
        }
        // ????????????
        PageHelper.startPage(zjTzExecutiveMeetChangeRecord.getPage(),zjTzExecutiveMeetChangeRecord.getLimit());
        // ????????????
        List<ZjTzExecutiveMeetChangeRecord> zjTzExecutiveMeetChangeRecordList = zjTzExecutiveMeetChangeRecordMapper.selectByZjTzExecutiveMeetChangeRecordList(zjTzExecutiveMeetChangeRecord);
        for (ZjTzExecutiveMeetChangeRecord zjTzExecutiveMeetChangeRecord2 : zjTzExecutiveMeetChangeRecordList) {
        	//??????
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzExecutiveMeetChangeRecord2.getExecutiveMeetChangeRecordId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzExecutiveMeetChangeRecord2.setZjTzFileList(zjTzFileList);
        	//??????
        	ZjTzExecutivePersonnel personnel = new ZjTzExecutivePersonnel();
        	personnel.setExecutiveMeetId(zjTzExecutiveMeetChangeRecord2.getExecutiveMeetChangeRecordId());
        	List<ZjTzExecutivePersonnel> personnelList = zjTzExecutivePersonnelMapper.selectByZjTzExecutivePersonnelList(personnel);
        	zjTzExecutiveMeetChangeRecord2.setPersonnelList(personnelList);
		}
        // ??????????????????
        PageInfo<ZjTzExecutiveMeetChangeRecord> p = new PageInfo<>(zjTzExecutiveMeetChangeRecordList);

        return repEntity.okList(zjTzExecutiveMeetChangeRecordList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzExecutiveMeetChangeRecordDetails(ZjTzExecutiveMeetChangeRecord zjTzExecutiveMeetChangeRecord) {
        if (zjTzExecutiveMeetChangeRecord == null) {
            zjTzExecutiveMeetChangeRecord = new ZjTzExecutiveMeetChangeRecord();
        }
        // ????????????
        ZjTzExecutiveMeetChangeRecord dbZjTzExecutiveMeetChangeRecord = zjTzExecutiveMeetChangeRecordMapper.selectByPrimaryKey(zjTzExecutiveMeetChangeRecord.getExecutiveMeetChangeRecordId());
        // ????????????
        if (dbZjTzExecutiveMeetChangeRecord != null) {
            return repEntity.ok(dbZjTzExecutiveMeetChangeRecord);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }
    @Override
    public ResponseEntity saveZjTzExecutiveMeetChangeRecord(ZjTzExecutiveMeetChangeRecord zjTzExecutiveMeetChangeRecord) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzExecutiveMeetChangeRecord.setExecutiveMeetChangeRecordId(UuidUtil.generate());
        zjTzExecutiveMeetChangeRecord.setCreateUserInfo(userKey, realName);
        int flag = zjTzExecutiveMeetChangeRecordMapper.insert(zjTzExecutiveMeetChangeRecord);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzExecutiveMeetChangeRecord);
        }
    }

    @Override
    public ResponseEntity updateZjTzExecutiveMeetChangeRecord(ZjTzExecutiveMeetChangeRecord zjTzExecutiveMeetChangeRecord) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzExecutiveMeetChangeRecord dbzjTzExecutiveMeetChangeRecord = zjTzExecutiveMeetChangeRecordMapper.selectByPrimaryKey(zjTzExecutiveMeetChangeRecord.getExecutiveMeetChangeRecordId());
        if (dbzjTzExecutiveMeetChangeRecord != null && StrUtil.isNotEmpty(dbzjTzExecutiveMeetChangeRecord.getExecutiveMeetChangeRecordId())) {
        	// ??????????????????id
           dbzjTzExecutiveMeetChangeRecord.setExecutiveMeetId(zjTzExecutiveMeetChangeRecord.getExecutiveMeetId());
           // ??????id
           dbzjTzExecutiveMeetChangeRecord.setProjectId(zjTzExecutiveMeetChangeRecord.getProjectId());
           // ???????????????id
           dbzjTzExecutiveMeetChangeRecord.setPeriodId(zjTzExecutiveMeetChangeRecord.getPeriodId());
           // ??????name
           dbzjTzExecutiveMeetChangeRecord.setPeriodName(zjTzExecutiveMeetChangeRecord.getPeriodName());
           // ????????????
           dbzjTzExecutiveMeetChangeRecord.setBusinessDate(zjTzExecutiveMeetChangeRecord.getBusinessDate());
           // ????????????????????????
           dbzjTzExecutiveMeetChangeRecord.setStartDate(zjTzExecutiveMeetChangeRecord.getStartDate());
           // ????????????????????????
           dbzjTzExecutiveMeetChangeRecord.setEndDate(zjTzExecutiveMeetChangeRecord.getEndDate());
           // ??????
           dbzjTzExecutiveMeetChangeRecord.setRemarks(zjTzExecutiveMeetChangeRecord.getRemarks());
           // ????????????id
           dbzjTzExecutiveMeetChangeRecord.setStatusId(zjTzExecutiveMeetChangeRecord.getStatusId());
           // ????????????name
           dbzjTzExecutiveMeetChangeRecord.setStatusName(zjTzExecutiveMeetChangeRecord.getStatusName());
           // ????????????
           dbzjTzExecutiveMeetChangeRecord.setConentDesc(zjTzExecutiveMeetChangeRecord.getConentDesc());
           // ????????????
           dbzjTzExecutiveMeetChangeRecord.setChangeNum(zjTzExecutiveMeetChangeRecord.getChangeNum());
           //???????????????
           dbzjTzExecutiveMeetChangeRecord.setLegal(zjTzExecutiveMeetChangeRecord.getLegal());
           // ??????
           dbzjTzExecutiveMeetChangeRecord.setModifyUserInfo(userKey, realName);
           flag = zjTzExecutiveMeetChangeRecordMapper.updateByPrimaryKey(dbzjTzExecutiveMeetChangeRecord);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzExecutiveMeetChangeRecord);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzExecutiveMeetChangeRecord(List<ZjTzExecutiveMeetChangeRecord> zjTzExecutiveMeetChangeRecordList) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zjTzExecutiveMeetChangeRecordList != null && zjTzExecutiveMeetChangeRecordList.size() > 0) {
    		if(zjTzExecutiveMeetChangeRecordList.size() >1) {
    			 return repEntity.layerMessage("no", "?????????????????????????????????????????????");
    		}
    		ZjTzExecutiveMeetChangeRecord zjTzExecutiveMeetChangeRecord = new ZjTzExecutiveMeetChangeRecord();
    		zjTzExecutiveMeetChangeRecord.setExecutiveMeetId(zjTzExecutiveMeetChangeRecordList.get(0).getExecutiveMeetId());
    		List<ZjTzExecutiveMeetChangeRecord> records = zjTzExecutiveMeetChangeRecordMapper.selectByZjTzExecutiveMeetChangeRecordList(zjTzExecutiveMeetChangeRecord);
    		if(records != null && records.size() >0) {
    			if(records.size() >1) {
    				if(StrUtil.equals(records.get(0).getExecutiveMeetChangeRecordId(), zjTzExecutiveMeetChangeRecordList.get(0).getExecutiveMeetChangeRecordId())) {
    					//????????????????????????????????????????????????
    					ZjTzExecutiveMeet updateMeet = zjTzExecutiveMeetMapper.selectByPrimaryKey(zjTzExecutiveMeetChangeRecordList.get(0).getExecutiveMeetId());
    					if(updateMeet != null && StrUtil.isNotEmpty(updateMeet.getExecutiveMeetId())){
    						updateMeet.setChangeNum(zjTzExecutiveMeetChangeRecordList.get(0).getChangeNum()-1);
    						updateMeet.setModifyUserInfo(userKey, realName);
    						flag = zjTzExecutiveMeetMapper.updateByPrimaryKey(updateMeet);
    					}
    					ZjTzFile fileRecord = new ZjTzFile();
    					fileRecord.setOtherId(zjTzExecutiveMeetChangeRecordList.get(0).getExecutiveMeetChangeRecordId());
    					List<ZjTzFile> delFileRecordList = zjTzFileMapper.selectByZjTzFileList(fileRecord);
    					if(delFileRecordList != null && delFileRecordList.size() >0) {
    						fileRecord.setModifyUserInfo(userKey, realName);
    						flag = zjTzFileMapper.batchDeleteUpdateZjTzFile(delFileRecordList, fileRecord);
    					}
    					zjTzExecutiveMeetChangeRecord.setModifyUserInfo(userKey, realName);
    					flag = zjTzExecutiveMeetChangeRecordMapper.batchDeleteUpdateZjTzExecutiveMeetChangeRecord(zjTzExecutiveMeetChangeRecordList, zjTzExecutiveMeetChangeRecord);
    				}else {
    					return repEntity.layerMessage("no", "????????????????????????????????????");
    				}
    			}else {
    				return repEntity.layerMessage("no", "??????????????????????????????????????????????????????????????????");
    			}
    		}
    	}
    	// ??????
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.delete",zjTzExecutiveMeetChangeRecordList);
    	}
    }
}