package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZjTzProfit;

public interface ZjTzProfitService {

    public ResponseEntity getZjTzProfitListByCondition(ZjTzProfit zjTzProfit);

    public ResponseEntity getZjTzProfitDetails(ZjTzProfit zjTzProfit);

    public ResponseEntity saveZjTzProfit(ZjTzProfit zjTzProfit);

    public ResponseEntity updateZjTzProfit(ZjTzProfit zjTzProfit);

    public ResponseEntity batchDeleteUpdateZjTzProfit(List<ZjTzProfit> zjTzProfitList);

}

