package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzAerialVideo;

public interface ZjTzAerialVideoService {

    public ResponseEntity getZjTzAerialVideoListByCondition(ZjTzAerialVideo zjTzAerialVideo);

    public ResponseEntity getZjTzAerialVideoDetails(ZjTzAerialVideo zjTzAerialVideo);

    public ResponseEntity saveZjTzAerialVideo(ZjTzAerialVideo zjTzAerialVideo);

    public ResponseEntity updateZjTzAerialVideo(ZjTzAerialVideo zjTzAerialVideo);

    public ResponseEntity batchDeleteUpdateZjTzAerialVideo(List<ZjTzAerialVideo> zjTzAerialVideoList);

}

