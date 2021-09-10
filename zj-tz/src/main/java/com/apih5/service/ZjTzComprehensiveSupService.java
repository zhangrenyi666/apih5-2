package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzComprehensiveSup;

public interface ZjTzComprehensiveSupService {

    public ResponseEntity getZjTzComprehensiveSupListByCondition(ZjTzComprehensiveSup zjTzComprehensiveSup);

    public ResponseEntity getZjTzComprehensiveSupDetails(ZjTzComprehensiveSup zjTzComprehensiveSup);

    public ResponseEntity saveZjTzComprehensiveSup(ZjTzComprehensiveSup zjTzComprehensiveSup);

    public ResponseEntity updateZjTzComprehensiveSup(ZjTzComprehensiveSup zjTzComprehensiveSup);

    public ResponseEntity batchDeleteUpdateZjTzComprehensiveSup(List<ZjTzComprehensiveSup> zjTzComprehensiveSupList);

	public ResponseEntity batchRecallZjTzComprehensiveSup(List<ZjTzComprehensiveSup> zjTzComprehensiveSupList);

	public ResponseEntity correctiveZjTzComprehensiveSup(ZjTzComprehensiveSup zjTzComprehensiveSup);

	public ResponseEntity getZjTzComprehensiveSupReplyList(ZjTzComprehensiveSup zjTzComprehensiveSup);

	public ResponseEntity batchExportZjTzComprehensiveSupFile(List<ZjTzComprehensiveSup> zjTzComprehensiveSupList);

	public ResponseEntity replyZjTzComprehensiveSup(ZjTzComprehensiveSup zjTzComprehensiveSup);

}

