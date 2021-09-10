package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtEqContrAlterItemResoureMapper;
import com.apih5.mybatis.dao.ZxCtEqContrItemMapper;
import com.apih5.mybatis.dao.ZxCtEqContrSupplyMapper;
import com.apih5.mybatis.dao.ZxCtEqContractMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxCtEqContrAlterItemResoure;
import com.apih5.mybatis.pojo.ZxCtEqContrItem;
import com.apih5.mybatis.pojo.ZxCtEqContrSupply;
import com.apih5.mybatis.pojo.ZxCtEqContract;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxCtEqContrSupplyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;

@Service("zxCtEqContrSupplyService")
public class ZxCtEqContrSupplyServiceImpl implements ZxCtEqContrSupplyService {

	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired(required = true)
	private ZxCtEqContrSupplyMapper zxCtEqContrSupplyMapper;

	@Autowired(required = true)
	private ZxErpFileMapper zxErpFileMapper;
	
    @Autowired(required = true)
    private ZxCtEqContractMapper zxCtEqContractMapper;
    
    @Autowired(required = true)
    private ZxCtEqContrItemMapper zxCtEqContrItemMapper;
    
    @Autowired(required = true)
    private ZxCtEqContrAlterItemResoureMapper zxCtEqContrAlterItemResoureMapper;

	@Override
	public ResponseEntity getZxCtEqContrSupplyListByCondition(ZxCtEqContrSupply zxCtEqContrSupply) {
		if (zxCtEqContrSupply == null) {
			zxCtEqContrSupply = new ZxCtEqContrSupply();
		}
		 HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
	        String userId = TokenUtils.getUserId(request);
	        String ext1 = TokenUtils.getExt1(request);
	        // 集团全部可见
	        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
	                || StrUtil.equals("admin", userId)) {
	        	zxCtEqContrSupply.setComID("");
	        	zxCtEqContrSupply.setOrgID("");
	        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
	            // 公司只看见自己的
	        	zxCtEqContrSupply.setComID(zxCtEqContrSupply.getOrgID());
	        	zxCtEqContrSupply.setOrgID("");
	        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
	                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
	            // 项目通过右上角数据
	        	zxCtEqContrSupply.setOrgID(zxCtEqContrSupply.getOrgID());
	        }
		// 分页查询
		PageHelper.startPage(zxCtEqContrSupply.getPage(),zxCtEqContrSupply.getLimit());
		// 获取数据
		List<ZxCtEqContrSupply> zxCtEqContrSupplyList = zxCtEqContrSupplyMapper.selectByZxCtEqContrSupplyList(zxCtEqContrSupply);
		for (ZxCtEqContrSupply zxCtEqContrSupply2 : zxCtEqContrSupplyList) {
			ZxErpFile file = new ZxErpFile();
			file.setOtherType("0");
			file.setOtherId(zxCtEqContrSupply2.getZxCtEqContrSupplyId());
			List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(file);
			zxCtEqContrSupply2.setZxErpFileList(zxErpFileList);
			file.setOtherType("1");
    		List<ZxErpFile> zxErpMainFileList = zxErpFileMapper.selectByZxErpFileList(file);
    		zxCtEqContrSupply2.setZxErpMainFileList(zxErpMainFileList);
		}
		// 得到分页信息
		PageInfo<ZxCtEqContrSupply> p = new PageInfo<>(zxCtEqContrSupplyList);

		return repEntity.okList(zxCtEqContrSupplyList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxCtEqContrSupplyDetail(ZxCtEqContrSupply zxCtEqContrSupply) {
		if (zxCtEqContrSupply == null) {
			zxCtEqContrSupply = new ZxCtEqContrSupply();
		}
		ZxCtEqContrSupply dbZxCtEqContrSupply = new ZxCtEqContrSupply();
		if(StrUtil.isNotEmpty(zxCtEqContrSupply.getWorkId())) {
			ZxCtEqContrSupply contrSupply = new ZxCtEqContrSupply();
			contrSupply.setWorkId(zxCtEqContrSupply.getWorkId());
			List<ZxCtEqContrSupply> csList = zxCtEqContrSupplyMapper.selectByZxCtEqContrSupplyList(contrSupply);
			if(csList != null && csList.size() >0) {
				dbZxCtEqContrSupply = csList.get(0);
			}
		}else if(StrUtil.isNotEmpty(zxCtEqContrSupply.getZxCtEqContrSupplyId())) {
			// 获取数据
			dbZxCtEqContrSupply = zxCtEqContrSupplyMapper.selectByPrimaryKey(zxCtEqContrSupply.getZxCtEqContrSupplyId());
		}
		if (dbZxCtEqContrSupply != null && StrUtil.isNotEmpty(dbZxCtEqContrSupply.getZxCtEqContrSupplyId())) {
			ZxErpFile file = new ZxErpFile();
			file.setOtherType("0");
			file.setOtherId(dbZxCtEqContrSupply.getZxCtEqContrSupplyId());
			List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(file);
			dbZxCtEqContrSupply.setZxErpFileList(zxErpFileList);
			file.setOtherType("1");
    		List<ZxErpFile> zxErpMainFileList = zxErpFileMapper.selectByZxErpFileList(file);
    		dbZxCtEqContrSupply.setZxErpMainFileList(zxErpMainFileList);
		} 
		// 数据存在
		if (dbZxCtEqContrSupply != null) {
			return repEntity.ok(dbZxCtEqContrSupply);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZxCtEqContrSupply(ZxCtEqContrSupply zxCtEqContrSupply) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		//同一合同往期补充协议已经评审通过，才可保存本次补充协议
		//合同id
		ZxCtEqContrSupply contrSupply = new ZxCtEqContrSupply();
		contrSupply.setContractID(zxCtEqContrSupply.getContractID());
		contrSupply.setApih5FlowStatusNot2Flag("查询未通过的所有数据");
		List<ZxCtEqContrSupply> contrSupplyList = zxCtEqContrSupplyMapper.selectByZxCtEqContrSupplyList(contrSupply);
		if(contrSupplyList != null && contrSupplyList.size() >0) {
			return repEntity.layerMessage("no", "该合同的补充协议存在未评审通过的，请先评审完！");
		}
		zxCtEqContrSupply.setZxCtEqContrSupplyId(UuidUtil.generate());
		zxCtEqContrSupply.setContrAlterID(UuidUtil.generate());
		zxCtEqContrSupply.setApih5FlowStatus("-1");
		zxCtEqContrSupply.setCreateUserInfo(userKey, realName);
		int flag = zxCtEqContrSupplyMapper.insert(zxCtEqContrSupply);
		//新增附件
		if(zxCtEqContrSupply.getZxErpFileList() != null && zxCtEqContrSupply.getZxErpFileList().size() >0) {
			for (ZxErpFile file : zxCtEqContrSupply.getZxErpFileList()) {
				file.setUid(UuidUtil.generate());
				file.setOtherType("0");
				file.setOtherId(zxCtEqContrSupply.getZxCtEqContrSupplyId());
				file.setCreateUserInfo(userKey, realName);
				flag = zxErpFileMapper.insert(file);
			}
		}
		if(zxCtEqContrSupply.getZxErpMainFileList() != null && zxCtEqContrSupply.getZxErpMainFileList().size() >0) {
			for (ZxErpFile file : zxCtEqContrSupply.getZxErpMainFileList()) {
				file.setUid(UuidUtil.generate());
				file.setOtherType("1");
				file.setOtherId(zxCtEqContrSupply.getZxCtEqContrSupplyId());
				file.setCreateUserInfo(userKey, realName);
				flag = zxErpFileMapper.insert(file);
			}
		}
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zxCtEqContrSupply);
		}
	}

	@Override
	public ResponseEntity updateZxCtEqContrSupply(ZxCtEqContrSupply zxCtEqContrSupply) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxCtEqContrSupply dbZxCtEqContrSupply = zxCtEqContrSupplyMapper.selectByPrimaryKey(zxCtEqContrSupply.getZxCtEqContrSupplyId());
		if (dbZxCtEqContrSupply != null && StrUtil.isNotEmpty(dbZxCtEqContrSupply.getZxCtEqContrSupplyId())) {
			// 补充协议名称
			dbZxCtEqContrSupply.setSupplyName(zxCtEqContrSupply.getSupplyName());
			// 开工日期
			dbZxCtEqContrSupply.setStartDate(zxCtEqContrSupply.getStartDate());
			// 竣工日期
			dbZxCtEqContrSupply.setEndDate(zxCtEqContrSupply.getEndDate());
			// 合同工期
			dbZxCtEqContrSupply.setCsTimeLimit(zxCtEqContrSupply.getCsTimeLimit());
			// 合同签订人
			dbZxCtEqContrSupply.setAgent(zxCtEqContrSupply.getAgent());
			// 合同内容
			dbZxCtEqContrSupply.setContent(zxCtEqContrSupply.getContent());
			//备注
			dbZxCtEqContrSupply.setRemark(zxCtEqContrSupply.getRemark());
			//workId
			dbZxCtEqContrSupply.setWorkId(zxCtEqContrSupply.getWorkId());
			// 流程状态
			dbZxCtEqContrSupply.setApih5FlowStatus(zxCtEqContrSupply.getApih5FlowStatus());
			//流程的意见
			if(StrUtil.equals("opinionField1", zxCtEqContrSupply.getOpinionField(), true)){
				dbZxCtEqContrSupply.setOpinionField1(zxCtEqContrSupply.getOpinionContent(realName, dbZxCtEqContrSupply.getOpinionField1()));
			}
			//
			if(StrUtil.equals("opinionField2", zxCtEqContrSupply.getOpinionField(), true)){
				dbZxCtEqContrSupply.setOpinionField2(zxCtEqContrSupply.getOpinionContent(realName, dbZxCtEqContrSupply.getOpinionField2()));
			}
			//
			if(StrUtil.equals("opinionField3", zxCtEqContrSupply.getOpinionField(), true)){
				dbZxCtEqContrSupply.setOpinionField3(zxCtEqContrSupply.getOpinionContent(realName, dbZxCtEqContrSupply.getOpinionField3()));
			}
			//
			if(StrUtil.equals("opinionField4", zxCtEqContrSupply.getOpinionField(), true)){
				dbZxCtEqContrSupply.setOpinionField4(zxCtEqContrSupply.getOpinionContent(realName, dbZxCtEqContrSupply.getOpinionField4()));
			}
			//
			if(StrUtil.equals("opinionField5", zxCtEqContrSupply.getOpinionField(), true)){
				dbZxCtEqContrSupply.setOpinionField5(zxCtEqContrSupply.getOpinionContent(realName, dbZxCtEqContrSupply.getOpinionField5()));
			}
			//
			if(StrUtil.equals("opinionField6", zxCtEqContrSupply.getOpinionField(), true)){
				dbZxCtEqContrSupply.setOpinionField6(zxCtEqContrSupply.getOpinionContent(realName, dbZxCtEqContrSupply.getOpinionField6()));
			}
			//
			if(StrUtil.equals("opinionField7", zxCtEqContrSupply.getOpinionField(), true)){
				dbZxCtEqContrSupply.setOpinionField7(zxCtEqContrSupply.getOpinionContent(realName, dbZxCtEqContrSupply.getOpinionField7()));
			}
			//
			if(StrUtil.equals("opinionField8", zxCtEqContrSupply.getOpinionField(), true)){
				dbZxCtEqContrSupply.setOpinionField8(zxCtEqContrSupply.getOpinionContent(realName, dbZxCtEqContrSupply.getOpinionField8()));
			}
			//
			if(StrUtil.equals("opinionField9", zxCtEqContrSupply.getOpinionField(), true)){
				dbZxCtEqContrSupply.setOpinionField9(zxCtEqContrSupply.getOpinionContent(realName, dbZxCtEqContrSupply.getOpinionField9()));
			}
			//
			if(StrUtil.equals("opinionField10", zxCtEqContrSupply.getOpinionField(), true)){
				dbZxCtEqContrSupply.setOpinionField10(zxCtEqContrSupply.getOpinionContent(realName, dbZxCtEqContrSupply.getOpinionField10()));
			}
			// 变更内容
			dbZxCtEqContrSupply.setAlterContent(zxCtEqContrSupply.getAlterContent());
			// 变更原因
			dbZxCtEqContrSupply.setAlterReason(zxCtEqContrSupply.getAlterReason());
			// 批复单位
			dbZxCtEqContrSupply.setSupplyUnit(zxCtEqContrSupply.getSupplyUnit());
			// 共通
			dbZxCtEqContrSupply.setModifyUserInfo(userKey, realName);
			flag = zxCtEqContrSupplyMapper.updateByPrimaryKey(dbZxCtEqContrSupply);
			//附件先删除再新增
			ZxErpFile delFile = new ZxErpFile();
			delFile.setOtherId(dbZxCtEqContrSupply.getZxCtEqContrSupplyId());
			List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
			if(delFileList != null && delFileList.size() >0) {
				delFile.setModifyUserInfo(userKey, realName);
				zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
			}
			if(zxCtEqContrSupply.getZxErpFileList() != null && zxCtEqContrSupply.getZxErpFileList().size() >0) {
				for (ZxErpFile file : zxCtEqContrSupply.getZxErpFileList()) {
					file.setUid(UuidUtil.generate());
					file.setOtherType("0");
					file.setOtherId(dbZxCtEqContrSupply.getZxCtEqContrSupplyId());
					file.setCreateUserInfo(userKey, realName);
					flag = zxErpFileMapper.insert(file);
				}
			}
			if(zxCtEqContrSupply.getZxErpMainFileList() != null && zxCtEqContrSupply.getZxErpMainFileList().size() >0) {
	        	for (ZxErpFile file : zxCtEqContrSupply.getZxErpMainFileList()) {
	        		file.setUid(UuidUtil.generate());
	        		file.setOtherType("1");
	        		file.setOtherId(dbZxCtEqContrSupply.getZxCtEqContrSupplyId());
	        		file.setCreateUserInfo(userKey, realName);
	        		flag = zxErpFileMapper.insert(file);
				}
	        }
			//补充协议评审通过update合同台账中的
			//【alterContractSum变更后含税合同金额(万元)】，
			//【alterContractSumNoTax变更后不含税合同额(万元)】，
			//【alterContractSumTax变更后合同税额】
			if(StrUtil.equals(zxCtEqContrSupply.getApih5FlowStatus(), "2")) {
				ZxCtEqContract contract = zxCtEqContractMapper.selectByPrimaryKey(dbZxCtEqContrSupply.getContractID());
				if(contract != null && StrUtil.isNotEmpty(contract.getContractID())) {
					contract.setAlterContractSum(dbZxCtEqContrSupply.getAlterContractSum());
					contract.setAlterContractSumNoTax(dbZxCtEqContrSupply.getAlterContractSumNoTax());
					contract.setAlterContractSumTax(dbZxCtEqContrSupply.getAlterContractSumTax());
					contract.setModifyUserInfo(userKey, realName);
					flag = zxCtEqContractMapper.updateByPrimaryKey(contract);
					//同时更新对应清单下的变更金额
					ZxCtEqContrAlterItemResoure alterItemResoure = new ZxCtEqContrAlterItemResoure();
					alterItemResoure.setContrAlterID(dbZxCtEqContrSupply.getContrAlterID());
					alterItemResoure.setAlterType("2");//查修改类的所有清单
					List<ZxCtEqContrAlterItemResoure> alterItemUpdateList = zxCtEqContrAlterItemResoureMapper.selectByZxCtEqContrAlterItemResoureList(alterItemResoure);
					if(alterItemUpdateList != null && alterItemUpdateList.size() >0) {
						for (ZxCtEqContrAlterItemResoure itemResoure : alterItemUpdateList) {
							ZxCtEqContrItem contrItems = zxCtEqContrItemMapper.selectByPrimaryKey(itemResoure.getContrItemID());
							if(contrItems != null && StrUtil.isNotEmpty(contrItems.getZxCtEqContrItemId())) {
								contrItems.setAlterTrrm(itemResoure.getAlterTrrm());
								contrItems.setAlterPrice(itemResoure.getAlterPrice());
								contrItems.setAlterPriceNoTax(itemResoure.getAlterPriceNoTax());
								contrItems.setAlterAmt(itemResoure.getAlterAmt());
								contrItems.setAlterAmtNoTax(itemResoure.getAlterAmtNoTax());
								contrItems.setAlterAmtTax(itemResoure.getAlterAmtTax());
								contrItems.setRemark(itemResoure.getRemark());
								contrItems.setModifyUserInfo(userKey, realName);
								flag = zxCtEqContrItemMapper.updateByPrimaryKey(contrItems);
							}
						} 
					}
					//新增类的清单
					alterItemResoure.setAlterType("1");//查新增类的所有清单
					List<ZxCtEqContrAlterItemResoure> alterItemAddList = zxCtEqContrAlterItemResoureMapper.selectByZxCtEqContrAlterItemResoureList(alterItemResoure);
					if(alterItemAddList != null && alterItemAddList.size() >0) {
						for (ZxCtEqContrAlterItemResoure zxCtEqContrAlterItemResoure : alterItemAddList) {
							ZxCtEqContrItem addCopyItem = new ZxCtEqContrItem();
							BeanUtil.copyProperties(zxCtEqContrAlterItemResoure, addCopyItem, true);
							addCopyItem.setZxCtEqContrItemId(zxCtEqContrAlterItemResoure.getContrItemID());
							addCopyItem.setContractID(contract.getContractID());
							//问题票3722
							addCopyItem.setPrice(addCopyItem.getAlterPrice());// 含税合同单价
							addCopyItem.setPriceNoTax(addCopyItem.getAlterPriceNoTax());// 不含税合同单价
							addCopyItem.setModifyUserInfo(userKey, realName);
							flag = zxCtEqContrItemMapper.insert(addCopyItem);
						}
					}
				}
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update",zxCtEqContrSupply);
		}
	}
	@Override
	public ResponseEntity batchDeleteUpdateZxCtEqContrSupply(List<ZxCtEqContrSupply> zxCtEqContrSupplyList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		String token = TokenUtils.getToken(request);
		int flag = 0;
		if (zxCtEqContrSupplyList != null && zxCtEqContrSupplyList.size() > 0) {
			JSONArray jsonArray = new JSONArray();
			for (ZxCtEqContrSupply zxCtEqContrSupply : zxCtEqContrSupplyList) {
				jsonArray.add(zxCtEqContrSupply.getWorkId());
				ZxErpFile delFile = new ZxErpFile();
				delFile.setOtherId(zxCtEqContrSupply.getZxCtEqContrSupplyId());
				List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
				if(delFileList != null && delFileList.size() >0) {
					delFile.setModifyUserInfo(userKey, realName);
					zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
				}
			}
			String url =Apih5Properties.getWebUrl() + "batchDeleteFlow";
			String delFlowResult = HttpUtil.sendPostToken(url, jsonArray.toString(), token);
			ZxCtEqContrSupply zxCtEqContrSupply = new ZxCtEqContrSupply();
			zxCtEqContrSupply.setModifyUserInfo(userKey, realName);
			flag = zxCtEqContrSupplyMapper.batchDeleteUpdateZxCtEqContrSupply(zxCtEqContrSupplyList, zxCtEqContrSupply);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete",zxCtEqContrSupplyList);
		}
	}

	@Override
	public ResponseEntity getZxCtEqContrSupplyListBycontractID(ZxCtEqContrSupply zxCtEqContrSupply) {
		if (zxCtEqContrSupply == null) {
			zxCtEqContrSupply = new ZxCtEqContrSupply();
		}

		if(StrUtil.isEmpty(zxCtEqContrSupply.getContractID())) {
			return repEntity.layerMessage("no", "ContractID没传");
		}

		// 分页查询
		PageHelper.startPage(zxCtEqContrSupply.getPage(),zxCtEqContrSupply.getLimit());
		// 获取数据
		List<ZxCtEqContrSupply> zxCtEqContrSupplyList = zxCtEqContrSupplyMapper.selectByZxCtEqContrSupplyList(zxCtEqContrSupply);
		for (ZxCtEqContrSupply zxCtEqContrSupply2 : zxCtEqContrSupplyList) {
			ZxErpFile file = new ZxErpFile();
			file.setOtherId(zxCtEqContrSupply2.getZxCtEqContrSupplyId());
			List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(file);
			zxCtEqContrSupply2.setZxErpFileList(zxErpFileList);
		}
		// 得到分页信息
		PageInfo<ZxCtEqContrSupply> p = new PageInfo<>(zxCtEqContrSupplyList);

		return repEntity.okList(zxCtEqContrSupplyList, p.getTotal());
	}

	@Override
	public ResponseEntity generateZxCtEqContrSupplyContractNo(ZxCtEqContrSupply zxCtEqContrSupply) {
		String contractNo = "";
		ZxCtEqContract zxCtEqContract = zxCtEqContractMapper.selectByPrimaryKey(zxCtEqContrSupply.getContractID());
		if(zxCtEqContract != null && StrUtil.isNotEmpty(zxCtEqContract.getContractID())) {
			contractNo = zxCtEqContract.getContractNo();
		}
		//查次数
		ZxCtEqContrSupply supply = new ZxCtEqContrSupply();
		supply.setContractID(zxCtEqContrSupply.getContractID());
		List<ZxCtEqContrSupply> supplyList = zxCtEqContrSupplyMapper.selectByZxCtEqContrSupplyList(supply);
		if(supplyList != null && supplyList.size() >0) {
			String auto = String.format("%02d", supplyList.size()+1); 
			contractNo = contractNo+"-补"+auto;
		}else {
			contractNo = contractNo+"-补01";
		}
		zxCtEqContrSupply.setContractNo(contractNo);
		return repEntity.ok(zxCtEqContrSupply);
	}

	@Override
	public ResponseEntity updateZxCtEqContrSupplyForContractTab(ZxCtEqContrSupply zxCtEqContrSupply) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxCtEqContrSupply dbZxCtEqContrSupply = zxCtEqContrSupplyMapper.selectByPrimaryKey(zxCtEqContrSupply.getZxCtEqContrSupplyId());
		if (dbZxCtEqContrSupply != null && StrUtil.isNotEmpty(dbZxCtEqContrSupply.getZxCtEqContrSupplyId())) {
			// 补充协议名称
			dbZxCtEqContrSupply.setSupplyName(zxCtEqContrSupply.getSupplyName());
			// 开工日期
			dbZxCtEqContrSupply.setStartDate(zxCtEqContrSupply.getStartDate());
			// 竣工日期
			dbZxCtEqContrSupply.setEndDate(zxCtEqContrSupply.getEndDate());
			// 合同工期
			dbZxCtEqContrSupply.setCsTimeLimit(zxCtEqContrSupply.getCsTimeLimit());
			// 合同签订人
			dbZxCtEqContrSupply.setAgent(zxCtEqContrSupply.getAgent());
			// 合同内容
			dbZxCtEqContrSupply.setContent(zxCtEqContrSupply.getContent());
			// 备注
			dbZxCtEqContrSupply.setRemark(zxCtEqContrSupply.getRemark());
			// 共通
			dbZxCtEqContrSupply.setModifyUserInfo(userKey, realName);
			flag = zxCtEqContrSupplyMapper.updateByPrimaryKey(dbZxCtEqContrSupply);
			//附件先删除再新增
			ZxErpFile delFile = new ZxErpFile();
			delFile.setOtherId(zxCtEqContrSupply.getZxCtEqContrSupplyId());
			List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
			if(delFileList != null && delFileList.size() >0) {
				delFile.setModifyUserInfo(userKey, realName);
				zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
			}
			if(zxCtEqContrSupply.getZxErpFileList() != null && zxCtEqContrSupply.getZxErpFileList().size() >0) {
				for (ZxErpFile file : zxCtEqContrSupply.getZxErpFileList()) {
					file.setUid(UuidUtil.generate());
					file.setOtherId(dbZxCtEqContrSupply.getZxCtEqContrSupplyId());
					file.setCreateUserInfo(userKey, realName);
					flag = zxErpFileMapper.insert(file);
				}
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update",zxCtEqContrSupply);
		}
	}

	// ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
