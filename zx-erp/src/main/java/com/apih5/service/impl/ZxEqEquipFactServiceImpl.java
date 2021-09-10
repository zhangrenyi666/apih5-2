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
import com.apih5.service.ZxEqEquipFactService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zxEqEquipFactService")
public class ZxEqEquipFactServiceImpl implements ZxEqEquipFactService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqEquipFactMapper zxEqEquipFactMapper;
    
    @Autowired(required = true)
    private ZxEqEquipFactItemMapper zxEqEquipFactItemMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxEqEquipFactRealMapper zxEqEquipFactRealMapper;
    
    @Autowired(required = true)
    private ZxEqEquipFactRealItemMapper zxEqEquipFactRealItemMapper;

    @Override
    public ResponseEntity getZxEqEquipFactListByCondition(ZxEqEquipFact zxEqEquipFact) {
        if (zxEqEquipFact == null) {
            zxEqEquipFact = new ZxEqEquipFact();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxEqEquipFact.setComID("");
        	zxEqEquipFact.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxEqEquipFact.setComID(zxEqEquipFact.getOrgID());
        	zxEqEquipFact.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxEqEquipFact.setOrgID(zxEqEquipFact.getOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxEqEquipFact.getPage(),zxEqEquipFact.getLimit());
        // 获取数据
        List<ZxEqEquipFact> zxEqEquipFactList = zxEqEquipFactMapper.selectByZxEqEquipFactList(zxEqEquipFact);
        for (ZxEqEquipFact zxEqEquipFact2 : zxEqEquipFactList) {
        	ZxEqEquipFactItem item = new ZxEqEquipFactItem();
        	item.setMainID(zxEqEquipFact2.getId());
        	List<ZxEqEquipFactItem> itemList = zxEqEquipFactItemMapper.selectByZxEqEquipFactItemList(item);
        	
        	int source1 = 0;
        	int source2 = 0;
        	int source3 = 0;
        	if(itemList != null && itemList.size() >0) {
        		for (ZxEqEquipFactItem itemSelect : itemList) {
					if(StrUtil.equals(itemSelect.getSource(), "1")) {
						source1 = source1+1;
					}else if(StrUtil.equals(itemSelect.getSource(), "2")) {
						source2 = source2+1;
					}else if(StrUtil.equals(itemSelect.getSource(), "3")) {
						source3 = source3+1;
					}
				}
        	}
        	zxEqEquipFact2.setSource1(source1);
        	zxEqEquipFact2.setSource2(source2);
        	zxEqEquipFact2.setSource3(source3);
        	
        	zxEqEquipFact2.setItemList(itemList);
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(zxEqEquipFact2.getId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	zxEqEquipFact2.setFileList(fileList);
        }
        // 得到分页信息
        PageInfo<ZxEqEquipFact> p = new PageInfo<>(zxEqEquipFactList);

        return repEntity.okList(zxEqEquipFactList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqEquipFactDetails(ZxEqEquipFact zxEqEquipFact) {
        if (zxEqEquipFact == null) {
            zxEqEquipFact = new ZxEqEquipFact();
        }
        // 获取数据
        ZxEqEquipFact dbZxEqEquipFact = zxEqEquipFactMapper.selectByPrimaryKey(zxEqEquipFact.getId());
        // 数据存在
        if (dbZxEqEquipFact != null) {
            return repEntity.ok(dbZxEqEquipFact);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqEquipFact(ZxEqEquipFact zxEqEquipFact) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        String period = "";

        if(zxEqEquipFact.getPeriodDate() != null) {
        	period = DateUtil.format(zxEqEquipFact.getPeriodDate(), "yyyyMM");
        }

        //重复校验
        ZxEqEquipFact factSelect = new ZxEqEquipFact();
        factSelect.setOrgID(zxEqEquipFact.getOrgID());
        factSelect.setPeriod(period);
        List<ZxEqEquipFact> factSelectList = zxEqEquipFactMapper.selectByZxEqEquipFactList(factSelect);
        if(factSelectList != null &&factSelectList.size() >0) {
        	return repEntity.layerMessage("no", "该期次存在，请重新选择！");
        }
        //add
        zxEqEquipFact.setId(UuidUtil.generate());
        zxEqEquipFact.setPeriod(period);
        zxEqEquipFact.setIsUse("0");//否
        zxEqEquipFact.setAuditStatus("0");
        zxEqEquipFact.setCreateUserInfo(userKey, realName);
        ZxEqEquipFactItem factItem = new ZxEqEquipFactItem();
        //复制期次
        if(StrUtil.isNotEmpty(zxEqEquipFact.getCopyPeriod())) {
        	factItem.setMainID(zxEqEquipFact.getCopyPeriod());
        	List<ZxEqEquipFactItem> factItems = zxEqEquipFactItemMapper.selectByZxEqEquipFactItemList(factItem);
//        	if(factItems != null &&factItems.size() >0) {
//        		for (ZxEqEquipFactItem zxEqEquipFactItem : factItems) {
//        			zxEqEquipFactItem.setId(UuidUtil.generate());
//        			zxEqEquipFactItem.setMainID(zxEqEquipFact.getId());
//        			zxEqEquipFactItem.setCreateUserInfo(userKey, realName);
//        			flag = zxEqEquipFactItemMapper.insert(zxEqEquipFactItem);
//        		}
//        	}
        }
        //复制上一期
//        String lastPeriod = "";
//        if(StrUtil.isNotEmpty(zxEqEquipFact.getIsUse())) {
//        	lastPeriod = DateUtil.format(DateUtil.offsetMonth(zxEqEquipFact.getPeriodDate(), -1), "yyyyMM");
//        	factSelect.setPeriod(lastPeriod);
//        	List<ZxEqEquipFact> facts = zxEqEquipFactMapper.selectByZxEqEquipFactList(factSelect);
//        	if(facts != null && facts.size() >0) {
//        		factItem.setMainID(facts.get(0).getId());
//        		List<ZxEqEquipFactItem> factItems = zxEqEquipFactItemMapper.selectByZxEqEquipFactItemList(factItem);
//        		if(factItems != null &&factItems.size() >0) {
//        			for (ZxEqEquipFactItem zxEqEquipFactItem : factItems) {
//        				zxEqEquipFactItem.setId(UuidUtil.generate());
//        				zxEqEquipFactItem.setMainID(zxEqEquipFact.getId());
//        				zxEqEquipFactItem.setCreateUserInfo(userKey, realName);
//        				flag = zxEqEquipFactItemMapper.insert(zxEqEquipFactItem);
//        			}
//        		}
//        	}
//        }
        
        flag = zxEqEquipFactMapper.insert(zxEqEquipFact);
        if(zxEqEquipFact.getItemList() != null && zxEqEquipFact.getItemList().size() >0) {	
        	for (ZxEqEquipFactItem lib : zxEqEquipFact.getItemList()) {
        		lib.setId(UuidUtil.generate());
        		lib.setMainID(zxEqEquipFact.getId());
        		lib.setCreateUserInfo(userKey, realName);
        		flag = zxEqEquipFactItemMapper.insert(lib);
			}
        }
        if(zxEqEquipFact.getFileList() != null && zxEqEquipFact.getFileList().size() >0) {
        	for (ZxErpFile file : zxEqEquipFact.getFileList()) {
        		file.setUid(UuidUtil.generate());
        		file.setOtherId(zxEqEquipFact.getId());
        		file.setCreateUserInfo(userKey, realName);
        		flag = zxErpFileMapper.insert(file);
			}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqEquipFact);
        }
    }

    @Override
    public ResponseEntity updateZxEqEquipFact(ZxEqEquipFact zxEqEquipFact) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqEquipFact dbzxEqEquipFact = zxEqEquipFactMapper.selectByPrimaryKey(zxEqEquipFact.getId());
        if (dbzxEqEquipFact != null && StrUtil.isNotEmpty(dbzxEqEquipFact.getId())) {
        	if(StrUtil.equals(dbzxEqEquipFact.getIsUse(), "1")) {
        		 return repEntity.layerMessage("no", "该数据已经被引用，不允许修改");
        	}
        	String period = "";
        	if(zxEqEquipFact.getPeriodDate() != null) {
        		period = DateUtil.format(zxEqEquipFact.getPeriodDate(), "yyyyMM");
        	}
        	 //重复校验
            ZxEqEquipFact factSelect = new ZxEqEquipFact();
            factSelect.setOrgID(zxEqEquipFact.getOrgID());
            factSelect.setPeriod(period);
            factSelect.setIdFlag(zxEqEquipFact.getId());
            List<ZxEqEquipFact> factSelectList = zxEqEquipFactMapper.selectByZxEqEquipFactList(factSelect);
            if(factSelectList != null &&factSelectList.size() >0) {
            	return repEntity.layerMessage("no", "该期次存在，请重新选择！");
            }
        	
        	
        	
        	
        	// 使用单位id
           dbzxEqEquipFact.setOrgID(zxEqEquipFact.getOrgID());
           // 使用单位name
           dbzxEqEquipFact.setOrgName(zxEqEquipFact.getOrgName());
           // 期次date
           dbzxEqEquipFact.setPeriodDate(zxEqEquipFact.getPeriodDate());
           // 期次
           dbzxEqEquipFact.setPeriod(DateUtil.format(zxEqEquipFact.getPeriodDate(), "yyyyMM"));
           // 记录人id
           dbzxEqEquipFact.setReporterID(zxEqEquipFact.getReporterID());
           // 记录人
           dbzxEqEquipFact.setReporter(zxEqEquipFact.getReporter());
           // 部门id
           dbzxEqEquipFact.setDepID(zxEqEquipFact.getDepID());
           // 部门name
           dbzxEqEquipFact.setDepName(zxEqEquipFact.getDepName());
           // 记录时间
           dbzxEqEquipFact.setReportDate(zxEqEquipFact.getReportDate());
           // 备注
           dbzxEqEquipFact.setRemark(zxEqEquipFact.getRemark());
           // 所属单位id
           dbzxEqEquipFact.setComID(zxEqEquipFact.getComID());
           // 所属单位name
           dbzxEqEquipFact.setComName(zxEqEquipFact.getComName());
           // 编制时间
           dbzxEqEquipFact.setEditTime(zxEqEquipFact.getEditTime());
           // 复制期次date
           dbzxEqEquipFact.setCopyPeriodDate(zxEqEquipFact.getCopyPeriodDate());
           // 复制期次
           dbzxEqEquipFact.setCopyPeriod(zxEqEquipFact.getCopyPeriod());
           // 共通
           dbzxEqEquipFact.setModifyUserInfo(userKey, realName);
           flag = zxEqEquipFactMapper.updateByPrimaryKey(dbzxEqEquipFact);
         //先删除再新增
           ZxEqEquipFactItem delLib = new ZxEqEquipFactItem();
           delLib.setMainID(zxEqEquipFact.getId());
           List<ZxEqEquipFactItem> delLibList = zxEqEquipFactItemMapper.selectByZxEqEquipFactItemList(delLib);
           if(delLibList != null && delLibList.size() >0) {
        	   delLib.setModifyUserInfo(userKey, realName);
        	   zxEqEquipFactItemMapper.batchDeleteUpdateZxEqEquipFactItem(delLibList, delLib);
           }
           if(zxEqEquipFact.getItemList() != null && zxEqEquipFact.getItemList().size() >0) {	
        	   for (ZxEqEquipFactItem lib : zxEqEquipFact.getItemList()) {
        		   lib.setId(UuidUtil.generate());
        		   lib.setMainID(zxEqEquipFact.getId());
        		   lib.setCreateUserInfo(userKey, realName);
        		   flag = zxEqEquipFactItemMapper.insert(lib);
        	   }
           }
           //复制期次
           ZxEqEquipFactItem factItem = new ZxEqEquipFactItem();
           if(StrUtil.isNotEmpty(zxEqEquipFact.getCopyPeriod())) {
        	   factItem.setMainID(zxEqEquipFact.getCopyPeriod());
        	   List<ZxEqEquipFactItem> factItems = zxEqEquipFactItemMapper.selectByZxEqEquipFactItemList(factItem);
        	   if(factItems != null &&factItems.size() >0) {
//        		   for (ZxEqEquipFactItem zxEqEquipFactItem : factItems) {
//        			   zxEqEquipFactItem.setId(UuidUtil.generate());
//        			   zxEqEquipFactItem.setMainID(zxEqEquipFact.getId());
//        			   zxEqEquipFactItem.setCreateUserInfo(userKey, realName);
//        			   flag = zxEqEquipFactItemMapper.insert(zxEqEquipFactItem);
//        		   }
        	   }
           }
           //先删除再新增
           ZxErpFile delFile = new ZxErpFile();
           delFile.setOtherId(zxEqEquipFact.getId());
           List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
           if(delFileList != null && delFileList.size() >0) {
        	   delFile.setModifyUserInfo(userKey, realName);
        	   zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
           }
           if(zxEqEquipFact.getFileList() != null && zxEqEquipFact.getFileList().size() >0) {
        	   for (ZxErpFile file : zxEqEquipFact.getFileList()) {
        		   file.setUid(UuidUtil.generate());
        		   file.setOtherId(zxEqEquipFact.getId());
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
            return repEntity.ok("sys.data.update",zxEqEquipFact);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqEquipFact(List<ZxEqEquipFact> zxEqEquipFactList) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zxEqEquipFactList != null && zxEqEquipFactList.size() > 0) {
    		for (ZxEqEquipFact zxEqEquipFact : zxEqEquipFactList) {
    			if(StrUtil.equals(zxEqEquipFact.getIsUse(), "1")) {
    				return repEntity.layerMessage("no", "包含已经被引用的数据，不允许删除");
    			}
    		}
    		
    		for (ZxEqEquipFact zxEqEquipFact : zxEqEquipFactList) {
    			ZxEqEquipFactItem delLib = new ZxEqEquipFactItem();
    			delLib.setMainID(zxEqEquipFact.getId());
    			List<ZxEqEquipFactItem> delLibList = zxEqEquipFactItemMapper.selectByZxEqEquipFactItemList(delLib);
    			if(delLibList != null && delLibList.size() >0) {
    				delLib.setModifyUserInfo(userKey, realName);
    				zxEqEquipFactItemMapper.batchDeleteUpdateZxEqEquipFactItem(delLibList, delLib);
    			}
    			ZxErpFile delFile = new ZxErpFile();
    			delFile.setOtherId(zxEqEquipFact.getId());
    			List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
    			if(delFileList != null && delFileList.size() >0) {
    				delFile.setModifyUserInfo(userKey, realName);
    				zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
    			}
    		}
    		ZxEqEquipFact zxEqEquipFact = new ZxEqEquipFact();
    		zxEqEquipFact.setModifyUserInfo(userKey, realName);
    		flag = zxEqEquipFactMapper.batchDeleteUpdateZxEqEquipFact(zxEqEquipFactList, zxEqEquipFact);
    	}
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqEquipFactList);
        }
    }

    @Override
    public ResponseEntity getZxEqEquipFactListForCopy(ZxEqEquipFact zxEqEquipFact) {
    	if (zxEqEquipFact == null) {
    		zxEqEquipFact = new ZxEqEquipFact();
    	}
    	if(StrUtil.isNotEmpty(zxEqEquipFact.getOrgID())) {
    		
    	}
    	
    	
    	// 分页查询
    	PageHelper.startPage(zxEqEquipFact.getPage(),zxEqEquipFact.getLimit());
    	// 获取数据
    	List<ZxEqEquipFact> zxEqEquipFactList = zxEqEquipFactMapper.getZxEqEquipFactListForCopy(zxEqEquipFact);
    	for (ZxEqEquipFact zxEqEquipFact2 : zxEqEquipFactList) {
    		ZxEqEquipFactItem item = new ZxEqEquipFactItem();
    		item.setMainID(zxEqEquipFact2.getId());
    		List<ZxEqEquipFactItem> itemList = zxEqEquipFactItemMapper.selectByZxEqEquipFactItemList(item);

    		int source1 = 0;
    		int source2 = 0;
    		int source3 = 0;
    		if(itemList != null && itemList.size() >0) {
    			for (ZxEqEquipFactItem itemSelect : itemList) {
    				if(StrUtil.equals(itemSelect.getSource(), "0")) {
    					source1 = source1++;
    				}else if(StrUtil.equals(itemSelect.getSource(), "1")) {
    					source2 = source2++;
    				}else if(StrUtil.equals(itemSelect.getSource(), "2")) {
    					source3 = source3++;
    				}
    			}
    		}
    		zxEqEquipFact2.setSource1(source1);
    		zxEqEquipFact2.setSource2(source2);
    		zxEqEquipFact2.setSource3(source3);

    		zxEqEquipFact2.setItemList(itemList);
    		ZxErpFile file = new ZxErpFile();
    		file.setOtherId(zxEqEquipFact2.getId());
    		List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
    		zxEqEquipFact2.setFileList(fileList);
    	}
    	// 得到分页信息
    	PageInfo<ZxEqEquipFact> p = new PageInfo<>(zxEqEquipFactList);

    	return repEntity.okList(zxEqEquipFactList, p.getTotal());
    }

	@Override
	public ResponseEntity auditZxEqEquipFact(ZxEqEquipFact zxEqEquipFact) {
		  HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
	        String userKey = TokenUtils.getUserKey(request);
	        String realName = TokenUtils.getRealName(request);
	        int flag = 0;
	        ZxEqEquipFact dbzxEqEquipFact = zxEqEquipFactMapper.selectByPrimaryKey(zxEqEquipFact.getId());
	        if (dbzxEqEquipFact != null && StrUtil.isNotEmpty(dbzxEqEquipFact.getId())) {
	        	if(StrUtil.equals(dbzxEqEquipFact.getIsUse(), "1")) {
	        		return repEntity.layerMessage("no", "该数据已经被引用，不允许操作");
	        	}
	        	// 审核状态
	        	dbzxEqEquipFact.setAuditStatus(zxEqEquipFact.getAuditStatus());
	        	
	        	if(StrUtil.equals(zxEqEquipFact.getAuditStatus(), "1")) {
	        		dbzxEqEquipFact.setIsUse("1");//是
	        	}
	        	// 共通
	        	dbzxEqEquipFact.setModifyUserInfo(userKey, realName);
	        	flag = zxEqEquipFactMapper.updateByPrimaryKey(dbzxEqEquipFact);
	        	
	        	if(StrUtil.equals(zxEqEquipFact.getAuditStatus(), "1")) {
	        		//审核同时将数据插入到实际月验收中
	        		ZxEqEquipFactReal zxEqEquipFactReal = new ZxEqEquipFactReal();
	        		BeanUtil.copyProperties(zxEqEquipFact, zxEqEquipFactReal);
	    			zxEqEquipFactReal.setId(UuidUtil.generate());
	    			zxEqEquipFactReal.setAuditStatus("0");
	    			zxEqEquipFactReal.setSourceID(zxEqEquipFact.getId());
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
	        // 失败
	        if (flag == 0) {
	            return repEntity.errorSave();
	        }
	        else {
	            return repEntity.ok("sys.data.update",zxEqEquipFact);
	        }
	}
}
