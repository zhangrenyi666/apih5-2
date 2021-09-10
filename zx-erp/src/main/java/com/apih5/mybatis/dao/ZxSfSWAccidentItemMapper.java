package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxSfSWAccidentItem;

public interface ZxSfSWAccidentItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxSfSWAccidentItem record);

    int insertSelective(ZxSfSWAccidentItem record);

    ZxSfSWAccidentItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxSfSWAccidentItem record);

    int updateByPrimaryKey(ZxSfSWAccidentItem record);

    List<ZxSfSWAccidentItem> selectByZxSfSWAccidentItemList(ZxSfSWAccidentItem record);

    int batchDeleteUpdateZxSfSWAccidentItem(List<ZxSfSWAccidentItem> recordList, ZxSfSWAccidentItem record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    List<ZxSfSWAccidentItem> uReportForm(ZxSfSWAccidentItem record);

    List<ZxSfSWAccidentItem> uReportFormCom(ZxSfSWAccidentItem record);

}
