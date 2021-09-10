package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.apih5.mybatis.pojo.ZxBuWorksToStockPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxBuStockPriceItemMapper;
import com.apih5.mybatis.pojo.ZxBuStockPriceItem;
import com.apih5.service.ZxBuStockPriceItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxBuStockPriceItemService")
public class ZxBuStockPriceItemServiceImpl implements ZxBuStockPriceItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxBuStockPriceItemMapper zxBuStockPriceItemMapper;

    @Override
    public ResponseEntity getZxBuStockPriceItemListByCondition(ZxBuStockPriceItem zxBuStockPriceItem) {
        if (zxBuStockPriceItem == null) {
            zxBuStockPriceItem = new ZxBuStockPriceItem();
        }
        if(StrUtil.isEmpty(zxBuStockPriceItem.getOrgID()) || StrUtil.isEmpty(zxBuStockPriceItem.getMixType())){
            return repEntity.ok(new ArrayList());
        }
        // 分页查询
        PageHelper.startPage(zxBuStockPriceItem.getPage(),zxBuStockPriceItem.getLimit());
        // 获取数据
        List<ZxBuStockPriceItem> zxBuStockPriceItemList = zxBuStockPriceItemMapper.selectByZxBuStockPriceItemList(zxBuStockPriceItem);
        // 得到分页信息
        PageInfo<ZxBuStockPriceItem> p = new PageInfo<>(zxBuStockPriceItemList);

        return repEntity.okList(zxBuStockPriceItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxBuStockPriceItemDetail(ZxBuStockPriceItem zxBuStockPriceItem) {
        if (zxBuStockPriceItem == null) {
            zxBuStockPriceItem = new ZxBuStockPriceItem();
        }
        // 获取数据
        ZxBuStockPriceItem dbZxBuStockPriceItem = zxBuStockPriceItemMapper.selectByPrimaryKey(zxBuStockPriceItem.getZxBuStockPriceItemId());
        // 数据存在
        if (dbZxBuStockPriceItem != null) {
            return repEntity.ok(dbZxBuStockPriceItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxBuStockPriceItem(ZxBuStockPriceItem zxBuStockPriceItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        //先查一下 获取编号
        //根据项目id
        ZxBuStockPriceItem zxBuStockPriceItem1 = new ZxBuStockPriceItem();
        zxBuStockPriceItem1.setOrgID(zxBuStockPriceItem.getOrgID());
        List<ZxBuStockPriceItem> zxBuStockPriceItems = zxBuStockPriceItemMapper.selectByZxBuStockPriceItemList(zxBuStockPriceItem1);
        zxBuStockPriceItem.setResCode(String.valueOf(zxBuStockPriceItems.size()+1));
        zxBuStockPriceItem.setZxBuStockPriceItemId(UuidUtil.generate());
        zxBuStockPriceItem.setCreateUserInfo(userKey, realName);
        //默认参考值
        zxBuStockPriceItem.setReferValue(new BigDecimal("0"));
        //是否调差 默认是
        zxBuStockPriceItem.setIsAdjustment("0");
        zxBuStockPriceItem.setMcsgPriceTax(new BigDecimal("0"));
        zxBuStockPriceItem.setYsFeeTax(new BigDecimal("0"));
        zxBuStockPriceItem.setScenePrice(new BigDecimal("0"));
        zxBuStockPriceItem.setScenePrice(new BigDecimal("0"));
        zxBuStockPriceItem.setTaxAmt(new BigDecimal("0"));
        int flag = zxBuStockPriceItemMapper.insert(zxBuStockPriceItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxBuStockPriceItem);
        }
    }

    @Override
    public ResponseEntity updateZxBuStockPriceItem(ZxBuStockPriceItem zxBuStockPriceItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxBuStockPriceItem dbZxBuStockPriceItem = zxBuStockPriceItemMapper.selectByPrimaryKey(zxBuStockPriceItem.getZxBuStockPriceItemId());
        if (dbZxBuStockPriceItem != null && StrUtil.isNotEmpty(dbZxBuStockPriceItem.getZxBuStockPriceItemId())) {
           // 材料编号
           dbZxBuStockPriceItem.setResCode(zxBuStockPriceItem.getResCode());
           // 材料名称
           dbZxBuStockPriceItem.setResName(zxBuStockPriceItem.getResName());
           // 资源ID
           dbZxBuStockPriceItem.setResID(zxBuStockPriceItem.getResID());
           // 资源单价
           dbZxBuStockPriceItem.setResPrice(zxBuStockPriceItem.getResPrice());
           // 
           dbZxBuStockPriceItem.setEditTime(zxBuStockPriceItem.getEditTime());
           // 物资价格库ID
           dbZxBuStockPriceItem.setStockPriceID(zxBuStockPriceItem.getStockPriceID());
           // 单位
           dbZxBuStockPriceItem.setUnit(zxBuStockPriceItem.getUnit());
           // 是否复合物资
           dbZxBuStockPriceItem.setIsOrNotCom(zxBuStockPriceItem.getIsOrNotCom());
           // 市场价
           dbZxBuStockPriceItem.setConstrPrice(zxBuStockPriceItem.getConstrPrice());
           // 总量
           dbZxBuStockPriceItem.setAllAmt(zxBuStockPriceItem.getAllAmt());
           // 已完成量
           dbZxBuStockPriceItem.setCompleQty(zxBuStockPriceItem.getCompleQty());
           // 平均单价
           dbZxBuStockPriceItem.setAvgPrice(zxBuStockPriceItem.getAvgPrice());
           // 首次标后预算资源单价(不含税)
           dbZxBuStockPriceItem.setScbhPrice(zxBuStockPriceItem.getScbhPrice());
           // 末次标后预算资源单价(不含税)
           dbZxBuStockPriceItem.setMcbhPrice(zxBuStockPriceItem.getMcbhPrice());
           // 首次施工预算资源单价(不含税)
           dbZxBuStockPriceItem.setScsgPrice(zxBuStockPriceItem.getScsgPrice());
           // 材料价格(不含税)
           dbZxBuStockPriceItem.setMcsgPrice(zxBuStockPriceItem.getMcsgPrice());
           // 启用该资源
           dbZxBuStockPriceItem.setIsStartRes(zxBuStockPriceItem.getIsStartRes());
           // 参考值
           dbZxBuStockPriceItem.setReferValue(zxBuStockPriceItem.getReferValue());
           // 剩余工程
           dbZxBuStockPriceItem.setMcsgAPrice(zxBuStockPriceItem.getMcsgAPrice());
           // 数量
           dbZxBuStockPriceItem.setQty(zxBuStockPriceItem.getQty());
           // 税率
           dbZxBuStockPriceItem.setTaxRate(zxBuStockPriceItem.getTaxRate());
           // 税金单价
           dbZxBuStockPriceItem.setMcsgPriceTax(zxBuStockPriceItem.getMcsgPriceTax());
           // 运费(不含税)
           dbZxBuStockPriceItem.setYsFee(zxBuStockPriceItem.getYsFee());
           // 税率
           dbZxBuStockPriceItem.setYsTaxRate(zxBuStockPriceItem.getYsTaxRate());
           // 税金单价
           dbZxBuStockPriceItem.setYsFeeTax(zxBuStockPriceItem.getYsFeeTax());
           // 不含税到场价(元)
           dbZxBuStockPriceItem.setScenePrice(zxBuStockPriceItem.getScenePrice());
           // 进项税金(元)
           dbZxBuStockPriceItem.setTaxAmt(zxBuStockPriceItem.getTaxAmt());
           // 不含税总价(元)
           dbZxBuStockPriceItem.setSumAmt(zxBuStockPriceItem.getSumAmt());
           // bhAmt
           dbZxBuStockPriceItem.setBhAmt(zxBuStockPriceItem.getBhAmt());
           // 拌合运输费(元/m3)
           dbZxBuStockPriceItem.setYsAmt(zxBuStockPriceItem.getYsAmt());
           // totalAmt
           dbZxBuStockPriceItem.setTotalAmt(zxBuStockPriceItem.getTotalAmt());
           // mixType
           dbZxBuStockPriceItem.setMixType(zxBuStockPriceItem.getMixType());
           // qty1
           dbZxBuStockPriceItem.setQty1(zxBuStockPriceItem.getQty1());
           // price1
           dbZxBuStockPriceItem.setPrice1(zxBuStockPriceItem.getPrice1());
           // amt1
           dbZxBuStockPriceItem.setAmt1(zxBuStockPriceItem.getAmt1());
           // qty2
           dbZxBuStockPriceItem.setQty2(zxBuStockPriceItem.getQty2());
           // price2
           dbZxBuStockPriceItem.setPrice2(zxBuStockPriceItem.getPrice2());
           // amt3
           dbZxBuStockPriceItem.setAmt3(zxBuStockPriceItem.getAmt3());
           // qty3
           dbZxBuStockPriceItem.setQty3(zxBuStockPriceItem.getQty3());
           // price3
           dbZxBuStockPriceItem.setPrice3(zxBuStockPriceItem.getPrice3());
           // amt12
           dbZxBuStockPriceItem.setAmt12(zxBuStockPriceItem.getAmt12());
           // amt2
           dbZxBuStockPriceItem.setAmt2(zxBuStockPriceItem.getAmt2());
           // qty4
           dbZxBuStockPriceItem.setQty4(zxBuStockPriceItem.getQty4());
           // price4
           dbZxBuStockPriceItem.setPrice4(zxBuStockPriceItem.getPrice4());
           // amt4
           dbZxBuStockPriceItem.setAmt4(zxBuStockPriceItem.getAmt4());
           // qty5
           dbZxBuStockPriceItem.setQty5(zxBuStockPriceItem.getQty5());
           // price5
           dbZxBuStockPriceItem.setPrice5(zxBuStockPriceItem.getPrice5());
           // amt5
           dbZxBuStockPriceItem.setAmt5(zxBuStockPriceItem.getAmt5());
           // qty6
           dbZxBuStockPriceItem.setQty6(zxBuStockPriceItem.getQty6());
           // price6
           dbZxBuStockPriceItem.setPrice6(zxBuStockPriceItem.getPrice6());
           // amt6
           dbZxBuStockPriceItem.setAmt6(zxBuStockPriceItem.getAmt6());
           // qty7
           dbZxBuStockPriceItem.setQty7(zxBuStockPriceItem.getQty7());
           // price7
           dbZxBuStockPriceItem.setPrice7(zxBuStockPriceItem.getPrice7());
           // amt7
           dbZxBuStockPriceItem.setAmt7(zxBuStockPriceItem.getAmt7());
           // qty8
           dbZxBuStockPriceItem.setQty8(zxBuStockPriceItem.getQty8());
           // price8
           dbZxBuStockPriceItem.setPrice8(zxBuStockPriceItem.getPrice8());
           // amt8
           dbZxBuStockPriceItem.setAmt8(zxBuStockPriceItem.getAmt8());
           // qty9
           dbZxBuStockPriceItem.setQty9(zxBuStockPriceItem.getQty9());
           // price9
           dbZxBuStockPriceItem.setPrice9(zxBuStockPriceItem.getPrice9());
           // amt9
           dbZxBuStockPriceItem.setAmt9(zxBuStockPriceItem.getAmt9());
           // qty10
           dbZxBuStockPriceItem.setQty10(zxBuStockPriceItem.getQty10());
           // price10
           dbZxBuStockPriceItem.setPrice10(zxBuStockPriceItem.getPrice10());
           // amt10
           dbZxBuStockPriceItem.setAmt10(zxBuStockPriceItem.getAmt10());
           // qty11
           dbZxBuStockPriceItem.setQty11(zxBuStockPriceItem.getQty11());
           // price11
           dbZxBuStockPriceItem.setPrice11(zxBuStockPriceItem.getPrice11());
           // amt11
           dbZxBuStockPriceItem.setAmt11(zxBuStockPriceItem.getAmt11());
           // qty12
           dbZxBuStockPriceItem.setQty12(zxBuStockPriceItem.getQty12());
           // price12
           dbZxBuStockPriceItem.setPrice12(zxBuStockPriceItem.getPrice12());
           // 规格型号
           dbZxBuStockPriceItem.setSpec(zxBuStockPriceItem.getSpec());
           // qty13
           dbZxBuStockPriceItem.setQty13(zxBuStockPriceItem.getQty13());
           // price13
           dbZxBuStockPriceItem.setPrice13(zxBuStockPriceItem.getPrice13());
           // amt13
           dbZxBuStockPriceItem.setAmt13(zxBuStockPriceItem.getAmt13());
           // flg
           dbZxBuStockPriceItem.setFlg(zxBuStockPriceItem.getFlg());
           // 机构ID
           dbZxBuStockPriceItem.setOrgID(zxBuStockPriceItem.getOrgID());
           // 是否调差
           dbZxBuStockPriceItem.setIsAdjustment(zxBuStockPriceItem.getIsAdjustment());
           // 备注
           dbZxBuStockPriceItem.setRemarks(zxBuStockPriceItem.getRemarks());
           // 排序
           dbZxBuStockPriceItem.setSort(zxBuStockPriceItem.getSort());
           // 共通
           dbZxBuStockPriceItem.setModifyUserInfo(userKey, realName);
           flag = zxBuStockPriceItemMapper.updateByPrimaryKey(dbZxBuStockPriceItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxBuStockPriceItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxBuStockPriceItem(List<ZxBuStockPriceItem> zxBuStockPriceItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxBuStockPriceItemList != null && zxBuStockPriceItemList.size() > 0) {
           ZxBuStockPriceItem zxBuStockPriceItem = new ZxBuStockPriceItem();
           zxBuStockPriceItem.setModifyUserInfo(userKey, realName);
           flag = zxBuStockPriceItemMapper.batchDeleteUpdateZxBuStockPriceItem(zxBuStockPriceItemList, zxBuStockPriceItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxBuStockPriceItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓


    @Override
    public ResponseEntity getBaseZxBuStockPriceItemList(ZxBuStockPriceItem zxBuStockPriceItem) {
        String filePathMaster = "C:\\Users\\张三\\Desktop\\stockPriceNew.xls";
        ExcelReader readerMaster = ExcelUtil.getReader(filePathMaster);
        List<ZxBuStockPriceItem> zxBuStockPriceItems = new ArrayList<>();
        List<Map<String, Object>> readMaster = readerMaster.read(zxBuStockPriceItem.getSheetHeadNumber(), zxBuStockPriceItem.getSheetStartNumber(), readerMaster.getSheet().getLastRowNum());
        if (readMaster == null || readMaster.size() == 0) {
            return repEntity.layerMessage("no", "基础物资列表无数据");
        }
        for (int i = 0; i < readMaster.size(); i++) {
            ZxBuStockPriceItem dbzxBuStockPriceItem = new ZxBuStockPriceItem();
            String resCode = String.valueOf(readMaster.get(i).getOrDefault("材料编号", ""));
            String resName = String.valueOf(readMaster.get(i).getOrDefault("材料名称", ""));
            String unit = String.valueOf(readMaster.get(i).getOrDefault("单位", ""));
            String spec = String.valueOf(readMaster.get(i).getOrDefault("规格型号", ""));
            dbzxBuStockPriceItem.setResCode(resCode);
            dbzxBuStockPriceItem.setResName(resName);
            dbzxBuStockPriceItem.setUnit(unit);
            dbzxBuStockPriceItem.setSpec(spec);
            zxBuStockPriceItems.add(dbzxBuStockPriceItem);
        }
        return repEntity.ok(zxBuStockPriceItems);
    }

    @Override
    public ResponseEntity getZxBuStockPriceItemResAll(ZxBuStockPriceItem zxBuStockPriceItem) {
        //获取所有的材料    材料和复合材料
        if(StrUtil.isEmpty(zxBuStockPriceItem.getOrgID())){
            return repEntity.ok(new ArrayList<>());
        }
        List<ZxBuStockPriceItem> zxBuStockPriceItemList = zxBuStockPriceItemMapper.selectByZxBuStockPriceItemList(zxBuStockPriceItem);
        //组装id
        //名字_编号_单位_规格型号_id		这里的id是stockPriceid
        for (ZxBuStockPriceItem buStockPriceItem : zxBuStockPriceItemList) {
            buStockPriceItem.setStockVOID(buStockPriceItem.getResName()
                    +"_"+buStockPriceItem.getResCode()
                    +"_"+buStockPriceItem.getUnit()
                    +"_"+buStockPriceItem.getSpec()
                    +"_"+buStockPriceItem.getStockPriceID());
        }

        return repEntity.ok(zxBuStockPriceItemList);
    }


}
