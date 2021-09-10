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
        // 分页查询
        PageHelper.startPage(zjTzExecutiveMeetChangeRecord.getPage(),zjTzExecutiveMeetChangeRecord.getLimit());
        // 获取数据
        List<ZjTzExecutiveMeetChangeRecord> zjTzExecutiveMeetChangeRecordList = zjTzExecutiveMeetChangeRecordMapper.selectByZjTzExecutiveMeetChangeRecordList(zjTzExecutiveMeetChangeRecord);
        for (ZjTzExecutiveMeetChangeRecord zjTzExecutiveMeetChangeRecord2 : zjTzExecutiveMeetChangeRecordList) {
        	//附件
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzExecutiveMeetChangeRecord2.getExecutiveMeetChangeRecordId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzExecutiveMeetChangeRecord2.setZjTzFileList(zjTzFileList);
        	//人员
        	ZjTzExecutivePersonnel personnel = new ZjTzExecutivePersonnel();
        	personnel.setExecutiveMeetId(zjTzExecutiveMeetChangeRecord2.getExecutiveMeetChangeRecordId());
        	List<ZjTzExecutivePersonnel> personnelList = zjTzExecutivePersonnelMapper.selectByZjTzExecutivePersonnelList(personnel);
        	zjTzExecutiveMeetChangeRecord2.setPersonnelList(personnelList);
		}
        // 得到分页信息
        PageInfo<ZjTzExecutiveMeetChangeRecord> p = new PageInfo<>(zjTzExecutiveMeetChangeRecordList);

        return repEntity.okList(zjTzExecutiveMeetChangeRecordList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzExecutiveMeetChangeRecordDetails(ZjTzExecutiveMeetChangeRecord zjTzExecutiveMeetChangeRecord) {
        if (zjTzExecutiveMeetChangeRecord == null) {
            zjTzExecutiveMeetChangeRecord = new ZjTzExecutiveMeetChangeRecord();
        }
        // 获取数据
        ZjTzExecutiveMeetChangeRecord dbZjTzExecutiveMeetChangeRecord = zjTzExecutiveMeetChangeRecordMapper.selectByPrimaryKey(zjTzExecutiveMeetChangeRecord.getExecutiveMeetChangeRecordId());
        // 数据存在
        if (dbZjTzExecutiveMeetChangeRecord != null) {
            return repEntity.ok(dbZjTzExecutiveMeetChangeRecord);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
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
        	// 董监高会主键id
           dbzjTzExecutiveMeetChangeRecord.setExecutiveMeetId(zjTzExecutiveMeetChangeRecord.getExecutiveMeetId());
           // 项目id
           dbzjTzExecutiveMeetChangeRecord.setProjectId(zjTzExecutiveMeetChangeRecord.getProjectId());
           // 董事会届次id
           dbzjTzExecutiveMeetChangeRecord.setPeriodId(zjTzExecutiveMeetChangeRecord.getPeriodId());
           // 届次name
           dbzjTzExecutiveMeetChangeRecord.setPeriodName(zjTzExecutiveMeetChangeRecord.getPeriodName());
           // 填报日期
           dbzjTzExecutiveMeetChangeRecord.setBusinessDate(zjTzExecutiveMeetChangeRecord.getBusinessDate());
           // 任职期限开始时间
           dbzjTzExecutiveMeetChangeRecord.setStartDate(zjTzExecutiveMeetChangeRecord.getStartDate());
           // 任职期限结束时间
           dbzjTzExecutiveMeetChangeRecord.setEndDate(zjTzExecutiveMeetChangeRecord.getEndDate());
           // 备注
           dbzjTzExecutiveMeetChangeRecord.setRemarks(zjTzExecutiveMeetChangeRecord.getRemarks());
           // 评审状态id
           dbzjTzExecutiveMeetChangeRecord.setStatusId(zjTzExecutiveMeetChangeRecord.getStatusId());
           // 评审状态name
           dbzjTzExecutiveMeetChangeRecord.setStatusName(zjTzExecutiveMeetChangeRecord.getStatusName());
           // 上报内容
           dbzjTzExecutiveMeetChangeRecord.setConentDesc(zjTzExecutiveMeetChangeRecord.getConentDesc());
           // 变更次数
           dbzjTzExecutiveMeetChangeRecord.setChangeNum(zjTzExecutiveMeetChangeRecord.getChangeNum());
           //法定代表人
           dbzjTzExecutiveMeetChangeRecord.setLegal(zjTzExecutiveMeetChangeRecord.getLegal());
           // 共通
           dbzjTzExecutiveMeetChangeRecord.setModifyUserInfo(userKey, realName);
           flag = zjTzExecutiveMeetChangeRecordMapper.updateByPrimaryKey(dbzjTzExecutiveMeetChangeRecord);
        }
        // 失败
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
    			 return repEntity.layerMessage("no", "只能选择最新的一条变更记录删除");
    		}
    		ZjTzExecutiveMeetChangeRecord zjTzExecutiveMeetChangeRecord = new ZjTzExecutiveMeetChangeRecord();
    		zjTzExecutiveMeetChangeRecord.setExecutiveMeetId(zjTzExecutiveMeetChangeRecordList.get(0).getExecutiveMeetId());
    		List<ZjTzExecutiveMeetChangeRecord> records = zjTzExecutiveMeetChangeRecordMapper.selectByZjTzExecutiveMeetChangeRecordList(zjTzExecutiveMeetChangeRecord);
    		if(records != null && records.size() >0) {
    			if(records.size() >1) {
    				if(StrUtil.equals(records.get(0).getExecutiveMeetChangeRecordId(), zjTzExecutiveMeetChangeRecordList.get(0).getExecutiveMeetChangeRecordId())) {
    					//如果让删除。则修改外层的变更次数
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
    					return repEntity.layerMessage("no", "请删除最新一条变更记录！");
    				}
    			}else {
    				return repEntity.layerMessage("no", "只有一条变更记录，不能删除，请到外层去删除！");
    			}
    		}
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.delete",zjTzExecutiveMeetChangeRecordList);
    	}
    }
}