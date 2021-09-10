package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.mybatis.dao.*;
import com.apih5.mybatis.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.service.ZxSfExamService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxSfExamService")
public class ZxSfExamServiceImpl implements ZxSfExamService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfExamMapper zxSfExamMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxSfExamItemMapper zxSfExamItemMapper;

    @Autowired(required = true)
    private ZxSysProjectMapper zxSysProjectMapper;

    @Autowired(required = true)
    private ZxSfJuAccessRoomMapper zxSfJuAccessRoomMapper;

    @Override
    public ResponseEntity getZxSfExamListByCondition(ZxSfExam zxSfExam) {
        if (zxSfExam == null) {
            zxSfExam = new ZxSfExam();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSfExam.setCompanyId("");
            zxSfExam.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSfExam.setCompanyId(zxSfExam.getOrgID());
            zxSfExam.setOrgID("");
        }
        // 分页查询
        PageHelper.startPage(zxSfExam.getPage(), zxSfExam.getLimit());
        // 获取数据
        List<ZxSfExam> zxSfExamList = zxSfExamMapper.selectByZxSfExamList(zxSfExam);
        // 查询明细
        for (ZxSfExam zxSfExam1 : zxSfExamList) {
            ZxSfExamItem zxSfExamItem = new ZxSfExamItem();
            zxSfExamItem.setExamID(zxSfExam1.getZxSfExamId());
            List<ZxSfExamItem> zxSfExamItemListAll = zxSfExamItemMapper.selectByZxSfExamItemList(zxSfExamItem);
            zxSfExam1.setZxSfExamItemList(zxSfExamItemListAll);
        }
        // 查询附件
        for (ZxSfExam zxSfExam2 : zxSfExamList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSfExam2.getZxSfExamId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSfExam2.setFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxSfExam> p = new PageInfo<>(zxSfExamList);
        return repEntity.okList(zxSfExamList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfExamDetail(ZxSfExam zxSfExam) {
        if (zxSfExam == null) {
            zxSfExam = new ZxSfExam();
        }
        // 获取数据
        ZxSfExam dbZxSfExam = zxSfExamMapper.selectByPrimaryKey(zxSfExam.getZxSfExamId());
        // 数据存在
        if (dbZxSfExam != null) {
            // 附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSfExam.getZxSfExamId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSfExam.setFileList(zxErpFiles);

            return repEntity.ok(dbZxSfExam);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfExam(ZxSfExam zxSfExam) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfExam.setZxSfExamId(UuidUtil.generate());
        zxSfExam.setCreateUserInfo(userKey, realName);
        String ext1 = TokenUtils.getExt1(request);
        zxSfExam.setOrgID(zxSfExam.getExamID());
        if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司
            zxSfExam.setCompanyId(zxSfExam.getOrgID());
            zxSfExam.setCompanyName(zxSfExam.getExamName());
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目
            ZxSysProject project = zxSysProjectMapper.getCompanyInfoByProjectId(zxSfExam.getOrgID());
            zxSfExam.setCompanyId(project.getCompanyId());
            zxSfExam.setCompanyName(project.getCompanyName());
        }
        //明细追加
//        List<ZxSfExamItem> zxSfExamItemAll = zxSfExam.getZxSfExamItemList();
//        if (zxSfExamItemAll != null && zxSfExamItemAll.size() > 0) {
//            for (ZxSfExamItem zxSfExamItem1 : zxSfExamItemAll) {
//                zxSfExamItem1.setExamID(zxSfExam.getZxSfExamId());
//                zxSfExamItem1.setZxSfExamItemId(UuidUtil.generate());
//                zxSfExamItem1.setCreateUserInfo(userKey, realName);
//                zxSfExamItemMapper.insert(zxSfExamItem1);
//            }
//        }

        // 考核项
        ZxSfJuAccessRoom zxSfJuAccessRoom = new ZxSfJuAccessRoom();
        List<ZxSfJuAccessRoom> zxSfJuAccessRoomList = zxSfJuAccessRoomMapper.selectByZxSfJuAccessRoomList(zxSfJuAccessRoom);
        for (ZxSfJuAccessRoom accessRoom : zxSfJuAccessRoomList ) {
            ZxSfExamItem zxSfExamItem = new ZxSfExamItem();
            zxSfExamItem.setZxSfExamItemId(UuidUtil.generate());
            zxSfExamItem.setCreateUserInfo(userKey, realName);
            zxSfExamItem.setExamID(zxSfExam.getZxSfExamId());
            zxSfExamItem.setExamName(accessRoom.getExamName());
            zxSfExamItem.setExamContext(accessRoom.getExamContext());
            zxSfExamItem.setAllScore(new BigDecimal(accessRoom.getAllScore()));
            zxSfExamItemMapper.insert(zxSfExamItem);
        }

        // 添加附件
        List<ZxErpFile> fileList = zxSfExam.getFileList();
        if (fileList != null && fileList.size() > 0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSfExam.getZxSfExamId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        zxSfExam.setStatus("0");
        int flag = zxSfExamMapper.insert(zxSfExam);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfExam);
        }
    }

    @Override
    public ResponseEntity updateZxSfExam(ZxSfExam zxSfExam) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfExam dbZxSfExam = zxSfExamMapper.selectByPrimaryKey(zxSfExam.getZxSfExamId());
        if (dbZxSfExam != null && StrUtil.isNotEmpty(dbZxSfExam.getZxSfExamId())) {
            // 单位ID
            dbZxSfExam.setExamID(zxSfExam.getExamID());
            // 总分
            dbZxSfExam.setScore(zxSfExam.getScore());
            // 被考核单位领导
            dbZxSfExam.setUnitManage(zxSfExam.getUnitManage());
            // 填报人
            dbZxSfExam.setCreator(zxSfExam.getCreator());
            // 单位名称
            dbZxSfExam.setExamName(zxSfExam.getExamName());
            // 机构ID
            dbZxSfExam.setOrgID(zxSfExam.getOrgID());
            // 考核日期
            dbZxSfExam.setExamDate(zxSfExam.getExamDate());
            // 机构名称
            dbZxSfExam.setOrgName(zxSfExam.getOrgName());
            // 编辑日期
            dbZxSfExam.setEditTime(zxSfExam.getEditTime());
            // 公司
            dbZxSfExam.setIsCompany(zxSfExam.getIsCompany());
            // 集团
            dbZxSfExam.setIsGroup(zxSfExam.getIsGroup());
            // 所属公司ID
            dbZxSfExam.setCompanyId(zxSfExam.getCompanyId());
            // 所属公司名称
            dbZxSfExam.setCompanyName(zxSfExam.getCompanyName());
            // 备注
            dbZxSfExam.setRemarks(zxSfExam.getRemarks());
            // 状态
            dbZxSfExam.setStatus("0");
            // 排序
            dbZxSfExam.setSort(zxSfExam.getSort());
            // 共通
            dbZxSfExam.setModifyUserInfo(userKey, realName);
            flag = zxSfExamMapper.updateByPrimaryKey(dbZxSfExam);

            // 修改再新增(明细)
            ZxSfExamItem zxSfExamItem = new ZxSfExamItem();
            zxSfExamItem.setExamID(dbZxSfExam.getZxSfExamId());
            List<ZxSfExamItem> zxSfExamItemlist = zxSfExamItemMapper.selectByZxSfExamItemList(zxSfExamItem);

            if (zxSfExamItemlist != null && zxSfExamItemlist.size() > 0) {
                zxSfExamItem.setModifyUserInfo(userKey, realName);
                zxSfExamItemMapper.batchDeleteUpdateZxSfExamItem(zxSfExamItemlist, zxSfExamItem);
            }
            List<ZxSfExamItem> zxSfExamItemListAll = zxSfExam.getZxSfExamItemList();

            if (zxSfExamItemListAll != null && zxSfExamItemListAll.size() > 0) {
                for (ZxSfExamItem zxSfExamItem1 : zxSfExamItemListAll) {
                    zxSfExamItem1.setExamID(zxSfExam.getZxSfExamId());
                    zxSfExamItem1.setZxSfExamItemId(UuidUtil.generate());
                    zxSfExamItem1.setCreateUserInfo(userKey, realName);
                    zxSfExamItemMapper.insert(zxSfExamItem1);
                }
            }

            // 修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSfExam.getZxSfExamId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if (zxErpFiles != null && zxErpFiles.size() > 0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            List<ZxErpFile> fileList = zxSfExam.getFileList();
            if (fileList != null && fileList.size() > 0) {
                for (ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setOtherId(zxSfExam.getZxSfExamId());
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
            return repEntity.ok("sys.data.update", zxSfExam);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfExam(List<ZxSfExam> zxSfExamList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfExamList != null && zxSfExamList.size() > 0) {
            for (ZxSfExam zxSfExam2 : zxSfExamList) {
                // 删除打分考核表
                ZxSfExamItem zxSfExamItemSelect = new ZxSfExamItem();
                zxSfExamItemSelect.setExamID(zxSfExam2.getZxSfExamId());
                List<ZxSfExamItem> zxSfExamItem = zxSfExamItemMapper.selectByZxSfExamItemList(zxSfExamItemSelect);

                if (zxSfExamItem != null && zxSfExamItem.size() > 0) {
                    zxSfExamItemSelect.setModifyUserInfo(userKey, realName);
                    zxSfExamItemMapper.batchDeleteUpdateZxSfExamItem(zxSfExamItem, zxSfExamItemSelect);
                }

                // 删除附件
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxSfExam2.getZxSfExamId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if (zxErpFiles != null && zxErpFiles.size() > 0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
            }
            ZxSfExam zxSfExam = new ZxSfExam();
            zxSfExam.setModifyUserInfo(userKey, realName);
            flag = zxSfExamMapper.batchDeleteUpdateZxSfExam(zxSfExamList, zxSfExam);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSfExamList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public ResponseEntity updateZxSfExamStatus(ZxSfExam zxSfExam) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfExam dbZxSfExam = zxSfExamMapper.selectByPrimaryKey(zxSfExam.getZxSfExamId());
        if (dbZxSfExam != null && StrUtil.isNotEmpty(dbZxSfExam.getZxSfExamId())) {
            // 状态
            dbZxSfExam.setStatus("1");
            // 共通
            dbZxSfExam.setModifyUserInfo(userKey, realName);
            flag = zxSfExamMapper.updateByPrimaryKey(dbZxSfExam);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSfExam);
        }
    }

    @Override
    public ResponseEntity updateZxSfExamStatusju(ZxSfExam zxSfExam) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfExam dbZxSfExam = zxSfExamMapper.selectByPrimaryKey(zxSfExam.getZxSfExamId());
        if (dbZxSfExam != null && StrUtil.isNotEmpty(dbZxSfExam.getZxSfExamId())) {
            // 状态
            dbZxSfExam.setStatus("2");
            // 共通
            dbZxSfExam.setModifyUserInfo(userKey, realName);
            flag = zxSfExamMapper.updateByPrimaryKey(dbZxSfExam);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSfExam);
        }
    }
}
