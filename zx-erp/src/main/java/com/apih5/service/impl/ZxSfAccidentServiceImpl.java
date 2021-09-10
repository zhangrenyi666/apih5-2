package com.apih5.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.framework.constant.SysConst;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.dao.ZxSfAccidentItemMapper;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.mybatis.pojo.ZxSfAccidentItem;
import com.apih5.mybatis.pojo.ZxSfSWAccidentItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSfAccidentMapper;
import com.apih5.mybatis.pojo.ZxSfAccident;
import com.apih5.service.ZxSfAccidentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSfAccidentService")
public class ZxSfAccidentServiceImpl implements ZxSfAccidentService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfAccidentMapper zxSfAccidentMapper;

    @Autowired(required = true)
    private ZxSfAccidentItemMapper zxSfAccidentItemMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Override
    public ResponseEntity getZxSfAccidentListByCondition(ZxSfAccident zxSfAccident) {
        if (zxSfAccident == null) {
            zxSfAccident = new ZxSfAccident();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSfAccident.setCompanyId("");
            zxSfAccident.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSfAccident.setCompanyId(zxSfAccident.getOrgId());
            zxSfAccident.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSfAccident.setOrgId(zxSfAccident.getOrgId());
        }
        // 分页查询
        PageHelper.startPage(zxSfAccident.getPage(),zxSfAccident.getLimit());
        // 获取数据
        List<ZxSfAccident> zxSfAccidentList = zxSfAccidentMapper.selectByZxSfAccidentList(zxSfAccident);
        for (ZxSfAccident zxSfAccident1 : zxSfAccidentList) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
                Date date = new Date();

            try {
                date = simpleDateFormat.parse(zxSfAccident1.getYear());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            zxSfAccident1.setDatePeriod(date);

            // 伤亡事故情况统计分析表明细list
            ZxSfAccidentItem dbZxSfAccidentItem = new ZxSfAccidentItem();
            dbZxSfAccidentItem.setAccidentId(zxSfAccident1.getZxSfAccidentId());
            List<ZxSfAccidentItem> zxSfAccidentItemList = zxSfAccidentItemMapper.selectByZxSfAccidentItemList(dbZxSfAccidentItem);
            zxSfAccident1.setZxSfAccidentItemList(zxSfAccidentItemList);

            //查询附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSfAccident1.getZxSfAccidentId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSfAccident1.setZxErpFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxSfAccident> p = new PageInfo<>(zxSfAccidentList);

        return repEntity.okList(zxSfAccidentList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfAccidentDetail(ZxSfAccident zxSfAccident) {
        if (zxSfAccident == null) {
            zxSfAccident = new ZxSfAccident();
        }
        // 获取数据
        ZxSfAccident dbZxSfAccident = zxSfAccidentMapper.selectByPrimaryKey(zxSfAccident.getZxSfAccidentId());
        // 数据存在
        if (dbZxSfAccident != null) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
            Date date = new Date();
            try {
                date = simpleDateFormat.parse(dbZxSfAccident.getYear());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            dbZxSfAccident.setDatePeriod(date);

            // 伤亡事故情况统计分析表明细list
            ZxSfAccidentItem dbZxSfAccidentItem = new ZxSfAccidentItem();
            dbZxSfAccidentItem.setAccidentId(dbZxSfAccident.getZxSfAccidentId());
            List<ZxSfAccidentItem> zxSfAccidentItemList = zxSfAccidentItemMapper.selectByZxSfAccidentItemList(dbZxSfAccidentItem);
            dbZxSfAccident.setZxSfAccidentItemList(zxSfAccidentItemList);

            //附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxSfAccident.getZxSfAccidentId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            dbZxSfAccident.setZxErpFileList(zxErpFiles);
            return repEntity.ok(dbZxSfAccident);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfAccident(ZxSfAccident zxSfAccident) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfAccident.setZxSfAccidentId(UuidUtil.generate());
        zxSfAccident.setCreateUserInfo(userKey, realName);
        String result = new SimpleDateFormat("yyyyMM").format(zxSfAccident.getDatePeriod());
        zxSfAccident.setYear(result);
        int flag = zxSfAccidentMapper.insert(zxSfAccident);

        //  添加伤亡事故情况统计分析表明细
        List<ZxSfAccidentItem> zxSfAccidentItemList = zxSfAccident.getZxSfAccidentItemList();
        if(zxSfAccidentItemList != null && zxSfAccidentItemList.size()>0) {
            for(ZxSfAccidentItem zxSfAccidentItem : zxSfAccidentItemList) {
                zxSfAccidentItem.setAccidentId(zxSfAccident.getZxSfAccidentId());
                zxSfAccidentItem.setZxSfAccidentItemId(UuidUtil.createUUID());
                zxSfAccidentItem.setCreateUserInfo(userKey, realName);
                zxSfAccidentItemMapper.insert(zxSfAccidentItem);
            }
        }

        //添加附件
        List<ZxErpFile> fileList = zxSfAccident.getZxErpFileList();
        if (fileList != null && fileList.size() > 0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSfAccident.getZxSfAccidentId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfAccident);
        }
    }

    @Override
    public ResponseEntity updateZxSfAccident(ZxSfAccident zxSfAccident) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String result = new SimpleDateFormat("yyyyMM").format(zxSfAccident.getDatePeriod());
        zxSfAccident.setYear(result);
        int flag = 0;
        ZxSfAccident dbZxSfAccident = zxSfAccidentMapper.selectByPrimaryKey(zxSfAccident.getZxSfAccidentId());
        if (dbZxSfAccident != null && StrUtil.isNotEmpty(dbZxSfAccident.getZxSfAccidentId())) {
           // 月份
           dbZxSfAccident.setYear(zxSfAccident.getYear());
           // 处（科）负责人
           dbZxSfAccident.setDeManager(zxSfAccident.getDeManager());
           // 单位职工平均人数
           dbZxSfAccident.setAvgEmpNum(zxSfAccident.getAvgEmpNum());
           // 报出日期
           dbZxSfAccident.setBizDate(zxSfAccident.getBizDate());
           // 单位负责人
           dbZxSfAccident.setUnitManager(zxSfAccident.getUnitManager());
           // 制表人
           dbZxSfAccident.setCreator(zxSfAccident.getCreator());
           // 共通
           dbZxSfAccident.setModifyUserInfo(userKey, realName);
           flag = zxSfAccidentMapper.updateByPrimaryKey(dbZxSfAccident);

            // 伤亡事故情况统计分析表明细删除
            ZxSfAccidentItem delZxSfAccidentItem= new ZxSfAccidentItem();
            delZxSfAccidentItem.setAccidentId(zxSfAccident.getZxSfAccidentId());
            List<ZxSfAccidentItem> items = zxSfAccidentItemMapper.selectByZxSfAccidentItemList(delZxSfAccidentItem);
            if (items != null && items.size() > 0) {
                delZxSfAccidentItem.setModifyUserInfo(userKey, realName);
                zxSfAccidentItemMapper.batchDeleteUpdateZxSfAccidentItem(items, delZxSfAccidentItem);
            }

            //  添加伤亡事故情况统计分析表明细
            List<ZxSfAccidentItem> zxSfAccidentItemList = zxSfAccident.getZxSfAccidentItemList();
            if(zxSfAccidentItemList != null && zxSfAccidentItemList.size()>0) {
                for(ZxSfAccidentItem zxSfAccidentItem : zxSfAccidentItemList) {
                    zxSfAccidentItem.setAccidentId(zxSfAccident.getZxSfAccidentId());
                    zxSfAccidentItem.setZxSfAccidentItemId(UuidUtil.createUUID());
                    zxSfAccidentItem.setCreateUserInfo(userKey, realName);
                    zxSfAccidentItemMapper.insert(zxSfAccidentItem);
                }
            }

            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSfAccident.getZxSfAccidentId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if (zxErpFiles != null && zxErpFiles.size() > 0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }

            //明细list
            List<ZxErpFile> fileList = zxSfAccident.getZxErpFileList();
            if (fileList != null && fileList.size() > 0) {
                for (ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxSfAccident.getZxSfAccidentId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSfAccident);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfAccident(List<ZxSfAccident> zxSfAccidentList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfAccidentList != null && zxSfAccidentList.size() > 0) {
            for (ZxSfAccident zxSfAccident : zxSfAccidentList) {
                //删除附件
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxSfAccident.getZxSfAccidentId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if (zxErpFiles != null && zxErpFiles.size() > 0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }

                // 伤亡事故情况统计分析表明细删除
                ZxSfAccidentItem delZxSfAccidentItem= new ZxSfAccidentItem();
                delZxSfAccidentItem.setAccidentId(zxSfAccident.getZxSfAccidentId());
                List<ZxSfAccidentItem> items = zxSfAccidentItemMapper.selectByZxSfAccidentItemList(delZxSfAccidentItem);
                if (items != null && items.size() > 0) {
                    delZxSfAccidentItem.setModifyUserInfo(userKey, realName);
                    zxSfAccidentItemMapper.batchDeleteUpdateZxSfAccidentItem(items, delZxSfAccidentItem);
                }
            }
           ZxSfAccident zxSfAccident = new ZxSfAccident();
           zxSfAccident.setModifyUserInfo(userKey, realName);
           flag = zxSfAccidentMapper.batchDeleteUpdateZxSfAccident(zxSfAccidentList, zxSfAccident);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSfAccidentList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
