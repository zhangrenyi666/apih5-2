package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtWorkBook;

public interface ZxCtWorkBookMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtWorkBook record);

    int insertSelective(ZxCtWorkBook record);

    ZxCtWorkBook selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtWorkBook record);

    int updateByPrimaryKey(ZxCtWorkBook record);

    List<ZxCtWorkBook> selectByZxCtWorkBookList(ZxCtWorkBook record);

    int batchDeleteUpdateZxCtWorkBook(List<ZxCtWorkBook> recordList, ZxCtWorkBook record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
	int deleteAllZxCtWorkBook(ZxCtWorkBook zxCtWorkBook);

	int updateContractQuantityByPrimaryKey(ZxCtWorkBook dbZxCtWorkBook);

	int updateVerificationQuantityByPrimaryKey(ZxCtWorkBook dbZxCtWorkBook);

	int updateStatusByOrgId(ZxCtWorkBook updateZxCtWorkBook);

}
