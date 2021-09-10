package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzPermissionUserMapper;
import com.apih5.mybatis.pojo.ZjTzPermissionUser;
import com.apih5.service.ZjTzPermissionUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzPermissionUserService")
public class ZjTzPermissionUserServiceImpl implements ZjTzPermissionUserService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzPermissionUserMapper zjTzPermissionUserMapper;

    @Override
    public ResponseEntity getZjTzPermissionUserListByCondition(ZjTzPermissionUser zjTzPermissionUser) {
        if (zjTzPermissionUser == null) {
            zjTzPermissionUser = new ZjTzPermissionUser();
        }
        // 分页查询
        PageHelper.startPage(zjTzPermissionUser.getPage(),zjTzPermissionUser.getLimit());
        // 获取数据
        List<ZjTzPermissionUser> zjTzPermissionUserList = zjTzPermissionUserMapper.selectByZjTzPermissionUserList(zjTzPermissionUser);
        // 得到分页信息
        PageInfo<ZjTzPermissionUser> p = new PageInfo<>(zjTzPermissionUserList);

        return repEntity.okList(zjTzPermissionUserList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzPermissionUserDetails(ZjTzPermissionUser zjTzPermissionUser) {
        if (zjTzPermissionUser == null) {
            zjTzPermissionUser = new ZjTzPermissionUser();
        }
        // 获取数据
        ZjTzPermissionUser dbZjTzPermissionUser = zjTzPermissionUserMapper.selectByPrimaryKey(zjTzPermissionUser.getPermissionUserId());
        // 数据存在
        if (dbZjTzPermissionUser != null) {
            return repEntity.ok(dbZjTzPermissionUser);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzPermissionUser(ZjTzPermissionUser zjTzPermissionUser) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzPermissionUser.setPermissionUserId(UuidUtil.generate());
        zjTzPermissionUser.setCreateUserInfo(userKey, realName);
        int flag = zjTzPermissionUserMapper.insert(zjTzPermissionUser);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzPermissionUser);
        }
    }

    @Override
    public ResponseEntity updateZjTzPermissionUser(ZjTzPermissionUser zjTzPermissionUser) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzPermissionUser dbzjTzPermissionUser = zjTzPermissionUserMapper.selectByPrimaryKey(zjTzPermissionUser.getPermissionUserId());
        if (dbzjTzPermissionUser != null && StrUtil.isNotEmpty(dbzjTzPermissionUser.getPermissionUserId())) {
           // 权限ID
           dbzjTzPermissionUser.setPermissionId(zjTzPermissionUser.getPermissionId());
           // 节点类型（0：根节点；1：部门；2：人员）
           dbzjTzPermissionUser.setType(zjTzPermissionUser.getType());
           // value（部门或人员主键ID）
           dbzjTzPermissionUser.setValue(zjTzPermissionUser.getValue());
           // 授权对象名称
           dbzjTzPermissionUser.setLabel(zjTzPermissionUser.getLabel());
           // 共通
           dbzjTzPermissionUser.setModifyUserInfo(userKey, realName);
           flag = zjTzPermissionUserMapper.updateByPrimaryKey(dbzjTzPermissionUser);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzPermissionUser);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzPermissionUser(List<ZjTzPermissionUser> zjTzPermissionUserList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzPermissionUserList != null && zjTzPermissionUserList.size() > 0) {
           ZjTzPermissionUser zjTzPermissionUser = new ZjTzPermissionUser();
           zjTzPermissionUser.setModifyUserInfo(userKey, realName);
           flag = zjTzPermissionUserMapper.batchDeleteUpdateZjTzPermissionUser(zjTzPermissionUserList, zjTzPermissionUser);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzPermissionUserList);
        }
    }
}
