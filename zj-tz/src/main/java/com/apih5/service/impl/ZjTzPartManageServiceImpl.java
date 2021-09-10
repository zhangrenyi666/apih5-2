package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzBaseCodeMapper;
import com.apih5.mybatis.dao.ZjTzDesignFlowMapper;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzPartManageMapper;
import com.apih5.mybatis.pojo.ZjTzBaseCode;
import com.apih5.mybatis.pojo.ZjTzDesignFlow;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzPartManage;
import com.apih5.service.ZjTzPartManageService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zjTzPartManageService")
public class ZjTzPartManageServiceImpl implements ZjTzPartManageService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzPartManageMapper zjTzPartManageMapper;
    
    @Autowired(required = true)
    private ZjTzDesignFlowMapper zjTzDesignFlowMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private ZjTzBaseCodeMapper zjTzBaseCodeMapper;

    @Override
    public ResponseEntity getZjTzPartManageListByCondition(ZjTzPartManage zjTzPartManage) {
     	if (zjTzPartManage == null) {
            zjTzPartManage = new ZjTzPartManage();
        }
     	if(StrUtil.isEmpty(zjTzPartManage.getDesignFlowId())) {
    		return repEntity.okList(null, 0);
    	}
     	
        List<ZjTzPartManage> returnList = new ArrayList<>();
      
        List<ZjTzPartManage> returnzjTzPartManageList = new ArrayList<>();
        //11.中标前 均为黄         22.中标后：   ==实际日期为空时  1. 计划日期>=今日   白色     2. 计划日期<今日     红色      == 实际日期不为空时  蓝色
        List<ZjTzPartManage> zjTzPartManageList = zjTzPartManageMapper.selectByZjTzPartManageList(zjTzPartManage);
       
        boolean bidPartIdFalg = false;//是否有中标时状态
        int orderNum = 0;
        ZjTzPartManage zjTzPartManagebidPartId = new ZjTzPartManage();//是中标时状态的数据
        
        for (ZjTzPartManage zjTzPartManage2selectBidPartId : zjTzPartManageList) {
        	if(StrUtil.equals(zjTzPartManage2selectBidPartId.getBidPartId(), "1")) {
        		bidPartIdFalg = true;
        		orderNum = zjTzPartManage2selectBidPartId.getOrderNum();
        		zjTzPartManagebidPartId = zjTzPartManage2selectBidPartId;
        	}
        }
        
        if(bidPartIdFalg) {
        	//中标前
        	ZjTzPartManage lessRecord = new ZjTzPartManage();
        	lessRecord.setDesignFlowId(zjTzPartManageList.get(0).getDesignFlowId());
        	lessRecord.setOrderNum(orderNum);
        	List<ZjTzPartManage> partLessList = zjTzPartManageMapper.selectByZjTzPartManageListLessOrderNum(lessRecord);
        	if(partLessList != null && partLessList.size() >0) {
        		for (ZjTzPartManage zjTzPartManage3 : partLessList) {
        			zjTzPartManage3.setColorFlag("yellow");
        		}
        		returnzjTzPartManageList.addAll(partLessList);
        	}
        	//中标的数据
        	returnzjTzPartManageList.add(zjTzPartManagebidPartId);
        	//中标后
        	ZjTzPartManage greaterRecord = new ZjTzPartManage();
        	greaterRecord.setDesignFlowId(zjTzPartManageList.get(0).getDesignFlowId());
        	greaterRecord.setOrderNum(orderNum);
        	List<ZjTzPartManage> partGreaterList = zjTzPartManageMapper.selectByZjTzPartManageListGreaterOrderNum(greaterRecord);
        	for (ZjTzPartManage zjTzPartManage3 : partGreaterList) {
        		if(StrUtil.equals(zjTzPartManage3.getFileFlag(), "1")) {
        			zjTzPartManage3.setColorFlag("blue");
                 }else {
                 	if(zjTzPartManage3.getPlanDate() != null) {
                 		if(DateUtil.compare(zjTzPartManage3.getPlanDate(), new Date()) >=0) {
                 			zjTzPartManage3.setColorFlag("white");
                 		}else {
                 			zjTzPartManage3.setColorFlag("red");
                 		}
                 	}else {
                 		zjTzPartManage3.setColorFlag("red");
                 	}
                 }
			}
        	returnzjTzPartManageList.addAll(partGreaterList);
        }else {
        	for (ZjTzPartManage zjTzPartManage2 : zjTzPartManageList) {
        		zjTzPartManage2.setColorFlag("yellow");
			}
        	returnzjTzPartManageList.addAll(zjTzPartManageList);
        }
        for (ZjTzPartManage manage : returnzjTzPartManageList) {
        	 ZjTzFile file = new ZjTzFile();
             file.setOtherId(manage.getPartManageId());
             List<ZjTzFile> files = zjTzFileMapper.selectByZjTzFileList(file);
             manage.setZjTzFileList(files);
		}
        ZjTzPartManage partManage = new ZjTzPartManage();
        partManage.setDesignFlowId(zjTzPartManage.getDesignFlowId());
        returnList.add(partManage);
        returnList.get(0).setZjTzPartManageList(returnzjTzPartManageList);
     	
        return repEntity.ok(returnList);
    }

    @Override
    public ResponseEntity getZjTzPartManageDetails(ZjTzPartManage zjTzPartManage) {
        if (zjTzPartManage == null) {
            zjTzPartManage = new ZjTzPartManage();
        }
        // 获取数据
        ZjTzPartManage dbZjTzPartManage = zjTzPartManageMapper.selectByPrimaryKey(zjTzPartManage.getPartManageId());
        // 数据存在
        if (dbZjTzPartManage != null) {
            return repEntity.ok(dbZjTzPartManage);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzPartManage(ZjTzPartManage zjTzPartManage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //没有操作过的可以删除之后再新增
        ZjTzPartManage partManage = new ZjTzPartManage();
        partManage.setUpdateFlag("1");
        partManage.setDesignFlowId(zjTzPartManage.getDesignFlowId());
        List<ZjTzPartManage> partManages = zjTzPartManageMapper.selectByZjTzPartManageList(partManage);
        if(partManages != null && partManages.size() >0) {
        	return repEntity.layerMessage("no", "已经进行过操作，不能添加！");
        }else {
        	//先删除  再新增
        	ZjTzPartManage partManageDel = new ZjTzPartManage();
        	partManageDel.setDesignFlowId(zjTzPartManage.getDesignFlowId());
        	List<ZjTzPartManage> delPartManages = zjTzPartManageMapper.selectByZjTzPartManageList(partManageDel);
        	if(delPartManages != null && delPartManages.size() >0) {
        		partManageDel.setModifyUserInfo(userKey, realName);
        		zjTzPartManageMapper.batchDeleteUpdateZjTzPartManage(delPartManages, partManageDel);
        	}
        	// 通过BaseCode表查询
        	ZjTzBaseCode baseCode = new ZjTzBaseCode();
    		baseCode.setCodePid(zjTzPartManage.getCodeId());
    		List<ZjTzBaseCode> baseCodeList = zjTzBaseCodeMapper.selectByZjTzBaseCodeList(baseCode);
    		if (baseCodeList != null && baseCodeList.size() > 0) {
    			int i = 1;
    			for (ZjTzBaseCode code : baseCodeList) {
    				zjTzPartManage.setPartManageId(UuidUtil.generate());
    				zjTzPartManage.setDesignPartId(code.getItemId());
    				zjTzPartManage.setDesignPartName(code.getItemName());
    				zjTzPartManage.setOrderNum(i);
    				zjTzPartManage.setLockFlag("0");//未锁定
    				zjTzPartManage.setBidPartId("0");
    				zjTzPartManage.setBidPartName("否");
    				zjTzPartManage.setBuId("0");
    				zjTzPartManage.setBuName("否");
    				zjTzPartManage.setUpdateFlag("0");
    				zjTzPartManage.setFileFlag("0");
    				zjTzPartManage.setCreateUserInfo(userKey, realName);
    				flag = zjTzPartManageMapper.insert(zjTzPartManage);
    				i++;
    			}
    		}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzPartManage);
        }
    }

    @Override
    public ResponseEntity updateZjTzPartManage(ZjTzPartManage zjTzPartManage) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzPartManage dbzjTzPartManage = zjTzPartManageMapper.selectByPrimaryKey(zjTzPartManage.getPartManageId());
        if (dbzjTzPartManage != null && StrUtil.isNotEmpty(dbzjTzPartManage.getPartManageId())) {
//           // 设计流程id
//           dbzjTzPartManage.setDesignFlowId(zjTzPartManage.getDesignFlowId());
//           // 设计流程模板id
//           dbzjTzPartManage.setDesignFlowTempId(zjTzPartManage.getDesignFlowTempId());
//           // 设计流程模板name
//           dbzjTzPartManage.setDesignFlowTempName(zjTzPartManage.getDesignFlowTempName());
           // 设计环节id
           dbzjTzPartManage.setDesignPartId(zjTzPartManage.getDesignPartId());
           // 设计环节name
           dbzjTzPartManage.setDesignPartName(zjTzPartManage.getDesignPartName());
        	if(StrUtil.equals(zjTzPartManage.getBidPartId(), "1")) {
        		if(StrUtil.equals(zjTzPartManage.getBidPartId(), dbzjTzPartManage.getBidPartId())) {
        			
        		}else {
        			ZjTzPartManage partManage = new ZjTzPartManage();
        			partManage.setBidPartId("1");
        			partManage.setDesignFlowId(dbzjTzPartManage.getDesignFlowId());
        			List<ZjTzPartManage> partManages = zjTzPartManageMapper.selectByZjTzPartManageList(partManage);
        			if(partManages != null &&partManages.size() >0) {
        				return repEntity.layerMessage("no", "已有设计环节为中标时环节，不能再次选中！");
        			}
        		}
        	}
           // 中标时环节id
           dbzjTzPartManage.setBidPartId(zjTzPartManage.getBidPartId());
           //更新外层的数据
           ZjTzDesignFlow designFlow = zjTzDesignFlowMapper.selectByPrimaryKey(dbzjTzPartManage.getDesignFlowId());
           // 中标时环节name
           if(StrUtil.equals(zjTzPartManage.getBidPartId(), "1")) {
        	   dbzjTzPartManage.setBidPartName("是");
        	   if(designFlow != null && StrUtil.isNotEmpty(designFlow.getDesignFlowId())) {
        		   designFlow.setDesignPartId(dbzjTzPartManage.getDesignPartId());
        		   designFlow.setDesignPartName(dbzjTzPartManage.getDesignPartName());
        		   designFlow.setModifyUserInfo(userKey, realName);
        		   flag = zjTzDesignFlowMapper.updateByPrimaryKey(designFlow);
        	   }
           }else {
        	   dbzjTzPartManage.setBidPartName("否");
        	   if(designFlow != null && StrUtil.isNotEmpty(designFlow.getDesignFlowId())) {
        		   designFlow.setDesignPartId("");
        		   designFlow.setDesignPartName("");
        		   designFlow.setModifyUserInfo(userKey, realName);
        		   flag = zjTzDesignFlowMapper.updateByPrimaryKey(designFlow);
        	   }
           }
           // 事业部介入id
           dbzjTzPartManage.setBuId(zjTzPartManage.getBuId());
           // 事业部介入name
           if(StrUtil.equals(zjTzPartManage.getBuId(), "1")) {
        	   dbzjTzPartManage.setBuName("是");
           }else {
        	   dbzjTzPartManage.setBuName("否");
           }
           // 计划完成日期
           dbzjTzPartManage.setPlanDate(zjTzPartManage.getPlanDate());
           //是否进行了更新操作标识
           dbzjTzPartManage.setUpdateFlag("1");
           // 共通
           dbzjTzPartManage.setModifyUserInfo(userKey, realName);
           
           //附件先删除再新增
           ZjTzFile file = new ZjTzFile();
           file.setOtherId(zjTzPartManage.getPartManageId());
           List<ZjTzFile> files = zjTzFileMapper.selectByZjTzFileList(file);
           if(files != null && files.size() >0) {
        	   file.setModifyUserInfo(userKey, realName);
        	   flag = zjTzFileMapper.batchDeleteUpdateZjTzFile(files, file);
           }
           List<ZjTzFile> ZjTzFileList = zjTzPartManage.getZjTzFileList();
           if(ZjTzFileList != null && ZjTzFileList.size()>0) {
        	   for(ZjTzFile zjTzFile:ZjTzFileList) {
        		   zjTzFile.setUid(UuidUtil.generate());
        		   zjTzFile.setOtherId(zjTzPartManage.getPartManageId());
        		   zjTzFile.setCreateUserInfo(userKey, realName);
        		   zjTzFileMapper.insert(zjTzFile);
        		   // 实际完成日期
        		   dbzjTzPartManage.setActualDate(zjTzFile.getModifyTime());
        	   }
        	   // 证明材料附件上传标示
               dbzjTzPartManage.setFileFlag("1");
           }else {
        	   // 实际完成日期
    		   dbzjTzPartManage.setActualDate(null);
    		   // 证明材料附件上传标示
        	   dbzjTzPartManage.setFileFlag("0");
           }
           flag = zjTzPartManageMapper.updateByPrimaryKey(dbzjTzPartManage);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzPartManage);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzPartManage(List<ZjTzPartManage> zjTzPartManageList) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zjTzPartManageList != null && zjTzPartManageList.size() > 0) {
    		if(zjTzPartManageList.size() >1) {
    			return repEntity.layerMessage("no", "只能选择一条数据！");
    		}
    		for (ZjTzPartManage partManage : zjTzPartManageList) {
    			
    			//修改的动作
        		ZjTzPartManage partManageSelect = new ZjTzPartManage();
        		partManageSelect.setDesignFlowId(partManage.getDesignFlowId());
        		partManageSelect.setOrderNum(partManage.getOrderNum());
        		List<ZjTzPartManage> updateZjTzPartManageList = zjTzPartManageMapper.selectByZjTzPartManageListGreaterOrderNum(partManageSelect);
        		if(updateZjTzPartManageList != null && updateZjTzPartManageList.size() >0) {
        			partManage.setModifyUserInfo(userKey, realName);
        			zjTzPartManageMapper.batchUpdateZjTzPartManageSubOne(updateZjTzPartManageList, partManage);
        		}
    			ZjTzFile file = new ZjTzFile();
    			file.setOtherId(partManage.getPartManageId());
    			List<ZjTzFile> files = zjTzFileMapper.selectByZjTzFileList(file);
    			if(files != null && files.size() >0) {
    				file.setModifyUserInfo(userKey, realName);
    				flag = zjTzFileMapper.batchDeleteUpdateZjTzFile(files, file);
    			}
    		}
    		ZjTzPartManage zjTzPartManage = new ZjTzPartManage();
    		zjTzPartManage.setModifyUserInfo(userKey, realName);
    		flag = zjTzPartManageMapper.batchDeleteUpdateZjTzPartManage(zjTzPartManageList, zjTzPartManage);
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorDelete();
    	}
    	else {
    		return repEntity.ok("sys.data.delete",zjTzPartManageList);
    	}
    }

    @Override
    public ResponseEntity insertZjTzPartManage(ZjTzPartManage zjTzPartManage) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZjTzPartManage partManage = zjTzPartManageMapper.selectByPrimaryKey(zjTzPartManage.getPartManageId());
    	if(partManage != null && StrUtil.isNotEmpty(zjTzPartManage.getPartManageId())) {
    		//修改的动作
    		ZjTzPartManage partManageSelect = new ZjTzPartManage();
    		partManageSelect.setOrderNum(partManage.getOrderNum());
    		List<ZjTzPartManage> updateZjTzPartManageList = zjTzPartManageMapper.selectByZjTzPartManageListGreaterOrderNum(partManageSelect);
    		if(updateZjTzPartManageList != null && updateZjTzPartManageList.size() >0) {
    			partManage.setModifyUserInfo(userKey, realName);
    			zjTzPartManageMapper.batchUpdateZjTzPartManageAddOne(updateZjTzPartManageList, partManage);
    		}
    		zjTzPartManage.setPartManageId(UuidUtil.generate());
    		zjTzPartManage.setDesignFlowId(partManage.getDesignFlowId());
    		zjTzPartManage.setDesignFlowTempId(partManage.getDesignFlowTempId());
    		zjTzPartManage.setDesignFlowTempName(partManage.getDesignFlowTempName());
    		zjTzPartManage.setOrderNum(partManage.getOrderNum()+1);
    		zjTzPartManage.setDesignPartId(zjTzPartManage.getDesignPartId());
    		zjTzPartManage.setDesignPartName(zjTzPartManage.getDesignPartName());
    		zjTzPartManage.setBidPartId("0");
    		zjTzPartManage.setBidPartName("否");
    		zjTzPartManage.setBuId("0");
    		zjTzPartManage.setBuName("否");
    		zjTzPartManage.setUpdateFlag("1");
    		zjTzPartManage.setFileFlag("0");
    		zjTzPartManage.setLockFlag("0");
    		zjTzPartManage.setCreateUserInfo(userKey, realName);
    		flag = zjTzPartManageMapper.insert(zjTzPartManage);
    	}
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.sava", zjTzPartManage);
    	}
    }

	@Override
	public ResponseEntity batchLockUpdateZjTzPartManage(List<ZjTzPartManage> zjTzPartManageList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zjTzPartManageList != null && zjTzPartManageList.size() > 0) {
    		ZjTzPartManage zjTzPartManage = new ZjTzPartManage();
    		zjTzPartManage.setModifyUserInfo(userKey, realName);
    		flag = zjTzPartManageMapper.batchLockUpdateZjTzPartManage(zjTzPartManageList, zjTzPartManage);
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorUpdate();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zjTzPartManageList);
    	}
	}

	@Override
	public ResponseEntity batchClearUpdateZjTzPartManage(List<ZjTzPartManage> zjTzPartManageList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zjTzPartManageList != null && zjTzPartManageList.size() > 0) {
    		ZjTzPartManage zjTzPartManage = new ZjTzPartManage();
    		zjTzPartManage.setModifyUserInfo(userKey, realName);
    		flag = zjTzPartManageMapper.batchClearUpdateZjTzPartManage(zjTzPartManageList, zjTzPartManage);
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorUpdate();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zjTzPartManageList);
    	}
	}
    
}