package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtFsContractReview;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Property;

public interface ZxCtFsContractReviewMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtFsContractReview record);

    int insertSelective(ZxCtFsContractReview record);

    ZxCtFsContractReview selectByPrimaryKey(String key);

    ZxCtFsContractReview selectByWorkId(@Param(value = "workId") String workId);

    int updateByPrimaryKeySelective(ZxCtFsContractReview record);

    int updateByPrimaryKey(ZxCtFsContractReview record);

    List<ZxCtFsContractReview> selectByZxCtFsContractReviewList(ZxCtFsContractReview record);

    int batchDeleteUpdateZxCtFsContractReview(List<ZxCtFsContractReview> recordList, ZxCtFsContractReview record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    int selectLeiJi(@Param(value = "fromContractNo") String fromContractNo );
}
