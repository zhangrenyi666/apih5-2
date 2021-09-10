package com.apih5.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.apih5.mybatis.dao.ZxSysProjectMapper;
import com.apih5.mybatis.pojo.ZxSysProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.dao.ZxSfFeeMapper;
import com.apih5.mybatis.pojo.ZxCrCustomerExtAttr;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.mybatis.pojo.ZxSfFee;
import com.apih5.service.ZxSfFeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.util.CollectionUtils;

@Service("zxSfFeeService")
public class ZxSfFeeServiceImpl implements ZxSfFeeService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfFeeMapper zxSfFeeMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxSysProjectMapper zxSysProjectMapper;

    @Override
    public ResponseEntity getZxSfFeeListByCondition(ZxSfFee zxSfFee) {
        if (zxSfFee == null) {
            zxSfFee = new ZxSfFee();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSfFee.setCompanyId("");
            zxSfFee.setProjectId("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSfFee.setCompanyId(zxSfFee.getOrgID());
            zxSfFee.setProjectId("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSfFee.setProjectId(zxSfFee.getOrgID());
        }
        zxSfFee.setOrgID("");
        zxSfFee.setIsCompany("");
        if (!CollectionUtils.isEmpty(zxSfFee.getSeasonQuery())) {
            List<Date> seasonQuery = new ArrayList<>();
            // 季度筛选 参数seasonQuery [起始,结束] 范围筛选
            Calendar from = Calendar.getInstance();
            from.setTime(zxSfFee.getSeasonQuery().get(0));
            int month1 = from.get(Calendar.MONTH) + 1;
            int season1 = month1%3 == 0 ? month1/3 : month1/3 +1;
            // The first month of the quarter
            int firstMonth = season1*3-2;
            from.set(Calendar.MONTH, firstMonth - 1);
            from.set(Calendar.DATE, 1);
            Date start = from.getTime();
            seasonQuery.add(start);
            if (zxSfFee.getSeasonQuery().size() > 1) {
                Calendar to = Calendar.getInstance();
                to.setTime(zxSfFee.getSeasonQuery().get(1));
                int month2 = to.get(Calendar.MONTH) + 1;
                int season2 = month2%3 == 0 ? month2/3 : month2/3 +1;
                // The last month of the quarter
                int lastMonth = season2*3;
                to.set(Calendar.MONTH, lastMonth - 1);
                to.set(Calendar.DATE, 1);
                Date end = to.getTime();
                seasonQuery.add(end);
            }
            zxSfFee.setSeasonQuery(seasonQuery);
        }
        // 分页查询
        PageHelper.startPage(zxSfFee.getPage(), zxSfFee.getLimit());
        // 获取数据
        List<ZxSfFee> zxSfFeeList = zxSfFeeMapper.selectByZxSfFeeList(zxSfFee);
        //查询附件
        for (ZxSfFee zxSfFee1 : zxSfFeeList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSfFee1.getZxSfFeeId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSfFee1.setFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxSfFee> p = new PageInfo<>(zxSfFeeList);

        return repEntity.okList(zxSfFeeList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfFeeDetail(ZxSfFee zxSfFee) {
        if (zxSfFee == null) {
            zxSfFee = new ZxSfFee();
        }
        // 获取数据
        ZxSfFee dbZxSfFee = zxSfFeeMapper.selectByPrimaryKey(zxSfFee.getZxSfFeeId());
        // 数据存在
        if (dbZxSfFee != null) {
            //附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSfFee.getZxSfFeeId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSfFee.setFileList(zxErpFiles);
            return repEntity.ok(dbZxSfFee);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfFee(ZxSfFee zxSfFee) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfFee.setZxSfFeeId(UuidUtil.generate());
        zxSfFee.setCreateUserInfo(userKey, realName);
        if (zxSfFee.getFeeAmt() != null) {
            String[] arr = zxSfFee.getFeeAmt().toString().split("\\.");
            if (arr[0].length() > 12) {
                return repEntity.layerMessage("no", "本期季度安全费用支出只接受小数点前12位范围内的数字，小数点后6位范围内的数字！");
            }
            if (arr.length > 1 && arr[1].length() > 6) {
                return repEntity.layerMessage("no", "本期季度安全费用支出只接受小数点前12位范围内的数字，小数点后6位范围内的数字！");
            }
        }
        // 公司id
        ZxSysProject project = zxSysProjectMapper.getCompanyInfoByProjectId(zxSfFee.getOrgID());
        if (project == null) {
            zxSfFee.setCompanyId(zxSfFee.getOrgID());
            zxSfFee.setCompanyName(zxSfFee.getOrgName());
        } else {
            zxSfFee.setProjectId(zxSfFee.getOrgID());
            zxSfFee.setCompanyId(project.getCompanyId());
            zxSfFee.setCompanyName(project.getCompanyName());
        }
        int flag = zxSfFeeMapper.insert(zxSfFee);
        //添加附件
        List<ZxErpFile> fileList = zxSfFee.getFileList();
        if (fileList != null && fileList.size() > 0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSfFee.getZxSfFeeId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfFee);
        }
    }

    @Override
    public ResponseEntity updateZxSfFee(ZxSfFee zxSfFee) {
        if (zxSfFee.getFeeAmt() != null) {
            String[] arr = zxSfFee.getFeeAmt().toString().split("\\.");
            if (arr[0].length() > 12) {
                return repEntity.layerMessage("no", "本期季度安全费用支出只接受小数点前12位范围内的数字，小数点后6位范围内的数字！");
            }
            if (arr.length > 1 && arr[1].length() > 6) {
                return repEntity.layerMessage("no", "本期季度安全费用支出只接受小数点前12位范围内的数字，小数点后6位范围内的数字！");
            }
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfFee dbZxSfFee = zxSfFeeMapper.selectByPrimaryKey(zxSfFee.getZxSfFeeId());
        if (dbZxSfFee != null && StrUtil.isNotEmpty(dbZxSfFee.getZxSfFeeId())) {
//            // 机构名称
//            dbZxSfFee.setOrgName(zxSfFee.getOrgName());
//            // 机构ID
//            dbZxSfFee.setOrgID(zxSfFee.getOrgID());
            // 季度
            dbZxSfFee.setSeason(zxSfFee.getSeason());
            // 本季度安全费用支出
            dbZxSfFee.setFeeAmt(zxSfFee.getFeeAmt());
//            // 备注
//            dbZxSfFee.setNotes(zxSfFee.getNotes());
//            // 公司上报金额
//            dbZxSfFee.setCompSendAmt(zxSfFee.getCompSendAmt());
//            // 局批复金额
//            dbZxSfFee.setGroupCheckAmt(zxSfFee.getGroupCheckAmt());
//            // 公司批复金额
//            dbZxSfFee.setCompCheckAmt(zxSfFee.getCompCheckAmt());
            // 状态
//            dbZxSfFee.setStatus(zxSfFee.getStatus());
            // 编辑时间
//            dbZxSfFee.setEditTime(zxSfFee.getEditTime());
//            // 年份
//            dbZxSfFee.setYearStr(zxSfFee.getYearStr());
//            // 是否局下达
//            dbZxSfFee.setIsGroup(zxSfFee.getIsGroup());
//            // 是否公司下达
//            dbZxSfFee.setIsCompany(zxSfFee.getIsCompany());
            // 本季度产值
            dbZxSfFee.setProduceAmt(zxSfFee.getProduceAmt());
            // 所属机构（项目ID）
            dbZxSfFee.setProjectId(zxSfFee.getProjectId());
            // 项目名称
            dbZxSfFee.setProjectName(zxSfFee.getProjectName());
            String companyId = zxSysProjectMapper.getCompanyIdByProjectId(zxSfFee.getProjectId());
            zxSfFee.setCompanyId(companyId);
            // 所属公司ID
            dbZxSfFee.setCompanyId(zxSfFee.getCompanyId());
            // 所属公司名称
//            dbZxSfFee.setCompanyName(zxSfFee.getCompanyName());
            // 备注
            dbZxSfFee.setRemarks(zxSfFee.getRemarks());
            // 排序
//            dbZxSfFee.setSort(zxSfFee.getSort());
            // 共通
            dbZxSfFee.setModifyUserInfo(userKey, realName);
            flag = zxSfFeeMapper.updateByPrimaryKey(dbZxSfFee);
            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSfFee.getZxSfFeeId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if (zxErpFiles != null && zxErpFiles.size() > 0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            List<ZxErpFile> fileList = zxSfFee.getFileList();
            if (fileList != null && fileList.size() > 0) {
                for (ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setOtherId(zxSfFee.getZxSfFeeId());
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
            return repEntity.ok("sys.data.update", zxSfFee);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfFee(List<ZxSfFee> zxSfFeeList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfFeeList != null && zxSfFeeList.size() > 0) {
            for (ZxSfFee zxSfFee2 : zxSfFeeList) {
                //删除附件
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxSfFee2.getZxSfFeeId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if (zxErpFiles != null && zxErpFiles.size() > 0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
            }
            ZxSfFee zxSfFee = new ZxSfFee();
            zxSfFee.setModifyUserInfo(userKey, realName);
            flag = zxSfFeeMapper.batchDeleteUpdateZxSfFee(zxSfFeeList, zxSfFee);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSfFeeList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public ResponseEntity getCompany(ZxSfFee zxSfCost) {
        // 获取数据
        ZxSfFee dbZxSfCost = zxSfFeeMapper.getCompany(zxSfCost);
        // 数据存在
        if (dbZxSfCost != null) {
            return repEntity.ok(dbZxSfCost);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }


    @Override
    public ResponseEntity getJuInfo(ZxSfFee zxSfCost) {
        // 获取数据
        ZxSfFee dbZxSfCost = zxSfFeeMapper.getJuInfo(zxSfCost);
        // 数据存在
        if (dbZxSfCost != null) {
            return repEntity.ok(dbZxSfCost);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity getCompanyList(ZxSfFee zxSfCost) {
        // 获取数据
        List<ZxSfFee> dbZxSfCostList = zxSfFeeMapper.getCompanyList(zxSfCost);
        // 数据存在
        if (dbZxSfCostList.size() > 0) {
            return repEntity.ok(dbZxSfCostList);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity getOrgCostList(ZxSfFee zxSfCost) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        ZxSfFee guiDang = zxSfFeeMapper.getGuiDang(zxSfCost, today);
        ZxSfFee jiaoGong = zxSfFeeMapper.getJiaoGong(zxSfCost, today);
        ZxSfFee wanGong = zxSfFeeMapper.getWanGong(zxSfCost, today);
        ZxSfFee kaiGong = zxSfFeeMapper.getKaiGong(zxSfCost, today);
        List<ZxSfFee> orgCostList = new ArrayList<>();
        if (guiDang != null) {
            orgCostList.add(guiDang);
        }
        if (jiaoGong != null) {
            orgCostList.add(jiaoGong);
        }
        if (wanGong != null) {
            orgCostList.add(wanGong);
        }
        if (kaiGong != null) {
            orgCostList.add(kaiGong);
        }
        return repEntity.ok(orgCostList);
    }

    @Override
    public ResponseEntity getGuiDangList(ZxSfFee zxSfCost) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        List<ZxSfFee> guiDangList = zxSfFeeMapper.getGuiDangList(zxSfCost, today);
        return repEntity.ok(guiDangList);
    }

    @Override
    public ResponseEntity getJiaoGongList(ZxSfFee zxSfCost) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        List<ZxSfFee> jiaoGongList = zxSfFeeMapper.getJiaoGongList(zxSfCost, today);
        return repEntity.ok(jiaoGongList);
    }

    @Override
    public ResponseEntity getWanGongList(ZxSfFee zxSfCost) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        List<ZxSfFee> wanGongList = zxSfFeeMapper.getWanGongList(zxSfCost, today);
        return repEntity.ok(wanGongList);
    }

    @Override
    public ResponseEntity getKaiGongList(ZxSfFee zxSfCost) {
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        List<ZxSfFee> kaiGongList = zxSfFeeMapper.getKaiGongList(zxSfCost, today);
        return repEntity.ok(kaiGongList);
    }
}
