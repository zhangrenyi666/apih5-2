package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.framework.constant.SysConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCrJYearCreditEvaItemMapper;
import com.apih5.mybatis.pojo.ZxCrHalfYearCreditEvaItem;
import com.apih5.mybatis.pojo.ZxCrJYearCreditEvaItem;
import com.apih5.service.ZxCrHalfYearCreditEvaItemService;
import com.apih5.service.ZxCrJYearCreditEvaItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxCrJYearCreditEvaItemService")
public class ZxCrJYearCreditEvaItemServiceImpl implements ZxCrJYearCreditEvaItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCrJYearCreditEvaItemMapper zxCrJYearCreditEvaItemMapper;

    @Autowired(required = true)
    private ZxCrHalfYearCreditEvaItemService zxCrHalfYearCreditEvaItemService;
    
    @Override
    public ResponseEntity getZxCrJYearCreditEvaItemListByCondition(ZxCrJYearCreditEvaItem zxCrJYearCreditEvaItem) {
        if (zxCrJYearCreditEvaItem == null) {
            zxCrJYearCreditEvaItem = new ZxCrJYearCreditEvaItem();
        }
        // 分页查询
        PageHelper.startPage(zxCrJYearCreditEvaItem.getPage(),zxCrJYearCreditEvaItem.getLimit());
        // 获取数据
        List<ZxCrJYearCreditEvaItem> zxCrJYearCreditEvaItemList = zxCrJYearCreditEvaItemMapper.selectByZxCrJYearCreditEvaItemList(zxCrJYearCreditEvaItem);
        // 得到分页信息
        PageInfo<ZxCrJYearCreditEvaItem> p = new PageInfo<>(zxCrJYearCreditEvaItemList);

        return repEntity.okList(zxCrJYearCreditEvaItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCrJYearCreditEvaItemDetail(ZxCrJYearCreditEvaItem zxCrJYearCreditEvaItem) {
        if (zxCrJYearCreditEvaItem == null) {
            zxCrJYearCreditEvaItem = new ZxCrJYearCreditEvaItem();
        }
        // 获取数据
        ZxCrJYearCreditEvaItem dbZxCrJYearCreditEvaItem = zxCrJYearCreditEvaItemMapper.selectByPrimaryKey(zxCrJYearCreditEvaItem.getZxCrJYearCreditEvaItemId());
        // 数据存在
        if (dbZxCrJYearCreditEvaItem != null) {
            return repEntity.ok(dbZxCrJYearCreditEvaItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCrJYearCreditEvaItem(ZxCrJYearCreditEvaItem zxCrJYearCreditEvaItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCrJYearCreditEvaItem.setZxCrJYearCreditEvaItemId(UuidUtil.generate());
        zxCrJYearCreditEvaItem.setCreateUserInfo(userKey, realName);
        
        int flag = zxCrJYearCreditEvaItemMapper.insert(zxCrJYearCreditEvaItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCrJYearCreditEvaItem);
        }
    }

    @Override
    public ResponseEntity updateZxCrJYearCreditEvaItem(ZxCrJYearCreditEvaItem zxCrJYearCreditEvaItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCrJYearCreditEvaItem dbZxCrJYearCreditEvaItem = zxCrJYearCreditEvaItemMapper.selectByPrimaryKey(zxCrJYearCreditEvaItem.getZxCrJYearCreditEvaItemId());
        if (dbZxCrJYearCreditEvaItem != null && StrUtil.isNotEmpty(dbZxCrJYearCreditEvaItem.getZxCrJYearCreditEvaItemId())) {
           // 主表ID
           dbZxCrJYearCreditEvaItem.setMasterID(zxCrJYearCreditEvaItem.getMasterID());
           // 协作单位id
           dbZxCrJYearCreditEvaItem.setCustomerId(zxCrJYearCreditEvaItem.getCustomerId());
           // 组织机构代码
           dbZxCrJYearCreditEvaItem.setOrgCertificate(zxCrJYearCreditEvaItem.getOrgCertificate());
           // 协作单位名称
           dbZxCrJYearCreditEvaItem.setCustomerName(zxCrJYearCreditEvaItem.getCustomerName());
           // 协作单位负责人
           dbZxCrJYearCreditEvaItem.setChargeMan(zxCrJYearCreditEvaItem.getChargeMan());
           // 工程所属公司ID
           dbZxCrJYearCreditEvaItem.setProjectID(zxCrJYearCreditEvaItem.getProjectID());
           // 工程所属公司
           dbZxCrJYearCreditEvaItem.setProjectName(zxCrJYearCreditEvaItem.getProjectName());
           // 进场日期
           dbZxCrJYearCreditEvaItem.setInDate(zxCrJYearCreditEvaItem.getInDate());
           // 退场日期
           dbZxCrJYearCreditEvaItem.setOutDate(zxCrJYearCreditEvaItem.getOutDate());
           // 承建工程合同额（万元）
           dbZxCrJYearCreditEvaItem.setContractAmt(zxCrJYearCreditEvaItem.getContractAmt());
           // 工程所属项目个数
           dbZxCrJYearCreditEvaItem.setProjectNum(zxCrJYearCreditEvaItem.getProjectNum());
           // 专业类别
           dbZxCrJYearCreditEvaItem.setCatName(zxCrJYearCreditEvaItem.getCatName());
           // 专业类别ID
           dbZxCrJYearCreditEvaItem.setCatID(zxCrJYearCreditEvaItem.getCatID());
           // 专业类别代码
           dbZxCrJYearCreditEvaItem.setCatCode(zxCrJYearCreditEvaItem.getCatCode());
           // 评价次数
           dbZxCrJYearCreditEvaItem.setCheckNum(zxCrJYearCreditEvaItem.getCheckNum());
           // 局信用评价得分（上半年）
           dbZxCrJYearCreditEvaItem.setFirstSoce(zxCrJYearCreditEvaItem.getFirstSoce());
           // 局评价等级（上半年）
           dbZxCrJYearCreditEvaItem.setFirstLevel(zxCrJYearCreditEvaItem.getFirstLevel());
           // 局信用评价得分（下半年）
           dbZxCrJYearCreditEvaItem.setSecondScore(zxCrJYearCreditEvaItem.getSecondScore());
           // 局评价等级（下半年）
           dbZxCrJYearCreditEvaItem.setSecondLevel(zxCrJYearCreditEvaItem.getSecondLevel());
           // 有无直接降为D的行为
           dbZxCrJYearCreditEvaItem.setdLevel(zxCrJYearCreditEvaItem.getdLevel());
           // 最终信用评价得分
           dbZxCrJYearCreditEvaItem.setLastScore(zxCrJYearCreditEvaItem.getLastScore());
           // 信用评价等级
           dbZxCrJYearCreditEvaItem.setLastLevel(zxCrJYearCreditEvaItem.getLastLevel());
           // editTime
           dbZxCrJYearCreditEvaItem.setEditTime(zxCrJYearCreditEvaItem.getEditTime());
           // comID
           dbZxCrJYearCreditEvaItem.setComID(zxCrJYearCreditEvaItem.getComID());
           // comName
           dbZxCrJYearCreditEvaItem.setComName(zxCrJYearCreditEvaItem.getComName());
           // comOrders
           dbZxCrJYearCreditEvaItem.setComOrders(zxCrJYearCreditEvaItem.getComOrders());
           // 季度信用评价ID
           dbZxCrJYearCreditEvaItem.setQuartEvalID(zxCrJYearCreditEvaItem.getQuartEvalID());
           // orderNo
           dbZxCrJYearCreditEvaItem.setOrderNo(zxCrJYearCreditEvaItem.getOrderNo());
           // 参建工程所属公司个数
           dbZxCrJYearCreditEvaItem.setCompNum(zxCrJYearCreditEvaItem.getCompNum());
           // combProp
           dbZxCrJYearCreditEvaItem.setCombProp(zxCrJYearCreditEvaItem.getCombProp());
           // 加减分项得分
           dbZxCrJYearCreditEvaItem.setScoreOfAdditionSubtraction(zxCrJYearCreditEvaItem.getScoreOfAdditionSubtraction());
           // 相关部门（单位）修正分值
           dbZxCrJYearCreditEvaItem.setRevisedScoresOfRelevantDepartments(zxCrJYearCreditEvaItem.getRevisedScoresOfRelevantDepartments());
           // 在省市级工程质量安全监督机构及相关部门的检查中被通报
           dbZxCrJYearCreditEvaItem.setInformedDuringInspection(zxCrJYearCreditEvaItem.getInformedDuringInspection());
           // 备注
           dbZxCrJYearCreditEvaItem.setRemarks(zxCrJYearCreditEvaItem.getRemarks());
           // 排序
           dbZxCrJYearCreditEvaItem.setSort(zxCrJYearCreditEvaItem.getSort());
           // 共通
           dbZxCrJYearCreditEvaItem.setModifyUserInfo(userKey, realName);
           flag = zxCrJYearCreditEvaItemMapper.updateByPrimaryKey(dbZxCrJYearCreditEvaItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCrJYearCreditEvaItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCrJYearCreditEvaItem(List<ZxCrJYearCreditEvaItem> zxCrJYearCreditEvaItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCrJYearCreditEvaItemList != null && zxCrJYearCreditEvaItemList.size() > 0) {
           ZxCrJYearCreditEvaItem zxCrJYearCreditEvaItem = new ZxCrJYearCreditEvaItem();
           zxCrJYearCreditEvaItem.setModifyUserInfo(userKey, realName);
           flag = zxCrJYearCreditEvaItemMapper.batchDeleteUpdateZxCrJYearCreditEvaItem(zxCrJYearCreditEvaItemList, zxCrJYearCreditEvaItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCrJYearCreditEvaItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    // 初期显示
    @Override
    public ResponseEntity getZxCrJYearCreditEvaItemInit(ZxCrJYearCreditEvaItem zxCrJYearCreditEvaItem) {
        if (zxCrJYearCreditEvaItem == null) {
        	zxCrJYearCreditEvaItem = new ZxCrJYearCreditEvaItem();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(zxCrJYearCreditEvaItem.getPeriod());
        int year = calendar.get(Calendar.YEAR);
        // int month = calendar.get(Calendar.MONTH);
        // 按 公司 项目 年份 专业分类 筛选数据
        ZxCrJYearCreditEvaItem dbQuery = new ZxCrJYearCreditEvaItem();
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            dbQuery.setComID("");
            dbQuery.setProjectID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            dbQuery.setComID(zxCrJYearCreditEvaItem.getCompanyId());
            dbQuery.setProjectID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            dbQuery.setProjectID(zxCrJYearCreditEvaItem.getProjectId());
        }
        dbQuery.setPeriodYear(String.valueOf(year));
        dbQuery.setCatID(zxCrJYearCreditEvaItem.getCatID());
        dbQuery.setResID(zxCrJYearCreditEvaItem.getResID());
        // 分页查询
        PageHelper.startPage(zxCrJYearCreditEvaItem.getPage(),zxCrJYearCreditEvaItem.getLimit());
        // 获取数据
        List<ZxCrJYearCreditEvaItem> zxCrHalfYearCreditEvaItemList = zxCrJYearCreditEvaItemMapper.selectByZxCrJYearCreditEvaItemList(dbQuery);
        // 得到分页信息
        PageInfo<ZxCrJYearCreditEvaItem> p = new PageInfo<>(zxCrHalfYearCreditEvaItemList);
        return repEntity.okList(zxCrHalfYearCreditEvaItemList, p.getTotal());
    }
    //逻辑修改手动录入部分数据
    @Override
    public ResponseEntity updateZxCrJYearCreditEvaItemAll(List<ZxCrJYearCreditEvaItem> zxCrJYearCreditEvaItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCrJYearCreditEvaItemList != null && zxCrJYearCreditEvaItemList.size() > 0) {
           ZxCrJYearCreditEvaItem zxCrJYearCreditEvaItem1 = new ZxCrJYearCreditEvaItem();
           zxCrJYearCreditEvaItem1.setModifyUserInfo(userKey, realName);
           flag = zxCrJYearCreditEvaItemMapper.batchDeleteUpdateZxCrJYearCreditEvaItem(zxCrJYearCreditEvaItemList, zxCrJYearCreditEvaItem1);
        }
        ZxCrJYearCreditEvaItem zxCrJYearCreditEvaItem2 = new ZxCrJYearCreditEvaItem();
        for(int i=0 ; i<zxCrJYearCreditEvaItemList.size();i++) {
        	ZxCrJYearCreditEvaItem a = zxCrJYearCreditEvaItemList.get(i);
        	zxCrJYearCreditEvaItem2.setZxCrJYearCreditEvaItemId(UuidUtil.generate());
        	zxCrJYearCreditEvaItem2.setCreateUserInfo(userKey, realName);
        	zxCrJYearCreditEvaItem2.setMasterID(a.getMasterID());
        	zxCrJYearCreditEvaItem2.setCustomerId(a.getCustomerId());
        	zxCrJYearCreditEvaItem2.setOrgCertificate(a.getOrgCertificate());
        	zxCrJYearCreditEvaItem2.setCustomerName(a.getCustomerName());
        	zxCrJYearCreditEvaItem2.setChargeMan(a.getChargeMan());
        	zxCrJYearCreditEvaItem2.setProjectID(a.getProjectID());
        	zxCrJYearCreditEvaItem2.setProjectName(a.getProjectName());
        	zxCrJYearCreditEvaItem2.setInDate(a.getInDate());
        	zxCrJYearCreditEvaItem2.setOutDate(a.getOutDate());
        	zxCrJYearCreditEvaItem2.setContractAmt(a.getContractAmt());
        	zxCrJYearCreditEvaItem2.setProjectNum(a.getProjectNum());
        	zxCrJYearCreditEvaItem2.setCatName(a.getCatName());
        	zxCrJYearCreditEvaItem2.setCatID(a.getCatID());
        	zxCrJYearCreditEvaItem2.setCatCode(a.getCatCode());
        	zxCrJYearCreditEvaItem2.setCheckNum(a.getCheckNum());
        	zxCrJYearCreditEvaItem2.setFirstSoce(a.getFirstSoce());
        	zxCrJYearCreditEvaItem2.setFirstLevel(a.getFirstLevel());
        	zxCrJYearCreditEvaItem2.setSecondScore(a.getSecondScore());
        	zxCrJYearCreditEvaItem2.setSecondLevel(a.getSecondLevel());
        	zxCrJYearCreditEvaItem2.setdLevel(a.getdLevel());
        	zxCrJYearCreditEvaItem2.setLastScore(a.getLastScore());
        	zxCrJYearCreditEvaItem2.setLastLevel(a.getLastLevel());
        	zxCrJYearCreditEvaItem2.setCompNum(a.getCompNum());
        	zxCrJYearCreditEvaItem2.setScoreOfAdditionSubtraction(a.getScoreOfAdditionSubtraction());
        	zxCrJYearCreditEvaItem2.setRevisedScoresOfRelevantDepartments(a.getRevisedScoresOfRelevantDepartments());
        	zxCrJYearCreditEvaItem2.setInformedDuringInspection(a.getInformedDuringInspection());
        	zxCrJYearCreditEvaItemMapper.insert(zxCrJYearCreditEvaItem2);
        }
        // 获取数据
        List<ZxCrJYearCreditEvaItem> zxCrHalfYearCreditEvaItemList = zxCrJYearCreditEvaItemMapper.selectInit(zxCrJYearCreditEvaItem2);
        
        // 分页查询
        PageHelper.startPage(zxCrJYearCreditEvaItem2.getPage(),zxCrJYearCreditEvaItem2.getLimit());
        // 得到分页信息
        PageInfo<ZxCrJYearCreditEvaItem> p = new PageInfo<>(zxCrHalfYearCreditEvaItemList);
        
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCrHalfYearCreditEvaItemList);
        }
    }
}
