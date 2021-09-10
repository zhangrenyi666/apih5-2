package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.apih5.framework.api.sysdb.entity.SysCompany;
import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.api.sysdb.entity.SysUserDepartment;
import com.apih5.framework.api.sysdb.service.SysDepartmentService;
import com.apih5.framework.api.sysdb.service.UserService;
import com.apih5.framework.api.zjoa.common.CachData;
import com.apih5.framework.api.zjoa.entity.OADepartment;
import com.apih5.framework.api.zjoa.entity.OAMember;
import com.apih5.framework.api.zjoa.entity.ZjAccessCode;
import com.apih5.framework.api.zjoa.service.ZjOaApiService;
import com.apih5.framework.api.zjoa.service.ZjOaDbService;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.ConfigConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.SysCompanyMapper;
import com.apih5.mybatis.dao.SysDepartmentMapper;
import com.apih5.mybatis.dao.SysUserDepartmentMapper;
import com.apih5.mybatis.dao.SysUserMapper;
import com.apih5.mybatis.pojo.BaseAccount;
import com.apih5.service.BaseAccountService;
import com.apih5.service.SysSyncUserInfoService;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.google.common.collect.Lists;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import net.sf.jsqlparser.statement.select.SelectBody;

/**
 * 中交微办公相关数据获取
 * 
 * @return www.apih5.com
 */
@Service("zjOaApiService")
public class ZjOaApiServiceImpl implements ZjOaApiService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
//	@Autowired
//	private Environment env;
	@ApolloConfig(ConfigConst.ZJ)
	private Config zjConfig;
	
	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired
	private SysDepartmentService sysDepartmentService;

	@Autowired
	private SysUserMapper userMapper;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired
	private ZjOaDbService zjOaDbService;
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private SysCompanyMapper sysCompanyMapper;

	@Autowired
	private SysDepartmentMapper sysDepartmentMapper;
	@Autowired
	private UserService userService;
	
	@Autowired
	private SysSyncUserInfoService sysSyncUserInfoService;
	
    @Autowired
    private Apih5Properties apih5Properties;
    
    @Autowired
    private BaseAccountService baseAccountService;
    
    @Autowired
    private SysUserDepartmentMapper sysUserDepartmentMapper;

	/**
	 * 部门获取
	 * 
	 * @param otherCompanyFlag 2是获取其他分公司
	 * @return
	 */
	@Override
	public ResponseEntity apiPostSysDepartmentList(String otherCompanyFlag) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
//		String userKey = TokenUtils.getUserKey(request);
//		String oaUserId = TokenUtils.getUserId(request);
		String companyId = TokenUtils.getCompanyId(request);

		List<OADepartment> listDepartment = new ArrayList<OADepartment>();
		// 1.1、登录者是局的人  
		if(StrUtil.equals("a5d82aM11cf9cf7329M65b09eb8996c077cbd7a49b1f0d7c83d", companyId)){
			// 1.2、登录者是局的人（看其他分公司数据）
			if(StrUtil.equals("2", otherCompanyFlag)) {
				SysDepartment sysDepartmentSelect = new SysDepartment();
				sysDepartmentSelect.setDepartmentPath("a5d82aM11cf9cea644M65b09eb8996c077cbd7a49b1f0d7c83d");
				List<SysDepartment> list = sysDepartmentMapper.selectBySysDepartmentList(sysDepartmentSelect);
				for (int i = 0; i < list.size(); i++) {
					SysDepartment sysDepartment = list.get(i);
					OADepartment department = new OADepartment();
					department.setId(sysDepartment.getDepartmentId());
					department.setParentid(sysDepartment.getDepartmentParentId());
					department.setName(sysDepartment.getDepartmentName());
					department.setOrgId(sysDepartment.getDepartmentId());
					department.setOrder(i);
					listDepartment.add(department);
				}
			} else {
				// 1.1、登录者是局的人（只看局数据）
				SysDepartment sysDepartmentSelect = new SysDepartment();
				sysDepartmentSelect.setDepartmentPath("a5d82aM11cf9cf7329M65b09eb8996c077cbd7a49b1f0d7c83d");
				List<SysDepartment> list = sysDepartmentMapper.selectBySysDepartmentList(sysDepartmentSelect);
				for (int i = 0; i < list.size(); i++) {
					SysDepartment sysDepartment = list.get(i);
					OADepartment department = new OADepartment();
					department.setId(sysDepartment.getDepartmentId());
					department.setParentid(sysDepartment.getDepartmentParentId());
					department.setName(sysDepartment.getDepartmentName());
					department.setOrgId(sysDepartment.getDepartmentId());
					department.setOrder(i);
					listDepartment.add(department);
				}
			
			}
		} else {
			if(StrUtil.equals("2", otherCompanyFlag)) {
				// 2.2、登录者是分公司（看局公司数据）
				SysDepartment sysDepartmentSelect = new SysDepartment();
				sysDepartmentSelect.setDepartmentPath(companyId);
				List<SysDepartment> list = sysDepartmentMapper.selectBySysDepartmentList(sysDepartmentSelect);
				for (int i = 0; i < list.size(); i++) {
					SysDepartment sysDepartment = list.get(i);
					OADepartment department = new OADepartment();
					department.setId(sysDepartment.getDepartmentId());
					department.setParentid(sysDepartment.getDepartmentParentId());
					department.setName(sysDepartment.getDepartmentName());
					department.setOrgId(sysDepartment.getDepartmentId());
					department.setOrder(i);
					listDepartment.add(department);
				}
				
				sysDepartmentSelect = new SysDepartment();
				sysDepartmentSelect.setDepartmentPath("a5d82aM11cf9cf7329M65b09eb8996c077cbd7a49b1f0d7c83d");
				list = sysDepartmentMapper.selectBySysDepartmentList(sysDepartmentSelect);
				for (int i = 0; i < list.size(); i++) {
					SysDepartment sysDepartment = list.get(i);
					OADepartment department = new OADepartment();
					department.setId(sysDepartment.getDepartmentId());
					department.setParentid(sysDepartment.getDepartmentParentId());
					department.setName(sysDepartment.getDepartmentName());
					department.setOrgId(sysDepartment.getDepartmentId());
					department.setOrder(i);
					listDepartment.add(department);
				}
			} else {
				// 2.1、登录者是分公司（只看自己数据）
				SysDepartment sysDepartmentSelect = new SysDepartment();
				sysDepartmentSelect.setDepartmentPath(companyId);
				List<SysDepartment> list = sysDepartmentMapper.selectBySysDepartmentList(sysDepartmentSelect);
				for (int i = 0; i < list.size(); i++) {
					SysDepartment sysDepartment = list.get(i);
					OADepartment department = new OADepartment();
					department.setId(sysDepartment.getDepartmentId());
					department.setParentid(sysDepartment.getDepartmentParentId());
					department.setName(sysDepartment.getDepartmentName());
					department.setOrgId(sysDepartment.getDepartmentId());
					department.setOrder(i);
					listDepartment.add(department);
				}
			} 
		}
		
		return repEntity.okList(listDepartment, listDepartment.size());
	}
	
	/**
	 * 根据登录者获取中交系统的部门列表
	 * 
	 * @return 部门列表
	 */
	@Override
	public ResponseEntity getDepartmentList() {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String oaUserId = TokenUtils.getUserId(request);
		String companyUrl = TokenUtils.getCompanyUrl(request);
		if (!Apih5Properties.isUseEhCache()) {
			SysUser dbSysUser = userMapper.selectByPrimaryKey(userKey);
			oaUserId = dbSysUser.getUserId();
		}

		// 1、根据userId获取oa系统中的分公司IP分发地址
//		String companyUrl = zjOaDbService.getZjWeChatOaCompanyIpAddressByUserId(oaUserId);
//		logger.debug("getDepartmentList" + companyUrl);
//		if (StrUtil.isNotEmpty(oaUserId) && oaUserId.indexOf("tz_") >= 0) {
//			companyUrl = "http://tz-oa.fheb.cn:8080";
//		} else if (StrUtil.isNotEmpty(oaUserId) && oaUserId.indexOf("haiwei_") >= 0) {
//			companyUrl = "http://haiwei-oa.fheb.cn:8083";
//		}

		// 2、把IP分发地址存入相应的userId缓存中，防止每次从接口获取
		CachData.setInterfaseUrl(oaUserId, companyUrl);
		CachData.oaInterfaceUserIdMap.put(oaUserId, oaUserId);

		// 3、获取OA系统安全认证的token
		CachData.getAcceeToken(oaUserId);
		ZjAccessCode accessCodeBean = (ZjAccessCode) CachData.accessMap.get(oaUserId);

		// 4、获取OA系统部门信息
		String url = CachData.oaInterfaceMap.get(oaUserId).getGET_ORG_BY_PARENT().replace("METHOD", "getOrgByParent")
				.replace("USERID", CachData.oaInterfaceUserIdMap.get(oaUserId))
				.replace("ACCESSCODE", accessCodeBean.getAccessCode()).replace("PARENT_BSID", "00001");
		String jsonStr = HttpUtil.sendGet(url);

		List<OADepartment> listDepartment = new ArrayList<OADepartment>();
		JSONObject json = new JSONObject(jsonStr);
		if (json.get("data") != null && !json.get("data").equals("")) {
			JSONArray dataArray = (JSONArray) json.get("data");
			// List list = (List) JSONArray.toCollection(dataArray, Map.class);
			List list = (List) dataArray;
			for (int i = 0; i < list.size(); i++) {
				Map accessMap = (Map) list.get(i);
				OADepartment department = new OADepartment();
				department.setId(accessMap.get("BSID").toString());
				department.setParentid(accessMap.get("parentBSID").toString());
				department.setName(accessMap.get("orgName").toString());
				department.setOrgId(accessMap.get("orgID").toString());
				department.setOrder(i);
				listDepartment.add(department);
			}
		}
		return repEntity.okList(listDepartment, listDepartment.size());
	}

	/**
	 * 根据登录者获取中交系统的部门列表
	 * 
	 * @return 部门列表
	 */
	@Override
	public ResponseEntity getPostDepartmentList() {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String oaUserId = TokenUtils.getUserId(request);
		List<OADepartment> listDepartment = new ArrayList<OADepartment>();

		String companyId = sysDepartmentService.getCompanyIdByUserKey(userKey);
		// 局身份进来时候
		if(StrUtil.equals("a5d82aM11cf9cf7329M65b09eb8996c077cbd7a49b1f0d7c83d", companyId)){
			String companyUrl = "http://oa.fheb.cn:8080";
			// 2、把IP分发地址存入相应的userId缓存中，防止每次从接口获取
			CachData.setInterfaseUrl(oaUserId, companyUrl);
			CachData.oaInterfaceUserIdMap.put(oaUserId, oaUserId);
			
			// 3、获取OA系统安全认证的token
			CachData.getAcceeToken(oaUserId);
			ZjAccessCode accessCodeBean = (ZjAccessCode) CachData.accessMap.get(oaUserId);
			
			// 4、获取OA系统部门信息
			String url = CachData.oaInterfaceMap.get(oaUserId).getGET_ORG_BY_PARENT().replace("METHOD", "getOrgByParent")
					.replace("USERID", CachData.oaInterfaceUserIdMap.get(oaUserId))
					.replace("ACCESSCODE", accessCodeBean.getAccessCode()).replace("PARENT_BSID", "00001");
			String jsonStr = HttpUtil.sendGet(url);
			
			JSONObject json = new JSONObject(jsonStr);
			if (json.get("data") != null && !json.get("data").equals("")) {
				JSONArray dataArray = (JSONArray) json.get("data");
				// List list = (List) JSONArray.toCollection(dataArray, Map.class);
				List list = (List) dataArray;
				for (int i = 0; i < list.size(); i++) {
					Map accessMap = (Map) list.get(i);
					OADepartment department = new OADepartment();
					department.setId(accessMap.get("BSID").toString());
					department.setParentid(accessMap.get("parentBSID").toString());
					department.setName(accessMap.get("orgName").toString());
					department.setOrgId(accessMap.get("orgID").toString());
					department.setOrder(i);
					listDepartment.add(department);
				}
			}
		}
		else {
			oaUserId = "admin";
			
//			sysDepartmentService
			SysCompany sysCompany = sysCompanyMapper.selectByPrimaryKey(companyId); 
			String parentBsid = sysCompany.getCompanyRemarks();
			
			// 1、根据userId获取oa系统中的分公司IP分发地址
			String companyUrl = "http://oa.fheb.cn:8080";
			
			// 2、把IP分发地址存入相应的userId缓存中，防止每次从接口获取
			CachData.setInterfaseUrl(oaUserId, companyUrl);
			CachData.oaInterfaceUserIdMap.put(oaUserId, oaUserId);
			
			// 3、获取OA系统安全认证的token
			CachData.getAcceeToken(oaUserId);
			ZjAccessCode  accessCodeBean = (ZjAccessCode) CachData.accessMap.get(oaUserId);
			
			// 4、获取OA系统部门信息
			String url = CachData.oaInterfaceMap.get(oaUserId).getGET_ORG_BY_PARENT().replace("METHOD", "getOrgByParent")
					.replace("USERID", CachData.oaInterfaceUserIdMap.get(oaUserId))
					.replace("ACCESSCODE", accessCodeBean.getAccessCode()).replace("PARENT_BSID", parentBsid);
			String jsonStr = HttpUtil.sendGet(url);
			
			OADepartment department1 = new OADepartment();
			department1.setId("00001");
			department1.setParentid("");
			department1.setName("中交第一公路工程局有限公司");
			department1.setOrgId("a5d82aM11cf9cea644M65b09eb8996c077cbd7a49b1f0d7c83d");
			department1.setOrder(0);
			listDepartment.add(department1);
			
			JSONObject json = new JSONObject(jsonStr);
			if (json.get("data") != null && !json.get("data").equals("")) {
				JSONArray dataArray = (JSONArray) json.get("data");
				// List list = (List) JSONArray.toCollection(dataArray, Map.class);
				List list = (List) dataArray;
				for (int i = 0; i < list.size(); i++) {
					Map accessMap = (Map) list.get(i);
					OADepartment department = new OADepartment();
					department.setId(accessMap.get("BSID").toString());
					department.setParentid(accessMap.get("parentBSID").toString());
					department.setName(accessMap.get("orgName").toString());
					department.setOrgId(accessMap.get("orgID").toString());
					department.setOrder(i);
					listDepartment.add(department);
				}
			}
			
			parentBsid = "00001-00001";
			companyUrl = "http://oa.fheb.cn:8080";
			// 2、把IP分发地址存入相应的userId缓存中，防止每次从接口获取
			CachData.setInterfaseUrl(oaUserId, companyUrl);
			CachData.oaInterfaceUserIdMap.put(oaUserId, oaUserId);
			
			// 3、获取OA系统安全认证的token
			CachData.getAcceeToken(oaUserId);
			 accessCodeBean = (ZjAccessCode) CachData.accessMap.get(oaUserId);
			
			// 4、获取OA系统部门信息
			 url = CachData.oaInterfaceMap.get(oaUserId).getGET_ORG_BY_PARENT().replace("METHOD", "getOrgByParent")
					.replace("USERID", CachData.oaInterfaceUserIdMap.get(oaUserId))
					.replace("ACCESSCODE", accessCodeBean.getAccessCode()).replace("PARENT_BSID", parentBsid);
			 jsonStr = HttpUtil.sendGet(url);
			
			 json = new JSONObject(jsonStr);
			if (json.get("data") != null && !json.get("data").equals("")) {
				JSONArray dataArray = (JSONArray) json.get("data");
				// List list = (List) JSONArray.toCollection(dataArray, Map.class);
				List list = (List) dataArray;
				for (int i = 0; i < list.size(); i++) {
					Map accessMap = (Map) list.get(i);
					OADepartment department = new OADepartment();
					department.setId(accessMap.get("BSID").toString());
					department.setParentid(accessMap.get("parentBSID").toString());
					department.setName(accessMap.get("orgName").toString());
					department.setOrgId(accessMap.get("orgID").toString());
					department.setOrder(i);
					listDepartment.add(department);
				}
			}
		}
		
		OADepartment department = new OADepartment();
		department.setId("00001-99999");
		department.setParentid("00001");
		department.setName("方案评审专家组");
		department.setOrgId("00001-99999");
		department.setOrder(1);
		listDepartment.add(department);
		
		return repEntity.okList(listDepartment, listDepartment.size());
	}
	
	/**
	 * 根据登录者获取中交系统的用户列表
	 * 
	 * @return 用户列表
	 */
	@Override
	public ResponseEntity tempMemberListXiamen(OADepartment oaDepartment) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String oaUserId = TokenUtils.getUserId(request);
		oaUserId ="xiamengs_admin";
		String orgId = oaDepartment.getOrgId();
		// .getTokenUtils.getDepartmentId(request);
		if (!Apih5Properties.isUseEhCache()) {
			SysUser dbSysUser = userMapper.selectByPrimaryKey(userKey);
			oaUserId = dbSysUser.getUserId();
			// SysDepartment dbSysDepartment =
			// sysDepartmentService.getSysDepartmentByUserkey(dbSysUser.getUserKey());
			// orgId = dbSysDepartment.getDepartmentId();
		}

		logger.debug("getMemberListByDepartment 开始....");
		logger.debug("getMemberListByDepartment oaUserId...." + oaUserId);
		// 1、根据userId获取oa系统中的分公司IP分发地址
		String companyUrl = "http://xm-oa.fheb.cn:8081";

		// 2、把IP分发地址存入相应的userId缓存中，防止每次从接口获取
		CachData.setInterfaseUrl(oaUserId, companyUrl);
		CachData.oaInterfaceUserIdMap.put(oaUserId, oaUserId);

		// 3、获取OA系统安全认证的token
		CachData.getAcceeToken(oaUserId);
		ZjAccessCode accessCodeBean = (ZjAccessCode) CachData.accessMap.get(oaUserId);

		// 4、获取OA系统人员信息
		String url = CachData.oaInterfaceMap.get(oaUserId).getGET_USER_BY_ORG().replace("METHOD", "getUserByOrg")
				.replace("USERID", CachData.oaInterfaceUserIdMap.get(oaUserId))
				.replace("ACCESSCODE", accessCodeBean.getAccessCode()).replace("ORG_ID", orgId);
		String jsonStr = HttpUtil.sendGet(url);
		List<OAMember> listMember = new ArrayList<OAMember>();
		JSONObject json = new JSONObject(jsonStr);// .fromObject(jsonStr);
		if (json.get("data") != null && !json.get("data").equals("")) {
			JSONArray dataArray = (JSONArray) json.get("data");
			List list = (List) dataArray;// JSONArray.toCollection(dataArray,
											// Map.class);
			for (int i = 0; i < list.size(); i++) {
				Map accessMap = (Map) list.get(i);
				OAMember member = new OAMember();
				member.setUserid(accessMap.get("userAccount").toString());
				member.setName(accessMap.get("userName").toString());
				member.setOrgId(accessMap.get("orgID").toString());
				listMember.add(member);
			}
		}
		return repEntity.okList(listMember, listMember.size());
	}

	
	/**
	 * 根据登录者获取中交系统的部门列表
	 * 
	 * @return 部门列表
	 */
	@Override
	public ResponseEntity tempPostDepartmentXiamen() {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String oaUserId = TokenUtils.getUserId(request);
		oaUserId="xiamengs_admin";
		if (!Apih5Properties.isUseEhCache()) {
			SysUser dbSysUser = userMapper.selectByPrimaryKey(userKey);
			oaUserId = dbSysUser.getUserId();
		}

		// 1、根据userId获取oa系统中的分公司IP分发地址
		String companyUrl = zjOaDbService.getZjWeChatOaCompanyIpAddressByUserId(oaUserId);
		logger.debug("getDepartmentList" + companyUrl);
		companyUrl = "http://xm-oa.fheb.cn:8081";

		// 2、把IP分发地址存入相应的userId缓存中，防止每次从接口获取
		CachData.setInterfaseUrl(oaUserId, companyUrl);
		CachData.oaInterfaceUserIdMap.put(oaUserId, oaUserId);

		// 3、获取OA系统安全认证的token
		CachData.getAcceeToken(oaUserId);
		ZjAccessCode accessCodeBean = (ZjAccessCode) CachData.accessMap.get(oaUserId);

		// 4、获取OA系统部门信息
		String url = CachData.oaInterfaceMap.get(oaUserId).getGET_ORG_BY_PARENT().replace("METHOD", "getOrgByParent")
				.replace("USERID", CachData.oaInterfaceUserIdMap.get(oaUserId))
				.replace("ACCESSCODE", accessCodeBean.getAccessCode()).replace("PARENT_BSID", "00001");
		String jsonStr = HttpUtil.sendGet(url);

		List<OADepartment> listDepartment = new ArrayList<OADepartment>();
		JSONObject json = new JSONObject(jsonStr);
		if (json.get("data") != null && !json.get("data").equals("")) {
			JSONArray dataArray = (JSONArray) json.get("data");
			// List list = (List) JSONArray.toCollection(dataArray, Map.class);
			List list = (List) dataArray;
			for (int i = 0; i < list.size(); i++) {
				Map accessMap = (Map) list.get(i);
				OADepartment department = new OADepartment();
				department.setId(accessMap.get("BSID").toString());
				department.setParentid(accessMap.get("parentBSID").toString());
				department.setName(accessMap.get("orgName").toString());
				department.setOrgId(accessMap.get("orgID").toString());
				department.setOrder(i);
				listDepartment.add(department);
			}
		}
		return repEntity.okList(listDepartment, listDepartment.size());
	}

	/**
	 * 根据登录者获取中交系统的部门列表
	 * (1：全部部门
	 * 2：登录者相关部门）
	 * 
	 * @return 部门列表
	 */
	@Override
	public ResponseEntity getPostDepartmentList2() {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String oaUserId = TokenUtils.getUserId(request);
		List<OADepartment> listDepartment = new ArrayList<OADepartment>();
		List<SysDepartment> list = sysDepartmentMapper.selectBySysDepartmentList(null);
		for (int i = 0; i < list.size(); i++) {
			SysDepartment sysDepartment = list.get(i);
			OADepartment department = new OADepartment();
			department.setId(sysDepartment.getDepartmentId());
			department.setParentid(sysDepartment.getDepartmentParentId());
			department.setName(sysDepartment.getDepartmentName());
			department.setOrgId(sysDepartment.getDepartmentId());
			department.setOrder(i);
			listDepartment.add(department);
		}
		return repEntity.okList(listDepartment, listDepartment.size());
	}
	
	
	/**
	 * 根据登录者获取中交系统的用户列表
	 * 
	 * @return 用户列表
	 */
	@Override
	public ResponseEntity getMemberList(OADepartment oaDepartment) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String oaUserId = TokenUtils.getUserId(request);
		String orgId = oaDepartment.getOrgId();
		String companyUrl = TokenUtils.getCompanyUrl(request);
		// .getTokenUtils.getDepartmentId(request);
		if (!Apih5Properties.isUseEhCache()) {
			SysUser dbSysUser = userMapper.selectByPrimaryKey(userKey);
			oaUserId = dbSysUser.getUserId();
			// SysDepartment dbSysDepartment =
			// sysDepartmentService.getSysDepartmentByUserkey(dbSysUser.getUserKey());
			// orgId = dbSysDepartment.getDepartmentId();
		}

		logger.debug("getMemberListByDepartment 开始....");
		logger.debug("getMemberListByDepartment oaUserId...." + oaUserId);
		// 1、根据userId获取oa系统中的分公司IP分发地址
//		String companyUrl = zjOaDbService.getZjWeChatOaCompanyIpAddressByUserId(oaUserId);
//		logger.debug("getMemberListByDepartment companyUrl...." + companyUrl);
//		if (StrUtil.isNotEmpty(oaUserId) && oaUserId.indexOf("tz_") >= 0) {
//			companyUrl = "http://tz-oa.fheb.cn:8080";
//		} else if (StrUtil.isNotEmpty(oaUserId) && oaUserId.indexOf("haiwei_") >= 0) {
//			companyUrl = "http://haiwei-oa.fheb.cn:8083";
//		}

		// 2、把IP分发地址存入相应的userId缓存中，防止每次从接口获取
		CachData.setInterfaseUrl(oaUserId, companyUrl);
		CachData.oaInterfaceUserIdMap.put(oaUserId, oaUserId);

		// 3、获取OA系统安全认证的token
		CachData.getAcceeToken(oaUserId);
		ZjAccessCode accessCodeBean = (ZjAccessCode) CachData.accessMap.get(oaUserId);

		// 4、获取OA系统人员信息
		String url = CachData.oaInterfaceMap.get(oaUserId).getGET_USER_BY_ORG().replace("METHOD", "getUserByOrg")
				.replace("USERID", CachData.oaInterfaceUserIdMap.get(oaUserId))
				.replace("ACCESSCODE", accessCodeBean.getAccessCode()).replace("ORG_ID", orgId);
		String jsonStr = HttpUtil.sendGet(url);
		List<OAMember> listMember = new ArrayList<OAMember>();
		JSONObject json = new JSONObject(jsonStr);// .fromObject(jsonStr);
		if (json.get("data") != null && !json.get("data").equals("")) {
			JSONArray dataArray = (JSONArray) json.get("data");
			List list = (List) dataArray;// JSONArray.toCollection(dataArray,
											// Map.class);
			for (int i = 0; i < list.size(); i++) {
				Map accessMap = (Map) list.get(i);
				OAMember member = new OAMember();
				member.setUserid(accessMap.get("userAccount").toString());
				member.setName(accessMap.get("userName").toString());
				member.setOrgId(accessMap.get("orgID").toString());
				listMember.add(member);
			}
		}
		return repEntity.okList(listMember, listMember.size());
	}
	
	/**
	 * 根据登录者获取中交系统的用户列表
	 * 
	 * @return 用户列表
	 */
	@Override
	public ResponseEntity getPostMemberList(OADepartment oaDepartment) {
		String orgId = oaDepartment.getOrgId();
		List<OAMember> listMember = new ArrayList<OAMember>();
		if(StrUtil.equals("00001-99999", orgId)) {
			String zhuanjia = zjConfig.getProperty("temp.zhuanjia", "");//env.getProperty("temp.zhuanjia");
			String[] zhuanJias = zhuanjia.split(",");
			for(int i=0;i<zhuanJias.length;i++) {
				SysUser sysUserSelect = new SysUser();
				sysUserSelect.setMobile(zhuanJias[i]);
				sysUserSelect.setAccountAppType("1");
				List<SysUser> sysUserList = userMapper.selectBySysUserList(sysUserSelect);
				if(sysUserList != null && sysUserList.size() > 0){
					SysUser sysUser = sysUserList.get(0);
					OAMember member = new OAMember();
					member.setUserid(sysUser.getUserKey());
					member.setName(sysUser.getRealName());
					member.setOrgId("00001-99999");
					listMember.add(member);
				}
			}
		}else {
			SysUser sysUserSelect = new SysUser();
			sysUserSelect.setDepartmentId(orgId);
			sysUserSelect.setAccountAppType("1");
			List<SysUser> sysUserList = userMapper.selectBySysUserListByDepartment(sysUserSelect);
			if(sysUserList != null && sysUserList.size() > 0){
				for (SysUser sysUser:sysUserList) {
					OAMember member = new OAMember();
					member.setUserid(sysUser.getUserKey());
					member.setName(sysUser.getRealName());
					member.setOrgId(sysUser.getDepartmentId());
					listMember.add(member);
				}
			}
		}
		return repEntity.okList(listMember, listMember.size());
	}

	/**
	 * 根据登录者获取改公司整体部门+人员
	 * 
	 * @return 部门+用户列表
	 */
	@Override
	public ResponseEntity getDepartmentMemberList() {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String oaUserId = TokenUtils.getUserId(request);
		String companyUrl = TokenUtils.getCompanyUrl(request);
		if (!Apih5Properties.isUseEhCache()) {
			SysUser dbSysUser = userMapper.selectByPrimaryKey(userKey);
			oaUserId = dbSysUser.getUserId();
		}

		// 1、根据userId获取oa系统中的分公司IP分发地址
//		String companyUrl = zjOaDbService.getZjWeChatOaCompanyIpAddressByUserId(oaUserId);
//		logger.debug("getDepartmentList" + companyUrl);
//		if (StrUtil.isNotEmpty(oaUserId) && oaUserId.indexOf("tz_") >= 0) {
//			companyUrl = "http://tz-oa.fheb.cn:8080";
//		} else if (StrUtil.isNotEmpty(oaUserId) && oaUserId.indexOf("haiwei_") >= 0) {
//			companyUrl = "http://haiwei-oa.fheb.cn:8083";
//		}

		// 2、把IP分发地址存入相应的userId缓存中，防止每次从接口获取
		CachData.setInterfaseUrl(oaUserId, companyUrl);
		CachData.oaInterfaceUserIdMap.put(oaUserId, oaUserId);

		// 3、获取OA系统安全认证的token
		CachData.getAcceeToken(oaUserId);
		ZjAccessCode accessCodeBean = (ZjAccessCode) CachData.accessMap.get(oaUserId);
		String bsid = "00001";
		if (companyUrl.indexOf("oa.fheb.cn") > 0) {
			bsid = "00001-00001";
		}
		// 4、获取OA系统部门信息
		String url = CachData.oaInterfaceMap.get(oaUserId).getGET_ORG_BY_PARENT().replace("METHOD", "getOrgByParent")
				.replace("USERID", CachData.oaInterfaceUserIdMap.get(oaUserId))
				.replace("ACCESSCODE", accessCodeBean.getAccessCode()).replace("PARENT_BSID", bsid);
		String jsonStr = HttpUtil.sendGet(url);

		List<OADepartment> listDepartment = new ArrayList<OADepartment>();
		JSONObject json = new JSONObject(jsonStr);
		if (json.get("data") != null && !json.get("data").equals("")) {
			JSONArray dataArray = (JSONArray) json.get("data");
			// List list = (List) JSONArray.toCollection(dataArray, Map.class);
			List list = (List) dataArray;
			for (int i = 0; i < list.size(); i++) {
				Map accessMap = (Map) list.get(i);
				OADepartment department = new OADepartment();
				department.setId(accessMap.get("BSID").toString());
				department.setParentid(accessMap.get("parentBSID").toString());
				department.setName(accessMap.get("orgName").toString());
				department.setOrgId(accessMap.get("orgID").toString());
				department.setOrder(i);

				// 人员列表
				// 4、获取OA系统人员信息
				String urlMem = CachData.oaInterfaceMap.get(oaUserId).getGET_USER_BY_ORG()
						.replace("METHOD", "getUserByOrg")
						.replace("USERID", CachData.oaInterfaceUserIdMap.get(oaUserId))
						.replace("ACCESSCODE", accessCodeBean.getAccessCode()).replace("ORG_ID", department.getOrgId());
				String jsonStrMem = HttpUtil.sendGet(urlMem);
				List<OAMember> listMember = new ArrayList<OAMember>();
				JSONObject jsonMem = new JSONObject(jsonStrMem);// .fromObject(jsonStr);
				if (jsonMem.get("data") != null && !jsonMem.get("data").equals("")) {
					JSONArray dataArrayMem = (JSONArray) jsonMem.get("data");
					List listMem = (List) dataArrayMem;// JSONArray.toCollection(dataArray,
														// Map.class);
					for (int j = 0; j < listMem.size(); j++) {
						Map map = (Map) listMem.get(j);
						OAMember member = new OAMember();
						member.setUserid(map.get("userAccount").toString());
						member.setName(map.get("userName").toString());
						member.setOrgId(map.get("orgID").toString());
						listMember.add(member);
					}
				}
				department.setOaMemberList(listMember);

				listDepartment.add(department);
			}
		}
		return repEntity.okList(listDepartment, listDepartment.size());
	}

	/**
	 * 根据系统用户组获取用户信息
	 * 
	 * @param oaUserId
	 * @return
	 */
	@Override
	public ResponseEntity getSysGroupList() {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String oaUserId = TokenUtils.getUserId(request);
		String companyUrl = TokenUtils.getCompanyUrl(request);
		// String orgId = TokenUtils.getDepartmentId(request);
		if (!Apih5Properties.isUseEhCache()) {
			SysUser dbSysUser = userMapper.selectByPrimaryKey(userKey);
			oaUserId = dbSysUser.getUserId();
			// SysDepartment dbSysDepartment =
			// sysDepartmentService.getSysDepartmentByUserkey(dbSysUser.getUserKey());
			// orgId = dbSysDepartment.getDepartmentId();
		}

		// 1、根据userId获取oa系统中的分公司IP分发地址
//		String companyUrl = zjOaDbService.getZjWeChatOaCompanyIpAddressByUserId(oaUserId);
//		logger.debug("getDepartmentList" + companyUrl);
//		if (StrUtil.isNotEmpty(oaUserId) && oaUserId.indexOf("tz_") >= 0) {
//			companyUrl = "http://tz-oa.fheb.cn:8080";
//		} else if (StrUtil.isNotEmpty(oaUserId) && oaUserId.indexOf("haiwei_") >= 0) {
//			companyUrl = "http://haiwei-oa.fheb.cn:8083";
//		}
		// 2、把IP分发地址存入相应的userId缓存中，防止每次从接口获取
		CachData.setInterfaseUrl(oaUserId, companyUrl);
		CachData.oaInterfaceUserIdMap.put(oaUserId, oaUserId);
		logger.debug("sysGroup --" + oaUserId + "   " + companyUrl);

		// 3、获取OA系统安全认证的token
		CachData.getAcceeToken(oaUserId);
		ZjAccessCode accessCodeBean = (ZjAccessCode) CachData.accessMap.get(oaUserId);

		// 4、获取OA系统部门信息
		String url = CachData.oaInterfaceMap.get(oaUserId).getGET_SYS_GROUP().replace("METHOD", "getSystemGroup")
				.replace("USERID", CachData.oaInterfaceUserIdMap.get(oaUserId))
				.replace("ACCESSCODE", accessCodeBean.getAccessCode());
		String jsonStr = HttpUtil.sendGet(url);

		List<OADepartment> listDepartment = Lists.newArrayList();// new
																	// ArrayList<Department>();
		JSONObject json = new JSONObject(jsonStr);// JSONObject.fromObject(jsonStr);
		if (json.get("data") != null && !json.get("data").equals("")) {
			JSONArray dataArray = (JSONArray) json.get("data");
			List list = (List) dataArray;// JSONArray.toCollection(dataArray,
											// Map.class);
			for (int i = 0; i < list.size(); i++) {
				Map accessMap = (Map) list.get(i);
				OADepartment department = new OADepartment();
				department.setName(accessMap.get("roleName").toString());
				department.setOrgId(accessMap.get("roleID").toString());
				listDepartment.add(department);
			}
		}
		// return listDepartment;
		return repEntity.okList(listDepartment, listDepartment.size());
	}

	/**
	 * 根据用户组获取用户信息数据
	 * 
	 * @param oaUserId
	 * @return
	 */
	@Override
	public ResponseEntity getSysGroupMemberList(OADepartment oaDepartment) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		// String userKey = TokenUtils.getUserKey(request);
		String oaUserId = TokenUtils.getUserId(request);
		String companyUrl = TokenUtils.getCompanyUrl(request);
		// String orgId = TokenUtils.getDepartmentId(request);
		// if(!Apih5Properties.isUseEhCache()){
		// SysUser dbSysUser = userMapper.selectByPrimaryKey(userKey);
		// oaUserId = dbSysUser.getUserId();
		//// List<SysDepartment> sysDepartmentList =
		// sysDepartmentService.getSysDepartmentByUserkey(dbSysUser.getUserKey());
		//// orgId = sysDepartmentList.get(0).getDepartmentId();
		// }
		// String orgId="";
		// 1、根据userId获取oa系统中的分公司IP分发地址
//		String companyUrl = zjOaDbService.getZjWeChatOaCompanyIpAddressByUserId(oaUserId);
//		logger.debug("getDepartmentList" + companyUrl);
//		if (StrUtil.isNotEmpty(oaUserId) && oaUserId.indexOf("tz_") >= 0) {
//			companyUrl = "http://tz-oa.fheb.cn:8080";
//		} else if (StrUtil.isNotEmpty(oaUserId) && oaUserId.indexOf("haiwei_") >= 0) {
//			companyUrl = "http://haiwei-oa.fheb.cn:8083";
//		}

		// 2、把IP分发地址存入相应的userId缓存中，防止每次从接口获取
		CachData.setInterfaseUrl(oaUserId, companyUrl);
		CachData.oaInterfaceUserIdMap.put(oaUserId, oaUserId);

		// 3、获取OA系统安全认证的token
		CachData.getAcceeToken(oaUserId);
		ZjAccessCode accessCodeBean = (ZjAccessCode) CachData.accessMap.get(oaUserId);

		// 4、获取OA系统部门信息
		String url = CachData.oaInterfaceMap.get(oaUserId).getGET_SYS_USER_BY_GROUP()
				.replace("METHOD", "getUserByGroup").replace("USERID", CachData.oaInterfaceUserIdMap.get(oaUserId))
				.replace("ACCESSCODE", accessCodeBean.getAccessCode()).replace("ROLE_ID", oaDepartment.getOrgId());
		String jsonStr = HttpUtil.sendGet(url);

		List<OAMember> listMember = new ArrayList<OAMember>();
		JSONObject json = new JSONObject(jsonStr);// JSONObject.fromObject(jsonStr);
		if (json.get("data") != null && !json.get("data").equals("")) {
			JSONArray dataArray = (JSONArray) json.get("data");
			List list = (List) dataArray;// JSONArray.toCollection(dataArray,
											// Map.class);
			for (int i = 0; i < list.size(); i++) {
				Map accessMap = (Map) list.get(i);
				OAMember member = new OAMember();
				member.setUserid(accessMap.get("userAccount").toString());
				member.setName(accessMap.get("userName").toString());
				member.setOrgId(accessMap.get("orgID").toString());
				listMember.add(member);
			}
		}
		// return listMember;
		return repEntity.okList(listMember, listMember.size());
	}

	@Override
	public JSONObject syncZjQfToZxDepartmentByAdd(SysDepartment sysDepartment) {
		JSONObject returnJSONObject = new JSONObject();
		if (StrUtil.isEmpty(sysDepartment.getDepartmentId())
				|| StrUtil.isEmpty(sysDepartment.getDepartmentName())
				|| StrUtil.isEmpty(sysDepartment.getDepartmentParentId())) {
			LoggerUtils.printLogger(logger, "syncZjQfToZxDepartmentByAdd=部门ID、部门名称、部门父ID中某一个可能为空");
			returnJSONObject.set("success", false);
			returnJSONObject.set("desc", "部门ID、部门名称、部门父ID中某一个可能为空");
			return returnJSONObject;
		}
		// 父ID为0的节点无权限同步！
		if(StrUtil.equals("0", sysDepartment.getDepartmentParentId())
				|| StrUtil.equals("a5d82aM11cf9cea644M65b09eb8996c077cbd7a49b1f0d7c83d", sysDepartment.getDepartmentParentId())) {
			LoggerUtils.printLogger(logger, "syncZjQfToZxDepartmentByAdd=该父为特殊节点，经授权才可以同步，请联系管理员：17060455885！");
			returnJSONObject.set("success", false);
			returnJSONObject.set("desc", "该父为特殊节点，经授权才可以同步，请联系管理员：17060455885！");
			return returnJSONObject;
		}
		// 没有父节点则不能同步
		SysDepartment dbSysDepartmentPid = sysDepartmentMapper.selectByPrimaryKey(sysDepartment.getDepartmentParentId());
		if(dbSysDepartmentPid == null) {
			LoggerUtils.printLogger(logger, "syncZjQfToZxDepartmentByAdd=" + "父ID不存在不能同步！(" + sysDepartment.getDepartmentParentId()+"）");
			returnJSONObject.set("success", false);
			returnJSONObject.set("desc", "父ID不存在不能同步！(" + sysDepartment.getDepartmentParentId()+"）");
			return returnJSONObject;
		}

		ResponseEntity responseEntity = null;
		SysDepartment dbSysDepartment = sysDepartmentMapper.selectByPrimaryKey(sysDepartment.getDepartmentId());
		try {
			if(dbSysDepartment == null) {
				responseEntity = sysDepartmentService.saveSysDepartment(sysDepartment);
			} else {
				responseEntity = sysDepartmentService.updateSysDepartment(sysDepartment);
			}
		} catch (Exception e) {
			LoggerUtils.printLogger(logger, "syncZjQfToZxDepartmentByAdd=部门创建失败!（WeChat）("+e.getMessage()+")");
			returnJSONObject.set("success", false);
			returnJSONObject.set("desc", "部门创建失败!（WeChat）");
			return returnJSONObject;
		}
		
		if(responseEntity.isSuccess()) {
		  	try {
		  	    String companyListStr = "[{\"apiUrl\":\"http://wechat.zjyjhw.com/apihaiwei/\",\"flag\":\"haiwei_\",\"accountId\":\"zj_qyh_woa_id\",\"userId\":\"haiwei_admin\"},{\"apiUrl\":\"http://xm-oa.fheb.cn:88/apixiamengs/\",\"flag\":\"xiamengs_\",\"accountId\":\"zj_qyh_woa_id\",\"userId\":\"xiamengs_admin\"},{\"apiUrl\":\"http://3gs-oa.fheb.cn:8088/apisangs/\",\"flag\":\"sangs_\",\"accountId\":\"zj_qyh_woa_id\",\"userId\":\"sangs_admin\"}]";
		        JSONArray companyList = new JSONArray(companyListStr);
	    		sysSyncUserInfoService.jobsSysSyncUserInfo(companyList);
	    	}catch (Exception e) {
			}
			returnJSONObject.set("success", true);
			returnJSONObject.set("desc", "ok");
			return returnJSONObject;
		} else {
			LoggerUtils.printLogger(logger, "syncZjQfToZxDepartmentByAdd=部门创建失败!("+responseEntity.getMessage()+")");
			returnJSONObject.set("success", false);
			returnJSONObject.set("desc", "部门创建失败!");
			return returnJSONObject;
		}
	}

	@Override
	public JSONObject syncZjQfToZxDepartmentByUpd(SysDepartment sysDepartment) {
		JSONObject returnJSONObject = new JSONObject();
		if (StrUtil.isEmpty(sysDepartment.getDepartmentId())) {
			LoggerUtils.printLogger(logger, "syncZjQfToZxDepartmentByUpd=部门ID不能为空");
			returnJSONObject.set("success", false);
			returnJSONObject.set("desc", "部门ID不能为空");
			return returnJSONObject;
		}
		try {
			SysDepartment dbsysDepartment = sysDepartmentMapper.selectByPrimaryKey(sysDepartment.getDepartmentId());
			if (dbsysDepartment != null && StrUtil.isNotEmpty(dbsysDepartment.getDepartmentId())) {
				// 同一个父节点下不要有重复名称
				SysDepartment sysDepartmentRepeat = new SysDepartment();
				sysDepartmentRepeat.setDepartmentName(dbsysDepartment.getDepartmentName());
				sysDepartmentRepeat.setDepartmentParentId(dbsysDepartment.getDepartmentParentId());
				List<SysDepartment> sysDepartmentRepeatList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartmentRepeat);
				if(sysDepartmentRepeatList != null && sysDepartmentRepeatList.size()>0) {
					String departmentName = sysDepartmentRepeatList.get(0).getDepartmentName();
					if(StrUtil.equals(sysDepartment.getDepartmentName(), departmentName)) {
						returnJSONObject.set("success", true);
						returnJSONObject.set("desc", "ok，名字重复，默认同步成功");
						return returnJSONObject;
					}
				}
			} else {
				// 新增
				return syncZjQfToZxDepartmentByAdd(sysDepartment);
			}
			ResponseEntity responseEntity = sysDepartmentService.updateSysDepartment(sysDepartment);
			if(responseEntity.isSuccess()) {
			  	try {
			  	    String companyListStr = "[{\"apiUrl\":\"http://wechat.zjyjhw.com/apihaiwei/\",\"flag\":\"haiwei_\",\"accountId\":\"zj_qyh_woa_id\",\"userId\":\"haiwei_admin\"},{\"apiUrl\":\"http://xm-oa.fheb.cn:88/apixiamengs/\",\"flag\":\"xiamengs_\",\"accountId\":\"zj_qyh_woa_id\",\"userId\":\"xiamengs_admin\"},{\"apiUrl\":\"http://3gs-oa.fheb.cn:8088/apisangs/\",\"flag\":\"sangs_\",\"accountId\":\"zj_qyh_woa_id\",\"userId\":\"sangs_admin\"}]";
	                JSONArray companyList = new JSONArray(companyListStr);
	                sysSyncUserInfoService.jobsSysSyncUserInfo(companyList);
		    	}catch (Exception e) {
				}
				returnJSONObject.set("success", true);
				returnJSONObject.set("desc", "ok");
				return returnJSONObject;
			} else {
				LoggerUtils.printLogger(logger, "syncZjQfToZxDepartmentByUpd=部门修改失败!("+responseEntity.getMessage()+")");
				returnJSONObject.set("success", false);
				returnJSONObject.set("desc", "部门修改失败!");
				return returnJSONObject;
			}
		} catch (Exception e) {
			LoggerUtils.printLogger(logger, "syncZjQfToZxDepartmentByUpd=部门修改失败!（WeChat）("+e.getMessage()+")");
			returnJSONObject.set("success", false);
			returnJSONObject.set("desc", "部门修改失败!（WeChat）");
			return returnJSONObject;
		}
	}

	@Override
	public JSONObject syncZjQfToZxDepartmentByDel(List<SysDepartment> sysDepartmentList) {
		JSONObject returnJSONObject = new JSONObject();
		if(sysDepartmentList == null || sysDepartmentList.size()==0) {
			LoggerUtils.printLogger(logger, "syncZjQfToZxDepartmentByDel=删除对象不能为空");
			returnJSONObject.set("success", false);
			returnJSONObject.set("desc", "删除对象不能为空");
			return returnJSONObject;
		}
		try {
			SysDepartment sysDepartment = sysDepartmentMapper.selectByPrimaryKey(sysDepartmentList.get(0).getDepartmentId());
			if(sysDepartment == null) {
				returnJSONObject.set("success", true);
				returnJSONObject.set("desc", "无数据!");
				return returnJSONObject;
			}
			ResponseEntity responseEntity = sysDepartmentService.batchDeleteUpdateSysDepartment(sysDepartmentList);
			if(responseEntity.isSuccess()) {
			  	try {
			  	    String companyListStr = "[{\"apiUrl\":\"http://wechat.zjyjhw.com/apihaiwei/\",\"flag\":\"haiwei_\",\"accountId\":\"zj_qyh_woa_id\",\"userId\":\"haiwei_admin\"},{\"apiUrl\":\"http://xm-oa.fheb.cn:88/apixiamengs/\",\"flag\":\"xiamengs_\",\"accountId\":\"zj_qyh_woa_id\",\"userId\":\"xiamengs_admin\"},{\"apiUrl\":\"http://3gs-oa.fheb.cn:8088/apisangs/\",\"flag\":\"sangs_\",\"accountId\":\"zj_qyh_woa_id\",\"userId\":\"sangs_admin\"}]";
	                JSONArray companyList = new JSONArray(companyListStr);
	                sysSyncUserInfoService.jobsSysSyncUserInfo(companyList);
		    	}catch (Exception e) {
				}
				returnJSONObject.set("success", true);
				returnJSONObject.set("desc", "ok");
				return returnJSONObject;
			} else {
				LoggerUtils.printLogger(logger, "syncZjQfToZxDepartmentByDel=部门删除失败!("+responseEntity.getMessage()+")");
				returnJSONObject.set("success", false);
				returnJSONObject.set("desc", "部门删除失败!");
				return returnJSONObject;
			}
		} catch (Exception e) {
			LoggerUtils.printLogger(logger, "syncZjQfToZxDepartmentByDel=部门删除失败!（WeChat）(" + e.getMessage() + ")");
			returnJSONObject.set("success", false);
			returnJSONObject.set("desc", "部门删除失败!（WeChat）(" + e.getMessage() + ")");
			return returnJSONObject;
		}
	}

	@Override
	public JSONObject syncZjQfToZxUserInfoByAdd(SysUser sysUser) {
		JSONObject returnJSONObject = new JSONObject();
		if (StrUtil.isEmpty(sysUser.getUserId())
				|| StrUtil.isEmpty(sysUser.getRealName())
				|| StrUtil.isEmpty(sysUser.getMobile())) {
			LoggerUtils.printLogger(logger, "syncZjQfToZxUserInfoByAdd=用户ID、用户名、电话号中某一个可能为空");
			returnJSONObject.set("success", false);
			returnJSONObject.set("desc", "用户ID、用户名、电话号中某一个可能为空");
			return returnJSONObject;
		}
		try {
			if(sysUser.getSysDepartmentList() == null || sysUser.getSysDepartmentList().size()==0) {
				LoggerUtils.printLogger(logger, "syncZjQfToZxUserInfoByAdd=该人员的部门不存在!1");
				returnJSONObject.set("success", false);
				returnJSONObject.set("desc", "该人员的部门不存在!");
				return returnJSONObject;
			}
			List<Map> sysDepartmentList = sysUser.getSysDepartmentList();
			Map map = sysDepartmentList.get(0);
			SysDepartment sysDepartment = sysDepartmentMapper.selectByPrimaryKey((String)map.get("departmentId"));
			if(sysDepartment == null) {
				LoggerUtils.printLogger(logger, "syncZjQfToZxUserInfoByAdd=该人员的部门不存在!2");
				returnJSONObject.set("success", false);
				returnJSONObject.set("desc", "该人员的部门不存在!");
				return returnJSONObject;
			}
			sysUser.setUserType("1");
			sysUser.setUserStatus("1");
			ResponseEntity responseEntity = userService.addSysUserInfoByBg(sysUser);
			if(responseEntity.isSuccess()) {
				try {
				    String companyListStr = "[{\"apiUrl\":\"http://wechat.zjyjhw.com/apihaiwei/\",\"flag\":\"haiwei_\",\"accountId\":\"zj_qyh_woa_id\",\"userId\":\"haiwei_admin\"},{\"apiUrl\":\"http://xm-oa.fheb.cn:88/apixiamengs/\",\"flag\":\"xiamengs_\",\"accountId\":\"zj_qyh_woa_id\",\"userId\":\"xiamengs_admin\"},{\"apiUrl\":\"http://3gs-oa.fheb.cn:8088/apisangs/\",\"flag\":\"sangs_\",\"accountId\":\"zj_qyh_woa_id\",\"userId\":\"sangs_admin\"}]";
	                JSONArray companyList = new JSONArray(companyListStr);
	                sysSyncUserInfoService.jobsSysSyncUserInfo(companyList);
		    	}catch (Exception e) {
				}
				returnJSONObject.set("success", true);
				returnJSONObject.set("desc", "ok");
				return returnJSONObject;
			} else {
			    // 微信手机号如果存在，则只更新本库，不更新微信后台（60104：手机号码已存在  ）
			    if(responseEntity.getMessage().indexOf("60104")>=0) {
			        sysUser.setUseSyncWeChatPar(false);// 不同步微信
			        responseEntity = userService.addSysUserInfoByBg(sysUser);
			        if(responseEntity.isSuccess()) {
			            try {
		                    String companyListStr = "[{\"apiUrl\":\"http://wechat.zjyjhw.com/apihaiwei/\",\"flag\":\"haiwei_\",\"accountId\":\"zj_qyh_woa_id\",\"userId\":\"haiwei_admin\"},{\"apiUrl\":\"http://xm-oa.fheb.cn:88/apixiamengs/\",\"flag\":\"xiamengs_\",\"accountId\":\"zj_qyh_woa_id\",\"userId\":\"xiamengs_admin\"},{\"apiUrl\":\"http://3gs-oa.fheb.cn:8088/apisangs/\",\"flag\":\"sangs_\",\"accountId\":\"zj_qyh_woa_id\",\"userId\":\"sangs_admin\"}]";
		                    JSONArray companyList = new JSONArray(companyListStr);
		                    sysSyncUserInfoService.jobsSysSyncUserInfo(companyList);
		                }catch (Exception e) {
		                }
		                returnJSONObject.set("success", true);
		                returnJSONObject.set("desc", "ok");
		                return returnJSONObject;
			        }
			    } else if(responseEntity.getMessage().indexOf("电话号已在【")>=0) {
			        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
			        String userKey = TokenUtils.getUserKey(request);
			        String realName = TokenUtils.getRealName(request);
			        String accountId = TokenUtils.getAccountId(request);
			        
			        sysUser.setUserKey(UuidUtil.generate());
			        sysUser.setAccountId(accountId);
//			        String userPwd = SecureUtil.md5(apih5Properties.getDefaultPassword() + apih5Properties.getMd5Salt());
//			        sysUser.setUserPwd(userPwd);

			        JSONArray jsonArrayExt10 = new JSONArray();
			        jsonArrayExt10.add(sysUser.getUserKey());
			        jsonArrayExt10.add((String)map.get("departmentId"));
			        sysUser.setExt10(jsonArrayExt10.toString());
			        sysUser.setUserPwd(apih5Properties.getDefaultPassword());
			        sysUser.setCreateUserInfo(userKey, realName);
			        ResponseEntity userResponseEntity =  userService.addUser(sysUser);
			        if(userResponseEntity.isSuccess()) {
			            // 关系表
			            SysUserDepartment sysUserDepartment = new SysUserDepartment();
			            sysUserDepartment.setUserDepartmentId(UuidUtil.generate());
			            sysUserDepartment.setUserKey(sysUser.getUserKey());
			            sysUserDepartment.setDepartmentId((String)map.get("departmentId"));
			            sysUserDepartment.setCreateUserInfo(userKey, realName);
			            sysUserDepartmentMapper.insert(sysUserDepartment);
			        } else {
			            returnJSONObject.set("success", false);
			            returnJSONObject.set("desc", "人员创建失败!(" + userResponseEntity.getMessage() + ")");
			            return returnJSONObject;
			        }
			        
			        
			    }
				LoggerUtils.printLogger(logger, "syncZjQfToZxUserInfoByAdd=" + "人员创建失败!(" + responseEntity.getMessage()+")");
				returnJSONObject.set("success", false);
				returnJSONObject.set("desc", "人员创建失败!(" + responseEntity.getMessage()+")");
				return returnJSONObject;
			}
		} catch (Exception e) {
			LoggerUtils.printLogger(logger, "syncZjQfToZxUserInfoByAdd=" + "try的人员创建失败!(" + e.getMessage() + ")");
			returnJSONObject.set("success", false);
			returnJSONObject.set("desc", "人员创建失败!(" + e.getMessage() + ")");
			return returnJSONObject;
		}
	}
	
	@Override
	public JSONObject syncZjQfToZxUserInfoByUpd(SysUser sysUser) {
		JSONObject returnJSONObject = new JSONObject();
		if (StrUtil.isEmpty(sysUser.getUserId())
				|| StrUtil.isEmpty(sysUser.getRealName())
				|| StrUtil.isEmpty(sysUser.getMobile())) {
			LoggerUtils.printLogger(logger, "syncZjQfToZxUserInfoByUpd=用户ID、用户名、电话号中某一个可能为空");
			returnJSONObject.set("success", false);
			returnJSONObject.set("desc", "用户ID、用户名、电话号中某一个可能为空");
			return returnJSONObject;
		}
		try {
			if(sysUser.getSysDepartmentList() == null || sysUser.getSysDepartmentList().size()==0) {
				LoggerUtils.printLogger(logger, "syncZjQfToZxUserInfoByUpd=该人员的部门不存在!1");
				returnJSONObject.set("success", false);
				returnJSONObject.set("desc", "该人员的部门不存在!");
				return returnJSONObject;
			}
			List<Map> sysDepartmentList = sysUser.getSysDepartmentList();
			Map map = sysDepartmentList.get(0);
			SysDepartment sysDepartment = sysDepartmentMapper.selectByPrimaryKey((String)map.get("departmentId"));
			if(sysDepartment == null) {
				LoggerUtils.printLogger(logger, "syncZjQfToZxUserInfoByUpd=该人员的部门不存在!2");
				returnJSONObject.set("success", false);
				returnJSONObject.set("desc", "该人员的部门不存在!");
				return returnJSONObject;
			}

			SysUser dbSysUser = userMapper.getSysUserByUserId(sysUser.getUserId());
			// 如果没有人员，则新增
			if(dbSysUser == null) {
				return syncZjQfToZxUserInfoByAdd(sysUser);
			}
			
			// 如果什么信息都不修改，则默认返回正确
			if (StrUtil.equals(sysUser.getRealName(), dbSysUser.getRealName())
					&& StrUtil.equals(sysUser.getMobile(), dbSysUser.getMobile())) {
				LoggerUtils.printLogger(logger, "syncZjQfToZxUserInfoByUpd=无任何信息修改。");
				returnJSONObject.set("success", true);
				returnJSONObject.set("desc", "无任何信息修改。");
				return returnJSONObject;
			}
			
			sysUser.setUserKey(dbSysUser.getUserKey());
			ResponseEntity responseEntity = userService.updateSysUserInfoByBg(sysUser);
			if(responseEntity.isSuccess()) {
				try {
				    String companyListStr = "[{\"apiUrl\":\"http://wechat.zjyjhw.com/apihaiwei/\",\"flag\":\"haiwei_\",\"accountId\":\"zj_qyh_woa_id\",\"userId\":\"haiwei_admin\"},{\"apiUrl\":\"http://xm-oa.fheb.cn:88/apixiamengs/\",\"flag\":\"xiamengs_\",\"accountId\":\"zj_qyh_woa_id\",\"userId\":\"xiamengs_admin\"},{\"apiUrl\":\"http://3gs-oa.fheb.cn:8088/apisangs/\",\"flag\":\"sangs_\",\"accountId\":\"zj_qyh_woa_id\",\"userId\":\"sangs_admin\"}]";
	                JSONArray companyList = new JSONArray(companyListStr);
	                sysSyncUserInfoService.jobsSysSyncUserInfo(companyList);
		    	}catch (Exception e) {
				}
				returnJSONObject.set("success", true);
				returnJSONObject.set("desc", "ok");
				return returnJSONObject;
			} else {
				LoggerUtils.printLogger(logger, "syncZjQfToZxUserInfoByUpd=人员修改失败!"+responseEntity.getMessage());
				returnJSONObject.set("success", false);
				returnJSONObject.set("desc", "人员修改失败!"+responseEntity.getMessage());
				return returnJSONObject;
			}
		} catch (Exception e) {
			LoggerUtils.printLogger(logger, "syncZjQfToZxUserInfoByUpd=人员修改失败！"+e.getMessage());
			returnJSONObject.set("success", false);
			returnJSONObject.set("desc", "人员修改失败!");
			return returnJSONObject;
		}
		
	}

	@Override
	public JSONObject syncZjQfToZxUserInfoByDel(SysUser sysUser) {
		JSONObject returnJSONObject = new JSONObject();
		if (StrUtil.isEmpty(sysUser.getUserId())) {
			LoggerUtils.printLogger(logger, "syncZjQfToZxUserInfoByDel=用户ID不能为空");
			returnJSONObject.set("success", false);
			returnJSONObject.set("desc", "用户ID不能为空");
			return returnJSONObject;
		}
		try {
			SysUser dbSysUser = userMapper.getSysUserByUserId(sysUser.getUserId());
			if(dbSysUser == null) {
				LoggerUtils.printLogger(logger, "syncZjQfToZxUserInfoByDel=无数据");
				returnJSONObject.set("success", true);
				returnJSONObject.set("desc", "无数据");
				return returnJSONObject;
			}
			sysUser.setUserKey(dbSysUser.getUserKey());
			ResponseEntity responseEntity = userService.deleteSysUserInfoByBg(sysUser);
			if(responseEntity.isSuccess()) {
				try {
				    String companyListStr = "[{\"apiUrl\":\"http://wechat.zjyjhw.com/apihaiwei/\",\"flag\":\"haiwei_\",\"accountId\":\"zj_qyh_woa_id\",\"userId\":\"haiwei_admin\"},{\"apiUrl\":\"http://xm-oa.fheb.cn:88/apixiamengs/\",\"flag\":\"xiamengs_\",\"accountId\":\"zj_qyh_woa_id\",\"userId\":\"xiamengs_admin\"},{\"apiUrl\":\"http://3gs-oa.fheb.cn:8088/apisangs/\",\"flag\":\"sangs_\",\"accountId\":\"zj_qyh_woa_id\",\"userId\":\"sangs_admin\"}]";
	                JSONArray companyList = new JSONArray(companyListStr);
	                sysSyncUserInfoService.jobsSysSyncUserInfo(companyList);
		    	}catch (Exception e) {
				}
				returnJSONObject.set("success", true);
				returnJSONObject.set("desc", "ok");
				return returnJSONObject;
			} else {
				LoggerUtils.printLogger(logger, "syncZjQfToZxUserInfoByDel=部门删除失败!"+responseEntity.getMessage());
				returnJSONObject.set("success", false);
				returnJSONObject.set("desc", "部门删除失败!"+responseEntity.getMessage());
				return returnJSONObject;
			}
		} catch (Exception e) {
			LoggerUtils.printLogger(logger, "syncZjQfToZxUserInfoByDel=部门删除失败!" + e.getMessage());
			returnJSONObject.set("success", false);
			returnJSONObject.set("desc", "部门删除失败!" + e.getMessage());
			return returnJSONObject;
		}
	}

	// /**
	// * 根据userId获取人员列表
	// *
	// * @param oaUserId
	// * @return
	// */
	// @Override
	// public ResponseEntity getMemberInfo() {
	// HttpServletRequest request =
	// requestHolderConfiguration.getHttpServletRequest();
	// String oaUserId = TokenUtils.getUserId(request);
	// String orgId = TokenUtils.getDepartmentId(request);
	//
	// logger.debug("getMemberInfo 开始....");
	// logger.debug("getMemberInfo oaUserId...." + oaUserId);
	// // 1、根据userId获取oa系统中的分公司IP分发地址
	// String companyUrl =
	// zjWeChatOaService.getZjWeChatOaCompanyIpAddressByUserId(oaUserId);
	// logger.debug("getMemberListByDepartment companyUrl...." + companyUrl);
	// if(StrUtil.isNotEmpty(oaUserId) && oaUserId.indexOf("tz_") >= 0){
	// companyUrl = "http://tz-oa.fheb.cn:8080";
	// }
	// else if(StrUtil.isNotEmpty(oaUserId) && oaUserId.indexOf("haiwei_") >=
	// 0){
	// companyUrl = "http://haiwei-oa.fheb.cn:8083";
	// }
	//
	// // 2、把IP分发地址存入相应的userId缓存中，防止每次从接口获取
	// CachData.setInterfaseUrl(oaUserId, companyUrl);
	// CachData.oaInterfaceUserIdMap.put(oaUserId, oaUserId);
	//
	// // 3、获取OA系统安全认证的token
	// CachData.getAcceeToken(oaUserId);
	// ZjAccessCode accessCodeBean = (ZjAccessCode)
	// CachData.accessMap.get(oaUserId);
	//
	// // 4、获取OA系统人员信息
	// String url =
	// CachData.oaInterfaceMap.get(oaUserId).getGET_USER_BY_ORG().replace("METHOD",
	// "getUserByOrg")
	// .replace("USERID", CachData.oaInterfaceUserIdMap.get(oaUserId))
	// .replace("ACCESSCODE", accessCodeBean.getAccessCode()).replace("ORG_ID",
	// orgId);
	// String jsonStr = HttpUtil.sendGet(url);
	// JSONObject json = new
	// JSONObject(jsonStr);//JSONObject.fromObject(jsonStr);
	// OAMember member = new OAMember();
	// if (json.get("data") != null && !json.get("data").equals("")) {
	// JSONArray dataArray = (JSONArray) json.get("data");
	// List list = (List) dataArray;//JSONArray.toCollection(dataArray,
	// Map.class);
	// for (int i =0; i< list.size();i++) {
	// Map accessMap = (Map) list.get(i);
	// if(oaUserId.equals(accessMap.get("userAccount").toString())){
	// member.setUserid(accessMap.get("userAccount").toString());
	// member.setName(accessMap.get("userName").toString());
	// member.setOrgId(accessMap.get("orgID").toString());
	//// return member;
	// return repEntity.ok(member);
	// }
	// }
	// }
	//// return member;
	// return repEntity.ok(member);
	// }
	//

}
