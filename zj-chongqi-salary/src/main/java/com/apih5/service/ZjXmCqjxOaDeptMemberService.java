package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmCqjxOaDeptMember;

public interface ZjXmCqjxOaDeptMemberService {

    public ResponseEntity getZjXmCqjxOaDeptMemberListByCondition(ZjXmCqjxOaDeptMember zjXmCqjxOaDeptMember);

    public ResponseEntity getZjXmCqjxOaDeptMemberDetails(ZjXmCqjxOaDeptMember zjXmCqjxOaDeptMember);

    public ResponseEntity saveZjXmCqjxOaDeptMember(ZjXmCqjxOaDeptMember zjXmCqjxOaDeptMember);

    public ResponseEntity updateZjXmCqjxOaDeptMember(ZjXmCqjxOaDeptMember zjXmCqjxOaDeptMember);

    public ResponseEntity batchDeleteUpdateZjXmCqjxOaDeptMember(List<ZjXmCqjxOaDeptMember> zjXmCqjxOaDeptMemberList);

}

