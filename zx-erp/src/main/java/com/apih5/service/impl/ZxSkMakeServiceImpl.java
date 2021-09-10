package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.framework.constant.SysConst;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.dao.ZxSkMakeItemMapper;
import com.apih5.mybatis.dao.ZxSkStockMapper;
import com.apih5.mybatis.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkMakeMapper;
import com.apih5.mybatis.pojo.ZxSkMake;
import com.apih5.service.ZxSkMakeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkMakeService")
public class ZxSkMakeServiceImpl implements ZxSkMakeService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkMakeMapper zxSkMakeMapper;

    @Autowired(required = true)
    private ZxSkMakeItemMapper zxSkMakeItemMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    //仓库
    @Autowired(required = true)
    private ZxSkStockMapper zxSkStockMapper;

    @Override
    public ResponseEntity getZxSkMakeListByCondition(ZxSkMake zxSkMake) {
        if (zxSkMake == null) {
            zxSkMake = new ZxSkMake();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSkMake.setCompanyId("");
            zxSkMake.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSkMake.setCompanyId(zxSkMake.getProjectId());
            zxSkMake.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSkMake.setProjectId(zxSkMake.getProjectId());
        }
        // 分页查询
        PageHelper.startPage(zxSkMake.getPage(),zxSkMake.getLimit());
        // 获取数据
        List<ZxSkMake> zxSkMakeList = zxSkMakeMapper.selectByZxSkMakeList(zxSkMake);
        //查询明细
        for (ZxSkMake zxSkMake1 : zxSkMakeList) {
            ZxSkMakeItem zxSkMakeItem = new ZxSkMakeItem();
            zxSkMakeItem.setMakeInvID(zxSkMake1.getId());
            List<ZxSkMakeItem> zxSkMakeItems = zxSkMakeItemMapper.selectByZxSkMakeItemList(zxSkMakeItem);
            zxSkMake1.setZxSkMakeItemList(zxSkMakeItems);
        }
        //附件
        for (ZxSkMake zxSkMake1 : zxSkMakeList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSkMake1.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSkMake1.setFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxSkMake> p = new PageInfo<>(zxSkMakeList);

        return repEntity.okList(zxSkMakeList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkMakeDetail(ZxSkMake zxSkMake) {
        if (zxSkMake == null) {
            zxSkMake = new ZxSkMake();
        }
        // 获取数据
        ZxSkMake dbZxSkMake = zxSkMakeMapper.selectByPrimaryKey(zxSkMake.getId());
        // 数据存在
        if (dbZxSkMake != null) {
            ZxSkMakeItem zxSkMakeItem = new ZxSkMakeItem();
            zxSkMakeItem.setMakeInvID(dbZxSkMake.getId());
            List<ZxSkMakeItem> zxSkMakeItems = zxSkMakeItemMapper.selectByZxSkMakeItemList(zxSkMakeItem);
            dbZxSkMake.setZxSkMakeItemList(zxSkMakeItems);

            //附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxSkMake.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            dbZxSkMake.setFileList(zxErpFiles);
            return repEntity.ok(dbZxSkMake);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkMake(ZxSkMake zxSkMake) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkMake.setId(UuidUtil.generate());
        zxSkMake.setCreateUserInfo(userKey, realName);
        //默认审核状态: 0:未审核
        zxSkMake.setStatus("0");

        //创建明细
        List<ZxSkMakeItem> zxSkMakeItemList = zxSkMake.getZxSkMakeItemList();
        if(zxSkMakeItemList != null && zxSkMakeItemList.size()>0) {
            for (ZxSkMakeItem zxSkMakeItem : zxSkMakeItemList) {
                zxSkMakeItem.setMakeInvID(zxSkMake.getId());
                zxSkMakeItem.setId((UuidUtil.generate()));
                zxSkMakeItem.setCreateUserInfo(userKey, realName);
                zxSkMakeItemMapper.insert(zxSkMakeItem);
            }
        }
        //添加附件
        List<ZxErpFile> fileList = zxSkMake.getFileList();
        if(fileList != null && fileList.size()>0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSkMake.getId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }

        int flag = zxSkMakeMapper.insert(zxSkMake);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkMake);
        }
    }

    @Override
    public ResponseEntity updateZxSkMake(ZxSkMake zxSkMake) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkMake dbZxSkMake = zxSkMakeMapper.selectByPrimaryKey(zxSkMake.getId());
        if (dbZxSkMake != null && StrUtil.isNotEmpty(dbZxSkMake.getId())) {
           // 仓库机构ID
           dbZxSkMake.setWhOrgID(zxSkMake.getWhOrgID());
           // 盘点日期
           dbZxSkMake.setMakeInvDate(zxSkMake.getMakeInvDate());
           // 经手人
           dbZxSkMake.setHandler(zxSkMake.getHandler());
           // 负责人
           dbZxSkMake.setPrincipal(zxSkMake.getPrincipal());
           // 状态
           dbZxSkMake.setStatus(zxSkMake.getStatus());
           // 盘点仓库
           dbZxSkMake.setWarehouseName(zxSkMake.getWarehouseName());
           // 盘点状态
           dbZxSkMake.setPlmm("4");
           // 项目id
           dbZxSkMake.setProjectId(zxSkMake.getProjectId());
           // 项目名称
           dbZxSkMake.setProjectName(zxSkMake.getProjectName());
           // 公司id
           dbZxSkMake.setCompanyId(zxSkMake.getCompanyId());
           // 公司名称
           dbZxSkMake.setCompanyName(zxSkMake.getCompanyName());
           // 备注
           dbZxSkMake.setRemarks(zxSkMake.getRemarks());
           // 排序
           dbZxSkMake.setSort(zxSkMake.getSort());

            //删除在新增
            ZxSkMakeItem zxSkMakeItem = new ZxSkMakeItem();
            zxSkMakeItem.setMakeInvID(zxSkMake.getId());
            List<ZxSkMakeItem> zxSkMakeItems = zxSkMakeItemMapper.selectByZxSkMakeItemList(zxSkMakeItem);
            if(zxSkMakeItems != null && zxSkMakeItems.size()>0) {
                zxSkMakeItem.setModifyUserInfo(userKey, realName);
                zxSkMakeItemMapper.batchDeleteUpdateZxSkMakeItem(zxSkMakeItems, zxSkMakeItem);
            }
            //明细list
            List<ZxSkMakeItem> zxSkMakeItemList = zxSkMake.getZxSkMakeItemList();
            if(zxSkMakeItemList != null && zxSkMakeItemList.size()>0) {
                for(ZxSkMakeItem zxSkMakeItem1 : zxSkMakeItemList) {
                    zxSkMakeItem1.setId(UuidUtil.generate());
                    zxSkMakeItem1.setMakeInvID(zxSkMake.getId());
                    zxSkMakeItem1.setCreateUserInfo(userKey, realName);
                    zxSkMakeItemMapper.insert(zxSkMakeItem1);
                }
            }

            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSkMake.getId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //明细list
            List<ZxErpFile> fileList = zxSkMake.getFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxSkMake.getId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
           // 共通
           dbZxSkMake.setModifyUserInfo(userKey, realName);
           flag = zxSkMakeMapper.updateByPrimaryKey(dbZxSkMake);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkMake);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkMake(List<ZxSkMake> zxSkMakeList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkMakeList != null && zxSkMakeList.size() > 0) {
            for (ZxSkMake zxSkMake : zxSkMakeList) {
                // 删除明细
                ZxSkMakeItem zxSkMakeItem = new ZxSkMakeItem();
                zxSkMakeItem.setMakeInvID(zxSkMake.getId());
                List<ZxSkMakeItem> zxSkMakeItems = zxSkMakeItemMapper.selectByZxSkMakeItemList(zxSkMakeItem);
                if(zxSkMakeItems != null && zxSkMakeItems.size()>0) {
                    zxSkMakeItem.setModifyUserInfo(userKey, realName);
                    zxSkMakeItemMapper.batchDeleteUpdateZxSkMakeItem(zxSkMakeItems, zxSkMakeItem);
                }
                //删除附件
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxSkMake.getId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if(zxErpFiles != null && zxErpFiles.size()>0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
            }
           ZxSkMake zxSkMake = new ZxSkMake();
           zxSkMake.setModifyUserInfo(userKey, realName);
           flag = zxSkMakeMapper.batchDeleteUpdateZxSkMake(zxSkMakeList, zxSkMake);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkMakeList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public synchronized ResponseEntity checkZxSkMake(ZxSkMake zxSkMake) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        //然后审核数据
        ZxSkMake zxSkMake1 = zxSkMakeMapper.selectByPrimaryKey(zxSkMake.getId());
        if(StrUtil.equals(zxSkMake1.getStatus(), "1")) {
            return repEntity.layerMessage("no", "已经盘点的，请重新选择！");
        }
        int flag = 0;
        zxSkMake.setStatus("1");
        zxSkMake.setModifyUserInfo(userKey, realName);
        flag = zxSkMakeMapper.checkZxSkMake(zxSkMake);
            // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zxSkMake);
        }
    }

    //库存物资的,(预收,收料的数据 不跟库存一样的 全按 0)
    @Override
    public ResponseEntity startZxSkMake(ZxSkMake zxSkMake) {
        if(StrUtil.isEmpty(zxSkMake.getCompanyID())
//                ||StrUtil.isEmpty(zxSkStock.getOrgID())
                    ||StrUtil.isEmpty(zxSkMake.getWhOrgID())){
            return repEntity.ok(new ArrayList<>());
//            return repEntity.layerMessage("no", "无数据！");
        }
//        ZxSkMake zxSkMake1 = zxSkMakeMapper.selectByPrimaryKey(zxSkMake.getId());
//        if (StrUtil.equals(zxSkMake1.getPlmm(),"4")&&StrUtil.equals(zxSkMake1.getStatus(),"1")){
//            //从数据库查询数据
//            return repEntity.layerMessage("no", "仓库已经盘点结束");
//        }
//        if (StrUtil.equals(zxSkMake1.getPlmm(),"4")){
//            //从数据库查询数据
//            return repEntity.ok(zxSkMake1.getZxSkMakeItemList());
//        }
        //排除库存物资中的其他 (预收,收料的数据 审核的)
        List<ZxSkStock> zxSkStockListRes = zxSkMakeMapper.selectResource(zxSkMake);
        return repEntity.ok(zxSkStockListRes);
    }
}
