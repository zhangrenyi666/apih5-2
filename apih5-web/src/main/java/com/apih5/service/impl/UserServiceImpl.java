package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.apih5.entity.CompanyEntity;
import com.apih5.entity.DepartmentEntity;
import com.apih5.entity.SysUserWebEntity;
import com.apih5.framework.api.sysdb.entity.SysCompany;
import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.api.sysdb.entity.SysUserCompany;
import com.apih5.framework.api.sysdb.entity.SysUserDepartment;
import com.apih5.framework.api.sysdb.service.BaseCodeService;
import com.apih5.framework.api.sysdb.service.SysDepartmentService;
import com.apih5.framework.api.sysdb.service.SysUserCompanyService;
import com.apih5.framework.api.sysdb.service.UserService;
import com.apih5.framework.api.wechatenterprise.entity.json.request.addressbook.UserInfoReq;
import com.apih5.framework.api.wechatenterprise.service.WeChatEnterpriseDbService;
import com.apih5.framework.api.wechatenterprise.service.WeChatEnterpriseService;
import com.apih5.framework.api.zjoa.entity.OADepartment;
import com.apih5.framework.api.zjoa.entity.OAMember;
import com.apih5.framework.cache.RedisCacheService;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.ConfigConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.entity.SmsEntity;
import com.apih5.framework.entity.TokenEntity;
import com.apih5.framework.exception.Apih5Exception;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.framework.utils.SmsUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.BaseAccountMapper;
import com.apih5.mybatis.dao.LogMapper;
import com.apih5.mybatis.dao.SysCompanyMapper;
import com.apih5.mybatis.dao.SysDepartmentMapper;
import com.apih5.mybatis.dao.SysProjectMapper;
import com.apih5.mybatis.dao.SysRoleUserMapper;
import com.apih5.mybatis.dao.SysUserCompanyMapper;
import com.apih5.mybatis.dao.SysUserDepartmentMapper;
import com.apih5.mybatis.dao.SysUserMapper;
import com.apih5.mybatis.pojo.BaseAccount;
import com.apih5.mybatis.pojo.SysLog;
import com.apih5.mybatis.pojo.SysProject;
import com.apih5.mybatis.pojo.SysRoleUser;
import com.apih5.service.BaseAccountService;
import com.apih5.service.SysMenuService;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @ApolloConfig
    private Config config;

    @ApolloConfig(ConfigConst.PUBLIC_OTHER_API)
    private Config publicConfig;

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RedisCacheService sysRedisCacheService;

//    @Autowired
//    private BaseAccountService accountService;

    @Autowired(required = true)
    private WeChatEnterpriseDbService weChatEnterpriseDbService;

    @Autowired(required = true)
    private WeChatEnterpriseService wechatEnterpriseService;

    @Autowired
    private SysUserMapper userMapper;

//    @Autowired
//    private MenuMapper menuMapper;

    @Autowired
    private LogMapper logMapper;

    @Autowired
    private BaseAccountMapper baseAccountMapper;
    @Autowired
    private BaseAccountService baseAccountService;

    @Autowired
    private SysCompanyMapper sysCompanyMapper;

//    @Autowired
//    private UserTokenMapper userTokenMapper;

    @Autowired
    private Apih5Properties apih5Properties;

    @Autowired
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired
    private SysDepartmentService sysDepartmentService;

    @Autowired
    private SysUserCompanyService sysUserCompanyService;

    @Autowired
    private SysDepartmentMapper sysDepartmentMapper;

    @Autowired
    private SysUserDepartmentMapper sysUserDepartmentMapper;
    
    @Autowired
    private SysProjectMapper sysProjectMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysUserCompanyMapper sysUserCompanyMapper;
    @Autowired
    private SysRoleUserMapper sysRoleUserMapper;
    @Autowired(required = true)
    private BaseCodeService baseCodeService;

    @Override
    public ResponseEntity changeCompany(SysUser sysUser) {
        if (sysUser == null) {
            sysUser = new SysUser();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String weChatUserId = TokenUtils.getWeChatUserId(request);
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String mobile = TokenUtils.getMobile(request);
        String accountId = TokenUtils.getAccountId(request);
        // String companyId = TokenUtils.getCompanyId(request);
        String changeUserKey = "";
        String changeUserId = "";
        String changeUserPwd = "";
        String changeCompanyId = sysUser.getCompanyId();
        if (StrUtil.isEmpty(changeCompanyId)) {
            return repEntity.layerMessage("no", "切换公司id不能为空");
        }

        // 1、删除当前改用户所有公司
        SysUserCompany sysUserCompany = new SysUserCompany();
        sysUserCompany.setUserKey(userKey);
        List<SysUserCompany> sysUserCompanyList = sysUserCompanyMapper.selectBySysUserCompanyList(sysUserCompany);
        if (sysUserCompanyList != null && sysUserCompanyList.size() > 0) {
            sysUserCompanyMapper.batchDeleteUpdateSysUserCompany(sysUserCompanyList);
        }

        // 2、获取当前切换公司的userInfo
        SysUser sysUserSelect = new SysUser();
        sysUserSelect.setCompanyId(changeCompanyId);
        sysUserSelect.setMobile(mobile);
        List<SysUser> sysUserList = userMapper.getSysUserListByCompanyMobile(sysUserSelect);
        if (sysUserList != null && sysUserList.size() > 0) {
            changeUserKey = sysUserList.get(0).getUserKey();
            changeUserId = sysUserList.get(0).getUserId();
            changeUserPwd = sysUserList.get(0).getUserPwd();
        }

        // 3、新增切换公司
        sysUserCompany = new SysUserCompany();
        sysUserCompany.setUserCompanyId(UuidUtil.generate());
        sysUserCompany.setUserKey(changeUserKey);
        sysUserCompany.setCompanyId(changeCompanyId);
        sysUserCompany.setCreateUserInfo(userKey, realName);
        int flag = sysUserCompanyMapper.insert(sysUserCompany);

        // 3、当前用户重新登录，并且返回前端token
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("accountId", accountId);
        jsonObject.accumulate("loginType", 1);
        jsonObject.accumulate("userId", changeUserId);
        jsonObject.accumulate("userPwd", apih5Properties.getDefaultPassword());
        // login登录
        String result = HttpUtil.sendPostJson(Apih5Properties.getWebUrl() + "/user/login", jsonObject.toString());
        String changeToken = "";
        String changeSysUserWebEntity = "";
        if (StrUtil.isNotEmpty(result)) {
            JSONObject resultJson = new JSONObject(result);
            boolean success = resultJson.getBool("success");
            if (success) {
                JSONObject jsonObjectData = resultJson.getJSONObject("data");
                changeToken = jsonObjectData.getStr("token");
                changeSysUserWebEntity = jsonObjectData.getStr("userInfo");
            }
        }

        Map<String, Object> infoMap = Maps.newHashMap();
        // 传给web
        infoMap.put("token", changeToken);
        infoMap.put("userInfo", new JSONObject(changeSysUserWebEntity));
        infoMap.put("defaultPasswordReset", false);

        // 4、最后删除切换前的token
//        EhCacheCacheHandler.removeUserTokenEhCache(userKey + "-" + accountId);
        sysRedisCacheService.removeUserTokenRedis(userKey + "-" + accountId);

        return repEntity.ok(infoMap);
    }

    @Override
    public ResponseEntity updateUserPwd(SysUser sysUser) {
        if (sysUser == null) {
            sysUser = new SysUser();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String userPwdOld = sysUser.getUserPwdOld();
        String userPwd = sysUser.getUserPwd();
        String userPwdNew = sysUser.getUserPwdNew();
        if (StrUtil.isEmpty(userPwdOld)) {
            return repEntity.layerMessage("no", "原密码不能为空。");
        }
        // 大小写字母、数字混合、首字母
        String checkPassPattern = "^(?=.*[0-9])(?=.*[a-zA-Z])([A-Z])(.{7,20})$";
        if (!userPwdNew.matches(checkPassPattern)) {
            return repEntity.layerMessage("no", "密码必须是8位以上的混合[大小写字母、数字、首字母]，请您重新设置！");
        }
        if (StrUtil.isEmpty(userPwd)) {
            return repEntity.layerMessage("no", "新密码不能为空。");
        }
        if (StrUtil.isEmpty(userPwdNew)) {
            return repEntity.layerMessage("no", "再次确认密码不能为空。");
        }
        if (!StrUtil.equals(userPwd, userPwdNew)) {
            return repEntity.layerMessage("no", "需要修改的密码两次输入不一致。");
        }

        // 获取用户信息
        SysUser sysUserDb = userMapper.selectByPrimaryKey(userKey);
        if (sysUserDb == null) {
            return repEntity.layerMessage("no", "密码修改失败。");
        } else {
            String userPwdOldMd5 = SecureUtil.md5(userPwdOld + apih5Properties.getMd5Salt());
            if (!StrUtil.equals(userPwdOldMd5, sysUserDb.getUserPwd())) {
                return repEntity.layerMessage("no", "原密码不正确。");
            }
        }
        // 修改密码
        SysUser sysUserUpdate = new SysUser();
        sysUserUpdate.setUserKey(userKey);
        sysUserUpdate.setUserPwd(SecureUtil.md5(userPwd + apih5Properties.getMd5Salt()));
        int flag = this.updateUserCommon(sysUserUpdate);
        if (flag == 0) {
            return repEntity.layerMessage("no", "密码修改失败。");
        } else {
            return repEntity.ok("密码修改成功。");
        }
    }

    @Override
    public ResponseEntity updateUserDefaultPwd(SysUser sysUser) {
        if (sysUser == null) {
            sysUser = new SysUser();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String userPwd = sysUser.getUserPwd();
        String userPwdNew = sysUser.getUserPwdNew();
        // 大小写字母、数字混合、首字母
        String checkPassPattern = "^(?=.*[0-9])(?=.*[a-zA-Z])([A-Z])(.{7,20})$";
        if (!userPwdNew.matches(checkPassPattern)) {
            return repEntity.layerMessage("no", "密码必须是8位以上的混合[大小写字母、数字、首字母]，请您重新设置！");
        }
        if (StrUtil.isEmpty(userPwd)) {
            return repEntity.layerMessage("no", "新密码不能为空。");
        }
        if (StrUtil.isEmpty(userPwdNew)) {
            return repEntity.layerMessage("no", "再次确认密码不能为空。");
        }
        if (!StrUtil.equals(userPwd, userPwdNew)) {
            return repEntity.layerMessage("no", "需要修改的密码两次输入不一致。");
        }

        // 获取用户信息
        SysUser sysUserDb = userMapper.selectByPrimaryKey(userKey);
        if (sysUserDb == null) {
            return repEntity.layerMessage("no", "密码修改失败。");
        }
        // 修改密码
        SysUser sysUserUpdate = new SysUser();
        sysUserUpdate.setUserKey(userKey);
        sysUserUpdate.setUserPwd(SecureUtil.md5(userPwd + apih5Properties.getMd5Salt()));
        int flag = this.updateUserCommon(sysUserUpdate);
        if (flag == 0) {
            return repEntity.layerMessage("no", "密码修改失败。");
        } else {
            return repEntity.ok("密码修改成功。");
        }
    }

    @Override
    public ResponseEntity resetUserPwd(List<SysUser> sysUserList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        boolean isAdmin = false;
        int flag = 0;
        if (sysUserList != null && sysUserList.size() > 0) {
            for (SysUser sysUser : sysUserList) {
                SysUser dbSysUser = sysUserMapper.selectByPrimaryKey(sysUser.getUserKey());
                String userPwd = SecureUtil.md5(apih5Properties.getDefaultPassword() + apih5Properties.getMd5Salt());
                dbSysUser.setUserPwd(userPwd);
                dbSysUser.setModifyUserInfo(userKey, realName);
                if (!StrUtil.equals("admin", sysUser.getUserId())) {
                    flag = sysUserMapper.updateByPrimaryKey(dbSysUser);
                } else {
                    isAdmin = true;
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            if (isAdmin) {
                return repEntity.ok("密码重置成功！【注，管理员密码不能重置】");
            } else {
                return repEntity.ok("密码重置成功！]");
            }
        }
    }

    /**
     * 缓存更新
     * 
     */
    @Override
    public ResponseEntity refreshAccessToken(TokenEntity tokenEntity) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        if (tokenEntity == null || StrUtil.isEmpty(tokenEntity.getToken())
                || StrUtil.equals("null", tokenEntity.getToken())) {
            return repEntity.error("sys.error.token");
        }
        // token解密
        String token = tokenEntity.getToken();
        String tokenDecrypt = "";
        try {
            tokenDecrypt = SecureUtil.des("apih5_key".getBytes()).decryptStr(token);
        } catch (Exception e) {
            return repEntity.error("sys.error.token");
        }
        JSONObject jsonObjectToken = new JSONObject(tokenDecrypt);
        String accountId = jsonObjectToken.getStr("accountId");
        String userKey = jsonObjectToken.getStr("userKey");
        String timeStamp = jsonObjectToken.getStr("timeStamp");
        String userEhCacheKey = userKey + "-" + accountId;
        String newIp = request.getRemoteAddr();

        // token不合法标识
        boolean tokenInvalidFlag = false;
        String checkToken = TokenUtils.createUserToken(accountId, userKey, Long.parseLong(timeStamp),
                apih5Properties.getUserTokenKey(), newIp);
        if (!StrUtil.equals(checkToken, token)) {
            tokenInvalidFlag = true;
        } else {
            // 得到刷新前的tokenEntity的值
//            tokenEntity = EhCacheCacheHandler.getUserTokenEhCache(userEhCacheKey);
            tokenEntity = sysRedisCacheService.getUserTokenRedis(userEhCacheKey);
            // 如果token的值为空
            if (ObjectUtil.isNull(tokenEntity)) {
                // LoggerUtils.printLogger(logger, "缓存token为空");
                return repEntity.error("sys.error.token.cache");
            }
        }
        // 如果token不合法
        if (tokenInvalidFlag) {
            LoggerUtils.printLogger(logger, "token不合法");
            return repEntity.error("sys.error.token");
        }

        // 更新token时间
        String newToken = TokenUtils.createUserToken(accountId, userKey, 0, apih5Properties.getUserTokenKey(), newIp);
        tokenEntity.setToken(newToken);
        // 更新缓存
//        EhCacheCacheHandler.putUserTokenEhCache(userEhCacheKey, tokenEntity);
        sysRedisCacheService.putUserTokenRedis(userEhCacheKey, tokenEntity);
        return repEntity.ok(newToken);
    }

    @Override
    public ResponseEntity getSysUserListByBg(SysUser sysUser) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String companyId = TokenUtils.getCompanyId(request);
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        if (sysUser == null) {
            sysUser = new SysUser();
        }
           // 通过项目获取user
        SysUser sysUserProSelect = new SysUser();
        sysUserProSelect.setDepartmentId(sysUser.getDepartmentId());
        // 分页查询
        PageHelper.startPage(sysUser.getPage(), sysUser.getLimit());
        // 根据设置【只显示对应部门下面的人员】
        boolean isSearchDepartmentId = publicConfig.getBooleanProperty("isSearchDepartmentId", false);
        if (isSearchDepartmentId) {
            // 暂时保留
            if (StrUtil.isNotEmpty(sysUser.getSearch())) {
                sysUser.setCompanyId(sysUser.getDepartmentId());
                sysUser.setDepartmentId("");
            }

            // 检索条件不为空时，设置有效值的部门id去检索
            if(StrUtil.isNotEmpty(sysUser.getRealName())
                    || StrUtil.isNotEmpty(sysUser.getUserId())
                    || StrUtil.isNotEmpty(sysUser.getMobile())) {
                if(StrUtil.equals("admin", userId) || StrUtil.equals("1", ext1)) {
//                    sysUser.setCompanyId(sysUser.getDepartmentId());
                    sysUser.setDepartmentId("");
                } else {
                    sysUser.setDepartmentId("");
                    // DepartmentPath的查询条件
                    sysUser.setCompanyId(sysUser.getRootId());
                }
            }
        } else {
            // 检索所有部门下数据，暂时用这个方法，后面看看有没有其他方法要加进来可以替换掉
            if(!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)
                    && (StrUtil.equals("9999999999", sysUser.getDepartmentId()) || StrUtil.equals("0", sysUser.getDepartmentId())) && StrUtil.isNotEmpty(companyId)) {
                sysUser.setCompanyId(companyId);
            } else {
                sysUser.setCompanyId(sysUser.getDepartmentId());
            }
            sysUser.setDepartmentId("");
        }

        List<SysUser> sysUserList = userMapper.getUserListByRoleAndCompanyId(sysUser);
        // 当前如果页面没有传id过来，则不检索项目，否则检索
        if(StrUtil.isNotEmpty(sysUserProSelect.getDepartmentId())) {
            SysProject sysUserPro = sysProjectMapper.selectByPrimaryKey(sysUserProSelect.getDepartmentId());
            if(sysUserPro != null) {
                List<SysUser> sysUserProList = userMapper.selectBySysUserListByProject(sysUserProSelect);
                if(sysUserList != null) {
                    sysUserList.addAll(sysUserProList);
                } else {
                    sysUserList = sysUserProList;
                }
            }
        }
        
        // 根据人员吧该人员所有部门筛选出来
        for (SysUser dbSysUser : sysUserList) {
            // 部门表
            SysDepartment sysDepartment = new SysDepartment();
            sysDepartment.setUserKey(dbSysUser.getUserKey());
            List<SysDepartment> sysDepartmentList = sysDepartmentMapper.getSysDepartmentListByUserkey(sysDepartment);
            // 转labele和value模式
            JSONArray sysDepartmentJSONArray = new JSONArray();
            List<String> sysDepartmentShowList = Lists.newArrayList();
            for (SysDepartment sysDepartmentDb : sysDepartmentList) {
                if (sysDepartmentDb.getDepartmentPathName().indexOf(",") > 0) {
                    sysDepartmentShowList.add(sysDepartmentDb.getDepartmentPathName().replaceAll(",", "/"));
                } else {
                    sysDepartmentShowList.add(sysDepartmentDb.getDepartmentPathName());
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("value", sysDepartmentDb.getDepartmentId());
                jsonObject.set("label", sysDepartmentDb.getDepartmentName());
                jsonObject.set("valuePid", sysDepartmentDb.getDepartmentParentId());
                jsonObject.set("type", "1");
                sysDepartmentJSONArray.add(jsonObject);
            }
            
            // 项目表
            SysProject sysProject = new SysProject();
            sysProject.setUserKey(dbSysUser.getUserKey());
            List<SysProject> sysProjectList = sysProjectMapper.selectBySysProjectListByUserKey(sysProject);
            for (SysProject dbSysProject : sysProjectList) {
                if (dbSysProject.getDepartmentPathName().indexOf(",") > 0) {
                    sysDepartmentShowList.add(dbSysProject.getDepartmentPathName().replaceAll(",", "/"));
                } else {
                    sysDepartmentShowList.add(dbSysProject.getDepartmentPathName());
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("value", dbSysProject.getDepartmentId());
                jsonObject.set("label", dbSysProject.getDepartmentName());
                jsonObject.set("valuePid", dbSysProject.getDepartmentParentId());
                jsonObject.set("type", "1");
                sysDepartmentJSONArray.add(jsonObject);
            }
            
            dbSysUser.setSysDepartmentList(sysDepartmentJSONArray);
            dbSysUser.setSysDepartmentShowList(sysDepartmentShowList);
            dbSysUser.setUserPwd("");
        }
        // 得到分页信息
        PageInfo<SysUser> p = new PageInfo<>(sysUserList);
        return repEntity.okList(sysUserList, p.getTotal());
    }

    @Override
    public ResponseEntity getSysUserDetails(SysUser sysUser) {
        if (sysUser == null || StrUtil.isEmpty(sysUser.getUserKey())) {
            return repEntity.ok("");
        }
        SysUser dbSysUser = userMapper.selectByPrimaryKey(sysUser.getUserKey());
        JSONObject jsonObject = new JSONObject();
        // 真实姓名
        jsonObject.set("realName", dbSysUser.getRealName());
        // 昵称
        jsonObject.set("nickname", dbSysUser.getNickname());
        // 电话
        jsonObject.set("mobile", dbSysUser.getMobile());
        // 邮箱
        jsonObject.set("email", dbSysUser.getEmail());
        // 出生日期
        jsonObject.set("birthday", dbSysUser.getBirthday());
        // 年龄
        jsonObject.set("age", dbSysUser.getAge());
        // 国籍
        jsonObject.set("nationnality", dbSysUser.getNationnality());
        jsonObject.set("nationnalityName", dbSysUser.getNationnalityName());
        // 民族
        jsonObject.set("nation", dbSysUser.getNation());
        jsonObject.set("nationName", dbSysUser.getNationName());
        // 职务
        jsonObject.set("postions", dbSysUser.getPostions());
        jsonObject.set("postionsName", dbSysUser.getPostionsName());
        // 最高职级
        jsonObject.set("positiongrade", dbSysUser.getPositiongrade());
        jsonObject.set("positiongradeName", dbSysUser.getPositiongradeName());
        // 岗位
        jsonObject.set("jobType", dbSysUser.getJobType());
        jsonObject.set("jobTypeName", dbSysUser.getJobTypeName());
        // 用工类型
        jsonObject.set("empType", dbSysUser.getEmpType());
        jsonObject.set("empTypeName", dbSysUser.getEmpTypeName());
        // 性别
        jsonObject.set("gender", dbSysUser.getGender());
        // 头像
        jsonObject.set("imageUrl", dbSysUser.getImageUrl());
        return repEntity.ok(jsonObject);
    }

    /**
     * 后台-新增用户、用户部门关系数据
     * 
     * @param sysUser
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity addSysUserInfoByBg(SysUser sysUser) throws Exception {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        // 部门关系表中写数据
        List<Map> sysDepartmentList = sysUser.getSysDepartmentList();
        if (sysDepartmentList == null || sysDepartmentList.size() == 0) {
            return repEntity.error("sys.exception");
        }
        // 手机号合法检查
        if(StrUtil.isNotEmpty(sysUser.getMobile())) {
            if (!Validator.isNumber(sysUser.getMobile())) {
                return repEntity.layerMessage("no", "手机号不合法，请您检查。");
            }
        }

        String userKey = TokenUtils.getUserKey(request);
        String userName = TokenUtils.getRealName(request);
        String accountId = TokenUtils.getAccountId(request);
        sysUser.setAccountId(accountId);
        // account中查找企业信息
//        Map<String, String> accessTokenMap = weChatEnterpriseDbService.getWeChatAccessToken(accountId);
//        BaseAccount baseAccount = baseAccountService.selectByPrimaryKey(accountId);
        BaseAccount baseAccount = baseAccountService.getBaseAccount(accountId);
        String accountCorpId = baseAccount.getAccountCorpId();
        String accountAppType = baseAccount.getAccountAppType();
        if (StrUtil.isNotEmpty(sysUser.getAccountAppType())) {
            accountAppType = sysUser.getAccountAppType();
        }
        sysUser.setAccountCorpId(accountCorpId);
        sysUser.setAccountAppType(accountAppType);

        // 用户存在check
        SysUser sysUserSelect = new SysUser();
        sysUserSelect.setUserId(sysUser.getUserId());
        sysUserSelect.setAccountId(accountId);
        sysUserSelect.setAccountAppType(accountAppType);
        sysUserSelect.setAccountCorpId(accountCorpId);
        List<SysUser> sysUserList = sysUserMapper.selectBySysUserList(sysUserSelect);
        // 如果注册账号已存在
        if (sysUserList != null && sysUserList.size() > 0) {
            if(StrUtil.equals("zj_xiamengs_renzi", accountId)) {
                for (Map map : sysDepartmentList) {
                    SysUserDepartment sysUserDepartment = new SysUserDepartment();
                    sysUserDepartment.setUserDepartmentId(UuidUtil.generate());
                    sysUserDepartment.setUserKey(sysUserList.get(0).getUserKey());
                    sysUserDepartment.setDepartmentId((String)map.get("value"));
                    sysUserDepartment.setCreateUserInfo(userKey, userName);
                    int flag = sysUserDepartmentMapper.insert(sysUserDepartment);
                    if (flag == 0) {
                        LoggerUtils.printExceptionLogger(logger, "部门关系表更新失败=");
                        throw new Apih5Exception("更新失败");
                    } else {
                        return repEntity.ok("sys.data.sava", sysUser);
                    }
                }
            }
            
            SysDepartment sysDepartmentSelect = new SysDepartment();
            sysDepartmentSelect.setUserKey(sysUserList.get(0).getUserKey());
            List<SysDepartment> errorSysDepartmentList = sysDepartmentService.getSysDepartmentListByUserkey(sysDepartmentSelect);
            String deptName = errorSysDepartmentList.get(0).getDepartmentPathName();
            return repEntity.layerMessage("no", "该账号已在【" + sysUserList.get(0).getRealName() + "(" + deptName + ")】中使用");
        }

        // 电话号check
        sysUserSelect = new SysUser();
        sysUserSelect.setMobile(sysUser.getMobile());
        sysUserSelect.setAccountId(accountId);
        sysUserSelect.setAccountAppType(accountAppType);
        sysUserSelect.setAccountCorpId(accountCorpId);
        sysUserList = sysUserMapper.selectBySysUserList(sysUserSelect);
        // 如果电话号已存在
        if(StrUtil.isNotEmpty(sysUser.getMobile())) {
            if (sysUserList != null && sysUserList.size() > 0) {
//                SysDepartment sysDepartmentSelect = new SysDepartment();
//                sysDepartmentSelect.setUserKey(sysUserList.get(0).getUserKey());
//                List<SysDepartment> errorSysDepartmentList = sysDepartmentService.getSysDepartmentListByUserkey(sysDepartmentSelect);
//                if(errorSysDepartmentList != null) {
//                    String deptName = errorSysDepartmentList.get(0).getDepartmentPathName();
//                    return repEntity.layerMessage("no",
//                            "电话号已在【" + sysUserList.get(0).getRealName() + "" + deptName + "】账号中使用。");
//                }
                return repEntity.layerMessage("no",
                      "电话号已在【" + sysUserList.get(0).getRealName() + "】账号中使用。");
            }
        }

        // 用户表中写数据
        sysUser.setUserKey(UuidUtil.generate());
        String userPwd = SecureUtil.md5(apih5Properties.getDefaultPassword() + apih5Properties.getMd5Salt());
        sysUser.setUserPwd(userPwd);
        sysUser.setCreateUserInfo(userKey, userName);

        // 权限字段扩展Ext10
        JSONArray jsonArrayExt10 = new JSONArray();
        jsonArrayExt10.add(sysUser.getUserKey());

        String companyId = "";
        String projectId = "";
        List departmentIdList = Lists.newArrayList();
        // 循环插入新部门数据
        for (Map map : sysDepartmentList) {
            if (map.get("value") != null) {
                map.put("departmentId", map.get("value"));
                map.put("departmentName", map.get("label"));
                map.put("departmentParentId", map.get("valuePid"));
            }
            SysDepartment sysDepartment = BeanUtil.mapToBean(map, SysDepartment.class, true);
            SysUserDepartment sysUserDepartment = new SysUserDepartment();
            sysUserDepartment.setUserDepartmentId(UuidUtil.generate());
            sysUserDepartment.setUserKey(sysUser.getUserKey());
            sysUserDepartment.setDepartmentId(sysDepartment.getDepartmentId());
            SysDepartment dbSysDepartment = sysDepartmentMapper.selectByPrimaryKey(sysDepartment.getDepartmentId());
            if(dbSysDepartment != null) {
                companyId = dbSysDepartment.getCompanyId();
            }
            SysProject dbSysProject = sysProjectMapper.selectByPrimaryKey(sysDepartment.getDepartmentId());
            if(dbSysProject != null) {
                projectId = dbSysProject.getProjectId();
            }
            
            if (Apih5Properties.isUseSyncWeChat()) {
                departmentIdList.add(dbSysDepartment.getOtherId());
            }
            sysUserDepartment.setCreateUserInfo(userKey, userName);
            int flag = sysUserDepartmentMapper.insert(sysUserDepartment);
            if (flag == 0) {
                throw new Apih5Exception("部门关系表插入失败!");
//                return repEntity.error("sys.exception");
            }
            jsonArrayExt10.add(sysDepartment.getDepartmentId());
        }
        // 更新user标的Ext10信息【userKey,departmentId】
        sysUser.setExt10(jsonArrayExt10.toString());

        // 证件类型
        if (StrUtil.isNotEmpty(sysUser.getCertType())) {
            String certTypeName = baseCodeService.getBaseCodeItemName("certType", sysUser.getCertType());
            sysUser.setCertTypeName(certTypeName);
        }
        // 国家
        if (StrUtil.isNotEmpty(sysUser.getNationnality())) {
            String nationnalityName = baseCodeService.getBaseCodeItemName("nationnality", sysUser.getNationnality());
            sysUser.setNationnalityName(nationnalityName);
        }
        // 民族
        if (StrUtil.isNotEmpty(sysUser.getNation())) {
            String nationName = baseCodeService.getBaseCodeItemName("nation", sysUser.getNation());
            sysUser.setNationName(nationName);
        }
        // 职务
        if (StrUtil.isNotEmpty(sysUser.getPostions())) {
            String postionsName = baseCodeService.getBaseCodeItemName("postions", sysUser.getPostions());
            sysUser.setPostionsName(postionsName);
        }
        // 职称
        if (StrUtil.isNotEmpty(sysUser.getPositiongrade())) {
            String positiongradeName = baseCodeService.getBaseCodeItemName("positiongrade", sysUser.getPositiongrade());
            sysUser.setPositiongradeName(positiongradeName);
        }
        // 岗位
        if (StrUtil.isNotEmpty(sysUser.getJobType())) {
            String jobTypeName = baseCodeService.getBaseCodeItemName("jobType", sysUser.getJobType());
            sysUser.setJobTypeName(jobTypeName);
        }
        // 员工类型
        if (StrUtil.isNotEmpty(sysUser.getEmpType())) {
            String empTypeName = baseCodeService.getBaseCodeItemName("empType", sysUser.getEmpType());
            sysUser.setEmpTypeName(empTypeName);
        }
        // 原来旧的前端没有，用户状态设置
        if(StrUtil.isEmpty(sysUser.getUserStatus())) {
            sysUser.setUserStatus("1");
        } else {
            sysUser.setUserStatus(sysUser.getUserStatus());
        }

        // 临时修改权限方式，应该用部门departmentFlag来判断权限才合理
        if(StrUtil.isEmpty(sysUser.getExt1())) {
            if(StrUtil.isNotEmpty(companyId)) {
                SysDepartment dbSysDepartment = sysDepartmentMapper.selectByPrimaryKey(companyId);
                sysUser.setExt1(dbSysDepartment.getDepartmentFlag());
            }
            if(StrUtil.isNotEmpty(projectId)) {
                SysProject dbSysProject = sysProjectMapper.selectByPrimaryKey(projectId);
                sysUser.setExt1(dbSysProject.getDepartmentFlag());
            }
        }
        int flag = userMapper.insertSelective(sysUser);
        if (flag == 0) {
            throw new Apih5Exception("用户创建失败!");
        }

        // 微信用户信息同步
        if (Apih5Properties.isUseSyncWeChat() && sysUser.isUseSyncWeChatPar()) {
            List<BaseAccount> baseAccountList = baseAccountMapper.selectByBaseAccountListByLike("_txl");
            accountId = baseAccountList.get(0).getAccountId();
            Map<String, String> accessTokenMap = weChatEnterpriseDbService.getWeChatAccessToken(accountId);
            String accessToken = accessTokenMap.get("accessToken");

            Map<String, String> getParamMap = new HashMap<String, String>();
            getParamMap.put("access_token", accessToken);
            UserInfoReq userInfoReq = new UserInfoReq();
            userInfoReq.setUserid(sysUser.getUserId());
            userInfoReq.setName(sysUser.getRealName());
            userInfoReq.setEnable(1);
            userInfoReq.setDepartment(departmentIdList);
            userInfoReq.setMobile(sysUser.getMobile());
            // 更新部门下的所有人员
            JSONObject jsonObject = wechatEnterpriseService.coreServiceByResurceAddressbook(6, getParamMap,
                    userInfoReq);
            if (jsonObject == null || !StrUtil.equals("0", jsonObject.getStr("errcode"))) {
                // UserID已存在
                if (StrUtil.equals("60102", jsonObject.getStr("errcode"))) {
                    // 更新部门下的所有人员
                    jsonObject = wechatEnterpriseService.coreServiceByResurceAddressbook(7, getParamMap, userInfoReq);
                    if (jsonObject == null || !StrUtil.equals("0", jsonObject.getStr("errcode"))) {
                        LoggerUtils.printExceptionLogger(logger, "微信同步添加人员(修改)错误信息=" + jsonObject.toString());
                        throw new Apih5Exception("微信同步添加人员(修改)错误信息(" + jsonObject.getStr("errcode") + ")");
                    }
                } else {
                    LoggerUtils.printExceptionLogger(logger, "微信同步添加人员错误信息=" + jsonObject.toString());
                    throw new Apih5Exception("微信同步添加人员错误信息(" + jsonObject.getStr("errcode") + ")");
                }
            }
        }

        // 清缓存
        SysDepartmentServiceImpl.cacheMap.clear();

        // 新增用户成功
        sysUser.setUserPwd(null);
        return repEntity.ok("sys.data.sava", sysUser);
    }

    /**
     * 后台-修改用户
     * 
     * @param sysUser
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity updateSysUserInfoByBg(SysUser sysUser) throws Exception {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String userId = TokenUtils.getUserId(request);
        String userName = TokenUtils.getRealName(request);
        String accountId = TokenUtils.getAccountId(request);
        // 部门关系表中写数据
        List<Map> sysDepartmentList = sysUser.getSysDepartmentList();
        if (sysDepartmentList == null || sysDepartmentList.size() == 0) {
            return repEntity.error("sys.exception");
        }
        if (StrUtil.isEmpty(sysUser.getUserKey())) {
            return repEntity.layerMessage("no", "数据错误！");
        }
        // 手机号合法检查
        if(StrUtil.isNotEmpty(sysUser.getMobile())) {
            if (!Validator.isNumber(sysUser.getMobile())) {
                return repEntity.layerMessage("no", "手机号不合法，请您检查。");
            }
        }
        
        // 厦门时使用此check
        if(StrUtil.equals("zj_xiamengs_renzi", accountId) && !StrUtil.equals("admin", userId)) {
            // 查数据库当前部门
            List<String> dbNotDepartmentList = Lists.newArrayList();
            SysDepartment checkSysDepartment = new SysDepartment();
            checkSysDepartment.setUserKey(sysUser.getUserKey());
            List<SysDepartment> checkSysDepartmentList = sysDepartmentMapper.selectSysDepartmentByUserkey(checkSysDepartment);
            for (SysDepartment checkDbSysDepartment : checkSysDepartmentList) {
                    // 吧数据库中不存在于部门的项目放到dbNotDepartmentId中
                    if(sysDepartmentList.toString().indexOf(checkDbSysDepartment.getProjectId())<0) {
                        dbNotDepartmentList.add(checkDbSysDepartment.getProjectId());
                    }
            }
            // 
            if(dbNotDepartmentList != null && dbNotDepartmentList.size()>0) {
                SysDepartment checkSysDepartment2 = new SysDepartment();
                checkSysDepartment2.setUserKey(userKey);
                List<SysDepartment> checkSysDepartmentList2 = sysDepartmentMapper.selectSysDepartmentByUserkey(checkSysDepartment2);
                JSONArray checkJSONArray = new JSONArray(checkSysDepartmentList2);
                for (String str : dbNotDepartmentList) {
                    if(checkJSONArray.toString().indexOf(str)<0) {
                        return repEntity.layerMessage("no", "无权限删除自己范围外的部门");
                    }
                }
            }
        }

        sysUser.setAccountId(accountId);
//        BaseAccount baseAccount = baseAccountMapper.selectByPrimaryKey(accountId);
        BaseAccount baseAccount = baseAccountService.getBaseAccount(accountId);
        String accountCorpId = baseAccount.getAccountCorpId();
        String accountAppType = baseAccount.getAccountAppType();
        if (StrUtil.isNotEmpty(sysUser.getAccountAppType())) {
            accountAppType = sysUser.getAccountAppType();
        }
        sysUser.setAccountCorpId(accountCorpId);
        sysUser.setAccountAppType(accountAppType);

        int flag = 0;
        SysUser dbSysUser = sysUserMapper.selectByPrimaryKey(sysUser.getUserKey());

        // 电话号check
        if (!StrUtil.equals(dbSysUser.getMobile(), sysUser.getMobile())) {
            SysUser sysUserSelect = new SysUser();
            sysUserSelect = new SysUser();
            sysUserSelect.setMobile(sysUser.getMobile());
            sysUserSelect.setAccountId(accountId);
            sysUserSelect.setAccountAppType(sysUser.getAccountAppType());
            sysUserSelect.setAccountCorpId(sysUser.getAccountCorpId());
            List sysUserList = sysUserMapper.selectBySysUserList(sysUserSelect);
            // 如果电话号已存在
            if (sysUserList != null && sysUserList.size() > 0) {
                return repEntity.layerMessage("no", "电话号已存在。");
            }
        }

        if (dbSysUser != null && StrUtil.isNotEmpty(dbSysUser.getUserId())) {
            dbSysUser.setRealName(sysUser.getRealName());
            dbSysUser.setMobile(sysUser.getMobile());
            dbSysUser.setIdentityCard(sysUser.getIdentityCard());
            dbSysUser.setGender(sysUser.getGender());
            dbSysUser.setImageUrl(sysUser.getImageUrl());
            dbSysUser.setExpirationDate(sysUser.getExpirationDate());
            dbSysUser.setEmail(sysUser.getEmail());
            dbSysUser.setBirthday(sysUser.getBirthday());
            dbSysUser.setAge(sysUser.getAge());
            dbSysUser.setExt1(sysUser.getExt1());
            dbSysUser.setExt2(sysUser.getExt2());
//            dbSysUser.setExt3(sysUser.getExt3());
            dbSysUser.setExt5(sysUser.getExt5());
            dbSysUser.setExt6(sysUser.getExt6());
            dbSysUser.setExt9(sysUser.getExt9());
            // ext10
            JSONArray jsonArrayExt10 = new JSONArray();
            jsonArrayExt10.add(dbSysUser.getUserKey());
            for (Map map : sysDepartmentList) {
                if (map.get("value") != null) {
                    map.put("departmentId", map.get("value"));
                    map.put("departmentName", map.get("label"));
                    map.put("departmentParentId", map.get("valuePid"));
                }
                SysDepartment sysDepartment = BeanUtil.mapToBean(map, SysDepartment.class, true);
                jsonArrayExt10.add(sysDepartment.getDepartmentId());
            }
            dbSysUser.setExt10(jsonArrayExt10.toString());

            // 证件类型
            if (StrUtil.isNotEmpty(sysUser.getCertType())) {
                String certTypeName = baseCodeService.getBaseCodeItemName("certType", sysUser.getCertType());
                dbSysUser.setCertType(sysUser.getCertType());
                dbSysUser.setCertTypeName(certTypeName);
            }
            // 国家
            if (StrUtil.isNotEmpty(sysUser.getNationnality())) {
                String nationnalityName = baseCodeService.getBaseCodeItemName("nationnality",
                        sysUser.getNationnality());
                dbSysUser.setNationnality(sysUser.getNationnality());
                dbSysUser.setNationnalityName(nationnalityName);
            }
            // 民族
            if (StrUtil.isNotEmpty(sysUser.getNation())) {
                String nationName = baseCodeService.getBaseCodeItemName("nation", sysUser.getNation());
                dbSysUser.setNation(sysUser.getNation());
                dbSysUser.setNationName(nationName);
            }
            // 职务
            if (StrUtil.isNotEmpty(sysUser.getPostions())) {
                String postionsName = baseCodeService.getBaseCodeItemName("postions", sysUser.getPostions());
                dbSysUser.setPostions(sysUser.getPostions());
                dbSysUser.setPostionsName(postionsName);
            } else {
                if (StrUtil.isNotEmpty(sysUser.getPostionsName())) {
                    dbSysUser.setPostionsName(sysUser.getPostionsName());
                }
            }
            // 职称
            if (StrUtil.isNotEmpty(sysUser.getPositiongrade())) {
                String positiongradeName = baseCodeService.getBaseCodeItemName("positiongrade",
                        sysUser.getPositiongrade());
                dbSysUser.setPositiongrade(sysUser.getPositiongrade());
                dbSysUser.setPositiongradeName(positiongradeName);
            }
            // 岗位
            if (StrUtil.isNotEmpty(sysUser.getJobType())) {
                String jobTypeName = baseCodeService.getBaseCodeItemName("jobType", sysUser.getJobType());
                dbSysUser.setJobType(sysUser.getJobType());
                dbSysUser.setJobTypeName(jobTypeName);
            }
            // 员工类型
            if (StrUtil.isNotEmpty(sysUser.getEmpType())) {
                String empTypeName = baseCodeService.getBaseCodeItemName("empType", sysUser.getEmpType());
                dbSysUser.setEmpType(sysUser.getEmpType());
                dbSysUser.setEmpTypeName(empTypeName);
            }
            // 原来旧的前端没有，用户状态设置
            if(StrUtil.isEmpty(sysUser.getUserStatus())) {
                dbSysUser.setUserStatus("1");
            } else {
                dbSysUser.setUserStatus(sysUser.getUserStatus());
            }
            dbSysUser.setSort(sysUser.getSort());
            // 共通
            dbSysUser.setModifyUserInfo(userKey, userName);
            flag = sysUserMapper.updateByPrimaryKey(dbSysUser);

            List departmentIdList = Lists.newArrayList();
            // 根据用户删除部门关系表
            List<SysUserDepartment> sysUserDepartmentList = Lists.newArrayList();
            SysUserDepartment sysUserDepartmentUserKey = new SysUserDepartment();
            sysUserDepartmentUserKey.setUserKey(sysUser.getUserKey());
            sysUserDepartmentList.add(sysUserDepartmentUserKey);
            sysUserDepartmentMapper.batchDeleteUpdateSysUserDepartmentByUserKey(sysUserDepartmentList);
            // 循环插入新部门数据
            for (Map map : sysDepartmentList) {
                SysDepartment sysDepartment = BeanUtil.mapToBean(map, SysDepartment.class, true);
                SysUserDepartment sysUserDepartment = new SysUserDepartment();
                sysUserDepartment.setUserDepartmentId(UuidUtil.generate());
                sysUserDepartment.setUserKey(sysUser.getUserKey());
                sysUserDepartment.setDepartmentId(sysDepartment.getDepartmentId());
                if (Apih5Properties.isUseSyncWeChat()) {
                    SysDepartment dbSysDepartment = sysDepartmentMapper
                            .selectByPrimaryKey(sysDepartment.getDepartmentId());
                    departmentIdList.add(dbSysDepartment.getOtherId());
                }
                sysUserDepartment.setCreateUserInfo(userKey, userName);
                flag = sysUserDepartmentMapper.insert(sysUserDepartment);
                if (flag == 0) {
                    LoggerUtils.printExceptionLogger(logger, "部门关系表更新失败=");
                    throw new Apih5Exception("部门关系表更新失败");
//                    return repEntity.error("sys.exception");
                }
            }

            // 微信用户信息同步
            if (Apih5Properties.isUseSyncWeChat()) {
                List<BaseAccount> baseAccountList = baseAccountMapper.selectByBaseAccountListByLike("_txl");
                accountId = baseAccountList.get(0).getAccountId();
                Map<String, String> accessTokenMap = weChatEnterpriseDbService.getWeChatAccessToken(accountId);
                String accessToken = accessTokenMap.get("accessToken");

                Map<String, String> getParamMap = new HashMap<String, String>();
                getParamMap.put("access_token", accessToken);
                UserInfoReq userInfoReq = new UserInfoReq();
                userInfoReq.setUserid(sysUser.getUserId());
                userInfoReq.setName(sysUser.getRealName());
                userInfoReq.setEnable(1);
                userInfoReq.setDepartment(departmentIdList);
                userInfoReq.setMobile(sysUser.getMobile());
                // 更新部门下的所有人员
                JSONObject jsonObject = wechatEnterpriseService.coreServiceByResurceAddressbook(7, getParamMap,
                        userInfoReq);
                if (jsonObject == null || !StrUtil.equals("0", jsonObject.getStr("errcode"))) {
                    LoggerUtils.printDebugLogger(logger, "微信同步修改人员错误信息" + jsonObject.toString());
                    // 有可能是实现么有做过同步所以需要新增
                    getParamMap = new HashMap<String, String>();
                    getParamMap.put("access_token", accessToken);
                    userInfoReq = new UserInfoReq();
                    userInfoReq.setUserid(sysUser.getUserId());
                    userInfoReq.setName(sysUser.getRealName());
                    userInfoReq.setEnable(1);
                    userInfoReq.setDepartment(departmentIdList);
                    userInfoReq.setMobile(sysUser.getMobile());
                    // 更新部门下的所有人员
                    jsonObject = wechatEnterpriseService.coreServiceByResurceAddressbook(6, getParamMap, userInfoReq);
                    if (jsonObject == null || !StrUtil.equals("0", jsonObject.getStr("errcode"))) {
                        LoggerUtils.printExceptionLogger(logger, "微信同步添加人员错误信息" + jsonObject.toString());
                        throw new Apih5Exception("微信同步修改人员错误信息(" + jsonObject.getStr("errcode") + ")");
                    }
                }

            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            // 清缓存
            SysDepartmentServiceImpl.cacheMap.clear();

            // 缓存key
            String userEhCacheKey = dbSysUser.getUserKey() + "-" + accountId;
            // 得到token的值
//            TokenEntity tokenEntity = EhCacheCacheHandler.getUserTokenEhCache(userEhCacheKey);
            TokenEntity tokenEntity = sysRedisCacheService.getUserTokenRedis(userEhCacheKey);
            if (tokenEntity != null) {
//                EhCacheCacheHandler.removeUserTokenEhCache(userEhCacheKey);
                sysRedisCacheService.removeUserTokenRedis(userEhCacheKey);
            }
            return repEntity.ok("sys.data.update", sysUser);
        }
    }

    /**
     * 后台-删除用户
     * 
     * @param sysUser
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity deleteSysUserInfoByBg(SysUser sysUser) throws Exception {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String token = TokenUtils.getToken(request);
        String tokenUserId = TokenUtils.getUserId(request);
        String accountId = TokenUtils.getAccountId(request);
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        if (!StrUtil.equals("admin", tokenUserId)) {
//            return repEntity.layerMessage("no", "您没有权限删除用户信息。");
        }
        String userId = sysUserMapper.selectByPrimaryKey(sysUser.getUserKey()).getUserId();
        if (StrUtil.equals("admin", userId)) {
            return repEntity.layerMessage("no", "系统管理员不能删除。");
        }
        if(StrUtil.equals(sysUser.getUserKey(), userKey)) {
            return repEntity.layerMessage("no", "不能刪除自己的账号！");
        }
        
        // 厦门时，用这check
        // 厦门时使用此check
        if(StrUtil.equals("zj_xiamengs_renzi", accountId) && !StrUtil.equals("admin", userId)) {
            // 查数据库当前部门
            List<String> dbNotDepartmentList = Lists.newArrayList();
            SysDepartment checkSysDepartment = new SysDepartment();
            checkSysDepartment.setUserKey(sysUser.getUserKey());
            List<SysDepartment> checkSysDepartmentList = sysDepartmentMapper.selectSysDepartmentByUserkey(checkSysDepartment);
           
            // 操作者
            SysDepartment checkSysDepartmentOpt = new SysDepartment();
            checkSysDepartmentOpt.setUserKey(userKey);
            List<SysDepartment> checkSysDepartmentListOpt = sysDepartmentMapper.selectSysDepartmentByUserkey(checkSysDepartmentOpt);
            JSONArray checkJSONArray = new JSONArray(checkSysDepartmentListOpt);
            for (SysDepartment checkDbSysDepartment : checkSysDepartmentList) {
                if(checkJSONArray.toString().indexOf(checkDbSysDepartment.getProjectId())<0) {
                    return repEntity.layerMessage("no", "无权限删除自己范围外的部门");
                }
            }
        }

        // 增加其他模块，已授权的用户信息禁止删除（如劳务系统授权了组织机构，则删除人员时，需要有提示）
        String checkAuthorize = publicConfig.getProperty("check.authorize.delete.api", "");
        if (!StrUtil.equals("空", checkAuthorize)) {
            String url = Apih5Properties.getWebUrl() + checkAuthorize;
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("value", sysUser.getUserKey());
            String result = HttpUtil.sendPostToken(url, jsonObject.toString(), token);
            JSONObject jsonResult = JSONUtil.parseObj(result);
            if (jsonResult.getBool("success")) {
                return repEntity.layerMessage("no", jsonResult.getStr("data"));
            }
        }

        sysUser.setAccountId(accountId);
//        BaseAccount baseAccount = baseAccountMapper.selectByPrimaryKey(accountId);
        BaseAccount baseAccount = baseAccountService.getBaseAccount(accountId);
        String accountCorpId = baseAccount.getAccountCorpId();
        String accountAppType = baseAccount.getAccountAppType();
        if (StrUtil.isNotEmpty(sysUser.getAccountAppType())) {
            accountAppType = sysUser.getAccountAppType();
        }
        sysUser.setAccountCorpId(accountCorpId);
        sysUser.setAccountAppType(accountAppType);

        // 删除用户信息
        List<SysUser> sysUserList = Lists.newArrayList();
        sysUserList.add(sysUser);
        SysUser sysUserInfo = new SysUser();
        sysUserInfo.setModifyUserInfo(userKey, realName);
        int flag = sysUserMapper.batchDeleteSysUser(sysUserList, sysUserInfo);
        if (flag == 0) {
            return repEntity.errorSave();
        }

        // 删除部门关系表
        List<SysUserDepartment> sysUserDepartmentList = Lists.newArrayList();
        SysUserDepartment sysUserDepartment = new SysUserDepartment();
        sysUserDepartment.setUserKey(sysUser.getUserKey());
        sysUserDepartmentList.add(sysUserDepartment);
        flag = sysUserDepartmentMapper.batchDeleteUpdateSysUserDepartmentByUserKey(sysUserDepartmentList);
        if (flag == 0) {
            return repEntity.errorSave();
        }

        // 删除角色菜单信息
        SysRoleUser sysRoleUser = new SysRoleUser();
        sysRoleUser.setValue(sysUser.getUserKey());
        List<SysRoleUser> sysRoleUserList = sysRoleUserMapper.selectBySysRoleUserList(sysRoleUser);
        if (sysRoleUserList != null && sysRoleUserList.size() > 0) {
            sysRoleUserMapper.batchDeleteUpdateSysRoleUser(sysRoleUserList);
        }

        // 微信用户信息同步,应该批量删除
        if (Apih5Properties.isUseSyncWeChat()) {
            List<BaseAccount> baseAccountList = baseAccountMapper.selectByBaseAccountListByLike("_txl");
            accountId = baseAccountList.get(0).getAccountId();
            Map<String, String> accessTokenMap = weChatEnterpriseDbService.getWeChatAccessToken(accountId);
            String accessToken = accessTokenMap.get("accessToken");

            Map<String, String> getParamMap = new HashMap<String, String>();
            getParamMap.put("access_token", accessToken);
            getParamMap.put("userid", userId);
            // 更新部门下的所有人员
            JSONObject jsonObject = wechatEnterpriseService.coreServiceByResurceAddressbook(8, getParamMap, null);
            if (jsonObject == null || !StrUtil.equals("0", jsonObject.getStr("errcode"))) {
                LoggerUtils.printExceptionLogger(logger, "微信同步删除人员错误信息" + jsonObject.toString());
//                throw new Apih5Exception("微信同步删除人员错误信息(" + jsonObject.getStr("errcode") + ")");
            }
        }

        // 清缓存
        SysDepartmentServiceImpl.cacheMap.clear();

        // 缓存key
        String userEhCacheKey = sysUser.getUserKey() + "-" + accountId;
        // 得到token的值
//        TokenEntity tokenEntity = EhCacheCacheHandler.getUserTokenEhCache(userEhCacheKey);
        TokenEntity tokenEntity = sysRedisCacheService.getUserTokenRedis(userEhCacheKey);
        if (tokenEntity != null) {
//            EhCacheCacheHandler.removeUserTokenEhCache(userEhCacheKey);
            sysRedisCacheService.removeUserTokenRedis(userEhCacheKey);
        }
        return repEntity.ok("sys.data.delete", sysUser);
    }

    /**
     * 后台-修改用户
     * 
     * @param sysUser
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity updateSysUserEnableByBg(SysUser sysUser) throws Exception {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String userName = TokenUtils.getRealName(request);

        // account中查找企业信息
        List<BaseAccount> baseAccountList = baseAccountMapper.selectByBaseAccountListByLike("_txl");
        String accountId = baseAccountList.get(0).getAccountId();
        Map<String, String> accessTokenMap = weChatEnterpriseDbService.getWeChatAccessToken(accountId);
        String accessToken = accessTokenMap.get("accessToken");
        sysUser.setAccountCorpId(accessTokenMap.get("accountCorpId"));
        sysUser.setAccountAppType(accessTokenMap.get("accountAppType"));

        int flag = 0;
        SysUser dbSysUser = sysUserMapper.selectByPrimaryKey(sysUser.getUserKey());
        if (dbSysUser != null && StrUtil.isNotEmpty(dbSysUser.getUserId())) {
            // 禁用0、启用1
            dbSysUser.setUserStatus(sysUser.getUserStatus());
            // 共通
            dbSysUser.setModifyUserInfo(userKey, userName);
            flag = sysUserMapper.updateByPrimaryKey(dbSysUser);

            // 微信用户信息同步
//            if (Apih5Properties.isUseSyncWeChat()) {
//                Map<String, String> getParamMap = new HashMap<String, String>();
//                getParamMap.put("access_token", accessToken);
//                UserInfoReq userInfoReq = new UserInfoReq();
//                userInfoReq.setUserid(sysUser.getUserId());
//                userInfoReq.setName(sysUser.getRealName());
//                userInfoReq.setEnable(1);
//                userInfoReq.setDepartment(departmentIdList);
//                userInfoReq.setMobile(sysUser.getMobile());
//                // 更新部门下的所有人员
//                JSONObject jsonObject = wechatEnterpriseService.coreServiceByResurceAddressbook(7, getParamMap,
//                        userInfoReq);
//                if (jsonObject == null || !StrUtil.equals("0", jsonObject.getStr("errcode"))) {
//                    LoggerUtils.printDebugLogger(logger, "微信同步修改人员错误信息" + jsonObject.toString());
//                    // 有可能是实现么有做过同步所以需要新增
//                    getParamMap = new HashMap<String, String>();
//                    getParamMap.put("access_token", accessToken);
//                    userInfoReq = new UserInfoReq();
//                    userInfoReq.setUserid(sysUser.getUserId());
//                    userInfoReq.setName(sysUser.getRealName());
//                    userInfoReq.setEnable(1);
//                    userInfoReq.setDepartment(departmentIdList);
//                    userInfoReq.setMobile(sysUser.getMobile());
//                    // 更新部门下的所有人员
//                    jsonObject = wechatEnterpriseService.coreServiceByResurceAddressbook(6, getParamMap, userInfoReq);
//                    if (jsonObject == null || !StrUtil.equals("0", jsonObject.getStr("errcode"))) {
//                        LoggerUtils.printExceptionLogger(logger, "微信同步添加人员错误信息" + jsonObject.toString());
//                        throw new Apih5Exception("微信同步修改人员错误信息(" + jsonObject.getStr("errcode") + ")");
//                    }
//                }
//            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            // 清缓存
            SysDepartmentServiceImpl.cacheMap.clear();

            // 缓存key
            String userEhCacheKey = dbSysUser.getUserKey() + "-" + accountId;
            // 得到token的值
//            TokenEntity tokenEntity = EhCacheCacheHandler.getUserTokenEhCache(userEhCacheKey);
            TokenEntity tokenEntity = sysRedisCacheService.getUserTokenRedis(userEhCacheKey);
            if (tokenEntity != null) {
//                EhCacheCacheHandler.removeUserTokenEhCache(userEhCacheKey);
                sysRedisCacheService.removeUserTokenRedis(userEhCacheKey);
            }
            return repEntity.ok("sys.data.update", sysUser);
        }
    }

    /**
     * 后台-修改用户
     * 
     * @param sysUser
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity updateSysUserRedisByBg(SysUser sysUser) throws Exception {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String userName = TokenUtils.getRealName(request);

        // account中查找企业信息
        List<BaseAccount> baseAccountList = baseAccountMapper.selectByBaseAccountListByLike("_txl");
        String accountId = baseAccountList.get(0).getAccountId();
//        Map<String, String> accessTokenMap = weChatEnterpriseDbService.getWeChatAccessToken(accountId);
//        sysUser.setAccountCorpId(accessTokenMap.get("accountCorpId"));
//        sysUser.setAccountAppType(accessTokenMap.get("accountAppType"));

        int flag = 0;
        SysUser dbSysUser = sysUserMapper.selectByPrimaryKey(sysUser.getUserKey());
        if (dbSysUser != null && StrUtil.isNotEmpty(dbSysUser.getUserId())) {
            // ext1
            dbSysUser.setExt1(sysUser.getExt1());
            // 共通
            dbSysUser.setModifyUserInfo(userKey, userName);
            flag = sysUserMapper.updateByPrimaryKey(dbSysUser);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            // 清缓存
            SysDepartmentServiceImpl.cacheMap.clear();
            // 缓存key
            String userEhCacheKey = dbSysUser.getUserKey() + "-" + accountId;
            // 得到token的值
            TokenEntity tokenEntity = sysRedisCacheService.getUserTokenRedis(userEhCacheKey);
            if (tokenEntity != null) {
                tokenEntity.setExt1(sysUser.getExt1());
                sysRedisCacheService.putUserTokenRedis(userEhCacheKey, tokenEntity);
            }

            return repEntity.ok("sys.data.update", sysUser);
        }
    }

    @Override
    public SysUser checkUserExists(SysUser sysUser) {
        return userMapper.checkUserExists(sysUser);
    }

    @Override
    public SysUser checkUserIdExists(SysUser sysUser) {
        return userMapper.checkUserIdExists(sysUser);
    }

    @Override
    public SysUser getSysUserByloginAccount(SysUser sysUser) {
        List<SysUser> userList = userMapper.selectBySysUserList(sysUser);
        if (userList == null || userList.size() == 0) {
            return null;
        } else {
            return userList.get(0);
        }
    }

    @Override
    public List<SysUser> listAll(SysUser sysUser) {
        return userMapper.selectBySysUserList(sysUser);
    }

    /**
     * 获取所有用户信息（只是自己的数据，不关联部门信息）
     * 
     * @param sysUser
     * @return sysUserList
     */
    @Override
    public ResponseEntity getSysUserListByUser(SysUser sysUser) {
        // 分页查询
        PageHelper.startPage(sysUser.getPage(), sysUser.getLimit());
        List<SysUser> sysUserList = userMapper.selectBySysUserList(sysUser);
        // 得到分页信息
        PageInfo<SysUser> p = new PageInfo<>(sysUserList);
        return repEntity.okList(sysUserList, p.getTotal());
    }

    // @Override
    // public int getUsersListCount(SysUser sysUser) {
    // return userMapper.getUsersListCount(sysUser);
    // }

    @Override
    public SysUser getSysUserByUserId(String key) {
        // return userMapper.getSysUserByUserId(id);
        return userMapper.getSysUserByUserId(key);
    }

    @Override
    public SysUser getSysUserByUserKey(String key) {
        return userMapper.selectByPrimaryKey(key);
    }

    @Override
    public void delete(String userKey) {
        userMapper.deleteByPrimaryKey(userKey);
        // userMapper.deleteUserRoleMapping(userId);
        // articleMapper.removeApih5ContentByUserId(userId);
        // articleMapper.removeTemplateConfigByUserId(userId);
    }

    // @Override
    // public void addUser(SysUser sysUser) {
    // // 默认密码：123456
    // String password = SecureUtil.md5(apih5Properties.getDefaultPassword() +
    // apih5Properties.getMd5Salt());
    // sysUser.setPassword(password);
    // // 首先向用户表中新增一条数据
    // userMapper.userAdd(sysUser);
    // // 得到用户选择的角色
    // String userRoleStr = sysUser.getUserRoleStr();
    // addSysUserRole(userRoleStr, sysUser);
    // }

    @Override
    public ResponseEntity addUser(SysUser sysUser) {
        // account中查找企业信息
//        BaseAccount baseAccount = baseAccountMapper.selectByPrimaryKey(sysUser.getAccountId());
        BaseAccount baseAccount = baseAccountService.getBaseAccount(sysUser.getAccountId());
        sysUser.setAccountCorpId(baseAccount.getAccountCorpId());
        sysUser.setAccountAppType(baseAccount.getAccountAppType());
        LoggerUtils.printDebugLogger(logger, "addUser+1+      baseAccount");
        // 如果验证码都已通过
        SysUser userExists = this.checkUserIdExists(sysUser);
        // 如果注册账号已存在
        if (userExists != null) {
            return repEntity.error("sys.user.register.exists");
        }
        LoggerUtils.printDebugLogger(logger, "addUser+2+      userExists");
        // 写数据
        sysUser.setUserKey(UuidUtil.generate());
        String userPwd = SecureUtil.md5(sysUser.getUserPwd() + apih5Properties.getMd5Salt());
        LoggerUtils.printDebugLogger(logger, "addUser+2+      sysUser.getUserPwd");
        sysUser.setUserPwd(userPwd);
        sysUser.setDelFlag("0");
        sysUser.setCreateTime(new Date());
        sysUser.setCreateUser(sysUser.getUserId());
        sysUser.setModifyTime(new Date());
        sysUser.setModifyUser(sysUser.getUserId());
        int flag = userMapper.insertSelective(sysUser);
        if (flag == 0) {
            return repEntity.error("sys.exception");
        } else {
            // 表示系统注册的用户
            return repEntity.ok("");
        }
        // // 得到用户选择的角色
        // String userRoleStr = sysUser.getUserRoleStr();
        // addSysUserRole(userRoleStr, sysUser);
    }

    // @Override
    // public ResponseEntity updateUser(SysUser sysUser) {
    // HttpServletRequest request =
    // requestHolderConfiguration.getHttpServletRequest();
    // String userKey = TokenUtils.getUserKey(request);
    // int flag = 0;
    // SysUser dbSysUser = userMapper.selectByPrimaryKey(sysUser.getUserKey());
    // if (dbSysUser != null && StrUtil.isNotEmpty(dbSysUser.getUserKey())) {
    // dbSysUser.setRealName(sysUser.getRealName());
    // dbSysUser.setGender(sysUser.getGender());
    // dbSysUser.setImageUrl(sysUser.getImageUrl());
    // // 共通
    // dbSysUser.setModifyTime(new Date());
    // dbSysUser.setModifyUser(userKey);
    // flag = userMapper.updateByPrimaryKey(dbSysUser);
    // }
    // // 失败
    // if (flag == 0) {
    // return repEntity.errorSave();
    // } else {
    // return repEntity.ok("sys.data.update", sysUser);
    // }
    //
    // // String password = SecureUtil.md5(sysUser.getPassword() +
    // // apih5Properties.getMd5Salt());
    // // sysUser.setPassword(password);
    // // // 首先更新用户表
    // // userMapper.updateByPrimaryKeySelective(sysUser);
    // // // 如果不是简单需改用户信息
    // // if(sysUser.getSimpleUpdate() == 0) {
    // // // 然后将用户角色表中数据根据用户id清空
    // // int userId = sysUser.getId();
    // // userMapper.deleteUserRoleMapping(userId);
    // // // 最后如果用户选择了角色，像用户角色表中增加数据
    // // // 得到用户选择的角色
    // // String userRoleStr = sysUser.getUserRoleStr();
    // // addSysUserRole(userRoleStr, sysUser);
    // // }
    // }

    /**
     * 通过用户角色字符串增加用户角色列表
     * 
     * @param userRoleStr
     */
    private void addSysUserRole(String userRoleStr, SysUser sysUser) {
        // // 如果用户角色列表不为空
        // if(!StringUtils.isEmpty(userRoleStr)) {
        // List<SysUserRole> sysUserRoles = Lists.newArrayList();
        // String[] split = userRoleStr.split(",");
        // for (String s : split) {
        // SysUserRole sysUserRole = new SysUserRole();
        // sysUserRole.setUserId(sysUser.getId());
        // sysUserRole.setRoleId(Integer.parseInt(s));
        // sysUserRoles.add(sysUserRole);
        // }
        //// userMapper.addSysUserRole(sysUserRoles);
        // }
    }

    @Override
    public ResponseEntity canLogin(SysUser sysUser) {
        // 最终返回的数据
        Map<String, Object> infoMap = Maps.newHashMap();
        if (!StrUtil.equals("1", sysUser.getDefaultUserPwdFlag())) {
            String userPwd = SecureUtil.md5(sysUser.getUserPwd() + apih5Properties.getMd5Salt());
            sysUser.setUserPwd(userPwd);
        }

        String loginType = sysUser.getLoginType();
        // 刚进入是，获取了微信的userId给改实体
        String weChatUserId = sysUser.getUserId();
        // 从DB获取企业账号信息
//        BaseAccount baseAccount = baseAccountMapper.selectByPrimaryKey(sysUser.getAccountId());
        BaseAccount baseAccount = baseAccountService.getBaseAccount(sysUser.getAccountId());
        if(baseAccount == null) {
            return repEntity.layerMessage("no", "登陆信息不正确！");
        }
        // 企业账号ID
        sysUser.setAccountCorpId(baseAccount.getAccountCorpId());
        // 企业应用类型
        sysUser.setAccountAppType(baseAccount.getAccountAppType());
        List<SysUser> sysUserList = null;
        // 中交企业时
        if ("zj_qyh".equals(sysUser.getAccountCorpId())) {
            // 企业号时
            if ("1".equals(baseAccount.getAccountAppType())) {
                // 用户名密码（PC登录时）登录时，已确定身份
                if (StrUtil.equals("1", sysUser.getLoginType()) || StrUtil.equals("9", sysUser.getLoginType())) {
                    // 使用login时的userId
                    // sysUser.setUserId(sysUserList.get(0).getUserId());
                    sysUserList = Lists.newArrayList();
                    sysUserList.add(sysUser);
                } else {
                    // 通过userId查找手机号，然后判断，当前用户属于哪个身份（公司）；sys_user &
                    // sys_user_company
                    sysUserList = userMapper.getSysUserListByCompany(sysUser);
                    // 如果有则获取当前userId（数据只能是一条，如果多条数据，说明有问题）
                    if (sysUserList != null && sysUserList.size() > 0) {
                        sysUser.setUserId(sysUserList.get(0).getUserId());
                    }
                    // 如果没有把当前userId的公司，更新人员公司关系表，在下面环境处理
                    else {
                    }
                }
            }
            // 企业号以外（电话号相同时，防止服务号人员共用企业号人员ID）
            else {
                sysUserList = userMapper.selectBySysUserList(sysUser);
            }
        }

        SysUser dbSysUser = userMapper.checkUserExists(sysUser);
        // 用户ID或密码不正确
        if (dbSysUser == null) {
            if(StrUtil.equals("1", sysUser.getLoginType())) {
                int loginErrorCount = sysRedisCacheService.getLoginLockRedis(sysUser.getUserId())+1;
                sysRedisCacheService.putLoginLockRedis(sysUser.getUserId(), loginErrorCount);
            }
            return repEntity.error("sys.user.pwd");
        }
        // 用户是否无效
        if (!"1".equals(dbSysUser.getUserStatus())) {
            return repEntity.error("sys.user.login.error");
        }
        // 用户是否过期
        if (dbSysUser.getExpirationDate() != null
                && DateUtil.between(DateUtil.date(), dbSysUser.getExpirationDate(), DateUnit.MINUTE) <= 0) {
            return repEntity.error("sys.user.login.error");
        }

        // 缓存key
        String ehCacheKey = dbSysUser.getUserKey() + "-" + sysUser.getAccountId();
        // 得到token的值
//        TokenEntity loginTokenEntity = EhCacheCacheHandler.getUserTokenEhCache(ehCacheKey);
        TokenEntity loginTokenEntity = sysRedisCacheService.getUserTokenRedis(ehCacheKey);
        // 获取缓存中的token，如果缓存没有过期则直接返回原来缓存数据
        if (loginTokenEntity != null) {
            JSONObject jsonObjectWeb = new JSONObject(loginTokenEntity.getSysUserWebObject());
            SysUserWebEntity sysUserWebEntity = JSONUtil.toBean(jsonObjectWeb, SysUserWebEntity.class);
            // SysUserWebEntity sysUserWebEntity =
            // (SysUserWebEntity)loginTokenEntity.getSysUserWebObject();
            // 用户菜单获取
            String roleUserKey = dbSysUser.getUserKey();
            if (StrUtil.equals("admin", dbSysUser.getUserId())) {
                roleUserKey = dbSysUser.getUserId();
            }
            JSONObject treeJSONObject = sysMenuService.getSysMenuAllTreeByRole(roleUserKey);
            if (treeJSONObject != null) {
                JSONObject menuTree = treeJSONObject.getJSONObject("menuTop");
                menuTree.set("children", treeJSONObject.getJSONArray("menuTree"));
//                JSON MenuTree.put("children", jsonObject.getJSONArray("menuTree"));
                sysUserWebEntity.setMenuTree(menuTree);
            }

            // 如果是默认密码是，需要提示修改密码
            boolean isDefaultPasswordReset = false;
            if (apih5Properties.isDefaultPasswordReset() && StrUtil.equals("1", loginType)) {
                String userPwd = sysUser.getUserPwd();
                String defaultPwd = SecureUtil.md5(apih5Properties.getDefaultPassword() + apih5Properties.getMd5Salt());
                if (StrUtil.equals(userPwd, defaultPwd)) {
                    isDefaultPasswordReset = true;
                }
            }
            // 传给web
            infoMap.put("token", loginTokenEntity.getToken());
            infoMap.put("userInfo", sysUserWebEntity);
            infoMap.put("defaultPasswordReset", isDefaultPasswordReset);
            return repEntity.ok(infoMap);
        }

        // 获取ip地址
        String ip = sysUser.getIp();// requestHolderConfiguration.getHttpServletRequest().getRemoteAddr();
        // 生成用户token
        String token = TokenUtils.createUserToken(sysUser.getAccountId(), dbSysUser.getUserKey(), 0,
                apih5Properties.getUserTokenKey(), ip);
        // 往系统log表中添加一条记录
        SysLog sysLog = new SysLog();
        sysLog.setLoginAccount(dbSysUser.getUserId());
        sysLog.setTypeId(1);
        sysLog.setIp(ip);
        sysLog.setDeviceId(sysUser.getDeviceId());
        sysLog.setEquipmentInfo(sysUser.getEquipmentInfo());
        logMapper.logAdd(sysLog);

        // 将用户表中的token字段更新
        // SysUserToken sysUserToken = new SysUserToken();
        // sysUserToken.setUserKey(dbSysUser.getUserKey());
        // sysUserToken.setToken(token);
        // sysUserToken.setIp(ip);
        // userTokenMapper.tokenAdd(sysUserToken);

        // 为服务内部使用提供userInfo参数
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setToken(token);
        tokenEntity.setUserId(dbSysUser.getUserId());
        tokenEntity.setOpenId(dbSysUser.getOpenid());
        tokenEntity.setAccountId(sysUser.getAccountId());
        tokenEntity.setRealName(dbSysUser.getRealName());
        tokenEntity.setExt1(dbSysUser.getExt1());
        tokenEntity.setExt2(dbSysUser.getExt2());
        tokenEntity.setExt3(dbSysUser.getExt3());
        tokenEntity.setExt4(dbSysUser.getExt4());
        tokenEntity.setExt5(dbSysUser.getExt5());
        tokenEntity.setExt6(dbSysUser.getExt6());
        tokenEntity.setExt7(dbSysUser.getExt7());
        tokenEntity.setExt8(dbSysUser.getExt8());
        tokenEntity.setExt9(dbSysUser.getExt9());
        tokenEntity.setExt10(dbSysUser.getExt10());// 权限放置
        tokenEntity.setJobType(dbSysUser.getJobType());
        tokenEntity.setMobile(dbSysUser.getMobile());
        tokenEntity.setWeChatUsereId(weChatUserId);

        // 为web提供的userInfo
        SysUserWebEntity sysUserWebEntity = new SysUserWebEntity();
        sysUserWebEntity.setUserKey(dbSysUser.getUserKey());
        sysUserWebEntity.setUserId(dbSysUser.getUserId());
        sysUserWebEntity.setRealName(dbSysUser.getRealName());
        sysUserWebEntity.setMobile(dbSysUser.getMobile());
        sysUserWebEntity.setImageUrl(dbSysUser.getImageUrl());
        sysUserWebEntity.setGender(dbSysUser.getGender());
        sysUserWebEntity.setExt1(dbSysUser.getExt1());
        sysUserWebEntity.setExt2(dbSysUser.getExt2());
        sysUserWebEntity.setJobType(dbSysUser.getJobType());

        // 中交
        if ("zj_qyh".equals(sysUser.getAccountCorpId())) {
            // 根据手机号查找，该用户所有公司
            List<SysCompany> sysCompanyList = null;
            // 企业号时
            if ("1".equals(baseAccount.getAccountAppType())) {
                sysCompanyList = sysDepartmentService.selectCompanyListByMobile(dbSysUser.getMobile(),
                        dbSysUser.getAccountAppType());
            }
            // 企业号以外（电话号相同时，防止服务号人员共用企业号人员ID）
            else {
                // 部门ID如果与公司主键相等说明当前的部门就是公司，如果不是递归遍历
                String companyId = sysDepartmentService.getCompanyIdByUserKey(dbSysUser.getUserKey());
                SysCompany sysCompany = sysCompanyMapper.selectByPrimaryKey(companyId);
                sysCompany.setUserKey(dbSysUser.getUserKey());
                sysCompanyList = Lists.newArrayList();
                sysCompanyList.add(sysCompany);
            }

            String companyId = "";
            String companyUrl = "";

            // 公司List
            List<CompanyEntity> companyList = Lists.newArrayList();
            for (SysCompany sysCompany : sysCompanyList) {
                CompanyEntity companyEntity = new CompanyEntity();
                companyEntity.setUserKey(sysCompany.getUserKey());
                companyEntity.setCompanyId(sysCompany.getCompanyId());
                companyEntity.setCompanyName(sysCompany.getCompanyName());
                // 设置列表中当前选中公司的标识
                if (StrUtil.equals(dbSysUser.getUserKey(), sysCompany.getUserKey())) {
                    companyEntity.setCompanySelectFlag(1);
                    companyId = sysCompany.getCompanyId();
                    companyUrl = sysCompany.getCompanyUrl();
                }
                // 此处应该返回错误信息
                companyList.add(companyEntity);
            }
            sysUserWebEntity.setCompanyList(companyList);
            if (companyList == null || companyList.size() == 0) {
                return repEntity.error("sys.role.exception");
            }

            // 获取所有部门返回给前台，前台根据自己的页面功能，选择业务部门
            List<SysDepartment> sysDepartmentList = sysDepartmentService
                    .getSysDepartmentByUserkey(dbSysUser.getUserKey());
            if (sysDepartmentList != null && sysDepartmentList.size() > 0) {
                List<DepartmentEntity> departmentList = Lists.newArrayList();
                for (SysDepartment sysDepartment : sysDepartmentList) {
                    DepartmentEntity departmentEntity = new DepartmentEntity();
                    departmentEntity.setDepartmentId(sysDepartment.getDepartmentId());
                    departmentEntity.setDepartmentName(sysDepartment.getDepartmentName());
                    departmentList.add(departmentEntity);
                }
                sysUserWebEntity.setDepartmentList(departmentList);
            } else {
                return repEntity.error("sys.role.exception");
            }

            // 如果没有对应公司，新增人员公司关系，公司列表中，标识当前公司返回给前台
            if (sysUserList == null || sysUserList.size() == 0) {
                SysUserCompany sysUserCompany = new SysUserCompany();
                sysUserCompany.setUserKey(dbSysUser.getUserKey());
                sysUserCompany.setCompanyId(companyId);
                sysUserCompanyService.addSysUserCompanyCommon(sysUserCompany);
            }
            tokenEntity.setCompanyId(companyId);
            tokenEntity.setCompanyUrl(companyUrl);
            // 通过userId获取IP分发信息，放到后面的信息里面，通过userKey关联公司关系表和公司表
        } else {
            // 默认第一个部门
            SysProject sysProjectSelect = new SysProject();
            sysProjectSelect.setUserKey(dbSysUser.getUserKey());
            List<SysProject> sysProjectList = sysProjectMapper.selectBySysProjectListByUserKey(sysProjectSelect);
            if(StrUtil.equals("4", dbSysUser.getExt1())
                    && sysProjectList != null && sysProjectList.size()>0) {
//                // 公司List
//                List<CompanyEntity> companyList = Lists.newArrayList();
//                for (SysUserDepartment dbSysUserDepartment : sysUserDepartmentList) {
////                    CompanyEntity companyEntity = new CompanyEntity();
////                    companyEntity.setUserKey(sysCompany.getUserKey());
////                    companyEntity.setCompanyId(sysCompany.getCompanyId());
////                    companyEntity.setCompanyName(sysCompany.getCompanyName());
////                    // 设置列表中当前选中公司的标识
////                    companyEntity.setCompanySelectFlag(1);
////                    // 此处应该返回错误信息
////                    companyList.add(companyEntity);
//                }
            } else {
                // 部门ID如果与公司主键相等说明当前的部门就是公司，如果不是递归遍历
                String companyId = sysDepartmentService.getCompanyIdByUserKey(dbSysUser.getUserKey());
                SysCompany sysCompanySelect = new SysCompany();
                if (!StrUtil.equals("1", dbSysUser.getExt1()) && StrUtil.isNotEmpty(companyId)) {
                    sysCompanySelect.setCompanyId(companyId);
                }
                List<SysCompany> sysCompanyList = sysCompanyMapper.selectBySysCompanyList(sysCompanySelect);
                // 公司List
                List<CompanyEntity> companyList = Lists.newArrayList();
                for (SysCompany sysCompany : sysCompanyList) {
                    CompanyEntity companyEntity = new CompanyEntity();
                    companyEntity.setUserKey(sysCompany.getUserKey());
                    companyEntity.setCompanyId(sysCompany.getCompanyId());
                    companyEntity.setCompanyName(sysCompany.getCompanyName());
                    // 设置列表中当前选中公司的标识
                    companyEntity.setCompanySelectFlag(1);
                    // 此处应该返回错误信息
                    companyList.add(companyEntity);
                }
                sysUserWebEntity.setCompanyList(companyList);
                // 缓存增加公司ID
                tokenEntity.setCompanyId(companyId);
                
                // 获取所有部门返回给前台，前台根据自己的页面功能，选择业务部门
                List<SysDepartment> sysDepartmentList = sysDepartmentService.getSysDepartmentByUserkey(dbSysUser.getUserKey());
                if (sysDepartmentList != null && sysDepartmentList.size() > 0) {
                    List<DepartmentEntity> departmentList = Lists.newArrayList();
                    for (SysDepartment sysDepartment : sysDepartmentList) {
                        DepartmentEntity departmentEntity = new DepartmentEntity();
                        departmentEntity.setDepartmentId(sysDepartment.getDepartmentId());
                        departmentEntity.setDepartmentName(sysDepartment.getDepartmentName());
                        departmentList.add(departmentEntity);
                    }
                    sysUserWebEntity.setDepartmentList(departmentList);
                }
            
            }
        }

        // 用户菜单获取
        String roleUserKey = dbSysUser.getUserKey();
        if (StrUtil.equals("admin", dbSysUser.getUserId())) {
            roleUserKey = dbSysUser.getUserId();
        }
        JSONObject treeJSONObject = sysMenuService.getSysMenuAllTreeByRole(roleUserKey);
        if (treeJSONObject != null) {
            JSONObject menuTree = treeJSONObject.getJSONObject("menuTop");
            menuTree.set("children", treeJSONObject.getJSONArray("menuTree"));
            sysUserWebEntity.setMenuTree(menuTree);
            tokenEntity.setpGIdList(treeJSONObject.getJSONArray("pGIdList"));
        }

        // 缓存对象添加
        tokenEntity.setSysUserWebObject(sysUserWebEntity);
        // 如果是默认密码是，需要提示修改密码
        boolean isDefaultPasswordReset = false;
        if (apih5Properties.isDefaultPasswordReset() && StrUtil.equals("1", loginType)) {
            String userPwd = sysUser.getUserPwd();
            String defaultPwd = SecureUtil.md5(apih5Properties.getDefaultPassword() + apih5Properties.getMd5Salt());
            if (StrUtil.equals(userPwd, defaultPwd)) {
                isDefaultPasswordReset = true;
            }
        }

        // 传给web
        infoMap.put("token", token);
        infoMap.put("userInfo", sysUserWebEntity);
        infoMap.put("defaultPasswordReset", isDefaultPasswordReset);

        // 向缓存中插入用户token,token的key为userToken_用户key
        String userEhCacheKey = dbSysUser.getUserKey() + "-" + sysUser.getAccountId();
        sysRedisCacheService.putUserTokenRedis(userEhCacheKey, tokenEntity);

        LoggerUtils.printDebugLogger(logger, "login tokenEntity=" + tokenEntity.getUserId());

        return repEntity.ok(infoMap);
        // 切换公司的时候，需要token获取userKey，删除公司关联表，然后置换token，公司关系表新增这个数据。判断公司和userKey是否统一
    }

    @Override
    public ResponseEntity getCacheUserInfo(String userEhCacheKey) {
//        TokenEntity tokenEntity = null;
//        CacheManager cacheManager = CacheManager.create();
//        // 2. 获取缓存对象
//        Cache cache = cacheManager.getCache("userTokenEhCache");
//        if(cache != null) {
//            Element value = cache.get(userEhCacheKey);
//            tokenEntity = (TokenEntity) value.getObjectValue();
//        }
        TokenEntity tokenEntity = sysRedisCacheService.getUserTokenRedis(userEhCacheKey);
        return repEntity.ok(tokenEntity);
    }

    /**
     * 缓存登录模式，从主服务器拿回缓存，并且put
     * 
     * @param accountId
     * @param userKey
     * @param jsonObject login返回的data数据
     * @return false:缓存存储失败
     */
    @Override
    public boolean copyCacheLogin(String accountId, String userKey, JSONObject jsonObject) {
        String userEhCacheKey = userKey + "-" + accountId;

        // 通过接口获取缓存
        String urlApi = config.getProperty("main.cache.url", "") + "user/getCacheUserInfo/" + userEhCacheKey;
        String result = HttpUtil.sendPostToken(urlApi, "", jsonObject.getStr("token"));
        JSONObject jsonObjectResult = new JSONObject(result);
        if (jsonObjectResult.getBool("success")) {
            TokenEntity tokenEntity = jsonObjectResult.getBean("data", TokenEntity.class);
            if (tokenEntity == null) {
                return false;
            }
            // 缓存put
            sysRedisCacheService.putUserTokenRedis(userEhCacheKey, tokenEntity);
            return true;
        }
        return false;
    }

    // 注册/登录/改密 获取手机短信验证码
    @Override
    public ResponseEntity getMobileSmsCaptcha(SmsEntity smsEntity) {
        String smsTxt = FileUtil.readUtf8String(Thread.currentThread().
                getContextClassLoader().getResource("") + "config/sms.txt");
        if(StrUtil.isEmpty(smsTxt)) {
            return repEntity.layerMessage("no", "短信配置文件错误！");
        } 
        smsTxt = smsTxt.replaceAll("\r\n", "");
        JSONObject jsonObject = new JSONObject(smsTxt);

        // 阿里云短信验证：aly00注册\aly01:登录\aly02:登录\aly03:通用操作
        if(StrUtil.equals("aly00", smsEntity.getTemplateType())
                || StrUtil.equals("aly01", smsEntity.getTemplateType())
                || StrUtil.equals("aly02", smsEntity.getTemplateType())
                || StrUtil.equals("aly03", smsEntity.getTemplateType())) {
            // 获取验证码
            String captcha = SmsUtils.generateCaptcha(4);
            // 将验证码存入缓存
            SmsUtils.saveCaptchaEhCache(smsEntity.getMobile(), captcha);
            // 设置模板参数
            smsEntity.setTemplateParam("{\"code\":\"" + captcha + "\"}");
            // 从模板获取数据
            jsonObject = jsonObject.getJSONObject(smsEntity.getTemplateType());
            smsEntity.setAccessKeyId(jsonObject.getStr("accessKeyId"));
            smsEntity.setAccessKeySecret(jsonObject.getStr("accessKeySecret"));
            smsEntity.setSignName(jsonObject.getStr("signName"));
            smsEntity.setTemplateCode(jsonObject.getStr("templateCode"));
        } else {
            
        }

        try {
            SendSmsResponse smsResult = SmsUtils.sendSms(smsEntity);
            if ("OK".equals(smsResult.getCode())) {
                return repEntity.ok("发送成功!");
            } else {
                return repEntity.layerMessage("no", "短信获取失败");
            }
        } catch (ClientException e) {
            return repEntity.error("sys.sms.send.error");
        }
    }

    @Override
    public ResponseEntity getSysUserList(OADepartment oaDepartment) {
        SysUser sysUser = new SysUser();
        sysUser.setDepartmentId(oaDepartment.getOrgId());
        List<SysUser> list = userMapper.selectBySysUserListByDepartment(sysUser);
        List<OAMember> listMember = new ArrayList<OAMember>();
        for (int i = 0; i < list.size(); i++) {
            SysUser dbSysUser = list.get(i);
            OAMember member = new OAMember();
            member.setUserid(dbSysUser.getUserId());
            member.setName(dbSysUser.getRealName());
            member.setOrgId(dbSysUser.getDepartmentId());
            listMember.add(member);
        }
        return repEntity.okList(listMember, listMember.size());
    }

    @Override
    public ResponseEntity apiSysUserList(OADepartment oaDepartment) {
        SysUser sysUser = new SysUser();
        sysUser.setDepartmentId(oaDepartment.getOrgId());
        List<SysUser> list = userMapper.selectBySysUserListByDepartment(sysUser);
        List<OAMember> listMember = new ArrayList<OAMember>();
        for (int i = 0; i < list.size(); i++) {
            SysUser dbSysUser = list.get(i);
            OAMember member = new OAMember();
            member.setUserid(dbSysUser.getUserId());
            member.setName(dbSysUser.getRealName());
            member.setOrgId(dbSysUser.getDepartmentId());
            if (dbSysUser.getUserId().length() == 11 || dbSysUser.getUserId().indexOf("haiwei") >= 0) {
                listMember.add(member);
            }
        }
        return repEntity.okList(listMember, listMember.size());
    }

    // 共通修改方法
    @Override
    public int updateUserCommon(SysUser sysUser) {
        int flag = 0;
        if (sysUser != null && StrUtil.isNotEmpty(sysUser.getUserKey())) {
            // 共通
            sysUser.setModifyTime(new Date());
            sysUser.setModifyUser(sysUser.getUserKey());
            flag = userMapper.updateByPrimaryKeySelective(sysUser);
        }
        // 失败
        return flag;
    }

    // 获取该部门下所有人员集合
    @Override
    public List<SysUser> getMemberListByDepartmentId(SysUser sysUser) {
        List<SysUser> userList = new ArrayList<SysUser>();
        if (sysUser != null && StringUtil.isNotEmpty(sysUser.getDepartmentId())) {
            userList = userMapper.getMemberListByDepartmentId(sysUser);
        }
        return userList;
    }

    @Override
    public int addUserCommon(SysUser sysUser) {
        // account中查找企业信息
//        BaseAccount baseAccount = baseAccountMapper.selectByPrimaryKey(sysUser.getAccountId());
        BaseAccount baseAccount = baseAccountService.getBaseAccount(sysUser.getAccountId());
        sysUser.setAccountCorpId(baseAccount.getAccountCorpId());
        sysUser.setAccountAppType(baseAccount.getAccountAppType());
        // 写数据
        String userPwd = SecureUtil.md5(sysUser.getUserPwd() + apih5Properties.getMd5Salt());
        sysUser.setUserPwd(userPwd);
        sysUser.setCreateUserInfo(sysUser.getUserKey(), sysUser.getRealName());
        return userMapper.insert(sysUser);
    }

    // 获取该公司所有总工
    @Override
    public List<SysUser> getUserListByRoleAndCompanyId(SysUser sysUser) {
        List<SysUser> userList = new ArrayList<SysUser>();
        if (sysUser != null && StringUtil.isNotEmpty(sysUser.getCompanyId())) {
            userList = userMapper.getUserListByRoleAndCompanyId(sysUser);
        }
        return userList;
    }
    
    // 获取该公司所有总工
    @Override
    public List<SysUser> getUserListByRoleAndCompanyIdXMJX(SysUser sysUser) {
        List<SysUser> userList = new ArrayList<SysUser>();
        if (sysUser != null && StringUtil.isNotEmpty(sysUser.getCompanyId())) {
            userList = userMapper.getUserListByRoleAndCompanyId(sysUser);
            if(userList.size() > 0) {
                for(SysUser dbSysUser : userList) {
                    // 根据userKey和deptId获取人员评分领导
                    SysUser search = new SysUser();
                    search.setDepartmentId(sysUser.getCompanyId());
                    search.setUserKey(dbSysUser.getUserKey());
                    SysUser dbScoreLeader = sysUserMapper.getScoringLeaderByDepartmentId(search);
                    dbSysUser.setExt3(dbScoreLeader != null ? dbScoreLeader.getExt3() : "");
                    dbSysUser.setExt4(dbScoreLeader != null ? dbScoreLeader.getExt4() : "");
                }
            }
         }
         return userList;
    }

    @Override
    public int batchDeleteSysUserCommon(List<SysUser> sysUserList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (sysUserList != null && sysUserList.size() > 0) {
            SysUser sysUserInfo = new SysUser();
            sysUserInfo.setModifyUserInfo(userKey, realName);
            flag = userMapper.batchDeleteSysUser(sysUserList, sysUserInfo);
        }
        return flag;
    }

    // 应该是不在使用的方法
    @Override
    public int bathInsertUserCommon(List<SysUser> sysUserList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        if (sysUserList != null && sysUserList.size() > 0) {
            for (SysUser user : sysUserList) {
                // account中查找企业信息
//                BaseAccount baseAccount = baseAccountMapper.selectByPrimaryKey(user.getAccountId());
                BaseAccount baseAccount = baseAccountService.getBaseAccount(user.getAccountId());
                if (baseAccount == null) {
                    return 0;
                }
                user.setAccountCorpId(baseAccount.getAccountCorpId());
                user.setAccountAppType(baseAccount.getAccountAppType());
                // 写数据
                String userPwd = SecureUtil.md5(user.getUserPwd() + apih5Properties.getMd5Salt());
                user.setUserPwd(userPwd);
                user.setCreateUserInfo(userKey, realName);
            }
        }
        return userMapper.bathInsertUserCommon(sysUserList);
    }

    @Override
    public List<SysUser> selectBySysUserListByDepartment(SysUser sysUser) {
        return userMapper.selectBySysUserListByDepartment(sysUser);
    }
    
    /**
     * 获取书记、经理的岗位暂时数据库定义写死
     */
    @Override
    public List<SysUser> selectBySysUserByJobType(SysUser sysUser) {
        SysUser sysUserSelect = new SysUser();
        sysUserSelect.setDepartmentPath(sysUser.getDepartmentId());
        List<SysUser> sysUserList = userMapper.selectByUserListByProJobType(sysUserSelect);
        return sysUserList;
    }

    @Override
    public List<SysUser> getSysUserListByUserKeyList(List sysUserList) {
        return userMapper.getSysUserListByUserKeyList(sysUserList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity moveUpdateSysUser(SysUser sysUser) throws Exception {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        // 要移动的人员id(一定存在)
        String moveUserKey = sysUser.getMoveUserKey();
        // 当前页面第一个节点的userKey(一定存在)
        String firstUserKey = sysUser.getFirstUserKey();
        // 当前页面最后一个节点的userKey(一定存在)
        String lastUserKey = sysUser.getLastUserKey();
        // 移动后人员的同级上面的节点id(前台不一定存在)
        String beforeUserKey = sysUser.getBeforeUserKey();
        // 移动后人员的同级下面的节点id(前台不一定存在)
        String afterUserKey = sysUser.getAfterUserKey();
        // 要移动节点对象(一定存在)
        SysUser moveSysUser = sysUserMapper.selectByPrimaryKey(moveUserKey);
        if (moveSysUser == null || StrUtil.isEmpty(firstUserKey) || StrUtil.isEmpty(lastUserKey)) {
            return repEntity.layerMessage("no", "传入参数有误!");
        }
        // 获取当前部门下所有人员的集合(正序排列)
        LinkedList<SysUser> sysUserList = new LinkedList<SysUser>(userMapper.getUserListByRoleAndCompanyId(sysUser));
        // 因为要移动的节点本来就在当前集合下,移动前需先将其移除
        // sysUserList.remove(moveSysUser);
        for (int i = 0; i < sysUserList.size(); i++) {
            // 从数据集合中先移出当前移动节点
            if (StrUtil.equals(moveUserKey, sysUserList.get(i).getUserKey())) {
                sysUserList.remove(i);
                break;
            }
        }
        moveSysUser.setModifyUserInfo(userKey, realName);
        // 获取真正的新位置的前一个节点和新位置的后一个节点对象
        // 定义实际新位置前一个节点(不一定存在)
        SysUser realBeforeSysUser = null;
        // 如果前台传过来beforeUserKey不存在,则需要找到前一个页的最后一个节点(不一定存在)
        if (StrUtil.isEmpty(beforeUserKey)) {
            for (SysUser dbSysUser : sysUserList) {
                if (StrUtil.equals(firstUserKey, dbSysUser.getUserKey())) {
                    break;
                } else {
                    realBeforeSysUser = dbSysUser;
                }
            }
        }
        // 如果存在,则直接获取
        else {
            realBeforeSysUser = sysUserMapper.selectByPrimaryKey(beforeUserKey);
        }
        // 定义实际新位置后一个节点(不一定存在)
        SysUser realAfterSysUser = null;
        // 如果前台传过来afterUserKey不存在,则需要找到后一个页的第一个节点(不一定存在)
        if (StrUtil.isEmpty(afterUserKey)) {
            for (int i = sysUserList.size() - 1; i >= 0; i--) {
                if (StrUtil.equals(lastUserKey, sysUserList.get(i).getUserKey())) {
                    break;
                } else {
                    realAfterSysUser = sysUserList.get(i);
                }
            }
        }
        // 如果存在,则直接获取
        else {
            realAfterSysUser = sysUserMapper.selectByPrimaryKey(afterUserKey);
        }
        // 处理要移动节点和移动后节点的同级兄弟节点的sort(分为以下三种情况)
        // befor、after同时存在时、说明移动到了中间位置
        if (realBeforeSysUser != null && realAfterSysUser != null) {
            for (int i = 0; i < sysUserList.size(); i++) {
                sysUserList.get(i).setSort(i);
                if (StrUtil.equals(sysUserList.get(i).getUserKey(), realBeforeSysUser.getUserKey())) {
                    // 将要移动的节点加入集合
                    sysUserList.add(i + 1, moveSysUser);
                }
            }
            // 将节点放在中间每次移动都将批量修改集合
            flag = sysUserMapper.batchUpdateSysUser(sysUserList);
        }
        // 只有befor存在时、说明移动到了最后面，移动后的节点后面没有内容
        else if (realBeforeSysUser != null && realAfterSysUser == null) {
            moveSysUser.setSort(realBeforeSysUser.getSort() + 1);
            flag = sysUserMapper.updateByPrimaryKey(moveSysUser);
        }
        // 只有after存在时、说明移动到了最前面面，移动后的节点前面没有内容
        else if (realBeforeSysUser == null && realAfterSysUser != null) {
            // 将要移动的节点加入集合
            sysUserList.add(0, moveSysUser);
            for (int i = 0; i < sysUserList.size(); i++) {
                sysUserList.get(i).setSort(i);
            }
            // 将节点移到最前面每次都将批量修改集合
            flag = sysUserMapper.batchUpdateSysUser(sysUserList);
        }
        // 失败
        if (flag == 0) {
            throw new Apih5Exception("人员移动失败!");
        } else {
            return repEntity.ok("sys.data.update", sysUser);
        }
    }

    @Override
    public ResponseEntity updateSysUserPhoneNumber(SysUser sysUser) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String accountId = TokenUtils.getAccountId(request);
        String userKey = TokenUtils.getUserKey(request);
        String userName = TokenUtils.getRealName(request);
//        if (StrUtil.isEmpty(sysUser.getUserKey())) {
//            return repEntity.layerMessage("no", "数据错误！");
//        }
        // 手机号合法检查
        if (!Validator.isNumber(sysUser.getMobile())) {
            return repEntity.layerMessage("no", "手机号不合法，请您检查。");
        }
        // 验证手机号验证码是否过期
        String code = SmsUtils.checkCaptchaEhCache(sysUser.getMobile(), sysUser.getCaptcha());
        if (!StrUtil.equals("ok", code)) {
            return repEntity.error(code);
        }
        int flag = 0;
        SysUser dbSysUser = sysUserMapper.selectByPrimaryKey(userKey);
        if (dbSysUser != null) {
            if(StrUtil.isNotEmpty(sysUser.getRealName())) {
                dbSysUser.setRealName(sysUser.getRealName());
            }
            dbSysUser.setMobile(sysUser.getMobile());
            // 共通
            dbSysUser.setModifyUserInfo(userKey, userName);
            flag = sysUserMapper.updateByPrimaryKey(dbSysUser);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            // 缓存key
            String userEhCacheKey = dbSysUser.getUserKey() + "-" + accountId;
            // 得到token的值
            TokenEntity tokenEntity = sysRedisCacheService.getUserTokenRedis(userEhCacheKey);
            if (tokenEntity != null) {
                tokenEntity.setMobile(sysUser.getMobile());
                tokenEntity.setRealName(sysUser.getRealName());
                JSONObject jsonObjectWeb = new JSONObject(tokenEntity.getSysUserWebObject());
                if(StrUtil.isNotEmpty(sysUser.getRealName())) {
                    jsonObjectWeb.set("realName", sysUser.getRealName());
                }
                jsonObjectWeb.set("mobile", sysUser.getMobile());
                tokenEntity.setSysUserWebObject(jsonObjectWeb);
                sysRedisCacheService.putUserTokenRedis(userEhCacheKey, tokenEntity);
            }
            return repEntity.ok("sys.data.update", sysUser);
        }
    }

    @Override
    public ResponseEntity updateSysUserScoringLeader(SysUser sysUser) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String userName = TokenUtils.getRealName(request);
        int flag = 0;
        if(sysUser != null && sysUser.getUserArray() != null
                           && sysUser.getUserArray().size() > 0
                           && sysUser.getUserList() != null
                           && sysUser.getUserList().size() > 0) {
            SysUser user = new SysUser();
            user.setExt3(sysUser.getUserArray().getJSONObject(0).getStr("value"));
            user.setExt4(sysUser.getUserArray().getJSONObject(0).getStr("label"));
            user.setUserList(sysUser.getUserList());
            flag = sysUserMapper.updateSysUserScoringLeader(user);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        } else {
            return repEntity.ok("sys.data.update", sysUser);
        }
    }

    @Override
    public ResponseEntity getSysUserListByBgXMJX(SysUser sysUser) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String companyId = TokenUtils.getCompanyId(request);
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        if (sysUser == null) {
            sysUser = new SysUser();
        }
        String departmentId = sysUser.getDepartmentId();
        // 分页查询
        PageHelper.startPage(sysUser.getPage(), sysUser.getLimit());
        // 根据设置【只显示对应部门下面的人员】
        boolean isSearchDepartmentId = publicConfig.getBooleanProperty("isSearchDepartmentId", false);
        if (isSearchDepartmentId) {
            // 暂时保留
            if (StrUtil.isNotEmpty(sysUser.getSearch())) {
                sysUser.setCompanyId(sysUser.getDepartmentId());
                sysUser.setDepartmentId("");
            }

            // 检索条件不为空时，设置有效值的部门id去检索
            if(StrUtil.isNotEmpty(sysUser.getRealName())
                    || StrUtil.isNotEmpty(sysUser.getUserId())
                    || StrUtil.isNotEmpty(sysUser.getMobile())) {
                if(StrUtil.equals("admin", userId) || StrUtil.equals("1", ext1)) {
//                    sysUser.setCompanyId(sysUser.getDepartmentId());
                    sysUser.setDepartmentId("");
                } else {
                    sysUser.setDepartmentId("");
                    // DepartmentPath的查询条件
                    sysUser.setCompanyId(sysUser.getRootId());
                }
            }
        } else {
            // 检索所有部门下数据，暂时用这个方法，后面看看有没有其他方法要加进来可以替换掉
            if(!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)
                    && (StrUtil.equals("9999999999", sysUser.getDepartmentId()) || StrUtil.equals("0", sysUser.getDepartmentId())) && StrUtil.isNotEmpty(companyId)) {
                sysUser.setCompanyId(companyId);
            } else {
                sysUser.setCompanyId(sysUser.getDepartmentId());
            }
            sysUser.setDepartmentId("");
        }

        List<SysUser> sysUserList = userMapper.getUserListByRoleAndCompanyId(sysUser);
        // 根据人员吧该人员所有部门筛选出来
        for (SysUser dbSysUser : sysUserList) {
            SysDepartment sysDepartment = new SysDepartment();
            sysDepartment.setUserKey(dbSysUser.getUserKey());
            List<SysDepartment> sysDepartmentList = sysDepartmentMapper.getSysDepartmentListByUserkey(sysDepartment);
            // 转labele和value模式
            JSONArray sysDepartmentJSONArray = new JSONArray();
            List<String> sysDepartmentShowList = Lists.newArrayList();
            for (SysDepartment sysDepartmentDb : sysDepartmentList) {
                if (sysDepartmentDb.getDepartmentPathName().indexOf(",") > 0) {
                    sysDepartmentShowList.add(sysDepartmentDb.getDepartmentPathName().replaceAll(",", "/"));
                } else {
                    sysDepartmentShowList.add(sysDepartmentDb.getDepartmentPathName());
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("value", sysDepartmentDb.getDepartmentId());
                jsonObject.set("label", sysDepartmentDb.getDepartmentName());
                jsonObject.set("valuePid", sysDepartmentDb.getDepartmentParentId());
                jsonObject.set("type", "1");
                sysDepartmentJSONArray.add(jsonObject);
            }
            dbSysUser.setSysDepartmentList(sysDepartmentJSONArray);
            dbSysUser.setSysDepartmentShowList(sysDepartmentShowList);
            dbSysUser.setUserPwd("");
            // 根据userKey和deptId获取人员评分领导
            if (StrUtil.isNotEmpty(departmentId)) {
                // 根据departmentId获取projectId
                SysDepartment dbSysDepartment = sysDepartmentMapper.selectByPrimaryKey(departmentId);
                if(dbSysDepartment != null
                        && StrUtil.isNotEmpty(dbSysDepartment.getProjectId())) {
                    SysUser search = new SysUser();
                    search.setDepartmentId(dbSysDepartment.getProjectId());
                    search.setUserKey(dbSysUser.getUserKey());
                    SysUser dbScoreLeader = sysUserMapper.getScoringLeaderByDepartmentId(search);
                    dbSysUser.setExt3(dbScoreLeader != null ? dbScoreLeader.getExt3() : "");
                    dbSysUser.setExt4(dbScoreLeader != null ? dbScoreLeader.getExt4() : "");
                }
           }
        }
        // 得到分页信息
        PageInfo<SysUser> p = new PageInfo<>(sysUserList);
        return repEntity.okList(sysUserList, p.getTotal());
    }
}
