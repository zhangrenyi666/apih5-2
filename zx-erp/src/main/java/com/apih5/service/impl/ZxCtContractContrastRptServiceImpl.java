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
import com.apih5.mybatis.dao.ZxCtContractContrastRptMapper;
import com.apih5.mybatis.pojo.ZxCtContractContrastRpt;
import com.apih5.service.ZxCtContractContrastRptService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtContractContrastRptService")
public class ZxCtContractContrastRptServiceImpl implements ZxCtContractContrastRptService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtContractContrastRptMapper zxCtContractContrastRptMapper;

    @Override
    public ResponseEntity getZxCtContractContrastRptListByCondition(ZxCtContractContrastRpt zxCtContractContrastRpt) {
        if (zxCtContractContrastRpt == null) {
            zxCtContractContrastRpt = new ZxCtContractContrastRpt();
        }
        // 分页查询
        PageHelper.startPage(zxCtContractContrastRpt.getPage(),zxCtContractContrastRpt.getLimit());
        // 获取数据
        List<ZxCtContractContrastRpt> zxCtContractContrastRptList = zxCtContractContrastRptMapper.selectByZxCtContractContrastRptList(zxCtContractContrastRpt);
        // 得到分页信息
        PageInfo<ZxCtContractContrastRpt> p = new PageInfo<>(zxCtContractContrastRptList);

        return repEntity.okList(zxCtContractContrastRptList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtContractContrastRptDetail(ZxCtContractContrastRpt zxCtContractContrastRpt) {
        if (zxCtContractContrastRpt == null) {
            zxCtContractContrastRpt = new ZxCtContractContrastRpt();
        }
        // 获取数据
        ZxCtContractContrastRpt dbZxCtContractContrastRpt = zxCtContractContrastRptMapper.selectByPrimaryKey(zxCtContractContrastRpt.getZxCtContractContrastRptId());
        // 数据存在
        if (dbZxCtContractContrastRpt != null) {
            return repEntity.ok(dbZxCtContractContrastRpt);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtContractContrastRpt(ZxCtContractContrastRpt zxCtContractContrastRpt) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtContractContrastRpt.setZxCtContractContrastRptId(UuidUtil.generate());
        zxCtContractContrastRpt.setCreateUserInfo(userKey, realName);
        int flag = zxCtContractContrastRptMapper.insert(zxCtContractContrastRpt);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtContractContrastRpt);
        }
    }

    @Override
    public ResponseEntity updateZxCtContractContrastRpt(ZxCtContractContrastRpt zxCtContractContrastRpt) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtContractContrastRpt dbZxCtContractContrastRpt = zxCtContractContrastRptMapper.selectByPrimaryKey(zxCtContractContrastRpt.getZxCtContractContrastRptId());
        if (dbZxCtContractContrastRpt != null && StrUtil.isNotEmpty(dbZxCtContractContrastRpt.getZxCtContractContrastRptId())) {
           // 项目名称
           dbZxCtContractContrastRpt.setFirstName(zxCtContractContrastRpt.getFirstName());
           // 合同编号
           dbZxCtContractContrastRpt.setContractNo(zxCtContractContrastRpt.getContractNo());
           // 供货单位
           dbZxCtContractContrastRpt.setCustomerName(zxCtContractContrastRpt.getCustomerName());
           // 供货单位
           dbZxCtContractContrastRpt.setSecondName(zxCtContractContrastRpt.getSecondName());
           // 物资大类
           dbZxCtContractContrastRpt.setWorkType(zxCtContractContrastRpt.getWorkType());
           // 物资编码
           dbZxCtContractContrastRpt.setWorkNo(zxCtContractContrastRpt.getWorkNo());
           // 物资名称
           dbZxCtContractContrastRpt.setWorkName(zxCtContractContrastRpt.getWorkName());
           // 规格型号
           dbZxCtContractContrastRpt.setSpec(zxCtContractContrastRpt.getSpec());
           // 计量单位
           dbZxCtContractContrastRpt.setUnit(zxCtContractContrastRpt.getUnit());
           // 物资合同单价
           dbZxCtContractContrastRpt.setPrice(zxCtContractContrastRpt.getPrice());
           // 物资合同数量
           dbZxCtContractContrastRpt.setQty(zxCtContractContrastRpt.getQty());
           // 物资合同金额
           dbZxCtContractContrastRpt.setContractSum(zxCtContractContrastRpt.getContractSum());
           // 物资采购单价
           dbZxCtContractContrastRpt.setInPrice(zxCtContractContrastRpt.getInPrice());
           // 物资采购数量
           dbZxCtContractContrastRpt.setInQty(zxCtContractContrastRpt.getInQty());
           // 物资采购金额
           dbZxCtContractContrastRpt.setInAmt(zxCtContractContrastRpt.getInAmt());
           // 备注
           dbZxCtContractContrastRpt.setRemarks(zxCtContractContrastRpt.getRemarks());
           // 排序
           dbZxCtContractContrastRpt.setSort(zxCtContractContrastRpt.getSort());
           // 共通
           dbZxCtContractContrastRpt.setModifyUserInfo(userKey, realName);
           flag = zxCtContractContrastRptMapper.updateByPrimaryKey(dbZxCtContractContrastRpt);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtContractContrastRpt);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtContractContrastRpt(List<ZxCtContractContrastRpt> zxCtContractContrastRptList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtContractContrastRptList != null && zxCtContractContrastRptList.size() > 0) {
           ZxCtContractContrastRpt zxCtContractContrastRpt = new ZxCtContractContrastRpt();
           zxCtContractContrastRpt.setModifyUserInfo(userKey, realName);
           flag = zxCtContractContrastRptMapper.batchDeleteUpdateZxCtContractContrastRpt(zxCtContractContrastRptList, zxCtContractContrastRpt);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtContractContrastRptList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public List<ZxCtContractContrastRpt> ureportZxCtContractContrastRpt(ZxCtContractContrastRpt zxCtContractContrastRpt) {
    	List<ZxCtContractContrastRpt> zxCtContractContrastRptList = zxCtContractContrastRptMapper.selectZxCtContractContrastRpt(zxCtContractContrastRpt);
    	
    	return zxCtContractContrastRptList;	
    }
    
    @Override
    public ResponseEntity ureportZxCtContractContrastRptIdle(ZxCtContractContrastRpt zxCtContractContrastRpt) {
        if (zxCtContractContrastRpt == null) {
            zxCtContractContrastRpt = new ZxCtContractContrastRpt();
        }
        // 分页查询
        PageHelper.startPage(zxCtContractContrastRpt.getPage(),zxCtContractContrastRpt.getLimit());
        // 获取数据
        List<ZxCtContractContrastRpt> zxCtContractContrastRptList = zxCtContractContrastRptMapper.selectZxCtContractContrastRpt(zxCtContractContrastRpt);
        // 得到分页信息
        PageInfo<ZxCtContractContrastRpt> p = new PageInfo<>(zxCtContractContrastRptList);

        return repEntity.okList(zxCtContractContrastRptList, p.getTotal());
    }
}
