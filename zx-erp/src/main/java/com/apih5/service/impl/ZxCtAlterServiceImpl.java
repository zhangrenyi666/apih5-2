package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtAlterMapper;
import com.apih5.mybatis.pojo.ZxCtAlter;
import com.apih5.mybatis.pojo.ZxCtWorkBook;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxCtAlterService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxCtAlterService")
public class ZxCtAlterServiceImpl implements ZxCtAlterService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtAlterMapper zxCtAlterMapper;
    
    @Autowired(required = true)
    private ZxErpFileServiceImpl zxErpFileServiceImpl;
    
    @Autowired(required = true)
    private ZxCtWorkBookServiceImpl zxCtWorkBookServiceImpl;

    @SuppressWarnings("unchecked")
	@Override
    public ResponseEntity getZxCtAlterListByCondition(ZxCtAlter zxCtAlter) {
        if (zxCtAlter == null) {
            zxCtAlter = new ZxCtAlter();
        }
        
        if (StrUtil.isNotEmpty(zxCtAlter.getOrgID())) {
        	ZxCtWorkBook zxCtWorkBook = new ZxCtWorkBook();
            zxCtWorkBook.setOrgID(zxCtAlter.getOrgID());
            List<ZxCtWorkBook> zxCtWorkBookList = (List<ZxCtWorkBook>) zxCtWorkBookServiceImpl.getZxCtWorkBookListByCondition(zxCtWorkBook).getData();
            if (zxCtWorkBookList != null && zxCtWorkBookList.size() > 0) {
            	zxCtAlter.setWorkBookID(zxCtWorkBookList.get(0).getId());
    		}
		}
        
        // 分页查询
        PageHelper.startPage(zxCtAlter.getPage(),zxCtAlter.getLimit());
        // 获取数据
        List<ZxCtAlter> zxCtAlterList = zxCtAlterMapper.selectByZxCtAlterList(zxCtAlter);
        // 得到分页信息
        PageInfo<ZxCtAlter> p = new PageInfo<>(zxCtAlterList);

        for (ZxCtAlter ctAlter : zxCtAlterList) {
        	// 附件
        	ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(ctAlter.getId());
        	zxErpFile.setOtherType("1");
        	List<ZxErpFile> zxErpFileList = (List<ZxErpFile>) zxErpFileServiceImpl.getZxErpFileListByCondition(zxErpFile).getData();
        	ctAlter.setAttachment(zxErpFileList);
		}
        
        return repEntity.okList(zxCtAlterList, p.getTotal());
    }

    @SuppressWarnings("unchecked")
	@Override
    public ResponseEntity getZxCtAlterDetail(ZxCtAlter zxCtAlter) {
        if (zxCtAlter == null) {
            zxCtAlter = new ZxCtAlter();
        }
        // 获取数据
        ZxCtAlter dbZxCtAlter = zxCtAlterMapper.selectByPrimaryKey(zxCtAlter.getId());
        // 数据存在
        if (dbZxCtAlter != null) {
        	// 附件
        	ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(dbZxCtAlter.getId());
        	zxErpFile.setOtherType("1");
        	List<ZxErpFile> zxErpFileList = (List<ZxErpFile>) zxErpFileServiceImpl.getZxErpFileListByCondition(zxErpFile).getData();
        	dbZxCtAlter.setAttachment(zxErpFileList);
        	
            return repEntity.ok(dbZxCtAlter);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @SuppressWarnings("unchecked")
	@Override
    public ResponseEntity saveZxCtAlter(ZxCtAlter zxCtAlter) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtAlter.setId(UuidUtil.generate());
        zxCtAlter.setCreateUserInfo(userKey, realName);
        zxCtAlter.setAuditStatus("0");
        
        // 根据orgID查询清单书主键id
        if (StrUtil.isEmpty(zxCtAlter.getOrgID())) {
			return repEntity.layerMessage("no", "项目ID不能为空！");
		}
        ZxCtWorkBook zxCtWorkBook = new ZxCtWorkBook();
        zxCtWorkBook.setOrgID(zxCtAlter.getOrgID());
        List<ZxCtWorkBook> zxCtWorkBookList = (List<ZxCtWorkBook>) zxCtWorkBookServiceImpl.getZxCtWorkBookListByCondition(zxCtWorkBook).getData();
        if (zxCtWorkBookList != null && zxCtWorkBookList.size() > 0) {
        	zxCtAlter.setWorkBookID(zxCtWorkBookList.get(0).getId());
		}
        
        int flag = zxCtAlterMapper.insert(zxCtAlter);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// 附件
        	List<ZxErpFile> zxErpFileList = zxCtAlter.getAttachment();
        	if (zxErpFileList != null && zxErpFileList.size() > 0) {
				for (ZxErpFile zxErpFile : zxErpFileList) {
		        	zxErpFile.setOtherId(zxCtAlter.getId());
		        	zxErpFile.setOtherType("1");
		        	zxErpFileServiceImpl.saveZxErpFile(zxErpFile);
				}
			}
        	
            return repEntity.ok("sys.data.sava", zxCtAlter);
        }
    }

    @Override
    public ResponseEntity updateZxCtAlter(ZxCtAlter zxCtAlter) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtAlter dbZxCtAlter = zxCtAlterMapper.selectByPrimaryKey(zxCtAlter.getId());
        if (dbZxCtAlter != null && StrUtil.isNotEmpty(dbZxCtAlter.getId())) {
           // 清单书ID(合同ID)
           dbZxCtAlter.setWorkBookID(zxCtAlter.getWorkBookID());
           // 变更等级
           dbZxCtAlter.setAlterLevel(zxCtAlter.getAlterLevel());
           // 提出单位
           dbZxCtAlter.setProposer(zxCtAlter.getProposer());
           // 变更内容
           dbZxCtAlter.setAlterContent(zxCtAlter.getAlterContent());
           // 变更原因
           dbZxCtAlter.setAlterReason(zxCtAlter.getAlterReason());
           // 发生时间
           dbZxCtAlter.setHappenDate(zxCtAlter.getHappenDate());
           // 申报金额
           dbZxCtAlter.setApplyAmount(zxCtAlter.getApplyAmount());
           // 申报日期
           dbZxCtAlter.setApplyDate(zxCtAlter.getApplyDate());
           // 申报文号
           dbZxCtAlter.setApplyNo(zxCtAlter.getApplyNo());
           // 申报延期天数
           dbZxCtAlter.setApplyDelay(zxCtAlter.getApplyDelay());
           // 业主批复金额
           dbZxCtAlter.setReplyAmount(zxCtAlter.getReplyAmount());
           // 业主批复日期
           dbZxCtAlter.setReplyDate(zxCtAlter.getReplyDate());
           // 批复文号
           dbZxCtAlter.setReplyNo(zxCtAlter.getReplyNo());
           // 批复延期天数
           dbZxCtAlter.setReplyDelay(zxCtAlter.getReplyDelay());
           // 业主批复状态
           dbZxCtAlter.setReplyStatus(zxCtAlter.getReplyStatus());
           // 记录人
           dbZxCtAlter.setRecorder(zxCtAlter.getRecorder());
           // 记录日期
           dbZxCtAlter.setRecordDate(zxCtAlter.getRecordDate());
           // 生效操作日期
           dbZxCtAlter.setTakeEffectDate(zxCtAlter.getTakeEffectDate());
           // 生效操作人
           dbZxCtAlter.setTakeEffectMan(zxCtAlter.getTakeEffectMan());
           // 审核状态
           dbZxCtAlter.setAuditStatus(zxCtAlter.getAuditStatus());
           // 需要公司协助
           dbZxCtAlter.setCompanyHelp(zxCtAlter.getCompanyHelp());
           // ？
           dbZxCtAlter.setCombProp(zxCtAlter.getCombProp());
           // 驻地监理确认金额
           dbZxCtAlter.setConfirmatAmountOfResidentSupervision(zxCtAlter.getConfirmatAmountOfResidentSupervision());
           // 驻地监理确认日期
           dbZxCtAlter.setConfirmatDateOfResidentSupervisor(zxCtAlter.getConfirmatDateOfResidentSupervisor());
           // 总监办确认金额
           dbZxCtAlter.setAmountConfirmedByDirectorOffice(zxCtAlter.getAmountConfirmedByDirectorOffice());
           // 总监办确认日期
           dbZxCtAlter.setDateConfirmedByDirectorOffice(zxCtAlter.getDateConfirmedByDirectorOffice());
           // pp5
           dbZxCtAlter.setPp5(zxCtAlter.getPp5());
           // pp6
           dbZxCtAlter.setPp6(zxCtAlter.getPp6());
           // pp7
           dbZxCtAlter.setPp7(zxCtAlter.getPp7());
           // pp8
           dbZxCtAlter.setPp8(zxCtAlter.getPp8());
           // pp9
           dbZxCtAlter.setPp9(zxCtAlter.getPp9());
           // pp10
           dbZxCtAlter.setPp10(zxCtAlter.getPp10());
           // 附件
           dbZxCtAlter.setAttachment(zxCtAlter.getAttachment());
           // 备注
           dbZxCtAlter.setRemarks(zxCtAlter.getRemarks());
           // 排序
           dbZxCtAlter.setSort(zxCtAlter.getSort());
           // 共通
           dbZxCtAlter.setModifyUserInfo(userKey, realName);
           flag = zxCtAlterMapper.updateByPrimaryKey(dbZxCtAlter);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// 附件
        	ZxErpFile delErpFile = new ZxErpFile();
        	delErpFile.setOtherId(dbZxCtAlter.getId());
        	delErpFile.setOtherType("1");
        	zxErpFileServiceImpl.deleteAllZxErpFile(delErpFile);
        	
        	List<ZxErpFile> zxErpFileList = zxCtAlter.getAttachment();
        	if (zxErpFileList != null && zxErpFileList.size() > 0) {
				for (ZxErpFile zxErpFile : zxErpFileList) {
		        	zxErpFile.setOtherId(dbZxCtAlter.getId());
		        	zxErpFile.setOtherType("1");
		        	zxErpFileServiceImpl.saveZxErpFile(zxErpFile);
				}
			}
        	
            return repEntity.ok("sys.data.update",zxCtAlter);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtAlter(List<ZxCtAlter> zxCtAlterList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtAlterList != null && zxCtAlterList.size() > 0) {
           ZxCtAlter zxCtAlter = new ZxCtAlter();
           zxCtAlter.setModifyUserInfo(userKey, realName);
           flag = zxCtAlterMapper.batchDeleteUpdateZxCtAlter(zxCtAlterList, zxCtAlter);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	for (ZxCtAlter zxCtAlter : zxCtAlterList) {
        		// 附件
            	ZxErpFile delErpFile = new ZxErpFile();
            	delErpFile.setOtherId(zxCtAlter.getId());
            	delErpFile.setOtherType("1");
            	zxErpFileServiceImpl.deleteAllZxErpFile(delErpFile);
			}
        	
            return repEntity.ok("sys.data.delete",zxCtAlterList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public ResponseEntity zxCtAlterAudit(ZxCtAlter zxCtAlter) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtAlter dbZxCtAlter = zxCtAlterMapper.selectByPrimaryKey(zxCtAlter.getId());
        if (dbZxCtAlter != null && StrUtil.isNotEmpty(dbZxCtAlter.getId())) {
           // 审核状态
           dbZxCtAlter.setAuditStatus("2"); // 审核中
           // 共通
           dbZxCtAlter.setModifyUserInfo(userKey, realName);
           flag = zxCtAlterMapper.updateByPrimaryKey(dbZxCtAlter);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtAlter);
        }
    }
    
    @Override
    public ResponseEntity zxCtAlterReporting(ZxCtAlter zxCtAlter) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZxCtAlter dbZxCtAlter = zxCtAlterMapper.selectByPrimaryKey(zxCtAlter.getId());
    	if (dbZxCtAlter != null && StrUtil.isNotEmpty(dbZxCtAlter.getId())) {
    		// 审核状态
    		dbZxCtAlter.setAuditStatus("1"); // 已上报
    		// 共通
    		dbZxCtAlter.setModifyUserInfo(userKey, realName);
    		flag = zxCtAlterMapper.updateByPrimaryKey(dbZxCtAlter);
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorSave();
    	} else {
    		return repEntity.ok("sys.data.update",zxCtAlter);
    	}
    }
}
