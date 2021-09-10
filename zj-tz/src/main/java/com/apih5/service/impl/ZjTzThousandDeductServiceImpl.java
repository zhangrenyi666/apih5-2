package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzThousandDeductMapper;
import com.apih5.mybatis.pojo.ZjTzThousandDeduct;
import com.apih5.service.ZjTzThousandDeductService;
import cn.hutool.core.util.StrUtil;

@Service("zjTzThousandDeductService")
public class ZjTzThousandDeductServiceImpl implements ZjTzThousandDeductService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzThousandDeductMapper zjTzThousandDeductMapper;

    @Override
    public ResponseEntity getZjTzThousandDeductListByCondition(ZjTzThousandDeduct zjTzThousandDeduct) {
    	if (zjTzThousandDeduct == null) {
    		zjTzThousandDeduct = new ZjTzThousandDeduct();
    	}
    	if(StrUtil.isEmpty(zjTzThousandDeduct.getThousandCheckId())) {
    		return repEntity.okList(null, 0);
    	}
    	String count = "0";
    	List<ZjTzThousandDeduct> returnList = new ArrayList<>();
    	List<ZjTzThousandDeduct> zjTzThousandCheckBaseItemList = new ArrayList<>();
    	zjTzThousandDeduct.setGroupByFlagCodePid("分组啊");
    	List<ZjTzThousandDeduct> zjTzThousandDeductGroupByList = zjTzThousandDeductMapper.selectByZjTzThousandDeductList(zjTzThousandDeduct);
    	for (ZjTzThousandDeduct deduct : zjTzThousandDeductGroupByList) {
    		ZjTzThousandDeduct thousandDeduct = new ZjTzThousandDeduct();
    		thousandDeduct.setThousandCheckId(zjTzThousandDeduct.getThousandCheckId());
    		thousandDeduct.setThousandCheckBaseId(deduct.getThousandCheckBaseId());
    		List<ZjTzThousandDeduct> zjTzThousandDeductList = zjTzThousandDeductMapper.selectByZjTzThousandDeductList(thousandDeduct);
    		if(zjTzThousandDeductList != null && zjTzThousandDeductList.size() >0) {
    			for (ZjTzThousandDeduct zjTzThousandDeduct2 : zjTzThousandDeductList) {
    				zjTzThousandDeduct2.setCount(count);
    			}
    			zjTzThousandDeductList.get(0).setCount(zjTzThousandDeductList.size()+"");
    			zjTzThousandCheckBaseItemList.addAll(zjTzThousandDeductList);
    		}
    	}
    	ZjTzThousandDeduct reply = new ZjTzThousandDeduct();
    	reply.setThousandCheckId(zjTzThousandDeduct.getThousandCheckId());
    	returnList.add(reply);
    	returnList.get(0).setZjTzThousandDeductList(zjTzThousandCheckBaseItemList);
    	return repEntity.ok(returnList);
    }

    @Override
    public ResponseEntity getZjTzThousandDeductDetails(ZjTzThousandDeduct zjTzThousandDeduct) {
        if (zjTzThousandDeduct == null) {
            zjTzThousandDeduct = new ZjTzThousandDeduct();
        }
        // 获取数据
        ZjTzThousandDeduct dbZjTzThousandDeduct = zjTzThousandDeductMapper.selectByPrimaryKey(zjTzThousandDeduct.getThousandDeductId());
        // 数据存在
        if (dbZjTzThousandDeduct != null) {
            return repEntity.ok(dbZjTzThousandDeduct);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzThousandDeduct(ZjTzThousandDeduct zjTzThousandDeduct) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzThousandDeduct.setThousandDeductId(UuidUtil.generate());
        zjTzThousandDeduct.setCreateUserInfo(userKey, realName);
        int flag = zjTzThousandDeductMapper.insert(zjTzThousandDeduct);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzThousandDeduct);
        }
    }

    @Override
    public ResponseEntity updateZjTzThousandDeduct(ZjTzThousandDeduct zjTzThousandDeduct) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzThousandDeduct dbzjTzThousandDeduct = zjTzThousandDeductMapper.selectByPrimaryKey(zjTzThousandDeduct.getThousandDeductId());
        if (dbzjTzThousandDeduct != null && StrUtil.isNotEmpty(dbzjTzThousandDeduct.getThousandDeductId())) {
        	if(StrUtil.equals(zjTzThousandDeduct.getUpdateFlag(), "0")) {
        		// 扣分
        		dbzjTzThousandDeduct.setDeduct(zjTzThousandDeduct.getDeduct());
        		//得分
        		dbzjTzThousandDeduct.setActualScore(zjTzThousandDeduct.getActualScore());
        	}else if(StrUtil.equals(zjTzThousandDeduct.getUpdateFlag(), "1")) {
        		// 扣分说明
        		dbzjTzThousandDeduct.setDeductDesc(zjTzThousandDeduct.getDeductDesc());
        	}
        	// 共通
        	dbzjTzThousandDeduct.setModifyUserInfo(userKey, realName);
           flag = zjTzThousandDeductMapper.updateByPrimaryKey(dbzjTzThousandDeduct);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzThousandDeduct);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzThousandDeduct(List<ZjTzThousandDeduct> zjTzThousandDeductList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzThousandDeductList != null && zjTzThousandDeductList.size() > 0) {
           ZjTzThousandDeduct zjTzThousandDeduct = new ZjTzThousandDeduct();
           zjTzThousandDeduct.setModifyUserInfo(userKey, realName);
           flag = zjTzThousandDeductMapper.batchDeleteUpdateZjTzThousandDeduct(zjTzThousandDeductList, zjTzThousandDeduct);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzThousandDeductList);
        }
    }
}
