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
import com.apih5.mybatis.dao.ZxCtSuppliesContrReplenishLeaseListMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesContrReplenishLeaseList;
import com.apih5.service.ZxCtSuppliesContrReplenishLeaseListService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtSuppliesContrReplenishLeaseListService")
public class ZxCtSuppliesContrReplenishLeaseListServiceImpl implements ZxCtSuppliesContrReplenishLeaseListService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesContrReplenishLeaseListMapper zxCtSuppliesContrReplenishLeaseListMapper;

    @Override
    public ResponseEntity getZxCtSuppliesContrReplenishLeaseListListByCondition(ZxCtSuppliesContrReplenishLeaseList zxCtSuppliesContrReplenishLeaseList) {
        if (zxCtSuppliesContrReplenishLeaseList == null) {
            zxCtSuppliesContrReplenishLeaseList = new ZxCtSuppliesContrReplenishLeaseList();
        }
        // 分页查询
        PageHelper.startPage(zxCtSuppliesContrReplenishLeaseList.getPage(),zxCtSuppliesContrReplenishLeaseList.getLimit());
        // 获取数据
        List<ZxCtSuppliesContrReplenishLeaseList> zxCtSuppliesContrReplenishLeaseListList = zxCtSuppliesContrReplenishLeaseListMapper.selectByZxCtSuppliesContrReplenishLeaseListList(zxCtSuppliesContrReplenishLeaseList);
        // 得到分页信息
        PageInfo<ZxCtSuppliesContrReplenishLeaseList> p = new PageInfo<>(zxCtSuppliesContrReplenishLeaseListList);

        return repEntity.okList(zxCtSuppliesContrReplenishLeaseListList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesContrReplenishLeaseListDetail(ZxCtSuppliesContrReplenishLeaseList zxCtSuppliesContrReplenishLeaseList) {
        if (zxCtSuppliesContrReplenishLeaseList == null) {
            zxCtSuppliesContrReplenishLeaseList = new ZxCtSuppliesContrReplenishLeaseList();
        }
        // 获取数据
        ZxCtSuppliesContrReplenishLeaseList dbZxCtSuppliesContrReplenishLeaseList = zxCtSuppliesContrReplenishLeaseListMapper.selectByPrimaryKey(zxCtSuppliesContrReplenishLeaseList.getZxCtSuppliesContrReplenishLeaseListId());
        // 数据存在
        if (dbZxCtSuppliesContrReplenishLeaseList != null) {
            return repEntity.ok(dbZxCtSuppliesContrReplenishLeaseList);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesContrReplenishLeaseList(ZxCtSuppliesContrReplenishLeaseList zxCtSuppliesContrReplenishLeaseList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesContrReplenishLeaseList.setZxCtSuppliesContrReplenishLeaseListId(UuidUtil.generate());
        zxCtSuppliesContrReplenishLeaseList.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesContrReplenishLeaseListMapper.insert(zxCtSuppliesContrReplenishLeaseList);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesContrReplenishLeaseList);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesContrReplenishLeaseList(ZxCtSuppliesContrReplenishLeaseList zxCtSuppliesContrReplenishLeaseList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesContrReplenishLeaseList dbZxCtSuppliesContrReplenishLeaseList = zxCtSuppliesContrReplenishLeaseListMapper.selectByPrimaryKey(zxCtSuppliesContrReplenishLeaseList.getZxCtSuppliesContrReplenishLeaseListId());
        if (dbZxCtSuppliesContrReplenishLeaseList != null && StrUtil.isNotEmpty(dbZxCtSuppliesContrReplenishLeaseList.getZxCtSuppliesContrReplenishLeaseListId())) {
           // 状态
           dbZxCtSuppliesContrReplenishLeaseList.setAuditStatus(zxCtSuppliesContrReplenishLeaseList.getAuditStatus());
           // 需要公司协助
           dbZxCtSuppliesContrReplenishLeaseList.setCompanyHelp(zxCtSuppliesContrReplenishLeaseList.getCompanyHelp());
           // 提出单位
           dbZxCtSuppliesContrReplenishLeaseList.setProposer(zxCtSuppliesContrReplenishLeaseList.getProposer());
           // 生效操作日期
           dbZxCtSuppliesContrReplenishLeaseList.setTakeEffectDate(zxCtSuppliesContrReplenishLeaseList.getTakeEffectDate());
           // 生效操作人
           dbZxCtSuppliesContrReplenishLeaseList.setTakeEffectMan(zxCtSuppliesContrReplenishLeaseList.getTakeEffectMan());
           // 申报延期天数
           dbZxCtSuppliesContrReplenishLeaseList.setApplyDelay(zxCtSuppliesContrReplenishLeaseList.getApplyDelay());
           // 申报文号
           dbZxCtSuppliesContrReplenishLeaseList.setApplyNo(zxCtSuppliesContrReplenishLeaseList.getApplyNo());
           // 申报日期
           dbZxCtSuppliesContrReplenishLeaseList.setApplyDate(zxCtSuppliesContrReplenishLeaseList.getApplyDate());
           // 上期末变更后税额
           dbZxCtSuppliesContrReplenishLeaseList.setUpAlterContractSumTax(zxCtSuppliesContrReplenishLeaseList.getUpAlterContractSumTax());
           // 上期末变更后金额不含税
           dbZxCtSuppliesContrReplenishLeaseList.setUpAlterContractSumNoTax(zxCtSuppliesContrReplenishLeaseList.getUpAlterContractSumNoTax());
           // 上期末变更后金额
           dbZxCtSuppliesContrReplenishLeaseList.setUpAlterContractSum(zxCtSuppliesContrReplenishLeaseList.getUpAlterContractSum());
           // 批复状态
           dbZxCtSuppliesContrReplenishLeaseList.setReplyStatus(zxCtSuppliesContrReplenishLeaseList.getReplyStatus());
           // 批复延期天数
           dbZxCtSuppliesContrReplenishLeaseList.setReplyDelay(zxCtSuppliesContrReplenishLeaseList.getReplyDelay());
           // 批复日期
           dbZxCtSuppliesContrReplenishLeaseList.setReplyDate(zxCtSuppliesContrReplenishLeaseList.getReplyDate());
           // 批复单位
           dbZxCtSuppliesContrReplenishLeaseList.setPp1(zxCtSuppliesContrReplenishLeaseList.getPp1());
           // 记录日期
           dbZxCtSuppliesContrReplenishLeaseList.setRecordDate(zxCtSuppliesContrReplenishLeaseList.getRecordDate());
           // 记录人
           dbZxCtSuppliesContrReplenishLeaseList.setRecorder(zxCtSuppliesContrReplenishLeaseList.getRecorder());
           // 机构ID
           dbZxCtSuppliesContrReplenishLeaseList.setOrgID(zxCtSuppliesContrReplenishLeaseList.getOrgID());
           // 合同税额(万元)
           dbZxCtSuppliesContrReplenishLeaseList.setPp2Tax(zxCtSuppliesContrReplenishLeaseList.getPp2Tax());
           // 合同ID
           dbZxCtSuppliesContrReplenishLeaseList.setContractID(zxCtSuppliesContrReplenishLeaseList.getContractID());
           // 含税合同金额(万元)
           dbZxCtSuppliesContrReplenishLeaseList.setPp2(zxCtSuppliesContrReplenishLeaseList.getPp2());
           // 发生时间
           dbZxCtSuppliesContrReplenishLeaseList.setHappenDate(zxCtSuppliesContrReplenishLeaseList.getHappenDate());
           // 不含税合同金额(万元)
           dbZxCtSuppliesContrReplenishLeaseList.setPp2NoTax(zxCtSuppliesContrReplenishLeaseList.getPp2NoTax());
           // 补充协议书编号
           dbZxCtSuppliesContrReplenishLeaseList.setReplyNo(zxCtSuppliesContrReplenishLeaseList.getReplyNo());
           // 补充协议书ID
           dbZxCtSuppliesContrReplenishLeaseList.setPp3(zxCtSuppliesContrReplenishLeaseList.getPp3());
           // 变更原因
           dbZxCtSuppliesContrReplenishLeaseList.setAlterReason(zxCtSuppliesContrReplenishLeaseList.getAlterReason());
           // 变更内容
           dbZxCtSuppliesContrReplenishLeaseList.setAlterContent(zxCtSuppliesContrReplenishLeaseList.getAlterContent());
           // 变更后税额(万元)
           dbZxCtSuppliesContrReplenishLeaseList.setReplyAmountTax(zxCtSuppliesContrReplenishLeaseList.getReplyAmountTax());
           // 变更后含税金额(万元)
           dbZxCtSuppliesContrReplenishLeaseList.setReplyAmount(zxCtSuppliesContrReplenishLeaseList.getReplyAmount());
           // 变更后不含税金额(万元)
           dbZxCtSuppliesContrReplenishLeaseList.setReplyAmountNoTax(zxCtSuppliesContrReplenishLeaseList.getReplyAmountNoTax());
           // 变更等级
           dbZxCtSuppliesContrReplenishLeaseList.setAlterLevel(zxCtSuppliesContrReplenishLeaseList.getAlterLevel());
           // 本期增减税额(万元)
           dbZxCtSuppliesContrReplenishLeaseList.setApplyAmountTax(zxCtSuppliesContrReplenishLeaseList.getApplyAmountTax());
           // 本期含税增减金额(万元)
           dbZxCtSuppliesContrReplenishLeaseList.setApplyAmount(zxCtSuppliesContrReplenishLeaseList.getApplyAmount());
           // 本期不含税增减金额(万元)
           dbZxCtSuppliesContrReplenishLeaseList.setApplyAmountNoTax(zxCtSuppliesContrReplenishLeaseList.getApplyAmountNoTax());
           // (IP的合同ID)
           dbZxCtSuppliesContrReplenishLeaseList.setPp6(zxCtSuppliesContrReplenishLeaseList.getPp6());
           // 备注
           dbZxCtSuppliesContrReplenishLeaseList.setRemarks(zxCtSuppliesContrReplenishLeaseList.getRemarks());
           // 排序
           dbZxCtSuppliesContrReplenishLeaseList.setSort(zxCtSuppliesContrReplenishLeaseList.getSort());
           // 共通
           dbZxCtSuppliesContrReplenishLeaseList.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrReplenishLeaseListMapper.updateByPrimaryKey(dbZxCtSuppliesContrReplenishLeaseList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesContrReplenishLeaseList);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContrReplenishLeaseList(List<ZxCtSuppliesContrReplenishLeaseList> zxCtSuppliesContrReplenishLeaseListList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesContrReplenishLeaseListList != null && zxCtSuppliesContrReplenishLeaseListList.size() > 0) {
           ZxCtSuppliesContrReplenishLeaseList zxCtSuppliesContrReplenishLeaseList = new ZxCtSuppliesContrReplenishLeaseList();
           zxCtSuppliesContrReplenishLeaseList.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContrReplenishLeaseListMapper.batchDeleteUpdateZxCtSuppliesContrReplenishLeaseList(zxCtSuppliesContrReplenishLeaseListList, zxCtSuppliesContrReplenishLeaseList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesContrReplenishLeaseListList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
