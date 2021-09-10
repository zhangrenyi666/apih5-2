package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.BaseFlowPersonMapper;
import com.apih5.mybatis.pojo.BaseFlowPerson;
import com.apih5.service.BaseFlowPersonService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("baseFlowPersonService")
public class BaseFlowPersonServiceImpl implements BaseFlowPersonService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private BaseFlowPersonMapper baseFlowPersonMapper;

    @Override
    public ResponseEntity getBaseFlowPersonListByCondition(BaseFlowPerson baseFlowPerson) {
        if (baseFlowPerson == null) {
            baseFlowPerson = new BaseFlowPerson();
        }
        // 分页查询
        PageHelper.startPage(baseFlowPerson.getPage(),baseFlowPerson.getLimit());
        // 获取数据
        List<BaseFlowPerson> baseFlowPersonList = baseFlowPersonMapper.selectByBaseFlowPersonList(baseFlowPerson);
        // 得到分页信息
        PageInfo<BaseFlowPerson> p = new PageInfo<>(baseFlowPersonList);

        return repEntity.okList(baseFlowPersonList, p.getTotal());
    }

    @Override
    public ResponseEntity saveBaseFlowPerson(BaseFlowPerson baseFlowPerson) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        baseFlowPerson.setFlowPersonId(UuidUtil.generate());
        baseFlowPerson.setCreateUserInfo(userKey, realName);
        int flag = baseFlowPersonMapper.insert(baseFlowPerson);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", baseFlowPerson);
        }
    }

    @Override
    public ResponseEntity updateBaseFlowPerson(BaseFlowPerson baseFlowPerson) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        BaseFlowPerson dbbaseFlowPerson = baseFlowPersonMapper.selectByPrimaryKey(baseFlowPerson.getFlowPersonId());
        if (dbbaseFlowPerson != null && StrUtil.isNotEmpty(dbbaseFlowPerson.getFlowPersonId())) {
           // 共通
           dbbaseFlowPerson.setModifyUserInfo(userKey, realName);
           flag = baseFlowPersonMapper.updateByPrimaryKey(dbbaseFlowPerson);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",baseFlowPerson);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateBaseFlowPerson(List<BaseFlowPerson> baseFlowPersonList) {
        int flag = 0;
        if (baseFlowPersonList != null && baseFlowPersonList.size() > 0) {
           flag = baseFlowPersonMapper.batchDeleteUpdateBaseFlowPerson(baseFlowPersonList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",baseFlowPersonList);
        }
   }
}
