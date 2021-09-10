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
import com.apih5.mybatis.dao.ZxCtSuppliesContrReplenishShopListMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrReplenishShopList;
import com.apih5.service.ZxCtSuppliesContrReplenishShopListService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtSuppliesContrReplenishShopListService")
public class ZxCtSuppliesContrReplenishShopListServiceImpl implements ZxCtSuppliesContrReplenishShopListService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesContrReplenishShopListMapper zxCtSuppliesContrReplenishShopListMapper;

    @Override
    public ResponseEntity getZxCtSuppliesContrReplenishShopListListByCondition(ZxCtSuppliesContrReplenishShopList zxCtSuppliesContrReplenishShopList) {
        if (zxCtSuppliesContrReplenishShopList == null) {
            zxCtSuppliesContrReplenishShopList = new ZxCtSuppliesContrReplenishShopList();
        }
        // 分页查询
        PageHelper.startPage(zxCtSuppliesContrReplenishShopList.getPage(),zxCtSuppliesContrReplenishShopList.getLimit());
        // 获取数据
        List<ZxCtSuppliesContrReplenishShopList> zxCtSuppliesContrReplenishShopListList = zxCtSuppliesContrReplenishShopListMapper.selectByZxCtSuppliesContrReplenishShopListList(zxCtSuppliesContrReplenishShopList);
        // 得到分页信息
        PageInfo<ZxCtSuppliesContrReplenishShopList> p = new PageInfo<>(zxCtSuppliesContrReplenishShopListList);

        return repEntity.okList(zxCtSuppliesContrReplenishShopListList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesContrReplenishShopListDetail(ZxCtSuppliesContrReplenishShopList zxCtSuppliesContrReplenishShopList) {
        if (zxCtSuppliesContrReplenishShopList == null) {
            zxCtSuppliesContrReplenishShopList = new ZxCtSuppliesContrReplenishShopList();
        }
        // 获取数据
        ZxCtSuppliesContrReplenishShopList dbZxCtSuppliesContrReplenishShopList = zxCtSuppliesContrReplenishShopListMapper.selectByPrimaryKey(zxCtSuppliesContrReplenishShopList.getZxCtSuppliesContrReplenishShopListId());
        // 数据存在
        if (dbZxCtSuppliesContrReplenishShopList != null) {
            return repEntity.ok(dbZxCtSuppliesContrReplenishShopList);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesContrReplenishShopList(ZxCtSuppliesContrReplenishShopList zxCtSuppliesContrReplenishShopList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesContrReplenishShopList.setZxCtSuppliesContrReplenishShopListId(UuidUtil.generate());
        zxCtSuppliesContrReplenishShopList.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesContrReplenishShopListMapper.insert(zxCtSuppliesContrReplenishShopList);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesContrReplenishShopList);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesContrReplenishShopList(ZxCtSuppliesContrReplenishShopList zxCtSuppliesContrReplenishShopList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesContrReplenishShopList dbZxCtSuppliesContrReplenishShopList = zxCtSuppliesContrReplenishShopListMapper.selectByPrimaryKey(zxCtSuppliesContrReplenishShopList.getZxCtSuppliesContrReplenishShopListId());
        if (dbZxCtSuppliesContrReplenishShopList != null && StrUtil.isNotEmpty(dbZxCtSuppliesContrReplenishShopList.getZxCtSuppliesContrReplenishShopListId())) {
           // 状态
           dbZxCtSuppliesContrReplenishShopList.setAudit(zxCtSuppliesContrReplenishShopList.getAudit());
           // 状态
           dbZxCtSuppliesContrReplenishShopList.setAuditStatus(zxCtSuppliesContrReplenishShopList.getAuditStatus());
           // 需要公司协助
           dbZxCtSuppliesContrReplenishShopList.setCompanyHelp(zxCtSuppliesContrReplenishShopList.getCompanyHelp());
           // 协作清单书ID
           dbZxCtSuppliesContrReplenishShopList.setCoBookID(zxCtSuppliesContrReplenishShopList.getCoBookID());
           // 提出单位
           dbZxCtSuppliesContrReplenishShopList.setProposer(zxCtSuppliesContrReplenishShopList.getProposer());
           // 生效操作日期
           dbZxCtSuppliesContrReplenishShopList.setTakeEffectDate(zxCtSuppliesContrReplenishShopList.getTakeEffectDate());
           // 生效操作人
           dbZxCtSuppliesContrReplenishShopList.setTakeEffectMan(zxCtSuppliesContrReplenishShopList.getTakeEffectMan());
           // 申报延期天数
           dbZxCtSuppliesContrReplenishShopList.setApplyDelay(zxCtSuppliesContrReplenishShopList.getApplyDelay());
           // 申报文号
           dbZxCtSuppliesContrReplenishShopList.setApplyNo(zxCtSuppliesContrReplenishShopList.getApplyNo());
           // 申报日期
           dbZxCtSuppliesContrReplenishShopList.setApplyDate(zxCtSuppliesContrReplenishShopList.getApplyDate());
           // 上期末变更后税额
           dbZxCtSuppliesContrReplenishShopList.setUpAlterContractSumTax(zxCtSuppliesContrReplenishShopList.getUpAlterContractSumTax());
           // 上期末变更后金额不含税
           dbZxCtSuppliesContrReplenishShopList.setUpAlterContractSumNoTax(zxCtSuppliesContrReplenishShopList.getUpAlterContractSumNoTax());
           // 上期末变更后金额
           dbZxCtSuppliesContrReplenishShopList.setUpAlterContractSum(zxCtSuppliesContrReplenishShopList.getUpAlterContractSum());
           // 批复状态
           dbZxCtSuppliesContrReplenishShopList.setReplyStatus(zxCtSuppliesContrReplenishShopList.getReplyStatus());
           // 批复延期天数
           dbZxCtSuppliesContrReplenishShopList.setReplyDelay(zxCtSuppliesContrReplenishShopList.getReplyDelay());
           // 批复日期
           dbZxCtSuppliesContrReplenishShopList.setReplyDate(zxCtSuppliesContrReplenishShopList.getReplyDate());
           // 批复单位
           dbZxCtSuppliesContrReplenishShopList.setPp1(zxCtSuppliesContrReplenishShopList.getPp1());
           // 记录日期
           dbZxCtSuppliesContrReplenishShopList.setRecordDate(zxCtSuppliesContrReplenishShopList.getRecordDate());
           // 记录人
           dbZxCtSuppliesContrReplenishShopList.setRecorder(zxCtSuppliesContrReplenishShopList.getRecorder());
           // 机构ID
           dbZxCtSuppliesContrReplenishShopList.setOrgID(zxCtSuppliesContrReplenishShopList.getOrgID());
           // 合同税额(万元)
           dbZxCtSuppliesContrReplenishShopList.setPp2Tax(zxCtSuppliesContrReplenishShopList.getPp2Tax());
           // 发生时间
           dbZxCtSuppliesContrReplenishShopList.setHappenDate(zxCtSuppliesContrReplenishShopList.getHappenDate());
           // 不含税合同金额(万元)
           dbZxCtSuppliesContrReplenishShopList.setPp2NoTax(zxCtSuppliesContrReplenishShopList.getPp2NoTax());
           // 补充协议书编号
           dbZxCtSuppliesContrReplenishShopList.setReplyNo(zxCtSuppliesContrReplenishShopList.getReplyNo());
           // 补充协议书ID
           dbZxCtSuppliesContrReplenishShopList.setPp3(zxCtSuppliesContrReplenishShopList.getPp3());
           // 变更原因
           dbZxCtSuppliesContrReplenishShopList.setAlterReason(zxCtSuppliesContrReplenishShopList.getAlterReason());
           // 变更内容
           dbZxCtSuppliesContrReplenishShopList.setAlterContent(zxCtSuppliesContrReplenishShopList.getAlterContent());
           // 变更后税额(万元)
           dbZxCtSuppliesContrReplenishShopList.setReplyAmountTax(zxCtSuppliesContrReplenishShopList.getReplyAmountTax());
           // 变更后含税金额(万元)
           dbZxCtSuppliesContrReplenishShopList.setReplyAmount(zxCtSuppliesContrReplenishShopList.getReplyAmount());
           // 变更后不含税金额(万元)
           dbZxCtSuppliesContrReplenishShopList.setReplyAmountNoTax(zxCtSuppliesContrReplenishShopList.getReplyAmountNoTax());
           // 变更等级
           dbZxCtSuppliesContrReplenishShopList.setAlterLevel(zxCtSuppliesContrReplenishShopList.getAlterLevel());
           // 本期增减税额(万元)
           dbZxCtSuppliesContrReplenishShopList.setApplyAmountTax(zxCtSuppliesContrReplenishShopList.getApplyAmountTax());
           // 本期含税增减金额(万元)
           dbZxCtSuppliesContrReplenishShopList.setApplyAmount(zxCtSuppliesContrReplenishShopList.getApplyAmount());
           // 本期不含税增减金额(万元)
           dbZxCtSuppliesContrReplenishShopList.setApplyAmountNoTax(zxCtSuppliesContrReplenishShopList.getApplyAmountNoTax());
           // (IP的合同ID)
           dbZxCtSuppliesContrReplenishShopList.setPp6(zxCtSuppliesContrReplenishShopList.getPp6());
           // 备注
           dbZxCtSuppliesContrReplenishShopList.setRemarks(zxCtSuppliesContrReplenishShopList.getRemarks());
           // 排序
           dbZxCtSuppliesContrReplenishShopList.setSort(zxCtSuppliesContrReplenishShopList.getSort());
           // 共通
           dbZxCtSuppliesContrReplenishShopList.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrReplenishShopListMapper.updateByPrimaryKey(dbZxCtSuppliesContrReplenishShopList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesContrReplenishShopList);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrReplenishShopList(List<ZxCtSuppliesContrReplenishShopList> zxCtSuppliesContrReplenishShopListList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesContrReplenishShopListList != null && zxCtSuppliesContrReplenishShopListList.size() > 0) {
           ZxCtSuppliesContrReplenishShopList zxCtSuppliesContrReplenishShopList = new ZxCtSuppliesContrReplenishShopList();
           zxCtSuppliesContrReplenishShopList.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrReplenishShopListMapper.batchDeleteUpdateZxCtSuppliesContrReplenishShopList(zxCtSuppliesContrReplenishShopListList, zxCtSuppliesContrReplenishShopList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesContrReplenishShopListList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
