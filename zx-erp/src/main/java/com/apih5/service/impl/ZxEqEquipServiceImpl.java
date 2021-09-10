package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.service.SysDepartmentService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxEqCooEquipItemMapper;
import com.apih5.mybatis.dao.ZxEqCooEquipMapper;
import com.apih5.mybatis.dao.ZxEqEquipMapper;
import com.apih5.mybatis.dao.ZxEqOuterEquipMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxEqCooEquip;
import com.apih5.mybatis.pojo.ZxEqCooEquipItem;
import com.apih5.mybatis.pojo.ZxEqEquip;
import com.apih5.mybatis.pojo.ZxEqOuterEquip;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxEqEquipService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zxEqEquipService")
public class ZxEqEquipServiceImpl implements ZxEqEquipService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqEquipMapper zxEqEquipMapper;
    
    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;
    
    @Autowired(required = true)
    private ZxEqOuterEquipMapper zxEqOuterEquipMapper;

    @Autowired(required = true)
    private ZxEqCooEquipMapper zxEqCooEquipMapper;

    @Autowired(required = true)
    private ZxEqCooEquipItemMapper zxEqCooEquipItemMapper;
    
    @Autowired(required = true)
    private SysDepartmentService sysDepartmentService;

    @Override
    public ResponseEntity getZxEqEquipListByCondition(ZxEqEquip zxEqEquip) {
        if (zxEqEquip == null) {
            zxEqEquip = new ZxEqEquip();
        }
        
        if(StrUtil.equals(zxEqEquip.getUreportFlag(), "comCar")) {//公司车辆台账 
        	
        }else if(StrUtil.equals(zxEqEquip.getUreportFlag(), "comCarForMech")) {//公司机械设备台账
        	
        }else {
        	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
            String userId = TokenUtils.getUserId(request);
            String ext1 = TokenUtils.getExt1(request);
            // 集团全部可见
            if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                    || StrUtil.equals("admin", userId)) {
            	zxEqEquip.setCompanyId("");
            	zxEqEquip.setUseOrgID("");
            } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
                // 公司只看见自己的
            	zxEqEquip.setCompanyId(zxEqEquip.getUseOrgID());
            	zxEqEquip.setUseOrgID("");
            } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                    || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
                // 项目通过右上角数据
            	zxEqEquip.setUseOrgID(zxEqEquip.getUseOrgID());//使用单位
            }	
        }
        
        // 分页查询
        PageHelper.startPage(zxEqEquip.getPage(),zxEqEquip.getLimit());
        // 获取数据
        List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.selectByZxEqEquipList(zxEqEquip);
        for (ZxEqEquip zxEqEquip2 : zxEqEquipList) {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(zxEqEquip2.getId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	zxEqEquip2.setFileList(fileList);
		}
        // 得到分页信息
        PageInfo<ZxEqEquip> p = new PageInfo<>(zxEqEquipList);

        return repEntity.okList(zxEqEquipList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqEquipDetails(ZxEqEquip zxEqEquip) {
        if (zxEqEquip == null) {
            zxEqEquip = new ZxEqEquip();
        }
        // 获取数据
        ZxEqEquip dbZxEqEquip = zxEqEquipMapper.selectByPrimaryKey(zxEqEquip.getId());
        // 数据存在
        if (dbZxEqEquip != null) {
            return repEntity.ok(dbZxEqEquip);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqEquip(ZxEqEquip zxEqEquip) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqEquip.setId(UuidUtil.generate());
        zxEqEquip.setCreateUserInfo(userKey, realName);
        int flag = zxEqEquipMapper.insert(zxEqEquip);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqEquip);
        }
    }

    @Override
    public ResponseEntity updateZxEqEquip(ZxEqEquip zxEqEquip) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqEquip dbzxEqEquip = zxEqEquipMapper.selectByPrimaryKey(zxEqEquip.getId());
        if (dbzxEqEquip != null && StrUtil.isNotEmpty(dbzxEqEquip.getId())) {
           // abc分类
           dbzxEqEquip.setAbcType(zxEqEquip.getAbcType());
           // 机种分类
           dbzxEqEquip.setResCatalogID(zxEqEquip.getResCatalogID());
           // 机种分类
           dbzxEqEquip.setIsWorkEquip(zxEqEquip.getIsWorkEquip());
           // 国产进口
           dbzxEqEquip.setIsMadeinChina(zxEqEquip.getIsMadeinChina());
           // 财务固定资产编号
           dbzxEqEquip.setFinanceNo(zxEqEquip.getFinanceNo());
           // 使用单位
           dbzxEqEquip.setUseOrgID(zxEqEquip.getUseOrgID());
           // 购置日期
           dbzxEqEquip.setPurDate(zxEqEquip.getPurDate());
           // 所在地点
           dbzxEqEquip.setUsePlace(zxEqEquip.getUsePlace());
           // 所属单位
           dbzxEqEquip.setOwnerOrgId(zxEqEquip.getOwnerOrgId());
           // 管理单位
           dbzxEqEquip.setManageOrgID(zxEqEquip.getManageOrgID());
           // 设备名称
           dbzxEqEquip.setEquipName(zxEqEquip.getEquipName());
           // 规格
           dbzxEqEquip.setSpec(zxEqEquip.getSpec());
           // 型号
           dbzxEqEquip.setModel(zxEqEquip.getModel());
           // 总功率（KW）
           dbzxEqEquip.setPower(zxEqEquip.getPower());
           // 来源
           dbzxEqEquip.setSource(zxEqEquip.getSource());
           // 副机系列号
           dbzxEqEquip.setViceserial(zxEqEquip.getViceserial());
           // 底盘厂家
           dbzxEqEquip.setDownfactory(zxEqEquip.getDownfactory());
           // 副机出厂日期
           dbzxEqEquip.setViceoutfactory(zxEqEquip.getViceoutfactory());
           // 副机功率（KW）
           dbzxEqEquip.setVicepower(zxEqEquip.getVicepower());
           // 副机型号
           dbzxEqEquip.setVicespec(zxEqEquip.getVicespec());
           // 副机厂牌
           dbzxEqEquip.setViceFactory(zxEqEquip.getViceFactory());
           // 主机功率（KW）
           dbzxEqEquip.setMainpower(zxEqEquip.getMainpower());
           // 主机出厂日期
           dbzxEqEquip.setMainoutfactory(zxEqEquip.getMainoutfactory());
           // 主机系列号
           dbzxEqEquip.setMainserial(zxEqEquip.getMainserial());
           // 主机型号
           dbzxEqEquip.setMainspec(zxEqEquip.getMainspec());
           // 主机厂牌
           dbzxEqEquip.setMainFactory(zxEqEquip.getMainFactory());
           // 底盘型号
           dbzxEqEquip.setDownspec(zxEqEquip.getDownspec());
           // 底盘系列号
           dbzxEqEquip.setDownserial(zxEqEquip.getDownserial());
           // 底盘出厂日期
           dbzxEqEquip.setDownoutfactory(zxEqEquip.getDownoutfactory());
           // 外型尺寸长宽高
           dbzxEqEquip.setHeightlong(zxEqEquip.getHeightlong());
           // 自重（t)
           dbzxEqEquip.setWeight(zxEqEquip.getWeight());
           // 原值
           dbzxEqEquip.setOrginalValue(zxEqEquip.getOrginalValue());
           // 折旧导入月份
           dbzxEqEquip.setDepreImportmonth(zxEqEquip.getDepreImportmonth());
           // 净值
           dbzxEqEquip.setLeftValue(zxEqEquip.getLeftValue());
           // 引进fob价
           dbzxEqEquip.setFobPrice(zxEqEquip.getFobPrice());
           // 引进总价
           dbzxEqEquip.setFobAmount(zxEqEquip.getFobAmount());
           // 引进总折价
           dbzxEqEquip.setDiscountAmount(zxEqEquip.getDiscountAmount());
           // 验收时间
           dbzxEqEquip.setAcceptanceDate(zxEqEquip.getAcceptanceDate());
           // 牌照号码
           dbzxEqEquip.setPassNo(zxEqEquip.getPassNo());
           // 当前状态
           dbzxEqEquip.setStatus(zxEqEquip.getStatus());
           // 出厂日期
           dbzxEqEquip.setOutFactoryDate(zxEqEquip.getOutFactoryDate());
           // 出厂系列号
           dbzxEqEquip.setOutFactorySerial(zxEqEquip.getOutFactorySerial());
           // 重点设备标志
           dbzxEqEquip.setIsimportant(zxEqEquip.getIsimportant());
           // 状态发生日期
           dbzxEqEquip.setChangeDate(zxEqEquip.getChangeDate());
           // 备注
           dbzxEqEquip.setRemark(zxEqEquip.getRemark());
           // 已提折旧
           dbzxEqEquip.setActualDepreAmt(zxEqEquip.getActualDepreAmt());
           // 机械管理编号
           dbzxEqEquip.setEquipNo(zxEqEquip.getEquipNo());
           // 国家厂家
           dbzxEqEquip.setFactory(zxEqEquip.getFactory());
           // 确认状态
           dbzxEqEquip.setCheckStatus(zxEqEquip.getCheckStatus());
           // 是否销账
           dbzxEqEquip.setIsDelete(zxEqEquip.getIsDelete());
           // 主机功率（KW）
           dbzxEqEquip.setMainPowerStr(zxEqEquip.getMainPowerStr());
           // 付机功率（KW）
           dbzxEqEquip.setVicePowerStr(zxEqEquip.getVicePowerStr());
           // 入账日期
           dbzxEqEquip.setRegdate(zxEqEquip.getRegdate());
           // 计划批文号
           dbzxEqEquip.setPlanNo(zxEqEquip.getPlanNo());
           // 
           dbzxEqEquip.setPlancode(zxEqEquip.getPlancode());
           // 
           dbzxEqEquip.setAttachmentID(zxEqEquip.getAttachmentID());
           // 共通
           dbzxEqEquip.setModifyUserInfo(userKey, realName);
           flag = zxEqEquipMapper.updateByPrimaryKey(dbzxEqEquip);
           
           //先删除再新增
           ZxErpFile delFile = new ZxErpFile();
           delFile.setOtherId(zxEqEquip.getId());
           List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
           if(delFileList != null && delFileList.size() >0) {
        	   delFile.setModifyUserInfo(userKey, realName);
        	   zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
           }
           if(zxEqEquip.getFileList() != null && zxEqEquip.getFileList().size() >0) {
        	   for (ZxErpFile file : zxEqEquip.getFileList()) {
        		   file.setUid(UuidUtil.generate());
        		   file.setOtherId(zxEqEquip.getId());
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
            return repEntity.ok("sys.data.update",zxEqEquip);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqEquip(List<ZxEqEquip> zxEqEquipList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqEquipList != null && zxEqEquipList.size() > 0) {
        	for (ZxEqEquip zxEqEquip : zxEqEquipList) {
        		ZxErpFile delFile = new ZxErpFile();
        		delFile.setOtherId(zxEqEquip.getId());
        		List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
        		if(delFileList != null && delFileList.size() >0) {
        			delFile.setModifyUserInfo(userKey, realName);
        			zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
        		}
        	}
        	ZxEqEquip zxEqEquip = new ZxEqEquip();
        	zxEqEquip.setModifyUserInfo(userKey, realName);
        	flag = zxEqEquipMapper.batchDeleteUpdateZxEqEquip(zxEqEquipList, zxEqEquip);
        }
        // 失败
        if (flag == 0) {
        	return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqEquipList);
        }
    }
    
    @Override
	public ResponseEntity unusedZxEqEquip(ZxEqEquip zxEqEquip) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZxEqEquip dbzxEqEquip = zxEqEquipMapper.selectByPrimaryKey(zxEqEquip.getId());
    	if (dbzxEqEquip != null && StrUtil.isNotEmpty(dbzxEqEquip.getId())) {
    		// 是否闲置
    		dbzxEqEquip.setIsXianzhi("1");
    		// 共通
    		dbzxEqEquip.setModifyUserInfo(userKey, realName);
    		flag = zxEqEquipMapper.updateByPrimaryKey(dbzxEqEquip);
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zxEqEquip);
    	}
	}

    @Override
    public ResponseEntity recoverZxEqEquip(ZxEqEquip zxEqEquip) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZxEqEquip dbzxEqEquip = zxEqEquipMapper.selectByPrimaryKey(zxEqEquip.getId());
    	if (dbzxEqEquip != null && StrUtil.isNotEmpty(dbzxEqEquip.getId())) {
    		// 是否闲置
    		dbzxEqEquip.setIsXianzhi("0");
    		// 共通
    		dbzxEqEquip.setModifyUserInfo(userKey, realName);
    		flag = zxEqEquipMapper.updateByPrimaryKey(dbzxEqEquip);
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zxEqEquip);
    	}
    }

    @Override
    public ResponseEntity getZxEqEquipListForReport(ZxEqEquip zxEqEquip) {
    	if (zxEqEquip == null) {
    		zxEqEquip = new ZxEqEquip();
    	}
    	
//    	if(StrUtil.equals(zxEqEquip.getr, str2)) {
//    		
//    	}
    	// 分页查询
    	PageHelper.startPage(zxEqEquip.getPage(),zxEqEquip.getLimit());
    	// 获取数据
    	List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.getZxEqEquipListForReport(zxEqEquip);
    	// 得到分页信息
    	PageInfo<ZxEqEquip> p = new PageInfo<>(zxEqEquipList);

    	return repEntity.okList(zxEqEquipList, p.getTotal());
    }
    
	@Override
	public List<ZxEqEquip> ureportZxEqEquipList(ZxEqEquip zxEqEquip) {
		if (zxEqEquip == null) {
			zxEqEquip = new ZxEqEquip();
		}
		if(StrUtil.isNotEmpty(zxEqEquip.getOrgID())) {//所属单位
			zxEqEquip.setOwnerOrgId(zxEqEquip.getOrgID());
		}
		if(StrUtil.isNotEmpty(zxEqEquip.getOutOrgID())) {//使用单位
			zxEqEquip.setUseOrgID(zxEqEquip.getOutOrgID());
		}
		// 获取数据
		List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.getZxEqEquipListForReport(zxEqEquip);
		return zxEqEquipList;
	}
	
	@Override
	public List<ZxEqEquip> ureportZxEqEquipListIdle(ZxEqEquip zxEqEquip) {
		if (zxEqEquip == null) {
			zxEqEquip = new ZxEqEquip();
		}
		if(StrUtil.isNotEmpty(zxEqEquip.getOrgID())) {//所属单位
			zxEqEquip.setOwnerOrgId(zxEqEquip.getOrgID());
		}
		if(StrUtil.isNotEmpty(zxEqEquip.getOutOrgID())) {//使用单位
			zxEqEquip.setUseOrgID(zxEqEquip.getOutOrgID());
		}
		
		// 获取数据
		List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.getZxEqEquipListForReportIdle(zxEqEquip);
		for(ZxEqEquip ZxEqEquip1 :zxEqEquipList) {
			if(StrUtil.isNotEmpty(ZxEqEquip1.getSource())) {
				if(ZxEqEquip1.getSource().equals("0")) {
					ZxEqEquip1.setSource("自购");
				}
				
				ZxEqEquip1.setAcceptanceDateStr(DateUtil.formatDateTime(ZxEqEquip1.getAcceptanceDate()));
				ZxEqEquip1.setOutFactoryDateStr(DateUtil.formatDateTime(ZxEqEquip1.getOutFactoryDate()));
				ZxEqEquip1.setDownoutfactoryStr(DateUtil.formatDateTime(ZxEqEquip1.getDownoutfactory()));
				ZxEqEquip1.setMainoutfactoryStr(DateUtil.formatDateTime(ZxEqEquip1.getMainoutfactory()));
				ZxEqEquip1.setViceoutfactoryStr(DateUtil.formatDateTime(ZxEqEquip1.getViceoutfactory()));
				if(ZxEqEquip1.getIsMadeinChina().equals("0")) {
					ZxEqEquip1.setIsMadeinChina("国产");
				}else if(ZxEqEquip1.getIsMadeinChina().equals("1")) {
					ZxEqEquip1.setIsMadeinChina("进口");
				}
			}
			
		}
		
		return zxEqEquipList;
	}

    @Override
    public List<ZxEqEquip> ureportZxEqEquipListVehicle(ZxEqEquip zxEqEquip) {
        if (zxEqEquip == null) {
            zxEqEquip = new ZxEqEquip();
        }
        // 获取数据
        List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.getZxEqEquipListVehicleForReport(zxEqEquip);
        return zxEqEquipList;
    }

    @Override
    public List<ZxEqEquip> ureportZxEqEquipListMechanics(ZxEqEquip zxEqEquip) {
        if (zxEqEquip == null) {
            zxEqEquip = new ZxEqEquip();
        }
        // 获取数据
        List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.getZxEqEquipListMechanicsForReport(zxEqEquip);
        return zxEqEquipList;
    }

	@Override
	public ResponseEntity getZxEqEquipListForRealFact(ZxEqEquip zxEqEquip) {
		   if (zxEqEquip == null) {
	            zxEqEquip = new ZxEqEquip();
	        }
		   if(StrUtil.equals(zxEqEquip.getSource(), "3")) {//协作队伍自带
			   List<ZxEqCooEquipItem> zxEqCooEquipItemList = new ArrayList<>();
			   ZxEqCooEquip zxEqCooEquip = new ZxEqCooEquip();
			   zxEqCooEquip.setOrgID(zxEqEquip.getOrgID());
			   List<ZxEqCooEquip> zxEqCooEquipList = zxEqCooEquipMapper.selectByZxEqCooEquipList(zxEqCooEquip);
		        for (ZxEqCooEquip zxEqCooEquip2 : zxEqCooEquipList) {
		        	ZxEqCooEquipItem item = new ZxEqCooEquipItem();
		        	item.setMasID(zxEqCooEquip2.getId());
		        	List<ZxEqCooEquipItem> itemList = zxEqCooEquipItemMapper.selectByZxEqCooEquipItemList(item);
		        	zxEqCooEquipItemList.addAll(itemList);
		        }
		        return repEntity.ok(zxEqCooEquipItemList);
		   }else if(StrUtil.equals(zxEqEquip.getSource(), "2")) {//租赁
			   ZxEqOuterEquip zxEqOuterEquip = new ZxEqOuterEquip();
			   zxEqOuterEquip.setOrgID(zxEqEquip.getOrgID());
			   // 分页查询
			   PageHelper.startPage(zxEqOuterEquip.getPage(),zxEqOuterEquip.getLimit());
			   // 获取数据
			   List<ZxEqOuterEquip> zxEqOuterEquipList = zxEqOuterEquipMapper.selectByZxEqOuterEquipList(zxEqOuterEquip);
			   // 得到分页信息
			   PageInfo<ZxEqOuterEquip> p = new PageInfo<>(zxEqOuterEquipList);

			   return repEntity.okList(zxEqOuterEquipList, p.getTotal());

		   }else if(StrUtil.equals(zxEqEquip.getSource(), "1")) {//自购
			   if(StrUtil.isNotEmpty(zxEqEquip.getOrgID())) {//使用单位
				   zxEqEquip.setUseOrgID(zxEqEquip.getOrgID());
			   }
			   zxEqEquip.setSource("");
			   // 分页查询
			   PageHelper.startPage(zxEqEquip.getPage(),zxEqEquip.getLimit());
			   // 获取数据
			   List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.selectByZxEqEquipList(zxEqEquip);
			   // 得到分页信息
			   PageInfo<ZxEqEquip> p = new PageInfo<>(zxEqEquipList);
			   return repEntity.okList(zxEqEquipList, p.getTotal());
		   }
		   return repEntity.okList(null, 0);
	}

    @Override
    public ResponseEntity ureportZxEqEquipListMechanicsIdle(ZxEqEquip zxEqEquip) {
        if (zxEqEquip == null) {
            zxEqEquip = new ZxEqEquip();
        }

        // 分页查询
        PageHelper.startPage(zxEqEquip.getPage(),zxEqEquip.getLimit());
        // 获取数据
        List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.getZxEqEquipListMechanicsForReport(zxEqEquip);
       
        // 得到分页信息
        PageInfo<ZxEqEquip> p = new PageInfo<>(zxEqEquipList);

        return repEntity.okList(zxEqEquipList, p.getTotal());
    }
    
    @Override
    public ResponseEntity ureportZxEqEquipListVehicleIdle(ZxEqEquip zxEqEquip) {
        if (zxEqEquip == null) {
            zxEqEquip = new ZxEqEquip();
        }

        // 分页查询
        PageHelper.startPage(zxEqEquip.getPage(),zxEqEquip.getLimit());
        // 获取数据
        List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.getZxEqEquipListVehicleForReport(zxEqEquip);
       
        // 得到分页信息
        PageInfo<ZxEqEquip> p = new PageInfo<>(zxEqEquipList);

        return repEntity.okList(zxEqEquipList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqEquipListForDepreciation(ZxEqEquip zxEqEquip) {
    	if (zxEqEquip == null) {
    		zxEqEquip = new ZxEqEquip();
    	}
    	String ext1 = "";
    	SysDepartment sysDepartment = sysDepartmentService.getSysDeptProByPrimaryKey(zxEqEquip.getUseOrgID());
    	if(sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getDepartmentId())) {
    		if(StrUtil.equals(sysDepartment.getDepartmentFlag(), "1")) {
    			ext1 = "1";
    		}else if(StrUtil.equals(sysDepartment.getDepartmentFlag(), "2")) {
    			ext1 = "2";
    		}
    	}
    	SysDepartment sysProject =  sysDepartmentService.getSysProjectPrimaryKeyByMapper(zxEqEquip.getUseOrgID());
    	if(sysProject != null && StrUtil.isNotEmpty(sysProject.getDepartmentId())) {
    		if(StrUtil.equals(sysProject.getDepartmentFlag(), "3")) {
    			ext1 = "3";
    		}else if(StrUtil.equals(sysProject.getDepartmentFlag(), "4")) {
    			ext1 = "4";
    		}
    	}

    	// 集团全部可见
    	if(StrUtil.equals("1", ext1)) {
    		zxEqEquip.setCompanyId("");
    		zxEqEquip.setUseOrgID("");
    	} else if(StrUtil.equals("2", ext1)) {
    		// 公司只看见自己的
    		zxEqEquip.setCompanyId(zxEqEquip.getUseOrgID());
    		zxEqEquip.setUseOrgID("");
    	} else if(StrUtil.equals("3", ext1) || StrUtil.equals("4", ext1)) {
    		// 项目通过右上角数据
    		zxEqEquip.setUseOrgID(zxEqEquip.getUseOrgID());//使用单位
    	}
    	// 分页查询
    	PageHelper.startPage(zxEqEquip.getPage(),zxEqEquip.getLimit());
    	// 获取数据
    	List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.selectByZxEqEquipList(zxEqEquip);
    	for (ZxEqEquip zxEqEquip2 : zxEqEquipList) {
    		ZxErpFile file = new ZxErpFile();
    		file.setOtherId(zxEqEquip2.getId());
    		List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
    		zxEqEquip2.setFileList(fileList);
    	}
    	// 得到分页信息
    	PageInfo<ZxEqEquip> p = new PageInfo<>(zxEqEquipList);

    	return repEntity.okList(zxEqEquipList, p.getTotal());
    }
    
    

	@Override
	public ResponseEntity getZxEqEquipListForDepreciationRemove(ZxEqEquip zxEqEquip) {
		if (zxEqEquip == null) {
    		zxEqEquip = new ZxEqEquip();
    	}
    	String ext1 = "";
    	SysDepartment sysDepartment = sysDepartmentService.getSysDeptProByPrimaryKey(zxEqEquip.getUseOrgID());
    	if(sysDepartment != null && StrUtil.isNotEmpty(sysDepartment.getDepartmentId())) {
    		if(StrUtil.equals(sysDepartment.getDepartmentFlag(), "1")) {
    			ext1 = "1";
    		}else if(StrUtil.equals(sysDepartment.getDepartmentFlag(), "2")) {
    			ext1 = "2";
    		}
    	}
    	SysDepartment sysProject =  sysDepartmentService.getSysProjectPrimaryKeyByMapper(zxEqEquip.getUseOrgID());
    	if(sysProject != null && StrUtil.isNotEmpty(sysProject.getDepartmentId())) {
    		if(StrUtil.equals(sysProject.getDepartmentFlag(), "3")) {
    			ext1 = "3";
    		}else if(StrUtil.equals(sysProject.getDepartmentFlag(), "4")) {
    			ext1 = "4";
    		}
    	}

    	// 集团全部可见
    	if(StrUtil.equals("1", ext1)) {
    		zxEqEquip.setCompanyId("");
    		zxEqEquip.setUseOrgID("");
    	} else if(StrUtil.equals("2", ext1)) {
    		// 公司只看见自己的
    		zxEqEquip.setCompanyId(zxEqEquip.getUseOrgID());
    		zxEqEquip.setUseOrgID("");
    	} else if(StrUtil.equals("3", ext1) || StrUtil.equals("4", ext1)) {
    		// 项目通过右上角数据
    		zxEqEquip.setUseOrgID(zxEqEquip.getUseOrgID());//使用单位
    	}

    	// 分页查询
    	PageHelper.startPage(zxEqEquip.getPage(),zxEqEquip.getLimit());
    	// 获取数据
    	List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.selectByZxEqEquipList(zxEqEquip);
    		for (ZxEqEquip zxEqEquip2 : zxEqEquipList) {
    		ZxErpFile file = new ZxErpFile();
    		file.setOtherId(zxEqEquip2.getId());
    		List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
    		zxEqEquip2.setFileList(fileList);
//    		if(zxEqEquip2.getLeftValue().compareTo(new BigDecimal("0")) <=0) {
//    			zxEqEquipList.remove(zxEqEquip2);
//    			if(zxEqEquipList != null && zxEqEquipList.size() >0) {
//    			}else {
//    				break;
//    			}
//    		}
    	}
    		for (int i = 0; i < zxEqEquipList.size(); i++) {
    			if(zxEqEquipList.get(i).getLeftValue().compareTo(new BigDecimal("0")) <=0) {
    				zxEqEquipList.remove(i);
    				i--;
    			}
    		}
    		
    		for (int i = 0; i < zxEqEquipList.size(); i++) {
    			int leftMonth = Integer.parseInt(zxEqEquipList.get(i).getDepreciation())*12-zxEqEquipList.get(i).getDepreciationMonth();
        		if(leftMonth<=0) {
        			zxEqEquipList.remove(i);
    				i--;
        		}
    		}
    		
    	if(zxEqEquipList != null && zxEqEquipList.size() >0) {
    		for (ZxEqEquip zxEqEquip2 : zxEqEquipList) {
        		//折旧
        		BigDecimal depreamout = new BigDecimal("0");
        		int leftMonth = Integer.parseInt(zxEqEquip2.getDepreciation())*12-zxEqEquip2.getDepreciationMonth();
        		//本月折旧=期末净值/剩余折旧月份
        		depreamout = CalcUtils.calcDivide(zxEqEquip2.getLeftValue(), new BigDecimal(leftMonth+""));
        		zxEqEquip2.setDepreamout(depreamout);
        		//期末净值 = 原值-累计-本月
        		//折旧累计 = 台账中的已提折旧
        		zxEqEquip2.setFinanceOrginalValue(zxEqEquip2.getActualDepreAmt());
        		zxEqEquip2.setLeftMonth(leftMonth);
			}
    	}
    	// 得到分页信息
    	PageInfo<ZxEqEquip> p = new PageInfo<>(zxEqEquipList);

    	return repEntity.okList(zxEqEquipList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxEqEquipListForMoveUseOrg(ZxEqEquip zxEqEquip) { 
		if (zxEqEquip == null) {
            zxEqEquip = new ZxEqEquip();
        }
        // 分页查询
        PageHelper.startPage(zxEqEquip.getPage(),zxEqEquip.getLimit());
        // 获取数据
        List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.selectByZxEqEquipList(zxEqEquip);
        for (ZxEqEquip zxEqEquip2 : zxEqEquipList) {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(zxEqEquip2.getId());
        	List<ZxErpFile> fileList = zxErpFileMapper.selectByZxErpFileList(file);
        	zxEqEquip2.setFileList(fileList);
		}
        // 得到分页信息
        PageInfo<ZxEqEquip> p = new PageInfo<>(zxEqEquipList);

        return repEntity.okList(zxEqEquipList, p.getTotal());
	}

	@Override
	public ResponseEntity getAddZxEqEquipTotalList(ZxEqEquip zxEqEquip) {
		if (zxEqEquip == null) {
			zxEqEquip = new ZxEqEquip();
		}
		// 分页查询
		PageHelper.startPage(zxEqEquip.getPage(),zxEqEquip.getLimit());
		// 获取数据        建伟张鹏写的sql前段联调的时候看看zxEqEquipMapper.ziYouCLForm
		List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.ureportAddZxEqEquipTotalList(zxEqEquip);
		// 得到分页信息
		PageInfo<ZxEqEquip> p = new PageInfo<>(zxEqEquipList);

		return repEntity.okList(zxEqEquipList, p.getTotal());
	}

	@Override
	public List<ZxEqEquip> ureportAddZxEqEquipTotalList(ZxEqEquip zxEqEquip) {
		if (zxEqEquip == null) {
			zxEqEquip = new ZxEqEquip();
		}
		// 获取数据
		List<ZxEqEquip> zxEqEquipList = zxEqEquipMapper.ureportAddZxEqEquipTotalList(zxEqEquip);
		return zxEqEquipList;
	}

	
}
