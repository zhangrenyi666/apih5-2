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
import com.apih5.mybatis.dao.ZxSkStockTransferRptNewMapper;
import com.apih5.mybatis.pojo.ZxSkStockTransferRptNew;
import com.apih5.service.ZxSkStockTransferRptNewService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkStockTransferRptNewService")
public class ZxSkStockTransferRptNewServiceImpl implements ZxSkStockTransferRptNewService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkStockTransferRptNewMapper zxSkStockTransferRptNewMapper;

    @Override
    public ResponseEntity getZxSkStockTransferRptNewListByCondition(ZxSkStockTransferRptNew zxSkStockTransferRptNew) {
        if (zxSkStockTransferRptNew == null) {
            zxSkStockTransferRptNew = new ZxSkStockTransferRptNew();
        }
        // 分页查询
        PageHelper.startPage(zxSkStockTransferRptNew.getPage(),zxSkStockTransferRptNew.getLimit());
        // 获取数据
        List<ZxSkStockTransferRptNew> zxSkStockTransferRptNewList = zxSkStockTransferRptNewMapper.selectByZxSkStockTransferRptNewList(zxSkStockTransferRptNew);
        // 得到分页信息
        PageInfo<ZxSkStockTransferRptNew> p = new PageInfo<>(zxSkStockTransferRptNewList);

        return repEntity.okList(zxSkStockTransferRptNewList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockTransferRptNewDetail(ZxSkStockTransferRptNew zxSkStockTransferRptNew) {
        if (zxSkStockTransferRptNew == null) {
            zxSkStockTransferRptNew = new ZxSkStockTransferRptNew();
        }
        // 获取数据
        ZxSkStockTransferRptNew dbZxSkStockTransferRptNew = zxSkStockTransferRptNewMapper.selectByPrimaryKey(zxSkStockTransferRptNew.getZxSkStockTransferRptNewId());
        // 数据存在
        if (dbZxSkStockTransferRptNew != null) {
            return repEntity.ok(dbZxSkStockTransferRptNew);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkStockTransferRptNew(ZxSkStockTransferRptNew zxSkStockTransferRptNew) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStockTransferRptNew.setZxSkStockTransferRptNewId(UuidUtil.generate());
        zxSkStockTransferRptNew.setCreateUserInfo(userKey, realName);
        int flag = zxSkStockTransferRptNewMapper.insert(zxSkStockTransferRptNew);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkStockTransferRptNew);
        }
    }

    @Override
    public ResponseEntity updateZxSkStockTransferRptNew(ZxSkStockTransferRptNew zxSkStockTransferRptNew) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStockTransferRptNew dbZxSkStockTransferRptNew = zxSkStockTransferRptNewMapper.selectByPrimaryKey(zxSkStockTransferRptNew.getZxSkStockTransferRptNewId());
        if (dbZxSkStockTransferRptNew != null && StrUtil.isNotEmpty(dbZxSkStockTransferRptNew.getZxSkStockTransferRptNewId())) {
           // 类别
           dbZxSkStockTransferRptNew.setNum(zxSkStockTransferRptNew.getNum());
           // 物资名称
           dbZxSkStockTransferRptNew.setResName(zxSkStockTransferRptNew.getResName());
           // 特征字符
           dbZxSkStockTransferRptNew.setResType(zxSkStockTransferRptNew.getResType());
           // 计量单位数量
           dbZxSkStockTransferRptNew.setSpec(zxSkStockTransferRptNew.getSpec());
           // 计量单位金额
           dbZxSkStockTransferRptNew.setUnit(zxSkStockTransferRptNew.getUnit());
           // 本季期初库存数量
           dbZxSkStockTransferRptNew.setQcinQty(zxSkStockTransferRptNew.getQcinQty());
           // 本季期初库存金额
           dbZxSkStockTransferRptNew.setQcinAmt(zxSkStockTransferRptNew.getQcinAmt());
           // 本季收入合计数量
           dbZxSkStockTransferRptNew.setC4Qty(zxSkStockTransferRptNew.getC4Qty());
           // 本季收入合计金额
           dbZxSkStockTransferRptNew.setC4Amt(zxSkStockTransferRptNew.getC4Amt());
           // 甲供数量
           dbZxSkStockTransferRptNew.setC1Qty(zxSkStockTransferRptNew.getC1Qty());
           // 甲供金额
           dbZxSkStockTransferRptNew.setC1Amt(zxSkStockTransferRptNew.getC1Amt());
           // 本季发出(消耗)数量
           dbZxSkStockTransferRptNew.setF3outQty(zxSkStockTransferRptNew.getF3outQty());
           // 本季发出(消耗)金额
           dbZxSkStockTransferRptNew.setF3outAmt(zxSkStockTransferRptNew.getF3outAmt());
           // 境外工程的国内采购数量
           dbZxSkStockTransferRptNew.setS1(zxSkStockTransferRptNew.getS1());
           // 境外工程的国内采购金额
           dbZxSkStockTransferRptNew.setS2(zxSkStockTransferRptNew.getS2());
           // 占本期营业额的比重
           dbZxSkStockTransferRptNew.setS3(zxSkStockTransferRptNew.getS3());
           // 本季期末库存数量
           dbZxSkStockTransferRptNew.setCurQty(zxSkStockTransferRptNew.getCurQty());
           // 本季期末库存金额
           dbZxSkStockTransferRptNew.setCurAmt(zxSkStockTransferRptNew.getCurAmt());
           // 备注
           dbZxSkStockTransferRptNew.setRemarks(zxSkStockTransferRptNew.getRemarks());
           // 排序
           dbZxSkStockTransferRptNew.setSort(zxSkStockTransferRptNew.getSort());
           // 共通
           dbZxSkStockTransferRptNew.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransferRptNewMapper.updateByPrimaryKey(dbZxSkStockTransferRptNew);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkStockTransferRptNew);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStockTransferRptNew(List<ZxSkStockTransferRptNew> zxSkStockTransferRptNewList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockTransferRptNewList != null && zxSkStockTransferRptNewList.size() > 0) {
           ZxSkStockTransferRptNew zxSkStockTransferRptNew = new ZxSkStockTransferRptNew();
           zxSkStockTransferRptNew.setModifyUserInfo(userKey, realName);
           flag = zxSkStockTransferRptNewMapper.batchDeleteUpdateZxSkStockTransferRptNew(zxSkStockTransferRptNewList, zxSkStockTransferRptNew);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkStockTransferRptNewList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public List<ZxSkStockTransferRptNew> ureportZxSkStockTransferRptNew(ZxSkStockTransferRptNew zxSkStockTransferRptNew) {
    	if(zxSkStockTransferRptNew==null) {
    		zxSkStockTransferRptNew = new ZxSkStockTransferRptNew();	
    	}
    	List<ZxSkStockTransferRptNew> zxSkStockTransferRptNewList = zxSkStockTransferRptNewMapper.selectZxSkStockTransferRptNew(zxSkStockTransferRptNew);
    	for(ZxSkStockTransferRptNew zxSkStockTransferRptNew1: zxSkStockTransferRptNewList) {
    		if(zxSkStockTransferRptNew1.getNum().equals("01")||zxSkStockTransferRptNew1.getNum().equals("02")||zxSkStockTransferRptNew1.getNum().equals("03")
    				||zxSkStockTransferRptNew1.getNum().equals("04")||zxSkStockTransferRptNew1.getNum().equals("05")||zxSkStockTransferRptNew1.getNum().equals("06")
    				||zxSkStockTransferRptNew1.getNum().equals("07")||zxSkStockTransferRptNew1.getNum().equals("08")||zxSkStockTransferRptNew1.getNum().equals("09")
    				||zxSkStockTransferRptNew1.getNum().equals("10")||zxSkStockTransferRptNew1.getNum().equals("11")||zxSkStockTransferRptNew1.getNum().equals("12")
    				||zxSkStockTransferRptNew1.getNum().equals("13")||zxSkStockTransferRptNew1.getNum().equals("14")||zxSkStockTransferRptNew1.getNum().equals("15")
    				) {
    			
    		}
    	}
    	return zxSkStockTransferRptNewList;
    }
    
    @Override
    public ResponseEntity ureportZxSkStockTransferRptNewIdle(ZxSkStockTransferRptNew zxSkStockTransferRptNew) {
        if (zxSkStockTransferRptNew == null) {
            zxSkStockTransferRptNew = new ZxSkStockTransferRptNew();
        }
        // 获取数据
        List<ZxSkStockTransferRptNew> zxSkStockTransferRptNewList = zxSkStockTransferRptNewMapper.selectZxSkStockTransferRptNew(zxSkStockTransferRptNew);

        return repEntity.okList(zxSkStockTransferRptNewList, zxSkStockTransferRptNewList.size());
    }
}
