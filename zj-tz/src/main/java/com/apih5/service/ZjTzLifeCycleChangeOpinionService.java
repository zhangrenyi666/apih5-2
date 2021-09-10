package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzLifeCycleChangeOpinion;

public interface ZjTzLifeCycleChangeOpinionService {

    public ResponseEntity getZjTzLifeCycleChangeOpinionListByCondition(ZjTzLifeCycleChangeOpinion zjTzLifeCycleChangeOpinion);

    public ResponseEntity getZjTzLifeCycleChangeOpinionDetails(ZjTzLifeCycleChangeOpinion zjTzLifeCycleChangeOpinion);

    public ResponseEntity saveZjTzLifeCycleChangeOpinion(ZjTzLifeCycleChangeOpinion zjTzLifeCycleChangeOpinion);

    public ResponseEntity updateZjTzLifeCycleChangeOpinion(ZjTzLifeCycleChangeOpinion zjTzLifeCycleChangeOpinion);

    public ResponseEntity batchDeleteUpdateZjTzLifeCycleChangeOpinion(List<ZjTzLifeCycleChangeOpinion> zjTzLifeCycleChangeOpinionList);

	public ResponseEntity getZjTzLifeCycleChangeOpinionAndProIdList(ZjTzLifeCycleChangeOpinion zjTzLifeCycleChangeOpinion);

}

