package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfCost;
import org.apache.ibatis.annotations.Param;

public interface ZxSfCostMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfCost record);

    int insertSelective(ZxSfCost record);

    ZxSfCost selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfCost record);

    int updateByPrimaryKey(ZxSfCost record);

    List<ZxSfCost> selectByZxSfCostList(ZxSfCost record);

    int batchDeleteUpdateZxSfCost(List<ZxSfCost> recordList, ZxSfCost record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    /**
     * 查询公司级数据
     * @author suncg
     * @param record
     * */
    ZxSfCost getCompany (ZxSfCost record);

    List<ZxSfCost> getCompanyList(ZxSfCost record);

    /**
     * 查询归档项目数据
     * @author suncg
     * @param record
     * */
    ZxSfCost getGuiDang (@Param("record") ZxSfCost record, @Param("today")String today);

    /**
     * 查询交工项目数据
     * @author suncg
     * @param record
     * */
    ZxSfCost getJiaoGong (@Param("record") ZxSfCost record, @Param("today")String today);

    /**
     * 查询完工项目数据
     * @author suncg
     * @param record
     * */
    ZxSfCost getWanGong (@Param("record") ZxSfCost record, @Param("today")String today);

    /**
     * 查询开工项目数据
     * @author suncg
     * @param record
     * */
    ZxSfCost getKaiGong (@Param("record") ZxSfCost record, @Param("today")String today);


    List<ZxSfCost> getGuiDangList(@Param("record") ZxSfCost record, @Param("today")String today);

    List<ZxSfCost> getJiaoGongList(@Param("record") ZxSfCost record, @Param("today")String today);

    List<ZxSfCost> getWanGongList(@Param("record") ZxSfCost record, @Param("today")String today);

    List<ZxSfCost> getKaiGongList(@Param("record") ZxSfCost record, @Param("today")String today);
}
