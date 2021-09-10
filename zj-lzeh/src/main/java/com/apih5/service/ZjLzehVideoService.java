package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjLzehVideo;

public interface ZjLzehVideoService {

    public ResponseEntity getZjLzehVideoListByCondition(ZjLzehVideo zjLzehVideo);

    public ResponseEntity getZjLzehVideoDetails(ZjLzehVideo zjLzehVideo);

    public ResponseEntity saveZjLzehVideo(ZjLzehVideo zjLzehVideo);

    public ResponseEntity updateZjLzehVideo(ZjLzehVideo zjLzehVideo);

    public ResponseEntity batchDeleteUpdateZjLzehVideo(List<ZjLzehVideo> zjLzehVideoList);

    public ResponseEntity getZjLzehVideo(ZjLzehVideo zjLzehVideo);

    public ResponseEntity updateZjLzehVideoValue(ZjLzehVideo zjLzehVideo);

}

