package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSkResInOutStockAllAmtMapper;
import com.apih5.mybatis.pojo.ZxSkResInOutStockAllAmt;
import com.apih5.service.ZxSkResInOutStockAllAmtService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkResInOutStockAllAmtService")
public class ZxSkResInOutStockAllAmtServiceImpl implements ZxSkResInOutStockAllAmtService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkResInOutStockAllAmtMapper zxSkResInOutStockAllAmtMapper;

    @Override
    public ResponseEntity getZxSkResInOutStockAllAmtListByCondition(ZxSkResInOutStockAllAmt zxSkResInOutStockAllAmt) {
        if (zxSkResInOutStockAllAmt == null) {
            zxSkResInOutStockAllAmt = new ZxSkResInOutStockAllAmt();
        }
        // 分页查询
        PageHelper.startPage(zxSkResInOutStockAllAmt.getPage(),zxSkResInOutStockAllAmt.getLimit());
        // 获取数据
        List<ZxSkResInOutStockAllAmt> zxSkResInOutStockAllAmtList = zxSkResInOutStockAllAmtMapper.selectByZxSkResInOutStockAllAmtList(zxSkResInOutStockAllAmt);
        // 得到分页信息
        PageInfo<ZxSkResInOutStockAllAmt> p = new PageInfo<>(zxSkResInOutStockAllAmtList);

        return repEntity.okList(zxSkResInOutStockAllAmtList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkResInOutStockAllAmtDetail(ZxSkResInOutStockAllAmt zxSkResInOutStockAllAmt) {
        if (zxSkResInOutStockAllAmt == null) {
            zxSkResInOutStockAllAmt = new ZxSkResInOutStockAllAmt();
        }
        // 获取数据
        ZxSkResInOutStockAllAmt dbZxSkResInOutStockAllAmt = zxSkResInOutStockAllAmtMapper.selectByPrimaryKey(zxSkResInOutStockAllAmt.getZxSkResInOutStockAllAmtId());
        // 数据存在
        if (dbZxSkResInOutStockAllAmt != null) {
            return repEntity.ok(dbZxSkResInOutStockAllAmt);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkResInOutStockAllAmt(ZxSkResInOutStockAllAmt zxSkResInOutStockAllAmt) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkResInOutStockAllAmt.setZxSkResInOutStockAllAmtId(UuidUtil.generate());
        zxSkResInOutStockAllAmt.setCreateUserInfo(userKey, realName);
        int flag = zxSkResInOutStockAllAmtMapper.insert(zxSkResInOutStockAllAmt);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkResInOutStockAllAmt);
        }
    }

    @Override
    public ResponseEntity updateZxSkResInOutStockAllAmt(ZxSkResInOutStockAllAmt zxSkResInOutStockAllAmt) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkResInOutStockAllAmt dbZxSkResInOutStockAllAmt = zxSkResInOutStockAllAmtMapper.selectByPrimaryKey(zxSkResInOutStockAllAmt.getZxSkResInOutStockAllAmtId());
        if (dbZxSkResInOutStockAllAmt != null && StrUtil.isNotEmpty(dbZxSkResInOutStockAllAmt.getZxSkResInOutStockAllAmtId())) {
           // 物资类别
           dbZxSkResInOutStockAllAmt.setResName(zxSkResInOutStockAllAmt.getResName());
           // 统计字母或代码
           dbZxSkResInOutStockAllAmt.setResCode(zxSkResInOutStockAllAmt.getResCode());
           // 年初结存(数量）
           dbZxSkResInOutStockAllAmt.setStockAmt(zxSkResInOutStockAllAmt.getStockAmt());
           // 累计收入
           dbZxSkResInOutStockAllAmt.setObuAmt(zxSkResInOutStockAllAmt.getObuAmt());
           // 累计收入合计
           dbZxSkResInOutStockAllAmt.setTotalAmt(zxSkResInOutStockAllAmt.getTotalAmt());
           // 累计收入总收入金额
           dbZxSkResInOutStockAllAmt.setTotalAmtAll(zxSkResInOutStockAllAmt.getTotalAmtAll());
           // 累计消费
           dbZxSkResInOutStockAllAmt.setOswAmt(zxSkResInOutStockAllAmt.getOswAmt());
           // 累计拨出
           dbZxSkResInOutStockAllAmt.setOtkAmt(zxSkResInOutStockAllAmt.getOtkAmt());
           // 盈亏
           dbZxSkResInOutStockAllAmt.setVinAmt(zxSkResInOutStockAllAmt.getVinAmt());
           // 期末库存
           dbZxSkResInOutStockAllAmt.setThisAmt(zxSkResInOutStockAllAmt.getThisAmt());
           // 备注
           dbZxSkResInOutStockAllAmt.setRemarks(zxSkResInOutStockAllAmt.getRemarks());
           // 排序
           dbZxSkResInOutStockAllAmt.setSort(zxSkResInOutStockAllAmt.getSort());
           // 共通
           dbZxSkResInOutStockAllAmt.setModifyUserInfo(userKey, realName);
           flag = zxSkResInOutStockAllAmtMapper.updateByPrimaryKey(dbZxSkResInOutStockAllAmt);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkResInOutStockAllAmt);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkResInOutStockAllAmt(List<ZxSkResInOutStockAllAmt> zxSkResInOutStockAllAmtList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkResInOutStockAllAmtList != null && zxSkResInOutStockAllAmtList.size() > 0) {
           ZxSkResInOutStockAllAmt zxSkResInOutStockAllAmt = new ZxSkResInOutStockAllAmt();
           zxSkResInOutStockAllAmt.setModifyUserInfo(userKey, realName);
           flag = zxSkResInOutStockAllAmtMapper.batchDeleteUpdateZxSkResInOutStockAllAmt(zxSkResInOutStockAllAmtList, zxSkResInOutStockAllAmt);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkResInOutStockAllAmtList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    @Override
    public List<ZxSkResInOutStockAllAmt> ureportZxSkResInOutStockAllAmt(ZxSkResInOutStockAllAmt zxSkResInOutStockAllAmt) {
    	if(zxSkResInOutStockAllAmt.getIsFinish().equals("null")) {
    		zxSkResInOutStockAllAmt.setIsFinish(null);
    	}
    	if(zxSkResInOutStockAllAmt.getResID().equals("null")) {
    		zxSkResInOutStockAllAmt.setResID(null);
    	}
    	
    	List<ZxSkResInOutStockAllAmt> zxSkResInOutStockAllAmtList = zxSkResInOutStockAllAmtMapper.selectZxSkResInOutStockAllAmt(zxSkResInOutStockAllAmt);
    	return zxSkResInOutStockAllAmtList;
    }
    
    @Override
    public ResponseEntity ureportZxSkResInOutStockAllAmtIdle(ZxSkResInOutStockAllAmt zxSkResInOutStockAllAmt) {
    	if (zxSkResInOutStockAllAmt == null) {
            zxSkResInOutStockAllAmt = new ZxSkResInOutStockAllAmt();
        }
        // 分页查询
        PageHelper.startPage(zxSkResInOutStockAllAmt.getPage(),zxSkResInOutStockAllAmt.getLimit());
        // 获取数据
        List<ZxSkResInOutStockAllAmt> zxSkResInOutStockAllAmtList = zxSkResInOutStockAllAmtMapper.selectZxSkResInOutStockAllAmt(zxSkResInOutStockAllAmt);
        // 得到分页信息
        PageInfo<ZxSkResInOutStockAllAmt> p = new PageInfo<>(zxSkResInOutStockAllAmtList);

        return repEntity.okList(zxSkResInOutStockAllAmtList, p.getTotal());
    	
    }
    
}
