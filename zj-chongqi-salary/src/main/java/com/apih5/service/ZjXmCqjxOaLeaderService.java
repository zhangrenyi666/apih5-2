package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxOaLeader;

public interface ZjXmCqjxOaLeaderService {

    public ResponseEntity getZjXmCqjxOaLeaderListByCondition(ZjXmCqjxOaLeader zjXmCqjxOaLeader);

    public ResponseEntity getZjXmCqjxOaLeaderDetails(ZjXmCqjxOaLeader zjXmCqjxOaLeader);

    public ResponseEntity saveZjXmCqjxOaLeader(ZjXmCqjxOaLeader zjXmCqjxOaLeader);

    public ResponseEntity updateZjXmCqjxOaLeader(ZjXmCqjxOaLeader zjXmCqjxOaLeader);

    public ResponseEntity batchDeleteUpdateZjXmCqjxOaLeader(List<ZjXmCqjxOaLeader> zjXmCqjxOaLeaderList);

}

