package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzProShareholderInfoMapper;
import com.apih5.mybatis.pojo.ZjTzProShareholderInfo;
import com.apih5.service.ZjTzProShareholderInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzProShareholderInfoService")
public class ZjTzProShareholderInfoServiceImpl implements ZjTzProShareholderInfoService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzProShareholderInfoMapper zjTzProShareholderInfoMapper;

    @Override
    public ResponseEntity getZjTzProShareholderInfoListByCondition(ZjTzProShareholderInfo zjTzProShareholderInfo) {
        if (zjTzProShareholderInfo == null) {
            zjTzProShareholderInfo = new ZjTzProShareholderInfo();
        }
        if(StrUtil.isEmpty(zjTzProShareholderInfo.getProjectId())){
        	 return repEntity.okList(null, 0);
        }
        // 分页查询
        PageHelper.startPage(zjTzProShareholderInfo.getPage(),zjTzProShareholderInfo.getLimit());
        // 获取数据
        List<ZjTzProShareholderInfo> zjTzProShareholderInfoList = zjTzProShareholderInfoMapper.selectByZjTzProShareholderInfoList(zjTzProShareholderInfo);
        // 得到分页信息
        PageInfo<ZjTzProShareholderInfo> p = new PageInfo<>(zjTzProShareholderInfoList);

        return repEntity.okList(zjTzProShareholderInfoList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzProShareholderInfoDetails(ZjTzProShareholderInfo zjTzProShareholderInfo) {
        if (zjTzProShareholderInfo == null) {
            zjTzProShareholderInfo = new ZjTzProShareholderInfo();
        }
        System.out.println(zjTzProShareholderInfo.getShareholderInfoId());
        // 获取数据
        ZjTzProShareholderInfo dbZjTzProShareholderInfo = zjTzProShareholderInfoMapper.selectByPrimaryKey(zjTzProShareholderInfo.getShareholderInfoId());
        // 数据存在
        if (dbZjTzProShareholderInfo != null) {
            return repEntity.ok(dbZjTzProShareholderInfo);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzProShareholderInfo(ZjTzProShareholderInfo zjTzProShareholderInfo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzProShareholderInfo.setShareholderInfoId(UuidUtil.generate());
        zjTzProShareholderInfo.setCreateUserInfo(userKey, realName);
        int flag = zjTzProShareholderInfoMapper.insert(zjTzProShareholderInfo);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzProShareholderInfo);
        }
    }

    @Override
    public ResponseEntity updateZjTzProShareholderInfo(ZjTzProShareholderInfo zjTzProShareholderInfo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzProShareholderInfo dbzjTzProShareholderInfo = zjTzProShareholderInfoMapper.selectByPrimaryKey(zjTzProShareholderInfo.getShareholderInfoId());
        if (dbzjTzProShareholderInfo != null && StrUtil.isNotEmpty(dbzjTzProShareholderInfo.getShareholderInfoId())) {
           // 项目id
           dbzjTzProShareholderInfo.setProjectId(zjTzProShareholderInfo.getProjectId());
           // 股东名称
           dbzjTzProShareholderInfo.setShareholderName(zjTzProShareholderInfo.getShareholderName());
           // 股比
           dbzjTzProShareholderInfo.setProportion(zjTzProShareholderInfo.getProportion());
           // 出资额
           dbzjTzProShareholderInfo.setContributionAmount(zjTzProShareholderInfo.getContributionAmount());
           // 共通
           dbzjTzProShareholderInfo.setModifyUserInfo(userKey, realName);
           //股东类型
           dbzjTzProShareholderInfo.setShareholderType(zjTzProShareholderInfo.getShareholderType());
           //施工比例
           dbzjTzProShareholderInfo.setConstructionProportion(zjTzProShareholderInfo.getConstructionProportion());
           flag = zjTzProShareholderInfoMapper.updateByPrimaryKey(dbzjTzProShareholderInfo);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzProShareholderInfo);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzProShareholderInfo(List<ZjTzProShareholderInfo> zjTzProShareholderInfoList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzProShareholderInfoList != null && zjTzProShareholderInfoList.size() > 0) {
           ZjTzProShareholderInfo zjTzProShareholderInfo = new ZjTzProShareholderInfo();
           zjTzProShareholderInfo.setModifyUserInfo(userKey, realName);
           flag = zjTzProShareholderInfoMapper.batchDeleteUpdateZjTzProShareholderInfo(zjTzProShareholderInfoList, zjTzProShareholderInfo);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzProShareholderInfoList);
        }
    }
}
