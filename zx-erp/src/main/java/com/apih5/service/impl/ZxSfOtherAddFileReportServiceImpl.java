package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.framework.constant.SysConst;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxErpFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSfOtherAddFileReportMapper;
import com.apih5.mybatis.pojo.ZxSfOtherAddFileReport;
import com.apih5.service.ZxSfOtherAddFileReportService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSfOtherAddFileReportService")
public class ZxSfOtherAddFileReportServiceImpl implements ZxSfOtherAddFileReportService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfOtherAddFileReportMapper zxSfOtherAddFileReportMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Override
    public ResponseEntity getZxSfOtherAddFileReportListByCondition(ZxSfOtherAddFileReport zxSfOtherAddFileReport) {
        if (zxSfOtherAddFileReport == null) {
            zxSfOtherAddFileReport = new ZxSfOtherAddFileReport();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSfOtherAddFileReport.setCompanyId("");
            zxSfOtherAddFileReport.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSfOtherAddFileReport.setCompanyId(zxSfOtherAddFileReport.getOrgId());
            zxSfOtherAddFileReport.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSfOtherAddFileReport.setOrgId(zxSfOtherAddFileReport.getOrgId());
        }

        // 分页查询
        PageHelper.startPage(zxSfOtherAddFileReport.getPage(),zxSfOtherAddFileReport.getLimit());
        // 获取数据
        List<ZxSfOtherAddFileReport> zxSfOtherAddFileReportList = zxSfOtherAddFileReportMapper.selectByZxSfOtherAddFileReportList(zxSfOtherAddFileReport);
        for (ZxSfOtherAddFileReport zxSfOtherAddFileReport1 : zxSfOtherAddFileReportList) {
            //查询附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSfOtherAddFileReport1.getZxSfOtherAddFileReportId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSfOtherAddFileReport1.setZxErpFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxSfOtherAddFileReport> p = new PageInfo<>(zxSfOtherAddFileReportList);

        return repEntity.okList(zxSfOtherAddFileReportList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfOtherAddFileReportDetail(ZxSfOtherAddFileReport zxSfOtherAddFileReport) {
        if (zxSfOtherAddFileReport == null) {
            zxSfOtherAddFileReport = new ZxSfOtherAddFileReport();
        }
        // 获取数据
        ZxSfOtherAddFileReport dbZxSfOtherAddFileReport = zxSfOtherAddFileReportMapper.selectByPrimaryKey(zxSfOtherAddFileReport.getZxSfOtherAddFileReportId());
        // 数据存在
        if (dbZxSfOtherAddFileReport != null) {
            //附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSfOtherAddFileReport.getZxSfOtherAddFileReportId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            dbZxSfOtherAddFileReport.setZxErpFileList(zxErpFiles);
            return repEntity.ok(dbZxSfOtherAddFileReport);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfOtherAddFileReport(ZxSfOtherAddFileReport zxSfOtherAddFileReport) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfOtherAddFileReport.setZxSfOtherAddFileReportId(UuidUtil.generate());
        zxSfOtherAddFileReport.setCreateUserInfo(userKey, realName);
        int flag = zxSfOtherAddFileReportMapper.insert(zxSfOtherAddFileReport);

        //添加附件
        List<ZxErpFile> fileList = zxSfOtherAddFileReport.getZxErpFileList();
        if (fileList != null && fileList.size() > 0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSfOtherAddFileReport.getZxSfOtherAddFileReportId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfOtherAddFileReport);
        }
    }

    @Override
    public ResponseEntity updateZxSfOtherAddFileReport(ZxSfOtherAddFileReport zxSfOtherAddFileReport) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfOtherAddFileReport dbZxSfOtherAddFileReport = zxSfOtherAddFileReportMapper.selectByPrimaryKey(zxSfOtherAddFileReport.getZxSfOtherAddFileReportId());
        if (dbZxSfOtherAddFileReport != null && StrUtil.isNotEmpty(dbZxSfOtherAddFileReport.getZxSfOtherAddFileReportId())) {
           // 名称
           dbZxSfOtherAddFileReport.setTitle(zxSfOtherAddFileReport.getTitle());
           // 填报日期
           dbZxSfOtherAddFileReport.setCreateDate(zxSfOtherAddFileReport.getCreateDate());
           // 备注
           dbZxSfOtherAddFileReport.setNotes(zxSfOtherAddFileReport.getNotes());
           // 共通
           dbZxSfOtherAddFileReport.setModifyUserInfo(userKey, realName);
           flag = zxSfOtherAddFileReportMapper.updateByPrimaryKey(dbZxSfOtherAddFileReport);

            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSfOtherAddFileReport.getZxSfOtherAddFileReportId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if (zxErpFiles != null && zxErpFiles.size() > 0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }

            //明细list
            List<ZxErpFile> fileList = zxSfOtherAddFileReport.getZxErpFileList();
            if (fileList != null && fileList.size() > 0) {
                for (ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setOtherId(zxSfOtherAddFileReport.getZxSfOtherAddFileReportId());
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
            return repEntity.ok("sys.data.update",zxSfOtherAddFileReport);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfOtherAddFileReport(List<ZxSfOtherAddFileReport> zxSfOtherAddFileReportList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfOtherAddFileReportList != null && zxSfOtherAddFileReportList.size() > 0) {
            for (ZxSfOtherAddFileReport zxSfOtherAddFileReport : zxSfOtherAddFileReportList) {
                //删除附件
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxSfOtherAddFileReport.getZxSfOtherAddFileReportId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if (zxErpFiles != null && zxErpFiles.size() > 0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
            }
           ZxSfOtherAddFileReport zxSfOtherAddFileReport = new ZxSfOtherAddFileReport();
           zxSfOtherAddFileReport.setModifyUserInfo(userKey, realName);
           flag = zxSfOtherAddFileReportMapper.batchDeleteUpdateZxSfOtherAddFileReport(zxSfOtherAddFileReportList, zxSfOtherAddFileReport);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSfOtherAddFileReportList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
