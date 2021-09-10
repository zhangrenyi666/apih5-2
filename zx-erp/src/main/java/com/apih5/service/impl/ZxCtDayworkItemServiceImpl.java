package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtDayworkItemMapper;
import com.apih5.mybatis.pojo.ZxCtDayworkItem;
import com.apih5.service.ZxCtDayworkItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxCtDayworkItemService")
public class ZxCtDayworkItemServiceImpl implements ZxCtDayworkItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtDayworkItemMapper zxCtDayworkItemMapper;

    @Override
    public ResponseEntity getZxCtDayworkItemListByCondition(ZxCtDayworkItem zxCtDayworkItem) {
        if (zxCtDayworkItem == null) {
            zxCtDayworkItem = new ZxCtDayworkItem();
        }
        // 分页查询
        PageHelper.startPage(zxCtDayworkItem.getPage(),zxCtDayworkItem.getLimit());
        // 获取数据
        List<ZxCtDayworkItem> zxCtDayworkItemList = zxCtDayworkItemMapper.selectByZxCtDayworkItemList(zxCtDayworkItem);
        // 得到分页信息
        PageInfo<ZxCtDayworkItem> p = new PageInfo<>(zxCtDayworkItemList);

        return repEntity.okList(zxCtDayworkItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtDayworkItemDetail(ZxCtDayworkItem zxCtDayworkItem) {
        if (zxCtDayworkItem == null) {
            zxCtDayworkItem = new ZxCtDayworkItem();
        }
        // 获取数据
        ZxCtDayworkItem dbZxCtDayworkItem = zxCtDayworkItemMapper.selectByPrimaryKey(zxCtDayworkItem.getId());
        // 数据存在
        if (dbZxCtDayworkItem != null) {
            return repEntity.ok(dbZxCtDayworkItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtDayworkItem(ZxCtDayworkItem zxCtDayworkItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtDayworkItem.setId(UuidUtil.generate());
        zxCtDayworkItem.setCreateUserInfo(userKey, realName);
        int flag = zxCtDayworkItemMapper.insert(zxCtDayworkItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtDayworkItem);
        }
    }

    @Override
    public ResponseEntity updateZxCtDayworkItem(ZxCtDayworkItem zxCtDayworkItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtDayworkItem dbZxCtDayworkItem = zxCtDayworkItemMapper.selectByPrimaryKey(zxCtDayworkItem.getId());
        if (dbZxCtDayworkItem != null && StrUtil.isNotEmpty(dbZxCtDayworkItem.getId())) {
           // 单据ID
           dbZxCtDayworkItem.setBillID(zxCtDayworkItem.getBillID());
           // 资源ID
           dbZxCtDayworkItem.setResID(zxCtDayworkItem.getResID());
           // 资源编号
           dbZxCtDayworkItem.setResCode(zxCtDayworkItem.getResCode());
           // 资源名称
           dbZxCtDayworkItem.setResName(zxCtDayworkItem.getResName());
           // 规格型号
           dbZxCtDayworkItem.setSpec(zxCtDayworkItem.getSpec());
           // 单位
           dbZxCtDayworkItem.setResUnit(zxCtDayworkItem.getResUnit());
           // 数量
           dbZxCtDayworkItem.setQuantity(zxCtDayworkItem.getQuantity());
           // 单价
           dbZxCtDayworkItem.setPrice(zxCtDayworkItem.getPrice());
           // 其它费
           dbZxCtDayworkItem.setOtherFee(zxCtDayworkItem.getOtherFee());
           // 金额
           dbZxCtDayworkItem.setAmount(zxCtDayworkItem.getAmount());
           // 管理单元ID
           dbZxCtDayworkItem.setMuID(zxCtDayworkItem.getMuID());
           // 成本管理单元
           dbZxCtDayworkItem.setMuName(zxCtDayworkItem.getMuName());
           // cbsID
           dbZxCtDayworkItem.setCbsID(zxCtDayworkItem.getCbsID());
           // 工作内容
           dbZxCtDayworkItem.setWorkContent(zxCtDayworkItem.getWorkContent());
           // combProp
           dbZxCtDayworkItem.setCombProp(zxCtDayworkItem.getCombProp());
           // pp1
           dbZxCtDayworkItem.setPp1(zxCtDayworkItem.getPp1());
           // pp2
           dbZxCtDayworkItem.setPp2(zxCtDayworkItem.getPp2());
           // pp3
           dbZxCtDayworkItem.setPp3(zxCtDayworkItem.getPp3());
           // pp4
           dbZxCtDayworkItem.setPp4(zxCtDayworkItem.getPp4());
           // pp5
           dbZxCtDayworkItem.setPp5(zxCtDayworkItem.getPp5());
           // pp6
           dbZxCtDayworkItem.setPp6(zxCtDayworkItem.getPp6());
           // pp7
           dbZxCtDayworkItem.setPp7(zxCtDayworkItem.getPp7());
           // pp8
           dbZxCtDayworkItem.setPp8(zxCtDayworkItem.getPp8());
           // pp9
           dbZxCtDayworkItem.setPp9(zxCtDayworkItem.getPp9());
           // pp10
           dbZxCtDayworkItem.setPp10(zxCtDayworkItem.getPp10());
           // 资源类型
           dbZxCtDayworkItem.setResstyle(zxCtDayworkItem.getResstyle());
           // 最后编辑时间
           dbZxCtDayworkItem.setEditTime(zxCtDayworkItem.getEditTime());
           // 排序
           dbZxCtDayworkItem.setSort(zxCtDayworkItem.getSort());
           // 共通
           dbZxCtDayworkItem.setModifyUserInfo(userKey, realName);
           flag = zxCtDayworkItemMapper.updateByPrimaryKey(dbZxCtDayworkItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtDayworkItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtDayworkItem(List<ZxCtDayworkItem> zxCtDayworkItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtDayworkItemList != null && zxCtDayworkItemList.size() > 0) {
           ZxCtDayworkItem zxCtDayworkItem = new ZxCtDayworkItem();
           zxCtDayworkItem.setModifyUserInfo(userKey, realName);
           flag = zxCtDayworkItemMapper.batchDeleteUpdateZxCtDayworkItem(zxCtDayworkItemList, zxCtDayworkItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtDayworkItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public ResponseEntity deleteAllZxCtDayworkItem(ZxCtDayworkItem zxCtDayworkItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtDayworkItem != null && StrUtil.isNotEmpty(zxCtDayworkItem.getBillID())) {
           zxCtDayworkItem.setModifyUserInfo(userKey, realName);
           flag = zxCtDayworkItemMapper.deleteAllZxCtDayworkItem(zxCtDayworkItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("删除成功！");
        }
    }
}
