package com.apih5.mybatis.dao;

import java.util.List;

import com.apih5.mybatis.pojo.CheckSfCheck;
import com.apih5.mybatis.pojo.ZxSfCheck;
import org.apache.ibatis.annotations.Param;

public interface ZxSfCheckMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfCheck record);

    int insertSelective(ZxSfCheck record);

    ZxSfCheck selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfCheck record);

    int updateByPrimaryKey(ZxSfCheck record);

    List<ZxSfCheck> selectByZxSfCheckList(ZxSfCheck record);

    int batchDeleteUpdateZxSfCheck(List<ZxSfCheck> recordList, ZxSfCheck record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxSfCheck> selectByZxSfCheckListAll(ZxSfCheck record);
    
    List<ZxSfCheck> selectByZxSfCheckListAllcompany(ZxSfCheck record);
    
    List<ZxSfCheck> selectByZxSfCheckListAllBureau(ZxSfCheck record);

    /**
     * 查询公司检查信息
     * @author suncg
     * @param record
     * */
    ZxSfCheck getCompany(ZxSfCheck record);


    /**
     * 查询归档项目信息
     * @author suncg
     * @param record,today
     * */
    ZxSfCheck getGuiDang(@Param(value = "record") ZxSfCheck record, @Param(value ="today") String today );


    /**
     * 查询交工项目信息
     * @author suncg
     * @param record,today
     * */
    ZxSfCheck getJiaoGong(@Param(value ="record") ZxSfCheck record,@Param("today") String today );

    /**
     * 查询完工项目信息
     * @author suncg
     * @param record,today
     * */
    ZxSfCheck getWanGong(@Param(value ="record") ZxSfCheck record,@Param("today") String today );

    /**
     * 查询开工项目信息
     * @author suncg
     * @param record,today
     * */
    ZxSfCheck getKaiGong(@Param(value ="record") ZxSfCheck record,@Param("today") String today);


    /**
     * 查询归档项目列表
     * @author suncg
     * @param record,today
     * */
    List<ZxSfCheck> getGuiDangList(@Param(value ="record") ZxSfCheck record,@Param("today") String today);

    /**
     * 查询交工项目列表
     * @author suncg
     * @param record,today
     * */
    List<ZxSfCheck> getJiaoGongList(@Param(value ="record") ZxSfCheck record,@Param("today") String today);

    /**
     * 查询完工项目列表
     * @author suncg
     * @param record,today
     * */
    List<ZxSfCheck> getWanGongList(@Param(value ="record") ZxSfCheck record,@Param("today") String today);

    /**
     * 查询开工项目列表
     * @author suncg
     * @param record,today
     * */
    List<ZxSfCheck> getKaiGongList(@Param(value = "record") ZxSfCheck record,@Param("today") String today);

    /**
     * 查询公司列表
     * @author suncg
     * */
    List<ZxSfCheck> getComList(@Param(value = "record") ZxSfCheck record);


    /**
     * 查询局信息
     * @author suncg
     * */
    ZxSfCheck getJuInfo(@Param(value = "record") ZxSfCheck record);


    /**
     * 项目安全检查报表导出
     * @author suncg
     * @param record
     * */
    List<ZxSfCheck> selectByZxSfCheckId(ZxSfCheck record);
}
