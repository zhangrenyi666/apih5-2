package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxCrProjCreditBadBehaMapper;
import com.apih5.mybatis.pojo.ZxCrProjCreditBadBeha;
import com.apih5.service.ZxCrProjCreditBadBehaService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCrProjCreditBadBehaService")
public class ZxCrProjCreditBadBehaServiceImpl implements ZxCrProjCreditBadBehaService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCrProjCreditBadBehaMapper zxCrProjCreditBadBehaMapper;

    @Override
    public ResponseEntity getZxCrProjCreditBadBehaListByCondition(ZxCrProjCreditBadBeha zxCrProjCreditBadBeha) {
        if (zxCrProjCreditBadBeha == null) {
            zxCrProjCreditBadBeha = new ZxCrProjCreditBadBeha();
        }
        // 分页查询
        PageHelper.startPage(zxCrProjCreditBadBeha.getPage(),zxCrProjCreditBadBeha.getLimit());
        // 获取数据
        List<ZxCrProjCreditBadBeha> zxCrProjCreditBadBehaList = zxCrProjCreditBadBehaMapper.selectByZxCrProjCreditBadBehaList(zxCrProjCreditBadBeha);
        // 得到分页信息
        PageInfo<ZxCrProjCreditBadBeha> p = new PageInfo<>(zxCrProjCreditBadBehaList);

        return repEntity.okList(zxCrProjCreditBadBehaList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCrProjCreditBadBehaDetail(ZxCrProjCreditBadBeha zxCrProjCreditBadBeha) {
        if (zxCrProjCreditBadBeha == null) {
            zxCrProjCreditBadBeha = new ZxCrProjCreditBadBeha();
        }
        // 获取数据
        ZxCrProjCreditBadBeha dbZxCrProjCreditBadBeha = zxCrProjCreditBadBehaMapper.selectByPrimaryKey(zxCrProjCreditBadBeha.getId());
        // 数据存在
        if (dbZxCrProjCreditBadBeha != null) {
            return repEntity.ok(dbZxCrProjCreditBadBeha);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCrProjCreditBadBeha(ZxCrProjCreditBadBeha zxCrProjCreditBadBeha) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCrProjCreditBadBeha.setId(UuidUtil.generate());
        zxCrProjCreditBadBeha.setCreateUserInfo(userKey, realName);
        int flag = zxCrProjCreditBadBehaMapper.insert(zxCrProjCreditBadBeha);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCrProjCreditBadBeha);
        }
    }

    @Override
    public ResponseEntity updateZxCrProjCreditBadBeha(ZxCrProjCreditBadBeha zxCrProjCreditBadBeha) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCrProjCreditBadBeha dbZxCrProjCreditBadBeha = zxCrProjCreditBadBehaMapper.selectByPrimaryKey(zxCrProjCreditBadBeha.getId());
        if (dbZxCrProjCreditBadBeha != null && StrUtil.isNotEmpty(dbZxCrProjCreditBadBeha.getId())) {
           // 评价内容
           dbZxCrProjCreditBadBeha.setEvalContent(zxCrProjCreditBadBeha.getEvalContent());
           // 评分明细
           dbZxCrProjCreditBadBeha.setScoreItem(zxCrProjCreditBadBeha.getScoreItem());
           // 标准分值
           dbZxCrProjCreditBadBeha.setStandardScore(zxCrProjCreditBadBeha.getStandardScore());
           // 是否启用
           dbZxCrProjCreditBadBeha.setIsUse(zxCrProjCreditBadBeha.getIsUse());
           // 所属公司ID
           dbZxCrProjCreditBadBeha.setComID(zxCrProjCreditBadBeha.getComID());
           // 所属公司名称
           dbZxCrProjCreditBadBeha.setComName(zxCrProjCreditBadBeha.getComName());
           // 所属公司排序
           dbZxCrProjCreditBadBeha.setComOrders(zxCrProjCreditBadBeha.getComOrders());
           // 排序
           dbZxCrProjCreditBadBeha.setOrderStr(zxCrProjCreditBadBeha.getOrderStr());
           // 备注
           dbZxCrProjCreditBadBeha.setRemarks(zxCrProjCreditBadBeha.getRemarks());
           // 共通
           dbZxCrProjCreditBadBeha.setModifyUserInfo(userKey, realName);
           flag = zxCrProjCreditBadBehaMapper.updateByPrimaryKey(dbZxCrProjCreditBadBeha);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCrProjCreditBadBeha);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCrProjCreditBadBeha(List<ZxCrProjCreditBadBeha> zxCrProjCreditBadBehaList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCrProjCreditBadBehaList != null && zxCrProjCreditBadBehaList.size() > 0) {
           ZxCrProjCreditBadBeha zxCrProjCreditBadBeha = new ZxCrProjCreditBadBeha();
           zxCrProjCreditBadBeha.setModifyUserInfo(userKey, realName);
           flag = zxCrProjCreditBadBehaMapper.batchDeleteUpdateZxCrProjCreditBadBeha(zxCrProjCreditBadBehaList, zxCrProjCreditBadBeha);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCrProjCreditBadBehaList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
