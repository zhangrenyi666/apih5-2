package com.apih5.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.date.DateUtil;
import com.apih5.mybatis.dao.ZxSysProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.dao.ZxSfEduItemMapper;
import com.apih5.mybatis.dao.ZxSfEduMapper;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.mybatis.pojo.ZxSfCheckItem;
import com.apih5.mybatis.pojo.ZxSfEdu;
import com.apih5.mybatis.pojo.ZxSfEduItem;
import com.apih5.service.ZxSfEduService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxSfEduService")
public class ZxSfEduServiceImpl implements ZxSfEduService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfEduMapper zxSfEduMapper;

    @Autowired(required = true)
    private ZxSfEduItemMapper zxSfEduItemMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxSysProjectMapper zxSysProjectMapper;

    @Override
    public ResponseEntity getZxSfEduListByCondition(ZxSfEdu zxSfEdu) {
        if (zxSfEdu == null) {
            zxSfEdu = new ZxSfEdu();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // ??????????????????
        if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSfEdu.setCompanyId("");
            zxSfEdu.setProjectId("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // ????????????????????????
            zxSfEdu.setCompanyId(zxSfEdu.getOrgID());
            // zxSfEdu.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // ???????????????????????????
            zxSfEdu.setProjectId(zxSfEdu.getOrgID());
        }
        zxSfEdu.setOrgID(zxSfEdu.getOrgID2());
        if (zxSfEdu.getBizDate() != null) {
            String a = String.valueOf(zxSfEdu.getBizDate().getTime()).substring(0, 10) + "000";
            Long b = Long.valueOf(a);
            java.sql.Date c = new java.sql.Date(b);
            zxSfEdu.setBizDate(c);
        }
        // ????????????
        PageHelper.startPage(zxSfEdu.getPage(), zxSfEdu.getLimit());
        // ????????????
        List<ZxSfEdu> zxSfEduList = zxSfEduMapper.selectByZxSfEduList(zxSfEdu);
        // ????????????
        for (ZxSfEdu zxSfEdu2 : zxSfEduList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSfEdu2.getZxSfEduId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSfEdu2.setFileList(zxErpFiles);
        }
        // ????????????
        for (ZxSfEdu zxSfEdu1 : zxSfEduList) {
            ZxSfEduItem zxSfEduItem = new ZxSfEduItem();
            zxSfEduItem.setEduID(zxSfEdu1.getZxSfEduId());
            List<ZxSfEduItem> zxSfEduItem1 = zxSfEduItemMapper.selectByZxSfEduItemList(zxSfEduItem);
            zxSfEdu1.setZxSfEduItemList(zxSfEduItem1);
        }
        // ??????????????????
        PageInfo<ZxSfEdu> p = new PageInfo<>(zxSfEduList);

        return repEntity.okList(zxSfEduList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfEduDetail(ZxSfEdu zxSfEdu) {
        if (zxSfEdu == null) {
            zxSfEdu = new ZxSfEdu();
        }
        // ????????????
        ZxSfEdu dbZxSfEdu = zxSfEduMapper.selectByPrimaryKey(zxSfEdu.getZxSfEduId());
        // ????????????
        if (dbZxSfEdu != null) {
            return repEntity.ok(dbZxSfEdu);
        } else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZxSfEdu(ZxSfEdu zxSfEdu) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfEdu.setZxSfEduId(UuidUtil.generate());
        zxSfEdu.setCreateUserInfo(userKey, realName);
        zxSfEdu.setProjectId(zxSfEdu.getOrgID());
        String companyId = zxSysProjectMapper.getCompanyIdByProjectId(zxSfEdu.getOrgID());
        zxSfEdu.setCompanyId(companyId);
        // ??????????????????
        List<ZxSfEduItem> zxSfEduItemList = zxSfEdu.getZxSfEduItemList();
        if (zxSfEduItemList != null && zxSfEduItemList.size() > 0) {
            for (ZxSfEduItem zxSfEduItem : zxSfEduItemList) {
                zxSfEduItem.setEduID(zxSfEdu.getZxSfEduId());
                zxSfEduItem.setZxSfEduItemId((UuidUtil.generate()));
                zxSfEduItem.setCreateUserInfo(userKey, realName);
                zxSfEduItemMapper.insert(zxSfEduItem);
            }
        }
        // ????????????
        List<ZxErpFile> fileList = zxSfEdu.getFileList();
        if (fileList != null && fileList.size() > 0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSfEdu.getZxSfEduId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        int flag = zxSfEduMapper.insert(zxSfEdu);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfEdu);
        }
    }

    @Override
    public ResponseEntity updateZxSfEdu(ZxSfEdu zxSfEdu) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfEdu dbZxSfEdu = zxSfEduMapper.selectByPrimaryKey(zxSfEdu.getZxSfEduId());
        if (dbZxSfEdu != null && StrUtil.isNotEmpty(dbZxSfEdu.getZxSfEduId())) {
            // ????????????
            dbZxSfEdu.setOrgName(zxSfEdu.getOrgName());
            // ??????ID
            dbZxSfEdu.setOrgID(zxSfEdu.getOrgID());
            // ????????????
            dbZxSfEdu.setTranName(zxSfEdu.getTranName());
            // ????????????
            dbZxSfEdu.setMainUnit(zxSfEdu.getMainUnit());
            // ????????????
            dbZxSfEdu.setAddress(zxSfEdu.getAddress());
            // ????????????
            dbZxSfEdu.setMaterials(zxSfEdu.getMaterials());
            // ??????
            dbZxSfEdu.setTeacher(zxSfEdu.getTeacher());
            // ??????
            dbZxSfEdu.setBizDate(zxSfEdu.getBizDate());
            // ??????
            dbZxSfEdu.setTranTime(zxSfEdu.getTranTime());
            // ????????????
            dbZxSfEdu.setTranContext(zxSfEdu.getTranContext());
            // ????????????
            dbZxSfEdu.setEditTime(zxSfEdu.getEditTime());
            // ??????
            dbZxSfEdu.setRemarks(zxSfEdu.getRemarks());
            // ??????
            dbZxSfEdu.setSort(zxSfEdu.getSort());
            // ??????
            dbZxSfEdu.setModifyUserInfo(userKey, realName);
            flag = zxSfEduMapper.updateByPrimaryKey(dbZxSfEdu);
            // ???????????????????????????
            ZxSfEduItem zxSfEduItemSelect = new ZxSfEduItem();
            zxSfEduItemSelect.setEduID(zxSfEdu.getZxSfEduId());
            List<ZxSfEduItem> zxSfEduItem1 = zxSfEduItemMapper.selectByZxSfEduItemList(zxSfEduItemSelect);
            if (zxSfEduItem1 != null && zxSfEduItem1.size() > 0) {
                zxSfEduItemSelect.setModifyUserInfo(userKey, realName);
                zxSfEduItemMapper.batchDeleteUpdateZxSfEduItem(zxSfEduItem1, zxSfEduItemSelect);
            }
            List<ZxSfEduItem> zxSfEduItemList = zxSfEdu.getZxSfEduItemList();
            if (zxSfEduItemList != null && zxSfEduItemList.size() > 0) {
                for (ZxSfEduItem zxSfEduItem : zxSfEduItemList) {
                    zxSfEduItem.setEduID(zxSfEdu.getZxSfEduId());
                    zxSfEduItem.setZxSfEduItemId((UuidUtil.generate()));
                    zxSfEduItem.setCreateUserInfo(userKey, realName);
                    zxSfEduItemMapper.insert(zxSfEduItem);
                }
            }

            // ???????????????(??????)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSfEdu.getZxSfEduId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if (zxErpFiles != null && zxErpFiles.size() > 0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            List<ZxErpFile> fileList = zxSfEdu.getFileList();
            if (fileList != null && fileList.size() > 0) {
                for (ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setOtherId(zxSfEdu.getZxSfEduId());
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
            return repEntity.ok("sys.data.update", zxSfEdu);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfEdu(List<ZxSfEdu> zxSfEduList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfEduList != null && zxSfEduList.size() > 0) {
            for (ZxSfEdu zxSfEdu1 : zxSfEduList) {
                //????????????
                ZxSfEduItem zxSfEduItemSelect = new ZxSfEduItem();
                zxSfEduItemSelect.setEduID(zxSfEdu1.getZxSfEduId());
                List<ZxSfEduItem> zxSfEduItem1 = zxSfEduItemMapper.selectByZxSfEduItemList(zxSfEduItemSelect);
                if (zxSfEduItem1 != null && zxSfEduItem1.size() > 0) {
                    zxSfEduItemSelect.setModifyUserInfo(userKey, realName);
                    zxSfEduItemMapper.batchDeleteUpdateZxSfEduItem(zxSfEduItem1, zxSfEduItemSelect);
                }
                // ????????????
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxSfEdu1.getZxSfEduId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if (zxErpFiles != null && zxErpFiles.size() > 0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
            }
            ZxSfEdu zxSfEdu = new ZxSfEdu();
            zxSfEdu.setModifyUserInfo(userKey, realName);
            flag = zxSfEduMapper.batchDeleteUpdateZxSfEdu(zxSfEduList, zxSfEdu);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSfEduList);
        }
    }

    // ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

    @Override
    public ResponseEntity getZxSfEduComList() {
        // ????????????
        List<ZxSfEdu> zxSfEduList = zxSfEduMapper.getZxSfEduComList();
        return repEntity.ok(zxSfEduList);
    }

    @Override
    public ResponseEntity getZxSfEduCom(ZxSfEdu zxSfEdu) {
        // ????????????
        ZxSfEdu dbZxSfEdu = zxSfEduMapper.getZxSfEduCom(zxSfEdu);
        // ????????????
        return repEntity.ok(dbZxSfEdu);
    }

    @Override
    public ResponseEntity getZxSfEduJuInfo(ZxSfEdu zxSfEdu) {
        // ????????????
        ZxSfEdu dbZxSfEdu = zxSfEduMapper.getZxSfEduJuInfo(zxSfEdu);
        // ????????????
        return repEntity.ok(dbZxSfEdu);
    }

    @Override
    public ResponseEntity getZxSfEduOrgList(ZxSfEdu zxSfEdu) {
        // ????????????
        List<ZxSfEdu> zxSfEduList = new ArrayList<>();

        String today = DateUtil.format(new Date(),"yyyyMMdd");

        ZxSfEdu zxSfEduGuiDang = zxSfEduMapper.getZxSfEduGuiDang(zxSfEdu, today);
        ZxSfEdu zxSfEduJiaoGong = zxSfEduMapper.getZxSfEduJiaoGong(zxSfEdu, today);
        ZxSfEdu zxSfEduWanGong = zxSfEduMapper.getZxSfEduWanGong(zxSfEdu, today);
        ZxSfEdu zxSfEduKaigong = zxSfEduMapper.getZxSfEduKaiGong(zxSfEdu, today);

        if (zxSfEduGuiDang != null) {
            zxSfEduList.add(zxSfEduGuiDang);
        }
        if (zxSfEduJiaoGong != null) {
            zxSfEduList.add(zxSfEduJiaoGong);
        }
        if (zxSfEduWanGong != null) {
            zxSfEduList.add(zxSfEduWanGong);
        }
        if (zxSfEduKaigong != null) {
            zxSfEduList.add(zxSfEduKaigong);
        }
        return repEntity.ok(zxSfEduList);
    }

    @Override
    public ResponseEntity getZxSfEduGuiDangList(ZxSfEdu zxSfEdu) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        // ????????????
        List<ZxSfEdu> guiDangList = zxSfEduMapper.getZxSfEduGuiDangList(zxSfEdu, today);
        return repEntity.ok(guiDangList);
    }

    @Override
    public ResponseEntity getZxSfEduJiaoGongList(ZxSfEdu zxSfEdu) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        // ????????????
        List<ZxSfEdu> jiaoDangList = zxSfEduMapper.getZxSfEduJiaoGongList(zxSfEdu, today);
        return repEntity.ok(jiaoDangList);
    }

    @Override
    public ResponseEntity getZxSfEduWanGongList(ZxSfEdu zxSfEdu) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        // ????????????
        List<ZxSfEdu> wanDangList = zxSfEduMapper.getZxSfEduWanGongList(zxSfEdu, today);
        return repEntity.ok(wanDangList);
    }

    @Override
    public ResponseEntity getZxSfEduKaiGongList(ZxSfEdu zxSfEdu) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        // ????????????
        List<ZxSfEdu> kaiDangList = zxSfEduMapper.getZxSfEduKaiGongList(zxSfEdu, today);
        return repEntity.ok(kaiDangList);
    }

}
