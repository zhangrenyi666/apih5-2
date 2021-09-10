package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.util.NumberUtil;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.ZxSkResourceMaterialsMapper;
import com.apih5.mybatis.pojo.ZxSkResourceMaterials;
import com.apih5.mybatis.pojo.ZxSkStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkTurnOverStockMapper;
import com.apih5.mybatis.pojo.ZxSkTurnOverStock;
import com.apih5.service.ZxSkTurnOverStockService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkTurnOverStockService")
public class ZxSkTurnOverStockServiceImpl implements ZxSkTurnOverStockService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkTurnOverStockMapper zxSkTurnOverStockMapper;

    @Autowired(required = true)
    private ZxSkResourceMaterialsMapper zxSkResourceMaterialsMapper;


    @Override
    public ResponseEntity getZxSkTurnOverStockListByCondition(ZxSkTurnOverStock zxSkTurnOverStock) {
        if (zxSkTurnOverStock == null) {
            zxSkTurnOverStock = new ZxSkTurnOverStock();
        }
        // 分页查询
        PageHelper.startPage(zxSkTurnOverStock.getPage(),zxSkTurnOverStock.getLimit());
        // 获取数据
        List<ZxSkTurnOverStock> zxSkTurnOverStockList = zxSkTurnOverStockMapper.selectByZxSkTurnOverStockList(zxSkTurnOverStock);
        // 得到分页信息
        PageInfo<ZxSkTurnOverStock> p = new PageInfo<>(zxSkTurnOverStockList);

        return repEntity.okList(zxSkTurnOverStockList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkTurnOverStockDetail(ZxSkTurnOverStock zxSkTurnOverStock) {
        if (zxSkTurnOverStock == null) {
            zxSkTurnOverStock = new ZxSkTurnOverStock();
        }
        // 获取数据
        ZxSkTurnOverStock dbZxSkTurnOverStock = zxSkTurnOverStockMapper.selectByPrimaryKey(zxSkTurnOverStock.getId());
        // 数据存在
        if (dbZxSkTurnOverStock != null) {
            return repEntity.ok(dbZxSkTurnOverStock);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkTurnOverStock(ZxSkTurnOverStock zxSkTurnOverStock) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkTurnOverStock.setId(UuidUtil.generate());
        zxSkTurnOverStock.setCreateUserInfo(userKey, realName);
        int flag = zxSkTurnOverStockMapper.insert(zxSkTurnOverStock);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkTurnOverStock);
        }
    }

    @Override
    public ResponseEntity updateZxSkTurnOverStock(ZxSkTurnOverStock zxSkTurnOverStock) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkTurnOverStock dbZxSkTurnOverStock = zxSkTurnOverStockMapper.selectByPrimaryKey(zxSkTurnOverStock.getId());
        if (dbZxSkTurnOverStock != null && StrUtil.isNotEmpty(dbZxSkTurnOverStock.getId())) {
           // 所属机构ID
           dbZxSkTurnOverStock.setOrgID(zxSkTurnOverStock.getOrgID());
           // 机构名称
           dbZxSkTurnOverStock.setOrgName(zxSkTurnOverStock.getOrgName());
           // 物资编码
           dbZxSkTurnOverStock.setResCode(zxSkTurnOverStock.getResCode());
           // 物资名称
           dbZxSkTurnOverStock.setResName(zxSkTurnOverStock.getResName());
           // 规格型号
           dbZxSkTurnOverStock.setSpec(zxSkTurnOverStock.getSpec());
           // 计量单位
           dbZxSkTurnOverStock.setResUnit(zxSkTurnOverStock.getResUnit());
           // 物资ID
           dbZxSkTurnOverStock.setResID(zxSkTurnOverStock.getResID());
           // 批次
           dbZxSkTurnOverStock.setBatchNo(zxSkTurnOverStock.getBatchNo());
           // 库存数量
           dbZxSkTurnOverStock.setStockQty(zxSkTurnOverStock.getStockQty());
           // 库存单价
           dbZxSkTurnOverStock.setStockPrice(zxSkTurnOverStock.getStockPrice());
           // 库存金额
           dbZxSkTurnOverStock.setStockAmt(zxSkTurnOverStock.getStockAmt());
           // 原数量
           dbZxSkTurnOverStock.setOriginalQty(zxSkTurnOverStock.getOriginalQty());
           // 原值
           dbZxSkTurnOverStock.setOriginalAmt(zxSkTurnOverStock.getOriginalAmt());
           // 净值
           dbZxSkTurnOverStock.setRemainAmt(zxSkTurnOverStock.getRemainAmt());
           // 备注
           dbZxSkTurnOverStock.setRemarks(zxSkTurnOverStock.getRemarks());
           // 排序
           dbZxSkTurnOverStock.setSort(zxSkTurnOverStock.getSort());
           // 共通
           dbZxSkTurnOverStock.setModifyUserInfo(userKey, realName);
           flag = zxSkTurnOverStockMapper.updateByPrimaryKey(dbZxSkTurnOverStock);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkTurnOverStock);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkTurnOverStock(List<ZxSkTurnOverStock> zxSkTurnOverStockList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkTurnOverStockList != null && zxSkTurnOverStockList.size() > 0) {
           ZxSkTurnOverStock zxSkTurnOverStock = new ZxSkTurnOverStock();
           zxSkTurnOverStock.setModifyUserInfo(userKey, realName);
           flag = zxSkTurnOverStockMapper.batchDeleteUpdateZxSkTurnOverStock(zxSkTurnOverStockList, zxSkTurnOverStock);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkTurnOverStockList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    //入库
    @Override
    public ResponseEntity addZxSkStock(List<ZxSkTurnOverStock> zxSkTurnOverStockList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //错误的list (入库数量不能等于0)
        List<ZxSkTurnOverStock> zxSkStockListErrorNumber = new ArrayList<>();
        for (ZxSkTurnOverStock zxSkStock : zxSkTurnOverStockList) {
            //首先判断   数量不能等于0
            if(CalcUtils.compareToZero(zxSkStock.getStockQty())==0){
                zxSkStockListErrorNumber.add(zxSkStock);
            }
        }
        if(zxSkStockListErrorNumber!=null&&zxSkStockListErrorNumber.size()>0){
            return repEntity.layerMessage(false,zxSkStockListErrorNumber, "入库的数量不能等于0");
        }
        for (ZxSkTurnOverStock zxSkTurnOverStock : zxSkTurnOverStockList) {
            //不用去查库存了
            //批次: 明细id  =>  batchNo
            //创建数据
            zxSkTurnOverStock.setId(UuidUtil.generate());
            zxSkTurnOverStock.setCreateUserInfo(userKey, realName);
            flag = zxSkTurnOverStockMapper.insert(zxSkTurnOverStock);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkTurnOverStockList);
        }
    }

    //出库
    @Override
    public ResponseEntity reduceZxSkStock(List<ZxSkTurnOverStock> zxSkTurnOverStockList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //错误的list (出库数量不能大于库存)
        List<ZxSkTurnOverStock> zxSkStockListErrorNumber = new ArrayList<>();
        //错误的list (仓库里没有这条物资)
        List<ZxSkTurnOverStock> zxSkStockListErrorResID = new ArrayList<>();
        //正确的库存list()
        Map<String,ZxSkTurnOverStock> zxSkStockMap = new HashMap<>();
        for (ZxSkTurnOverStock zxSkTurnOverStock : zxSkTurnOverStockList) {
            //先查(根据仓库Id 项目Id 物资id)
            ZxSkTurnOverStock zxSkTurnOverStock1 = new ZxSkTurnOverStock();
            //批次
            zxSkTurnOverStock1.setBatchNo(zxSkTurnOverStock.getBatchNo());
            ZxSkTurnOverStock dbzxSkTurnOverStocks = zxSkTurnOverStockMapper.selectReturnZxSkStockList(zxSkTurnOverStock1);
            if(dbzxSkTurnOverStocks != null ) {
                //出库数量不能大于库存
                if (CalcUtils.compareTo(dbzxSkTurnOverStocks.getStockQty(),zxSkTurnOverStock.getStockQty())<0) {
                    //获取编码,获取名称,获取数量,获取单价
                    zxSkStockListErrorNumber.add(dbzxSkTurnOverStocks);
                    continue;
                }
                if(zxSkStockListErrorNumber.size()==0){
                    //根据批次查询
                    zxSkStockMap.put(dbzxSkTurnOverStocks.getBatchNo(),dbzxSkTurnOverStocks);
                }
            }else {
                //查询材料基础数据
                //查询物资.提示仓库里没有这条物资
                ZxSkResourceMaterials zxSkResourceMaterials = new ZxSkResourceMaterials();
                zxSkResourceMaterials.setId(zxSkTurnOverStock.getResID());
                List<ZxSkResourceMaterials> zxSkResourceMaterials1 = zxSkResourceMaterialsMapper.selectByZxSkResourceMaterialsList(zxSkResourceMaterials);
                if(zxSkResourceMaterials1!=null&zxSkResourceMaterials1.size()>0){
                    ZxSkResourceMaterials zxSkResourceMaterials2 = zxSkResourceMaterials1.get(0);
                    zxSkTurnOverStock.setResCode(zxSkResourceMaterials2.getResCode());
                    zxSkTurnOverStock.setResName(zxSkResourceMaterials2.getResName());
                    zxSkTurnOverStock.setSpec(zxSkResourceMaterials2.getSpec());
                    zxSkTurnOverStock.setResUnit(zxSkResourceMaterials2.getUnit());
                    zxSkStockListErrorResID.add(zxSkTurnOverStock);
                }
            }
        }
        if (zxSkStockListErrorNumber!=null && zxSkStockListErrorNumber.size()>0){
            return repEntity.layerMessage(false,zxSkStockListErrorNumber,"出库数量不能大于库存");
        }
        if(zxSkStockListErrorResID!=null && zxSkStockListErrorResID.size()>0){
            return repEntity.layerMessage(false,zxSkStockListErrorResID,"仓库中没有这个物资");
        }
        for (ZxSkTurnOverStock zxSkTurnOverStock : zxSkTurnOverStockList) {
            //根据map查询仓库中的数据
            ZxSkTurnOverStock zxSkTurnOverStock1 = zxSkStockMap.get(zxSkTurnOverStock.getBatchNo());

            //库存数量
            BigDecimal dbstockQty = zxSkTurnOverStock1.getStockQty();
            //库存单价
            BigDecimal stockPrice = zxSkTurnOverStock1.getStockPrice();
            //数量
            dbstockQty = CalcUtils.calcSubtract(dbstockQty, zxSkTurnOverStock.getStockQty());
            zxSkTurnOverStock1.setStockQty(dbstockQty);
            //库存金额 = 剩余库存数量 * 库存单价
            zxSkTurnOverStock1.setStockAmt(CalcUtils.calcMultiply(dbstockQty, stockPrice));
            if(zxSkTurnOverStock.getOriginalAmt()!=null&&zxSkTurnOverStock.getRemainAmt()!=null){
                //库存原数量
                BigDecimal dboriginalQty = zxSkTurnOverStock1.getOriginalQty();
                //原数量
                dboriginalQty = CalcUtils.calcSubtract(dboriginalQty,zxSkTurnOverStock.getStockQty());
                zxSkTurnOverStock1.setOriginalQty(dboriginalQty);
                //出库减少原值
                zxSkTurnOverStock1.setOriginalAmt(CalcUtils.calcSubtract(zxSkTurnOverStock1.getOriginalAmt(),zxSkTurnOverStock.getOriginalAmt()));
                //出库减少净值
                zxSkTurnOverStock1.setRemainAmt(CalcUtils.calcSubtract(zxSkTurnOverStock1.getRemainAmt(),zxSkTurnOverStock.getRemainAmt()));
            }
            // 共通
            zxSkTurnOverStock1.setModifyUserInfo(userKey, realName);
            flag = zxSkTurnOverStockMapper.updateByPrimaryKey(zxSkTurnOverStock1);

        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkTurnOverStockList);
        }
    }

    //归还到库里
    @Override
    public ResponseEntity returnZxSkStock(List<ZxSkTurnOverStock> zxSkTurnOverStockList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        for (ZxSkTurnOverStock zxSkTurnOverStock : zxSkTurnOverStockList) {
            //先查(根据仓库Id 项目Id 物资id)
            ZxSkTurnOverStock zxSkTurnOverStock1 = new ZxSkTurnOverStock();
            //批次
            zxSkTurnOverStock1.setBatchNo(zxSkTurnOverStock.getBatchNo());
            //查询 所有数据包括已删除的
            ZxSkTurnOverStock dbzxSkTurnOverStocks = zxSkTurnOverStockMapper.selectReturnZxSkStockList(zxSkTurnOverStock1);
            if(dbzxSkTurnOverStocks != null) {
                //归还数量+库存数量 不能大于原数量
//                if(CalcUtils.compareTo(dbzxSkTurnOverStocks.getOriginalQty(),CalcUtils.calcAdd(dbzxSkTurnOverStocks.getStockQty(),zxSkTurnOverStock.getStockQty()))>=0){
                    //数量
                    dbzxSkTurnOverStocks.setStockQty(CalcUtils.calcAdd(dbzxSkTurnOverStocks.getStockQty(),zxSkTurnOverStock.getStockQty()));
                    //总金额
                    dbzxSkTurnOverStocks.setStockAmt(CalcUtils.calcMultiply(dbzxSkTurnOverStocks.getStockQty(),dbzxSkTurnOverStocks.getStockPrice()));
                    if(CalcUtils.compareToZero(dbzxSkTurnOverStocks.getStockQty())!=0){
                        dbzxSkTurnOverStocks.setDelFlag("0");
                    }
                    if(zxSkTurnOverStock.getOriginalAmt()!=null&&zxSkTurnOverStock.getRemainAmt()!=null){
                        //退回增加原值
                        dbzxSkTurnOverStocks.setOriginalAmt(CalcUtils.calcAdd(dbzxSkTurnOverStocks.getOriginalAmt(),zxSkTurnOverStock.getOriginalAmt()));
                        //退回增加净值
                        dbzxSkTurnOverStocks.setRemainAmt(CalcUtils.calcAdd(dbzxSkTurnOverStocks.getRemainAmt(),zxSkTurnOverStock.getRemainAmt()));
                    }
                    // 共通
                    dbzxSkTurnOverStocks.setModifyUserInfo(userKey, realName);
                    flag = zxSkTurnOverStockMapper.updateByPrimaryKey(dbzxSkTurnOverStocks);
//                }else {
//                    报错
//                    return repEntity.layerMessage(false,zxSkTurnOverStockList,"归还数量不能大于入库数量");
//                }
            }else {
                return repEntity.errorSave();
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkTurnOverStockList);
        }
    }






}
