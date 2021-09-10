package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzVideo;

public interface ZjTzVideoService {

    public ResponseEntity getZjTzVideoListByCondition(ZjTzVideo zjTzVideo);

    public ResponseEntity getZjTzVideoDetails(ZjTzVideo zjTzVideo);

    public ResponseEntity saveZjTzVideo(ZjTzVideo zjTzVideo);

    public ResponseEntity updateZjTzVideo(ZjTzVideo zjTzVideo);

    public ResponseEntity batchDeleteUpdateZjTzVideo(List<ZjTzVideo> zjTzVideoList);

	public ResponseEntity toHomeShowZjTzVideo(ZjTzVideo zjTzVideo);

	public ResponseEntity batchApproveAgreeZjTzVideo(List<ZjTzVideo> zjTzVideoList);

	public ResponseEntity batchApproveRejectZjTzVideo(List<ZjTzVideo> zjTzVideoList);

	public ResponseEntity batchDeleteReleaseZjTzVideo(List<ZjTzVideo> zjTzVideoList);

	public ResponseEntity batchDeleteRecallZjTzVideo(List<ZjTzVideo> zjTzVideoList);

	public ResponseEntity getZjTzVideoHome(ZjTzVideo zjTzVideo);

}

