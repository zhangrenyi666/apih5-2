package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtWorkAlterSingleMapper;
import com.apih5.mybatis.dao.ZxCtWorksMapper;
import com.apih5.mybatis.pojo.ZxCtWorkAlterSingle;
import com.apih5.mybatis.pojo.ZxCtWorks;
import com.apih5.service.ZxCtWorkAlterSingleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxCtWorkAlterSingleService")
public class ZxCtWorkAlterSingleServiceImpl implements ZxCtWorkAlterSingleService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtWorkAlterSingleMapper zxCtWorkAlterSingleMapper;
    
    @Autowired(required = true)
    private ZxCtWorksMapper zxCtWorksMapper;

    @Override
    public ResponseEntity getZxCtWorkAlterSingleListByCondition(ZxCtWorkAlterSingle zxCtWorkAlterSingle) {
        if (zxCtWorkAlterSingle == null) {
            zxCtWorkAlterSingle = new ZxCtWorkAlterSingle();
        }
        // 分页查询
        PageHelper.startPage(zxCtWorkAlterSingle.getPage(),zxCtWorkAlterSingle.getLimit());
        // 获取数据
        List<ZxCtWorkAlterSingle> zxCtWorkAlterSingleList = zxCtWorkAlterSingleMapper.selectByZxCtWorkAlterSingleList(zxCtWorkAlterSingle);
        // 得到分页信息
        PageInfo<ZxCtWorkAlterSingle> p = new PageInfo<>(zxCtWorkAlterSingleList);

        return repEntity.okList(zxCtWorkAlterSingleList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtWorkAlterSingleDetail(ZxCtWorkAlterSingle zxCtWorkAlterSingle) {
        if (zxCtWorkAlterSingle == null) {
            zxCtWorkAlterSingle = new ZxCtWorkAlterSingle();
        }
        // 获取数据
        ZxCtWorkAlterSingle dbZxCtWorkAlterSingle = zxCtWorkAlterSingleMapper.selectByPrimaryKey(zxCtWorkAlterSingle.getId());
        // 数据存在
        if (dbZxCtWorkAlterSingle != null) {
            return repEntity.ok(dbZxCtWorkAlterSingle);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtWorkAlterSingle(ZxCtWorkAlterSingle zxCtWorkAlterSingle) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtWorkAlterSingle.setId(UuidUtil.generate());
        zxCtWorkAlterSingle.setCreateUserInfo(userKey, realName);
        zxCtWorkAlterSingle.setAlterType("修改");
        int flag = zxCtWorkAlterSingleMapper.insert(zxCtWorkAlterSingle);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// 修改清单变更后数量、变更后金额、变更后金额、清单单位、清单名称、计量单位
        	ZxCtWorks zxCtWorks = new ZxCtWorks();
        	zxCtWorks.setId(zxCtWorkAlterSingle.getWorkID());
        	zxCtWorks.setPrice(zxCtWorkAlterSingle.getPrice());
        	zxCtWorks.setQuantity(zxCtWorkAlterSingle.getQuantity());
        	zxCtWorks.setWorkNo(zxCtWorkAlterSingle.getWorkNo());
        	zxCtWorks.setWorkName(zxCtWorkAlterSingle.getWorkName());
        	zxCtWorks.setUnit(zxCtWorkAlterSingle.getUnit());
        	zxCtWorks.setModifyUserInfo(userKey, realName);
        	zxCtWorksMapper.updateWorksByPrimaryKey(zxCtWorks);
            return repEntity.ok("sys.data.sava", zxCtWorkAlterSingle);
        }
    }

    @Override
    public ResponseEntity updateZxCtWorkAlterSingle(ZxCtWorkAlterSingle zxCtWorkAlterSingle) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtWorkAlterSingle dbZxCtWorkAlterSingle = zxCtWorkAlterSingleMapper.selectByPrimaryKey(zxCtWorkAlterSingle.getId());
        if (dbZxCtWorkAlterSingle != null && StrUtil.isNotEmpty(dbZxCtWorkAlterSingle.getId())) {
           // 清单ID
           dbZxCtWorkAlterSingle.setWorkID(zxCtWorkAlterSingle.getWorkID());
           // 清单编号
           dbZxCtWorkAlterSingle.setWorkNo(zxCtWorkAlterSingle.getWorkNo());
           // 清单名称
           dbZxCtWorkAlterSingle.setWorkName(zxCtWorkAlterSingle.getWorkName());
           // 计量单位
           dbZxCtWorkAlterSingle.setUnit(zxCtWorkAlterSingle.getUnit());
           // 变更后数量
           dbZxCtWorkAlterSingle.setQuantity(zxCtWorkAlterSingle.getQuantity());
           // 变更后单价
           dbZxCtWorkAlterSingle.setPrice(zxCtWorkAlterSingle.getPrice());
           // 变更人
           dbZxCtWorkAlterSingle.setAlterPerson(zxCtWorkAlterSingle.getAlterPerson());
           // 变更时间
           dbZxCtWorkAlterSingle.setAlterDate(zxCtWorkAlterSingle.getAlterDate());
           // 变更令
           dbZxCtWorkAlterSingle.setAlterSign(zxCtWorkAlterSingle.getAlterSign());
           // 说明
           dbZxCtWorkAlterSingle.setInformation(zxCtWorkAlterSingle.getInformation());
           // 类型
           dbZxCtWorkAlterSingle.setAlterType(zxCtWorkAlterSingle.getAlterType());
           // 原来数量
           dbZxCtWorkAlterSingle.setOriginalQuantity(zxCtWorkAlterSingle.getOriginalQuantity());
           // 原来单价
           dbZxCtWorkAlterSingle.setOriginalPrice(zxCtWorkAlterSingle.getOriginalPrice());
           // 编辑时间
           dbZxCtWorkAlterSingle.setEditTime(zxCtWorkAlterSingle.getEditTime());
           // 是否禁用
           dbZxCtWorkAlterSingle.setReplyType(zxCtWorkAlterSingle.getReplyType());
           // 是否叶子节点
           dbZxCtWorkAlterSingle.setIsLeaf(zxCtWorkAlterSingle.getIsLeaf());
           // 备注
           dbZxCtWorkAlterSingle.setRemarks(zxCtWorkAlterSingle.getRemarks());
           // 排序
           dbZxCtWorkAlterSingle.setSort(zxCtWorkAlterSingle.getSort());
           // 共通
           dbZxCtWorkAlterSingle.setModifyUserInfo(userKey, realName);
           flag = zxCtWorkAlterSingleMapper.updateByPrimaryKey(dbZxCtWorkAlterSingle);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtWorkAlterSingle);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtWorkAlterSingle(List<ZxCtWorkAlterSingle> zxCtWorkAlterSingleList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtWorkAlterSingleList != null && zxCtWorkAlterSingleList.size() > 0) {
           ZxCtWorkAlterSingle zxCtWorkAlterSingle = new ZxCtWorkAlterSingle();
           zxCtWorkAlterSingle.setModifyUserInfo(userKey, realName);
           flag = zxCtWorkAlterSingleMapper.batchDeleteUpdateZxCtWorkAlterSingle(zxCtWorkAlterSingleList, zxCtWorkAlterSingle);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtWorkAlterSingleList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    
    @Override
    public ResponseEntity zxCtCancelWorkAlterSingle(ZxCtWorkAlterSingle zxCtWorkAlterSingle) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
    	if (zxCtWorkAlterSingle == null || StrUtil.isEmpty(zxCtWorkAlterSingle.getWorkID())) {
			return repEntity.layerMessage("no", "workID不能为空！");
		}
    	
    	// 查询最后一条数据修改清单变更后数量、变更后金额、变更后金额
    	ZxCtWorkAlterSingle dbZxCtWorkAlterSingle = zxCtWorkAlterSingleMapper.zxCtWorkAlterSingleLast(zxCtWorkAlterSingle);
    	if (dbZxCtWorkAlterSingle != null) {
    		ZxCtWorks zxCtWorks = new ZxCtWorks();
    		zxCtWorks.setId(dbZxCtWorkAlterSingle.getWorkID());
    		zxCtWorks.setPrice(dbZxCtWorkAlterSingle.getOriginalPrice());
    		zxCtWorks.setQuantity(dbZxCtWorkAlterSingle.getOriginalQuantity());
    		zxCtWorks.setModifyUserInfo(userKey, realName);
    		zxCtWorksMapper.updateWorksByPrimaryKey(zxCtWorks);
		}
    	
    	zxCtWorkAlterSingle.setModifyUserInfo(userKey, realName);
    	int flag = zxCtWorkAlterSingleMapper.zxCtCancelWorkAlterSingle(zxCtWorkAlterSingle);
    	
    	// 失败
        if (flag == 0) {
            return repEntity.errorDelete();
        } else {
            return repEntity.ok("删除成功！");
        }
    }
}
