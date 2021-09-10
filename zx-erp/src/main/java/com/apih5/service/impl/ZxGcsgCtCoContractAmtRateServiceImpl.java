package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxGcsgCtCoContractAmtRateMapper;
import com.apih5.mybatis.pojo.ZxGcsgCtCoContractAmtRate;
import com.apih5.service.ZxGcsgCtCoContractAmtRateService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxGcsgCtCoContractAmtRateService")
public class ZxGcsgCtCoContractAmtRateServiceImpl implements ZxGcsgCtCoContractAmtRateService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxGcsgCtCoContractAmtRateMapper zxGcsgCtCoContractAmtRateMapper;

    @Override
    public ResponseEntity getZxGcsgCtCoContractAmtRateListByCondition(ZxGcsgCtCoContractAmtRate zxGcsgCtCoContractAmtRate) {
        if (zxGcsgCtCoContractAmtRate == null) {
            zxGcsgCtCoContractAmtRate = new ZxGcsgCtCoContractAmtRate();
        }
        // 分页查询
        PageHelper.startPage(zxGcsgCtCoContractAmtRate.getPage(),zxGcsgCtCoContractAmtRate.getLimit());
        // 获取数据
        List<ZxGcsgCtCoContractAmtRate> zxGcsgCtCoContractAmtRateList = zxGcsgCtCoContractAmtRateMapper.selectByZxGcsgCtCoContractAmtRateList(zxGcsgCtCoContractAmtRate);
        // 得到分页信息
        PageInfo<ZxGcsgCtCoContractAmtRate> p = new PageInfo<>(zxGcsgCtCoContractAmtRateList);

        return repEntity.okList(zxGcsgCtCoContractAmtRateList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxGcsgCtCoContractAmtRateDetail(ZxGcsgCtCoContractAmtRate zxGcsgCtCoContractAmtRate) {
        if (zxGcsgCtCoContractAmtRate == null) {
            zxGcsgCtCoContractAmtRate = new ZxGcsgCtCoContractAmtRate();
        }
        // 获取数据
        ZxGcsgCtCoContractAmtRate dbZxGcsgCtCoContractAmtRate = zxGcsgCtCoContractAmtRateMapper.selectByPrimaryKey(zxGcsgCtCoContractAmtRate.getCtCoContractAmtRateId());
        // 数据存在
        if (dbZxGcsgCtCoContractAmtRate != null) {
            return repEntity.ok(dbZxGcsgCtCoContractAmtRate);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxGcsgCtCoContractAmtRate(ZxGcsgCtCoContractAmtRate zxGcsgCtCoContractAmtRate) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxGcsgCtCoContractAmtRate.setCtCoContractAmtRateId(UuidUtil.generate());
        zxGcsgCtCoContractAmtRate.setCreateUserInfo(userKey, realName);
        int flag = zxGcsgCtCoContractAmtRateMapper.insert(zxGcsgCtCoContractAmtRate);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxGcsgCtCoContractAmtRate);
        }
    }

    @Override
    public ResponseEntity updateZxGcsgCtCoContractAmtRate(ZxGcsgCtCoContractAmtRate zxGcsgCtCoContractAmtRate) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxGcsgCtCoContractAmtRate dbZxGcsgCtCoContractAmtRate = zxGcsgCtCoContractAmtRateMapper.selectByPrimaryKey(zxGcsgCtCoContractAmtRate.getCtCoContractAmtRateId());
        if (dbZxGcsgCtCoContractAmtRate != null && StrUtil.isNotEmpty(dbZxGcsgCtCoContractAmtRate.getCtCoContractAmtRateId())) {
           // 项目ID
           dbZxGcsgCtCoContractAmtRate.setOrgID(zxGcsgCtCoContractAmtRate.getOrgID());
           // 合同ID(contractID)
           dbZxGcsgCtCoContractAmtRate.setCtContractId(zxGcsgCtCoContractAmtRate.getCtContractId());
           // 保证金编号
           dbZxGcsgCtCoContractAmtRate.setStatisticsNo(zxGcsgCtCoContractAmtRate.getStatisticsNo());
           // 保证金
           dbZxGcsgCtCoContractAmtRate.setStatisticsName(zxGcsgCtCoContractAmtRate.getStatisticsName());
           // 扣除比例(%)
           dbZxGcsgCtCoContractAmtRate.setStatisticsRate(zxGcsgCtCoContractAmtRate.getStatisticsRate());
           // 返还条件
           dbZxGcsgCtCoContractAmtRate.setBackCondition(zxGcsgCtCoContractAmtRate.getBackCondition());
           // 期限
           dbZxGcsgCtCoContractAmtRate.setTimeLimit(zxGcsgCtCoContractAmtRate.getTimeLimit());
           // 是否允许删除
           dbZxGcsgCtCoContractAmtRate.setAllowDelete(zxGcsgCtCoContractAmtRate.getAllowDelete());
           // 最后编辑时间
           dbZxGcsgCtCoContractAmtRate.setEditTime(zxGcsgCtCoContractAmtRate.getEditTime());
           // 所属公司ID
           dbZxGcsgCtCoContractAmtRate.setComID(zxGcsgCtCoContractAmtRate.getComID());
           // 所属公司名称
           dbZxGcsgCtCoContractAmtRate.setComName(zxGcsgCtCoContractAmtRate.getComName());
           // 所属公司排序
           dbZxGcsgCtCoContractAmtRate.setComOrders(zxGcsgCtCoContractAmtRate.getComOrders());
           // 备注
           dbZxGcsgCtCoContractAmtRate.setRemarks(zxGcsgCtCoContractAmtRate.getRemarks());
           // 排序
           dbZxGcsgCtCoContractAmtRate.setSort(zxGcsgCtCoContractAmtRate.getSort());
           // 共通
           dbZxGcsgCtCoContractAmtRate.setModifyUserInfo(userKey, realName);
           flag = zxGcsgCtCoContractAmtRateMapper.updateByPrimaryKey(dbZxGcsgCtCoContractAmtRate);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxGcsgCtCoContractAmtRate);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxGcsgCtCoContractAmtRate(List<ZxGcsgCtCoContractAmtRate> zxGcsgCtCoContractAmtRateList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxGcsgCtCoContractAmtRateList != null && zxGcsgCtCoContractAmtRateList.size() > 0) {
           ZxGcsgCtCoContractAmtRate zxGcsgCtCoContractAmtRate = new ZxGcsgCtCoContractAmtRate();
           zxGcsgCtCoContractAmtRate.setModifyUserInfo(userKey, realName);
           flag = zxGcsgCtCoContractAmtRateMapper.batchDeleteUpdateZxGcsgCtCoContractAmtRate(zxGcsgCtCoContractAmtRateList, zxGcsgCtCoContractAmtRate);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxGcsgCtCoContractAmtRateList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
