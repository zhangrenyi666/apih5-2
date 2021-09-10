package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxGcsgCtPriceSysItemMapper;
import com.apih5.mybatis.pojo.ZxGcsgCtPriceSysItem;
import com.apih5.service.ZxGcsgCtPriceSysItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxGcsgCtPriceSysItemService")
public class ZxGcsgCtPriceSysItemServiceImpl implements ZxGcsgCtPriceSysItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxGcsgCtPriceSysItemMapper zxGcsgCtPriceSysItemMapper;

    @Override
    public ResponseEntity getZxGcsgCtPriceSysItemListByCondition(ZxGcsgCtPriceSysItem zxGcsgCtPriceSysItem) {
        if (zxGcsgCtPriceSysItem == null) {
            zxGcsgCtPriceSysItem = new ZxGcsgCtPriceSysItem();
        }
        // 分页查询
        PageHelper.startPage(zxGcsgCtPriceSysItem.getPage(),zxGcsgCtPriceSysItem.getLimit());
        // 获取数据
        List<ZxGcsgCtPriceSysItem> zxGcsgCtPriceSysItemList = zxGcsgCtPriceSysItemMapper.selectByZxGcsgCtPriceSysItemList(zxGcsgCtPriceSysItem);
        // 得到分页信息
        PageInfo<ZxGcsgCtPriceSysItem> p = new PageInfo<>(zxGcsgCtPriceSysItemList);

        return repEntity.okList(zxGcsgCtPriceSysItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxGcsgCtPriceSysItemDetail(ZxGcsgCtPriceSysItem zxGcsgCtPriceSysItem) {
        if (zxGcsgCtPriceSysItem == null) {
            zxGcsgCtPriceSysItem = new ZxGcsgCtPriceSysItem();
        }
        // 获取数据
        ZxGcsgCtPriceSysItem dbZxGcsgCtPriceSysItem = zxGcsgCtPriceSysItemMapper.selectByPrimaryKey(zxGcsgCtPriceSysItem.getCtPriceSysItemId());
        // 数据存在
        if (dbZxGcsgCtPriceSysItem != null) {
            return repEntity.ok(dbZxGcsgCtPriceSysItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxGcsgCtPriceSysItem(ZxGcsgCtPriceSysItem zxGcsgCtPriceSysItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxGcsgCtPriceSysItem.setCtPriceSysItemId(UuidUtil.generate());
        zxGcsgCtPriceSysItem.setCreateUserInfo(userKey, realName);
        int flag = zxGcsgCtPriceSysItemMapper.insert(zxGcsgCtPriceSysItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxGcsgCtPriceSysItem);
        }
    }

    @Override
    public ResponseEntity updateZxGcsgCtPriceSysItem(ZxGcsgCtPriceSysItem zxGcsgCtPriceSysItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxGcsgCtPriceSysItem dbZxGcsgCtPriceSysItem = zxGcsgCtPriceSysItemMapper.selectByPrimaryKey(zxGcsgCtPriceSysItem.getCtPriceSysItemId());
        if (dbZxGcsgCtPriceSysItem != null && StrUtil.isNotEmpty(dbZxGcsgCtPriceSysItem.getCtPriceSysItemId())) {
           // 合同单价分析表ID(mainID)
           dbZxGcsgCtPriceSysItem.setCtPriceSysId(zxGcsgCtPriceSysItem.getCtPriceSysId());
           // 标准工序ID
           dbZxGcsgCtPriceSysItem.setProcessID(zxGcsgCtPriceSysItem.getProcessID());
           // 工序编码
           dbZxGcsgCtPriceSysItem.setProcessNo(zxGcsgCtPriceSysItem.getProcessNo());
           // 标准工序名称
           dbZxGcsgCtPriceSysItem.setProcessName(zxGcsgCtPriceSysItem.getProcessName());
           // 最后编辑时间
           dbZxGcsgCtPriceSysItem.setEditTime(zxGcsgCtPriceSysItem.getEditTime());
           // 人工费
           dbZxGcsgCtPriceSysItem.setRgf(zxGcsgCtPriceSysItem.getRgf());
           // 机械设备
           dbZxGcsgCtPriceSysItem.setJxsb(zxGcsgCtPriceSysItem.getJxsb());
           // 周转材料
           dbZxGcsgCtPriceSysItem.setZzcl(zxGcsgCtPriceSysItem.getZzcl());
           // 备注
           dbZxGcsgCtPriceSysItem.setOpinionField1(zxGcsgCtPriceSysItem.getOpinionField1());
           // 备注
           dbZxGcsgCtPriceSysItem.setOpinionField2(zxGcsgCtPriceSysItem.getOpinionField2());
           // 备注
           dbZxGcsgCtPriceSysItem.setOpinionField3(zxGcsgCtPriceSysItem.getOpinionField3());
           // 备注
           dbZxGcsgCtPriceSysItem.setOpinionField4(zxGcsgCtPriceSysItem.getOpinionField4());
           // 备注
           dbZxGcsgCtPriceSysItem.setOpinionField5(zxGcsgCtPriceSysItem.getOpinionField5());
           // 备注
           dbZxGcsgCtPriceSysItem.setOpinionField6(zxGcsgCtPriceSysItem.getOpinionField6());
           // 备注
           dbZxGcsgCtPriceSysItem.setOpinionField7(zxGcsgCtPriceSysItem.getOpinionField7());
           // 备注
           dbZxGcsgCtPriceSysItem.setOpinionField8(zxGcsgCtPriceSysItem.getOpinionField8());
           // 备注
           dbZxGcsgCtPriceSysItem.setOpinionField9(zxGcsgCtPriceSysItem.getOpinionField9());
           // 备注
           dbZxGcsgCtPriceSysItem.setOpinionField10(zxGcsgCtPriceSysItem.getOpinionField10());
           // 流程id
           dbZxGcsgCtPriceSysItem.setApih5FlowId(zxGcsgCtPriceSysItem.getApih5FlowId());
           // work_id
           dbZxGcsgCtPriceSysItem.setWorkId(zxGcsgCtPriceSysItem.getWorkId());
           // 工序审核状态
           dbZxGcsgCtPriceSysItem.setApih5FlowStatus(zxGcsgCtPriceSysItem.getApih5FlowStatus());
           // 流程状态
           dbZxGcsgCtPriceSysItem.setApih5FlowNodeStatus(zxGcsgCtPriceSysItem.getApih5FlowNodeStatus());
           // 单价
           dbZxGcsgCtPriceSysItem.setPrice(zxGcsgCtPriceSysItem.getPrice());
           // 备注
           dbZxGcsgCtPriceSysItem.setRemarks(zxGcsgCtPriceSysItem.getRemarks());
           // 排序
           dbZxGcsgCtPriceSysItem.setSort(zxGcsgCtPriceSysItem.getSort());
           // 共通
           dbZxGcsgCtPriceSysItem.setModifyUserInfo(userKey, realName);
           flag = zxGcsgCtPriceSysItemMapper.updateByPrimaryKey(dbZxGcsgCtPriceSysItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxGcsgCtPriceSysItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxGcsgCtPriceSysItem(List<ZxGcsgCtPriceSysItem> zxGcsgCtPriceSysItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxGcsgCtPriceSysItemList != null && zxGcsgCtPriceSysItemList.size() > 0) {
           ZxGcsgCtPriceSysItem zxGcsgCtPriceSysItem = new ZxGcsgCtPriceSysItem();
           zxGcsgCtPriceSysItem.setModifyUserInfo(userKey, realName);
           flag = zxGcsgCtPriceSysItemMapper.batchDeleteUpdateZxGcsgCtPriceSysItem(zxGcsgCtPriceSysItemList, zxGcsgCtPriceSysItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxGcsgCtPriceSysItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
