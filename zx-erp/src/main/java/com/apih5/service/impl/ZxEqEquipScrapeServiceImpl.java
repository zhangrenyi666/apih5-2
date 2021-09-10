package com.apih5.service.impl;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxEqEquipMapper;
import com.apih5.mybatis.dao.ZxEqEquipScrapeMapper;
import com.apih5.mybatis.dao.ZxEqUseEquipMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxEqEquip;
import com.apih5.mybatis.pojo.ZxEqEquipScrape;
import com.apih5.mybatis.pojo.ZxEqUseEquip;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxEqEquipScrapeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zxEqEquipScrapeService")
public class ZxEqEquipScrapeServiceImpl implements ZxEqEquipScrapeService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqEquipScrapeMapper zxEqEquipScrapeMapper;
    
    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;
    
    @Autowired(required = true)
    private ZxEqEquipMapper zxEqEquipMapper;
    
    @Autowired(required = true)
    private ZxEqUseEquipMapper zxEqUseEquipMapper;
    

    @Override
    public ResponseEntity getZxEqEquipScrapeListByCondition(ZxEqEquipScrape zxEqEquipScrape) {
        if (zxEqEquipScrape == null) {
            zxEqEquipScrape = new ZxEqEquipScrape();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxEqEquipScrape.setComID("");
        	zxEqEquipScrape.setOrgID("");
        	zxEqEquipScrape.setSeeFlagForJu("未上报之前局看不到");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxEqEquipScrape.setComID(zxEqEquipScrape.getOrgID());
        	zxEqEquipScrape.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxEqEquipScrape.setOrgID(zxEqEquipScrape.getOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxEqEquipScrape.getPage(),zxEqEquipScrape.getLimit());
        // 获取数据
        List<ZxEqEquipScrape> zxEqEquipScrapeList = zxEqEquipScrapeMapper.selectByZxEqEquipScrapeList(zxEqEquipScrape);
        for (ZxEqEquipScrape zxEqEquipScrape2 : zxEqEquipScrapeList) {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(zxEqEquipScrape2.getId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	zxEqEquipScrape2.setFileList(fileList);
        }
        // 得到分页信息
        PageInfo<ZxEqEquipScrape> p = new PageInfo<>(zxEqEquipScrapeList);

        return repEntity.okList(zxEqEquipScrapeList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqEquipScrapeDetails(ZxEqEquipScrape zxEqEquipScrape) {
        if (zxEqEquipScrape == null) {
            zxEqEquipScrape = new ZxEqEquipScrape();
        }
        // 获取数据
        ZxEqEquipScrape dbZxEqEquipScrape = zxEqEquipScrapeMapper.selectByPrimaryKey(zxEqEquipScrape.getId());
        // 数据存在
        if (dbZxEqEquipScrape != null) {
            return repEntity.ok(dbZxEqEquipScrape);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqEquipScrape(ZxEqEquipScrape zxEqEquipScrape) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        if(zxEqEquipScrape.getPeriodDate() != null) {
        	zxEqEquipScrape.setPeriod(DateUtil.format(zxEqEquipScrape.getPeriodDate(), "yyyy-MM"));
        }
        zxEqEquipScrape.setId(UuidUtil.generate());
        zxEqEquipScrape.setAuditStatus("0");
        zxEqEquipScrape.setCreateUserInfo(userKey, realName);
        int flag = zxEqEquipScrapeMapper.insert(zxEqEquipScrape);
        if(zxEqEquipScrape.getFileList() != null && zxEqEquipScrape.getFileList().size() >0) {
        	for (ZxErpFile file : zxEqEquipScrape.getFileList()) {
        		file.setUid(UuidUtil.generate());
        		file.setOtherId(zxEqEquipScrape.getId());
        		file.setCreateUserInfo(userKey, realName);
        		flag = zxErpFileMapper.insert(file);
			}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqEquipScrape);
        }
    }

    @Override
    public ResponseEntity updateZxEqEquipScrape(ZxEqEquipScrape zxEqEquipScrape) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqEquipScrape dbzxEqEquipScrape = zxEqEquipScrapeMapper.selectByPrimaryKey(zxEqEquipScrape.getId());
        if (dbzxEqEquipScrape != null && StrUtil.isNotEmpty(dbzxEqEquipScrape.getId())) {
           // 单位id
           dbzxEqEquipScrape.setOrgID(zxEqEquipScrape.getOrgID());
           // 申请年月
           if(zxEqEquipScrape.getPeriodDate() != null) {
        	   dbzxEqEquipScrape.setPeriodDate(zxEqEquipScrape.getPeriodDate());
        	   dbzxEqEquipScrape.setPeriod(DateUtil.format(zxEqEquipScrape.getPeriodDate(), "yyyy-MM"));
           }
           // 资产id
           dbzxEqEquipScrape.setEquipID(zxEqEquipScrape.getEquipID());
           // 资产名称
           dbzxEqEquipScrape.setEquipName(zxEqEquipScrape.getEquipName());
           // 资产编号
           dbzxEqEquipScrape.setFinanceNo(zxEqEquipScrape.getFinanceNo());
           // 规格形式
           dbzxEqEquipScrape.setSpec(zxEqEquipScrape.getSpec());
           // 制造厂家
           dbzxEqEquipScrape.setFactory(zxEqEquipScrape.getFactory());
           // 发动机号
           dbzxEqEquipScrape.setEnginerno(zxEqEquipScrape.getEnginerno());
           // 底盘系列号
           dbzxEqEquipScrape.setDownserial(zxEqEquipScrape.getDownserial());
           // 出厂日期
           dbzxEqEquipScrape.setOutFactoryDate(zxEqEquipScrape.getOutFactoryDate());
           // 规定使用年限
           dbzxEqEquipScrape.setRequireYear(zxEqEquipScrape.getRequireYear());
           // 实际使用年限
           dbzxEqEquipScrape.setActualYear(zxEqEquipScrape.getActualYear());
           // 资产原值
           dbzxEqEquipScrape.setOrginalValue(zxEqEquipScrape.getOrginalValue());
           // 已提折旧
           dbzxEqEquipScrape.setDeprevalue(zxEqEquipScrape.getDeprevalue());
           // 资产净值
           dbzxEqEquipScrape.setLeftvalue(zxEqEquipScrape.getLeftvalue());
           // 停放地点
           dbzxEqEquipScrape.setPlace(zxEqEquipScrape.getPlace());
           // 车辆牌照号
           dbzxEqEquipScrape.setPassNo(zxEqEquipScrape.getPassNo());
           // 设备现状及报废原因
           dbzxEqEquipScrape.setScrapeReason(zxEqEquipScrape.getScrapeReason());
           // 公司（处）管理部门鉴定审批意见
           dbzxEqEquipScrape.setOption1(zxEqEquipScrape.getOption1());
           // 报废后初步处理意见
           dbzxEqEquipScrape.setHandleway(zxEqEquipScrape.getHandleway());
           // 局管理部门处理意见
           dbzxEqEquipScrape.setOption2(zxEqEquipScrape.getOption2());
//           // 审核状态
//           dbzxEqEquipScrape.setAuditStatus(zxEqEquipScrape.getAuditStatus());
           // 备注
           dbzxEqEquipScrape.setRemark(zxEqEquipScrape.getRemark());
           // 编制时间
           dbzxEqEquipScrape.setEditTime(zxEqEquipScrape.getEditTime());
           // 
           dbzxEqEquipScrape.setApprovalNo(zxEqEquipScrape.getApprovalNo());
           // 报废日期
           dbzxEqEquipScrape.setScrapeDate(zxEqEquipScrape.getScrapeDate());
           // 
           dbzxEqEquipScrape.setComID(zxEqEquipScrape.getComID());
           // 
           dbzxEqEquipScrape.setComName(zxEqEquipScrape.getComName());
           // 单位name
           dbzxEqEquipScrape.setOrgName(zxEqEquipScrape.getOrgName());
           // 共通
           dbzxEqEquipScrape.setModifyUserInfo(userKey, realName);
           flag = zxEqEquipScrapeMapper.updateByPrimaryKey(dbzxEqEquipScrape);
           //先删除再新增
           ZxErpFile delFile = new ZxErpFile();
           delFile.setOtherId(zxEqEquipScrape.getId());
           List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
           if(delFileList != null && delFileList.size() >0) {
        	   delFile.setModifyUserInfo(userKey, realName);
        	   zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
           }
           if(zxEqEquipScrape.getFileList() != null && zxEqEquipScrape.getFileList().size() >0) {
        	   for (ZxErpFile file : zxEqEquipScrape.getFileList()) {
        		   file.setUid(UuidUtil.generate());
        		   file.setOtherId(zxEqEquipScrape.getId());
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
            return repEntity.ok("sys.data.update",zxEqEquipScrape);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqEquipScrape(List<ZxEqEquipScrape> zxEqEquipScrapeList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqEquipScrapeList != null && zxEqEquipScrapeList.size() > 0) {
        	for (ZxEqEquipScrape zxEqEquipScrape : zxEqEquipScrapeList) {
    			ZxErpFile delFile = new ZxErpFile();
    			delFile.setOtherId(zxEqEquipScrape.getId());
    			List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
    			if(delFileList != null && delFileList.size() >0) {
    				delFile.setModifyUserInfo(userKey, realName);
    				zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
    			}
    		}
        	ZxEqEquipScrape zxEqEquipScrape = new ZxEqEquipScrape();
           zxEqEquipScrape.setModifyUserInfo(userKey, realName);
           flag = zxEqEquipScrapeMapper.batchDeleteUpdateZxEqEquipScrape(zxEqEquipScrapeList, zxEqEquipScrape);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqEquipScrapeList);
        }
    }

    @Override
    public ResponseEntity reportZxEqEquipScrape(ZxEqEquipScrape zxEqEquipScrape) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZxEqEquipScrape dbzxEqEquipScrape = zxEqEquipScrapeMapper.selectByPrimaryKey(zxEqEquipScrape.getId());
    	if (dbzxEqEquipScrape != null && StrUtil.isNotEmpty(dbzxEqEquipScrape.getId())) {
    		// 审核状态
    		dbzxEqEquipScrape.setAuditStatus("2");//2已上报
    		// 共通
    		dbzxEqEquipScrape.setModifyUserInfo(userKey, realName);
    		flag = zxEqEquipScrapeMapper.updateByPrimaryKey(dbzxEqEquipScrape);
//    		//上报成功将台账中的数据删除
//    		ZxEqEquip equip = zxEqEquipMapper.selectByPrimaryKey(dbzxEqEquipScrape.getEquipID());
//    		if(equip != null && StrUtil.isNotEmpty(equip.getId())) {
//    			equip.setDelFlag("1");
//    			equip.setModifyUserInfo(userKey, realName);
//    			flag = zxEqEquipMapper.updateByPrimaryKey(equip);
//    		}
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zxEqEquipScrape);
    	}
    }

	@Override
	public ZxEqEquipScrape ureportZxEqEquipScrape(ZxEqEquipScrape zxEqEquipScrape) {
		 if (zxEqEquipScrape == null) {
	            zxEqEquipScrape = new ZxEqEquipScrape();
	        }
	        // 获取数据
	        ZxEqEquipScrape dbZxEqEquipScrape = zxEqEquipScrapeMapper.selectByPrimaryKey(zxEqEquipScrape.getId());
	        
	        return dbZxEqEquipScrape;
	}

	@Override
	public ResponseEntity agreeZxEqEquipScrape(ZxEqEquipScrape zxEqEquipScrape) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZxEqEquipScrape dbzxEqEquipScrape = zxEqEquipScrapeMapper.selectByPrimaryKey(zxEqEquipScrape.getId());
    	if (dbzxEqEquipScrape != null && StrUtil.isNotEmpty(dbzxEqEquipScrape.getId())) {
    		// 审核状态
    		dbzxEqEquipScrape.setAuditStatus("1");//1同意
    		// 共通
    		dbzxEqEquipScrape.setModifyUserInfo(userKey, realName);
    		flag = zxEqEquipScrapeMapper.updateByPrimaryKey(dbzxEqEquipScrape);
    		//审核成功===改上面的status状态，记录报废时间changeDate，并在公司设备台账、项目自有设备台账中过滤掉
    		ZxEqEquip equip = zxEqEquipMapper.selectByPrimaryKey(dbzxEqEquipScrape.getEquipID());
    		if(equip != null && StrUtil.isNotEmpty(equip.getId())) {
    			//update
				equip.setStatus("报废");
				equip.setChangeDate(new Date());
    			equip.setModifyUserInfo(userKey, realName);
    			flag = zxEqEquipMapper.updateByPrimaryKey(equip);
    			//将自有设备del
    			ZxEqUseEquip useEquip = new ZxEqUseEquip();
    			useEquip.setRefEquipID(equip.getId());
    			List<ZxEqUseEquip> useEquipList = zxEqUseEquipMapper.selectByZxEqUseEquipList(useEquip);
    			if(useEquipList != null && useEquipList.size() >0) {
    				useEquipList.get(0).setDelFlag("1");
    				useEquipList.get(0).setModifyUserInfo(userKey, realName);
    				flag = zxEqUseEquipMapper.updateByPrimaryKey(useEquipList.get(0));
    				//del对应的附件
    				ZxErpFile delFile = new ZxErpFile();
    				delFile.setOtherId(useEquipList.get(0).getId());
    				List<ZxErpFile> delFileList= zxErpFileMapper.selectByZxErpFileList(delFile);
    				if(delFileList != null && delFileList.size() >0) {
    					delFile.setModifyUserInfo(userKey, realName);
    					zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
    				}
    			}
    		}
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zxEqEquipScrape);
    	}
	}

	@Override
	public ResponseEntity refuseZxEqEquipScrape(ZxEqEquipScrape zxEqEquipScrape) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZxEqEquipScrape dbzxEqEquipScrape = zxEqEquipScrapeMapper.selectByPrimaryKey(zxEqEquipScrape.getId());
    	if (dbzxEqEquipScrape != null && StrUtil.isNotEmpty(dbzxEqEquipScrape.getId())) {
    		// 审核状态
    		dbzxEqEquipScrape.setAuditStatus("3");//3拒绝
    		// 共通
    		dbzxEqEquipScrape.setModifyUserInfo(userKey, realName);
    		flag = zxEqEquipScrapeMapper.updateByPrimaryKey(dbzxEqEquipScrape);
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zxEqEquipScrape);
    	}
	}
}
