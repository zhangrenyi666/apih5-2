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
import com.apih5.mybatis.dao.ZxCtSuppliesContractChangeMapper;
import com.apih5.mybatis.pojo.ZxCtSuppliesContractChange;
import com.apih5.service.ZxCtSuppliesContractChangeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtSuppliesContractChangeService")
public class ZxCtSuppliesContractChangeServiceImpl implements ZxCtSuppliesContractChangeService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtSuppliesContractChangeMapper zxCtSuppliesContractChangeMapper;

    @Override
    public ResponseEntity getZxCtSuppliesContractChangeListByCondition(ZxCtSuppliesContractChange zxCtSuppliesContractChange) {
        if (zxCtSuppliesContractChange == null) {
            zxCtSuppliesContractChange = new ZxCtSuppliesContractChange();
        }
        // 分页查询
        PageHelper.startPage(zxCtSuppliesContractChange.getPage(),zxCtSuppliesContractChange.getLimit());
        // 获取数据
        List<ZxCtSuppliesContractChange> zxCtSuppliesContractChangeList = zxCtSuppliesContractChangeMapper.selectByZxCtSuppliesContractChangeList(zxCtSuppliesContractChange);
        // 得到分页信息
        PageInfo<ZxCtSuppliesContractChange> p = new PageInfo<>(zxCtSuppliesContractChangeList);

        return repEntity.okList(zxCtSuppliesContractChangeList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtSuppliesContractChangeDetail(ZxCtSuppliesContractChange zxCtSuppliesContractChange) {
        if (zxCtSuppliesContractChange == null) {
            zxCtSuppliesContractChange = new ZxCtSuppliesContractChange();
        }
        // 获取数据
        ZxCtSuppliesContractChange dbZxCtSuppliesContractChange = zxCtSuppliesContractChangeMapper.selectByPrimaryKey(zxCtSuppliesContractChange.getZxCtSuppliesContractChangeId());
        // 数据存在
        if (dbZxCtSuppliesContractChange != null) {
            return repEntity.ok(dbZxCtSuppliesContractChange);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtSuppliesContractChange(ZxCtSuppliesContractChange zxCtSuppliesContractChange) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtSuppliesContractChange.setZxCtSuppliesContractChangeId(UuidUtil.generate());
        zxCtSuppliesContractChange.setCreateUserInfo(userKey, realName);
        int flag = zxCtSuppliesContractChangeMapper.insert(zxCtSuppliesContractChange);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtSuppliesContractChange);
        }
    }

    @Override
    public ResponseEntity updateZxCtSuppliesContractChange(ZxCtSuppliesContractChange zxCtSuppliesContractChange) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtSuppliesContractChange dbZxCtSuppliesContractChange = zxCtSuppliesContractChangeMapper.selectByPrimaryKey(zxCtSuppliesContractChange.getZxCtSuppliesContractChangeId());
        if (dbZxCtSuppliesContractChange != null && StrUtil.isNotEmpty(dbZxCtSuppliesContractChange.getZxCtSuppliesContractChangeId())) {
           // 需要公司协助
           dbZxCtSuppliesContractChange.setCompanyHelp(zxCtSuppliesContractChange.getCompanyHelp());
           // 提出单位
           dbZxCtSuppliesContractChange.setProposer(zxCtSuppliesContractChange.getProposer());
           // 生效状态位
           dbZxCtSuppliesContractChange.setAudit(zxCtSuppliesContractChange.getAudit());
           // 生效操作日期
           dbZxCtSuppliesContractChange.setTakeEffectDate(zxCtSuppliesContractChange.getTakeEffectDate());
           // 生效操作人
           dbZxCtSuppliesContractChange.setTakeEffectMan(zxCtSuppliesContractChange.getTakeEffectMan());
           // 申报延期天数
           dbZxCtSuppliesContractChange.setApplyDelay(zxCtSuppliesContractChange.getApplyDelay());
           // 申报文号
           dbZxCtSuppliesContractChange.setApplyNo(zxCtSuppliesContractChange.getApplyNo());
           // 申报日期
           dbZxCtSuppliesContractChange.setApplyDate(zxCtSuppliesContractChange.getApplyDate());
           // 上期末变更后税额
           dbZxCtSuppliesContractChange.setUpAlterContractSumTax(zxCtSuppliesContractChange.getUpAlterContractSumTax());
           // 上期末变更后金额不含税
           dbZxCtSuppliesContractChange.setUpAlterContractSumNoTax(zxCtSuppliesContractChange.getUpAlterContractSumNoTax());
           // 上期末变更后金额
           dbZxCtSuppliesContractChange.setUpAlterContractSum(zxCtSuppliesContractChange.getUpAlterContractSum());
           // 批复状态
           dbZxCtSuppliesContractChange.setReplyStatus(zxCtSuppliesContractChange.getReplyStatus());
           // 批复延期天数
           dbZxCtSuppliesContractChange.setReplyDelay(zxCtSuppliesContractChange.getReplyDelay());
           // 批复日期
           dbZxCtSuppliesContractChange.setReplyDate(zxCtSuppliesContractChange.getReplyDate());
           // 批复单位
           dbZxCtSuppliesContractChange.setPp1(zxCtSuppliesContractChange.getPp1());
           // 记录日期
           dbZxCtSuppliesContractChange.setRecordDate(zxCtSuppliesContractChange.getRecordDate());
           // 记录人
           dbZxCtSuppliesContractChange.setRecorder(zxCtSuppliesContractChange.getRecorder());
           // 机构ID
           dbZxCtSuppliesContractChange.setOrgID(zxCtSuppliesContractChange.getOrgID());
           // 合同金额(万元)
           dbZxCtSuppliesContractChange.setPp2(zxCtSuppliesContractChange.getPp2());
           // 合同ID
           dbZxCtSuppliesContractChange.setContractID(zxCtSuppliesContractChange.getContractID());
           // 发生时间
           dbZxCtSuppliesContractChange.setHappenDate(zxCtSuppliesContractChange.getHappenDate());
           // 补充协议书编号
           dbZxCtSuppliesContractChange.setReplyNo(zxCtSuppliesContractChange.getReplyNo());
           // 补充协议书ID
           dbZxCtSuppliesContractChange.setPp3(zxCtSuppliesContractChange.getPp3());
           // 变更增减金额(万元)
           dbZxCtSuppliesContractChange.setApplyAmount(zxCtSuppliesContractChange.getApplyAmount());
           // 变更原因
           dbZxCtSuppliesContractChange.setAlterReason(zxCtSuppliesContractChange.getAlterReason());
           // 变更内容
           dbZxCtSuppliesContractChange.setAlterContent(zxCtSuppliesContractChange.getAlterContent());
           // 变更后合同金额(万元)
           dbZxCtSuppliesContractChange.setReplyAmount(zxCtSuppliesContractChange.getReplyAmount());
           // 变更等级
           dbZxCtSuppliesContractChange.setAlterLevel(zxCtSuppliesContractChange.getAlterLevel());
           // (IP的合同ID)
           dbZxCtSuppliesContractChange.setPp6(zxCtSuppliesContractChange.getPp6());
           // 备注
           dbZxCtSuppliesContractChange.setRemarks(zxCtSuppliesContractChange.getRemarks());
           // 排序
           dbZxCtSuppliesContractChange.setSort(zxCtSuppliesContractChange.getSort());
           // 共通
           dbZxCtSuppliesContractChange.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContractChangeMapper.updateByPrimaryKey(dbZxCtSuppliesContractChange);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtSuppliesContractChange);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtSuppliesContractChange(List<ZxCtSuppliesContractChange> zxCtSuppliesContractChangeList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtSuppliesContractChangeList != null && zxCtSuppliesContractChangeList.size() > 0) {
           ZxCtSuppliesContractChange zxCtSuppliesContractChange = new ZxCtSuppliesContractChange();
           zxCtSuppliesContractChange.setModifyUserInfo(userKey, realName);
           flag = zxCtSuppliesContractChangeMapper.batchDeleteUpdateZxCtSuppliesContractChange(zxCtSuppliesContractChangeList, zxCtSuppliesContractChange);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtSuppliesContractChangeList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
