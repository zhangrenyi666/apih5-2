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
import com.apih5.mybatis.dao.ZxCcConstCoAlterWorkMapper;
import com.apih5.mybatis.pojo.ZxCcConstCoAlterWork;
import com.apih5.service.ZxCcConstCoAlterWorkService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCcConstCoAlterWorkService")
public class ZxCcConstCoAlterWorkServiceImpl implements ZxCcConstCoAlterWorkService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCcConstCoAlterWorkMapper zxCcConstCoAlterWorkMapper;

    @Override
    public ResponseEntity getZxCcConstCoAlterWorkListByCondition(ZxCcConstCoAlterWork zxCcConstCoAlterWork) {
        if (zxCcConstCoAlterWork == null) {
            zxCcConstCoAlterWork = new ZxCcConstCoAlterWork();
        }
        // 分页查询
        PageHelper.startPage(zxCcConstCoAlterWork.getPage(),zxCcConstCoAlterWork.getLimit());
        // 获取数据
        List<ZxCcConstCoAlterWork> zxCcConstCoAlterWorkList = zxCcConstCoAlterWorkMapper.selectByZxCcConstCoAlterWorkList(zxCcConstCoAlterWork);
        // 得到分页信息
        PageInfo<ZxCcConstCoAlterWork> p = new PageInfo<>(zxCcConstCoAlterWorkList);

        return repEntity.okList(zxCcConstCoAlterWorkList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCcConstCoAlterWorkDetail(ZxCcConstCoAlterWork zxCcConstCoAlterWork) {
        if (zxCcConstCoAlterWork == null) {
            zxCcConstCoAlterWork = new ZxCcConstCoAlterWork();
        }
        // 获取数据
        ZxCcConstCoAlterWork dbZxCcConstCoAlterWork = zxCcConstCoAlterWorkMapper.selectByPrimaryKey(zxCcConstCoAlterWork.getId());
        // 数据存在
        if (dbZxCcConstCoAlterWork != null) {
            return repEntity.ok(dbZxCcConstCoAlterWork);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCcConstCoAlterWork(ZxCcConstCoAlterWork zxCcConstCoAlterWork) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCcConstCoAlterWork.setId(UuidUtil.generate());
        zxCcConstCoAlterWork.setCreateUserInfo(userKey, realName);
        int flag = zxCcConstCoAlterWorkMapper.insert(zxCcConstCoAlterWork);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCcConstCoAlterWork);
        }
    }

    @Override
    public ResponseEntity updateZxCcConstCoAlterWork(ZxCcConstCoAlterWork zxCcConstCoAlterWork) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCcConstCoAlterWork dbZxCcConstCoAlterWork = zxCcConstCoAlterWorkMapper.selectByPrimaryKey(zxCcConstCoAlterWork.getId());
        if (dbZxCcConstCoAlterWork != null && StrUtil.isNotEmpty(dbZxCcConstCoAlterWork.getId())) {
           // 变更ID
           dbZxCcConstCoAlterWork.setAlterId(zxCcConstCoAlterWork.getAlterId());
           // 变更类型
           dbZxCcConstCoAlterWork.setAlterType(zxCcConstCoAlterWork.getAlterType());
           // 管理单元ID
           dbZxCcConstCoAlterWork.setMuId(zxCcConstCoAlterWork.getMuId());
           // 清单ID
           dbZxCcConstCoAlterWork.setWorkId(zxCcConstCoAlterWork.getWorkId());
           // 原合同数量
           dbZxCcConstCoAlterWork.setOriginQty(zxCcConstCoAlterWork.getOriginQty());
           // 原含税合同单价
           dbZxCcConstCoAlterWork.setOriginPrice(zxCcConstCoAlterWork.getOriginPrice());
           // 申报数量
           dbZxCcConstCoAlterWork.setApplyQty(zxCcConstCoAlterWork.getApplyQty());
           // 申报单价
           dbZxCcConstCoAlterWork.setApplyPrice(zxCcConstCoAlterWork.getApplyPrice());
           // 申报新增数量
           dbZxCcConstCoAlterWork.setApplyAddQty(zxCcConstCoAlterWork.getApplyAddQty());
           // 批复数量
           dbZxCcConstCoAlterWork.setReplyQty(zxCcConstCoAlterWork.getReplyQty());
           // 批复单价
           dbZxCcConstCoAlterWork.setReplyPrice(zxCcConstCoAlterWork.getReplyPrice());
           // 批复新增数量
           dbZxCcConstCoAlterWork.setReplyAddQty(zxCcConstCoAlterWork.getReplyAddQty());
           // combProp
           dbZxCcConstCoAlterWork.setCombProp(zxCcConstCoAlterWork.getCombProp());
           // 归属主合同清单编号
           dbZxCcConstCoAlterWork.setMainContractListNo(zxCcConstCoAlterWork.getMainContractListNo());
           // 清单名称
           dbZxCcConstCoAlterWork.setListName(zxCcConstCoAlterWork.getListName());
           // 单位
           dbZxCcConstCoAlterWork.setUnit(zxCcConstCoAlterWork.getUnit());
           // 清单编号
           dbZxCcConstCoAlterWork.setListNo(zxCcConstCoAlterWork.getListNo());
           // pp5
           dbZxCcConstCoAlterWork.setPp5(zxCcConstCoAlterWork.getPp5());
           // 归属主合同清单ID
           dbZxCcConstCoAlterWork.setMainContractListId(zxCcConstCoAlterWork.getMainContractListId());
           // pp7
           dbZxCcConstCoAlterWork.setPp7(zxCcConstCoAlterWork.getPp7());
           // pp8
           dbZxCcConstCoAlterWork.setPp8(zxCcConstCoAlterWork.getPp8());
           // pp9
           dbZxCcConstCoAlterWork.setPp9(zxCcConstCoAlterWork.getPp9());
           // 挂接
           dbZxCcConstCoAlterWork.setHook(zxCcConstCoAlterWork.getHook());
           // 变更增减数量
           dbZxCcConstCoAlterWork.setChangeQty(zxCcConstCoAlterWork.getChangeQty());
           // 增减单价
           dbZxCcConstCoAlterWork.setChangePrice(zxCcConstCoAlterWork.getChangePrice());
           // 原含税合同金额
           dbZxCcConstCoAlterWork.setContractPrice(zxCcConstCoAlterWork.getContractPrice());
           // 变更后数量
           dbZxCcConstCoAlterWork.setAfterChangeQty(zxCcConstCoAlterWork.getAfterChangeQty());
           // 变更后单价
           dbZxCcConstCoAlterWork.setAfterChangePrice(zxCcConstCoAlterWork.getAfterChangePrice());
           // 是否叶子节点
           dbZxCcConstCoAlterWork.setIsLeaf(zxCcConstCoAlterWork.getIsLeaf());
           // 要求修改
           dbZxCcConstCoAlterWork.setRequestEdit(zxCcConstCoAlterWork.getRequestEdit());
           // 原不含税合同单价
           dbZxCcConstCoAlterWork.setOriginPriceNoTax(zxCcConstCoAlterWork.getOriginPriceNoTax());
           // 原不含税合同金额
           dbZxCcConstCoAlterWork.setContractCostNoTax(zxCcConstCoAlterWork.getContractCostNoTax());
           // 税率(%)
           dbZxCcConstCoAlterWork.setTaxRate(zxCcConstCoAlterWork.getTaxRate());
           // 变更后含税金额
           dbZxCcConstCoAlterWork.setAfterAmt(zxCcConstCoAlterWork.getAfterAmt());
           // 变更后不含税金额
           dbZxCcConstCoAlterWork.setAfterAmtNoTax(zxCcConstCoAlterWork.getAfterAmtNoTax());
           // 变更后税额
           dbZxCcConstCoAlterWork.setAfterAmtTax(zxCcConstCoAlterWork.getAfterAmtTax());
           // ruleId
           dbZxCcConstCoAlterWork.setRuleId(zxCcConstCoAlterWork.getRuleId());
           // ruleName
           dbZxCcConstCoAlterWork.setRuleName(zxCcConstCoAlterWork.getRuleName());
           // 已挂接工序数
           dbZxCcConstCoAlterWork.setGjNum(zxCcConstCoAlterWork.getGjNum());
           // 排序
           dbZxCcConstCoAlterWork.setSort(zxCcConstCoAlterWork.getSort());
           // 共通
           dbZxCcConstCoAlterWork.setModifyUserInfo(userKey, realName);
           flag = zxCcConstCoAlterWorkMapper.updateByPrimaryKey(dbZxCcConstCoAlterWork);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCcConstCoAlterWork);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCcConstCoAlterWork(List<ZxCcConstCoAlterWork> zxCcConstCoAlterWorkList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCcConstCoAlterWorkList != null && zxCcConstCoAlterWorkList.size() > 0) {
           ZxCcConstCoAlterWork zxCcConstCoAlterWork = new ZxCcConstCoAlterWork();
           zxCcConstCoAlterWork.setModifyUserInfo(userKey, realName);
           flag = zxCcConstCoAlterWorkMapper.batchDeleteUpdateZxCcConstCoAlterWork(zxCcConstCoAlterWorkList, zxCcConstCoAlterWork);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCcConstCoAlterWorkList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
