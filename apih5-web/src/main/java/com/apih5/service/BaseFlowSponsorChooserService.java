package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.BaseFlowSponsorChooser;

public interface BaseFlowSponsorChooserService {

    public ResponseEntity getBaseFlowSponsorChooserListByCondition(BaseFlowSponsorChooser baseFlowSponsorChooser);

    public ResponseEntity saveBaseFlowSponsorChooser(BaseFlowSponsorChooser baseFlowSponsorChooser);

    public ResponseEntity updateBaseFlowSponsorChooser(BaseFlowSponsorChooser baseFlowSponsorChooser);

    public ResponseEntity batchDeleteUpdateBaseFlowSponsorChooser(List<BaseFlowSponsorChooser> baseFlowSponsorChooserList);
    
    public ResponseEntity addBaseFlowSponsorChooserByList(BaseFlowSponsorChooser baseFlowSponsorChooser);
    
    public ResponseEntity updateBaseFlowSponsorChooserByList(BaseFlowSponsorChooser baseFlowSponsorChooser);
    
}

