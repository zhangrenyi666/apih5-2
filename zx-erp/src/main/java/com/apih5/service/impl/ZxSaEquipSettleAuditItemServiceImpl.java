package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSaEquipSettleAuditItemMapper;
import com.apih5.mybatis.dao.ZxSaEquipSettleAuditMapper;
import com.apih5.mybatis.pojo.ZxSaEquipSettleAudit;
import com.apih5.mybatis.pojo.ZxSaEquipSettleAuditItem;
import com.apih5.service.ZxSaEquipSettleAuditItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;

@Service("zxSaEquipSettleAuditItemService")
public class ZxSaEquipSettleAuditItemServiceImpl implements ZxSaEquipSettleAuditItemService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSaEquipSettleAuditItemMapper zxSaEquipSettleAuditItemMapper;
    
    @Autowired(required = true)
    private ZxSaEquipSettleAuditMapper zxSaEquipSettleAuditMapper;

    @Override
    public ResponseEntity getZxSaEquipSettleAuditItemListByCondition(ZxSaEquipSettleAuditItem zxSaEquipSettleAuditItem) {
        if (zxSaEquipSettleAuditItem == null) {
            zxSaEquipSettleAuditItem = new ZxSaEquipSettleAuditItem();
        }
        if(StrUtil.isEmpty(zxSaEquipSettleAuditItem.getZxSaEquipSettleAuditId())) {
     	   return repEntity.layerMessage("no", "结算单id【zxSaEquipSettleAuditId】必传！"); 
        }
         
        // 分页查询
        PageHelper.startPage(zxSaEquipSettleAuditItem.getPage(),zxSaEquipSettleAuditItem.getLimit());
        // 获取数据
        List<ZxSaEquipSettleAuditItem> zxSaEquipSettleAuditItemList = zxSaEquipSettleAuditItemMapper.selectByZxSaEquipSettleAuditItemList(zxSaEquipSettleAuditItem);
        // 得到分页信息
        PageInfo<ZxSaEquipSettleAuditItem> p = new PageInfo<>(zxSaEquipSettleAuditItemList);

        return repEntity.okList(zxSaEquipSettleAuditItemList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSaEquipSettleAuditItemDetail(ZxSaEquipSettleAuditItem zxSaEquipSettleAuditItem) {
        if (zxSaEquipSettleAuditItem == null) {
            zxSaEquipSettleAuditItem = new ZxSaEquipSettleAuditItem();
        }
        // 获取数据
        ZxSaEquipSettleAuditItem dbZxSaEquipSettleAuditItem = zxSaEquipSettleAuditItemMapper.selectByPrimaryKey(zxSaEquipSettleAuditItem.getZxSaEquipSettleAuditItemId());
        // 数据存在
        if (dbZxSaEquipSettleAuditItem != null) {
            return repEntity.ok(dbZxSaEquipSettleAuditItem);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSaEquipSettleAuditItem(ZxSaEquipSettleAuditItem zxSaEquipSettleAuditItem) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSaEquipSettleAuditItem.setZxSaEquipSettleAuditItemId(UuidUtil.generate());
        zxSaEquipSettleAuditItem.setCreateUserInfo(userKey, realName);
        int flag = zxSaEquipSettleAuditItemMapper.insert(zxSaEquipSettleAuditItem);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSaEquipSettleAuditItem);
        }
    }

    @Override
    public ResponseEntity updateZxSaEquipSettleAuditItem(ZxSaEquipSettleAuditItem zxSaEquipSettleAuditItem) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZxSaEquipSettleAuditItem dbZxSaEquipSettleAuditItem = zxSaEquipSettleAuditItemMapper.selectByPrimaryKey(zxSaEquipSettleAuditItem.getZxSaEquipSettleAuditItemId());
    	if (dbZxSaEquipSettleAuditItem != null && StrUtil.isNotEmpty(dbZxSaEquipSettleAuditItem.getZxSaEquipSettleAuditItemId())) {
    		BigDecimal thisAmt_orig = new BigDecimal("0");
    		BigDecimal totalAmt_orig = new BigDecimal("0");
    		if(StrUtil.isNotEmpty(dbZxSaEquipSettleAuditItem.getThisAmt())) {
    			thisAmt_orig = new BigDecimal(dbZxSaEquipSettleAuditItem.getThisAmt());
    		}
    		if(StrUtil.isNotEmpty(dbZxSaEquipSettleAuditItem.getTotalAmt())) {
    			totalAmt_orig = new BigDecimal(dbZxSaEquipSettleAuditItem.getTotalAmt());
    		}
    		// 本期(元)
    		dbZxSaEquipSettleAuditItem.setThisAmt(zxSaEquipSettleAuditItem.getThisAmt());
    		// 开累(元)=原来的-旧的+新的
    		BigDecimal  newTotalAmt = CalcUtils.calcAdd(CalcUtils.calcSubtract(new BigDecimal(dbZxSaEquipSettleAuditItem.getTotalAmt()), thisAmt_orig), new BigDecimal(zxSaEquipSettleAuditItem.getThisAmt()));
    		dbZxSaEquipSettleAuditItem.setTotalAmt(newTotalAmt+"");
    		// 共通
    		dbZxSaEquipSettleAuditItem.setModifyUserInfo(userKey, realName);
    		flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(dbZxSaEquipSettleAuditItem);
    		BigDecimal thisAmt_new = new BigDecimal(dbZxSaEquipSettleAuditItem.getThisAmt());
    		BigDecimal totalAmt_new = new BigDecimal(dbZxSaEquipSettleAuditItem.getTotalAmt());
    //更新其他统计项的金额=返还保证金合计,应付租赁款(大小写)
    		ZxSaEquipSettleAuditItem auditItemSelect = new ZxSaEquipSettleAuditItem();
    		auditItemSelect.setZxSaEquipSettleAuditId(dbZxSaEquipSettleAuditItem.getZxSaEquipSettleAuditId());
    		auditItemSelect.setContractID(dbZxSaEquipSettleAuditItem.getContractID());
    		auditItemSelect.setPeriod(dbZxSaEquipSettleAuditItem.getPeriod());
    		auditItemSelect.setBaseFlag("false");
    		List<ZxSaEquipSettleAuditItem> auditItemSelectList = zxSaEquipSettleAuditItemMapper.selectByZxSaEquipSettleAuditItemList(auditItemSelect);
    		if(auditItemSelectList != null && auditItemSelectList.size() >0) {
    			BigDecimal thisAmt_100 = new BigDecimal("0");
    			BigDecimal totalAmt_100 = new BigDecimal("0");
    			BigDecimal thisAmt_300 = new BigDecimal("0");
    			BigDecimal totalAmt_300 = new BigDecimal("0");
    			BigDecimal thisAmt_500 = new BigDecimal("0");
    			BigDecimal totalAmt_500 = new BigDecimal("0");
    			BigDecimal thisAmt_700 = new BigDecimal("0");
    			BigDecimal totalAmt_700 = new BigDecimal("0");
    			for (ZxSaEquipSettleAuditItem auditItem : auditItemSelectList) {
					if(StrUtil.equals(auditItem.getStatisticsNo(), "100100")) {
						thisAmt_100 = new BigDecimal(auditItem.getThisAmt());
						totalAmt_100 = new BigDecimal(auditItem.getTotalAmt());
					}else if(StrUtil.equals(auditItem.getStatisticsNo(), "100300")) {
						thisAmt_300 = new BigDecimal(auditItem.getThisAmt());
						totalAmt_300 = new BigDecimal(auditItem.getTotalAmt());
					}else if(StrUtil.equals(auditItem.getStatisticsNo(), "100500")) {
						//总的+新的-旧的
						auditItem.setThisAmt(CalcUtils.calcSubtract(CalcUtils.calcAdd(new BigDecimal(auditItem.getThisAmt()), thisAmt_new), thisAmt_orig)+"");
						auditItem.setTotalAmt(CalcUtils.calcSubtract(CalcUtils.calcAdd(new BigDecimal(auditItem.getTotalAmt()), totalAmt_new), totalAmt_orig)+"");
						auditItem.setModifyUserInfo(userKey, realName);
						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItem);
						thisAmt_500 = new BigDecimal(auditItem.getThisAmt());
						totalAmt_500 = new BigDecimal(auditItem.getTotalAmt());
					}else if(StrUtil.equals(auditItem.getStatisticsNo(), "100700")) {
						//700 = 100-300+500
						auditItem.setThisAmt(CalcUtils.calcAdd(CalcUtils.calcSubtract(thisAmt_100, thisAmt_300), thisAmt_500)+"");
						auditItem.setTotalAmt(CalcUtils.calcAdd(CalcUtils.calcSubtract(totalAmt_100, totalAmt_300), totalAmt_500)+"");
						auditItem.setModifyUserInfo(userKey, realName);
						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItem);
						thisAmt_700 = new BigDecimal(auditItem.getThisAmt());
						totalAmt_700 = new BigDecimal(auditItem.getTotalAmt());
					}else if(StrUtil.equals(auditItem.getStatisticsNo(), "100800")) {
						auditItem.setThisAmt(Convert.digitToChinese(thisAmt_700));
						auditItem.setTotalAmt(Convert.digitToChinese(totalAmt_700));
						auditItem.setModifyUserInfo(userKey, realName);
						flag = zxSaEquipSettleAuditItemMapper.updateByPrimaryKey(auditItem);
					}
				}
    	//更新统计项就要更新结算单
    			//本期结算含税金额thisAmt	= eqAuditItem_100
    			//开累结算含税金额totalAmt = eqAuditItem_100
    			//本期应支付含税金额thisPayAmt = eqAuditItem_700
    			//开累应支付含税金额totalPayAmt = eqAuditItem_700
    			ZxSaEquipSettleAudit auditSelect = zxSaEquipSettleAuditMapper.selectByPrimaryKey(auditItemSelectList.get(0).getZxSaEquipSettleAuditId());
		        if(auditSelect != null && StrUtil.isNotEmpty(auditSelect.getZxSaEquipSettleAuditId())) {
		        	auditSelect.setThisAmt(thisAmt_100);
		        	auditSelect.setTotalAmt(totalAmt_100);
		        	auditSelect.setThisPayAmt(thisAmt_700);
		        	auditSelect.setTotalPayAmt(totalAmt_700);
		        	flag = zxSaEquipSettleAuditMapper.updateByPrimaryKey(auditSelect);
		        }
    		}
    	}
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSaEquipSettleAuditItem);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSaEquipSettleAuditItem(List<ZxSaEquipSettleAuditItem> zxSaEquipSettleAuditItemList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSaEquipSettleAuditItemList != null && zxSaEquipSettleAuditItemList.size() > 0) {
           ZxSaEquipSettleAuditItem zxSaEquipSettleAuditItem = new ZxSaEquipSettleAuditItem();
           zxSaEquipSettleAuditItem.setModifyUserInfo(userKey, realName);
           flag = zxSaEquipSettleAuditItemMapper.batchDeleteUpdateZxSaEquipSettleAuditItem(zxSaEquipSettleAuditItemList, zxSaEquipSettleAuditItem);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSaEquipSettleAuditItemList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
