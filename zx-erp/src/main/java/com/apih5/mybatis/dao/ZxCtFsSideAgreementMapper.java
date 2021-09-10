package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtFsSideAgreement;
import org.apache.ibatis.annotations.Param;

public interface ZxCtFsSideAgreementMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtFsSideAgreement record);

    int insertSelective(ZxCtFsSideAgreement record);

    ZxCtFsSideAgreement selectByPrimaryKey(String key);
    ZxCtFsSideAgreement selectByPrimaryWorkId(@Param(value = "workId") String workId);

    int updateByPrimaryKeySelective(ZxCtFsSideAgreement record);

    int updateByPrimaryKey(ZxCtFsSideAgreement record);
    int updateByWorkId(ZxCtFsSideAgreement record);

    List<ZxCtFsSideAgreement> selectByZxCtFsSideAgreementList(ZxCtFsSideAgreement record);

    int batchDeleteUpdateZxCtFsSideAgreement(List<ZxCtFsSideAgreement> recordList, ZxCtFsSideAgreement record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    int sumContractNo(@Param(value = "contractNo") String contractNo);

    List<ZxCtFsSideAgreement> checkSideAgement(@Param(value = "record") ZxCtFsSideAgreement record);
}
