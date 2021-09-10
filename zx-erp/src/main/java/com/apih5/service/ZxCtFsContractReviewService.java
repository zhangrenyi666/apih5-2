package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtFsContractReview;

import javax.servlet.http.HttpServletResponse;

public interface ZxCtFsContractReviewService {

    public ResponseEntity getZxCtFsContractReviewListByCondition(ZxCtFsContractReview zxCtFsContractReview);

    public ResponseEntity getZxCtFsContractReviewDetail(ZxCtFsContractReview zxCtFsContractReview);

    public ResponseEntity saveZxCtFsContractReview(ZxCtFsContractReview zxCtFsContractReview);

    public ResponseEntity updateZxCtFsContractReview(ZxCtFsContractReview zxCtFsContractReview);

    public ResponseEntity batchDeleteUpdateZxCtFsContractReview(List<ZxCtFsContractReview> zxCtFsContractReviewList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    /**
     * 导出附属合同评审列表
     * @author suncg
     * @param zxCtFsContractReview
     * */
    public ResponseEntity exportContractReview(ZxCtFsContractReview zxCtFsContractReview, HttpServletResponse response);


    /**
     * 根据原合同编号生成附属合同编号（用作前段预览）
     * @author suncg
     * @param zxCtFsContractReview
     * */
    public ResponseEntity getZxCtFsContractNo(ZxCtFsContractReview zxCtFsContractReview);
}
