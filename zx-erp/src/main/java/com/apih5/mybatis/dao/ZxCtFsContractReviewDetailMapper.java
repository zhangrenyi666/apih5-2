package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtFsContractReviewDetail;
import org.apache.ibatis.annotations.Param;

public interface ZxCtFsContractReviewDetailMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtFsContractReviewDetail record);

    int insertSelective(ZxCtFsContractReviewDetail record);

    ZxCtFsContractReviewDetail selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtFsContractReviewDetail record);

    int updateByPrimaryKey(ZxCtFsContractReviewDetail record);

    List<ZxCtFsContractReviewDetail> selectByZxCtFsContractReviewDetailList(ZxCtFsContractReviewDetail record);

    int batchDeleteUpdateZxCtFsContractReviewDetail(List<ZxCtFsContractReviewDetail> recordList, ZxCtFsContractReviewDetail record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    /**
     * @author suncg
     * @param excelImportList
     * 导入excel数据
     * */
    int importZxCtFsContractReviewDetailsList(@Param("excelImportList") List<ZxCtFsContractReviewDetail> excelImportList);

    /**
     * @author suncg
     * @param zxCtFsContractId
     * 导入excel数据
     * */
    List<ZxCtFsContractReviewDetail> selectByFsContractId(@Param("zxCtFsContractId") String zxCtFsContractId);

    List<ZxCtFsContractReviewDetail> selectReviewDetailList(@Param("detil") ZxCtFsContractReviewDetail detil);
}
