package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.dao.ZxSfHazardRoomAttMapper;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.mybatis.pojo.ZxSfHazardRoomAtt;
import com.apih5.service.ZxSfHazardRoomAttService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxSfHazardRoomAttService")
public class ZxSfHazardRoomAttServiceImpl implements ZxSfHazardRoomAttService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfHazardRoomAttMapper zxSfHazardRoomAttMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;


    @Override
    public ResponseEntity getZxSfHazardRoomAttListByCondition(ZxSfHazardRoomAtt zxSfHazardRoomAtt) {
        if (zxSfHazardRoomAtt == null) {
            zxSfHazardRoomAtt = new ZxSfHazardRoomAtt();
        }

        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSfHazardRoomAtt.setCompanyId("");
            zxSfHazardRoomAtt.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSfHazardRoomAtt.setCompanyId(zxSfHazardRoomAtt.getOrgID());
            zxSfHazardRoomAtt.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSfHazardRoomAtt.setOrgID(zxSfHazardRoomAtt.getOrgID());
        }

        // 分页查询
        PageHelper.startPage(zxSfHazardRoomAtt.getPage(), zxSfHazardRoomAtt.getLimit());
        // 获取数据
        List<ZxSfHazardRoomAtt> zxSfHazardRoomAttList = zxSfHazardRoomAttMapper.selectByZxSfHazardRoomAttList(zxSfHazardRoomAtt);
        // 查询附件
        for (ZxSfHazardRoomAtt zxSfHazardRoomAtt2 : zxSfHazardRoomAttList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSfHazardRoomAtt2.getZxSfHazardRoomAttId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSfHazardRoomAtt2.setFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxSfHazardRoomAtt> p = new PageInfo<>(zxSfHazardRoomAttList);

        return repEntity.okList(zxSfHazardRoomAttList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfHazardRoomAttDetail(ZxSfHazardRoomAtt zxSfHazardRoomAtt) {
        if (zxSfHazardRoomAtt == null) {
            zxSfHazardRoomAtt = new ZxSfHazardRoomAtt();
        }
        // 获取数据
        ZxSfHazardRoomAtt dbZxSfHazardRoomAtt = zxSfHazardRoomAttMapper.selectByPrimaryKey(zxSfHazardRoomAtt.getZxSfHazardRoomAttId());
        // 数据存在
        if (dbZxSfHazardRoomAtt != null) {
            return repEntity.ok(dbZxSfHazardRoomAtt);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfHazardRoomAtt(ZxSfHazardRoomAtt zxSfHazardRoomAtt) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfHazardRoomAtt.setZxSfHazardRoomAttId(UuidUtil.generate());
        zxSfHazardRoomAtt.setCreateUserInfo(userKey, realName);
        // 添加附件
        List<ZxErpFile> fileList = zxSfHazardRoomAtt.getFileList();
        if (fileList != null && fileList.size() > 0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSfHazardRoomAtt.getZxSfHazardRoomAttId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        //危险评价计算
        BigDecimal num1 = zxSfHazardRoomAtt.getLee();
        BigDecimal num2 = zxSfHazardRoomAtt.getBee();
        BigDecimal num3 = zxSfHazardRoomAtt.getCee();
        BigDecimal result1 = num1.multiply(num2);
        BigDecimal result2 = num3.multiply(result1);
        zxSfHazardRoomAtt.setDee(result2);

        //根据分数定等级
        BigDecimal result3 = zxSfHazardRoomAtt.getDee();
        BigDecimal bd0 = new BigDecimal(0);
        BigDecimal bd100 = new BigDecimal(100);
        BigDecimal bd400 = new BigDecimal(400);
        BigDecimal bd540 = new BigDecimal(540);
        BigDecimal bd720 = new BigDecimal(720);

        //风险登记评估
        if (result3.compareTo(bd720) == -1) {
            zxSfHazardRoomAtt.setRiskLevel("5");
        } else if (result3.compareTo(bd720) == -1 && result3.compareTo(bd540) > -1) {
            zxSfHazardRoomAtt.setRiskLevel("4");
        } else if (result3.compareTo(bd540) == -1 && result3.compareTo(bd400) > -1) {
            zxSfHazardRoomAtt.setRiskLevel("3");
        } else if (result3.compareTo(bd400) == -1 && result3.compareTo(bd100) > -1) {
            zxSfHazardRoomAtt.setRiskLevel("2");
        } else if (result3.compareTo(bd100) == -1 && result3.compareTo(bd0) > -1) {
            zxSfHazardRoomAtt.setRiskLevel("1");
        }

        int flag = zxSfHazardRoomAttMapper.insert(zxSfHazardRoomAtt);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfHazardRoomAtt);
        }
    }

    @Override
    public ResponseEntity updateZxSfHazardRoomAtt(ZxSfHazardRoomAtt zxSfHazardRoomAtt) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfHazardRoomAtt dbZxSfHazardRoomAtt = zxSfHazardRoomAttMapper.selectByPrimaryKey(zxSfHazardRoomAtt.getZxSfHazardRoomAttId());
        if (dbZxSfHazardRoomAtt != null && StrUtil.isNotEmpty(dbZxSfHazardRoomAtt.getZxSfHazardRoomAttId())) {
            // 机构ID
            dbZxSfHazardRoomAtt.setOrgID(zxSfHazardRoomAtt.getOrgID());
            // 类别
            dbZxSfHazardRoomAtt.setType(zxSfHazardRoomAtt.getType());
            // 编辑时间
            dbZxSfHazardRoomAtt.setEditTime(zxSfHazardRoomAtt.getEditTime());
            // 编制人
            dbZxSfHazardRoomAtt.setPreparedby(zxSfHazardRoomAtt.getPreparedby());
            // 明细ID
            dbZxSfHazardRoomAtt.setItemID(zxSfHazardRoomAtt.getItemID());
            // 父ID
            dbZxSfHazardRoomAtt.setParentID(zxSfHazardRoomAtt.getParentID());
            // 名称
            dbZxSfHazardRoomAtt.setAttName(zxSfHazardRoomAtt.getAttName());
            // 作业条件危险性评价（D）
            dbZxSfHazardRoomAtt.setDee(zxSfHazardRoomAtt.getDee());
            // 作业条件危险性评价（C）
            dbZxSfHazardRoomAtt.setCee(zxSfHazardRoomAtt.getCee());
            // 作业条件危险性评价（B）
            dbZxSfHazardRoomAtt.setBee(zxSfHazardRoomAtt.getBee());
            // 作业条件危险性评价（L）
            dbZxSfHazardRoomAtt.setLee(zxSfHazardRoomAtt.getLee());
            // 风险等级
            dbZxSfHazardRoomAtt.setRiskLevel(zxSfHazardRoomAtt.getRiskLevel());
            // 是否下达
            dbZxSfHazardRoomAtt.setIsGroup(zxSfHazardRoomAtt.getIsGroup());
            // 过程（区域）
            dbZxSfHazardRoomAtt.setProArea(zxSfHazardRoomAtt.getProArea());
            // 行为（活动）或设备=环境
            dbZxSfHazardRoomAtt.setDoing(zxSfHazardRoomAtt.getDoing());
            // 危险因素
            dbZxSfHazardRoomAtt.setRiskFactors(zxSfHazardRoomAtt.getRiskFactors());
            // 可能导致的伤害（事故）
            dbZxSfHazardRoomAtt.setAccident(zxSfHazardRoomAtt.getAccident());
            // 备注
            dbZxSfHazardRoomAtt.setRemarks(zxSfHazardRoomAtt.getRemarks());
            // 排序
            dbZxSfHazardRoomAtt.setSort(zxSfHazardRoomAtt.getSort());
            // 共通
            dbZxSfHazardRoomAtt.setModifyUserInfo(userKey, realName);
            flag = zxSfHazardRoomAttMapper.updateByPrimaryKey(dbZxSfHazardRoomAtt);

            // 修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSfHazardRoomAtt.getZxSfHazardRoomAttId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if (zxErpFiles != null && zxErpFiles.size() > 0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            List<ZxErpFile> fileList = zxSfHazardRoomAtt.getFileList();
            if (fileList != null && fileList.size() > 0) {
                for (ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setOtherId(zxSfHazardRoomAtt.getZxSfHazardRoomAttId());
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
            return repEntity.ok("sys.data.update", zxSfHazardRoomAtt);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfHazardRoomAtt(List<ZxSfHazardRoomAtt> zxSfHazardRoomAttList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfHazardRoomAttList != null && zxSfHazardRoomAttList.size() > 0) {
            for (ZxSfHazardRoomAtt zxSfHazardRoomAtt1 : zxSfHazardRoomAttList) {
                // 删除附件
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxSfHazardRoomAtt1.getZxSfHazardRoomAttId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if (zxErpFiles != null && zxErpFiles.size() > 0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
                ZxSfHazardRoomAtt zxSfHazardRoomAtt = new ZxSfHazardRoomAtt();
                zxSfHazardRoomAtt.setModifyUserInfo(userKey, realName);
                flag = zxSfHazardRoomAttMapper.batchDeleteUpdateZxSfHazardRoomAtt(zxSfHazardRoomAttList, zxSfHazardRoomAtt);
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxSfHazardRoomAttList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public List<ZxSfHazardRoomAtt> getZxSfHazardRoomListByCondition(ZxSfHazardRoomAtt zxSfHazardRoomAtt) {
        if (zxSfHazardRoomAtt == null) {
            zxSfHazardRoomAtt = new ZxSfHazardRoomAtt();
        }
        // 分页查询
        PageHelper.startPage(zxSfHazardRoomAtt.getPage(), zxSfHazardRoomAtt.getLimit());
        //项目区分
        if (zxSfHazardRoomAtt.getType().equals("pro")) {
            zxSfHazardRoomAtt.setType("pro");
            // 获取数据
            List<ZxSfHazardRoomAtt> zxSfHazardRoomAttList = zxSfHazardRoomAttMapper.selectByZxSfHazardRoomAttList(zxSfHazardRoomAtt);
            // 得到分页信息
            PageInfo<ZxSfHazardRoomAtt> p = new PageInfo<>(zxSfHazardRoomAttList);

            return zxSfHazardRoomAttList;
        }
        // 获取数据
        List<ZxSfHazardRoomAtt> zxSfHazardRoomAttList = zxSfHazardRoomAttMapper.selectByZxSfHazardRoomAttList(zxSfHazardRoomAtt);

        return zxSfHazardRoomAttList;
    }

    @Override
    public ResponseEntity getZxSfHazardRoomAttProArea(ZxSfHazardRoomAtt zxSfHazardRoomAtt) {
        if (zxSfHazardRoomAtt == null) {
            zxSfHazardRoomAtt = new ZxSfHazardRoomAtt();
        }

        // 分页查询
        PageHelper.startPage(zxSfHazardRoomAtt.getPage(), zxSfHazardRoomAtt.getLimit());
        // 获取数据
        List<ZxSfHazardRoomAtt> zxSfHazardRoomAttList = zxSfHazardRoomAttMapper.selectByZxSfHazardRoomProArea(zxSfHazardRoomAtt);
        // 得到分页信息
        PageInfo<ZxSfHazardRoomAtt> p = new PageInfo<>(zxSfHazardRoomAttList);

        return repEntity.okList(zxSfHazardRoomAttList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfHazardRoomAttDoing(ZxSfHazardRoomAtt zxSfHazardRoomAtt) {
        if (zxSfHazardRoomAtt == null) {
            zxSfHazardRoomAtt = new ZxSfHazardRoomAtt();
        }

        // 分页查询
        PageHelper.startPage(zxSfHazardRoomAtt.getPage(), zxSfHazardRoomAtt.getLimit());

        if (StrUtil.isNotEmpty(zxSfHazardRoomAtt.getZxSfHazardRoomAttId())) {
            zxSfHazardRoomAtt.setParentID(zxSfHazardRoomAtt.getZxSfHazardRoomAttId());
        }
        // 获取数据
        List<ZxSfHazardRoomAtt> zxSfHazardRoomAttList = zxSfHazardRoomAttMapper.selectByZxSfHazardRoomProDoing(zxSfHazardRoomAtt);
        // 得到分页信息
        PageInfo<ZxSfHazardRoomAtt> p = new PageInfo<>(zxSfHazardRoomAttList);

        return repEntity.okList(zxSfHazardRoomAttList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfHazardRoomAttRiskFactors(ZxSfHazardRoomAtt zxSfHazardRoomAtt) {
        if (zxSfHazardRoomAtt == null) {
            zxSfHazardRoomAtt = new ZxSfHazardRoomAtt();
        }

        // 分页查询
        PageHelper.startPage(zxSfHazardRoomAtt.getPage(), zxSfHazardRoomAtt.getLimit());
        if (StrUtil.isNotEmpty(zxSfHazardRoomAtt.getZxSfHazardRoomAttId())) {
            zxSfHazardRoomAtt.setParentID(zxSfHazardRoomAtt.getZxSfHazardRoomAttId());
        }
        // 获取数据
        List<ZxSfHazardRoomAtt> zxSfHazardRoomAttList = zxSfHazardRoomAttMapper.selectByZxSfHazardRoomProDoing(zxSfHazardRoomAtt);
        // 得到分页信息
        PageInfo<ZxSfHazardRoomAtt> p = new PageInfo<>(zxSfHazardRoomAttList);

        return repEntity.okList(zxSfHazardRoomAttList, p.getTotal());
    }

    /**
     * 展示危险源台账（项目）报表查询
     *
     * @param zxSfHazardRoomAtt
     * @author suncg
     */

    @Override
    public ResponseEntity getUreportFormList(ZxSfHazardRoomAtt zxSfHazardRoomAtt) {
        if (zxSfHazardRoomAtt == null) {
            zxSfHazardRoomAtt = new ZxSfHazardRoomAtt();
        }

        // 分页查询
        PageHelper.startPage(zxSfHazardRoomAtt.getPage(), zxSfHazardRoomAtt.getLimit());
        // 获取数据
        List<ZxSfHazardRoomAtt> zxSfHazardRoomAttList = zxSfHazardRoomAttMapper.exportForm(zxSfHazardRoomAtt);
        // 得到分页信息
        PageInfo<ZxSfHazardRoomAtt> p = new PageInfo<>(zxSfHazardRoomAttList);

        return repEntity.okList(zxSfHazardRoomAttList, p.getTotal());
    }

    /**
     * 导出危险源台账（项目）报表查询
     *
     * @param zxSfHazardRoomAtt
     * @author suncg
     */
    @Override
    public List<ZxSfHazardRoomAtt> UreportForm(ZxSfHazardRoomAtt zxSfHazardRoomAtt) {
        if (zxSfHazardRoomAtt == null) {
            zxSfHazardRoomAtt = new ZxSfHazardRoomAtt();
        }

        // 分页查询
        // PageHelper.startPage(zxSfHazardRoomAtt.getPage(),zxSfHazardRoomAtt.getLimit());
        // 获取数据
        List<ZxSfHazardRoomAtt> zxSfHazardRoomAttList = zxSfHazardRoomAttMapper.exportForm(zxSfHazardRoomAtt);
        // 得到分页信息
        // PageInfo<ZxSfHazardRoomAtt> p = new PageInfo<>(zxSfHazardRoomAttList);

        return zxSfHazardRoomAttList;
    }

    /**
     * 展示危险源台账（公司）报表查询
     *
     * @param zxSfHazardRoomAtt
     * @author suncg
     */
    @Override
    public ResponseEntity getUreportFormComList(ZxSfHazardRoomAtt zxSfHazardRoomAtt) {
        if (zxSfHazardRoomAtt == null) {
            zxSfHazardRoomAtt = new ZxSfHazardRoomAtt();
        }

        // 分页查询
        PageHelper.startPage(zxSfHazardRoomAtt.getPage(), zxSfHazardRoomAtt.getLimit());
        // 获取数据
        List<ZxSfHazardRoomAtt> zxSfHazardRoomAttList = zxSfHazardRoomAttMapper.exportFormCom(zxSfHazardRoomAtt);
        // 得到分页信息
        PageInfo<ZxSfHazardRoomAtt> p = new PageInfo<>(zxSfHazardRoomAttList);

        return repEntity.okList(zxSfHazardRoomAttList, p.getTotal());
    }

    /**
     * 导出危险源台账（公司）报表查询
     *
     * @param zxSfHazardRoomAtt
     * @author suncg
     */

    @Override
    public List<ZxSfHazardRoomAtt> UreportFormCom(ZxSfHazardRoomAtt zxSfHazardRoomAtt) {
        if (zxSfHazardRoomAtt == null) {
            zxSfHazardRoomAtt = new ZxSfHazardRoomAtt();
        }
        // 获取数据
        List<ZxSfHazardRoomAtt> zxSfHazardRoomAttList = zxSfHazardRoomAttMapper.exportFormCom(zxSfHazardRoomAtt);
        // 得到分页信息
        // PageInfo<ZxSfHazardRoomAtt> p = new PageInfo<>(zxSfHazardRoomAttList);

        return zxSfHazardRoomAttList;
    }


}
