package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzPolicyCountryReply;

public interface ZjTzPolicyCountryReplyService {

    public ResponseEntity getZjTzPolicyCountryReplyListByCondition(ZjTzPolicyCountryReply zjTzPolicyCountryReply);

    public ResponseEntity getZjTzPolicyCountryReplyDetails(ZjTzPolicyCountryReply zjTzPolicyCountryReply);

    public ResponseEntity saveZjTzPolicyCountryReply(ZjTzPolicyCountryReply zjTzPolicyCountryReply);

    public ResponseEntity updateZjTzPolicyCountryReply(ZjTzPolicyCountryReply zjTzPolicyCountryReply);

    public ResponseEntity batchDeleteUpdateZjTzPolicyCountryReply(List<ZjTzPolicyCountryReply> zjTzPolicyCountryReplyList);

}

