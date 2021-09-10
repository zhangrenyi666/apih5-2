package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzPppTerm;

public interface ZjTzPppTermService {

    public ResponseEntity getZjTzPppTermListByCondition(ZjTzPppTerm zjTzPppTerm);

    public ResponseEntity getZjTzPppTermDetails(ZjTzPppTerm zjTzPppTerm);

    public ResponseEntity saveZjTzPppTerm(ZjTzPppTerm zjTzPppTerm);

    public ResponseEntity updateZjTzPppTerm(ZjTzPppTerm zjTzPppTerm);

    public ResponseEntity batchDeleteUpdateZjTzPppTerm(List<ZjTzPppTerm> zjTzPppTermList);

	public ResponseEntity saveZjTzPppTermAllReply(ZjTzPppTerm zjTzPppTerm);

	public ResponseEntity batchReleaseZjTzPppTerm(List<ZjTzPppTerm> zjTzPppTermList);

	public ResponseEntity batchRecallZjTzPppTerm(List<ZjTzPppTerm> zjTzPppTermList);

}

