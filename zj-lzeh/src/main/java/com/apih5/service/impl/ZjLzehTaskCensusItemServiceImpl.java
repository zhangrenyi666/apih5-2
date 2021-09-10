package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.mybatis.dao.ZjLzehTaskCensusMapper;
import com.apih5.mybatis.pojo.ZjLzehTaskCensus;
import com.apih5.mybatis.pojo.ZjLzehTeamScoreItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjLzehTaskCensusItemMapper;
import com.apih5.mybatis.pojo.ZjLzehTaskCensusItem;
import com.apih5.service.ZjLzehTaskCensusItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjLzehTaskCensusItemService")
public class ZjLzehTaskCensusItemServiceImpl implements ZjLzehTaskCensusItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjLzehTaskCensusItemMapper zjLzehTaskCensusItemMapper;

    @Autowired(required = true)
    private ZjLzehTaskCensusMapper zjLzehTaskCensusMapper;

    @Override
    public ResponseEntity getZjLzehTaskCensusItemListByCondition(ZjLzehTaskCensusItem zjLzehTaskCensusItem) {
        if (zjLzehTaskCensusItem == null) {
            zjLzehTaskCensusItem = new ZjLzehTaskCensusItem();
        }
        // 分页查询
        PageHelper.startPage(zjLzehTaskCensusItem.getPage(),zjLzehTaskCensusItem.getLimit());
        // 获取数据
        List<ZjLzehTaskCensusItem> zjLzehTaskCensusItemList = zjLzehTaskCensusItemMapper.selectByZjLzehTaskCensusItemList(zjLzehTaskCensusItem);
        // 得到分页信息
        PageInfo<ZjLzehTaskCensusItem> p = new PageInfo<>(zjLzehTaskCensusItemList);

        return repEntity.okList(zjLzehTaskCensusItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjLzehTaskCensusItemDetail(ZjLzehTaskCensusItem zjLzehTaskCensusItem) {
        if (zjLzehTaskCensusItem == null) {
            zjLzehTaskCensusItem = new ZjLzehTaskCensusItem();
        }
        // 获取数据
        ZjLzehTaskCensusItem dbZjLzehTaskCensusItem = zjLzehTaskCensusItemMapper.selectByPrimaryKey(zjLzehTaskCensusItem.getZjLzehTaskCensusItemId());
        // 数据存在
        if (dbZjLzehTaskCensusItem != null) {
            return repEntity.ok(dbZjLzehTaskCensusItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZjLzehTaskCensusItem(ZjLzehTaskCensusItem zjLzehTaskCensusItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjLzehTaskCensusItem.setZjLzehTaskCensusItemId(UuidUtil.generate());
        zjLzehTaskCensusItem.setCreateUserInfo(userKey, realName);
        int flag = zjLzehTaskCensusItemMapper.insert(zjLzehTaskCensusItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zjLzehTaskCensusItem);
        }
    }

    @Override
    public ResponseEntity updateZjLzehTaskCensusItem(ZjLzehTaskCensusItem zjLzehTaskCensusItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjLzehTaskCensusItem dbZjLzehTaskCensusItem = zjLzehTaskCensusItemMapper.selectByPrimaryKey(zjLzehTaskCensusItem.getZjLzehTaskCensusItemId());
        if (dbZjLzehTaskCensusItem != null && StrUtil.isNotEmpty(dbZjLzehTaskCensusItem.getZjLzehTaskCensusItemId())) {
           // 任务统计ID
           dbZjLzehTaskCensusItem.setZjLzehTaskCensusId(zjLzehTaskCensusItem.getZjLzehTaskCensusId());
           // 人员姓名
           dbZjLzehTaskCensusItem.setPersonName(zjLzehTaskCensusItem.getPersonName());
           // 临时任务数
           dbZjLzehTaskCensusItem.setAllNum(zjLzehTaskCensusItem.getAllNum());
           // 临时完成任务数
           dbZjLzehTaskCensusItem.setcNum(zjLzehTaskCensusItem.getcNum());
           // 临时未完成任务数
           dbZjLzehTaskCensusItem.setNum(zjLzehTaskCensusItem.getNum());
           // 经营任务数
           dbZjLzehTaskCensusItem.setMallNum(zjLzehTaskCensusItem.getMallNum());
           // 经营完成任务数
           dbZjLzehTaskCensusItem.setMcNum(zjLzehTaskCensusItem.getMcNum());
           // 经营未完成任务
           dbZjLzehTaskCensusItem.setMnum(zjLzehTaskCensusItem.getMnum());
           // 生产任务数
           dbZjLzehTaskCensusItem.setPallNum(zjLzehTaskCensusItem.getPallNum());
           // 生产完成任务数
           dbZjLzehTaskCensusItem.setPcNum(zjLzehTaskCensusItem.getPcNum());
           // 生产未完成任务数
           dbZjLzehTaskCensusItem.setPnum(zjLzehTaskCensusItem.getPnum());
           // 所有任务数
           dbZjLzehTaskCensusItem.setAllqty(zjLzehTaskCensusItem.getAllqty());
           // 所有完成任务数
           dbZjLzehTaskCensusItem.setCqty(zjLzehTaskCensusItem.getCqty());
           // 所有未完成任务数
           dbZjLzehTaskCensusItem.setQty(zjLzehTaskCensusItem.getQty());
           // 任务完成比例
           dbZjLzehTaskCensusItem.setcRate(zjLzehTaskCensusItem.getcRate());
           // 序号/排名
           dbZjLzehTaskCensusItem.setXuhao(zjLzehTaskCensusItem.getXuhao());
           // 备注
           dbZjLzehTaskCensusItem.setRemarks(zjLzehTaskCensusItem.getRemarks());
           // 排序
           dbZjLzehTaskCensusItem.setSort(zjLzehTaskCensusItem.getSort());
           // 共通
           dbZjLzehTaskCensusItem.setModifyUserInfo(userKey, realName);
           flag = zjLzehTaskCensusItemMapper.updateByPrimaryKey(dbZjLzehTaskCensusItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zjLzehTaskCensusItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjLzehTaskCensusItem(List<ZjLzehTaskCensusItem> zjLzehTaskCensusItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjLzehTaskCensusItemList != null && zjLzehTaskCensusItemList.size() > 0) {
           ZjLzehTaskCensusItem zjLzehTaskCensusItem = new ZjLzehTaskCensusItem();
           zjLzehTaskCensusItem.setModifyUserInfo(userKey, realName);
           flag = zjLzehTaskCensusItemMapper.batchDeleteUpdateZjLzehTaskCensusItem(zjLzehTaskCensusItemList, zjLzehTaskCensusItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zjLzehTaskCensusItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public ResponseEntity getZjLzehTaskCensusItemChartList(ZjLzehTaskCensusItem zjLzehTaskCensusItem) {
        if (zjLzehTaskCensusItem == null) {
            zjLzehTaskCensusItem = new ZjLzehTaskCensusItem();
        }
        // 获取数据
        List<ZjLzehTaskCensusItem> zjLzehTaskCensusItemList = zjLzehTaskCensusItemMapper.selectChartInfoByID(zjLzehTaskCensusItem);

        return repEntity.ok(zjLzehTaskCensusItemList);
    }

    @Override
    public ResponseEntity getTaskCensusItemChartByCenMonth(ZjLzehTaskCensusItem zjLzehTaskCensusItem) {

        ZjLzehTaskCensusItem zjLzehTaskCensusItem1 =new ZjLzehTaskCensusItem();
        ZjLzehTaskCensus zjLzehTaskCensus =new ZjLzehTaskCensus();

        zjLzehTaskCensus.setCensusMonth(zjLzehTaskCensusItem.getCenMonth());
        List<ZjLzehTaskCensus> zjLzehTaskCensusList=   zjLzehTaskCensusMapper.selectByZjLzehTaskCensusList(zjLzehTaskCensus);
        if(zjLzehTaskCensusList!=null && zjLzehTaskCensusList.size()>0){
            zjLzehTaskCensusItem1.setZjLzehTaskCensusId(zjLzehTaskCensusList.get(0).getZjLzehTaskCensusId());

            batchDeleteUpdateZjLzehTaskCensusItem(zjLzehTaskCensusItemMapper.selectByZjLzehTaskCensusItemList(zjLzehTaskCensusItem1));

            List <ZjLzehTaskCensusItem> zjLzehTaskCensusItemList= zjLzehTaskCensusItemMapper.selectTaskCensusItemByMonth(zjLzehTaskCensusItem);
            for (ZjLzehTaskCensusItem zi:zjLzehTaskCensusItemList
            ) {
                zi.setZjLzehTaskCensusId(zjLzehTaskCensusList.get(0).getZjLzehTaskCensusId());
                saveZjLzehTaskCensusItem(zi);
            }
        }



        // 获取数据
        List<ZjLzehTaskCensusItem> ScoreList = zjLzehTaskCensusItemMapper.selectChartByCenMonth(zjLzehTaskCensusItem);
        List<ZjLzehTaskCensusItem> avgScoreList = zjLzehTaskCensusItemMapper.selectAvgCRateByCenMonth(zjLzehTaskCensusItem);
        ScoreList.stream().forEach( a->avgScoreList.stream().filter(b->b.getPersonName().equals(a.getPersonName())).forEach(b1->a.setAvgRate(b1.getAvgRate())));
        return repEntity.ok(ScoreList);
    }

}
