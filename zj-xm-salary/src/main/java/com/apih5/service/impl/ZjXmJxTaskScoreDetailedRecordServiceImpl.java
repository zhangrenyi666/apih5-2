package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjXmJxTaskScoreDetailedRecordMapper;
import com.apih5.mybatis.pojo.ZjXmJxTaskScoreDetailedRecord;
import com.apih5.service.ZjXmJxTaskScoreDetailedRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjXmJxTaskScoreDetailedRecordService")
public class ZjXmJxTaskScoreDetailedRecordServiceImpl implements ZjXmJxTaskScoreDetailedRecordService {

	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired(required = true)
	private ZjXmJxTaskScoreDetailedRecordMapper zjXmJxTaskScoreDetailedRecordMapper;

	@Override
	public ResponseEntity getZjXmJxTaskScoreDetailedRecordListByCondition(
			ZjXmJxTaskScoreDetailedRecord zjXmJxTaskScoreDetailedRecord) {
		if (zjXmJxTaskScoreDetailedRecord == null) {
			zjXmJxTaskScoreDetailedRecord = new ZjXmJxTaskScoreDetailedRecord();
		}
		// 分页查询
		PageHelper.startPage(zjXmJxTaskScoreDetailedRecord.getPage(), zjXmJxTaskScoreDetailedRecord.getLimit());
		// 获取数据
		List<ZjXmJxTaskScoreDetailedRecord> zjXmJxTaskScoreDetailedRecordList = zjXmJxTaskScoreDetailedRecordMapper
				.selectByZjXmJxTaskScoreDetailedRecordList(zjXmJxTaskScoreDetailedRecord);
		// 得到分页信息
		PageInfo<ZjXmJxTaskScoreDetailedRecord> p = new PageInfo<>(zjXmJxTaskScoreDetailedRecordList);

		return repEntity.okList(zjXmJxTaskScoreDetailedRecordList, p.getTotal());
	}

	@Override
	public ResponseEntity getZjXmJxTaskScoreDetailedRecordDetails(
			ZjXmJxTaskScoreDetailedRecord zjXmJxTaskScoreDetailedRecord) {
		if (zjXmJxTaskScoreDetailedRecord == null) {
			zjXmJxTaskScoreDetailedRecord = new ZjXmJxTaskScoreDetailedRecord();
		}
		// 获取数据
		ZjXmJxTaskScoreDetailedRecord dbZjXmJxTaskScoreDetailedRecord = zjXmJxTaskScoreDetailedRecordMapper
				.selectByPrimaryKey(zjXmJxTaskScoreDetailedRecord.getRecordId());
		// 数据存在
		if (dbZjXmJxTaskScoreDetailedRecord != null) {
			return repEntity.ok(dbZjXmJxTaskScoreDetailedRecord);
		} else {
			return repEntity.layerMessage("no", "无数据！");
		}
	}

	@Override
	public ResponseEntity saveZjXmJxTaskScoreDetailedRecord(
			ZjXmJxTaskScoreDetailedRecord zjXmJxTaskScoreDetailedRecord) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		zjXmJxTaskScoreDetailedRecord.setRecordId(UuidUtil.generate());
		zjXmJxTaskScoreDetailedRecord.setCreateUserInfo(userKey, realName);
		int flag = zjXmJxTaskScoreDetailedRecordMapper.insert(zjXmJxTaskScoreDetailedRecord);
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.sava", zjXmJxTaskScoreDetailedRecord);
		}
	}

	@Override
	public ResponseEntity updateZjXmJxTaskScoreDetailedRecord(
			ZjXmJxTaskScoreDetailedRecord zjXmJxTaskScoreDetailedRecord) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjXmJxTaskScoreDetailedRecord dbzjXmJxTaskScoreDetailedRecord = zjXmJxTaskScoreDetailedRecordMapper
				.selectByPrimaryKey(zjXmJxTaskScoreDetailedRecord.getRecordId());
		if (dbzjXmJxTaskScoreDetailedRecord != null
				&& StrUtil.isNotEmpty(dbzjXmJxTaskScoreDetailedRecord.getRecordId())) {
			// 任务考核得分明细id
			dbzjXmJxTaskScoreDetailedRecord.setDetailedId(zjXmJxTaskScoreDetailedRecord.getDetailedId());
			// 被审核人员主键
			dbzjXmJxTaskScoreDetailedRecord.setAuditeeKey(zjXmJxTaskScoreDetailedRecord.getAuditeeKey());
			// 被审核人员姓名
			dbzjXmJxTaskScoreDetailedRecord.setAuditeeName(zjXmJxTaskScoreDetailedRecord.getAuditeeName());
			// 被审核者部门主键
			dbzjXmJxTaskScoreDetailedRecord.setAuditeeDeptId(zjXmJxTaskScoreDetailedRecord.getAuditeeDeptId());
			// 被审核者部门名称
			dbzjXmJxTaskScoreDetailedRecord.setAuditeeDeptName(zjXmJxTaskScoreDetailedRecord.getAuditeeDeptName());
			// 被审核者项目id
			dbzjXmJxTaskScoreDetailedRecord.setAuditeeProId(zjXmJxTaskScoreDetailedRecord.getAuditeeProId());
			// 被审核者项目名称
			dbzjXmJxTaskScoreDetailedRecord.setAuditeeProName(zjXmJxTaskScoreDetailedRecord.getAuditeeProName());
			// 人事专员主键
			dbzjXmJxTaskScoreDetailedRecord.setHrUserKey(zjXmJxTaskScoreDetailedRecord.getHrUserKey());
			// 人事专员姓名
			dbzjXmJxTaskScoreDetailedRecord.setHrName(zjXmJxTaskScoreDetailedRecord.getHrName());
			// 申诉状态
			dbzjXmJxTaskScoreDetailedRecord.setAppealStatus(zjXmJxTaskScoreDetailedRecord.getAppealStatus());
			// 申诉意见
			dbzjXmJxTaskScoreDetailedRecord.setAppealOpinion(zjXmJxTaskScoreDetailedRecord.getAppealOpinion());
			// 人事专员意见
			dbzjXmJxTaskScoreDetailedRecord.setHrOpinion(zjXmJxTaskScoreDetailedRecord.getHrOpinion());
			// 备注
			dbzjXmJxTaskScoreDetailedRecord.setRemarks(zjXmJxTaskScoreDetailedRecord.getRemarks());
			// 排序
			dbzjXmJxTaskScoreDetailedRecord.setSort(zjXmJxTaskScoreDetailedRecord.getSort());
			// 共通
			dbzjXmJxTaskScoreDetailedRecord.setModifyUserInfo(userKey, realName);
			flag = zjXmJxTaskScoreDetailedRecordMapper.updateByPrimaryKey(dbzjXmJxTaskScoreDetailedRecord);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update", zjXmJxTaskScoreDetailedRecord);
		}
	}

	@Override
	public ResponseEntity batchDeleteUpdateZjXmJxTaskScoreDetailedRecord(
			List<ZjXmJxTaskScoreDetailedRecord> zjXmJxTaskScoreDetailedRecordList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		if (zjXmJxTaskScoreDetailedRecordList != null && zjXmJxTaskScoreDetailedRecordList.size() > 0) {
			ZjXmJxTaskScoreDetailedRecord zjXmJxTaskScoreDetailedRecord = new ZjXmJxTaskScoreDetailedRecord();
			zjXmJxTaskScoreDetailedRecord.setModifyUserInfo(userKey, realName);
			flag = zjXmJxTaskScoreDetailedRecordMapper.batchDeleteUpdateZjXmJxTaskScoreDetailedRecord(
					zjXmJxTaskScoreDetailedRecordList, zjXmJxTaskScoreDetailedRecord);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.delete", zjXmJxTaskScoreDetailedRecordList);
		}
	}
}
