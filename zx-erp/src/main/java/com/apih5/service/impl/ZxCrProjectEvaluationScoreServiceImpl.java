package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.apih5.mybatis.dao.ZxCrProjCreditScoreMapper;
import com.apih5.mybatis.pojo.ZxCrProjCreditScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCrProjectEvaluationScoreMapper;
import com.apih5.mybatis.pojo.ZxCrProjectEvaluationScore;
import com.apih5.service.ZxCrProjectEvaluationScoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.CollectionUtils;

@Service("zxCrProjectEvaluationScoreService")
public class ZxCrProjectEvaluationScoreServiceImpl implements ZxCrProjectEvaluationScoreService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCrProjectEvaluationScoreMapper zxCrProjectEvaluationScoreMapper;

    @Autowired(required = true)
    private ZxCrProjCreditScoreMapper zxCrProjCreditScoreMapper;

    @Override
    public ResponseEntity getZxCrProjectEvaluationScoreListByCondition(ZxCrProjectEvaluationScore zxCrProjectEvaluationScore) {
        if (zxCrProjectEvaluationScore == null) {
            zxCrProjectEvaluationScore = new ZxCrProjectEvaluationScore();
        }
        // 分页查询
        PageHelper.startPage(zxCrProjectEvaluationScore.getPage(),zxCrProjectEvaluationScore.getLimit());
        
        // 获取数据
        List<ZxCrProjectEvaluationScore> zxCrProjectEvaluationScoreList = zxCrProjectEvaluationScoreMapper.selectByZxCrProjectEvaluationScoreList(zxCrProjectEvaluationScore);
 
        // 得到分页信息
        PageInfo<ZxCrProjectEvaluationScore> p = new PageInfo<>(zxCrProjectEvaluationScoreList);
        
        return repEntity.okList(zxCrProjectEvaluationScoreList, p.getTotal());
        
    }

    @Override
    public ResponseEntity getZxCrProjectEvaluationScoreDetail(ZxCrProjectEvaluationScore zxCrProjectEvaluationScore) {
        if (zxCrProjectEvaluationScore == null) {
            zxCrProjectEvaluationScore = new ZxCrProjectEvaluationScore();
        }
        // 获取数据
        ZxCrProjectEvaluationScore dbZxCrProjectEvaluationScore = zxCrProjectEvaluationScoreMapper.selectByPrimaryKey(zxCrProjectEvaluationScore.getZxCrProjectEvaluationScoreId());
        // 数据存在
        if (dbZxCrProjectEvaluationScore != null) {
            return repEntity.ok(dbZxCrProjectEvaluationScore);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCrProjectEvaluationScore(ZxCrProjectEvaluationScore zxCrProjectEvaluationScore) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCrProjectEvaluationScore.setZxCrProjectEvaluationScoreId(UuidUtil.generate());
        zxCrProjectEvaluationScore.setCreateUserInfo(userKey, realName);
        int flag = zxCrProjectEvaluationScoreMapper.insert(zxCrProjectEvaluationScore);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCrProjectEvaluationScore);
        }
    }

    @Override
    public ResponseEntity updateZxCrProjectEvaluationScore(ZxCrProjectEvaluationScore zxCrProjectEvaluationScore) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCrProjectEvaluationScore dbZxCrProjectEvaluationScore = zxCrProjectEvaluationScoreMapper.selectByPrimaryKey(zxCrProjectEvaluationScore.getZxCrProjectEvaluationScoreId());
        if (dbZxCrProjectEvaluationScore != null && StrUtil.isNotEmpty(dbZxCrProjectEvaluationScore.getZxCrProjectEvaluationScoreId())) {
           // mainID
           dbZxCrProjectEvaluationScore.setMainID(zxCrProjectEvaluationScore.getMainID());
           // 评价内容
           dbZxCrProjectEvaluationScore.setEvalContent(zxCrProjectEvaluationScore.getEvalContent());
           // 评价细目
           dbZxCrProjectEvaluationScore.setScoreItem(zxCrProjectEvaluationScore.getScoreItem());
           // 标准分值
           dbZxCrProjectEvaluationScore.setStandardScore(zxCrProjectEvaluationScore.getStandardScore());
           // 项目减分
           dbZxCrProjectEvaluationScore.setDeductScore(zxCrProjectEvaluationScore.getDeductScore());
           // 项目得分
           dbZxCrProjectEvaluationScore.setGetScore(zxCrProjectEvaluationScore.getGetScore());
           // editTime
           dbZxCrProjectEvaluationScore.setEditTime(zxCrProjectEvaluationScore.getEditTime());
           // comID
           dbZxCrProjectEvaluationScore.setComID(zxCrProjectEvaluationScore.getComID());
           // comName
           dbZxCrProjectEvaluationScore.setComName(zxCrProjectEvaluationScore.getComName());
           // comOrders
           dbZxCrProjectEvaluationScore.setComOrders(zxCrProjectEvaluationScore.getComOrders());
           // 打分考核id
           dbZxCrProjectEvaluationScore.setEvalID(zxCrProjectEvaluationScore.getEvalID());
           // 排序
           dbZxCrProjectEvaluationScore.setOrderStr(zxCrProjectEvaluationScore.getOrderStr());
           // 备注
           dbZxCrProjectEvaluationScore.setRemarks(zxCrProjectEvaluationScore.getRemarks());
           // 排序
           dbZxCrProjectEvaluationScore.setSort(zxCrProjectEvaluationScore.getSort());
           // 共通
           dbZxCrProjectEvaluationScore.setModifyUserInfo(userKey, realName);
           flag = zxCrProjectEvaluationScoreMapper.updateByPrimaryKey(dbZxCrProjectEvaluationScore);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCrProjectEvaluationScore);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCrProjectEvaluationScore(List<ZxCrProjectEvaluationScore> zxCrProjectEvaluationScoreList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCrProjectEvaluationScoreList != null && zxCrProjectEvaluationScoreList.size() > 0) {
           ZxCrProjectEvaluationScore zxCrProjectEvaluationScore = new ZxCrProjectEvaluationScore();
           zxCrProjectEvaluationScore.setModifyUserInfo(userKey, realName);
           flag = zxCrProjectEvaluationScoreMapper.batchDeleteUpdateZxCrProjectEvaluationScore(zxCrProjectEvaluationScoreList, zxCrProjectEvaluationScore);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCrProjectEvaluationScoreList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public ResponseEntity getZxCrProjectEvaluationScoreDetailOne(ZxCrProjCreditScore zxCrProjCreditScore) {
        if (zxCrProjCreditScore == null) {
            zxCrProjCreditScore = new ZxCrProjCreditScore();
        }
        zxCrProjCreditScore.setIsUse("1");
        // 获取数据
        List<ZxCrProjCreditScore> zxCrProjCreditScores = zxCrProjCreditScoreMapper.selectByZxCrProjCreditScoreList(zxCrProjCreditScore);
        zxCrProjCreditScores.forEach(s -> {
            s.setGetScore(s.getStandardScore());
        });
        // 数据存在
        if (!CollectionUtils.isEmpty(zxCrProjCreditScores)) {
            return repEntity.ok(zxCrProjCreditScores);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
}
