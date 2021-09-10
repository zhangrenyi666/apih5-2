package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.CheckSfCheck;
import org.apache.ibatis.annotations.Param;

public interface CheckSfCheckMapper {
    int deleteByPrimaryKey(String key);

    int insert(CheckSfCheck record);

    int insertSelective(CheckSfCheck record);

    CheckSfCheck selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(CheckSfCheck record);

    int updateByPrimaryKey(CheckSfCheck record);

    List<CheckSfCheck> selectByCheckSfCheckList(CheckSfCheck record);

    int batchDeleteUpdateCheckSfCheck(List<CheckSfCheck> recordList, CheckSfCheck record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    /**
     * 查询公司检查信息
     * @author suncg
     * @param record
     * */
    CheckSfCheck getCompany(CheckSfCheck record);


    /**
     * 查询归档项目信息
     * @author suncg
     * @param record,today
     * */
    CheckSfCheck getGuiDang(@Param("record") CheckSfCheck record,@Param("today") String today );


    /**
     * 查询交工项目信息
     * @author suncg
     * @param record,today
     * */
    CheckSfCheck getJiaoGong(@Param("record") CheckSfCheck record,@Param("today") String today );

    /**
     * 查询完工项目信息
     * @author suncg
     * @param record,today
     * */
    CheckSfCheck getWanGong(@Param("record") CheckSfCheck record,@Param("today") String today );

    /**
     * 查询开工项目信息
     * @author suncg
     * @param record,today
     * */
    CheckSfCheck getKaiGong(@Param("record") CheckSfCheck record,@Param("today") String today);


    /**
     * 查询归档项目列表
     * @author suncg
     * @param record,today
     * */
    List<CheckSfCheck> getGuiDangList(@Param("record") CheckSfCheck record,@Param("today") String today);

    /**
     * 查询交工项目列表
     * @author suncg
     * @param record,today
     * */
    List<CheckSfCheck> getJiaoGongList(@Param("record") CheckSfCheck record,@Param("today") String today);

    /**
     * 查询完工项目列表
     * @author suncg
     * @param record,today
     * */
    List<CheckSfCheck> getWanGongList(@Param("record") CheckSfCheck record,@Param("today") String today);

    /**
     * 查询开工项目列表
     * @author suncg
     * @param record,today
     * */
    List<CheckSfCheck> getKaiGongList(@Param("record") CheckSfCheck record,@Param("today") String today);

    /**
     * 查询公司列表
     * @author suncg
     * */
    List<CheckSfCheck> getComList(@Param("record") CheckSfCheck record);

}
