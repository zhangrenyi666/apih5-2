package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSaFsInventorySettlementDetail;
import org.apache.ibatis.annotations.Param;

public interface ZxSaFsInventorySettlementDetailMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaFsInventorySettlementDetail record);

    int insertSelective(ZxSaFsInventorySettlementDetail record);

    ZxSaFsInventorySettlementDetail selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSaFsInventorySettlementDetail record);

    int updateByPrimaryKey(ZxSaFsInventorySettlementDetail record);

    List<ZxSaFsInventorySettlementDetail> selectByZxSaFsInventorySettlementDetailList(ZxSaFsInventorySettlementDetail record);

    int batchDeleteUpdateZxSaFsInventorySettlementDetail(List<ZxSaFsInventorySettlementDetail> recordList, ZxSaFsInventorySettlementDetail record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    ZxSaFsInventorySettlementDetail selectUpInfo(@Param(value = "period") String period ,@Param(value = "conRevDetailId") String conRevDetailId);

    List<ZxSaFsInventorySettlementDetail> YgzYiLan(@Param(value = "record") ZxSaFsInventorySettlementDetail record);

    List<ZxSaFsInventorySettlementDetail> getDzInfo (@Param(value = "record") ZxSaFsInventorySettlementDetail record);

    List<ZxSaFsInventorySettlementDetail>  getYzInfo (@Param(value = "record") ZxSaFsInventorySettlementDetail record);

    List<ZxSaFsInventorySettlementDetail> getJfInfo (@Param(value = "record") ZxSaFsInventorySettlementDetail record);

    List<ZxSaFsInventorySettlementDetail> getQtInfo (@Param(value = "record") ZxSaFsInventorySettlementDetail record);

}
