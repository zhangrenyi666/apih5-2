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
        // ????????????
        PageHelper.startPage(zxCrHalfYearCreditEvaItem.getPage(),zxCrHalfYearCreditEvaItem.getLimit());
        // ????????????
        List<ZxCrHalfYearCreditEvaItem> zxCrHalfYearCreditEvaItemList = zxCrHalfYearCreditEvaItemMapper.selectByZxCrHalfYearCreditEvaItemList(zxCrHalfYearCreditEvaItem);
        // ??????????????????
        PageInfo<ZxCrHalfYearCreditEvaItem> p = new PageInfo<>(zxCrHalfYearCreditEvaItemList);

        return repEntity.okList(zxCrHalfYearCreditEvaItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCrHalfYearCreditEvaItemDetail(ZxCrHalfYearCreditEvaItem zxCrHalfYearCreditEvaItem) {
        if (zxCrHalfYearCreditEvaItem == null) {
            zxCrHalfYearCreditEvaItem = new ZxCrHalfYearCreditEvaItem();
        }
        // ????????????
        ZxCrHalfYearCreditEvaItem dbZxCrHalfYearCreditEvaItem = zxCrHalfYearCreditEvaItemMapper.selectByPrimaryKey(zxCrHalfYearCreditEvaItem.getZxCrHalfYearCreditEvaItemId());
        // ????????????
        if (dbZxCrHalfYearCreditEvaItem != null) {
            return repEntity.ok(dbZxCrHalfYearCreditEvaItem);
        } else {
            return repEntity.layerMessage("no", "????????????");
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
           // ??????ID
           dbZxCrHalfYearCreditEvaItem.setMasterID(zxCrHalfYearCreditEvaItem.getMasterID());
           // ????????????id
           dbZxCrHalfYearCreditEvaItem.setCustomerId(zxCrHalfYearCreditEvaItem.getCustomerId());
           // ??????????????????
           dbZxCrHalfYearCreditEvaItem.setOrgCertificate(zxCrHalfYearCreditEvaItem.getOrgCertificate());
           // ??????????????????
           dbZxCrHalfYearCreditEvaItem.setCustomerName(zxCrHalfYearCreditEvaItem.getCustomerName());
           // ?????????????????????
           dbZxCrHalfYearCreditEvaItem.setChargeMan(zxCrHalfYearCreditEvaItem.getChargeMan());
           // ??????????????????ID
           dbZxCrHalfYearCreditEvaItem.setProjectID(zxCrHalfYearCreditEvaItem.getProjectID());
           // ??????????????????
           dbZxCrHalfYearCreditEvaItem.setProjectName(zxCrHalfYearCreditEvaItem.getProjectName());
           // ????????????
           dbZxCrHalfYearCreditEvaItem.setInDate(zxCrHalfYearCreditEvaItem.getInDate());
           // ????????????
           dbZxCrHalfYearCreditEvaItem.setOutDate(zxCrHalfYearCreditEvaItem.getOutDate());
           // ?????????????????????????????????
           dbZxCrHalfYearCreditEvaItem.setContractAmt(zxCrHalfYearCreditEvaItem.getContractAmt());
           // ????????????????????????
           dbZxCrHalfYearCreditEvaItem.setProjectNum(zxCrHalfYearCreditEvaItem.getProjectNum());
           // ????????????
           dbZxCrHalfYearCreditEvaItem.setCatName(zxCrHalfYearCreditEvaItem.getCatName());
           // ????????????ID
           dbZxCrHalfYearCreditEvaItem.setCatID(zxCrHalfYearCreditEvaItem.getCatID());
           // ??????????????????
           dbZxCrHalfYearCreditEvaItem.setCatCode(zxCrHalfYearCreditEvaItem.getCatCode());
           // ????????????
           dbZxCrHalfYearCreditEvaItem.setCheckNum(zxCrHalfYearCreditEvaItem.getCheckNum());
           // ???????????????????????????????????????
           dbZxCrHalfYearCreditEvaItem.setFirstSoce(zxCrHalfYearCreditEvaItem.getFirstSoce());
           // ????????????(?????????)
           dbZxCrHalfYearCreditEvaItem.setFirstLevel(zxCrHalfYearCreditEvaItem.getFirstLevel());
           // ???????????????????????????????????????
           dbZxCrHalfYearCreditEvaItem.setSecondScore(zxCrHalfYearCreditEvaItem.getSecondScore());
           // ????????????(?????????)
           dbZxCrHalfYearCreditEvaItem.setSecondLevel(zxCrHalfYearCreditEvaItem.getSecondLevel());
           // ??????????????????D?????????
           dbZxCrHalfYearCreditEvaItem.setdLevel(zxCrHalfYearCreditEvaItem.getdLevel());
           // ????????????????????????
           dbZxCrHalfYearCreditEvaItem.setLastScore(zxCrHalfYearCreditEvaItem.getLastScore());
           // ??????????????????
           dbZxCrHalfYearCreditEvaItem.setLastLevel(zxCrHalfYearCreditEvaItem.getLastLevel());
           // editTime
           dbZxCrHalfYearCreditEvaItem.setEditTime(zxCrHalfYearCreditEvaItem.getEditTime());
           // comID
           dbZxCrHalfYearCreditEvaItem.setComID(zxCrHalfYearCreditEvaItem.getComID());
           // comName
           dbZxCrHalfYearCreditEvaItem.setComName(zxCrHalfYearCreditEvaItem.getComName());
           // comOrders
           dbZxCrHalfYearCreditEvaItem.setComOrders(zxCrHalfYearCreditEvaItem.getComOrders());
           // ??????????????????ID
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
           // ??????
           dbZxCrHalfYearCreditEvaItem.setSort(zxCrHalfYearCreditEvaItem.getSort());
           // ??????
           dbZxCrHalfYearCreditEvaItem.setModifyUserInfo(userKey, realName);
           flag = zxCrHalfYearCreditEvaItemMapper.updateByPrimaryKey(dbZxCrHalfYearCreditEvaItem);
        }
        // ??????
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
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCrHalfYearCreditEvaItemList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
    //????????????????????????
    @Override
	public ResponseEntity getZxCrColCategoryTree(ZxCrColCategory zxCrColCategory) {
		if(zxCrColCategory == null) {
			zxCrColCategory = new ZxCrColCategory();
		}
        JSONArray result = new JSONArray();
		JSONArray categoryNodes = new JSONArray();
        // ??????????????????
        List<ZxCrColCategory> zxCrColCategoryList = zxCrColCategoryMapper.selectByZxCrColCategoryDear(zxCrColCategory);
        ZxCrColResource zxCrColResource = new ZxCrColResource();
		for (ZxCrColCategory category : zxCrColCategoryList) {
            JSONObject categoryNode = createNode("1", category.getId(), category.getCatName(), category.getCatCode(), category.getParentID());
            JSONArray resNodes = new JSONArray();
            // ??????????????????
            zxCrColResource.setCategoryID(category.getId());
            List<ZxCrColResource> resources = zxCrColResourceMapper.selectByZxCrColResourceList(zxCrColResource);
            for (ZxCrColResource resource : resources) {
                JSONObject resNode = createNode("2", resource.getId(), resource.getResName(), resource.getResCode(), resource.getCategoryID());
                resNodes.add(resNode);
            }
            categoryNode.set("childrenList", resNodes);
            categoryNodes.add(categoryNode);
        }
        JSONObject root = createNode("1", "11111", "???????????????", "0", "-1");
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
        if ("1".equals(type)) { // ??????
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
        // ??? ?????? ?????? ?????? ???????????? ????????????
        ZxCrHalfYearCreditEvaItem param = new ZxCrHalfYearCreditEvaItem();
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            param.setComID("");
            param.setProjectID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            param.setComID(zxCrHalfYearCreditEvaItem.getCompanyId());
            param.setProjectID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            param.setProjectID(zxCrHalfYearCreditEvaItem.getProjectId());
        }
        param.setPeriodYear(String.valueOf(year));
        param.setQuartEvalID(month == 0 ? "1" : "2");
        param.setResID(zxCrHalfYearCreditEvaItem.getResID());
        param.setCatID(zxCrHalfYearCreditEvaItem.getCatID());
        // ????????????
        PageHelper.startPage(zxCrHalfYearCreditEvaItem.getPage(),zxCrHalfYearCreditEvaItem.getLimit());
        // ????????????
        List<ZxCrHalfYearCreditEvaItem> zxCrHalfYearCreditEvaItemList = zxCrHalfYearCreditEvaItemMapper.selectByZxCrHalfYearCreditEvaItemList(param);
        // ??????????????????
        PageInfo<ZxCrHalfYearCreditEvaItem> p = new PageInfo<>(zxCrHalfYearCreditEvaItemList);
        return repEntity.okList(zxCrHalfYearCreditEvaItemList, p.getTotal());
    }
    
    @Override
    public List<ZxCrHalfYearCreditEvaItem> getZxCrHalfYearCreditEvaItem(ZxCrHalfYearCreditEvaItem zxCrHalfYearCreditEvaItem) {
        if (zxCrHalfYearCreditEvaItem == null) {
            zxCrHalfYearCreditEvaItem = new ZxCrHalfYearCreditEvaItem();
        }
        // ????????????
        PageHelper.startPage(zxCrHalfYearCreditEvaItem.getPage(),zxCrHalfYearCreditEvaItem.getLimit());
         // ????????????
        List<ZxCrHalfYearCreditEvaItem> zxCrHalfYearCreditEvaItemList = zxCrHalfYearCreditEvaItemMapper.selectInit(zxCrHalfYearCreditEvaItem);
        return zxCrHalfYearCreditEvaItemList;
    }
    
    @Override
    public ResponseEntity batchDeleteUpdateAll() {
      
        int flag = 0;
        flag = zxCrHalfYearCreditEvaItemMapper.batchDeleteUpdateAll();
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",flag);
        }
    }
    
}
