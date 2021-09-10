package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfPlanTarget;
import org.apache.ibatis.annotations.Param;

public interface ZxSfPlanTargetMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfPlanTarget record);

    int insertSelective(ZxSfPlanTarget record);

    ZxSfPlanTarget selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfPlanTarget record);

    int updateByPrimaryKey(ZxSfPlanTarget record);

    List<ZxSfPlanTarget> selectByZxSfPlanTargetList(ZxSfPlanTarget record);

    int batchDeleteUpdateZxSfPlanTarget(List<ZxSfPlanTarget> recordList, ZxSfPlanTarget record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    ZxSfPlanTarget getJuInfo();

    List<ZxSfPlanTarget> getComInfoList();

    ZxSfPlanTarget getComInfo(@Param("record")ZxSfPlanTarget record);

    ZxSfPlanTarget getGuiDang(@Param("record") ZxSfPlanTarget record,@Param("today") String today);

    ZxSfPlanTarget getJiaoGong(@Param("record") ZxSfPlanTarget record,@Param("today") String today);

    ZxSfPlanTarget getWanGong(@Param("record") ZxSfPlanTarget record,@Param("today") String today);

    ZxSfPlanTarget getKaiGong(@Param("record") ZxSfPlanTarget record,@Param("today") String today);

    List<ZxSfPlanTarget> getGuiDangList(@Param("record") ZxSfPlanTarget record,@Param("today") String today);

    List<ZxSfPlanTarget> getJiaoGongList(@Param("record") ZxSfPlanTarget record,@Param("today") String today);

    List<ZxSfPlanTarget> getWanGongList(@Param("record") ZxSfPlanTarget record,@Param("today") String today);

    List<ZxSfPlanTarget> getKaiGongList(@Param("record") ZxSfPlanTarget record,@Param("today") String today);




}
