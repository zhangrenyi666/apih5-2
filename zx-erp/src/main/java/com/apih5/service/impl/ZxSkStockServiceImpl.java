package com.apih5.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.util.NumberUtil;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.ZxSkResourceMaterialsMapper;
import com.apih5.mybatis.pojo.ZxSkResourceMaterials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkStockMapper;
import com.apih5.mybatis.pojo.ZxSkStock;
import com.apih5.service.ZxSkStockService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
@Service("zxSkStockService")
public class ZxSkStockServiceImpl implements ZxSkStockService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkStockMapper zxSkStockMapper;

    @Autowired(required = true)
    private ZxSkResourceMaterialsMapper zxSkResourceMaterialsMapper;

    @Override
    public ResponseEntity getZxSkStockListByCondition(ZxSkStock zxSkStock) {
        //分类id
        if (
                //仓库id
                StrUtil.isEmpty(zxSkStock.getWhOrgID())) {
            return repEntity.ok(new ArrayList<>());
        }
        // 分页查询
        PageHelper.startPage(zxSkStock.getPage(), zxSkStock.getLimit());
        // 获取数据
        List<ZxSkStock> zxSkStockList = zxSkStockMapper.selectByZxSkStockList(zxSkStock);
        //拼接
        //编号,名称,规格型号,库存,参考单价.参考金额
        for (ZxSkStock skStock : zxSkStockList) {
            skStock.setJoinName(
                    skStock.getResCode() //编号
                            + "-" + skStock.getResName()          //名称
                            + "-" + skStock.getSpec()             //规格型号
                            + "-" + skStock.getStockQty()         //库存
                            + "-" + skStock.getStockPrice()       //参考单价
                            + "-" + skStock.getStockAmt()         //参考金额
            );
        }
        // 得到分页信息
        PageInfo<ZxSkStock> p = new PageInfo<>(zxSkStockList);

        return repEntity.okList(zxSkStockList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockDetails(ZxSkStock zxSkStock) {
        if (zxSkStock == null) {
            zxSkStock = new ZxSkStock();
        }
        // 获取数据
        ZxSkStock dbZxSkStock = zxSkStockMapper.selectByPrimaryKey(zxSkStock.getId());
        // 数据存在
        if (dbZxSkStock != null) {
            return repEntity.ok(dbZxSkStock);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkStock(ZxSkStock zxSkStock) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkStock.setId(UuidUtil.generate());
        zxSkStock.setCreateUserInfo(userKey, realName);
        int flag = zxSkStockMapper.insert(zxSkStock);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkStock);
        }
    }

    @Override
    public ResponseEntity updateZxSkStock(ZxSkStock zxSkStock) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkStock dbzxSkStock = zxSkStockMapper.selectByPrimaryKey(zxSkStock.getId());
        if (dbzxSkStock != null && StrUtil.isNotEmpty(dbzxSkStock.getId())) {
            // 公司ID
            dbzxSkStock.setCompanyID(zxSkStock.getCompanyID());
            // 机构(项目表ID)
            dbzxSkStock.setOrgID(zxSkStock.getOrgID());
            // 仓库机构ID(仓库表ID)
            dbzxSkStock.setWhOrgID(zxSkStock.getWhOrgID());
            // 物资大类id
            dbzxSkStock.setResourceId(zxSkStock.getResourceId());
            // 物资大类
            dbzxSkStock.setResourceName(zxSkStock.getResourceName());
            // 物资(大类)编码
            dbzxSkStock.setResourceNo(zxSkStock.getResourceNo());
            // 物资
            dbzxSkStock.setResID(zxSkStock.getResID());
            // 资源编号
            dbzxSkStock.setResCode(zxSkStock.getResCode());
            // 资源名称
            dbzxSkStock.setResName(zxSkStock.getResName());
            // 规格型号
            dbzxSkStock.setSpec(zxSkStock.getSpec());
            // 单位
            dbzxSkStock.setUnit(zxSkStock.getUnit());
            // 批次
            dbzxSkStock.setBatchNo(zxSkStock.getBatchNo());
            // 库存数量
            dbzxSkStock.setStockQty(zxSkStock.getStockQty());
            // 库存单价
            dbzxSkStock.setStockPrice(zxSkStock.getStockPrice());
            // 库存金额
            dbzxSkStock.setStockAmt(zxSkStock.getStockAmt());
            // 租进数量
            dbzxSkStock.setRentinQty(zxSkStock.getRentinQty());
            // 租出数量
            dbzxSkStock.setRentoutQty(zxSkStock.getRentoutQty());
            // 备注
            dbzxSkStock.setRemark(zxSkStock.getRemark());
            // 明细
            dbzxSkStock.setCombProp(zxSkStock.getCombProp());
            // 共通
            dbzxSkStock.setModifyUserInfo(userKey, realName);
            flag = zxSkStockMapper.updateByPrimaryKey(dbzxSkStock);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSkStock);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkStock(List<ZxSkStock> zxSkStockList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkStockList != null && zxSkStockList.size() > 0) {
            ZxSkStock zxSkStock = new ZxSkStock();
            zxSkStock.setModifyUserInfo(userKey, realName);
            flag = zxSkStockMapper.batchDeleteUpdateZxSkStock(zxSkStockList, zxSkStock);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSkStockList);
        }
    }

    //-----扩展---↓
    //添加库存(传入数量,传入总金额  计算出单价)
    @Override
    public ResponseEntity addZxSkStock(List<ZxSkStock> zxSkStockList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //错误的list (入库数量不能等于0)
        List<ZxSkStock> zxSkStockListErrorNumber = new ArrayList<>();
        for (ZxSkStock zxSkStock : zxSkStockList) {
            //首先判断   数量不能等于0
            if (CalcUtils.compareToZero(zxSkStock.getStockQty()) == 0) {
                zxSkStockListErrorNumber.add(zxSkStock);
            }
        }
        if (zxSkStockListErrorNumber != null && zxSkStockListErrorNumber.size() > 0) {
            return repEntity.layerMessage(false, zxSkStockListErrorNumber, "入库的数量不能等于0");
        }
        for (ZxSkStock zxSkStock : zxSkStockList) {
            ZxSkStock zxSkStock1 = new ZxSkStock();
            zxSkStock1.setCompanyID(zxSkStock.getCompanyID());
            zxSkStock1.setOrgID(zxSkStock.getOrgID());
            zxSkStock1.setWhOrgID(zxSkStock.getWhOrgID());
            zxSkStock1.setResID(zxSkStock.getResID());
            //获取库存
            List<ZxSkStock> zxSkStocks = zxSkStockMapper.selectByZxSkStockListNew(zxSkStock1);
            if (zxSkStocks != null && zxSkStocks.size() > 0) {
                ZxSkStock dbzxSkStock = zxSkStocks.get(0);
                //库存数量
                BigDecimal dbstockQty = dbzxSkStock.getStockQty();
                //库存单价 stockPrice
//                BigDecimal dbstockPrice = dbzxSkStock.getStockPrice();
                //库存金额  stockAmt
                BigDecimal dbstockAmt = dbzxSkStock.getStockAmt();
                //数量
                dbstockQty = CalcUtils.calcAdd(dbstockQty, zxSkStock.getStockQty());
                dbzxSkStock.setStockQty(dbstockQty);
                //库存金额
                dbstockAmt = CalcUtils.calcAdd(dbstockAmt, zxSkStock.getStockAmt());
                dbzxSkStock.setStockAmt(dbstockAmt);
                //单价
                dbzxSkStock.setStockPrice(CalcUtils.calcDivide(dbstockAmt, dbstockQty,6));
                // 共通
                dbzxSkStock.setModifyUserInfo(userKey, realName);
                dbzxSkStock.setDelFlag("0");
                flag = zxSkStockMapper.updateByPrimaryKey(dbzxSkStock);
            } else {
                //创建数据
                zxSkStock.setId(UuidUtil.generate());
                zxSkStock.setStockPrice(CalcUtils.calcDivide(zxSkStock.getStockAmt(), zxSkStock.getStockQty(),6));
                zxSkStock.setCreateUserInfo(userKey, realName);
                flag = zxSkStockMapper.insert(zxSkStock);
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSkStockList);
        }
    }

    //减少库存(只通过数量)
    @Override
    public ResponseEntity reduceZxSkStock(List<ZxSkStock> zxSkStockList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //错误的list (出库数量不能大于库存)
        List<ZxSkStock> zxSkStockListErrorNumber = new ArrayList<>();
        //错误的list (出库的单价和库存单价不同)
        List<ZxSkStock> zxSkStockListErrorPrice = new ArrayList<>();
        //错误的list (仓库里没有这条物资)
        List<ZxSkStock> zxSkStockListErrorResID = new ArrayList<>();
        //正确的库存list()
        Map<String, ZxSkStock> zxSkStockMap = new HashMap<>();
        for (ZxSkStock zxSkStock : zxSkStockList) {
            //先查(根据仓库Id 项目Id 物资id)
            ZxSkStock zxSkStock1 = new ZxSkStock();
            zxSkStock1.setCompanyID(zxSkStock.getCompanyID());
            zxSkStock1.setOrgID(zxSkStock.getOrgID());
            zxSkStock1.setWhOrgID(zxSkStock.getWhOrgID());
            zxSkStock1.setResID(zxSkStock.getResID());
            List<ZxSkStock> zxSkStocks = zxSkStockMapper.selectByZxSkStockList(zxSkStock1);
            if (zxSkStocks != null && zxSkStocks.size() > 0) {
                ZxSkStock dbzxSkStock = zxSkStocks.get(0);
                //出库数量不能大于库存
                if (CalcUtils.compareTo(dbzxSkStock.getStockQty(), zxSkStock.getStockQty()) < 0) {
                    //获取编码,获取名称,获取数量,获取单价
                    zxSkStockListErrorNumber.add(dbzxSkStock);
                    continue;
                }
                if (!(CalcUtils.compareTo(NumberUtil.round(zxSkStock.getStockPrice(), 2), NumberUtil.round(dbzxSkStock.getStockPrice(), 2)) == 0)
                        && zxSkStockListErrorNumber.size() == 0) {
                    //获取编码,获取名称,获取数量,获取单价
                    zxSkStockListErrorPrice.add(dbzxSkStock);
                    continue;
                }
                if (zxSkStockListErrorNumber.size() == 0 && zxSkStockListErrorPrice.size() == 0) {
                    zxSkStockMap.put(dbzxSkStock.getResID(), dbzxSkStock);
                }
            } else {
                //查询材料基础数据
                //查询物资.提示仓库里没有这条物资
                ZxSkResourceMaterials zxSkResourceMaterials = new ZxSkResourceMaterials();
                zxSkResourceMaterials.setId(zxSkStock.getResID());
                List<ZxSkResourceMaterials> zxSkResourceMaterials1 = zxSkResourceMaterialsMapper.selectByZxSkResourceMaterialsList(zxSkResourceMaterials);
                if (zxSkResourceMaterials1 != null & zxSkResourceMaterials1.size() > 0) {
                    ZxSkResourceMaterials zxSkResourceMaterials2 = zxSkResourceMaterials1.get(0);
                    zxSkStock.setResCode(zxSkResourceMaterials2.getResCode());
                    zxSkStock.setResName(zxSkResourceMaterials2.getResName());
                    zxSkStock.setSpec(zxSkResourceMaterials2.getSpec());
                    zxSkStock.setUnit(zxSkResourceMaterials2.getUnit());
                    zxSkStockListErrorResID.add(zxSkStock);
                }
            }
        }
        if (zxSkStockListErrorNumber != null && zxSkStockListErrorNumber.size() > 0) {
            return repEntity.layerMessage(false, zxSkStockListErrorNumber, "出库数量不能大于库存");
        }
        if (zxSkStockListErrorPrice != null && zxSkStockListErrorPrice.size() > 0) {
            return repEntity.layerMessage(false, zxSkStockListErrorPrice, "出库的单价和库存单价不一致");
        }
        if (zxSkStockListErrorResID != null && zxSkStockListErrorResID.size() > 0) {
            return repEntity.layerMessage(false, zxSkStockListErrorResID, "仓库中没有这个物资");
        }
        for (ZxSkStock zxSkStock : zxSkStockList) {
            //根据map查询仓库中的数据
            ZxSkStock zxSkStock1 = zxSkStockMap.get(zxSkStock.getResID());
            if (CalcUtils.compareTo(NumberUtil.round(zxSkStock.getStockQty(), 3), NumberUtil.round(zxSkStock1.getStockQty(), 3)) == 0
                    && CalcUtils.compareTo(NumberUtil.round(zxSkStock.getStockAmt(), 6), NumberUtil.round(zxSkStock1.getStockAmt(), 6)) == 0) {
                //出库金额等于库存金额
                //如果库存数量等于出库数量就删除该条数据
                ZxSkStock zxSkStock2 = new ZxSkStock();
                zxSkStock2.setCreateUserInfo(userKey, realName);
                List<ZxSkStock> zxSkStocksList = new ArrayList<>();
                zxSkStock1.setStockQty(new BigDecimal(0));
                zxSkStock1.setStockAmt(new BigDecimal(0));
                zxSkStock1.setStockPrice(new BigDecimal(0));
                zxSkStocksList.add(zxSkStock1);
                int i = zxSkStockMapper.updateByPrimaryKey(zxSkStock1);
                if (i == 0) {
                    return repEntity.errorSave();
                } else {
                    flag = zxSkStockMapper.batchDeleteUpdateZxSkStock(zxSkStocksList, zxSkStock2);
                }
            } else {
                //库存数量
                BigDecimal dbstockQty = zxSkStock1.getStockQty();
                //库存单价
                BigDecimal dbstockPrice = zxSkStock1.getStockPrice();
                //库存金额
                BigDecimal dbstockAmt = zxSkStock1.getStockAmt();
                //数量
                dbstockQty = CalcUtils.calcSubtract(dbstockQty, zxSkStock.getStockQty());
                zxSkStock1.setStockQty(dbstockQty);
                //出库金额 = 出库数量 * 库存内单价
                BigDecimal outAmt = NumberUtil.round(CalcUtils.calcMultiply(zxSkStock.getStockQty(), dbstockPrice),2);
                //库存金额
                dbstockAmt = CalcUtils.calcSubtract(dbstockAmt, outAmt);
                zxSkStock1.setStockAmt(dbstockAmt);
                // 共通
                zxSkStock1.setModifyUserInfo(userKey, realName);
                flag = zxSkStockMapper.updateByPrimaryKey(zxSkStock1);
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSkStockList);
        }
    }

    //减少库存(通过数量,总金额 会修改单价)
    @Override
    public ResponseEntity reduceZxSkStockPriceChange(List<ZxSkStock> zxSkStockList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //错误的list (出库数量不能大于库存)
        List<ZxSkStock> zxSkStockListErrorNumber = new ArrayList<>();
        //错误的list (仓库里没有这条物资)
        List<ZxSkStock> zxSkStockListErrorResID = new ArrayList<>();
        //正确的库存list()
        Map<String, ZxSkStock> zxSkStockMap = new HashMap<>();
        for (ZxSkStock zxSkStock : zxSkStockList) {
            //先查(根据仓库Id 项目Id 物资id)
            ZxSkStock zxSkStock1 = new ZxSkStock();
            zxSkStock1.setCompanyID(zxSkStock.getCompanyID());
            zxSkStock1.setOrgID(zxSkStock.getOrgID());
            zxSkStock1.setWhOrgID(zxSkStock.getWhOrgID());
            zxSkStock1.setResID(zxSkStock.getResID());
            List<ZxSkStock> zxSkStocks = zxSkStockMapper.selectByZxSkStockList(zxSkStock1);
            if (zxSkStocks != null && zxSkStocks.size() > 0) {
                ZxSkStock dbzxSkStock = zxSkStocks.get(0);
                //出库数量不能大于库存
                if (CalcUtils.compareTo(dbzxSkStock.getStockQty(), zxSkStock.getStockQty()) < 0) {
                    //获取编码,获取名称,获取数量,获取单价
                    zxSkStockListErrorNumber.add(dbzxSkStock);
                    continue;
                }
                if (zxSkStockListErrorNumber.size() == 0) {
                    zxSkStockMap.put(dbzxSkStock.getResID(), dbzxSkStock);
                }
            } else {
                //查询材料基础数据
                //查询物资.提示仓库里没有这条物资
                ZxSkResourceMaterials zxSkResourceMaterials = new ZxSkResourceMaterials();
                zxSkResourceMaterials.setId(zxSkStock.getResID());
                List<ZxSkResourceMaterials> zxSkResourceMaterials1 = zxSkResourceMaterialsMapper.selectByZxSkResourceMaterialsList(zxSkResourceMaterials);
                if (zxSkResourceMaterials1 != null & zxSkResourceMaterials1.size() > 0) {
                    ZxSkResourceMaterials zxSkResourceMaterials2 = zxSkResourceMaterials1.get(0);
                    zxSkStock.setResCode(zxSkResourceMaterials2.getResCode());
                    zxSkStock.setResName(zxSkResourceMaterials2.getResName());
                    zxSkStock.setSpec(zxSkResourceMaterials2.getSpec());
                    zxSkStock.setUnit(zxSkResourceMaterials2.getUnit());
                    zxSkStockListErrorResID.add(zxSkStock);
                }
            }
        }
        if (zxSkStockListErrorNumber != null && zxSkStockListErrorNumber.size() > 0) {
            return repEntity.layerMessage(false, zxSkStockListErrorNumber, "出库数量不能大于库存");
        }
        if (zxSkStockListErrorResID != null && zxSkStockListErrorResID.size() > 0) {
            return repEntity.layerMessage(false, zxSkStockListErrorResID, "仓库中没有这个物资");
        }
        for (ZxSkStock zxSkStock : zxSkStockList) {
            //根据map查询仓库中的数据
            ZxSkStock zxSkStock1 = zxSkStockMap.get(zxSkStock.getResID());
            if (CalcUtils.compareTo(NumberUtil.round(zxSkStock.getStockQty(), 3), NumberUtil.round(zxSkStock1.getStockQty(), 3)) == 0
                    && CalcUtils.compareTo(NumberUtil.round(zxSkStock.getStockAmt(), 6), NumberUtil.round(zxSkStock1.getStockAmt(), 6)) == 0) {
                //如果库存数量等于出库数量就删除该条数据
                ZxSkStock zxSkStock2 = new ZxSkStock();
                zxSkStock2.setCreateUserInfo(userKey, realName);
                List<ZxSkStock> zxSkStocksList = new ArrayList<>();
                zxSkStock1.setStockQty(new BigDecimal(0));
                zxSkStock1.setStockAmt(new BigDecimal(0));
                zxSkStock1.setStockPrice(new BigDecimal(0));
                zxSkStocksList.add(zxSkStock1);
                int i = zxSkStockMapper.updateByPrimaryKey(zxSkStock1);
                if (i == 0) {
                    return repEntity.errorSave();
                } else {
                    flag = zxSkStockMapper.batchDeleteUpdateZxSkStock(zxSkStocksList, zxSkStock2);
                }
            } else {
                //库存数量
                BigDecimal dbstockQty = zxSkStock1.getStockQty();
                //库存单价
                BigDecimal dbstockPrice = zxSkStock1.getStockPrice();
                //库存金额
                BigDecimal dbstockAmt = zxSkStock1.getStockAmt();

                //数量
                dbstockQty = CalcUtils.calcSubtract(dbstockQty, zxSkStock.getStockQty());
                zxSkStock1.setStockQty(dbstockQty);
                //总金额
                dbstockAmt = CalcUtils.calcSubtract(dbstockAmt, zxSkStock.getStockAmt());
                zxSkStock1.setStockAmt(dbstockAmt);
                //单价
                dbstockPrice = CalcUtils.calcDivide(dbstockAmt, dbstockQty,6);
                zxSkStock1.setStockPrice(dbstockPrice);
                // 共通
                zxSkStock1.setModifyUserInfo(userKey, realName);
                flag = zxSkStockMapper.updateByPrimaryKey(zxSkStock1);
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSkStockList);
        }
    }

    //盘盈入库库存
    @Override
    public ResponseEntity addZxSkStockNumTotalChange(List<ZxSkStock> zxSkStockList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        for (ZxSkStock zxSkStock : zxSkStockList) {
            ZxSkStock zxSkStock1 = new ZxSkStock();
            zxSkStock1.setCompanyID(zxSkStock.getCompanyID());
            zxSkStock1.setOrgID(zxSkStock.getOrgID());
            zxSkStock1.setWhOrgID(zxSkStock.getWhOrgID());
            zxSkStock1.setResID(zxSkStock.getResID());
            //获取库存
            List<ZxSkStock> zxSkStocks = zxSkStockMapper.selectByZxSkStockListNew(zxSkStock1);
            if (zxSkStocks != null && zxSkStocks.size() > 0) {
                ZxSkStock dbzxSkStock = zxSkStocks.get(0);
                //库存数量
                BigDecimal dbstockQty = dbzxSkStock.getStockQty();
                //库存金额  stockAmt
                BigDecimal dbstockAmt = dbzxSkStock.getStockAmt();
                //数量
                dbstockQty = CalcUtils.calcAdd(dbstockQty, zxSkStock.getStockQty());
                dbzxSkStock.setStockQty(dbstockQty);
                //库存金额
                dbstockAmt = CalcUtils.calcAdd(dbstockAmt, zxSkStock.getStockAmt());
                dbzxSkStock.setStockAmt(dbstockAmt);
                //单价
                dbzxSkStock.setStockPrice(CalcUtils.calcDivide(dbstockAmt, dbstockQty,6));
                // 共通
                dbzxSkStock.setModifyUserInfo(userKey, realName);
                dbzxSkStock.setDelFlag("0");
                flag = zxSkStockMapper.updateByPrimaryKey(dbzxSkStock);
            } else {
                //todo:不知道这块要不要报错
                //创建数据
                zxSkStock.setId(UuidUtil.generate());
                zxSkStock.setStockPrice(CalcUtils.calcDivide(zxSkStock.getStockAmt(), zxSkStock.getStockQty(),6));
                zxSkStock.setCreateUserInfo(userKey, realName);
                flag = zxSkStockMapper.insert(zxSkStock);
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSkStockList);
        }
    }

    //盘亏出库库存(减少库存(通过数量,总金额 会修改单价))
    @Override
    public ResponseEntity reduceZxSkStockNumTotalChange(List<ZxSkStock> zxSkStockList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //错误的list (出库数量不能大于库存)
        List<ZxSkStock> zxSkStockListErrorNumber = new ArrayList<>();
        //错误的list (仓库里没有这条物资)
        List<ZxSkStock> zxSkStockListErrorResID = new ArrayList<>();
        //正确的库存list()
        Map<String, ZxSkStock> zxSkStockMap = new HashMap<>();
        for (ZxSkStock zxSkStock : zxSkStockList) {
            //先查(根据仓库Id 项目Id 物资id)
            ZxSkStock zxSkStock1 = new ZxSkStock();
            zxSkStock1.setCompanyID(zxSkStock.getCompanyID());
            zxSkStock1.setOrgID(zxSkStock.getOrgID());
            zxSkStock1.setWhOrgID(zxSkStock.getWhOrgID());
            zxSkStock1.setResID(zxSkStock.getResID());
            List<ZxSkStock> zxSkStocks = zxSkStockMapper.selectByZxSkStockList(zxSkStock1);
            if (zxSkStocks != null && zxSkStocks.size() > 0) {
                ZxSkStock dbzxSkStock = zxSkStocks.get(0);
                //出库数量不能大于库存
                if (CalcUtils.compareTo(dbzxSkStock.getStockQty(), zxSkStock.getStockQty()) < 0) {
                    //获取编码,获取名称,获取数量,获取单价
                    zxSkStockListErrorNumber.add(dbzxSkStock);
                    continue;
                }
                if (zxSkStockListErrorNumber.size() == 0) {
                    zxSkStockMap.put(dbzxSkStock.getResID(), dbzxSkStock);
                }
            } else {
                //查询材料基础数据
                //查询物资.提示仓库里没有这条物资
                ZxSkResourceMaterials zxSkResourceMaterials = new ZxSkResourceMaterials();
                zxSkResourceMaterials.setId(zxSkStock.getResID());
                List<ZxSkResourceMaterials> zxSkResourceMaterials1 = zxSkResourceMaterialsMapper.selectByZxSkResourceMaterialsList(zxSkResourceMaterials);
                if (zxSkResourceMaterials1 != null & zxSkResourceMaterials1.size() > 0) {
                    ZxSkResourceMaterials zxSkResourceMaterials2 = zxSkResourceMaterials1.get(0);
                    zxSkStock.setResCode(zxSkResourceMaterials2.getResCode());
                    zxSkStock.setResName(zxSkResourceMaterials2.getResName());
                    zxSkStock.setSpec(zxSkResourceMaterials2.getSpec());
                    zxSkStock.setUnit(zxSkResourceMaterials2.getUnit());
                    zxSkStockListErrorResID.add(zxSkStock);
                }
            }
        }
        if (zxSkStockListErrorNumber != null && zxSkStockListErrorNumber.size() > 0) {
            return repEntity.layerMessage(false, zxSkStockListErrorNumber, "出库数量不能大于库存");
        }
        if (zxSkStockListErrorResID != null && zxSkStockListErrorResID.size() > 0) {
            return repEntity.layerMessage(false, zxSkStockListErrorResID, "仓库中没有这个物资");
        }
        for (ZxSkStock zxSkStock : zxSkStockList) {
            //根据map查询仓库中的数据
            ZxSkStock zxSkStock1 = zxSkStockMap.get(zxSkStock.getResID());
            if (CalcUtils.compareTo(NumberUtil.round(zxSkStock.getStockQty(), 3), NumberUtil.round(zxSkStock1.getStockQty(), 3)) == 0
                    && CalcUtils.compareTo(NumberUtil.round(zxSkStock.getStockAmt(), 6), NumberUtil.round(zxSkStock1.getStockAmt(), 6)) == 0) {
                //如果库存数量等于出库数量就删除该条数据
                ZxSkStock zxSkStock2 = new ZxSkStock();
                zxSkStock2.setCreateUserInfo(userKey, realName);
                List<ZxSkStock> zxSkStocksList = new ArrayList<>();
                zxSkStock1.setStockQty(new BigDecimal(0));
                zxSkStock1.setStockAmt(new BigDecimal(0));
                zxSkStock1.setStockPrice(new BigDecimal(0));
                zxSkStocksList.add(zxSkStock1);
                int i = zxSkStockMapper.updateByPrimaryKey(zxSkStock1);
                if (i == 0) {
                    return repEntity.errorSave();
                } else {
                    flag = zxSkStockMapper.batchDeleteUpdateZxSkStock(zxSkStocksList, zxSkStock2);
                }
            } else {
                //库存数量
                BigDecimal dbstockQty = zxSkStock1.getStockQty();
                //库存单价
                BigDecimal dbstockPrice = zxSkStock1.getStockPrice();
                //库存金额
                BigDecimal dbstockAmt = zxSkStock1.getStockAmt();
                //数量
                dbstockQty = CalcUtils.calcSubtract(dbstockQty, zxSkStock.getStockQty());
                zxSkStock1.setStockQty(dbstockQty);
                //总金额
                dbstockAmt = CalcUtils.calcSubtract(dbstockAmt, zxSkStock.getStockAmt());
                zxSkStock1.setStockAmt(dbstockAmt);
                //单价
                dbstockPrice = CalcUtils.calcDivide(dbstockAmt, dbstockQty,6);
                zxSkStock1.setStockPrice(dbstockPrice);
                // 共通
                zxSkStock1.setModifyUserInfo(userKey, realName);
                flag = zxSkStockMapper.updateByPrimaryKey(zxSkStock1);
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSkStockList);
        }
    }

    //只修改总金额(冲账单)   如果冲账把总金额和数量变成0删除这条数据
    @Override
    public ResponseEntity changeZxSkStockTotal(List<ZxSkStock> zxSkStockList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        for (ZxSkStock zxSkStock : zxSkStockList) {
            ZxSkStock zxSkStock1 = new ZxSkStock();
            zxSkStock1.setCompanyID(zxSkStock.getCompanyID());
            zxSkStock1.setWhOrgID(zxSkStock.getWhOrgID());
            zxSkStock1.setResID(zxSkStock.getResID());
            //获取库存
            List<ZxSkStock> zxSkStocks = zxSkStockMapper.selectByZxSkStockList(zxSkStock1);
            if (zxSkStocks != null && zxSkStocks.size() > 0) {
                ZxSkStock dbzxSkStock = zxSkStocks.get(0);
                //库存数量
                BigDecimal dbstockQty = dbzxSkStock.getStockQty();
                //库存金额  stockAmt
                BigDecimal dbstockAmt = dbzxSkStock.getStockAmt();

                //库存金额
                dbstockAmt = CalcUtils.calcAdd(dbstockAmt, zxSkStock.getStockAmt());
                dbzxSkStock.setStockAmt(dbstockAmt);
                //单价
                dbzxSkStock.setStockPrice(CalcUtils.calcDivide(dbstockAmt, dbstockQty,6));
                // 共通
                dbzxSkStock.setModifyUserInfo(userKey, realName);
                //如果值都为0  那么就删除这条数据
                if (CalcUtils.compareToZero(dbstockAmt) == 0
                        && CalcUtils.compareToZero(dbzxSkStock.getStockPrice()) == 0
                        && CalcUtils.compareToZero(dbstockQty) == 0) {
                    ZxSkStock zxSkStock2 = new ZxSkStock();
                    zxSkStock2.setCreateUserInfo(userKey, realName);
                    List<ZxSkStock> zxSkStocksList = new ArrayList<>();
                    dbzxSkStock.setStockQty(new BigDecimal(0));
                    dbzxSkStock.setStockAmt(new BigDecimal(0));
                    dbzxSkStock.setStockPrice(new BigDecimal(0));
                    zxSkStocksList.add(dbzxSkStock);
                    int i = zxSkStockMapper.updateByPrimaryKey(dbzxSkStock);
                    if (i == 0) {
                        return repEntity.errorSave();
                    } else {
                        flag = zxSkStockMapper.batchDeleteUpdateZxSkStock(zxSkStocksList, zxSkStock2);
                    }
                } else {
                    flag = zxSkStockMapper.updateByPrimaryKey(dbzxSkStock);
                }
            } else {
                //todo:不知道要不要报错这块
                //创建数据
                zxSkStock.setId(UuidUtil.generate());
                zxSkStock.setStockPrice(CalcUtils.calcDivide(zxSkStock.getStockAmt(), zxSkStock.getStockQty(),6));
                zxSkStock.setCreateUserInfo(userKey, realName);
                flag = zxSkStockMapper.insert(zxSkStock);
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSkStockList);
        }
    }

    //获取库存
    @Override
    public ResponseEntity getZxSkStockDataList(ZxSkStock zxSkStock) {
        if (
//                StrUtil.isEmpty(zxSkStock.getCompanyID())
//                ||StrUtil.isEmpty(zxSkStock.getOrgID())
                 StrUtil.isEmpty(zxSkStock.getWhOrgID())) {
            return repEntity.ok(new ArrayList<>());
//            return repEntity.layerMessage("no", "无数据！");
        }
        // 分页查询
        PageHelper.startPage(zxSkStock.getPage(), zxSkStock.getLimit());
        // 获取数据
        List<ZxSkStock> zxSkStockList = zxSkStockMapper.selectByZxSkStockList(zxSkStock);
        // 得到分页信息
        PageInfo<ZxSkStock> p = new PageInfo<>(zxSkStockList);

        return repEntity.okList(zxSkStockList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkStockResCategoryDataList(ZxSkStock zxSkStock) {
        if (StrUtil.isEmpty(zxSkStock.getWhOrgID())) {
            return repEntity.ok(new ArrayList<>());
        }
        // 获取数据
        List<ZxSkStock> zxSkStockList = zxSkStockMapper.getZxSkStockResCategoryDataList(zxSkStock);
        return repEntity.ok(zxSkStockList);
    }


}
