package com.apih5.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.mybatis.dao.ZxCtFsContractBondMapper;
import com.apih5.mybatis.dao.ZxCtFsContractMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.*;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCtFsContractReviewMapper;
import com.apih5.service.ZxCtFsContractReviewService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtFsContractReviewService")
public class ZxCtFsContractReviewServiceImpl implements ZxCtFsContractReviewService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtFsContractReviewMapper zxCtFsContractReviewMapper;

    @Autowired(required = true)
    private SequenceService sequenceService;

    @Autowired(required = true)
    private ZxCtFsContractMapper zxCtFsContractMapper;

    @Autowired(required = true)
    private ZxCtFsContractBondMapper zxCtFsContractBondMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Override
    public ResponseEntity getZxCtFsContractReviewListByCondition(ZxCtFsContractReview zxCtFsContractReview) {
        if (zxCtFsContractReview == null) {
            zxCtFsContractReview = new ZxCtFsContractReview();
        }

        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxCtFsContractReview.setComId("");
            zxCtFsContractReview.setOrgId("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxCtFsContractReview.setComId(zxCtFsContractReview.getOrgId());
            zxCtFsContractReview.setOrgId("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxCtFsContractReview.setOrgId(zxCtFsContractReview.getOrgId());
        }

        // 分页查询
        PageHelper.startPage(zxCtFsContractReview.getPage(), zxCtFsContractReview.getLimit());
        // 获取数据
        List<ZxCtFsContractReview> zxCtFsContractReviewList = zxCtFsContractReviewMapper.selectByZxCtFsContractReviewList(zxCtFsContractReview);

        for (ZxCtFsContractReview zxCtFsContractReview1 : zxCtFsContractReviewList) {
            ZxErpFile file = new ZxErpFile();
            file.setOtherId(zxCtFsContractReview1.getContractReviewId());
            List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(file);
            zxCtFsContractReview1.setZxErpFileList(zxErpFileList);
        }

        // 得到分页信息
        PageInfo<ZxCtFsContractReview> p = new PageInfo<>(zxCtFsContractReviewList);

        return repEntity.okList(zxCtFsContractReviewList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtFsContractReviewDetail(ZxCtFsContractReview zxCtFsContractReview) {
        if (zxCtFsContractReview == null) {
            zxCtFsContractReview = new ZxCtFsContractReview();
        }
        ZxCtFsContractReview dbZxCtFsContractReview = new ZxCtFsContractReview();
        // 获取数据
        if (zxCtFsContractReview.getContractReviewId() != "") {
            dbZxCtFsContractReview = zxCtFsContractReviewMapper.selectByPrimaryKey(zxCtFsContractReview.getContractReviewId());
        } else if (zxCtFsContractReview.getWorkId() != null) {
            dbZxCtFsContractReview = zxCtFsContractReviewMapper.selectByWorkId(zxCtFsContractReview.getWorkId());
        }

        // 数据存在
        if (dbZxCtFsContractReview != null) {
            ZxErpFile file = new ZxErpFile();
            file.setOtherType("1");
            file.setOtherId(dbZxCtFsContractReview.getContractReviewId());
            List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(file);
            dbZxCtFsContractReview.setZxErpFileList(zxErpFileList);
            file.setOtherType("2");
            List<ZxErpFile> zhengWenFileList = zxErpFileMapper.selectByZxErpFileList(file);
            dbZxCtFsContractReview.setZxZhengWenFileList(zhengWenFileList);
            return repEntity.ok(dbZxCtFsContractReview);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtFsContractReview(ZxCtFsContractReview zxCtFsContractReview) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        //zxCtFsContractReviewMapper.selectLeiJi(zxCtFsContractReview.getContractNo());
        ZxCtFsContractReview zxCtFsContractReview1 = new ZxCtFsContractReview();
        zxCtFsContractReview1.setContractNo(zxCtFsContractReview.getContractNo());
        List<ZxCtFsContractReview> list1 = zxCtFsContractReviewMapper.selectByZxCtFsContractReviewList(zxCtFsContractReview1);
        if (list1.size() > 0) {
            return repEntity.layerMessage("no", "附属合同编号已存在");
        }
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtFsContractReview.setContractReviewId(UuidUtil.generate());
        zxCtFsContractReview.setCreateUserInfo(userKey, realName);
        zxCtFsContractReview.getFromContractNo();
        zxCtFsContractReview.setBeginPer(realName);
        zxCtFsContractReview.setApih5FlowStatus("-1");
        int flag = zxCtFsContractReviewMapper.insert(zxCtFsContractReview);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            List<ZxErpFile> zxErpFileList = zxCtFsContractReview.getZxErpFileList();
            if (zxErpFileList != null && zxErpFileList.size() > 0) {
                for (ZxErpFile zxErpFile : zxErpFileList) {
                    zxErpFile.setOtherId(zxCtFsContractReview.getContractReviewId());
                    zxErpFile.setOtherType("1");
                    zxErpFile.setUid((UuidUtil.generate()));
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    flag = zxErpFileMapper.insert(zxErpFile);
                    if (flag == 0) {
                        return repEntity.layerMessage("no", "附件上传失败！");
                    }
                }
            }
            return repEntity.ok("sys.data.sava", zxCtFsContractReview);
        }
    }

    @Override
    public ResponseEntity updateZxCtFsContractReview(ZxCtFsContractReview zxCtFsContractReview) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtFsContractReview dbZxCtFsContractReview = zxCtFsContractReviewMapper.selectByPrimaryKey(zxCtFsContractReview.getContractReviewId());
        if (dbZxCtFsContractReview != null && StrUtil.isNotEmpty(dbZxCtFsContractReview.getContractReviewId())) {
            if (!dbZxCtFsContractReview.getIsDeduct().equals(zxCtFsContractReview.getIsDeduct())) {

            }
            // 附属合同编号
            dbZxCtFsContractReview.setContractNo(zxCtFsContractReview.getContractNo());
            // 附属合同名称
            dbZxCtFsContractReview.setContractName(zxCtFsContractReview.getContractName());
            // 合同类型
            dbZxCtFsContractReview.setContractType(zxCtFsContractReview.getContractType());
            // 含税合同金额(万元)
            dbZxCtFsContractReview.setContractCost(zxCtFsContractReview.getContractCost());
            // 甲方ID
            dbZxCtFsContractReview.setFirstId(zxCtFsContractReview.getFirstId());
            // 甲方名称
            dbZxCtFsContractReview.setFirstName(zxCtFsContractReview.getFirstName());
            // 乙方ID
            dbZxCtFsContractReview.setSecondId(zxCtFsContractReview.getSecondId());
            // 乙方名称
            dbZxCtFsContractReview.setSecondName(zxCtFsContractReview.getSecondName());
            // 开工日期
            dbZxCtFsContractReview.setStartDate(zxCtFsContractReview.getStartDate());
            // 竣工日期
            dbZxCtFsContractReview.setEndDate(zxCtFsContractReview.getEndDate());
            // 合同工期
            dbZxCtFsContractReview.setCsTimeLimit(zxCtFsContractReview.getCsTimeLimit());
            // 合同签定人
            dbZxCtFsContractReview.setAgent(zxCtFsContractReview.getAgent());
            // 合同内容
            dbZxCtFsContractReview.setContent(zxCtFsContractReview.getContent());
            // 评审状态
            dbZxCtFsContractReview.setStatus(zxCtFsContractReview.getStatus());
            // combProp
            dbZxCtFsContractReview.setCombProp(zxCtFsContractReview.getCombProp());
            // 乙方手机
            dbZxCtFsContractReview.setPp1(zxCtFsContractReview.getPp1());
            // 乙方传真
            dbZxCtFsContractReview.setPp2(zxCtFsContractReview.getPp2());
            // 合同类型
            dbZxCtFsContractReview.setPp3(zxCtFsContractReview.getPp3());
            // 累计结算金额
            dbZxCtFsContractReview.setPp4(zxCtFsContractReview.getPp4());
            // 累计支付金额
            dbZxCtFsContractReview.setPp5(zxCtFsContractReview.getPp5());
            // pp6
            dbZxCtFsContractReview.setPp6(zxCtFsContractReview.getPp6());
            // pp7
            dbZxCtFsContractReview.setPp7(zxCtFsContractReview.getPp7());
            // 清单
            dbZxCtFsContractReview.setPp8(zxCtFsContractReview.getPp8());
            // pp9
            dbZxCtFsContractReview.setPp9(zxCtFsContractReview.getPp9());
            // 资金流向
            dbZxCtFsContractReview.setPp10(zxCtFsContractReview.getPp10());
            // 流程实例ID
            dbZxCtFsContractReview.setInstProcessId(zxCtFsContractReview.getInstProcessId());
            // 公文任务ID
            dbZxCtFsContractReview.setWorkitemId(zxCtFsContractReview.getWorkitemId());
            // 编码
            dbZxCtFsContractReview.setCode(zxCtFsContractReview.getCode());
            // 机构编码
            dbZxCtFsContractReview.setCode1(zxCtFsContractReview.getCode1());
            // 承建单位简称
            dbZxCtFsContractReview.setCode2(zxCtFsContractReview.getCode2());
            // 中标单位简称
            dbZxCtFsContractReview.setCode3(zxCtFsContractReview.getCode3());
            // 项目所在省份简称
            dbZxCtFsContractReview.setCode4(zxCtFsContractReview.getCode4());
            // 项目简称
            dbZxCtFsContractReview.setCode5(zxCtFsContractReview.getCode5());
            // 标段号
            dbZxCtFsContractReview.setCode6(zxCtFsContractReview.getCode6());
            // 合同类别
            dbZxCtFsContractReview.setCode7(zxCtFsContractReview.getCode7());
            // 合同序号
            dbZxCtFsContractReview.setCode8(zxCtFsContractReview.getCode8());
            // 业主合同功能码
            dbZxCtFsContractReview.setCodeP1(zxCtFsContractReview.getCodeP1());
            // 同一公司
            dbZxCtFsContractReview.setCode2T3(zxCtFsContractReview.getCode2T3());
            // 是否局审批
            dbZxCtFsContractReview.setIsFlag(zxCtFsContractReview.getIsFlag());
            // 发送局判断ID
            dbZxCtFsContractReview.setSendToJuId(zxCtFsContractReview.getSendToJuId());
            // 上期末变更后金额
            dbZxCtFsContractReview.setUpAlterContractSum(zxCtFsContractReview.getUpAlterContractSum());
            // 是否局指审批
            dbZxCtFsContractReview.setIsFlagZhb(zxCtFsContractReview.getIsFlagZhb());
            // 发送局指判断ID
            dbZxCtFsContractReview.setSendToZhbId(zxCtFsContractReview.getSendToZhbId());
            // 原合同名称
            dbZxCtFsContractReview.setFromContractName(zxCtFsContractReview.getFromContractName());
            // comId
            dbZxCtFsContractReview.setComId(zxCtFsContractReview.getComId());
            // 原合同编号ID
            dbZxCtFsContractReview.setFromContractId(zxCtFsContractReview.getFromContractId());
            // 原合同编号
            dbZxCtFsContractReview.setFromContractNo(zxCtFsContractReview.getFromContractNo());
            // 机构ID
            dbZxCtFsContractReview.setOrgId(zxCtFsContractReview.getOrgId());
            // 协作单位类型
            dbZxCtFsContractReview.setSecondOrgType(zxCtFsContractReview.getSecondOrgType());
            // 不含税合同金额（万元）
            dbZxCtFsContractReview.setContractCostNoTax(zxCtFsContractReview.getContractCostNoTax());
            // 合同税额
            dbZxCtFsContractReview.setContractCostTax(zxCtFsContractReview.getContractCostTax());
            // 税率
            dbZxCtFsContractReview.setTaxRate(zxCtFsContractReview.getTaxRate());

            // 上期末变更后金额不含税
            dbZxCtFsContractReview.setUpAlterContractSumNoTax(zxCtFsContractReview.getUpAlterContractSumNoTax());
            // 上期末变更后金额含税
            dbZxCtFsContractReview.setUpAlterContractSumTax(zxCtFsContractReview.getUpAlterContractSumTax());
            // 附属合同id
            dbZxCtFsContractReview.setZxCtFsContractId(zxCtFsContractReview.getZxCtFsContractId());
            // 备注
            dbZxCtFsContractReview.setRemarks(zxCtFsContractReview.getRemarks());
            // 排序
            dbZxCtFsContractReview.setSort(zxCtFsContractReview.getSort());

            dbZxCtFsContractReview.setApih5FlowId(zxCtFsContractReview.getApih5FlowId());
            dbZxCtFsContractReview.setApih5FlowStatus(zxCtFsContractReview.getApih5FlowStatus());
            dbZxCtFsContractReview.setApih5FlowNodeStatus(zxCtFsContractReview.getApih5FlowNodeStatus());
            dbZxCtFsContractReview.setWorkId(zxCtFsContractReview.getWorkId());
            if (StrUtil.equals("opinionField1", zxCtFsContractReview.getOpinionField(), true)) {
                dbZxCtFsContractReview.setOpinionField1(zxCtFsContractReview.getOpinionContent(realName,
                        dbZxCtFsContractReview.getOpinionField1()));
            }
            //
            if (StrUtil.equals("opinionField2", zxCtFsContractReview.getOpinionField(), true)) {
                dbZxCtFsContractReview.setOpinionField2(zxCtFsContractReview.getOpinionContent(realName, dbZxCtFsContractReview.getOpinionField2()));
            }
            //
            if (StrUtil.equals("opinionField3", zxCtFsContractReview.getOpinionField(), true)) {
                dbZxCtFsContractReview.setOpinionField3(zxCtFsContractReview.getOpinionContent(realName, dbZxCtFsContractReview.getOpinionField3()));
            }
            //
            if (StrUtil.equals("opinionField4", zxCtFsContractReview.getOpinionField(), true)) {
                dbZxCtFsContractReview.setOpinionField4(zxCtFsContractReview.getOpinionContent(realName, dbZxCtFsContractReview.getOpinionField4()));
            }
            //
            if (StrUtil.equals("opinionField5", zxCtFsContractReview.getOpinionField(), true)) {
                dbZxCtFsContractReview.setOpinionField5(zxCtFsContractReview.getOpinionContent(realName, dbZxCtFsContractReview.getOpinionField5()));
            }
            //
            if (StrUtil.equals("opinionField6", zxCtFsContractReview.getOpinionField(), true)) {
                dbZxCtFsContractReview.setOpinionField6(zxCtFsContractReview.getOpinionContent(realName, dbZxCtFsContractReview.getOpinionField6()));
            }
            //
            if (StrUtil.equals("opinionField7", zxCtFsContractReview.getOpinionField(), true)) {
                dbZxCtFsContractReview.setOpinionField7(zxCtFsContractReview.getOpinionContent(realName, dbZxCtFsContractReview.getOpinionField7()));
            }
            //
            if (StrUtil.equals("opinionField8", zxCtFsContractReview.getOpinionField(), true)) {
                dbZxCtFsContractReview.setOpinionField8(zxCtFsContractReview.getOpinionContent(realName, dbZxCtFsContractReview.getOpinionField8()));
            }
            //
            if (StrUtil.equals("opinionField9", zxCtFsContractReview.getOpinionField(), true)) {
                dbZxCtFsContractReview.setOpinionField9(zxCtFsContractReview.getOpinionContent(realName, dbZxCtFsContractReview.getOpinionField9()));
            }
            //
            if (StrUtil.equals("opinionField10", zxCtFsContractReview.getOpinionField(), true)) {
                dbZxCtFsContractReview.setOpinionField10(zxCtFsContractReview.getOpinionContent(realName, dbZxCtFsContractReview.getOpinionField10()));
            }
//            if(){
//
//            }
            // 是否抵扣
            dbZxCtFsContractReview.setIsDeduct(zxCtFsContractReview.getIsDeduct());
            // 共通
            dbZxCtFsContractReview.setModifyUserInfo(userKey, realName);
            if ("2".equals(zxCtFsContractReview.getApih5FlowStatus())) {
                flag = synContract(dbZxCtFsContractReview);
                if (flag == 0) {
                    return repEntity.errorSave();
                }
            }
            flag = zxCtFsContractReviewMapper.updateByPrimaryKey(dbZxCtFsContractReview);
            //附件先删除再新增
            ZxErpFile delFile = new ZxErpFile();
            delFile.setOtherId(zxCtFsContractReview.getContractReviewId());
            List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
            if (delFileList != null && delFileList.size() > 0) {
                delFile.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
            }
            if (zxCtFsContractReview.getZxErpFileList() != null && zxCtFsContractReview.getZxErpFileList().size() > 0) {
                for (ZxErpFile file : zxCtFsContractReview.getZxErpFileList()) {
                    file.setUid(UuidUtil.generate());
                    file.setOtherId(zxCtFsContractReview.getContractReviewId());
                    file.setOtherType("1");
                    file.setCreateUserInfo(userKey, realName);
                    flag = zxErpFileMapper.insert(file);
                }
            }
            if (zxCtFsContractReview.getZxZhengWenFileList() != null && zxCtFsContractReview.getZxZhengWenFileList().size() > 0) {
                for (ZxErpFile file : zxCtFsContractReview.getZxZhengWenFileList()) {
                    file.setUid(UuidUtil.generate());
                    file.setOtherId(zxCtFsContractReview.getContractReviewId());
                    file.setOtherType("2");
                    file.setCreateUserInfo(userKey, realName);
                    flag = zxErpFileMapper.insert(file);
                }
            }

        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxCtFsContractReview);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtFsContractReview(List<ZxCtFsContractReview> zxCtFsContractReviewList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String token = TokenUtils.getToken(request);
        int flag = 0;
        JSONArray jsonArr = new JSONArray();
        if (zxCtFsContractReviewList != null && zxCtFsContractReviewList.size() > 0) {

            for (ZxCtFsContractReview zxCtFsReview : zxCtFsContractReviewList) {
                if (StrUtil.isNotEmpty(zxCtFsReview.getWorkId())) {
                    jsonArr.add(zxCtFsReview.getWorkId());
                }
            }
            ZxCtFsContractReview zxCtFsContractReview = new ZxCtFsContractReview();
            zxCtFsContractReview.setModifyUserInfo(userKey, realName);
            flag = zxCtFsContractReviewMapper.batchDeleteUpdateZxCtFsContractReview(zxCtFsContractReviewList, zxCtFsContractReview);
            if (jsonArr.size() > 0) {
                HttpUtil.sendPostToken(HttpUtil.getUrl(request) + "batchDeleteFlow", jsonArr.toString(), token);
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxCtFsContractReviewList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    /**
     * 同步新增附属合同
     *
     * @param zxCtFsContractReview
     * @author suncg
     */
    private int synContract(ZxCtFsContractReview zxCtFsContractReview) {
        int flag = 0;
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);

        ZxCtFsContract zxCtFsContract = new ZxCtFsContract();
        zxCtFsContract.setZxCtFsContractId(UuidUtil.generate()); // 主键
        zxCtFsContract.setSettleType("实际无结算");
        zxCtFsContract.setAudit("评审通过");
        zxCtFsContract.setSecondPrincipal(zxCtFsContractReview.getAgent());//乙方代表
        zxCtFsContract.setContractNo(zxCtFsContractReview.getContractNo());// 合同编号
        zxCtFsContract.setContractName(zxCtFsContractReview.getContractName()); // 合同名称
        zxCtFsContract.setContractType(zxCtFsContractReview.getContractType());  // 合同类型
        zxCtFsContract.setContent(zxCtFsContractReview.getContent()); // 合同内容
        zxCtFsContract.setOrgID(zxCtFsContractReview.getOrgId());// 所属项目ID
        zxCtFsContract.setFirstId(zxCtFsContractReview.getFirstId());    // 甲方ID
        zxCtFsContract.setFirstName(zxCtFsContractReview.getFirstName()); // 合同甲方
        zxCtFsContract.setSecondID(zxCtFsContractReview.getSecondId());//乙方ID
        zxCtFsContract.setSecondName(zxCtFsContractReview.getSecondName());  // 合同乙方
        zxCtFsContract.setContractCost(zxCtFsContractReview.getContractCost());//含税合同总价(万元)
        zxCtFsContract.setCsTimeLimit(zxCtFsContractReview.getCsTimeLimit());// 合同工期(天)
        zxCtFsContract.setPlanStartDate(zxCtFsContractReview.getStartDate());// 合同开始时间(开工日期)
        zxCtFsContract.setPlanEndDate(zxCtFsContractReview.getEndDate()); //合同结束时间(竣工日期)
        zxCtFsContract.setContractStatus("1");// 合同状态(评审状态)
        zxCtFsContract.setProjectName(zxCtFsContractReview.getFirstName());
        zxCtFsContract.setProjectNo(zxCtFsContractReview.getOrgId());
        zxCtFsContract.setShortName(zxCtFsContractReview.getCode5()); // 项目简称
        zxCtFsContract.setFaxRate(zxCtFsContractReview.getTaxRate());// 税率
        zxCtFsContract.setPp1(zxCtFsContractReview.getCodeP1());    // 乙方(手机)
        zxCtFsContract.setPp2(zxCtFsContractReview.getPp2()); // 乙方(传真)
        zxCtFsContract.setPp3(zxCtFsContractReview.getPp3());  // 合同类型
        zxCtFsContract.setPp4(zxCtFsContractReview.getPp4()); // 累计结算金额
        zxCtFsContract.setPp5(zxCtFsContractReview.getPp5()); //  累计支付金额
        zxCtFsContract.setPp9(zxCtFsContractReview.getPp9()); //  pp9
        zxCtFsContract.setPp10(zxCtFsContractReview.getPp10()); //   资金流向
        zxCtFsContract.setCode(zxCtFsContractReview.getCode());// 编码
        zxCtFsContract.setCode1(zxCtFsContractReview.getCode1());// 机构编码
        zxCtFsContract.setCode2(zxCtFsContractReview.getCode2());// 承建单位简称
        zxCtFsContract.setCode3(zxCtFsContractReview.getCode3());// 中标单位简称
        zxCtFsContract.setCode4(zxCtFsContractReview.getCode4());// 项目所在省份简称
        zxCtFsContract.setCode5(zxCtFsContractReview.getCode5());  // 项目简称
        zxCtFsContract.setCode6(zxCtFsContractReview.getCode6());// 标段号
        zxCtFsContract.setCode7(zxCtFsContractReview.getCode7()); // 合同类别
        zxCtFsContract.setCode8(zxCtFsContractReview.getCode8());// 合同序号
        zxCtFsContract.setTaxRate(String.valueOf(zxCtFsContractReview.getTaxRate()));//税率
        zxCtFsContract.setIsDeduct(zxCtFsContractReview.getIsDeduct());// 是否抵扣
        zxCtFsContract.setContractCostNoTax(zxCtFsContractReview.getContractCostNoTax());// 不含税合同总价(万元)
        zxCtFsContract.setContractCostTax(zxCtFsContractReview.getContractCostTax());// 合同税额
        zxCtFsContract.setComID(zxCtFsContractReview.getComId());//公司ID
        zxCtFsContract.setContractReviewId(zxCtFsContractReview.getContractReviewId());// 附属合同评审ID
        zxCtFsContract.setDelFlag("0");
        zxCtFsContract.setWorkId(zxCtFsContractReview.getWorkId());
        zxCtFsContract.setRemarks(zxCtFsContractReview.getRemarks());
        zxCtFsContract.setFromContractNo(zxCtFsContractReview.getFromContractNo());
        zxCtFsContract.setSignatureDate(new Date());
        zxCtFsContract.setCreateUserInfo(userKey, realName);
        flag = zxCtFsContractMapper.insert(zxCtFsContract);

        return flag;
    }

    /**
     * 根据原合同编号生成附属合同编号（用作前段预览）
     *
     * @param zxCtFsContractReview
     * @author suncg
     */

    @Override
    public ResponseEntity getZxCtFsContractNo(ZxCtFsContractReview zxCtFsContractReview) {
        if (zxCtFsContractReview == null) {
            repEntity.layerMessage("no", "原合同编号不能为空");
        } else if (zxCtFsContractReview.getFromContractNo().equals("")) {
            repEntity.layerMessage("no", "原合同编号不能为空");
        }

        int num = zxCtFsContractReviewMapper.selectLeiJi(zxCtFsContractReview.getFromContractNo()) + 1;
        zxCtFsContractReview.setContractNo(zxCtFsContractReview.getFromContractNo() + "-" + "附" + String.format("%03d", num));
        //ZxCtFsContractReview dbZxCtFsContractReview = new ZxCtFsContractReview();
        return repEntity.ok(zxCtFsContractReview);

    }

    /**
     * 导出附属合同评审列表
     *
     * @param zxCtFsContractReview
     * @author suncg
     */

    @Override
    public ResponseEntity exportContractReview(ZxCtFsContractReview zxCtFsContractReview, HttpServletResponse response) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        // HttpServletResponse response = new HttpServletResponse();
        if (zxCtFsContractReview == null) {
            zxCtFsContractReview = new ZxCtFsContractReview();
        }

        // 获取数据
        List<ZxCtFsContractReview> zxCtFsContractReviewList = zxCtFsContractReviewMapper.selectByZxCtFsContractReviewList(zxCtFsContractReview);
        // 表头
        List<List<?>> rowsList = Lists.newArrayList();
        List<?> row1 = CollUtil.newArrayList("附属合同编号", "附属合同名称", "合同类型", "原合同编号", "合同类别", "甲方名称",
                "乙方名称", "协作单位类型", "合同签订人", "含税合同金额（万元）", "是否抵扣", "合同工期", "发起人", "评审状态");
        rowsList.add(row1);

        // 数据装载（与上面的表头对应）
        if (zxCtFsContractReviewList != null && zxCtFsContractReviewList.size() > 0) {
            for (ZxCtFsContractReview zxCtFsContractReview1 : zxCtFsContractReviewList) {
                rowsList.add(CollUtil.newArrayList(zxCtFsContractReview1.getContractNo(),
                        zxCtFsContractReview1.getContractName(),
                        zxCtFsContractReview1.getContractType(),
                        zxCtFsContractReview1.getFromContractNo(),
                        zxCtFsContractReview1.getCode7(),
                        zxCtFsContractReview1.getFirstName(),
                        zxCtFsContractReview1.getSecondName(),
                        zxCtFsContractReview1.getSecondOrgType(),
                        zxCtFsContractReview1.getAgent(),
                        zxCtFsContractReview1.getContractCost(),
                        zxCtFsContractReview1.getIsDeduct(),
                        zxCtFsContractReview1.getCsTimeLimit(),
                        zxCtFsContractReview1.getBeginPer(),
                        zxCtFsContractReview1.getStatus()

                        )
                );
            }

            // 报表名称
            String datestr = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MINUTE_PATTERN).replaceAll(":", "：");
            String fileName = "xxx报表-" + datestr + ".xlsx";

            List<List<?>> rows = CollUtil.newArrayList(rowsList);
            // 通过工具类创建writer，创建xlsx格式
            ExcelWriter writer = ExcelUtil.getWriter(true);
            // 设置response下载弹窗
            // response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            // out为OutputStream，需要写出到的目标流
            ServletOutputStream out = null;
            try {
                response.setHeader("Content-Disposition",
                        "attachment; filename=\"" + new String("fdsafdsa".getBytes("utf-8"), "iso-8859-1") + "\"");
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

            //String url = HttpUtil.
            return null;
        } else {
            return repEntity.ok("无数据");
        }
    }


//    @Override
//    public ResponseEntity updateZxCtFsContractReview(ZxCtFsContractReview zxCtFsContractReview) {
//        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
//        String userKey = TokenUtils.getUserKey(request);
//        String realName = TokenUtils.getRealName(request);
//        int flag = 0;
//        ZxCtFsContractReview dbZxCtFsContractReview = zxCtFsContractReviewMapper.selectByPrimaryKey(zxCtFsContractReview.getContractReviewId());
//        if (dbZxCtFsContractReview != null && StrUtil.isNotEmpty(dbZxCtFsContractReview.getContractReviewId())) {
//            if(!dbZxCtFsContractReview.getIsDeduct().equals(zxCtFsContractReview.getIsDeduct())){
//
//            }
//            // 附属合同编号
//            dbZxCtFsContractReview.setContractNo(zxCtFsContractReview.getContractNo());
//            // 附属合同名称
//            dbZxCtFsContractReview.setContractName(zxCtFsContractReview.getContractName());
//            // 合同类型
//            dbZxCtFsContractReview.setContractType(zxCtFsContractReview.getContractType());
//            // 含税合同金额(万元)
//            dbZxCtFsContractReview.setContractCost(zxCtFsContractReview.getContractCost());
//            // 甲方ID
//            dbZxCtFsContractReview.setFirstId(zxCtFsContractReview.getFirstId());
//            // 甲方名称
//            dbZxCtFsContractReview.setFirstName(zxCtFsContractReview.getFirstName());
//            // 乙方ID
//            dbZxCtFsContractReview.setSecondId(zxCtFsContractReview.getSecondId());
//            // 乙方名称
//            dbZxCtFsContractReview.setSecondName(zxCtFsContractReview.getSecondName());
//            // 开工日期
//            dbZxCtFsContractReview.setStartDate(zxCtFsContractReview.getStartDate());
//            // 竣工日期
//            dbZxCtFsContractReview.setEndDate(zxCtFsContractReview.getEndDate());
//            // 合同工期
//            dbZxCtFsContractReview.setCsTimeLimit(zxCtFsContractReview.getCsTimeLimit());
//            // 合同签定人
//            dbZxCtFsContractReview.setAgent(zxCtFsContractReview.getAgent());
//            // 合同内容
//            dbZxCtFsContractReview.setContent(zxCtFsContractReview.getContent());
//            // 评审状态
//            dbZxCtFsContractReview.setStatus(zxCtFsContractReview.getStatus());
//            // combProp
//            dbZxCtFsContractReview.setCombProp(zxCtFsContractReview.getCombProp());
//            // 乙方手机
//            dbZxCtFsContractReview.setPp1(zxCtFsContractReview.getPp1());
//            // 乙方传真
//            dbZxCtFsContractReview.setPp2(zxCtFsContractReview.getPp2());
//            // 合同类型
//            dbZxCtFsContractReview.setPp3(zxCtFsContractReview.getPp3());
//            // 累计结算金额
//            dbZxCtFsContractReview.setPp4(zxCtFsContractReview.getPp4());
//            // 累计支付金额
//            dbZxCtFsContractReview.setPp5(zxCtFsContractReview.getPp5());
//            // pp6
//            dbZxCtFsContractReview.setPp6(zxCtFsContractReview.getPp6());
//            // pp7
//            dbZxCtFsContractReview.setPp7(zxCtFsContractReview.getPp7());
//            // 清单
//            dbZxCtFsContractReview.setPp8(zxCtFsContractReview.getPp8());
//            // pp9
//            dbZxCtFsContractReview.setPp9(zxCtFsContractReview.getPp9());
//            // 资金流向
//            dbZxCtFsContractReview.setPp10(zxCtFsContractReview.getPp10());
//            // 流程实例ID
//            dbZxCtFsContractReview.setInstProcessId(zxCtFsContractReview.getInstProcessId());
//            // 公文任务ID
//            dbZxCtFsContractReview.setWorkitemId(zxCtFsContractReview.getWorkitemId());
//            // 编码
//            dbZxCtFsContractReview.setCode(zxCtFsContractReview.getCode());
//            // 机构编码
//            dbZxCtFsContractReview.setCode1(zxCtFsContractReview.getCode1());
//            // 承建单位简称
//            dbZxCtFsContractReview.setCode2(zxCtFsContractReview.getCode2());
//            // 中标单位简称
//            dbZxCtFsContractReview.setCode3(zxCtFsContractReview.getCode3());
//            // 项目所在省份简称
//            dbZxCtFsContractReview.setCode4(zxCtFsContractReview.getCode4());
//            // 项目简称
//            dbZxCtFsContractReview.setCode5(zxCtFsContractReview.getCode5());
//            // 标段号
//            dbZxCtFsContractReview.setCode6(zxCtFsContractReview.getCode6());
//            // 合同类别
//            dbZxCtFsContractReview.setCode7(zxCtFsContractReview.getCode7());
//            // 合同序号
//            dbZxCtFsContractReview.setCode8(zxCtFsContractReview.getCode8());
//            // 业主合同功能码
//            dbZxCtFsContractReview.setCodeP1(zxCtFsContractReview.getCodeP1());
//            // 同一公司
//            dbZxCtFsContractReview.setCode2T3(zxCtFsContractReview.getCode2T3());
//            // 是否局审批
//            dbZxCtFsContractReview.setIsFlag(zxCtFsContractReview.getIsFlag());
//            // 发送局判断ID
//            dbZxCtFsContractReview.setSendToJuId(zxCtFsContractReview.getSendToJuId());
//            // 上期末变更后金额
//            dbZxCtFsContractReview.setUpAlterContractSum(zxCtFsContractReview.getUpAlterContractSum());
//            // 是否局指审批
//            dbZxCtFsContractReview.setIsFlagZhb(zxCtFsContractReview.getIsFlagZhb());
//            // 发送局指判断ID
//            dbZxCtFsContractReview.setSendToZhbId(zxCtFsContractReview.getSendToZhbId());
//            // 原合同名称
//            dbZxCtFsContractReview.setFromContractName(zxCtFsContractReview.getFromContractName());
//            // comId
//            dbZxCtFsContractReview.setComId(zxCtFsContractReview.getComId());
//            // 原合同编号ID
//            dbZxCtFsContractReview.setFromContractId(zxCtFsContractReview.getFromContractId());
//            // 原合同编号
//            dbZxCtFsContractReview.setFromContractNo(zxCtFsContractReview.getFromContractNo());
//            // 机构ID
//            dbZxCtFsContractReview.setOrgId(zxCtFsContractReview.getOrgId());
//            // 协作单位类型
//            dbZxCtFsContractReview.setSecondOrgType(zxCtFsContractReview.getSecondOrgType());
//            // 不含税合同金额（万元）
//            dbZxCtFsContractReview.setContractCostNoTax(zxCtFsContractReview.getContractCostNoTax());
//            // 合同税额
//            dbZxCtFsContractReview.setContractCostTax(zxCtFsContractReview.getContractCostTax());
//            // 税率
//            dbZxCtFsContractReview.setTaxRate(zxCtFsContractReview.getTaxRate());
//
//            // 上期末变更后金额不含税
//            dbZxCtFsContractReview.setUpAlterContractSumNoTax(zxCtFsContractReview.getUpAlterContractSumNoTax());
//            // 上期末变更后金额含税
//            dbZxCtFsContractReview.setUpAlterContractSumTax(zxCtFsContractReview.getUpAlterContractSumTax());
//            // 附属合同id
//            dbZxCtFsContractReview.setZxCtFsContractId(zxCtFsContractReview.getZxCtFsContractId());
//            // 备注
//            dbZxCtFsContractReview.setRemarks(zxCtFsContractReview.getRemarks());
//            // 排序
//            dbZxCtFsContractReview.setSort(zxCtFsContractReview.getSort());
//
//            dbZxCtFsContractReview.setOpinionField1(zxCtFsContractReview.getOpinionField1());
//            dbZxCtFsContractReview.setOpinionField2(zxCtFsContractReview.getOpinionField2());
//            dbZxCtFsContractReview.setOpinionField3(zxCtFsContractReview.getOpinionField3());
//            dbZxCtFsContractReview.setOpinionField4(zxCtFsContractReview.getOpinionField4());
//            dbZxCtFsContractReview.setOpinionField5(zxCtFsContractReview.getOpinionField5());
//            dbZxCtFsContractReview.setOpinionField6(zxCtFsContractReview.getOpinionField6());
//            dbZxCtFsContractReview.setOpinionField7(zxCtFsContractReview.getOpinionField7());
//            dbZxCtFsContractReview.setOpinionField8(zxCtFsContractReview.getOpinionField8());
//            dbZxCtFsContractReview.setOpinionField9(zxCtFsContractReview.getOpinionField9());
//            dbZxCtFsContractReview.setOpinionField10(zxCtFsContractReview.getOpinionField10());
//            dbZxCtFsContractReview.setApih5FlowId(zxCtFsContractReview.getApih5FlowId());
//            dbZxCtFsContractReview.setApih5FlowStatus(zxCtFsContractReview.getApih5FlowStatus());
//            dbZxCtFsContractReview.setApih5FlowNodeStatus(zxCtFsContractReview.getApih5FlowNodeStatus());
//            dbZxCtFsContractReview.setWorkId(zxCtFsContractReview.getWorkId());
////            if(){
////
////            }
//            // 是否抵扣
//            dbZxCtFsContractReview.setIsDeduct(zxCtFsContractReview.getIsDeduct());
//            // 共通
//            dbZxCtFsContractReview.setModifyUserInfo(userKey, realName);
//            if("2".equals(zxCtFsContractReview.getApih5FlowStatus())){
//                flag=  synContract(dbZxCtFsContractReview);
//                if (flag==0){
//                    return repEntity.errorSave();
//                }
//            }
//            flag = zxCtFsContractReviewMapper.updateByPrimaryKey(dbZxCtFsContractReview);
//
//        }
//        // 失败
//        if (flag == 0) {
//            return repEntity.errorSave();
//        } else {
//            return repEntity.ok("sys.data.update",zxCtFsContractReview);
//        }
//    }

}
