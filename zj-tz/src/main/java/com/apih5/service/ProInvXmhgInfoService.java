package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ProInvXmhgInfo;

public interface ProInvXmhgInfoService {

    public ResponseEntity getProInvXmhgInfoListByCondition(ProInvXmhgInfo proInvXmhgInfo);

    public ResponseEntity getProInvXmhgInfoDetails(ProInvXmhgInfo proInvXmhgInfo);

    public ResponseEntity saveProInvXmhgInfo(ProInvXmhgInfo proInvXmhgInfo);

    public ResponseEntity updateProInvXmhgInfo(ProInvXmhgInfo proInvXmhgInfo);

    public ResponseEntity batchDeleteUpdateProInvXmhgInfo(List<ProInvXmhgInfo> proInvXmhgInfoList);

}

