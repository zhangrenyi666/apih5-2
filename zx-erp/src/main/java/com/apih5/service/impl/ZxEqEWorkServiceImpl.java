package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxEqEWorkItemMapper;
import com.apih5.mybatis.dao.ZxEqEWorkMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxEqEWork;
import com.apih5.mybatis.pojo.ZxEqEWorkItem;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxEqEWorkService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqEWorkService")
public class ZxEqEWorkServiceImpl implements ZxEqEWorkService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqEWorkMapper zxEqEWorkMapper;
    
    @Autowired(required = true)
    private ZxEqEWorkItemMapper zxEqEWorkItemMapper;
    
    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Override
    public ResponseEntity getZxEqEWorkListByCondition(ZxEqEWork zxEqEWork) {
        if (zxEqEWork == null) {
            zxEqEWork = new ZxEqEWork();
        }
        // 分页查询
        PageHelper.startPage(zxEqEWork.getPage(),zxEqEWork.getLimit());
        // 获取数据
        List<ZxEqEWork> zxEqEWorkList = zxEqEWorkMapper.selectByZxEqEWorkList(zxEqEWork);
        for (ZxEqEWork zxEqEWork2 : zxEqEWorkList) {
        	ZxEqEWorkItem item = new ZxEqEWorkItem();
        	item.setMainID(zxEqEWork2.getId());
        	List<ZxEqEWorkItem> itemList = zxEqEWorkItemMapper.selectByZxEqEWorkItemList(item);
        	zxEqEWork2.setItemList(itemList);
        	
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(zxEqEWork2.getId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	zxEqEWork2.setFileList(fileList);
        }
        // 得到分页信息
        PageInfo<ZxEqEWork> p = new PageInfo<>(zxEqEWorkList);

        return repEntity.okList(zxEqEWorkList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqEWorkDetails(ZxEqEWork zxEqEWork) {
        if (zxEqEWork == null) {
            zxEqEWork = new ZxEqEWork();
        }
        // 获取数据
        ZxEqEWork dbZxEqEWork = zxEqEWorkMapper.selectByPrimaryKey(zxEqEWork.getId());
        // 数据存在
        if (dbZxEqEWork != null) {
            return repEntity.ok(dbZxEqEWork);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqEWork(ZxEqEWork zxEqEWork) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqEWork.setId(UuidUtil.generate());
        zxEqEWork.setCreateUserInfo(userKey, realName);
        int flag = zxEqEWorkMapper.insert(zxEqEWork);
        if(zxEqEWork.getItemList() != null && zxEqEWork.getItemList().size() >0) {	
        	for (ZxEqEWorkItem lib : zxEqEWork.getItemList()) {
        		lib.setId(UuidUtil.generate());
        		lib.setEquipID(zxEqEWork.getEquipID());
        		lib.setMainID(zxEqEWork.getId());
        		lib.setCreateUserInfo(userKey, realName);
        		flag = zxEqEWorkItemMapper.insert(lib);
			}
        }
        if(zxEqEWork.getFileList() != null && zxEqEWork.getFileList().size() >0) {
        	for (ZxErpFile file : zxEqEWork.getFileList()) {
        		file.setUid(UuidUtil.generate());
        		file.setOtherId(zxEqEWork.getId());
        		file.setCreateUserInfo(userKey, realName);
        		flag = zxErpFileMapper.insert(file);
			}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqEWork);
        }
    }

    @Override
    public ResponseEntity updateZxEqEWork(ZxEqEWork zxEqEWork) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqEWork dbzxEqEWork = zxEqEWorkMapper.selectByPrimaryKey(zxEqEWork.getId());
        if (dbzxEqEWork != null && StrUtil.isNotEmpty(dbzxEqEWork.getId())) {
           // 使用单位
           dbzxEqEWork.setOrgName(zxEqEWork.getOrgName());
           // 
           dbzxEqEWork.setOrgID(zxEqEWork.getOrgID());
           // 填写日期
           dbzxEqEWork.setBizDate(zxEqEWork.getBizDate());
           // 单据编号
           dbzxEqEWork.setBillNo(zxEqEWork.getBillNo());
           // 设备id
           dbzxEqEWork.setEquipID(zxEqEWork.getEquipID());
           dbzxEqEWork.setStatus(zxEqEWork.getStatus());
           // 备注
           dbzxEqEWork.setRemark(zxEqEWork.getRemark());
           // 
           dbzxEqEWork.setComID(zxEqEWork.getComID());
           // 
           dbzxEqEWork.setComName(zxEqEWork.getComName());
           // 共通
           dbzxEqEWork.setModifyUserInfo(userKey, realName);
           flag = zxEqEWorkMapper.updateByPrimaryKey(dbzxEqEWork);
         //先删除再新增
           ZxEqEWorkItem delLib = new ZxEqEWorkItem();
           delLib.setMainID(zxEqEWork.getId());
           List<ZxEqEWorkItem> delLibList = zxEqEWorkItemMapper.selectByZxEqEWorkItemList(delLib);
           if(delLibList != null && delLibList.size() >0) {
        	   delLib.setModifyUserInfo(userKey, realName);
        	   zxEqEWorkItemMapper.batchDeleteUpdateZxEqEWorkItem(delLibList, delLib);
           }
           if(zxEqEWork.getItemList() != null && zxEqEWork.getItemList().size() >0) {	
        	   for (ZxEqEWorkItem lib : zxEqEWork.getItemList()) {
        		   lib.setId(UuidUtil.generate());
        		   lib.setEquipID(dbzxEqEWork.getEquipID());
        		   lib.setMainID(zxEqEWork.getId());
        		   lib.setCreateUserInfo(userKey, realName);
        		   flag = zxEqEWorkItemMapper.insert(lib);
        	   }
           }
           //先删除再新增
           ZxErpFile delFile = new ZxErpFile();
           delFile.setOtherId(zxEqEWork.getId());
           List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
           if(delFileList != null && delFileList.size() >0) {
        	   delFile.setModifyUserInfo(userKey, realName);
        	   zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
           }
           if(zxEqEWork.getFileList() != null && zxEqEWork.getFileList().size() >0) {
        	   for (ZxErpFile file : zxEqEWork.getFileList()) {
        		   file.setUid(UuidUtil.generate());
        		   file.setOtherId(zxEqEWork.getId());
        		   file.setCreateUserInfo(userKey, realName);
        		   flag = zxErpFileMapper.insert(file);
        	   }
           }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqEWork);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqEWork(List<ZxEqEWork> zxEqEWorkList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqEWorkList != null && zxEqEWorkList.size() > 0) {
        	for (ZxEqEWork zxEqEWork : zxEqEWorkList) {
    			ZxEqEWorkItem delLib = new ZxEqEWorkItem();
    			delLib.setMainID(zxEqEWork.getId());
    			List<ZxEqEWorkItem> delLibList = zxEqEWorkItemMapper.selectByZxEqEWorkItemList(delLib);
    			if(delLibList != null && delLibList.size() >0) {
    				delLib.setModifyUserInfo(userKey, realName);
    				zxEqEWorkItemMapper.batchDeleteUpdateZxEqEWorkItem(delLibList, delLib);
    			}
    			ZxErpFile delFile = new ZxErpFile();
    			delFile.setOtherId(zxEqEWork.getId());
    			List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
    			if(delFileList != null && delFileList.size() >0) {
    				delFile.setModifyUserInfo(userKey, realName);
    				zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
    			}
    		}
        	ZxEqEWork zxEqEWork = new ZxEqEWork();
           zxEqEWork.setModifyUserInfo(userKey, realName);
           flag = zxEqEWorkMapper.batchDeleteUpdateZxEqEWork(zxEqEWorkList, zxEqEWork);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqEWorkList);
        }
    }

	@Override
	public ResponseEntity getZxEqEWorkListForReport(ZxEqEWork zxEqEWork) {
		if (zxEqEWork == null) {
			zxEqEWork = new ZxEqEWork();
		}
		// 分页查询
		PageHelper.startPage(zxEqEWork.getPage(),zxEqEWork.getLimit());
		// 获取数据
		List<ZxEqEWork> zxEqEWorkList = zxEqEWorkMapper.getZxEqEWorkListForReport(zxEqEWork);
		// 得到分页信息
		PageInfo<ZxEqEWork> p = new PageInfo<>(zxEqEWorkList);

		return repEntity.okList(zxEqEWorkList, p.getTotal());
	}

	@Override
	public List<ZxEqEWork> ureportZxEqEWorkListForReport(ZxEqEWork zxEqEWork) {
		if (zxEqEWork == null) {
			zxEqEWork = new ZxEqEWork();
		}
		// 获取数据
		List<ZxEqEWork> zxEqEWorkList = zxEqEWorkMapper.getZxEqEWorkListForReport(zxEqEWork);
		return zxEqEWorkList;
	}
	
}
