package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxProjectOaDeptMember;

public interface ZjXmCqjxProjectOaDeptMemberService {

    public ResponseEntity getZjXmCqjxProjectOaDeptMemberListByCondition(ZjXmCqjxProjectOaDeptMember zjXmCqjxProjectOaDeptMember);

    public ResponseEntity getZjXmCqjxProjectOaDeptMemberDetails(ZjXmCqjxProjectOaDeptMember zjXmCqjxProjectOaDeptMember);

    public ResponseEntity saveZjXmCqjxProjectOaDeptMember(ZjXmCqjxProjectOaDeptMember zjXmCqjxProjectOaDeptMember);

    public ResponseEntity updateZjXmCqjxProjectOaDeptMember(ZjXmCqjxProjectOaDeptMember zjXmCqjxProjectOaDeptMember);

    public ResponseEntity batchDeleteUpdateZjXmCqjxProjectOaDeptMember(List<ZjXmCqjxProjectOaDeptMember> zjXmCqjxProjectOaDeptMemberList);

}

