package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONArray;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.ConvertUtil;
import com.apih5.mybatis.pojo.ZxCtWorkBook;
import com.apih5.mybatis.pojo.ZxCtWorks;
import com.apih5.mybatis.pojo.ZxSkResourceMaterials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxBuProjectTypeCheckMapper;
import com.apih5.mybatis.pojo.ZxBuProjectTypeCheck;
import com.apih5.service.ZxBuProjectTypeCheckService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxBuProjectTypeCheckService")
public class ZxBuProjectTypeCheckServiceImpl implements ZxBuProjectTypeCheckService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxBuProjectTypeCheckMapper zxBuProjectTypeCheckMapper;

    @Override
    public ResponseEntity getZxBuProjectTypeCheckListByCondition(ZxBuProjectTypeCheck zxBuProjectTypeCheck) {
        if (zxBuProjectTypeCheck == null) {
            zxBuProjectTypeCheck = new ZxBuProjectTypeCheck();
        }
        // 分页查询
        PageHelper.startPage(zxBuProjectTypeCheck.getPage(),zxBuProjectTypeCheck.getLimit());
        // 获取数据
        List<ZxBuProjectTypeCheck> zxBuProjectTypeCheckList = zxBuProjectTypeCheckMapper.selectByZxBuProjectTypeCheckList(zxBuProjectTypeCheck);
        // 得到分页信息
        PageInfo<ZxBuProjectTypeCheck> p = new PageInfo<>(zxBuProjectTypeCheckList);

        return repEntity.okList(zxBuProjectTypeCheckList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxBuProjectTypeCheckDetail(ZxBuProjectTypeCheck zxBuProjectTypeCheck) {
        if (zxBuProjectTypeCheck == null) {
            zxBuProjectTypeCheck = new ZxBuProjectTypeCheck();
        }
        // 获取数据
        ZxBuProjectTypeCheck dbZxBuProjectTypeCheck = zxBuProjectTypeCheckMapper.selectByPrimaryKey(zxBuProjectTypeCheck.getZxBuProjectTypeCheckId());
        // 数据存在
        if (dbZxBuProjectTypeCheck != null) {
            return repEntity.ok(dbZxBuProjectTypeCheck);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxBuProjectTypeCheck(ZxBuProjectTypeCheck zxBuProjectTypeCheck) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxBuProjectTypeCheck.setCreateUserInfo(userKey, realName);
        if (StrUtil.isEmpty(zxBuProjectTypeCheck.getParentID()) && StrUtil.isEmpty(zxBuProjectTypeCheck.getZxBuProjectTypeCheckId())) {
//            return repEntity.ok(new ArrayList<>());
            return repEntity.layerMessage("no", "id、parentID不能同时为空！");
        }
        // treeNode处理
        // 有父id
        // 同级新增
        if (StrUtil.isNotEmpty(zxBuProjectTypeCheck.getParentID())) {
            String treeNode = zxBuProjectTypeCheck.getTreeNode();
            BigDecimal intTreeNode = new BigDecimal(treeNode);
            zxBuProjectTypeCheck.setTreeNode(CalcUtils.calcAdd(intTreeNode,(new BigDecimal(1))) + "");
        } else if (StrUtil.isNotEmpty(zxBuProjectTypeCheck.getZxBuProjectTypeCheckId())) { // 子级新增
            zxBuProjectTypeCheck.setParentID(zxBuProjectTypeCheck.getZxBuProjectTypeCheckId());
            ZxBuProjectTypeCheck selectzxBuProjectTypeCheck = new ZxBuProjectTypeCheck();
            selectzxBuProjectTypeCheck.setOrgID(zxBuProjectTypeCheck.getOrgID());
            //添加父级id
            selectzxBuProjectTypeCheck.setParentID(zxBuProjectTypeCheck.getZxBuProjectTypeCheckId());
            List<ZxBuProjectTypeCheck> zxBuProjectTypeCheckList = zxBuProjectTypeCheckMapper.selectByZxBuProjectTypeCheckList(selectzxBuProjectTypeCheck);
            if (zxBuProjectTypeCheckList == null || zxBuProjectTypeCheckList.size() == 0) {
                //查询treeNode这些
                ZxBuProjectTypeCheck zxBuProjectTypeCheck1 = zxBuProjectTypeCheckMapper.selectByPrimaryKey(zxBuProjectTypeCheck.getZxBuProjectTypeCheckId());
                //如果数据库没有他就是第一个值
                zxBuProjectTypeCheck.setTreeNode(zxBuProjectTypeCheck1.getTreeNode() + "1000");
            } else {
                //如果数据库有,就取最后一个值
                String treeNode = zxBuProjectTypeCheckList.get(zxBuProjectTypeCheckList.size() - 1).getTreeNode();
                BigDecimal intTreeNode = new BigDecimal(treeNode);
                zxBuProjectTypeCheck.setTreeNode(CalcUtils.calcAdd(intTreeNode,(new BigDecimal(1))) + "");
            }
        }
        zxBuProjectTypeCheck.setZxBuProjectTypeCheckId(UuidUtil.generate());
        //添加种类
        zxBuProjectTypeCheck.setCheckType(2);
        //在这里添加是叶子节点
        zxBuProjectTypeCheck.setIsLeaf(1);
        int flag = zxBuProjectTypeCheckMapper.insert(zxBuProjectTypeCheck);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            // 修改父节点IsLeaf为0
            ZxBuProjectTypeCheck updateZxBuProjectTypeCheck = new ZxBuProjectTypeCheck();
            updateZxBuProjectTypeCheck.setZxBuProjectTypeCheckId(zxBuProjectTypeCheck.getParentID());
            //先查
            ZxBuProjectTypeCheck zxBuProjectTypeCheck1 = zxBuProjectTypeCheckMapper.selectByPrimaryKey(updateZxBuProjectTypeCheck.getZxBuProjectTypeCheckId());
            zxBuProjectTypeCheck1.setIsLeaf(0);
            zxBuProjectTypeCheckMapper.updateByPrimaryKey(zxBuProjectTypeCheck1);
            return repEntity.ok("sys.data.sava", updateZxBuProjectTypeCheck);
        }
    }

    @Override
    public ResponseEntity updateZxBuProjectTypeCheck(ZxBuProjectTypeCheck zxBuProjectTypeCheck) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxBuProjectTypeCheck dbZxBuProjectTypeCheck = zxBuProjectTypeCheckMapper.selectByPrimaryKey(zxBuProjectTypeCheck.getZxBuProjectTypeCheckId());
        if (dbZxBuProjectTypeCheck != null && StrUtil.isNotEmpty(dbZxBuProjectTypeCheck.getZxBuProjectTypeCheckId())) {
           // 父节点
           dbZxBuProjectTypeCheck.setParentID(zxBuProjectTypeCheck.getParentID());
           // 树节点
           dbZxBuProjectTypeCheck.setTreeNode(zxBuProjectTypeCheck.getTreeNode());
           // 机构ID
           dbZxBuProjectTypeCheck.setOrgID(zxBuProjectTypeCheck.getOrgID());
           // 类型
           dbZxBuProjectTypeCheck.setCheckType(2);
           // 编码
           dbZxBuProjectTypeCheck.setCheckNo(zxBuProjectTypeCheck.getCheckNo());
           // 名称
           dbZxBuProjectTypeCheck.setCheckName(zxBuProjectTypeCheck.getCheckName());
           // 关系一
           dbZxBuProjectTypeCheck.setOperate1(zxBuProjectTypeCheck.getOperate1());
           // 参数一
           dbZxBuProjectTypeCheck.setRate1(zxBuProjectTypeCheck.getRate1());
           // 关系二
           dbZxBuProjectTypeCheck.setOperate2(zxBuProjectTypeCheck.getOperate2());
           // 参数二
           dbZxBuProjectTypeCheck.setRate2(zxBuProjectTypeCheck.getRate2());
           // 参数显示
           dbZxBuProjectTypeCheck.setDispRate(zxBuProjectTypeCheck.getDispRate());
           // 是否叶子节点
           dbZxBuProjectTypeCheck.setIsLeaf(zxBuProjectTypeCheck.getIsLeaf());
           // 最后编辑时间
           dbZxBuProjectTypeCheck.setEditTime(zxBuProjectTypeCheck.getEditTime());
           // combProp
           dbZxBuProjectTypeCheck.setCombProp(zxBuProjectTypeCheck.getCombProp());
           // pp1
           dbZxBuProjectTypeCheck.setPp1(zxBuProjectTypeCheck.getPp1());
           // pp2
           dbZxBuProjectTypeCheck.setPp2(zxBuProjectTypeCheck.getPp2());
           // pp3
           dbZxBuProjectTypeCheck.setPp3(zxBuProjectTypeCheck.getPp3());
           // pp4
           dbZxBuProjectTypeCheck.setPp4(zxBuProjectTypeCheck.getPp4());
           // pp5
           dbZxBuProjectTypeCheck.setPp5(zxBuProjectTypeCheck.getPp5());
           // pp6
           dbZxBuProjectTypeCheck.setPp6(zxBuProjectTypeCheck.getPp6());
           // pp7
           dbZxBuProjectTypeCheck.setPp7(zxBuProjectTypeCheck.getPp7());
           // pp8
           dbZxBuProjectTypeCheck.setPp8(zxBuProjectTypeCheck.getPp8());
           // pp9
           dbZxBuProjectTypeCheck.setPp9(zxBuProjectTypeCheck.getPp9());
           // pp10
           dbZxBuProjectTypeCheck.setPp10(zxBuProjectTypeCheck.getPp10());
           // 备注
           dbZxBuProjectTypeCheck.setRemarks(zxBuProjectTypeCheck.getRemarks());
           // 排序
           dbZxBuProjectTypeCheck.setSort(zxBuProjectTypeCheck.getSort());
           // 共通
           dbZxBuProjectTypeCheck.setModifyUserInfo(userKey, realName);
           flag = zxBuProjectTypeCheckMapper.updateByPrimaryKey(dbZxBuProjectTypeCheck);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxBuProjectTypeCheck);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxBuProjectTypeCheck(List<ZxBuProjectTypeCheck> zxBuProjectTypeCheckList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxBuProjectTypeCheckList != null && zxBuProjectTypeCheckList.size() > 0) {
            for (ZxBuProjectTypeCheck zxBuProjectTypeCheck : zxBuProjectTypeCheckList) {
                ZxBuProjectTypeCheck zxBuProjectTypeCheck1 = new ZxBuProjectTypeCheck();
                zxBuProjectTypeCheck1.setParentID(zxBuProjectTypeCheck.getZxBuProjectTypeCheckId());
                List<ZxBuProjectTypeCheck> zxBuProjectTypeCheckList1 = zxBuProjectTypeCheckMapper.selectByZxBuProjectTypeCheckList(zxBuProjectTypeCheck1);
                //子节点
                if(zxBuProjectTypeCheckList1 != null && zxBuProjectTypeCheckList1.size() >0) {
                    return repEntity.layerMessage("no", "请先删除子节点");
                }
            }
           ZxBuProjectTypeCheck zxBuProjectTypeCheck = new ZxBuProjectTypeCheck();
           zxBuProjectTypeCheck.setModifyUserInfo(userKey, realName);
           flag = zxBuProjectTypeCheckMapper.batchDeleteUpdateZxBuProjectTypeCheck(zxBuProjectTypeCheckList, zxBuProjectTypeCheck);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            for (ZxBuProjectTypeCheck zxBuProjectTypeCheck : zxBuProjectTypeCheckList) {
                // 查询是否有子节点
                ZxBuProjectTypeCheck selectZxBuProjectTypeCheck = new ZxBuProjectTypeCheck();
                selectZxBuProjectTypeCheck.setParentID(zxBuProjectTypeCheck.getParentID());
                List<ZxBuProjectTypeCheck> ProjectTypeCheckList = zxBuProjectTypeCheckMapper.selectByZxBuProjectTypeCheckList(selectZxBuProjectTypeCheck);
                if (ProjectTypeCheckList == null || ProjectTypeCheckList.size() == 0) {
                    // 无根节点修改 IsLeaf为1
                    ZxBuProjectTypeCheck updateZxBuProjectTypeCheck = new ZxBuProjectTypeCheck();
                    updateZxBuProjectTypeCheck.setIsLeaf(1);
                    updateZxBuProjectTypeCheck.setZxBuProjectTypeCheckId(zxBuProjectTypeCheck.getParentID());
                    updateZxBuProjectTypeCheck.setModifyUserInfo(userKey, realName);
                    flag = zxBuProjectTypeCheckMapper.updateZxBuProjectTypeCheck(updateZxBuProjectTypeCheck);
                    if (flag == 0) {
                        return repEntity.errorSave();
                    }
                }
            }
            return repEntity.ok("sys.data.delete",zxBuProjectTypeCheckList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @Override
    public ResponseEntity getZxBuProjectTypeCheckTree(ZxBuProjectTypeCheck zxBuProjectTypeCheck) {
        if (zxBuProjectTypeCheck == null) {
            zxBuProjectTypeCheck = new ZxBuProjectTypeCheck();
        }
        if (StrUtil.isEmpty(zxBuProjectTypeCheck.getOrgID())) {
            return repEntity.ok(new ArrayList<>());
//            return repEntity.layerMessage("no", "项目ID不能为空！");
        }
        // 获取数据
        List<ZxBuProjectTypeCheck> zxBuProjectTypeCheckList = zxBuProjectTypeCheckMapper.selectByZxBuProjectTypeCheckList(zxBuProjectTypeCheck);
        JSONArray jsonArray = ConvertUtil.listToTree(new JSONArray(zxBuProjectTypeCheckList), "zxBuProjectTypeCheckId", "parentID", "checkName", "checkNo");
        return repEntity.ok(jsonArray);
    }

    @Override
    public ResponseEntity getZxBuProjectTypeCheckTreeList(ZxBuProjectTypeCheck zxBuProjectTypeCheck) {
        if (zxBuProjectTypeCheck == null) {
            zxBuProjectTypeCheck = new ZxBuProjectTypeCheck();
        }
        if (StrUtil.isEmpty(zxBuProjectTypeCheck.getParentID())) {
            return repEntity.ok(new ArrayList<>());
//            return repEntity.layerMessage("no", "父ID不能为空！");
        }
        if (StrUtil.isEmpty(zxBuProjectTypeCheck.getOrgID())) {
            return repEntity.ok(new ArrayList<>());
//            return repEntity.layerMessage("no", "项目不能为空！");
        }
        // 分页查询
        PageHelper.startPage(zxBuProjectTypeCheck.getPage(),zxBuProjectTypeCheck.getLimit());
        // 获取数据
        List<ZxBuProjectTypeCheck> zxBuProjectTypeCheckList = zxBuProjectTypeCheckMapper.selectByZxBuProjectTypeCheckList(zxBuProjectTypeCheck);
        PageInfo<ZxBuProjectTypeCheck> p = new PageInfo<>(zxBuProjectTypeCheckList);
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (ZxBuProjectTypeCheck zxBuProjectTypeCheck1 : zxBuProjectTypeCheckList) {
            Map<String, Object> map = BeanUtil.beanToMap(zxBuProjectTypeCheck1);
            map.remove("children");
            mapList.add(map);
        }
        return repEntity.okList(mapList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxBuProjectTypeCheckProjectType(ZxBuProjectTypeCheck zxBuProjectTypeCheck) {
        List<ZxBuProjectTypeCheck> zxBuProjectTypeCheckList = zxBuProjectTypeCheckMapper.getZxBuProjectTypeCheckProjectType(zxBuProjectTypeCheck);
        ZxBuProjectTypeCheck zxBuProjectTypeCheck1 = zxBuProjectTypeCheckList.get(0);
        return repEntity.ok(zxBuProjectTypeCheck1);
    }
}
