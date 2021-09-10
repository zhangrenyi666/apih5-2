package com.apih5.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.date.DateUtil;
import com.apih5.framework.constant.SysConst;
import com.apih5.mybatis.dao.ZxSysProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.dao.ZxSfProjectEmpMainMapper;
import com.apih5.mybatis.dao.ZxSfProjectEmployeeMapper;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.mybatis.pojo.ZxSfAccess;
import com.apih5.mybatis.pojo.ZxSfAccessItem;
import com.apih5.mybatis.pojo.ZxSfProjectEmpMain;
import com.apih5.mybatis.pojo.ZxSfProjectEmployee;
import com.apih5.service.ZxSfProjectEmpMainService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.StringUtils;

@Service("zxSfProjectEmpMainService")
public class ZxSfProjectEmpMainServiceImpl implements ZxSfProjectEmpMainService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfProjectEmpMainMapper zxSfProjectEmpMainMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxSfProjectEmployeeMapper zxSfProjectEmployeeMapper;

    @Autowired(required = true)
    private ZxSysProjectMapper zxSysProjectMapper;

    @Override
    public ResponseEntity getZxSfProjectEmpMainListByCondition(ZxSfProjectEmpMain zxSfProjectEmpMain) {
        if (zxSfProjectEmpMain == null) {
            zxSfProjectEmpMain = new ZxSfProjectEmpMain();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSfProjectEmpMain.setCompanyId("");
            // zxSfProjectEmpMain.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSfProjectEmpMain.setCompanyId(zxSfProjectEmpMain.getOrgID2());
            // zxSfProjectEmpMain.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSfProjectEmpMain.setProjectId(zxSfProjectEmpMain.getOrgID2());
        }
        // 分页查询
        PageHelper.startPage(zxSfProjectEmpMain.getPage(), zxSfProjectEmpMain.getLimit());
        // 获取数据
        List<ZxSfProjectEmpMain> zxSfProjectEmpMainList = zxSfProjectEmpMainMapper.selectByZxSfProjectEmpMainList(zxSfProjectEmpMain);
        // 查询明细
        for (ZxSfProjectEmpMain zxSfProjectEmpMain1 : zxSfProjectEmpMainList) {
            ZxSfProjectEmployee zxSfProjectEmployee = new ZxSfProjectEmployee();
            zxSfProjectEmployee.setMainID(zxSfProjectEmpMain1.getZxSfProjectEmpMainId());
            List<ZxSfProjectEmployee> zxSfProjectEmployee1 = zxSfProjectEmployeeMapper.selectByZxSfProjectEmployeeList(zxSfProjectEmployee);
            zxSfProjectEmpMain1.setZxSfProjectEmployeeList(zxSfProjectEmployee1);
            for (ZxSfProjectEmployee zxSfProjectEmployee2 : zxSfProjectEmployee1) {
                java.util.Date date = new Date();
                if (zxSfProjectEmployee2.getUseEndDate() != null) {
                    //比较时间
                    //获取系统时间
                    int sjUse = zxSfProjectEmployee2.getUseEndDate().compareTo(date);
                    if (sjUse < 0) {
                        zxSfProjectEmpMain1.setColorflag("1");
                    }
                }
                if (zxSfProjectEmployee2.getBuildEndDate() != null) {
                    int sjBuild = zxSfProjectEmployee2.getBuildEndDate().compareTo(date);
                    if (sjBuild < 0) {
                        zxSfProjectEmpMain1.setColorflag("1");
                    }
                }
            }
        }
        // 查询附件
        for (ZxSfProjectEmpMain zxSfProjectEmpMain2 : zxSfProjectEmpMainList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSfProjectEmpMain2.getZxSfProjectEmpMainId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSfProjectEmpMain2.setFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxSfProjectEmpMain> p = new PageInfo<>(zxSfProjectEmpMainList);

        return repEntity.okList(zxSfProjectEmpMainList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfProjectEmpMainDetail(ZxSfProjectEmpMain zxSfProjectEmpMain) {
        if (zxSfProjectEmpMain == null) {
            zxSfProjectEmpMain = new ZxSfProjectEmpMain();
        }
        // 获取数据
        ZxSfProjectEmpMain dbZxSfProjectEmpMain = zxSfProjectEmpMainMapper.selectByPrimaryKey(zxSfProjectEmpMain.getZxSfProjectEmpMainId());
        // 数据存在
        if (dbZxSfProjectEmpMain != null) {
            // 附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSfProjectEmpMain.getZxSfProjectEmpMainId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSfProjectEmpMain.setFileList(zxErpFiles);
            return repEntity.ok(dbZxSfProjectEmpMain);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfProjectEmpMain(ZxSfProjectEmpMain zxSfProjectEmpMain) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfProjectEmpMain.setZxSfProjectEmpMainId(UuidUtil.generate());
        zxSfProjectEmpMain.setCreateUserInfo(userKey, realName);
        String companyId = zxSysProjectMapper.getCompanyIdByProjectId(zxSfProjectEmpMain.getOrgID());
        if (StringUtils.isEmpty(companyId)) {
            zxSfProjectEmpMain.setCompanyId(zxSfProjectEmpMain.getOrgID());
        } else {
            zxSfProjectEmpMain.setProjectId(zxSfProjectEmpMain.getOrgID());
            zxSfProjectEmpMain.setCompanyId(companyId);
        }
        // 添加明细
        List<ZxSfProjectEmployee> zxSfProjectEmployeeList = zxSfProjectEmpMain.getZxSfProjectEmployeeList();
        if (zxSfProjectEmployeeList != null && zxSfProjectEmployeeList.size() > 0) {
            for (ZxSfProjectEmployee zxSfProjectEmployee : zxSfProjectEmployeeList) {
                zxSfProjectEmployee.setMainID(zxSfProjectEmpMain.getZxSfProjectEmpMainId());
                zxSfProjectEmployee.setZxSfProjectEmployeeId((UuidUtil.generate()));
                zxSfProjectEmployee.setCreateUserInfo(userKey, realName);
                zxSfProjectEmployeeMapper.insert(zxSfProjectEmployee);
            }
        }
        // 添加附件
        List<ZxErpFile> fileList = zxSfProjectEmpMain.getFileList();
        if (fileList != null && fileList.size() > 0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSfProjectEmpMain.getZxSfProjectEmpMainId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        int flag = zxSfProjectEmpMainMapper.insert(zxSfProjectEmpMain);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfProjectEmpMain);
        }
    }

    @Override
    public ResponseEntity updateZxSfProjectEmpMain(ZxSfProjectEmpMain zxSfProjectEmpMain) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfProjectEmpMain dbZxSfProjectEmpMain = zxSfProjectEmpMainMapper.selectByPrimaryKey(zxSfProjectEmpMain.getZxSfProjectEmpMainId());
        if (dbZxSfProjectEmpMain != null && StrUtil.isNotEmpty(dbZxSfProjectEmpMain.getZxSfProjectEmpMainId())) {
            // 机构名称
            dbZxSfProjectEmpMain.setOrgID(zxSfProjectEmpMain.getOrgID());
            // 编制人
            dbZxSfProjectEmpMain.setReporter(zxSfProjectEmpMain.getReporter());
            // 编辑时间
            dbZxSfProjectEmpMain.setEditTime(zxSfProjectEmpMain.getEditTime());
            // 备注
            dbZxSfProjectEmpMain.setRemarks(zxSfProjectEmpMain.getRemarks());
            // 排序
            dbZxSfProjectEmpMain.setSort(zxSfProjectEmpMain.getSort());
            // 共通
            dbZxSfProjectEmpMain.setModifyUserInfo(userKey, realName);
            flag = zxSfProjectEmpMainMapper.updateByPrimaryKey(dbZxSfProjectEmpMain);
            // 修改在新增明细数据
            ZxSfProjectEmployee zxSfProjectEmployeeSelect = new ZxSfProjectEmployee();
            zxSfProjectEmployeeSelect.setMainID(zxSfProjectEmpMain.getZxSfProjectEmpMainId());
            List<ZxSfProjectEmployee> zxSfProjectEmployee1 = zxSfProjectEmployeeMapper.selectByZxSfProjectEmployeeList(zxSfProjectEmployeeSelect);
            if (zxSfProjectEmployee1 != null && zxSfProjectEmployee1.size() > 0) {
                zxSfProjectEmployeeSelect.setModifyUserInfo(userKey, realName);
                zxSfProjectEmployeeMapper.batchDeleteUpdateZxSfProjectEmployee(zxSfProjectEmployee1, zxSfProjectEmployeeSelect);
            }
            List<ZxSfProjectEmployee> zxSfProjectEmployeeList = zxSfProjectEmpMain.getZxSfProjectEmployeeList();
            if (zxSfProjectEmployeeList != null && zxSfProjectEmployeeList.size() > 0) {
                for (ZxSfProjectEmployee zxSfProjectEmployee : zxSfProjectEmployeeList) {
                    zxSfProjectEmployee.setMainID(zxSfProjectEmpMain.getZxSfProjectEmpMainId());
                    zxSfProjectEmployee.setZxSfProjectEmployeeId((UuidUtil.generate()));
                    zxSfProjectEmployee.setCreateUserInfo(userKey, realName);
                    zxSfProjectEmployeeMapper.insert(zxSfProjectEmployee);
                }
            }
            // 修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSfProjectEmpMain.getZxSfProjectEmpMainId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if (zxErpFiles != null && zxErpFiles.size() > 0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            List<ZxErpFile> fileList = zxSfProjectEmpMain.getFileList();
            if (fileList != null && fileList.size() > 0) {
                for (ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setOtherId(zxSfProjectEmpMain.getZxSfProjectEmpMainId());
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
            return repEntity.ok("sys.data.update", zxSfProjectEmpMain);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfProjectEmpMain(List<ZxSfProjectEmpMain> zxSfProjectEmpMainList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfProjectEmpMainList != null && zxSfProjectEmpMainList.size() > 0) {
            for (ZxSfProjectEmpMain zxSfProjectEmpMain1 : zxSfProjectEmpMainList) {
                // 删除明细
                ZxSfProjectEmployee zxSfProjectEmployeeSelect = new ZxSfProjectEmployee();
                zxSfProjectEmployeeSelect.setMainID(zxSfProjectEmpMain1.getZxSfProjectEmpMainId());
                List<ZxSfProjectEmployee> zxSfProjectEmployee1 = zxSfProjectEmployeeMapper.selectByZxSfProjectEmployeeList(zxSfProjectEmployeeSelect);
                if (zxSfProjectEmployee1 != null && zxSfProjectEmployee1.size() > 0) {
                    zxSfProjectEmployeeSelect.setModifyUserInfo(userKey, realName);
                    zxSfProjectEmployeeMapper.batchDeleteUpdateZxSfProjectEmployee(zxSfProjectEmployee1, zxSfProjectEmployeeSelect);
                }
                // 删除附件
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxSfProjectEmpMain1.getZxSfProjectEmpMainId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if (zxErpFiles != null && zxErpFiles.size() > 0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
                ZxSfProjectEmpMain zxSfProjectEmpMain = new ZxSfProjectEmpMain();
                zxSfProjectEmpMain.setModifyUserInfo(userKey, realName);
                flag = zxSfProjectEmpMainMapper.batchDeleteUpdateZxSfProjectEmpMain(zxSfProjectEmpMainList, zxSfProjectEmpMain);
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSfProjectEmpMainList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    /**
     * 查询局级人员学历列表
     *
     * @author suncg
     */
    @Override
    public ResponseEntity getZxSfProEmpMainJuInfo() {
        List<ZxSfProjectEmpMain> zxSfProjectEmpMainList = zxSfProjectEmpMainMapper.getZxSfProEmpMainJuInfo();
        return repEntity.ok(zxSfProjectEmpMainList);
    }


    /**
     * 查询局人员学历统计
     *
     * @param zxSfProjectEmpMain
     * @author suncg
     */
    @Override
    public ResponseEntity getJuInfo(ZxSfProjectEmpMain zxSfProjectEmpMain) {
        ZxSfProjectEmpMain zxSfProjectEmpMain1ComInfo = zxSfProjectEmpMainMapper.getJuInfo(zxSfProjectEmpMain);
        return repEntity.ok(zxSfProjectEmpMain1ComInfo);
    }

    /**
     * 查询公司人员学历统计
     *
     * @param zxSfProjectEmpMain
     * @author suncg
     */
    @Override
    public ResponseEntity getZxSfProEmpMainComInfo(ZxSfProjectEmpMain zxSfProjectEmpMain) {
        ZxSfProjectEmpMain zxSfProjectEmpMain1ComInfo = zxSfProjectEmpMainMapper.getZxSfProEmpMainComInfo(zxSfProjectEmpMain);
        return repEntity.ok(zxSfProjectEmpMain1ComInfo);
    }

    /**
     * 查询项目人员学历统计
     *
     * @param zxSfProjectEmpMain
     * @author suncg
     */
    @Override
    public ResponseEntity getZxSfProEmpMainOrgList(ZxSfProjectEmpMain zxSfProjectEmpMain) {
        List<ZxSfProjectEmpMain> zxSfProjectEmpMainOrgList = new ArrayList<>();
        Date date = new Date();//获取当前的日期
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        String today = df.format(date);//获取String类型的时间
        ZxSfProjectEmpMain zxSfProjectEmpMainGuiDang = zxSfProjectEmpMainMapper.getZxSfProEmpMainGuiDang(zxSfProjectEmpMain, today);
        ZxSfProjectEmpMain zxSfProjectEmpMain1JiaoGong = zxSfProjectEmpMainMapper.getZxSfProEmpMainJiaoGong(zxSfProjectEmpMain, today);
        ZxSfProjectEmpMain zxSfProjectEmpMain1WanGong = zxSfProjectEmpMainMapper.getZxSfProEmpMainWanGong(zxSfProjectEmpMain, today);
        ZxSfProjectEmpMain zxSfProjectEmpMain1KaiGong = zxSfProjectEmpMainMapper.getZxSfProEmpMainKaiGong(zxSfProjectEmpMain, today);

        if (zxSfProjectEmpMainGuiDang != null) {
            zxSfProjectEmpMainOrgList.add(zxSfProjectEmpMainGuiDang);
        }
        if (zxSfProjectEmpMain1JiaoGong != null) {
            zxSfProjectEmpMainOrgList.add(zxSfProjectEmpMain1JiaoGong);
        }
        if (zxSfProjectEmpMain1WanGong != null) {
            zxSfProjectEmpMainOrgList.add(zxSfProjectEmpMain1WanGong);
        }
        if (zxSfProjectEmpMain1KaiGong != null) {
            zxSfProjectEmpMainOrgList.add(zxSfProjectEmpMain1KaiGong);
        }
        return repEntity.ok(zxSfProjectEmpMainOrgList);
    }

    /**
     * 查询归档项目人员学历统计
     *
     * @param zxSfProjectEmpMain
     * @author suncg
     */
    @Override
    public ResponseEntity getZxSfProEmpMainGuiDangList(ZxSfProjectEmpMain zxSfProjectEmpMain) {
        Date date = new Date();//获取当前的日期
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        String today = df.format(date);//获取String类型的时间
        List<ZxSfProjectEmpMain> zxSfProjectEmpGuiDangList = zxSfProjectEmpMainMapper.getZxSfProEmpMainGuiDangList(zxSfProjectEmpMain, today);
        return repEntity.ok(zxSfProjectEmpGuiDangList);
    }

    /**
     * 查询交工项目人员学历统计
     *
     * @param zxSfProjectEmpMain
     * @author suncg
     */
    @Override
    public ResponseEntity getZxSfProEmpMainJiaoGongList(ZxSfProjectEmpMain zxSfProjectEmpMain) {
        Date date = new Date();//获取当前的日期
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        String today = df.format(date);//获取String类型的时间
        List<ZxSfProjectEmpMain> zxSfProjectEmpJiaoGongList = zxSfProjectEmpMainMapper.getZxSfProEmpMainJiaoGongList(zxSfProjectEmpMain, today);
        return repEntity.ok(zxSfProjectEmpJiaoGongList);
    }

    /**
     * 查询完工项目人员学历统计
     *
     * @param zxSfProjectEmpMain
     * @author suncg
     */
    @Override
    public ResponseEntity getZxSfProEmpMainWanGongList(ZxSfProjectEmpMain zxSfProjectEmpMain) {
        Date date = new Date();//获取当前的日期
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        String today = df.format(date);//获取String类型的时间
        List<ZxSfProjectEmpMain> zxSfProjectEmpWanGongList = zxSfProjectEmpMainMapper.getZxSfProEmpMainWanGongList(zxSfProjectEmpMain, today);
        return repEntity.ok(zxSfProjectEmpWanGongList);
    }

    /**
     * 查询开工项目人员学历统计
     *
     * @param zxSfProjectEmpMain
     * @author suncg
     */
    @Override
    public ResponseEntity getZxSfProEmpMainKaiGongList(ZxSfProjectEmpMain zxSfProjectEmpMain) {
        Date date = new Date();//获取当前的日期
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        String today = df.format(date);//获取String类型的时间
        List<ZxSfProjectEmpMain> zxSfProjectEmpKaiGongList = zxSfProjectEmpMainMapper.getZxSfProEmpMainKaiGongList(zxSfProjectEmpMain, today);
        return repEntity.ok(zxSfProjectEmpKaiGongList);
    }


    /**
     * 查询局级人员职称列表
     *
     * @author suncg
     */
    @Override
    public ResponseEntity getZhiChengComList() {
        List<ZxSfProjectEmpMain> zxSfProjectEmpMainList = zxSfProjectEmpMainMapper.getZhiChengComList();
        return repEntity.ok(zxSfProjectEmpMainList);
    }

    /**
     * 查询公司人员职称统计
     *
     * @param zxSfProjectEmpMain
     * @author suncg
     */
    @Override
    public ResponseEntity getZhiChengComInfo(ZxSfProjectEmpMain zxSfProjectEmpMain) {
        ZxSfProjectEmpMain zxSfProjectEmpMain1ComInfo = zxSfProjectEmpMainMapper.getZhiChengComInfo(zxSfProjectEmpMain);
        return repEntity.ok(zxSfProjectEmpMain1ComInfo);
    }

    /**
     * 查询公司人员职称统计
     *
     * @param zxSfProjectEmpMain
     * @author suncg
     */
    @Override
    public ResponseEntity getZhiChengJuInfo(ZxSfProjectEmpMain zxSfProjectEmpMain) {
        ZxSfProjectEmpMain zxSfProjectEmpMain1ComInfo = zxSfProjectEmpMainMapper.getZhiChengJuInfo(zxSfProjectEmpMain);
        return repEntity.ok(zxSfProjectEmpMain1ComInfo);
    }

    /**
     * 查询项目人员职称统计
     *
     * @param zxSfProjectEmpMain
     * @author suncg
     */
    @Override
    public ResponseEntity getZxSfProEmpZhiChengOrgList(ZxSfProjectEmpMain zxSfProjectEmpMain) {
        List<ZxSfProjectEmpMain> zxSfProEmpZhiChengOrgList = new ArrayList<>();
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        ZxSfProjectEmpMain ZhiChengGuiDang = zxSfProjectEmpMainMapper.getZhiChengGuiDang(zxSfProjectEmpMain, today);
        ZxSfProjectEmpMain ZhiChengJiaoGong = zxSfProjectEmpMainMapper.getZhiChengJiaoGong(zxSfProjectEmpMain, today);
        ZxSfProjectEmpMain ZhiChengWanGong = zxSfProjectEmpMainMapper.getZhiChengWanGong(zxSfProjectEmpMain, today);
        ZxSfProjectEmpMain ZhiChengKaiGong = zxSfProjectEmpMainMapper.getZhiChengKaiGong(zxSfProjectEmpMain, today);

        if (ZhiChengGuiDang != null) {
            zxSfProEmpZhiChengOrgList.add(ZhiChengGuiDang);
        }
        if (ZhiChengJiaoGong != null) {
            zxSfProEmpZhiChengOrgList.add(ZhiChengJiaoGong);
        }
        if (ZhiChengWanGong != null) {
            zxSfProEmpZhiChengOrgList.add(ZhiChengWanGong);
        }
        if (ZhiChengKaiGong != null) {
            zxSfProEmpZhiChengOrgList.add(ZhiChengKaiGong);
        }
        return repEntity.ok(zxSfProEmpZhiChengOrgList);
    }

    /**
     * 查询归档项目人员职称统计
     *
     * @param zxSfProjectEmpMain
     * @author suncg
     */
    @Override
    public ResponseEntity getZhiChengGuiDangList(ZxSfProjectEmpMain zxSfProjectEmpMain) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        List<ZxSfProjectEmpMain> ZhiChengGuiDangList = zxSfProjectEmpMainMapper.getZhiChengGuiDangList(zxSfProjectEmpMain, today);
        return repEntity.ok(ZhiChengGuiDangList);
    }

    /**
     * 查询交工项目人员职称统计
     *
     * @param zxSfProjectEmpMain
     * @author suncg
     */
    @Override
    public ResponseEntity getZhiChengJiaoGongList(ZxSfProjectEmpMain zxSfProjectEmpMain) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        List<ZxSfProjectEmpMain> ZhiChengJiaoGongList = zxSfProjectEmpMainMapper.getZhiChengJiaoGongList(zxSfProjectEmpMain, today);
        return repEntity.ok(ZhiChengJiaoGongList);
    }

    /**
     * 查询完工项目人员职称统计
     *
     * @param zxSfProjectEmpMain
     * @author suncg
     */
    @Override
    public ResponseEntity getZhiChengWanGongList(ZxSfProjectEmpMain zxSfProjectEmpMain) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        List<ZxSfProjectEmpMain> ZhiChengWanGongList = zxSfProjectEmpMainMapper.getZhiChengWanGongList(zxSfProjectEmpMain, today);
        return repEntity.ok(ZhiChengWanGongList);
    }

    /**
     * 查询开工项目人员职称统计
     *
     * @param zxSfProjectEmpMain
     * @author suncg
     */
    @Override
    public ResponseEntity getZhiChengKaiGongList(ZxSfProjectEmpMain zxSfProjectEmpMain) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        List<ZxSfProjectEmpMain> ZhiChengKaiGongList = zxSfProjectEmpMainMapper.getZhiChengKaiGongList(zxSfProjectEmpMain, today);
        return repEntity.ok(ZhiChengKaiGongList);
    }


    /**
     * 查询公司列表人员工龄列表
     *
     * @author suncg
     */
    @Override
    public ResponseEntity getWorkAgeComList() {
        List<ZxSfProjectEmpMain> zxSfProjectEmpMainList = zxSfProjectEmpMainMapper.getWorkAgeComList();
        return repEntity.ok(zxSfProjectEmpMainList);
    }

    /**
     * 查询公司人员工龄统计
     *
     * @param zxSfProjectEmpMain
     * @author suncg
     */
    @Override
    public ResponseEntity getWorkAgeComInfo(ZxSfProjectEmpMain zxSfProjectEmpMain) {
        ZxSfProjectEmpMain zxSfProjectEmpMain1ComInfo = zxSfProjectEmpMainMapper.getWorkAgeComInfo(zxSfProjectEmpMain);
        return repEntity.ok(zxSfProjectEmpMain1ComInfo);
    }

    /**
     * 查询公司人员工龄统计
     *
     * @param zxSfProjectEmpMain
     * @author suncg
     */
    @Override
    public ResponseEntity getWorkAgeJuInfo(ZxSfProjectEmpMain zxSfProjectEmpMain) {
        ZxSfProjectEmpMain zxSfProjectEmpMain1ComInfo = zxSfProjectEmpMainMapper.getWorkAgeJuInfo(zxSfProjectEmpMain);
        return repEntity.ok(zxSfProjectEmpMain1ComInfo);
    }


    /**
     * 查询项目人员职称统计
     *
     * @param zxSfProjectEmpMain
     * @author suncg
     */
    @Override
    public ResponseEntity getZxSfProEmpWorkAgeOrgList(ZxSfProjectEmpMain zxSfProjectEmpMain) {
        List<ZxSfProjectEmpMain> zxSfProEmpWorkAgeOrgList = new ArrayList<>();
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        ZxSfProjectEmpMain WorkAgeGuiDang = zxSfProjectEmpMainMapper.getWorkAgeGuiDang(zxSfProjectEmpMain, today);
        ZxSfProjectEmpMain WorkAgeJiaoGong = zxSfProjectEmpMainMapper.getWorkAgeJiaoGong(zxSfProjectEmpMain, today);
        ZxSfProjectEmpMain WorkAgeWanGong = zxSfProjectEmpMainMapper.getWorkAgeWanGong(zxSfProjectEmpMain, today);
        ZxSfProjectEmpMain WorkAgeKaiGong = zxSfProjectEmpMainMapper.getWorkAgeKaiGong(zxSfProjectEmpMain, today);

        if (WorkAgeGuiDang != null) {
            zxSfProEmpWorkAgeOrgList.add(WorkAgeGuiDang);
        }
        if (WorkAgeJiaoGong != null) {
            zxSfProEmpWorkAgeOrgList.add(WorkAgeJiaoGong);
        }
        if (WorkAgeWanGong != null) {
            zxSfProEmpWorkAgeOrgList.add(WorkAgeWanGong);
        }
        if (WorkAgeKaiGong != null) {
            zxSfProEmpWorkAgeOrgList.add(WorkAgeKaiGong);
        }
        return repEntity.ok(zxSfProEmpWorkAgeOrgList);
    }

    /**
     * 查询归档项目人员工龄统计
     *
     * @param zxSfProjectEmpMain
     * @author suncg
     */
    @Override
    public ResponseEntity getWorkAgeGuiDangList(ZxSfProjectEmpMain zxSfProjectEmpMain) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        List<ZxSfProjectEmpMain> WorkAgeGuiDangList = zxSfProjectEmpMainMapper.getWorkAgeGuiDangList(zxSfProjectEmpMain, today);
        return repEntity.ok(WorkAgeGuiDangList);
    }

    /**
     * 查询交工项目人员工龄统计
     *
     * @param zxSfProjectEmpMain
     * @author suncg
     */
    @Override
    public ResponseEntity getWorkAgeJiaoGongList(ZxSfProjectEmpMain zxSfProjectEmpMain) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        List<ZxSfProjectEmpMain> WorkAgeJiaoGongList = zxSfProjectEmpMainMapper.getWorkAgeJiaoGongList(zxSfProjectEmpMain, today);
        return repEntity.ok(WorkAgeJiaoGongList);
    }

    /**
     * 查询完工项目人员工龄统计
     *
     * @param zxSfProjectEmpMain
     * @author suncg
     */
    @Override
    public ResponseEntity getWorkAgeWanGongList(ZxSfProjectEmpMain zxSfProjectEmpMain) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        List<ZxSfProjectEmpMain> WorkAgeWanGongList = zxSfProjectEmpMainMapper.getWorkAgeWanGongList(zxSfProjectEmpMain, today);
        return repEntity.ok(WorkAgeWanGongList);
    }

    /**
     * 查询开工项目人员工龄统计
     *
     * @param zxSfProjectEmpMain
     * @author suncg
     */
    @Override
    public ResponseEntity getWorkAgeKaiGongList(ZxSfProjectEmpMain zxSfProjectEmpMain) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        List<ZxSfProjectEmpMain> WorkAgeKaiGongList = zxSfProjectEmpMainMapper.getWorkAgeKaiGongList(zxSfProjectEmpMain, today);
        return repEntity.ok(WorkAgeKaiGongList);
    }


}
