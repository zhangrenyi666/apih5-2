package com.apih5.entity;

import com.apih5.framework.api.wechatenterprise.entity.JSSdkEntity;

public class BaseAccountWebEntity {
    private String corpId;

    private String agentId;

	private String uri;

    private JSSdkEntity jssdkEntity;

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}
    
	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public JSSdkEntity getJssdkEntity() {
		return jssdkEntity;
	}

	public void setJssdkEntity(JSSdkEntity jssdkEntity) {
		this.jssdkEntity = jssdkEntity;
	}
}

