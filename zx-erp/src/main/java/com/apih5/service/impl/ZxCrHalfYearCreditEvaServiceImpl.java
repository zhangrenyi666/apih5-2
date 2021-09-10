package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCrHalfYearCreditEvaMapper;
import com.apih5.mybatis.pojo.ZxCrHalfYearCreditEva;
import com.apih5.service.ZxCrHalfYearCreditEvaItemService;
import com.apih5.service.ZxCrHalfYearCreditEvaService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxCrHalfYearCreditEvaService")
public class ZxCrHalfYearCreditEvaServiceImpl implements ZxCrHalfYearCreditEvaService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCrHalfYearCreditEvaMapper zxCrHalfYearCreditEvaMapper;

    @Autowired(required = true)
    private ZxCrHalfYearCreditEvaItemService zxCrHalfYearCreditEvaItemService;
    
    @Override
    public ResponseEntity getZxCrHalfYearCreditEvaListByCondition(ZxCrHalfYearCreditEva zxCrHalfYearCreditEva) {
        if (zxCrHalfYearCreditEva == null) {
            zxCrHalfYearCreditEva = new ZxCrHalfYearCreditEva();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCrHalfYearCreditEva.setCompanyId("");
        	zxCrHalfYearCreditEva.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxCrHalfYearCreditEva.setCompanyId(zxCrHalfYearCreditEva.getOrgID());
        	zxCrHalfYearCreditEva.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxCrHalfYearCreditEva.setOrgID(zxCrHalfYearCreditEva.getOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxCrHalfYearCreditEva.getPage(),zxCrHalfYearCreditEva.getLimit());
        // 获取数据
        List<ZxCrHalfYearCreditEva> zxCrHalfYearCreditEvaList = zxCrHalfYearCreditEvaMapper.selectByZxCrHalfYearCreditEvaList(zxCrHalfYearCreditEva);
        // 得到分页信息
        PageInfo<ZxCrHalfYearCreditEva> p = new PageInfo<>(zxCrHalfYearCreditEvaList);

        return repEntity.okList(zxCrHalfYearCreditEvaList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCrHalfYearCreditEvaDetail(ZxCrHalfYearCreditEva zxCrHalfYearCreditEva) {
        if (zxCrHalfYearCreditEva == null) {
            zxCrHalfYearCreditEva = new ZxCrHalfYearCreditEva();
        }
        // 获取数据
        ZxCrHalfYearCreditEva dbZxCrHalfYearCreditEva = zxCrHalfYearCreditEvaMapper.selectByPrimaryKey(zxCrHalfYearCreditEva.getZxCrHalfYearCreditEvaId());
        // 数据存在
        if (dbZxCrHalfYearCreditEva != null) {
            return repEntity.ok(dbZxCrHalfYearCreditEva);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCrHalfYearCreditEva(ZxCrHalfYearCreditEva zxCrHalfYearCreditEva) {
        // 唯一性校验
        List<ZxCrHalfYearCreditEva> zxCrHalfYearCreditEvaList = zxCrHalfYearCreditEvaMapper.selectByZxCrHalfYearCreditEvaListAll(zxCrHalfYearCreditEva);
        for(ZxCrHalfYearCreditEva zxCrHalfYearCreditEva1 : zxCrHalfYearCreditEvaList) {
        	 if(zxCrHalfYearCreditEva1.getOrgName().equals(zxCrHalfYearCreditEva.getOrgName())
        			 && zxCrHalfYearCreditEva1.getPeriod().equals(zxCrHalfYearCreditEva.getPeriod())){
        		 return repEntity.layerMessage("no", "数据已经存在，不可再次新增！");
             }
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        //String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            zxCrHalfYearCreditEva.setCompanyId(zxCrHalfYearCreditEva.getOrgID());
        }
        zxCrHalfYearCreditEva.setZxCrHalfYearCreditEvaId(UuidUtil.generate());
        zxCrHalfYearCreditEva.setCreateUserInfo(userKey, realName);
        zxCrHalfYearCreditEva.setAuditStatus("0");
        int flag = zxCrHalfYearCreditEvaMapper.insert(zxCrHalfYearCreditEva);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCrHalfYearCreditEva);
        }
    }

    @Override
    public ResponseEntity updateZxCrHalfYearCreditEva(ZxCrHalfYearCreditEva zxCrHalfYearCreditEva) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);

//        // 唯一性校验
//        List<ZxCrHalfYearCreditEva> zxCrHalfYearCreditEvaList = zxCrHalfYearCreditEvaMapper.selectByZxCrHalfYearCreditEvaListAll(zxCrHalfYearCreditEva);
//        for(ZxCrHalfYearCreditEva zxCrHalfYearCreditEva1 : zxCrHalfYearCreditEvaList) {
//        	 if(zxCrHalfYearCreditEva1.getOrgName().equals(zxCrHalfYearCreditEva.getOrgName())
//        			 && zxCrHalfYearCreditEva1.getPeriod().equals(zxCrHalfYearCreditEva.getPeriod())){
//        		 return repEntity.layerMessage("no", "数据已经存在，不可再次新增！");
//             }
//        }
        int flag = 0;
        ZxCrHalfYearCreditEva dbZxCrHalfYearCreditEva = zxCrHalfYearCreditEvaMapper.selectByPrimaryKey(zxCrHalfYearCreditEva.getZxCrHalfYearCreditEvaId());
        if (dbZxCrHalfYearCreditEva != null && StrUtil.isNotEmpty(dbZxCrHalfYearCreditEva.getZxCrHalfYearCreditEvaId())) {
            if (!zxCrHalfYearCreditEva.getPeriod().equals(dbZxCrHalfYearCreditEva.getPeriod())) {
                return repEntity.layerMessage("no", "评价期次无法修改！");
            }
            // 机构ID
           dbZxCrHalfYearCreditEva.setOrgID(zxCrHalfYearCreditEva.getOrgID());
           // 机构名称
           dbZxCrHalfYearCreditEva.setOrgName(zxCrHalfYearCreditEva.getOrgName());
           // 评价期次
           dbZxCrHalfYearCreditEva.setPeriod(zxCrHalfYearCreditEva.getPeriod());
           // 评价日期
           dbZxCrHalfYearCreditEva.setDateTime(zxCrHalfYearCreditEva.getDateTime());
           // 最后编辑时间
           dbZxCrHalfYearCreditEva.setEditTime(zxCrHalfYearCreditEva.getEditTime());
           // 所属公司ID
           dbZxCrHalfYearCreditEva.setComID(zxCrHalfYearCreditEva.getComID());
           // 所属公司名称
           dbZxCrHalfYearCreditEva.setComName(zxCrHalfYearCreditEva.getComName());
           // 所属公司排序
           dbZxCrHalfYearCreditEva.setComOrders(zxCrHalfYearCreditEva.getComOrders());
           // 审核状态
           dbZxCrHalfYearCreditEva.setAuditStatus("0");
           // 明细
           dbZxCrHalfYearCreditEva.setItems(zxCrHalfYearCreditEva.getItems());
           // 备注
           dbZxCrHalfYearCreditEva.setRemarks(zxCrHalfYearCreditEva.getRemarks());
           // 排序
           dbZxCrHalfYearCreditEva.setSort(zxCrHalfYearCreditEva.getSort());
           // 共通
           dbZxCrHalfYearCreditEva.setModifyUserInfo(userKey, realName);
           flag = zxCrHalfYearCreditEvaMapper.updateByPrimaryKey(dbZxCrHalfYearCreditEva);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCrHalfYearCreditEva);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCrHalfYearCreditEva(List<ZxCrHalfYearCreditEva> zxCrHalfYearCreditEvaList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCrHalfYearCreditEvaList != null && zxCrHalfYearCreditEvaList.size() > 0) {
           ZxCrHalfYearCreditEva zxCrHalfYearCreditEva = new ZxCrHalfYearCreditEva();
           zxCrHalfYearCreditEva.setModifyUserInfo(userKey, realName);
           flag = zxCrHalfYearCreditEvaMapper.batchDeleteUpdateZxCrHalfYearCreditEva(zxCrHalfYearCreditEvaList, zxCrHalfYearCreditEva);
        }
        //连同明细里的数据一并清空
        zxCrHalfYearCreditEvaItemService.batchDeleteUpdateAll();
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCrHalfYearCreditEvaList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public ResponseEntity updateauditStatus(ZxCrHalfYearCreditEva zxCrHalfYearCreditEva) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCrHalfYearCreditEva dbZxCrHalfYearCreditEva = zxCrHalfYearCreditEvaMapper.selectByPrimaryKey(zxCrHalfYearCreditEva.getZxCrHalfYearCreditEvaId());
        if (dbZxCrHalfYearCreditEva != null && StrUtil.isNotEmpty(dbZxCrHalfYearCreditEva.getZxCrHalfYearCreditEvaId())) {
           // 共通
           dbZxCrHalfYearCreditEva.setModifyUserInfo(userKey, realName);
           flag = zxCrHalfYearCreditEvaMapper.updateauditStatusList(dbZxCrHalfYearCreditEva);
       }
       // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCrHalfYearCreditEva);
        }
    }
}
