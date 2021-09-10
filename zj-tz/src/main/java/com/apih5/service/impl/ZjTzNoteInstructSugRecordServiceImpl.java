package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.service.SysDepartmentService;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzNoteInstructSugRecordMapper;
import com.apih5.mybatis.pojo.ZjTzNoteInstructSugRecord;
import com.apih5.service.ZjTzNoteInstructSugRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;

@Service("zjTzNoteInstructSugRecordService")
public class ZjTzNoteInstructSugRecordServiceImpl implements ZjTzNoteInstructSugRecordService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzNoteInstructSugRecordMapper zjTzNoteInstructSugRecordMapper;
    
    @Autowired(required = true)
    private SysDepartmentService sysDepartmentService;
    
    @Override
    public ResponseEntity getZjTzNoteInstructSugRecordListByCondition(ZjTzNoteInstructSugRecord zjTzNoteInstructSugRecord) {
        if (zjTzNoteInstructSugRecord == null) {
            zjTzNoteInstructSugRecord = new ZjTzNoteInstructSugRecord();
        }
        if(StrUtil.isEmpty(zjTzNoteInstructSugRecord.getNoteInstructSugId())) {
        	return repEntity.okList(null, 0);
        }
        // 分页查询
        PageHelper.startPage(zjTzNoteInstructSugRecord.getPage(),zjTzNoteInstructSugRecord.getLimit());
        // 获取数据
        List<ZjTzNoteInstructSugRecord> zjTzNoteInstructSugRecordList = zjTzNoteInstructSugRecordMapper.selectByZjTzNoteInstructSugRecordList(zjTzNoteInstructSugRecord);
        // 得到分页信息
        PageInfo<ZjTzNoteInstructSugRecord> p = new PageInfo<>(zjTzNoteInstructSugRecordList);

        return repEntity.okList(zjTzNoteInstructSugRecordList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzNoteInstructSugRecordDetails(ZjTzNoteInstructSugRecord zjTzNoteInstructSugRecord) {
        if (zjTzNoteInstructSugRecord == null) {
            zjTzNoteInstructSugRecord = new ZjTzNoteInstructSugRecord();
        }
        // 获取数据
        ZjTzNoteInstructSugRecord dbZjTzNoteInstructSugRecord = zjTzNoteInstructSugRecordMapper.selectByPrimaryKey(zjTzNoteInstructSugRecord.getNoteInstructSugRecordId());
        // 数据存在
        if (dbZjTzNoteInstructSugRecord != null) {
            return repEntity.ok(dbZjTzNoteInstructSugRecord);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzNoteInstructSugRecord(ZjTzNoteInstructSugRecord zjTzNoteInstructSugRecord) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        List<JSONObject> approve1List = zjTzNoteInstructSugRecord.getPersonList();
		if(approve1List != null && approve1List.size()>0) {
			for (int i = 0; i < approve1List.size(); i++) {
				Object object = approve1List.get(i);
				JSONObject jsonObject = new JSONObject(object);
				zjTzNoteInstructSugRecord.setNoteInstructSugRecordId(UuidUtil.generate());
				zjTzNoteInstructSugRecord.setNoteInstructSugId(zjTzNoteInstructSugRecord.getNoteInstructSugId());
				//查部门
				SysDepartment sysDepartment = new SysDepartment();
				sysDepartment.setDepartmentPath(jsonObject.getStr("valuePid"));//页面当前获取的pid
				sysDepartment.setDepartmentFlag("4");
				List<SysDepartment> departments = sysDepartmentService.selectBySysDepartmentList(sysDepartment);
				if(departments != null && departments.size() >0) {
					zjTzNoteInstructSugRecord.setPersonDeptKey(departments.get(0).getDepartmentId());
					zjTzNoteInstructSugRecord.setPersonDeptName(departments.get(0).getDepartmentName());
				}
				zjTzNoteInstructSugRecord.setPersonKey(jsonObject.getStr("value"));
				zjTzNoteInstructSugRecord.setPersonName(jsonObject.getStr("label"));
				zjTzNoteInstructSugRecord.setSendTime(new Date());
				zjTzNoteInstructSugRecord.setCreateUserInfo(userKey, realName);
				flag = zjTzNoteInstructSugRecordMapper.insert(zjTzNoteInstructSugRecord);
			}
		}
		
		if (flag == 0) {
			return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzNoteInstructSugRecord);
        }
    }

    @Override
    public ResponseEntity updateZjTzNoteInstructSugRecord(ZjTzNoteInstructSugRecord zjTzNoteInstructSugRecord) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzNoteInstructSugRecord dbzjTzNoteInstructSugRecord = zjTzNoteInstructSugRecordMapper.selectByPrimaryKey(zjTzNoteInstructSugRecord.getNoteInstructSugRecordId());
        if (dbzjTzNoteInstructSugRecord != null && StrUtil.isNotEmpty(dbzjTzNoteInstructSugRecord.getNoteInstructSugRecordId())) {
           // 公告、指令、建议id
           dbzjTzNoteInstructSugRecord.setNoteInstructSugId(zjTzNoteInstructSugRecord.getNoteInstructSugId());
           // 内容描述
           dbzjTzNoteInstructSugRecord.setContentDesc(zjTzNoteInstructSugRecord.getContentDesc());
           // 推送时间
           dbzjTzNoteInstructSugRecord.setSendTime(zjTzNoteInstructSugRecord.getSendTime());
           // 共通
           dbzjTzNoteInstructSugRecord.setModifyUserInfo(userKey, realName);
           flag = zjTzNoteInstructSugRecordMapper.updateByPrimaryKey(dbzjTzNoteInstructSugRecord);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzNoteInstructSugRecord);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzNoteInstructSugRecord(List<ZjTzNoteInstructSugRecord> zjTzNoteInstructSugRecordList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzNoteInstructSugRecordList != null && zjTzNoteInstructSugRecordList.size() > 0) {
           ZjTzNoteInstructSugRecord zjTzNoteInstructSugRecord = new ZjTzNoteInstructSugRecord();
           zjTzNoteInstructSugRecord.setModifyUserInfo(userKey, realName);
           flag = zjTzNoteInstructSugRecordMapper.batchDeleteUpdateZjTzNoteInstructSugRecord(zjTzNoteInstructSugRecordList, zjTzNoteInstructSugRecord);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzNoteInstructSugRecordList);
        }
    }
}
