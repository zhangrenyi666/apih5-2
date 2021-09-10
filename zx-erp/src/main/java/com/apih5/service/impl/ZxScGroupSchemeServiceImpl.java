package com.apih5.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.mybatis.pojo.ZxSaOtherEquipSettle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxScGroupSchemeMapper;
import com.apih5.mybatis.pojo.ZxScGroupScheme;
import com.apih5.service.ZxScGroupSchemeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service("zxScGroupSchemeService")
public class ZxScGroupSchemeServiceImpl implements ZxScGroupSchemeService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxScGroupSchemeMapper zxScGroupSchemeMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Override
    public ResponseEntity getZxScGroupSchemeListByCondition(ZxScGroupScheme zxScGroupScheme) {
        if (zxScGroupScheme == null) {
            zxScGroupScheme = new ZxScGroupScheme();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxScGroupScheme.setCompanyId("");
        	zxScGroupScheme.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxScGroupScheme.setCompanyId(zxScGroupScheme.getOrgID());
            zxScGroupScheme.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxScGroupScheme.setProjectId(zxScGroupScheme.getOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxScGroupScheme.getPage(),zxScGroupScheme.getLimit());
        // 获取数据
        List<ZxScGroupScheme> zxScGroupSchemeList = zxScGroupSchemeMapper.selectByZxScGroupSchemeList(zxScGroupScheme);

        // 得到分页信息
        PageInfo<ZxScGroupScheme> p = new PageInfo<>(zxScGroupSchemeList);

        return repEntity.okList(zxScGroupSchemeList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxScGroupSchemeDetail(ZxScGroupScheme zxScGroupScheme) {
        ZxScGroupScheme dbZxScGroupScheme = new ZxScGroupScheme();
        if(StrUtil.isNotEmpty(zxScGroupScheme.getWorkId())) {
            List<ZxScGroupScheme> zxScGroupSchemeList = zxScGroupSchemeMapper.selectByZxScGroupSchemeList(zxScGroupScheme);
            if(zxScGroupSchemeList != null && zxScGroupSchemeList.size() >0) {
                dbZxScGroupScheme = zxScGroupSchemeList.get(0);
            }
        } else {
            // 获取数据
            dbZxScGroupScheme = zxScGroupSchemeMapper.selectByPrimaryKey(zxScGroupScheme.getZxScGroupSchemeId());
        }
        // 数据存在
        if (dbZxScGroupScheme != null) {
            //查询附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxScGroupScheme.getZxScGroupSchemeId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            dbZxScGroupScheme.setZxZhengWenFileList(zxErpFiles);
            return repEntity.ok(dbZxScGroupScheme);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxScGroupScheme(ZxScGroupScheme zxScGroupScheme) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxScGroupScheme.setZxScGroupSchemeId(UuidUtil.generate());
        zxScGroupScheme.setCreateUserInfo(userKey, realName);
        // zxScGroupScheme.setApih5FlowStatus("-1");
        zxScGroupScheme.setBeginer(realName);
        // zxScGroupScheme.setSchemeAppro(realName);
        int flag = zxScGroupSchemeMapper.insert(zxScGroupScheme);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxScGroupScheme);
        }
    }

    @Override
    public ResponseEntity updateZxScGroupScheme(ZxScGroupScheme zxScGroupScheme) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxScGroupScheme dbZxScGroupScheme = zxScGroupSchemeMapper.selectByPrimaryKey(zxScGroupScheme.getZxScGroupSchemeId());
        if (dbZxScGroupScheme != null && StrUtil.isNotEmpty(dbZxScGroupScheme.getZxScGroupSchemeId())) {
            // 施组编号
            dbZxScGroupScheme.setSchemeNo(zxScGroupScheme.getSchemeNo());
            // 施组名称
            dbZxScGroupScheme.setSchemeName(zxScGroupScheme.getSchemeName());
            // 项目名称ID
            dbZxScGroupScheme.setProjectId(zxScGroupScheme.getProjectId());
            // 项目名称
            dbZxScGroupScheme.setProjectName(zxScGroupScheme.getProjectName());
            // 所属公司ID
            dbZxScGroupScheme.setComId(zxScGroupScheme.getComId());
            // 单位名称
            dbZxScGroupScheme.setComName(zxScGroupScheme.getComName());
            // 工程类别
            dbZxScGroupScheme.setProjectType(zxScGroupScheme.getProjectType());
            // 所在省份
            dbZxScGroupScheme.setProvince(zxScGroupScheme.getProvince());
            // 合同工期
            dbZxScGroupScheme.setContrDuration(zxScGroupScheme.getContrDuration());
            // 计划开工日期
            dbZxScGroupScheme.setPlanDate(zxScGroupScheme.getPlanDate());
            // 合同竣工日期
            dbZxScGroupScheme.setContrEndDate(zxScGroupScheme.getContrEndDate());
            // 合同金额(万元)
            dbZxScGroupScheme.setContractAmt(zxScGroupScheme.getContractAmt());
            // 项目经理
            dbZxScGroupScheme.setProjManager(zxScGroupScheme.getProjManager());
            // 项目总工
            dbZxScGroupScheme.setProjEngineer(zxScGroupScheme.getProjEngineer());
            // 联系方式
            dbZxScGroupScheme.setEngineerTel(zxScGroupScheme.getEngineerTel());
            // 项目联系人
            dbZxScGroupScheme.setSchemeAppro(zxScGroupScheme.getSchemeAppro());
            // 联系方式
            dbZxScGroupScheme.setApproTel(zxScGroupScheme.getApproTel());
            // 发起人
            dbZxScGroupScheme.setBeginer(zxScGroupScheme.getBeginer());
            // 流程实例ID、用于查看流程图
            dbZxScGroupScheme.setInstProcessId(zxScGroupScheme.getInstProcessId());
            // 用于查看审批进度
            dbZxScGroupScheme.setWorkitemId(zxScGroupScheme.getWorkitemId());
            // 所属公司排序
            dbZxScGroupScheme.setComOrders(zxScGroupScheme.getComOrders());
            // 上报日期
            dbZxScGroupScheme.setBizDate(zxScGroupScheme.getBizDate());
            // 评审结束时间
            dbZxScGroupScheme.setPassTime(zxScGroupScheme.getPassTime());
            // 序列号
            dbZxScGroupScheme.setSerialNumber(zxScGroupScheme.getSerialNumber());
            // 备注
            dbZxScGroupScheme.setRemark(zxScGroupScheme.getRemark());
            // 排序
            dbZxScGroupScheme.setSort(zxScGroupScheme.getSort());
            // 共通
            dbZxScGroupScheme.setModifyUserInfo(userKey, realName);
            flag = zxScGroupSchemeMapper.updateByPrimaryKey(dbZxScGroupScheme);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxScGroupScheme);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxScGroupScheme(List<ZxScGroupScheme> zxScGroupSchemeList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxScGroupSchemeList != null && zxScGroupSchemeList.size() > 0) {
           ZxScGroupScheme zxScGroupScheme = new ZxScGroupScheme();
           zxScGroupScheme.setModifyUserInfo(userKey, realName);
           flag = zxScGroupSchemeMapper.batchDeleteUpdateZxScGroupScheme(zxScGroupSchemeList, zxScGroupScheme);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxScGroupSchemeList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @Override
    public ResponseEntity getZxScGroupSchemeSequence(ZxScGroupScheme zxScGroupScheme) {
        if (StringUtils.isEmpty(zxScGroupScheme.getProjectId())) {
            return repEntity.layerMessage("no", "参数错误！");
        }
        String schemeNo = zxScGroupSchemeMapper.getLastIssueSchemeNoByProjectId(zxScGroupScheme.getProjectId());
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        if (StringUtils.isEmpty(schemeNo)) {
            return repEntity.ok(year + "-001");
        } else {
            String[] arr = schemeNo.split("-");
            Integer sq = Integer.parseInt(arr[arr.length - 1]);
            return repEntity.ok(year + "-" + String.format("%03d", sq + 1));
        }
    }

    @Override
    public ResponseEntity zxScGroupSchemeReviewApply(ZxScGroupScheme zxScGroupScheme) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxScGroupScheme dbZxScGroupScheme = zxScGroupSchemeMapper.selectByPrimaryKey(zxScGroupScheme.getZxScGroupSchemeId());
        if (dbZxScGroupScheme != null && StrUtil.isNotEmpty(dbZxScGroupScheme.getZxScGroupSchemeId())) {
            dbZxScGroupScheme.setApih5FlowStatus(zxScGroupScheme.getApih5FlowStatus());
            dbZxScGroupScheme.setWorkId(zxScGroupScheme.getWorkId());
            if ("2".equals(zxScGroupScheme.getApih5FlowStatus())) {
                dbZxScGroupScheme.setPassTime(new Date());
            }
            // 共通
            dbZxScGroupScheme.setModifyUserInfo(userKey, realName);
            flag = zxScGroupSchemeMapper.updateByPrimaryKey(dbZxScGroupScheme);
            if (!CollectionUtils.isEmpty(zxScGroupScheme.getZxZhengWenFileList())) {
                ZxErpFile file = new ZxErpFile();
                file.setOtherId(dbZxScGroupScheme.getZxScGroupSchemeId());
                //file.setOtherType("1");
                List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
                file.setModifyUserInfo(userKey, realName);
                if(fileList.size() > 0) {
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(fileList, file);
                }
                for(ZxErpFile zxErpFile : zxScGroupScheme.getZxZhengWenFileList()) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(dbZxScGroupScheme.getZxScGroupSchemeId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFile.setOtherType("1");
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxScGroupScheme);
        }
    }
}
