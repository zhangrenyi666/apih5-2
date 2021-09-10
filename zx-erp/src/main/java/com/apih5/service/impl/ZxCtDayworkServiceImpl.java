package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtBalanceMapper;
import com.apih5.mybatis.dao.ZxCtDayworkMapper;
import com.apih5.mybatis.pojo.ZxCtDaywork;
import com.apih5.mybatis.pojo.ZxCtDayworkItem;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxCtDayworkService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxCtDayworkService")
public class ZxCtDayworkServiceImpl implements ZxCtDayworkService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtDayworkMapper zxCtDayworkMapper;
    
    @Autowired(required = true)
    private ZxErpFileServiceImpl zxErpFileServiceImpl;
    
    @Autowired(required = true)
    private ZxCtDayworkItemServiceImpl zxCtDayworkItemServiceImpl;
    
    @Autowired(required = true)
    private ZxCtBalanceMapper zxCtBalanceMapper;

    @SuppressWarnings("unchecked")
	@Override
    public ResponseEntity getZxCtDayworkListByCondition(ZxCtDaywork zxCtDaywork) {
        if (zxCtDaywork == null) {
            zxCtDaywork = new ZxCtDaywork();
        }
        // 分页查询
        PageHelper.startPage(zxCtDaywork.getPage(),zxCtDaywork.getLimit());
        // 获取数据
        List<ZxCtDaywork> zxCtDayworkList = zxCtDayworkMapper.selectByZxCtDayworkList(zxCtDaywork);
        // 得到分页信息
        PageInfo<ZxCtDaywork> p = new PageInfo<>(zxCtDayworkList);

        for (ZxCtDaywork ctDaywork : zxCtDayworkList) {
        	// 附件
        	ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(ctDaywork.getId());
        	zxErpFile.setOtherType("1");
        	List<ZxErpFile> zxErpFileList = (List<ZxErpFile>) zxErpFileServiceImpl.getZxErpFileListByCondition(zxErpFile).getData();
        	ctDaywork.setAttachment(zxErpFileList);
        	
        	// 资源列表
        	ZxCtDayworkItem zxCtDayworkItem = new ZxCtDayworkItem();
        	zxCtDayworkItem.setBillID(ctDaywork.getId());
        	List<ZxCtDayworkItem> zxCtDayworkItemList = (List<ZxCtDayworkItem>) zxCtDayworkItemServiceImpl.getZxCtDayworkItemListByCondition(zxCtDayworkItem).getData();
        	ctDaywork.setDayworkItemList(zxCtDayworkItemList);
        }
        
        return repEntity.okList(zxCtDayworkList, p.getTotal());
    }

    @SuppressWarnings("unchecked")
	@Override
    public ResponseEntity getZxCtDayworkDetail(ZxCtDaywork zxCtDaywork) {
        if (zxCtDaywork == null) {
            zxCtDaywork = new ZxCtDaywork();
        }
        // 获取数据
        ZxCtDaywork dbZxCtDaywork = zxCtDayworkMapper.selectByPrimaryKey(zxCtDaywork.getId());
        // 数据存在
        if (dbZxCtDaywork != null) {
        	// 附件
        	ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(dbZxCtDaywork.getId());
        	zxErpFile.setOtherType("1");
        	List<ZxErpFile> zxErpFileList = (List<ZxErpFile>) zxErpFileServiceImpl.getZxErpFileListByCondition(zxErpFile).getData();
        	dbZxCtDaywork.setAttachment(zxErpFileList);
        	
        	// 资源列表
        	ZxCtDayworkItem zxCtDayworkItem = new ZxCtDayworkItem();
        	zxCtDayworkItem.setBillID(dbZxCtDaywork.getId());
        	List<ZxCtDayworkItem> zxCtDayworkItemList = (List<ZxCtDayworkItem>) zxCtDayworkItemServiceImpl.getZxCtDayworkItemListByCondition(zxCtDayworkItem).getData();
        	dbZxCtDaywork.setDayworkItemList(zxCtDayworkItemList);
        	
            return repEntity.ok(dbZxCtDaywork);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtDaywork(ZxCtDaywork zxCtDaywork) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtDaywork.setId(UuidUtil.generate());
        zxCtDaywork.setCreateUserInfo(userKey, realName);
        int flag = zxCtDayworkMapper.insert(zxCtDaywork);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// 修改清单主表本期计量金额
        	zxCtBalanceMapper.updatebalAmtByPrimaryKey(zxCtDaywork.getBalID());
        	
        	// 附件
        	List<ZxErpFile> zxErpFileList = zxCtDaywork.getAttachment();
        	if (zxErpFileList != null && zxErpFileList.size() > 0) {
				for (ZxErpFile zxErpFile : zxErpFileList) {
					zxErpFile.setOtherId(zxCtDaywork.getId());
		        	zxErpFile.setOtherType("1");
		        	zxErpFileServiceImpl.saveZxErpFile(zxErpFile);
				}
			}
        	
        	// 资源列表
        	List<ZxCtDayworkItem> zxCtDayworkItemList = zxCtDaywork.getDayworkItemList();
        	if (zxCtDayworkItemList != null && zxCtDayworkItemList.size() > 0) {
				for (ZxCtDayworkItem zxCtDayworkItem : zxCtDayworkItemList) {
					zxCtDayworkItem.setBillID(zxCtDaywork.getId());
		        	zxCtDayworkItemServiceImpl.saveZxCtDayworkItem(zxCtDayworkItem);
				}
			}
            return repEntity.ok("sys.data.sava", zxCtDaywork);
        }
    }

    @Override
    public ResponseEntity updateZxCtDaywork(ZxCtDaywork zxCtDaywork) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtDaywork dbZxCtDaywork = zxCtDayworkMapper.selectByPrimaryKey(zxCtDaywork.getId());
        if (dbZxCtDaywork != null && StrUtil.isNotEmpty(dbZxCtDaywork.getId())) {
           // 计量单ID
           dbZxCtDaywork.setBalID(zxCtDaywork.getBalID());
           // 业务日期
           dbZxCtDaywork.setBusDate(zxCtDaywork.getBusDate());
           // 管理单元ID
           dbZxCtDaywork.setMuID(zxCtDaywork.getMuID());
           // 计量类型
           dbZxCtDaywork.setBalanceType(zxCtDaywork.getBalanceType());
           // 单号
           dbZxCtDaywork.setBillNo(zxCtDaywork.getBillNo());
           // 金额
           dbZxCtDaywork.setTotalAmt(zxCtDaywork.getTotalAmt());
           // 记录人
           dbZxCtDaywork.setReporter(zxCtDaywork.getReporter());
           // 记录日期
           dbZxCtDaywork.setCreateDate(zxCtDaywork.getCreateDate());
           // 审核人
           dbZxCtDaywork.setAuditor(zxCtDaywork.getAuditor());
           // 审核日期
           dbZxCtDaywork.setAuditDate(zxCtDaywork.getAuditDate());
           // 审核意见
           dbZxCtDaywork.setAuditMsg(zxCtDaywork.getAuditMsg());
           // 审核状态
           dbZxCtDaywork.setAuditStatus(zxCtDaywork.getAuditStatus());
           // combProp
           dbZxCtDaywork.setCombProp(zxCtDaywork.getCombProp());
           // pp1
           dbZxCtDaywork.setPp1(zxCtDaywork.getPp1());
           // pp2
           dbZxCtDaywork.setPp2(zxCtDaywork.getPp2());
           // pp3
           dbZxCtDaywork.setPp3(zxCtDaywork.getPp3());
           // pp4
           dbZxCtDaywork.setPp4(zxCtDaywork.getPp4());
           // pp5
           dbZxCtDaywork.setPp5(zxCtDaywork.getPp5());
           // pp6
           dbZxCtDaywork.setPp6(zxCtDaywork.getPp6());
           // pp7
           dbZxCtDaywork.setPp7(zxCtDaywork.getPp7());
           // pp8
           dbZxCtDaywork.setPp8(zxCtDaywork.getPp8());
           // pp9
           dbZxCtDaywork.setPp9(zxCtDaywork.getPp9());
           // pp10
           dbZxCtDaywork.setPp10(zxCtDaywork.getPp10());
           // 最后编辑日期
           dbZxCtDaywork.setEditTime(zxCtDaywork.getEditTime());
           // 计日工
           dbZxCtDaywork.setDayPrice(zxCtDaywork.getDayPrice());
           // 暂定金
           dbZxCtDaywork.setTempPrice(zxCtDaywork.getTempPrice());
           // 备注
           dbZxCtDaywork.setRemark(zxCtDaywork.getRemark());
           // 排序
           dbZxCtDaywork.setSort(zxCtDaywork.getSort());
           // 共通
           dbZxCtDaywork.setModifyUserInfo(userKey, realName);
           flag = zxCtDayworkMapper.updateByPrimaryKey(dbZxCtDaywork);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// 修改清单主表本期计量金额
        	zxCtBalanceMapper.updatebalAmtByPrimaryKey(dbZxCtDaywork.getBalID());
        	
        	// 附件
        	ZxErpFile delZxErpFile = new ZxErpFile();
        	delZxErpFile.setOtherId(dbZxCtDaywork.getId());
        	delZxErpFile.setOtherType("1");
        	zxErpFileServiceImpl.deleteAllZxErpFile(delZxErpFile);
        	
        	List<ZxErpFile> zxErpFileList = zxCtDaywork.getAttachment();
        	if (zxErpFileList != null && zxErpFileList.size() > 0) {
				for (ZxErpFile zxErpFile : zxErpFileList) {
					zxErpFile.setOtherId(dbZxCtDaywork.getId());
		        	zxErpFile.setOtherType("1");
		        	zxErpFileServiceImpl.saveZxErpFile(zxErpFile);
				}
			}
        	
        	// 资源列表
        	ZxCtDayworkItem delZxCtDayworkItem = new ZxCtDayworkItem();
        	delZxCtDayworkItem.setBillID(dbZxCtDaywork.getId());
        	zxCtDayworkItemServiceImpl.deleteAllZxCtDayworkItem(delZxCtDayworkItem);
        	
        	List<ZxCtDayworkItem> zxCtDayworkItemList = zxCtDaywork.getDayworkItemList();
        	if (zxCtDayworkItemList != null && zxCtDayworkItemList.size() > 0) {
				for (ZxCtDayworkItem zxCtDayworkItem : zxCtDayworkItemList) {
					zxCtDayworkItem.setBillID(dbZxCtDaywork.getId());
		        	zxCtDayworkItemServiceImpl.saveZxCtDayworkItem(zxCtDayworkItem);
				}
			}
        	
            return repEntity.ok("sys.data.update",zxCtDaywork);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtDaywork(List<ZxCtDaywork> zxCtDayworkList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtDayworkList != null && zxCtDayworkList.size() > 0) {
           ZxCtDaywork zxCtDaywork = new ZxCtDaywork();
           zxCtDaywork.setModifyUserInfo(userKey, realName);
           flag = zxCtDayworkMapper.batchDeleteUpdateZxCtDaywork(zxCtDayworkList, zxCtDaywork);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	for (ZxCtDaywork zxCtDaywork : zxCtDayworkList) {
        		// 附件
            	ZxErpFile delZxErpFile = new ZxErpFile();
            	delZxErpFile.setOtherId(zxCtDaywork.getId());
            	delZxErpFile.setOtherType("1");
            	zxErpFileServiceImpl.deleteAllZxErpFile(delZxErpFile);
            	
            	// 资源列表
            	ZxCtDayworkItem delZxCtDayworkItem = new ZxCtDayworkItem();
            	delZxCtDayworkItem.setBillID(zxCtDaywork.getId());
            	zxCtDayworkItemServiceImpl.deleteAllZxCtDayworkItem(delZxCtDayworkItem);
			}
        	
        	// 修改清单主表本期计量金额
        	zxCtBalanceMapper.updatebalAmtByPrimaryKey(zxCtDayworkList.get(0).getBalID());
            return repEntity.ok("sys.data.delete",zxCtDayworkList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
