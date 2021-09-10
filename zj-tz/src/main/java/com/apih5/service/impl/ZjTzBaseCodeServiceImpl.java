package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzBaseCodeMapper;
import com.apih5.mybatis.pojo.ZjTzBaseCode;
import com.apih5.service.ZjTzBaseCodeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.hutool.core.util.StrUtil;

@Service("zjTzBaseCodeService")
public class ZjTzBaseCodeServiceImpl implements ZjTzBaseCodeService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzBaseCodeMapper zjTzBaseCodeMapper;

    @Override
    public ResponseEntity getZjTzBaseCodeListByCondition(ZjTzBaseCode zjTzBaseCode) {
        if (zjTzBaseCode == null) {
            zjTzBaseCode = new ZjTzBaseCode();
        }
        // 分页查询
        PageHelper.startPage(zjTzBaseCode.getPage(),zjTzBaseCode.getLimit());
        if(StrUtil.equals(zjTzBaseCode.getInterfaceFlag(), "0")) {
        	if(StrUtil.isEmpty(zjTzBaseCode.getTypeId())) {
           	 return repEntity.okList(null, 0);
           }
        }else {
        	if(StrUtil.isEmpty(zjTzBaseCode.getCodePid())) {
           	 return repEntity.okList(null, 0);
           }
        }
        // 获取数据
        List<ZjTzBaseCode> zjTzBaseCodeList = zjTzBaseCodeMapper.selectByZjTzBaseCodeList(zjTzBaseCode);
        // 得到分页信息
        PageInfo<ZjTzBaseCode> p = new PageInfo<>(zjTzBaseCodeList);

        return repEntity.okList(zjTzBaseCodeList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzBaseCodeDetails(ZjTzBaseCode zjTzBaseCode) {
        if (zjTzBaseCode == null) {
            zjTzBaseCode = new ZjTzBaseCode();
        }
        // 获取数据
        ZjTzBaseCode dbZjTzBaseCode = zjTzBaseCodeMapper.selectByPrimaryKey(zjTzBaseCode.getCodeId());
        // 数据存在
        if (dbZjTzBaseCode != null) {
            return repEntity.ok(dbZjTzBaseCode);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzBaseCode(ZjTzBaseCode baseCode) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (baseCode != null) {
    		 if(StrUtil.equals(baseCode.getInterfaceFlag(), "0")) {
    			 if(StrUtil.isEmpty(baseCode.getTypeId())) {
    				 return repEntity.layerMessage("no", "请选择你要新增的数据字典类别");
    			 }
    		 }else {
    			 if(StrUtil.isEmpty(baseCode.getCodePid())) {
    				 return repEntity.layerMessage("no", "请选择你要新增的数据字典类别");
    			 } 
    		 }
    		ZjTzBaseCode checkBaseCode = new ZjTzBaseCode();
    		checkBaseCode.setCodePid(baseCode.getCodePid());
    		checkBaseCode.setItemId(baseCode.getItemId());
    		int count = zjTzBaseCodeMapper.countBaseCodeListByCodePid(checkBaseCode);
    		if (count > 0) {
    			return repEntity.layerMessage("no", "字典ID在其它根目录已存在,请换个试试!");
    		}
    		baseCode.setCodeId(UuidUtil.generate());
    		baseCode.setCodeSort(999);
    		// 上一节点的pid_all(包含上一节点id)
    		if (StrUtil.isNotEmpty(baseCode.getPidAll())) {
    			String pidAll = baseCode.getPidAll();
    			baseCode.setPidAll(pidAll + "," + baseCode.getCodeId());
    		} else {
    			// 新增根节点时情况
    			baseCode.setPidAll(baseCode.getCodeId());
    		}
    		// 上一节点的pid_name_all(包含上一节点name)
    		if (StrUtil.isNotEmpty(baseCode.getPidNameAll())) {
    			baseCode.setPidNameAll(baseCode.getPidNameAll() + "," + baseCode.getItemName());
    		} else {
    			// 新增根节点时情况
    			baseCode.setPidNameAll(baseCode.getItemName());
    		}
    		baseCode.setCreateUserInfo(userKey, realName);
    		flag = zjTzBaseCodeMapper.insert(baseCode);
    	}
    	if (flag == 0) {
    		return repEntity.errorSave();
    	} else {
    		return repEntity.ok("sys.data.sava", baseCode);
    	}
    }

    @Override
    public ResponseEntity updateZjTzBaseCode(ZjTzBaseCode baseCode) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZjTzBaseCode dbbaseCode = zjTzBaseCodeMapper.selectByPrimaryKey(baseCode.getCodeId());
		if (dbbaseCode != null) {
			String oldItemName = dbbaseCode.getItemName();
			// check(如果是更新根节点时，则itemId不能跟其他根节点重复)
			if (StrUtil.equals("0", dbbaseCode.getCodePid())) {
				ZjTzBaseCode checkBaseCode = new ZjTzBaseCode();
				checkBaseCode.setCodePid(dbbaseCode.getCodePid());
				checkBaseCode.setItemId(baseCode.getItemId());
				int count = zjTzBaseCodeMapper.countBaseCodeListByCodePid(checkBaseCode);
				if (count > 0) {
					return repEntity.layerMessage("no", "字典ID在其它根目录已存在,请换个试试!");
				}
			}
			dbbaseCode.setItemId(baseCode.getItemId());
			dbbaseCode.setItemName(baseCode.getItemName());
			dbbaseCode.setItemAsName(baseCode.getItemAsName());
			dbbaseCode.setExt1(baseCode.getExt1());
			dbbaseCode.setExt2(baseCode.getExt2());
			dbbaseCode.setExt3(baseCode.getExt3());
			dbbaseCode.setExt4(baseCode.getExt4());
			dbbaseCode.setExt5(baseCode.getExt5());
			dbbaseCode.setRemarks(baseCode.getRemarks());
			// dbbaseCode.setCodeSort(baseCode.getCodeSort());
			// 共通
			dbbaseCode.setModifyUserInfo(userKey, realName);
			flag = zjTzBaseCodeMapper.updateByPrimaryKey(dbbaseCode);
			if (flag != 0) {
				// 批量更新层级表pid_name_all字段
				ZjTzBaseCode baseCode1 = new ZjTzBaseCode();
				baseCode1.setOldItemName(oldItemName);
				baseCode1.setNewItemName(dbbaseCode.getItemName());
				baseCode1.setCodeId(dbbaseCode.getCodeId());
				flag = zjTzBaseCodeMapper.batchUpdateBaseCodePidNameAll(baseCode1);
			}
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorUpdate();
		} else {
			return repEntity.ok("sys.data.update", baseCode);
		}
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzBaseCode(List<ZjTzBaseCode> baseCodeList) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		// 定义最终删掉的层级
		List<ZjTzBaseCode> delBaseCodeList = Lists.newArrayList();
		if (baseCodeList != null && baseCodeList.size() > 0) {
			for (int i = 0; i < baseCodeList.size(); i++) {
				ZjTzBaseCode dbBaseCode = zjTzBaseCodeMapper.selectByPrimaryKey(baseCodeList.get(i).getCodeId());
				if (dbBaseCode != null) {
					// 获取该层级下面所有的层级集合(包含自己)
					ZjTzBaseCode baseCode = new ZjTzBaseCode();
					baseCode.setPidAll(dbBaseCode.getCodeId());
					List<ZjTzBaseCode> dbBaseCodeList = zjTzBaseCodeMapper.selectByBaseCodeListByLike(baseCode);
					delBaseCodeList.addAll(dbBaseCodeList);
				}
			}
		}
		if (delBaseCodeList != null && delBaseCodeList.size() > 0) {
			delBaseCodeList.get(0).setModifyUserInfo(userKey, realName);
			flag = zjTzBaseCodeMapper.batchDeleteUpdateZjTzBaseCode(delBaseCodeList,delBaseCodeList.get(0));
			// if (flag == 0) {
			// throw new Apih5Exception("删除失败!");
			// }
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorDelete();
		} else {
			return repEntity.ok("sys.data.delete", delBaseCodeList);
		}
    }
}
