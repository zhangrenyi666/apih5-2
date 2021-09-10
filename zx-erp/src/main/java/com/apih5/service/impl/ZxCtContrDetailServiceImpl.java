package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtContrDetailMapper;
import com.apih5.mybatis.pojo.ZxCtContrDetail;
import com.apih5.mybatis.pojo.ZxCtContrDetailAttr;
import com.apih5.service.ZxCtContrDetailAttrService;
import com.apih5.service.ZxCtContrDetailService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxCtContrDetailService")
public class ZxCtContrDetailServiceImpl implements ZxCtContrDetailService {

	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired(required = true)
	private ZxCtContrDetailMapper zxCtContrDetailMapper;
	
	@Autowired(required = true)
	private ZxCtContrDetailAttrService zxCtContrDetailAttrService;

	@Override
	public ResponseEntity getZxCtContrDetailListByCondition(ZxCtContrDetail zxCtContrDetail) {
		if (zxCtContrDetail == null) {
			zxCtContrDetail = new ZxCtContrDetail();
		}
		// 分页查询
		PageHelper.startPage(zxCtContrDetail.getPage(), zxCtContrDetail.getLimit());
		// 获取数据
		List<ZxCtContrDetail> zxCtContrDetailList = zxCtContrDetailMapper.selectByZxCtContrDetailList(zxCtContrDetail);
		// 得到分页信息
		PageInfo<ZxCtContrDetail> p = new PageInfo<>(zxCtContrDetailList);

		for (ZxCtContrDetail contrDetail : zxCtContrDetailList) {
			// 合同条款子表(保证金)
			ZxCtContrDetailAttr zxCtContrDetailAttr = new ZxCtContrDetailAttr();
			zxCtContrDetailAttr.setContrDetailID(contrDetail.getContrDetailId());
			@SuppressWarnings("unchecked")
			List<ZxCtContrDetailAttr> zxCtContrDetailAttrList = (List<ZxCtContrDetailAttr>) zxCtContrDetailAttrService.getZxCtContrDetailAttrListByCondition(zxCtContrDetailAttr).getData();
			contrDetail.setZxCtContrDetailAttrList(zxCtContrDetailAttrList);
		}
		return repEntity.okList(zxCtContrDetailList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxCtContrDetailDetail(ZxCtContrDetail zxCtContrDetail) {
		if (zxCtContrDetail == null) {
			zxCtContrDetail = new ZxCtContrDetail();
		}
		// 获取数据
		ZxCtContrDetail dbZxCtContrDetail = zxCtContrDetailMapper.selectByPrimaryKey(zxCtContrDetail.getContrDetailId());
		// 数据存在
		if (dbZxCtContrDetail != null) {
			// 合同条款子表(保证金)
			ZxCtContrDetailAttr zxCtContrDetailAttr = new ZxCtContrDetailAttr();
			zxCtContrDetailAttr.setContrDetailID(dbZxCtContrDetail.getContrDetailId());
			@SuppressWarnings("unchecked")
			List<ZxCtContrDetailAttr> zxCtContrDetailAttrList = (List<ZxCtContrDetailAttr>) zxCtContrDetailAttrService.getZxCtContrDetailAttrListByCondition(zxCtContrDetailAttr).getData();
			dbZxCtContrDetail.setZxCtContrDetailAttrList(zxCtContrDetailAttrList);
			return repEntity.ok(dbZxCtContrDetail);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZxCtContrDetail(ZxCtContrDetail zxCtContrDetail) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zxCtContrDetail.setContrDetailId(UuidUtil.generate());
		zxCtContrDetail.setCreateUserInfo(userKey, realName);
		int flag = zxCtContrDetailMapper.insert(zxCtContrDetail);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			// 合同条款子表(保证金)
	    	List<ZxCtContrDetailAttr> zxCtContrDetailAttrList = zxCtContrDetail.getZxCtContrDetailAttrList();
	    	if (zxCtContrDetailAttrList != null && zxCtContrDetailAttrList.size() > 0) {
				for (ZxCtContrDetailAttr zxCtContrDetailAttr : zxCtContrDetailAttrList) {
					zxCtContrDetailAttr.setContrDetailID(zxCtContrDetail.getContrDetailId());
					zxCtContrDetailAttrService.saveZxCtContrDetailAttr(zxCtContrDetailAttr);
				}
			}
			
			return repEntity.ok("sys.data.sava", zxCtContrDetail);
		}
	}

	@Override
	public ResponseEntity updateZxCtContrDetail(ZxCtContrDetail zxCtContrDetail) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxCtContrDetail dbZxCtContrDetail = zxCtContrDetailMapper.selectByPrimaryKey(zxCtContrDetail.getContrDetailId());
		if (dbZxCtContrDetail != null && StrUtil.isNotEmpty(dbZxCtContrDetail.getContrDetailId())) {
			// 合同ID
			dbZxCtContrDetail.setContractID(zxCtContrDetail.getContractID());
			// 工程类别
			dbZxCtContrDetail.setProjType(zxCtContrDetail.getProjType());
			// 项目性质
			dbZxCtContrDetail.setProjQuality(zxCtContrDetail.getProjQuality());
			// 合同英文名称
			dbZxCtContrDetail.setEname(zxCtContrDetail.getEname());
			// 合同位置
			dbZxCtContrDetail.setLocation(zxCtContrDetail.getLocation());
			// 地区
			dbZxCtContrDetail.setArea(zxCtContrDetail.getArea());
			// 合同长度
			dbZxCtContrDetail.setLength(zxCtContrDetail.getLength());
			// 清单金额（元）
			dbZxCtContrDetail.setQdMoney(zxCtContrDetail.getQdMoney());
			// 计日工金额（元）
			dbZxCtContrDetail.setJrgMoney(zxCtContrDetail.getJrgMoney());
			// 暂定金额（元）
			dbZxCtContrDetail.setZdMoney(zxCtContrDetail.getZdMoney());
			// 外币币种
			dbZxCtContrDetail.setForergnCurrencyType(zxCtContrDetail.getForergnCurrencyType());
			// 外币比重
			dbZxCtContrDetail.setForergnCurrencyProportion(zxCtContrDetail.getForergnCurrencyProportion());
			// 外币汇率（%）
			dbZxCtContrDetail.setForergnCurrencyExchangeRate(zxCtContrDetail.getForergnCurrencyExchangeRate());
			// 车道数
			dbZxCtContrDetail.setDriveways(zxCtContrDetail.getDriveways());
			// 公路级别
			dbZxCtContrDetail.setRoadLevel(zxCtContrDetail.getRoadLevel());
			// 动员预付款总额（元）
			dbZxCtContrDetail.setDyyfkMoney(zxCtContrDetail.getDyyfkMoney());
			// 动员预付款起扣点（元）
			dbZxCtContrDetail.setDyyfkqkdMoney(zxCtContrDetail.getDyyfkqkdMoney());
			// 扣回动员预付款比例（%）
			dbZxCtContrDetail.setKhdyyfkPercent(zxCtContrDetail.getKhdyyfkPercent());
			// 动员预付款全额扣回点（元）
			dbZxCtContrDetail.setDyyfkqekhdMoney(zxCtContrDetail.getDyyfkqekhdMoney());
			// 物资预付款比例（%）
			dbZxCtContrDetail.setClyfkPercent(zxCtContrDetail.getClyfkPercent());
			// 物资预付款限额（元）
			dbZxCtContrDetail.setClyfkxeMoney(zxCtContrDetail.getClyfkxeMoney());
			// 物资扣回款比例（%）
			dbZxCtContrDetail.setClkhkblPercent(zxCtContrDetail.getClkhkblPercent());
			// 迟扣款利息（%。）
			dbZxCtContrDetail.setCkklxPercent(zxCtContrDetail.getCkklxPercent());
			// 迟扣款利息限额（元）
			dbZxCtContrDetail.setCkklxxeMoney(zxCtContrDetail.getCkklxxeMoney());
			// 违约金限额（元）
			dbZxCtContrDetail.setWyjxeMoney(zxCtContrDetail.getWyjxeMoney());
			// 索赔金额限额（元）
			dbZxCtContrDetail.setCpjexeMoney(zxCtContrDetail.getCpjexeMoney());
			// 保留金起扣点（元）
			dbZxCtContrDetail.setBljqkdMoney(zxCtContrDetail.getBljqkdMoney());
			// 累计扣保留金限额（元）
			dbZxCtContrDetail.setLjkbljxeMoney(zxCtContrDetail.getLjkbljxeMoney());
			// 保留金比例（%）
			dbZxCtContrDetail.setBljblPercent(zxCtContrDetail.getBljblPercent());
			// 调整指数
			dbZxCtContrDetail.setAdjustIndx(zxCtContrDetail.getAdjustIndx());
			// 工程项目及工程数量
			dbZxCtContrDetail.setProjDetail(zxCtContrDetail.getProjDetail());
			// 业主节点工期
			dbZxCtContrDetail.setOwnerNodeDeys(zxCtContrDetail.getOwnerNodeDeys());
			// 业主主要专用条款
			dbZxCtContrDetail.setOwnerSpecialClause(zxCtContrDetail.getOwnerSpecialClause());
			// 项目员工人数
			dbZxCtContrDetail.setProjPersonTotal(zxCtContrDetail.getProjPersonTotal());
			// 其中外聘人数
			dbZxCtContrDetail.setProjOtherPerson(zxCtContrDetail.getProjOtherPerson());
			// 是否历史项目
			dbZxCtContrDetail.setIsHistory(zxCtContrDetail.getIsHistory());
			// combProp
			dbZxCtContrDetail.setCombProp(zxCtContrDetail.getCombProp());
			// pp1
			dbZxCtContrDetail.setPp1(zxCtContrDetail.getPp1());
			// pp2
			dbZxCtContrDetail.setPp2(zxCtContrDetail.getPp2());
			// pp3
			dbZxCtContrDetail.setPp3(zxCtContrDetail.getPp3());
			// pp4
			dbZxCtContrDetail.setPp4(zxCtContrDetail.getPp4());
			// pp5
			dbZxCtContrDetail.setPp5(zxCtContrDetail.getPp5());
			// pp6
			dbZxCtContrDetail.setPp6(zxCtContrDetail.getPp6());
			// pp7
			dbZxCtContrDetail.setPp7(zxCtContrDetail.getPp7());
			// pp8
			dbZxCtContrDetail.setPp8(zxCtContrDetail.getPp8());
			// pp9
			dbZxCtContrDetail.setPp9(zxCtContrDetail.getPp9());
			// pp10
			dbZxCtContrDetail.setPp10(zxCtContrDetail.getPp10());
			// 编辑时间
			dbZxCtContrDetail.setEditTime(zxCtContrDetail.getEditTime());
			// 国家
			dbZxCtContrDetail.setCountry(zxCtContrDetail.getCountry());
			// 项目特征
			dbZxCtContrDetail.setProjFea(zxCtContrDetail.getProjFea());
			// 备注
			dbZxCtContrDetail.setRemark(zxCtContrDetail.getRemark());
			// 排序
			dbZxCtContrDetail.setSort(zxCtContrDetail.getSort());
			// 共通
			dbZxCtContrDetail.setModifyUserInfo(userKey, realName);
			flag = zxCtContrDetailMapper.updateByPrimaryKey(dbZxCtContrDetail);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			// 合同条款子表(保证金)
        	ZxCtContrDetailAttr delContrDetailAttr = new ZxCtContrDetailAttr();
        	delContrDetailAttr.setContrDetailID(dbZxCtContrDetail.getContrDetailId());
        	zxCtContrDetailAttrService.deleteAllZxCtContrDetailAttr(delContrDetailAttr);
        	
        	List<ZxCtContrDetailAttr> zxCtContrDetailAttrList = zxCtContrDetail.getZxCtContrDetailAttrList();
        	if (zxCtContrDetailAttrList != null && zxCtContrDetailAttrList.size() > 0) {
				for (ZxCtContrDetailAttr zxCtContrDetailAttr : zxCtContrDetailAttrList) {
					zxCtContrDetailAttr.setContrDetailID(dbZxCtContrDetail.getContrDetailId());
					zxCtContrDetailAttrService.saveZxCtContrDetailAttr(zxCtContrDetailAttr);
				}
			}
			return repEntity.ok("sys.data.update", zxCtContrDetail);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZxCtContrDetail(List<ZxCtContrDetail> zxCtContrDetailList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zxCtContrDetailList != null && zxCtContrDetailList.size() > 0) {
			ZxCtContrDetail zxCtContrDetail = new ZxCtContrDetail();
			zxCtContrDetail.setModifyUserInfo(userKey, realName);
			flag = zxCtContrDetailMapper.batchDeleteUpdateZxCtContrDetail(zxCtContrDetailList, zxCtContrDetail);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			for (ZxCtContrDetail zxCtContrDetail : zxCtContrDetailList) {
				// 合同条款子表(保证金)
	        	ZxCtContrDetailAttr delContrDetailAttr = new ZxCtContrDetailAttr();
	        	delContrDetailAttr.setContrDetailID(zxCtContrDetail.getContrDetailId());
	        	zxCtContrDetailAttrService.deleteAllZxCtContrDetailAttr(delContrDetailAttr);
			}
			return repEntity.ok("sys.data.delete", zxCtContrDetailList);
		}
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
