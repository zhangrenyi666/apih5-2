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
import com.apih5.mybatis.dao.ZxSkResWasAdmListMapper;
import com.apih5.mybatis.pojo.ZxSkResWasAdmList;
import com.apih5.service.ZxSkResWasAdmListService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkResWasAdmListService")
public class ZxSkResWasAdmListServiceImpl implements ZxSkResWasAdmListService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkResWasAdmListMapper zxSkResWasAdmListMapper;

    @Override
    public ResponseEntity getZxSkResWasAdmListListByCondition(ZxSkResWasAdmList zxSkResWasAdmList) {
        if (zxSkResWasAdmList == null) {
            zxSkResWasAdmList = new ZxSkResWasAdmList();
        }
        // 分页查询
        PageHelper.startPage(zxSkResWasAdmList.getPage(),zxSkResWasAdmList.getLimit());
        // 获取数据
        List<ZxSkResWasAdmList> zxSkResWasAdmListList = zxSkResWasAdmListMapper.selectByZxSkResWasAdmListList(zxSkResWasAdmList);
        // 得到分页信息
        PageInfo<ZxSkResWasAdmList> p = new PageInfo<>(zxSkResWasAdmListList);

        return repEntity.okList(zxSkResWasAdmListList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkResWasAdmListDetail(ZxSkResWasAdmList zxSkResWasAdmList) {
        if (zxSkResWasAdmList == null) {
            zxSkResWasAdmList = new ZxSkResWasAdmList();
        }
        // 获取数据
        ZxSkResWasAdmList dbZxSkResWasAdmList = zxSkResWasAdmListMapper.selectByPrimaryKey(zxSkResWasAdmList.getZxSkResWasAdmListId());
        // 数据存在
        if (dbZxSkResWasAdmList != null) {
            return repEntity.ok(dbZxSkResWasAdmList);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkResWasAdmList(ZxSkResWasAdmList zxSkResWasAdmList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkResWasAdmList.setZxSkResWasAdmListId(UuidUtil.generate());
        zxSkResWasAdmList.setCreateUserInfo(userKey, realName);
        int flag = zxSkResWasAdmListMapper.insert(zxSkResWasAdmList);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkResWasAdmList);
        }
    }

    @Override
    public ResponseEntity updateZxSkResWasAdmList(ZxSkResWasAdmList zxSkResWasAdmList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkResWasAdmList dbZxSkResWasAdmList = zxSkResWasAdmListMapper.selectByPrimaryKey(zxSkResWasAdmList.getZxSkResWasAdmListId());
        if (dbZxSkResWasAdmList != null && StrUtil.isNotEmpty(dbZxSkResWasAdmList.getZxSkResWasAdmListId())) {
           // 分部分项名称
           dbZxSkResWasAdmList.setCbsName(zxSkResWasAdmList.getCbsName());
           // 物资编码
           dbZxSkResWasAdmList.setResCode(zxSkResWasAdmList.getResCode());
           // 物资名称
           dbZxSkResWasAdmList.setResName(zxSkResWasAdmList.getResName());
           // 物资规格
           dbZxSkResWasAdmList.setSpec(zxSkResWasAdmList.getSpec());
           // 物资单位
           dbZxSkResWasAdmList.setResUnit(zxSkResWasAdmList.getResUnit());
           // 单价
           dbZxSkResWasAdmList.setPrice(zxSkResWasAdmList.getPrice());
           // 数量
           dbZxSkResWasAdmList.setOutQty(zxSkResWasAdmList.getOutQty());
           // 金额
           dbZxSkResWasAdmList.setOutAmt(zxSkResWasAdmList.getOutAmt());
           // 项目id
           dbZxSkResWasAdmList.setOrgID(zxSkResWasAdmList.getOrgID());
           // 材料分类
           dbZxSkResWasAdmList.setResTypeID(zxSkResWasAdmList.getResTypeID());
           // 期次
           dbZxSkResWasAdmList.setPeriod(zxSkResWasAdmList.getPeriod());
           // 开始时间
           dbZxSkResWasAdmList.setBeginDate(zxSkResWasAdmList.getBeginDate());
           // 结束时间
           dbZxSkResWasAdmList.setEndDate(zxSkResWasAdmList.getEndDate());
           // 备注
           dbZxSkResWasAdmList.setRemarks(zxSkResWasAdmList.getRemarks());
           // 排序
           dbZxSkResWasAdmList.setSort(zxSkResWasAdmList.getSort());
           // 共通
           dbZxSkResWasAdmList.setModifyUserInfo(userKey, realName);
           flag = zxSkResWasAdmListMapper.updateByPrimaryKey(dbZxSkResWasAdmList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkResWasAdmList);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkResWasAdmList(List<ZxSkResWasAdmList> zxSkResWasAdmListList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkResWasAdmListList != null && zxSkResWasAdmListList.size() > 0) {
           ZxSkResWasAdmList zxSkResWasAdmList = new ZxSkResWasAdmList();
           zxSkResWasAdmList.setModifyUserInfo(userKey, realName);
           flag = zxSkResWasAdmListMapper.batchDeleteUpdateZxSkResWasAdmList(zxSkResWasAdmListList, zxSkResWasAdmList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkResWasAdmListList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
