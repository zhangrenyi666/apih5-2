package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjXmSalaryUserAttachment;

public interface ZjXmSalaryUserAttachmentMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZjXmSalaryUserAttachment record);

	int insertSelective(ZjXmSalaryUserAttachment record);

	ZjXmSalaryUserAttachment selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZjXmSalaryUserAttachment record);

	int updateByPrimaryKey(ZjXmSalaryUserAttachment record);

	List<ZjXmSalaryUserAttachment> selectByZjXmSalaryUserAttachmentList(ZjXmSalaryUserAttachment record);

	int batchDeleteUpdateZjXmSalaryUserAttachment(List<ZjXmSalaryUserAttachment> recordList,
			ZjXmSalaryUserAttachment record);

	int batchInsertZjXmSalaryUserAttachment(List<ZjXmSalaryUserAttachment> recordList);

	int deleteZjXmSalaryUserAttachmentByOtherId(String key);

	int deleteZjXmSalaryUserAttachmentByCondition(ZjXmSalaryUserAttachment record);

}
