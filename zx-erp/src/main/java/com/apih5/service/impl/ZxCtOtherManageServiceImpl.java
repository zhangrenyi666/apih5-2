package com.apih5.service.impl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.apih5.framework.constant.SysConst;
import com.apih5.mybatis.dao.*;
import com.apih5.mybatis.pojo.*;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.service.ZxCtOtherManageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtOtherManageService")
public class ZxCtOtherManageServiceImpl implements ZxCtOtherManageService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtOtherManageMapper zxCtOtherManageMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxCtOtherManageAmtRateMapper zxCtOtherManageAmtRateMapper;

    @Autowired(required = true)
    private ZxSaOtherEquipSettleMapper zxSaOtherEquipSettleMapper;

    @Autowired(required = true)
    private ZxCtOtherMapper zxCtOtherMapper;

    @Override
    public ResponseEntity getZxCtOtherManageListByCondition(ZxCtOtherManage zxCtOtherManage) {
        if (zxCtOtherManage == null) {
            zxCtOtherManage = new ZxCtOtherManage();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxCtOtherManage.setCompanyId("");
            zxCtOtherManage.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxCtOtherManage.setCompanyId(zxCtOtherManage.getOrgId());
            zxCtOtherManage.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxCtOtherManage.setOrgId(zxCtOtherManage.getOrgId());
        }
        // 分页查询
        PageHelper.startPage(zxCtOtherManage.getPage(),zxCtOtherManage.getLimit());
        // 获取数据
        List<ZxCtOtherManage> zxCtOtherManageList = zxCtOtherManageMapper.selectByZxCtOtherManageList(zxCtOtherManage);
        //查询附件
        for (ZxCtOtherManage otherManage : zxCtOtherManageList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(otherManage.getZxCtOtherManageId());
            zxErpFile.setOtherType("0");
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            otherManage.setZxErpFileList(zxErpFiles);

            // 保证金
            ZxCtOtherManageAmtRate zxCtOtherManageAmtRate = new ZxCtOtherManageAmtRate();
            zxCtOtherManageAmtRate.setZxCtOtherManageId(otherManage.getZxCtOtherManageId());
            List<ZxCtOtherManageAmtRate> zxCtOtherManageAmtRateList = zxCtOtherManageAmtRateMapper.selectByZxCtOtherManageAmtRateList(zxCtOtherManageAmtRate);
            otherManage.setZxCtOtherManageAmtRateList(zxCtOtherManageAmtRateList);
        }
        // 得到分页信息
        PageInfo<ZxCtOtherManage> p = new PageInfo<>(zxCtOtherManageList);
        return repEntity.okList(zxCtOtherManageList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtOtherManageDetail(ZxCtOtherManage zxCtOtherManage) {
        if (zxCtOtherManage == null) {
            zxCtOtherManage = new ZxCtOtherManage();
        }
        // 获取数据
        ZxCtOtherManage dbZxCtOtherManage = zxCtOtherManageMapper.selectByPrimaryKey(zxCtOtherManage.getZxCtOtherManageId());
        // 数据存在
        if (dbZxCtOtherManage != null) {
            //附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxCtOtherManage.getZxCtOtherManageId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            dbZxCtOtherManage.setZxErpFileList(zxErpFiles);

            // 保证金
            ZxCtOtherManageAmtRate zxCtOtherManageAmtRate = new ZxCtOtherManageAmtRate();
            zxCtOtherManageAmtRate.setZxCtOtherManageId(dbZxCtOtherManage.getZxCtOtherManageId());
            List<ZxCtOtherManageAmtRate> zxCtOtherManageAmtRateList = zxCtOtherManageAmtRateMapper.selectByZxCtOtherManageAmtRateList(zxCtOtherManageAmtRate);
            dbZxCtOtherManage.setZxCtOtherManageAmtRateList(zxCtOtherManageAmtRateList);
            return repEntity.ok(dbZxCtOtherManage);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtOtherManage(ZxCtOtherManage zxCtOtherManage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtOtherManage.setZxCtOtherManageId(UuidUtil.generate());
        zxCtOtherManage.setCreateUserInfo(userKey, realName);
        int flag = zxCtOtherManageMapper.insert(zxCtOtherManage);

        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtOtherManage);
        }
    }

    @Override
    public ResponseEntity updateZxCtOtherManage(ZxCtOtherManage zxCtOtherManage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtOtherManage dbZxCtOtherManage = zxCtOtherManageMapper.selectByPrimaryKey(zxCtOtherManage.getZxCtOtherManageId());
        if (dbZxCtOtherManage != null && StrUtil.isNotEmpty(dbZxCtOtherManage.getZxCtOtherManageId())) {
            // 合同名称
            dbZxCtOtherManage.setContractName(zxCtOtherManage.getContractName());
            // 合同内容
            dbZxCtOtherManage.setContent(zxCtOtherManage.getContent());
            // 合同甲方
            dbZxCtOtherManage.setFirstName(zxCtOtherManage.getFirstName());
            // 甲方代表身份证
            dbZxCtOtherManage.setFirstPrincipalIdCard(zxCtOtherManage.getFirstPrincipalIdCard());
            // 甲方联系电话
            dbZxCtOtherManage.setFirstUnitTel(zxCtOtherManage.getFirstUnitTel());
            // 乙方id
            dbZxCtOtherManage.setSecondId(zxCtOtherManage.getSecondId());
            // 乙方名称
            dbZxCtOtherManage.setSecondName(zxCtOtherManage.getSecondName());
            // 乙方代表
            dbZxCtOtherManage.setSecondPrincipal(zxCtOtherManage.getSecondPrincipal());
            // 乙方代表身份证
            dbZxCtOtherManage.setSecondPrincipalIdCard(zxCtOtherManage.getSecondPrincipalIdCard());
            // 乙方(固话)
            dbZxCtOtherManage.setSecondUnitTel(zxCtOtherManage.getSecondUnitTel());
            // 签订日期
            dbZxCtOtherManage.setSignatureDate(zxCtOtherManage.getSignatureDate());
            // 含税合同总价(万元)
            dbZxCtOtherManage.setContractCost(zxCtOtherManage.getContractCost());
            // 合同工期(天)
            dbZxCtOtherManage.setCsTimeLimit(zxCtOtherManage.getCsTimeLimit());
            // 合同开工日期
            dbZxCtOtherManage.setPlanStartDate(zxCtOtherManage.getPlanStartDate());
            // 实际开始时间
            dbZxCtOtherManage.setActualStartDate(zxCtOtherManage.getActualStartDate());
            // 合同竣工日期
            dbZxCtOtherManage.setPlanEndDate(zxCtOtherManage.getPlanEndDate());
            // 实际结束时间
            dbZxCtOtherManage.setActualEndDate(zxCtOtherManage.getActualEndDate());
            // 监理单位
            dbZxCtOtherManage.setConsultative(zxCtOtherManage.getConsultative());
            // 项目经理
            dbZxCtOtherManage.setProjectManager(zxCtOtherManage.getProjectManager());
            // 乙方性质
            dbZxCtOtherManage.setAgent(zxCtOtherManage.getAgent());
            // 乙方(手机)
            dbZxCtOtherManage.setSecondPhone(zxCtOtherManage.getSecondPhone());
            // 乙方(传真)
            dbZxCtOtherManage.setSecondFax(zxCtOtherManage.getSecondFax());
            // 合同类型
            dbZxCtOtherManage.setContractType(zxCtOtherManage.getContractType());
            // 合同类别
            dbZxCtOtherManage.setContractCategory(zxCtOtherManage.getContractCategory());
            // 变更后含税合同金额(万元)
            dbZxCtOtherManage.setAlterContractSum(zxCtOtherManage.getAlterContractSum());
            // 结算情况
            dbZxCtOtherManage.setSettleType(zxCtOtherManage.getSettleType());
            // 不含税合同总价(万元)
            dbZxCtOtherManage.setContractCostNoTax(zxCtOtherManage.getContractCostNoTax());
            // 合同税额(万元)
            dbZxCtOtherManage.setContractCostTax(zxCtOtherManage.getContractCostTax());
            // 变更后不含税合同金额(万元)
            dbZxCtOtherManage.setAlterContractSumNoTax(zxCtOtherManage.getAlterContractSumNoTax());
            // 变更后合同税额(万元)
            dbZxCtOtherManage.setAlterContractSumTax(zxCtOtherManage.getAlterContractSumTax());
            // 是否抵扣
            dbZxCtOtherManage.setIsDeduct(zxCtOtherManage.getIsDeduct());
            // 备注
            dbZxCtOtherManage.setRemark(zxCtOtherManage.getRemark());
            // 所属项目
            dbZxCtOtherManage.setOrgName(zxCtOtherManage.getOrgName());
            // 共通
            dbZxCtOtherManage.setModifyUserInfo(userKey, realName);
            flag = zxCtOtherManageMapper.updateByPrimaryKey(dbZxCtOtherManage);

            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxCtOtherManage.getZxCtOtherManageId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if (zxErpFiles != null && zxErpFiles.size() > 0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }

            //明细list
            List<ZxErpFile> fileList = zxCtOtherManage.getZxErpFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxCtOtherManage.getZxCtOtherManageId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtOtherManage);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtOtherManage(List<ZxCtOtherManage> zxCtOtherManageList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtOtherManageList != null && zxCtOtherManageList.size() > 0) {
            for (ZxCtOtherManage zxCtOtherManage : zxCtOtherManageList) {
                ZxSaOtherEquipSettle dbZxSaOtherEquipSettle = new ZxSaOtherEquipSettle();
                dbZxSaOtherEquipSettle.setZxCtOtherManageId(zxCtOtherManage.getZxCtOtherManageId());
                List<ZxSaOtherEquipSettle> dbZxSaOtherEquipSettleList = zxSaOtherEquipSettleMapper.selectByZxSaOtherEquipSettleList(dbZxSaOtherEquipSettle);
                if (dbZxSaOtherEquipSettleList != null && dbZxSaOtherEquipSettleList.size() > 0) {
                    return repEntity.layerMessage("no", "该合同已做结算，不能删除！");
                }
                // 删除其他合同评审数据
                ZxCtOther delZxCtOther = new ZxCtOther();
                delZxCtOther.setZxCtOtherId(zxCtOtherManage.getZxCtOtherId());
                List<ZxCtOther> zxCtOtherList = zxCtOtherMapper.selectByZxCtOtherList(delZxCtOther);
                if(zxCtOtherList != null && zxCtOtherList.size()>0) {
                    delZxCtOther.setModifyUserInfo(userKey, realName);
                    zxCtOtherMapper.batchDeleteUpdateZxCtOther(zxCtOtherList, delZxCtOther);
                }
                //删除附件
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxCtOtherManage.getZxCtOtherManageId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if(zxErpFiles != null && zxErpFiles.size()>0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }

                // 保证金
                ZxCtOtherManageAmtRate delZxCtOtherManageAmtRate = new ZxCtOtherManageAmtRate();
                delZxCtOtherManageAmtRate.setZxCtOtherManageId(zxCtOtherManage.getZxCtOtherManageId());
                List<ZxCtOtherManageAmtRate> zxCtOtherManageAmtRateList = zxCtOtherManageAmtRateMapper.selectByZxCtOtherManageAmtRateList(delZxCtOtherManageAmtRate);
                if(zxCtOtherManageAmtRateList != null && zxCtOtherManageAmtRateList.size()>0) {
                    delZxCtOtherManageAmtRate.setModifyUserInfo(userKey, realName);
                    zxCtOtherManageAmtRateMapper.batchDeleteUpdateZxCtOtherManageAmtRate(zxCtOtherManageAmtRateList, delZxCtOtherManageAmtRate);
                }
            }
           ZxCtOtherManage zxCtOtherManage = new ZxCtOtherManage();
           zxCtOtherManage.setModifyUserInfo(userKey, realName);
           flag = zxCtOtherManageMapper.batchDeleteUpdateZxCtOtherManage(zxCtOtherManageList, zxCtOtherManage);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtOtherManageList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public ResponseEntity zxCtOtherManageRegainExecute(ZxCtOtherManage zxCtOtherManage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtOtherManage dbZxCtOtherManage = zxCtOtherManageMapper.selectByPrimaryKey(zxCtOtherManage.getZxCtOtherManageId());
        if (dbZxCtOtherManage != null && StrUtil.isNotEmpty(dbZxCtOtherManage.getZxCtOtherId())) {
//            if ("3".equals(dbZxCtOtherManage.getContractStatus())) {
//                dbZxCtOtherManage.setContractStatus("1");
//            } else if ("1".equals(dbZxCtOtherManage.getContractStatus())) {
//                dbZxCtOtherManage.setContractStatus("3");
//            }
            dbZxCtOtherManage.setContractStatus("3".equals(dbZxCtOtherManage.getContractStatus())? "1" : "3");
            dbZxCtOtherManage.setModifyUserInfo(userKey, realName);
            flag = zxCtOtherManageMapper.updateByPrimaryKey(dbZxCtOtherManage);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtOtherManage);
        }
    }

    @Override
    public void exportZxCtOtherManage(ZxCtOtherManage zxCtOtherManage, HttpServletResponse response) {
        if (zxCtOtherManage == null) {
            zxCtOtherManage = new ZxCtOtherManage();
        }
        // 获取数据
        List<ZxCtOtherManage> zxCtOtherManageList = zxCtOtherManageMapper.selectByZxCtOtherManageList(zxCtOtherManage);
        // 表头
        List<List<?>> rowsList = Lists.newArrayList();
        List<?> row1 = CollUtil.newArrayList("合同编号",
                "合同名称",
                "管理机构",
                "合同类型",
                "合同甲方",
                "合同乙方",
                "合同录入类型",
                "含税合同总价（万元）",
                "变更后含税合同总价（万元）",
                "累计结算金额",
                "是否抵扣",
                "结算情况",
                "监理单位",
                "合同类别",
                "备注"
        );
        rowsList.add(row1);

        // 数据装载（与上面的表头对应）
        if (zxCtOtherManageList != null && zxCtOtherManageList.size() > 0) {
            for (ZxCtOtherManage dbZxCtOtherManage : zxCtOtherManageList) {
                rowsList.add(CollUtil.newArrayList(dbZxCtOtherManage.getContractNo(),
                        dbZxCtOtherManage.getContractName(),
                        dbZxCtOtherManage.getOrgName(),
                        dbZxCtOtherManage.getContractType(),
                        dbZxCtOtherManage.getFirstName(),
                        dbZxCtOtherManage.getSecondName(),
                        dbZxCtOtherManage.getAuditStatus(),
                        dbZxCtOtherManage.getContractCost(),
                        dbZxCtOtherManage.getAlterContractSum(),
                        dbZxCtOtherManage.getTotalSettleAmount(),
                        dbZxCtOtherManage.getIsDeduct(),
                        dbZxCtOtherManage.getSettleType(),
                        dbZxCtOtherManage.getConsultative(),
                        dbZxCtOtherManage.getContractCategory(),
                        dbZxCtOtherManage.getRemark()
                        )
                );
            }

            // 报表名称
            //String datestr = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MINUTE_PATTERN).replaceAll(":", "：");
            //String fileName = "结算单报表-" + datestr + ".xlsx";
            List<List<?>> rows = CollUtil.newArrayList(rowsList);

            // 通过工具类创建writer，创建xlsx格式
            ExcelWriter writer = ExcelUtil.getWriter(true);
            // 设置response下载弹窗
            // response.reset();
            //response为HttpServletResponse对象
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            // out为OutputStream，需要写出到的目标流
            ServletOutputStream out = null;
            try {
                response.setHeader("Content-Disposition",
                        "attachment; filename=\"" + new String("其他合同管理报表".getBytes("utf-8"), "iso-8859-1") + "\"");
                out = response.getOutputStream();
                // 一次性写出内容，使用默认样式，强制输出标题
                writer.write(rows, true);
                writer.flush(out, true);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // 关闭writer，释放内存
                if (writer != null) {
                    writer.close();
                }
                // 此处记得关闭输出Servlet流
                if (out != null) {
                    IoUtil.close(out);
                }
            }
        }
    }
}
