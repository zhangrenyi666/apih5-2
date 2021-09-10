package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfProjectEmployee;

public interface ZxSfProjectEmployeeMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfProjectEmployee record);

    int insertSelective(ZxSfProjectEmployee record);

    ZxSfProjectEmployee selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfProjectEmployee record);

    int updateByPrimaryKey(ZxSfProjectEmployee record);

    List<ZxSfProjectEmployee> selectByZxSfProjectEmployeeList(ZxSfProjectEmployee record);

    int batchDeleteUpdateZxSfProjectEmployee(List<ZxSfProjectEmployee> recordList, ZxSfProjectEmployee record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    List<ZxSfProjectEmployee> uReportForm(ZxSfProjectEmployee record);

    List<ZxSfProjectEmployee> uReportFormCom(ZxSfProjectEmployee record);
}
