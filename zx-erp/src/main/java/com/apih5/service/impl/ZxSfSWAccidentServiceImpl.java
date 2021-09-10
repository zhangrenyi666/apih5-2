package com.apih5.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.framework.constant.SysConst;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.dao.ZxSfSWAccidentItemMapper;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.mybatis.pojo.ZxSfSWAccidentItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSfSWAccidentMapper;
import com.apih5.mybatis.pojo.ZxSfSWAccident;
import com.apih5.service.ZxSfSWAccidentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSfSWAccidentService")
public class ZxSfSWAccidentServiceImpl implements ZxSfSWAccidentService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfSWAccidentMapper zxSfSWAccidentMapper;

    @Autowired(required = true)
    private ZxSfSWAccidentItemMapper zxSfSWAccidentItemMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Override
    public ResponseEntity getZxSfSWAccidentListByCondition(ZxSfSWAccident zxSfSWAccident) {
        if (zxSfSWAccident == null) {
            zxSfSWAccident = new ZxSfSWAccident();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxSfSWAccident.setCompanyId("");
            zxSfSWAccident.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxSfSWAccident.setCompanyId(zxSfSWAccident.getOrgId());
            zxSfSWAccident.setOrgId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxSfSWAccident.setOrgId(zxSfSWAccident.getOrgId());
        }
        // 分页查询
        PageHelper.startPage(zxSfSWAccident.getPage(),zxSfSWAccident.getLimit());
        // 获取数据
        List<ZxSfSWAccident> zxSfSWAccidentList = zxSfSWAccidentMapper.selectByZxSfSWAccidentList(zxSfSWAccident);
        for (ZxSfSWAccident zxSfSWAccident1 : zxSfSWAccidentList) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
            Date date = new Date();
            try {
                date = simpleDateFormat.parse(zxSfSWAccident1.getPeriod());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            zxSfSWAccident1.setDatePeriod(date);
            // 船舶水上交通事故明细list
            ZxSfSWAccidentItem dbZxSfSWAccidentItem = new ZxSfSWAccidentItem();
            dbZxSfSWAccidentItem.setSwaID(zxSfSWAccident1.getZxSfSWAccidentId());
            List<ZxSfSWAccidentItem> zxSfSWAccidentItemList = zxSfSWAccidentItemMapper.selectByZxSfSWAccidentItemList(dbZxSfSWAccidentItem);
            zxSfSWAccident1.setZxSfSWAccidentItemList(zxSfSWAccidentItemList);

            //查询附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxSfSWAccident1.getZxSfSWAccidentId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxSfSWAccident1.setZxErpFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxSfSWAccident> p = new PageInfo<>(zxSfSWAccidentList);

        return repEntity.okList(zxSfSWAccidentList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfSWAccidentDetail(ZxSfSWAccident zxSfSWAccident) {
        if (zxSfSWAccident == null) {
            zxSfSWAccident = new ZxSfSWAccident();
        }
               // 获取数据
        ZxSfSWAccident dbZxSfSWAccident = zxSfSWAccidentMapper.selectByPrimaryKey(zxSfSWAccident.getZxSfSWAccidentId());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(dbZxSfSWAccident.getPeriod());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dbZxSfSWAccident.setDatePeriod(date);
        // 数据存在
        if (dbZxSfSWAccident != null) {
            // 船舶水上交通事故明细list
            ZxSfSWAccidentItem dbZxSfSWAccidentItem = new ZxSfSWAccidentItem();
            dbZxSfSWAccidentItem.setSwaID(dbZxSfSWAccident.getZxSfSWAccidentId());
            List<ZxSfSWAccidentItem> zxSfSWAccidentItemList = zxSfSWAccidentItemMapper.selectByZxSfSWAccidentItemList(dbZxSfSWAccidentItem);
            dbZxSfSWAccident.setZxSfSWAccidentItemList(zxSfSWAccidentItemList);

            //附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxSfSWAccident.getZxSfSWAccidentId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            dbZxSfSWAccident.setZxErpFileList(zxErpFiles);
            return repEntity.ok(dbZxSfSWAccident);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfSWAccident(ZxSfSWAccident zxSfSWAccident) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfSWAccident.setZxSfSWAccidentId(UuidUtil.generate());
        zxSfSWAccident.setCreateUserInfo(userKey, realName);
        String result = new SimpleDateFormat("yyyyMM").format(zxSfSWAccident.getDatePeriod());
        zxSfSWAccident.setPeriod(result);
        int flag = zxSfSWAccidentMapper.insert(zxSfSWAccident);

        //  添加船舶水上交通事故明细
        List<ZxSfSWAccidentItem> zxSfSWAccidentItemList = zxSfSWAccident.getZxSfSWAccidentItemList();
        if(zxSfSWAccidentItemList != null && zxSfSWAccidentItemList.size()>0) {
            for(ZxSfSWAccidentItem zxSfSWAccidentItem : zxSfSWAccidentItemList) {
                zxSfSWAccidentItem.setSwaID(zxSfSWAccident.getZxSfSWAccidentId());
                zxSfSWAccidentItem.setZxSfSWAccidentItemId(UuidUtil.createUUID());
                zxSfSWAccidentItem.setCreateUserInfo(userKey, realName);
                zxSfSWAccidentItemMapper.insert(zxSfSWAccidentItem);
            }
        }

        //添加附件
        List<ZxErpFile> fileList = zxSfSWAccident.getZxErpFileList();
        if (fileList != null && fileList.size() > 0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxSfSWAccident.getZxSfSWAccidentId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfSWAccident);
        }
    }

    @Override
    public ResponseEntity updateZxSfSWAccident(ZxSfSWAccident zxSfSWAccident) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        String result = new SimpleDateFormat("yyyyMM").format(zxSfSWAccident.getDatePeriod());
        zxSfSWAccident.setPeriod(result);
        ZxSfSWAccident dbZxSfSWAccident = zxSfSWAccidentMapper.selectByPrimaryKey(zxSfSWAccident.getZxSfSWAccidentId());
        if (dbZxSfSWAccident != null && StrUtil.isNotEmpty(dbZxSfSWAccident.getZxSfSWAccidentId())) {
           // 机构ID
           dbZxSfSWAccident.setOrgId(zxSfSWAccident.getOrgId());
           // 填表人
           dbZxSfSWAccident.setCreator(zxSfSWAccident.getCreator());
           // 单位负责人
           dbZxSfSWAccident.setManager(zxSfSWAccident.getManager());
           // 备注
           dbZxSfSWAccident.setNotes(zxSfSWAccident.getNotes());
           // 处（科）负责人
           dbZxSfSWAccident.setDeManager(zxSfSWAccident.getDeManager());
           // 期次
           dbZxSfSWAccident.setPeriod(zxSfSWAccident.getPeriod());
           // 填报日期
           dbZxSfSWAccident.setBizDate(zxSfSWAccident.getBizDate());
           // 填报单位
           dbZxSfSWAccident.setOrgName(zxSfSWAccident.getOrgName());
           // 排序
           dbZxSfSWAccident.setSort(zxSfSWAccident.getSort());
           // 共通
           dbZxSfSWAccident.setModifyUserInfo(userKey, realName);
           flag = zxSfSWAccidentMapper.updateByPrimaryKey(dbZxSfSWAccident);

            // 船舶水上交通事故明细删除
            ZxSfSWAccidentItem delZxSfSWAccidentItem= new ZxSfSWAccidentItem();
            delZxSfSWAccidentItem.setSwaID(zxSfSWAccident.getZxSfSWAccidentId());
            List<ZxSfSWAccidentItem> items = zxSfSWAccidentItemMapper.selectByZxSfSWAccidentItemList(delZxSfSWAccidentItem);
            if (items != null && items.size() > 0) {
                delZxSfSWAccidentItem.setModifyUserInfo(userKey, realName);
                zxSfSWAccidentItemMapper.batchDeleteUpdateZxSfSWAccidentItem(items, delZxSfSWAccidentItem);
            }

            //  添加船舶水上交通事故明细
            List<ZxSfSWAccidentItem> zxSfSWAccidentItemList = zxSfSWAccident.getZxSfSWAccidentItemList();
            if(zxSfSWAccidentItemList != null && zxSfSWAccidentItemList.size()>0) {
                for(ZxSfSWAccidentItem zxSfSWAccidentItem : zxSfSWAccidentItemList) {
                    zxSfSWAccidentItem.setSwaID(zxSfSWAccident.getZxSfSWAccidentId());
                    zxSfSWAccidentItem.setZxSfSWAccidentItemId(UuidUtil.createUUID());
                    zxSfSWAccidentItem.setCreateUserInfo(userKey, realName);
                    zxSfSWAccidentItemMapper.insert(zxSfSWAccidentItem);
                }
            }

            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxSfSWAccident.getZxSfSWAccidentId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if (zxErpFiles != null && zxErpFiles.size() > 0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }

            //明细list
            List<ZxErpFile> fileList = zxSfSWAccident.getZxErpFileList();
            if (fileList != null && fileList.size() > 0) {
                for (ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxSfSWAccident.getZxSfSWAccidentId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSfSWAccident);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfSWAccident(List<ZxSfSWAccident> zxSfSWAccidentList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfSWAccidentList != null && zxSfSWAccidentList.size() > 0) {
            for (ZxSfSWAccident zxSfSWAccident : zxSfSWAccidentList) {
                //删除附件
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxSfSWAccident.getZxSfSWAccidentId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if (zxErpFiles != null && zxErpFiles.size() > 0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }

                // 船舶水上交通事故明细删除
                ZxSfSWAccidentItem delZxSfSWAccidentItem= new ZxSfSWAccidentItem();
                delZxSfSWAccidentItem.setSwaID(zxSfSWAccident.getZxSfSWAccidentId());
                List<ZxSfSWAccidentItem> items = zxSfSWAccidentItemMapper.selectByZxSfSWAccidentItemList(delZxSfSWAccidentItem);
                if (items != null && items.size() > 0) {
                    delZxSfSWAccidentItem.setModifyUserInfo(userKey, realName);
                    zxSfSWAccidentItemMapper.batchDeleteUpdateZxSfSWAccidentItem(items, delZxSfSWAccidentItem);
                }
            }
           ZxSfSWAccident zxSfSWAccident = new ZxSfSWAccident();
           zxSfSWAccident.setModifyUserInfo(userKey, realName);
           flag = zxSfSWAccidentMapper.batchDeleteUpdateZxSfSWAccident(zxSfSWAccidentList, zxSfSWAccident);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSfSWAccidentList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
