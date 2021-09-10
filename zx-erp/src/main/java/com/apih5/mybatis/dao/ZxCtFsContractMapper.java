package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtFsContract;
import org.apache.ibatis.annotations.Param;

public interface ZxCtFsContractMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtFsContract record);

    int insertSelective(ZxCtFsContract record);

    ZxCtFsContract selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtFsContract record);

    int updateByPrimaryKey(ZxCtFsContract record);

    List<ZxCtFsContract> selectByZxCtFsContractList(ZxCtFsContract record);

    int batchDeleteUpdateZxCtFsContract(List<ZxCtFsContract> recordList, ZxCtFsContract record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    List<ZxCtFsContract> selectOrg(ZxCtFsContract record);

    List<ZxCtFsContract> selectByOrg(@Param(value = "record") ZxCtFsContract record);

    ZxCtFsContract selectYunShu(@Param(value = "record") ZxCtFsContract record);

    ZxCtFsContract selectQiTa(@Param(value = "record") ZxCtFsContract record);
}
