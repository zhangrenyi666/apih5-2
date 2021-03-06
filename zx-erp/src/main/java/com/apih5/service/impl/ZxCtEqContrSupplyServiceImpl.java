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
	        // ??????????????????
	        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
	                || StrUtil.equals("admin", userId)) {
	        	zxCtEqContrSupply.setComID("");
	        	zxCtEqContrSupply.setOrgID("");
	        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
	            // ????????????????????????
	        	zxCtEqContrSupply.setComID(zxCtEqContrSupply.getOrgID());
	        	zxCtEqContrSupply.setOrgID("");
	        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
	                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
	            // ???????????????????????????
	        	zxCtEqContrSupply.setOrgID(zxCtEqContrSupply.getOrgID());
	        }
		// ????????????
		PageHelper.startPage(zxCtEqContrSupply.getPage(),zxCtEqContrSupply.getLimit());
		// ????????????
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
		// ??????????????????
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
			// ????????????
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
		// ????????????
		if (dbZxCtEqContrSupply != null) {
			return repEntity.ok(dbZxCtEqContrSupply);
		} else {
			return repEntity.layerMessage("no", "????????????");
		}
	}

	@Override
	public ResponseEntity saveZxCtEqContrSupply(ZxCtEqContrSupply zxCtEqContrSupply) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		//?????????????????????????????????????????????????????????????????????????????????
		//??????id
		ZxCtEqContrSupply contrSupply = new ZxCtEqContrSupply();
		contrSupply.setContractID(zxCtEqContrSupply.getContractID());
		contrSupply.setApih5FlowStatusNot2Flag("??????????????????????????????");
		List<ZxCtEqContrSupply> contrSupplyList = zxCtEqContrSupplyMapper.selectByZxCtEqContrSupplyList(contrSupply);
		if(contrSupplyList != null && contrSupplyList.size() >0) {
			return repEntity.layerMessage("no", "?????????????????????????????????????????????????????????????????????");
		}
		zxCtEqContrSupply.setZxCtEqContrSupplyId(UuidUtil.generate());
		zxCtEqContrSupply.setContrAlterID(UuidUtil.generate());
		zxCtEqContrSupply.setApih5FlowStatus("-1");
		zxCtEqContrSupply.setCreateUserInfo(userKey, realName);
		int flag = zxCtEqContrSupplyMapper.insert(zxCtEqContrSupply);
		//????????????
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
			// ??????????????????
			dbZxCtEqContrSupply.setSupplyName(zxCtEqContrSupply.getSupplyName());
			// ????????????
			dbZxCtEqContrSupply.setStartDate(zxCtEqContrSupply.getStartDate());
			// ????????????
			dbZxCtEqContrSupply.setEndDate(zxCtEqContrSupply.getEndDate());
			// ????????????
			dbZxCtEqContrSupply.setCsTimeLimit(zxCtEqContrSupply.getCsTimeLimit());
			// ???????????????
			dbZxCtEqContrSupply.setAgent(zxCtEqContrSupply.getAgent());
			// ????????????
			dbZxCtEqContrSupply.setContent(zxCtEqContrSupply.getContent());
			//??????
			dbZxCtEqContrSupply.setRemark(zxCtEqContrSupply.getRemark());
			//workId
			dbZxCtEqContrSupply.setWorkId(zxCtEqContrSupply.getWorkId());
			// ????????????
			dbZxCtEqContrSupply.setApih5FlowStatus(zxCtEqContrSupply.getApih5FlowStatus());
			//???????????????
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
			// ????????????
			dbZxCtEqContrSupply.setAlterContent(zxCtEqContrSupply.getAlterContent());
			// ????????????
			dbZxCtEqContrSupply.setAlterReason(zxCtEqContrSupply.getAlterReason());
			// ????????????
			dbZxCtEqContrSupply.setSupplyUnit(zxCtEqContrSupply.getSupplyUnit());
			// ??????
			dbZxCtEqContrSupply.setModifyUserInfo(userKey, realName);
			flag = zxCtEqContrSupplyMapper.updateByPrimaryKey(dbZxCtEqContrSupply);
			//????????????????????????
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
			//????????????????????????update??????????????????
			//???alterContractSum???????????????????????????(??????)??????
			//???alterContractSumNoTax???????????????????????????(??????)??????
			//???alterContractSumTax????????????????????????
			if(StrUtil.equals(zxCtEqContrSupply.getApih5FlowStatus(), "2")) {
				ZxCtEqContract contract = zxCtEqContractMapper.selectByPrimaryKey(dbZxCtEqContrSupply.getContractID());
				if(contract != null && StrUtil.isNotEmpty(contract.getContractID())) {
					contract.setAlterContractSum(dbZxCtEqContrSupply.getAlterContractSum());
					contract.setAlterContractSumNoTax(dbZxCtEqContrSupply.getAlterContractSumNoTax());
					contract.setAlterContractSumTax(dbZxCtEqContrSupply.getAlterContractSumTax());
					contract.setModifyUserInfo(userKey, realName);
					flag = zxCtEqContractMapper.updateByPrimaryKey(contract);
					//??????????????????????????????????????????
					ZxCtEqContrAlterItemResoure alterItemResoure = new ZxCtEqContrAlterItemResoure();
					alterItemResoure.setContrAlterID(dbZxCtEqContrSupply.getContrAlterID());
					alterItemResoure.setAlterType("2");//???????????????????????????
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
					//??????????????????
					alterItemResoure.setAlterType("1");//???????????????????????????
					List<ZxCtEqContrAlterItemResoure> alterItemAddList = zxCtEqContrAlterItemResoureMapper.selectByZxCtEqContrAlterItemResoureList(alterItemResoure);
					if(alterItemAddList != null && alterItemAddList.size() >0) {
						for (ZxCtEqContrAlterItemResoure zxCtEqContrAlterItemResoure : alterItemAddList) {
							ZxCtEqContrItem addCopyItem = new ZxCtEqContrItem();
							BeanUtil.copyProperties(zxCtEqContrAlterItemResoure, addCopyItem, true);
							addCopyItem.setZxCtEqContrItemId(zxCtEqContrAlterItemResoure.getContrItemID());
							addCopyItem.setContractID(contract.getContractID());
							//?????????3722
							addCopyItem.setPrice(addCopyItem.getAlterPrice());// ??????????????????
							addCopyItem.setPriceNoTax(addCopyItem.getAlterPriceNoTax());// ?????????????????????
							addCopyItem.setModifyUserInfo(userKey, realName);
							flag = zxCtEqContrItemMapper.insert(addCopyItem);
						}
					}
				}
			}
		}
		// ??????
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
		// ??????
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
			return repEntity.layerMessage("no", "ContractID??????");
		}

		// ????????????
		PageHelper.startPage(zxCtEqContrSupply.getPage(),zxCtEqContrSupply.getLimit());
		// ????????????
		List<ZxCtEqContrSupply> zxCtEqContrSupplyList = zxCtEqContrSupplyMapper.selectByZxCtEqContrSupplyList(zxCtEqContrSupply);
		for (ZxCtEqContrSupply zxCtEqContrSupply2 : zxCtEqContrSupplyList) {
			ZxErpFile file = new ZxErpFile();
			file.setOtherId(zxCtEqContrSupply2.getZxCtEqContrSupplyId());
			List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(file);
			zxCtEqContrSupply2.setZxErpFileList(zxErpFileList);
		}
		// ??????????????????
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
		//?????????
		ZxCtEqContrSupply supply = new ZxCtEqContrSupply();
		supply.setContractID(zxCtEqContrSupply.getContractID());
		List<ZxCtEqContrSupply> supplyList = zxCtEqContrSupplyMapper.selectByZxCtEqContrSupplyList(supply);
		if(supplyList != null && supplyList.size() >0) {
			String auto = String.format("%02d", supplyList.size()+1); 
			contractNo = contractNo+"-???"+auto;
		}else {
			contractNo = contractNo+"-???01";
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
			// ??????????????????
			dbZxCtEqContrSupply.setSupplyName(zxCtEqContrSupply.getSupplyName());
			// ????????????
			dbZxCtEqContrSupply.setStartDate(zxCtEqContrSupply.getStartDate());
			// ????????????
			dbZxCtEqContrSupply.setEndDate(zxCtEqContrSupply.getEndDate());
			// ????????????
			dbZxCtEqContrSupply.setCsTimeLimit(zxCtEqContrSupply.getCsTimeLimit());
			// ???????????????
			dbZxCtEqContrSupply.setAgent(zxCtEqContrSupply.getAgent());
			// ????????????
			dbZxCtEqContrSupply.setContent(zxCtEqContrSupply.getContent());
			// ??????
			dbZxCtEqContrSupply.setRemark(zxCtEqContrSupply.getRemark());
			// ??????
			dbZxCtEqContrSupply.setModifyUserInfo(userKey, realName);
			flag = zxCtEqContrSupplyMapper.updateByPrimaryKey(dbZxCtEqContrSupply);
			//????????????????????????
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
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update",zxCtEqContrSupply);
		}
	}

	// ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????
}
