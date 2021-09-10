package com.apih5.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.date.DateUtil;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.mybatis.dao.ZxSysProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.dao.ZxSfCheckItemMapper;
import com.apih5.mybatis.dao.ZxSfCheckMapper;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.mybatis.pojo.ZxSfCheck;
import com.apih5.mybatis.pojo.ZxSfCheckItem;
import com.apih5.service.ZxSfCheckService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxSfCheckService")
public class ZxSfCheckServiceImpl implements ZxSfCheckService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfCheckMapper zxSfCheckMapper;

    @Autowired(required = true)
    private ZxSfCheckItemMapper zxSfCheckItemMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxSysProjectMapper zxSysProjectMapper;

    @Override
    public ResponseEntity getZxSfCheckListByCondition(ZxSfCheck zxSfCheck) {
        if (zxSfCheck == null) {
            zxSfCheck = new ZxSfCheck();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSfCheck.setCompanyId("");
            zxSfCheck.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSfCheck.setCompanyId(zxSfCheck.getOrgID());
            zxSfCheck.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSfCheck.setOrgID(zxSfCheck.getOrgID());
        }
        // 获取数据
        List<ZxSfCheck> zxSfCheckList = zxSfCheckMapper.selectByZxSfCheckList(zxSfCheck);
        // 得到明细数据
        for (ZxSfCheck zxSfCheck1 : zxSfCheckList) {
            ZxSfCheckItem zxSfCheckItem = new ZxSfCheckItem();
            zxSfCheckItem.setCheckID(zxSfCheck.getZxSfCheckId());
            List<ZxSfCheckItem> zxSfCheckItem1 = zxSfCheckItemMapper.selectByZxSfCheckItemList(zxSfCheckItem);
            zxSfCheck1.setZxSfCheckItemList(zxSfCheckItem1);
        }
        // 查询附件
        for (ZxSfCheck zxSfCheck2 : zxSfCheckList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSfCheck2.getZxSfCheckId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSfCheck2.setFileList(zxErpFiles);
        }
        for (ZxSfCheck ZxSfCheck3 : zxSfCheckList) {
            if (ZxSfCheck3.getCheckType().equals("3") || ZxSfCheck3.getIsSend().equals("2") || ZxSfCheck3.getIsSend().equals("1")) {

                zxSfCheck.setIsSend("2");
                // 获取数据
                List<ZxSfCheck> zxSfCheckList1 = zxSfCheckMapper.selectByZxSfCheckList(zxSfCheck);
                // 得到分页信息
                PageInfo<ZxSfCheck> p = new PageInfo<>(zxSfCheckList1);

                return repEntity.okList(zxSfCheckList1, p.getTotal());
            }
        }
        // 得到分页信息
        PageInfo<ZxSfCheck> p = new PageInfo<>(zxSfCheckList);

        return repEntity.okList(zxSfCheckList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfCheckDetail(ZxSfCheck zxSfCheck) {
        if (zxSfCheck == null) {
            zxSfCheck = new ZxSfCheck();
        }
        // 获取数据
        ZxSfCheck dbZxSfCheck = zxSfCheckMapper.selectByPrimaryKey(zxSfCheck.getZxSfCheckId());
        // 数据存在
        if (dbZxSfCheck != null) {
            // 附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSfCheck.getZxSfCheckId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSfCheck.setFileList(zxErpFiles);
            return repEntity.ok(dbZxSfCheck);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfCheck(ZxSfCheck zxSfCheck) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfCheck.setZxSfCheckId(UuidUtil.generate());
        zxSfCheck.setCreateUserInfo(userKey, realName);
        zxSfCheck.setOrgID(zxSfCheck.getProjID());
        // 新增明细数据
        List<ZxSfCheckItem> zxSfCheckItemList = zxSfCheck.getZxSfCheckItemList();
        if (zxSfCheckItemList != null && zxSfCheckItemList.size() > 0) {
            for (ZxSfCheckItem zxSfCheckItem : zxSfCheckItemList) {
                zxSfCheckItem.setCheckID(zxSfCheck.getZxSfCheckId());
                zxSfCheckItem.setZxSfCheckItemId((UuidUtil.generate()));
                zxSfCheckItem.setCreateUserInfo(userKey, realName);
                zxSfCheckItemMapper.insert(zxSfCheckItem);
            }
        }
        // 添加附件
        List<ZxErpFile> fileList = zxSfCheck.getFileList();
        if (fileList != null && fileList.size() > 0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSfCheck.getZxSfCheckId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        int flag = zxSfCheckMapper.insert(zxSfCheck);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfCheck);
        }
    }

    @Override
    public ResponseEntity updateZxSfCheck(ZxSfCheck zxSfCheck) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfCheck dbZxSfCheck = zxSfCheckMapper.selectByPrimaryKey(zxSfCheck.getZxSfCheckId());
        if (dbZxSfCheck != null && StrUtil.isNotEmpty(dbZxSfCheck.getZxSfCheckId())) {
            // 机构名称
            dbZxSfCheck.setOrgName(zxSfCheck.getOrgName());
            // 机构ID
            dbZxSfCheck.setOrgID(zxSfCheck.getProjID());
            // 项目名称
            dbZxSfCheck.setProjName(zxSfCheck.getProjName());
            // 项目ID
            dbZxSfCheck.setProjID(zxSfCheck.getProjID());
            // 检查日期
            dbZxSfCheck.setCheckDate(zxSfCheck.getCheckDate());
            // 检查内容
            dbZxSfCheck.setCheckContext(zxSfCheck.getCheckContext());
            // 检查组长
            dbZxSfCheck.setCheckGroup(zxSfCheck.getCheckGroup());
            // 检查成员
            dbZxSfCheck.setCheckEmployee(zxSfCheck.getCheckEmployee());
            // 编辑时间
            dbZxSfCheck.setEditTime(zxSfCheck.getEditTime());
            // isGroup
            dbZxSfCheck.setIsGroup(zxSfCheck.getIsGroup());
            // isCompany
            dbZxSfCheck.setIsCompany(zxSfCheck.getIsCompany());
            // 检查类型
            dbZxSfCheck.setCheckType(zxSfCheck.getCheckType());
            // 报表ID
            dbZxSfCheck.setReportID(zxSfCheck.getReportID());
            // 状态
            dbZxSfCheck.setStatus(zxSfCheck.getStatus());
            // 是否下达
            dbZxSfCheck.setIsSend(zxSfCheck.getIsSend());
            // 是否上报
            dbZxSfCheck.setIsReported(zxSfCheck.getIsReported());
            // sendID
            dbZxSfCheck.setSendID(zxSfCheck.getSendID());
            // 复查状态
            dbZxSfCheck.setCheckAgainStatus(zxSfCheck.getCheckAgainStatus());
            // 备注
            dbZxSfCheck.setRemarks(zxSfCheck.getRemarks());
            // 排序
            dbZxSfCheck.setSort(zxSfCheck.getSort());
            // 共通
            dbZxSfCheck.setModifyUserInfo(userKey, realName);
            flag = zxSfCheckMapper.updateByPrimaryKey(dbZxSfCheck);
            // 修改在新增明细数据
            ZxSfCheckItem zxSfCheckItemSelect = new ZxSfCheckItem();
            zxSfCheckItemSelect.setCheckID(zxSfCheck.getZxSfCheckId());
            List<ZxSfCheckItem> zxSfCheckItem1 = zxSfCheckItemMapper.selectByZxSfCheckItemList(zxSfCheckItemSelect);
            if (zxSfCheckItem1 != null && zxSfCheckItem1.size() > 0) {
                zxSfCheckItemSelect.setModifyUserInfo(userKey, realName);
                zxSfCheckItemMapper.batchDeleteUpdateZxSfCheckItem(zxSfCheckItem1, zxSfCheckItemSelect);
            }
            List<ZxSfCheckItem> zxSfCheckItemList = zxSfCheck.getZxSfCheckItemList();
            if (zxSfCheckItemList != null && zxSfCheckItemList.size() > 0) {
                for (ZxSfCheckItem zxSfCheckItem : zxSfCheckItemList) {
                    zxSfCheckItem.setCheckID(zxSfCheck.getZxSfCheckId());
                    zxSfCheckItem.setZxSfCheckItemId((UuidUtil.generate()));
                    zxSfCheckItem.setCreateUserInfo(userKey, realName);
                    zxSfCheckItemMapper.insert(zxSfCheckItem);
                }
            }
            // 修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSfCheck.getZxSfCheckId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if (zxErpFiles != null && zxErpFiles.size() > 0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            List<ZxErpFile> fileList = zxSfCheck.getFileList();
            if (fileList != null && fileList.size() > 0) {
                for (ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setOtherId(zxSfCheck.getZxSfCheckId());
                    zxErpFile.setUid((UuidUtil.generate()));
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSfCheck);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfCheck(List<ZxSfCheck> zxSfCheckList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfCheckList != null && zxSfCheckList.size() > 0) {
            //删除明细
            for (ZxSfCheck zxSfCheckList1 : zxSfCheckList) {
                ZxSfCheckItem zxSfCheckItemSelect = new ZxSfCheckItem();
                zxSfCheckItemSelect.setCheckID(zxSfCheckList1.getZxSfCheckId());
                List<ZxSfCheckItem> zxSfCheckItem1 = zxSfCheckItemMapper.selectByZxSfCheckItemList(zxSfCheckItemSelect);
                if (zxSfCheckItem1 != null && zxSfCheckItem1.size() > 0) {
                    zxSfCheckItemSelect.setModifyUserInfo(userKey, realName);
                    zxSfCheckItemMapper.batchDeleteUpdateZxSfCheckItem(zxSfCheckItem1, zxSfCheckItemSelect);
                }
                // 删除附件
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxSfCheckList1.getZxSfCheckId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if (zxErpFiles != null && zxErpFiles.size() > 0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
                ZxSfCheck zxSfCheck = new ZxSfCheck();
                zxSfCheck.setModifyUserInfo(userKey, realName);
                flag = zxSfCheckMapper.batchDeleteUpdateZxSfCheck(zxSfCheckList, zxSfCheck);
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSfCheckList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    // 项目上报
    @Override
    public ResponseEntity updateZxSfCheckIsReported(ZxSfCheck zxSfCheck) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfCheck dbZxSfCheck = zxSfCheckMapper.selectByPrimaryKey(zxSfCheck.getZxSfCheckId());
        if (dbZxSfCheck.getCheckAgainStatus().equals("3") && dbZxSfCheck.getIsSend().equals("2") || dbZxSfCheck.getIsSend().equals("1")) {
            if (dbZxSfCheck != null && StrUtil.isNotEmpty(dbZxSfCheck.getZxSfCheckId())) {
                // 检查类型
                dbZxSfCheck.setCheckType("3");
                // 是否下达
                if (dbZxSfCheck.getIsSend().equals("2")) {
                    //公司
                    dbZxSfCheck.setIsSend("2");
                } else if (dbZxSfCheck.getIsSend().equals("1")) {
                    //局
                    dbZxSfCheck.setIsSend("1");
                }
                // 是否上报
                dbZxSfCheck.setIsReported("3");
                // 复查状态
                dbZxSfCheck.setCheckAgainStatus("3");
                // 共通
                dbZxSfCheck.setModifyUserInfo(userKey, realName);
                flag = zxSfCheckMapper.updateByPrimaryKey(dbZxSfCheck);
            }
        } else {
            return repEntity.layerMessage("no", "必须是(公司)或者(局)下达并且项目已复查的数据才能上报！");
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSfCheck);
        }
    }

    //项目复查
    @Override
    public ResponseEntity updateZxSfCheckCheckAgainStatus(ZxSfCheck zxSfCheck) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfCheck dbZxSfCheck = zxSfCheckMapper.selectByPrimaryKey(zxSfCheck.getZxSfCheckId());
        if (dbZxSfCheck != null && StrUtil.isNotEmpty(dbZxSfCheck.getZxSfCheckId())) {
            // 检查类型
            dbZxSfCheck.setCheckType("3");
            // 复查状态
            dbZxSfCheck.setCheckAgainStatus("3");
            // 共通
            dbZxSfCheck.setModifyUserInfo(userKey, realName);
            flag = zxSfCheckMapper.updateByPrimaryKey(dbZxSfCheck);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSfCheck);
        }
    }

    // 项目新查询
    @Override
    public ResponseEntity getZxSfCheckListAll(ZxSfCheck zxSfCheck) {
        if (zxSfCheck == null) {
            zxSfCheck = new ZxSfCheck();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSfCheck.setCompanyId("");
            zxSfCheck.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSfCheck.setCompanyId(zxSfCheck.getProjectId());
            zxSfCheck.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSfCheck.setOrgID(zxSfCheck.getProjectId());
        }
        // 分页查询
        PageHelper.startPage(zxSfCheck.getPage(), zxSfCheck.getLimit());
        // 获取数据
        List<ZxSfCheck> zxSfCheckList = zxSfCheckMapper.selectByZxSfCheckListAll(zxSfCheck);
        // 得到明细数据
        for (ZxSfCheck zxSfCheck1 : zxSfCheckList) {
            ZxSfCheckItem zxSfCheckItem = new ZxSfCheckItem();
            zxSfCheckItem.setCheckID(zxSfCheck1.getZxSfCheckId());
            List<ZxSfCheckItem> zxSfCheckItem1 = zxSfCheckItemMapper.selectByZxSfCheckItemList(zxSfCheckItem);
            zxSfCheck1.setZxSfCheckItemList(zxSfCheckItem1);
        }
        // 查询附件
        for (ZxSfCheck zxSfCheck2 : zxSfCheckList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSfCheck2.getZxSfCheckId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSfCheck2.setFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxSfCheck> p = new PageInfo<>(zxSfCheckList);
        return repEntity.okList(zxSfCheckList, p.getTotal());
    }

    // 公司上报
    @Override
    public ResponseEntity updateZxSfCheckIsReportedCompany(ZxSfCheck zxSfCheck) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfCheck dbZxSfCheck = zxSfCheckMapper.selectByPrimaryKey(zxSfCheck.getZxSfCheckId());
        if (dbZxSfCheck.getCheckAgainStatus().equals("2") && dbZxSfCheck.getIsSend().equals("1")) {
            if (dbZxSfCheck != null && StrUtil.isNotEmpty(dbZxSfCheck.getZxSfCheckId())) {
                // 检查类型
                dbZxSfCheck.setCheckType("2");
                // 是否下达
                dbZxSfCheck.setIsSend("1");
                // 是否上报
                dbZxSfCheck.setIsReported("2");
                // 复查状态
                dbZxSfCheck.setCheckAgainStatus("2");
                // 共通
                dbZxSfCheck.setModifyUserInfo(userKey, realName);
                flag = zxSfCheckMapper.updateByPrimaryKey(dbZxSfCheck);
            }
        } else {
            return repEntity.layerMessage("no", "必须是(局)下达并且公司已复查的数据才能上报！");
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSfCheck);
        }
    }

    //公司复查
    @Override
    public ResponseEntity updateZxSfCheckCheckAgainStatusCompany(ZxSfCheck zxSfCheck) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfCheck dbZxSfCheck = zxSfCheckMapper.selectByPrimaryKey(zxSfCheck.getZxSfCheckId());

        if (dbZxSfCheck != null && StrUtil.isNotEmpty(dbZxSfCheck.getZxSfCheckId())) {
            // 检查类型
            dbZxSfCheck.setCheckType("2");
            // 复查状态
            dbZxSfCheck.setCheckAgainStatus("2");
            // 共通
            dbZxSfCheck.setModifyUserInfo(userKey, realName);
            flag = zxSfCheckMapper.updateByPrimaryKey(dbZxSfCheck);


        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSfCheck);
        }
    }

    // 公司新查询
    @Override
    public ResponseEntity getZxSfCheckListAllCompany(ZxSfCheck zxSfCheck) {
        if (zxSfCheck == null) {
            zxSfCheck = new ZxSfCheck();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSfCheck.setCompanyId("");
            zxSfCheck.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSfCheck.setCompanyId(zxSfCheck.getProjectId());
            zxSfCheck.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSfCheck.setOrgID(zxSfCheck.getProjectId());
        }
        // 分页查询
        PageHelper.startPage(zxSfCheck.getPage(), zxSfCheck.getLimit());
        // 项目安全检查区分
        zxSfCheck.setCheckType("2");
        // 获取数据
        List<ZxSfCheck> zxSfCheckList = zxSfCheckMapper.selectByZxSfCheckListAllcompany(zxSfCheck);
        // 得到明细数据
        for (ZxSfCheck zxSfCheck1 : zxSfCheckList) {
            ZxSfCheckItem zxSfCheckItem = new ZxSfCheckItem();
            zxSfCheckItem.setCheckID(zxSfCheck1.getZxSfCheckId());
            List<ZxSfCheckItem> zxSfCheckItem1 = zxSfCheckItemMapper.selectByZxSfCheckItemList(zxSfCheckItem);
            zxSfCheck1.setZxSfCheckItemList(zxSfCheckItem1);
        }
        // 查询附件
        for (ZxSfCheck zxSfCheck2 : zxSfCheckList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSfCheck2.getZxSfCheckId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSfCheck2.setFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxSfCheck> p = new PageInfo<>(zxSfCheckList);

        return repEntity.okList(zxSfCheckList, p.getTotal());
    }

    // 公司下达
    @Override
    public ResponseEntity updateZxSfCheckIsSendCompany(ZxSfCheck zxSfCheck) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfCheck dbZxSfCheck = zxSfCheckMapper.selectByPrimaryKey(zxSfCheck.getZxSfCheckId());
        // dbZxSfCheck.getCheckAgainStatus().equals("2")
        // if (StrUtil.isNotEmpty(dbZxSfCheck.getCheckAgainStatus())) {
            if (dbZxSfCheck != null && StrUtil.isNotEmpty(dbZxSfCheck.getZxSfCheckId())) {
                // 检查类型
                dbZxSfCheck.setCheckType("2");
                // 是否下达
                dbZxSfCheck.setIsSend("2");
                // 共通
                dbZxSfCheck.setModifyUserInfo(userKey, realName);
                flag = zxSfCheckMapper.updateByPrimaryKey(dbZxSfCheck);
            }
        // }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSfCheck);
        }
    }

    //局复查
    @Override
    public ResponseEntity updateZxSfCheckAgainStatusBureau(ZxSfCheck zxSfCheck) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfCheck dbZxSfCheck = zxSfCheckMapper.selectByPrimaryKey(zxSfCheck.getZxSfCheckId());
        if (dbZxSfCheck != null && StrUtil.isNotEmpty(dbZxSfCheck.getZxSfCheckId())) {
            // 检查类型
            dbZxSfCheck.setCheckType("1");
            // 复查状态
            dbZxSfCheck.setCheckAgainStatus("1");
            // 共通
            dbZxSfCheck.setModifyUserInfo(userKey, realName);
            flag = zxSfCheckMapper.updateByPrimaryKey(dbZxSfCheck);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSfCheck);
        }
    }

    // 局新查询
    @Override
    public ResponseEntity getZxSfCheckListAllBureau(ZxSfCheck zxSfCheck) {
        if (zxSfCheck == null) {
            zxSfCheck = new ZxSfCheck();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSfCheck.setCompanyId("");
            zxSfCheck.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSfCheck.setCompanyId(zxSfCheck.getProjectId());
            zxSfCheck.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSfCheck.setOrgID(zxSfCheck.getProjectId());
        }
        // 分页查询
        PageHelper.startPage(zxSfCheck.getPage(), zxSfCheck.getLimit());
        // 项目安全检查区分
        zxSfCheck.setCheckType("1");
        // 获取数据
        List<ZxSfCheck> zxSfCheckList = zxSfCheckMapper.selectByZxSfCheckListAllBureau(zxSfCheck);
        // 得到明细数据
        for (ZxSfCheck zxSfCheck1 : zxSfCheckList) {
            ZxSfCheckItem zxSfCheckItem = new ZxSfCheckItem();
            zxSfCheckItem.setCheckID(zxSfCheck1.getZxSfCheckId());
            List<ZxSfCheckItem> zxSfCheckItem1 = zxSfCheckItemMapper.selectByZxSfCheckItemList(zxSfCheckItem);
            zxSfCheck1.setZxSfCheckItemList(zxSfCheckItem1);
        }
        // 查询附件
        for (ZxSfCheck zxSfCheck2 : zxSfCheckList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSfCheck2.getZxSfCheckId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSfCheck2.setFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxSfCheck> p = new PageInfo<>(zxSfCheckList);
        return repEntity.okList(zxSfCheckList, p.getTotal());
    }

    // 局下达
    @Override
    public ResponseEntity updateZxSfCheckIsSendBureau(ZxSfCheck zxSfCheck) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfCheck dbZxSfCheck = zxSfCheckMapper.selectByPrimaryKey(zxSfCheck.getZxSfCheckId());
        if (dbZxSfCheck.getCheckAgainStatus().equals("1")) {
            if (dbZxSfCheck != null && StrUtil.isNotEmpty(dbZxSfCheck.getZxSfCheckId())) {
                // 检查类型
                dbZxSfCheck.setCheckType("1");
                // 是否下达
                dbZxSfCheck.setIsSend("1");
                // 共通
                dbZxSfCheck.setModifyUserInfo(userKey, realName);
                flag = zxSfCheckMapper.updateByPrimaryKey(dbZxSfCheck);
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSfCheck);
        }
    }

    /**
     * 查询公司
     *
     * @param zxSfCheck
     * @suncg
     */
    @Override
    public ResponseEntity getCompany(ZxSfCheck zxSfCheck) {
        ZxSfCheck dbcheck = zxSfCheckMapper.getCompany(zxSfCheck);
        dbcheck.setCheckFGL(CalcUtils.calcDivide(new BigDecimal(dbcheck.getOrgNum()), new BigDecimal(dbcheck.getCheckNum()), 2));
        return repEntity.ok(dbcheck);
    }

    /**
     * 查询项目(状态区分)
     *
     * @param checkSfCheck
     * @suncg
     */
    @Override
    public ResponseEntity getCheckList(ZxSfCheck checkSfCheck) {
        List<ZxSfCheck> checklist = new ArrayList<ZxSfCheck>();
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        //归档查询
        ZxSfCheck gdcheck = zxSfCheckMapper.getGuiDang(checkSfCheck, today);
        gdcheck.setCheckFGL(CalcUtils.calcDivide(new BigDecimal(gdcheck.getOrgNum()), new BigDecimal(gdcheck.getCheckNum()), 2));
        //交工查询
        ZxSfCheck jgcheck = zxSfCheckMapper.getJiaoGong(checkSfCheck, today);
        jgcheck.setCheckFGL(CalcUtils.calcDivide(new BigDecimal(jgcheck.getOrgNum()), new BigDecimal(jgcheck.getCheckNum()), 2));
        //完工
        ZxSfCheck wgcheck = zxSfCheckMapper.getWanGong(checkSfCheck, today);
        wgcheck.setCheckFGL(CalcUtils.calcDivide(new BigDecimal(wgcheck.getOrgNum()), new BigDecimal(wgcheck.getCheckNum()), 2));
        //
        ZxSfCheck kgcheck = zxSfCheckMapper.getKaiGong(checkSfCheck, today);
        kgcheck.setCheckFGL(CalcUtils.calcDivide(new BigDecimal(kgcheck.getOrgNum()), new BigDecimal(kgcheck.getCheckNum()), 2));
        checklist.add(gdcheck);
        checklist.add(jgcheck);
        checklist.add(wgcheck);
        checklist.add(kgcheck);
        return repEntity.ok(checklist);
    }

    /**
     * 查询归档项目数据
     *
     * @param checkSfCheck
     * @suncg
     */
    @Override
    public ResponseEntity getGuiDangList(ZxSfCheck checkSfCheck) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        List<ZxSfCheck> guiDangList = zxSfCheckMapper.getGuiDangList(checkSfCheck, today);
        return repEntity.ok(guiDangList);
    }

    /**
     * 查询交工数据
     *
     * @param checkSfCheck
     * @suncg
     */
    @Override
    public ResponseEntity getJiaoGongList(ZxSfCheck checkSfCheck) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        List<ZxSfCheck> jiaoGongList = zxSfCheckMapper.getJiaoGongList(checkSfCheck, today);
        return repEntity.ok(jiaoGongList);
    }

    /**
     * 查询完工数据
     *
     * @param checkSfCheck
     * @suncg
     */
    @Override
    public ResponseEntity getWanGongList(ZxSfCheck checkSfCheck) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        List<ZxSfCheck> wanGongList = zxSfCheckMapper.getWanGongList(checkSfCheck, today);
        return repEntity.ok(wanGongList);
    }

    /**
     * 查询开工数据
     *
     * @param checkSfCheck
     * @suncg
     */
    @Override
    public ResponseEntity getKaiGongList(ZxSfCheck checkSfCheck) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        List<ZxSfCheck> kaiGongList = zxSfCheckMapper.getKaiGongList(checkSfCheck, today);
        return repEntity.ok(kaiGongList);
    }

    @Override
    public ResponseEntity getComList(ZxSfCheck checkSfCheck) {
        List<ZxSfCheck> comList = zxSfCheckMapper.getComList(checkSfCheck);
        return repEntity.ok(comList);
    }

    @Override
    public List<ZxSfCheck> getZxSfCheckListForm(ZxSfCheck zxSfCheck) {
        // 获取数据
        List<ZxSfCheck> dbZxSfCheckList = zxSfCheckMapper.selectByZxSfCheckId(zxSfCheck);
        return dbZxSfCheckList;

    }

    @Override
    public ResponseEntity getFormZxSfCheckList(ZxSfCheck zxSfCheck) {
        // 获取数据
        List<ZxSfCheck> dbZxSfCheckList = zxSfCheckMapper.selectByZxSfCheckId(zxSfCheck);
        return repEntity.ok(dbZxSfCheckList);
    }

    @Override
    public ResponseEntity getFormJuInfo(ZxSfCheck zxSfCheck) {
        // 获取数据
        ZxSfCheck dbZxSfCheck = zxSfCheckMapper.getJuInfo(zxSfCheck);
        return repEntity.ok(dbZxSfCheck);
    }

}
