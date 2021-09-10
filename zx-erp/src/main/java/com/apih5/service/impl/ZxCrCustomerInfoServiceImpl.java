package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCrCustomerInfoMapper;
import com.apih5.mybatis.pojo.ZxCrCustomerInfo;
import com.apih5.service.ZxCrCustomerInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.StringUtils;

@Service("zxCrCustomerInfoService")
public class ZxCrCustomerInfoServiceImpl implements ZxCrCustomerInfoService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCrCustomerInfoMapper zxCrCustomerInfoMapper;

    @Override
    public ResponseEntity getZxCrCustomerInfoListByCondition(ZxCrCustomerInfo zxCrCustomerInfo) {
        if (zxCrCustomerInfo == null) {
            zxCrCustomerInfo = new ZxCrCustomerInfo();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCrCustomerInfo.setCompanyId("");
        	zxCrCustomerInfo.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxCrCustomerInfo.setCompanyId(zxCrCustomerInfo.getOrgID());
        	zxCrCustomerInfo.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxCrCustomerInfo.setOrgID(zxCrCustomerInfo.getOrgID());
        }
        
        // 分页查询
        PageHelper.startPage(zxCrCustomerInfo.getPage(),zxCrCustomerInfo.getLimit());
        // 获取数据
        List<ZxCrCustomerInfo> zxCrCustomerInfoList = zxCrCustomerInfoMapper.selectByZxCrCustomerInfoList(zxCrCustomerInfo);
        // 判断证照是否过期 0 过期 1 未过期 并更新状态
        ZxCrCustomerInfo updateCustomerInfo = new ZxCrCustomerInfo();
        for (ZxCrCustomerInfo crCustomerInfo : zxCrCustomerInfoList) {
            Date now = new Date();
            if (crCustomerInfo.getLicenceDate().before(now) ||
                crCustomerInfo.getQualificateDate().before(now) ||
                crCustomerInfo.getTaxRegDate().before(now) ||
                crCustomerInfo.getSafeBookDate().before(now)
            ) {
                crCustomerInfo.setDateStatus("0");
                if (!"0".equals(crCustomerInfo.getDateStatus())) {
                    updateCustomerInfo.setZxCrCustomerInfoId(crCustomerInfo.getZxCrCustomerInfoId());
                    updateCustomerInfo.setDateStatus("0");
                    zxCrCustomerInfoMapper.updateByPrimaryKeySelective(updateCustomerInfo);
                }
            } else {
                crCustomerInfo.setDateStatus("1");
                if (!"1".equals(crCustomerInfo.getDateStatus())) {
                    updateCustomerInfo.setZxCrCustomerInfoId(crCustomerInfo.getZxCrCustomerInfoId());
                    updateCustomerInfo.setDateStatus("1");
                    zxCrCustomerInfoMapper.updateByPrimaryKeySelective(updateCustomerInfo);
                }
            }
        }

        // 得到分页信息
        PageInfo<ZxCrCustomerInfo> p = new PageInfo<>(zxCrCustomerInfoList);

        return repEntity.okList(zxCrCustomerInfoList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCrCustomerInfoDetail(ZxCrCustomerInfo zxCrCustomerInfo) {
        if (zxCrCustomerInfo == null) {
            zxCrCustomerInfo = new ZxCrCustomerInfo();
        }
        // 获取数据
        ZxCrCustomerInfo dbZxCrCustomerInfo = zxCrCustomerInfoMapper.selectByPrimaryKey(zxCrCustomerInfo.getZxCrCustomerInfoId());
        // 数据存在
        if (dbZxCrCustomerInfo != null) {
            return repEntity.ok(dbZxCrCustomerInfo);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCrCustomerInfo(ZxCrCustomerInfo zxCrCustomerInfo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCrCustomerInfo.setZxCrCustomerInfoId(UuidUtil.generate());
        zxCrCustomerInfo.setCreateUserInfo(userKey, realName);
        int flag = zxCrCustomerInfoMapper.insert(zxCrCustomerInfo);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCrCustomerInfo);
        }
    }

    @Override
    public ResponseEntity updateZxCrCustomerInfo(ZxCrCustomerInfo zxCrCustomerInfo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCrCustomerInfo dbZxCrCustomerInfo = zxCrCustomerInfoMapper.selectByPrimaryKey(zxCrCustomerInfo.getZxCrCustomerInfoId());
        if (dbZxCrCustomerInfo != null && StrUtil.isNotEmpty(dbZxCrCustomerInfo.getZxCrCustomerInfoId())) {
           // 统一社会信用代码
           dbZxCrCustomerInfo.setOrgCertificate(zxCrCustomerInfo.getOrgCertificate());
           // 协作单位名称
           dbZxCrCustomerInfo.setCustomerName(zxCrCustomerInfo.getCustomerName());
           // 注册资本金(元)
           dbZxCrCustomerInfo.setRegMoney(zxCrCustomerInfo.getRegMoney());
           // 法定代表人
           dbZxCrCustomerInfo.setCorparation(zxCrCustomerInfo.getCorparation());
           // 法定代表人身份证号码
           dbZxCrCustomerInfo.setPricinpalIDCard(zxCrCustomerInfo.getPricinpalIDCard());
           // 法定代表人电话
           dbZxCrCustomerInfo.setPricinpalMobile(zxCrCustomerInfo.getPricinpalMobile());
           // 企业详细地址
           dbZxCrCustomerInfo.setPricinpalAddr(zxCrCustomerInfo.getPricinpalAddr());
           // 营业执照注册号
           dbZxCrCustomerInfo.setLicenceNO(zxCrCustomerInfo.getLicenceNO());
           // 企业资质证书编号
           dbZxCrCustomerInfo.setQualificateNo(zxCrCustomerInfo.getQualificateNo());
           // 企业税务登记证号
           dbZxCrCustomerInfo.setTaxRegNo(zxCrCustomerInfo.getTaxRegNo());
           // 企业资质等级
           dbZxCrCustomerInfo.setQualifiLevel(zxCrCustomerInfo.getQualifiLevel());
           // 证照是否过期
           dbZxCrCustomerInfo.setDateStatus(zxCrCustomerInfo.getDateStatus());
           // 是否战略供应商
           dbZxCrCustomerInfo.setStrategicSupplier(zxCrCustomerInfo.getStrategicSupplier());
           // 银行授信额度
           dbZxCrCustomerInfo.setCreditLineAmt(zxCrCustomerInfo.getCreditLineAmt());
           // 推荐单位
           dbZxCrCustomerInfo.setReferenceOrg(zxCrCustomerInfo.getReferenceOrg());
           // 推荐人姓名
           dbZxCrCustomerInfo.setReferenceName(zxCrCustomerInfo.getReferenceName());
           // 推荐人职务
           dbZxCrCustomerInfo.setReferencePost(zxCrCustomerInfo.getReferencePost());
           // 推荐人联系电话
           dbZxCrCustomerInfo.setReferencePhone(zxCrCustomerInfo.getReferencePhone());
           // 曾用名
           dbZxCrCustomerInfo.setUsedNames(zxCrCustomerInfo.getUsedNames());
           // 黑名单
           dbZxCrCustomerInfo.setIsBlack(zxCrCustomerInfo.getIsBlack());
           // 项目名称
           dbZxCrCustomerInfo.setOrgName(zxCrCustomerInfo.getOrgName());
           // 状态
           dbZxCrCustomerInfo.setAuditStatus(zxCrCustomerInfo.getAuditStatus());
           // 所在省份
           dbZxCrCustomerInfo.setProvince(zxCrCustomerInfo.getProvince());
           // 所在区域
           dbZxCrCustomerInfo.setArea(zxCrCustomerInfo.getArea());
           // 营业范围
           dbZxCrCustomerInfo.setScope(zxCrCustomerInfo.getScope());
           // 安全生产许可证编码
           dbZxCrCustomerInfo.setSafeCode(zxCrCustomerInfo.getSafeCode());
           // 纳税人识别号
           dbZxCrCustomerInfo.setTaxpayerNum(zxCrCustomerInfo.getTaxpayerNum());
           // 纳税人类别
           dbZxCrCustomerInfo.setTaxpayerType(zxCrCustomerInfo.getTaxpayerType());
           // 开户行名称
           dbZxCrCustomerInfo.setBankName(zxCrCustomerInfo.getBankName());
           // 开户行账号
           dbZxCrCustomerInfo.setBankAccount(zxCrCustomerInfo.getBankAccount());
           // 是否已占号
           dbZxCrCustomerInfo.setUseFlag(zxCrCustomerInfo.getUseFlag());
           // 实缴资本金（元）
           dbZxCrCustomerInfo.setRealRegMoney(zxCrCustomerInfo.getRealRegMoney());
           // 企业性质
           dbZxCrCustomerInfo.setBusinessType(zxCrCustomerInfo.getBusinessType());
           // 营业执照有效期至
           dbZxCrCustomerInfo.setLicenceDate(zxCrCustomerInfo.getLicenceDate());
           // 企业资质证书有效期至
           dbZxCrCustomerInfo.setQualificateDate(zxCrCustomerInfo.getQualificateDate());
           // 企业税务登记有效期至
           dbZxCrCustomerInfo.setTaxRegDate(zxCrCustomerInfo.getTaxRegDate());
           // 安全生产许可证有效期至
           dbZxCrCustomerInfo.setSafeBookDate(zxCrCustomerInfo.getSafeBookDate());
           // 占号维护单位
           dbZxCrCustomerInfo.setComOrgName(zxCrCustomerInfo.getComOrgName());
           // 是否需要复审
           dbZxCrCustomerInfo.setIsNeedfushen(zxCrCustomerInfo.getIsNeedfushen());
           // 复核状态
           dbZxCrCustomerInfo.setFuheStatus(zxCrCustomerInfo.getFuheStatus());
           // 复审状态
           dbZxCrCustomerInfo.setFushenStatus(zxCrCustomerInfo.getFushenStatus());
           // 备注
           dbZxCrCustomerInfo.setRemarks(zxCrCustomerInfo.getRemarks());
           // 排序
           dbZxCrCustomerInfo.setSort(zxCrCustomerInfo.getSort());
           // 共通
           dbZxCrCustomerInfo.setModifyUserInfo(userKey, realName);
           flag = zxCrCustomerInfoMapper.updateByPrimaryKey(dbZxCrCustomerInfo);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCrCustomerInfo);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCrCustomerInfo(List<ZxCrCustomerInfo> zxCrCustomerInfoList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCrCustomerInfoList != null && zxCrCustomerInfoList.size() > 0) {
           ZxCrCustomerInfo zxCrCustomerInfo = new ZxCrCustomerInfo();
           zxCrCustomerInfo.setModifyUserInfo(userKey, realName);
           flag = zxCrCustomerInfoMapper.batchDeleteUpdateZxCrCustomerInfo(zxCrCustomerInfoList, zxCrCustomerInfo);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCrCustomerInfoList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public ResponseEntity getZxCrCustomerInfoListOne(ZxCrCustomerInfo zxCrCustomerInfo) {
        if (zxCrCustomerInfo == null) {
            zxCrCustomerInfo = new ZxCrCustomerInfo();
        }
        // 分页查询
        PageHelper.startPage(zxCrCustomerInfo.getPage(),zxCrCustomerInfo.getLimit());
        // 获取数据
        List<ZxCrCustomerInfo> zxCrCustomerInfoList = zxCrCustomerInfoMapper.selectByZxCrCustomerInfoListOne(zxCrCustomerInfo);
        // 得到分页信息
        PageInfo<ZxCrCustomerInfo> p = new PageInfo<>(zxCrCustomerInfoList);

        return repEntity.okList(zxCrCustomerInfoList, p.getTotal());
    }
}
