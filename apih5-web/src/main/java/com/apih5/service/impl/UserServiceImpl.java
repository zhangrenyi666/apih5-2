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
            return repEntity.layerMessage("no", "????????????id????????????");
        }

        // 1????????????????????????????????????
        SysUserCompany sysUserCompany = new SysUserCompany();
        sysUserCompany.setUserKey(userKey);
        List<SysUserCompany> sysUserCompanyList = sysUserCompanyMapper.selectBySysUserCompanyList(sysUserCompany);
        if (sysUserCompanyList != null && sysUserCompanyList.size() > 0) {
            sysUserCompanyMapper.batchDeleteUpdateSysUserCompany(sysUserCompanyList);
        }

        // 2??????????????????????????????userInfo
        SysUser sysUserSelect = new SysUser();
        sysUserSelect.setCompanyId(changeCompanyId);
        sysUserSelect.setMobile(mobile);
        List<SysUser> sysUserList = userMapper.getSysUserListByCompanyMobile(sysUserSelect);
        if (sysUserList != null && sysUserList.size() > 0) {
            changeUserKey = sysUserList.get(0).getUserKey();
            changeUserId = sysUserList.get(0).getUserId();
            changeUserPwd = sysUserList.get(0).getUserPwd();
        }

        // 3?????????????????????
        sysUserCompany = new SysUserCompany();
        sysUserCompany.setUserCompanyId(UuidUtil.generate());
        sysUserCompany.setUserKey(changeUserKey);
        sysUserCompany.setCompanyId(changeCompanyId);
        sysUserCompany.setCreateUserInfo(userKey, realName);
        int flag = sysUserCompanyMapper.insert(sysUserCompany);

        // 3????????????????????????????????????????????????token
        JSONObject jsonObject = new JSONObject();
        jsonObject.accumulate("accountId", accountId);
        jsonObject.accumulate("loginType", 1);
        jsonObject.accumulate("userId", changeUserId);
        jsonObject.accumulate("userPwd", apih5Properties.getDefaultPassword());
        // login??????
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
        // ??????web
        infoMap.put("token", changeToken);
        infoMap.put("userInfo", new JSONObject(changeSysUserWebEntity));
        infoMap.put("defaultPasswordReset", false);

        // 4???????????????????????????token
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
            return repEntity.layerMessage("no", "????????????????????????");
        }
        // ??????????????????????????????????????????
        String checkPassPattern = "^(?=.*[0-9])(?=.*[a-zA-Z])([A-Z])(.{7,20})$";
        if (!userPwdNew.matches(checkPassPattern)) {
            return repEntity.layerMessage("no", "???????????????8??????????????????[????????????????????????????????????]????????????????????????");
        }
        if (StrUtil.isEmpty(userPwd)) {
            return repEntity.layerMessage("no", "????????????????????????");
        }
        if (StrUtil.isEmpty(userPwdNew)) {
            return repEntity.layerMessage("no", "?????????????????????????????????");
        }
        if (!StrUtil.equals(userPwd, userPwdNew)) {
            return repEntity.layerMessage("no", "?????????????????????????????????????????????");
        }

        // ??????????????????
        SysUser sysUserDb = userMapper.selectByPrimaryKey(userKey);
        if (sysUserDb == null) {
            return repEntity.layerMessage("no", "?????????????????????");
        } else {
            String userPwdOldMd5 = SecureUtil.md5(userPwdOld + apih5Properties.getMd5Salt());
            if (!StrUtil.equals(userPwdOldMd5, sysUserDb.getUserPwd())) {
                return repEntity.layerMessage("no", "?????????????????????");
            }
        }
        // ????????????
        SysUser sysUserUpdate = new SysUser();
        sysUserUpdate.setUserKey(userKey);
        sysUserUpdate.setUserPwd(SecureUtil.md5(userPwd + apih5Properties.getMd5Salt()));
        int flag = this.updateUserCommon(sysUserUpdate);
        if (flag == 0) {
            return repEntity.layerMessage("no", "?????????????????????");
        } else {
            return repEntity.ok("?????????????????????");
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
        // ??????????????????????????????????????????
        String checkPassPattern = "^(?=.*[0-9])(?=.*[a-zA-Z])([A-Z])(.{7,20})$";
        if (!userPwdNew.matches(checkPassPattern)) {
            return repEntity.layerMessage("no", "???????????????8??????????????????[????????????????????????????????????]????????????????????????");
        }
        if (StrUtil.isEmpty(userPwd)) {
            return repEntity.layerMessage("no", "????????????????????????");
        }
        if (StrUtil.isEmpty(userPwdNew)) {
            return repEntity.layerMessage("no", "?????????????????????????????????");
        }
        if (!StrUtil.equals(userPwd, userPwdNew)) {
            return repEntity.layerMessage("no", "?????????????????????????????????????????????");
        }

        // ??????????????????
        SysUser sysUserDb = userMapper.selectByPrimaryKey(userKey);
        if (sysUserDb == null) {
            return repEntity.layerMessage("no", "?????????????????????");
        }
        // ????????????
        SysUser sysUserUpdate = new SysUser();
        sysUserUpdate.setUserKey(userKey);
        sysUserUpdate.setUserPwd(SecureUtil.md5(userPwd + apih5Properties.getMd5Salt()));
        int flag = this.updateUserCommon(sysUserUpdate);
        if (flag == 0) {
            return repEntity.layerMessage("no", "?????????????????????");
        } else {
            return repEntity.ok("?????????????????????");
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
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            if (isAdmin) {
                return repEntity.ok("????????????????????????????????????????????????????????????");
            } else {
                return repEntity.ok("?????????????????????]");
            }
        }
    }

    /**
     * ????????????
     * 
     */
    @Override
    public ResponseEntity refreshAccessToken(TokenEntity tokenEntity) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        if (tokenEntity == null || StrUtil.isEmpty(tokenEntity.getToken())
                || StrUtil.equals("null", tokenEntity.getToken())) {
            return repEntity.error("sys.error.token");
        }
        // token??????
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

        // token???????????????
        boolean tokenInvalidFlag = false;
        String checkToken = TokenUtils.createUserToken(accountId, userKey, Long.parseLong(timeStamp),
                apih5Properties.getUserTokenKey(), newIp);
        if (!StrUtil.equals(checkToken, token)) {
            tokenInvalidFlag = true;
        } else {
            // ??????????????????tokenEntity??????
//            tokenEntity = EhCacheCacheHandler.getUserTokenEhCache(userEhCacheKey);
            tokenEntity = sysRedisCacheService.getUserTokenRedis(userEhCacheKey);
            // ??????token????????????
            if (ObjectUtil.isNull(tokenEntity)) {
                // LoggerUtils.printLogger(logger, "??????token??????");
                return repEntity.error("sys.error.token.cache");
            }
        }
        // ??????token?????????
        if (tokenInvalidFlag) {
            LoggerUtils.printLogger(logger, "token?????????");
            return repEntity.error("sys.error.token");
        }

        // ??????token??????
        String newToken = TokenUtils.createUserToken(accountId, userKey, 0, apih5Properties.getUserTokenKey(), newIp);
        tokenEntity.setToken(newToken);
        // ????????????
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
           // ??????????????????user
        SysUser sysUserProSelect = new SysUser();
        sysUserProSelect.setDepartmentId(sysUser.getDepartmentId());
        // ????????????
        PageHelper.startPage(sysUser.getPage(), sysUser.getLimit());
        // ??????????????????????????????????????????????????????
        boolean isSearchDepartmentId = publicConfig.getBooleanProperty("isSearchDepartmentId", false);
        if (isSearchDepartmentId) {
            // ????????????
            if (StrUtil.isNotEmpty(sysUser.getSearch())) {
                sysUser.setCompanyId(sysUser.getDepartmentId());
                sysUser.setDepartmentId("");
            }

            // ???????????????????????????????????????????????????id?????????
            if(StrUtil.isNotEmpty(sysUser.getRealName())
                    || StrUtil.isNotEmpty(sysUser.getUserId())
                    || StrUtil.isNotEmpty(sysUser.getMobile())) {
                if(StrUtil.equals("admin", userId) || StrUtil.equals("1", ext1)) {
//                    sysUser.setCompanyId(sysUser.getDepartmentId());
                    sysUser.setDepartmentId("");
                } else {
                    sysUser.setDepartmentId("");
                    // DepartmentPath???????????????
                    sysUser.setCompanyId(sysUser.getRootId());
                }
            }
        } else {
            // ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            if(!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)
                    && (StrUtil.equals("9999999999", sysUser.getDepartmentId()) || StrUtil.equals("0", sysUser.getDepartmentId())) && StrUtil.isNotEmpty(companyId)) {
                sysUser.setCompanyId(companyId);
            } else {
                sysUser.setCompanyId(sysUser.getDepartmentId());
            }
            sysUser.setDepartmentId("");
        }

        List<SysUser> sysUserList = userMapper.getUserListByRoleAndCompanyId(sysUser);
        // ???????????????????????????id??????????????????????????????????????????
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
        
        // ????????????????????????????????????????????????
        for (SysUser dbSysUser : sysUserList) {
            // ?????????
            SysDepartment sysDepartment = new SysDepartment();
            sysDepartment.setUserKey(dbSysUser.getUserKey());
            List<SysDepartment> sysDepartmentList = sysDepartmentMapper.getSysDepartmentListByUserkey(sysDepartment);
            // ???labele???value??????
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
            
            // ?????????
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
        // ??????????????????
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
        // ????????????
        jsonObject.set("realName", dbSysUser.getRealName());
        // ??????
        jsonObject.set("nickname", dbSysUser.getNickname());
        // ??????
        jsonObject.set("mobile", dbSysUser.getMobile());
        // ??????
        jsonObject.set("email", dbSysUser.getEmail());
        // ????????????
        jsonObject.set("birthday", dbSysUser.getBirthday());
        // ??????
        jsonObject.set("age", dbSysUser.getAge());
        // ??????
        jsonObject.set("nationnality", dbSysUser.getNationnality());
        jsonObject.set("nationnalityName", dbSysUser.getNationnalityName());
        // ??????
        jsonObject.set("nation", dbSysUser.getNation());
        jsonObject.set("nationName", dbSysUser.getNationName());
        // ??????
        jsonObject.set("postions", dbSysUser.getPostions());
        jsonObject.set("postionsName", dbSysUser.getPostionsName());
        // ????????????
        jsonObject.set("positiongrade", dbSysUser.getPositiongrade());
        jsonObject.set("positiongradeName", dbSysUser.getPositiongradeName());
        // ??????
        jsonObject.set("jobType", dbSysUser.getJobType());
        jsonObject.set("jobTypeName", dbSysUser.getJobTypeName());
        // ????????????
        jsonObject.set("empType", dbSysUser.getEmpType());
        jsonObject.set("empTypeName", dbSysUser.getEmpTypeName());
        // ??????
        jsonObject.set("gender", dbSysUser.getGender());
        // ??????
        jsonObject.set("imageUrl", dbSysUser.getImageUrl());
        return repEntity.ok(jsonObject);
    }

    /**
     * ??????-???????????????????????????????????????
     * 
     * @param sysUser
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity addSysUserInfoByBg(SysUser sysUser) throws Exception {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        // ???????????????????????????
        List<Map> sysDepartmentList = sysUser.getSysDepartmentList();
        if (sysDepartmentList == null || sysDepartmentList.size() == 0) {
            return repEntity.error("sys.exception");
        }
        // ?????????????????????
        if(StrUtil.isNotEmpty(sysUser.getMobile())) {
            if (!Validator.isNumber(sysUser.getMobile())) {
                return repEntity.layerMessage("no", "????????????????????????????????????");
            }
        }

        String userKey = TokenUtils.getUserKey(request);
        String userName = TokenUtils.getRealName(request);
        String accountId = TokenUtils.getAccountId(request);
        sysUser.setAccountId(accountId);
        // account?????????????????????
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

        // ????????????check
        SysUser sysUserSelect = new SysUser();
        sysUserSelect.setUserId(sysUser.getUserId());
        sysUserSelect.setAccountId(accountId);
        sysUserSelect.setAccountAppType(accountAppType);
        sysUserSelect.setAccountCorpId(accountCorpId);
        List<SysUser> sysUserList = sysUserMapper.selectBySysUserList(sysUserSelect);
        // ???????????????????????????
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
                        LoggerUtils.printExceptionLogger(logger, "???????????????????????????=");
                        throw new Apih5Exception("????????????");
                    } else {
                        return repEntity.ok("sys.data.sava", sysUser);
                    }
                }
            }
            
            SysDepartment sysDepartmentSelect = new SysDepartment();
            sysDepartmentSelect.setUserKey(sysUserList.get(0).getUserKey());
            List<SysDepartment> errorSysDepartmentList = sysDepartmentService.getSysDepartmentListByUserkey(sysDepartmentSelect);
            String deptName = errorSysDepartmentList.get(0).getDepartmentPathName();
            return repEntity.layerMessage("no", "??????????????????" + sysUserList.get(0).getRealName() + "(" + deptName + ")????????????");
        }

        // ?????????check
        sysUserSelect = new SysUser();
        sysUserSelect.setMobile(sysUser.getMobile());
        sysUserSelect.setAccountId(accountId);
        sysUserSelect.setAccountAppType(accountAppType);
        sysUserSelect.setAccountCorpId(accountCorpId);
        sysUserList = sysUserMapper.selectBySysUserList(sysUserSelect);
        // ????????????????????????
        if(StrUtil.isNotEmpty(sysUser.getMobile())) {
            if (sysUserList != null && sysUserList.size() > 0) {
//                SysDepartment sysDepartmentSelect = new SysDepartment();
//                sysDepartmentSelect.setUserKey(sysUserList.get(0).getUserKey());
//                List<SysDepartment> errorSysDepartmentList = sysDepartmentService.getSysDepartmentListByUserkey(sysDepartmentSelect);
//                if(errorSysDepartmentList != null) {
//                    String deptName = errorSysDepartmentList.get(0).getDepartmentPathName();
//                    return repEntity.layerMessage("no",
//                            "??????????????????" + sysUserList.get(0).getRealName() + "" + deptName + "?????????????????????");
//                }
                return repEntity.layerMessage("no",
                      "??????????????????" + sysUserList.get(0).getRealName() + "?????????????????????");
            }
        }

        // ?????????????????????
        sysUser.setUserKey(UuidUtil.generate());
        String userPwd = SecureUtil.md5(apih5Properties.getDefaultPassword() + apih5Properties.getMd5Salt());
        sysUser.setUserPwd(userPwd);
        sysUser.setCreateUserInfo(userKey, userName);

        // ??????????????????Ext10
        JSONArray jsonArrayExt10 = new JSONArray();
        jsonArrayExt10.add(sysUser.getUserKey());

        String companyId = "";
        String projectId = "";
        List departmentIdList = Lists.newArrayList();
        // ???????????????????????????
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
                throw new Apih5Exception("???????????????????????????!");
//                return repEntity.error("sys.exception");
            }
            jsonArrayExt10.add(sysDepartment.getDepartmentId());
        }
        // ??????user??????Ext10?????????userKey,departmentId???
        sysUser.setExt10(jsonArrayExt10.toString());

        // ????????????
        if (StrUtil.isNotEmpty(sysUser.getCertType())) {
            String certTypeName = baseCodeService.getBaseCodeItemName("certType", sysUser.getCertType());
            sysUser.setCertTypeName(certTypeName);
        }
        // ??????
        if (StrUtil.isNotEmpty(sysUser.getNationnality())) {
            String nationnalityName = baseCodeService.getBaseCodeItemName("nationnality", sysUser.getNationnality());
            sysUser.setNationnalityName(nationnalityName);
        }
        // ??????
        if (StrUtil.isNotEmpty(sysUser.getNation())) {
            String nationName = baseCodeService.getBaseCodeItemName("nation", sysUser.getNation());
            sysUser.setNationName(nationName);
        }
        // ??????
        if (StrUtil.isNotEmpty(sysUser.getPostions())) {
            String postionsName = baseCodeService.getBaseCodeItemName("postions", sysUser.getPostions());
            sysUser.setPostionsName(postionsName);
        }
        // ??????
        if (StrUtil.isNotEmpty(sysUser.getPositiongrade())) {
            String positiongradeName = baseCodeService.getBaseCodeItemName("positiongrade", sysUser.getPositiongrade());
            sysUser.setPositiongradeName(positiongradeName);
        }
        // ??????
        if (StrUtil.isNotEmpty(sysUser.getJobType())) {
            String jobTypeName = baseCodeService.getBaseCodeItemName("jobType", sysUser.getJobType());
            sysUser.setJobTypeName(jobTypeName);
        }
        // ????????????
        if (StrUtil.isNotEmpty(sysUser.getEmpType())) {
            String empTypeName = baseCodeService.getBaseCodeItemName("empType", sysUser.getEmpType());
            sysUser.setEmpTypeName(empTypeName);
        }
        // ?????????????????????????????????????????????
        if(StrUtil.isEmpty(sysUser.getUserStatus())) {
            sysUser.setUserStatus("1");
        } else {
            sysUser.setUserStatus(sysUser.getUserStatus());
        }

        // ??????????????????????????????????????????departmentFlag????????????????????????
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
            throw new Apih5Exception("??????????????????!");
        }

        // ????????????????????????
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
            // ??????????????????????????????
            JSONObject jsonObject = wechatEnterpriseService.coreServiceByResurceAddressbook(6, getParamMap,
                    userInfoReq);
            if (jsonObject == null || !StrUtil.equals("0", jsonObject.getStr("errcode"))) {
                // UserID?????????
                if (StrUtil.equals("60102", jsonObject.getStr("errcode"))) {
                    // ??????????????????????????????
                    jsonObject = wechatEnterpriseService.coreServiceByResurceAddressbook(7, getParamMap, userInfoReq);
                    if (jsonObject == null || !StrUtil.equals("0", jsonObject.getStr("errcode"))) {
                        LoggerUtils.printExceptionLogger(logger, "????????????????????????(??????)????????????=" + jsonObject.toString());
                        throw new Apih5Exception("????????????????????????(??????)????????????(" + jsonObject.getStr("errcode") + ")");
                    }
                } else {
                    LoggerUtils.printExceptionLogger(logger, "????????????????????????????????????=" + jsonObject.toString());
                    throw new Apih5Exception("????????????????????????????????????(" + jsonObject.getStr("errcode") + ")");
                }
            }
        }

        // ?????????
        SysDepartmentServiceImpl.cacheMap.clear();

        // ??????????????????
        sysUser.setUserPwd(null);
        return repEntity.ok("sys.data.sava", sysUser);
    }

    /**
     * ??????-????????????
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
        // ???????????????????????????
        List<Map> sysDepartmentList = sysUser.getSysDepartmentList();
        if (sysDepartmentList == null || sysDepartmentList.size() == 0) {
            return repEntity.error("sys.exception");
        }
        if (StrUtil.isEmpty(sysUser.getUserKey())) {
            return repEntity.layerMessage("no", "???????????????");
        }
        // ?????????????????????
        if(StrUtil.isNotEmpty(sysUser.getMobile())) {
            if (!Validator.isNumber(sysUser.getMobile())) {
                return repEntity.layerMessage("no", "????????????????????????????????????");
            }
        }
        
        // ??????????????????check
        if(StrUtil.equals("zj_xiamengs_renzi", accountId) && !StrUtil.equals("admin", userId)) {
            // ????????????????????????
            List<String> dbNotDepartmentList = Lists.newArrayList();
            SysDepartment checkSysDepartment = new SysDepartment();
            checkSysDepartment.setUserKey(sysUser.getUserKey());
            List<SysDepartment> checkSysDepartmentList = sysDepartmentMapper.selectSysDepartmentByUserkey(checkSysDepartment);
            for (SysDepartment checkDbSysDepartment : checkSysDepartmentList) {
                    // ????????????????????????????????????????????????dbNotDepartmentId???
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
                        return repEntity.layerMessage("no", "???????????????????????????????????????");
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

        // ?????????check
        if (!StrUtil.equals(dbSysUser.getMobile(), sysUser.getMobile())) {
            SysUser sysUserSelect = new SysUser();
            sysUserSelect = new SysUser();
            sysUserSelect.setMobile(sysUser.getMobile());
            sysUserSelect.setAccountId(accountId);
            sysUserSelect.setAccountAppType(sysUser.getAccountAppType());
            sysUserSelect.setAccountCorpId(sysUser.getAccountCorpId());
            List sysUserList = sysUserMapper.selectBySysUserList(sysUserSelect);
            // ????????????????????????
            if (sysUserList != null && sysUserList.size() > 0) {
                return repEntity.layerMessage("no", "?????????????????????");
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

            // ????????????
            if (StrUtil.isNotEmpty(sysUser.getCertType())) {
                String certTypeName = baseCodeService.getBaseCodeItemName("certType", sysUser.getCertType());
                dbSysUser.setCertType(sysUser.getCertType());
                dbSysUser.setCertTypeName(certTypeName);
            }
            // ??????
            if (StrUtil.isNotEmpty(sysUser.getNationnality())) {
                String nationnalityName = baseCodeService.getBaseCodeItemName("nationnality",
                        sysUser.getNationnality());
                dbSysUser.setNationnality(sysUser.getNationnality());
                dbSysUser.setNationnalityName(nationnalityName);
            }
            // ??????
            if (StrUtil.isNotEmpty(sysUser.getNation())) {
                String nationName = baseCodeService.getBaseCodeItemName("nation", sysUser.getNation());
                dbSysUser.setNation(sysUser.getNation());
                dbSysUser.setNationName(nationName);
            }
            // ??????
            if (StrUtil.isNotEmpty(sysUser.getPostions())) {
                String postionsName = baseCodeService.getBaseCodeItemName("postions", sysUser.getPostions());
                dbSysUser.setPostions(sysUser.getPostions());
                dbSysUser.setPostionsName(postionsName);
            } else {
                if (StrUtil.isNotEmpty(sysUser.getPostionsName())) {
                    dbSysUser.setPostionsName(sysUser.getPostionsName());
                }
            }
            // ??????
            if (StrUtil.isNotEmpty(sysUser.getPositiongrade())) {
                String positiongradeName = baseCodeService.getBaseCodeItemName("positiongrade",
                        sysUser.getPositiongrade());
                dbSysUser.setPositiongrade(sysUser.getPositiongrade());
                dbSysUser.setPositiongradeName(positiongradeName);
            }
            // ??????
            if (StrUtil.isNotEmpty(sysUser.getJobType())) {
                String jobTypeName = baseCodeService.getBaseCodeItemName("jobType", sysUser.getJobType());
                dbSysUser.setJobType(sysUser.getJobType());
                dbSysUser.setJobTypeName(jobTypeName);
            }
            // ????????????
            if (StrUtil.isNotEmpty(sysUser.getEmpType())) {
                String empTypeName = baseCodeService.getBaseCodeItemName("empType", sysUser.getEmpType());
                dbSysUser.setEmpType(sysUser.getEmpType());
                dbSysUser.setEmpTypeName(empTypeName);
            }
            // ?????????????????????????????????????????????
            if(StrUtil.isEmpty(sysUser.getUserStatus())) {
                dbSysUser.setUserStatus("1");
            } else {
                dbSysUser.setUserStatus(sysUser.getUserStatus());
            }
            dbSysUser.setSort(sysUser.getSort());
            // ??????
            dbSysUser.setModifyUserInfo(userKey, userName);
            flag = sysUserMapper.updateByPrimaryKey(dbSysUser);

            List departmentIdList = Lists.newArrayList();
            // ?????????????????????????????????
            List<SysUserDepartment> sysUserDepartmentList = Lists.newArrayList();
            SysUserDepartment sysUserDepartmentUserKey = new SysUserDepartment();
            sysUserDepartmentUserKey.setUserKey(sysUser.getUserKey());
            sysUserDepartmentList.add(sysUserDepartmentUserKey);
            sysUserDepartmentMapper.batchDeleteUpdateSysUserDepartmentByUserKey(sysUserDepartmentList);
            // ???????????????????????????
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
                    LoggerUtils.printExceptionLogger(logger, "???????????????????????????=");
                    throw new Apih5Exception("???????????????????????????");
//                    return repEntity.error("sys.exception");
                }
            }

            // ????????????????????????
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
                // ??????????????????????????????
                JSONObject jsonObject = wechatEnterpriseService.coreServiceByResurceAddressbook(7, getParamMap,
                        userInfoReq);
                if (jsonObject == null || !StrUtil.equals("0", jsonObject.getStr("errcode"))) {
                    LoggerUtils.printDebugLogger(logger, "????????????????????????????????????" + jsonObject.toString());
                    // ??????????????????????????????????????????????????????
                    getParamMap = new HashMap<String, String>();
                    getParamMap.put("access_token", accessToken);
                    userInfoReq = new UserInfoReq();
                    userInfoReq.setUserid(sysUser.getUserId());
                    userInfoReq.setName(sysUser.getRealName());
                    userInfoReq.setEnable(1);
                    userInfoReq.setDepartment(departmentIdList);
                    userInfoReq.setMobile(sysUser.getMobile());
                    // ??????????????????????????????
                    jsonObject = wechatEnterpriseService.coreServiceByResurceAddressbook(6, getParamMap, userInfoReq);
                    if (jsonObject == null || !StrUtil.equals("0", jsonObject.getStr("errcode"))) {
                        LoggerUtils.printExceptionLogger(logger, "????????????????????????????????????" + jsonObject.toString());
                        throw new Apih5Exception("????????????????????????????????????(" + jsonObject.getStr("errcode") + ")");
                    }
                }

            }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            // ?????????
            SysDepartmentServiceImpl.cacheMap.clear();

            // ??????key
            String userEhCacheKey = dbSysUser.getUserKey() + "-" + accountId;
            // ??????token??????
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
     * ??????-????????????
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
//            return repEntity.layerMessage("no", "????????????????????????????????????");
        }
        String userId = sysUserMapper.selectByPrimaryKey(sysUser.getUserKey()).getUserId();
        if (StrUtil.equals("admin", userId)) {
            return repEntity.layerMessage("no", "??????????????????????????????");
        }
        if(StrUtil.equals(sysUser.getUserKey(), userKey)) {
            return repEntity.layerMessage("no", "??????????????????????????????");
        }
        
        // ??????????????????check
        // ??????????????????check
        if(StrUtil.equals("zj_xiamengs_renzi", accountId) && !StrUtil.equals("admin", userId)) {
            // ????????????????????????
            List<String> dbNotDepartmentList = Lists.newArrayList();
            SysDepartment checkSysDepartment = new SysDepartment();
            checkSysDepartment.setUserKey(sysUser.getUserKey());
            List<SysDepartment> checkSysDepartmentList = sysDepartmentMapper.selectSysDepartmentByUserkey(checkSysDepartment);
           
            // ?????????
            SysDepartment checkSysDepartmentOpt = new SysDepartment();
            checkSysDepartmentOpt.setUserKey(userKey);
            List<SysDepartment> checkSysDepartmentListOpt = sysDepartmentMapper.selectSysDepartmentByUserkey(checkSysDepartmentOpt);
            JSONArray checkJSONArray = new JSONArray(checkSysDepartmentListOpt);
            for (SysDepartment checkDbSysDepartment : checkSysDepartmentList) {
                if(checkJSONArray.toString().indexOf(checkDbSysDepartment.getProjectId())<0) {
                    return repEntity.layerMessage("no", "???????????????????????????????????????");
                }
            }
        }

        // ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
        String checkAuthorize = publicConfig.getProperty("check.authorize.delete.api", "");
        if (!StrUtil.equals("???", checkAuthorize)) {
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

        // ??????????????????
        List<SysUser> sysUserList = Lists.newArrayList();
        sysUserList.add(sysUser);
        SysUser sysUserInfo = new SysUser();
        sysUserInfo.setModifyUserInfo(userKey, realName);
        int flag = sysUserMapper.batchDeleteSysUser(sysUserList, sysUserInfo);
        if (flag == 0) {
            return repEntity.errorSave();
        }

        // ?????????????????????
        List<SysUserDepartment> sysUserDepartmentList = Lists.newArrayList();
        SysUserDepartment sysUserDepartment = new SysUserDepartment();
        sysUserDepartment.setUserKey(sysUser.getUserKey());
        sysUserDepartmentList.add(sysUserDepartment);
        flag = sysUserDepartmentMapper.batchDeleteUpdateSysUserDepartmentByUserKey(sysUserDepartmentList);
        if (flag == 0) {
            return repEntity.errorSave();
        }

        // ????????????????????????
        SysRoleUser sysRoleUser = new SysRoleUser();
        sysRoleUser.setValue(sysUser.getUserKey());
        List<SysRoleUser> sysRoleUserList = sysRoleUserMapper.selectBySysRoleUserList(sysRoleUser);
        if (sysRoleUserList != null && sysRoleUserList.size() > 0) {
            sysRoleUserMapper.batchDeleteUpdateSysRoleUser(sysRoleUserList);
        }

        // ????????????????????????,??????????????????
        if (Apih5Properties.isUseSyncWeChat()) {
            List<BaseAccount> baseAccountList = baseAccountMapper.selectByBaseAccountListByLike("_txl");
            accountId = baseAccountList.get(0).getAccountId();
            Map<String, String> accessTokenMap = weChatEnterpriseDbService.getWeChatAccessToken(accountId);
            String accessToken = accessTokenMap.get("accessToken");

            Map<String, String> getParamMap = new HashMap<String, String>();
            getParamMap.put("access_token", accessToken);
            getParamMap.put("userid", userId);
            // ??????????????????????????????
            JSONObject jsonObject = wechatEnterpriseService.coreServiceByResurceAddressbook(8, getParamMap, null);
            if (jsonObject == null || !StrUtil.equals("0", jsonObject.getStr("errcode"))) {
                LoggerUtils.printExceptionLogger(logger, "????????????????????????????????????" + jsonObject.toString());
//                throw new Apih5Exception("????????????????????????????????????(" + jsonObject.getStr("errcode") + ")");
            }
        }

        // ?????????
        SysDepartmentServiceImpl.cacheMap.clear();

        // ??????key
        String userEhCacheKey = sysUser.getUserKey() + "-" + accountId;
        // ??????token??????
//        TokenEntity tokenEntity = EhCacheCacheHandler.getUserTokenEhCache(userEhCacheKey);
        TokenEntity tokenEntity = sysRedisCacheService.getUserTokenRedis(userEhCacheKey);
        if (tokenEntity != null) {
//            EhCacheCacheHandler.removeUserTokenEhCache(userEhCacheKey);
            sysRedisCacheService.removeUserTokenRedis(userEhCacheKey);
        }
        return repEntity.ok("sys.data.delete", sysUser);
    }

    /**
     * ??????-????????????
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

        // account?????????????????????
        List<BaseAccount> baseAccountList = baseAccountMapper.selectByBaseAccountListByLike("_txl");
        String accountId = baseAccountList.get(0).getAccountId();
        Map<String, String> accessTokenMap = weChatEnterpriseDbService.getWeChatAccessToken(accountId);
        String accessToken = accessTokenMap.get("accessToken");
        sysUser.setAccountCorpId(accessTokenMap.get("accountCorpId"));
        sysUser.setAccountAppType(accessTokenMap.get("accountAppType"));

        int flag = 0;
        SysUser dbSysUser = sysUserMapper.selectByPrimaryKey(sysUser.getUserKey());
        if (dbSysUser != null && StrUtil.isNotEmpty(dbSysUser.getUserId())) {
            // ??????0?????????1
            dbSysUser.setUserStatus(sysUser.getUserStatus());
            // ??????
            dbSysUser.setModifyUserInfo(userKey, userName);
            flag = sysUserMapper.updateByPrimaryKey(dbSysUser);

            // ????????????????????????
//            if (Apih5Properties.isUseSyncWeChat()) {
//                Map<String, String> getParamMap = new HashMap<String, String>();
//                getParamMap.put("access_token", accessToken);
//                UserInfoReq userInfoReq = new UserInfoReq();
//                userInfoReq.setUserid(sysUser.getUserId());
//                userInfoReq.setName(sysUser.getRealName());
//                userInfoReq.setEnable(1);
//                userInfoReq.setDepartment(departmentIdList);
//                userInfoReq.setMobile(sysUser.getMobile());
//                // ??????????????????????????????
//                JSONObject jsonObject = wechatEnterpriseService.coreServiceByResurceAddressbook(7, getParamMap,
//                        userInfoReq);
//                if (jsonObject == null || !StrUtil.equals("0", jsonObject.getStr("errcode"))) {
//                    LoggerUtils.printDebugLogger(logger, "????????????????????????????????????" + jsonObject.toString());
//                    // ??????????????????????????????????????????????????????
//                    getParamMap = new HashMap<String, String>();
//                    getParamMap.put("access_token", accessToken);
//                    userInfoReq = new UserInfoReq();
//                    userInfoReq.setUserid(sysUser.getUserId());
//                    userInfoReq.setName(sysUser.getRealName());
//                    userInfoReq.setEnable(1);
//                    userInfoReq.setDepartment(departmentIdList);
//                    userInfoReq.setMobile(sysUser.getMobile());
//                    // ??????????????????????????????
//                    jsonObject = wechatEnterpriseService.coreServiceByResurceAddressbook(6, getParamMap, userInfoReq);
//                    if (jsonObject == null || !StrUtil.equals("0", jsonObject.getStr("errcode"))) {
//                        LoggerUtils.printExceptionLogger(logger, "????????????????????????????????????" + jsonObject.toString());
//                        throw new Apih5Exception("????????????????????????????????????(" + jsonObject.getStr("errcode") + ")");
//                    }
//                }
//            }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            // ?????????
            SysDepartmentServiceImpl.cacheMap.clear();

            // ??????key
            String userEhCacheKey = dbSysUser.getUserKey() + "-" + accountId;
            // ??????token??????
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
     * ??????-????????????
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

        // account?????????????????????
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
            // ??????
            dbSysUser.setModifyUserInfo(userKey, userName);
            flag = sysUserMapper.updateByPrimaryKey(dbSysUser);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            // ?????????
            SysDepartmentServiceImpl.cacheMap.clear();
            // ??????key
            String userEhCacheKey = dbSysUser.getUserKey() + "-" + accountId;
            // ??????token??????
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
     * ???????????????????????????????????????????????????????????????????????????
     * 
     * @param sysUser
     * @return sysUserList
     */
    @Override
    public ResponseEntity getSysUserListByUser(SysUser sysUser) {
        // ????????????
        PageHelper.startPage(sysUser.getPage(), sysUser.getLimit());
        List<SysUser> sysUserList = userMapper.selectBySysUserList(sysUser);
        // ??????????????????
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
    // // ???????????????123456
    // String password = SecureUtil.md5(apih5Properties.getDefaultPassword() +
    // apih5Properties.getMd5Salt());
    // sysUser.setPassword(password);
    // // ???????????????????????????????????????
    // userMapper.userAdd(sysUser);
    // // ???????????????????????????
    // String userRoleStr = sysUser.getUserRoleStr();
    // addSysUserRole(userRoleStr, sysUser);
    // }

    @Override
    public ResponseEntity addUser(SysUser sysUser) {
        // account?????????????????????
//        BaseAccount baseAccount = baseAccountMapper.selectByPrimaryKey(sysUser.getAccountId());
        BaseAccount baseAccount = baseAccountService.getBaseAccount(sysUser.getAccountId());
        sysUser.setAccountCorpId(baseAccount.getAccountCorpId());
        sysUser.setAccountAppType(baseAccount.getAccountAppType());
        LoggerUtils.printDebugLogger(logger, "addUser+1+      baseAccount");
        // ???????????????????????????
        SysUser userExists = this.checkUserIdExists(sysUser);
        // ???????????????????????????
        if (userExists != null) {
            return repEntity.error("sys.user.register.exists");
        }
        LoggerUtils.printDebugLogger(logger, "addUser+2+      userExists");
        // ?????????
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
            // ???????????????????????????
            return repEntity.ok("");
        }
        // // ???????????????????????????
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
    // // ??????
    // dbSysUser.setModifyTime(new Date());
    // dbSysUser.setModifyUser(userKey);
    // flag = userMapper.updateByPrimaryKey(dbSysUser);
    // }
    // // ??????
    // if (flag == 0) {
    // return repEntity.errorSave();
    // } else {
    // return repEntity.ok("sys.data.update", sysUser);
    // }
    //
    // // String password = SecureUtil.md5(sysUser.getPassword() +
    // // apih5Properties.getMd5Salt());
    // // sysUser.setPassword(password);
    // // // ?????????????????????
    // // userMapper.updateByPrimaryKeySelective(sysUser);
    // // // ????????????????????????????????????
    // // if(sysUser.getSimpleUpdate() == 0) {
    // // // ?????????????????????????????????????????????id??????
    // // int userId = sysUser.getId();
    // // userMapper.deleteUserRoleMapping(userId);
    // // // ?????????????????????????????????????????????????????????????????????
    // // // ???????????????????????????
    // // String userRoleStr = sysUser.getUserRoleStr();
    // // addSysUserRole(userRoleStr, sysUser);
    // // }
    // }

    /**
     * ???????????????????????????????????????????????????
     * 
     * @param userRoleStr
     */
    private void addSysUserRole(String userRoleStr, SysUser sysUser) {
        // // ?????????????????????????????????
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
        // ?????????????????????
        Map<String, Object> infoMap = Maps.newHashMap();
        if (!StrUtil.equals("1", sysUser.getDefaultUserPwdFlag())) {
            String userPwd = SecureUtil.md5(sysUser.getUserPwd() + apih5Properties.getMd5Salt());
            sysUser.setUserPwd(userPwd);
        }

        String loginType = sysUser.getLoginType();
        // ?????????????????????????????????userId????????????
        String weChatUserId = sysUser.getUserId();
        // ???DB????????????????????????
//        BaseAccount baseAccount = baseAccountMapper.selectByPrimaryKey(sysUser.getAccountId());
        BaseAccount baseAccount = baseAccountService.getBaseAccount(sysUser.getAccountId());
        if(baseAccount == null) {
            return repEntity.layerMessage("no", "????????????????????????");
        }
        // ????????????ID
        sysUser.setAccountCorpId(baseAccount.getAccountCorpId());
        // ??????????????????
        sysUser.setAccountAppType(baseAccount.getAccountAppType());
        List<SysUser> sysUserList = null;
        // ???????????????
        if ("zj_qyh".equals(sysUser.getAccountCorpId())) {
            // ????????????
            if ("1".equals(baseAccount.getAccountAppType())) {
                // ??????????????????PC???????????????????????????????????????
                if (StrUtil.equals("1", sysUser.getLoginType()) || StrUtil.equals("9", sysUser.getLoginType())) {
                    // ??????login??????userId
                    // sysUser.setUserId(sysUserList.get(0).getUserId());
                    sysUserList = Lists.newArrayList();
                    sysUserList.add(sysUser);
                } else {
                    // ??????userId??????????????????????????????????????????????????????????????????????????????sys_user &
                    // sys_user_company
                    sysUserList = userMapper.getSysUserListByCompany(sysUser);
                    // ????????????????????????userId??????????????????????????????????????????????????????????????????
                    if (sysUserList != null && sysUserList.size() > 0) {
                        sysUser.setUserId(sysUserList.get(0).getUserId());
                    }
                    // ?????????????????????userId???????????????????????????????????????????????????????????????
                    else {
                    }
                }
            }
            // ?????????????????????????????????????????????????????????????????????????????????ID???
            else {
                sysUserList = userMapper.selectBySysUserList(sysUser);
            }
        }

        SysUser dbSysUser = userMapper.checkUserExists(sysUser);
        // ??????ID??????????????????
        if (dbSysUser == null) {
            if(StrUtil.equals("1", sysUser.getLoginType())) {
                int loginErrorCount = sysRedisCacheService.getLoginLockRedis(sysUser.getUserId())+1;
                sysRedisCacheService.putLoginLockRedis(sysUser.getUserId(), loginErrorCount);
            }
            return repEntity.error("sys.user.pwd");
        }
        // ??????????????????
        if (!"1".equals(dbSysUser.getUserStatus())) {
            return repEntity.error("sys.user.login.error");
        }
        // ??????????????????
        if (dbSysUser.getExpirationDate() != null
                && DateUtil.between(DateUtil.date(), dbSysUser.getExpirationDate(), DateUnit.MINUTE) <= 0) {
            return repEntity.error("sys.user.login.error");
        }

        // ??????key
        String ehCacheKey = dbSysUser.getUserKey() + "-" + sysUser.getAccountId();
        // ??????token??????
//        TokenEntity loginTokenEntity = EhCacheCacheHandler.getUserTokenEhCache(ehCacheKey);
        TokenEntity loginTokenEntity = sysRedisCacheService.getUserTokenRedis(ehCacheKey);
        // ??????????????????token????????????????????????????????????????????????????????????
        if (loginTokenEntity != null) {
            JSONObject jsonObjectWeb = new JSONObject(loginTokenEntity.getSysUserWebObject());
            SysUserWebEntity sysUserWebEntity = JSONUtil.toBean(jsonObjectWeb, SysUserWebEntity.class);
            // SysUserWebEntity sysUserWebEntity =
            // (SysUserWebEntity)loginTokenEntity.getSysUserWebObject();
            // ??????????????????
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

            // ???????????????????????????????????????????????????
            boolean isDefaultPasswordReset = false;
            if (apih5Properties.isDefaultPasswordReset() && StrUtil.equals("1", loginType)) {
                String userPwd = sysUser.getUserPwd();
                String defaultPwd = SecureUtil.md5(apih5Properties.getDefaultPassword() + apih5Properties.getMd5Salt());
                if (StrUtil.equals(userPwd, defaultPwd)) {
                    isDefaultPasswordReset = true;
                }
            }
            // ??????web
            infoMap.put("token", loginTokenEntity.getToken());
            infoMap.put("userInfo", sysUserWebEntity);
            infoMap.put("defaultPasswordReset", isDefaultPasswordReset);
            return repEntity.ok(infoMap);
        }

        // ??????ip??????
        String ip = sysUser.getIp();// requestHolderConfiguration.getHttpServletRequest().getRemoteAddr();
        // ????????????token
        String token = TokenUtils.createUserToken(sysUser.getAccountId(), dbSysUser.getUserKey(), 0,
                apih5Properties.getUserTokenKey(), ip);
        // ?????????log????????????????????????
        SysLog sysLog = new SysLog();
        sysLog.setLoginAccount(dbSysUser.getUserId());
        sysLog.setTypeId(1);
        sysLog.setIp(ip);
        sysLog.setDeviceId(sysUser.getDeviceId());
        sysLog.setEquipmentInfo(sysUser.getEquipmentInfo());
        logMapper.logAdd(sysLog);

        // ??????????????????token????????????
        // SysUserToken sysUserToken = new SysUserToken();
        // sysUserToken.setUserKey(dbSysUser.getUserKey());
        // sysUserToken.setToken(token);
        // sysUserToken.setIp(ip);
        // userTokenMapper.tokenAdd(sysUserToken);

        // ???????????????????????????userInfo??????
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
        tokenEntity.setExt10(dbSysUser.getExt10());// ????????????
        tokenEntity.setJobType(dbSysUser.getJobType());
        tokenEntity.setMobile(dbSysUser.getMobile());
        tokenEntity.setWeChatUsereId(weChatUserId);

        // ???web?????????userInfo
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

        // ??????
        if ("zj_qyh".equals(sysUser.getAccountCorpId())) {
            // ?????????????????????????????????????????????
            List<SysCompany> sysCompanyList = null;
            // ????????????
            if ("1".equals(baseAccount.getAccountAppType())) {
                sysCompanyList = sysDepartmentService.selectCompanyListByMobile(dbSysUser.getMobile(),
                        dbSysUser.getAccountAppType());
            }
            // ?????????????????????????????????????????????????????????????????????????????????ID???
            else {
                // ??????ID???????????????????????????????????????????????????????????????????????????????????????
                String companyId = sysDepartmentService.getCompanyIdByUserKey(dbSysUser.getUserKey());
                SysCompany sysCompany = sysCompanyMapper.selectByPrimaryKey(companyId);
                sysCompany.setUserKey(dbSysUser.getUserKey());
                sysCompanyList = Lists.newArrayList();
                sysCompanyList.add(sysCompany);
            }

            String companyId = "";
            String companyUrl = "";

            // ??????List
            List<CompanyEntity> companyList = Lists.newArrayList();
            for (SysCompany sysCompany : sysCompanyList) {
                CompanyEntity companyEntity = new CompanyEntity();
                companyEntity.setUserKey(sysCompany.getUserKey());
                companyEntity.setCompanyId(sysCompany.getCompanyId());
                companyEntity.setCompanyName(sysCompany.getCompanyName());
                // ??????????????????????????????????????????
                if (StrUtil.equals(dbSysUser.getUserKey(), sysCompany.getUserKey())) {
                    companyEntity.setCompanySelectFlag(1);
                    companyId = sysCompany.getCompanyId();
                    companyUrl = sysCompany.getCompanyUrl();
                }
                // ??????????????????????????????
                companyList.add(companyEntity);
            }
            sysUserWebEntity.setCompanyList(companyList);
            if (companyList == null || companyList.size() == 0) {
                return repEntity.error("sys.role.exception");
            }

            // ??????????????????????????????????????????????????????????????????????????????????????????
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

            // ?????????????????????????????????????????????????????????????????????????????????????????????????????????
            if (sysUserList == null || sysUserList.size() == 0) {
                SysUserCompany sysUserCompany = new SysUserCompany();
                sysUserCompany.setUserKey(dbSysUser.getUserKey());
                sysUserCompany.setCompanyId(companyId);
                sysUserCompanyService.addSysUserCompanyCommon(sysUserCompany);
            }
            tokenEntity.setCompanyId(companyId);
            tokenEntity.setCompanyUrl(companyUrl);
            // ??????userId??????IP???????????????????????????????????????????????????userKey?????????????????????????????????
        } else {
            // ?????????????????????
            SysProject sysProjectSelect = new SysProject();
            sysProjectSelect.setUserKey(dbSysUser.getUserKey());
            List<SysProject> sysProjectList = sysProjectMapper.selectBySysProjectListByUserKey(sysProjectSelect);
            if(StrUtil.equals("4", dbSysUser.getExt1())
                    && sysProjectList != null && sysProjectList.size()>0) {
//                // ??????List
//                List<CompanyEntity> companyList = Lists.newArrayList();
//                for (SysUserDepartment dbSysUserDepartment : sysUserDepartmentList) {
////                    CompanyEntity companyEntity = new CompanyEntity();
////                    companyEntity.setUserKey(sysCompany.getUserKey());
////                    companyEntity.setCompanyId(sysCompany.getCompanyId());
////                    companyEntity.setCompanyName(sysCompany.getCompanyName());
////                    // ??????????????????????????????????????????
////                    companyEntity.setCompanySelectFlag(1);
////                    // ??????????????????????????????
////                    companyList.add(companyEntity);
//                }
            } else {
                // ??????ID???????????????????????????????????????????????????????????????????????????????????????
                String companyId = sysDepartmentService.getCompanyIdByUserKey(dbSysUser.getUserKey());
                SysCompany sysCompanySelect = new SysCompany();
                if (!StrUtil.equals("1", dbSysUser.getExt1()) && StrUtil.isNotEmpty(companyId)) {
                    sysCompanySelect.setCompanyId(companyId);
                }
                List<SysCompany> sysCompanyList = sysCompanyMapper.selectBySysCompanyList(sysCompanySelect);
                // ??????List
                List<CompanyEntity> companyList = Lists.newArrayList();
                for (SysCompany sysCompany : sysCompanyList) {
                    CompanyEntity companyEntity = new CompanyEntity();
                    companyEntity.setUserKey(sysCompany.getUserKey());
                    companyEntity.setCompanyId(sysCompany.getCompanyId());
                    companyEntity.setCompanyName(sysCompany.getCompanyName());
                    // ??????????????????????????????????????????
                    companyEntity.setCompanySelectFlag(1);
                    // ??????????????????????????????
                    companyList.add(companyEntity);
                }
                sysUserWebEntity.setCompanyList(companyList);
                // ??????????????????ID
                tokenEntity.setCompanyId(companyId);
                
                // ??????????????????????????????????????????????????????????????????????????????????????????
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

        // ??????????????????
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

        // ??????????????????
        tokenEntity.setSysUserWebObject(sysUserWebEntity);
        // ???????????????????????????????????????????????????
        boolean isDefaultPasswordReset = false;
        if (apih5Properties.isDefaultPasswordReset() && StrUtil.equals("1", loginType)) {
            String userPwd = sysUser.getUserPwd();
            String defaultPwd = SecureUtil.md5(apih5Properties.getDefaultPassword() + apih5Properties.getMd5Salt());
            if (StrUtil.equals(userPwd, defaultPwd)) {
                isDefaultPasswordReset = true;
            }
        }

        // ??????web
        infoMap.put("token", token);
        infoMap.put("userInfo", sysUserWebEntity);
        infoMap.put("defaultPasswordReset", isDefaultPasswordReset);

        // ????????????????????????token,token???key???userToken_??????key
        String userEhCacheKey = dbSysUser.getUserKey() + "-" + sysUser.getAccountId();
        sysRedisCacheService.putUserTokenRedis(userEhCacheKey, tokenEntity);

        LoggerUtils.printDebugLogger(logger, "login tokenEntity=" + tokenEntity.getUserId());

        return repEntity.ok(infoMap);
        // ??????????????????????????????token??????userKey???????????????????????????????????????token??????????????????????????????????????????????????????userKey????????????
    }

    @Override
    public ResponseEntity getCacheUserInfo(String userEhCacheKey) {
//        TokenEntity tokenEntity = null;
//        CacheManager cacheManager = CacheManager.create();
//        // 2. ??????????????????
//        Cache cache = cacheManager.getCache("userTokenEhCache");
//        if(cache != null) {
//            Element value = cache.get(userEhCacheKey);
//            tokenEntity = (TokenEntity) value.getObjectValue();
//        }
        TokenEntity tokenEntity = sysRedisCacheService.getUserTokenRedis(userEhCacheKey);
        return repEntity.ok(tokenEntity);
    }

    /**
     * ?????????????????????????????????????????????????????????put
     * 
     * @param accountId
     * @param userKey
     * @param jsonObject login?????????data??????
     * @return false:??????????????????
     */
    @Override
    public boolean copyCacheLogin(String accountId, String userKey, JSONObject jsonObject) {
        String userEhCacheKey = userKey + "-" + accountId;

        // ????????????????????????
        String urlApi = config.getProperty("main.cache.url", "") + "user/getCacheUserInfo/" + userEhCacheKey;
        String result = HttpUtil.sendPostToken(urlApi, "", jsonObject.getStr("token"));
        JSONObject jsonObjectResult = new JSONObject(result);
        if (jsonObjectResult.getBool("success")) {
            TokenEntity tokenEntity = jsonObjectResult.getBean("data", TokenEntity.class);
            if (tokenEntity == null) {
                return false;
            }
            // ??????put
            sysRedisCacheService.putUserTokenRedis(userEhCacheKey, tokenEntity);
            return true;
        }
        return false;
    }

    // ??????/??????/?????? ???????????????????????????
    @Override
    public ResponseEntity getMobileSmsCaptcha(SmsEntity smsEntity) {
        String smsTxt = FileUtil.readUtf8String(Thread.currentThread().
                getContextClassLoader().getResource("") + "config/sms.txt");
        if(StrUtil.isEmpty(smsTxt)) {
            return repEntity.layerMessage("no", "???????????????????????????");
        } 
        smsTxt = smsTxt.replaceAll("\r\n", "");
        JSONObject jsonObject = new JSONObject(smsTxt);

        // ????????????????????????aly00??????\aly01:??????\aly02:??????\aly03:????????????
        if(StrUtil.equals("aly00", smsEntity.getTemplateType())
                || StrUtil.equals("aly01", smsEntity.getTemplateType())
                || StrUtil.equals("aly02", smsEntity.getTemplateType())
                || StrUtil.equals("aly03", smsEntity.getTemplateType())) {
            // ???????????????
            String captcha = SmsUtils.generateCaptcha(4);
            // ????????????????????????
            SmsUtils.saveCaptchaEhCache(smsEntity.getMobile(), captcha);
            // ??????????????????
            smsEntity.setTemplateParam("{\"code\":\"" + captcha + "\"}");
            // ?????????????????????
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
                return repEntity.ok("????????????!");
            } else {
                return repEntity.layerMessage("no", "??????????????????");
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

    // ??????????????????
    @Override
    public int updateUserCommon(SysUser sysUser) {
        int flag = 0;
        if (sysUser != null && StrUtil.isNotEmpty(sysUser.getUserKey())) {
            // ??????
            sysUser.setModifyTime(new Date());
            sysUser.setModifyUser(sysUser.getUserKey());
            flag = userMapper.updateByPrimaryKeySelective(sysUser);
        }
        // ??????
        return flag;
    }

    // ????????????????????????????????????
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
        // account?????????????????????
//        BaseAccount baseAccount = baseAccountMapper.selectByPrimaryKey(sysUser.getAccountId());
        BaseAccount baseAccount = baseAccountService.getBaseAccount(sysUser.getAccountId());
        sysUser.setAccountCorpId(baseAccount.getAccountCorpId());
        sysUser.setAccountAppType(baseAccount.getAccountAppType());
        // ?????????
        String userPwd = SecureUtil.md5(sysUser.getUserPwd() + apih5Properties.getMd5Salt());
        sysUser.setUserPwd(userPwd);
        sysUser.setCreateUserInfo(sysUser.getUserKey(), sysUser.getRealName());
        return userMapper.insert(sysUser);
    }

    // ???????????????????????????
    @Override
    public List<SysUser> getUserListByRoleAndCompanyId(SysUser sysUser) {
        List<SysUser> userList = new ArrayList<SysUser>();
        if (sysUser != null && StringUtil.isNotEmpty(sysUser.getCompanyId())) {
            userList = userMapper.getUserListByRoleAndCompanyId(sysUser);
        }
        return userList;
    }
    
    // ???????????????????????????
    @Override
    public List<SysUser> getUserListByRoleAndCompanyIdXMJX(SysUser sysUser) {
        List<SysUser> userList = new ArrayList<SysUser>();
        if (sysUser != null && StringUtil.isNotEmpty(sysUser.getCompanyId())) {
            userList = userMapper.getUserListByRoleAndCompanyId(sysUser);
            if(userList.size() > 0) {
                for(SysUser dbSysUser : userList) {
                    // ??????userKey???deptId????????????????????????
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

    // ??????????????????????????????
    @Override
    public int bathInsertUserCommon(List<SysUser> sysUserList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        if (sysUserList != null && sysUserList.size() > 0) {
            for (SysUser user : sysUserList) {
                // account?????????????????????
//                BaseAccount baseAccount = baseAccountMapper.selectByPrimaryKey(user.getAccountId());
                BaseAccount baseAccount = baseAccountService.getBaseAccount(user.getAccountId());
                if (baseAccount == null) {
                    return 0;
                }
                user.setAccountCorpId(baseAccount.getAccountCorpId());
                user.setAccountAppType(baseAccount.getAccountAppType());
                // ?????????
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
     * ?????????????????????????????????????????????????????????
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
        // ??????????????????id(????????????)
        String moveUserKey = sysUser.getMoveUserKey();
        // ??????????????????????????????userKey(????????????)
        String firstUserKey = sysUser.getFirstUserKey();
        // ?????????????????????????????????userKey(????????????)
        String lastUserKey = sysUser.getLastUserKey();
        // ???????????????????????????????????????id(?????????????????????)
        String beforeUserKey = sysUser.getBeforeUserKey();
        // ???????????????????????????????????????id(?????????????????????)
        String afterUserKey = sysUser.getAfterUserKey();
        // ?????????????????????(????????????)
        SysUser moveSysUser = sysUserMapper.selectByPrimaryKey(moveUserKey);
        if (moveSysUser == null || StrUtil.isEmpty(firstUserKey) || StrUtil.isEmpty(lastUserKey)) {
            return repEntity.layerMessage("no", "??????????????????!");
        }
        // ??????????????????????????????????????????(????????????)
        LinkedList<SysUser> sysUserList = new LinkedList<SysUser>(userMapper.getUserListByRoleAndCompanyId(sysUser));
        // ???????????????????????????????????????????????????,???????????????????????????
        // sysUserList.remove(moveSysUser);
        for (int i = 0; i < sysUserList.size(); i++) {
            // ?????????????????????????????????????????????
            if (StrUtil.equals(moveUserKey, sysUserList.get(i).getUserKey())) {
                sysUserList.remove(i);
                break;
            }
        }
        moveSysUser.setModifyUserInfo(userKey, realName);
        // ??????????????????????????????????????????????????????????????????????????????
        // ????????????????????????????????????(???????????????)
        SysUser realBeforeSysUser = null;
        // ?????????????????????beforeUserKey?????????,????????????????????????????????????????????????(???????????????)
        if (StrUtil.isEmpty(beforeUserKey)) {
            for (SysUser dbSysUser : sysUserList) {
                if (StrUtil.equals(firstUserKey, dbSysUser.getUserKey())) {
                    break;
                } else {
                    realBeforeSysUser = dbSysUser;
                }
            }
        }
        // ????????????,???????????????
        else {
            realBeforeSysUser = sysUserMapper.selectByPrimaryKey(beforeUserKey);
        }
        // ????????????????????????????????????(???????????????)
        SysUser realAfterSysUser = null;
        // ?????????????????????afterUserKey?????????,?????????????????????????????????????????????(???????????????)
        if (StrUtil.isEmpty(afterUserKey)) {
            for (int i = sysUserList.size() - 1; i >= 0; i--) {
                if (StrUtil.equals(lastUserKey, sysUserList.get(i).getUserKey())) {
                    break;
                } else {
                    realAfterSysUser = sysUserList.get(i);
                }
            }
        }
        // ????????????,???????????????
        else {
            realAfterSysUser = sysUserMapper.selectByPrimaryKey(afterUserKey);
        }
        // ???????????????????????????????????????????????????????????????sort(????????????????????????)
        // befor???after????????????????????????????????????????????????
        if (realBeforeSysUser != null && realAfterSysUser != null) {
            for (int i = 0; i < sysUserList.size(); i++) {
                sysUserList.get(i).setSort(i);
                if (StrUtil.equals(sysUserList.get(i).getUserKey(), realBeforeSysUser.getUserKey())) {
                    // ?????????????????????????????????
                    sysUserList.add(i + 1, moveSysUser);
                }
            }
            // ?????????????????????????????????????????????????????????
            flag = sysUserMapper.batchUpdateSysUser(sysUserList);
        }
        // ??????befor??????????????????????????????????????????????????????????????????????????????
        else if (realBeforeSysUser != null && realAfterSysUser == null) {
            moveSysUser.setSort(realBeforeSysUser.getSort() + 1);
            flag = sysUserMapper.updateByPrimaryKey(moveSysUser);
        }
        // ??????after?????????????????????????????????????????????????????????????????????????????????
        else if (realBeforeSysUser == null && realAfterSysUser != null) {
            // ?????????????????????????????????
            sysUserList.add(0, moveSysUser);
            for (int i = 0; i < sysUserList.size(); i++) {
                sysUserList.get(i).setSort(i);
            }
            // ??????????????????????????????????????????????????????
            flag = sysUserMapper.batchUpdateSysUser(sysUserList);
        }
        // ??????
        if (flag == 0) {
            throw new Apih5Exception("??????????????????!");
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
//            return repEntity.layerMessage("no", "???????????????");
//        }
        // ?????????????????????
        if (!Validator.isNumber(sysUser.getMobile())) {
            return repEntity.layerMessage("no", "????????????????????????????????????");
        }
        // ????????????????????????????????????
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
            // ??????
            dbSysUser.setModifyUserInfo(userKey, userName);
            flag = sysUserMapper.updateByPrimaryKey(dbSysUser);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            // ??????key
            String userEhCacheKey = dbSysUser.getUserKey() + "-" + accountId;
            // ??????token??????
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
        // ??????
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
        // ????????????
        PageHelper.startPage(sysUser.getPage(), sysUser.getLimit());
        // ??????????????????????????????????????????????????????
        boolean isSearchDepartmentId = publicConfig.getBooleanProperty("isSearchDepartmentId", false);
        if (isSearchDepartmentId) {
            // ????????????
            if (StrUtil.isNotEmpty(sysUser.getSearch())) {
                sysUser.setCompanyId(sysUser.getDepartmentId());
                sysUser.setDepartmentId("");
            }

            // ???????????????????????????????????????????????????id?????????
            if(StrUtil.isNotEmpty(sysUser.getRealName())
                    || StrUtil.isNotEmpty(sysUser.getUserId())
                    || StrUtil.isNotEmpty(sysUser.getMobile())) {
                if(StrUtil.equals("admin", userId) || StrUtil.equals("1", ext1)) {
//                    sysUser.setCompanyId(sysUser.getDepartmentId());
                    sysUser.setDepartmentId("");
                } else {
                    sysUser.setDepartmentId("");
                    // DepartmentPath???????????????
                    sysUser.setCompanyId(sysUser.getRootId());
                }
            }
        } else {
            // ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
            if(!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)
                    && (StrUtil.equals("9999999999", sysUser.getDepartmentId()) || StrUtil.equals("0", sysUser.getDepartmentId())) && StrUtil.isNotEmpty(companyId)) {
                sysUser.setCompanyId(companyId);
            } else {
                sysUser.setCompanyId(sysUser.getDepartmentId());
            }
            sysUser.setDepartmentId("");
        }

        List<SysUser> sysUserList = userMapper.getUserListByRoleAndCompanyId(sysUser);
        // ????????????????????????????????????????????????
        for (SysUser dbSysUser : sysUserList) {
            SysDepartment sysDepartment = new SysDepartment();
            sysDepartment.setUserKey(dbSysUser.getUserKey());
            List<SysDepartment> sysDepartmentList = sysDepartmentMapper.getSysDepartmentListByUserkey(sysDepartment);
            // ???labele???value??????
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
            // ??????userKey???deptId????????????????????????
            if (StrUtil.isNotEmpty(departmentId)) {
                // ??????departmentId??????projectId
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
        // ??????????????????
        PageInfo<SysUser> p = new PageInfo<>(sysUserList);
        return repEntity.okList(sysUserList, p.getTotal());
    }
}
