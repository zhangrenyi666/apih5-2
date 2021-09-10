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
import com.apih5.mybatis.dao.ZxCtEqContrItemMapper;
import com.apih5.mybatis.dao.ZxCtEqContractMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxCtEqContrItem;
import com.apih5.mybatis.pojo.ZxCtEqContract;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxCtEqContractService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCtEqContractService")
public class ZxCtEqContractServiceImpl implements ZxCtEqContractService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtEqContractMapper zxCtEqContractMapper;
    
    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;
    
    @Autowired(required = true)
    private ZxCtEqContrItemMapper zxCtEqContrItemMapper;

    @Override
    public ResponseEntity getZxCtEqContractListByCondition(ZxCtEqContract zxCtEqContract) {
        if (zxCtEqContract == null) {
            zxCtEqContract = new ZxCtEqContract();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCtEqContract.setComID("");
        	zxCtEqContract.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxCtEqContract.setComID(zxCtEqContract.getOrgID());
        	zxCtEqContract.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxCtEqContract.setOrgID(zxCtEqContract.getOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxCtEqContract.getPage(),zxCtEqContract.getLimit());
        // 获取数据
        List<ZxCtEqContract> zxCtEqContractList = zxCtEqContractMapper.selectByZxCtEqContractList(zxCtEqContract);
        for (ZxCtEqContract zxCtEqContract2 : zxCtEqContractList) {
        	ZxErpFile file = new ZxErpFile();
        	file.setOtherId(zxCtEqContract2.getContractID());
        	List<ZxErpFile> zxErpFileList = zxErpFileMapper.selectByZxErpFileList(file);
        	zxCtEqContract2.setZxErpFileList(zxErpFileList);
		}
        // 得到分页信息
        PageInfo<ZxCtEqContract> p = new PageInfo<>(zxCtEqContractList);

        return repEntity.okList(zxCtEqContractList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtEqContractDetail(ZxCtEqContract zxCtEqContract) {
        if (zxCtEqContract == null) {
            zxCtEqContract = new ZxCtEqContract();
        }
        // 获取数据
        ZxCtEqContract dbZxCtEqContract = zxCtEqContractMapper.selectByPrimaryKey(zxCtEqContract.getContractID());
        // 数据存在
        if (dbZxCtEqContract != null) {
            return repEntity.ok(dbZxCtEqContract);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtEqContract(ZxCtEqContract zxCtEqContract) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtEqContract.setContractID(UuidUtil.generate());
        zxCtEqContract.setCreateUserInfo(userKey, realName);
        int flag = zxCtEqContractMapper.insert(zxCtEqContract);
        if(zxCtEqContract.getZxErpFileList() != null && zxCtEqContract.getZxErpFileList().size() >0) {
        	for (ZxErpFile file : zxCtEqContract.getZxErpFileList()) {
        		file.setUid(UuidUtil.generate());
        		file.setOtherId(zxCtEqContract.getContractID());
        		file.setCreateUserInfo(userKey, realName);
        		flag = zxErpFileMapper.insert(file);
			}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtEqContract);
        }
    }

    @Override
    public ResponseEntity updateZxCtEqContract(ZxCtEqContract zxCtEqContract) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtEqContract dbZxCtEqContract = zxCtEqContractMapper.selectByPrimaryKey(zxCtEqContract.getContractID());
        if (dbZxCtEqContract != null && StrUtil.isNotEmpty(dbZxCtEqContract.getContractID())) {
        	//签订时间
        	dbZxCtEqContract.setSignatureDate(zxCtEqContract.getSignatureDate());
        	// 合同名称
        	dbZxCtEqContract.setContractName(zxCtEqContract.getContractName());
        	// 合同类型
        	dbZxCtEqContract.setPp3(zxCtEqContract.getPp3());
        	// 甲方id
        	dbZxCtEqContract.setFirstId(zxCtEqContract.getFirstId());
        	// 甲方名称
        	dbZxCtEqContract.setFirstName(zxCtEqContract.getFirstName());
        	// 乙方id
        	dbZxCtEqContract.setSecondID(zxCtEqContract.getSecondID());
        	// 乙方名称
        	dbZxCtEqContract.setSecondName(zxCtEqContract.getSecondName());
        	//乙方性质
        	dbZxCtEqContract.setAgent(zxCtEqContract.getAgent());
        	// 登记日期
        	dbZxCtEqContract.setRegisterDate(zxCtEqContract.getRegisterDate());
        	//项目经理
        	dbZxCtEqContract.setProjectManager(zxCtEqContract.getProjectManager());
        	// 乙方代表
        	dbZxCtEqContract.setSecondPrincipal(zxCtEqContract.getSecondPrincipal());
        	// 合同结束时间
        	dbZxCtEqContract.setPlanEndDate(zxCtEqContract.getPlanEndDate());
        	// 甲方联系电话
        	dbZxCtEqContract.setFirstUnitTel(zxCtEqContract.getFirstUnitTel());
        	// 乙方(固话)
        	dbZxCtEqContract.setSecondUnitTel(zxCtEqContract.getSecondUnitTel());
        	//结算情况
        	dbZxCtEqContract.setSettleType(zxCtEqContract.getSettleType());
        	// 合同内容
        	dbZxCtEqContract.setContent(zxCtEqContract.getContent());
        	//乙方(手机)
        	dbZxCtEqContract.setPp1(zxCtEqContract.getPp1());
        	//乙方传真
        	dbZxCtEqContract.setPp2(zxCtEqContract.getPp2());
        	// 合同工期
        	dbZxCtEqContract.setCsTimeLimit(zxCtEqContract.getCsTimeLimit());
        	// 合同状态
        	dbZxCtEqContract.setContractStatus(zxCtEqContract.getContractStatus());
        	// 合同开始时间
        	dbZxCtEqContract.setPlanStartDate(zxCtEqContract.getPlanStartDate());
        	//租赁方式
        	dbZxCtEqContract.setRentType(zxCtEqContract.getRentType());
        	//备注
        	dbZxCtEqContract.setRemark(zxCtEqContract.getRemark());
        	// 共通
        	dbZxCtEqContract.setModifyUserInfo(userKey, realName);
        	flag = zxCtEqContractMapper.updateByPrimaryKey(dbZxCtEqContract);
        	//附件先删除再新增
        	ZxErpFile delFile = new ZxErpFile();
        	delFile.setOtherId(zxCtEqContract.getContractID());
        	List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
        	if(delFileList != null && delFileList.size() >0) {
        		delFile.setModifyUserInfo(userKey, realName);
        		zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
        	}
        	if(zxCtEqContract.getZxErpFileList() != null && zxCtEqContract.getZxErpFileList().size() >0) {
        		for (ZxErpFile file : zxCtEqContract.getZxErpFileList()) {
        			file.setUid(UuidUtil.generate());
        			file.setOtherId(zxCtEqContract.getContractID());
        			file.setCreateUserInfo(userKey, realName);
        			flag = zxErpFileMapper.insert(file);
        		}
        	}
        }
        // 失败
        if (flag == 0) {
        	return repEntity.errorSave();
        } else {
        	return repEntity.ok("sys.data.update",zxCtEqContract);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtEqContract(List<ZxCtEqContract> zxCtEqContractList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtEqContractList != null && zxCtEqContractList.size() > 0) {
        	for (ZxCtEqContract zxCtEqContract : zxCtEqContractList) {
        		ZxErpFile delFile = new ZxErpFile();
        		delFile.setOtherId(zxCtEqContract.getContractID());
        		List<ZxErpFile> delFileList = zxErpFileMapper.selectByZxErpFileList(delFile);
        		if(delFileList != null && delFileList.size() >0) {
        			delFile.setModifyUserInfo(userKey, realName);
        			zxErpFileMapper.batchDeleteUpdateZxErpFile(delFileList, delFile);
        		}
        	}
        	ZxCtEqContract zxCtEqContract = new ZxCtEqContract();
        	zxCtEqContract.setModifyUserInfo(userKey, realName);
        	flag = zxCtEqContractMapper.batchDeleteUpdateZxCtEqContract(zxCtEqContractList, zxCtEqContract);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtEqContractList);
        }
    }

	@Override
	public ResponseEntity getZxCtEqContractListByOrgId(ZxCtEqContract zxCtEqContract) {
		if (zxCtEqContract == null) {
			zxCtEqContract = new ZxCtEqContract();
		}
		if(StrUtil.isEmpty(zxCtEqContract.getOrgID())) {
			 return repEntity.layerMessage("no", "项目id【orgID】不能为空");
		}
		zxCtEqContract.setContractCategory("0");//合同类别 =设备租赁 
		zxCtEqContract.setContractStatus("1");//合同状态:1执行中
		// 分页查询
		PageHelper.startPage(zxCtEqContract.getPage(),zxCtEqContract.getLimit());
		// 获取数据
		List<ZxCtEqContract> zxCtEqContractList = zxCtEqContractMapper.selectByZxCtEqContractList(zxCtEqContract);
		for (ZxCtEqContract contract : zxCtEqContractList) {
			ZxCtEqContrItem contrItem = new ZxCtEqContrItem();
			contrItem.setContractID(contract.getContractID());
			List<ZxCtEqContrItem> contrItemList = zxCtEqContrItemMapper.selectByZxCtEqContrItemList(contrItem);
			contract.setZxCtEqContrItemList(contrItemList);
		}
		// 得到分页信息
		PageInfo<ZxCtEqContract> p = new PageInfo<>(zxCtEqContractList);

		return repEntity.okList(zxCtEqContractList, p.getTotal());
	}

	@Override
	public ResponseEntity recoverZxCtEqContract(ZxCtEqContract zxCtEqContract) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxCtEqContract dbZxCtEqContract = zxCtEqContractMapper.selectByPrimaryKey(zxCtEqContract.getContractID());
		if (dbZxCtEqContract != null && StrUtil.isNotEmpty(dbZxCtEqContract.getContractID())) {
			//把 合同状态=终止 的合同 更新为 合同状态=执行中。
			//合同状态= 执行中 的合同 不可操作该按钮。
			if(StrUtil.equals(dbZxCtEqContract.getContractStatus(), "1")) {
				dbZxCtEqContract.setContractStatus("3");//合同状态:1执行中,3终止
			}else if(StrUtil.equals(dbZxCtEqContract.getContractStatus(), "3")) {
				dbZxCtEqContract.setContractStatus("1");//合同状态:1执行中,3终止
			}else {
				return repEntity.layerMessage("no", "该状态下不允许操作！");
			}
			// 共通
			dbZxCtEqContract.setModifyUserInfo(userKey, realName);
			flag = zxCtEqContractMapper.updateByPrimaryKey(dbZxCtEqContract);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorSave();
		} else {
			return repEntity.ok("sys.data.update",zxCtEqContract);
		}
	}

	@Override
	public ResponseEntity getZxCtEqContractListForEqMan(ZxCtEqContract zxCtEqContract) {
		if (zxCtEqContract == null) {
            zxCtEqContract = new ZxCtEqContract();
		}
		zxCtEqContract.setContractCategory("1");//合同类别 =设备采购 
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userId = TokenUtils.getUserId(request);
		String ext1 = TokenUtils.getExt1(request);
		// 集团全部可见
		if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
				|| StrUtil.equals("admin", userId)) {
			zxCtEqContract.setComID("");
			zxCtEqContract.setOrgID("");
		} else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
			// 公司只看见自己的
			zxCtEqContract.setComID(zxCtEqContract.getOrgID());
			zxCtEqContract.setOrgID("");
		} else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
				|| StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
			// 项目通过右上角数据
			zxCtEqContract.setOrgID(zxCtEqContract.getOrgID());
		}
        // 分页查询
        PageHelper.startPage(zxCtEqContract.getPage(),zxCtEqContract.getLimit());
        // 获取数据
        List<ZxCtEqContract> zxCtEqContractList = zxCtEqContractMapper.selectByZxCtEqContractList(zxCtEqContract);
        // 得到分页信息
        PageInfo<ZxCtEqContract> p = new PageInfo<>(zxCtEqContractList);
        return repEntity.okList(zxCtEqContractList, p.getTotal());
	}

	@Override
	public ResponseEntity getZxCtEqContractListForOuterEquip(ZxCtEqContract zxCtEqContract) {
		if (zxCtEqContract == null) {
            zxCtEqContract = new ZxCtEqContract();
        }
		zxCtEqContract.setContractCategory("0");//合同类别 =设备租赁 
        // 分页查询
        PageHelper.startPage(zxCtEqContract.getPage(),zxCtEqContract.getLimit());
        // 获取数据
        List<ZxCtEqContract> zxCtEqContractList = zxCtEqContractMapper.selectByZxCtEqContractList(zxCtEqContract);
        for (ZxCtEqContract zxCtEqContract2 : zxCtEqContractList) {
        	ZxCtEqContrItem item = new ZxCtEqContrItem();
        	item.setContractID(zxCtEqContract2.getContractID());
        	List<ZxCtEqContrItem> itemList = zxCtEqContrItemMapper.selectByZxCtEqContrItemList(item);
        	zxCtEqContract2.setZxCtEqContrItemList(itemList);
        }
        // 得到分页信息
        PageInfo<ZxCtEqContract> p = new PageInfo<>(zxCtEqContractList);
        return repEntity.okList(zxCtEqContractList, p.getTotal());
	}

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
