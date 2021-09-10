package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCtWorks;

public interface ZxCtWorksMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCtWorks record);

    int insertSelective(ZxCtWorks record);

    ZxCtWorks selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCtWorks record);

    int updateByPrimaryKey(ZxCtWorks record);

    List<ZxCtWorks> selectByZxCtWorksList(ZxCtWorks record);

    int batchDeleteUpdateZxCtWorks(List<ZxCtWorks> recordList, ZxCtWorks record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    List<ZxCtWorks> getZxCtWorksTreeList(ZxCtWorks zxCtWorks);

	List<ZxCtWorks> getZxCtWorksBalanceList(ZxCtWorks zxCtWorks);

	List<ZxCtWorks> findWorksByParentID(ZxCtWorks zxCtWorks);

	List<ZxCtWorks> getEqWorksBalance(ZxCtWorks zxCtWorks);

	List<ZxCtWorks> getLastWorksBalance(ZxCtWorks zxCtWorks);

	List<ZxCtWorks> findSonWorksByParentIDs(ZxCtWorks zxCtWorks);

	int updateIsLeafByPrimaryKey(ZxCtWorks zxCtWorks);

	List<ZxCtWorks> getZxCtWorksBalanceEditList(ZxCtWorks zxCtWorks);

	ZxCtWorks getMaxTreeNode(ZxCtWorks zxCtWorks);

	int deleteByWorkBookID(ZxCtWorks zxCtWorks);

	int updateIsLeaf(ZxCtWorks isLeafZxCtWorks);

	int deleteAllByTreeNode(ZxCtWorks zxCtWorks);

	int updateWorksByPrimaryKey(ZxCtWorks zxCtWorks);

	int delZxCtWorksByOrgIDWorkBookID(ZxCtWorks zxCtWorks);

	int deleteAllChildren(List<ZxCtWorks> children);

	List<ZxCtWorks> selectAlredyHaveWorks(ZxCtWorks zxCtWorks, List<ZxCtWorks> children);

}
