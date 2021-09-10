package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apih5.framework.api.sysdb.entity.BaseCode;
import com.apih5.framework.api.sysdb.entity.SysCompany;
import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.api.sysdb.entity.SysUserDepartment;
import com.apih5.framework.api.sysdb.service.BaseCodeService;
import com.apih5.framework.api.sysdb.service.SysDepartmentService;
import com.apih5.framework.api.wechatenterprise.entity.json.request.addressbook.DepartmentInfoReq;
import com.apih5.framework.api.wechatenterprise.service.WeChatEnterpriseDbService;
import com.apih5.framework.api.wechatenterprise.service.WeChatEnterpriseService;
import com.apih5.framework.api.zjoa.entity.OADepartment;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.ConfigConst;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.entity.TreeNodeEntity;
import com.apih5.framework.entity.TreeNodeMerger;
import com.apih5.framework.exception.Apih5Exception;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.framework.utils.ConvertUtil;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.BaseAccountMapper;
import com.apih5.mybatis.dao.BaseCodeMapper;
import com.apih5.mybatis.dao.SysCompanyMapper;
import com.apih5.mybatis.dao.SysDepartmentMapper;
import com.apih5.mybatis.dao.SysDepartmentPermissionMapper;
import com.apih5.mybatis.dao.SysFlowUserMapper;
import com.apih5.mybatis.dao.SysFlowUserProjectMapper;
import com.apih5.mybatis.dao.SysProjectMapper;
import com.apih5.mybatis.dao.SysRoleUserMapper;
import com.apih5.mybatis.dao.SysUserDepartmentMapper;
import com.apih5.mybatis.dao.SysUserMapper;
import com.apih5.mybatis.pojo.BaseAccount;
import com.apih5.mybatis.pojo.SysDepartmentPermission;
import com.apih5.mybatis.pojo.SysFlowUser;
import com.apih5.mybatis.pojo.SysFlowUserProject;
import com.apih5.mybatis.pojo.SysProject;
import com.apih5.mybatis.pojo.SysRoleUser;
import com.apih5.service.SysProjectService;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

@Service("sysDepartmentService")
public class SysDepartmentServiceImpl implements SysDepartmentService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @ApolloConfig(ConfigConst.PUBLIC_OTHER_API)
    private Config publicConfig;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private WeChatEnterpriseService wechatEnterpriseService;

    @Autowired(required = true)
    private WeChatEnterpriseDbService weChatEnterpriseDbService;

    @Autowired(required = true)
    private SysUserMapper sysUserMapper;

    @Autowired(required = true)
    private SysDepartmentMapper sysDepartmentMapper;

    @Autowired(required = true)
    private SysCompanyMapper sysCompanyMapper;

    @Autowired(required = true)
    private SysUserDepartmentMapper sysUserDepartmentMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;
    @Autowired(required = true)
    private SysDepartmentPermissionMapper sysDepartmentPermissionMapper;
    @Autowired(required = true)
    private SysRoleUserMapper sysRoleUserMapper;
    @Autowired(required = true)
    private BaseAccountMapper baseAccountMapper;
    @Autowired(required = true)
    private BaseCodeService baseCodeService;
    @Autowired(required = true)
    private BaseCodeMapper baseCodeMapper;
    @Autowired(required = true)
    private SysProjectService sysProjectService;
    @Autowired(required = true)
    private SysProjectMapper sysProjectMapper;
    @Autowired(required = true)
    private SysFlowUserMapper sysFlowUserMapper;
    @Autowired(required = true)
    private SysFlowUserProjectMapper sysFlowUserProjectMapper;

    public static Map<String, TreeNodeEntity> zjUserMap = new HashMap<String, TreeNodeEntity>();
    public static Map<String, JSONArray> cacheMap = new HashMap<String, JSONArray>();

    @Override
    public ResponseEntity getSysDepartmentListByCondition(SysDepartment sysDepartment) {
        if (sysDepartment == null) {
            sysDepartment = new SysDepartment();
        }
        // 分页查询
        PageHelper.startPage(sysDepartment.getPage(), sysDepartment.getLimit());
        // 获取数据
        List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
        // 得到分页信息
        PageInfo<SysDepartment> p = new PageInfo<>(sysDepartmentList);

        return repEntity.okList(sysDepartmentList, p.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity saveSysDepartment(SysDepartment sysDepartment) throws Exception {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String userName = TokenUtils.getRealName(request);

        if (StrUtil.isEmpty(sysDepartment.getDepartmentId())) {
            sysDepartment.setDepartmentId(UuidUtil.generate());
        }
        
        // 同一个父节点下不要有重复名称
        SysDepartment sysDepartmentRepeat = new SysDepartment();
        sysDepartmentRepeat.setDepartmentName(sysDepartment.getDepartmentName());

        String weChatParentid = "";
        
        // pid如果在项目中存在，说明是项目表，则按照项目表规则添加内容
        SysProject dbSysProject = sysProjectMapper.selectByPrimaryKey(sysDepartment.getDepartmentParentId());
        if(dbSysProject != null) {
            SysProject sysProject = new SysProject();
            // 复制内容
            BeanUtil.copyProperties(sysDepartment, sysProject, true);
            
//            sysDepartmentRepeat.setDepartmentParentId(sysDepartment.getDepartmentParentId());
//            int countName = sysDepartmentMapper.countRepeatSysDepartmentName(sysDepartmentRepeat);
//            if(countName > 0) {
//                return repEntity.layerMessage("no", "名称重复！");
//            }
            if(StrUtil.isEmpty(sysProject.getOtherId())) {
                sysProject.setOtherId(String.valueOf(sequenceService.getSequenceNextval("wechat_department_id")));
            }
            
            // 查询上一个节点ID
            sysProject.setDepartmentPath(dbSysProject.getDepartmentPath() + "," + sysProject.getDepartmentId());
            sysProject.setDepartmentPathName(dbSysProject.getDepartmentPathName() + "," + sysProject.getDepartmentName());
            
            // 说明外部传了
            if(sysProject.getSort() == 0) {
                sysProject.setSort(9999);
            } 
            
            // 部门类型 1:集团；2:公司；3:部门；4:项目；5:小组；6:虚拟层级
            if(StrUtil.isNotEmpty(sysProject.getDepartmentFlag())) {
                String departmentFlagName = baseCodeService.getBaseCodeItemName("departmentType", sysDepartment.getDepartmentFlag());
                sysProject.setDepartmentFlagName(departmentFlagName);
            }
            
            sysProject.setCompanyId(dbSysProject.getCompanyId());
            sysProject.setCompanyName(dbSysProject.getCompanyName());
            sysProject.setProjectId(dbSysProject.getProjectId());
            sysProject.setProjectName(dbSysProject.getProjectName());
            
            sysProject.setCreateUserInfo(userKey, userName);
            int flag = sysProjectMapper.insert(sysProject);
            if (flag == 0) {
                return repEntity.errorSave();
            }
            // 复制内容
            BeanUtil.copyProperties(sysProject, sysDepartment, true);
        } else {
            sysDepartmentRepeat.setDepartmentParentId(sysDepartment.getDepartmentParentId());
            int countName = sysDepartmentMapper.countRepeatSysDepartmentName(sysDepartmentRepeat);
            if(countName > 0) {
                return repEntity.layerMessage("no", "名称重复！");
            }
            if(StrUtil.isEmpty(sysDepartment.getOtherId())) {
                sysDepartment.setOtherId(String.valueOf(sequenceService.getSequenceNextval("wechat_department_id")));
            }
            // 查询上一个节点ID
            SysDepartment sysDepartmentDb = sysDepartmentMapper.selectByPrimaryKey(sysDepartment.getDepartmentParentId());
            if (sysDepartmentDb != null) {
                sysDepartment.setDepartmentPath(sysDepartmentDb.getDepartmentPath() + "," + sysDepartment.getDepartmentId());
                sysDepartment.setDepartmentPathName(sysDepartmentDb.getDepartmentPathName() + "," + sysDepartment.getDepartmentName());
            } else {
                sysDepartment.setDepartmentPath(sysDepartment.getDepartmentId());
                sysDepartment.setDepartmentPathName(sysDepartment.getDepartmentName());
            }
            weChatParentid = sysDepartmentDb.getOtherId();
            // 说明外部传了
            if(sysDepartment.getSort() == 0) {
                sysDepartment.setSort(9999);
            } 
            
            // 部门类型 1:集团；2:公司；3:部门；4:项目；5:小组；6:虚拟层级
            if(StrUtil.isNotEmpty(sysDepartment.getDepartmentFlag())) {
                String departmentFlagName = baseCodeService.getBaseCodeItemName("departmentType", sysDepartment.getDepartmentFlag());
                sysDepartment.setDepartmentFlagName(departmentFlagName);
            }
            
            // 公司类型
            if(StrUtil.equals("2", sysDepartment.getDepartmentFlag())) {
                if(StrUtil.isNotEmpty(sysDepartmentDb.getCompanyId())) {
                    return repEntity.layerMessage("no", "公司类型（公司名称：" + sysDepartmentDb.getCompanyName() + "）下不允许在建项目！");
                }
                // 所属公司
                sysDepartment.setCompanyId(sysDepartment.getDepartmentId());
                sysDepartment.setCompanyName(sysDepartment.getDepartmentName());

                // 插入公司数据
                SysCompany sysCompany = new SysCompany();
                BeanUtil.copyProperties(sysDepartment, sysCompany, true);
                sysCompany.setCreateUserInfo(userKey, userName);
                sysCompanyMapper.insert(sysCompany);
            } else if(StrUtil.equals("4", sysDepartment.getDepartmentFlag())) {
                if(StrUtil.isNotEmpty(sysDepartmentDb.getProjectId())) {
                    return repEntity.layerMessage("no", "项目类型（项目名称：" + sysDepartmentDb.getProjectName() + "）下不允许在建项目！");
                }
                // 项目类型
                sysDepartment.setCompanyId(sysDepartmentDb.getCompanyId());
                sysDepartment.setCompanyName(sysDepartmentDb.getCompanyName());
                // 所属项目
                sysDepartment.setProjectId(sysDepartment.getDepartmentId());
                sysDepartment.setProjectName(sysDepartment.getDepartmentName());
            } else {
                sysDepartment.setCompanyId(sysDepartmentDb.getCompanyId());
                sysDepartment.setCompanyName(sysDepartmentDb.getCompanyName());
                sysDepartment.setProjectId(sysDepartmentDb.getProjectId());
                sysDepartment.setProjectName(sysDepartmentDb.getProjectName());
            }
            
            sysDepartment.setCreateUserInfo(userKey, userName);
            int flag = sysDepartmentMapper.insert(sysDepartment);
            if (flag == 0) {
                return repEntity.errorSave();
            }
        }

        // 同步微信
        if (Apih5Properties.isUseSyncWeChat()) {
            List<BaseAccount> baseAccountList = baseAccountMapper.selectByBaseAccountListByLike("_txl");
            String accountId = baseAccountList.get(0).getAccountId();
            Map<String, String> accessTokenMap = weChatEnterpriseDbService.getWeChatAccessToken(accountId);
            String accessToken = accessTokenMap.get("accessToken");
            Map<String, String> getParamMap = new HashMap<String, String>();
            getParamMap.put("access_token", accessToken);
            JSONObject jsonObjectReq = new JSONObject();
            jsonObjectReq.set("id", sysDepartment.getOtherId());
            jsonObjectReq.set("name", sysDepartment.getDepartmentName());
            // 通过pid查询到该节点的微信pid
            jsonObjectReq.set("parentid", weChatParentid);
            jsonObjectReq.set("order", sysDepartment.getSort());
            LoggerUtils.printDebugLogger(logger, "微信同步新增部门-accessToken==" + accessToken);
            LoggerUtils.printDebugLogger(logger, "微信同步新增部门- ==" + jsonObjectReq.toString());
            JSONObject jsonObject = wechatEnterpriseService.coreServiceByResurceAddressbook(2, getParamMap,
                    jsonObjectReq);
            if (jsonObject == null || !StrUtil.equals("0", jsonObject.getStr("errcode"))) {
                LoggerUtils.printExceptionLogger(logger, "微信同步新增部门错误信息" + jsonObject.toString());
                throw new Apih5Exception("更新失败!" + jsonObject.getStr("errcode"));
            }
        }
        
        // 清缓存
        cacheMap.clear();
        return repEntity.ok("sys.data.sava", sysDepartment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity updateSysDepartment(SysDepartment sysDepartment) throws Exception {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String userName = TokenUtils.getRealName(request);
        int flag = 0;
        String departmentFlagOld = "";
        SysDepartment dbsysDepartment = sysDepartmentMapper.selectByPrimaryKey(sysDepartment.getDepartmentId());
        if(dbsysDepartment == null) {
            return repEntity.layerMessage("no", "项目数据无法修改，请到项目管理模块修改！");
        }
        if (dbsysDepartment != null && StrUtil.isNotEmpty(dbsysDepartment.getDepartmentId())) {
            departmentFlagOld = dbsysDepartment.getDepartmentFlag();
            // 同一个父节点下不要有重复名称
            SysDepartment sysDepartmentRepeat = new SysDepartment();
            sysDepartmentRepeat.setDepartmentName(dbsysDepartment.getDepartmentName());
            
            // 后台维护时，传这list过来
//            JSONArray departmentParentJSONArray = sysDepartment.getDepartmentParentList();
//            if(departmentParentJSONArray != null && departmentParentJSONArray.size()>0) {
//                JSONObject jsonObject = new JSONObject(departmentParentJSONArray.get(0));
//                sysDepartmentRepeat.setDepartmentParentId(jsonObject.getStr("value"));
//            } else {
//                sysDepartmentRepeat.setDepartmentParentId(sysDepartment.getDepartmentParentId());
//            }
            
            sysDepartmentRepeat.setDepartmentParentId(dbsysDepartment.getDepartmentParentId());
            List<SysDepartment> sysDepartmentRepeatList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartmentRepeat);
            if(sysDepartmentRepeatList != null && sysDepartmentRepeatList.size()>1) {
                String departmentName = sysDepartmentRepeatList.get(0).getDepartmentName();
                if(StrUtil.equals(sysDepartment.getDepartmentName(), departmentName)) {
                    return repEntity.layerMessage("no", "名称重复！");
                }
            }
            
            // 需要改变父节点，暂时因为层级没有改变，可以不用动整个地方，层级变化时，这个地方需要改代码
            // 部门名称
            dbsysDepartment.setDepartmentName(sysDepartment.getDepartmentName());
            String pathNameOld = dbsysDepartment.getDepartmentPathName();
            // 部门全路径
            String pathName = dbsysDepartment.getDepartmentPathName().substring(0, dbsysDepartment.getDepartmentPathName().lastIndexOf(","));
                   pathName += "," + sysDepartment.getDepartmentName();
            dbsysDepartment.setDepartmentPathName(pathName);
            
            
            // 部门类型 1:集团；2:公司；3:部门；4:项目；5:小组；6:虚拟层级
            if(StrUtil.isNotEmpty(sysDepartment.getDepartmentFlag())) {
                dbsysDepartment.setDepartmentFlag(sysDepartment.getDepartmentFlag());
                String departmentFlagName = baseCodeService.getBaseCodeItemName("departmentType", sysDepartment.getDepartmentFlag());
                dbsysDepartment.setDepartmentFlagName(departmentFlagName);
            }
            
            SysDepartment sysDepartmentDb = sysDepartmentMapper.selectByPrimaryKey(dbsysDepartment.getDepartmentParentId());
            // 公司类型
            if(StrUtil.equals("2", sysDepartment.getDepartmentFlag())) {
                // 所属公司
                if(StrUtil.isNotEmpty(sysDepartmentDb.getCompanyId())) {
                    return repEntity.layerMessage("no", "公司类型（公司名称：" + sysDepartmentDb.getCompanyName() + "）下不允许在建项目！");
                }
                dbsysDepartment.setCompanyId(sysDepartment.getDepartmentId());
                dbsysDepartment.setCompanyName(sysDepartment.getDepartmentName());
            } else if(StrUtil.equals("4", sysDepartment.getDepartmentFlag())) {
                if(StrUtil.isNotEmpty(sysDepartmentDb.getProjectId())) {
                    return repEntity.layerMessage("no", "项目类型（项目名称：" + sysDepartmentDb.getProjectName() + "）下不允许在建项目！");
                }
                // 项目类型
                dbsysDepartment.setCompanyId(sysDepartmentDb.getCompanyId());
                dbsysDepartment.setCompanyName(sysDepartmentDb.getCompanyName());
                // 所属项目
                dbsysDepartment.setProjectId(sysDepartment.getDepartmentId());
                dbsysDepartment.setProjectName(sysDepartment.getDepartmentName());
            } else {
                dbsysDepartment.setCompanyId(sysDepartmentDb.getCompanyId());
                dbsysDepartment.setCompanyName(sysDepartmentDb.getCompanyName());
                dbsysDepartment.setProjectId(sysDepartmentDb.getProjectId());
                dbsysDepartment.setProjectName(sysDepartmentDb.getProjectName());
            }
            sysDepartment.setCompanyId(dbsysDepartment.getCompanyId());
            sysDepartment.setCompanyId(dbsysDepartment.getCompanyName());
            sysDepartment.setProjectId(dbsysDepartment.getProjectId());
            sysDepartment.setProjectName(dbsysDepartment.getProjectName());
        
            // 排序
            dbsysDepartment.setSort(sysDepartment.getSort());   
            // 共通
            dbsysDepartment.setModifyUserInfo(userKey, userName);
            flag = sysDepartmentMapper.updateByPrimaryKey(dbsysDepartment);

            // 其他相关修改
            SysDepartment dbsysDepartmentSelect = new SysDepartment();
            dbsysDepartmentSelect.setDepartmentPath(dbsysDepartment.getDepartmentPath());
            List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(dbsysDepartmentSelect);
            for (SysDepartment dbSysDepartment : sysDepartmentList) {
                String pathNameOther = dbSysDepartment.getDepartmentPathName();
                pathNameOther = pathNameOther.replaceAll(pathNameOld, pathName);
                dbSysDepartment.setDepartmentPathName(pathNameOther);
                sysDepartmentMapper.updateByPrimaryKey(dbSysDepartment);
            }
            
            // 公司相关数据,类型不相同时
            if(!StrUtil.equals(departmentFlagOld, sysDepartment.getDepartmentFlag())) {
                if(StrUtil.equals(SysConst.SYS_DEPARTMENT_TYPE_COMPANY, departmentFlagOld)
                        || StrUtil.equals(SysConst.SYS_DEPARTMENT_TYPE_COMPANY, sysDepartment.getDepartmentFlag())) {
                    // 旧数据如果是公司，则删除，否则新增
                    if(StrUtil.equals(SysConst.SYS_DEPARTMENT_TYPE_COMPANY, departmentFlagOld)) {
                        sysCompanyMapper.deleteByPrimaryKey(dbsysDepartment.getDepartmentId());
                    } else {
                        SysCompany sysCompany = new SysCompany();
                        BeanUtil.copyProperties(dbsysDepartment, sysCompany, true);
                        sysCompany.setCreateUserInfo(userKey, userName);
                        sysCompanyMapper.insert(sysCompany);
                    }
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }

        // 同步微信
        if (Apih5Properties.isUseSyncWeChat()) {
            List<BaseAccount> baseAccountList = baseAccountMapper.selectByBaseAccountListByLike("_txl");
            String accountId = baseAccountList.get(0).getAccountId();
            SysDepartment parentSysDepartment = sysDepartmentMapper.selectByPrimaryKey(sysDepartment.getDepartmentParentId());
            Map<String, String> accessTokenMap = weChatEnterpriseDbService.getWeChatAccessToken(accountId);
            String accessToken = accessTokenMap.get("accessToken");
            Map<String, String> getParamMap = new HashMap<String, String>();
            getParamMap.put("access_token", accessToken);
            DepartmentInfoReq departmentInfoReq = new DepartmentInfoReq();
            departmentInfoReq.setId(dbsysDepartment.getOtherId());
            departmentInfoReq.setName(dbsysDepartment.getDepartmentName());
            departmentInfoReq.setParentid(parentSysDepartment.getOtherId());
            JSONObject jsonObject = wechatEnterpriseService.coreServiceByResurceAddressbook(3, getParamMap,
                    departmentInfoReq);
            if (jsonObject == null || !StrUtil.equals("0", jsonObject.getStr("errcode"))) {
                LoggerUtils.printDebugLogger(logger, "微信同步新增部门错误信息" + jsonObject.toString());
                // 之前没有做过同步，所以更新失败时，再重新添加一次
                accessTokenMap = weChatEnterpriseDbService.getWeChatAccessToken(accountId);
                accessToken = accessTokenMap.get("accessToken");
                getParamMap = new HashMap<String, String>();
                getParamMap.put("access_token", accessToken);
                JSONObject jsonObjectReq = new JSONObject();
                jsonObjectReq.set("id", sysDepartment.getOtherId());
                jsonObjectReq.set("name", sysDepartment.getDepartmentName());
                jsonObjectReq.set("parentid", parentSysDepartment.getOtherId());
                jsonObjectReq.set("order", sysDepartment.getSort());
                jsonObject = wechatEnterpriseService.coreServiceByResurceAddressbook(2, getParamMap, jsonObjectReq);
                if (jsonObject == null || !StrUtil.equals("0", jsonObject.getStr("errcode"))) {
                    LoggerUtils.printExceptionLogger(logger, "微信同步新增部门错误信息" + jsonObject.toString());
                    throw new Apih5Exception("更新失败!" + jsonObject.getStr("errcode"));
                }
            }
        }

        // 清缓存
        cacheMap.clear();
        return repEntity.ok("sys.data.update", dbsysDepartment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity moveUpdateSysDepartment(SysDepartment sysDepartment) throws Exception {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        // 移动后的部门的新父节点(一定存在)
        String parentStr = sysDepartment.getDepartmentParent();
        // 要移动的部门(一定存在)
        String toMoveStr = sysDepartment.getDepartmentToMove();
        // 移动后的部门位于此节点前面【也就是移动后部门的后面的节点】
        String afterStr = sysDepartment.getDepartmentAfter();
        // 移动后的部门位于此节点后面【也就是移动后部门的前面的节点】
        String beforeStr = sysDepartment.getDepartmentBefore();

        // 父节点对象(一定存在)
        SysDepartment sysDepartmentParent = sysDepartmentMapper.selectByPrimaryKey(parentStr);
        // 要移动节点对象(一定存在)
        SysDepartment sysDepartmentToMove = sysDepartmentMapper.selectByPrimaryKey(toMoveStr);
        // 上一个节点对象(可能存在)
        SysDepartment sysDepartmentBefore = sysDepartmentMapper.selectByPrimaryKey(beforeStr);
        if (sysDepartmentParent == null || sysDepartmentToMove == null) {
            return repEntity.layerMessage("no", "传入参数有误!");
        }
        // 暂存要移动节点原属性
        String oldDepartmentParentId = sysDepartmentToMove.getDepartmentParentId();
        String oldDepartmentPath = sysDepartmentToMove.getDepartmentPath();
        String oldDepartmentPathName = sysDepartmentToMove.getDepartmentPathName();
        // 获取新父节点下原有的所有子节点集合
        SysDepartment department = new SysDepartment();
        department.setDepartmentParentId(parentStr);
        List<SysDepartment> list = sysDepartmentMapper.selectBySysDepartmentList(department);
        LinkedList<SysDepartment> sysDepartmentList = new LinkedList<SysDepartment>(list);
        // 如果要移动的节点本来就在当前父节点集合下则先将其移除
        if (sysDepartmentList.size() > 0 && StrUtil.equals(oldDepartmentParentId, parentStr)) {
            for (int i = 0; i < sysDepartmentList.size(); i++) {
                // 从数据集合中先移出当前移动节点
                if (StrUtil.equals(toMoveStr, sysDepartmentList.get(i).getDepartmentId())) {
                    sysDepartmentList.remove(i);
                    break;
                }
            }
        } else {
            // 如果要移动的节点不在当前父节点集合下则重新构建移动后节点数据
            sysDepartmentToMove.setDepartmentPath(
                    sysDepartmentParent.getDepartmentPath() + "," + sysDepartmentToMove.getDepartmentId());
            sysDepartmentToMove.setDepartmentPathName(
                    sysDepartmentParent.getDepartmentPathName() + "," + sysDepartmentToMove.getDepartmentName());
            sysDepartmentToMove.setDepartmentParentId(parentStr);
            // 如果要移动的节点不在当前父节点集合下则需要处理要移动节点的下级节点数据
            SysDepartment sysDepartmentCondition = new SysDepartment();
            sysDepartmentCondition.setDepartmentPath(sysDepartmentToMove.getDepartmentId());
            int count = sysDepartmentMapper.countSysDepartmentListByCondition(sysDepartmentCondition);
            if (count > 1) {
                sysDepartmentCondition.setOldDepartmentPath(oldDepartmentPath);
                sysDepartmentCondition.setOldDepartmentPathName(oldDepartmentPathName);
                sysDepartmentCondition.setDepartmentPath(sysDepartmentToMove.getDepartmentPath());
                sysDepartmentCondition.setDepartmentPathName(sysDepartmentToMove.getDepartmentPathName());
                flag = sysDepartmentMapper.batchUpdateDepartmentPathAndDepartmentPathName(sysDepartmentCondition);
                if (flag < 2) {
                    throw new Apih5Exception("处理要移动节点的子节点数据失败!");
                }
            }
        }
        sysDepartmentToMove.setModifyUserInfo(userKey, realName);
        // 处理要移动节点和移动后节点的同级兄弟节点的sort(分为以下四种情况)
        // befor、after同时存在时、说明移动到了中间位置
        if (StrUtil.isNotEmpty(beforeStr) && StrUtil.isNotEmpty(afterStr)) {
            for (int i = 0; i < sysDepartmentList.size(); i++) {
                sysDepartmentList.get(i).setSort(i);
                if (StrUtil.equals(sysDepartmentList.get(i).getDepartmentId(), beforeStr)) {
                    // 将要移动的节点加入集合
                    sysDepartmentList.add(i + 1, sysDepartmentToMove);
                }
            }
            // 将节点放在中间每次移动都将批量修改集合
            flag = sysDepartmentMapper.batchUpdateSysDepartment(sysDepartmentList);
        }
        // 只有befor存在时、说明移动到了最后面，移动后的节点后面没有内容
        else if (StrUtil.isNotEmpty(beforeStr) && StrUtil.isEmpty(afterStr)) {
            sysDepartmentToMove.setSort(sysDepartmentBefore.getSort() + 1);
            flag = sysDepartmentMapper.updateByPrimaryKey(sysDepartmentToMove);
        }
        // 只有after存在时、说明移动到了最前面面，移动后的节点前面没有内容
        else if (StrUtil.isEmpty(beforeStr) && StrUtil.isNotEmpty(afterStr)) {
            // 将要移动的节点加入集合
            sysDepartmentList.add(0, sysDepartmentToMove);
            for (int i = 0; i < sysDepartmentList.size(); i++) {
                sysDepartmentList.get(i).setSort(i);
            }
            // 将节点移到最前面每次都将批量修改集合
            flag = sysDepartmentMapper.batchUpdateSysDepartment(sysDepartmentList);
        }
        // befor、after都不存在时，说明前后不存在节点（即当前父节点下只有一个节点）
        else if (StrUtil.isEmpty(beforeStr) && StrUtil.isEmpty(afterStr)) {
            if (sysDepartmentList.size() > 0) {
                // 将插入的放在最后面
                sysDepartmentToMove.setSort(sysDepartmentList.get(sysDepartmentList.size() - 1).getSort() + 1);
            } else {
                // 随意设置一个排序即可
                sysDepartmentToMove.setSort(1);
            }
            flag = sysDepartmentMapper.updateByPrimaryKey(sysDepartmentToMove);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        } else {
            // 清缓存
            cacheMap.clear();
            return repEntity.ok("sys.data.update", sysDepartment);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity batchDeleteUpdateSysDepartment(List<SysDepartment> sysDepartmentList) throws Exception {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String token = TokenUtils.getToken(request);
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String otherId = sysDepartmentMapper.selectByPrimaryKey(sysDepartmentList.get(0).getDepartmentId()).getOtherId();
        int flag = 0;
        if (sysDepartmentList != null && sysDepartmentList.size() > 0) {
            for (SysDepartment sysDepartment : sysDepartmentList) {
                // 增加其他模块，已授权的用户信息禁止删除（如劳务系统授权了组织机构，则删除人员时，需要有提示）
                String checkAuthorize = publicConfig.getProperty("check.authorize.delete.api", "");
                if(StrUtil.isNotEmpty(checkAuthorize)
                        && !StrUtil.equals("空", checkAuthorize)) {
                    String url = Apih5Properties.getWebUrl() + checkAuthorize;
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.set("value", sysDepartment.getDepartmentId());
                    String result = HttpUtil.sendPostToken(url, jsonObject.toString(), token);
                    JSONObject jsonResult = JSONUtil.parseObj(result);
                    if(jsonResult.getBool("success")) {
                        return repEntity.layerMessage("no", jsonResult.getStr("data"));
                    }
                }
                
                // 判断该部门下是否还有子部门
                SysDepartment sysDepartmentCheck = new SysDepartment();
                sysDepartmentCheck.setDepartmentPath(sysDepartment.getDepartmentId() + ",");
                List<SysDepartment> sysDepartmentListCheck = sysDepartmentMapper
                        .selectBySysDepartmentList(sysDepartmentCheck);
                if (sysDepartmentListCheck != null && sysDepartmentListCheck.size() > 0) {
                    return repEntity.layerMessage("no", "该部门下还有子部门，请先删除子部门。");
                }

                // 判断该部门下是否还有关联人员
                SysUserDepartment sysUserDepartment = new SysUserDepartment();
                sysUserDepartment.setDepartmentId(sysDepartment.getDepartmentId());
                List<SysUserDepartment> sysUserDepartmentList = sysUserDepartmentMapper
                        .selectBySysUserDepartmentList(sysUserDepartment);
                if (sysUserDepartmentList != null && sysUserDepartmentList.size() > 0) {
                    return repEntity.layerMessage("no", "该部门下存在其他用户，请先删除用户。");
                }
                
                // 删除公司
                SysCompany sysCompany = sysCompanyMapper.selectByPrimaryKey(sysDepartment.getDepartmentId());
                if(sysCompany != null) {
                    sysCompanyMapper.deleteByPrimaryKey(sysDepartment.getDepartmentId());
                }
            }
            
            // 删除部门数据
            SysDepartment sysDepartmentInfo = new SysDepartment();
            sysDepartmentInfo.setModifyUserInfo(userKey, realName);
            flag = sysDepartmentMapper.batchDeleteUpdateSysDepartment(sysDepartmentList, sysDepartmentInfo);
            
            // 删除角色菜单信息
            SysRoleUser sysRoleUser= new SysRoleUser();
            sysRoleUser.setValue(sysDepartmentList.get(0).getDepartmentId());
            List<SysRoleUser> sysRoleUserList = sysRoleUserMapper.selectBySysRoleUserList(sysRoleUser);
            if(sysRoleUserList != null && sysRoleUserList.size()>0) {
                sysRoleUserMapper.batchDeleteUpdateSysRoleUser(sysRoleUserList);
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }

        // 同步微信
        if (Apih5Properties.isUseSyncWeChat()) {
            List<BaseAccount> baseAccountList = baseAccountMapper.selectByBaseAccountListByLike("_txl");
            String accountId = baseAccountList.get(0).getAccountId();
            Map<String, String> accessTokenMap = weChatEnterpriseDbService.getWeChatAccessToken(accountId);
            String accessToken = accessTokenMap.get("accessToken");
            Map<String, String> getParamMap = new HashMap<String, String>();
            getParamMap.put("access_token", accessToken);
            getParamMap.put("id", otherId);
            JSONObject jsonObject = wechatEnterpriseService.coreServiceByResurceAddressbook(4, getParamMap, null);
            if (jsonObject == null || !StrUtil.equals("0", jsonObject.getStr("errcode"))) {
                LoggerUtils.printExceptionLogger(logger, "微信同步新增部门错误信息" + jsonObject.toString());
                throw new Apih5Exception("更新失败!" + jsonObject.getStr("errcode"));
            }
        }
        
        // 清缓存
        cacheMap.clear();
        return repEntity.ok("sys.data.delete", sysDepartmentList);
    }

    @Override
    public ResponseEntity deleteSysDepartment(SysDepartment sysDepartment) {
        if (sysDepartment == null) {
            return repEntity.layerMessage("no", "所选部门不能为空");
        }
        // 判断该部门下是否还有子部门
        SysDepartment sysDepartmentCheck = new SysDepartment();
        sysDepartmentCheck.setDepartmentPath(sysDepartment.getDepartmentId() + ",");
        List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartmentCheck);
        if (sysDepartmentList != null && sysDepartmentList.size() > 0) {
            return repEntity.layerMessage("no", "该部门下还有子部门，请先删除子部门。");
        }
        // 判断该部门下是否还有关联人员
        SysUserDepartment sysUserDepartment = new SysUserDepartment();
        sysUserDepartment.setDepartmentId(sysDepartment.getDepartmentId());
        List<SysUserDepartment> sysUserDepartmentList = sysUserDepartmentMapper
                .selectBySysUserDepartmentList(sysUserDepartment);
        if (sysUserDepartmentList != null && sysUserDepartmentList.size() > 0) {
            return repEntity.layerMessage("no", "该部门下存在其他用户，请先删除用户。");
        }

        int flag = sysDepartmentMapper.deleteByPrimaryKey(sysDepartment.getDepartmentId());
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", sysDepartmentList);
        }
    }

    /**
     * 根据用户Key获取部门信息
     * 
     * @param userkey
     * @return 部门信息
     */
    @Override
    public List<SysDepartment> getSysDepartmentByUserkey(String userkey) {
        SysDepartment sysDepartment = new SysDepartment();
        sysDepartment.setUserKey(userkey);
        // 获取数据
        List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectSysDepartmentByUserkey(sysDepartment);

        return sysDepartmentList;
    }
    
   /**
     * 根据用户Key获取部门信息
     * 
     * @param userkey
     * @return 部门信息
     */
    @Override
    public List<SysDepartment> getSysDepartmentProjectByUserkey(String userkey) {
        SysDepartment sysDepartment = new SysDepartment();
        sysDepartment.setUserKey(userkey);
        // 获取数据
        List<SysDepartment> sysDepartmentList = sysDepartmentMapper.getSysDepartmentProjectByUserkey(sysDepartment);

        return sysDepartmentList;
    }

    /**
     * 组织机构一步一步点击获取
     */
    @Override
    public ResponseEntity getSysDepartmentProListByCondition(SysDepartment sysDepartment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        if (sysDepartment == null) {
            sysDepartment = new SysDepartment();
        }

        // 根据权限获取对应的pid
        List sysDepartmentList = Lists.newArrayList();
        
        String userKey = TokenUtils.getUserKey(request);
        String ext1 = TokenUtils.getExt1(request);
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)) {
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司：第一次进来时判断顶级权限后，查询部门表数据
            if(StrUtil.equals("0", sysDepartment.getDepartmentParentId())) {
                SysDepartment sysDepartmentSelect = new SysDepartment(); 
                sysDepartmentSelect.setUserKey(userKey);
                List<SysDepartment> dbSysDepartmentList = sysDepartmentMapper.getSysDepartmentListByUserkey(sysDepartmentSelect);
                // 获取的数据时当前关联的部门，数据公司数据在companyId中，所以吧当前departmentId更换成companyId
                if(dbSysDepartmentList != null && dbSysDepartmentList.size()>0) {
                    for(SysDepartment dbSysDepartment:dbSysDepartmentList) {
                        dbSysDepartment.setDepartmentId(dbSysDepartment.getCompanyId());
                        dbSysDepartment.setDepartmentName(dbSysDepartment.getCompanyName());
                        // 获取当前部门的顶级节点对应的部门类型
                        SysDepartment companySysDepartment = sysDepartmentMapper.selectByPrimaryKey(dbSysDepartment.getCompanyId());
                        dbSysDepartment.setDepartmentFlag(companySysDepartment.getDepartmentFlag());
                        dbSysDepartment.setDepartmentFlagName(companySysDepartment.getDepartmentFlagName());
                    }
                }
                return repEntity.ok(dbSysDepartmentList);
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1) || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目：第一次进来时判断顶级权限后，查询项目表数据
            if(StrUtil.equals("0", sysDepartment.getDepartmentParentId())) {
                SysProject sysProjectSelect = new SysProject();
                sysProjectSelect.setUserKey(userKey);
                List<SysProject> sysProjectList = sysProjectMapper.selectBySysProjectListByUserKey(sysProjectSelect);
                // 获取的数据时当前关联的部门，数据公司数据在companyId中，所以吧当前departmentId更换成companyId
                if(sysProjectList != null && sysProjectList.size()>0) {
                    for(SysProject dbSysProject:sysProjectList) {
                        dbSysProject.setDepartmentId(dbSysProject.getProjectId());
                        dbSysProject.setDepartmentName(dbSysProject.getProjectName());
                    }
                }
                return repEntity.ok(sysProjectList);
            }
        }
        
    
        List<SysDepartment> dbSysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
        if(dbSysDepartmentList != null && dbSysDepartmentList.size()>0) {
            sysDepartmentList.addAll(dbSysDepartmentList);
        }

        // 获取部门表数据
        if(StrUtil.isNotEmpty(sysDepartment.getDepartmentParentId())) {
            SysProject sysProject = new SysProject();
            sysProject.setDepartmentParentId(sysDepartment.getDepartmentParentId());
            List<SysProject> dbSysProjectList = sysProjectMapper.selectBySysProjectList(sysProject);
            if(dbSysProjectList != null && dbSysProjectList.size()>0) {
                sysDepartmentList.addAll(dbSysProjectList);
            }
        }
        
        return repEntity.ok(sysDepartmentList);
    }

    /**
     * 获取当前登陆者的所有所在项目信息
     */
    @Override
    public ResponseEntity getSysDepartmentProList(SysDepartment sysDepartment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        if (sysDepartment == null) {
            sysDepartment = new SysDepartment();
        }
        sysDepartment.setUserKey(userKey);
//        sysDepartment.setDepartmentFlag(SysConst.SYS_DEPARTMENT_TYPE_PRO);
        // 获取数据
        List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectSysDepartmentByUserkey(sysDepartment);
        if(sysDepartmentList == null || sysDepartmentList.size()==0) {
            SysDepartment emptySysDepartment = new SysDepartment();
            emptySysDepartment.setProjectId("999");
            emptySysDepartment.setProjectName("无项目");
            sysDepartmentList.add(emptySysDepartment);
        }
        return repEntity.ok(sysDepartmentList);
    }
    
    /**
     * 获取当前登陆者的所有所在项目信息
     */
    @Override
    public ResponseEntity changeSysDepartmentPro(SysDepartment sysDepartment) {
//        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
//        String userKey = TokenUtils.getUserKey(request);
        if (sysDepartment == null) {
            sysDepartment = new SysDepartment();
        }
        
        // 切换项目的ID
        String changeProjectId = sysDepartment.getProjectId();
        if(StrUtil.isEmpty(changeProjectId)) {
            return repEntity.layerMessage("no", "请选择要切换的项目。");
        }
        SysDepartment dbSysDepartment = sysDepartmentMapper.selectByPrimaryKey(changeProjectId);
            
        return repEntity.ok(dbSysDepartment);
    }
    
    /**
     * 获取当前所选项目对应的部门（同一个项目项目下不考虑跨部门）
     */
    @Override
    public SysDepartment getSysDepartmentProDept(SysDepartment sysDepartment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        //String userKey = TokenUtils.getUserKey(request);
        if (sysDepartment == null) {
            sysDepartment = new SysDepartment();
        }
        SysDepartment sysDepartmentSelect = new SysDepartment();
        sysDepartmentSelect.setUserKey(sysDepartment.getUserKey());
        sysDepartmentSelect.setDepartmentPath(sysDepartment.getDepartmentId());
        // 获取数据
        List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectSysDepartmentByUserkey(sysDepartmentSelect);
        if(sysDepartmentList != null && sysDepartmentList.size()>0) {
            return sysDepartmentList.get(0);
        }
        return null;
    }

    @Override
    public ResponseEntity getSysDepartmentList(SysDepartment sysDepartment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String companyId = TokenUtils.getCompanyId(request);
        if (sysDepartment == null) {
            sysDepartment = new SysDepartment();
        }
        sysDepartment.setDepartmentPath(companyId);
        // 获取数据
        List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
        List<OADepartment> listDepartment = new ArrayList<OADepartment>();
        for (int i = 0; i < sysDepartmentList.size(); i++) {
            SysDepartment dbSysDepartment = sysDepartmentList.get(i);
            OADepartment department = new OADepartment();
            department.setId(dbSysDepartment.getDepartmentId());
            department.setParentid(dbSysDepartment.getDepartmentParentId());
            department.setName(dbSysDepartment.getDepartmentName());
            department.setOrgId(dbSysDepartment.getDepartmentId());
            department.setOrder(i);
            listDepartment.add(department);
        }
        return repEntity.okList(listDepartment, listDepartment.size());
    }

    @Override
    public ResponseEntity apiSysDepartmentList(SysDepartment sysDepartment) {
        if (sysDepartment == null) {
            sysDepartment = new SysDepartment();
        }
        // 获取数据
        List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
        List<OADepartment> listDepartment = new ArrayList<OADepartment>();
        for (int i = 0; i < sysDepartmentList.size(); i++) {
            SysDepartment dbSysDepartment = sysDepartmentList.get(i);
            OADepartment department = new OADepartment();
            department.setId(dbSysDepartment.getDepartmentId());
            department.setParentid(dbSysDepartment.getDepartmentParentId());
            department.setName(dbSysDepartment.getDepartmentName());
            department.setOrgId(dbSysDepartment.getDepartmentId());
            department.setOrder(i);
            listDepartment.add(department);
        }
        return repEntity.okList(listDepartment, listDepartment.size());
    }

    @Override
    public List<SysDepartment> getSysDepartmentListByUserkey(SysDepartment sysDepartment) {
        // 获取数据
        return sysDepartmentMapper.getSysDepartmentListByUserkey(sysDepartment);
    }

    @Override
    public int batchDeleteByDepIdAndUserList(SysDepartment sysDepartment) {
        return sysDepartmentMapper.batchDeleteByDepIdAndUserList(sysDepartment);
    }

    @Override
    public SysDepartment getSysDepartmentByPrimaryKey(String key) {
        return sysDepartmentMapper.selectByPrimaryKey(key);
    }

    /**
     * 获取登陆用户所属公司列表
     */
    @Override
    public List<SysCompany> selectCompanyListByMobile(String mobile, String accountAppType) {
        List<SysCompany> userCompanyList = new ArrayList<SysCompany>();
        Map<String, SysCompany> userCompanyMap = new java.util.HashMap<String, SysCompany>();
        // 获取全部公司 Map
        List<SysCompany> companyAllList = sysCompanyMapper.selectBySysCompanyList(new SysCompany());
        Map<String, SysCompany> companyAllMap = new HashMap<String, SysCompany>();
        for (SysCompany company : companyAllList) {
            companyAllMap.put(company.getCompanyId(), company);
        }

        // 公司存在
        if (companyAllMap != null) {
            // 添加公司到List
            // 获取用户所属组织结构（公司或者部门）
            // 根据手机号查找，该用户所有公司
            SysDepartment sysDepartmentMobile = new SysDepartment();
            sysDepartmentMobile.setMobile(mobile);
            sysDepartmentMobile.setAccountAppType(accountAppType);
            List<SysDepartment> userDepartList = sysDepartmentMapper.getSysDepartmentListByMobile(sysDepartmentMobile);
            // List<BaseMemberDepartment> userDepartList =
            // memberDepartmentMapper.selectMemDepartmentByMobile(mobile);
            // int count = 0;
            for (SysDepartment memDepart : userDepartList) {
                String userKey = memDepart.getUserKey();
                if (companyAllMap.containsKey(memDepart.getDepartmentId())) {
                    // 公司
                    // 去除重复公司
                    if (!userCompanyMap.containsKey(memDepart.getDepartmentId())) {
                        SysCompany userCompany = companyAllMap.get(memDepart.getDepartmentId());
                        userCompany.setUserKey(userKey);
                        // userCompany.setCompanyId(memDepart.getDepartmentId());
                        // userCompany.setCompanyName(memDepart.getDepartmentName());
                        userCompanyMap.put(memDepart.getDepartmentId(), userCompany);
                    }
                } else {
                    // 部门时，获取此部门的父部门
                    getDepartmentByOaId(userKey, userCompanyMap, companyAllMap, memDepart.getDepartmentId());
                }
                // count++;
            }
        }

        // Map 转List
        if (userCompanyMap.size() > 0) {
            for (Map.Entry<String, SysCompany> company : userCompanyMap.entrySet()) {
                userCompanyList.add(company.getValue());
            }
        }

        return userCompanyList;
    }

    /**
     * 更加userKey获取公司ID
     * 
     * @param userKey
     *            用户Key
     * @return 公司ID
     */
    @Override
    public String getCompanyIdByUserKey(String userKey) {
        // 防止递归死循环
        int count = 0;
        // 获取全部公司 Map
        List<SysCompany> companyAllList = sysCompanyMapper.selectBySysCompanyList(new SysCompany());
        Map<String, SysCompany> companyAllMap = new HashMap<String, SysCompany>();
        for (SysCompany company : companyAllList) {
            companyAllMap.put(company.getCompanyId(), company);
        }

        // 根据userKey,从用户部门关系表中获取部门ID
        SysUserDepartment sysUserDepartment = new SysUserDepartment();
        sysUserDepartment.setUserKey(userKey);
        List<SysUserDepartment> sysUserDepartmentList = sysUserDepartmentMapper
                .selectBySysUserDepartmentList(sysUserDepartment);
        if (sysUserDepartmentList == null || sysUserDepartmentList.size() == 0) {
            return "";
        }
        // 部门ID
        String departmentId = sysUserDepartmentList.get(0).getDepartmentId();
        // 部门ID如果与公司主键相等说明当前的部门就是公司，如果不是递归遍历
        if (companyAllMap.containsKey(departmentId)) {
            return departmentId;
        } else {
            return getDepartmentByOaId(companyAllMap, departmentId, count);
        }
    }

    public SysDepartment getSysDeptProByPrimaryKey(String key) {
        SysDepartment sysDepartment = sysDepartmentMapper.selectByPrimaryKey(key);
        if(sysDepartment == null) {
            sysDepartment = new SysDepartment();
            SysProject sysProject = sysProjectMapper.selectByPrimaryKey(key);
            BeanUtil.copyProperties(sysProject, sysDepartment, true);
        }
        return sysDepartment;
    }
    
//    /**
//     * 获取部门树形部门信息（通用版本）
//     * 
//     * @return 树形结构
//     */
//    @Override
//    public ResponseEntity getSysDepartmentTree(SysDepartment sysDepartment) {
//        if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
//            return getSysUserListByLikeTree(sysDepartment.getSearch());
//        }
//        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
//        String companyId = TokenUtils.getCompanyId(request);
//        String userKey = TokenUtils.getUserKey(request);
//        String permission = "";
//        if (sysDepartment == null) {
//            sysDepartment = new SysDepartment();
//        }
//        // sysDepartment.setDepartmentPath(companyId);
//        // if(StrUtil.isNotEmpty(sysDepartment.getDepartmentId())) {
//        // sysDepartment.setDepartmentPath(sysDepartment.getDepartmentId());
//        // }
//
//        // 获取全部的部门
//        List<SysDepartment> sysDepartmentList = Lists.newArrayList();
//        // // 是否有通过权限获取
//        // String authority = request.getParameter("authority");
//        // if(StrUtil.equals("1", authority) && !userKey.equals("admin")) {
//        // // 此授权只能是一个部门，如果是多个部门需要将多部门的顶级合并起来才可以
//        // SysDepartmentPermission sysDepartmentPermissionSelect = new
//        // SysDepartmentPermission();
//        // sysDepartmentPermissionSelect.setValue(userKey);
//        // List<SysDepartmentPermission> sysDepartmentPermissionList=
//        // sysDepartmentPermissionMapper.selectBySysDepartmentPermissionList(sysDepartmentPermissionSelect);
//        // if(sysDepartmentPermissionList == null ||
//        // sysDepartmentPermissionList.size() == 0) {
//        // return repEntity.layerMessage("no", "无权限查看");
//        // }
//        // SysDepartmentPermission dbSysDepartmentPermission =
//        // sysDepartmentPermissionList.get(0);
//        // String[] departmentIds =
//        // dbSysDepartmentPermission.getDepartmentIds().split(",");
//        // if(departmentIds != null && departmentIds.length > 0) {
//        // sysDepartment.setDepartmentId(departmentIds[0]);
//        // for(int i=0;i<departmentIds.length;i++) {
//        // SysDepartment sysDepartmentSelect = new SysDepartment();
//        // sysDepartmentSelect.setDepartmentPath(departmentIds[i]);
//        // List<SysDepartment> dbSysDepartmentList =
//        // sysDepartmentMapper.selectBySysDepartmentList(sysDepartmentSelect);
//        // sysDepartmentList.addAll(dbSysDepartmentList);
//        // }
//        // }
//        // } else {
//        // sysDepartmentList =
//        // sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
//        // }
//        //
//        // int i = 0;
//        // TreeNodeEntity[] treeNodes = new
//        // TreeNodeEntity[sysDepartmentList.size()];
//        // // 合并部门&人员信息 【特殊条件，类似分公司的时候departmentId,
//        // dbSysDepartment.getDepartmentId()】
//        // for (SysDepartment dbSysDepartment : sysDepartmentList) {
//        // // 判断顶级节点条件pid=0或null 或者 id=pid
//        // if (StrUtil.isEmpty(dbSysDepartment.getDepartmentParentId())
//        // || StrUtil.equals("0", dbSysDepartment.getDepartmentParentId())) {
//        // // 顶级节点
//        // treeNodes[i] = new TreeNodeEntity("0",
//        // dbSysDepartment.getDepartmentId(),
//        // dbSysDepartment.getDepartmentParentId(),
//        // dbSysDepartment.getDepartmentName(), "");
//        // } else {
//        // // 部门类型
//        // treeNodes[i] = new TreeNodeEntity("1",
//        // dbSysDepartment.getDepartmentId(),
//        // dbSysDepartment.getDepartmentParentId(),
//        // dbSysDepartment.getDepartmentName(), "");
//        // }
//        // i++;
//        // }
//        // TreeNodeEntity treeNode = TreeNodeMerger.merge(treeNodes);
//        // return repEntity.ok(treeNode);
//
//        if (!userKey.equals("admin")) {
//            if (!StrUtil.equals("1", permission)) {
//                if ((StrUtil.equals("2", permission)) || (StrUtil.equals("3", permission))) {
//                    sysDepartment.setDepartmentPath(companyId);
//                    sysDepartmentList = this.sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
//                } else if (!StrUtil.equals("3-temp", permission)) {
//                    if ((StrUtil.equals("4", permission)) || (StrUtil.equals("5", permission))) {
//                        SysDepartment sysDepartmentSelect = new SysDepartment();
//                        sysDepartmentSelect.setUserKey(userKey);
//                        List<SysDepartment> dbSysDepartmentList = this.sysDepartmentMapper
//                                .getSysDepartmentListByUserkey(sysDepartmentSelect);
//                        for (SysDepartment dbSysDepartment : dbSysDepartmentList) {
//                            sysDepartmentSelect = new SysDepartment();
//                            sysDepartmentSelect.setDepartmentPath(dbSysDepartment.getDepartmentId());
//                            List<SysDepartment> newDbSysDepartmentList = this.sysDepartmentMapper
//                                    .selectBySysDepartmentList(sysDepartmentSelect);
//                            sysDepartmentList.addAll(newDbSysDepartmentList);
//                        }
//                    } else if (!StrUtil.equals("5-temp", permission)) {
//                        if (!StrUtil.equals("6", permission)) {
//                            if (StrUtil.equals("7", permission)) {
//                                SysDepartmentPermission sysDepartmentPermissionSelect = new SysDepartmentPermission();
//                                sysDepartmentPermissionSelect.setValue(userKey);
//                                List<SysDepartmentPermission> sysDepartmentPermissionList = this.sysDepartmentPermissionMapper
//                                        .selectBySysDepartmentPermissionList(sysDepartmentPermissionSelect);
//                                if ((sysDepartmentPermissionList == null)
//                                        || (sysDepartmentPermissionList.size() == 0)) {
//                                    return this.repEntity.layerMessage("no", "无权限查看");
//                                }
//                                SysDepartmentPermission dbSysDepartmentPermission = (SysDepartmentPermission) sysDepartmentPermissionList
//                                        .get(0);
//                                String[] departmentIds = dbSysDepartmentPermission.getDepartmentIds().split(",");
//                                if ((departmentIds != null) && (departmentIds.length > 0)) {
//                                    sysDepartment.setDepartmentId(departmentIds[0]);
//                                    for (int i = 0; i < departmentIds.length; i++) {
//                                        SysDepartment sysDepartmentSelect = new SysDepartment();
//                                        sysDepartmentSelect.setDepartmentPath(departmentIds[i]);
//                                        List<SysDepartment> dbSysDepartmentList = this.sysDepartmentMapper
//                                                .selectBySysDepartmentList(sysDepartmentSelect);
//                                        sysDepartmentList.addAll(dbSysDepartmentList);
//                                    }
//                                }
//                            } else {
//                                return this.repEntity.ok(new JSONArray());
//                            }
//                        }
//                    }
//                }
//            }
//        } else {
//            sysDepartmentList = this.sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
//        }
//
//        JSONArray result = ConvertUtil.listToTree(new JSONArray(sysDepartmentList), "departmentId",
//                "departmentParentId", "departmentName");
//        return this.repEntity.ok(result);
//    }

    /**
     * 获取所有部门及人员节点，并形成树形结构
     * 
     * @return 树形结构
     */
    @Override
    public ResponseEntity getSysDepartmentUserAllTree(SysDepartment sysDepartment) {
        if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
            return getSysUserListByLikeTree(sysDepartment.getSearchType(), sysDepartment.getSearch());
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        // 是否只显示部门
        String noPersonnel = request.getParameter("noPersonnel");
        String companyId = TokenUtils.getCompanyId(request);
        String userKey = TokenUtils.getUserKey(request);
        String userId = TokenUtils.getUserId(request);
        String departmentId = "";
        if (sysDepartment == null) {
            sysDepartment = new SysDepartment();
        }
        sysDepartment.setDepartmentPath(companyId);
        if (StrUtil.isNotEmpty(sysDepartment.getDepartmentId())) {
            sysDepartment.setDepartmentPath(sysDepartment.getDepartmentId());
        }

        // 获取全部的部门
        List<SysDepartment> sysDepartmentList = Lists.newArrayList();
        // 是否有通过权限获取
        String authority = request.getParameter("authority");
        if (StrUtil.equals("1", authority) && !userId.equals("admin")) {
            // 此授权只能是一个部门，如果是多个部门需要将多部门的顶级合并起来才可以
            SysDepartmentPermission sysDepartmentPermissionSelect = new SysDepartmentPermission();
            sysDepartmentPermissionSelect.setValue(userKey);
            List<SysDepartmentPermission> sysDepartmentPermissionList = sysDepartmentPermissionMapper
                    .selectBySysDepartmentPermissionList(sysDepartmentPermissionSelect);
            if (sysDepartmentPermissionList == null || sysDepartmentPermissionList.size() == 0) {
                return repEntity.layerMessage("no", "无权限查看");
            }
            SysDepartmentPermission dbSysDepartmentPermission = sysDepartmentPermissionList.get(0);
            String[] departmentIds = dbSysDepartmentPermission.getDepartmentIds().split(",");
            if (departmentIds != null && departmentIds.length > 0) {
                sysDepartment.setDepartmentId(departmentIds[0]);
                for (int i = 0; i < departmentIds.length; i++) {
                    SysDepartment sysDepartmentSelect = new SysDepartment();
                    sysDepartmentSelect.setDepartmentPath(departmentIds[i]);
                    List<SysDepartment> dbSysDepartmentList = sysDepartmentMapper
                            .selectBySysDepartmentList(sysDepartmentSelect);
                    sysDepartmentList.addAll(dbSysDepartmentList);
                }
            }
        } else {
            sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
        }

        // 获取全部的用户部门关系，并且按照部门ID分类人员
        List<SysUser> sysUserList = Lists.newArrayList();
        if (!StrUtil.equals("1", noPersonnel)) {
            // 默认全部；1：部门
            if (!StrUtil.equals("1", sysDepartment.getTreeType())) {
                sysUserList = sysUserMapper.selectBySysUserListByDepartment(null);
            }
        }

        // 流程选择时，部门设置部分↓↓↓
        String id = "";
        String name = "";
        int treeSize = sysDepartmentList.size() + sysUserList.size();
        boolean isDefault = false;
        for (SysDepartment dbSysDepartment : sysDepartmentList) {
            if (StrUtil.isEmpty(dbSysDepartment.getDepartmentParentId())
                    || StrUtil.equals("0", dbSysDepartment.getDepartmentParentId())
                    || StrUtil.equals(departmentId, dbSysDepartment.getDepartmentId())) {
                isDefault = true;
            }
            if (StrUtil.equals(sysDepartment.getDepartmentId(), dbSysDepartment.getDepartmentId())) {
                id = dbSysDepartment.getDepartmentId();
                name = dbSysDepartment.getDepartmentName();
            }
        }
        int i = 0;
        TreeNodeEntity[] treeNodes = null;
        if (!isDefault) {
            treeSize++;
            treeNodes = new TreeNodeEntity[treeSize];
            // pid默认给0，顶级节点pid必须是0
            treeNodes[i] = new TreeNodeEntity("0", id, "0", name, "");
            i++;
        } else {
            treeNodes = new TreeNodeEntity[treeSize];
        }
        // 流程选择时，部门设置部分↑↑↑

        // 合并部门&人员信息 【特殊条件，类似分公司的时候departmentId,
        // dbSysDepartment.getDepartmentId()】
        for (SysDepartment dbSysDepartment : sysDepartmentList) {
            if (StrUtil.isEmpty(dbSysDepartment.getDepartmentParentId())
                    || StrUtil.equals("0", dbSysDepartment.getDepartmentParentId())
                    || StrUtil.equals(departmentId, dbSysDepartment.getDepartmentId())) {
                treeNodes[i] = new TreeNodeEntity("0", dbSysDepartment.getDepartmentId(),
                        dbSysDepartment.getDepartmentParentId(), dbSysDepartment.getDepartmentName(), "");
            } else {
                treeNodes[i] = new TreeNodeEntity("1", dbSysDepartment.getDepartmentId(),
                        dbSysDepartment.getDepartmentParentId(), dbSysDepartment.getDepartmentName(), "");
            }
            i++;
        }

        for (SysUser sysUser : sysUserList) {
            List list = Lists.newArrayList();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("label", "姓名:");
            jsonObject.put("value", sysUser.getRealName());
            list.add(jsonObject);

            jsonObject = new JSONObject();
            jsonObject.put("label", "电话:");
            jsonObject.put("value", sysUser.getMobile());
            list.add(jsonObject);

            treeNodes[i] = new TreeNodeEntity("2", sysUser.getUserKey(), sysUser.getDepartmentId(),
                    sysUser.getRealName(), list);
            i++;
        }
        TreeNodeEntity treeNode = TreeNodeMerger.merge(treeNodes);
        return repEntity.ok(treeNode);
    }

    /**
     * 中交·获取所有部门及人员节点，并形成树形结构
     * 
     * @param sysDepartment
     * @param syncCacheFlag
     *            1：更新缓存；其他不更新
     * @return 树形结构
     */
    @Override
    public ResponseEntity getSysDepartmentUserAllTreeByZj(SysDepartment sysDepartment, int syncCacheFlag) {
        if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
            return getSysUserListByLikeTreeByZj(sysDepartment.getSearch(), "");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        // 是否只显示部门
        String noPersonnel = request.getParameter("noPersonnel");
        String pId = "0";
        String companyId = TokenUtils.getCompanyId(request);
        String departmentId = "";
        if (sysDepartment == null) {
            sysDepartment = new SysDepartment();
        }
        // 返回缓存
        if (syncCacheFlag != 1) {
            if (zjUserMap != null && zjUserMap.get(companyId) != null) {
                return repEntity.ok(zjUserMap.get(companyId));
            }
        }

        if (StrUtil.equals("a5d82aM11cf9cf7329M65b09eb8996c077cbd7a49b1f0d7c83d", companyId)) {
            pId = "a5d82aM11cf9cea644M65b09eb8996c077cbd7a49b1f0d7c83d";
            sysDepartment.setDepartmentPath("a5d82aM11cf9cf7329M65b09eb8996c077cbd7a49b1f0d7c83d");
        } else {
            sysDepartment.setDepartmentPath(companyId);
            pId = sysDepartmentMapper.selectByPrimaryKey(companyId).getDepartmentParentId();
        }
        
        // 获取全部的部门
        List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
        // 获取全部的用户部门关系，并且按照部门ID分类人员
        List<SysUser> sysUserList = Lists.newArrayList();
        // 默认全部；1：部门
        if (StrUtil.equals("0", sysDepartment.getTreeType())) {
            SysUser sysUser = new SysUser();
            sysUser.setAccountAppType("1");
            sysUserList = sysUserMapper.selectBySysUserListByDepartment(sysUser);
        }
        // if(!StrUtil.equals("1", noPersonnel)) {
        if (!StrUtil.equals("1", noPersonnel)) {
            SysUser sysUser = new SysUser();
            sysUser.setAccountAppType("1");
            sysUserList = sysUserMapper.selectBySysUserListByDepartment(sysUser);
        }

        int i = 0;
        TreeNodeEntity[] treeNodes = new TreeNodeEntity[sysDepartmentList.size() + sysUserList.size()];
        // 合并部门&人员信息 【特殊条件，类似分公司的时候departmentId,
        // dbSysDepartment.getDepartmentId()】
        for (SysDepartment dbSysDepartment : sysDepartmentList) {
            if (StrUtil.isEmpty(dbSysDepartment.getDepartmentParentId())
                    || StrUtil.equals("0", dbSysDepartment.getDepartmentParentId())
                    || StrUtil.equals(departmentId, dbSysDepartment.getDepartmentId())) {
                treeNodes[i] = new TreeNodeEntity("0", dbSysDepartment.getDepartmentId(),
                        dbSysDepartment.getDepartmentParentId(), dbSysDepartment.getDepartmentName(), "");
            } else {
                treeNodes[i] = new TreeNodeEntity("1", dbSysDepartment.getDepartmentId(),
                        dbSysDepartment.getDepartmentParentId(), dbSysDepartment.getDepartmentName(), "");
            }
            i++;
        }

        for (SysUser sysUser : sysUserList) {
            List list = Lists.newArrayList();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("label", "姓名:");
            jsonObject.put("value", sysUser.getRealName());
            list.add(jsonObject);

            jsonObject = new JSONObject();
            jsonObject.put("label", "电话:");
            jsonObject.put("value", sysUser.getMobile());
            list.add(jsonObject);

            treeNodes[i] = new TreeNodeEntity("2", sysUser.getUserKey(), sysUser.getDepartmentId(),
                    sysUser.getRealName(), list);
            i++;
        }
        TreeNodeEntity treeNode = TreeNodeMerger.mergeById(pId, treeNodes);

        zjUserMap.put(companyId, treeNode);

        return repEntity.ok(treeNode);
    }

    @Override
    public ResponseEntity tempUpdateDepartmentPath1(SysDepartment sysDepartment) {
        return null;
    }

    /**
     * 获取所有部门及人员节点，并形成树形结构
     * 
     * @return 树形结构
     */
    @Override
    public ResponseEntity tempUpdateDepartmentPath(SysDepartment sysDepartment) {
        if (sysDepartment == null) {
            sysDepartment = new SysDepartment();
        }
        // 获取全部的部门
        List<SysDepartment> sysDepartmentList = sysDepartmentMapper.tempBySysDepartmentList(null);

        for (int i = 0; i < sysDepartmentList.size(); i++) {
            SysDepartment dbSysDepartment = sysDepartmentList.get(i);
            if ("0".equals(dbSysDepartment.getDepartmentParentId())) {
                continue;
            }
            String[] str = new String[10];
            String[] strName = new String[10];
            for (int j = 0; j < 10; j++) {
                if (j == 0) {
                    str[j] = dbSysDepartment.getDepartmentParentId();
                    SysDepartment sysDepartment2 = sysDepartmentMapper
                            .selectByPrimaryKey(dbSysDepartment.getDepartmentParentId());
                    if (sysDepartment2 != null) {
                        strName[j] = sysDepartment2.getDepartmentName();
                    } else {
                        strName[j] = "";
                    }
                } else {
                    String str2 = str[j - 1];
                    if (StrUtil.isEmpty(str2) || StrUtil.equals("0", str2)) {
                        break;
                    }
                    SysDepartment sysDepartment2 = sysDepartmentMapper.selectByPrimaryKey(str[j - 1]);
                    if (sysDepartment2 != null) {
                        if (StrUtil.isEmpty(sysDepartment2.getDepartmentParentId())
                                || StrUtil.equals("0", sysDepartment2.getDepartmentParentId())) {
                            break;
                        }
                        str[j] = sysDepartment2.getDepartmentParentId();
                        if (StrUtil.isNotEmpty(sysDepartment2.getDepartmentParentId())
                                && !StrUtil.equals("0", sysDepartment2.getDepartmentParentId())) {
                            sysDepartment2 = sysDepartmentMapper
                                    .selectByPrimaryKey(sysDepartment2.getDepartmentParentId());
                            if (sysDepartment2 != null) {
                                strName[j] = sysDepartment2.getDepartmentName();
                            } else {
                                strName[j] = "";
                            }
                        }
                    }
                }
            }
            String strPath = "";
            String strPathName = "";
            for (int j = 9; j >= 0; j--) {
                String str1 = str[j];
                String strName2 = strName[j];
                if (StrUtil.isNotEmpty(str1)) {
                    if ("".equals(strPath)) {
                        strPath = str1;
                        strPathName = strName2;
                    } else {
                        strPath = strPath + "," + str1;
                        strPathName = strPathName + "," + strName2;
                    }
                }
            }

            dbSysDepartment.setDepartmentPath(strPath + "," + dbSysDepartment.getDepartmentId());
            dbSysDepartment.setDepartmentPathName(strPathName + "," + dbSysDepartment.getDepartmentName());
            int t = sysDepartmentMapper.updateByPrimaryKey(dbSysDepartment);
            if (t == 0) {
                System.out.println(
                        "----------" + dbSysDepartment.getDepartmentId() + dbSysDepartment.getDepartmentName());
            }
        }
        return repEntity.ok("");
    }

    @Override
    public List<SysUser> selectBySysUserListByDepartment(SysUser sysUser) {
        if (sysUser == null) {
            sysUser = new SysUser();
        }
        List<SysUser> sysUserList = sysUserMapper.selectBySysUserListByDepartment(sysUser);
        return sysUserList;
    }

    @Override
    public List<SysDepartment> selectBySysDepartmentList(SysDepartment sysDepartment) {
        if (sysDepartment == null) {
            sysDepartment = new SysDepartment();
        }
        List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
        return sysDepartmentList;
    }

    // -----------------↓↓↓以下为内部私有方法↓↓↓-----------------
    /**
     * 递归比较是否为公司
     * 
     * @param userKey
     * @param userCompanyMap
     * @param companyAllMap
     * @param departmentId
     */
    private void getDepartmentByOaId(String userKey, Map<String, SysCompany> userCompanyMap,
            Map<String, SysCompany> companyAllMap, String departmentId) {
        SysDepartment depar = sysDepartmentMapper.selectByPrimaryKey(departmentId);
        if (companyAllMap.containsKey(depar.getDepartmentParentId())) {
            if (!userCompanyMap.containsKey(depar.getDepartmentParentId())) {
                SysCompany userCompany = companyAllMap.get(depar.getDepartmentParentId());
                userCompany.setUserKey(userKey);
                userCompanyMap.put(depar.getDepartmentParentId(), userCompany);
            }
        } else {
            getDepartmentByOaId(userKey, userCompanyMap, companyAllMap, depar.getDepartmentParentId());
        }
    }

    /**
     * 递归比较是否为公司
     * 
     * @param companyAllMap
     * @param departmentId
     * @return
     */
    private String getDepartmentByOaId(Map<String, SysCompany> companyAllMap, String departmentId, int count) {
        String depId = "";
        // 为防止递归死循环，10遍之后跳出
        if (count >= 10) {
            return "";
        }
        SysDepartment depar = sysDepartmentMapper.selectByPrimaryKey(departmentId);
        if (companyAllMap.containsKey(depar.getDepartmentParentId())) {
            depId = depar.getDepartmentParentId();
            return depId;
        } else {
            count = count + 1;
            if(StrUtil.equals("0", depar.getDepartmentParentId())) {
                return depId;
            }
            depId = getDepartmentByOaId(companyAllMap, depar.getDepartmentParentId(), count);
            if (StrUtil.isNotEmpty(depId)) {
                return depId;
            }
        }
        return depId;
    }

    /**
     * tree对应的检索数据
     * 
     * @param searchType 0:默认全查；1：部门；2：人员
     * @param search
     * @return
     */
    private ResponseEntity getSysUserListByLikeTree(String searchType, String search) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
//        String userKey = TokenUtils.getUserKey(request);
        String userId = TokenUtils.getUserId(request);
        String companyId = TokenUtils.getCompanyId(request);
        String ext1 = TokenUtils.getExt1(request);
        if(StrUtil.equals("admin", userId) || StrUtil.equals("1", ext1)) {
            companyId = "";
        }

        if (StrUtil.isEmpty(search)) {
            return repEntity.okList(Lists.newArrayList(), 0);
        }
        JSONArray jsonArray = new JSONArray();
        // 部门获取
        if(StrUtil.equals("0", searchType)
                || StrUtil.equals("1", searchType)) {
            JSONArray jsonArrayDepartment = this.getSysDepartmentListByLikeTreeDate(search, companyId);
            if(jsonArrayDepartment != null && jsonArrayDepartment.size()>0) {
                jsonArray.addAll(jsonArrayDepartment);
            }

            // 共享部门
            if(StrUtil.isNotEmpty(TokenUtils.getCompanyId(request)) && TokenUtils.getCompanyId(request).indexOf("999999")<0) {
                JSONArray shareDepartmentJSONArray = new JSONArray(publicConfig.getProperty("share.department", null));
                if(shareDepartmentJSONArray != null && shareDepartmentJSONArray.size()>0) {
                    for (Iterator<Object> iterator = shareDepartmentJSONArray.iterator(); iterator.hasNext();) {
                        JSONObject jsonObject = (JSONObject)iterator.next();
                        // 如果是相同的共享机构，则不显示
                        if(StrUtil.equals(jsonObject.getStr("departmentId"), TokenUtils.getCompanyId(request))) {
                            continue;
                        }
                        JSONArray jsonArrayDepartmentShare = this.getSysDepartmentListByLikeTreeDate(search, jsonObject.getStr("departmentId"));
                        if(jsonArrayDepartmentShare != null && jsonArrayDepartmentShare.size()>0) {
                            jsonArray.addAll(jsonArrayDepartmentShare);
                        }
                    }
                }
            }
        }

        // 人员获取
        // 条件
        if(StrUtil.equals("0", searchType)
                || StrUtil.equals("2", searchType)) {
            SysDepartment sysDepartment = new SysDepartment(); 
            sysDepartment.setRealName(search);
            sysDepartment.setDepartmentPath(companyId);
            JSONArray jsonArrayUser = this.getSysUserListByLikeTreeDate(sysDepartment);
            if(jsonArrayUser != null && jsonArrayUser.size()>0) {
                jsonArray.addAll(jsonArrayUser);
            }
            
            // 共享部门
            if(StrUtil.isNotEmpty(TokenUtils.getCompanyId(request)) && TokenUtils.getCompanyId(request).indexOf("999999")<0) {
                JSONArray shareDepartmentJSONArray = new JSONArray(publicConfig.getProperty("share.department", null));
                if(shareDepartmentJSONArray != null && shareDepartmentJSONArray.size()>0) {
                    for (Iterator<Object> iterator = shareDepartmentJSONArray.iterator(); iterator.hasNext();) {
                        JSONObject jsonObject = (JSONObject)iterator.next();
                        // 如果是相同的共享机构，则不显示
                        if(StrUtil.equals(jsonObject.getStr("departmentId"), TokenUtils.getCompanyId(request))) {
                            continue;
                        }
                        SysDepartment sysDepartmentShare = new SysDepartment(); 
                        sysDepartmentShare.setRealName(search);
                        sysDepartmentShare.setDepartmentPath(jsonObject.getStr("departmentId"));
                        JSONArray jsonArrayUserShare = this.getSysUserListByLikeTreeDate(sysDepartmentShare);
                        if(jsonArrayUserShare != null && jsonArrayUserShare.size()>0) {
                            jsonArray.addAll(jsonArrayUserShare);
                        }
                    }
                }
            }
        }
        
        return repEntity.okList(jsonArray, jsonArray.size());
    }

    /**
     * tree对应的检索数据
     * 
     * @param search
     * @param authorityFlag 默认空只有自己，1：本公司+局 或 本公司+局
     * @return
     */
    private ResponseEntity getSysUserListByLikeTreeByZj(String search, String authorityFlag) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        if (StrUtil.isEmpty(search)) {
            return repEntity.okList(Lists.newArrayList(), 0);
        }
        String companyId = TokenUtils.getCompanyId(request);
        // 条件
        SysDepartment sysDepartment = new SysDepartment(); 
        sysDepartment.setRealName(search);
        // 默认空只有自己
        sysDepartment.setDepartmentPath(companyId);
        // 当有权限 && 局的人进来时（局+其它所有分公司)
        if(StrUtil.equals("1", authorityFlag)
                && StrUtil.equals("a5d82aM11cf9cf7329M65b09eb8996c077cbd7a49b1f0d7c83d", companyId)) {
            sysDepartment.setDepartmentPath(null);
        }
        JSONArray jsonArray = this.getSysUserListByLikeTreeDate(sysDepartment);

        // 当有权限 && 分公司人进来时（本公司+局)
        if(StrUtil.equals("1", authorityFlag)
                && !StrUtil.equals("a5d82aM11cf9cf7329M65b09eb8996c077cbd7a49b1f0d7c83d", companyId)) {
            sysDepartment.setDepartmentPath("a5d82aM11cf9cf7329M65b09eb8996c077cbd7a49b1f0d7c83d");
            JSONArray jsonArray2 = this.getSysUserListByLikeTreeDate(sysDepartment);
            jsonArray.addAll(jsonArray2);
        }
        
        return repEntity.okList(jsonArray, jsonArray.size());
    }

    /**
     * 获取树形结构人员、部门、人员+部门的相关数据
     * 
     * @param sysDepartment
     * @return
     */
    private JSONArray getSysDepartmentListByLikeTreeDate(String departmentName, String companyId) {
        JSONArray jsonArray = new JSONArray();
        SysDepartment sysDepartment = new SysDepartment();
        sysDepartment.setDepartmentName(departmentName);
        sysDepartment.setDepartmentPath(companyId);
        List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
        for (SysDepartment dbSysDepartment:sysDepartmentList) {
            JSONObject jsonObject = new JSONObject();
            // 姓名
            jsonObject.put("label", dbSysDepartment.getDepartmentName());
            jsonObject.put("title", dbSysDepartment.getDepartmentName());
            // 节点类型（0：根节点；1：部门；2：人员）
            jsonObject.put("type", "1");
            // userKey
            jsonObject.put("value", dbSysDepartment.getDepartmentId());
            jsonObject.put("valuePid", "");
            String departmentPathName = dbSysDepartment.getDepartmentPathName().replaceAll(",", "/");
            if(departmentPathName.indexOf("/") > 0) {
                // 显示第二层
                departmentName = departmentPathName.substring(departmentPathName.indexOf("/")+1);
                if(departmentName.indexOf("/")>0) {
                    departmentName = departmentName.substring(0, departmentName.indexOf("/"));
                }
                jsonObject.put("departmentName", departmentName);
            } else {
                jsonObject.put("departmentName", departmentPathName);
            }
            jsonObject.put("showDepartmentName", departmentPathName);
            
            // 下面显示的详情内容
            jsonObject.put("showData", new JSONArray());
            
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    /**
     * 获取树形结构人员的相关数据
     * 
     * @param sysDepartment
     * @return
     */
    private JSONArray getSysUserListByLikeTreeDate(SysDepartment sysDepartment) {
        JSONArray jsonArray = new JSONArray();
        List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectSysDepartmentUserListByLike(sysDepartment);
        for (SysDepartment dbSysDepartment:sysDepartmentList) {
            JSONObject jsonObject = new JSONObject();
            // 姓名
            jsonObject.put("label", dbSysDepartment.getRealName());
            jsonObject.put("title", dbSysDepartment.getRealName());
            // 节点类型（0：根节点；1：部门；2：人员）
            jsonObject.put("type", "2");
            // userKey
            jsonObject.put("value", dbSysDepartment.getUserKey());
            jsonObject.put("valuePid", dbSysDepartment.getDepartmentId());
            jsonObject.put("departmentName", dbSysDepartment.getDepartmentName());
            jsonObject.put("showDepartmentName", dbSysDepartment.getDepartmentPathName().replaceAll(",", "/"));
            
            // 下面显示的详情内容
            JSONArray showDataJsonArray = new JSONArray();
            JSONObject showDataJsonObject = new JSONObject();
            showDataJsonObject.put("label", "部门");
            showDataJsonObject.put("value", dbSysDepartment.getDepartmentPathName().replaceAll(",", "/"));
            showDataJsonArray.add(showDataJsonObject);
            jsonObject.put("showData", showDataJsonArray);
            
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
    
    /**
     * 获取【所有部门】并形成树形结构（通用版本）
     * 
     * @param sysDepartment
     * @return 【所有部门】，并形成树形结构
     */
    @Override
    public ResponseEntity getSysDepartmentTree(SysDepartment sysDepartment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String userId = TokenUtils.getUserId(request);
        if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
            return getSysUserListByLikeTree(sysDepartment.getSearchType(), sysDepartment.getSearch());
        }
        if(sysDepartment == null) {
            sysDepartment = new SysDepartment();
        }
        String companyId = TokenUtils.getCompanyId(request);
        String ext1 = TokenUtils.getExt1(request);
        if(StrUtil.equals("admin", userId) || StrUtil.equals("1", ext1)) {
            companyId = "";
        }
        sysDepartment.setDepartmentPath(companyId);
        // 清当前用户缓存
        String cacheKey = companyId + "getSysDepartmentTree";
        // 返回缓存
        if (cacheMap != null && cacheMap.get(cacheKey) != null) {
            return repEntity.ok(cacheMap.get(cacheKey));
        }
        
        List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);

        // 共享部门
        if(StrUtil.isNotEmpty(companyId) && companyId.indexOf("999999")<0) {
            JSONArray shareDepartmentJSONArray = new JSONArray(publicConfig.getProperty("share.department", null));
            if(shareDepartmentJSONArray != null && shareDepartmentJSONArray.size()>0) {
                for (Iterator<Object> iterator = shareDepartmentJSONArray.iterator(); iterator.hasNext();) {
                    JSONObject jsonObject = (JSONObject)iterator.next();
                    SysDepartment sysDepartmentShare = new SysDepartment();
                    sysDepartmentShare.setDepartmentPath(jsonObject.getStr("departmentId"));
                    List<SysDepartment> sysDepartmentShareList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartmentShare);
                    // 添加共享部门
                    sysDepartmentList.addAll(sysDepartmentShareList);
                }
            }
        }
        
        JSONArray jsonArray = ConvertUtil.listToTree(new JSONArray(sysDepartmentList), "departmentId", "departmentParentId", "departmentName", "departmentName");
        
        cacheMap.put(cacheKey, jsonArray);
        
        return this.repEntity.ok(jsonArray);
    }
    
    /**
     * 获取【所有部门、人员】并形成树形结构（通用版本）
     * 
     * @param sysDepartment
     * @return 【所有部门、人员】，并形成树形结构
     */
    @Override
    public ResponseEntity getSysUserTree(SysDepartment sysDepartment) {
        if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
            return getSysUserListByLikeTree(sysDepartment.getSearchType(), sysDepartment.getSearch());
        }
        if(sysDepartment == null) {
            sysDepartment = new SysDepartment();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String companyId = TokenUtils.getCompanyId(request);
        String ext1 = TokenUtils.getExt1(request);
        if(StrUtil.equals("admin", userId) || StrUtil.equals("1", ext1)) {
            companyId = "";
        }
        String cacheKey = companyId + "getSysUserTree";
        // 返回缓存
        if (cacheMap != null && cacheMap.get(cacheKey) != null) {
            return repEntity.ok(cacheMap.get(cacheKey));
        }
        
        // 全部部门
        sysDepartment.setDepartmentPath(companyId);
        List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
        
        // 全部人员
        SysUser sysUser = new SysUser();
        sysUser.setAccountAppType("1");
        // 默认全部
        sysUser.setCompanyId(companyId);
        List<SysDepartment> sysUserToSysDepartmentList = sysDepartmentMapper.getUserListByCompanyId(sysUser);
        sysDepartmentList.addAll(sysUserToSysDepartmentList);
        
        // 共享部门&人员（比如17局增加劳务部人员）
        String shareCompanyId = TokenUtils.getCompanyId(request);
        if(StrUtil.isNotEmpty(shareCompanyId) && shareCompanyId.indexOf("999999")<0) {
            JSONArray shareDepartmentJSONArray = new JSONArray(publicConfig.getProperty("share.department", null));
            if(shareDepartmentJSONArray != null && shareDepartmentJSONArray.size()>0) {
                for (Iterator<Object> iterator = shareDepartmentJSONArray.iterator(); iterator.hasNext();) {
                    JSONObject jsonObject = (JSONObject)iterator.next();
                    // 如果是相同的共享机构，则不显示
                    if(StrUtil.equals(jsonObject.getStr("departmentId"), shareCompanyId)) {
                        continue;
                    }
                    SysDepartment sysDepartmentShare = new SysDepartment();
                    sysDepartmentShare.setDepartmentPath(jsonObject.getStr("departmentId"));
                    List<SysDepartment> sysDepartmentShareList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartmentShare);
                    // 添加共享部门
                    sysDepartmentList.addAll(sysDepartmentShareList);
                    
                    // 全部人员
                    SysUser sysUserShare = new SysUser();
                    sysUserShare.setAccountAppType("1");
                    // 默认全部
                    sysUserShare.setCompanyId(jsonObject.getStr("departmentId"));
                    List<SysDepartment> sysUserToSysUsertList = sysDepartmentMapper.getUserListByCompanyId(sysUserShare);
                    sysDepartmentList.addAll(sysUserToSysUsertList);
                }
            }
        }
        
        // 转换
        JSONArray jsonArray = ConvertUtil.listToTree(new JSONArray(sysDepartmentList), "departmentId", "departmentParentId", "departmentName", "realName");
        cacheMap.put(cacheKey, jsonArray);
        
        return this.repEntity.ok(jsonArray);
    }
    
    /**
     * 获取【所有部门】并形成树形结构（通用版本）,点击一层一层请求
     * 
     * @param sysDepartment
     * @return 【所有部门】，并形成树形结构
     */
    @Override
    public ResponseEntity getSysDepartmentCurrentTree(SysDepartment sysDepartment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String companyId = TokenUtils.getCompanyId(request);
        String mobile = TokenUtils.getMobile(request);
        String ext1 = TokenUtils.getExt1(request);
        if(sysDepartment == null || StrUtil.isEmpty(sysDepartment.getDepartmentParentId())) {
            sysDepartment = new SysDepartment();
            sysDepartment.setDepartmentParentId("0");
        }
        // 项目类型时
        if(StrUtil.equals("3", ext1) || StrUtil.equals("4", ext1)) {
            return sysProjectService.getSysProjectTree(sysDepartment);
        }
        
        if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
            return getSysUserListByLikeTree(sysDepartment.getSearchType(), sysDepartment.getSearch());
        }
        
        JSONArray jsonArray = new JSONArray();
        // 1:部门
        if(StrUtil.isEmpty(sysDepartment.getType())) {
            sysDepartment.setType("1");
        }

        // 权限判断（非集团时）
        if(!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            if(StrUtil.equals("0", sysDepartment.getDepartmentParentId())) {
                sysDepartment.setDepartmentPath(companyId);
                // 拼凑根节点
                SysDepartment sysDepartmentCompany = sysDepartmentMapper.selectByPrimaryKey(companyId);
                if(sysDepartmentCompany != null) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.set("valuePid", sysDepartmentCompany.getDepartmentParentId());
                    jsonObject.set("value", companyId);
                    jsonObject.set("label", sysDepartmentCompany.getDepartmentName());
                    jsonObject.set("title", sysDepartmentCompany.getDepartmentName());
                    jsonObject.set("type", "1");
                    jsonArray.add(jsonObject);
                }
                // 通过配置中心设置，拼凑多个所属公司时的根节点
                if(StrUtil.isNotEmpty(companyId) && companyId.indexOf("999999")<0) {
                    JSONArray departmentJSONArray = new JSONArray(publicConfig.getProperty("share.department", null));
                    if(departmentJSONArray != null && departmentJSONArray.size()>0) {
                        for (Iterator<Object> iterator = departmentJSONArray.iterator(); iterator.hasNext();) {
                            JSONObject shareJSONObject = (JSONObject)iterator.next();
                            String shareCompanyId = shareJSONObject.getStr("departmentId");
                            // 不相同的companyId时，添加共享节点
//                            if(!StrUtil.equals(companyId, shareCompanyId)) {
                                sysDepartmentCompany = sysDepartmentMapper.selectByPrimaryKey(shareCompanyId);
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.set("valuePid", sysDepartmentCompany.getDepartmentParentId());
                                jsonObject.set("value", sysDepartmentCompany.getDepartmentId());
                                jsonObject.set("label", sysDepartmentCompany.getDepartmentName());
                                jsonObject.set("title", sysDepartmentCompany.getDepartmentName());
                                jsonObject.set("type", "1");
                                // 添加共享根节点
                                jsonArray.add(jsonObject);
//                            }
                        }
                    }
                }
                
                // 厦门公司特殊添加
                List<SysCompany>  sysCompanyList = selectCompanyListByMobile(mobile, "1");
                if(sysCompanyList.size()>1) {
                    for(SysCompany sysCompany:sysCompanyList) {
                        if(!StrUtil.equals(companyId, sysCompany.getCompanyId())) {
                            sysDepartmentCompany = sysDepartmentMapper.selectByPrimaryKey(sysCompany.getCompanyId());
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.set("valuePid", sysDepartmentCompany.getDepartmentParentId());
                            jsonObject.set("value", sysDepartmentCompany.getDepartmentId());
                            jsonObject.set("label", sysDepartmentCompany.getDepartmentName());
                            jsonObject.set("title", sysDepartmentCompany.getDepartmentName());
                            jsonObject.set("type", "1");
                            // 添加共享根节点
                            jsonArray.add(jsonObject);
                        }
                    }
                }
                return repEntity.ok(jsonArray); 
            }
        }
        
        // 调用方法获取部门数据
        jsonArray.addAll(getPrivateSysUserCurrentTree(sysDepartment, false));
        
        return repEntity.ok(jsonArray);
    }

    /**
     * 无权下方式获取所有部门数据，并形成树形结构（通用版本）
     * 
     * @param sysDepartment
     * @return 【所有部门】，并形成树形结构
     */
    @Override
    public ResponseEntity getSysDepartmentCurrentTreeAll(SysDepartment sysDepartment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String companyId = TokenUtils.getCompanyId(request);
        String mobile = TokenUtils.getMobile(request);
        String ext1 = TokenUtils.getExt1(request);
        // 项目类型时
        if(StrUtil.equals("3", ext1) || StrUtil.equals("4", ext1)) {
            return sysProjectService.getSysProjectTree(sysDepartment);
        }
        
        if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
            return getSysUserListByLikeTree(sysDepartment.getSearchType(), sysDepartment.getSearch());
        }
        
        JSONArray jsonArray = new JSONArray();
        if(sysDepartment == null || StrUtil.isEmpty(sysDepartment.getDepartmentParentId())) {
            sysDepartment = new SysDepartment();
            sysDepartment.setDepartmentParentId("0");
        }
        // 1:部门
        if(StrUtil.isEmpty(sysDepartment.getType())) {
            sysDepartment.setType("1");
        }
        
        // 调用方法获取部门数据
        jsonArray.addAll(getPrivateSysUserCurrentTree(sysDepartment, false));
        
        return repEntity.ok(jsonArray);
    }

    /**
     * 获取【所有部门、人员】并形成树形结构（通用版本）,代表一层一层请求
     * 
     * @param sysDepartment
     * @return 【所有部门、人员】，并形成树形结构
     */
    @Override
    public ResponseEntity getSysUserCurrentTree(SysDepartment sysDepartment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String companyId = TokenUtils.getCompanyId(request);
        String ext1 = TokenUtils.getExt1(request);
        if(sysDepartment == null || StrUtil.isEmpty(sysDepartment.getDepartmentParentId())) {
            sysDepartment = new SysDepartment();
            sysDepartment.setDepartmentParentId("0");
        }
        // 项目类型时
        if(StrUtil.equals("3", ext1) || StrUtil.equals("4", ext1)) {
            sysDepartment.setDepartmentId(sysDepartment.getDepartmentParentId());
            return sysProjectService.getSysProjectUserTree(sysDepartment);
        }
        if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
            return getSysUserListByLikeTree(sysDepartment.getSearchType(), sysDepartment.getSearch());
        }
  
        JSONArray jsonArray = new JSONArray();
        // 1:部门
        if(StrUtil.isEmpty(sysDepartment.getType())) {
            sysDepartment.setType("1");
        }

        // 权限判断（非集团时）
        if(!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            if(StrUtil.equals("0", sysDepartment.getDepartmentParentId())) {
                sysDepartment.setDepartmentPath(companyId);
                // 拼凑根节点
                SysDepartment sysDepartmentCompany = sysDepartmentMapper.selectByPrimaryKey(companyId);
                if(sysDepartmentCompany != null) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.set("valuePid", sysDepartmentCompany.getDepartmentParentId());
                    jsonObject.set("value", companyId);
                    jsonObject.set("label", sysDepartmentCompany.getDepartmentName());
                    jsonObject.set("title", sysDepartmentCompany.getDepartmentName());
                    jsonObject.set("type", "1");
                    jsonArray.add(jsonObject);
                }
                // 配置中心：拼凑多个所属公司时的根节点
                if(StrUtil.isNotEmpty(companyId) && companyId.indexOf("999999")<0) {
                    JSONArray departmentJSONArray = new JSONArray(publicConfig.getProperty("share.department", null));
                    if(departmentJSONArray != null && departmentJSONArray.size()>0) {
                        for (Iterator<Object> iterator = departmentJSONArray.iterator(); iterator.hasNext();) {
                            JSONObject shareJSONObject = (JSONObject)iterator.next();
                            String shareCompanyId = shareJSONObject.getStr("departmentId");
                            sysDepartmentCompany = sysDepartmentMapper.selectByPrimaryKey(shareCompanyId);
                            // 不相同的companyId时，添加共享节点
                            if(sysDepartmentCompany != null) {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.set("valuePid", sysDepartmentCompany.getDepartmentParentId());
                                jsonObject.set("value", sysDepartmentCompany.getDepartmentId());
                                jsonObject.set("label", sysDepartmentCompany.getDepartmentName());
                                jsonObject.set("type", "1");
                                // 添加共享根节点
                                jsonArray.add(jsonObject);
                            }
                        }
                    }
                }
                return repEntity.ok(jsonArray); 
            }
        }

        // 调用方法获取部门及人员数据
        jsonArray.addAll(getPrivateSysUserCurrentTree(sysDepartment, true));
        
        return repEntity.ok(jsonArray);
    }

    /**
     * 一层一层请求:获取【所有当前所属公司人员】并形成树形结构
     * 
     * @param sysDepartment
     * @return 【所有部门、人员】，并形成树形结构
     */
    @Override
    public ResponseEntity getSysUserTreeByCompany(SysDepartment sysDepartment) {
        if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
            return getSysUserListByLikeTree(sysDepartment.getSearchType(), sysDepartment.getSearch());
        }

        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String userId = TokenUtils.getUserId(request);
        String companyId = TokenUtils.getCompanyId(request);
        String ext1 = TokenUtils.getExt1(request);
        JSONArray jsonArray = new JSONArray();
        if(sysDepartment == null || StrUtil.isEmpty(sysDepartment.getDepartmentParentId())) {
            sysDepartment = new SysDepartment();
            sysDepartment.setDepartmentParentId("0");
        }
        // 1:部门
        if(StrUtil.isEmpty(sysDepartment.getType())) {
            sysDepartment.setType("1");
        }

        // 权限判断（非集团时）
        if(!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            sysDepartment.setDepartmentPath(companyId);
            if(StrUtil.equals("0", sysDepartment.getDepartmentParentId())) {
                SysDepartment sysDepartmentSelect = new SysDepartment(); 
                sysDepartmentSelect.setUserKey(userKey);
                List<SysDepartment> dbSysDepartmentList = sysDepartmentMapper.getSysDepartmentListByUserkey(sysDepartmentSelect);
                // 获取的数据时当前关联的部门，数据公司数据在companyId中，所以吧当前departmentId更换成companyId
                if(dbSysDepartmentList != null && dbSysDepartmentList.size()>0) {
                    for(SysDepartment dbSysDepartment:dbSysDepartmentList) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.set("valuePid", dbSysDepartment.getDepartmentParentId());
                        jsonObject.set("value", dbSysDepartment.getCompanyId());
                        jsonObject.set("label", dbSysDepartment.getCompanyName());
                        jsonObject.set("title", dbSysDepartment.getCompanyName());
                        jsonObject.set("type", "1");
                        jsonArray.add(jsonObject);
                    }
                }
                
                // 项目：第一次进来时判断顶级权限后，查询项目表数据
                SysProject sysProjectSelect = new SysProject();
                sysProjectSelect.setUserKey(userKey);
                List<SysProject> sysProjectList = sysProjectMapper.selectBySysProjectCompanyListByUser(sysProjectSelect);
                // 获取的数据时当前关联的部门，数据公司数据在companyId中，所以吧当前departmentId更换成companyId
                if(sysProjectList != null && sysProjectList.size()>0) {
                    for(SysProject dbSysProject:sysProjectList) {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.set("valuePid", dbSysProject.getDepartmentParentId());
                        jsonObject.set("value", dbSysProject.getCompanyId());
                        jsonObject.set("label", dbSysProject.getCompanyName());
                        jsonObject.set("title", dbSysProject.getCompanyName());
                        jsonObject.set("type", "1");
                        jsonArray.add(jsonObject);
                    }
                }
                return repEntity.ok(jsonArray); 
            }
        }
        
        // 调用方法获取部门及人员数据
        jsonArray.addAll(getPrivateSysUserTreeByCompany(sysDepartment, true));
        
        return repEntity.ok(jsonArray);
    }

    /**
     * 一层一层请求:获取【所有局（机关）人员】并形成树形结构
     * 
     * @param sysDepartment
     * @return 【所有部门、人员】，并形成树形结构
     */
    @Override
    public ResponseEntity getSysUserTreeByJu(SysDepartment sysDepartment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
            return getSysUserListByLikeTree(sysDepartment.getSearchType(), sysDepartment.getSearch());
        }
        
        JSONArray jsonArray = new JSONArray();
        if(sysDepartment == null || StrUtil.isEmpty(sysDepartment.getDepartmentParentId())) {
            sysDepartment = new SysDepartment();
            sysDepartment.setDepartmentParentId("0");
        }
        // 1:部门
        if(StrUtil.isEmpty(sysDepartment.getType())) {
            sysDepartment.setType("1");
        }

        if(StrUtil.equals("0", sysDepartment.getDepartmentParentId())) {
            SysDepartment dbSysDepartment = sysDepartmentMapper.selectByPrimaryKey("1EOMCA8NB00K7601A8C0000067BD6832");
            // 获取的数据时当前关联的部门，数据公司数据在companyId中，所以吧当前departmentId更换成companyId
            if(dbSysDepartment != null) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.set("valuePid", dbSysDepartment.getDepartmentParentId());
                jsonObject.set("value", dbSysDepartment.getDepartmentId());
                jsonObject.set("label", dbSysDepartment.getDepartmentName());
                jsonObject.set("title", dbSysDepartment.getDepartmentName());
                jsonObject.set("type", "1");
                jsonArray.add(jsonObject);
            }
            
            return repEntity.ok(jsonArray); 
        }
        
        // 调用方法获取部门及人员数据
        jsonArray.addAll(getPrivateSysUserTreeByCompany(sysDepartment, true));
        
        return repEntity.ok(jsonArray);
    }
    
    /**
     * 一层一层请求:获取【总部人员】并形成树形结构
     * 
     * @param sysDepartment
     * @return 【所有部门、人员】，并形成树形结构
     */
    @Override
    public ResponseEntity getSysUserTreeByZb(SysDepartment sysDepartment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String userId = TokenUtils.getUserId(request);
        String companyId = TokenUtils.getCompanyId(request);
        String ext1 = TokenUtils.getExt1(request);
        
        if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
            return getSysUserListByLikeTree(sysDepartment.getSearchType(), sysDepartment.getSearch());
        }
        
        JSONArray jsonArray = new JSONArray();
        if(sysDepartment == null || StrUtil.isEmpty(sysDepartment.getDepartmentParentId())) {
            sysDepartment = new SysDepartment();
            sysDepartment.setDepartmentParentId("0");
        }
        // 1:部门
        if(StrUtil.isEmpty(sysDepartment.getType())) {
            sysDepartment.setType("1");
        }

        if(StrUtil.equals("0", sysDepartment.getDepartmentParentId())) {
            // 查找当前人所在的总部
            SysProject sysProjectSelect = new SysProject();
            sysProjectSelect.setUserKey(userKey);
            List<SysProject> sysProjectList = sysProjectMapper.selectBySysProjectListByUserKey(sysProjectSelect);
            for(SysProject dbSysProject:sysProjectList) {
                JSONArray jsonArrayConfUp = new JSONArray(dbSysProject.getConfUp());
                jsonArray.addAll(jsonArrayConfUp);
            }
            return repEntity.ok(jsonArray); 
        }
        
        // 调用方法获取部门及人员数据
        jsonArray.addAll(getPrivateSysUserCurrentTree(sysDepartment, true));
        
        return repEntity.ok(jsonArray);
    }

    /**
     * 获取【所有部门、人员】并形成树形结构（通用版本）,代表一层一层请求
     * 
     * @param sysDepartment
     * @return 【所有部门、人员】，并形成树形结构
     */
    @Override
    public ResponseEntity getSysUserFlowLeftTree(SysDepartment sysDepartment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);

        // 根据右上角信息来切换
        JSONArray jsonArray = new JSONArray();
        // 树结构的第一层
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("flowRoleId", sysDepartment.getDepartmentId());
        jsonObject.set("flowRolePid", "0");
        jsonObject.set("flowRoleName", sysDepartment.getDepartmentName());
       
        int flag = 0;
        // 查找部门表，如果有再判断属性
        SysDepartment dbSysDepartment = sysDepartmentMapper.selectByPrimaryKey(sysDepartment.getDepartmentId());
        if(dbSysDepartment != null) {
            // 集团
            if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, dbSysDepartment.getDepartmentFlag())) {
                flag = 1;
            } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, dbSysDepartment.getDepartmentFlag())) {
                // 公司
                flag = 2;
            }
        } else {
            SysProject dbSysProject = sysProjectMapper.selectByPrimaryKey(sysDepartment.getDepartmentId());
            // 项目
            if(StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, dbSysProject.getDepartmentFlag())
                    || StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, dbSysProject.getDepartmentFlag())) {
                // 局直管总部项目、局托管总部项目
                if(StrUtil.equals("2", dbSysProject.getContractorType())
                        || StrUtil.equals("3", dbSysProject.getContractorType())) {
                    flag = 5;
                } else if(StrUtil.equals("5", dbSysProject.getContractorType())){
                    // 托管
                    flag = 6;
                } else {
                    flag = 3;
                }
            }
        }
        
        
        // 树结构的第二层
        JSONArray jsonArrayBaseCode = new JSONArray();
        BaseCode baseCodeSelect  = new BaseCode();
        baseCodeSelect.setItemId("flowRole");
        List<BaseCode> baseCodeList = baseCodeMapper.getBaseCodeTree(baseCodeSelect);
        for(BaseCode dbBaseCode:baseCodeList) {
            int FlowRoleIdNum = Integer.valueOf(dbBaseCode.getItemId());
            
            if(flag == 1 && FlowRoleIdNum < 200) {
                JSONObject jsonObjectBaseCode = new JSONObject();
                jsonObjectBaseCode.set("flowRoleId", dbBaseCode.getItemId());
                jsonObjectBaseCode.set("flowRolePid", sysDepartment.getDepartmentId());
                jsonObjectBaseCode.set("flowRoleName", dbBaseCode.getItemName());
                jsonObjectBaseCode.set("isLeaf", true);
                jsonArrayBaseCode.add(jsonObjectBaseCode);
            } else if(flag == 2 && (FlowRoleIdNum > 200 && FlowRoleIdNum < 300)) {
                JSONObject jsonObjectBaseCode = new JSONObject();
                jsonObjectBaseCode.set("flowRoleId", dbBaseCode.getItemId());
                jsonObjectBaseCode.set("flowRolePid", sysDepartment.getDepartmentId());
                jsonObjectBaseCode.set("flowRoleName", dbBaseCode.getItemName());
                jsonObjectBaseCode.set("isLeaf", true);
                jsonArrayBaseCode.add(jsonObjectBaseCode);
            } else if(flag == 3 && (FlowRoleIdNum > 300 && FlowRoleIdNum < 400)) {
                JSONObject jsonObjectBaseCode = new JSONObject();
                jsonObjectBaseCode.set("flowRoleId", dbBaseCode.getItemId());
                jsonObjectBaseCode.set("flowRolePid", sysDepartment.getDepartmentId());
                jsonObjectBaseCode.set("flowRoleName", dbBaseCode.getItemName());
                jsonObjectBaseCode.set("isLeaf", true);
                jsonArrayBaseCode.add(jsonObjectBaseCode);
            } else if(flag == 5 && (FlowRoleIdNum > 400 && FlowRoleIdNum < 500)) {
                JSONObject jsonObjectBaseCode = new JSONObject();
                jsonObjectBaseCode.set("flowRoleId", dbBaseCode.getItemId());
                jsonObjectBaseCode.set("flowRolePid", sysDepartment.getDepartmentId());
                jsonObjectBaseCode.set("flowRoleName", dbBaseCode.getItemName());
                jsonObjectBaseCode.set("isLeaf", true);
                jsonArrayBaseCode.add(jsonObjectBaseCode);
            } else if(flag == 6 && (FlowRoleIdNum > 500 && FlowRoleIdNum < 600)) {
                JSONObject jsonObjectBaseCode = new JSONObject();
                jsonObjectBaseCode.set("flowRoleId", dbBaseCode.getItemId());
                jsonObjectBaseCode.set("flowRolePid", sysDepartment.getDepartmentId());
                jsonObjectBaseCode.set("flowRoleName", dbBaseCode.getItemName());
                jsonObjectBaseCode.set("isLeaf", true);
                jsonArrayBaseCode.add(jsonObjectBaseCode);
            }
            
            
//            if(StrUtil.equals("zb", dbBaseCode.getExt1())) {
//                JSONObject jsonObjectBaseCode = new JSONObject();
//                jsonObjectBaseCode.set("flowRoleId", dbBaseCode.getItemId());
//                jsonObjectBaseCode.set("flowRolePid", sysDepartment.getDepartmentId());
//                jsonObjectBaseCode.set("flowRoleName", dbBaseCode.getItemName());
//                jsonObjectBaseCode.set("isLeaf", true);
//                jsonArrayBaseCode.add(jsonObjectBaseCode);
//            } else {
//                if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
//                        && FlowRoleIdNum > 200) {
//                    continue;
//                } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)
//                        && !(FlowRoleIdNum > 200 && FlowRoleIdNum < 300)) {
//                    continue;
//                } else if((StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)
//                        || StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1))
//                        && !(FlowRoleIdNum > 300 && FlowRoleIdNum < 600)) {
//                    continue;
//                } 
//                JSONObject jsonObjectBaseCode = new JSONObject();
//                jsonObjectBaseCode.set("flowRoleId", dbBaseCode.getItemId());
//                jsonObjectBaseCode.set("flowRolePid", sysDepartment.getDepartmentId());
//                jsonObjectBaseCode.set("flowRoleName", dbBaseCode.getItemName());
//                jsonObjectBaseCode.set("isLeaf", true);
//                jsonArrayBaseCode.add(jsonObjectBaseCode);
//            }
        }
        jsonObject.set("children", jsonArrayBaseCode);
        jsonArray.add(jsonObject);
        return repEntity.ok(jsonArray);
    }

    /**
     * 获取【所有部门、人员】并形成树形结构（通用版本）,代表一层一层请求
     * 
     * @param sysDepartment
     * @return 【所有部门、人员】，并形成树形结构
     */
    @Override
    public ResponseEntity getSysUserFlowSetUpTree(SysDepartment sysDepartment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String companyId = TokenUtils.getCompanyId(request);
        String ext1 = TokenUtils.getExt1(request);
        if(sysDepartment == null || StrUtil.isEmpty(sysDepartment.getDepartmentParentId())) {
            sysDepartment = new SysDepartment();
            sysDepartment.setDepartmentParentId("0");
        }
        // 项目类型时
        if(StrUtil.equals("3", ext1) || StrUtil.equals("4", ext1)) {
            sysDepartment.setDepartmentId(sysDepartment.getDepartmentParentId());
            return sysProjectService.getSysProjectUserFlowTree(sysDepartment);
        }
        if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
            return getSysUserListByLikeTree(sysDepartment.getSearchType(), sysDepartment.getSearch());
        }
  
        JSONArray jsonArray = new JSONArray();
        // 1:部门
        if(StrUtil.isEmpty(sysDepartment.getType())) {
            sysDepartment.setType("1");
        }

        // 权限判断（非集团时）
        if(!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
            if(StrUtil.equals("0", sysDepartment.getDepartmentParentId())) {
                sysDepartment.setDepartmentPath(companyId);
                // 拼凑根节点
                SysDepartment sysDepartmentCompany = sysDepartmentMapper.selectByPrimaryKey(companyId);
                if(sysDepartmentCompany != null) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.set("valuePid", sysDepartmentCompany.getDepartmentParentId());
                    jsonObject.set("value", companyId);
                    jsonObject.set("label", sysDepartmentCompany.getDepartmentName());
                    jsonObject.set("title", sysDepartmentCompany.getDepartmentName());
                    jsonObject.set("type", "1");
                    jsonArray.add(jsonObject);
                }
                // 拼凑多个所属公司时的根节点
                if(StrUtil.isNotEmpty(companyId) && companyId.indexOf("999999")<0) {
                    JSONArray departmentJSONArray = new JSONArray(publicConfig.getProperty("share.department", null));
                    if(departmentJSONArray != null && departmentJSONArray.size()>0) {
                        for (Iterator<Object> iterator = departmentJSONArray.iterator(); iterator.hasNext();) {
                            JSONObject shareJSONObject = (JSONObject)iterator.next();
                            String shareCompanyId = shareJSONObject.getStr("departmentId");
                            // 不相同的companyId时，添加共享节点
                            if(!StrUtil.equals(companyId, shareCompanyId)) {
                                sysDepartmentCompany = sysDepartmentMapper.selectByPrimaryKey(shareCompanyId);
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.set("valuePid", sysDepartmentCompany.getDepartmentParentId());
                                jsonObject.set("value", companyId);
                                jsonObject.set("label", sysDepartmentCompany.getDepartmentName());
                                jsonObject.set("title", sysDepartmentCompany.getDepartmentName());
                                jsonObject.set("type", "1");
                                // 添加共享根节点
                                jsonArray.add(jsonObject);
                            }
                        }
                    }
                }
                return repEntity.ok(jsonArray); 
            }
        }
        
        // 调用方法获取部门及人员数据
        jsonArray.addAll(getPrivateSysUserFlowTree(sysDepartment, true));
        return repEntity.ok(jsonArray);
    }

    /**
     * 获取【流程人员树结构】并形成树形结构（通用版本）,代表一层一层请求
     * 
     * @param sysDepartment
     * @return 【流程人员树结构】，并形成树形结构
     */
    @Override
    public ResponseEntity getSysUserFlowRoleTree(SysDepartment sysDepartment) {
//        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
//        String userId = TokenUtils.getUserId(request);
//        String companyId = TokenUtils.getCompanyId(request);
//        String ext1 = TokenUtils.getExt1(request);
        JSONArray jsonArray = new JSONArray();
        SysFlowUser sysFlowUser =  new SysFlowUser();
        sysFlowUser.setFlowRoleId(sysDepartment.getFlowRoleId());

        int flowRoleIdNum = Integer.valueOf(sysDepartment.getFlowRoleId());
        // 局
        if(flowRoleIdNum < 200) {
            SysDepartment sysDepartmentSelect = new SysDepartment();
            sysDepartmentSelect.setDepartmentParentId("9999999999");
            sysDepartmentSelect.setDepartmentFlag(SysConst.SYS_DEPARTMENT_TYPE_TOP);
            List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartmentSelect);
            sysDepartment.setTopId(sysDepartmentList.get(0).getDepartmentId());
        } else if(flowRoleIdNum > 200 && flowRoleIdNum < 300) {
            // 公司的内容
            SysFlowUserProject sysFlowUserProject = sysFlowUserProjectMapper.selectByPrimaryKey(sysDepartment.getWorkId());
            sysDepartment.setTopId(sysFlowUserProject.getCompanyId());
        }  else if(flowRoleIdNum > 400 && flowRoleIdNum < 500) {
            // 总部
            SysFlowUserProject sysFlowUserProject =sysFlowUserProjectMapper.selectByPrimaryKey("HZ9d508177138d7d017713a395610034");
            sysDepartment.setTopId(sysFlowUserProject.getHeadquartersId());
        }  else if(flowRoleIdNum > 500 && flowRoleIdNum < 600) {
            // 托管
            SysFlowUserProject sysFlowUserProject =sysFlowUserProjectMapper.selectByPrimaryKey("HZ9d508177138d7d017713a395610034");
            sysDepartment.setTopId(sysFlowUserProject.getDelegateId());
        }

        if(StrUtil.equals("0", sysDepartment.getDepartmentParentId())) {
            sysFlowUser.setValue(sysDepartment.getTopId());
            List<SysFlowUser> sysFlowUserList = sysFlowUserMapper.selectBySysFlowUserList(sysFlowUser);
            SysFlowUser dbSysFlowUser = sysFlowUserList.get(0);
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("value", dbSysFlowUser.getValue());
            jsonObject.set("valuePid", dbSysFlowUser.getValuePid());
            jsonObject.set("label", dbSysFlowUser.getLabel());
            jsonObject.set("title", dbSysFlowUser.getLabel());
            jsonObject.set("type", dbSysFlowUser.getType());
            jsonArray.add(jsonObject);
            return repEntity.ok(jsonArray);
        }
        sysFlowUser.setTopId(sysDepartment.getTopId());
//        sysDepartment.setDepartmentParentId(sysDepartment.getTopId());
        sysFlowUser.setValuePid(sysDepartment.getDepartmentParentId());
        List<SysFlowUser> sysFlowUserList = sysFlowUserMapper.selectBySysFlowUserList(sysFlowUser);
        for(SysFlowUser dbSysFlowUser:sysFlowUserList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.set("value", dbSysFlowUser.getValue());
            jsonObject.set("valuePid", dbSysFlowUser.getValuePid());
            jsonObject.set("label", dbSysFlowUser.getLabel());
            jsonObject.set("title", dbSysFlowUser.getLabel());
            jsonObject.set("type", dbSysFlowUser.getType());
            jsonArray.add(jsonObject);
        }
        return repEntity.ok(jsonArray);
    }

    //---------------↓↓↓项目信息↓↓↓-----------------
    @Override
    public List getSysProjectListByMapper(SysDepartment sysDepartment) {
        SysProject sysProject = new SysProject();
        BeanUtil.copyProperties(sysDepartment, sysProject, true);
        List<SysProject> sysProjectList = sysProjectMapper.selectBySysProjectList(sysProject);
        return sysProjectList;
    }

    @Override
    public SysDepartment getSysProjectPrimaryKeyByMapper(String departmentId) {
        SysProject sysProject = sysProjectMapper.selectByPrimaryKey(departmentId);
        SysDepartment sysDepartment = new SysDepartment();
        BeanUtil.copyProperties(sysProject, sysDepartment, true);
        return sysDepartment;
    }
    //---------------↑↑↑项目信息↑↑↑-----------------
    
    //---------------↓↓↓ 中交 ↓↓↓------------
    /**
     * 获取中交【所有部门、人员】并形成树形结构（通用版本）,代表一层一层请求
     * 
     * @param sysDepartment
     * @return 【所有部门、人员】，并形成树形结构
     */
    @Override
    public ResponseEntity getSysDepartmentCurrentTreeByZj(SysDepartment sysDepartment) {
        if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
            return getSysUserListByLikeTree(sysDepartment.getSearchType(), sysDepartment.getSearch());
        }
        JSONArray jsonArray = new JSONArray();
        
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String companyId = TokenUtils.getCompanyId(request);
        String userId = TokenUtils.getUserId(request);
        if(StrUtil.equals("admin", userId)) {
            companyId="a5d82aM11cf9cf7329M65b09eb8996c077cbd7a49b1f0d7c83d";
        }
        // 公司id是空的时候，不返回任何数据
        if(StrUtil.isEmpty(companyId)) {
            return repEntity.ok(jsonArray);
        }

        if(sysDepartment == null || 
                StrUtil.isEmpty(sysDepartment.getDepartmentParentId()) 
                || StrUtil.equals("0", sysDepartment.getDepartmentParentId())) {
            sysDepartment = new SysDepartment();
            sysDepartment.setDepartmentParentId("0");
            sysDepartment.setType("1");

            // 非局身份进来，把顶级的分公司+局信息展示给前端
            if(!StrUtil.equals("a5d82aM11cf9cf7329M65b09eb8996c077cbd7a49b1f0d7c83d", companyId)) {
                
                // 为顶部层级的固定值
                sysDepartment.setDepartmentParentId("a5d82aM11cf9cea644M65b09eb8996c077cbd7a49b1f0d7c83d");
                
                // 局机关时，固定局机关路径
                sysDepartment.setDepartmentPath("a5d82aM11cf9cf7329M65b09eb8996c077cbd7a49b1f0d7c83d");
                jsonArray.addAll(getPrivateSysUserCurrentTree(sysDepartment, false));
                
                // 非局身份时，当前公司路径
                sysDepartment.setDepartmentPath(companyId);
                jsonArray.addAll(getPrivateSysUserCurrentTree(sysDepartment, false));
                
                return repEntity.ok(jsonArray);        
            }
        }
        
        // 局机关或根据【下层节点查找时】时
        sysDepartment.setDepartmentPath("");
        jsonArray.addAll(getPrivateSysUserCurrentTree(sysDepartment, false));

        return this.repEntity.ok(jsonArray);
    }

    /**
     * 获取中交【所有部门、人员】并形成树形结构（通用版本）,代表一层一层请求
     * 
     * @param sysDepartment
     * @return 【所有部门、人员】，并形成树形结构
     */
    @Override
    public ResponseEntity getSysUserCurrentTreeByZj(SysDepartment sysDepartment) {
        if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
            return getSysUserListByLikeTree(sysDepartment.getSearchType(), sysDepartment.getSearch());
        }
        JSONArray jsonArray = new JSONArray();
        
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String companyId = TokenUtils.getCompanyId(request);
        String userId = TokenUtils.getUserId(request);
        if(StrUtil.equals("admin", userId)) {
            companyId="a5d82aM11cf9cf7329M65b09eb8996c077cbd7a49b1f0d7c83d";
        }
        // 公司id是空的时候，不返回任何数据
        if(StrUtil.isEmpty(companyId)) {
            return repEntity.ok(jsonArray);
        }

        if(sysDepartment == null 
                || StrUtil.isEmpty(sysDepartment.getDepartmentParentId()) 
                || StrUtil.equals("0", sysDepartment.getDepartmentParentId())) {
            sysDepartment = new SysDepartment();
            sysDepartment.setDepartmentParentId("0");
            sysDepartment.setType("1");

            // 非局身份进来，把顶级的分公司+局信息展示给前端
            if(!StrUtil.equals("a5d82aM11cf9cf7329M65b09eb8996c077cbd7a49b1f0d7c83d", companyId)) {
                
                // 为顶部层级的固定值
                sysDepartment.setDepartmentParentId("a5d82aM11cf9cea644M65b09eb8996c077cbd7a49b1f0d7c83d");
                
                // 局机关时，固定局机关路径
                sysDepartment.setDepartmentPath("a5d82aM11cf9cf7329M65b09eb8996c077cbd7a49b1f0d7c83d");
                jsonArray.addAll(getPrivateSysUserCurrentTree(sysDepartment, true));
                
                // 非局身份时，当前公司路径
                sysDepartment.setDepartmentPath(companyId);
                jsonArray.addAll(getPrivateSysUserCurrentTree(sysDepartment, true));
                
                return repEntity.ok(jsonArray);        
            }
        }
        
        // 局机关或根据【下层节点查找时】时
        sysDepartment.setDepartmentPath("");
        // 不检索外部人员
        sysDepartment.setAccountAppType("1");
        jsonArray.addAll(getPrivateSysUserCurrentTree(sysDepartment, true));

        return this.repEntity.ok(jsonArray);
    }

    /**
     * 中交·获取【本部门】并形成树形结构
     * 
     * @param sysDepartment
     * @return 【本部门】，并形成树形结构
     */
    @Override
    public ResponseEntity getSysDepartmentTreeByZj(SysDepartment sysDepartment) {
        if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
            return getSysUserListByLikeTreeByZj(sysDepartment.getSearch(), "");
        }
        if(sysDepartment == null) {
            sysDepartment = new SysDepartment();
        }

        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String companyId = TokenUtils.getCompanyId(request);
        String cacheKey = TokenUtils.getCompanyId(request) + "getSysDepartmentTreeByZj";
        // 返回缓存
        if (cacheMap != null && cacheMap.get(cacheKey) != null) {
            return repEntity.ok(cacheMap.get(cacheKey));
        }
        
        // 自己部门
        sysDepartment.setDepartmentPath(companyId);
        List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
        
        JSONArray jsonArray = ConvertUtil.listToTree(new JSONArray(sysDepartmentList),
                "departmentId", "departmentParentId", "departmentName", "departmentName");
        
        cacheMap.put(cacheKey, jsonArray);
        
        return this.repEntity.ok(jsonArray);
    }
    
    /**
     * 中交·获取【本部门+局】并形成树形结构
     * 
     * @param sysDepartment
     * @return 【本部门+局】，并形成树形结构
     */
    @Override
    public ResponseEntity getSysDepartmentTreeByZjAddOther(SysDepartment sysDepartment) {
        if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
            return getSysUserListByLikeTreeByZj(sysDepartment.getSearch(), "1");
        }
        if(sysDepartment == null) {
            sysDepartment = new SysDepartment();
        }
        
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String companyId = TokenUtils.getCompanyId(request);
        String cacheKey = TokenUtils.getCompanyId(request) + "getSysDepartmentTreeByZjAddOther";
        // 返回缓存
        if (cacheMap != null && cacheMap.get(cacheKey) != null) {
            return repEntity.ok(cacheMap.get(cacheKey));
        }
        // 如果是局身份进来，则应该看见全部信息，否则分公司+局信息
        if(StrUtil.equals("a5d82aM11cf9cf7329M65b09eb8996c077cbd7a49b1f0d7c83d", companyId)) {
            List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
            JSONArray jsonArray = ConvertUtil.listToTree(new JSONArray(sysDepartmentList), "departmentId",
                    "departmentParentId", "departmentName", "departmentName");
            cacheMap.put(cacheKey, jsonArray);
            return this.repEntity.ok(jsonArray);
        } else {
            List<SysDepartment> sysDepartmentListAll = Lists.newArrayList();
            // 局机关
            sysDepartment.setDepartmentPath("a5d82aM11cf9cf7329M65b09eb8996c077cbd7a49b1f0d7c83d");
            List<SysDepartment> sysDepartmentListJu = sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
            sysDepartmentListAll.addAll(sysDepartmentListJu);
            
            // 自己部门
            sysDepartment.setDepartmentPath(companyId);
            List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
            sysDepartmentListAll.addAll(sysDepartmentList);
            JSONArray jsonArray = ConvertUtil.listToTree(new JSONArray(sysDepartmentListAll), "departmentId",
                    "departmentParentId", "departmentName", "departmentName");
            
            cacheMap.put(cacheKey, jsonArray);
            
            return this.repEntity.ok(jsonArray);
        }
    }

    /**
     * 中交·获取【本公司人员】并形成树形结构
     * 
     * @param sysDepartment
     * @return 【本公司人员】，并形成树形结构
     */
    @Override
    public ResponseEntity getSysUserTreeByZj(SysDepartment sysDepartment) {
        if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
            return getSysUserListByLikeTreeByZj(sysDepartment.getSearch(), "");
        }
        if(sysDepartment == null) {
            sysDepartment = new SysDepartment();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String companyId = TokenUtils.getCompanyId(request);
        String cacheKey = TokenUtils.getCompanyId(request) + "getSysUserTreeByZj";
        // 返回缓存
        if (cacheMap != null && cacheMap.get(cacheKey) != null) {
            return repEntity.ok(cacheMap.get(cacheKey));
        }
        
        List<SysDepartment>  sysDepartmentListAll = Lists.newArrayList();

        // 自己公司
        sysDepartment.setDepartmentPath(companyId);
        List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
        sysDepartmentListAll.addAll(sysDepartmentList);
        
        // 自己公司-人员
        SysUser sysUser = new SysUser();
        sysUser.setAccountAppType("1");
        sysUser.setCompanyId(companyId);
        List<SysDepartment> sysUserToSysDepartmentList = sysDepartmentMapper.getUserListByCompanyId(sysUser);
        sysDepartmentListAll.addAll(sysUserToSysDepartmentList);
        
        // 人员信息整合到部门里
        JSONArray jsonArray = ConvertUtil.listToTree(new JSONArray(sysDepartmentListAll), "departmentId",
                "departmentParentId", "departmentName", "realName");
        
        cacheMap.put(cacheKey, jsonArray);
        
        return this.repEntity.ok(jsonArray);
    }

    /**
     * 中交·获取【本公司人员+局】并形成树形结构
     * 
     * @param sysDepartment
     * @return 【本公司人员+局】，并形成树形结构
     */
    @Override
    public ResponseEntity getSysUserTreeByZjAddOther(SysDepartment sysDepartment) {
        if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
            return getSysUserListByLikeTreeByZj(sysDepartment.getSearch(), "1");
        }
        if(sysDepartment == null) {
            sysDepartment = new SysDepartment();
        }
        
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String companyId = TokenUtils.getCompanyId(request);
        String cacheKey = TokenUtils.getCompanyId(request) + "getSysUserTreeByZjAddOther";
        // 返回缓存
        if (cacheMap != null && cacheMap.get(cacheKey) != null) {
            return repEntity.ok(cacheMap.get(cacheKey));
        }

        // 如果是局身份进来，则应该看见全部信息，否则分公司+局信息
        if(StrUtil.equals("a5d82aM11cf9cf7329M65b09eb8996c077cbd7a49b1f0d7c83d", companyId)) {
            // 全部部门
            List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
            
            // 全部人员
            SysUser sysUser = new SysUser();
            sysUser.setAccountAppType("1");
            // 默认全部
            sysUser.setCompanyId("");
            List<SysDepartment> sysUserToSysDepartmentList = sysDepartmentMapper.getUserListByCompanyId(sysUser);
            sysDepartmentList.addAll(sysUserToSysDepartmentList);
            // 转换
            JSONArray jsonArray = ConvertUtil.listToTree(new JSONArray(sysDepartmentList), "departmentId",
                    "departmentParentId", "departmentName", "realName");
            cacheMap.put(cacheKey, jsonArray);
            
            return this.repEntity.ok(jsonArray);
        } else {
            List<SysDepartment>  sysDepartmentListAll = Lists.newArrayList();
            // 局机关-部门
            sysDepartment.setDepartmentPath("a5d82aM11cf9cf7329M65b09eb8996c077cbd7a49b1f0d7c83d");
            List<SysDepartment> sysDepartmentListJu = sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
            sysDepartmentListAll.addAll(sysDepartmentListJu);
            // 局机关-人员
            SysUser sysUser = new SysUser();
            sysUser.setAccountAppType("1");
            sysUser.setCompanyId("a5d82aM11cf9cf7329M65b09eb8996c077cbd7a49b1f0d7c83d");
            List<SysDepartment> sysUserToSysDepartmentListJu = sysDepartmentMapper.getUserListByCompanyId(sysUser);
            sysDepartmentListAll.addAll(sysUserToSysDepartmentListJu);
            
            // 自己公司
            sysDepartment.setDepartmentPath(companyId);
            List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
            sysDepartmentListAll.addAll(sysDepartmentList);
            
            // 自己公司-人员
            sysUser = new SysUser();
            sysUser.setAccountAppType("1");
            sysUser.setCompanyId(companyId);
            List<SysDepartment> sysUserToSysDepartmentList = sysDepartmentMapper.getUserListByCompanyId(sysUser);
            sysDepartmentListAll.addAll(sysUserToSysDepartmentList);
            
            // 人员信息整合到部门里
            JSONArray jsonArray = ConvertUtil.listToTree(new JSONArray(sysDepartmentListAll), "departmentId",
                    "departmentParentId", "departmentName", "realName");
            
            cacheMap.put(cacheKey, jsonArray);
            
            return this.repEntity.ok(jsonArray);
        }
    }
    
    /**
     * 获取中交【所有部门、人员】并形成树形结构（中交）
     * 
     * @param sysDepartment
     * @return 中交【所有部门、人员】，并形成树形结构
     */
    @Override
    public ResponseEntity getSysUserTreeByZjAll(SysDepartment sysDepartment) {
        if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
            return getSysUserListByLikeTree(sysDepartment.getSearchType(), sysDepartment.getSearch());
        }
        if(sysDepartment == null) {
            sysDepartment = new SysDepartment();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String companyId = TokenUtils.getCompanyId(request);
        String ext1 = TokenUtils.getExt1(request);
        if(StrUtil.equals("admin", userId) || StrUtil.equals("1", ext1)) {
            companyId = "";
        }
        String cacheKey = companyId + "getSysUserTreeByZjAll";
        // 返回缓存
        if (cacheMap != null && cacheMap.get(cacheKey) != null) {
            return repEntity.ok(cacheMap.get(cacheKey));
        }
        
        // 全部部门
        List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
        
        // 全部人员
        SysUser sysUser = new SysUser();
        sysUser.setAccountAppType("1");
        List<SysDepartment> sysUserToSysDepartmentList = sysDepartmentMapper.getUserListByCompanyId(sysUser);
        sysDepartmentList.addAll(sysUserToSysDepartmentList);
        
        // 转换
        JSONArray jsonArray = ConvertUtil.listToTree(new JSONArray(sysDepartmentList), "departmentId", "departmentParentId", "departmentName", "realName");
        cacheMap.put(cacheKey, jsonArray);
        
        return this.repEntity.ok(jsonArray);
    }
    
    /**
     * 中交厦门人资处理多项目问题
     */
    @Override
    public ResponseEntity getSysDepartmentTreeByZjXmrz(SysDepartment sysDepartment) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String userId = TokenUtils.getUserId(request);
        String mobile = TokenUtils.getMobile(request);
        if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
            return getSysUserListByLikeTree(sysDepartment.getSearchType(), sysDepartment.getSearch());
        }
        if(sysDepartment == null) {
            sysDepartment = new SysDepartment();
        }
        String companyId = TokenUtils.getCompanyId(request);
        String ext1 = TokenUtils.getExt1(request);
        if(StrUtil.equals("admin", userId) || StrUtil.equals("1", ext1)) {
            companyId = "";
        }
        sysDepartment.setDepartmentPath(companyId);
        // 清当前用户缓存
        String cacheKey = companyId + "getSysDepartmentTree";
        // 返回缓存
        if (cacheMap != null && cacheMap.get(cacheKey) != null) {
//          return repEntity.ok(cacheMap.get(cacheKey));
        }
        sysDepartment.setDepartmentParentId("");
        List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);

        // 共享部门
        if(StrUtil.isNotEmpty(companyId) && companyId.indexOf("999999")<0) {
            JSONArray shareDepartmentJSONArray = new JSONArray(publicConfig.getProperty("share.department", null));
            if(shareDepartmentJSONArray != null && shareDepartmentJSONArray.size()>0) {
                for (Iterator<Object> iterator = shareDepartmentJSONArray.iterator(); iterator.hasNext();) {
                    JSONObject jsonObject = (JSONObject)iterator.next();
                    SysDepartment sysDepartmentShare = new SysDepartment();
                    sysDepartmentShare.setDepartmentPath(jsonObject.getStr("departmentId"));
                    List<SysDepartment> sysDepartmentShareList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartmentShare);
                    // 添加共享部门
                    sysDepartmentList.addAll(sysDepartmentShareList);
                }
            }
        }
        // 厦门公司特殊添加
        List<SysCompany> sysCompanyList = selectCompanyListByMobile(mobile, "1");
        if(sysCompanyList.size()>1) {
            for(SysCompany sysCompany:sysCompanyList) {
                if(!StrUtil.equals(companyId, sysCompany.getCompanyId())) {
                    SysDepartment sysDepartmentShare = new SysDepartment();
                    sysDepartmentShare.setDepartmentPath(sysCompany.getCompanyId());
                    List<SysDepartment> sysDepartmentShareList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartmentShare);
                    // 添加共享部门
                    sysDepartmentList.addAll(sysDepartmentShareList);
                }
            }
        }
        
        JSONArray jsonArray = ConvertUtil.listToTree(new JSONArray(sysDepartmentList), "departmentId", "departmentParentId", "departmentName", "departmentName");
        
        cacheMap.put(cacheKey, jsonArray);
        
        return this.repEntity.ok(jsonArray);
    }
    
    /**
     * 获取【所有部门、人员】并形成树形结构（通用版本）
     * 
     * @param sysDepartment
     * @return 【所有部门、人员】，并形成树形结构
     */
    @Override
    public ResponseEntity getSysUserTreeByZjXmrz(SysDepartment sysDepartment) {
        if (sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getSearch())) {
            return getSysUserListByLikeTree(sysDepartment.getSearchType(), sysDepartment.getSearch());
        }
        if(sysDepartment == null) {
            sysDepartment = new SysDepartment();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String mobile = TokenUtils.getMobile(request);
        String companyId = TokenUtils.getCompanyId(request);
        String ext1 = TokenUtils.getExt1(request);
        if(StrUtil.equals("admin", userId) || StrUtil.equals("1", ext1)) {
            companyId = "";
        }
        String cacheKey = companyId + "getSysUserTree";
        // 返回缓存
        if (cacheMap != null && cacheMap.get(cacheKey) != null) {
//            return repEntity.ok(cacheMap.get(cacheKey));
        }
        
        // 全部部门
        sysDepartment.setDepartmentPath(companyId);
        sysDepartment.setDepartmentParentId("");
        List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
        
        // 全部人员
        SysUser sysUser = new SysUser();
        sysUser.setAccountAppType("1");
        // 默认全部
        sysUser.setCompanyId(companyId);
        List<SysDepartment> sysUserToSysDepartmentList = sysDepartmentMapper.getUserListByCompanyId(sysUser);
        sysDepartmentList.addAll(sysUserToSysDepartmentList);
        
        // 共享部门&人员（比如17局增加劳务部人员）
        String shareCompanyId = TokenUtils.getCompanyId(request);
//        if(StrUtil.isNotEmpty(shareCompanyId) && shareCompanyId.indexOf("999999")<0) {
//            JSONArray shareDepartmentJSONArray = new JSONArray(publicConfig.getProperty("share.department", null));
//            if(shareDepartmentJSONArray != null && shareDepartmentJSONArray.size()>0) {
//                for (Iterator<Object> iterator = shareDepartmentJSONArray.iterator(); iterator.hasNext();) {
//                    JSONObject jsonObject = (JSONObject)iterator.next();
//                    // 如果是相同的共享机构，则不显示
//                    if(StrUtil.equals(jsonObject.getStr("departmentId"), shareCompanyId)) {
//                        continue;
//                    }
//                    SysDepartment sysDepartmentShare = new SysDepartment();
//                    sysDepartmentShare.setDepartmentPath(jsonObject.getStr("departmentId"));
//                    List<SysDepartment> sysDepartmentShareList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartmentShare);
//                    // 添加共享部门
//                    sysDepartmentList.addAll(sysDepartmentShareList);
//                    
//                    // 全部人员
//                    SysUser sysUserShare = new SysUser();
//                    sysUserShare.setAccountAppType("1");
//                    // 默认全部
//                    sysUserShare.setCompanyId(jsonObject.getStr("departmentId"));
//                    List<SysDepartment> sysUserToSysUsertList = sysDepartmentMapper.getUserListByCompanyId(sysUserShare);
//                    sysDepartmentList.addAll(sysUserToSysUsertList);
//                }
//            }
//        }
        
        
        List<SysCompany>  sysCompanyList = selectCompanyListByMobile(mobile, "1");
        if(sysCompanyList.size()>1) {
            for(SysCompany sysCompany:sysCompanyList) {
                if(!StrUtil.equals(companyId, sysCompany.getCompanyId())) {
                    SysDepartment sysDepartmentShare = new SysDepartment();
                    sysDepartmentShare.setDepartmentPath(sysCompany.getCompanyId());
                    List<SysDepartment> sysDepartmentShareList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartmentShare);
                    // 添加共享部门
                    sysDepartmentList.addAll(sysDepartmentShareList);
                    
                    
                    // 全部人员
                    SysUser sysUserShare = new SysUser();
                    sysUserShare.setAccountAppType("1");
                    // 默认全部
                    sysUserShare.setCompanyId(sysCompany.getCompanyId());
                    List<SysDepartment> sysUserToSysUsertList = sysDepartmentMapper.getUserListByCompanyId(sysUserShare);
                    sysDepartmentList.addAll(sysUserToSysUsertList);
                }
            }
        }
        
        // 转换
        JSONArray jsonArray = ConvertUtil.listToTree(new JSONArray(sysDepartmentList), "departmentId", "departmentParentId", "departmentName", "realName");
        cacheMap.put(cacheKey, jsonArray);
        
        return this.repEntity.ok(jsonArray);
    }
    
    @Override
    public ResponseEntity getSysDepartmentListByProjectXmrz(SysDepartment sysDepartment) {
        SysDepartment dbDepartment = new SysDepartment();
        if(sysDepartment != null &&
                StrUtil.isNotEmpty(sysDepartment.getDepartmentId())) {
            dbDepartment = sysDepartmentMapper.selectByPrimaryKey(sysDepartment.getDepartmentId());
        }
        return repEntity.ok(dbDepartment);
    }
    
    //---------------↑↑↑ 中交 ↑↑↑------------
    
    
    // -------------↓↓↓ 私有方法  ↓↓↓------------
    /**
     * 获取全部数据（公司+项目）
     * 
     * @param sysDepartment
     * @param userDataFlag
     * @return
     */
    private JSONArray getPrivateSysUserCurrentTree(SysDepartment sysDepartment, boolean userDataFlag) {
        JSONArray jsonArray = new JSONArray();
        // 当前公司路径
        SysDepartment sysDepartmentSelect = new SysDepartment();
        sysDepartmentSelect.setDepartmentParentId(sysDepartment.getDepartmentParentId());
        sysDepartmentSelect.setDepartmentPath(sysDepartment.getDepartmentPath());
        // 前端给的type=1则查询当前的部门下所有部门 
        if(StrUtil.equals("1", sysDepartment.getType())) {
            // 部门数据
            List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartmentSelect);
            if(sysDepartmentList != null && sysDepartmentList.size()>0) {
                for(SysDepartment dbSysDepartment:sysDepartmentList){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.set("value", dbSysDepartment.getDepartmentId());
                    jsonObject.set("valuePid", sysDepartment.getDepartmentParentId());
                    jsonObject.set("label", dbSysDepartment.getDepartmentName());
                    jsonObject.set("title", dbSysDepartment.getDepartmentName());
                    jsonObject.set("type", "1");
                    jsonArray.add(jsonObject);
                }        
            }
            
            // 项目表的部门数据
            SysProject sysProjectCheck = sysProjectMapper.selectByPrimaryKey(sysDepartment.getDepartmentParentId());
            if(sysProjectCheck != null) {
                SysProject sysProjectSelect = new SysProject();
                sysProjectSelect.setDepartmentParentId(sysDepartment.getDepartmentParentId());
                sysProjectSelect.setDepartmentPath(sysDepartment.getDepartmentPath());
                List<SysProject> sysProjectList = sysProjectMapper.selectBySysProjectList(sysProjectSelect);
                if(sysProjectList != null && sysProjectList.size()>0) {
                    for(SysProject dbSysProject:sysProjectList){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.set("value", dbSysProject.getDepartmentId());
                        jsonObject.set("valuePid", sysDepartment.getDepartmentParentId());
                        jsonObject.set("label", dbSysProject.getDepartmentName());
                        jsonObject.set("title", dbSysProject.getDepartmentName());
                        jsonObject.set("type", "1");
                        jsonArray.add(jsonObject);
                    }        
                }
            }
        }
        // 查找confDown部门表下所有数据
        SysDepartment sysDepartmentKey = sysDepartmentMapper.selectByPrimaryKey(sysDepartment.getDepartmentParentId());
        if(sysDepartmentKey != null && StrUtil.isNotEmpty(sysDepartmentKey.getConfDown())) {
            JSONArray confDownjsonArray =  new JSONArray(sysDepartmentKey.getConfDown());
            if(confDownjsonArray != null && confDownjsonArray.size()>0) {
                for (Iterator<Object> iterator = confDownjsonArray.iterator(); iterator.hasNext();) {
                    Object jsonObject = (Object)iterator.next();
                    SysDepartment dbSysDepartment = sysDepartmentMapper.selectByPrimaryKey(jsonObject.toString());
                    if(dbSysDepartment != null) {
                        JSONObject confDownJSONObject = new JSONObject();
                        confDownJSONObject.set("value", dbSysDepartment.getDepartmentId());
                        confDownJSONObject.set("valuePid", sysDepartment.getDepartmentParentId());
                        confDownJSONObject.set("label", dbSysDepartment.getDepartmentName());
                        confDownJSONObject.set("title", dbSysDepartment.getDepartmentName());
                        confDownJSONObject.set("type", "1");
                        jsonArray.add(confDownJSONObject);
                    }
                    // 项目
                    SysProject dbSysProject = sysProjectMapper.selectByPrimaryKey(jsonObject.toString());
                    if(dbSysProject != null) {
                        JSONObject confDownJSONObject = new JSONObject();
                        confDownJSONObject.set("value", dbSysProject.getDepartmentId());
                        confDownJSONObject.set("valuePid", sysDepartment.getDepartmentParentId());
                        confDownJSONObject.set("label", dbSysProject.getDepartmentName());
                        confDownJSONObject.set("title", dbSysProject.getDepartmentName());
                        confDownJSONObject.set("type", "1");
                        jsonArray.add(confDownJSONObject);
                    }
                    
                }
            }
        }
        // 查找confDown项目表下所有数据
        SysProject sysProjectKey = sysProjectMapper.selectByPrimaryKey(sysDepartment.getDepartmentParentId());
        if(sysProjectKey != null && StrUtil.isNotEmpty(sysProjectKey.getConfDown())) {
            JSONArray confDownjsonArray =  new JSONArray(sysProjectKey.getConfDown());
            if(confDownjsonArray != null && confDownjsonArray.size()>0) {
                for (Iterator<Object> iterator = confDownjsonArray.iterator(); iterator.hasNext();) {
                    Object jsonObject = (Object)iterator.next();
                    SysDepartment dbSysDepartment = sysDepartmentMapper.selectByPrimaryKey(jsonObject.toString());
                    if(dbSysDepartment != null) {
                        JSONObject confDownJSONObject = new JSONObject();
                        confDownJSONObject.set("value", dbSysDepartment.getDepartmentId());
                        confDownJSONObject.set("valuePid", sysDepartment.getDepartmentParentId());
                        confDownJSONObject.set("label", dbSysDepartment.getDepartmentName());
                        confDownJSONObject.set("title", dbSysDepartment.getDepartmentName());
                        confDownJSONObject.set("type", "1");
                        jsonArray.add(confDownJSONObject);
                    }
                    // 项目
                    SysProject dbSysProject = sysProjectMapper.selectByPrimaryKey(jsonObject.toString());
                    if(dbSysProject != null) {
                        JSONObject confDownJSONObject = new JSONObject();
                        confDownJSONObject.set("value", dbSysProject.getDepartmentId());
                        confDownJSONObject.set("valuePid", sysDepartment.getDepartmentParentId());
                        confDownJSONObject.set("label", dbSysProject.getDepartmentName());
                        confDownJSONObject.set("title", dbSysProject.getDepartmentName());
                        confDownJSONObject.set("type", "1");
                        jsonArray.add(confDownJSONObject);
                    }
                    
                }
            }
        }
        
        // 人员数据
        if(userDataFlag) {
            sysDepartmentSelect = new SysDepartment();
            sysDepartmentSelect.setDepartmentId(sysDepartment.getDepartmentParentId());
            sysDepartmentSelect.setDepartmentPath(sysDepartment.getDepartmentPath());
            sysDepartmentSelect.setAccountAppType(sysDepartment.getAccountAppType());
            List<SysDepartment> sysUserList = sysDepartmentMapper.selectSysDepartmentUserListByLike(sysDepartmentSelect);
            if(sysUserList != null && sysUserList.size()>0) {
                for(SysDepartment dbSysDepartment:sysUserList){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.set("value", dbSysDepartment.getUserKey());
                    jsonObject.set("valuePid", sysDepartment.getDepartmentParentId());
                    jsonObject.set("label", dbSysDepartment.getRealName());
                    jsonObject.set("title", dbSysDepartment.getRealName());
                    jsonObject.set("type", "2");
                    jsonArray.add(jsonObject);
                }        
            }

            // 当前departmentId如果存在于project表则获取当前项目的用户数据
            SysProject sysProject = sysProjectMapper.selectByPrimaryKey(sysDepartment.getDepartmentParentId());
            if(sysProject != null) {
                SysUser sysUserSelect = new SysUser();
                sysUserSelect.setDepartmentId(sysDepartment.getDepartmentParentId());
                List<SysUser> sysUserProjectList = sysUserMapper.selectBySysUserListByDepartment(sysUserSelect);
                if(sysUserProjectList != null && sysUserProjectList.size()>0) {
                    for(SysUser dbSysUser:sysUserProjectList){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.set("value", dbSysUser.getUserKey());
                        jsonObject.set("valuePid", sysDepartment.getDepartmentParentId());
                        jsonObject.set("label", dbSysUser.getRealName());
                        jsonObject.set("title", dbSysUser.getRealName());
                        jsonObject.set("type", "2");
                        jsonArray.add(jsonObject);
                    }        
                }
            }
        }
        return jsonArray;
    }
    
    /**
     * 获取公司数据
     * 
     * @param sysDepartment
     * @param userDataFlag
     * @return
     */
    private JSONArray getPrivateSysUserTreeByCompany(SysDepartment sysDepartment, boolean userDataFlag) {
        JSONArray jsonArray = new JSONArray();
        // 当前公司路径
        SysDepartment sysDepartmentSelect = new SysDepartment();
        sysDepartmentSelect.setDepartmentParentId(sysDepartment.getDepartmentParentId());
        sysDepartmentSelect.setDepartmentPath(sysDepartment.getDepartmentPath());
        // 前端给的type=1则查询当前的部门下所有部门 
        if(StrUtil.equals("1", sysDepartment.getType())) {
            // 部门数据
            List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartmentSelect);
            if(sysDepartmentList != null && sysDepartmentList.size()>0) {
                for(SysDepartment dbSysDepartment:sysDepartmentList){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.set("value", dbSysDepartment.getDepartmentId());
                    jsonObject.set("valuePid", sysDepartment.getDepartmentParentId());
                    jsonObject.set("label", dbSysDepartment.getDepartmentName());
                    jsonObject.set("title", dbSysDepartment.getDepartmentName());
                    jsonObject.set("type", "1");
                    jsonArray.add(jsonObject);
                }        
            }
        }
        
        // 人员数据
        if(userDataFlag) {
            sysDepartmentSelect = new SysDepartment();
            sysDepartmentSelect.setDepartmentId(sysDepartment.getDepartmentParentId());
            sysDepartmentSelect.setDepartmentPath(sysDepartment.getDepartmentPath());
            sysDepartmentSelect.setAccountAppType("1");
            List<SysDepartment> sysUserList = sysDepartmentMapper.selectSysDepartmentUserListByLike(sysDepartmentSelect);
            if(sysUserList != null && sysUserList.size()>0) {
                for(SysDepartment dbSysDepartment:sysUserList){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.set("value", dbSysDepartment.getUserKey());
                    jsonObject.set("valuePid", sysDepartment.getDepartmentParentId());
                    jsonObject.set("label", dbSysDepartment.getRealName());
                    jsonObject.set("title", dbSysDepartment.getRealName());
                    jsonObject.set("type", "2");
                    jsonArray.add(jsonObject);
                }        
            }
        }
        return jsonArray;
    }
    
    /**
     * 获取全部数据（流程设置），流程设置因为是各种的，所以当前公司时，不能显示项目的组织机构
     * 
     * @param sysDepartment
     * @param userDataFlag
     * @return
     */
    private JSONArray getPrivateSysUserFlowTree(SysDepartment sysDepartment, boolean userDataFlag) {
        JSONArray jsonArray = new JSONArray();
        // 当前公司路径
        SysDepartment sysDepartmentSelect = new SysDepartment();
        sysDepartmentSelect.setDepartmentParentId(sysDepartment.getDepartmentParentId());
        sysDepartmentSelect.setDepartmentPath(sysDepartment.getDepartmentPath());
        // 前端给的type=1则查询当前的部门下所有部门 
        if(StrUtil.equals("1", sysDepartment.getType())) {
            // 部门数据
            List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartmentSelect);
            if(sysDepartmentList != null && sysDepartmentList.size()>0) {
                for(SysDepartment dbSysDepartment:sysDepartmentList){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.set("value", dbSysDepartment.getDepartmentId());
                    jsonObject.set("valuePid", sysDepartment.getDepartmentParentId());
                    jsonObject.set("label", dbSysDepartment.getDepartmentName());
                    jsonObject.set("title", dbSysDepartment.getDepartmentName());
                    jsonObject.set("type", "1");
                    jsonArray.add(jsonObject);
                }        
            }
            
//            // 项目表的部门数据
//            SysProject sysProjectCheck = sysProjectMapper.selectByPrimaryKey(sysDepartment.getDepartmentParentId());
//            if(sysProjectCheck != null) {
//                SysProject sysProjectSelect = new SysProject();
//                sysProjectSelect.setDepartmentParentId(sysDepartment.getDepartmentParentId());
//                sysProjectSelect.setDepartmentPath(sysDepartment.getDepartmentPath());
//                List<SysProject> sysProjectList = sysProjectMapper.selectBySysProjectList(sysProjectSelect);
//                if(sysProjectList != null && sysProjectList.size()>0) {
//                    for(SysProject dbSysProject:sysProjectList){
//                        JSONObject jsonObject = new JSONObject();
//                        jsonObject.set("value", dbSysProject.getDepartmentId());
//                        jsonObject.set("valuePid", sysDepartment.getDepartmentParentId());
//                        jsonObject.set("label", dbSysProject.getDepartmentName());
//                        jsonObject.set("title", dbSysProject.getDepartmentName());
//                        jsonObject.set("type", "1");
//                        jsonArray.add(jsonObject);
//                    }        
//                }
//            }
        }
        // 查找confDown部门表下所有数据
        SysDepartment sysDepartmentKey = sysDepartmentMapper.selectByPrimaryKey(sysDepartment.getDepartmentParentId());
        if(sysDepartmentKey != null && StrUtil.isNotEmpty(sysDepartmentKey.getConfDown())) {
            JSONArray confDownjsonArray =  new JSONArray(sysDepartmentKey.getConfDown());
            if(confDownjsonArray != null && confDownjsonArray.size()>0) {
                for (Iterator<Object> iterator = confDownjsonArray.iterator(); iterator.hasNext();) {
                    Object jsonObject = (Object)iterator.next();
                    SysDepartment dbSysDepartment = sysDepartmentMapper.selectByPrimaryKey(jsonObject.toString());
                    if(dbSysDepartment != null) {
                        JSONObject confDownJSONObject = new JSONObject();
                        confDownJSONObject.set("value", dbSysDepartment.getDepartmentId());
                        confDownJSONObject.set("valuePid", sysDepartment.getDepartmentParentId());
                        confDownJSONObject.set("label", dbSysDepartment.getDepartmentName());
                        confDownJSONObject.set("title", dbSysDepartment.getDepartmentName());
                        confDownJSONObject.set("type", "1");
                        jsonArray.add(confDownJSONObject);
                    }
//                    // 项目
//                    SysProject dbSysProject = sysProjectMapper.selectByPrimaryKey(jsonObject.toString());
//                    if(dbSysProject != null) {
//                        JSONObject confDownJSONObject = new JSONObject();
//                        confDownJSONObject.set("value", dbSysProject.getDepartmentId());
//                        confDownJSONObject.set("valuePid", sysDepartment.getDepartmentParentId());
//                        confDownJSONObject.set("label", dbSysProject.getDepartmentName());
//                        confDownJSONObject.set("title", dbSysProject.getDepartmentName());
//                        confDownJSONObject.set("type", "1");
//                        jsonArray.add(confDownJSONObject);
//                    }
                    
                }
            }
        }
        // 查找confDown项目表下所有数据
        SysProject sysProjectKey = sysProjectMapper.selectByPrimaryKey(sysDepartment.getDepartmentParentId());
        if(sysProjectKey != null && StrUtil.isNotEmpty(sysProjectKey.getConfDown())) {
            JSONArray confDownjsonArray =  new JSONArray(sysProjectKey.getConfDown());
            if(confDownjsonArray != null && confDownjsonArray.size()>0) {
                for (Iterator<Object> iterator = confDownjsonArray.iterator(); iterator.hasNext();) {
                    Object jsonObject = (Object)iterator.next();
                    SysDepartment dbSysDepartment = sysDepartmentMapper.selectByPrimaryKey(jsonObject.toString());
                    if(dbSysDepartment != null) {
                        JSONObject confDownJSONObject = new JSONObject();
                        confDownJSONObject.set("value", dbSysDepartment.getDepartmentId());
                        confDownJSONObject.set("valuePid", sysDepartment.getDepartmentParentId());
                        confDownJSONObject.set("label", dbSysDepartment.getDepartmentName());
                        confDownJSONObject.set("title", dbSysDepartment.getDepartmentName());
                        confDownJSONObject.set("type", "1");
                        jsonArray.add(confDownJSONObject);
                    }
//                    // 项目
//                    SysProject dbSysProject = sysProjectMapper.selectByPrimaryKey(jsonObject.toString());
//                    if(dbSysProject != null) {
//                        JSONObject confDownJSONObject = new JSONObject();
//                        confDownJSONObject.set("value", dbSysProject.getDepartmentId());
//                        confDownJSONObject.set("valuePid", sysDepartment.getDepartmentParentId());
//                        confDownJSONObject.set("label", dbSysProject.getDepartmentName());
//                        confDownJSONObject.set("title", dbSysProject.getDepartmentName());
//                        confDownJSONObject.set("type", "1");
//                        jsonArray.add(confDownJSONObject);
//                    }
                    
                }
            }
        }
        
        // 人员数据
        if(userDataFlag) {
            sysDepartmentSelect = new SysDepartment();
            sysDepartmentSelect.setDepartmentId(sysDepartment.getDepartmentParentId());
            sysDepartmentSelect.setDepartmentPath(sysDepartment.getDepartmentPath());
            sysDepartmentSelect.setAccountAppType("1");
            List<SysDepartment> sysUserList = sysDepartmentMapper.selectSysDepartmentUserListByLike(sysDepartmentSelect);
            if(sysUserList != null && sysUserList.size()>0) {
                for(SysDepartment dbSysDepartment:sysUserList){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.set("value", dbSysDepartment.getUserKey());
                    jsonObject.set("valuePid", sysDepartment.getDepartmentParentId());
                    jsonObject.set("label", dbSysDepartment.getRealName());
                    jsonObject.set("type", "2");

                    SysFlowUser sysFlowUser =  new SysFlowUser();
                    sysFlowUser.setValue(dbSysDepartment.getUserKey());
                    sysFlowUser.setFlowRoleId(sysDepartment.getFlowRoleId());
                    sysFlowUser.setTopId(sysDepartment.getTopId());
                    List<SysFlowUser> sysFlowUserList = sysFlowUserMapper.selectBySysFlowUserList(sysFlowUser);
                    if(sysFlowUserList != null && sysFlowUserList.size()>0) {
                        jsonObject.set("checked", true);
                    }
                    jsonArray.add(jsonObject);
                }        
            }

            // 当前departmentId如果存在于project表则获取当前项目的用户数据
            SysProject sysProject = sysProjectMapper.selectByPrimaryKey(sysDepartment.getDepartmentParentId());
            if(sysProject != null) {
                SysUser sysUserSelect = new SysUser();
                sysUserSelect.setDepartmentId(sysDepartment.getDepartmentParentId());
                List<SysUser> sysUserProjectList = sysUserMapper.selectBySysUserListByDepartment(sysUserSelect);
                if(sysUserProjectList != null && sysUserProjectList.size()>0) {
                    for(SysUser dbSysUser:sysUserProjectList){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.set("value", dbSysUser.getUserKey());
                        jsonObject.set("valuePid", sysDepartment.getDepartmentParentId());
                        jsonObject.set("label", dbSysUser.getRealName());
                        jsonObject.set("type", "2");
                        jsonObject.set("checked", true);
                        jsonArray.add(jsonObject);
                    }        
                }
            }
        }
        return jsonArray;
    }
    // ------------- ↑↑↑ 私有方法  ↑↑↑------------

}
