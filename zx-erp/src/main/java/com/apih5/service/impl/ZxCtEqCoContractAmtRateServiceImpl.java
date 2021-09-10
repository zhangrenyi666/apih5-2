package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtEqCoContractAmtRateMapper;
import com.apih5.mybatis.dao.ZxSaEquipSettleAuditMapper;
import com.apih5.mybatis.pojo.ZxCtEqCoContractAmtRate;
import com.apih5.mybatis.pojo.ZxSaEquipSettleAudit;
import com.apih5.service.ZxCtEqCoContractAmtRateService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtEqCoContractAmtRateService")
public class ZxCtEqCoContractAmtRateServiceImpl implements ZxCtEqCoContractAmtRateService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtEqCoContractAmtRateMapper zxCtEqCoContractAmtRateMapper;

    @Autowired(required = true)
    private ZxSaEquipSettleAuditMapper zxSaEquipSettleAuditMapper;

    @Override
    public ResponseEntity getZxCtEqCoContractAmtRateListByCondition(ZxCtEqCoContractAmtRate zxCtEqCoContractAmtRate) {
        if (zxCtEqCoContractAmtRate == null) {
            zxCtEqCoContractAmtRate = new ZxCtEqCoContractAmtRate();
        }
        // 分页查询
        PageHelper.startPage(zxCtEqCoContractAmtRate.getPage(),zxCtEqCoContractAmtRate.getLimit());
        // 获取数据
        List<ZxCtEqCoContractAmtRate> zxCtEqCoContractAmtRateList = zxCtEqCoContractAmtRateMapper.selectByZxCtEqCoContractAmtRateList(zxCtEqCoContractAmtRate);
        // 得到分页信息
        PageInfo<ZxCtEqCoContractAmtRate> p = new PageInfo<>(zxCtEqCoContractAmtRateList);

        return repEntity.okList(zxCtEqCoContractAmtRateList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtEqCoContractAmtRateDetail(ZxCtEqCoContractAmtRate zxCtEqCoContractAmtRate) {
        if (zxCtEqCoContractAmtRate == null) {
            zxCtEqCoContractAmtRate = new ZxCtEqCoContractAmtRate();
        }
        // 获取数据
        ZxCtEqCoContractAmtRate dbZxCtEqCoContractAmtRate = zxCtEqCoContractAmtRateMapper.selectByPrimaryKey(zxCtEqCoContractAmtRate.getZxCtEqCoContractAmtRateId());
        // 数据存在
        if (dbZxCtEqCoContractAmtRate != null) {
            return repEntity.ok(dbZxCtEqCoContractAmtRate);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtEqCoContractAmtRate(ZxCtEqCoContractAmtRate zxCtEqCoContractAmtRate) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        //判断保证金是否被引用
        ZxSaEquipSettleAudit audit = new ZxSaEquipSettleAudit();
        audit.setContractID(zxCtEqCoContractAmtRate.getContractID());
        List<ZxSaEquipSettleAudit> auditList = zxSaEquipSettleAuditMapper.selectByZxSaEquipSettleAuditList(audit);
        if(auditList != null && auditList.size() >0) {
        	return repEntity.layerMessage("no", "该合同下的保证金已经被结算引用，不允许新增");	
        }
        zxCtEqCoContractAmtRate.setZxCtEqCoContractAmtRateId(UuidUtil.generate());
        zxCtEqCoContractAmtRate.setCreateUserInfo(userKey, realName);
        int flag = zxCtEqCoContractAmtRateMapper.insert(zxCtEqCoContractAmtRate);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtEqCoContractAmtRate);
        }
    }

    @Override
    public ResponseEntity updateZxCtEqCoContractAmtRate(ZxCtEqCoContractAmtRate zxCtEqCoContractAmtRate) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtEqCoContractAmtRate dbZxCtEqCoContractAmtRate = zxCtEqCoContractAmtRateMapper.selectByPrimaryKey(zxCtEqCoContractAmtRate.getZxCtEqCoContractAmtRateId());
        if (dbZxCtEqCoContractAmtRate != null && StrUtil.isNotEmpty(dbZxCtEqCoContractAmtRate.getZxCtEqCoContractAmtRateId())) {
        	//判断保证金是否被引用
            ZxSaEquipSettleAudit audit = new ZxSaEquipSettleAudit();
            audit.setContractID(dbZxCtEqCoContractAmtRate.getContractID());
            List<ZxSaEquipSettleAudit> auditList = zxSaEquipSettleAuditMapper.selectByZxSaEquipSettleAuditList(audit);
            if(auditList != null && auditList.size() >0) {
            	return repEntity.layerMessage("no", "该合同下的保证金已经被结算引用，不允许修改");	
            }
        	// 合同id
           dbZxCtEqCoContractAmtRate.setContractID(zxCtEqCoContractAmtRate.getContractID());
           // 保证金编号
           dbZxCtEqCoContractAmtRate.setStatisticsNo(zxCtEqCoContractAmtRate.getStatisticsNo());
           // 项目id
           dbZxCtEqCoContractAmtRate.setOrgID(zxCtEqCoContractAmtRate.getOrgID());
           // 保证金
           dbZxCtEqCoContractAmtRate.setStatisticsName(zxCtEqCoContractAmtRate.getStatisticsName());
           // 扣除比例(%)
           dbZxCtEqCoContractAmtRate.setStatisticsRate(zxCtEqCoContractAmtRate.getStatisticsRate());
           // 返还条件
           dbZxCtEqCoContractAmtRate.setBackCondition(zxCtEqCoContractAmtRate.getBackCondition());
           // 期限
           dbZxCtEqCoContractAmtRate.setTimeLimit(zxCtEqCoContractAmtRate.getTimeLimit());
           // 备注
           dbZxCtEqCoContractAmtRate.setRemark(zxCtEqCoContractAmtRate.getRemark());
           // 是否允许删除
           dbZxCtEqCoContractAmtRate.setAllowDelete(zxCtEqCoContractAmtRate.getAllowDelete());
           // 公司名称
           dbZxCtEqCoContractAmtRate.setComName(zxCtEqCoContractAmtRate.getComName());
           // 公司排序
           dbZxCtEqCoContractAmtRate.setComOrders(zxCtEqCoContractAmtRate.getComOrders());
           // 共通
           dbZxCtEqCoContractAmtRate.setModifyUserInfo(userKey, realName);
           flag = zxCtEqCoContractAmtRateMapper.updateByPrimaryKey(dbZxCtEqCoContractAmtRate);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtEqCoContractAmtRate);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtEqCoContractAmtRate(List<ZxCtEqCoContractAmtRate> zxCtEqCoContractAmtRateList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtEqCoContractAmtRateList != null && zxCtEqCoContractAmtRateList.size() > 0) {
        	for (ZxCtEqCoContractAmtRate zxCtEqCoContractAmtRate : zxCtEqCoContractAmtRateList) {
        		//判断保证金是否被引用
        		ZxSaEquipSettleAudit audit = new ZxSaEquipSettleAudit();
        		audit.setContractID(zxCtEqCoContractAmtRate.getContractID());
        		List<ZxSaEquipSettleAudit> auditList = zxSaEquipSettleAuditMapper.selectByZxSaEquipSettleAuditList(audit);
        		if(auditList != null && auditList.size() >0) {
        			return repEntity.layerMessage("no", "该合同下的保证金已经被结算引用，不允许删除");	
        		}

        	}
        	ZxCtEqCoContractAmtRate zxCtEqCoContractAmtRate = new ZxCtEqCoContractAmtRate();
        	zxCtEqCoContractAmtRate.setModifyUserInfo(userKey, realName);
        	flag = zxCtEqCoContractAmtRateMapper.batchDeleteUpdateZxCtEqCoContractAmtRate(zxCtEqCoContractAmtRateList, zxCtEqCoContractAmtRate);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtEqCoContractAmtRateList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
