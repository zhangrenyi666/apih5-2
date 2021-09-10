package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjLzehTempTaskManage;

public interface ZjLzehTempTaskManageMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjLzehTempTaskManage record);

    int insertSelective(ZjLzehTempTaskManage record);

    ZjLzehTempTaskManage selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjLzehTempTaskManage record);

    int updateByPrimaryKey(ZjLzehTempTaskManage record);

    List<ZjLzehTempTaskManage> selectByZjLzehTempTaskManageList(ZjLzehTempTaskManage record);

    int batchDeleteUpdateZjLzehTempTaskManage(List<ZjLzehTempTaskManage> recordList, ZjLzehTempTaskManage record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓

    /**
     * 按人员查看临时任务
     * @author suncg
     * @param record
     * */
    List<ZjLzehTempTaskManage> selectAllByPerson(ZjLzehTempTaskManage record);

    /**
     * 查询所有任务执行人员信息
     * @author suncg
     * @param record
     * */
    List<ZjLzehTempTaskManage> selectAllPersonInfo(ZjLzehTempTaskManage record);


    /**
     * 查询树根节点
     * @author suncg
     * @param record
     * */
    List<ZjLzehTempTaskManage> selectByZjLzehTempTreeRootList(ZjLzehTempTaskManage record);

    List<ZjLzehTempTaskManage> selectByZjLzehTempJsRootList(ZjLzehTempTaskManage record);

    List<ZjLzehTempTaskManage> selectZjLzehTempTaskManageListByPersonMonth(ZjLzehTempTaskManage record);

    List<ZjLzehTempTaskManage> selectTempTaskManageListByPerson(ZjLzehTempTaskManage record);

}
