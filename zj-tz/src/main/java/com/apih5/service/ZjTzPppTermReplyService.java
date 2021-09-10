package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzPppTermReply;

public interface ZjTzPppTermReplyService {

    public ResponseEntity getZjTzPppTermReplyListByCondition(ZjTzPppTermReply zjTzPppTermReply);

    public ResponseEntity getZjTzPppTermReplyDetails(ZjTzPppTermReply zjTzPppTermReply);

    public ResponseEntity saveZjTzPppTermReply(ZjTzPppTermReply zjTzPppTermReply);

    public ResponseEntity updateZjTzPppTermReply(ZjTzPppTermReply zjTzPppTermReply);

    public ResponseEntity batchDeleteUpdateZjTzPppTermReply(List<ZjTzPppTermReply> zjTzPppTermReplyList);

	public List<ZjTzPppTermReply> ureportZjTzPppTermReplyList(ZjTzPppTermReply zjTzPppTermReply);

}

