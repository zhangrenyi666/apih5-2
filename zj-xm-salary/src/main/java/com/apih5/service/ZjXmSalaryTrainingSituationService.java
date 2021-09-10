package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjXmSalaryTrainingSituation;

public interface ZjXmSalaryTrainingSituationService {

    public ResponseEntity getZjXmSalaryTrainingSituationListByCondition(ZjXmSalaryTrainingSituation zjXmSalaryTrainingSituation);

    public ResponseEntity getZjXmSalaryTrainingSituationDetails(ZjXmSalaryTrainingSituation zjXmSalaryTrainingSituation);

    public ResponseEntity saveZjXmSalaryTrainingSituation(ZjXmSalaryTrainingSituation zjXmSalaryTrainingSituation);

    public ResponseEntity updateZjXmSalaryTrainingSituation(ZjXmSalaryTrainingSituation zjXmSalaryTrainingSituation);

    public ResponseEntity batchDeleteUpdateZjXmSalaryTrainingSituation(List<ZjXmSalaryTrainingSituation> zjXmSalaryTrainingSituationList);

}

