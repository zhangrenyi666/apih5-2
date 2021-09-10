package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.apih5.mybatis.pojo.ZxCrHalfYearCreditEvaItem;
import com.apih5.mybatis.pojo.ZxCrProjectEvaluation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCrJYearCreditEvaMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxCrJYearCreditEva;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxCrJYearCreditEvaService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxCrJYearCreditEvaService")
public class ZxCrJYearCreditEvaServiceImpl implements ZxCrJYearCreditEvaService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCrJYearCreditEvaMapper zxCrJYearCreditEvaMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;
    @Override
    public ResponseEntity getZxCrJYearCreditEvaListByCondition(ZxCrJYearCreditEva zxCrJYearCreditEva) {
        if (zxCrJYearCreditEva == null) {
            zxCrJYearCreditEva = new ZxCrJYearCreditEva();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCrJYearCreditEva.setCompanyId("");
        	zxCrJYearCreditEva.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxCrJYearCreditEva.setCompanyId(zxCrJYearCreditEva.getOrgID());
        	zxCrJYearCreditEva.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxCrJYearCreditEva.setOrgID(zxCrJYearCreditEva.getOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxCrJYearCreditEva.getPage(),zxCrJYearCreditEva.getLimit());
        // 获取数据
        List<ZxCrJYearCreditEva> zxCrJYearCreditEvaList = zxCrJYearCreditEvaMapper.selectByZxCrJYearCreditEvaList(zxCrJYearCreditEva);
        
        //查询附件
        for (ZxCrJYearCreditEva zxCrJYearCreditEva1 : zxCrJYearCreditEvaList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxCrJYearCreditEva1.getZxCrJYearCreditEvaId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxCrJYearCreditEva1.setFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxCrJYearCreditEva> p = new PageInfo<>(zxCrJYearCreditEvaList);

        return repEntity.okList(zxCrJYearCreditEvaList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCrJYearCreditEvaDetail(ZxCrJYearCreditEva zxCrJYearCreditEva) {
        if (zxCrJYearCreditEva == null) {
            zxCrJYearCreditEva = new ZxCrJYearCreditEva();
        }
        // 获取数据
        ZxCrJYearCreditEva dbZxCrJYearCreditEva = zxCrJYearCreditEvaMapper.selectByPrimaryKey(zxCrJYearCreditEva.getZxCrJYearCreditEvaId());
        // 数据存在
        if (dbZxCrJYearCreditEva != null) {
        	//附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxCrJYearCreditEva.getZxCrJYearCreditEvaId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxCrJYearCreditEva.setFileList(zxErpFiles);
            return repEntity.ok(dbZxCrJYearCreditEva);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCrJYearCreditEva(ZxCrJYearCreditEva zxCrJYearCreditEva) {

        //唯一性校验
        List<ZxCrJYearCreditEva> ZxCrJYearCreditEva1 = zxCrJYearCreditEvaMapper.selectAll();
        for(ZxCrJYearCreditEva ZxCrJYearCreditEvaList : ZxCrJYearCreditEva1) {
        	if(ZxCrJYearCreditEvaList.getOrgName().equals(zxCrJYearCreditEva.getOrgName()) &&
        			ZxCrJYearCreditEvaList.getPeriod().equals(zxCrJYearCreditEva.getPeriod())){
        		return repEntity.layerMessage("no", "数据已经存在，不可再次新增！");
        	}
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String ext1 = TokenUtils.getExt1(request);
        //添加附件
        List<ZxErpFile> fileList = zxCrJYearCreditEva.getFileList();
        if(fileList != null && fileList.size()>0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxCrJYearCreditEva.getZxCrJYearCreditEvaId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        zxCrJYearCreditEva.setZxCrJYearCreditEvaId(UuidUtil.generate());
        zxCrJYearCreditEva.setCreateUserInfo(userKey, realName);
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            zxCrJYearCreditEva.setCompanyId(zxCrJYearCreditEva.getOrgID());
        }
        zxCrJYearCreditEva.setAuditStatus("0");
        int flag = zxCrJYearCreditEvaMapper.insert(zxCrJYearCreditEva);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCrJYearCreditEva);
        }
    }

    @Override
    public ResponseEntity updateZxCrJYearCreditEva(ZxCrJYearCreditEva zxCrJYearCreditEva) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCrJYearCreditEva dbZxCrJYearCreditEva = zxCrJYearCreditEvaMapper.selectByPrimaryKey(zxCrJYearCreditEva.getZxCrJYearCreditEvaId());

        if (dbZxCrJYearCreditEva != null && StrUtil.isNotEmpty(dbZxCrJYearCreditEva.getZxCrJYearCreditEvaId())) {
            if (!dbZxCrJYearCreditEva.getPeriod().equals(zxCrJYearCreditEva.getPeriod())) {
                return repEntity.layerMessage("no", "评价期次无法修改！");
            }
           // 机构ID
           dbZxCrJYearCreditEva.setOrgID(zxCrJYearCreditEva.getOrgID());
           // 机构名称
           dbZxCrJYearCreditEva.setOrgName(zxCrJYearCreditEva.getOrgName());
           // 评价期次
           dbZxCrJYearCreditEva.setPeriod(zxCrJYearCreditEva.getPeriod());
           // 评价日期
           dbZxCrJYearCreditEva.setDateTime(zxCrJYearCreditEva.getDateTime());
           // 最后编辑时间
           dbZxCrJYearCreditEva.setEditTime(zxCrJYearCreditEva.getEditTime());
           // 所属公司ID
           dbZxCrJYearCreditEva.setComID(zxCrJYearCreditEva.getComID());
           // 所属公司名称
           dbZxCrJYearCreditEva.setComName(zxCrJYearCreditEva.getComName());
           // 所属公司排序
           dbZxCrJYearCreditEva.setComOrders(zxCrJYearCreditEva.getComOrders());
           // 审核状态
           dbZxCrJYearCreditEva.setAuditStatus("0");
           // combProp
           dbZxCrJYearCreditEva.setCombProp(zxCrJYearCreditEva.getCombProp());
           // 明细
           dbZxCrJYearCreditEva.setItems(zxCrJYearCreditEva.getItems());
           // 备注
           dbZxCrJYearCreditEva.setRemarks(zxCrJYearCreditEva.getRemarks());
           // 排序
           dbZxCrJYearCreditEva.setSort(zxCrJYearCreditEva.getSort());
           // 共通
           dbZxCrJYearCreditEva.setModifyUserInfo(userKey, realName);
           flag = zxCrJYearCreditEvaMapper.updateByPrimaryKey(dbZxCrJYearCreditEva);
           //修改在新增(附件)
           ZxErpFile zxErpFileSelect = new ZxErpFile();
           zxErpFileSelect.setOtherId(zxCrJYearCreditEva.getZxCrJYearCreditEvaId());
           List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
           if(zxErpFiles != null && zxErpFiles.size()>0) {
               zxErpFileSelect.setModifyUserInfo(userKey, realName);
               zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
           }
           List<ZxErpFile> fileList = zxCrJYearCreditEva.getFileList();
           if(fileList != null && fileList.size()>0) {
               for (ZxErpFile zxErpFile : fileList) {
                   zxErpFile.setOtherId(zxCrJYearCreditEva.getZxCrJYearCreditEvaId());
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
            return repEntity.ok("sys.data.update",zxCrJYearCreditEva);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCrJYearCreditEva(List<ZxCrJYearCreditEva> zxCrJYearCreditEvaList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCrJYearCreditEvaList != null && zxCrJYearCreditEvaList.size() > 0) {
        	for(ZxCrJYearCreditEva zxCrJYearCreditEva2 : zxCrJYearCreditEvaList) {
        		//删除附件
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxCrJYearCreditEva2.getZxCrJYearCreditEvaId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if(zxErpFiles != null && zxErpFiles.size()>0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
        	}
           ZxCrJYearCreditEva zxCrJYearCreditEva = new ZxCrJYearCreditEva();
           zxCrJYearCreditEva.setModifyUserInfo(userKey, realName);
           flag = zxCrJYearCreditEvaMapper.batchDeleteUpdateZxCrJYearCreditEva(zxCrJYearCreditEvaList, zxCrJYearCreditEva);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCrJYearCreditEvaList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public ResponseEntity updateZxCrJYearCreditEvaAuditStatus(ZxCrJYearCreditEva zxCrJYearCreditEva) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCrJYearCreditEva dbZxCrJYearCreditEva = zxCrJYearCreditEvaMapper.selectByPrimaryKey(zxCrJYearCreditEva.getZxCrJYearCreditEvaId());
        if (dbZxCrJYearCreditEva != null && StrUtil.isNotEmpty(dbZxCrJYearCreditEva.getZxCrJYearCreditEvaId())) {
           // 审核状态
           dbZxCrJYearCreditEva.setAuditStatus("1");
           // 共通
           dbZxCrJYearCreditEva.setModifyUserInfo(userKey, realName);
           flag = zxCrJYearCreditEvaMapper.updateByPrimaryKey(dbZxCrJYearCreditEva);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCrJYearCreditEva);
        }
    }

}
