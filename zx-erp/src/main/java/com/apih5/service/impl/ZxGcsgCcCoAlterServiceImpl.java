package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxGcsgCcCoAlterMapper;
import com.apih5.mybatis.dao.ZxGcsgCcCoAlterWorkMapper;
import com.apih5.mybatis.dao.ZxGcsgCcWorksMapper;
import com.apih5.mybatis.dao.ZxGcsgCommonAttachmentMapper;
import com.apih5.mybatis.dao.ZxGcsgCtContrApplyMapper;
import com.apih5.mybatis.pojo.ZxGcsgCcCoAlter;
import com.apih5.mybatis.pojo.ZxGcsgCcCoAlterWork;
import com.apih5.mybatis.pojo.ZxGcsgCcWorks;
import com.apih5.mybatis.pojo.ZxGcsgCommonAttachment;
import com.apih5.mybatis.pojo.ZxGcsgCtContrApply;
import com.apih5.service.ZxGcsgCcCoAlterService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;

@Service("zxGcsgCcCoAlterService")
public class ZxGcsgCcCoAlterServiceImpl implements ZxGcsgCcCoAlterService {

	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;
	@Autowired(required = true)
	private ZxGcsgCcCoAlterMapper zxGcsgCcCoAlterMapper;
	@Autowired(required = true)
	private ZxGcsgCcCoAlterWorkMapper zxGcsgCcCoAlterWorkMapper;
	@Autowired(required = true)
	private ZxGcsgCommonAttachmentMapper zxGcsgCommonAttachmentMapper;
	@Autowired(required = true)
	private ZxGcsgCtContrApplyMapper zxGcsgCtContrApplyMapper;
	@Autowired(required = true)
	private ZxGcsgCcWorksMapper zxGcsgCcWorksMapper;

	@Override
	public ResponseEntity getZxGcsgCcCoAlterListByCondition(ZxGcsgCcCoAlter zxGcsgCcCoAlter) {
		if (zxGcsgCcCoAlter == null) {
			zxGcsgCcCoAlter = new ZxGcsgCcCoAlter();
		}
		// ????????????
		PageHelper.startPage(zxGcsgCcCoAlter.getPage(), zxGcsgCcCoAlter.getLimit());
		// ????????????
		List<ZxGcsgCcCoAlter> zxGcsgCcCoAlterList = zxGcsgCcCoAlterMapper.selectByZxGcsgCcCoAlterList(zxGcsgCcCoAlter);
		// ??????????????????
		PageInfo<ZxGcsgCcCoAlter> p = new PageInfo<>(zxGcsgCcCoAlterList);

		return repEntity.okList(zxGcsgCcCoAlterList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxGcsgCcCoAlterDetail(ZxGcsgCcCoAlter zxGcsgCcCoAlter) {
		if (zxGcsgCcCoAlter == null) {
			zxGcsgCcCoAlter = new ZxGcsgCcCoAlter();
		}
		// ????????????
		ZxGcsgCcCoAlter dbZxGcsgCcCoAlter = zxGcsgCcCoAlterMapper.selectByPrimaryKey(zxGcsgCcCoAlter.getCcCoAlterId());
		// ????????????
		if (dbZxGcsgCcCoAlter != null) {
			return repEntity.ok(dbZxGcsgCcCoAlter);
		} else {
			return repEntity.layerMessage("no", "????????????");
		}
	}

	@Override
	public ResponseEntity saveZxGcsgCcCoAlter(ZxGcsgCcCoAlter zxGcsgCcCoAlter) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zxGcsgCcCoAlter.setCcCoAlterId(UuidUtil.generate());
		zxGcsgCcCoAlter.setCreateUserInfo(userKey, realName);
		int flag = zxGcsgCcCoAlterMapper.insert(zxGcsgCcCoAlter);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zxGcsgCcCoAlter);
		}
	}

	@Override
	public ResponseEntity updateZxGcsgCcCoAlter(ZxGcsgCcCoAlter zxGcsgCcCoAlter) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxGcsgCcCoAlter dbZxGcsgCcCoAlter = zxGcsgCcCoAlterMapper.selectByPrimaryKey(zxGcsgCcCoAlter.getCcCoAlterId());
		if (dbZxGcsgCcCoAlter != null && StrUtil.isNotEmpty(dbZxGcsgCcCoAlter.getCcCoAlterId())) {
			// ???????????????ID(coBookID)
			dbZxGcsgCcCoAlter.setCcCoBookId(zxGcsgCcCoAlter.getCcCoBookId());
			// ????????????
			dbZxGcsgCcCoAlter.setAlterLevel(zxGcsgCcCoAlter.getAlterLevel());
			// ??????????????????
			dbZxGcsgCcCoAlter.setProposer(zxGcsgCcCoAlter.getProposer());
			// ????????????
			dbZxGcsgCcCoAlter.setAlterContent(zxGcsgCcCoAlter.getAlterContent());
			// ????????????
			dbZxGcsgCcCoAlter.setAlterReason(zxGcsgCcCoAlter.getAlterReason());
			// ????????????
			dbZxGcsgCcCoAlter.setHappenDate(zxGcsgCcCoAlter.getHappenDate());
			// ??????????????????????????????(??????)
			dbZxGcsgCcCoAlter.setApplyAmount(zxGcsgCcCoAlter.getApplyAmount());
			// ????????????
			dbZxGcsgCcCoAlter.setApplyDate(zxGcsgCcCoAlter.getApplyDate());
			// ????????????
			dbZxGcsgCcCoAlter.setApplyNo(zxGcsgCcCoAlter.getApplyNo());
			// ??????????????????
			dbZxGcsgCcCoAlter.setApplyDelay(zxGcsgCcCoAlter.getApplyDelay());
			// ???????????????????????????(??????)
			dbZxGcsgCcCoAlter.setReplyAmount(zxGcsgCcCoAlter.getReplyAmount());
			// ??????
			dbZxGcsgCcCoAlter.setReplyDate(zxGcsgCcCoAlter.getReplyDate());
			// ?????????????????????
			dbZxGcsgCcCoAlter.setReplyNo(zxGcsgCcCoAlter.getReplyNo());
			// ??????????????????
			dbZxGcsgCcCoAlter.setReplyDelay(zxGcsgCcCoAlter.getReplyDelay());
			// ????????????
			dbZxGcsgCcCoAlter.setReplyStatus(zxGcsgCcCoAlter.getReplyStatus());
			// ?????????
			dbZxGcsgCcCoAlter.setRecorder(zxGcsgCcCoAlter.getRecorder());
			// ????????????
			dbZxGcsgCcCoAlter.setRecordDate(zxGcsgCcCoAlter.getRecordDate());
			// ??????????????????
			dbZxGcsgCcCoAlter.setTakeEffectDate(zxGcsgCcCoAlter.getTakeEffectDate());
			// ???????????????
			dbZxGcsgCcCoAlter.setTakeEffectMan(zxGcsgCcCoAlter.getTakeEffectMan());
			// ????????????
			dbZxGcsgCcCoAlter.setAuditStatus(zxGcsgCcCoAlter.getAuditStatus());
			// ?????????????????????
			dbZxGcsgCcCoAlter.setCombProp(zxGcsgCcCoAlter.getCombProp());
			// ????????????
			dbZxGcsgCcCoAlter.setPp1(zxGcsgCcCoAlter.getPp1());
			// ?????????????????????(??????)
			dbZxGcsgCcCoAlter.setPp2(zxGcsgCcCoAlter.getPp2());
			// ????????????ID
			dbZxGcsgCcCoAlter.setPp3(zxGcsgCcCoAlter.getPp3());
			// pp4
			dbZxGcsgCcCoAlter.setPp4(zxGcsgCcCoAlter.getPp4());
			// pp5
			dbZxGcsgCcCoAlter.setPp5(zxGcsgCcCoAlter.getPp5());
			// (IP?????????ID)
			dbZxGcsgCcCoAlter.setPp6(zxGcsgCcCoAlter.getPp6());
			// pp7
			dbZxGcsgCcCoAlter.setPp7(zxGcsgCcCoAlter.getPp7());
			// pp8
			dbZxGcsgCcCoAlter.setPp8(zxGcsgCcCoAlter.getPp8());
			// pp9
			dbZxGcsgCcCoAlter.setPp9(zxGcsgCcCoAlter.getPp9());
			// pp10
			dbZxGcsgCcCoAlter.setPp10(zxGcsgCcCoAlter.getPp10());
			// ????????????(pp1)
			dbZxGcsgCcCoAlter.setApprovalUnit(zxGcsgCcCoAlter.getApprovalUnit());
			// ?????????????????????(??????)(pp2)
			dbZxGcsgCcCoAlter.setOriginalTaxAmount(zxGcsgCcCoAlter.getOriginalTaxAmount());
			// ????????????ID(pp3)
			dbZxGcsgCcCoAlter.setCtContrApplyId(zxGcsgCcCoAlter.getCtContrApplyId());
			// (IP?????????ID)(pp6)
			dbZxGcsgCcCoAlter.setIpContractId(zxGcsgCcCoAlter.getIpContractId());
			// ??????ID(contractID)
			dbZxGcsgCcCoAlter.setCtContractId(zxGcsgCcCoAlter.getCtContractId());
			// ??????ID
			dbZxGcsgCcCoAlter.setOrgID(zxGcsgCcCoAlter.getOrgID());
			// ??????????????????
			dbZxGcsgCcCoAlter.setEditTime(zxGcsgCcCoAlter.getEditTime());
			// ?????????????????????????????????(??????)
			dbZxGcsgCcCoAlter.setApplyAmountNoTax(zxGcsgCcCoAlter.getApplyAmountNoTax());
			// ????????????????????????(??????)
			dbZxGcsgCcCoAlter.setApplyAmountTax(zxGcsgCcCoAlter.getApplyAmountTax());
			// ??????????????????????????????(??????)
			dbZxGcsgCcCoAlter.setReplyAmountNoTax(zxGcsgCcCoAlter.getReplyAmountNoTax());
			// ?????????????????????(??????)
			dbZxGcsgCcCoAlter.setReplyAmountTax(zxGcsgCcCoAlter.getReplyAmountTax());
			// ????????????????????????(??????)
			dbZxGcsgCcCoAlter.setPp2NoTax(zxGcsgCcCoAlter.getPp2NoTax());
			// ???????????????(??????)
			dbZxGcsgCcCoAlter.setPp2Tax(zxGcsgCcCoAlter.getPp2Tax());
			// ??????
			dbZxGcsgCcCoAlter.setOpinionField1(zxGcsgCcCoAlter.getOpinionField1());
			// ??????
			dbZxGcsgCcCoAlter.setOpinionField2(zxGcsgCcCoAlter.getOpinionField2());
			// ??????
			dbZxGcsgCcCoAlter.setOpinionField3(zxGcsgCcCoAlter.getOpinionField3());
			// ??????
			dbZxGcsgCcCoAlter.setOpinionField4(zxGcsgCcCoAlter.getOpinionField4());
			// ??????
			dbZxGcsgCcCoAlter.setOpinionField5(zxGcsgCcCoAlter.getOpinionField5());
			// ??????
			dbZxGcsgCcCoAlter.setOpinionField6(zxGcsgCcCoAlter.getOpinionField6());
			// ??????
			dbZxGcsgCcCoAlter.setOpinionField7(zxGcsgCcCoAlter.getOpinionField7());
			// ??????
			dbZxGcsgCcCoAlter.setOpinionField8(zxGcsgCcCoAlter.getOpinionField8());
			// ??????
			dbZxGcsgCcCoAlter.setOpinionField9(zxGcsgCcCoAlter.getOpinionField9());
			// ??????
			dbZxGcsgCcCoAlter.setOpinionField10(zxGcsgCcCoAlter.getOpinionField10());
			// ??????id
			dbZxGcsgCcCoAlter.setApih5FlowId(zxGcsgCcCoAlter.getApih5FlowId());
			// work_id
			dbZxGcsgCcCoAlter.setWorkId(zxGcsgCcCoAlter.getWorkId());
			// ??????????????????
			dbZxGcsgCcCoAlter.setApih5FlowStatus(zxGcsgCcCoAlter.getApih5FlowStatus());
			// ????????????
			dbZxGcsgCcCoAlter.setApih5FlowNodeStatus(zxGcsgCcCoAlter.getApih5FlowNodeStatus());
			// ??????
			dbZxGcsgCcCoAlter.setRemarks(zxGcsgCcCoAlter.getRemarks());
			// ??????
			dbZxGcsgCcCoAlter.setSort(zxGcsgCcCoAlter.getSort());
			// ??????
			dbZxGcsgCcCoAlter.setModifyUserInfo(userKey, realName);
			flag = zxGcsgCcCoAlterMapper.updateByPrimaryKey(dbZxGcsgCcCoAlter);
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zxGcsgCcCoAlter);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZxGcsgCcCoAlter(List<ZxGcsgCcCoAlter> zxGcsgCcCoAlterList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zxGcsgCcCoAlterList != null && zxGcsgCcCoAlterList.size() > 0) {
			ZxGcsgCcCoAlter zxGcsgCcCoAlter = new ZxGcsgCcCoAlter();
			zxGcsgCcCoAlter.setModifyUserInfo(userKey, realName);
			flag = zxGcsgCcCoAlterMapper.batchDeleteUpdateZxGcsgCcCoAlter(zxGcsgCcCoAlterList, zxGcsgCcCoAlter);
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zxGcsgCcCoAlterList);
		}
	}

	// ?????????----??????-??????????????????export?????????import?????????sync????????????----?????????

	@Override
	public ResponseEntity getZxGcsgCcCoAlterDetailsWorkList(ZxGcsgCcCoAlter zxGcsgCcCoAlter) {
		if (zxGcsgCcCoAlter == null) {
			zxGcsgCcCoAlter = new ZxGcsgCcCoAlter();
		}
		// ????????????
		ZxGcsgCcCoAlter dbZxGcsgCcCoAlter = zxGcsgCcCoAlterMapper.selectByPrimaryKey(zxGcsgCcCoAlter.getCcCoAlterId());
		if (dbZxGcsgCcCoAlter != null) {
			ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork = new ZxGcsgCcCoAlterWork();
			zxGcsgCcCoAlterWork.setCcCoAlterId(dbZxGcsgCcCoAlter.getCcCoAlterId());
			List<ZxGcsgCcCoAlterWork> list = zxGcsgCcCoAlterWorkMapper
					.getZxGcsgCcCoAlterWorkListByMainTable(zxGcsgCcCoAlterWork);
			dbZxGcsgCcCoAlter.setAlterWorkList(list);
			ZxGcsgCommonAttachment zxGcsgCommonAttachment = new ZxGcsgCommonAttachment();
			zxGcsgCommonAttachment.setOtherId(dbZxGcsgCcCoAlter.getCcCoAlterId());
			List<ZxGcsgCommonAttachment> attachmentList = zxGcsgCommonAttachmentMapper
					.selectByZxGcsgCommonAttachmentList(zxGcsgCommonAttachment);
			dbZxGcsgCcCoAlter.setAttachmentList(attachmentList);
		}
		// ????????????
		if (dbZxGcsgCcCoAlter != null) {
			return repEntity.ok(dbZxGcsgCcCoAlter);
		} else {
			return repEntity.layerMessage("no", "????????????");
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity saveZxGcsgCcCoAlterDetailsWorkList20210121(ZxGcsgCcCoAlter zxGcsgCcCoAlter) {
		if (zxGcsgCcCoAlter == null) {
			zxGcsgCcCoAlter = new ZxGcsgCcCoAlter();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		// ????????????
		ZxGcsgCcCoAlter dbZxGcsgCcCoAlter = zxGcsgCcCoAlterMapper.selectByPrimaryKey(zxGcsgCcCoAlter.getCcCoAlterId());
		if (dbZxGcsgCcCoAlter != null) {
			dbZxGcsgCcCoAlter.setReplyDate(zxGcsgCcCoAlter.getReplyDate());
			dbZxGcsgCcCoAlter.setRemarks(zxGcsgCcCoAlter.getRemarks());
			// ??????????????????????????????(??????)????????????????????????????????????(??????)???????????????????????????(??????)
			BigDecimal[] applyAmount = { BigDecimal.ZERO };
			BigDecimal[] applyAmountNoTax = { BigDecimal.ZERO };
			BigDecimal[] applyAmountTax = { BigDecimal.ZERO };
			if (zxGcsgCcCoAlter.getAlterWorkList() != null && zxGcsgCcCoAlter.getAlterWorkList().size() > 0) {
				// ???????????????????????????
				// check?????????????????????,?????????????????????????????????
				// Boolean checkBoo = false;
				Map<String, Object> checkMap = new HashMap<String, Object>();
				Map<String, Object> notLeafMap = new HashMap<String, Object>();
				Map<String, String> treeNodeMap = new HashMap<String, String>();
				Map<String, Integer> suffixMap = new HashMap<String, Integer>();
				Iterator<ZxGcsgCcCoAlterWork> it = zxGcsgCcCoAlter.getAlterWorkList().iterator();
				while (it.hasNext()) {
					ZxGcsgCcCoAlterWork alterWork = it.next();
					alterWork.setCcCoAlterWorkId(UuidUtil.generate());
					alterWork.setCcCoAlterId(dbZxGcsgCcCoAlter.getCcCoAlterId());
					alterWork.setCtContrApplyId(dbZxGcsgCcCoAlter.getCtContrApplyId());
					alterWork.setCreateUserInfo(userKey, realName);
					// ?????????????????????????????????
					if (StrUtil.equals("2", alterWork.getAlterType()) && alterWork.getIsLeaf() == 1) {
						if (checkMap.containsKey(alterWork.getCcWorksId())) {
							// checkBoo = true;
							return repEntity.layerMessage("no", "?????????????????????,?????????????????????????????????");
						} else {
							checkMap.put(alterWork.getCcWorksId(), 1);
						}
					}
					// ???????????????,??????????????????
					if (alterWork.getIsLeaf() == 0) {
						notLeafMap.put(alterWork.getCcWorksNo(), alterWork.getCcCoAlterWorkId());
						// ????????????,??????treeNode
						if (StrUtil.equals("1", alterWork.getAlterType())) {
							ZxGcsgCcWorks ccWorks = new ZxGcsgCcWorks();
							ccWorks.setParentID(alterWork.getCcWorksParentId());
							ZxGcsgCcWorks maxTreeNode = zxGcsgCcWorksMapper
									.getZxGcsgCcWorksMAXTreeNodeByCondition(ccWorks);
							String treeNode = "";
							// ???????????????????????????
							if (maxTreeNode != null) {
								String preTreeNode = maxTreeNode.getTreeNode()
										.substring(maxTreeNode.getTreeNode().length() - 4);
								String proTreeNode = maxTreeNode.getTreeNode().substring(
										maxTreeNode.getTreeNode().length() - 4, maxTreeNode.getTreeNode().length());
								if (suffixMap.containsKey(alterWork.getCcWorksParentNo())) {
									treeNode = preTreeNode + (suffixMap.get(alterWork.getCcWorksParentNo()) + 1);
									suffixMap.put(alterWork.getCcWorksParentNo(),
											suffixMap.get(alterWork.getCcWorksParentNo()) + 1);
								} else {
									treeNode = preTreeNode + (Integer.parseInt(proTreeNode) + 1);
									suffixMap.put(alterWork.getCcWorksParentNo(), Integer.parseInt(proTreeNode) + 1);
								}
								treeNodeMap.put(alterWork.getCcWorksNo(), treeNode);
							}
							// ???????????????
							else {
								ccWorks.setParentID("");
								ccWorks.setCcWorksId(alterWork.getCcWorksParentId());
								maxTreeNode = zxGcsgCcWorksMapper.getZxGcsgCcWorksMAXTreeNodeByCondition(ccWorks);
								if (maxTreeNode != null) {
									String preTreeNode = maxTreeNode.getTreeNode();
									if (suffixMap.containsKey(alterWork.getCcWorksParentNo())) {
										treeNode = preTreeNode + (suffixMap.get(alterWork.getCcWorksNo()) + 1);
										suffixMap.put(alterWork.getCcWorksParentNo(),
												suffixMap.get(alterWork.getCcWorksParentNo()) + 1);
									} else {
										treeNode = preTreeNode + "1001";
										suffixMap.put(alterWork.getCcWorksParentNo(), 1001);
									}
									treeNodeMap.put(alterWork.getCcWorksNo(), treeNode);
								} else {
									// ???????????????????????????????????????????????????
								}
							}
							alterWork.setTreeNode(treeNode);
						} else {
							return repEntity.layerMessage("no", "?????????????????????????????????????????????");
						}
					}
				}
				Map<String, Integer> suffixIsLeafMap = new HashMap<String, Integer>();
				zxGcsgCcCoAlter.getAlterWorkList().stream().forEach(alterWork -> {
					BigDecimal afterAmt = CalcUtils.calcMultiply(alterWork.getAfterChangeQty(),
							alterWork.getAfterChangePrice());
					BigDecimal afterAmtNoTax = BigDecimal.ZERO;
					if (alterWork.getTaxRate() != null && NumberUtil.isNumber(alterWork.getTaxRate())) {
						afterAmtNoTax = CalcUtils.calcDivide(afterAmt, CalcUtils.calcAdd(BigDecimal.ONE,
								CalcUtils.calcDivide(new BigDecimal(alterWork.getTaxRate()), new BigDecimal(100))), 2);
					}
					BigDecimal afterAmtTax = CalcUtils.calcSubtract(afterAmt, afterAmtNoTax);
					if (alterWork.getIsLeaf() == 1 && StrUtil.equals("2", alterWork.getAlterType())) {
						applyAmount[0] = CalcUtils.calcAdd(applyAmount[0],
								CalcUtils.calcMultiply(alterWork.getChangeQty(), alterWork.getOriginPrice()));
						applyAmountNoTax[0] = CalcUtils.calcAdd(applyAmountNoTax[0],
								CalcUtils.calcMultiply(alterWork.getChangeQty(), alterWork.getOriginPriceNoTax()));
					} else if (alterWork.getIsLeaf() == 1 && StrUtil.equals("1", alterWork.getAlterType())) {
						applyAmount[0] = CalcUtils.calcAdd(applyAmount[0], afterAmt);
						applyAmountNoTax[0] = CalcUtils.calcAdd(applyAmountNoTax[0], afterAmtNoTax);
					}
					alterWork.setAfterAmt(afterAmtTax);
					alterWork.setAfterAmtNoTax(afterAmtNoTax);
					alterWork.setAfterAmtTax(afterAmtTax);
					if (notLeafMap.containsKey(alterWork.getCcWorksParentNo())) {
						alterWork.setCcWorksParentId(String.valueOf(notLeafMap.get(alterWork.getCcWorksParentNo())));
					}
					// ????????????,??????treeNode
					if (StrUtil.equals("1", alterWork.getAlterType()) && alterWork.getIsLeaf() == 1) {
						ZxGcsgCcWorks ccWorks = new ZxGcsgCcWorks();
						ccWorks.setCcWorksId(alterWork.getCcWorksParentId());
						ZxGcsgCcWorks dbMaxTreeNode = zxGcsgCcWorksMapper
								.getZxGcsgCcWorksMAXTreeNodeByCondition(ccWorks);
						// ??????????????????
						if (dbMaxTreeNode != null) {
							// ????????????????????????treeNode
							ccWorks.setCcWorksId("");
							ccWorks.setParentID(alterWork.getCcWorksParentId());
							ZxGcsgCcWorks maxTreeNode = zxGcsgCcWorksMapper
									.getZxGcsgCcWorksMAXTreeNodeByCondition(ccWorks);
							if (maxTreeNode != null) {
								String preTreeNode = maxTreeNode.getTreeNode()
										.substring(maxTreeNode.getTreeNode().length() - 4);
								String proTreeNode = maxTreeNode.getTreeNode().substring(
										maxTreeNode.getTreeNode().length() - 4, maxTreeNode.getTreeNode().length());
								if (suffixIsLeafMap.containsKey(alterWork.getCcWorksParentNo())) {
									suffixIsLeafMap.put(alterWork.getCcWorksParentNo(),
											suffixIsLeafMap.get(alterWork.getCcWorksParentNo()) + 1);
									alterWork.setTreeNode(
											preTreeNode + (suffixIsLeafMap.get(alterWork.getCcWorksParentNo()) + 1));
								} else {
									suffixIsLeafMap.put(alterWork.getCcWorksParentNo(),
											Integer.parseInt(proTreeNode) + 1);
									alterWork.setTreeNode(preTreeNode + (Integer.parseInt(proTreeNode) + 1));
								}
							} else {
								String preTreeNode = dbMaxTreeNode.getTreeNode();
								if (suffixIsLeafMap.containsKey(alterWork.getCcWorksParentNo())) {
									suffixIsLeafMap.put(alterWork.getCcWorksParentNo(),
											suffixIsLeafMap.get(alterWork.getCcWorksParentNo()) + 1);
									alterWork.setTreeNode(preTreeNode + (suffixMap.get(alterWork.getCcWorksNo()) + 1));
								} else {
									suffixIsLeafMap.put(alterWork.getCcWorksParentNo(), 1001);
									alterWork.setTreeNode(preTreeNode + "1001");
								}
							}
						} else {
							String preTreeNode = treeNodeMap.get(alterWork.getCcWorksParentNo());
							if (suffixIsLeafMap.containsKey(alterWork.getCcWorksParentNo())) {
								suffixIsLeafMap.put(alterWork.getCcWorksParentNo(),
										suffixIsLeafMap.get(alterWork.getCcWorksParentNo()) + 1);
								alterWork.setTreeNode(preTreeNode + (suffixMap.get(alterWork.getCcWorksNo()) + 1));
							} else {
								suffixIsLeafMap.put(alterWork.getCcWorksParentNo(), 1001);
								alterWork.setTreeNode(preTreeNode + "1001");
							}
						}
					}
				});
				applyAmountTax[0] = CalcUtils.calcSubtract(applyAmount[0], applyAmountNoTax[0]);
			}
			// ????????????????????????
			ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork = new ZxGcsgCcCoAlterWork();
			zxGcsgCcCoAlterWork.setCcCoAlterId(dbZxGcsgCcCoAlter.getCcCoAlterId());
			zxGcsgCcCoAlterWorkMapper.deleteZxGcsgCcCoAlterWorkByCondition(zxGcsgCcCoAlterWork);
			if (zxGcsgCcCoAlter.getAlterWorkList() != null && zxGcsgCcCoAlter.getAlterWorkList().size() > 0) {
				flag = zxGcsgCcCoAlterWorkMapper.batchInsertZxGcsgCcCoAlterWork(zxGcsgCcCoAlter.getAlterWorkList());
			}
			ZxGcsgCommonAttachment zxGcsgCommonAttachment = new ZxGcsgCommonAttachment();
			zxGcsgCommonAttachment.setOtherId(dbZxGcsgCcCoAlter.getCcCoAlterId());
			zxGcsgCommonAttachmentMapper.deleteZxGcsgCommonAttachmentByCondition(zxGcsgCommonAttachment);
			if (zxGcsgCcCoAlter.getAttachmentList() != null && zxGcsgCcCoAlter.getAttachmentList().size() > 0) {
				zxGcsgCcCoAlter.getAttachmentList().stream().forEach(attachment -> {
					attachment.setUid(UuidUtil.generate());
					attachment.setOtherId(dbZxGcsgCcCoAlter.getCcCoAlterId());
					attachment.setFileType("1");
					attachment.setCreateUserInfo(userKey, realName);
				});
				zxGcsgCommonAttachmentMapper.batchInsertZxGcsgCommonAttachment(zxGcsgCcCoAlter.getAttachmentList());
			}
			// ??????????????????????????????(??????)????????????????????????????????????(??????)???????????????????????????(??????)
			// ???????????????????????????(??????)?????????????????????????????????(??????)????????????????????????(??????)
			// ??????????????????????????????(??????)????????????????????????????????????(??????)???????????????????????????(??????)
			ZxGcsgCtContrApply dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper
					.selectByPrimaryKey(dbZxGcsgCcCoAlter.getCtContrApplyId());
			BigDecimal replyAmount = BigDecimal.ZERO;
			BigDecimal replyAmountNoTax = BigDecimal.ZERO;
			BigDecimal replyAmountTax = BigDecimal.ZERO;
			if (dbZxGcsgCtContrApply != null) {
				replyAmount = CalcUtils.calcAdd(dbZxGcsgCtContrApply.getUpAlterContractSum(), applyAmount[0]);
				replyAmountNoTax = CalcUtils.calcAdd(dbZxGcsgCtContrApply.getUpAlterContractSumNoTax(),
						applyAmountNoTax[0]);
				replyAmountTax = CalcUtils.calcAdd(dbZxGcsgCtContrApply.getUpAlterContractSumTax(), applyAmountTax[0]);
				dbZxGcsgCtContrApply.setAlterContractSum(CalcUtils.calcDivide(replyAmount, new BigDecimal(10000), 6));
				dbZxGcsgCtContrApply
						.setAlterContractSumNoTax(CalcUtils.calcDivide(replyAmountNoTax, new BigDecimal(10000), 6));
				dbZxGcsgCtContrApply
						.setAlterContractSumTax(CalcUtils.calcDivide(replyAmountTax, new BigDecimal(10000), 6));
				dbZxGcsgCtContrApply
						.setCurrentTaxAmount(CalcUtils.calcDivide(applyAmount[0], new BigDecimal(10000), 6));
				dbZxGcsgCtContrApply.setPp4NoTax(CalcUtils.calcDivide(applyAmountNoTax[0], new BigDecimal(10000), 6));
				dbZxGcsgCtContrApply.setPp4Tax(CalcUtils.calcDivide(applyAmountTax[0], new BigDecimal(10000), 6));
				flag = zxGcsgCtContrApplyMapper.updateByPrimaryKey(dbZxGcsgCtContrApply);
			}
			dbZxGcsgCcCoAlter.setReplyAmount(CalcUtils.calcDivide(replyAmount, new BigDecimal(10000), 6));
			dbZxGcsgCcCoAlter.setReplyAmountNoTax(CalcUtils.calcDivide(replyAmountNoTax, new BigDecimal(10000), 6));
			dbZxGcsgCcCoAlter.setReplyAmountTax(CalcUtils.calcDivide(replyAmountTax, new BigDecimal(10000), 6));
			dbZxGcsgCcCoAlter.setApplyAmount(CalcUtils.calcDivide(applyAmount[0], new BigDecimal(10000), 6));
			dbZxGcsgCcCoAlter.setApplyAmountNoTax(CalcUtils.calcDivide(applyAmountNoTax[0], new BigDecimal(10000), 6));
			dbZxGcsgCcCoAlter.setApplyAmountTax(CalcUtils.calcDivide(applyAmountTax[0], new BigDecimal(10000), 6));
			dbZxGcsgCcCoAlter.setModifyUserInfo(userKey, realName);
			flag = zxGcsgCcCoAlterMapper.updateByPrimaryKey(dbZxGcsgCcCoAlter);
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zxGcsgCcCoAlter);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseEntity saveZxGcsgCcCoAlterDetailsWorkList(ZxGcsgCcCoAlter zxGcsgCcCoAlter) {
		if (zxGcsgCcCoAlter == null) {
			zxGcsgCcCoAlter = new ZxGcsgCcCoAlter();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		// ????????????
		ZxGcsgCcCoAlter dbZxGcsgCcCoAlter = zxGcsgCcCoAlterMapper.selectByPrimaryKey(zxGcsgCcCoAlter.getCcCoAlterId());
		if (dbZxGcsgCcCoAlter != null) {
			// ??????????????????????????????(??????)????????????????????????????????????(??????)???????????????????????????(??????)
			BigDecimal[] applyAmount = { BigDecimal.ZERO };
			BigDecimal[] applyAmountNoTax = { BigDecimal.ZERO };
			BigDecimal[] applyAmountTax = { BigDecimal.ZERO };
			if (zxGcsgCcCoAlter.getAlterWorkList() != null && zxGcsgCcCoAlter.getAlterWorkList().size() > 0) {
				Map<String, Object> checkMap1 = new HashMap<String, Object>();
				Map<String, Object> checkMap2 = new HashMap<String, Object>();
				ZxGcsgCcWorks zxGcsgCcWorks = new ZxGcsgCcWorks();
				zxGcsgCcWorks.setCtContractId(dbZxGcsgCcCoAlter.getCtContractId());
				List<ZxGcsgCcWorks> worksList = zxGcsgCcWorksMapper.selectByZxGcsgCcWorksList(zxGcsgCcWorks);
				if (worksList.size() > 0) {
					worksList.stream().forEach(works -> {
						checkMap2.put(works.getWorkNo(), 1);
					});
				}
				Iterator<ZxGcsgCcCoAlterWork> it = zxGcsgCcCoAlter.getAlterWorkList().iterator();
				while (it.hasNext()) {
					ZxGcsgCcCoAlterWork checkAlterWork = it.next();
					// check?????????????????????????????????
					if (StrUtil.equals("2", checkAlterWork.getAlterType()) && checkAlterWork.getIsLeaf() != 1) {
						return repEntity.layerMessage("no", "??????????????????????????????????????????????????????");
					}
					// check?????????????????????,?????????????????????????????????
					if (StrUtil.equals("2", checkAlterWork.getAlterType()) && checkAlterWork.getIsLeaf() == 1) {
						if (checkMap1.containsKey(checkAlterWork.getCcWorksId())) {
							return repEntity.layerMessage("no", "?????????????????????,?????????????????????????????????");
						} else {
							checkMap1.put(checkAlterWork.getCcWorksId(), 1);
						}
					}
					// check??????cc_works_parent_id????????????
					if (StrUtil.isEmpty(checkAlterWork.getCcWorksParentId())) {
						return repEntity.layerMessage("no", "???????????????");
					}
					// check????????????????????????????????????
					if (StrUtil.equals("1", checkAlterWork.getAlterType())
							&& checkMap2.containsKey(checkAlterWork.getCcWorksNo())) {
						return repEntity.layerMessage("no", "???????????????????????????????????????????????????????????????????????????????????????");
					} else if (StrUtil.equals("1", checkAlterWork.getAlterType())) {
						checkMap2.put(checkAlterWork.getCcWorksNo(), 1);
					}
				}
				Map<String, Integer> newAddSuffixMap = new HashMap<String, Integer>();
				zxGcsgCcCoAlter.getAlterWorkList().stream().forEach(alterWork -> {
					alterWork.setCcCoAlterWorkId(UuidUtil.generate());
					alterWork.setCcCoAlterId(dbZxGcsgCcCoAlter.getCcCoAlterId());
					alterWork.setCtContrApplyId(dbZxGcsgCcCoAlter.getCtContrApplyId());
					// ???????????????????????????id(??????????????????)
					// if (StrUtil.equals("1", alterWork.getAlterType())) {
					// alterWork.setCcWorksId("");
					// }
					alterWork.setCreateUserInfo(userKey, realName);
					BigDecimal afterAmt = CalcUtils.calcMultiply(alterWork.getAfterChangeQty(),
							alterWork.getAfterChangePrice());
					BigDecimal afterAmtNoTax = BigDecimal.ZERO;
					BigDecimal afterAmtTax = BigDecimal.ZERO;
					if (alterWork.getTaxRate() != null && NumberUtil.isNumber(alterWork.getTaxRate())) {
						afterAmtNoTax = CalcUtils.calcDivide(afterAmt, CalcUtils.calcAdd(BigDecimal.ONE,
								CalcUtils.calcDivide(new BigDecimal(alterWork.getTaxRate()), new BigDecimal(100))), 2);
						afterAmtTax = CalcUtils.calcSubtract(afterAmt, afterAmtNoTax);
					}
					if (StrUtil.equals("2", alterWork.getAlterType()) && alterWork.getIsLeaf() != null
							&& alterWork.getIsLeaf() == 1) {
						applyAmount[0] = CalcUtils.calcAdd(applyAmount[0],
								CalcUtils.calcMultiply(alterWork.getChangeQty(), alterWork.getOriginPrice()));
						applyAmountNoTax[0] = CalcUtils.calcAdd(applyAmountNoTax[0],
								CalcUtils.calcMultiply(alterWork.getChangeQty(), alterWork.getOriginPriceNoTax()));
					} else if (StrUtil.equals("1", alterWork.getAlterType()) && alterWork.getIsLeaf() != null
							&& alterWork.getIsLeaf() == 1) {
						applyAmount[0] = CalcUtils.calcAdd(applyAmount[0], afterAmt);
						applyAmountNoTax[0] = CalcUtils.calcAdd(applyAmountNoTax[0], afterAmtNoTax);
					}
					alterWork.setAfterAmt(afterAmt);
					alterWork.setAfterAmtNoTax(afterAmtNoTax);
					alterWork.setAfterAmtTax(afterAmtTax);
					// ????????????,??????treeNode[????????????treeNode??????]
					if (StrUtil.equals("1", alterWork.getAlterType())) {
						ZxGcsgCcWorks ccWorks = new ZxGcsgCcWorks();
						ccWorks.setCcWorksId(alterWork.getCcWorksParentId());
						ZxGcsgCcWorks dbMaxTreeNode = zxGcsgCcWorksMapper.getZxGcsgCcWorksByCondition(ccWorks);
						// ???????????????
						if (dbMaxTreeNode != null) {
							String initPreTreeNode = dbMaxTreeNode.getTreeNode();
							// 1???map?????????map
							if (newAddSuffixMap.containsKey(alterWork.getCcWorksParentId())) {
								alterWork.setTreeNode(
										initPreTreeNode + (newAddSuffixMap.get(alterWork.getCcWorksParentId()) + 1));
								newAddSuffixMap.put(alterWork.getCcWorksParentId(),
										newAddSuffixMap.get(alterWork.getCcWorksParentId()) + 1);
							} else {
								// 2???DB?????????????????????treeNode
								// ????????????????????????treeNode
								ccWorks.setCcWorksId("");
								ccWorks.setParentID(alterWork.getCcWorksParentId());
								ZxGcsgCcWorks maxTreeNode = zxGcsgCcWorksMapper
										.getZxGcsgCcWorksMAXTreeNodeByCondition(ccWorks);
								if (maxTreeNode != null) {
									String preTreeNode = maxTreeNode.getTreeNode().substring(0,
											maxTreeNode.getTreeNode().length() - 4);
									String proTreeNode = maxTreeNode.getTreeNode()
											.substring(maxTreeNode.getTreeNode().length() - 4);
									alterWork.setTreeNode(preTreeNode + (Integer.parseInt(proTreeNode) + 1));
									newAddSuffixMap.put(alterWork.getCcWorksParentId(),
											Integer.parseInt(proTreeNode) + 1);
								} else {
									// 3????????????????????????treeNode
									String preTreeNode = dbMaxTreeNode.getTreeNode();
									alterWork.setTreeNode(preTreeNode + "1001");
									newAddSuffixMap.put(alterWork.getCcWorksParentId(), 1001);
								}
							}
						}
					} else {
						// ????????????????????????
						ZxGcsgCcWorks ccWorks = new ZxGcsgCcWorks();
						ccWorks.setCcWorksId(alterWork.getCcWorksId());
						ZxGcsgCcWorks dbZxGcsgCcWorks = zxGcsgCcWorksMapper.getZxGcsgCcWorksByCondition(ccWorks);
						alterWork.setTreeNode(dbZxGcsgCcWorks.getTreeNode());
					}
				});
				applyAmountTax[0] = CalcUtils.calcSubtract(applyAmount[0], applyAmountNoTax[0]);
			}
			// ????????????????????????
			applyAmount[0] = CalcUtils.calcDivide(applyAmount[0], new BigDecimal(10000), 6);
			applyAmountNoTax[0] = CalcUtils.calcDivide(applyAmountNoTax[0], new BigDecimal(10000), 6);
			applyAmountTax[0] = CalcUtils.calcDivide(applyAmountTax[0], new BigDecimal(10000), 6);
			// ????????????????????????
			ZxGcsgCcCoAlterWork zxGcsgCcCoAlterWork = new ZxGcsgCcCoAlterWork();
			zxGcsgCcCoAlterWork.setCcCoAlterId(dbZxGcsgCcCoAlter.getCcCoAlterId());
			zxGcsgCcCoAlterWorkMapper.deleteZxGcsgCcCoAlterWorkByCondition(zxGcsgCcCoAlterWork);
			if (zxGcsgCcCoAlter.getAlterWorkList() != null && zxGcsgCcCoAlter.getAlterWorkList().size() > 0) {
				flag = zxGcsgCcCoAlterWorkMapper.batchInsertZxGcsgCcCoAlterWork(zxGcsgCcCoAlter.getAlterWorkList());
			}
			ZxGcsgCommonAttachment zxGcsgCommonAttachment = new ZxGcsgCommonAttachment();
			zxGcsgCommonAttachment.setOtherId(dbZxGcsgCcCoAlter.getCcCoAlterId());
			zxGcsgCommonAttachmentMapper.deleteZxGcsgCommonAttachmentByCondition(zxGcsgCommonAttachment);
			if (zxGcsgCcCoAlter.getAttachmentList() != null && zxGcsgCcCoAlter.getAttachmentList().size() > 0) {
				zxGcsgCcCoAlter.getAttachmentList().stream().forEach(attachment -> {
					attachment.setUid(UuidUtil.generate());
					attachment.setOtherId(dbZxGcsgCcCoAlter.getCcCoAlterId());
					attachment.setFileType("1");
					attachment.setCreateUserInfo(userKey, realName);
				});
				zxGcsgCommonAttachmentMapper.batchInsertZxGcsgCommonAttachment(zxGcsgCcCoAlter.getAttachmentList());
			}
			// ??????????????????????????????(??????)????????????????????????????????????(??????)???????????????????????????(??????)
			// ???????????????????????????(??????)?????????????????????????????????(??????)????????????????????????(??????)
			// ??????????????????????????????(??????)????????????????????????????????????(??????)???????????????????????????(??????)
			ZxGcsgCtContrApply dbZxGcsgCtContrApply = zxGcsgCtContrApplyMapper
					.selectByPrimaryKey(dbZxGcsgCcCoAlter.getCtContrApplyId());
			BigDecimal replyAmount = BigDecimal.ZERO;
			BigDecimal replyAmountNoTax = BigDecimal.ZERO;
			BigDecimal replyAmountTax = BigDecimal.ZERO;
			if (dbZxGcsgCtContrApply != null) {
				replyAmount = CalcUtils.calcAdd(dbZxGcsgCtContrApply.getUpAlterContractSum(), applyAmount[0]);
				replyAmountNoTax = CalcUtils.calcAdd(dbZxGcsgCtContrApply.getUpAlterContractSumNoTax(),
						applyAmountNoTax[0]);
				replyAmountTax = CalcUtils.calcAdd(dbZxGcsgCtContrApply.getUpAlterContractSumTax(), applyAmountTax[0]);
				dbZxGcsgCtContrApply.setAlterContractSum(replyAmount);
				dbZxGcsgCtContrApply.setAlterContractSumNoTax(replyAmountNoTax);
				dbZxGcsgCtContrApply.setAlterContractSumTax(replyAmountTax);
				dbZxGcsgCtContrApply.setCurrentTaxAmount(applyAmount[0]);
				dbZxGcsgCtContrApply.setPp4NoTax(applyAmountNoTax[0]);
				dbZxGcsgCtContrApply.setPp4Tax(applyAmountTax[0]);
				flag = zxGcsgCtContrApplyMapper.updateByPrimaryKey(dbZxGcsgCtContrApply);
			}
			dbZxGcsgCcCoAlter.setReplyAmount(replyAmount);
			dbZxGcsgCcCoAlter.setReplyAmountNoTax(replyAmountNoTax);
			dbZxGcsgCcCoAlter.setReplyAmountTax(replyAmountTax);
			dbZxGcsgCcCoAlter.setApplyAmount(applyAmount[0]);
			dbZxGcsgCcCoAlter.setApplyAmountNoTax(applyAmountNoTax[0]);
			dbZxGcsgCcCoAlter.setApplyAmountTax(applyAmountTax[0]);
			dbZxGcsgCcCoAlter.setReplyDate(zxGcsgCcCoAlter.getReplyDate());
			dbZxGcsgCcCoAlter.setRemarks(zxGcsgCcCoAlter.getRemarks());
			dbZxGcsgCcCoAlter.setModifyUserInfo(userKey, realName);
			flag = zxGcsgCcCoAlterMapper.updateByPrimaryKey(dbZxGcsgCcCoAlter);
		}
		// ??????
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zxGcsgCcCoAlter);
		}
	}

}
