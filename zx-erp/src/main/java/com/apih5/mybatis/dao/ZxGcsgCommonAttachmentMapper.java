package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxGcsgCommonAttachment;

public interface ZxGcsgCommonAttachmentMapper {
	int deleteByPrimaryKey(String key);

	int insert(ZxGcsgCommonAttachment record);

	int insertSelective(ZxGcsgCommonAttachment record);

	ZxGcsgCommonAttachment selectByPrimaryKey(String key);

	int updateByPrimaryKeySelective(ZxGcsgCommonAttachment record);

	int updateByPrimaryKey(ZxGcsgCommonAttachment record);

	List<ZxGcsgCommonAttachment> selectByZxGcsgCommonAttachmentList(ZxGcsgCommonAttachment record);

	int batchDeleteUpdateZxGcsgCommonAttachment(List<ZxGcsgCommonAttachment> recordList, ZxGcsgCommonAttachment record);

	// ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

	int batchInsertZxGcsgCommonAttachment(List<ZxGcsgCommonAttachment> recordList);

	int deleteZxGcsgCommonAttachmentByCondition(ZxGcsgCommonAttachment record);
}
