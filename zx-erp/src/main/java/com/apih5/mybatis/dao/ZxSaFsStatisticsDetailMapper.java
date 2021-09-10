package com.apih5.mybatis.dao;

import java.math.BigDecimal;
import java.util.List;

import com.apih5.mybatis.pojo.ZxSaFsSettlement;
import com.apih5.mybatis.pojo.ZxSaFsStatisticsDetail;
import org.apache.ibatis.annotations.Param;

public interface ZxSaFsStatisticsDetailMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaFsStatisticsDetail record);

    int insertSelective(ZxSaFsStatisticsDetail record);

    ZxSaFsStatisticsDetail selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSaFsStatisticsDetail record);

    int updateByPrimaryKey(ZxSaFsStatisticsDetail record);

    List<ZxSaFsStatisticsDetail> selectByZxSaFsStatisticsDetailList(ZxSaFsStatisticsDetail record);

    int batchDeleteUpdateZxSaFsStatisticsDetail(List<ZxSaFsStatisticsDetail> recordList, ZxSaFsStatisticsDetail record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    int updateStatisticsDetail(@Param(value = "tatisticsList")  List<ZxSaFsStatisticsDetail> tatisticsList);

    BigDecimal selectKaiLei(@Param(value = "period") String period, @Param(value = "zxCtFsContractId")String zxCtFsContractId,@Param(value = "statisticsType") String statisticsType);

    ZxSaFsStatisticsDetail selectBaoZhengJin(@Param(value ="record")ZxSaFsStatisticsDetail record);

    ZxSaFsStatisticsDetail selectBaoZhengJinSum(@Param(value ="record")ZxSaFsStatisticsDetail record);

    ZxSaFsStatisticsDetail selectByContractId(@Param(value ="record") ZxSaFsStatisticsDetail record);

    ZxSaFsStatisticsDetail selectBzjTotal(@Param(value ="record") ZxSaFsStatisticsDetail record);
}
