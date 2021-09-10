package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import com.apih5.framework.constant.SysConst;
import com.apih5.mybatis.dao.ZxBuProjectTypeItemMapper;
import com.apih5.mybatis.dao.ZxCtContractMapper;
import com.apih5.mybatis.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxBuProjectTypeMapper;
import com.apih5.service.ZxBuProjectTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxBuProjectTypeService")
public class ZxBuProjectTypeServiceImpl implements ZxBuProjectTypeService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxBuProjectTypeMapper zxBuProjectTypeMapper;

    @Autowired(required = true)
    private ZxBuProjectTypeItemMapper zxBuProjectTypeItemMapper;

    @Autowired(required = true)
    private ZxCtContractMapper zxCtContractMapper;

    @Override
    public ResponseEntity getZxBuProjectTypeListByCondition(ZxBuProjectType zxBuProjectType) {
        if (zxBuProjectType == null) {
            zxBuProjectType = new ZxBuProjectType();
        }
        //1.组织机构权限
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxBuProjectType.setCompanyId("");
            zxBuProjectType.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxBuProjectType.setCompanyId(zxBuProjectType.getOrgID());
            zxBuProjectType.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxBuProjectType.setOrgID(zxBuProjectType.getOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxBuProjectType.getPage(),zxBuProjectType.getLimit());
        // 获取数据
        List<ZxBuProjectType> zxBuProjectTypeList = zxBuProjectTypeMapper.selectByZxBuProjectTypeList(zxBuProjectType);
        //查询明细
        for (ZxBuProjectType zxSkWornOut1 : zxBuProjectTypeList) {
            ZxBuProjectTypeItem zxBuProjectTypeItem = new ZxBuProjectTypeItem();
            zxBuProjectTypeItem.setMainID(zxSkWornOut1.getZxBuProjectTypeId());
            List<ZxBuProjectTypeItem> zxBuProjectTypeItems = zxBuProjectTypeItemMapper.selectByZxBuProjectTypeItemList(zxBuProjectTypeItem);
            zxSkWornOut1.setZxBuProjectTypeItemList(zxBuProjectTypeItems);
        }
        // 得到分页信息
        PageInfo<ZxBuProjectType> p = new PageInfo<>(zxBuProjectTypeList);

        return repEntity.okList(zxBuProjectTypeList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxBuProjectTypeDetail(ZxBuProjectType zxBuProjectType) {
        if (zxBuProjectType == null) {
            zxBuProjectType = new ZxBuProjectType();
        }
        // 获取数据
        ZxBuProjectType dbZxBuProjectType = zxBuProjectTypeMapper.selectByPrimaryKey(zxBuProjectType.getZxBuProjectTypeId());
        // 数据存在
        if (dbZxBuProjectType != null) {
            ZxBuProjectTypeItem zxBuProjectTypeItem = new ZxBuProjectTypeItem();
            zxBuProjectTypeItem.setMainID(dbZxBuProjectType.getZxBuProjectTypeId());
            List<ZxBuProjectTypeItem> zxBuProjectTypeItems = zxBuProjectTypeItemMapper.selectByZxBuProjectTypeItemList(zxBuProjectTypeItem);
            dbZxBuProjectType.setZxBuProjectTypeItemList(zxBuProjectTypeItems);
            return repEntity.ok(dbZxBuProjectType);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxBuProjectType(ZxBuProjectType zxBuProjectType) {
        //先判断 项目是否已审核过
        if (zxBuProjectType == null) {
            zxBuProjectType = new ZxBuProjectType();
        }
        if(StrUtil.isNotEmpty(zxBuProjectType.getOrgID())){
            ZxBuProjectType zxBuProjectType2 = new ZxBuProjectType();
            zxBuProjectType2.setOrgID(zxBuProjectType.getOrgID());
            //查询下数据库
            List<ZxBuProjectType> zxBuProjectTypes = zxBuProjectTypeMapper.selectByZxBuProjectTypeList(zxBuProjectType2);
            if (zxBuProjectTypes!=null && zxBuProjectTypes.size()>0){
                ZxBuProjectType zxBuProjectType1 = zxBuProjectTypes.get(0);
                if(StrUtil.equals(zxBuProjectType1.getStatus(),"1")){
                    return repEntity.layerMessage(false,null,"这个项目已有审核的数据,不可在创建");
                }
            }
        }else {
            return repEntity.errorSave();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxBuProjectType.setZxBuProjectTypeId(UuidUtil.generate());
        zxBuProjectType.setCreateUserInfo(userKey, realName);
        //默认审核状态: 0:未审核
        zxBuProjectType.setStatus("0");
        //创建明细
        List<ZxBuProjectTypeItem> zxBuProjectTypeItemList = zxBuProjectType.getZxBuProjectTypeItemList();
        if (zxBuProjectTypeItemList != null && zxBuProjectTypeItemList.size() > 0) {
            for (ZxBuProjectTypeItem zxBuProjectTypeItem : zxBuProjectTypeItemList) {
                zxBuProjectTypeItem.setMainID(zxBuProjectType.getZxBuProjectTypeId());
                zxBuProjectTypeItem.setZxBuProjectTypeItemId((UuidUtil.generate()));
                zxBuProjectTypeItem.setCreateUserInfo(userKey, realName);
                zxBuProjectTypeItemMapper.insert(zxBuProjectTypeItem);
            }
        }
        int flag = zxBuProjectTypeMapper.insert(zxBuProjectType);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxBuProjectType);
        }
    }

    @Override
    public ResponseEntity updateZxBuProjectType(ZxBuProjectType zxBuProjectType) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxBuProjectType dbZxBuProjectType = zxBuProjectTypeMapper.selectByPrimaryKey(zxBuProjectType.getZxBuProjectTypeId());
        if (dbZxBuProjectType != null && StrUtil.isNotEmpty(dbZxBuProjectType.getZxBuProjectTypeId())) {
           // 项目ID
           dbZxBuProjectType.setOrgID(zxBuProjectType.getOrgID());
           // 项目名称
           dbZxBuProjectType.setOrgName(zxBuProjectType.getOrgName());
           // 合同中标价(亿元)
           dbZxBuProjectType.setContractCost(zxBuProjectType.getContractCost());
           // 总工期(月)
           dbZxBuProjectType.setDuration(zxBuProjectType.getDuration());
           // 折算系数
           dbZxBuProjectType.setRate(zxBuProjectType.getRate());
           // 折算总合同金额(亿元)ID
           dbZxBuProjectType.setCodeID1(zxBuProjectType.getCodeID1());
           // 折算总合同金额(亿元)
           dbZxBuProjectType.setCodeNum1(zxBuProjectType.getCodeNum1());
           // 折算平均年产值(亿元)ID
           dbZxBuProjectType.setCodeID2(zxBuProjectType.getCodeID2());
           // 折算平均年产值(亿元)
           dbZxBuProjectType.setCodeNum2(zxBuProjectType.getCodeNum2());
           // 工程类别划分标准扩展ID3
           dbZxBuProjectType.setCodeID3(zxBuProjectType.getCodeID3());
           // 工程类别划分标准扩展3
           dbZxBuProjectType.setCodeNum3(zxBuProjectType.getCodeNum3());
           // 工程类别划分标准扩展ID4
           dbZxBuProjectType.setCodeID4(zxBuProjectType.getCodeID4());
           // 工程类别划分标准扩展4
           dbZxBuProjectType.setCodeNum4(zxBuProjectType.getCodeNum4());
           // 工程类别划分标准扩展ID5
           dbZxBuProjectType.setCodeID5(zxBuProjectType.getCodeID5());
           // 工程类别划分标准扩展5
           dbZxBuProjectType.setCodeNum5(zxBuProjectType.getCodeNum5());
           // 编制日期
           dbZxBuProjectType.setRegTime(zxBuProjectType.getRegTime());
           // 项目工程类别
           dbZxBuProjectType.setProjectTypeName(zxBuProjectType.getProjectTypeName());
           // 工程类型ID
           dbZxBuProjectType.setCheckLevel1ID(zxBuProjectType.getCheckLevel1ID());
           // 工程类型
           dbZxBuProjectType.setCheckLevel1Name(zxBuProjectType.getCheckLevel1Name());
           // 状态
           dbZxBuProjectType.setStatus(zxBuProjectType.getStatus());
           // 最后修改时间
           dbZxBuProjectType.setEditTime(zxBuProjectType.getEditTime());
           // combProp
           dbZxBuProjectType.setCombProp(zxBuProjectType.getCombProp());
           // pp1
           dbZxBuProjectType.setPp1(zxBuProjectType.getPp1());
           // pp2
           dbZxBuProjectType.setPp2(zxBuProjectType.getPp2());
           // pp3
           dbZxBuProjectType.setPp3(zxBuProjectType.getPp3());
           // pp4
           dbZxBuProjectType.setPp4(zxBuProjectType.getPp4());
           // pp5
           dbZxBuProjectType.setPp5(zxBuProjectType.getPp5());
           // pp6
           dbZxBuProjectType.setPp6(zxBuProjectType.getPp6());
           // pp7
           dbZxBuProjectType.setPp7(zxBuProjectType.getPp7());
           // pp8
           dbZxBuProjectType.setPp8(zxBuProjectType.getPp8());
           // pp9
           dbZxBuProjectType.setPp9(zxBuProjectType.getPp9());
           // pp10
           dbZxBuProjectType.setPp10(zxBuProjectType.getPp10());
           // 所属工程类别
           dbZxBuProjectType.setEngiType(zxBuProjectType.getEngiType());
           // 公司id
           dbZxBuProjectType.setCompanyId(zxBuProjectType.getCompanyId());
           // 公司名称
           dbZxBuProjectType.setCompanyName(zxBuProjectType.getCompanyName());
           // 备注
           dbZxBuProjectType.setRemarks(zxBuProjectType.getRemarks());
           // 排序
           dbZxBuProjectType.setSort(zxBuProjectType.getSort());
           // 共通
           dbZxBuProjectType.setModifyUserInfo(userKey, realName);

            //删除在新增
            ZxBuProjectTypeItem zxBuProjectTypeItem = new ZxBuProjectTypeItem();
            zxBuProjectTypeItem.setMainID(zxBuProjectType.getZxBuProjectTypeId());
            List<ZxBuProjectTypeItem> zxBuProjectTypeItems = zxBuProjectTypeItemMapper.selectByZxBuProjectTypeItemList(zxBuProjectTypeItem);
            if (zxBuProjectTypeItems != null && zxBuProjectTypeItems.size() > 0) {
                zxBuProjectTypeItem.setModifyUserInfo(userKey, realName);
                zxBuProjectTypeItemMapper.batchDeleteUpdateZxBuProjectTypeItem(zxBuProjectTypeItems, zxBuProjectTypeItem);
            }
            //明细list
            List<ZxBuProjectTypeItem> zxBuProjectTypeItemList = zxBuProjectType.getZxBuProjectTypeItemList();
            if (zxBuProjectTypeItemList != null && zxBuProjectTypeItemList.size() > 0) {
                for (ZxBuProjectTypeItem zxBuProjectTypeItem1 : zxBuProjectTypeItemList) {
                    zxBuProjectTypeItem1.setZxBuProjectTypeItemId(UuidUtil.generate());
                    zxBuProjectTypeItem1.setMainID(zxBuProjectType.getZxBuProjectTypeId());
                    zxBuProjectTypeItem1.setCreateUserInfo(userKey, realName);
                    zxBuProjectTypeItemMapper.insert(zxBuProjectTypeItem1);
                }
            }
           flag = zxBuProjectTypeMapper.updateByPrimaryKey(dbZxBuProjectType);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxBuProjectType);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxBuProjectType(List<ZxBuProjectType> zxBuProjectTypeList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxBuProjectTypeList != null && zxBuProjectTypeList.size() > 0) {
            for (ZxBuProjectType zxBuProjectType : zxBuProjectTypeList) {
                // 删除明细
                ZxBuProjectTypeItem zxBuProjectTypeItem = new ZxBuProjectTypeItem();
                zxBuProjectTypeItem.setMainID(zxBuProjectType.getZxBuProjectTypeId());
                List<ZxBuProjectTypeItem> zxBuProjectTypeItems = zxBuProjectTypeItemMapper.selectByZxBuProjectTypeItemList(zxBuProjectTypeItem);
                if (zxBuProjectTypeItems != null && zxBuProjectTypeItems.size() > 0) {
                    zxBuProjectTypeItem.setModifyUserInfo(userKey, realName);
                    zxBuProjectTypeItemMapper.batchDeleteUpdateZxBuProjectTypeItem(zxBuProjectTypeItems, zxBuProjectTypeItem);
                }
            }
           ZxBuProjectType zxBuProjectType = new ZxBuProjectType();
           zxBuProjectType.setModifyUserInfo(userKey, realName);
           flag = zxBuProjectTypeMapper.batchDeleteUpdateZxBuProjectType(zxBuProjectTypeList, zxBuProjectType);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxBuProjectTypeList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓


    @Override
    public synchronized ResponseEntity checkZxBuProjectType(ZxBuProjectType zxBuProjectType) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxBuProjectType dbzxBuProjectType = zxBuProjectTypeMapper.selectByPrimaryKey(zxBuProjectType.getZxBuProjectTypeId());
        if(StrUtil.equals(dbzxBuProjectType.getStatus(), "1")) {
            return repEntity.layerMessage("no", "包含已经审核的，请重新选择！");
        }
        dbzxBuProjectType.setStatus("1");
        dbzxBuProjectType.setModifyUserInfo(userKey, realName);
        flag = zxBuProjectTypeMapper.checkZxBuProjectType(dbzxBuProjectType);
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",dbzxBuProjectType);
        }
    }

    //todo:可能需要删除
    @Override
    public ResponseEntity getZxBuProjectTypeCheckOver(ZxBuProjectType zxBuProjectType) {
        //获取所有审核后的项目工程
        if (zxBuProjectType == null) {
            zxBuProjectType = new ZxBuProjectType();
        }
        //权限控制
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxBuProjectType.setCompanyId("");
            zxBuProjectType.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxBuProjectType.setCompanyId(zxBuProjectType.getOrgID());
            zxBuProjectType.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxBuProjectType.setOrgID(zxBuProjectType.getOrgID());
        }
        zxBuProjectType.setStatus("1");
        // 获取数据
        List<ZxBuProjectType> zxBuProjectTypeList = zxBuProjectTypeMapper.selectByZxBuProjectTypeList(zxBuProjectType);

        return repEntity.ok(zxBuProjectTypeList);
    }

    @Override
    public ResponseEntity getZxBuProjectTypeProjectList(ZxBuProjectType zxBuProjectType) {
        if (zxBuProjectType == null) {
            zxBuProjectType = new ZxBuProjectType();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxBuProjectType.setCompanyId("");
            zxBuProjectType.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxBuProjectType.setCompanyId(zxBuProjectType.getOrgID());
            zxBuProjectType.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxBuProjectType.setOrgID(zxBuProjectType.getOrgID());
        }
        ZxCtContract zxCtContract = new ZxCtContract();
        zxCtContract.setContrStatus("1");
        // 获取数据
        List<ZxCtContract> zxCtContractList = zxCtContractMapper.selectByZxCtContractList(zxCtContract);
        //查找项目工程类划分审核后的数据
//        ZxBuProjectType zxBuProjectType1 = new ZxBuProjectType();
//        zxBuProjectType1.setStatus("1");
//        List<ZxBuProjectType> zxBuProjectTypes = zxBuProjectTypeMapper.selectByZxBuProjectTypeList(zxBuProjectType1);
//        Map<String, ZxBuProjectType> map = zxBuProjectTypes.stream()
//                .collect(Collectors.toMap(ZxBuProjectType::getOrgID, ZxBuProjectType -> ZxBuProjectType));
//        zxCtContractList.stream().collect(Collectors.toList()).forEach(o->{
//            if(map.get(o.getOrgID()) != null){
//                zxCtContractList.remove(o);
//            }
//        });
        //如果项目id是空的话,那么就是公司账号
        List<ZxCtContract> zxCtContractStream;
        if(StrUtil.isEmpty(zxBuProjectType.getCompanyId()) && StrUtil.isEmpty(zxBuProjectType.getOrgID())){
            zxCtContractStream = zxCtContractList;
        }else if(StrUtil.isEmpty(zxBuProjectType.getOrgID())){
            String companyId = zxBuProjectType.getCompanyId();
            //删选出来公司id
            zxCtContractStream = zxCtContractList.stream().filter(o -> StrUtil.equals(o.getCompanyId(), companyId)).collect(Collectors.toList());
        }else {
            //项目账号,筛选出来那个账号
            String orgID = zxBuProjectType.getOrgID();
            zxCtContractStream = zxCtContractList.stream().filter(o -> StrUtil.equals(o.getOrgID(), orgID)).collect(Collectors.toList());
        }
        return repEntity.ok(zxCtContractStream);
    }


}
