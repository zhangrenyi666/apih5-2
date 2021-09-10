package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.entity.SysSequence;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.SysSequenceMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("sysSequenceService")
public class SysSequenceServiceImpl implements SequenceService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private SysSequenceMapper sysSequenceMapper;

    @Override
    public ResponseEntity getSysSequenceListByCondition(SysSequence sysSequence) {
        if (sysSequence == null) {
            sysSequence = new SysSequence();
        }
        // 分页查询
        PageHelper.startPage(sysSequence.getPage(),sysSequence.getLimit());
        // 获取数据
        List<SysSequence> sysSequenceList = sysSequenceMapper.selectBySysSequenceList(sysSequence);
        // 得到分页信息
        PageInfo<SysSequence> p = new PageInfo<>(sysSequenceList);

        return repEntity.okList(sysSequenceList, p.getTotal());
    }

    @Override
    public ResponseEntity saveSysSequence(SysSequence sysSequence) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        sysSequence.setSeqName(sysSequence.getSeqName());
        sysSequence.setCreateUserInfo(userKey, realName);
        int flag = sysSequenceMapper.insert(sysSequence);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", sysSequence);
        }
    }

    @Override
    public ResponseEntity updateSysSequence(SysSequence sysSequence) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        SysSequence dbsysSequence = sysSequenceMapper.selectByPrimaryKey(sysSequence.getSeqName());
        if (dbsysSequence != null && StrUtil.isNotEmpty(dbsysSequence.getSeqName())) {
           // 共通
           dbsysSequence.setModifyUserInfo(userKey, realName);
           flag = sysSequenceMapper.updateByPrimaryKey(dbsysSequence);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",sysSequence);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateSysSequence(List<SysSequence> sysSequenceList) {
        int flag = 0;
        if (sysSequenceList != null && sysSequenceList.size() > 0) {
           flag = sysSequenceMapper.batchDeleteUpdateSysSequence(sysSequenceList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",sysSequenceList);
        }
   }
	
    @Override
	public SysSequence getSysSequenceByKey(String key) {
		return sysSequenceMapper.selectByPrimaryKey(key);
	}
	
    @Override
	public int getSequenceNextval(String seqName) {
		SysSequence sysSequence = new SysSequence();
		sysSequence.setSeqName(seqName);
		int nextval = sysSequenceMapper.selectNextval(sysSequence);
		return nextval;
	}


}
