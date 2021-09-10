package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfEdu;
import org.apache.ibatis.annotations.Param;

public interface ZxSfEduMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfEdu record);

    int insertSelective(ZxSfEdu record);

    ZxSfEdu selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfEdu record);

    int updateByPrimaryKey(ZxSfEdu record);

    List<ZxSfEdu> selectByZxSfEduList(ZxSfEdu record);

    int batchDeleteUpdateZxSfEdu(List<ZxSfEdu> recordList, ZxSfEdu record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    List<ZxSfEdu> getZxSfEduComList();

    ZxSfEdu getZxSfEduCom(ZxSfEdu record);

    List<ZxSfEdu> getZxSfEduGuiDangList(@Param("record") ZxSfEdu record, @Param("today") String today);

    List<ZxSfEdu> getZxSfEduJiaoGongList(@Param("record") ZxSfEdu record, @Param("today") String today);

    List<ZxSfEdu> getZxSfEduWanGongList(@Param("record") ZxSfEdu record, @Param("today") String today);

    List<ZxSfEdu> getZxSfEduKaiGongList(@Param("record") ZxSfEdu record, @Param("today") String today);

    ZxSfEdu getZxSfEduGuiDang(@Param("record") ZxSfEdu record, @Param("today") String today);

    ZxSfEdu getZxSfEduJiaoGong(@Param("record") ZxSfEdu record, @Param("today") String today);

    ZxSfEdu getZxSfEduWanGong(@Param("record") ZxSfEdu record, @Param("today") String today);

    ZxSfEdu getZxSfEduKaiGong(@Param("record") ZxSfEdu record, @Param("today") String today);

    ZxSfEdu getZxSfEduJuInfo(ZxSfEdu record);

}
