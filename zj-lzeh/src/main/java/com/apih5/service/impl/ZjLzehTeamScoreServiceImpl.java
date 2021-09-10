package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.mybatis.dao.ZjLzehTeamManagementMapper;
import com.apih5.mybatis.dao.ZjLzehTeamScoreItemMapper;
import com.apih5.mybatis.pojo.ZjLzehTeamManagement;
import com.apih5.mybatis.pojo.ZjLzehTeamScoreItem;
import com.apih5.service.ZjLzehTeamScoreItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjLzehTeamScoreMapper;
import com.apih5.mybatis.pojo.ZjLzehTeamScore;
import com.apih5.service.ZjLzehTeamScoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.transaction.annotation.Transactional;

@Service("zjLzehTeamScoreService")
public class ZjLzehTeamScoreServiceImpl implements ZjLzehTeamScoreService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjLzehTeamScoreMapper zjLzehTeamScoreMapper;

    @Autowired(required = true)
    private ZjLzehTeamManagementMapper zjLzehTeamManagementMapper;

    @Autowired(required = true)
    private ZjLzehTeamScoreItemMapper zjLzehTeamScoreItemMapper;

    @Autowired(required = true)
    private ZjLzehTeamScoreItemService zjLzehTeamScoreItemService;

    @Override
    public ResponseEntity getZjLzehTeamScoreListByCondition(ZjLzehTeamScore zjLzehTeamScore) {
        if (zjLzehTeamScore == null) {
            zjLzehTeamScore = new ZjLzehTeamScore();
        }
        // 分页查询
        PageHelper.startPage(zjLzehTeamScore.getPage(),zjLzehTeamScore.getLimit());
        // 获取数据
        List<ZjLzehTeamScore> zjLzehTeamScoreList = zjLzehTeamScoreMapper.selectByZjLzehTeamScoreList(zjLzehTeamScore);
        // 得到分页信息
        PageInfo<ZjLzehTeamScore> p = new PageInfo<>(zjLzehTeamScoreList);

        return repEntity.okList(zjLzehTeamScoreList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjLzehTeamScoreDetail(ZjLzehTeamScore zjLzehTeamScore) {
        if (zjLzehTeamScore == null) {
            zjLzehTeamScore = new ZjLzehTeamScore();
        }
        // 获取数据
        ZjLzehTeamScore dbZjLzehTeamScore = zjLzehTeamScoreMapper.selectByPrimaryKey(zjLzehTeamScore.getZjLzehTeamScoreId());
        // 数据存在
        if (dbZjLzehTeamScore != null) {
            return repEntity.ok(dbZjLzehTeamScore);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity saveZjLzehTeamScore(ZjLzehTeamScore zjLzehTeamScore) {

        ZjLzehTeamScore zjLzehTeamScore1 =new ZjLzehTeamScore();
        zjLzehTeamScore1.setScoreMonth(zjLzehTeamScore.getScoreMonth());
        List<ZjLzehTeamScore> list= zjLzehTeamScoreMapper.selectByZjLzehTeamScoreList(zjLzehTeamScore1);
        if(list.size()>0){
            return repEntity.layerMessage("no","该月份的数据已存在");
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);

        zjLzehTeamScore.setZjLzehTeamScoreId(UuidUtil.generate());
        zjLzehTeamScore.setCreateUserInfo(userKey, realName);

        ZjLzehTeamManagement zjLzehTeamManagement =new ZjLzehTeamManagement();
        zjLzehTeamManagement.setIsScore("1");
        List <ZjLzehTeamManagement> zjLzehTeamManagements = zjLzehTeamManagementMapper.selectByZjLzehTeamManagementList(zjLzehTeamManagement);
        zjLzehTeamScore.setTeamNum(zjLzehTeamManagements.size());
        int flag = zjLzehTeamScoreMapper.insert(zjLzehTeamScore);

        if (flag !=0) {
            //查询参加评分的班组

            //创建班组评分明细对象并为主表ID赋值
            ZjLzehTeamScoreItem zjLzehTeamScoreItem =new ZjLzehTeamScoreItem();
            zjLzehTeamScoreItem.setZjLzehTeamScoreId(zjLzehTeamScore.getZjLzehTeamScoreId());
            //遍历参加评分的班组列列表并获取其数据ID
            for (ZjLzehTeamManagement zjLzehTeamManageme:zjLzehTeamManagements
                 ) {
                zjLzehTeamScoreItem.setTeamId(zjLzehTeamManageme.getZjLzehTeamManagementId());
                zjLzehTeamScoreItemService.saveZjLzehTeamScoreItem(zjLzehTeamScoreItem);
            }
            return repEntity.ok("sys.data.sava", zjLzehTeamScore);
        } else {
            return repEntity.errorSave();

        }
    }

    @Override
    public ResponseEntity updateZjLzehTeamScore(ZjLzehTeamScore zjLzehTeamScore) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjLzehTeamScore dbZjLzehTeamScore = zjLzehTeamScoreMapper.selectByPrimaryKey(zjLzehTeamScore.getZjLzehTeamScoreId());
        if (dbZjLzehTeamScore != null && StrUtil.isNotEmpty(dbZjLzehTeamScore.getZjLzehTeamScoreId())) {
           // 评分月份
           dbZjLzehTeamScore.setScoreMonth(zjLzehTeamScore.getScoreMonth());
           // 班组数量
           dbZjLzehTeamScore.setTeamNum(zjLzehTeamScore.getTeamNum());
           // 备注
           dbZjLzehTeamScore.setRemarks(zjLzehTeamScore.getRemarks());
           // 排序
           dbZjLzehTeamScore.setSort(zjLzehTeamScore.getSort());
           // 共通
           dbZjLzehTeamScore.setModifyUserInfo(userKey, realName);
           flag = zjLzehTeamScoreMapper.updateByPrimaryKey(dbZjLzehTeamScore);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zjLzehTeamScore);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity batchDeleteUpdateZjLzehTeamScore(List<ZjLzehTeamScore> zjLzehTeamScoreList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        for (ZjLzehTeamScore lzehTeamScore:zjLzehTeamScoreList
             ) {
            ZjLzehTeamScoreItem zjLzehTeamScoreItem =new ZjLzehTeamScoreItem();
            zjLzehTeamScoreItem.setZjLzehTeamScoreId(lzehTeamScore.getZjLzehTeamScoreId());
            List<ZjLzehTeamScoreItem> zjLzehTeamScoreItems = zjLzehTeamScoreItemMapper.selectByZjLzehTeamScoreItemList(zjLzehTeamScoreItem);
            zjLzehTeamScoreItemService.batchDeleteUpdateZjLzehTeamScoreItem(zjLzehTeamScoreItems);

        }
        int flag = 0;
        if (zjLzehTeamScoreList != null && zjLzehTeamScoreList.size() > 0) {
           ZjLzehTeamScore zjLzehTeamScore = new ZjLzehTeamScore();
           zjLzehTeamScore.setModifyUserInfo(userKey, realName);
           flag = zjLzehTeamScoreMapper.batchDeleteUpdateZjLzehTeamScore(zjLzehTeamScoreList, zjLzehTeamScore);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zjLzehTeamScoreList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
