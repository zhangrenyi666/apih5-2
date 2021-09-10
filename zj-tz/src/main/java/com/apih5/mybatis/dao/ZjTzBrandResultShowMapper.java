package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzBrandResultShow;

public interface ZjTzBrandResultShowMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzBrandResultShow record);

    int insertSelective(ZjTzBrandResultShow record);

    ZjTzBrandResultShow selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzBrandResultShow record);

    int updateByPrimaryKey(ZjTzBrandResultShow record);

    List<ZjTzBrandResultShow> selectByZjTzBrandResultShowList(ZjTzBrandResultShow record);

    int batchDeleteUpdateZjTzBrandResultShow(List<ZjTzBrandResultShow> recordList, ZjTzBrandResultShow record);

	int batchReleaseZjTzBrandResultShow(List<ZjTzBrandResultShow> zjTzBrandResultShowList,ZjTzBrandResultShow zjTzRules);

	int batchRecallZjTzBrandResultShow(List<ZjTzBrandResultShow> zjTzBrandResultShowList,ZjTzBrandResultShow zjTzRules);
	
	List<ZjTzBrandResultShow> selectZjTzHomeBrandResultShow(ZjTzBrandResultShow record);
	
	List<ZjTzBrandResultShow> exportZjTzBrandResultShowList(ZjTzBrandResultShow record);
	
}

