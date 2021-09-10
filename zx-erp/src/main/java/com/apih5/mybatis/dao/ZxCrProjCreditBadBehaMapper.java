package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCrProjCreditBadBeha;

public interface ZxCrProjCreditBadBehaMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCrProjCreditBadBeha record);

    int insertSelective(ZxCrProjCreditBadBeha record);

    ZxCrProjCreditBadBeha selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCrProjCreditBadBeha record);

    int updateByPrimaryKey(ZxCrProjCreditBadBeha record);

    List<ZxCrProjCreditBadBeha> selectByZxCrProjCreditBadBehaList(ZxCrProjCreditBadBeha record);

    int batchDeleteUpdateZxCrProjCreditBadBeha(List<ZxCrProjCreditBadBeha> recordList, ZxCrProjCreditBadBeha record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
}
