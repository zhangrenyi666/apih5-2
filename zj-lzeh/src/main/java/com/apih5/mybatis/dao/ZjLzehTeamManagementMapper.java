package com.apih5.mybatis.dao;

import java.util.List;
import com.apih5.mybatis.pojo.ZjLzehTeamManagement;

public interface ZjLzehTeamManagementMapper {
    int deleteByPrimaryKey(String key);

    int insert(ZjLzehTeamManagement record);

    int insertSelective(ZjLzehTeamManagement record);

    ZjLzehTeamManagement selectByPrimaryKey(String key);

    int updateByPrimaryKeySelective(ZjLzehTeamManagement record);

    int updateByPrimaryKey(ZjLzehTeamManagement record);

    List<ZjLzehTeamManagement> selectByZjLzehTeamManagementList(ZjLzehTeamManagement record);

    int batchDeleteUpdateZjLzehTeamManagement(List<ZjLzehTeamManagement> recordList, ZjLzehTeamManagement record);

    // ↓↓↓----扩展-（命名格式：select查询；update更新；sum统计；count数量）----↓↓↓
    ZjLzehTeamManagement getCount();
}
