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
        // 集团全部可见
        if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSfEdu.setCompanyId("");
            zxSfEdu.setProjectId("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSfEdu.setCompanyId(zxSfEdu.getOrgID());
            // zxSfEdu.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSfEdu.setProjectId(zxSfEdu.getOrgID());
        }
        zxSfEdu.setOrgID(zxSfEdu.getOrgID2());
        if (zxSfEdu.getBizDate() != null) {
            String a = String.valueOf(zxSfEdu.getBizDate().getTime()).substring(0, 10) + "000";
            Long b = Long.valueOf(a);
            java.sql.Date c = new java.sql.Date(b);
            zxSfEdu.setBizDate(c);
        }
        // 分页查询
        PageHelper.startPage(zxSfEdu.getPage(), zxSfEdu.getLimit());
        // 获取数据
        List<ZxSfEdu> zxSfEduList = zxSfEduMapper.selectByZxSfEduList(zxSfEdu);
        // 查询附件
        for (ZxSfEdu zxSfEdu2 : zxSfEduList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSfEdu2.getZxSfEduId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSfEdu2.setFileList(zxErpFiles);
        }
        // 查询明细
        for (ZxSfEdu zxSfEdu1 : zxSfEduList) {
            ZxSfEduItem zxSfEduItem = new ZxSfEduItem();
            zxSfEduItem.setEduID(zxSfEdu1.getZxSfEduId());
            List<ZxSfEduItem> zxSfEduItem1 = zxSfEduItemMapper.selectByZxSfEduItemList(zxSfEduItem);
            zxSfEdu1.setZxSfEduItemList(zxSfEduItem1);
        }
        // 得到分页信息
        PageInfo<ZxSfEdu> p = new PageInfo<>(zxSfEduList);

        return repEntity.okList(zxSfEduList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfEduDetail(ZxSfEdu zxSfEdu) {
        if (zxSfEdu == null) {
            zxSfEdu = new ZxSfEdu();
        }
        // 获取数据
        ZxSfEdu dbZxSfEdu = zxSfEduMapper.selectByPrimaryKey(zxSfEdu.getZxSfEduId());
        // 数据存在
        if (dbZxSfEdu != null) {
            return repEntity.ok(dbZxSfEdu);
        } else {
            return repEntity.layerMessage("no", "无数据！");
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
        // 新增明细数据
        List<ZxSfEduItem> zxSfEduItemList = zxSfEdu.getZxSfEduItemList();
        if (zxSfEduItemList != null && zxSfEduItemList.size() > 0) {
            for (ZxSfEduItem zxSfEduItem : zxSfEduItemList) {
                zxSfEduItem.setEduID(zxSfEdu.getZxSfEduId());
                zxSfEduItem.setZxSfEduItemId((UuidUtil.generate()));
                zxSfEduItem.setCreateUserInfo(userKey, realName);
                zxSfEduItemMapper.insert(zxSfEduItem);
            }
        }
        // 添加附件
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
            // 机构名称
            dbZxSfEdu.setOrgName(zxSfEdu.getOrgName());
            // 机构ID
            dbZxSfEdu.setOrgID(zxSfEdu.getOrgID());
            // 培训名称
            dbZxSfEdu.setTranName(zxSfEdu.getTranName());
            // 主办单位
            dbZxSfEdu.setMainUnit(zxSfEdu.getMainUnit());
            // 办班地点
            dbZxSfEdu.setAddress(zxSfEdu.getAddress());
            // 采用教材
            dbZxSfEdu.setMaterials(zxSfEdu.getMaterials());
            // 教员
            dbZxSfEdu.setTeacher(zxSfEdu.getTeacher());
            // 日期
            dbZxSfEdu.setBizDate(zxSfEdu.getBizDate());
            // 学时
            dbZxSfEdu.setTranTime(zxSfEdu.getTranTime());
            // 培训内容
            dbZxSfEdu.setTranContext(zxSfEdu.getTranContext());
            // 编辑时间
            dbZxSfEdu.setEditTime(zxSfEdu.getEditTime());
            // 备注
            dbZxSfEdu.setRemarks(zxSfEdu.getRemarks());
            // 排序
            dbZxSfEdu.setSort(zxSfEdu.getSort());
            // 共通
            dbZxSfEdu.setModifyUserInfo(userKey, realName);
            flag = zxSfEduMapper.updateByPrimaryKey(dbZxSfEdu);
            // 修改在新增明细数据
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

            // 修改在新增(附件)
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
        // 失败
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
                //删除明细
                ZxSfEduItem zxSfEduItemSelect = new ZxSfEduItem();
                zxSfEduItemSelect.setEduID(zxSfEdu1.getZxSfEduId());
                List<ZxSfEduItem> zxSfEduItem1 = zxSfEduItemMapper.selectByZxSfEduItemList(zxSfEduItemSelect);
                if (zxSfEduItem1 != null && zxSfEduItem1.size() > 0) {
                    zxSfEduItemSelect.setModifyUserInfo(userKey, realName);
                    zxSfEduItemMapper.batchDeleteUpdateZxSfEduItem(zxSfEduItem1, zxSfEduItemSelect);
                }
                // 删除附件
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
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSfEduList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public ResponseEntity getZxSfEduComList() {
        // 获取数据
        List<ZxSfEdu> zxSfEduList = zxSfEduMapper.getZxSfEduComList();
        return repEntity.ok(zxSfEduList);
    }

    @Override
    public ResponseEntity getZxSfEduCom(ZxSfEdu zxSfEdu) {
        // 获取数据
        ZxSfEdu dbZxSfEdu = zxSfEduMapper.getZxSfEduCom(zxSfEdu);
        // 数据存在
        return repEntity.ok(dbZxSfEdu);
    }

    @Override
    public ResponseEntity getZxSfEduJuInfo(ZxSfEdu zxSfEdu) {
        // 获取数据
        ZxSfEdu dbZxSfEdu = zxSfEduMapper.getZxSfEduJuInfo(zxSfEdu);
        // 数据存在
        return repEntity.ok(dbZxSfEdu);
    }

    @Override
    public ResponseEntity getZxSfEduOrgList(ZxSfEdu zxSfEdu) {
        // 获取数据
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
        // 获取数据
        List<ZxSfEdu> guiDangList = zxSfEduMapper.getZxSfEduGuiDangList(zxSfEdu, today);
        return repEntity.ok(guiDangList);
    }

    @Override
    public ResponseEntity getZxSfEduJiaoGongList(ZxSfEdu zxSfEdu) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        // 获取数据
        List<ZxSfEdu> jiaoDangList = zxSfEduMapper.getZxSfEduJiaoGongList(zxSfEdu, today);
        return repEntity.ok(jiaoDangList);
    }

    @Override
    public ResponseEntity getZxSfEduWanGongList(ZxSfEdu zxSfEdu) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        // 获取数据
        List<ZxSfEdu> wanDangList = zxSfEduMapper.getZxSfEduWanGongList(zxSfEdu, today);
        return repEntity.ok(wanDangList);
    }

    @Override
    public ResponseEntity getZxSfEduKaiGongList(ZxSfEdu zxSfEdu) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        // 获取数据
        List<ZxSfEdu> kaiDangList = zxSfEduMapper.getZxSfEduKaiGongList(zxSfEdu, today);
        return repEntity.ok(kaiDangList);
    }

}
