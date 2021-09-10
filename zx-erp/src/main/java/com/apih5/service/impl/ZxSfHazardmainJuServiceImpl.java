package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.dao.ZxSfHazardmainDetailJuMapper;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.mybatis.pojo.ZxSfHazardmainDetailJu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSfHazardmainJuMapper;
import com.apih5.mybatis.pojo.ZxSfHazardmainJu;
import com.apih5.service.ZxSfHazardmainJuService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.CollectionUtils;

@Service("zxSfHazardmainJuService")
public class ZxSfHazardmainJuServiceImpl implements ZxSfHazardmainJuService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfHazardmainJuMapper zxSfHazardmainJuMapper;

    @Autowired(required = true)
    private ZxSfHazardmainDetailJuMapper zxSfHazardmainDetailJuMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Override
    public ResponseEntity getZxSfHazardmainJuListByCondition(ZxSfHazardmainJu zxSfHazardmainJu) {
        if (zxSfHazardmainJu == null) {
            zxSfHazardmainJu = new ZxSfHazardmainJu();
        }
        // 分页查询
        PageHelper.startPage(zxSfHazardmainJu.getPage(),zxSfHazardmainJu.getLimit());
        // 获取数据
        List<ZxSfHazardmainJu> zxSfHazardmainJuList = zxSfHazardmainJuMapper.selectByZxSfHazardmainJuList(zxSfHazardmainJu);
        // 得到分页信息
        PageInfo<ZxSfHazardmainJu> p = new PageInfo<>(zxSfHazardmainJuList);
        for (ZxSfHazardmainJu hazardmainJu : zxSfHazardmainJuList) {
            // 明细
            ZxSfHazardmainDetailJu zxSfHazardmainDetailJu = new ZxSfHazardmainDetailJu();
            zxSfHazardmainDetailJu.setMainId(hazardmainJu.getZxSfHazardmainJuId());
            List<ZxSfHazardmainDetailJu> detailJuList = zxSfHazardmainDetailJuMapper.selectByZxSfHazardmainDetailJuList(zxSfHazardmainDetailJu);
            hazardmainJu.setDetailList(detailJuList);
            // 附件
            ZxErpFile file = new ZxErpFile();
            file.setOtherId(hazardmainJu.getZxSfHazardmainJuId());
            List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
            hazardmainJu.setFileList(fileList);
        }
        return repEntity.okList(zxSfHazardmainJuList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfHazardmainJuDetail(ZxSfHazardmainJu zxSfHazardmainJu) {
        if (zxSfHazardmainJu == null) {
            zxSfHazardmainJu = new ZxSfHazardmainJu();
        }
        // 获取数据
        ZxSfHazardmainJu dbZxSfHazardmainJu = zxSfHazardmainJuMapper.selectByPrimaryKey(zxSfHazardmainJu.getZxSfHazardmainJuId());
        // 数据存在
        if (dbZxSfHazardmainJu != null) {
            return repEntity.ok(dbZxSfHazardmainJu);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfHazardmainJu(ZxSfHazardmainJu zxSfHazardmainJu) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfHazardmainJu.setZxSfHazardmainJuId(UuidUtil.generate());
        zxSfHazardmainJu.setCreateUserInfo(userKey, realName);
        int flag = zxSfHazardmainJuMapper.insert(zxSfHazardmainJu);
        // 明细
        if (!CollectionUtils.isEmpty(zxSfHazardmainJu.getDetailList())) {
            for (ZxSfHazardmainDetailJu zxSfHazardmainDetailJu : zxSfHazardmainJu.getDetailList()) {
                zxSfHazardmainDetailJu.setZxSfHazardmainDetailJuId(UuidUtil.generate());
                zxSfHazardmainDetailJu.setCreateUserInfo(userKey, realName);
                zxSfHazardmainDetailJu.setMainId(zxSfHazardmainJu.getZxSfHazardmainJuId());
                zxSfHazardmainDetailJuMapper.insert(zxSfHazardmainDetailJu);
            }
        }
        // 附件
        if (!CollectionUtils.isEmpty(zxSfHazardmainJu.getFileList())) {
            for (ZxErpFile file : zxSfHazardmainJu.getFileList()) {
                file.setOtherId(zxSfHazardmainJu.getZxSfHazardmainJuId());
                file.setUid((UuidUtil.generate()));
                file.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(file);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfHazardmainJu);
        }
    }

    @Override
    public ResponseEntity updateZxSfHazardmainJu(ZxSfHazardmainJu zxSfHazardmainJu) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfHazardmainJu dbZxSfHazardmainJu = zxSfHazardmainJuMapper.selectByPrimaryKey(zxSfHazardmainJu.getZxSfHazardmainJuId());
        if (dbZxSfHazardmainJu != null && StrUtil.isNotEmpty(dbZxSfHazardmainJu.getZxSfHazardmainJuId())) {
            // 机构ID
            dbZxSfHazardmainJu.setOrgId(zxSfHazardmainJu.getOrgId());
            // 所属机构
            dbZxSfHazardmainJu.setOrgName(zxSfHazardmainJu.getOrgName());
            // 编制人
            dbZxSfHazardmainJu.setPreparer(zxSfHazardmainJu.getPreparer());
            // 备注
            dbZxSfHazardmainJu.setRemarks(zxSfHazardmainJu.getRemarks());
            // 排序
            dbZxSfHazardmainJu.setSort(zxSfHazardmainJu.getSort());
            // 共通
            dbZxSfHazardmainJu.setModifyUserInfo(userKey, realName);
            flag = zxSfHazardmainJuMapper.updateByPrimaryKey(dbZxSfHazardmainJu);
            // 编辑明细
            ZxSfHazardmainDetailJu detailJu = new ZxSfHazardmainDetailJu();
            detailJu.setMainId(zxSfHazardmainJu.getZxSfHazardmainJuId());
            List<ZxSfHazardmainDetailJu> detailJuList = zxSfHazardmainDetailJuMapper.selectByZxSfHazardmainDetailJuList(detailJu);
            if (!CollectionUtils.isEmpty(detailJuList)) {
                detailJu.setModifyUserInfo(userKey, realName);
                zxSfHazardmainDetailJuMapper.batchDeleteUpdateZxSfHazardmainDetailJu(detailJuList, detailJu);
            }
            if (CollectionUtils.isEmpty(zxSfHazardmainJu.getDetailList())) {
                for (ZxSfHazardmainDetailJu zxSfHazardmainDetailJu : zxSfHazardmainJu.getDetailList()) {
                    zxSfHazardmainDetailJu.setZxSfHazardmainDetailJuId(UuidUtil.generate());
                    zxSfHazardmainDetailJu.setCreateUserInfo(userKey, realName);
                    zxSfHazardmainDetailJu.setMainId(zxSfHazardmainJu.getZxSfHazardmainJuId());
                    zxSfHazardmainDetailJuMapper.insert(zxSfHazardmainDetailJu);
                }
            }
            // 编辑附件
            ZxErpFile query = new ZxErpFile();
            query.setOtherId(zxSfHazardmainJu.getZxSfHazardmainJuId());
            List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(query);
            if (!CollectionUtils.isEmpty(fileList)) {
                query.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(fileList, query);
            }
            // 附件
            if (!CollectionUtils.isEmpty(zxSfHazardmainJu.getFileList())) {
                for (ZxErpFile file : zxSfHazardmainJu.getFileList()) {
                    file.setUid((UuidUtil.generate()));
                    file.setOtherId(zxSfHazardmainJu.getZxSfHazardmainJuId());
                    file.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(file);
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSfHazardmainJu);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfHazardmainJu(List<ZxSfHazardmainJu> zxSfHazardmainJuList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfHazardmainJuList != null && zxSfHazardmainJuList.size() > 0) {
           ZxSfHazardmainJu zxSfHazardmainJu = new ZxSfHazardmainJu();
           zxSfHazardmainJu.setModifyUserInfo(userKey, realName);
           flag = zxSfHazardmainJuMapper.batchDeleteUpdateZxSfHazardmainJu(zxSfHazardmainJuList, zxSfHazardmainJu);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSfHazardmainJuList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
