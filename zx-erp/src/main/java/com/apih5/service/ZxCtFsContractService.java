package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.ZxCtFsContract;

import javax.servlet.http.HttpServletResponse;

public interface ZxCtFsContractService {

    public ResponseEntity getZxCtFsContractListByCondition(ZxCtFsContract zxCtFsContract);

    public ResponseEntity getZxCtFsContractDetail(ZxCtFsContract zxCtFsContract);

    public ResponseEntity saveZxCtFsContract(ZxCtFsContract zxCtFsContract);

    public ResponseEntity updateZxCtFsContract(ZxCtFsContract zxCtFsContract);

    public ResponseEntity batchDeleteUpdateZxCtFsContract(List<ZxCtFsContract> zxCtFsContractList);

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    public ResponseEntity getOrg(ZxCtFsContract zxCtFsContract);

    public ResponseEntity exportContract(ZxCtFsContract zxCtFsContract, HttpServletResponse response);

    public ResponseEntity getZxCtFsContractList(ZxCtFsContract zxCtFsContract);

    /**
     * 查询运输类附属合同
     * @author suncg
     * @param zxCtFsContract
     * */
    public ResponseEntity getYunShuZxCtFsContractList(ZxCtFsContract zxCtFsContract);

    /**
     * 查询其他类附属合同
     * @author suncg
     * @param zxCtFsContract
     * */
    public ResponseEntity getQiTaZxCtFsContractList(ZxCtFsContract zxCtFsContract);
}
