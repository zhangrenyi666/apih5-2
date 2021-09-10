package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzProRebuyInfoMapper;
import com.apih5.mybatis.pojo.ZjTzProRebuyInfo;
import com.apih5.service.ZjTzProRebuyInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzProRebuyInfoService")
public class ZjTzProRebuyInfoServiceImpl implements ZjTzProRebuyInfoService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzProRebuyInfoMapper zjTzProRebuyInfoMapper;

    @Override
    public ResponseEntity getZjTzProRebuyInfoListByCondition(ZjTzProRebuyInfo zjTzProRebuyInfo) {
        if (zjTzProRebuyInfo == null) {
            zjTzProRebuyInfo = new ZjTzProRebuyInfo();
        }
        if(StrUtil.isEmpty(zjTzProRebuyInfo.getProjectId())){
        	return repEntity.okList(null, 0);
        }
        // 分页查询
        PageHelper.startPage(zjTzProRebuyInfo.getPage(),zjTzProRebuyInfo.getLimit());
        // 获取数据
        List<ZjTzProRebuyInfo> zjTzProRebuyInfoList = zjTzProRebuyInfoMapper.selectByZjTzProRebuyInfoList(zjTzProRebuyInfo);
        // 得到分页信息
        PageInfo<ZjTzProRebuyInfo> p = new PageInfo<>(zjTzProRebuyInfoList);

        return repEntity.okList(zjTzProRebuyInfoList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzProRebuyInfoDetails(ZjTzProRebuyInfo zjTzProRebuyInfo) {
        if (zjTzProRebuyInfo == null) {
            zjTzProRebuyInfo = new ZjTzProRebuyInfo();
        }
        // 获取数据
        ZjTzProRebuyInfo dbZjTzProRebuyInfo = zjTzProRebuyInfoMapper.selectByPrimaryKey(zjTzProRebuyInfo.getRebuyInfoId());
        // 数据存在
        if (dbZjTzProRebuyInfo != null) {
            return repEntity.ok(dbZjTzProRebuyInfo);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzProRebuyInfo(ZjTzProRebuyInfo zjTzProRebuyInfo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzProRebuyInfo.setRebuyInfoId(UuidUtil.generate());
        zjTzProRebuyInfo.setCreateUserInfo(userKey, realName);
        int flag = zjTzProRebuyInfoMapper.insert(zjTzProRebuyInfo);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzProRebuyInfo);
        }
    }

    @Override
    public ResponseEntity updateZjTzProRebuyInfo(ZjTzProRebuyInfo zjTzProRebuyInfo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzProRebuyInfo dbzjTzProRebuyInfo = zjTzProRebuyInfoMapper.selectByPrimaryKey(zjTzProRebuyInfo.getRebuyInfoId());
        if (dbzjTzProRebuyInfo != null && StrUtil.isNotEmpty(dbzjTzProRebuyInfo.getRebuyInfoId())) {
           // 项目id
           dbzjTzProRebuyInfo.setProjectId(zjTzProRebuyInfo.getProjectId());
           // 协议约定次数
           dbzjTzProRebuyInfo.setAppointNumber(zjTzProRebuyInfo.getAppointNumber());
           // 协议约定金额
           dbzjTzProRebuyInfo.setAppointAmount(zjTzProRebuyInfo.getAppointAmount());
           // 协议约定时间
           dbzjTzProRebuyInfo.setAppointDate(zjTzProRebuyInfo.getAppointDate());
           // 共通
           dbzjTzProRebuyInfo.setModifyUserInfo(userKey, realName);
           flag = zjTzProRebuyInfoMapper.updateByPrimaryKey(dbzjTzProRebuyInfo);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzProRebuyInfo);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzProRebuyInfo(List<ZjTzProRebuyInfo> zjTzProRebuyInfoList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzProRebuyInfoList != null && zjTzProRebuyInfoList.size() > 0) {
           ZjTzProRebuyInfo zjTzProRebuyInfo = new ZjTzProRebuyInfo();
           zjTzProRebuyInfo.setModifyUserInfo(userKey, realName);
           flag = zjTzProRebuyInfoMapper.batchDeleteUpdateZjTzProRebuyInfo(zjTzProRebuyInfoList, zjTzProRebuyInfo);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzProRebuyInfoList);
        }
    }
}
