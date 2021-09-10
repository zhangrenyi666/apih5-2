package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ProInvLrsxqk;

public interface ProInvLrsxqkService {

    public ResponseEntity getProInvLrsxqkListByCondition(ProInvLrsxqk proInvLrsxqk);

    public ResponseEntity getProInvLrsxqkDetails(ProInvLrsxqk proInvLrsxqk);

    public ResponseEntity saveProInvLrsxqk(ProInvLrsxqk proInvLrsxqk);

    public ResponseEntity updateProInvLrsxqk(ProInvLrsxqk proInvLrsxqk);

    public ResponseEntity batchDeleteUpdateProInvLrsxqk(List<ProInvLrsxqk> proInvLrsxqkList);

}

