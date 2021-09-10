package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.mybatis.pojo.ZxSkStockDifMonthItem;
import com.apih5.mybatis.pojo.ZxSkStockTransferInitialReceipt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkReceivingDynamicMapper;
import com.apih5.mybatis.pojo.ZxSkReceivingDynamic;
import com.apih5.service.ZxSkReceivingDynamicService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkReceivingDynamicService")
public class ZxSkReceivingDynamicServiceImpl implements ZxSkReceivingDynamicService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkReceivingDynamicMapper zxSkReceivingDynamicMapper;

    @Override
    public ResponseEntity getZxSkReceivingDynamicListByCondition(ZxSkReceivingDynamic zxSkReceivingDynamic) {
        if (zxSkReceivingDynamic == null) {
            zxSkReceivingDynamic = new ZxSkReceivingDynamic();
        }
        // 分页查询
        PageHelper.startPage(zxSkReceivingDynamic.getPage(),zxSkReceivingDynamic.getLimit());
        // 获取数据
        List<ZxSkReceivingDynamic> zxSkReceivingDynamicList = zxSkReceivingDynamicMapper.selectByZxSkReceivingDynamicList(zxSkReceivingDynamic);
        // 得到分页信息
        PageInfo<ZxSkReceivingDynamic> p = new PageInfo<>(zxSkReceivingDynamicList);

        return repEntity.okList(zxSkReceivingDynamicList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkReceivingDynamicDetail(ZxSkReceivingDynamic zxSkReceivingDynamic) {
        if (zxSkReceivingDynamic == null) {
            zxSkReceivingDynamic = new ZxSkReceivingDynamic();
        }
        // 获取数据
//        ZxSkReceivingDynamic dbZxSkReceivingDynamic = zxSkReceivingDynamicMapper.selectByPrimaryKey(zxSkReceivingDynamic.getZxSkReceivingDynamicId());
        // 数据存在
//        if (dbZxSkReceivingDynamic != null) {
//            return repEntity.ok(dbZxSkReceivingDynamic);
//        } else {
            return repEntity.layerMessage("no", "无数据！");
//        }
    }

    @Override
    public ResponseEntity saveZxSkReceivingDynamic(ZxSkReceivingDynamic zxSkReceivingDynamic) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
//        zxSkReceivingDynamic.setZxSkReceivingDynamicId(UuidUtil.generate());
        zxSkReceivingDynamic.setCreateUserInfo(userKey, realName);
        int flag = zxSkReceivingDynamicMapper.insert(zxSkReceivingDynamic);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkReceivingDynamic);
        }
    }

    @Override
    public ResponseEntity updateZxSkReceivingDynamic(ZxSkReceivingDynamic zxSkReceivingDynamic) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
//        ZxSkReceivingDynamic dbZxSkReceivingDynamic = zxSkReceivingDynamicMapper.selectByPrimaryKey(zxSkReceivingDynamic.getZxSkReceivingDynamicId());
//        if (dbZxSkReceivingDynamic != null && StrUtil.isNotEmpty(dbZxSkReceivingDynamic.getZxSkReceivingDynamicId())) {
//           // 日期
//           dbZxSkReceivingDynamic.setInbusDate(zxSkReceivingDynamic.getInbusDate());
//           // 物资编码
//           dbZxSkReceivingDynamic.setResCode(zxSkReceivingDynamic.getResCode());
//           // 物资名称
//           dbZxSkReceivingDynamic.setResName(zxSkReceivingDynamic.getResName());
//           // 规格
//           dbZxSkReceivingDynamic.setSpec(zxSkReceivingDynamic.getSpec());
//           // 计量单位
//           dbZxSkReceivingDynamic.setUnit(zxSkReceivingDynamic.getUnit());
//           // 凭证号
//           dbZxSkReceivingDynamic.setVoucherNo(zxSkReceivingDynamic.getVoucherNo());
//           // 摘要
//           dbZxSkReceivingDynamic.setOutOrgName(zxSkReceivingDynamic.getOutOrgName());
//           // 购入单价
//           dbZxSkReceivingDynamic.setStdPrice(zxSkReceivingDynamic.getStdPrice());
//           // 甲供
//           dbZxSkReceivingDynamic.setOrsQty(zxSkReceivingDynamic.getOrsQty());
//           // 其他
//           dbZxSkReceivingDynamic.setOtrQty(zxSkReceivingDynamic.getOtrQty());
//           // 自行采购
//           dbZxSkReceivingDynamic.setSerQty(zxSkReceivingDynamic.getSerQty());
//           // 预收
//           dbZxSkReceivingDynamic.setObuQty(zxSkReceivingDynamic.getObuQty());
//           // 甲控
//           dbZxSkReceivingDynamic.setOcsQty(zxSkReceivingDynamic.getOcsQty());
//           // 收入合计数量
//           dbZxSkReceivingDynamic.setTotalQty(zxSkReceivingDynamic.getTotalQty());
//           // 收入合计金额
//           dbZxSkReceivingDynamic.setInAmt(zxSkReceivingDynamic.getInAmt());
//           // 工程耗用
//           dbZxSkReceivingDynamic.setOtkQty(zxSkReceivingDynamic.getOtkQty());
//           // 调拨内调
//           dbZxSkReceivingDynamic.setWithinQty(zxSkReceivingDynamic.getWithinQty());
//           // 调拨外调
//           dbZxSkReceivingDynamic.setRithinQty(zxSkReceivingDynamic.getRithinQty());
//           // 发出数量
//           dbZxSkReceivingDynamic.setInOrOutTotalQty(zxSkReceivingDynamic.getInOrOutTotalQty());
//           // 发出平均单价
//           dbZxSkReceivingDynamic.setInOrOutStdPrice(zxSkReceivingDynamic.getInOrOutStdPrice());
//           // 发出金额
//           dbZxSkReceivingDynamic.setInOrOutoutAmt(zxSkReceivingDynamic.getInOrOutoutAmt());
//           // 盈亏数量
//           dbZxSkReceivingDynamic.setDiskoutQty(zxSkReceivingDynamic.getDiskoutQty());
//           // 盈亏金额
//           dbZxSkReceivingDynamic.setOutAmt(zxSkReceivingDynamic.getOutAmt());
//           // 结存数量
//           dbZxSkReceivingDynamic.setResultQty(zxSkReceivingDynamic.getResultQty());
//           // 结存平均单价
//           dbZxSkReceivingDynamic.setResultAmt(zxSkReceivingDynamic.getResultAmt());
//           // 结存金额
//           dbZxSkReceivingDynamic.setResultPrice(zxSkReceivingDynamic.getResultPrice());
//           // 材料类型
//           dbZxSkReceivingDynamic.setMaterialType(zxSkReceivingDynamic.getMaterialType());
//           // 项目ID
//           dbZxSkReceivingDynamic.setDepartmentId(zxSkReceivingDynamic.getDepartmentId());
//           // 开始时间
//           dbZxSkReceivingDynamic.setInDate(zxSkReceivingDynamic.getInDate());
//           // 结束时间
//           dbZxSkReceivingDynamic.setOutDate(zxSkReceivingDynamic.getOutDate());
//           // 项目名称
//           dbZxSkReceivingDynamic.setDepartmentName(zxSkReceivingDynamic.getDepartmentName());
//           // 备注
//           dbZxSkReceivingDynamic.setRemarks(zxSkReceivingDynamic.getRemarks());
//           // 排序
//           dbZxSkReceivingDynamic.setSort(zxSkReceivingDynamic.getSort());
//           // 共通
//           dbZxSkReceivingDynamic.setModifyUserInfo(userKey, realName);
//           flag = zxSkReceivingDynamicMapper.updateByPrimaryKey(dbZxSkReceivingDynamic);
//        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkReceivingDynamic);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkReceivingDynamic(List<ZxSkReceivingDynamic> zxSkReceivingDynamicList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkReceivingDynamicList != null && zxSkReceivingDynamicList.size() > 0) {
           ZxSkReceivingDynamic zxSkReceivingDynamic = new ZxSkReceivingDynamic();
           zxSkReceivingDynamic.setModifyUserInfo(userKey, realName);
           flag = zxSkReceivingDynamicMapper.batchDeleteUpdateZxSkReceivingDynamic(zxSkReceivingDynamicList, zxSkReceivingDynamic);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkReceivingDynamicList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓


}
