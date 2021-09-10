package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ProInvBasicPfinfo;

public interface ProInvBasicPfinfoService {

    public ResponseEntity getProInvBasicPfinfoListByCondition(ProInvBasicPfinfo proInvBasicPfinfo);

    public ResponseEntity getProInvBasicPfinfoDetails(ProInvBasicPfinfo proInvBasicPfinfo);

    public ResponseEntity saveProInvBasicPfinfo(ProInvBasicPfinfo proInvBasicPfinfo);

    public ResponseEntity updateProInvBasicPfinfo(ProInvBasicPfinfo proInvBasicPfinfo);

    public ResponseEntity batchDeleteUpdateProInvBasicPfinfo(List<ProInvBasicPfinfo> proInvBasicPfinfoList);

}

