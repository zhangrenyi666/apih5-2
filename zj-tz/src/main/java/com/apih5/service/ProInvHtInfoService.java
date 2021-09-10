package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ProInvHtInfo;

public interface ProInvHtInfoService {

    public ResponseEntity getProInvHtInfoListByCondition(ProInvHtInfo proInvHtInfo);

    public ResponseEntity getProInvHtInfoDetails(ProInvHtInfo proInvHtInfo);

    public ResponseEntity saveProInvHtInfo(ProInvHtInfo proInvHtInfo);

    public ResponseEntity updateProInvHtInfo(ProInvHtInfo proInvHtInfo);

    public ResponseEntity batchDeleteUpdateProInvHtInfo(List<ProInvHtInfo> proInvHtInfoList);

}

