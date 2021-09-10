package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkResMoveMonthMPMapper;
import com.apih5.mybatis.pojo.ZxSkResMoveMonthMP;
import com.apih5.service.ZxSkResMoveMonthMPService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkResMoveMonthMPService")
public class ZxSkResMoveMonthMPServiceImpl implements ZxSkResMoveMonthMPService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkResMoveMonthMPMapper zxSkResMoveMonthMPMapper;

    @Override
    public ResponseEntity getZxSkResMoveMonthMPListByCondition(ZxSkResMoveMonthMP zxSkResMoveMonthMP) {
        if (zxSkResMoveMonthMP == null) {
            zxSkResMoveMonthMP = new ZxSkResMoveMonthMP();
        }
        // 分页查询
        PageHelper.startPage(zxSkResMoveMonthMP.getPage(),zxSkResMoveMonthMP.getLimit());
        // 获取数据
        List<ZxSkResMoveMonthMP> zxSkResMoveMonthMPList = zxSkResMoveMonthMPMapper.selectByZxSkResMoveMonthMPList(zxSkResMoveMonthMP);
        // 得到分页信息
        PageInfo<ZxSkResMoveMonthMP> p = new PageInfo<>(zxSkResMoveMonthMPList);

        return repEntity.okList(zxSkResMoveMonthMPList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkResMoveMonthMPDetail(ZxSkResMoveMonthMP zxSkResMoveMonthMP) {
        if (zxSkResMoveMonthMP == null) {
            zxSkResMoveMonthMP = new ZxSkResMoveMonthMP();
        }
        // 获取数据
        ZxSkResMoveMonthMP dbZxSkResMoveMonthMP = zxSkResMoveMonthMPMapper.selectByPrimaryKey(zxSkResMoveMonthMP.getZxSkResMoveMonthMPId());
        // 数据存在
        if (dbZxSkResMoveMonthMP != null) {
            return repEntity.ok(dbZxSkResMoveMonthMP);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkResMoveMonthMP(ZxSkResMoveMonthMP zxSkResMoveMonthMP) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkResMoveMonthMP.setZxSkResMoveMonthMPId(UuidUtil.generate());
        zxSkResMoveMonthMP.setCreateUserInfo(userKey, realName);
        int flag = zxSkResMoveMonthMPMapper.insert(zxSkResMoveMonthMP);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkResMoveMonthMP);
        }
    }

    @Override
    public ResponseEntity updateZxSkResMoveMonthMP(ZxSkResMoveMonthMP zxSkResMoveMonthMP) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkResMoveMonthMP dbZxSkResMoveMonthMP = zxSkResMoveMonthMPMapper.selectByPrimaryKey(zxSkResMoveMonthMP.getZxSkResMoveMonthMPId());
        if (dbZxSkResMoveMonthMP != null && StrUtil.isNotEmpty(dbZxSkResMoveMonthMP.getZxSkResMoveMonthMPId())) {
           // 物资编码
           dbZxSkResMoveMonthMP.setResCode(zxSkResMoveMonthMP.getResCode());
           // 物资名称
           dbZxSkResMoveMonthMP.setResName(zxSkResMoveMonthMP.getResName());
           // 规格
           dbZxSkResMoveMonthMP.setSpec(zxSkResMoveMonthMP.getSpec());
           // 计量单位
           dbZxSkResMoveMonthMP.setUnit(zxSkResMoveMonthMP.getUnit());
           // 上月结存数量
           dbZxSkResMoveMonthMP.setStockQty(zxSkResMoveMonthMP.getStockQty());
           // 上月结存金额
           dbZxSkResMoveMonthMP.setStockAmt(zxSkResMoveMonthMP.getStockAmt());
           // 上月结存平均单价
           dbZxSkResMoveMonthMP.setStockPrice(zxSkResMoveMonthMP.getStockPrice());
           // 本月收入合计（数量）
           dbZxSkResMoveMonthMP.setInQty(zxSkResMoveMonthMP.getInQty());
           // 本月收入合计（平均价格）
           dbZxSkResMoveMonthMP.setInPrice(zxSkResMoveMonthMP.getInPrice());
           // 本月收入金额
           dbZxSkResMoveMonthMP.setInAmt(zxSkResMoveMonthMP.getInAmt());
           // 甲供
           dbZxSkResMoveMonthMP.setOrsQty(zxSkResMoveMonthMP.getOrsQty());
           // 其他
           dbZxSkResMoveMonthMP.setOtrQty(zxSkResMoveMonthMP.getOtrQty());
           // 自行采购
           dbZxSkResMoveMonthMP.setSerQty(zxSkResMoveMonthMP.getSerQty());
           // 预收
           dbZxSkResMoveMonthMP.setObuQty(zxSkResMoveMonthMP.getObuQty());
           // 甲控
           dbZxSkResMoveMonthMP.setOcsQty(zxSkResMoveMonthMP.getOcsQty());
           // 工程耗用数量
           dbZxSkResMoveMonthMP.setOswQty(zxSkResMoveMonthMP.getOswQty());
           // 工程耗用金额
           dbZxSkResMoveMonthMP.setOswAmt(zxSkResMoveMonthMP.getOswAmt());
           // 工程耗用平均单价
           dbZxSkResMoveMonthMP.setOswPrice(zxSkResMoveMonthMP.getOswPrice());
           // 调拨金额
           dbZxSkResMoveMonthMP.setOutAmt(zxSkResMoveMonthMP.getOutAmt());
           // 调拨平均单价
           dbZxSkResMoveMonthMP.setOtkPrice(zxSkResMoveMonthMP.getOtkPrice());
           // 调拨
           dbZxSkResMoveMonthMP.setOtkQty(zxSkResMoveMonthMP.getOtkQty());
           // 盈亏数量
           dbZxSkResMoveMonthMP.setVinQty(zxSkResMoveMonthMP.getVinQty());
           // 盈亏金额
           dbZxSkResMoveMonthMP.setVinAmt(zxSkResMoveMonthMP.getVinAmt());
           // 本月结存数量
           dbZxSkResMoveMonthMP.setThisQtys(zxSkResMoveMonthMP.getThisQtys());
           // 本月结存金额
           dbZxSkResMoveMonthMP.setThisAmts(zxSkResMoveMonthMP.getThisAmts());
           // 本月结存平均价格
           dbZxSkResMoveMonthMP.setThisProce(zxSkResMoveMonthMP.getThisProce());
           // 备注
           dbZxSkResMoveMonthMP.setRemarks(zxSkResMoveMonthMP.getRemarks());
           // 排序
           dbZxSkResMoveMonthMP.setSort(zxSkResMoveMonthMP.getSort());
           // 共通
           dbZxSkResMoveMonthMP.setModifyUserInfo(userKey, realName);
           flag = zxSkResMoveMonthMPMapper.updateByPrimaryKey(dbZxSkResMoveMonthMP);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkResMoveMonthMP);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkResMoveMonthMP(List<ZxSkResMoveMonthMP> zxSkResMoveMonthMPList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkResMoveMonthMPList != null && zxSkResMoveMonthMPList.size() > 0) {
           ZxSkResMoveMonthMP zxSkResMoveMonthMP = new ZxSkResMoveMonthMP();
           zxSkResMoveMonthMP.setModifyUserInfo(userKey, realName);
           flag = zxSkResMoveMonthMPMapper.batchDeleteUpdateZxSkResMoveMonthMP(zxSkResMoveMonthMPList, zxSkResMoveMonthMP);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkResMoveMonthMPList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
