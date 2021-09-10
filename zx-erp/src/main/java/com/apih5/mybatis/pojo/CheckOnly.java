package com.apih5.mybatis.pojo;

import com.apih5.framework.entity.BasePojo;

import java.math.BigDecimal;
import java.util.Date;

public class CheckOnly extends BasePojo {
    // 对象名称
    private String objName;

    // 条件
    private String condStr;

    // 提示语
    private String message;

    //表名
    private String tableName;

    //条件组装
    private String condStrPack;
    
    private Integer existCount;
    
    public Integer getExistCount() {
		return existCount;
	}

	public void setExistCount(Integer existCount) {
		this.existCount = existCount;
	}

	public String getObjName() {
        return objName;
    }

    public String getCondStr() {
        return condStr;
    }

    public void setObjName(String objName) {
        this.objName = objName;
    }

    public void setCondStr(String condStr) {
        this.condStr = condStr;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCondStrPack() {
        return condStrPack;
    }

    public void setCondStrPack(String condStrPack) {
        this.condStrPack = condStrPack;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

