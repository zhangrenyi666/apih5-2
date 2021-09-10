package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfProjectEmpMain;
import org.apache.ibatis.annotations.Param;

public interface ZxSfProjectEmpMainMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfProjectEmpMain record);

    int insertSelective(ZxSfProjectEmpMain record);

    ZxSfProjectEmpMain selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfProjectEmpMain record);

    int updateByPrimaryKey(ZxSfProjectEmpMain record);

    List<ZxSfProjectEmpMain> selectByZxSfProjectEmpMainList(ZxSfProjectEmpMain record);

    int batchDeleteUpdateZxSfProjectEmpMain(List<ZxSfProjectEmpMain> recordList, ZxSfProjectEmpMain record);


    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    List<ZxSfProjectEmpMain> getZxSfProEmpMainJuInfo();

    ZxSfProjectEmpMain getZxSfProEmpMainComInfo(ZxSfProjectEmpMain record);

    ZxSfProjectEmpMain getJuInfo (ZxSfProjectEmpMain record);

    ZxSfProjectEmpMain getZxSfProEmpMainGuiDang(@Param("record") ZxSfProjectEmpMain record, @Param("today")String today);

    ZxSfProjectEmpMain getZxSfProEmpMainJiaoGong(@Param("record") ZxSfProjectEmpMain record, @Param("today")String today);

    ZxSfProjectEmpMain getZxSfProEmpMainWanGong(@Param("record") ZxSfProjectEmpMain record, @Param("today")String today);

    ZxSfProjectEmpMain getZxSfProEmpMainKaiGong(@Param("record") ZxSfProjectEmpMain record, @Param("today")String today);

    List<ZxSfProjectEmpMain> getZxSfProEmpMainGuiDangList(@Param("record") ZxSfProjectEmpMain record, @Param("today")String today);

    List<ZxSfProjectEmpMain> getZxSfProEmpMainJiaoGongList(@Param("record") ZxSfProjectEmpMain record, @Param("today")String today);

    List<ZxSfProjectEmpMain> getZxSfProEmpMainWanGongList(@Param("record") ZxSfProjectEmpMain record, @Param("today")String today);

    List<ZxSfProjectEmpMain> getZxSfProEmpMainKaiGongList(@Param("record") ZxSfProjectEmpMain record, @Param("today")String today);


    List<ZxSfProjectEmpMain> getZhiChengComList();

    ZxSfProjectEmpMain getZhiChengJuInfo (ZxSfProjectEmpMain record);

    ZxSfProjectEmpMain getZhiChengComInfo(ZxSfProjectEmpMain record);

    ZxSfProjectEmpMain getZhiChengGuiDang(@Param("record") ZxSfProjectEmpMain record, @Param("today")String today);

    ZxSfProjectEmpMain getZhiChengJiaoGong(@Param("record") ZxSfProjectEmpMain record, @Param("today")String today);

    ZxSfProjectEmpMain getZhiChengWanGong(@Param("record") ZxSfProjectEmpMain record, @Param("today")String today);

    ZxSfProjectEmpMain getZhiChengKaiGong(@Param("record") ZxSfProjectEmpMain record, @Param("today")String today);

    List<ZxSfProjectEmpMain> getZhiChengGuiDangList(@Param("record") ZxSfProjectEmpMain record, @Param("today")String today);

    List<ZxSfProjectEmpMain> getZhiChengJiaoGongList(@Param("record") ZxSfProjectEmpMain record, @Param("today")String today);

    List<ZxSfProjectEmpMain> getZhiChengWanGongList(@Param("record") ZxSfProjectEmpMain record, @Param("today")String today);

    List<ZxSfProjectEmpMain> getZhiChengKaiGongList(@Param("record") ZxSfProjectEmpMain record, @Param("today")String today);


    List<ZxSfProjectEmpMain> getWorkAgeComList();

    ZxSfProjectEmpMain getWorkAgeComInfo(ZxSfProjectEmpMain record);

    ZxSfProjectEmpMain getWorkAgeJuInfo(ZxSfProjectEmpMain record);

    ZxSfProjectEmpMain getWorkAgeGuiDang(@Param("record") ZxSfProjectEmpMain record, @Param("today")String today);

    ZxSfProjectEmpMain getWorkAgeJiaoGong(@Param("record") ZxSfProjectEmpMain record, @Param("today")String today);

    ZxSfProjectEmpMain getWorkAgeWanGong(@Param("record") ZxSfProjectEmpMain record, @Param("today")String today);

    ZxSfProjectEmpMain getWorkAgeKaiGong(@Param("record") ZxSfProjectEmpMain record, @Param("today")String today);

    List<ZxSfProjectEmpMain> getWorkAgeGuiDangList(@Param("record") ZxSfProjectEmpMain record, @Param("today")String today);

    List<ZxSfProjectEmpMain> getWorkAgeJiaoGongList(@Param("record") ZxSfProjectEmpMain record, @Param("today")String today);

    List<ZxSfProjectEmpMain> getWorkAgeWanGongList(@Param("record") ZxSfProjectEmpMain record, @Param("today")String today);

    List<ZxSfProjectEmpMain> getWorkAgeKaiGongList(@Param("record") ZxSfProjectEmpMain record, @Param("today")String today);





}
