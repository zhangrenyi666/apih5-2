package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfFee;
import org.apache.ibatis.annotations.Param;

public interface ZxSfFeeMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfFee record);

    int insertSelective(ZxSfFee record);

    ZxSfFee selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfFee record);

    int updateByPrimaryKey(ZxSfFee record);

    List<ZxSfFee> selectByZxSfFeeList(ZxSfFee record);

    int batchDeleteUpdateZxSfFee(List<ZxSfFee> recordList, ZxSfFee record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    /**
     * 查询公司级数据
     * @author suncg
     * @param record
     * */
    ZxSfFee getCompany (ZxSfFee record);

    List<ZxSfFee> getCompanyList(ZxSfFee record);

    /**
     * 查询归档项目数据
     * @author suncg
     * @param record
     * */
    ZxSfFee getGuiDang (@Param("record") ZxSfFee record, @Param("today")String today);

    /**
     * 查询交工项目数据
     * @author suncg
     * @param record
     * */
    ZxSfFee getJiaoGong (@Param("record") ZxSfFee record, @Param("today")String today);

    /**
     * 查询完工项目数据
     * @author suncg
     * @param record
     * */
    ZxSfFee getWanGong (@Param("record") ZxSfFee record, @Param("today")String today);

    /**
     * 查询开工项目数据
     * @author suncg
     * @param record
     * */
    ZxSfFee getKaiGong (@Param("record") ZxSfFee record, @Param("today")String today);


    List<ZxSfFee> getGuiDangList(@Param("record") ZxSfFee record, @Param("today")String today);

    List<ZxSfFee> getJiaoGongList(@Param("record") ZxSfFee record, @Param("today")String today);

    List<ZxSfFee> getWanGongList(@Param("record") ZxSfFee record, @Param("today")String today);

    List<ZxSfFee> getKaiGongList(@Param("record") ZxSfFee record, @Param("today")String today);


    ZxSfFee getJuInfo (ZxSfFee record);

}
