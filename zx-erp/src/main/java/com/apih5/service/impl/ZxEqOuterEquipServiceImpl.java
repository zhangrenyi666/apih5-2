package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.service.SysDepartmentService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxEqOuterEquipMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxEqOuterEquip;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxEqOuterEquipService;
import com.apih5.utils.QRCodeUtil;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqOuterEquipService")
public class ZxEqOuterEquipServiceImpl implements ZxEqOuterEquipService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqOuterEquipMapper zxEqOuterEquipMapper;
    
    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;
    
    @ApolloConfig
	private Config config;
    
    @Autowired(required = true)
    private SysDepartmentService sysDepartmentService;

    @Override
    public ResponseEntity getZxEqOuterEquipListByCondition(ZxEqOuterEquip zxEqOuterEquip) {
        if (zxEqOuterEquip == null) {
            zxEqOuterEquip = new ZxEqOuterEquip();
        }
        
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxEqOuterEquip.setComID("");
        	zxEqOuterEquip.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxEqOuterEquip.setComID(zxEqOuterEquip.getOrgID());
        	zxEqOuterEquip.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxEqOuterEquip.setOrgID(zxEqOuterEquip.getOrgID());
        }
        
        // 分页查询
        PageHelper.startPage(zxEqOuterEquip.getPage(),zxEqOuterEquip.getLimit());
        // 获取数据
        List<ZxEqOuterEquip> zxEqOuterEquipList = zxEqOuterEquipMapper.selectByZxEqOuterEquipList(zxEqOuterEquip);
        for (ZxEqOuterEquip zxEqOuterEquip2 : zxEqOuterEquipList) {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(zxEqOuterEquip2.getId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	zxEqOuterEquip2.setFileList(fileList);
		}
        // 得到分页信息
        PageInfo<ZxEqOuterEquip> p = new PageInfo<>(zxEqOuterEquipList);

        return repEntity.okList(zxEqOuterEquipList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqOuterEquipDetails(ZxEqOuterEquip zxEqOuterEquip) {
        if (zxEqOuterEquip == null) {
            zxEqOuterEquip = new ZxEqOuterEquip();
        }
        // 获取数据
        ZxEqOuterEquip dbZxEqOuterEquip = zxEqOuterEquipMapper.selectByPrimaryKey(zxEqOuterEquip.getId());
        // 数据存在
        if (dbZxEqOuterEquip != null) {
            return repEntity.ok(dbZxEqOuterEquip);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqOuterEquip(ZxEqOuterEquip zxEqOuterEquip) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	zxEqOuterEquip.setId(UuidUtil.generate());
    	//生成二维码
    	String qrcodeContent = config.getProperty("ng.web.url", "") + "regulatoryFrameworkMobile/#/regulatoryFrameworkMobile/RegulatoryFrameDetail/" + zxEqOuterEquip.getId();
    	zxEqOuterEquip.setQrcodeContent(qrcodeContent);
    	String qrcodeName = "";
    	try {
    		qrcodeName = QRCodeUtil.encode(qrcodeContent, HttpUtil.getUploadPath("zx-erp"));
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	zxEqOuterEquip.setQrcodeName(qrcodeName);
    	zxEqOuterEquip.setQrcodeUrl(HttpUtil.getUploadPathRelative("zx-erp") + qrcodeName);
    	zxEqOuterEquip.setQrcodeDownUrl("downloadFile?filePath="+ zxEqOuterEquip.getQrcodeUrl() +"&downName=" + qrcodeName);
    	zxEqOuterEquip.setCreateUserInfo(userKey, realName);
    	int flag = zxEqOuterEquipMapper.insert(zxEqOuterEquip);
    	if(zxEqOuterEquip.getFileList() != null && zxEqOuterEquip.getFileList().size() >0) {
    		for (ZxErpFile file : zxEqOuterEquip.getFileList()) {
    			file.setUid(UuidUtil.generate());
    			file.setOtherId(zxEqOuterEquip.getId());
    			file.setCreateUserInfo(userKey, realName);
    			flag = zxErpFileMapper.insert(file);
    		}
    	}
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.sava", zxEqOuterEquip);
    	}
    }

    @Override
    public ResponseEntity updateZxEqOuterEquip(ZxEqOuterEquip zxEqOuterEquip) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqOuterEquip dbzxEqOuterEquip = zxEqOuterEquipMapper.selectByPrimaryKey(zxEqOuterEquip.getId());
        if (dbzxEqOuterEquip != null && StrUtil.isNotEmpty(dbzxEqOuterEquip.getId())) {
           // 单位名称
           dbzxEqOuterEquip.setOrgID(zxEqOuterEquip.getOrgID());
           // 设备名称
           dbzxEqOuterEquip.setEquipName(zxEqOuterEquip.getEquipName());
           // 型号
           dbzxEqOuterEquip.setSpec(zxEqOuterEquip.getSpec());
           // 规格
           dbzxEqOuterEquip.setModel(zxEqOuterEquip.getModel());
           // 功率(KW)
           dbzxEqOuterEquip.setPower(zxEqOuterEquip.getPower());
           // 生产厂家
           dbzxEqOuterEquip.setOutfactory(zxEqOuterEquip.getOutfactory());
           // 出产日期
           dbzxEqOuterEquip.setOutfactoryDate(zxEqOuterEquip.getOutfactoryDate());
           // 原值
           dbzxEqOuterEquip.setOrginalValue(zxEqOuterEquip.getOrginalValue());
           // 净值
           dbzxEqOuterEquip.setLeftValue(zxEqOuterEquip.getLeftValue());
           // 使用地点
           dbzxEqOuterEquip.setPlace(zxEqOuterEquip.getPlace());
           // 租赁期限（月）
           dbzxEqOuterEquip.setLeaseLimit(zxEqOuterEquip.getLeaseLimit());
           // 租赁价格（元/月）
           dbzxEqOuterEquip.setLeaseprice(zxEqOuterEquip.getLeaseprice());
           // 租赁公司
           dbzxEqOuterEquip.setSupplierID(zxEqOuterEquip.getSupplierID());
           // 租赁公司
           dbzxEqOuterEquip.setSupplierMaster(zxEqOuterEquip.getSupplierMaster());
           // 机主姓名
           dbzxEqOuterEquip.setOperator(zxEqOuterEquip.getOperator());
           // 入场时间
           dbzxEqOuterEquip.setInDate(zxEqOuterEquip.getInDate());
           // 退场时间
           dbzxEqOuterEquip.setOutDate(zxEqOuterEquip.getOutDate());
           // 备注
           dbzxEqOuterEquip.setRemark(zxEqOuterEquip.getRemark());
           // 审核状态
           dbzxEqOuterEquip.setState(zxEqOuterEquip.getState());
           // 合同id
           dbzxEqOuterEquip.setContrID(zxEqOuterEquip.getContrID());
           // 合同明细id
           dbzxEqOuterEquip.setContrItem(zxEqOuterEquip.getContrItem());
           // 编号
           dbzxEqOuterEquip.setEquipNo(zxEqOuterEquip.getEquipNo());
           // 
           dbzxEqOuterEquip.setContrItemID(zxEqOuterEquip.getContrItemID());
           // 牌照号
           dbzxEqOuterEquip.setPassNo(zxEqOuterEquip.getPassNo());
           // 开竣工日期
           dbzxEqOuterEquip.setStartEndDate(zxEqOuterEquip.getStartEndDate());
           // comid
           dbzxEqOuterEquip.setComID(zxEqOuterEquip.getComID());
           // 公司名称
           dbzxEqOuterEquip.setComName(zxEqOuterEquip.getComName());
           // 验收总体评价
           dbzxEqOuterEquip.setAcceptance(zxEqOuterEquip.getAcceptance());
           // 是否特种设备
           dbzxEqOuterEquip.setIsSepcial(zxEqOuterEquip.getIsSepcial());
           // 特种设备检验报告
           dbzxEqOuterEquip.setInspReport(zxEqOuterEquip.getInspReport());
           // 特种设备使用登记证
           dbzxEqOuterEquip.setInspCert(zxEqOuterEquip.getInspCert());
           // 操作人员证
           dbzxEqOuterEquip.setOpCert(zxEqOuterEquip.getOpCert());
           // 是否退场
           dbzxEqOuterEquip.setIsOut(zxEqOuterEquip.getIsOut());
           // 共通
           dbzxEqOuterEquip.setModifyUserInfo(userKey, realName);
           flag = zxEqOuterEquipMapper.updateByPrimaryKey(dbzxEqOuterEquip);
           //先删除再新增
           ZxErpFile delFile = new ZxErpFile();
           delFile.setOtherId(zxEqOuterEquip.getId());
           List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
           if(delFileList != null && delFileList.size() >0) {
        	   delFile.setModifyUserInfo(userKey, realName);
        	   zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
           }
           if(zxEqOuterEquip.getFileList() != null && zxEqOuterEquip.getFileList().size() >0) {
        	   for (ZxErpFile file : zxEqOuterEquip.getFileList()) {
        		   file.setUid(UuidUtil.generate());
        		   file.setOtherId(zxEqOuterEquip.getId());
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
            return repEntity.ok("sys.data.update",zxEqOuterEquip);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqOuterEquip(List<ZxEqOuterEquip> zxEqOuterEquipList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqOuterEquipList != null && zxEqOuterEquipList.size() > 0) {
        	for (ZxEqOuterEquip zxEqOuterEquip : zxEqOuterEquipList) {
    			ZxErpFile delFile = new ZxErpFile();
    			delFile.setOtherId(zxEqOuterEquip.getId());
    			List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
    			if(delFileList != null && delFileList.size() >0) {
    				delFile.setModifyUserInfo(userKey, realName);
    				zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
    			}
    		}
        	ZxEqOuterEquip zxEqOuterEquip = new ZxEqOuterEquip();
           zxEqOuterEquip.setModifyUserInfo(userKey, realName);
           flag = zxEqOuterEquipMapper.batchDeleteUpdateZxEqOuterEquip(zxEqOuterEquipList, zxEqOuterEquip);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqOuterEquipList);
        }
    }
    
    @Override
	public ResponseEntity getZxEqOuterEquipListForRecord(ZxEqOuterEquip zxEqOuterEquip) {
    	if (zxEqOuterEquip == null) {
    		zxEqOuterEquip = new ZxEqOuterEquip();
    	}
    	String ext1 = "";
    	SysDepartment sysDepartment = sysDepartmentService.getSysDeptProByPrimaryKey(zxEqOuterEquip.getOrgID());
    	if(sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getDepartmentId())) {
    		if(StrUtil.equals(sysDepartment.getDepartmentFlag(), "1")) {
    			ext1 = "1";
    		}else if(StrUtil.equals(sysDepartment.getDepartmentFlag(), "2")) {
    			ext1 = "2";
    		}
    	}
    	SysDepartment sysProject =  sysDepartmentService.getSysProjectPrimaryKeyByMapper(zxEqOuterEquip.getOrgID());
    	if(sysProject != null && StrUtil.isNotEmpty(sysProject.getDepartmentId())) {
    		if(StrUtil.equals(sysProject.getDepartmentFlag(), "3")) {
    			ext1 = "3";
    		}else if(StrUtil.equals(sysProject.getDepartmentFlag(), "4")) {
    			ext1 = "4";
    		}
    	}
    	// 集团全部可见
    	if(StrUtil.equals("1", ext1)) {
    		zxEqOuterEquip.setComID("");
    		zxEqOuterEquip.setUseOrgID("");
    	} else if(StrUtil.equals("2", ext1)) {
    		// 公司只看见自己的
    		zxEqOuterEquip.setComID(zxEqOuterEquip.getOrgID());
    		zxEqOuterEquip.setOrgID("");
    	} else if(StrUtil.equals("3", ext1) || StrUtil.equals("4", ext1)) {
    		// 项目通过右上角数据
    		zxEqOuterEquip.setOrgID(zxEqOuterEquip.getOrgID());//使用单位
    	}


    	// 分页查询
    	PageHelper.startPage(zxEqOuterEquip.getPage(),zxEqOuterEquip.getLimit());
    	// 获取数据
    	List<ZxEqOuterEquip> zxEqOuterEquipList = zxEqOuterEquipMapper.selectByZxEqOuterEquipList(zxEqOuterEquip);
    	for (ZxEqOuterEquip zxEqOuterEquip2 : zxEqOuterEquipList) {
    		ZxErpFile file = new ZxErpFile();
    		file.setOtherId(zxEqOuterEquip2.getId());
    		List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
    		zxEqOuterEquip2.setFileList(fileList);
    	}
    	// 得到分页信息
    	PageInfo<ZxEqOuterEquip> p = new PageInfo<>(zxEqOuterEquipList);

    	return repEntity.okList(zxEqOuterEquipList, p.getTotal());
    }
    
    @Override
    public ResponseEntity getZxEqOuterEquipListForCar(ZxEqOuterEquip zxEqOuterEquip) {
        // 分页查询
        PageHelper.startPage(zxEqOuterEquip.getPage(),zxEqOuterEquip.getLimit());
        // 获取数据
        List<ZxEqOuterEquip> zxEqOuterEquipList = zxEqOuterEquipMapper.getZxEqOuterEquipListForCar(zxEqOuterEquip);
        // 得到分页信息
        PageInfo<ZxEqOuterEquip> p = new PageInfo<>(zxEqOuterEquipList);

        return repEntity.okList(zxEqOuterEquipList, p.getTotal());
    }

    @Override
    public List<ZxEqOuterEquip> reportZuLinCLForm(ZxEqOuterEquip zxEqOuterEquip) {
        if (zxEqOuterEquip == null) {
            zxEqOuterEquip = new ZxEqOuterEquip();
        }
        // 获取数据
        List<ZxEqOuterEquip> zxEqOuterEquipList = zxEqOuterEquipMapper.getZxEqOuterEquipListForCar(zxEqOuterEquip);
        return zxEqOuterEquipList;
    }

}