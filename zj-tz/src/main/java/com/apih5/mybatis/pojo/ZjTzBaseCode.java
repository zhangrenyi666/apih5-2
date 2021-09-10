package com.apih5.mybatis.pojo;

import java.util.Date;
import com.apih5.framework.entity.BasePojo;

public class ZjTzBaseCode extends BasePojo {
    private String codeId;

    private String codePid;

    private String typeId;

    private String itemId;

    private String itemName;

    private String itemAsName;

    private String pidAll;

    private String pidNameAll;

    private String ext1;

    private String ext2;

    private String ext3;

    private String ext4;

    private String ext5;

    private String remarks;

    private Integer codeSort;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private String oldItemName;
    
    private String newItemName;
    
    private String interfaceFlag;//0外层  1里层

    public String getCodeId() {
        return codeId == null ? "" : codeId.trim();
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId == null ? null : codeId.trim();
    }

    public String getCodePid() {
        return codePid == null ? "" : codePid.trim();
    }

    public void setCodePid(String codePid) {
        this.codePid = codePid == null ? null : codePid.trim();
    }

    public String getTypeId() {
        return typeId == null ? "" : typeId.trim();
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public String getItemId() {
        return itemId == null ? "" : itemId.trim();
    }

    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    public String getItemName() {
        return itemName == null ? "" : itemName.trim();
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public String getItemAsName() {
        return itemAsName == null ? "" : itemAsName.trim();
    }

    public void setItemAsName(String itemAsName) {
        this.itemAsName = itemAsName == null ? null : itemAsName.trim();
    }

    public String getPidAll() {
        return pidAll == null ? "" : pidAll.trim();
    }

    public void setPidAll(String pidAll) {
        this.pidAll = pidAll == null ? null : pidAll.trim();
    }

    public String getPidNameAll() {
        return pidNameAll == null ? "" : pidNameAll.trim();
    }

    public void setPidNameAll(String pidNameAll) {
        this.pidNameAll = pidNameAll == null ? null : pidNameAll.trim();
    }

    public String getExt1() {
        return ext1 == null ? "" : ext1.trim();
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1 == null ? null : ext1.trim();
    }

    public String getExt2() {
        return ext2 == null ? "" : ext2.trim();
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2 == null ? null : ext2.trim();
    }

    public String getExt3() {
        return ext3 == null ? "" : ext3.trim();
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3 == null ? null : ext3.trim();
    }

    public String getExt4() {
        return ext4 == null ? "" : ext4.trim();
    }

    public void setExt4(String ext4) {
        this.ext4 = ext4 == null ? null : ext4.trim();
    }

    public String getExt5() {
        return ext5 == null ? "" : ext5.trim();
    }

    public void setExt5(String ext5) {
        this.ext5 = ext5 == null ? null : ext5.trim();
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getCodeSort() {
        return codeSort;
    }

    public void setCodeSort(Integer codeSort) {
        this.codeSort = codeSort;
    }

    public String getDelFlag() {
        return delFlag == null ? "" : delFlag.trim();
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser == null ? "" : createUser.trim();
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getCreateUserName() {
        return createUserName == null ? "" : createUserName.trim();
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser == null ? "" : modifyUser.trim();
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    public String getModifyUserName() {
        return modifyUserName == null ? "" : modifyUserName.trim();
    }

    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName == null ? null : modifyUserName.trim();
    }

	public String getOldItemName() {
		return oldItemName;
	}

	public void setOldItemName(String oldItemName) {
		this.oldItemName = oldItemName;
	}

	public String getNewItemName() {
		return newItemName;
	}

	public void setNewItemName(String newItemName) {
		this.newItemName = newItemName;
	}

	public String getInterfaceFlag() {
		return interfaceFlag;
	}

	public void setInterfaceFlag(String interfaceFlag) {
		this.interfaceFlag = interfaceFlag;
	}
	

}

