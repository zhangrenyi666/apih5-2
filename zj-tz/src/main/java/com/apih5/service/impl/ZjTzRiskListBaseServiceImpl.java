package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.sysdb.service.BaseCodeService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzRiskListBaseMapper;
import com.apih5.mybatis.pojo.ZjTzRiskListBase;
import com.apih5.service.ZjTzRiskListBaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzRiskListBaseService")
public class ZjTzRiskListBaseServiceImpl implements ZjTzRiskListBaseService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzRiskListBaseMapper zjTzRiskListBaseMapper;
    
    @Autowired(required = true)
    private BaseCodeService baseCodeService;

    @Override
    public ResponseEntity getZjTzRiskListBaseListByCondition(ZjTzRiskListBase zjTzRiskListBase) {
        if (zjTzRiskListBase == null) {
            zjTzRiskListBase = new ZjTzRiskListBase();
        }
        int mainNumber = 1;
        String mainNumberStr = "一";
        // 分页查询
        PageHelper.startPage(zjTzRiskListBase.getPage(),zjTzRiskListBase.getLimit());
        zjTzRiskListBase.setGroupByFlagTypeId("分组啊");
        // 获取数据
        List<ZjTzRiskListBase> zjTzRiskListBaseList = zjTzRiskListBaseMapper.selectByZjTzRiskListBaseList(zjTzRiskListBase);
        for (ZjTzRiskListBase mainBase : zjTzRiskListBaseList) {
        	if(mainNumber == 1) {
        		mainNumberStr = "一";
        	}else if(mainNumber == 2) {
        		mainNumberStr = "二";
        	}else if(mainNumber == 3) {
        		mainNumberStr = "三";
        	}else if(mainNumber == 4) {
        		mainNumberStr = "四";
        	}else if(mainNumber == 5) {
        		mainNumberStr = "五";
        	}else if(mainNumber == 6) {
        		mainNumberStr = "六";
        	}else if(mainNumber == 7) {
        		mainNumberStr = "七";
        	}else if(mainNumber == 8) {
        		mainNumberStr = "八";
        	}
        	ZjTzRiskListBase base = new ZjTzRiskListBase();
        	base.setTypeId(mainBase.getTypeId());
        	List<ZjTzRiskListBase> baseList = zjTzRiskListBaseMapper.selectByZjTzRiskListBaseList(base);
        	int subNumber = 1;
        	for (ZjTzRiskListBase subBase : baseList) {
        		subBase.setNumber((subNumber++)+" .    "+subBase.getRiskName());
        		subBase.setMainFlag("sub");
			}
        	mainBase.setChildren(baseList);
        	mainBase.setNumber(mainNumberStr+"、"+mainBase.getTypeName());
        	mainBase.setRiskListBaseId(mainNumberStr+"、"+mainBase.getTypeName());
        	mainBase.setManagLever("");
        	mainBase.setApplicableItemType("");
        	mainBase.setRemarks("");
        	mainBase.setLockFlag("");
        	mainBase.setMainFlag("main");
        	mainNumber ++;
		}
        // 得到分页信息
        PageInfo<ZjTzRiskListBase> p = new PageInfo<>(zjTzRiskListBaseList);

        return repEntity.okList(zjTzRiskListBaseList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzRiskListBaseDetails(ZjTzRiskListBase zjTzRiskListBase) {
        if (zjTzRiskListBase == null) {
            zjTzRiskListBase = new ZjTzRiskListBase();
        }
        // 获取数据
        ZjTzRiskListBase dbZjTzRiskListBase = zjTzRiskListBaseMapper.selectByPrimaryKey(zjTzRiskListBase.getRiskListBaseId());
        // 数据存在
        if (dbZjTzRiskListBase != null) {
            return repEntity.ok(dbZjTzRiskListBase);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzRiskListBase(ZjTzRiskListBase zjTzRiskListBase) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
       
        // 所属风险类别
        if (StrUtil.isNotEmpty(zjTzRiskListBase.getTypeId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("suoSuFengXianLeiBie", zjTzRiskListBase.getTypeId());
        	zjTzRiskListBase.setTypeName(openBankName);
        }
        zjTzRiskListBase.setRiskListBaseId(UuidUtil.generate());
        zjTzRiskListBase.setLockFlag("0");//未锁定
        zjTzRiskListBase.setCreateUserInfo(userKey, realName);
        int flag = zjTzRiskListBaseMapper.insert(zjTzRiskListBase);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzRiskListBase);
        }
    }

    @Override
    public ResponseEntity updateZjTzRiskListBase(ZjTzRiskListBase zjTzRiskListBase) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzRiskListBase dbzjTzRiskListBase = zjTzRiskListBaseMapper.selectByPrimaryKey(zjTzRiskListBase.getRiskListBaseId());
        if (dbzjTzRiskListBase != null && StrUtil.isNotEmpty(dbzjTzRiskListBase.getRiskListBaseId())) {
           // 所属风险类别id
           dbzjTzRiskListBase.setTypeId(zjTzRiskListBase.getTypeId());
           // 所属风险类别name
           if (StrUtil.isNotEmpty(zjTzRiskListBase.getTypeId())) {
           	String openBankName = baseCodeService.getBaseCodeItemName("suoSuFengXianLeiBie", zjTzRiskListBase.getTypeId());
           	dbzjTzRiskListBase.setTypeName(openBankName);
           }
           //风险名称
           dbzjTzRiskListBase.setRiskName(zjTzRiskListBase.getRiskName());
           // 管理层级
           dbzjTzRiskListBase.setManagLever(zjTzRiskListBase.getManagLever());
           // 适用项目类型
           dbzjTzRiskListBase.setApplicableItemType(zjTzRiskListBase.getApplicableItemType());
           // 备注
           dbzjTzRiskListBase.setRemarks(zjTzRiskListBase.getRemarks());
           // 共通
           dbzjTzRiskListBase.setModifyUserInfo(userKey, realName);
           flag = zjTzRiskListBaseMapper.updateByPrimaryKey(dbzjTzRiskListBase);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzRiskListBase);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzRiskListBase(List<ZjTzRiskListBase> zjTzRiskListBaseList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzRiskListBaseList != null && zjTzRiskListBaseList.size() > 0) {
           ZjTzRiskListBase zjTzRiskListBase = new ZjTzRiskListBase();
           zjTzRiskListBase.setModifyUserInfo(userKey, realName);
           flag = zjTzRiskListBaseMapper.batchDeleteUpdateZjTzRiskListBase(zjTzRiskListBaseList, zjTzRiskListBase);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzRiskListBaseList);
        }
    }

	@Override
	public ResponseEntity batchLockUpdateZjTzRiskListBase(List<ZjTzRiskListBase> zjTzRiskListBaseList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zjTzRiskListBaseList != null && zjTzRiskListBaseList.size() > 0) {
    		ZjTzRiskListBase zjTzRiskListBase = new ZjTzRiskListBase();
    		zjTzRiskListBase.setModifyUserInfo(userKey, realName);
    		flag = zjTzRiskListBaseMapper.batchLockUpdateZjTzRiskListBase(zjTzRiskListBaseList, zjTzRiskListBase);
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorUpdate();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zjTzRiskListBaseList);
    	}
	}

	@Override
	public ResponseEntity batchClearUpdateZjTzRiskListBase(List<ZjTzRiskListBase> zjTzRiskListBaseList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zjTzRiskListBaseList != null && zjTzRiskListBaseList.size() > 0) {
    		ZjTzRiskListBase zjTzRiskListBase = new ZjTzRiskListBase();
    		zjTzRiskListBase.setModifyUserInfo(userKey, realName);
    		flag = zjTzRiskListBaseMapper.batchClearUpdateZjTzRiskListBase(zjTzRiskListBaseList, zjTzRiskListBase);
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorUpdate();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zjTzRiskListBaseList);
    	}
	}
}
