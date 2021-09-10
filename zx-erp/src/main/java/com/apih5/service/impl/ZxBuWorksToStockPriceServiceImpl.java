package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.mybatis.dao.ZxBuStockPriceMapper;
import com.apih5.mybatis.pojo.ZxBuStockPrice;
import com.apih5.mybatis.pojo.ZxBuWorks;
import com.apih5.mybatis.pojo.ZxBuYgjResTechnics;
import com.apih5.service.ZxBuWorksService;
import flex.messaging.util.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxBuWorksToStockPriceMapper;
import com.apih5.mybatis.pojo.ZxBuWorksToStockPrice;
import com.apih5.service.ZxBuWorksToStockPriceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxBuWorksToStockPriceService")
public class ZxBuWorksToStockPriceServiceImpl implements ZxBuWorksToStockPriceService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxBuWorksToStockPriceMapper zxBuWorksToStockPriceMapper;

    @Autowired(required = true)
    private ZxBuStockPriceMapper zxBuStockPriceMapper;

    @Autowired(required = true)
    private ZxBuWorksService zxBuWorksService;

    @Override
    public ResponseEntity getZxBuWorksToStockPriceListByCondition(ZxBuWorksToStockPrice zxBuWorksToStockPrice) {
        if (zxBuWorksToStockPrice == null) {
            zxBuWorksToStockPrice = new ZxBuWorksToStockPrice();
        }
        // 分页查询
        PageHelper.startPage(zxBuWorksToStockPrice.getPage(),zxBuWorksToStockPrice.getLimit());
        // 获取数据
        List<ZxBuWorksToStockPrice> zxBuWorksToStockPriceList = zxBuWorksToStockPriceMapper.selectByZxBuWorksToStockPriceList(zxBuWorksToStockPrice);
        // 得到分页信息
        PageInfo<ZxBuWorksToStockPrice> p = new PageInfo<>(zxBuWorksToStockPriceList);

        return repEntity.okList(zxBuWorksToStockPriceList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxBuWorksToStockPriceDetail(ZxBuWorksToStockPrice zxBuWorksToStockPrice) {
        if (zxBuWorksToStockPrice == null) {
            zxBuWorksToStockPrice = new ZxBuWorksToStockPrice();
        }
        // 获取数据
        ZxBuWorksToStockPrice dbZxBuWorksToStockPrice = zxBuWorksToStockPriceMapper.selectByPrimaryKey(zxBuWorksToStockPrice.getZxBuWorksToStockPriceId());
        // 数据存在
        if (dbZxBuWorksToStockPrice != null) {
            return repEntity.ok(dbZxBuWorksToStockPrice);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxBuWorksToStockPrice(ZxBuWorksToStockPrice zxBuWorksToStockPrice) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxBuWorksToStockPrice.setZxBuWorksToStockPriceId(UuidUtil.generate());
        zxBuWorksToStockPrice.setCreateUserInfo(userKey, realName);
        int flag = zxBuWorksToStockPriceMapper.insert(zxBuWorksToStockPrice);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxBuWorksToStockPrice);
        }
    }

    @Override
    public ResponseEntity updateZxBuWorksToStockPrice(ZxBuWorksToStockPrice zxBuWorksToStockPrice) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxBuWorksToStockPrice dbZxBuWorksToStockPrice = zxBuWorksToStockPriceMapper.selectByPrimaryKey(zxBuWorksToStockPrice.getZxBuWorksToStockPriceId());
        if (dbZxBuWorksToStockPrice != null && StrUtil.isNotEmpty(dbZxBuWorksToStockPrice.getZxBuWorksToStockPriceId())) {
           // 项目清单ID
           dbZxBuWorksToStockPrice.setProjWorkID(zxBuWorksToStockPrice.getProjWorkID());
           // VOID
           dbZxBuWorksToStockPrice.setStockVOID(zxBuWorksToStockPrice.getStockVOID());
           // 分类id
           dbZxBuWorksToStockPrice.setBusinessType(zxBuWorksToStockPrice.getBusinessType());
           // 耗用量
           dbZxBuWorksToStockPrice.setGjQty1(zxBuWorksToStockPrice.getGjQty1());
           // 不含税金额
           dbZxBuWorksToStockPrice.setGjAmt1(zxBuWorksToStockPrice.getGjAmt1());
           // 耗用量
           dbZxBuWorksToStockPrice.setGjQty2(zxBuWorksToStockPrice.getGjQty2());
           // 不含税金额
           dbZxBuWorksToStockPrice.setGjAmt2(zxBuWorksToStockPrice.getGjAmt2());
           // 损耗系数
           dbZxBuWorksToStockPrice.setGjLossCoefficient1(zxBuWorksToStockPrice.getGjLossCoefficient1());
           // 损耗系数
           dbZxBuWorksToStockPrice.setGjLossCoefficient2(zxBuWorksToStockPrice.getGjLossCoefficient2());
           // 折算系数
           dbZxBuWorksToStockPrice.setGjConCoefficient1(zxBuWorksToStockPrice.getGjConCoefficient1());
           // 折算系数
           dbZxBuWorksToStockPrice.setGjConCoefficient2(zxBuWorksToStockPrice.getGjConCoefficient2());
           // 期次
           dbZxBuWorksToStockPrice.setPeriod(zxBuWorksToStockPrice.getPeriod());
           // 材料编号
           dbZxBuWorksToStockPrice.setResCode(zxBuWorksToStockPrice.getResCode());
           // 材料名称
           dbZxBuWorksToStockPrice.setResName(zxBuWorksToStockPrice.getResName());
           // 材料单位
           dbZxBuWorksToStockPrice.setUnit(zxBuWorksToStockPrice.getUnit());
           // 规格型号
           dbZxBuWorksToStockPrice.setSpec(zxBuWorksToStockPrice.getSpec());
           // 单价
           dbZxBuWorksToStockPrice.setScenePrice1(zxBuWorksToStockPrice.getScenePrice1());
           // 单价
           dbZxBuWorksToStockPrice.setScenePrice2(zxBuWorksToStockPrice.getScenePrice2());
           // 机构id
           dbZxBuWorksToStockPrice.setOrgID(zxBuWorksToStockPrice.getOrgID());
           // 全局均价
           dbZxBuWorksToStockPrice.setComPrice(zxBuWorksToStockPrice.getComPrice());
           // 本公司均价
           dbZxBuWorksToStockPrice.setOwnerComPrice(zxBuWorksToStockPrice.getOwnerComPrice());
           // 备注
           dbZxBuWorksToStockPrice.setRemarks(zxBuWorksToStockPrice.getRemarks());
           // 排序
           dbZxBuWorksToStockPrice.setSort(zxBuWorksToStockPrice.getSort());
           // 共通
           dbZxBuWorksToStockPrice.setModifyUserInfo(userKey, realName);
           flag = zxBuWorksToStockPriceMapper.updateByPrimaryKey(dbZxBuWorksToStockPrice);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxBuWorksToStockPrice);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxBuWorksToStockPrice(List<ZxBuWorksToStockPrice> zxBuWorksToStockPriceList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxBuWorksToStockPriceList != null && zxBuWorksToStockPriceList.size() > 0) {
           ZxBuWorksToStockPrice zxBuWorksToStockPrice = new ZxBuWorksToStockPrice();
           zxBuWorksToStockPrice.setModifyUserInfo(userKey, realName);
           flag = zxBuWorksToStockPriceMapper.batchDeleteUpdateZxBuWorksToStockPrice(zxBuWorksToStockPriceList, zxBuWorksToStockPrice);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxBuWorksToStockPriceList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public ResponseEntity relevanceZxBuWorksToStockPrice(ZxBuWorksToStockPrice zxBuWorksToStockPrice) {
        if(zxBuWorksToStockPrice == null){
            return repEntity.ok(new ArrayList<>());
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        //getStockPriceID
        //根据id查一下
        ZxBuStockPrice zxBuStockPrice = new ZxBuStockPrice();
        zxBuStockPrice.setZxBuStockPriceId(zxBuWorksToStockPrice.getStockPriceID());
        List<ZxBuStockPrice> zxBuStockPrices = zxBuStockPriceMapper.selectByZxBuStockPriceList(zxBuStockPrice);
        if(zxBuStockPrices!=null && zxBuStockPrices.size()>0){
            zxBuWorksToStockPrice.setBusinessType(zxBuStockPrices.get(0).getBusinessType());
        }
        //拼接id
        //名字_编号_单位_规格型号_id		这里的id是stockPriceid
        String VOID = zxBuWorksToStockPrice.getResName()+"_"+
                        zxBuWorksToStockPrice.getResCode()+"_"+
                        zxBuWorksToStockPrice.getUnit()+"_"+
                        zxBuWorksToStockPrice.getSpec()+"_"+
                        zxBuWorksToStockPrice.getStockPriceID();
        zxBuWorksToStockPrice.setStockVOID(VOID);
        //id
        zxBuWorksToStockPrice.setZxBuWorksToStockPriceId(UUIDUtils.createUUID());
        zxBuWorksToStockPrice.setCreateUserInfo(userKey,realName);
        int flag = 0;
        flag = zxBuWorksToStockPriceMapper.insert(zxBuWorksToStockPrice);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            //获取表格数据.
            ZxBuWorks zxBuWorks = new ZxBuWorks();
            zxBuWorks.setOrgID(zxBuWorksToStockPrice.getOrgID());
            zxBuWorks.setParentID("-1");
            ResponseEntity zxBuWorksResNoName = zxBuWorksService.getZxBuWorksResNoName(zxBuWorks);
            if(zxBuWorksResNoName.isSuccess()){
                return repEntity.ok("sys.data.sava", zxBuWorksResNoName.getData());
            }else {
                return repEntity.errorSave();
            }
        }
    }

    @Override
    public ResponseEntity removeRelevanceZxBuWorksToStockPrice(ZxBuWorksToStockPrice zxBuWorksToStockPrice) {
        if(zxBuWorksToStockPrice == null){
            return repEntity.ok(new ArrayList<>());
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        //拼接id
        //名字_编号_单位_规格型号_id		这里的id是stockPriceid
        String VOID = zxBuWorksToStockPrice.getResName()+"_"+
                zxBuWorksToStockPrice.getResCode()+"_"+
                zxBuWorksToStockPrice.getUnit()+"_"+
                zxBuWorksToStockPrice.getSpec()+"_"+
                zxBuWorksToStockPrice.getStockPriceID();
        ZxBuWorksToStockPrice zxBuWorksToStockPrice1 = new ZxBuWorksToStockPrice();
        //项目清单id,拼接id,orgID
        zxBuWorksToStockPrice1.setStockVOID(VOID);
        zxBuWorksToStockPrice1.setProjWorkID(zxBuWorksToStockPrice.getProjWorkID());
        zxBuWorksToStockPrice1.setOrgID(zxBuWorksToStockPrice.getOrgID());
//        zxBuWorksToStockPrice1.setBusinessType(zxBuWorksToStockPrice.getBusinessType());
        List<ZxBuWorksToStockPrice> zxBuWorksToStockPrices = zxBuWorksToStockPriceMapper.selectByZxBuWorksToStockPriceList(zxBuWorksToStockPrice1);
        zxBuWorksToStockPrice1.setModifyUserInfo(userKey,realName);
        int flag = 0;
        flag = zxBuWorksToStockPriceMapper.batchDeleteUpdateZxBuWorksToStockPrice(zxBuWorksToStockPrices, zxBuWorksToStockPrice1);
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            //获取表格数据.
            ZxBuWorks zxBuWorks = new ZxBuWorks();
            zxBuWorks.setOrgID(zxBuWorksToStockPrice.getOrgID());
            zxBuWorks.setParentID("-1");
            ResponseEntity zxBuWorksResNoName = zxBuWorksService.getZxBuWorksResNoName(zxBuWorks);
            if (zxBuWorksResNoName.isSuccess()) {
                return repEntity.ok("sys.data.sava", zxBuWorksResNoName.getData());
            } else {
                return repEntity.errorSave();
            }
        }
    }



}
