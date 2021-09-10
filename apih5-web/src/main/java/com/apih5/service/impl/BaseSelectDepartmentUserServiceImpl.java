package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.api.sysdb.entity.BaseSelectDepartmentUser;
import com.apih5.framework.api.sysdb.service.BaseSelectDepartmentUserService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.BaseSelectDepartmentUserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("baseSelectDepartmentUserService")
public class BaseSelectDepartmentUserServiceImpl implements BaseSelectDepartmentUserService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private BaseSelectDepartmentUserMapper baseSelectDepartmentUserMapper;

    @Override
    public ResponseEntity getBaseSelectDepartmentUserListByCondition(BaseSelectDepartmentUser baseSelectDepartmentUser) {
        if (baseSelectDepartmentUser == null) {
            baseSelectDepartmentUser = new BaseSelectDepartmentUser();
        }
        // 分页查询
        PageHelper.startPage(baseSelectDepartmentUser.getPage(),baseSelectDepartmentUser.getLimit());
        // 获取数据
        List<BaseSelectDepartmentUser> baseSelectDepartmentUserList = baseSelectDepartmentUserMapper.selectByBaseSelectDepartmentUserList(baseSelectDepartmentUser);
        // 得到分页信息
        PageInfo<BaseSelectDepartmentUser> p = new PageInfo<>(baseSelectDepartmentUserList);

        return repEntity.okList(baseSelectDepartmentUserList, p.getTotal());
    }

    @Override
    public ResponseEntity saveBaseSelectDepartmentUser(BaseSelectDepartmentUser baseSelectDepartmentUser) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        baseSelectDepartmentUser.setSelectId(UuidUtil.generate());
        baseSelectDepartmentUser.setCreateUserInfo(userKey, realName);
        int flag = baseSelectDepartmentUserMapper.insert(baseSelectDepartmentUser);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", baseSelectDepartmentUser);
        }
    }

    @Override
    public ResponseEntity updateBaseSelectDepartmentUser(BaseSelectDepartmentUser baseSelectDepartmentUser) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        BaseSelectDepartmentUser dbbaseSelectDepartmentUser = baseSelectDepartmentUserMapper.selectByPrimaryKey(baseSelectDepartmentUser.getSelectId());
        if (dbbaseSelectDepartmentUser != null && StrUtil.isNotEmpty(dbbaseSelectDepartmentUser.getSelectId())) {
           // 共通
           dbbaseSelectDepartmentUser.setModifyUserInfo(userKey, realName);
           flag = baseSelectDepartmentUserMapper.updateByPrimaryKey(dbbaseSelectDepartmentUser);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",baseSelectDepartmentUser);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateBaseSelectDepartmentUser(List<BaseSelectDepartmentUser> baseSelectDepartmentUserList) {
        int flag = 0;
        if (baseSelectDepartmentUserList != null && baseSelectDepartmentUserList.size() > 0) {
           flag = baseSelectDepartmentUserMapper.batchDeleteUpdateBaseSelectDepartmentUser(baseSelectDepartmentUserList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",baseSelectDepartmentUserList);
        }
   }
}
