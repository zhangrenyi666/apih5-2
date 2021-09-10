package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.mybatis.dao.ZjLzehTaskCensusItemMapper;
import com.apih5.mybatis.pojo.ZjLzehTaskCensusItem;
import com.apih5.service.ZjLzehTaskCensusItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjLzehTaskCensusMapper;
import com.apih5.mybatis.pojo.ZjLzehTaskCensus;
import com.apih5.service.ZjLzehTaskCensusService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

@Service("zjLzehTaskCensusService")
public class ZjLzehTaskCensusServiceImpl implements ZjLzehTaskCensusService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjLzehTaskCensusMapper zjLzehTaskCensusMapper;

    @Autowired(required = true)
    private ZjLzehTaskCensusItemMapper zjLzehTaskCensusItemMapper;

    @Autowired(required = true)
    ZjLzehTaskCensusItemService zjLzehTaskCensusItemService;

    @Override
    public ResponseEntity getZjLzehTaskCensusListByCondition(ZjLzehTaskCensus zjLzehTaskCensus) {
        if (zjLzehTaskCensus == null) {
            zjLzehTaskCensus = new ZjLzehTaskCensus();
        }
        // 分页查询
        PageHelper.startPage(zjLzehTaskCensus.getPage(),zjLzehTaskCensus.getLimit());
        // 获取数据
        List<ZjLzehTaskCensus> zjLzehTaskCensusList = zjLzehTaskCensusMapper.selectByZjLzehTaskCensusList(zjLzehTaskCensus);
        for (ZjLzehTaskCensus zj:zjLzehTaskCensusList
             ) {
            zj.setPersonQty(synZjLzehTaskCensusItemByCensusId(zj));
        }

        // 得到分页信息
        PageInfo<ZjLzehTaskCensus> p = new PageInfo<>(zjLzehTaskCensusList);

        return repEntity.okList(zjLzehTaskCensusList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjLzehTaskCensusDetail(ZjLzehTaskCensus zjLzehTaskCensus) {
        if (zjLzehTaskCensus == null) {
            zjLzehTaskCensus = new ZjLzehTaskCensus();
        }
        // 获取数据
        ZjLzehTaskCensus dbZjLzehTaskCensus = zjLzehTaskCensusMapper.selectByPrimaryKey(zjLzehTaskCensus.getZjLzehTaskCensusId());
        // 数据存在
        if (dbZjLzehTaskCensus != null) {
            return repEntity.ok(dbZjLzehTaskCensus);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity saveZjLzehTaskCensus(ZjLzehTaskCensus zjLzehTaskCensus) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjLzehTaskCensus.setZjLzehTaskCensusId(UuidUtil.generate());
        zjLzehTaskCensus.setCreateUserInfo(userKey, realName);
        ZjLzehTaskCensus zjLzehTaskCensus1 =new ZjLzehTaskCensus();
        zjLzehTaskCensus1.setCensusMonth( zjLzehTaskCensus.getCensusMonth());
        List<ZjLzehTaskCensus> dbZjLzehTaskCensuss=zjLzehTaskCensusMapper.selectByZjLzehTaskCensusList(zjLzehTaskCensus1);
        if(dbZjLzehTaskCensuss.size()>0){
            return repEntity.layerMessage("no","月份已存在");
        }
        synZjLzehTaskCensusItem(zjLzehTaskCensus);
        ZjLzehTaskCensusItem zjLzehTaskCensusItem =new ZjLzehTaskCensusItem();
        zjLzehTaskCensusItem.setZjLzehTaskCensusId(zjLzehTaskCensus.getZjLzehTaskCensusId());
        List<ZjLzehTaskCensusItem>  lzehTaskCensusItemList = zjLzehTaskCensusItemMapper.selectByZjLzehTaskCensusItemList(zjLzehTaskCensusItem);
        zjLzehTaskCensus.setPersonQty(lzehTaskCensusItemList.size());
        int flag = zjLzehTaskCensusMapper.insert(zjLzehTaskCensus);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zjLzehTaskCensus);
        }
    }

    @Override
    public ResponseEntity updateZjLzehTaskCensus(ZjLzehTaskCensus zjLzehTaskCensus) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjLzehTaskCensus dbZjLzehTaskCensus = zjLzehTaskCensusMapper.selectByPrimaryKey(zjLzehTaskCensus.getZjLzehTaskCensusId());
        if (dbZjLzehTaskCensus != null && StrUtil.isNotEmpty(dbZjLzehTaskCensus.getZjLzehTaskCensusId())) {
           // 统计月份
           dbZjLzehTaskCensus.setCensusMonth(zjLzehTaskCensus.getCensusMonth());
           // 人数
           dbZjLzehTaskCensus.setPersonQty(zjLzehTaskCensus.getPersonQty());
           // 备注
           dbZjLzehTaskCensus.setRemarks(zjLzehTaskCensus.getRemarks());
           // 排序
           dbZjLzehTaskCensus.setSort(zjLzehTaskCensus.getSort());
           // 共通
           dbZjLzehTaskCensus.setModifyUserInfo(userKey, realName);
           flag = zjLzehTaskCensusMapper.updateByPrimaryKey(dbZjLzehTaskCensus);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zjLzehTaskCensus);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity batchDeleteUpdateZjLzehTaskCensus(List<ZjLzehTaskCensus> zjLzehTaskCensusList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjLzehTaskCensusList != null && zjLzehTaskCensusList.size() > 0) {
            for (ZjLzehTaskCensus zc: zjLzehTaskCensusList){
                ZjLzehTaskCensusItem zjLzehTaskCensusItem=new ZjLzehTaskCensusItem();
                zjLzehTaskCensusItem.setZjLzehTaskCensusId(zc.getZjLzehTaskCensusId());
               List<ZjLzehTaskCensusItem> lzehTaskCensusItemList = zjLzehTaskCensusItemMapper.selectByZjLzehTaskCensusItemList(zjLzehTaskCensusItem);
                zjLzehTaskCensusItemService.batchDeleteUpdateZjLzehTaskCensusItem(lzehTaskCensusItemList);
            }

           ZjLzehTaskCensus zjLzehTaskCensus = new ZjLzehTaskCensus();
           zjLzehTaskCensus.setModifyUserInfo(userKey, realName);
           flag = zjLzehTaskCensusMapper.batchDeleteUpdateZjLzehTaskCensus(zjLzehTaskCensusList, zjLzehTaskCensus);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zjLzehTaskCensusList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Transactional(rollbackFor = Exception.class)
    public void synZjLzehTaskCensusItem (ZjLzehTaskCensus zjLzehTaskCensus){
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZjLzehTaskCensusItem zjLzehTaskCensusItem =new ZjLzehTaskCensusItem();
        zjLzehTaskCensusItem.setCenMonth(zjLzehTaskCensus.getCensusMonth());
       //List<ZjLzehTaskCensusItem> dbTaskCensusItemList= zjLzehTaskCensusItemMapper.selectByZjLzehTaskCensusItemList(zjLzehTaskCensusItem);
       List <ZjLzehTaskCensusItem> zjLzehTaskCensusItemList= zjLzehTaskCensusItemMapper.selectTaskCensusItemByMonth(zjLzehTaskCensusItem);
        //zjLzehTaskCensusItemService.batchDeleteUpdateZjLzehTaskCensusItem(dbTaskCensusItemList);
        for (ZjLzehTaskCensusItem zjci: zjLzehTaskCensusItemList
             ) {
            zjci.setZjLzehTaskCensusItemId(UuidUtil.generate());
            zjci.setZjLzehTaskCensusId(zjLzehTaskCensus.getZjLzehTaskCensusId());
            zjci.setCreateUserInfo(userKey, realName);
            zjLzehTaskCensusItemMapper.insert(zjci);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public int synZjLzehTaskCensusItemByCensusId (ZjLzehTaskCensus zjLzehTaskCensus){
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        ZjLzehTaskCensusItem zjLzehTaskCensusItem =new ZjLzehTaskCensusItem();
        zjLzehTaskCensusItem.setCenMonth(zjLzehTaskCensus.getCensusMonth());
        zjLzehTaskCensusItem.setZjLzehTaskCensusId(zjLzehTaskCensus.getZjLzehTaskCensusId());
        List<ZjLzehTaskCensusItem> dbTaskCensusItemList= zjLzehTaskCensusItemMapper.selectByZjLzehTaskCensusItemList(zjLzehTaskCensusItem);
        List <ZjLzehTaskCensusItem> zjLzehTaskCensusItemList= zjLzehTaskCensusItemMapper.selectTaskCensusItemByMonth(zjLzehTaskCensusItem);
        zjLzehTaskCensusItemService.batchDeleteUpdateZjLzehTaskCensusItem(dbTaskCensusItemList);
        for (ZjLzehTaskCensusItem zjci: zjLzehTaskCensusItemList
        ) {
            zjci.setZjLzehTaskCensusItemId(UuidUtil.generate());
            zjci.setZjLzehTaskCensusId(zjLzehTaskCensus.getZjLzehTaskCensusId());
            zjci.setCreateUserInfo(userKey, realName);
            zjLzehTaskCensusItemMapper.insert(zjci);
        }
        zjLzehTaskCensus.setPersonQty(zjLzehTaskCensusItemList.size());
        zjLzehTaskCensusMapper.updateByPrimaryKey(zjLzehTaskCensus);
       return zjLzehTaskCensusItemList.size();
    }
}
