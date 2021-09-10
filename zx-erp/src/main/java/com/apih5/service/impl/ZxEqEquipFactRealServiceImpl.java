package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxEqEquipFactItemMapper;
import com.apih5.mybatis.dao.ZxEqEquipFactMapper;
import com.apih5.mybatis.dao.ZxEqEquipFactRealItemMapper;
import com.apih5.mybatis.dao.ZxEqEquipFactRealMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxEqEquipFact;
import com.apih5.mybatis.pojo.ZxEqEquipFactItem;
import com.apih5.mybatis.pojo.ZxEqEquipFactReal;
import com.apih5.mybatis.pojo.ZxEqEquipFactRealItem;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxEqEquipFactRealService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;

@Service("zxEqEquipFactRealService")
public class ZxEqEquipFactRealServiceImpl implements ZxEqEquipFactRealService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqEquipFactRealMapper zxEqEquipFactRealMapper;
    
    @Autowired(required = true)
    private ZxEqEquipFactRealItemMapper zxEqEquipFactRealItemMapper;
    
    @Autowired(required = true)
    private ZxEqEquipFactMapper zxEqEquipFactMapper;
    
    @Autowired(required = true)
    private ZxEqEquipFactItemMapper zxEqEquipFactItemMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Override
    public ResponseEntity getZxEqEquipFactRealListByCondition(ZxEqEquipFactReal zxEqEquipFactReal) {
        if (zxEqEquipFactReal == null) {
            zxEqEquipFactReal = new ZxEqEquipFactReal();
        }
        
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxEqEquipFactReal.setComID("");
        	zxEqEquipFactReal.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxEqEquipFactReal.setComID(zxEqEquipFactReal.getOrgID());
        	zxEqEquipFactReal.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxEqEquipFactReal.setOrgID(zxEqEquipFactReal.getOrgID());
        }
        
        // 分页查询
        PageHelper.startPage(zxEqEquipFactReal.getPage(),zxEqEquipFactReal.getLimit());
        // 获取数据
        List<ZxEqEquipFactReal> zxEqEquipFactRealList = zxEqEquipFactRealMapper.selectByZxEqEquipFactRealList(zxEqEquipFactReal);
        for (ZxEqEquipFactReal zxEqEquipFactReal2 : zxEqEquipFactRealList) {
        	ZxEqEquipFactRealItem item = new ZxEqEquipFactRealItem();
        	item.setMainID(zxEqEquipFactReal2.getId());
        	List<ZxEqEquipFactRealItem> itemList = zxEqEquipFactRealItemMapper.selectByZxEqEquipFactRealItemList(item);
        	
        	int source1 = 0;
        	int source2 = 0;
        	int source3 = 0;
        	if(itemList != null && itemList.size() >0) {
        		for (ZxEqEquipFactRealItem itemSelect : itemList) {
        			if(StrUtil.equals(itemSelect.getSource(), "1")) {
						source1 = source1+1;
					}else if(StrUtil.equals(itemSelect.getSource(), "2")) {
						source2 = source2+1;
					}else if(StrUtil.equals(itemSelect.getSource(), "3")) {
						source3 = source3+1;
					}
				}
        	}
        	zxEqEquipFactReal2.setSource1(source1);
        	zxEqEquipFactReal2.setSource2(source2);
        	zxEqEquipFactReal2.setSource3(source3);
        	
        	zxEqEquipFactReal2.setItemList(itemList);
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(zxEqEquipFactReal2.getId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	zxEqEquipFactReal2.setFileList(fileList);
        }
        // 得到分页信息
        PageInfo<ZxEqEquipFactReal> p = new PageInfo<>(zxEqEquipFactRealList);

        return repEntity.okList(zxEqEquipFactRealList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqEquipFactRealDetails(ZxEqEquipFactReal zxEqEquipFactReal) {
        if (zxEqEquipFactReal == null) {
            zxEqEquipFactReal = new ZxEqEquipFactReal();
        }
        // 获取数据
        ZxEqEquipFactReal dbZxEqEquipFactReal = zxEqEquipFactRealMapper.selectByPrimaryKey(zxEqEquipFactReal.getId());
        // 数据存在
        if (dbZxEqEquipFactReal != null) {
            return repEntity.ok(dbZxEqEquipFactReal);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqEquipFactReal(ZxEqEquipFactReal zxEqEquipFactReal) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqEquipFactReal.setId(UuidUtil.generate());
        zxEqEquipFactReal.setCreateUserInfo(userKey, realName);
        int flag = zxEqEquipFactRealMapper.insert(zxEqEquipFactReal);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqEquipFactReal);
        }
    }

    @Override
    public ResponseEntity updateZxEqEquipFactReal(ZxEqEquipFactReal zxEqEquipFactReal) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqEquipFactReal dbzxEqEquipFactReal = zxEqEquipFactRealMapper.selectByPrimaryKey(zxEqEquipFactReal.getId());
        if (dbzxEqEquipFactReal != null && StrUtil.isNotEmpty(dbzxEqEquipFactReal.getId())) {
           // 复制期次date
           dbzxEqEquipFactReal.setCopyPeriodDate(zxEqEquipFactReal.getCopyPeriodDate());
           // 复制期次
           dbzxEqEquipFactReal.setCopyPeriod(zxEqEquipFactReal.getCopyPeriod());
           //备注
           dbzxEqEquipFactReal.setRemark(zxEqEquipFactReal.getRemark());
           // 共通
           dbzxEqEquipFactReal.setModifyUserInfo(userKey, realName);
           flag = zxEqEquipFactRealMapper.updateByPrimaryKey(dbzxEqEquipFactReal);
       
           //itemList先删除再新增
           ZxEqEquipFactRealItem delItem = new ZxEqEquipFactRealItem();
           delItem.setMainID(dbzxEqEquipFactReal.getId());
           List<ZxEqEquipFactRealItem> delItemList = zxEqEquipFactRealItemMapper.selectByZxEqEquipFactRealItemList(delItem);
           if(delItemList != null && delItemList.size() >0) {
        	   delItem.setModifyUserInfo(userKey, realName);
        	   zxEqEquipFactRealItemMapper.batchDeleteUpdateZxEqEquipFactRealItem(delItemList, delItem);
           }
           if(zxEqEquipFactReal.getItemList() != null && zxEqEquipFactReal.getItemList().size() >0) {	
        	   for (ZxEqEquipFactRealItem lib : zxEqEquipFactReal.getItemList()) {
        		   lib.setId(UuidUtil.generate());
        		   lib.setMainID(dbzxEqEquipFactReal.getId());
        		   lib.setCreateUserInfo(userKey, realName);
        		   flag = zxEqEquipFactRealItemMapper.insert(lib);
        	   }
           }
           //附件先删除再新增
           ZxErpFile delFile = new ZxErpFile();
           delFile.setOtherId(dbzxEqEquipFactReal.getId());
           List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
           if(delFileList != null && delFileList.size() >0) {
        	   delFile.setModifyUserInfo(userKey, realName);
        	   zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
           }
           if(zxEqEquipFactReal.getFileList() != null && zxEqEquipFactReal.getFileList().size() >0) {
        	   for (ZxErpFile zxErpFile : zxEqEquipFactReal.getFileList()) {
        		   zxErpFile.setUid(UuidUtil.generate());
        		   zxErpFile.setOtherId(dbzxEqEquipFactReal.getId());
        		   flag = zxErpFileMapper.insert(zxErpFile);
        	   }
           }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqEquipFactReal);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqEquipFactReal(List<ZxEqEquipFactReal> zxEqEquipFactRealList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqEquipFactRealList != null && zxEqEquipFactRealList.size() > 0) {
        	for (ZxEqEquipFactReal zxEqEquipFactReal : zxEqEquipFactRealList) {
        		ZxEqEquipFactRealItem delLib = new ZxEqEquipFactRealItem();
    			delLib.setMainID(zxEqEquipFactReal.getId());
    			List<ZxEqEquipFactRealItem> delLibList = zxEqEquipFactRealItemMapper.selectByZxEqEquipFactRealItemList(delLib);
    			if(delLibList != null && delLibList.size() >0) {
    				delLib.setModifyUserInfo(userKey, realName);
    				zxEqEquipFactRealItemMapper.batchDeleteUpdateZxEqEquipFactRealItem(delLibList, delLib);
    			}
    			ZxErpFile delFile = new ZxErpFile();
    			delFile.setOtherId(zxEqEquipFactReal.getId());
    			List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
    			if(delFileList != null && delFileList.size() >0) {
    				delFile.setModifyUserInfo(userKey, realName);
    				zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
    			}
    			//
    			ZxEqEquipFact updateFact = zxEqEquipFactMapper.selectByPrimaryKey(zxEqEquipFactReal.getSourceID());
    			if(updateFact != null && StrUtil.isNotEmpty(updateFact.getId())) {
    				updateFact.setIsUse("0");//否
//    				updateFact.setAuditStatus("0");//未审核
    				flag = zxEqEquipFactMapper.updateByPrimaryKey(updateFact);
    			}
        	}
        	ZxEqEquipFactReal zxEqEquipFactReal = new ZxEqEquipFactReal();
        	zxEqEquipFactReal.setModifyUserInfo(userKey, realName);
        	flag = zxEqEquipFactRealMapper.batchDeleteUpdateZxEqEquipFactReal(zxEqEquipFactRealList, zxEqEquipFactReal);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqEquipFactRealList);
        }
    }

    @Override
    public ResponseEntity syncZxEqEquipFactReal(ZxEqEquipFactReal zxEqEquipFactReal) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;

    	//先删除再同步
    	List<ZxEqEquipFactReal>  zxEqEquipFactRealList = zxEqEquipFactRealMapper.selectByZxEqEquipFactRealList(new ZxEqEquipFactReal());
        if (zxEqEquipFactRealList != null && zxEqEquipFactRealList.size() > 0) {
        	for (ZxEqEquipFactReal zxEqEquipFactRealSelcet : zxEqEquipFactRealList) {
        		ZxEqEquipFactRealItem delLib = new ZxEqEquipFactRealItem();
    			delLib.setMainID(zxEqEquipFactRealSelcet.getId());
    			List<ZxEqEquipFactRealItem> delLibList = zxEqEquipFactRealItemMapper.selectByZxEqEquipFactRealItemList(delLib);
    			if(delLibList != null && delLibList.size() >0) {
    				delLib.setModifyUserInfo(userKey, realName);
    				zxEqEquipFactRealItemMapper.batchDeleteUpdateZxEqEquipFactRealItem(delLibList, delLib);
    			}
    			ZxErpFile delFile = new ZxErpFile();
    			delFile.setOtherId(zxEqEquipFactRealSelcet.getId());
    			List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
    			if(delFileList != null && delFileList.size() >0) {
    				delFile.setModifyUserInfo(userKey, realName);
    				zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
    			}
        	}
        	ZxEqEquipFactReal zxEqEquipFactRealDel = new ZxEqEquipFactReal();
        	zxEqEquipFactRealDel.setModifyUserInfo(userKey, realName);
        	flag = zxEqEquipFactRealMapper.batchDeleteUpdateZxEqEquipFactReal(zxEqEquipFactRealList, zxEqEquipFactRealDel);
        }
    	
        //同步
    	List<ZxEqEquipFact> zxEqEquipFactList = zxEqEquipFactMapper.selectByZxEqEquipFactList(new ZxEqEquipFact());
    	if(zxEqEquipFactList != null && zxEqEquipFactList.size() >0) {
    		for (ZxEqEquipFact zxEqEquipFact : zxEqEquipFactList) {
    			BeanUtil.copyProperties(zxEqEquipFact, zxEqEquipFactReal);
    			zxEqEquipFactReal.setId(UuidUtil.generate());
    			zxEqEquipFactReal.setCreateUserInfo(userKey, realName);
    			flag = zxEqEquipFactRealMapper.insert(zxEqEquipFactReal);
    			//
    			ZxEqEquipFactItem factItem = new ZxEqEquipFactItem();
    			factItem.setMainID(zxEqEquipFact.getId());
    			List<ZxEqEquipFactItem> factItemList = zxEqEquipFactItemMapper.selectByZxEqEquipFactItemList(factItem);
    			if(factItemList != null && factItemList.size() >0) {
    				for (ZxEqEquipFactItem item : factItemList) {
    					ZxEqEquipFactRealItem copyRealItem = new ZxEqEquipFactRealItem();
    					BeanUtil.copyProperties(item, copyRealItem);
    					copyRealItem.setId(UuidUtil.generate());
    					copyRealItem.setMainID(zxEqEquipFactReal.getId());
    					copyRealItem.setCreateUserInfo(userKey, realName);
    					flag = zxEqEquipFactRealItemMapper.insert(copyRealItem);
					}
    			}
    			//
    			ZxErpFile zxErpFile = new ZxErpFile();
    			zxErpFile.setOtherId(zxEqEquipFact.getId());
    			List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
    			if(fileList != null && fileList.size() >0) {
    				for (ZxErpFile file : fileList) {
    					ZxErpFile copyFile = new ZxErpFile();
    					BeanUtil.copyProperties(file, copyFile);
    					copyFile.setUid(UuidUtil.generate());
    					copyFile.setOtherId(zxEqEquipFactReal.getId());
    					copyFile.setCreateUserInfo(userKey, realName);
    					flag = zxErpFileMapper.insert(copyFile);
					}
    			}
    		}
    	}

    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.sava", zxEqEquipFactReal);
    	}
	}

	@Override
	public ResponseEntity auditZxEqEquipFactReal(ZxEqEquipFactReal zxEqEquipFactReal) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxEqEquipFactReal dbzxEqEquipFactReal = zxEqEquipFactRealMapper.selectByPrimaryKey(zxEqEquipFactReal.getId());
		if (dbzxEqEquipFactReal != null && StrUtil.isNotEmpty(dbzxEqEquipFactReal.getId())) {
			// 审核状态
			dbzxEqEquipFactReal.setAuditStatus(zxEqEquipFactReal.getAuditStatus());
			// 共通
			dbzxEqEquipFactReal.setModifyUserInfo(userKey, realName);
			flag = zxEqEquipFactRealMapper.updateByPrimaryKey(dbzxEqEquipFactReal);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		}
		else {
			return repEntity.ok("sys.data.update",zxEqEquipFactReal);
		}
	}
}
