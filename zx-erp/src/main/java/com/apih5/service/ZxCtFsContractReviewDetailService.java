package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtFsContractReviewDetail;

public interface ZxCtFsContractReviewDetailService {

    public ResponseEntity getZxCtFsContractReviewDetailListByCondition(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail);

    public ResponseEntity getZxCtFsContractReviewDetailDetail(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail);

    public ResponseEntity saveZxCtFsContractReviewDetail(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail);

    public ResponseEntity updateZxCtFsContractReviewDetail(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail);

    public ResponseEntity batchDeleteUpdateZxCtFsContractReviewDetail(List<ZxCtFsContractReviewDetail> zxCtFsContractReviewDetailList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    /**
     * @author suncg
     * @param zxCtFsContractReviewDetail
     * 导入excel数据
     * */
    public ResponseEntity importExcel(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail);

    public ResponseEntity getZxCtFsContractReviewDetailList(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    /**
     * 根据附属合同ID查询评审清单
     * @author suncg
     * @param zxCtFsContractReviewDetail
     * 导入excel数据
     * */
    public ResponseEntity getReviewDetailList(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail);

    /**
     * 根据合同ID查询所有清单
     * @author suncg
     *  @param zxCtFsContractReviewDetail
     * */
    public ResponseEntity getAllReviewDetailListByContract(ZxCtFsContractReviewDetail zxCtFsContractReviewDetail);
}
