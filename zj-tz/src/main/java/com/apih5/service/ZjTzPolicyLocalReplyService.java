package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzPolicyLocalReply;

public interface ZjTzPolicyLocalReplyService {

    public ResponseEntity getZjTzPolicyLocalReplyListByCondition(ZjTzPolicyLocalReply zjTzPolicyLocalReply);

    public ResponseEntity getZjTzPolicyLocalReplyDetails(ZjTzPolicyLocalReply zjTzPolicyLocalReply);

    public ResponseEntity saveZjTzPolicyLocalReply(ZjTzPolicyLocalReply zjTzPolicyLocalReply);

    public ResponseEntity updateZjTzPolicyLocalReply(ZjTzPolicyLocalReply zjTzPolicyLocalReply);

    public ResponseEntity batchDeleteUpdateZjTzPolicyLocalReply(List<ZjTzPolicyLocalReply> zjTzPolicyLocalReplyList);

}

