package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjTzThreeShareholder;

public interface ZjTzThreeShareholderMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjTzThreeShareholder record);

    int insertSelective(ZjTzThreeShareholder record);

    ZjTzThreeShareholder selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjTzThreeShareholder record);

    int updateByPrimaryKey(ZjTzThreeShareholder record);

    List<ZjTzThreeShareholder> selectByZjTzThreeShareholderList(ZjTzThreeShareholder record);

    int batchDeleteUpdateZjTzThreeShareholder(List<ZjTzThreeShareholder> recordList, ZjTzThreeShareholder record);

	int updateZjTzThreeShareholderProjectShortName(ZjTzThreeShareholder shareholder);

}

