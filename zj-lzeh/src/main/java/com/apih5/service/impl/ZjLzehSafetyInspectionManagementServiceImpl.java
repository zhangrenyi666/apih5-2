package com.apih5.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.ConfigConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.entity.SysSequence;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZjLzehQualityInspectionStatisticsMapper;
import com.apih5.mybatis.dao.ZjLzehSafetyInspectionManagementMapper;
import com.apih5.mybatis.dao.ZjLzehSafetyInspectionStatisticsMapper;
import com.apih5.mybatis.pojo.PictureBean;
import com.apih5.mybatis.pojo.ZjLzehFile;
import com.apih5.mybatis.pojo.ZjLzehQualityInspectionStatistics;
import com.apih5.mybatis.pojo.ZjLzehSafetyInspectionManagement;
import com.apih5.mybatis.pojo.ZjLzehSafetyInspectionStatistics;
import com.apih5.service.ZjLzehSafetyInspectionManagementService;
import com.apih5.utils.ZjLzehUtil;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.data.DocxRenderData;
import com.deepoove.poi.data.PictureRenderData;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

@Service("zjLzehSafetyInspectionManagementService")
public class ZjLzehSafetyInspectionManagementServiceImpl implements ZjLzehSafetyInspectionManagementService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjLzehSafetyInspectionManagementMapper zjLzehSafetyInspectionManagementMapper;
    
    @Autowired(required = true)
    private ZjLzehSafetyInspectionStatisticsMapper zjLzehSafetyInspectionStatisticsMapper;
    
    @Autowired(required = true)
    private ZjLzehQualityInspectionStatisticsMapper zjLzehQualityInspectionStatisticsMapper;
    
    @Autowired(required = true)
	private SequenceService sequenceService;
    
    @ApolloConfig(ConfigConst.ZX)
	private Config zxConfig;

    @Override
    public ResponseEntity getZjLzehSafetyInspectionManagementListByCondition(ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagement) {
        if (zjLzehSafetyInspectionManagement == null) {
            zjLzehSafetyInspectionManagement = new ZjLzehSafetyInspectionManagement();
        }
        // 分页查询
//        PageHelper.startPage(zjLzehSafetyInspectionManagement.getPage(),zjLzehSafetyInspectionManagement.getLimit());
        // 获取数据
        List<ZjLzehSafetyInspectionManagement> zjLzehSafetyInspectionManagementList = zjLzehSafetyInspectionManagementMapper.selectByZjLzehSafetyInspectionManagementList(zjLzehSafetyInspectionManagement);
        // 得到分页信息
//        PageInfo<ZjLzehSafetyInspectionManagement> p = new PageInfo<>(zjLzehSafetyInspectionManagementList);

        return repEntity.ok(zjLzehSafetyInspectionManagementList);
    }

    @Override
    public ResponseEntity getZjLzehSafetyInspectionManagementDetails(ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagement) {
        if (zjLzehSafetyInspectionManagement == null) {
            zjLzehSafetyInspectionManagement = new ZjLzehSafetyInspectionManagement();
        }
        // 获取数据
        ZjLzehSafetyInspectionManagement dbZjLzehSafetyInspectionManagement = zjLzehSafetyInspectionManagementMapper.selectByPrimaryKey(zjLzehSafetyInspectionManagement.getSafetyInspectionManagementId());
        // 数据存在
        if (dbZjLzehSafetyInspectionManagement != null) {
            return repEntity.ok(dbZjLzehSafetyInspectionManagement);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjLzehSafetyInspectionManagement(ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjLzehSafetyInspectionManagement.setSafetyInspectionManagementId(UuidUtil.generate());
        zjLzehSafetyInspectionManagement.setCreateUserInfo(userKey, realName);
        int flag = zjLzehSafetyInspectionManagementMapper.insert(zjLzehSafetyInspectionManagement);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjLzehSafetyInspectionManagement);
        }
    }

    @Override
    public ResponseEntity updateZjLzehSafetyInspectionManagement(ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagement) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjLzehSafetyInspectionManagement dbzjLzehSafetyInspectionManagement = zjLzehSafetyInspectionManagementMapper.selectByPrimaryKey(zjLzehSafetyInspectionManagement.getSafetyInspectionManagementId());
        if (dbzjLzehSafetyInspectionManagement != null && StrUtil.isNotEmpty(dbzjLzehSafetyInspectionManagement.getSafetyInspectionManagementId())) {
           // 排序
           dbzjLzehSafetyInspectionManagement.setOrderFlag(zjLzehSafetyInspectionManagement.getOrderFlag());
           // 名称
           dbzjLzehSafetyInspectionManagement.setName(zjLzehSafetyInspectionManagement.getName());
           // 总计数量
           dbzjLzehSafetyInspectionManagement.setTotalNumber(zjLzehSafetyInspectionManagement.getTotalNumber());
           // 解除数量
           dbzjLzehSafetyInspectionManagement.setRemoveNumber(zjLzehSafetyInspectionManagement.getRemoveNumber());
           // 共通
           dbzjLzehSafetyInspectionManagement.setModifyUserInfo(userKey, realName);
           flag = zjLzehSafetyInspectionManagementMapper.updateByPrimaryKey(dbzjLzehSafetyInspectionManagement);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjLzehSafetyInspectionManagement);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjLzehSafetyInspectionManagement(List<ZjLzehSafetyInspectionManagement> zjLzehSafetyInspectionManagementList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjLzehSafetyInspectionManagementList != null && zjLzehSafetyInspectionManagementList.size() > 0) {
           ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagement = new ZjLzehSafetyInspectionManagement();
           zjLzehSafetyInspectionManagement.setModifyUserInfo(userKey, realName);
           flag = zjLzehSafetyInspectionManagementMapper.batchDeleteUpdateZjLzehSafetyInspectionManagement(zjLzehSafetyInspectionManagementList, zjLzehSafetyInspectionManagement);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zjLzehSafetyInspectionManagementList);
        }
    }

	@Override
	public ResponseEntity getZjLzehSafetyInspectionManagementBySumDangerType(ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagement) {
		String falseFlag = zxConfig.getProperty("use.flase.date", "0");
		if (StrUtil.equals("1", falseFlag)) {
			List<ZjLzehSafetyInspectionManagement> returnList = zjLzehSafetyInspectionManagementMapper.selectByZjLzehSafetyInspectionManagementList(zjLzehSafetyInspectionManagement);
			for (ZjLzehSafetyInspectionManagement management : returnList) {
				management.setContent(management.getName());
				management.setTotal(management.getTotalNumber());
				management.setCount(management.getRemoveNumber()); 
			}
			
			return repEntity.ok(returnList);
		}
		
		
		List<ZjLzehSafetyInspectionManagement> newZjLzehSafetyInspectionManagementDangerTypeList = Lists.newArrayList();
			if(true) {
				ZjLzehSafetyInspectionManagement newZjLzehSafetyInspectionManagementDangerType = new ZjLzehSafetyInspectionManagement();
				newZjLzehSafetyInspectionManagementDangerType.setContent("安全管理");
				newZjLzehSafetyInspectionManagementDangerType.setTotal(0);
				newZjLzehSafetyInspectionManagementDangerType.setCount(0); 
				newZjLzehSafetyInspectionManagementDangerTypeList.add(newZjLzehSafetyInspectionManagementDangerType);
			}
			
			if(true) {
				ZjLzehSafetyInspectionManagement newZjLzehSafetyInspectionManagementDangerType = new ZjLzehSafetyInspectionManagement();
				newZjLzehSafetyInspectionManagementDangerType.setContent("文明施工");
				newZjLzehSafetyInspectionManagementDangerType.setTotal(0);
				newZjLzehSafetyInspectionManagementDangerType.setCount(0); 
				newZjLzehSafetyInspectionManagementDangerTypeList.add(newZjLzehSafetyInspectionManagementDangerType);
			}
			if(true) {
				ZjLzehSafetyInspectionManagement newZjLzehSafetyInspectionManagementDangerType = new ZjLzehSafetyInspectionManagement();
				newZjLzehSafetyInspectionManagementDangerType.setContent("临边防护");
				newZjLzehSafetyInspectionManagementDangerType.setTotal(0);
				newZjLzehSafetyInspectionManagementDangerType.setCount(0); 
				newZjLzehSafetyInspectionManagementDangerTypeList.add(newZjLzehSafetyInspectionManagementDangerType);
			}
			if(true) {
				ZjLzehSafetyInspectionManagement newZjLzehSafetyInspectionManagementDangerType = new ZjLzehSafetyInspectionManagement();
				newZjLzehSafetyInspectionManagementDangerType.setContent("高处作业");
				newZjLzehSafetyInspectionManagementDangerType.setTotal(0);
				newZjLzehSafetyInspectionManagementDangerType.setCount(0); 
				newZjLzehSafetyInspectionManagementDangerTypeList.add(newZjLzehSafetyInspectionManagementDangerType);
			}
			if(true) {
				ZjLzehSafetyInspectionManagement newZjLzehSafetyInspectionManagementDangerType = new ZjLzehSafetyInspectionManagement();
				newZjLzehSafetyInspectionManagementDangerType.setContent("基坑支护");
				newZjLzehSafetyInspectionManagementDangerType.setTotal(0);
				newZjLzehSafetyInspectionManagementDangerType.setCount(0); 
				newZjLzehSafetyInspectionManagementDangerTypeList.add(newZjLzehSafetyInspectionManagementDangerType);
			}
			if(true) {
				ZjLzehSafetyInspectionManagement newZjLzehSafetyInspectionManagementDangerType = new ZjLzehSafetyInspectionManagement();
				newZjLzehSafetyInspectionManagementDangerType.setContent("模板工程");
				newZjLzehSafetyInspectionManagementDangerType.setTotal(0);
				newZjLzehSafetyInspectionManagementDangerType.setCount(0); 
				newZjLzehSafetyInspectionManagementDangerTypeList.add(newZjLzehSafetyInspectionManagementDangerType);
			}
			if(true) {
				ZjLzehSafetyInspectionManagement newZjLzehSafetyInspectionManagementDangerType = new ZjLzehSafetyInspectionManagement();
				newZjLzehSafetyInspectionManagementDangerType.setContent("施工机具");
				newZjLzehSafetyInspectionManagementDangerType.setTotal(0);
				newZjLzehSafetyInspectionManagementDangerType.setCount(0); 
				newZjLzehSafetyInspectionManagementDangerTypeList.add(newZjLzehSafetyInspectionManagementDangerType);
			}
			if(true) {
				ZjLzehSafetyInspectionManagement newZjLzehSafetyInspectionManagementDangerType = new ZjLzehSafetyInspectionManagement();
				newZjLzehSafetyInspectionManagementDangerType.setContent("脚手架");
				newZjLzehSafetyInspectionManagementDangerType.setTotal(0);
				newZjLzehSafetyInspectionManagementDangerType.setCount(0); 
				newZjLzehSafetyInspectionManagementDangerTypeList.add(newZjLzehSafetyInspectionManagementDangerType);
			}
			if(true) {
				ZjLzehSafetyInspectionManagement newZjLzehSafetyInspectionManagementDangerType = new ZjLzehSafetyInspectionManagement();
				newZjLzehSafetyInspectionManagementDangerType.setContent("交通安全");
				newZjLzehSafetyInspectionManagementDangerType.setTotal(0);
				newZjLzehSafetyInspectionManagementDangerType.setCount(0); 
				newZjLzehSafetyInspectionManagementDangerTypeList.add(newZjLzehSafetyInspectionManagementDangerType);
			}
			if(true) {
				ZjLzehSafetyInspectionManagement newZjLzehSafetyInspectionManagementDangerType = new ZjLzehSafetyInspectionManagement();
				newZjLzehSafetyInspectionManagementDangerType.setContent("个体防护");
				newZjLzehSafetyInspectionManagementDangerType.setTotal(0);
				newZjLzehSafetyInspectionManagementDangerType.setCount(0); 
				newZjLzehSafetyInspectionManagementDangerTypeList.add(newZjLzehSafetyInspectionManagementDangerType);
			}
			if(true) {
				ZjLzehSafetyInspectionManagement newZjLzehSafetyInspectionManagementDangerType = new ZjLzehSafetyInspectionManagement();
				newZjLzehSafetyInspectionManagementDangerType.setContent("起重吊装");
				newZjLzehSafetyInspectionManagementDangerType.setTotal(0);
				newZjLzehSafetyInspectionManagementDangerType.setCount(0); 
				newZjLzehSafetyInspectionManagementDangerTypeList.add(newZjLzehSafetyInspectionManagementDangerType);
			}
			if(true) {
				ZjLzehSafetyInspectionManagement newZjLzehSafetyInspectionManagementDangerType = new ZjLzehSafetyInspectionManagement();
				newZjLzehSafetyInspectionManagementDangerType.setContent("施工用电");
				newZjLzehSafetyInspectionManagementDangerType.setTotal(0);
				newZjLzehSafetyInspectionManagementDangerType.setCount(0); 
				newZjLzehSafetyInspectionManagementDangerTypeList.add(newZjLzehSafetyInspectionManagementDangerType);
			}
			if(true) {
				ZjLzehSafetyInspectionManagement newZjLzehSafetyInspectionManagementDangerType = new ZjLzehSafetyInspectionManagement();
				newZjLzehSafetyInspectionManagementDangerType.setContent("消防防火");
				newZjLzehSafetyInspectionManagementDangerType.setTotal(0);
				newZjLzehSafetyInspectionManagementDangerType.setCount(0); 
				newZjLzehSafetyInspectionManagementDangerTypeList.add(newZjLzehSafetyInspectionManagementDangerType);
			}
		
		List<ZjLzehSafetyInspectionManagement> zjLzehSafetyInspectionManagementDangerTypeList = zjLzehSafetyInspectionManagementMapper.selectZjLzehSafetyInspectionManagementBySumDangerType(null);
		for(ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagementDangerType:newZjLzehSafetyInspectionManagementDangerTypeList) {
			if(zjLzehSafetyInspectionManagementDangerTypeList != null && zjLzehSafetyInspectionManagementDangerTypeList.size() > 0) {
				for(ZjLzehSafetyInspectionManagement dbZjLzehSafetyInspectionManagementDangerType:zjLzehSafetyInspectionManagementDangerTypeList) {
					if(StrUtil.equals(zjLzehSafetyInspectionManagementDangerType.getContent(), dbZjLzehSafetyInspectionManagementDangerType.getContent())) {
						zjLzehSafetyInspectionManagementDangerType.setCount(dbZjLzehSafetyInspectionManagementDangerType.getCount());
						zjLzehSafetyInspectionManagementDangerType.setTotal(dbZjLzehSafetyInspectionManagementDangerType.getTotal());
					}
				}
			}
		}
		
		return repEntity.ok(newZjLzehSafetyInspectionManagementDangerTypeList);
	}

	@Override
	public ResponseEntity getZjLzehSafetyInspectionManagementBySumDangerLevel(ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagement) {
		String falseFlag = zxConfig.getProperty("use.flase.date", "0");
		if (StrUtil.equals("1", falseFlag)) {
			List<ZjLzehSafetyInspectionManagement> newZjLzehSafetyInspectionManagementDangerLevelList = Lists.newArrayList();
			List<ZjLzehSafetyInspectionStatistics> returnList = zjLzehSafetyInspectionStatisticsMapper.selectByZjLzehSafetyInspectionStatisticsList(new ZjLzehSafetyInspectionStatistics());
			for (ZjLzehSafetyInspectionStatistics Statistics : returnList) {
				ZjLzehSafetyInspectionManagement newZjLzehSafetyInspectionManagementDangerLevel = new ZjLzehSafetyInspectionManagement();
				newZjLzehSafetyInspectionManagementDangerLevel.setDangerlevel(Statistics.getName());
				newZjLzehSafetyInspectionManagementDangerLevel.setSolved(Statistics.getTotalNumber());
				newZjLzehSafetyInspectionManagementDangerLevel.setUnsolved(Statistics.getRemoveNumber()); 
				newZjLzehSafetyInspectionManagementDangerLevelList.add(newZjLzehSafetyInspectionManagementDangerLevel);
			}
			
			return repEntity.ok(newZjLzehSafetyInspectionManagementDangerLevelList);
		}
		
		List<ZjLzehSafetyInspectionManagement> newZjLzehSafetyInspectionManagementDangerLevelList = Lists.newArrayList();
			if (true) {
				ZjLzehSafetyInspectionManagement newZjLzehSafetyInspectionManagementDangerLevel = new ZjLzehSafetyInspectionManagement();
				newZjLzehSafetyInspectionManagementDangerLevel.setDangerlevel("可接受");
				newZjLzehSafetyInspectionManagementDangerLevel.setSolved(0);
				newZjLzehSafetyInspectionManagementDangerLevel.setUnsolved(0);
				newZjLzehSafetyInspectionManagementDangerLevelList.add(newZjLzehSafetyInspectionManagementDangerLevel);

			}
			if (true) {
				ZjLzehSafetyInspectionManagement newZjLzehSafetyInspectionManagementDangerLevel = new ZjLzehSafetyInspectionManagement();
				newZjLzehSafetyInspectionManagementDangerLevel.setDangerlevel("轻微");
				newZjLzehSafetyInspectionManagementDangerLevel.setSolved(0);
				newZjLzehSafetyInspectionManagementDangerLevel.setUnsolved(0);
				newZjLzehSafetyInspectionManagementDangerLevelList.add(newZjLzehSafetyInspectionManagementDangerLevel);

			}
			if (true) {
				ZjLzehSafetyInspectionManagement newZjLzehSafetyInspectionManagementDangerLevel = new ZjLzehSafetyInspectionManagement();
				newZjLzehSafetyInspectionManagementDangerLevel.setDangerlevel("较大");
				newZjLzehSafetyInspectionManagementDangerLevel.setSolved(0);
				newZjLzehSafetyInspectionManagementDangerLevel.setUnsolved(0);
				newZjLzehSafetyInspectionManagementDangerLevelList.add(newZjLzehSafetyInspectionManagementDangerLevel);

			}
			if (true) {
				ZjLzehSafetyInspectionManagement newZjLzehSafetyInspectionManagementDangerLevel = new ZjLzehSafetyInspectionManagement();
				newZjLzehSafetyInspectionManagementDangerLevel.setDangerlevel("重大");
				newZjLzehSafetyInspectionManagementDangerLevel.setSolved(0);
				newZjLzehSafetyInspectionManagementDangerLevel.setUnsolved(0);
				newZjLzehSafetyInspectionManagementDangerLevelList.add(newZjLzehSafetyInspectionManagementDangerLevel);
			}
		
		List<ZjLzehSafetyInspectionManagement> zjLzehSafetyInspectionManagementDangerLevelList = zjLzehSafetyInspectionManagementMapper.selectZjLzehSafetyInspectionManagementBySumDangerLevel(zjLzehSafetyInspectionManagement);
		for (ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagementDangerLevel : newZjLzehSafetyInspectionManagementDangerLevelList) {
			if (zjLzehSafetyInspectionManagementDangerLevelList != null && zjLzehSafetyInspectionManagementDangerLevelList.size() > 0) {
				for (ZjLzehSafetyInspectionManagement dbZjLzehSafetyInspectionManagementDangerLevel : zjLzehSafetyInspectionManagementDangerLevelList) {
					if (StrUtil.equals(zjLzehSafetyInspectionManagementDangerLevel.getDangerlevel(), dbZjLzehSafetyInspectionManagementDangerLevel.getDangerlevel())) {
						zjLzehSafetyInspectionManagementDangerLevel.setSolved(dbZjLzehSafetyInspectionManagementDangerLevel.getSolved());
						zjLzehSafetyInspectionManagementDangerLevel.setUnsolved(dbZjLzehSafetyInspectionManagementDangerLevel.getUnsolved());
					}
				}
			}
		}
		
		return repEntity.ok(newZjLzehSafetyInspectionManagementDangerLevelList);
	}

	@Override
	public ResponseEntity getZjLzehSafetyInspectionManagementBySumTroubleLevel(ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagement) {
		String falseFlag = zxConfig.getProperty("use.flase.date", "0");
		if (StrUtil.equals("1", falseFlag)) {
			List<ZjLzehSafetyInspectionManagement> newZjLzehSafetyInspectionManagementDangerLevelList = Lists.newArrayList();
	        List<ZjLzehQualityInspectionStatistics> zjLzehQualityInspectionStatisticsList = zjLzehQualityInspectionStatisticsMapper.selectByZjLzehQualityInspectionStatisticsList(new ZjLzehQualityInspectionStatistics());
			for (ZjLzehQualityInspectionStatistics Statistics : zjLzehQualityInspectionStatisticsList) {
				ZjLzehSafetyInspectionManagement newZjLzehSafetyInspectionManagementDangerLevel = new ZjLzehSafetyInspectionManagement();
				newZjLzehSafetyInspectionManagementDangerLevel.setTroublelevel(Statistics.getName());
				newZjLzehSafetyInspectionManagementDangerLevel.setSolved(Statistics.getTotalNumber());
				newZjLzehSafetyInspectionManagementDangerLevel.setUnsolved(Statistics.getRemoveNumber()); 
				newZjLzehSafetyInspectionManagementDangerLevelList.add(newZjLzehSafetyInspectionManagementDangerLevel);
			}
			
			return repEntity.ok(newZjLzehSafetyInspectionManagementDangerLevelList);
		}
		
		List<ZjLzehSafetyInspectionManagement> newZjLzehSafetyInspectionManagementTroubleLevelList = Lists.newArrayList();
			if (true) {
				ZjLzehSafetyInspectionManagement newZjLzehSafetyInspectionManagementTroubleLevel = new ZjLzehSafetyInspectionManagement();
				newZjLzehSafetyInspectionManagementTroubleLevel.setTroublelevel("一般");
				newZjLzehSafetyInspectionManagementTroubleLevel.setSolved(0);
				newZjLzehSafetyInspectionManagementTroubleLevel.setUnsolved(0);
				newZjLzehSafetyInspectionManagementTroubleLevelList.add(newZjLzehSafetyInspectionManagementTroubleLevel);
			}
			if (true) {
				ZjLzehSafetyInspectionManagement newZjLzehSafetyInspectionManagementTroubleLevel = new ZjLzehSafetyInspectionManagement();
				newZjLzehSafetyInspectionManagementTroubleLevel.setTroublelevel("紧要");
				newZjLzehSafetyInspectionManagementTroubleLevel.setSolved(0);
				newZjLzehSafetyInspectionManagementTroubleLevel.setUnsolved(0);
				newZjLzehSafetyInspectionManagementTroubleLevelList.add(newZjLzehSafetyInspectionManagementTroubleLevel);
			}
			if (true) {
				ZjLzehSafetyInspectionManagement newZjLzehSafetyInspectionManagementTroubleLevel = new ZjLzehSafetyInspectionManagement();
				newZjLzehSafetyInspectionManagementTroubleLevel.setTroublelevel("严重");
				newZjLzehSafetyInspectionManagementTroubleLevel.setSolved(0);
				newZjLzehSafetyInspectionManagementTroubleLevel.setUnsolved(0);
				newZjLzehSafetyInspectionManagementTroubleLevelList.add(newZjLzehSafetyInspectionManagementTroubleLevel);
			}
		
		List<ZjLzehSafetyInspectionManagement> zjLzehSafetyInspectionManagementTroubleLevelList = zjLzehSafetyInspectionManagementMapper.selectZjLzehSafetyInspectionManagementBySumTroubleLevel(zjLzehSafetyInspectionManagement);
		for (ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagementTroubleLevel : newZjLzehSafetyInspectionManagementTroubleLevelList) {
			if (zjLzehSafetyInspectionManagementTroubleLevelList != null && zjLzehSafetyInspectionManagementTroubleLevelList.size() > 0) {
				for (ZjLzehSafetyInspectionManagement dbZjLzehSafetyInspectionManagementTroubleLevel : zjLzehSafetyInspectionManagementTroubleLevelList) {
					if (StrUtil.equals(zjLzehSafetyInspectionManagementTroubleLevel.getTroublelevel(), dbZjLzehSafetyInspectionManagementTroubleLevel.getTroublelevel())) {
						zjLzehSafetyInspectionManagementTroubleLevel.setSolved(dbZjLzehSafetyInspectionManagementTroubleLevel.getSolved());
						zjLzehSafetyInspectionManagementTroubleLevel.setUnsolved(dbZjLzehSafetyInspectionManagementTroubleLevel.getUnsolved());
					}
				}
			}
		}
		
		return repEntity.ok(newZjLzehSafetyInspectionManagementTroubleLevelList);
	}

	@Override
	public ResponseEntity getZjLzehDataCenterDynamicInfo(ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagement) {
		String otherUrl = Apih5Properties.getWebUrl()+"otherApiGetNewestFinishedData";
		String otherReuslt = com.apih5.framework.utils.HttpUtil.sendPostJson(otherUrl, "");
		JSONObject otherJSONObject = new JSONObject(otherReuslt);
		JSONObject dataJSONObject = otherJSONObject.getJSONObject("data");
//		JSONObject dataJSONObject = new JSONObject();
		ZjLzehUtil zjLzehUtil = new ZjLzehUtil();
		JSONObject jsonObject = zjLzehUtil.getLzehOtherSessionId();
		if(jsonObject.getBool("success")) {
		    String url = ZjLzehUtil.OTHER_URL + "/a/progress/progressPjLz2h/getDayProgressRealTimeData?page=1&limit=10&projectId=" + ZjLzehUtil.PROJECT_ID;
		    Map<String, String> headersMap = Maps.newHashMap();
		    headersMap.put("cookie", "jeesite.session.id=" + jsonObject.getStr("data"));
		    String reuslt = HttpUtil.createGet(url).addHeaders(headersMap).execute().body();
		    JSONObject reusltJSONObjectDayProgress = new JSONObject(reuslt);
		    if(StrUtil.equals("0", reusltJSONObjectDayProgress.getStr("rtnFlag"))) {
		        JSONArray dataJSONArray = new JSONArray(); 
		        JSONArray jsonArray = new JSONArray(reusltJSONObjectDayProgress.getJSONObject("rtnObj").getJSONArray("mapList"));
		        if(jsonArray != null){
		            int i = 0;
		            for (Iterator<Object> iterator = jsonArray.iterator(); iterator.hasNext();) {
		                if(i<10) {
		                    JSONObject jsonObjectDayProgress = (JSONObject)iterator.next();
		                    // 重新整理数据
		                    JSONObject dataJSONObjectDayProgress = new JSONObject();
		                    dataJSONObjectDayProgress.set("levelNameAll", jsonObjectDayProgress.getStr("fullName"));
		                    DateTime dateTime =  new DateTime(jsonObjectDayProgress.getStr("busdate"), DatePattern.NORM_DATE_PATTERN);
		                    dataJSONObjectDayProgress.set("finishTime", dateTime);
		                    dataJSONObjectDayProgress.set("modifyUserName", jsonObjectDayProgress.getStr("preparedBy"));
		                    // 附件获取
		                    String fileUrl = ZjLzehUtil.OTHER_URL + "/a/sys/annex/getListData?busiType=progressPjLz2h&busiId=" + jsonObjectDayProgress.getStr("id");
		        		    Map<String, String> headersMapFile = Maps.newHashMap();
		        		    headersMapFile.put("cookie", "jeesite.session.id=" + jsonObject.getStr("data"));
		        		    String reusltFile = HttpUtil.createGet(fileUrl).addHeaders(headersMapFile).execute().body();
		        		    JSONObject reusltJSONObjectDayProgressFile = new JSONObject(reusltFile);
		        		    JSONArray jsonArrayFile = new JSONArray(reusltJSONObjectDayProgressFile.getJSONObject("rtnObj").getJSONArray("annexList"));
		        		    JSONArray jsonArrayImg = new JSONArray(); 
		                    for(int j = 0; j < jsonArrayFile.size(); j++) {
		                    	JSONObject jsonObjectImg = new JSONObject();
		                    	JSONObject jsonObjectFile = jsonArrayFile.getJSONObject(j);
		                    	String newFileUrl = jsonObjectFile.getStr("fileUrl");
		                    	jsonObjectImg.set("url",  ZjLzehUtil.OTHER_URL + newFileUrl);
		                    	jsonArrayImg.set(jsonObjectImg);
		                    }
		                    dataJSONObjectDayProgress.set("gxAttachmentList", jsonArrayImg);
		                    dataJSONArray.add(dataJSONObjectDayProgress);
		                } else {
		                    break;
		                }
		                i++;
		            } 
		        }
		        dataJSONObject.set("processList", dataJSONArray);
		    }
		}
		
//		JSONArray JSONArray = jsonArraySort(zjWoaDocWorkEntityList);

		return repEntity.ok(dataJSONObject);
	}

	@Override
	public ResponseEntity exportZjLzehQualityRectificatReply(ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagement) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		// excel模板路径
		String srcDocPath = com.apih5.framework.utils.HttpUtil.getTemplate("zj-lzeh") + "质量整改回复.docx";
		// excel导出路径
		String path = com.apih5.framework.utils.HttpUtil.getExportTemplate("zj-lzeh");
		File file = new File(path);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
		
		zjLzehSafetyInspectionManagement.setTroubleTitle(zjLzehSafetyInspectionManagement.getTroubleTitle().replaceAll("/", "-"));
		
		String desDocPath = path + zjLzehSafetyInspectionManagement.getTroubleTitle() + "质量整改回复.docx";
		
		Map<String, Object> map = new HashMap<>();
        map.put("projectName", "泸州市二环路高新区AB段工程项目");
        String sequence = zjLzehSafetyInspectionManagement.getTroubleId() + "-" + DateUtil.format(new Date(), "yyyyMMdd");
        // L-20210507-顺序号
        int expenseNo = sequenceService.getSequenceNextval(sequence);
        if (expenseNo == 0) {
        	map.put("troubleNo", "L-" + DateUtil.format(new Date(), "yyyyMMdd") + "-01");
        	SysSequence sysSequence = new SysSequence();
        	sysSequence.setSeqName(sequence);
        	sysSequence.setCurrentVal(1);
        	sysSequence.setIncrementVal(1);
        	sequenceService.saveSysSequence(sysSequence);
		} else {
			map.put("troubleNo", "L-" + DateUtil.format(new Date(), "yyyyMMdd") + "-" + String.format("%02d", expenseNo));
		}
        String rectification = zjLzehSafetyInspectionManagement.getOpinionField2();
        String reviewComment = zjLzehSafetyInspectionManagement.getOpinionField3();
        
        map.put("rectification", rectification.substring(0, rectification.indexOf("<br/>") == -1 ? rectification.length() : rectification.indexOf("<br/>")));
        map.put("reviewComment", reviewComment.substring(0, reviewComment.indexOf("<br/>") == -1 ? reviewComment.length() : reviewComment.indexOf("<br/>")));
        
        XWPFTemplate template = XWPFTemplate.compile(srcDocPath).render(map);
    
        FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(desDocPath));
			template.write(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.flush();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
	        try {
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	        try {
				template.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String exportPath = com.apih5.framework.utils.HttpUtil.getExportTemplateWeb(request, "zj-lzeh") + zjLzehSafetyInspectionManagement.getTroubleTitle() + "质量整改回复.docx";
		return repEntity.ok(exportPath);	
	}
	
	@Override
	public ResponseEntity exportZjLzehQualityRectificatSheet(ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagement) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		// excel模板路径
		String srcDocPath = com.apih5.framework.utils.HttpUtil.getTemplate("zj-lzeh") + "质量整改单.docx";
		// excel导出路径
		String path = com.apih5.framework.utils.HttpUtil.getExportTemplate("zj-lzeh");
		File file = new File(path);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
		
		zjLzehSafetyInspectionManagement.setTroubleTitle(zjLzehSafetyInspectionManagement.getTroubleTitle().replaceAll("/", "-"));
		
		String desDocPath = path + zjLzehSafetyInspectionManagement.getTroubleTitle() + "质量整改单.docx";
		
		Map<String, Object> map = new HashMap<>();
        map.put("troubleContent", zjLzehSafetyInspectionManagement.getTroubleContent());
        map.put("troubleRequire", zjLzehSafetyInspectionManagement.getTroubleRequire());
        
        String sequence = zjLzehSafetyInspectionManagement.getTroubleId() + "-" + DateUtil.format(new Date(), "yyyyMMdd");
        // L-20210507-顺序号
        int expenseNo = sequenceService.getSequenceNextval(sequence);
        if (expenseNo == 0) {
        	map.put("troubleNo", "L-" + DateUtil.format(new Date(), "yyyyMMdd") + "-01");
        	SysSequence sysSequence = new SysSequence();
        	sysSequence.setSeqName(sequence);
        	sysSequence.setCurrentVal(1);
        	sysSequence.setIncrementVal(1);
        	sequenceService.saveSysSequence(sysSequence);
		} else {
			map.put("troubleNo", "L-" + DateUtil.format(new Date(), "yyyyMMdd") + "-" + String.format("%02d", expenseNo));
		}
        // 读取本地磁盘图片
        List<PictureRenderData> weChatPictures = new ArrayList<>();
        List<ZjLzehFile> fileList = zjLzehSafetyInspectionManagement.getFileList();
        if (fileList != null && fileList.size() > 0) {
        	fileList.forEach(addFile -> {
        		weChatPictures.add(new PictureRenderData(130, 160, Apih5Properties.getFilePath() + addFile.getRelativeUrl()));
        	});
        	List<PictureBean> pictureBeans = new ArrayList<>();
        	PictureBean testBean = null;
        	for (int i = 0; i < weChatPictures.size(); i++) {
				if (i % 4 == 0) {
					testBean = new PictureBean();
					testBean.setImage1(weChatPictures.get(i));
				} else if (i % 4 == 1) {
					testBean.setImage2(weChatPictures.get(i));
				} else if (i % 4 == 2) {
					testBean.setImage3(weChatPictures.get(i));
				} else if (i % 4 == 3) {
					testBean.setImage4(weChatPictures.get(i));
				}
				
				if ((i != weChatPictures.size() - 1 && i % 4 == 3) || i == weChatPictures.size() - 1) {
					pictureBeans.add(testBean);
				}
			}
            DocxRenderData segment = new DocxRenderData(new File(com.apih5.framework.utils.HttpUtil.getTemplate("zj-lzeh") + "图片模板.docx"), pictureBeans);
            map.put("图片模板", segment);
		}
        
        XWPFTemplate template = XWPFTemplate.compile(srcDocPath).render(map);
    
        FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(desDocPath));
			template.write(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.flush();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
	        try {
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	        try {
				template.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String exportPath = com.apih5.framework.utils.HttpUtil.getExportTemplateWeb(request, "zj-lzeh") + zjLzehSafetyInspectionManagement.getTroubleTitle() + "质量整改单.docx";
		return repEntity.ok(exportPath);	
	}
	
	@Override
	public ResponseEntity exportZjLzehHiddenDangerRectificatReply(ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagement) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		// excel模板路径
		String srcDocPath = com.apih5.framework.utils.HttpUtil.getTemplate("zj-lzeh") + "隐患整改回复单.docx";
		// excel导出路径
		String path = com.apih5.framework.utils.HttpUtil.getExportTemplate("zj-lzeh");
		File file = new File(path);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
		
		zjLzehSafetyInspectionManagement.setDangerTitle(zjLzehSafetyInspectionManagement.getDangerTitle().replaceAll("/", "-"));
		
		String desDocPath = path + zjLzehSafetyInspectionManagement.getDangerTitle() + "隐患整改回复单.docx";
		
		Map<String, Object> map = new HashMap<>();
        map.put("year", DateUtil.year(zjLzehSafetyInspectionManagement.getModifyTime()));
        map.put("month", DateUtil.month(zjLzehSafetyInspectionManagement.getModifyTime()) + 1);
        map.put("day", DateUtil.dayOfMonth(zjLzehSafetyInspectionManagement.getModifyTime()));
        map.put("dangerTitle", zjLzehSafetyInspectionManagement.getDangerRequire() + zjLzehSafetyInspectionManagement.getDangerContent());
        
        // 读取本地磁盘图片
        List<PictureRenderData> weChatPictures = new ArrayList<>();
        List<ZjLzehFile> fileList = zjLzehSafetyInspectionManagement.getFileList();
        if (fileList != null && fileList.size() > 0) {
        	fileList.forEach(addFile -> {
        		weChatPictures.add(new PictureRenderData(130, 160, Apih5Properties.getFilePath() + addFile.getRelativeUrl()));
        	});
        	List<PictureBean> pictureBeans = new ArrayList<>();
        	PictureBean testBean = null;
        	for (int i = 0; i < weChatPictures.size(); i++) {
				if (i % 4 == 0) {
					testBean = new PictureBean();
					testBean.setImage1(weChatPictures.get(i));
				} else if (i % 4 == 1) {
					testBean.setImage2(weChatPictures.get(i));
				} else if (i % 4 == 2) {
					testBean.setImage3(weChatPictures.get(i));
				} else if (i % 4 == 3) {
					testBean.setImage4(weChatPictures.get(i));
				}
				
				if ((i != weChatPictures.size() - 1 && i % 4 == 3) || i == weChatPictures.size() - 1) {
					pictureBeans.add(testBean);
				}
			}
            DocxRenderData segment = new DocxRenderData(new File(com.apih5.framework.utils.HttpUtil.getTemplate("zj-lzeh") + "图片模板.docx"), pictureBeans);
            map.put("图片模板", segment);
		}
        
        XWPFTemplate template = XWPFTemplate.compile(srcDocPath).render(map);
    
        FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(desDocPath));
			template.write(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.flush();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
	        try {
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	        try {
				template.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String exportPath = com.apih5.framework.utils.HttpUtil.getExportTemplateWeb(request, "zj-lzeh") + zjLzehSafetyInspectionManagement.getDangerTitle() + "隐患整改回复单.docx";
		return repEntity.ok(exportPath);	
	}
	
	@Override
	public ResponseEntity exportZjLzehHiddenDangerRectificatInstructBook(ZjLzehSafetyInspectionManagement zjLzehSafetyInspectionManagement) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		// excel模板路径
		String srcDocPath = com.apih5.framework.utils.HttpUtil.getTemplate("zj-lzeh") + "隐患整改指令书.docx";
		// excel导出路径
		String path = com.apih5.framework.utils.HttpUtil.getExportTemplate("zj-lzeh");
		File file = new File(path);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		}
		
		zjLzehSafetyInspectionManagement.setDangerTitle(zjLzehSafetyInspectionManagement.getDangerTitle().replaceAll("/", "-"));
		
		String desDocPath = path + zjLzehSafetyInspectionManagement.getDangerTitle() + "隐患整改指令书.docx";
		
		Map<String, Object> map = new HashMap<>();
        map.put("dangerContent", zjLzehSafetyInspectionManagement.getDangerContent());
        map.put("dangerRequire", zjLzehSafetyInspectionManagement.getDangerRequire());
        map.put("dangerTitle", zjLzehSafetyInspectionManagement.getDangerTitle());
        
        // 读取本地磁盘图片
        List<PictureRenderData> weChatPictures = new ArrayList<>();
        List<ZjLzehFile> fileList = zjLzehSafetyInspectionManagement.getFileList();
        if (fileList != null && fileList.size() > 0) {
        	fileList.forEach(addFile -> {
        		weChatPictures.add(new PictureRenderData(130, 160, Apih5Properties.getFilePath() + addFile.getRelativeUrl()));
        	});
        	List<PictureBean> pictureBeans = new ArrayList<>();
        	PictureBean testBean = null;
        	for (int i = 0; i < weChatPictures.size(); i++) {
				if (i % 4 == 0) {
					testBean = new PictureBean();
					testBean.setImage1(weChatPictures.get(i));
				} else if (i % 4 == 1) {
					testBean.setImage2(weChatPictures.get(i));
				} else if (i % 4 == 2) {
					testBean.setImage3(weChatPictures.get(i));
				} else if (i % 4 == 3) {
					testBean.setImage4(weChatPictures.get(i));
				}
				
				if ((i != weChatPictures.size() - 1 && i % 4 == 3) || i == weChatPictures.size() - 1) {
					pictureBeans.add(testBean);
				}
			}
            DocxRenderData segment = new DocxRenderData(new File(com.apih5.framework.utils.HttpUtil.getTemplate("zj-lzeh") + "图片模板.docx"), pictureBeans);
            map.put("图片模板", segment);
		}
        
        XWPFTemplate template = XWPFTemplate.compile(srcDocPath).render(map);
    
        FileOutputStream out = null;
		try {
			out = new FileOutputStream(new File(desDocPath));
			template.write(out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.flush();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
	        try {
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	        try {
				template.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String exportPath = com.apih5.framework.utils.HttpUtil.getExportTemplateWeb(request, "zj-lzeh") + zjLzehSafetyInspectionManagement.getDangerTitle() + "隐患整改指令书.docx";
		return repEntity.ok(exportPath);	
	}
	
//	   private static JSONArray jsonArraySort(List<ZjWoaDocWorkEntity> zjWoaDocWorkEntityList) {
//	        JSONArray jsonArr = JSONUtil.parseArray(zjWoaDocWorkEntityList);
//	        JSONArray sortedJsonArray = new JSONArray();
////	      List<JSONObject> jsonValues = new ArrayList<JSONObject>();
//	        List<JSONObject> jsonValues = Lists.newArrayList();
//	        for (int i = 0; i < jsonArr.size(); i++) {
//	            jsonValues.add(jsonArr.getJSONObject(i));
//	        }
//	        Collections.sort(jsonValues, new Comparator<JSONObject>() {
//	            // You can change "Name" with "ID" if you want to sort by ID
//	            private static final String KEY_NAME = "startTime";
//
//	            @Override
//	            public int compare(JSONObject a, JSONObject b) {
//	                String valA = new String();
//	                String valB = new String();
//	                try {
//	                    // 这里是a、b需要处理的业务，需要根据你的规则进行修改。
//	                    String aStr = a.getStr(KEY_NAME);
//	                    valA = aStr.replaceAll("-", "");
//	                    String bStr = b.getStr(KEY_NAME);
//	                    valB = bStr.replaceAll("-", "");
//	                } catch (Exception e) {
//	                    // do something
//	                }
//	                return -valA.compareTo(valB);
//	                // if you want to change the sort order, simply use the following:
//	                // return -valA.compareTo(valB);
//	            }
//	        });
//	        int size = jsonArr.size() > 10? 10 : jsonArr.size();
//	        for (int i = 0; i < size; i++) {
//	            sortedJsonArray.add(jsonValues.get(i));
//	        }
//	        return sortedJsonArray;
//	    }
}
