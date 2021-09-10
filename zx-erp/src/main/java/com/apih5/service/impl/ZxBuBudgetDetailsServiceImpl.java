package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.ZxBuStockPriceItemMapper;
import com.apih5.mybatis.pojo.ZxBuStockPrice;
import com.apih5.mybatis.pojo.ZxBuStockPriceItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxBuBudgetDetailsMapper;
import com.apih5.mybatis.pojo.ZxBuBudgetDetails;
import com.apih5.service.ZxBuBudgetDetailsService;
import cn.hutool.core.util.StrUtil;

@Service("zxBuBudgetDetailsService")
public class ZxBuBudgetDetailsServiceImpl implements ZxBuBudgetDetailsService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxBuBudgetDetailsMapper zxBuBudgetDetailsMapper;

    @Autowired(required = true)
    private ZxBuStockPriceItemMapper zxBuStockPriceItemMapper;

    @Override
    public ResponseEntity getZxBuBudgetDetailsListByCondition(ZxBuBudgetDetails zxBuBudgetDetails) {
        if(StrUtil.isEmpty(zxBuBudgetDetails.getBudgetBookID())){
            return repEntity.ok(new ArrayList<>());
        }
        // 获取数据
        List<ZxBuBudgetDetails> zxBuBudgetDetailsList = zxBuBudgetDetailsMapper.selectByZxBuBudgetDetailsList(zxBuBudgetDetails);

        //处理集合往里添加数据  6-1销项税金,6-2进项税金,6-3测算税负
        ZxBuBudgetDetails zxBuBudgetDetails61 = new ZxBuBudgetDetails();
        //序号 6-1
        zxBuBudgetDetails61.setSerialNumber("6-1");
        //名称
        zxBuBudgetDetails61.setBudgetElement("销项税金");
        //费用
        zxBuBudgetDetails61.setBudgetElementFirstFree(new BigDecimal("0"));
        //比例
        zxBuBudgetDetails61.setNeedDeduct(0);
        zxBuBudgetDetailsList.add(6,zxBuBudgetDetails61);


        ZxBuBudgetDetails zxBuBudgetDetails62 = new ZxBuBudgetDetails();
        //序号 6-2
        zxBuBudgetDetails62.setSerialNumber("6-2");
        //名称
        zxBuBudgetDetails62.setBudgetElement("进项税金");
        //费用(这里使用的是标后预算,材料价格表中进项税金的总额)
        ZxBuStockPriceItem zxBuStockPriceItem = new ZxBuStockPriceItem();
        zxBuStockPriceItem.setOrgID(zxBuBudgetDetails.getOrgID());
        List<ZxBuStockPriceItem> zxBuStockPriceItems = zxBuStockPriceItemMapper.selectByZxBuStockPriceItemList(zxBuStockPriceItem);
        if(CollectionUtil.isNotEmpty(zxBuStockPriceItems)){
            BigDecimal sum = zxBuStockPriceItems.stream().map(ZxBuStockPriceItem::getTaxAmt).reduce(BigDecimal::add).get();
            zxBuBudgetDetails62.setBudgetElementFirstFree(sum);
        }else {
            zxBuBudgetDetails62.setBudgetElementFirstFree(new BigDecimal("0"));
        }
        //加上手填的进项税金
        if(CollectionUtil.isNotEmpty(zxBuBudgetDetailsList)){
            //5
            List<ZxBuBudgetDetails> zxBuBudgetDetailList = zxBuBudgetDetailsList.stream().filter(o -> StrUtil.equals(o.getSerialNumber(), "5")).collect(Collectors.toList());
            BigDecimal firstJtFee = zxBuBudgetDetailList.get(0).getFirstJtFee();
            firstJtFee = NumberUtil.isNumber(String.valueOf(firstJtFee)) ? firstJtFee : new BigDecimal("0");
            zxBuBudgetDetails62.setBudgetElementFirstFree(CalcUtils.calcAdd(zxBuBudgetDetails62.getBudgetElementFirstFree(),firstJtFee));
        }
        //比例
        zxBuBudgetDetails62.setNeedDeduct(0);
        zxBuBudgetDetailsList.add(7,zxBuBudgetDetails62);


        ZxBuBudgetDetails zxBuBudgetDetails63 = new ZxBuBudgetDetails();
        //序号 6-3
        zxBuBudgetDetails63.setSerialNumber("6-3");
        //名称
        zxBuBudgetDetails63.setBudgetElement("测算税负");
        //费用
        zxBuBudgetDetails63.setBudgetElementFirstFree(new BigDecimal("0"));
        //比例
        zxBuBudgetDetails63.setNeedDeduct(0);
        zxBuBudgetDetailsList.add(8,zxBuBudgetDetails63);


        //标后预算费用合计（元）
        ZxBuBudgetDetails zxBuBudgetDetails13 = new ZxBuBudgetDetails();
        //名称
        zxBuBudgetDetails13.setBudgetElement("标后预算费用合计(元)");
        //费用
        zxBuBudgetDetails13.setBudgetElementFirstFree(new BigDecimal("0"));
        //比例
        zxBuBudgetDetails13.setNeedDeduct(0);
        zxBuBudgetDetailsList.add(13,zxBuBudgetDetails13);


        //建筑安装工程费（元）
        ZxBuBudgetDetails zxBuBudgetDetails15 = new ZxBuBudgetDetails();
        //名称
        zxBuBudgetDetails15.setBudgetElement("建筑安装工程费(元)");
        //费用
        zxBuBudgetDetails15.setBudgetElementFirstFree(new BigDecimal("0"));
        //比例
        zxBuBudgetDetails15.setNeedDeduct(0);
        zxBuBudgetDetailsList.add(15,zxBuBudgetDetails15);


        //单位预留费用（元）
        ZxBuBudgetDetails zxBuBudgetDetails16 = new ZxBuBudgetDetails();
        //名称
        zxBuBudgetDetails16.setBudgetElement("单位预留费用(元)");
        //费用
        zxBuBudgetDetails16.setBudgetElementFirstFree(new BigDecimal("0"));
        //比例
        zxBuBudgetDetails16.setNeedDeduct(0);
        zxBuBudgetDetailsList.add(16,zxBuBudgetDetails16);


        //标后预算切块比例（%）
        ZxBuBudgetDetails zxBuBudgetDetails17 = new ZxBuBudgetDetails();
        //名称
        zxBuBudgetDetails17.setBudgetElement("标后预算切块比例(%)");
        //费用
        zxBuBudgetDetails17.setBudgetElementFirstFree(new BigDecimal("0"));
        //比例
        zxBuBudgetDetails17.setNeedDeduct(0);
        zxBuBudgetDetailsList.add(17,zxBuBudgetDetails17);

        return repEntity.ok(zxBuBudgetDetailsList);
    }

    @Override
    public ResponseEntity getZxBuBudgetDetailsDetail(ZxBuBudgetDetails zxBuBudgetDetails) {
        if (zxBuBudgetDetails == null) {
            zxBuBudgetDetails = new ZxBuBudgetDetails();
        }
        // 获取数据
        ZxBuBudgetDetails dbZxBuBudgetDetails = zxBuBudgetDetailsMapper.selectByPrimaryKey(zxBuBudgetDetails.getZxBuBudgetDetailsId());
        // 数据存在
        if (dbZxBuBudgetDetails != null) {
            return repEntity.ok(dbZxBuBudgetDetails);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxBuBudgetDetails(ZxBuBudgetDetails zxBuBudgetDetails) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxBuBudgetDetails.setZxBuBudgetDetailsId(UuidUtil.generate());
        zxBuBudgetDetails.setCreateUserInfo(userKey, realName);
        int flag = zxBuBudgetDetailsMapper.insert(zxBuBudgetDetails);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxBuBudgetDetails);
        }
    }

    @Override
    public ResponseEntity updateZxBuBudgetDetails(ZxBuBudgetDetails zxBuBudgetDetails) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxBuBudgetDetails dbZxBuBudgetDetails = zxBuBudgetDetailsMapper.selectByPrimaryKey(zxBuBudgetDetails.getZxBuBudgetDetailsId());
        if (dbZxBuBudgetDetails != null && StrUtil.isNotEmpty(dbZxBuBudgetDetails.getZxBuBudgetDetailsId())) {
           // null
           dbZxBuBudgetDetails.setBudgetBookID(zxBuBudgetDetails.getBudgetBookID());
           // 预算类型
           dbZxBuBudgetDetails.setBudgetType(zxBuBudgetDetails.getBudgetType());
           // 预算元素类型
           dbZxBuBudgetDetails.setBudgetElementType(zxBuBudgetDetails.getBudgetElementType());
           // 预算元素
           dbZxBuBudgetDetails.setBudgetElement(zxBuBudgetDetails.getBudgetElement());
           // 预算元素单位
           dbZxBuBudgetDetails.setBudgetElementUnit(zxBuBudgetDetails.getBudgetElementUnit());
           // 预算元素首要费用
           dbZxBuBudgetDetails.setBudgetElementFirstFree(zxBuBudgetDetails.getBudgetElementFirstFree());
           // 预算元素当前的费用
           dbZxBuBudgetDetails.setBudgetElementCurrFree(zxBuBudgetDetails.getBudgetElementCurrFree());
           // null
           dbZxBuBudgetDetails.setAudited(zxBuBudgetDetails.getAudited());
           // null
           dbZxBuBudgetDetails.setNeedDeduct(zxBuBudgetDetails.getNeedDeduct());
           // null
           dbZxBuBudgetDetails.setEditTime(zxBuBudgetDetails.getEditTime());
           // null
           dbZxBuBudgetDetails.setScBudgetDetailsID(zxBuBudgetDetails.getScBudgetDetailsID());
           // null
           dbZxBuBudgetDetails.setFirstJtFee(zxBuBudgetDetails.getFirstJtFee());
           // null
           dbZxBuBudgetDetails.setFirstDwFee(zxBuBudgetDetails.getFirstDwFee());
           // null
           dbZxBuBudgetDetails.setCurrJtFee(zxBuBudgetDetails.getCurrJtFee());
           // null
           dbZxBuBudgetDetails.setCurrDwFee(zxBuBudgetDetails.getCurrDwFee());
           // null
           dbZxBuBudgetDetails.setRemfirstJtFee(zxBuBudgetDetails.getRemfirstJtFee());
           // null
           dbZxBuBudgetDetails.setRemfirstDwFee(zxBuBudgetDetails.getRemfirstDwFee());
           // null
           dbZxBuBudgetDetails.setRemcurrJtFee(zxBuBudgetDetails.getRemcurrJtFee());
           // null
           dbZxBuBudgetDetails.setRemcurrDwFee(zxBuBudgetDetails.getRemcurrDwFee());
           // null
           dbZxBuBudgetDetails.setFinFirst(zxBuBudgetDetails.getFinFirst());
           // null
           dbZxBuBudgetDetails.setFinCurr(zxBuBudgetDetails.getFinCurr());
           // nulll
           dbZxBuBudgetDetails.setRemFirst(zxBuBudgetDetails.getRemFirst());
           // null
           dbZxBuBudgetDetails.setRemCurr(zxBuBudgetDetails.getRemCurr());
           // null
           dbZxBuBudgetDetails.setFinConPrice(zxBuBudgetDetails.getFinConPrice());
           // null
           dbZxBuBudgetDetails.setFinChgAmount(zxBuBudgetDetails.getFinChgAmount());
           // null
           dbZxBuBudgetDetails.setFinAwdPenalty(zxBuBudgetDetails.getFinAwdPenalty());
           // null
           dbZxBuBudgetDetails.setFinMtladjustment(zxBuBudgetDetails.getFinMtladjustment());
           // null
           dbZxBuBudgetDetails.setFinTaxPrice(zxBuBudgetDetails.getFinTaxPrice());
           // null
           dbZxBuBudgetDetails.setFinOther(zxBuBudgetDetails.getFinOther());
           // null
           dbZxBuBudgetDetails.setFintotal(zxBuBudgetDetails.getFintotal());
           // null
           dbZxBuBudgetDetails.setRemConPrice(zxBuBudgetDetails.getRemConPrice());
           // null
           dbZxBuBudgetDetails.setRemChgAmount(zxBuBudgetDetails.getRemChgAmount());
           // null
           dbZxBuBudgetDetails.setRemAwdPenalty(zxBuBudgetDetails.getRemAwdPenalty());
           // null
           dbZxBuBudgetDetails.setRemMtladjustment(zxBuBudgetDetails.getRemMtladjustment());
           // null
           dbZxBuBudgetDetails.setRemTaxPrice(zxBuBudgetDetails.getRemTaxPrice());
           // null
           dbZxBuBudgetDetails.setRemOther(zxBuBudgetDetails.getRemOther());
           // null
           dbZxBuBudgetDetails.setRemtotal(zxBuBudgetDetails.getRemtotal());
           // null
           dbZxBuBudgetDetails.setFirstJtSca(zxBuBudgetDetails.getFirstJtSca());
           // null
           dbZxBuBudgetDetails.setFirstDwSca(zxBuBudgetDetails.getFirstDwSca());
           // null
           dbZxBuBudgetDetails.setCurrJtSca(zxBuBudgetDetails.getCurrJtSca());
           // null
           dbZxBuBudgetDetails.setCurrDwSca(zxBuBudgetDetails.getCurrDwSca());
           // null
           dbZxBuBudgetDetails.setOrderNo(zxBuBudgetDetails.getOrderNo());
           // 顺序
           dbZxBuBudgetDetails.setSerialNumber(zxBuBudgetDetails.getSerialNumber());
           // 备注
           dbZxBuBudgetDetails.setRemarks(zxBuBudgetDetails.getRemarks());
           // 排序
           dbZxBuBudgetDetails.setSort(zxBuBudgetDetails.getSort());
           // 共通
           dbZxBuBudgetDetails.setModifyUserInfo(userKey, realName);
           flag = zxBuBudgetDetailsMapper.updateByPrimaryKey(dbZxBuBudgetDetails);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxBuBudgetDetails);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxBuBudgetDetails(List<ZxBuBudgetDetails> zxBuBudgetDetailsList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxBuBudgetDetailsList != null && zxBuBudgetDetailsList.size() > 0) {
           ZxBuBudgetDetails zxBuBudgetDetails = new ZxBuBudgetDetails();
           zxBuBudgetDetails.setModifyUserInfo(userKey, realName);
           flag = zxBuBudgetDetailsMapper.batchDeleteUpdateZxBuBudgetDetails(zxBuBudgetDetailsList, zxBuBudgetDetails);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxBuBudgetDetailsList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
