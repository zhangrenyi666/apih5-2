package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.apih5.framework.constant.SysConst;
import com.apih5.mybatis.dao.ZxCrColResourceMapper;
import com.apih5.mybatis.dao.ZxCrProjectEvaluationMapper;
import com.apih5.mybatis.pojo.ZxCrColResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCrColCategoryMapper;
import com.apih5.mybatis.dao.ZxCrHalfYearCreditEvaItemMapper;
import com.apih5.mybatis.pojo.ZxCrColCategory;
import com.apih5.mybatis.pojo.ZxCrHalfYearCreditEvaItem;
import com.apih5.mybatis.pojo.ZxCrProjectEvaluation;
import com.apih5.service.ZxCrHalfYearCreditEvaItemService;
import com.apih5.service.ZxCrProjectEvaluationService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import org.springframework.util.CollectionUtils;

@Service("zxCrHalfYearCreditEvaItemService")
public class ZxCrHalfYearCreditEvaItemServiceImpl implements ZxCrHalfYearCreditEvaItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCrHalfYearCreditEvaItemMapper zxCrHalfYearCreditEvaItemMapper;
    
    @Autowired(required = true)
    private ZxCrColCategoryMapper zxCrColCategoryMapper;
    
    @Autowired(required = true)
    private ZxCrProjectEvaluationService zxCrProjectEvaluationService;

    @Autowired(required = true)
    private ZxCrColResourceMapper zxCrColResourceMapper;

    @Autowired(required = true)
    private ZxCrProjectEvaluationMapper zxCrProjectEvaluationMapper;

    @Override
    public ResponseEntity getZxCrHalfYearCreditEvaItemListByCondition(ZxCrHalfYearCreditEvaItem zxCrHalfYearCreditEvaItem) {
        if (zxCrHalfYearCreditEvaItem == null) {
            zxCrHalfYearCreditEvaItem = new ZxCrHalfYearCreditEvaItem();
        }
        // 分页查询
        PageHelper.startPage(zxCrHalfYearCreditEvaItem.getPage(),zxCrHalfYearCreditEvaItem.getLimit());
        // 获取数据
        List<ZxCrHalfYearCreditEvaItem> zxCrHalfYearCreditEvaItemList = zxCrHalfYearCreditEvaItemMapper.selectByZxCrHalfYearCreditEvaItemList(zxCrHalfYearCreditEvaItem);
        // 得到分页信息
        PageInfo<ZxCrHalfYearCreditEvaItem> p = new PageInfo<>(zxCrHalfYearCreditEvaItemList);

        return repEntity.okList(zxCrHalfYearCreditEvaItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCrHalfYearCreditEvaItemDetail(ZxCrHalfYearCreditEvaItem zxCrHalfYearCreditEvaItem) {
        if (zxCrHalfYearCreditEvaItem == null) {
            zxCrHalfYearCreditEvaItem = new ZxCrHalfYearCreditEvaItem();
        }
        // 获取数据
        ZxCrHalfYearCreditEvaItem dbZxCrHalfYearCreditEvaItem = zxCrHalfYearCreditEvaItemMapper.selectByPrimaryKey(zxCrHalfYearCreditEvaItem.getZxCrHalfYearCreditEvaItemId());
        // 数据存在
        if (dbZxCrHalfYearCreditEvaItem != null) {
            return repEntity.ok(dbZxCrHalfYearCreditEvaItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCrHalfYearCreditEvaItem(ZxCrHalfYearCreditEvaItem zxCrHalfYearCreditEvaItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCrHalfYearCreditEvaItem.setZxCrHalfYearCreditEvaItemId(UuidUtil.generate());
        zxCrHalfYearCreditEvaItem.setCreateUserInfo(userKey, realName);
        int flag = zxCrHalfYearCreditEvaItemMapper.insert(zxCrHalfYearCreditEvaItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCrHalfYearCreditEvaItem);
        }
    }

    @Override
    public ResponseEntity updateZxCrHalfYearCreditEvaItem(ZxCrHalfYearCreditEvaItem zxCrHalfYearCreditEvaItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCrHalfYearCreditEvaItem dbZxCrHalfYearCreditEvaItem = zxCrHalfYearCreditEvaItemMapper.selectByPrimaryKey(zxCrHalfYearCreditEvaItem.getZxCrHalfYearCreditEvaItemId());
        if (dbZxCrHalfYearCreditEvaItem != null && StrUtil.isNotEmpty(dbZxCrHalfYearCreditEvaItem.getZxCrHalfYearCreditEvaItemId())) {
           // 主表ID
           dbZxCrHalfYearCreditEvaItem.setMasterID(zxCrHalfYearCreditEvaItem.getMasterID());
           // 协作单位id
           dbZxCrHalfYearCreditEvaItem.setCustomerId(zxCrHalfYearCreditEvaItem.getCustomerId());
           // 组织机构代码
           dbZxCrHalfYearCreditEvaItem.setOrgCertificate(zxCrHalfYearCreditEvaItem.getOrgCertificate());
           // 协作单位名称
           dbZxCrHalfYearCreditEvaItem.setCustomerName(zxCrHalfYearCreditEvaItem.getCustomerName());
           // 协作单位负责人
           dbZxCrHalfYearCreditEvaItem.setChargeMan(zxCrHalfYearCreditEvaItem.getChargeMan());
           // 工程所属项目ID
           dbZxCrHalfYearCreditEvaItem.setProjectID(zxCrHalfYearCreditEvaItem.getProjectID());
           // 工程所属项目
           dbZxCrHalfYearCreditEvaItem.setProjectName(zxCrHalfYearCreditEvaItem.getProjectName());
           // 进场日期
           dbZxCrHalfYearCreditEvaItem.setInDate(zxCrHalfYearCreditEvaItem.getInDate());
           // 退场日期
           dbZxCrHalfYearCreditEvaItem.setOutDate(zxCrHalfYearCreditEvaItem.getOutDate());
           // 承建工程合同额（万元）
           dbZxCrHalfYearCreditEvaItem.setContractAmt(zxCrHalfYearCreditEvaItem.getContractAmt());
           // 工程所属项目个数
           dbZxCrHalfYearCreditEvaItem.setProjectNum(zxCrHalfYearCreditEvaItem.getProjectNum());
           // 专业类别
           dbZxCrHalfYearCreditEvaItem.setCatName(zxCrHalfYearCreditEvaItem.getCatName());
           // 专业类别ID
           dbZxCrHalfYearCreditEvaItem.setCatID(zxCrHalfYearCreditEvaItem.getCatID());
           // 专业类别代码
           dbZxCrHalfYearCreditEvaItem.setCatCode(zxCrHalfYearCreditEvaItem.getCatCode());
           // 评价次数
           dbZxCrHalfYearCreditEvaItem.setCheckNum(zxCrHalfYearCreditEvaItem.getCheckNum());
           // 公司信用评价得分（第一次）
           dbZxCrHalfYearCreditEvaItem.setFirstSoce(zxCrHalfYearCreditEvaItem.getFirstSoce());
           // 评价等级(第一次)
           dbZxCrHalfYearCreditEvaItem.setFirstLevel(zxCrHalfYearCreditEvaItem.getFirstLevel());
           // 公司信用评价得分（第二次）
           dbZxCrHalfYearCreditEvaItem.setSecondScore(zxCrHalfYearCreditEvaItem.getSecondScore());
           // 评价等级(第二次)
           dbZxCrHalfYearCreditEvaItem.setSecondLevel(zxCrHalfYearCreditEvaItem.getSecondLevel());
           // 有无直接降为D的行为
           dbZxCrHalfYearCreditEvaItem.setdLevel(zxCrHalfYearCreditEvaItem.getdLevel());
           // 最终信用评价得分
           dbZxCrHalfYearCreditEvaItem.setLastScore(zxCrHalfYearCreditEvaItem.getLastScore());
           // 信用评价等级
           dbZxCrHalfYearCreditEvaItem.setLastLevel(zxCrHalfYearCreditEvaItem.getLastLevel());
           // editTime
           dbZxCrHalfYearCreditEvaItem.setEditTime(zxCrHalfYearCreditEvaItem.getEditTime());
           // comID
           dbZxCrHalfYearCreditEvaItem.setComID(zxCrHalfYearCreditEvaItem.getComID());
           // comName
           dbZxCrHalfYearCreditEvaItem.setComName(zxCrHalfYearCreditEvaItem.getComName());
           // comOrders
           dbZxCrHalfYearCreditEvaItem.setComOrders(zxCrHalfYearCreditEvaItem.getComOrders());
           // 季度信用评价ID
           dbZxCrHalfYearCreditEvaItem.setQuartEvalID(zxCrHalfYearCreditEvaItem.getQuartEvalID());
           // orderNo
           dbZxCrHalfYearCreditEvaItem.setOrderNo(zxCrHalfYearCreditEvaItem.getOrderNo());
           // isHaveD
           dbZxCrHalfYearCreditEvaItem.setIsHaveD(zxCrHalfYearCreditEvaItem.getIsHaveD());
           // isHaveD
           dbZxCrHalfYearCreditEvaItem.setIsHaveD(zxCrHalfYearCreditEvaItem.getIsHaveD());
           // pp1
           dbZxCrHalfYearCreditEvaItem.setPp1(zxCrHalfYearCreditEvaItem.getPp1());
           // pp2
           dbZxCrHalfYearCreditEvaItem.setPp2(zxCrHalfYearCreditEvaItem.getPp2());
           // 排序
           dbZxCrHalfYearCreditEvaItem.setSort(zxCrHalfYearCreditEvaItem.getSort());
           // 共通
           dbZxCrHalfYearCreditEvaItem.setModifyUserInfo(userKey, realName);
           flag = zxCrHalfYearCreditEvaItemMapper.updateByPrimaryKey(dbZxCrHalfYearCreditEvaItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCrHalfYearCreditEvaItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCrHalfYearCreditEvaItem(List<ZxCrHalfYearCreditEvaItem> zxCrHalfYearCreditEvaItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCrHalfYearCreditEvaItemList != null && zxCrHalfYearCreditEvaItemList.size() > 0) {
           ZxCrHalfYearCreditEvaItem zxCrHalfYearCreditEvaItem = new ZxCrHalfYearCreditEvaItem();
           zxCrHalfYearCreditEvaItem.setModifyUserInfo(userKey, realName);
           flag = zxCrHalfYearCreditEvaItemMapper.batchDeleteUpdateZxCrHalfYearCreditEvaItem(zxCrHalfYearCreditEvaItemList, zxCrHalfYearCreditEvaItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCrHalfYearCreditEvaItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    //左面树【专业化】
    @Override
	public ResponseEntity getZxCrColCategoryTree(ZxCrColCategory zxCrColCategory) {
		if(zxCrColCategory == null) {
			zxCrColCategory = new ZxCrColCategory();
		}
        JSONArray result = new JSONArray();
		JSONArray categoryNodes = new JSONArray();
        // 获取专业类别
        List<ZxCrColCategory> zxCrColCategoryList = zxCrColCategoryMapper.selectByZxCrColCategoryDear(zxCrColCategory);
        ZxCrColResource zxCrColResource = new ZxCrColResource();
		for (ZxCrColCategory category : zxCrColCategoryList) {
            JSONObject categoryNode = createNode("1", category.getId(), category.getCatName(), category.getCatCode(), category.getParentID());
            JSONArray resNodes = new JSONArray();
            // 获取分类列表
            zxCrColResource.setCategoryID(category.getId());
            List<ZxCrColResource> resources = zxCrColResourceMapper.selectByZxCrColResourceList(zxCrColResource);
            for (ZxCrColResource resource : resources) {
                JSONObject resNode = createNode("2", resource.getId(), resource.getResName(), resource.getResCode(), resource.getCategoryID());
                resNodes.add(resNode);
            }
            categoryNode.set("childrenList", resNodes);
            categoryNodes.add(categoryNode);
        }
        JSONObject root = createNode("1", "11111", "专业化分类", "0", "-1");
        root.set("childrenList", categoryNodes);
        result.add(root);
		return repEntity.ok(result);
	}

	private JSONObject createNode(String type, String id, String name, String code, String parentId) {
        JSONObject node = new JSONObject();
        node.set("id", id);
        node.set("parentId", parentId);
        node.set("name", name);
        node.set("code", code);
        node.set("bsid", "-1".equals(parentId) ? id : parentId + "," + id);
        if ("1".equals(type)) { // 类别
            node.set("catID", id);
            node.set("resID", "");
        } else {
            node.set("catID", "");
            node.set("resID", id);
        }
        return node;
    }

    @Override
    public ResponseEntity getZxCrHalfYearCreditEvaItemInit(ZxCrHalfYearCreditEvaItem zxCrHalfYearCreditEvaItem) {
        if (zxCrHalfYearCreditEvaItem == null) {
            zxCrHalfYearCreditEvaItem = new ZxCrHalfYearCreditEvaItem();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(zxCrHalfYearCreditEvaItem.getPeriod());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        // 按 公司 项目 期次 专业分类 筛选数据
        ZxCrHalfYearCreditEvaItem param = new ZxCrHalfYearCreditEvaItem();
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            param.setComID("");
            param.setProjectID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            param.setComID(zxCrHalfYearCreditEvaItem.getCompanyId());
            param.setProjectID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            param.setProjectID(zxCrHalfYearCreditEvaItem.getProjectId());
        }
        param.setPeriodYear(String.valueOf(year));
        param.setQuartEvalID(month == 0 ? "1" : "2");
        param.setResID(zxCrHalfYearCreditEvaItem.getResID());
        param.setCatID(zxCrHalfYearCreditEvaItem.getCatID());
        // 分页查询
        PageHelper.startPage(zxCrHalfYearCreditEvaItem.getPage(),zxCrHalfYearCreditEvaItem.getLimit());
        // 获取数据
        List<ZxCrHalfYearCreditEvaItem> zxCrHalfYearCreditEvaItemList = zxCrHalfYearCreditEvaItemMapper.selectByZxCrHalfYearCreditEvaItemList(param);
        // 得到分页信息
        PageInfo<ZxCrHalfYearCreditEvaItem> p = new PageInfo<>(zxCrHalfYearCreditEvaItemList);
        return repEntity.okList(zxCrHalfYearCreditEvaItemList, p.getTotal());
    }
    
    @Override
    public List<ZxCrHalfYearCreditEvaItem> getZxCrHalfYearCreditEvaItem(ZxCrHalfYearCreditEvaItem zxCrHalfYearCreditEvaItem) {
        if (zxCrHalfYearCreditEvaItem == null) {
            zxCrHalfYearCreditEvaItem = new ZxCrHalfYearCreditEvaItem();
        }
        // 分页查询
        PageHelper.startPage(zxCrHalfYearCreditEvaItem.getPage(),zxCrHalfYearCreditEvaItem.getLimit());
         // 获取数据
        List<ZxCrHalfYearCreditEvaItem> zxCrHalfYearCreditEvaItemList = zxCrHalfYearCreditEvaItemMapper.selectInit(zxCrHalfYearCreditEvaItem);
        return zxCrHalfYearCreditEvaItemList;
    }
    
    @Override
    public ResponseEntity batchDeleteUpdateAll() {
      
        int flag = 0;
        flag = zxCrHalfYearCreditEvaItemMapper.batchDeleteUpdateAll();
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",flag);
        }
    }
    
}
