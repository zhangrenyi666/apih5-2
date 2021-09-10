package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSaFsSettlement;
import org.apache.ibatis.annotations.Param;

public interface ZxSaFsSettlementMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSaFsSettlement record);

    int insertSelective(ZxSaFsSettlement record);

    ZxSaFsSettlement selectByPrimaryKey(String key);

    ZxSaFsSettlement selectByWorkId(@Param(value = "workId") String workId);

    int updateByPrimaryKeySelective(ZxSaFsSettlement record);

    int updateByPrimaryKey(ZxSaFsSettlement record);

    List<ZxSaFsSettlement> selectByZxSaFsSettlementList(ZxSaFsSettlement record);

    int batchDeleteUpdateZxSaFsSettlement(List<ZxSaFsSettlement> recordList, ZxSaFsSettlement record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    /**
     * 统计结算单开累数据
     * @author suncg
     * @param period,zxCtFsContractId
     * */
    ZxSaFsSettlement selectLeijiInfo (@Param(value = "period") String period,@Param(value = "zxCtFsContractId") String zxCtFsContractId);

    /**
     * 查询其次最大值
     * @author suncg
     * @param record
     * */
    List<ZxSaFsSettlement> selectMaxPeriod(@Param(value = "record") ZxSaFsSettlement record);

    ZxSaFsSettlement  selectCountByContract(@Param(value = "record") ZxSaFsSettlement record);
}
