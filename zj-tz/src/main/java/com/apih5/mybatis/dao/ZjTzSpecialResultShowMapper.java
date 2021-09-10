package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzSpecialResultShow;

public interface ZjTzSpecialResultShowMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzSpecialResultShow record);

    int insertSelective(ZjTzSpecialResultShow record);

    ZjTzSpecialResultShow selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzSpecialResultShow record);

    int updateByPrimaryKey(ZjTzSpecialResultShow record);

    List<ZjTzSpecialResultShow> selectByZjTzSpecialResultShowList(ZjTzSpecialResultShow record);

    int batchDeleteUpdateZjTzSpecialResultShow(List<ZjTzSpecialResultShow> recordList, ZjTzSpecialResultShow record);

	int batchReleaseZjTzSpecialResultShow(List<ZjTzSpecialResultShow> zjTzSpecialResultShowList,
			ZjTzSpecialResultShow zjTzRules);

	int batchRecallZjTzSpecialResultShow(List<ZjTzSpecialResultShow> zjTzSpecialResultShowList,
			ZjTzSpecialResultShow zjTzRules);
	
}

