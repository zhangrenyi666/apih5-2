package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.BaseFlowSponsorChooser;

public interface BaseFlowSponsorChooserMapper {
    int deleteByPrimaryKey(String key);

    int insert(BaseFlowSponsorChooser record);

    int insertSelective(BaseFlowSponsorChooser record);

    BaseFlowSponsorChooser selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(BaseFlowSponsorChooser record);

    int updateByPrimaryKey(BaseFlowSponsorChooser record);

    List<BaseFlowSponsorChooser> selectByBaseFlowSponsorChooserList(BaseFlowSponsorChooser record);

    int batchDeleteUpdateBaseFlowSponsorChooser(List<BaseFlowSponsorChooser> recordList);
    
    List<BaseFlowSponsorChooser> selectBySponsorChooserList(BaseFlowSponsorChooser record);

}

