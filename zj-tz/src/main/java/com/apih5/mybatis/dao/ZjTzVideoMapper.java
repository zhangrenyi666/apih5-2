package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzVideo;

public interface ZjTzVideoMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzVideo record);

    int insertSelective(ZjTzVideo record);

    ZjTzVideo selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzVideo record);

    int updateByPrimaryKey(ZjTzVideo record);

    List<ZjTzVideo> selectByZjTzVideoList(ZjTzVideo record);

    int batchDeleteUpdateZjTzVideo(List<ZjTzVideo> recordList, ZjTzVideo record);

	int batchApproveAgreeZjTzVideo(List<ZjTzVideo> zjTzVideoList, ZjTzVideo zjTzVideo);

	int batchApproveRejectZjTzVideo(List<ZjTzVideo> zjTzVideoList, ZjTzVideo zjTzVideo);

	int batchDeleteReleaseZjTzVideo(List<ZjTzVideo> zjTzVideoList, ZjTzVideo zjTzRules);

	int batchDeleteRecallZjTzVideo(List<ZjTzVideo> zjTzVideoList, ZjTzVideo zjTzRules);
	
	ZjTzVideo selectHomeZjTzVideo(ZjTzVideo record);
}

