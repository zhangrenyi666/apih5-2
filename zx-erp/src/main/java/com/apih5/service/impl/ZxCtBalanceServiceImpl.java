package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtBalanceMapper;
import com.apih5.mybatis.pojo.ZxCtBalance;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxCtBalanceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxCtBalanceService")
public class ZxCtBalanceServiceImpl implements ZxCtBalanceService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtBalanceMapper zxCtBalanceMapper;
    
    @Autowired(required = true)
    private ZxErpFileServiceImpl zxErpFileServiceImpl;

    @SuppressWarnings("unchecked")
	@Override
    public ResponseEntity getZxCtBalanceListByCondition(ZxCtBalance zxCtBalance) {
        if (zxCtBalance == null) {
            zxCtBalance = new ZxCtBalance();
        }
        // 分页查询
        PageHelper.startPage(zxCtBalance.getPage(),zxCtBalance.getLimit());
        // 获取数据
        List<ZxCtBalance> zxCtBalanceList = zxCtBalanceMapper.selectByZxCtBalanceList(zxCtBalance);
        // 得到分页信息
        PageInfo<ZxCtBalance> p = new PageInfo<>(zxCtBalanceList);

        for (ZxCtBalance ctBalance : zxCtBalanceList) {
        	// 附件
        	ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(ctBalance.getId());
        	zxErpFile.setOtherType("1");
        	List<ZxErpFile> zxErpFileList = (List<ZxErpFile>) zxErpFileServiceImpl.getZxErpFileListByCondition(zxErpFile).getData();
        	ctBalance.setAttachment(zxErpFileList);
		}
        
        return repEntity.okList(zxCtBalanceList, p.getTotal());
    }

    @SuppressWarnings("unchecked")
	@Override
    public ResponseEntity getZxCtBalanceDetail(ZxCtBalance zxCtBalance) {
        if (zxCtBalance == null) {
            zxCtBalance = new ZxCtBalance();
        }
        // 获取数据
        ZxCtBalance dbZxCtBalance = zxCtBalanceMapper.selectByPrimaryKey(zxCtBalance.getId());
        // 数据存在
        if (dbZxCtBalance != null) {
        	// 附件
        	ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(dbZxCtBalance.getId());
        	zxErpFile.setOtherType("1");
        	List<ZxErpFile> zxErpFileList = (List<ZxErpFile>) zxErpFileServiceImpl.getZxErpFileListByCondition(zxErpFile).getData();
        	dbZxCtBalance.setAttachment(zxErpFileList);
        	
            return repEntity.ok(dbZxCtBalance);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtBalance(ZxCtBalance zxCtBalance) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtBalance.setId(UuidUtil.generate());
        zxCtBalance.setCreateUserInfo(userKey, realName);
        
        int flag = zxCtBalanceMapper.insert(zxCtBalance);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// 附件
        	List<ZxErpFile> zxErpFileList = zxCtBalance.getAttachment();
        	if (zxErpFileList != null && zxErpFileList.size() > 0) {
				for (ZxErpFile zxErpFile : zxErpFileList) {
		        	zxErpFile.setOtherId(zxCtBalance.getId());
		        	zxErpFile.setOtherType("1");
		        	zxErpFileServiceImpl.saveZxErpFile(zxErpFile);
				}
			}
        	
            return repEntity.ok("sys.data.sava", zxCtBalance);
        }
    }

    @Override
    public ResponseEntity updateZxCtBalance(ZxCtBalance zxCtBalance) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtBalance dbZxCtBalance = zxCtBalanceMapper.selectByPrimaryKey(zxCtBalance.getId());
        if (dbZxCtBalance != null && StrUtil.isNotEmpty(dbZxCtBalance.getId())) {
           // 附件
           dbZxCtBalance.setAttachment(zxCtBalance.getAttachment());
           // 项目ID
           dbZxCtBalance.setOrgID(zxCtBalance.getOrgID());
           // 项目ID
           dbZxCtBalance.setWorkBookID(zxCtBalance.getWorkBookID());
           // 合同ID
           dbZxCtBalance.setContractID(zxCtBalance.getContractID());
           // 编号
           dbZxCtBalance.setBalNo(zxCtBalance.getBalNo());
           // 计量单类型
           dbZxCtBalance.setBalType(zxCtBalance.getBalType());
           // 结算期次
           dbZxCtBalance.setPeriod(zxCtBalance.getPeriod());
           // 开累计量(元)
           dbZxCtBalance.setTotalbalAmt(zxCtBalance.getTotalbalAmt());
           // 本期计量(元)
           dbZxCtBalance.setBalAmt(zxCtBalance.getBalAmt());
           // 日期
           dbZxCtBalance.setBusDate(zxCtBalance.getBusDate());
           // 结算期限开始时间
           dbZxCtBalance.setStartDate(zxCtBalance.getStartDate());
           // 结算期限结束时间
           dbZxCtBalance.setEndDate(zxCtBalance.getEndDate());
           // 业务日期
           dbZxCtBalance.setCreateDate(zxCtBalance.getCreateDate());
           // 记录人
           dbZxCtBalance.setReporter(zxCtBalance.getReporter());
           // 审核人
           dbZxCtBalance.setAuditor(zxCtBalance.getAuditor());
           // 审核日期
           dbZxCtBalance.setAuditDate(zxCtBalance.getAuditDate());
           // 审核意见
           dbZxCtBalance.setAuditMsg(zxCtBalance.getAuditMsg());
           // 评审状态
           dbZxCtBalance.setAuditStatus(zxCtBalance.getAuditStatus());
           // combProp
           dbZxCtBalance.setCombProp(zxCtBalance.getCombProp());
           // pp1
           dbZxCtBalance.setPp1(zxCtBalance.getPp1());
           // pp2
           dbZxCtBalance.setPp2(zxCtBalance.getPp2());
           // pp3
           dbZxCtBalance.setPp3(zxCtBalance.getPp3());
           // pp4
           dbZxCtBalance.setPp4(zxCtBalance.getPp4());
           // pp5
           dbZxCtBalance.setPp5(zxCtBalance.getPp5());
           // pp6
           dbZxCtBalance.setPp6(zxCtBalance.getPp6());
           // pp7
           dbZxCtBalance.setPp7(zxCtBalance.getPp7());
           // pp8
           dbZxCtBalance.setPp8(zxCtBalance.getPp8());
           // pp9
           dbZxCtBalance.setPp9(zxCtBalance.getPp9());
           // pp10
           dbZxCtBalance.setPp10(zxCtBalance.getPp10());
           // 编号
           dbZxCtBalance.setNo(zxCtBalance.getNo());
           // 申请日期
           dbZxCtBalance.setApplyDate(zxCtBalance.getApplyDate());
           // 驻地监理确认日期
           dbZxCtBalance.setStationRlyDate(zxCtBalance.getStationRlyDate());
           // 总监确认日期
           dbZxCtBalance.setMajorRlyDate(zxCtBalance.getMajorRlyDate());
           // 业主批复日期
           dbZxCtBalance.setOwnerRlyDate(zxCtBalance.getOwnerRlyDate());
           // 申请金额
           dbZxCtBalance.setApplyamt(zxCtBalance.getApplyamt());
           // 驻地监理确认金额
           dbZxCtBalance.setStationRlyamt(zxCtBalance.getStationRlyamt());
           // 总监确定金额
           dbZxCtBalance.setMajorRlyamt(zxCtBalance.getMajorRlyamt());
           // 业主批复金额
           dbZxCtBalance.setOwnerRlyamt(zxCtBalance.getOwnerRlyamt());
           // 最后编辑时间
           dbZxCtBalance.setEditTime(zxCtBalance.getEditTime());
           // 期次
           dbZxCtBalance.setCaption(zxCtBalance.getCaption());
           // 统一社会信用代码
           dbZxCtBalance.setOrgCertificate(zxCtBalance.getOrgCertificate());
           // 本期应付金额(元)
           dbZxCtBalance.setThisPayAmt(zxCtBalance.getThisPayAmt());
           // 开累应付金额(元)
           dbZxCtBalance.setTotalPayAmt(zxCtBalance.getTotalPayAmt());
           // 本期结算金额(元)
           dbZxCtBalance.setThisAmt(zxCtBalance.getThisAmt());
           // 本期结算不含税金额(元)
           dbZxCtBalance.setThisAmtNoTax(zxCtBalance.getThisAmtNoTax());
           // 本期结算税额(元)
           dbZxCtBalance.setThisAmtTax(zxCtBalance.getThisAmtTax());
           // 开累结算金额(元)
           dbZxCtBalance.setTotalAmt(zxCtBalance.getTotalAmt());
           // 结算金额差值
           dbZxCtBalance.setTcje(zxCtBalance.getTcje());
           // 税额差值
           dbZxCtBalance.setTcse(zxCtBalance.getTcse());
           // 调差后累计结算金额
           dbZxCtBalance.setTchljjsje(zxCtBalance.getTchljjsje());
           // 本期调差后税额
           dbZxCtBalance.setBqtchse(zxCtBalance.getBqtchse());
           // 本期调差后结算金额
           dbZxCtBalance.setBqtchjsje(zxCtBalance.getBqtchjsje());
           // 截止到上期累计金额(元)
           dbZxCtBalance.setUpTotalAmt(zxCtBalance.getUpTotalAmt());
           // zjgcxm_xmbh
           dbZxCtBalance.setZjgcxmXmbh(zxCtBalance.getZjgcxmXmbh());
           // zjgcxm_nm
           dbZxCtBalance.setZjgcxmNm(zxCtBalance.getZjgcxmNm());
           // 项目
           dbZxCtBalance.setZjgcxmXmmc(zxCtBalance.getZjgcxmXmmc());
           // 填报人
           dbZxCtBalance.setReportPerson(zxCtBalance.getReportPerson());
           // 结算方向
           dbZxCtBalance.setSetDir(zxCtBalance.getSetDir());
           // 核算单位内码
           dbZxCtBalance.setAccountUnitId(zxCtBalance.getAccountUnitId());
           // 核算单位编号
           dbZxCtBalance.setAccountUnitCode(zxCtBalance.getAccountUnitCode());
           // 核算单位名称
           dbZxCtBalance.setAccountUnitName(zxCtBalance.getAccountUnitName());
           // 核算部门内码
           dbZxCtBalance.setAccountDepId(zxCtBalance.getAccountDepId());
           // 核算部门编号
           dbZxCtBalance.setAccountDepCode(zxCtBalance.getAccountDepCode());
           // 核算部门名称
           dbZxCtBalance.setAccountDepName(zxCtBalance.getAccountDepName());
           // 是否签认
           dbZxCtBalance.setIsSign(zxCtBalance.getIsSign());
           // 债权人编号
           dbZxCtBalance.setSecondCode(zxCtBalance.getSecondCode());
           // 债权人编号
           dbZxCtBalance.setSecondIDCodeBh(zxCtBalance.getSecondIDCodeBh());
           // 计量确认日期
           dbZxCtBalance.setConfirmDate(zxCtBalance.getConfirmDate());
           // 币种
           dbZxCtBalance.setCurrency(zxCtBalance.getCurrency());
           // 汇率
           dbZxCtBalance.setExchangeRate(zxCtBalance.getExchangeRate());
           // 计税方法
           dbZxCtBalance.setTaxCountWay(zxCtBalance.getTaxCountWay());
           // 附件张数
           dbZxCtBalance.setNumOfSheets(zxCtBalance.getNumOfSheets());
           // 摘要
           dbZxCtBalance.setSummary(zxCtBalance.getSummary());
           // 预计付款日期
           dbZxCtBalance.setEstPayDate(zxCtBalance.getEstPayDate());
           // 到期日期
           dbZxCtBalance.setExpDate(zxCtBalance.getExpDate());
           // 计算人
           dbZxCtBalance.setCountPerson(zxCtBalance.getCountPerson());
           // 推送状态
           dbZxCtBalance.setIsSend(zxCtBalance.getIsSend());
           // 所属公司ID
           dbZxCtBalance.setComID(zxCtBalance.getComID());
           // 推送时间
           dbZxCtBalance.setSendDate(zxCtBalance.getSendDate());
           // 财务审批状态说明
           dbZxCtBalance.setCwStatusRemark(zxCtBalance.getCwStatusRemark());
           // 所属公司名称
           dbZxCtBalance.setComName(zxCtBalance.getComName());
           // 复核人
           dbZxCtBalance.setReCountPerson(zxCtBalance.getReCountPerson());
           // 备注
           dbZxCtBalance.setRemarks(zxCtBalance.getRemarks());
           // 排序
           dbZxCtBalance.setSort(zxCtBalance.getSort());
           // 共通
           dbZxCtBalance.setModifyUserInfo(userKey, realName);
           flag = zxCtBalanceMapper.updateByPrimaryKey(dbZxCtBalance);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// 附件
        	ZxErpFile delErpFile = new ZxErpFile();
        	delErpFile.setOtherId(dbZxCtBalance.getId());
        	delErpFile.setOtherType("1");
        	zxErpFileServiceImpl.deleteAllZxErpFile(delErpFile);
        	
        	List<ZxErpFile> zxErpFileList = zxCtBalance.getAttachment();
        	if (zxErpFileList != null && zxErpFileList.size() > 0) {
				for (ZxErpFile zxErpFile : zxErpFileList) {
		        	zxErpFile.setOtherId(dbZxCtBalance.getId());
		        	zxErpFile.setOtherType("1");
		        	zxErpFileServiceImpl.saveZxErpFile(zxErpFile);
				}
			}
        	
            return repEntity.ok("sys.data.update",zxCtBalance);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtBalance(List<ZxCtBalance> zxCtBalanceList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtBalanceList != null && zxCtBalanceList.size() > 0) {
           ZxCtBalance zxCtBalance = new ZxCtBalance();
           zxCtBalance.setModifyUserInfo(userKey, realName);
           flag = zxCtBalanceMapper.batchDeleteUpdateZxCtBalance(zxCtBalanceList, zxCtBalance);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	for (ZxCtBalance zxCtBalance : zxCtBalanceList) {
        		// 附件
            	ZxErpFile delErpFile = new ZxErpFile();
            	delErpFile.setOtherId(zxCtBalance.getId());
            	delErpFile.setOtherType("1");
            	zxErpFileServiceImpl.deleteAllZxErpFile(delErpFile);
			}
        	
            return repEntity.ok("sys.data.delete",zxCtBalanceList);
        }
    }


    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @Override
	public ResponseEntity zxCtBalanceAudit(ZxCtBalance zxCtBalance) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
		if (zxCtBalance == null || StrUtil.isEmpty(zxCtBalance.getId())) {
			return repEntity.layerMessage("no", "主键ID不能为空！");
		}
		int flag = 0;
		ZxCtBalance dbZxCtBalance = zxCtBalanceMapper.selectByPrimaryKey(zxCtBalance.getId());
        if (dbZxCtBalance != null && StrUtil.isNotEmpty(dbZxCtBalance.getId())) {
        	dbZxCtBalance.setAuditStatus(zxCtBalance.getAuditStatus());
        	// 共通
            dbZxCtBalance.setModifyUserInfo(userKey, realName);
            flag = zxCtBalanceMapper.updateByPrimaryKey(dbZxCtBalance);
         }
         // 失败
         if (flag == 0) {
             return repEntity.errorUpdate();
         } else {
             return repEntity.ok("sys.data.update",dbZxCtBalance);
         }
	}
}
