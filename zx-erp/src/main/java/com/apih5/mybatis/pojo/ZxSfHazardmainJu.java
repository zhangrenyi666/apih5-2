package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSfHazardmainJu extends BasePojo {
    // 主键
    private String zxSfHazardmainJuId;

    // 机构ID
    private String orgId;

    // 所属机构
    private String orgName;

    // 编制人
    private String preparer;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    // 明细
    private List<ZxSfHazardmainDetailJu> detailList;

    // 附件
    private List<ZxErpFile> fileList;

    public String getZxSfHazardmainJuId() {
        return zxSfHazardmainJuId == null ? "" : zxSfHazardmainJuId.trim();
    }

    public void setZxSfHazardmainJuId(String zxSfHazardmainJuId) {
        this.zxSfHazardmainJuId = zxSfHazardmainJuId == null ? null : zxSfHazardmainJuId.trim();
    }

    public String getOrgId() {
        return orgId == null ? "" : orgId.trim();
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getPreparer() {
        return preparer == null ? "" : preparer.trim();
    }

    public void setPreparer(String preparer) {
        this.preparer = preparer == null ? null : preparer.trim();
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<ZxSfHazardmainDetailJu> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<ZxSfHazardmainDetailJu> detailList) {
        this.detailList = detailList;
    }

    public List<ZxErpFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<ZxErpFile> fileList) {
        this.fileList = fileList;
    }

}
