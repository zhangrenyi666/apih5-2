package com.apih5.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.framework.constant.SysConst;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.dao.ZxSfEAccidentItemMapper;
import com.apih5.mybatis.dao.ZxSfSWAccidentItemMapper;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.mybatis.pojo.ZxSfEAccidentItem;
import com.apih5.mybatis.pojo.ZxSfSWAccidentItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSfEAccidentMapper;
import com.apih5.mybatis.pojo.ZxSfEAccident;
import com.apih5.service.ZxSfEAccidentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSfEAccidentService")
public class ZxSfEAccidentServiceImpl implements ZxSfEAccidentService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfEAccidentMapper zxSfEAccidentMapper;

    @Autowired(required = true)
    private ZxSfEAccidentItemMapper zxSfEAccidentItemMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Override
    public ResponseEntity getZxSfEAccidentListByCondition(ZxSfEAccident zxSfEAccident) {
        if (zxSfEAccident == null) {
            zxSfEAccident = new ZxSfEAccident();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSfEAccident.setCompanyId("");
            zxSfEAccident.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxSfEAccident.setCompanyId(zxSfEAccident.getOrgId());
            zxSfEAccident.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxSfEAccident.setOrgId(zxSfEAccident.getOrgId());
        }
        // ????????????
        PageHelper.startPage(zxSfEAccident.getPage(),zxSfEAccident.getLimit());
        // ????????????
        List<ZxSfEAccident> zxSfEAccidentList = zxSfEAccidentMapper.selectByZxSfEAccidentList(zxSfEAccident);
        for (ZxSfEAccident zxSfEAccident1 : zxSfEAccidentList) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
            Date date = new Date();

            try {
                date = simpleDateFormat.parse(zxSfEAccident1.getYear());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            zxSfEAccident1.setDatePeriod(date);
            // ??????????????????????????????????????????????????? list
            ZxSfEAccidentItem dbZxSfEAccidentItem = new ZxSfEAccidentItem();
            dbZxSfEAccidentItem.setEaId(zxSfEAccident1.getZxSfEAccidentId());
            List<ZxSfEAccidentItem> zxSfEAccidentItemList = zxSfEAccidentItemMapper.selectByZxSfEAccidentItemList(dbZxSfEAccidentItem);
            zxSfEAccident1.setZxSfEAccidentItemList(zxSfEAccidentItemList);

            //????????????
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSfEAccident1.getZxSfEAccidentId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSfEAccident1.setZxErpFileList(zxErpFiles);
        }
        // ??????????????????
        PageInfo<ZxSfEAccident> p = new PageInfo<>(zxSfEAccidentList);

        return repEntity.okList(zxSfEAccidentList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfEAccidentDetail(ZxSfEAccident zxSfEAccident) {
        if (zxSfEAccident == null) {
            zxSfEAccident = new ZxSfEAccident();
        }
        // ????????????
        ZxSfEAccident dbZxSfEAccident = zxSfEAccidentMapper.selectByPrimaryKey(zxSfEAccident.getZxSfEAccidentId());
        // ????????????
        if (dbZxSfEAccident != null) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
            Date date = new Date();

            try {
                date = simpleDateFormat.parse(dbZxSfEAccident.getYear());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            dbZxSfEAccident.setDatePeriod(date);
            // ??????????????????????????????????????????????????? list
            ZxSfEAccidentItem dbZxSfEAccidentItem = new ZxSfEAccidentItem();
            dbZxSfEAccidentItem.setEaId(dbZxSfEAccident.getZxSfEAccidentId());
            List<ZxSfEAccidentItem> zxSfEAccidentItemList = zxSfEAccidentItemMapper.selectByZxSfEAccidentItemList(dbZxSfEAccidentItem);
            dbZxSfEAccident.setZxSfEAccidentItemList(zxSfEAccidentItemList);

            //??????
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxSfEAccident.getZxSfEAccidentId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            dbZxSfEAccident.setZxErpFileList(zxErpFiles);
            return repEntity.ok(dbZxSfEAccident);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxSfEAccident(ZxSfEAccident zxSfEAccident) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfEAccident.setZxSfEAccidentId(UuidUtil.generate());
        zxSfEAccident.setCreateUserInfo(userKey, realName);
        String result = new SimpleDateFormat("yyyyMM").format(zxSfEAccident.getDatePeriod());
        zxSfEAccident.setYear(result);
        int flag = zxSfEAccidentMapper.insert(zxSfEAccident);

        //  ?????????????????????????????????????????????????????????
        List<ZxSfEAccidentItem> zxSfEAccidentItemList = zxSfEAccident.getZxSfEAccidentItemList();
        if(zxSfEAccidentItemList != null && zxSfEAccidentItemList.size()>0) {
            for(ZxSfEAccidentItem zxSfEAccidentItem : zxSfEAccidentItemList) {
                zxSfEAccidentItem.setEaId(zxSfEAccident.getZxSfEAccidentId());
                zxSfEAccidentItem.setZxSfEAccidentItemId(UuidUtil.createUUID());
                zxSfEAccidentItem.setCreateUserInfo(userKey, realName);
                zxSfEAccidentItemMapper.insert(zxSfEAccidentItem);
            }
        }

        //????????????
        List<ZxErpFile> fileList = zxSfEAccident.getZxErpFileList();
        if (fileList != null && fileList.size() > 0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSfEAccident.getZxSfEAccidentId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfEAccident);
        }
    }

    @Override
    public ResponseEntity updateZxSfEAccident(ZxSfEAccident zxSfEAccident) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String result = new SimpleDateFormat("yyyyMM").format(zxSfEAccident.getDatePeriod());
        zxSfEAccident.setYear(result);
        int flag = 0;
        ZxSfEAccident dbZxSfEAccident = zxSfEAccidentMapper.selectByPrimaryKey(zxSfEAccident.getZxSfEAccidentId());
        if (dbZxSfEAccident != null && StrUtil.isNotEmpty(dbZxSfEAccident.getZxSfEAccidentId())) {
           // ????????????
           dbZxSfEAccident.setCreateDate(zxSfEAccident.getCreateDate());
           // ??????
           dbZxSfEAccident.setYear(zxSfEAccident.getYear());
           // ??????
           dbZxSfEAccident.setModifyUserInfo(userKey, realName);
           flag = zxSfEAccidentMapper.updateByPrimaryKey(dbZxSfEAccident);

            // ?????????????????????????????????????????????????????????
            ZxSfEAccidentItem delZxSfEAccidentItem = new ZxSfEAccidentItem();
            delZxSfEAccidentItem.setEaId(zxSfEAccident.getZxSfEAccidentId());
            List<ZxSfEAccidentItem> items = zxSfEAccidentItemMapper.selectByZxSfEAccidentItemList(delZxSfEAccidentItem);
            if (items != null && items.size() > 0) {
                delZxSfEAccidentItem.setModifyUserInfo(userKey, realName);
                zxSfEAccidentItemMapper.batchDeleteUpdateZxSfEAccidentItem(items, delZxSfEAccidentItem);
            }

            //  ?????????????????????????????????????????????????????????
            List<ZxSfEAccidentItem> zxSfEAccidentItemList = zxSfEAccident.getZxSfEAccidentItemList();
            if(zxSfEAccidentItemList != null && zxSfEAccidentItemList.size()>0) {
                for(ZxSfEAccidentItem zxSfEAccidentItem : zxSfEAccidentItemList) {
                    zxSfEAccidentItem.setEaId(zxSfEAccident.getZxSfEAccidentId());
                    zxSfEAccidentItem.setZxSfEAccidentItemId(UuidUtil.createUUID());
                    zxSfEAccidentItem.setCreateUserInfo(userKey, realName);
                    zxSfEAccidentItemMapper.insert(zxSfEAccidentItem);
                }
            }

            //???????????????(??????)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSfEAccident.getZxSfEAccidentId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if (zxErpFiles != null && zxErpFiles.size() > 0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }

            //??????list
            List<ZxErpFile> fileList = zxSfEAccident.getZxErpFileList();
            if (fileList != null && fileList.size() > 0) {
                for (ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setOtherId(zxSfEAccident.getZxSfEAccidentId());
                    zxErpFile.setUid((UuidUtil.generate()));
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSfEAccident);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfEAccident(List<ZxSfEAccident> zxSfEAccidentList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfEAccidentList != null && zxSfEAccidentList.size() > 0) {
            for (ZxSfEAccident zxSfEAccident : zxSfEAccidentList) {
                //????????????
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxSfEAccident.getZxSfEAccidentId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if (zxErpFiles != null && zxErpFiles.size() > 0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }

                // ?????????????????????????????????????????????????????????
                ZxSfEAccidentItem delZxSfEAccidentItem = new ZxSfEAccidentItem();
                delZxSfEAccidentItem.setEaId(zxSfEAccident.getZxSfEAccidentId());
                List<ZxSfEAccidentItem> items = zxSfEAccidentItemMapper.selectByZxSfEAccidentItemList(delZxSfEAccidentItem);
                if (items != null && items.size() > 0) {
                    delZxSfEAccidentItem.setModifyUserInfo(userKey, realName);
                    zxSfEAccidentItemMapper.batchDeleteUpdateZxSfEAccidentItem(items, delZxSfEAccidentItem);
                }
            }
           ZxSfEAccident zxSfEAccident = new ZxSfEAccident();
           zxSfEAccident.setModifyUserInfo(userKey, realName);
           flag = zxSfEAccidentMapper.batchDeleteUpdateZxSfEAccident(zxSfEAccidentList, zxSfEAccident);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSfEAccidentList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
}
