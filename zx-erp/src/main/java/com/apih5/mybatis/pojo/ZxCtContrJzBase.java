package com.apih5.mybatis.pojo;
import java.util.List;
import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;

public class ZxCtContrJzBase extends BasePojo {
    // 主键
    private String id;

    // 项目名称id
    private String orgID;

    // 项目名称
    private String orgName;

    // 类型
    private String type;

    // 备注
    private String remarks;

    // 清单明细List
    private List<ZxCtContrJzItemBase> zxCtContrJzItemBaseList;

    // 公司id
    private String comID;

    // 公司name
    private String comName;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getType() {
        return type == null ? "" : type.trim();
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public List<ZxCtContrJzItemBase> getZxCtContrJzItemBaseList() {
        return zxCtContrJzItemBaseList == null ? Lists.newArrayList() : zxCtContrJzItemBaseList;
    }

    public void setZxCtContrJzItemBaseList(List<ZxCtContrJzItemBase> zxCtContrJzItemBaseList) {
        this.zxCtContrJzItemBaseList = zxCtContrJzItemBaseList == null ? null : zxCtContrJzItemBaseList;
    }

    public String getComID() {
        return comID == null ? "" : comID.trim();
    }

    public void setComID(String comID) {
        this.comID = comID == null ? null : comID.trim();
    }

    public String getComName() {
        return comName == null ? "" : comName.trim();
    }

    public void setComName(String comName) {
        this.comName = comName == null ? null : comName.trim();
    }

}
