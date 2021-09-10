package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZxCrCustomerExtAttr;

public interface ZxCrCustomerExtAttrMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZxCrCustomerExtAttr record);

    int insertSelective(ZxCrCustomerExtAttr record);

    ZxCrCustomerExtAttr selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZxCrCustomerExtAttr record);

    int updateByPrimaryKey(ZxCrCustomerExtAttr record);

    List<ZxCrCustomerExtAttr> selectByZxCrCustomerExtAttrList(ZxCrCustomerExtAttr record);

    int batchDeleteUpdateZxCrCustomerExtAttr(List<ZxCrCustomerExtAttr> recordList, ZxCrCustomerExtAttr record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    
    List<ZxCrCustomerExtAttr> selectByPrimaryKeyall(ZxCrCustomerExtAttr recordall);
    
    List<ZxCrCustomerExtAttr> selectAll(ZxCrCustomerExtAttr recordall);
    
    List<ZxCrCustomerExtAttr> selectByZxCrCustomerExtAttrListAll(ZxCrCustomerExtAttr record);
    
//    List<ZxCrCustomerExtAttr> selectByZxCtSuppliesSecondUnitAllList(ZxCrCustomerExtAttr record);//获取物资乙方数据List
    
    List<ZxCrCustomerExtAttr> selectByZxCrCustomerExtAttrEngineeringList(ZxCrCustomerExtAttr record);
}
