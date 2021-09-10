package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtContrClaimMapper;
import com.apih5.mybatis.pojo.ZxCtContrClaim;
import com.apih5.mybatis.pojo.ZxCtContrClaimItem;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxCtContrClaimService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxCtContrClaimService")
public class ZxCtContrClaimServiceImpl implements ZxCtContrClaimService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtContrClaimMapper zxCtContrClaimMapper;
    
    @Autowired(required = true)
    private ZxErpFileServiceImpl zxErpFileServiceImpl;
    
    @Autowired(required = true)
    private ZxCtContrClaimItemServiceImpl zxCtContrClaimItemServiceImpl;

    @SuppressWarnings("unchecked")
	@Override
    public ResponseEntity getZxCtContrClaimListByCondition(ZxCtContrClaim zxCtContrClaim) {
        if (zxCtContrClaim == null) {
            zxCtContrClaim = new ZxCtContrClaim();
        }
        // 分页查询
        PageHelper.startPage(zxCtContrClaim.getPage(),zxCtContrClaim.getLimit());
        // 获取数据
        List<ZxCtContrClaim> zxCtContrClaimList = zxCtContrClaimMapper.selectByZxCtContrClaimList(zxCtContrClaim);
        // 得到分页信息
        PageInfo<ZxCtContrClaim> p = new PageInfo<>(zxCtContrClaimList);

        for (ZxCtContrClaim ctContrClaim : zxCtContrClaimList) {
        	// 附件
        	ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(ctContrClaim.getId());
        	zxErpFile.setOtherType("1");
        	List<ZxErpFile> zxErpFileList = (List<ZxErpFile>) zxErpFileServiceImpl.getZxErpFileListByCondition(zxErpFile).getData();
        	ctContrClaim.setAttachment(zxErpFileList);
        	
        	// 索赔明细
        	ZxCtContrClaimItem zxCtContrClaimItem = new ZxCtContrClaimItem();
        	zxCtContrClaimItem.setClaimID(ctContrClaim.getId());
        	List<ZxCtContrClaimItem> zxCtContrClaimItemList = (List<ZxCtContrClaimItem>) zxCtContrClaimItemServiceImpl.getZxCtContrClaimItemListByCondition(zxCtContrClaimItem).getData();
        	ctContrClaim.setContrClaimItemList(zxCtContrClaimItemList);
        }
        
        return repEntity.okList(zxCtContrClaimList, p.getTotal());
    }

    @SuppressWarnings("unchecked")
	@Override
    public ResponseEntity getZxCtContrClaimDetail(ZxCtContrClaim zxCtContrClaim) {
        if (zxCtContrClaim == null) {
            zxCtContrClaim = new ZxCtContrClaim();
        }
        // 获取数据
        ZxCtContrClaim dbZxCtContrClaim = zxCtContrClaimMapper.selectByPrimaryKey(zxCtContrClaim.getId());
        // 数据存在
        if (dbZxCtContrClaim != null) {
        	// 附件
        	ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(dbZxCtContrClaim.getId());
        	zxErpFile.setOtherType("1");
        	List<ZxErpFile> zxErpFileList = (List<ZxErpFile>) zxErpFileServiceImpl.getZxErpFileListByCondition(zxErpFile).getData();
        	dbZxCtContrClaim.setAttachment(zxErpFileList);
        	
        	// 索赔明细
        	ZxCtContrClaimItem zxCtContrClaimItem = new ZxCtContrClaimItem();
        	zxCtContrClaimItem.setClaimID(dbZxCtContrClaim.getId());
        	List<ZxCtContrClaimItem> zxCtContrClaimItemList = (List<ZxCtContrClaimItem>) zxCtContrClaimItemServiceImpl.getZxCtContrClaimItemListByCondition(zxCtContrClaimItem).getData();
        	dbZxCtContrClaim.setContrClaimItemList(zxCtContrClaimItemList);
        	
            return repEntity.ok(dbZxCtContrClaim);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtContrClaim(ZxCtContrClaim zxCtContrClaim) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtContrClaim.setId(UuidUtil.generate());
        zxCtContrClaim.setCreateUserInfo(userKey, realName);
        int flag = zxCtContrClaimMapper.insert(zxCtContrClaim);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// 附件
        	List<ZxErpFile> zxErpFileList = zxCtContrClaim.getAttachment();
        	if (zxErpFileList != null && zxErpFileList.size() > 0) {
				for (ZxErpFile zxErpFile : zxErpFileList) {
		        	zxErpFile.setOtherId(zxCtContrClaim.getId());
		        	zxErpFile.setOtherType("1");
		        	zxErpFileServiceImpl.saveZxErpFile(zxErpFile);
				}
			}
        	
        	// 索赔明细
        	List<ZxCtContrClaimItem> zxCtContrClaimItemList = zxCtContrClaim.getContrClaimItemList();
        	if (zxCtContrClaimItemList != null && zxCtContrClaimItemList.size() > 0) {
				for (ZxCtContrClaimItem zxCtContrClaimItem : zxCtContrClaimItemList) {
					zxCtContrClaimItem.setClaimID(zxCtContrClaim.getId());
					zxCtContrClaimItemServiceImpl.saveZxCtContrClaimItem(zxCtContrClaimItem);
				}
			}
            return repEntity.ok("sys.data.sava", zxCtContrClaim);
        }
    }

    @Override
    public ResponseEntity updateZxCtContrClaim(ZxCtContrClaim zxCtContrClaim) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtContrClaim dbZxCtContrClaim = zxCtContrClaimMapper.selectByPrimaryKey(zxCtContrClaim.getId());
        if (dbZxCtContrClaim != null && StrUtil.isNotEmpty(dbZxCtContrClaim.getId())) {
           // 合同ID
           dbZxCtContrClaim.setContractID(zxCtContrClaim.getContractID());
           // 项目ID
           dbZxCtContrClaim.setOrgID(zxCtContrClaim.getOrgID());
           // 索赔等级
           dbZxCtContrClaim.setAlterLevel(zxCtContrClaim.getAlterLevel());
           // 要求索赔方
           dbZxCtContrClaim.setProposer(zxCtContrClaim.getProposer());
           // 主要内容
           dbZxCtContrClaim.setAlterContent(zxCtContrClaim.getAlterContent());
           // 主要原因
           dbZxCtContrClaim.setAlterReason(zxCtContrClaim.getAlterReason());
           // 发生时间
           dbZxCtContrClaim.setHappenDate(zxCtContrClaim.getHappenDate());
           // 申报金额
           dbZxCtContrClaim.setApplyAmount(zxCtContrClaim.getApplyAmount());
           // 申报日期
           dbZxCtContrClaim.setApplyDate(zxCtContrClaim.getApplyDate());
           // 申报文号
           dbZxCtContrClaim.setApplyNo(zxCtContrClaim.getApplyNo());
           // 申报延期天数
           dbZxCtContrClaim.setApplyDelay(zxCtContrClaim.getApplyDelay());
           // 批复金额
           dbZxCtContrClaim.setReplyAmount(zxCtContrClaim.getReplyAmount());
           // 批复日期
           dbZxCtContrClaim.setReplyDate(zxCtContrClaim.getReplyDate());
           // 批复文号
           dbZxCtContrClaim.setReplyNo(zxCtContrClaim.getReplyNo());
           // 批复延期天数
           dbZxCtContrClaim.setReplyDelay(zxCtContrClaim.getReplyDelay());
           // 批复状态
           dbZxCtContrClaim.setReplyStatus(zxCtContrClaim.getReplyStatus());
           // 需要公司协助
           dbZxCtContrClaim.setCompanyHelp(zxCtContrClaim.getCompanyHelp());
           // 生效操作日期
           dbZxCtContrClaim.setTakeEffectDate(zxCtContrClaim.getTakeEffectDate());
           // 生效操作人
           dbZxCtContrClaim.setTakeEffectMan(zxCtContrClaim.getTakeEffectMan());
           // 审核状态
           dbZxCtContrClaim.setAuditStatus(zxCtContrClaim.getAuditStatus());
           // 索赔工期
           dbZxCtContrClaim.setClaimPeriod(zxCtContrClaim.getClaimPeriod());
           // 记录人
           dbZxCtContrClaim.setRecorder(zxCtContrClaim.getRecorder());
           // 记录日期
           dbZxCtContrClaim.setRecordDate(zxCtContrClaim.getRecordDate());
           // 驻地监理确认金额
           dbZxCtContrClaim.setConfirmatAmountOfResidentSupervision(zxCtContrClaim.getConfirmatAmountOfResidentSupervision());
           // 驻地监理确认日期
           dbZxCtContrClaim.setConfirmatDateOfResidentSupervisor(zxCtContrClaim.getConfirmatDateOfResidentSupervisor());
           // 总监办确认金额
           dbZxCtContrClaim.setAmountConfirmedByDirectorOffice(zxCtContrClaim.getAmountConfirmedByDirectorOffice());
           // 总监办确认日期
           dbZxCtContrClaim.setDateConfirmedByDirectorOffice(zxCtContrClaim.getDateConfirmedByDirectorOffice());
           // pp5
           dbZxCtContrClaim.setPp5(zxCtContrClaim.getPp5());
           // pp6
           dbZxCtContrClaim.setPp6(zxCtContrClaim.getPp6());
           // pp7
           dbZxCtContrClaim.setPp7(zxCtContrClaim.getPp7());
           // pp8
           dbZxCtContrClaim.setPp8(zxCtContrClaim.getPp8());
           // pp9
           dbZxCtContrClaim.setPp9(zxCtContrClaim.getPp9());
           // pp10
           dbZxCtContrClaim.setPp10(zxCtContrClaim.getPp10());
           // 备注
           dbZxCtContrClaim.setRemarks(zxCtContrClaim.getRemarks());
           // 排序
           dbZxCtContrClaim.setSort(zxCtContrClaim.getSort());
           // 共通
           dbZxCtContrClaim.setModifyUserInfo(userKey, realName);
           flag = zxCtContrClaimMapper.updateByPrimaryKey(dbZxCtContrClaim);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// 附件
        	ZxErpFile delErpFile = new ZxErpFile();
        	delErpFile.setOtherId(dbZxCtContrClaim.getId());
        	delErpFile.setOtherType("1");
        	zxErpFileServiceImpl.deleteAllZxErpFile(delErpFile);
        	
        	List<ZxErpFile> zxErpFileList = zxCtContrClaim.getAttachment();
        	if (zxErpFileList != null && zxErpFileList.size() > 0) {
				for (ZxErpFile zxErpFile : zxErpFileList) {
		        	zxErpFile.setOtherId(dbZxCtContrClaim.getId());
		        	zxErpFile.setOtherType("1");
		        	zxErpFileServiceImpl.saveZxErpFile(zxErpFile);
				}
			}
        	
        	// 索赔明细
        	ZxCtContrClaimItem delZxCtContrClaimItem = new ZxCtContrClaimItem();
        	delZxCtContrClaimItem.setClaimID(dbZxCtContrClaim.getId());
        	zxCtContrClaimItemServiceImpl.delAllZxCtContrClaimItem(delZxCtContrClaimItem);
        	
        	List<ZxCtContrClaimItem> zxCtContrClaimItemList = zxCtContrClaim.getContrClaimItemList();
        	if (zxCtContrClaimItemList != null && zxCtContrClaimItemList.size() > 0) {
				for (ZxCtContrClaimItem zxCtContrClaimItem : zxCtContrClaimItemList) {
					zxCtContrClaimItem.setClaimID(dbZxCtContrClaim.getId());
					zxCtContrClaimItemServiceImpl.saveZxCtContrClaimItem(zxCtContrClaimItem);
				}
			}
            return repEntity.ok("sys.data.update",zxCtContrClaim);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtContrClaim(List<ZxCtContrClaim> zxCtContrClaimList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtContrClaimList != null && zxCtContrClaimList.size() > 0) {
           ZxCtContrClaim zxCtContrClaim = new ZxCtContrClaim();
           zxCtContrClaim.setModifyUserInfo(userKey, realName);
           flag = zxCtContrClaimMapper.batchDeleteUpdateZxCtContrClaim(zxCtContrClaimList, zxCtContrClaim);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	for (ZxCtContrClaim zxCtContrClaim : zxCtContrClaimList) {
        		// 附件
            	ZxErpFile delErpFile = new ZxErpFile();
            	delErpFile.setOtherId(zxCtContrClaim.getId());
            	delErpFile.setOtherType("1");
            	zxErpFileServiceImpl.deleteAllZxErpFile(delErpFile);
            	
            	// 索赔明细
            	ZxCtContrClaimItem delZxCtContrClaimItem = new ZxCtContrClaimItem();
            	delZxCtContrClaimItem.setClaimID(zxCtContrClaim.getId());
            	zxCtContrClaimItemServiceImpl.delAllZxCtContrClaimItem(delZxCtContrClaimItem);
			}
            return repEntity.ok("sys.data.delete",zxCtContrClaimList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
