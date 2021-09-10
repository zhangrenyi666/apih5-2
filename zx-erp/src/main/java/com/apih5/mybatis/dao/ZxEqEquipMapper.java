package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxEqEquip;

public interface ZxEqEquipMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxEqEquip record);

    int insertSelective(ZxEqEquip record);

    ZxEqEquip selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxEqEquip record);

    int updateByPrimaryKey(ZxEqEquip record);

    List<ZxEqEquip> selectByZxEqEquipList(ZxEqEquip record);

    int batchDeleteUpdateZxEqEquip(List<ZxEqEquip> recordList, ZxEqEquip record);

	List<ZxEqEquip> getZxEqEquipListForReport(ZxEqEquip zxEqEquip);
	
	List<ZxEqEquip> getZxEqEquipListForReportIdle(ZxEqEquip zxEqEquip);

    List<ZxEqEquip> getZxEqEquipListVehicleForReport(ZxEqEquip zxEqEquip);

    List<ZxEqEquip> getZxEqEquipListMechanicsForReport(ZxEqEquip zxEqEquip);

    List<ZxEqEquip> ziYouCLForm(ZxEqEquip zxEqEquip);

	List<ZxEqEquip> ureportAddZxEqEquipTotalList(ZxEqEquip zxEqEquip);
}

