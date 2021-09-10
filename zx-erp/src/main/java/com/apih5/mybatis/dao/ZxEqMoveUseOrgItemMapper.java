package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqMoveUseOrgItem;

public interface ZxEqMoveUseOrgItemMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqMoveUseOrgItem record);

    int insertSelective(ZxEqMoveUseOrgItem record);

    ZxEqMoveUseOrgItem selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqMoveUseOrgItem record);

    int updateByPrimaryKey(ZxEqMoveUseOrgItem record);

    List<ZxEqMoveUseOrgItem> selectByZxEqMoveUseOrgItemList(ZxEqMoveUseOrgItem record);

    int batchDeleteUpdateZxEqMoveUseOrgItem(List<ZxEqMoveUseOrgItem> recordList, ZxEqMoveUseOrgItem record);

	List<ZxEqMoveUseOrgItem> getZxEqMoveUseOrgItemListForTab(ZxEqMoveUseOrgItem zxEqMoveUseOrgItem);

	List<ZxEqMoveUseOrgItem> ureportZxEqMoveUseOrgItemList(ZxEqMoveUseOrgItem zxEqMoveUseOrgItem);

}

