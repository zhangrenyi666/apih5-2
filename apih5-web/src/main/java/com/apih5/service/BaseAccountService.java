package com.apih5.service;

import java.util.List;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.pojo.BaseAccount;

public interface BaseAccountService {
	
	public ResponseEntity getCorpInfo(BaseAccount baseAccount);	

    public ResponseEntity getBaseAccountListByCondition(BaseAccount baseAccount);

    public ResponseEntity saveBaseAccount(BaseAccount baseAccount);

    public ResponseEntity updateBaseAccount(BaseAccount baseAccount);

    public ResponseEntity batchDeleteUpdateBaseAccount(List<BaseAccount> baseAccountList);

    /**
     * 获取getBaseAccount
     * 
     * @return baseAccount
     */
    public BaseAccount getBaseAccount(String accountId);
    
    /**
	 * 获取accessToken
	 * @return accessToken
	 */
	public BaseAccount getAccountInfo(String accountId);
	
	public ResponseEntity syncWeChatAccessTokenByFrom(BaseAccount baseAccount);

	public ResponseEntity syncWeChatAccessToken(BaseAccount baseAccount);
	
    /**
     * 向redis中插入BaseAccount数据
     * 
     * @param accountId
     * @param BaseAccount
     */
    public void putAccessTokenRedis(String accountId, BaseAccount baseAccount);

    /**
     * 从redis中获取BaseAccount
     * 
     * @param tokenId
     */
    BaseAccount getAccessTokenRedis(String accountId);

    /**
     * 从redis中删除accountId
     * 
     * @param accountId
     */
    void removeAccessTokenRedis(String accountId);
    
    /**
     * 向redis中插入BaseAccount数据
     * 
     * @param accountId
     * @param BaseAccount
     */
    public void putBaseAccountRedis(String accountId, BaseAccount baseAccount);
    
    /**
     * 从redis中获取BaseAccount
     * 
     * @param tokenId
     */
    BaseAccount getBaseAccountRedis(String accountId);
    
    /**
     * 从redis中删除accountId
     * 
     * @param accountId
     */
    void removeBaseAccountRedis(String accountId);
}

