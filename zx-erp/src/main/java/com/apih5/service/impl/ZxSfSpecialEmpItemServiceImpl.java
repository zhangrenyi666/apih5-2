package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.framework.constant.SysConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSfSpecialEmpItemMapper;
import com.apih5.mybatis.pojo.ZxSfSpecialEmpItem;
import com.apih5.service.ZxSfSpecialEmpItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSfSpecialEmpItemService")
public class ZxSfSpecialEmpItemServiceImpl implements ZxSfSpecialEmpItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfSpecialEmpItemMapper zxSfSpecialEmpItemMapper;

    @Override
    public ResponseEntity getZxSfSpecialEmpItemListByCondition(ZxSfSpecialEmpItem zxSfSpecialEmpItem) {
        if (zxSfSpecialEmpItem == null) {
            zxSfSpecialEmpItem = new ZxSfSpecialEmpItem();
        }

        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSfSpecialEmpItem.setCompanyId("");
            zxSfSpecialEmpItem.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSfSpecialEmpItem.setCompanyId(zxSfSpecialEmpItem.getOrgID());
            zxSfSpecialEmpItem.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSfSpecialEmpItem.setOrgID(zxSfSpecialEmpItem.getOrgID());
        }
        zxSfSpecialEmpItem.setSeID(zxSfSpecialEmpItem.getZxSfSpecialEmpId());
        // 分页查询
        PageHelper.startPage(zxSfSpecialEmpItem.getPage(), zxSfSpecialEmpItem.getLimit());
        // 获取数据
        List<ZxSfSpecialEmpItem> zxSfSpecialEmpItemList = zxSfSpecialEmpItemMapper.selectByZxSfSpecialEmpItemList(zxSfSpecialEmpItem);
        // 得到分页信息
        PageInfo<ZxSfSpecialEmpItem> p = new PageInfo<>(zxSfSpecialEmpItemList);

        return repEntity.okList(zxSfSpecialEmpItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfSpecialEmpItemDetail(ZxSfSpecialEmpItem zxSfSpecialEmpItem) {
        if (zxSfSpecialEmpItem == null) {
            zxSfSpecialEmpItem = new ZxSfSpecialEmpItem();
        }
        // 获取数据
        ZxSfSpecialEmpItem dbZxSfSpecialEmpItem = zxSfSpecialEmpItemMapper.selectByPrimaryKey(zxSfSpecialEmpItem.getZxSfSpecialEmpItemId());
        // 数据存在
        if (dbZxSfSpecialEmpItem != null) {
            return repEntity.ok(dbZxSfSpecialEmpItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfSpecialEmpItem(ZxSfSpecialEmpItem zxSfSpecialEmpItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfSpecialEmpItem.setZxSfSpecialEmpItemId(UuidUtil.generate());
        zxSfSpecialEmpItem.setCreateUserInfo(userKey, realName);
        zxSfSpecialEmpItem.setSeID(zxSfSpecialEmpItem.getZxSfSpecialEmpId());
        int flag = zxSfSpecialEmpItemMapper.insert(zxSfSpecialEmpItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfSpecialEmpItem);
        }
    }

    @Override
    public ResponseEntity updateZxSfSpecialEmpItem(ZxSfSpecialEmpItem zxSfSpecialEmpItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfSpecialEmpItem dbZxSfSpecialEmpItem = zxSfSpecialEmpItemMapper.selectByPrimaryKey(zxSfSpecialEmpItem.getZxSfSpecialEmpItemId());
        if (dbZxSfSpecialEmpItem != null && StrUtil.isNotEmpty(dbZxSfSpecialEmpItem.getZxSfSpecialEmpItemId())) {
            // 主表ID
            dbZxSfSpecialEmpItem.setSeID(zxSfSpecialEmpItem.getSeID());
            // 姓名
            dbZxSfSpecialEmpItem.setName(zxSfSpecialEmpItem.getName());
            // 性别
            dbZxSfSpecialEmpItem.setSex(zxSfSpecialEmpItem.getSex());
            // 准操作项目
            dbZxSfSpecialEmpItem.setOpProjName(zxSfSpecialEmpItem.getOpProjName());
            // 发证机关
            dbZxSfSpecialEmpItem.setSendName(zxSfSpecialEmpItem.getSendName());
            // 发证日期
            dbZxSfSpecialEmpItem.setSendDate(zxSfSpecialEmpItem.getSendDate());
            // 证件号码
            dbZxSfSpecialEmpItem.setCardNo(zxSfSpecialEmpItem.getCardNo());
            // 复审日期
            dbZxSfSpecialEmpItem.setCheckDate(zxSfSpecialEmpItem.getCheckDate());
            // 项目ID
            dbZxSfSpecialEmpItem.setOrgID(zxSfSpecialEmpItem.getOrgID());
            // 项目名称
            dbZxSfSpecialEmpItem.setOrgName(zxSfSpecialEmpItem.getOrgName());
            // 编辑时间
            dbZxSfSpecialEmpItem.setEditTime(zxSfSpecialEmpItem.getEditTime());
            // 退场时间
            dbZxSfSpecialEmpItem.setOutDate(zxSfSpecialEmpItem.getOutDate());
            // 备注
            dbZxSfSpecialEmpItem.setRemarks(zxSfSpecialEmpItem.getRemarks());
            // 排序
            dbZxSfSpecialEmpItem.setSort(zxSfSpecialEmpItem.getSort());
            // 共通
            dbZxSfSpecialEmpItem.setModifyUserInfo(userKey, realName);
            flag = zxSfSpecialEmpItemMapper.updateByPrimaryKey(dbZxSfSpecialEmpItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSfSpecialEmpItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfSpecialEmpItem(List<ZxSfSpecialEmpItem> zxSfSpecialEmpItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfSpecialEmpItemList != null && zxSfSpecialEmpItemList.size() > 0) {
            ZxSfSpecialEmpItem zxSfSpecialEmpItem = new ZxSfSpecialEmpItem();
            zxSfSpecialEmpItem.setModifyUserInfo(userKey, realName);
            flag = zxSfSpecialEmpItemMapper.batchDeleteUpdateZxSfSpecialEmpItem(zxSfSpecialEmpItemList, zxSfSpecialEmpItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSfSpecialEmpItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public ResponseEntity getFormList(ZxSfSpecialEmpItem zxSfSpecialEmpItem) {
        if (zxSfSpecialEmpItem == null) {
            zxSfSpecialEmpItem = new ZxSfSpecialEmpItem();
        }
        // 分页查询
        PageHelper.startPage(zxSfSpecialEmpItem.getPage(), zxSfSpecialEmpItem.getLimit());
        // 获取数据
        List<ZxSfSpecialEmpItem> zxSfSpecialEmpItemList = zxSfSpecialEmpItemMapper.UreportForm(zxSfSpecialEmpItem);
        // 得到分页信息
        PageInfo<ZxSfSpecialEmpItem> p = new PageInfo<>(zxSfSpecialEmpItemList);

        return repEntity.okList(zxSfSpecialEmpItemList, p.getTotal());
    }

    @Override
    public List<ZxSfSpecialEmpItem> UreportForm(ZxSfSpecialEmpItem zxSfSpecialEmpItem) {
        if (zxSfSpecialEmpItem == null) {
            zxSfSpecialEmpItem = new ZxSfSpecialEmpItem();
        }
        // 分页查询
        PageHelper.startPage(zxSfSpecialEmpItem.getPage(), zxSfSpecialEmpItem.getLimit());
        // 获取数据
        List<ZxSfSpecialEmpItem> zxSfSpecialEmpItemList = zxSfSpecialEmpItemMapper.UreportForm(zxSfSpecialEmpItem);
        // 得到分页信息
        //PageInfo<ZxSfSpecialEmpItem> p = new PageInfo<>(zxSfSpecialEmpItemList);

        return zxSfSpecialEmpItemList;
    }

    @Override
    public ResponseEntity getFormListCom(ZxSfSpecialEmpItem zxSfSpecialEmpItem) {
        if (zxSfSpecialEmpItem == null) {
            zxSfSpecialEmpItem = new ZxSfSpecialEmpItem();
        }
        // 分页查询
        PageHelper.startPage(zxSfSpecialEmpItem.getPage(), zxSfSpecialEmpItem.getLimit());
        // 获取数据
        List<ZxSfSpecialEmpItem> zxSfSpecialEmpItemList = zxSfSpecialEmpItemMapper.UreportFormCom(zxSfSpecialEmpItem);
        // 得到分页信息
        PageInfo<ZxSfSpecialEmpItem> p = new PageInfo<>(zxSfSpecialEmpItemList);

        return repEntity.okList(zxSfSpecialEmpItemList, p.getTotal());
    }

    @Override
    public List<ZxSfSpecialEmpItem> UreportFormCom(ZxSfSpecialEmpItem zxSfSpecialEmpItem) {
        // 分页查询
        // PageHelper.startPage(zxSfSpecialEmpItem.getPage(),zxSfSpecialEmpItem.getLimit());
        // 获取数据
        List<ZxSfSpecialEmpItem> zxSfSpecialEmpItemList = zxSfSpecialEmpItemMapper.UreportFormCom(zxSfSpecialEmpItem);
        // 得到分页信息
        //PageInfo<ZxSfSpecialEmpItem> p = new PageInfo<>(zxSfSpecialEmpItemList);

        return zxSfSpecialEmpItemList;
    }
}
