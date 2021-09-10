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
import com.apih5.mybatis.dao.ZxSkCustomerOutResMapper;
import com.apih5.mybatis.pojo.ZxEqEquip;
import com.apih5.mybatis.pojo.ZxSkCustomerOutRes;
import com.apih5.service.ZxSkCustomerOutResService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkCustomerOutResService")
public class ZxSkCustomerOutResServiceImpl implements ZxSkCustomerOutResService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkCustomerOutResMapper zxSkCustomerOutResMapper;

    @Override
    public ResponseEntity getZxSkCustomerOutResListByCondition(ZxSkCustomerOutRes zxSkCustomerOutRes) {
        if (zxSkCustomerOutRes == null) {
            zxSkCustomerOutRes = new ZxSkCustomerOutRes();
        }
        // 分页查询
        PageHelper.startPage(zxSkCustomerOutRes.getPage(),zxSkCustomerOutRes.getLimit());
        // 获取数据
        List<ZxSkCustomerOutRes> zxSkCustomerOutResList = zxSkCustomerOutResMapper.selectByZxSkCustomerOutResList(zxSkCustomerOutRes);
        // 得到分页信息
        PageInfo<ZxSkCustomerOutRes> p = new PageInfo<>(zxSkCustomerOutResList);

        return repEntity.okList(zxSkCustomerOutResList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkCustomerOutResDetail(ZxSkCustomerOutRes zxSkCustomerOutRes) {
        if (zxSkCustomerOutRes == null) {
            zxSkCustomerOutRes = new ZxSkCustomerOutRes();
        }
        // 获取数据
        ZxSkCustomerOutRes dbZxSkCustomerOutRes = zxSkCustomerOutResMapper.selectByPrimaryKey(zxSkCustomerOutRes.getZxSkCustomerOutResId());
        // 数据存在
        if (dbZxSkCustomerOutRes != null) {
            return repEntity.ok(dbZxSkCustomerOutRes);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkCustomerOutRes(ZxSkCustomerOutRes zxSkCustomerOutRes) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkCustomerOutRes.setZxSkCustomerOutResId(UuidUtil.generate());
        zxSkCustomerOutRes.setCreateUserInfo(userKey, realName);
        int flag = zxSkCustomerOutResMapper.insert(zxSkCustomerOutRes);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkCustomerOutRes);
        }
    }

    @Override
    public ResponseEntity updateZxSkCustomerOutRes(ZxSkCustomerOutRes zxSkCustomerOutRes) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkCustomerOutRes dbZxSkCustomerOutRes = zxSkCustomerOutResMapper.selectByPrimaryKey(zxSkCustomerOutRes.getZxSkCustomerOutResId());
        if (dbZxSkCustomerOutRes != null && StrUtil.isNotEmpty(dbZxSkCustomerOutRes.getZxSkCustomerOutResId())) {
           // 项目名称
           dbZxSkCustomerOutRes.setProjectName(zxSkCustomerOutRes.getProjectName());
           // 供应商
           dbZxSkCustomerOutRes.setOutOrgName(zxSkCustomerOutRes.getOutOrgName());
           // 业务日期
           dbZxSkCustomerOutRes.setBusDate(zxSkCustomerOutRes.getBusDate());
           // 单据编号
           dbZxSkCustomerOutRes.setBillNo(zxSkCustomerOutRes.getBillNo());
           // 物资大类
           dbZxSkCustomerOutRes.setResourceName(zxSkCustomerOutRes.getResourceName());
           // 物资编码
           dbZxSkCustomerOutRes.setResCode(zxSkCustomerOutRes.getResCode());
           // 物资名称
           dbZxSkCustomerOutRes.setResName(zxSkCustomerOutRes.getResName());
           // 规格型号
           dbZxSkCustomerOutRes.setSpec(zxSkCustomerOutRes.getSpec());
           // 计量单位
           dbZxSkCustomerOutRes.setResUnit(zxSkCustomerOutRes.getResUnit());
           // 数量
           dbZxSkCustomerOutRes.setInQty(zxSkCustomerOutRes.getInQty());
           // 含税采购单价
           dbZxSkCustomerOutRes.setInPrice(zxSkCustomerOutRes.getInPrice());
           // 含税采购金额
           dbZxSkCustomerOutRes.setResAllFee(zxSkCustomerOutRes.getResAllFee());
           // 不含税采购单价
           dbZxSkCustomerOutRes.setInPriceNoTax(zxSkCustomerOutRes.getInPriceNoTax());
           // 不含税采购金额
           dbZxSkCustomerOutRes.setResAllFeeNoTax(zxSkCustomerOutRes.getResAllFeeNoTax());
           // 入账金额
           dbZxSkCustomerOutRes.setInAmt(zxSkCustomerOutRes.getInAmt());
           // 市场来源
           dbZxSkCustomerOutRes.setAsmaterialSource(zxSkCustomerOutRes.getAsmaterialSource());
           // 是否预收
           dbZxSkCustomerOutRes.setPrecollecte(zxSkCustomerOutRes.getPrecollecte());
           // 是否有合同
           dbZxSkCustomerOutRes.setPurchType(zxSkCustomerOutRes.getPurchType());
           // 合同编号
           dbZxSkCustomerOutRes.setContractNo(zxSkCustomerOutRes.getContractNo());
           // 备注
           dbZxSkCustomerOutRes.setRemarks(zxSkCustomerOutRes.getRemarks());
           // 排序
           dbZxSkCustomerOutRes.setSort(zxSkCustomerOutRes.getSort());
           // 共通
           dbZxSkCustomerOutRes.setModifyUserInfo(userKey, realName);
           flag = zxSkCustomerOutResMapper.updateByPrimaryKey(dbZxSkCustomerOutRes);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkCustomerOutRes);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkCustomerOutRes(List<ZxSkCustomerOutRes> zxSkCustomerOutResList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkCustomerOutResList != null && zxSkCustomerOutResList.size() > 0) {
           ZxSkCustomerOutRes zxSkCustomerOutRes = new ZxSkCustomerOutRes();
           zxSkCustomerOutRes.setModifyUserInfo(userKey, realName);
           flag = zxSkCustomerOutResMapper.batchDeleteUpdateZxSkCustomerOutRes(zxSkCustomerOutResList, zxSkCustomerOutRes);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkCustomerOutResList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @Override
    public List<ZxSkCustomerOutRes> ureportZxSkReceivingDynamic(ZxSkCustomerOutRes zxSkCustomerOutRes) {
    	List<ZxSkCustomerOutRes> zxSkCustomerOutResList = zxSkCustomerOutResMapper.selectZxSkCustomerOutResList(zxSkCustomerOutRes);
    	return zxSkCustomerOutResList;
    }
    
    @Override
    public ResponseEntity ureportZxSkReceivingDynamicIdle(ZxSkCustomerOutRes zxSkCustomerOutRes) {
    	
    	 if (zxSkCustomerOutRes == null) {
             zxSkCustomerOutRes = new ZxSkCustomerOutRes();
         }
         // 分页查询
         PageHelper.startPage(zxSkCustomerOutRes.getPage(),zxSkCustomerOutRes.getLimit());
         // 获取数据
         List<ZxSkCustomerOutRes> zxSkCustomerOutResList = zxSkCustomerOutResMapper.selectZxSkCustomerOutResList(zxSkCustomerOutRes);
         // 得到分页信息
         PageInfo<ZxSkCustomerOutRes> p = new PageInfo<>(zxSkCustomerOutResList);

         return repEntity.okList(zxSkCustomerOutResList, p.getTotal());
    	
    }
}
