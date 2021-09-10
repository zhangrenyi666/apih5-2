package com.apih5.service.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.api.sysdb.entity.SysUserDepartment;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.SysDepartmentMapper;
import com.apih5.mybatis.dao.SysSyncUserInfoMapper;
import com.apih5.mybatis.dao.SysUserDepartmentMapper;
import com.apih5.mybatis.dao.SysUserMapper;
import com.apih5.mybatis.pojo.SysSyncUserInfo;
import com.apih5.service.SysSyncUserInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

@Service("sysSyncUserInfoService")
public class SysSyncUserInfoServiceImpl implements SysSyncUserInfoService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private SysSyncUserInfoMapper sysSyncUserInfoMapper;
    
    @Autowired(required = true)
    private SysDepartmentMapper sysDepartmentMapper;
    
    @Autowired(required = true)
    private SysUserDepartmentMapper sysUserDepartmentMapper;
    
    @Autowired(required = true)
    private SysUserMapper sysUserMapper;
    
    @Autowired
    private Apih5Properties apih5Properties;

    @Override
    public ResponseEntity getSysSyncUserInfoListByCondition(SysSyncUserInfo sysSyncUserInfo) {
        if (sysSyncUserInfo == null) {
            sysSyncUserInfo = new SysSyncUserInfo();
        }
        // 分页查询
        PageHelper.startPage(sysSyncUserInfo.getPage(),sysSyncUserInfo.getLimit());
        // 获取数据
        List<SysSyncUserInfo> sysSyncUserInfoList = sysSyncUserInfoMapper.selectBySysSyncUserInfoList(sysSyncUserInfo);
        // 得到分页信息
        PageInfo<SysSyncUserInfo> p = new PageInfo<>(sysSyncUserInfoList);

        return repEntity.okList(sysSyncUserInfoList, p.getTotal());
    }

    @Override
    public ResponseEntity getSysSyncUserInfoDetails(SysSyncUserInfo sysSyncUserInfo) {
        if (sysSyncUserInfo == null) {
            sysSyncUserInfo = new SysSyncUserInfo();
        }
        // 获取数据
        SysSyncUserInfo dbSysSyncUserInfo = sysSyncUserInfoMapper.selectByPrimaryKey(sysSyncUserInfo.getSyncId());
        // 数据存在
        if (dbSysSyncUserInfo != null) {
            return repEntity.ok(dbSysSyncUserInfo);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveSysSyncUserInfo(SysSyncUserInfo sysSyncUserInfo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        sysSyncUserInfo.setSyncId(UuidUtil.generate());
        sysSyncUserInfo.setCreateUserInfo(userKey, realName);
        int flag = sysSyncUserInfoMapper.insert(sysSyncUserInfo);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", sysSyncUserInfo);
        }
    }

    @Override
    public ResponseEntity updateSysSyncUserInfo(SysSyncUserInfo sysSyncUserInfo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        SysSyncUserInfo dbsysSyncUserInfo = sysSyncUserInfoMapper.selectByPrimaryKey(sysSyncUserInfo.getSyncId());
        if (dbsysSyncUserInfo != null && StrUtil.isNotEmpty(dbsysSyncUserInfo.getSyncId())) {
           // 同步转态
           dbsysSyncUserInfo.setSyncState(sysSyncUserInfo.getSyncState());
           // 同步类型
           dbsysSyncUserInfo.setSyncType(sysSyncUserInfo.getSyncType());
           // 公司
           dbsysSyncUserInfo.setCompanyId(sysSyncUserInfo.getCompanyId());
           // 公司名称
           dbsysSyncUserInfo.setCompanyName(sysSyncUserInfo.getCompanyName());
           // apiUrl
           dbsysSyncUserInfo.setApiUrl(sysSyncUserInfo.getApiUrl());
           // apiPar
           dbsysSyncUserInfo.setApiPar(sysSyncUserInfo.getApiPar());
           // 共通
           dbsysSyncUserInfo.setModifyUserInfo(userKey, realName);
           flag = sysSyncUserInfoMapper.updateByPrimaryKey(dbsysSyncUserInfo);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",sysSyncUserInfo);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateSysSyncUserInfo(List<SysSyncUserInfo> sysSyncUserInfoList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (sysSyncUserInfoList != null && sysSyncUserInfoList.size() > 0) {
           SysSyncUserInfo sysSyncUserInfo = new SysSyncUserInfo();
           sysSyncUserInfo.setModifyUserInfo(userKey, realName);
           flag = sysSyncUserInfoMapper.batchDeleteUpdateSysSyncUserInfo(sysSyncUserInfoList, sysSyncUserInfo);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",sysSyncUserInfoList);
        }
    }

    @Override
	public ResponseEntity jobsSysSyncUserInfo(JSONArray jsonArray) {
        SysSyncUserInfo sysSyncUserInfo = new SysSyncUserInfo();
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = "";//TokenUtils.getUserKey(request);
        String realName = "";//TokenUtils.getRealName(request);
        DateTime time = new DateTime();
        String timePar = request.getParameter("time");
        if(StrUtil.isNotEmpty(timePar)) {
            time.offset(DateField.HOUR_OF_DAY, Integer.valueOf(timePar));
        } else {
            time.offset(DateField.HOUR_OF_DAY, -1);
        }
        String dateStr = request.getParameter("date");
        if(StrUtil.isNotEmpty(dateStr)) {
            time = DateUtil.parse(dateStr, "yyyyMMdd");
        }
		
		if(jsonArray == null || jsonArray.size()==0) {
			return repEntity.ok("loing全部失败");
		}
		
		// 1、根据当前时间获取增量数据（部门）
		SysDepartment sysDepartmentSelect = new SysDepartment();
		sysDepartmentSelect.setModifyTime(time);
	    List<SysDepartment> sysDepartmentList= sysDepartmentMapper.selectSysDepartmentListBySync(sysDepartmentSelect);
		if(sysDepartmentList != null && sysDepartmentList.size()>0) {
			for(SysDepartment sysDepartment : sysDepartmentList) {
				String param = JSONUtil.parseObj(sysDepartment).toString();
				sysSyncUserInfo.setSyncId(UuidUtil.generate());
				sysSyncUserInfo.setSyncType("2");
				sysSyncUserInfo.setApiPar(param);
				sysSyncUserInfo.setCreateUserInfo(userKey, realName);
				// 2、则分别循环同步给不同的服务器
				for (Iterator<Object> iterator = jsonArray.iterator(); iterator.hasNext();) {
					JSONObject jsonObjectApiUrl = (JSONObject)iterator.next();
					String url = jsonObjectApiUrl.getStr("apiUrl");
					sysSyncUserInfo.setCompanyId(jsonObjectApiUrl.getStr("companyId"));
					sysSyncUserInfo.setApiUrl(url);
					try {
						if(StrUtil.equals("1", sysDepartment.getDelFlag())) {
							url += "deleteSysDepartment";
							sysSyncUserInfo.setCreateUserInfo(userKey, "del");
						} else {
							// 相同则新增
							if(sysDepartment.getModifyTime().equals(sysDepartment.getCreateTime())) {
								url += "addSysDepartment";
								sysSyncUserInfo.setCreateUserInfo(userKey, "add");
							} else {
								// 修改
								url += "updateSysDepartment";
								sysSyncUserInfo.setCreateUserInfo(userKey, "upd");
							}
						}
						String result = HttpUtil.sendPostToken(url, param, jsonObjectApiUrl.getStr("token"));
						JSONObject jsonObject = new JSONObject(result);
						// 缓存过期
						if(StrUtil.equals("3007", jsonObject.getStr("code"))) {
							JSONObject tokenPar = new JSONObject();
							tokenPar.set("token", jsonObjectApiUrl.getStr("token"));
							result = HttpUtil.sendPostJson(jsonObjectApiUrl.getStr("apiUrl")+"user/refreshAccessToken", tokenPar.toString());
							JSONObject tokenResult = new JSONObject(result);
							
							result = HttpUtil.sendPostToken(url, param, tokenResult.getStr("data"));
							jsonObject = new JSONObject(result);
						}
						
						if(!jsonObject.getBool("success")) {
							// 3、请求失败时，存入同步表中
							sysSyncUserInfo.setSyncState("1");
							try {
							    int flag = sysSyncUserInfoMapper.insert(sysSyncUserInfo);
							}catch (Exception e) {
                            }
						}
					} catch (Exception e) {
						sysSyncUserInfo.setSyncState("2");
//						int flag = sysSyncUserInfoMapper.insert(sysSyncUserInfo);
					}
				}
			}
		}
		
		// 2、根据当前时间获取增量数据（用户部门表）
        SysUserDepartment sysUserDepartmentSelect = new SysUserDepartment();
        sysUserDepartmentSelect.setModifyTime(time);
		List<SysUserDepartment> sysUserDepartmentList = sysUserDepartmentMapper.selectSysUserDepartmentListBySync(sysUserDepartmentSelect);
		if(sysUserDepartmentList != null && sysUserDepartmentList.size()>0) {
			for(SysUserDepartment sysUserDepartment : sysUserDepartmentList) {
				String param = JSONUtil.parseObj(sysUserDepartment).toString();
				sysSyncUserInfo.setSyncId(UuidUtil.generate());
				sysSyncUserInfo.setSyncType("2");
				sysSyncUserInfo.setApiPar(param);
				sysSyncUserInfo.setCreateUserInfo(userKey, realName);
				// 2、则分别循环同步给不同的服务器
				for (Iterator<Object> iterator = jsonArray.iterator(); iterator.hasNext();) {
					JSONObject jsonObjectApiUrl = (JSONObject)iterator.next();
					String url = jsonObjectApiUrl.getStr("apiUrl");
					sysSyncUserInfo.setCompanyId(jsonObjectApiUrl.getStr("companyId"));
					try {
						if(StrUtil.equals("1", sysUserDepartment.getDelFlag())) {
							url += "batchDeleteUpdateSysUserDepartment";
							sysSyncUserInfo.setCreateUserInfo(userKey, "delete");
						} else {
							// 相同则新增
							if(sysUserDepartment.getModifyTime().equals(sysUserDepartment.getCreateTime())) {
								sysSyncUserInfo.setCreateUserInfo(userKey, "add");
								url += "syncAddSysUserDepartment";
							} else {
								// 修改
								sysSyncUserInfo.setCreateUserInfo(userKey, "upd");
								url += "updateSysUserDepartment";
							}
						}
						sysSyncUserInfo.setApiUrl(url);
						
						String result = HttpUtil.sendPostToken(url, param, jsonObjectApiUrl.getStr("token"));
						JSONObject jsonObject = new JSONObject(result);
						// 缓存过期
						if(StrUtil.equals("3007", jsonObject.getStr("code"))) {
							JSONObject tokenPar = new JSONObject();
							tokenPar.put("token", jsonObjectApiUrl.getStr("token"));
							result = HttpUtil.sendPostJson(jsonObjectApiUrl.getStr("apiUrl")+"user/refreshAccessToken", tokenPar.toString());
							JSONObject tokenResult = new JSONObject(result);
							
							result = HttpUtil.sendPostToken(url, param, tokenResult.getStr("data"));
							jsonObject = new JSONObject(result);
						}
						if(!jsonObject.getBool("success")) {
							// 3、请求失败时，存入同步表中
							sysSyncUserInfo.setSyncState("1");
							try {
							    int flag = sysSyncUserInfoMapper.insert(sysSyncUserInfo);
                            }catch (Exception e) {
                            }
						} else {
							if(sysUserDepartment.getModifyTime().equals(sysUserDepartment.getCreateTime())
									&& StrUtil.endWith("0", jsonObject.getStr("data"))) {
								// 3、请求失败时，存入同步表中
								sysSyncUserInfo.setSyncState("1");
								try {
								    int flag = sysSyncUserInfoMapper.insert(sysSyncUserInfo);
	                            }catch (Exception e) {
	                            }
							}
						}
					} catch (Exception e) {
						sysSyncUserInfo.setSyncState("2");
//						int flag = sysSyncUserInfoMapper.insert(sysSyncUserInfo);
					}
				}
			}
		}
        
        // 3、根据当前时间获取增量数据（用户表）
        SysUser sysUserSelect = new SysUser();
        sysUserSelect.setModifyTime(time);
		List<SysUser> sysUserList = sysUserMapper.selectSysUserListBySync(sysUserSelect);
		if(sysUserList != null && sysUserList.size()>0) {
			for(SysUser sysUser : sysUserList) {
				sysSyncUserInfo.setSyncId(UuidUtil.generate());
				sysSyncUserInfo.setSyncType("3");
				sysSyncUserInfo.setCreateUserInfo(userKey, realName);
				 String userPwd = SecureUtil.md5(apih5Properties.getDefaultPassword() + apih5Properties.getMd5Salt());
				sysUser.setUserPwd(userPwd);
				// 2、则分别循环同步给不同的服务器
				for (Iterator<Object> iterator = jsonArray.iterator(); iterator.hasNext();) {
					JSONObject jsonObjectApiUrl = (JSONObject)iterator.next();
					String url = jsonObjectApiUrl.getStr("apiUrl");
					sysSyncUserInfo.setCompanyId(jsonObjectApiUrl.getStr("companyId"));
					try {
						if(StrUtil.equals("1", sysUser.getDelFlag())) {
							url += "user/deleteSysUserInfoByBg";
							sysSyncUserInfo.setCreateUserInfo(userKey, "delete");
						} else {
							// 相同则新增
							if(sysUser.getModifyTime().equals(sysUser.getCreateTime())) {
								sysSyncUserInfo.setCreateUserInfo(userKey, "add");
								url += "user/syncAddSysUser";
							} else {
								// 修改
								sysSyncUserInfo.setCreateUserInfo(userKey, "upd");
								url += "user/syncUpdateUserCommon";
							}
						}
						sysSyncUserInfo.setApiUrl(url);
						sysUser.setAccountId(jsonObjectApiUrl.getStr("accountId"));
						String param = JSONUtil.parseObj(sysUser).toString();
						sysSyncUserInfo.setApiPar(param);
						String result = HttpUtil.sendPostToken(url, param, jsonObjectApiUrl.getStr("token"));
						JSONObject jsonObject = new JSONObject(result);
						// 缓存过期
						if(StrUtil.equals("3007", jsonObject.getStr("code"))) {
							JSONObject tokenPar = new JSONObject();
							tokenPar.put("token", jsonObjectApiUrl.getStr("token"));
							result = HttpUtil.sendPostJson(jsonObjectApiUrl.getStr("apiUrl")+"user/refreshAccessToken", tokenPar.toString());
							JSONObject tokenResult = new JSONObject(result);
							
							result = HttpUtil.sendPostToken(url, param, tokenResult.getStr("data"));
							jsonObject = new JSONObject(result);
						}
						if(!jsonObject.getBool("success")) {
							// 3、请求失败时，存入同步表中
							sysSyncUserInfo.setSyncState("1");
							try {
							    int flag = sysSyncUserInfoMapper.insert(sysSyncUserInfo);
                            }catch (Exception e) {
                            }
						} else {
							if(sysUser.getModifyTime().equals(sysUser.getCreateTime())
									&& StrUtil.equals("0", jsonObject.getStr("data"))) {
								// 3、请求失败时，存入同步表中
								sysSyncUserInfo.setSyncState("1");
								try {
								    int flag = sysSyncUserInfoMapper.insert(sysSyncUserInfo);
	                            }catch (Exception e) {
	                            }
							}
						}
					} catch (Exception e) {
						sysSyncUserInfo.setSyncState("2");
						try {
						    int flag = sysSyncUserInfoMapper.insert(sysSyncUserInfo);
                        }catch (Exception e1) {
                        }
					}
				}
			}
		}

		return repEntity.ok("");
	}
}
