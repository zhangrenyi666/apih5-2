package com.apih5.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.date.DateUtil;
import com.apih5.framework.constant.SysConst;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxErpFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSfPlanTargetMapper;
import com.apih5.mybatis.pojo.ZxSfPlanTarget;
import com.apih5.service.ZxSfPlanTargetService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSfPlanTargetService")
public class ZxSfPlanTargetServiceImpl implements ZxSfPlanTargetService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfPlanTargetMapper zxSfPlanTargetMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Override
    public ResponseEntity getZxSfPlanTargetListByCondition(ZxSfPlanTarget zxSfPlanTarget) {
        if (zxSfPlanTarget == null) {
            zxSfPlanTarget = new ZxSfPlanTarget();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSfPlanTarget.setCompanyId("");
            zxSfPlanTarget.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSfPlanTarget.setCompanyId(zxSfPlanTarget.getOrgID());
            zxSfPlanTarget.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSfPlanTarget.setOrgID(zxSfPlanTarget.getOrgID());
        }


        // 分页查询
        PageHelper.startPage(zxSfPlanTarget.getPage(), zxSfPlanTarget.getLimit());
        // 获取数据
        List<ZxSfPlanTarget> zxSfPlanTargetList = zxSfPlanTargetMapper.selectByZxSfPlanTargetList(zxSfPlanTarget);

        for (ZxSfPlanTarget zxSfPlanTarget1 : zxSfPlanTargetList) {
            ZxErpFile file = new ZxErpFile();
            file.setOtherId(zxSfPlanTarget1.getZxSfPlanTargetId());
            List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(file);
            zxSfPlanTarget1.setZxErpFileList(zxErpFileList);
        }
        // 得到分页信息
        PageInfo<ZxSfPlanTarget> p = new PageInfo<>(zxSfPlanTargetList);

        return repEntity.okList(zxSfPlanTargetList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfPlanTargetDetail(ZxSfPlanTarget zxSfPlanTarget) {
        if (zxSfPlanTarget == null) {
            zxSfPlanTarget = new ZxSfPlanTarget();
        }
        // 获取数据
        ZxSfPlanTarget dbZxSfPlanTarget = zxSfPlanTargetMapper.selectByPrimaryKey(zxSfPlanTarget.getZxSfPlanTargetId());
        // 数据存在
        if (dbZxSfPlanTarget != null) {

            ZxErpFile file = new ZxErpFile();
            file.setOtherId(dbZxSfPlanTarget.getZxSfPlanTargetId());
            List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(file);
            dbZxSfPlanTarget.setZxErpFileList(zxErpFileList);
            return repEntity.ok(dbZxSfPlanTarget);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfPlanTarget(ZxSfPlanTarget zxSfPlanTarget) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfPlanTarget.setZxSfPlanTargetId(UuidUtil.generate());
        zxSfPlanTarget.setCreateUserInfo(userKey, realName);
        int flag = zxSfPlanTargetMapper.insert(zxSfPlanTarget);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            // 附件
            List<ZxErpFile> zxErpFileList = zxSfPlanTarget.getZxErpFileList();
            if (zxErpFileList != null && zxErpFileList.size() > 0) {
                for (ZxErpFile zxErpFile : zxErpFileList) {
                    zxErpFile.setOtherId(zxSfPlanTarget.getZxSfPlanTargetId());
                    zxErpFile.setOtherType("1");
                    zxErpFile.setUid((UuidUtil.generate()));
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    flag = zxErpFileMapper.insert(zxErpFile);
                    if (flag == 0) {
                        return repEntity.layerMessage("no", "附件上传失败！");
                    }
                }
            }
            return repEntity.ok("sys.data.sava", zxSfPlanTarget);
        }
    }

    @Override
    public ResponseEntity updateZxSfPlanTarget(ZxSfPlanTarget zxSfPlanTarget) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfPlanTarget dbZxSfPlanTarget = zxSfPlanTargetMapper.selectByPrimaryKey(zxSfPlanTarget.getZxSfPlanTargetId());
        if (dbZxSfPlanTarget != null && StrUtil.isNotEmpty(dbZxSfPlanTarget.getZxSfPlanTargetId())) {
            // 项目名称
            dbZxSfPlanTarget.setOrgName(zxSfPlanTarget.getOrgName());
            // 因工死亡率%
            dbZxSfPlanTarget.setDeadRate(zxSfPlanTarget.getDeadRate());
            // 重伤率%
            dbZxSfPlanTarget.setInjuresRate(zxSfPlanTarget.getInjuresRate());
            // 轻伤率%
            dbZxSfPlanTarget.setSlightlyRate(zxSfPlanTarget.getSlightlyRate());
            // 隐患整改率%
            dbZxSfPlanTarget.setHidChageRate(zxSfPlanTarget.getHidChageRate());
            // 项目ID
            dbZxSfPlanTarget.setOrgID(zxSfPlanTarget.getOrgID());
            // 更新时间
            dbZxSfPlanTarget.setEditTime(zxSfPlanTarget.getEditTime());
            // 公司名称
            dbZxSfPlanTarget.setCompName(zxSfPlanTarget.getCompName());
            // 年度
            dbZxSfPlanTarget.setYear(zxSfPlanTarget.getYear());
            // 备注
            dbZxSfPlanTarget.setRemarks(zxSfPlanTarget.getRemarks());
            // 排序
            dbZxSfPlanTarget.setSort(zxSfPlanTarget.getSort());
            // 共通
            dbZxSfPlanTarget.setModifyUserInfo(userKey, realName);
            flag = zxSfPlanTargetMapper.updateByPrimaryKey(dbZxSfPlanTarget);

            //附件先删除再新增
            ZxErpFile delFile = new ZxErpFile();
            delFile.setOtherId(zxSfPlanTarget.getZxSfPlanTargetId());
            List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
            if (delFileList != null && delFileList.size() > 0) {
                delFile.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
            }
            if (zxSfPlanTarget.getZxErpFileList() != null && zxSfPlanTarget.getZxErpFileList().size() > 0) {
                for (ZxErpFile file : zxSfPlanTarget.getZxErpFileList()) {
                    file.setUid(UuidUtil.generate());
                    file.setOtherId(zxSfPlanTarget.getZxSfPlanTargetId());
                    file.setCreateUserInfo(userKey, realName);
                    flag = zxErpFileMapper.insert(file);
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxSfPlanTarget);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfPlanTarget(List<ZxSfPlanTarget> zxSfPlanTargetList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfPlanTargetList != null && zxSfPlanTargetList.size() > 0) {
            ZxSfPlanTarget zxSfPlanTarget = new ZxSfPlanTarget();
            zxSfPlanTarget.setModifyUserInfo(userKey, realName);
            flag = zxSfPlanTargetMapper.batchDeleteUpdateZxSfPlanTarget(zxSfPlanTargetList, zxSfPlanTarget);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSfPlanTargetList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public ResponseEntity getJuInfo() {
        // 获取数据
        ZxSfPlanTarget zxSfPlanTarget = zxSfPlanTargetMapper.getJuInfo();
        // 数据存在
        return repEntity.ok(zxSfPlanTarget);
    }

    @Override
    public ResponseEntity getComList() {
        // 获取数据
        List<ZxSfPlanTarget> zxSfPlanTargetList = zxSfPlanTargetMapper.getComInfoList();
        return repEntity.ok(zxSfPlanTargetList);
    }

    @Override
    public ResponseEntity getComInfo(ZxSfPlanTarget zxSfPlanTarget) {
        // 获取数据
        ZxSfPlanTarget dbZxSfPlanList = zxSfPlanTargetMapper.getComInfo(zxSfPlanTarget);
        // 数据存在
        return repEntity.ok(dbZxSfPlanList);
    }

    @Override
    public ResponseEntity getOrgList(ZxSfPlanTarget zxSfPlanTarget) {
        // 获取数据
        List<ZxSfPlanTarget> zxSfEduList = new ArrayList<>();

        String today = DateUtil.format(new Date(),"yyyyMMdd");

        ZxSfPlanTarget zxSfPlanGuiDang = zxSfPlanTargetMapper.getGuiDang(zxSfPlanTarget, today);
        ZxSfPlanTarget zxSfPlanJiaoGong = zxSfPlanTargetMapper.getJiaoGong(zxSfPlanTarget, today);
        ZxSfPlanTarget zxSfPlanWanGong = zxSfPlanTargetMapper.getWanGong(zxSfPlanTarget, today);
        ZxSfPlanTarget zxSfPlanKaigong = zxSfPlanTargetMapper.getKaiGong(zxSfPlanTarget, today);

        if (zxSfPlanGuiDang != null) {
            zxSfEduList.add(zxSfPlanGuiDang);
        }
        if (zxSfPlanJiaoGong != null) {
            zxSfEduList.add(zxSfPlanJiaoGong);
        }
        if (zxSfPlanWanGong != null) {
            zxSfEduList.add(zxSfPlanWanGong);
        }
        if (zxSfPlanKaigong != null) {
            zxSfEduList.add(zxSfPlanKaigong);
        }
        return repEntity.ok(zxSfEduList);
    }

    @Override
    public ResponseEntity getGuiDangList(ZxSfPlanTarget zxSfPlanTarget) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        // 获取数据
        List<ZxSfPlanTarget> guiDangList = zxSfPlanTargetMapper.getGuiDangList(zxSfPlanTarget, today);
        return repEntity.ok(guiDangList);
    }

    @Override
    public ResponseEntity getJiaoGongList(ZxSfPlanTarget zxSfPlanTarget) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        // 获取数据
        List<ZxSfPlanTarget> jiaoGangList = zxSfPlanTargetMapper.getJiaoGongList(zxSfPlanTarget, today);
        return repEntity.ok(jiaoGangList);
    }

    @Override
    public ResponseEntity getWanGongList(ZxSfPlanTarget zxSfPlanTarget) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        // 获取数据
        List<ZxSfPlanTarget> wanDangList = zxSfPlanTargetMapper.getWanGongList(zxSfPlanTarget, today);
        return repEntity.ok(wanDangList);
    }

    @Override
    public ResponseEntity getKaiGongList(ZxSfPlanTarget zxSfPlanTarget) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        // 获取数据
        List<ZxSfPlanTarget> kaiDangList = zxSfPlanTargetMapper.getKaiGongList(zxSfPlanTarget, today);
        return repEntity.ok(kaiDangList);
    }
}
