package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.mybatis.dao.ZxCcConstCoAlterWorkMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCcConstCoAlterMapper;
import com.apih5.service.ZxCcConstCoAlterService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCcConstCoAlterService")
public class ZxCcConstCoAlterServiceImpl implements ZxCcConstCoAlterService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCcConstCoAlterMapper zxCcConstCoAlterMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxCcConstCoAlterWorkMapper zxCcConstCoAlterWorkMapper;

    @Override
    public ResponseEntity getZxCcConstCoAlterListByCondition(ZxCcConstCoAlter zxCcConstCoAlter) {
        if (zxCcConstCoAlter == null) {
            zxCcConstCoAlter = new ZxCcConstCoAlter();
        }
        // 分页查询
        PageHelper.startPage(zxCcConstCoAlter.getPage(),zxCcConstCoAlter.getLimit());
        // 获取数据
        List<ZxCcConstCoAlter> zxCcConstCoAlterList = zxCcConstCoAlterMapper.selectByZxCcConstCoAlterList(zxCcConstCoAlter);

        //查询附件
        for (ZxCcConstCoAlter zZxCcConstCoAlter1 : zxCcConstCoAlterList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zZxCcConstCoAlter1.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zZxCcConstCoAlter1.setFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxCcConstCoAlter> p = new PageInfo<>(zxCcConstCoAlterList);

        return repEntity.okList(zxCcConstCoAlterList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCcConstCoAlterDetail(ZxCcConstCoAlter zxCcConstCoAlter) {
        if (zxCcConstCoAlter == null) {
            zxCcConstCoAlter = new ZxCcConstCoAlter();
        }
        // 获取数据
        ZxCcConstCoAlter dbZxCcConstCoAlter = zxCcConstCoAlterMapper.selectByPrimaryKey(zxCcConstCoAlter.getId());
        // 数据存在
        if (dbZxCcConstCoAlter != null) {
            //附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxCcConstCoAlter.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxCcConstCoAlter.setFileList(zxErpFiles);
            return repEntity.ok(dbZxCcConstCoAlter);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCcConstCoAlter(ZxCcConstCoAlter zxCcConstCoAlter) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCcConstCoAlter.setId(UuidUtil.generate());
        zxCcConstCoAlter.setCreateUserInfo(userKey, realName);
        int flag = zxCcConstCoAlterMapper.insert(zxCcConstCoAlter);

        //添加附件
        List<ZxErpFile> fileList = zxCcConstCoAlter.getFileList();
        if(fileList != null && fileList.size()>0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxCcConstCoAlter.getId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCcConstCoAlter);
        }
    }

    @Override
    public ResponseEntity updateZxCcConstCoAlter(ZxCcConstCoAlter zxCcConstCoAlter) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCcConstCoAlter dbZxCcConstCoAlter = zxCcConstCoAlterMapper.selectByPrimaryKey(zxCcConstCoAlter.getId());
        if (dbZxCcConstCoAlter != null && StrUtil.isNotEmpty(dbZxCcConstCoAlter.getId())) {
           // 协作清单书ID
           dbZxCcConstCoAlter.setCoBookId(zxCcConstCoAlter.getCoBookId());
           // 变更等级
           dbZxCcConstCoAlter.setAlterLevel(zxCcConstCoAlter.getAlterLevel());
           // 补充协议名称
           dbZxCcConstCoAlter.setProposer(zxCcConstCoAlter.getProposer());
           // 变更内容
           dbZxCcConstCoAlter.setAlterContent(zxCcConstCoAlter.getAlterContent());
           // 变更原因
           dbZxCcConstCoAlter.setAlterReason(zxCcConstCoAlter.getAlterReason());
           // 发生时间
           dbZxCcConstCoAlter.setHappenDate(zxCcConstCoAlter.getHappenDate());
           // 本期含税变更增减金额(万元)
           dbZxCcConstCoAlter.setApplyAmount(zxCcConstCoAlter.getApplyAmount());
           // 申报日期
           dbZxCcConstCoAlter.setApplyDate(zxCcConstCoAlter.getApplyDate());
           // 申报文号
           dbZxCcConstCoAlter.setApplyNo(zxCcConstCoAlter.getApplyNo());
           // 申报延期天数
           dbZxCcConstCoAlter.setApplyDelay(zxCcConstCoAlter.getApplyDelay());
           // 变更后含税合同金额(万元)
           dbZxCcConstCoAlter.setReplyAmount(zxCcConstCoAlter.getReplyAmount());
           // 日期
           dbZxCcConstCoAlter.setReplyDate(zxCcConstCoAlter.getReplyDate());
           // 补充协议书编号
           dbZxCcConstCoAlter.setReplyNo(zxCcConstCoAlter.getReplyNo());
           // 批复延期天数
           dbZxCcConstCoAlter.setReplyDelay(zxCcConstCoAlter.getReplyDelay());
           // 批复状态
           dbZxCcConstCoAlter.setReplyStatus(zxCcConstCoAlter.getReplyStatus());
           // 记录人
           dbZxCcConstCoAlter.setRecorder(zxCcConstCoAlter.getRecorder());
           // 记录日期
           dbZxCcConstCoAlter.setRecordDate(zxCcConstCoAlter.getRecordDate());
           // 生效操作日期
           dbZxCcConstCoAlter.setTakeEffectDate(zxCcConstCoAlter.getTakeEffectDate());
           // 生效操作人
           dbZxCcConstCoAlter.setTakeEffectMan(zxCcConstCoAlter.getTakeEffectMan());
           // auditStatus
           dbZxCcConstCoAlter.setAuditStatus(zxCcConstCoAlter.getAuditStatus());
           // combProp
           dbZxCcConstCoAlter.setCombProp(zxCcConstCoAlter.getCombProp());
           // 批复单位
           dbZxCcConstCoAlter.setReplyUnit(zxCcConstCoAlter.getReplyUnit());
           // 原含税合同金额(万元)
           dbZxCcConstCoAlter.setOriginalContractAmountTax(zxCcConstCoAlter.getOriginalContractAmountTax());
           // 评审ID
           dbZxCcConstCoAlter.setReviewId(zxCcConstCoAlter.getReviewId());
           // pp4
           dbZxCcConstCoAlter.setPp4(zxCcConstCoAlter.getPp4());
           // pp5
           dbZxCcConstCoAlter.setPp5(zxCcConstCoAlter.getPp5());
           // IP的合同ID
           dbZxCcConstCoAlter.setIpContractId(zxCcConstCoAlter.getIpContractId());
           // pp7
           dbZxCcConstCoAlter.setPp7(zxCcConstCoAlter.getPp7());
           // pp8
           dbZxCcConstCoAlter.setPp8(zxCcConstCoAlter.getPp8());
           // pp9
           dbZxCcConstCoAlter.setPp9(zxCcConstCoAlter.getPp9());
           // pp10
           dbZxCcConstCoAlter.setPp10(zxCcConstCoAlter.getPp10());
           // 合同ID
           dbZxCcConstCoAlter.setContractId(zxCcConstCoAlter.getContractId());
           // 机构ID
           dbZxCcConstCoAlter.setOrgId(zxCcConstCoAlter.getOrgId());
           // 本期不含税变更增减金额(万元)
           dbZxCcConstCoAlter.setApplyAmountNoTax(zxCcConstCoAlter.getApplyAmountNoTax());
           // 本期变更增减税额(万元)
           dbZxCcConstCoAlter.setApplyAmountTax(zxCcConstCoAlter.getApplyAmountTax());
           // 变更后不含税合同金额(万元)
           dbZxCcConstCoAlter.setReplyAmountNoTax(zxCcConstCoAlter.getReplyAmountNoTax());
           // 变更后合同税额(万元)
           dbZxCcConstCoAlter.setReplyAmountTax(zxCcConstCoAlter.getReplyAmountTax());
           // 原不含税合同金额(万元)
           dbZxCcConstCoAlter.setOriginalContractAmountNoTax(zxCcConstCoAlter.getOriginalContractAmountNoTax());
           // 原合同税额(万元)
           dbZxCcConstCoAlter.setOriginalContractTax(zxCcConstCoAlter.getOriginalContractTax());
           // 排序
           dbZxCcConstCoAlter.setSort(zxCcConstCoAlter.getSort());
           // 共通
           dbZxCcConstCoAlter.setModifyUserInfo(userKey, realName);
           flag = zxCcConstCoAlterMapper.updateByPrimaryKey(dbZxCcConstCoAlter);

            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(dbZxCcConstCoAlter.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCcConstCoAlter);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCcConstCoAlter(List<ZxCcConstCoAlter> zxCcConstCoAlterList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCcConstCoAlterList != null && zxCcConstCoAlterList.size() > 0) {
            for (ZxCcConstCoAlter zxCcConstCoAlter : zxCcConstCoAlterList) {
                // 删除明细
                ZxCcConstCoAlterWork zxCcConstCoAlterWork = new ZxCcConstCoAlterWork();
                zxCcConstCoAlterWork.setWorkId(zxCcConstCoAlter.getId());
                List<ZxCcConstCoAlterWork> zxCcConstCoAlterWorks = zxCcConstCoAlterWorkMapper.selectByZxCcConstCoAlterWorkList(zxCcConstCoAlterWork);
                if(zxCcConstCoAlterWorks != null && zxCcConstCoAlterWorks.size()>0) {
                    zxCcConstCoAlterWork.setModifyUserInfo(userKey, realName);
                    zxCcConstCoAlterWorkMapper.batchDeleteUpdateZxCcConstCoAlterWork(zxCcConstCoAlterWorks, zxCcConstCoAlterWork);
                }
                //删除附件
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxCcConstCoAlter.getId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if(zxErpFiles != null && zxErpFiles.size()>0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
            }
           ZxCcConstCoAlter zxCcConstCoAlter = new ZxCcConstCoAlter();
           zxCcConstCoAlter.setModifyUserInfo(userKey, realName);
           flag = zxCcConstCoAlterMapper.batchDeleteUpdateZxCcConstCoAlter(zxCcConstCoAlterList, zxCcConstCoAlter);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCcConstCoAlterList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    // 评审状态为“正在评审、评审通过、评审不通过”时，查看工程施工类补充协议清单明细
    @Override
    public ResponseEntity getZxCcConstCoAlterWorkDetail(ZxCcConstCoAlter zxCcConstCoAlter) {
        if (zxCcConstCoAlter == null) {
            zxCcConstCoAlter = new ZxCcConstCoAlter();
        }
        // 获取数据
        ZxCcConstCoAlter dbZxCcConstCoAlter = zxCcConstCoAlterMapper.selectByPrimaryKey(zxCcConstCoAlter.getId());
        // 数据存在
        if (dbZxCcConstCoAlter != null) {
            //工程施工类合同补充协议清单明细
            ZxCcConstCoAlterWork zxCcConstCoAlterWork = new ZxCcConstCoAlterWork();
            zxCcConstCoAlterWork.setWorkId(dbZxCcConstCoAlter.getId());
            List<ZxCcConstCoAlterWork> zxCcConstCoAlterWorks = zxCcConstCoAlterWorkMapper.selectByZxCcConstCoAlterWorkList(zxCcConstCoAlterWork);
            dbZxCcConstCoAlter.setZxCcConstCoAlterWorkList(zxCcConstCoAlterWorks);

            //附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxCcConstCoAlter.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            dbZxCcConstCoAlter.setFileList(zxErpFiles);
            return repEntity.ok(dbZxCcConstCoAlter);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    // 评审状态为“未评审”时，编辑工程施工类补充协议清单明细
    @Override
    public ResponseEntity updateZxCcConstCoAlterAndWorkDetail(ZxCcConstCoAlter zxCcConstCoAlter) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCcConstCoAlter dbZxCcConstCoAlter = zxCcConstCoAlterMapper.selectByPrimaryKey(zxCcConstCoAlter.getId());
        if (dbZxCcConstCoAlter != null && StrUtil.isNotEmpty(dbZxCcConstCoAlter.getId())) {
            // 协作清单书ID
            dbZxCcConstCoAlter.setCoBookId(zxCcConstCoAlter.getCoBookId());
            // 变更等级
            dbZxCcConstCoAlter.setAlterLevel(zxCcConstCoAlter.getAlterLevel());
            // 补充协议名称
            dbZxCcConstCoAlter.setProposer(zxCcConstCoAlter.getProposer());
            // 变更内容
            dbZxCcConstCoAlter.setAlterContent(zxCcConstCoAlter.getAlterContent());
            // 变更原因
            dbZxCcConstCoAlter.setAlterReason(zxCcConstCoAlter.getAlterReason());
            // 发生时间
            dbZxCcConstCoAlter.setHappenDate(zxCcConstCoAlter.getHappenDate());
            // 本期含税变更增减金额(万元)
            dbZxCcConstCoAlter.setApplyAmount(zxCcConstCoAlter.getApplyAmount());
            // 申报日期
            dbZxCcConstCoAlter.setApplyDate(zxCcConstCoAlter.getApplyDate());
            // 申报文号
            dbZxCcConstCoAlter.setApplyNo(zxCcConstCoAlter.getApplyNo());
            // 申报延期天数
            dbZxCcConstCoAlter.setApplyDelay(zxCcConstCoAlter.getApplyDelay());
            // 变更后含税合同金额(万元)
            dbZxCcConstCoAlter.setReplyAmount(zxCcConstCoAlter.getReplyAmount());
            // 日期
            dbZxCcConstCoAlter.setReplyDate(zxCcConstCoAlter.getReplyDate());
            // 补充协议书编号
            dbZxCcConstCoAlter.setReplyNo(zxCcConstCoAlter.getReplyNo());
            // 批复延期天数
            dbZxCcConstCoAlter.setReplyDelay(zxCcConstCoAlter.getReplyDelay());
            // 批复状态
            dbZxCcConstCoAlter.setReplyStatus(zxCcConstCoAlter.getReplyStatus());
            // 记录人
            dbZxCcConstCoAlter.setRecorder(zxCcConstCoAlter.getRecorder());
            // 记录日期
            dbZxCcConstCoAlter.setRecordDate(zxCcConstCoAlter.getRecordDate());
            // 生效操作日期
            dbZxCcConstCoAlter.setTakeEffectDate(zxCcConstCoAlter.getTakeEffectDate());
            // 生效操作人
            dbZxCcConstCoAlter.setTakeEffectMan(zxCcConstCoAlter.getTakeEffectMan());
            // auditStatus
            dbZxCcConstCoAlter.setAuditStatus(zxCcConstCoAlter.getAuditStatus());
            // combProp
            dbZxCcConstCoAlter.setCombProp(zxCcConstCoAlter.getCombProp());
            // 批复单位
            dbZxCcConstCoAlter.setReplyUnit(zxCcConstCoAlter.getReplyUnit());
            // 原含税合同金额(万元)
            dbZxCcConstCoAlter.setOriginalContractAmountTax(zxCcConstCoAlter.getOriginalContractAmountTax());
            // 评审ID
            dbZxCcConstCoAlter.setReviewId(zxCcConstCoAlter.getReviewId());
            // pp4
            dbZxCcConstCoAlter.setPp4(zxCcConstCoAlter.getPp4());
            // pp5
            dbZxCcConstCoAlter.setPp5(zxCcConstCoAlter.getPp5());
            // IP的合同ID
            dbZxCcConstCoAlter.setIpContractId(zxCcConstCoAlter.getIpContractId());
            // pp7
            dbZxCcConstCoAlter.setPp7(zxCcConstCoAlter.getPp7());
            // pp8
            dbZxCcConstCoAlter.setPp8(zxCcConstCoAlter.getPp8());
            // pp9
            dbZxCcConstCoAlter.setPp9(zxCcConstCoAlter.getPp9());
            // pp10
            dbZxCcConstCoAlter.setPp10(zxCcConstCoAlter.getPp10());
            // 合同ID
            dbZxCcConstCoAlter.setContractId(zxCcConstCoAlter.getContractId());
            // 机构ID
            dbZxCcConstCoAlter.setOrgId(zxCcConstCoAlter.getOrgId());
            // 本期不含税变更增减金额(万元)
            dbZxCcConstCoAlter.setApplyAmountNoTax(zxCcConstCoAlter.getApplyAmountNoTax());
            // 本期变更增减税额(万元)
            dbZxCcConstCoAlter.setApplyAmountTax(zxCcConstCoAlter.getApplyAmountTax());
            // 变更后不含税合同金额(万元)
            dbZxCcConstCoAlter.setReplyAmountNoTax(zxCcConstCoAlter.getReplyAmountNoTax());
            // 变更后合同税额(万元)
            dbZxCcConstCoAlter.setReplyAmountTax(zxCcConstCoAlter.getReplyAmountTax());
            // 原不含税合同金额(万元)
            dbZxCcConstCoAlter.setOriginalContractAmountNoTax(zxCcConstCoAlter.getOriginalContractAmountNoTax());
            // 原合同税额(万元)
            dbZxCcConstCoAlter.setOriginalContractTax(zxCcConstCoAlter.getOriginalContractTax());
            // 排序
            dbZxCcConstCoAlter.setSort(zxCcConstCoAlter.getSort());
            // 共通
            dbZxCcConstCoAlter.setModifyUserInfo(userKey, realName);
            flag = zxCcConstCoAlterMapper.updateByPrimaryKey(dbZxCcConstCoAlter);

            //删除在新增明细
            ZxCcConstCoAlterWork zxCcConstCoAlterWork = new ZxCcConstCoAlterWork();
            zxCcConstCoAlterWork.setWorkId(zxCcConstCoAlter.getId());
            List<ZxCcConstCoAlterWork> zxCcConstCoAlterWorks = zxCcConstCoAlterWorkMapper.selectByZxCcConstCoAlterWorkList(zxCcConstCoAlterWork);
            if(zxCcConstCoAlterWorks != null && zxCcConstCoAlterWorks.size()>0) {
                zxCcConstCoAlterWork.setModifyUserInfo(userKey, realName);
                zxCcConstCoAlterWorkMapper.batchDeleteUpdateZxCcConstCoAlterWork(zxCcConstCoAlterWorks, zxCcConstCoAlterWork);
            }
            //明细list
            List<ZxCcConstCoAlterWork> zxCcConstCoAlterWorkList = zxCcConstCoAlter.getZxCcConstCoAlterWorkList();
            if(zxCcConstCoAlterWorkList != null && zxCcConstCoAlterWorkList.size()>0) {
                for(ZxCcConstCoAlterWork zxCcConstCoAlterWork1 : zxCcConstCoAlterWorkList) {
                    zxCcConstCoAlterWork1.setId(UuidUtil.generate());
                    zxCcConstCoAlterWork1.setWorkId(zxCcConstCoAlter.getId());
                    zxCcConstCoAlterWork1.setCreateUserInfo(userKey, realName);
                    zxCcConstCoAlterWorkMapper.insert(zxCcConstCoAlterWork1);
                }
            }

            //修改在新增附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxCcConstCoAlter.getId());
            List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            if(zxErpFileList != null && zxErpFileList.size()>0) {
                zxErpFile.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFileList, zxErpFile);
            }
            // 附件list
            List<ZxErpFile> fileList = zxCcConstCoAlter.getFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile1 : fileList) {
                    zxErpFile1.setUid(UuidUtil.generate());
                    zxErpFile1.setOtherId(zxCcConstCoAlter.getId());
                    zxErpFile1.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile1);
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCcConstCoAlter);
        }
    }

}
