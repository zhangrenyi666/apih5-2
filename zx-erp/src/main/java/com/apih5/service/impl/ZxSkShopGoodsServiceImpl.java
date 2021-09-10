package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.util.ObjectUtil;
import com.apih5.framework.constant.SysConst;
import com.apih5.mybatis.dao.ZxSkResCategoryMaterialsMapper;
import com.apih5.mybatis.dao.ZxSkResourceMaterialsMapper;
import com.apih5.mybatis.dao.ZxSkShopGoodsItemMapper;
import com.apih5.mybatis.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkShopGoodsMapper;
import com.apih5.service.ZxSkShopGoodsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkShopGoodsService")
public class ZxSkShopGoodsServiceImpl implements ZxSkShopGoodsService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkShopGoodsMapper zxSkShopGoodsMapper;

    @Autowired(required = true)
    private ZxSkShopGoodsItemMapper zxSkShopGoodsItemMapper;

    @Autowired(required = true)
    private ZxSkResCategoryMaterialsMapper zxSkResCategoryMaterialsMapper;

    @Autowired(required = true)
    private ZxSkResourceMaterialsMapper zxSkResourceMaterialsMapper;

    @Override
    public ResponseEntity getZxSkShopGoodsListByCondition(ZxSkShopGoods zxSkShopGoods) {
        if (zxSkShopGoods == null) {
            zxSkShopGoods = new ZxSkShopGoods();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkShopGoods.setComID("");
            zxSkShopGoods.setOrgID("");
            if(StrUtil.isNotEmpty(zxSkShopGoods.getOrgID2())){
                zxSkShopGoods.setOrgID(zxSkShopGoods.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkShopGoods.setComID(zxSkShopGoods.getOrgID());
            zxSkShopGoods.setOrgID("");
            if(StrUtil.isNotEmpty(zxSkShopGoods.getOrgID2())){
                zxSkShopGoods.setOrgID(zxSkShopGoods.getOrgID2());
            }
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkShopGoods.setOrgID(zxSkShopGoods.getOrgID());
            if(StrUtil.isNotEmpty(zxSkShopGoods.getOrgID2())){
                zxSkShopGoods.setOrgID(zxSkShopGoods.getOrgID2());
            }
        }
        // 分页查询
        PageHelper.startPage(zxSkShopGoods.getPage(),zxSkShopGoods.getLimit());
        // 获取数据
        List<ZxSkShopGoods> zxSkShopGoodsList = zxSkShopGoodsMapper.selectByZxSkShopGoodsList(zxSkShopGoods);
        //查询明细
        for (ZxSkShopGoods zxSkShopGoods1 : zxSkShopGoodsList) {
            ZxSkShopGoodsItem zxSkShopGoodsItem = new ZxSkShopGoodsItem();
            zxSkShopGoodsItem.setMainID(zxSkShopGoods1.getId());
            List<ZxSkShopGoodsItem> zxSkShopGoodsItems = zxSkShopGoodsItemMapper.selectByZxSkShopGoodsItemList(zxSkShopGoodsItem);
            for (ZxSkShopGoodsItem skShopGoodsItem : zxSkShopGoodsItems) {
                String name = "";
                String[] id = skShopGoodsItem.getResID().split(",");
                if(id.length!=0){
                    for (String s : id) {
                        ZxSkResCategoryMaterials zxSkResCategoryMaterials = zxSkResCategoryMaterialsMapper.selectByPrimaryKey(s);

                        if(ObjectUtil.isEmpty(zxSkResCategoryMaterials)){
                            ZxSkResourceMaterials zxSkResourceMaterials = zxSkResourceMaterialsMapper.selectByPrimaryKey(s);
                            if(ObjectUtil.isNotEmpty(zxSkResourceMaterials)){
                                if (StrUtil.isNotEmpty(zxSkResourceMaterials.getResName())){
                                    name += "-"+ zxSkResourceMaterials.getResName();
                                }
                            }
                        }else {
                            if(StrUtil.isNotEmpty(zxSkResCategoryMaterials.getCatName())){
                                name += zxSkResCategoryMaterials.getCatName();
                            }
                        }
                    }
                }
                skShopGoodsItem.setJoinName(name);
            }
            zxSkShopGoods1.setZxSkShopGoodsItemList(zxSkShopGoodsItems);
        }
        // 得到分页信息
        PageInfo<ZxSkShopGoods> p = new PageInfo<>(zxSkShopGoodsList);

        return repEntity.okList(zxSkShopGoodsList, p.getTotal());

    }

    @Override
    public ResponseEntity getZxSkShopGoodsDetail(ZxSkShopGoods zxSkShopGoods) {
        if (zxSkShopGoods == null) {
            zxSkShopGoods = new ZxSkShopGoods();
        }
        // 获取数据
        ZxSkShopGoods dbZxSkShopGoods = zxSkShopGoodsMapper.selectByPrimaryKey(zxSkShopGoods.getId());
        // 数据存在
        if (dbZxSkShopGoods != null) {
            ZxSkShopGoodsItem zxSkShopGoodsItem = new ZxSkShopGoodsItem();
            zxSkShopGoodsItem.setMainID(dbZxSkShopGoods.getId());
            List<ZxSkShopGoodsItem> zxSkShopGoodsItems = zxSkShopGoodsItemMapper.selectByZxSkShopGoodsItemList(zxSkShopGoodsItem);
            dbZxSkShopGoods.setZxSkShopGoodsItemList(zxSkShopGoodsItems);
            return repEntity.ok(dbZxSkShopGoods);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkShopGoods(ZxSkShopGoods zxSkShopGoods) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkShopGoods.setId(UuidUtil.generate());
        zxSkShopGoods.setCreateUserInfo(userKey, realName);
        //默认审核状态: 0:未审核
        zxSkShopGoods.setStatus("0");

        //创建明细
        List<ZxSkShopGoodsItem> zxSkShopGoodsItemList = zxSkShopGoods.getZxSkShopGoodsItemList();
        if(zxSkShopGoodsItemList != null && zxSkShopGoodsItemList.size()>0) {
            for (ZxSkShopGoodsItem zxSkShopGoodsItem : zxSkShopGoodsItemList) {
                zxSkShopGoodsItem.setMainID(zxSkShopGoods.getId());
                zxSkShopGoodsItem.setId((UuidUtil.generate()));
                zxSkShopGoodsItem.setCreateUserInfo(userKey, realName);
                zxSkShopGoodsItemMapper.insert(zxSkShopGoodsItem);
            }
        }

        int flag = zxSkShopGoodsMapper.insert(zxSkShopGoods);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkShopGoods);
        }
    }

    @Override
    public ResponseEntity updateZxSkShopGoods(ZxSkShopGoods zxSkShopGoods) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkShopGoods dbZxSkShopGoods = zxSkShopGoodsMapper.selectByPrimaryKey(zxSkShopGoods.getId());
        if (dbZxSkShopGoods != null && StrUtil.isNotEmpty(dbZxSkShopGoods.getId())) {
           // 项目名称ID
           dbZxSkShopGoods.setOrgID(zxSkShopGoods.getOrgID());
           // 项目名称
           dbZxSkShopGoods.setOrgName(zxSkShopGoods.getOrgName());
           // 填写日期
           dbZxSkShopGoods.setReportDate(zxSkShopGoods.getReportDate());
           // 填报人
           dbZxSkShopGoods.setReporter(zxSkShopGoods.getReporter());
           // 状态
           dbZxSkShopGoods.setStatus(zxSkShopGoods.getStatus());
           // 审核员
           dbZxSkShopGoods.setAuditor(zxSkShopGoods.getAuditor());
           // 所属公司ID
           dbZxSkShopGoods.setComID(zxSkShopGoods.getComID());
           // 所属公司名称
           dbZxSkShopGoods.setComName(zxSkShopGoods.getComName());
           // 备注
           dbZxSkShopGoods.setRemarks(zxSkShopGoods.getRemarks());
           // 排序
           dbZxSkShopGoods.setSort(zxSkShopGoods.getSort());
           // 共通
           dbZxSkShopGoods.setModifyUserInfo(userKey, realName);
            //删除在新增
            ZxSkShopGoodsItem zxSkShopGoodsItem = new ZxSkShopGoodsItem();
            zxSkShopGoodsItem.setMainID(zxSkShopGoods.getId());
            List<ZxSkShopGoodsItem> zxSkShopGoodsItems = zxSkShopGoodsItemMapper.selectByZxSkShopGoodsItemList(zxSkShopGoodsItem);
            if(zxSkShopGoodsItems != null && zxSkShopGoodsItems.size()>0) {
                zxSkShopGoodsItem.setModifyUserInfo(userKey, realName);
                zxSkShopGoodsItemMapper.batchDeleteUpdateZxSkShopGoodsItem(zxSkShopGoodsItems, zxSkShopGoodsItem);
            }
            //明细list
            List<ZxSkShopGoodsItem> zxSkShopGoodsItemList = zxSkShopGoods.getZxSkShopGoodsItemList();
            if(zxSkShopGoodsItemList != null && zxSkShopGoodsItemList.size()>0) {
                for(ZxSkShopGoodsItem zxSkShopGoodsItem1 : zxSkShopGoodsItemList) {
                    zxSkShopGoodsItem1.setId(UuidUtil.generate());
                    zxSkShopGoodsItem1.setMainID(zxSkShopGoods.getId());
                    zxSkShopGoodsItem1.setCreateUserInfo(userKey, realName);
                    zxSkShopGoodsItemMapper.insert(zxSkShopGoodsItem1);
                }
            }
           flag = zxSkShopGoodsMapper.updateByPrimaryKey(dbZxSkShopGoods);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkShopGoods);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkShopGoods(List<ZxSkShopGoods> zxSkShopGoodsList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkShopGoodsList != null && zxSkShopGoodsList.size() > 0) {
            for (ZxSkShopGoods zxSkShopGoods : zxSkShopGoodsList) {
                // 删除明细
                ZxSkShopGoodsItem zxSkShopGoodsItem = new ZxSkShopGoodsItem();
                zxSkShopGoodsItem.setMainID(zxSkShopGoods.getId());
                List<ZxSkShopGoodsItem> zxSkShopGoodsItems = zxSkShopGoodsItemMapper.selectByZxSkShopGoodsItemList(zxSkShopGoodsItem);
                if(zxSkShopGoodsItems != null && zxSkShopGoodsItems.size()>0) {
                    zxSkShopGoodsItem.setModifyUserInfo(userKey, realName);
                    zxSkShopGoodsItemMapper.batchDeleteUpdateZxSkShopGoodsItem(zxSkShopGoodsItems, zxSkShopGoodsItem);
                }
            }
           ZxSkShopGoods zxSkShopGoods = new ZxSkShopGoods();
           zxSkShopGoods.setModifyUserInfo(userKey, realName);
           flag = zxSkShopGoodsMapper.batchDeleteUpdateZxSkShopGoods(zxSkShopGoodsList, zxSkShopGoods);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkShopGoodsList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓


}
