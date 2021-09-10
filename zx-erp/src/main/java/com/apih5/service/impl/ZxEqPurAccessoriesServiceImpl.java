package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxEqPurAccessoriesMapper;
import com.apih5.mybatis.pojo.ZxEqPurAccessories;
import com.apih5.service.ZxEqPurAccessoriesService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxEqPurAccessoriesService")
public class ZxEqPurAccessoriesServiceImpl implements ZxEqPurAccessoriesService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqPurAccessoriesMapper zxEqPurAccessoriesMapper;

    @Override
    public ResponseEntity getZxEqPurAccessoriesListByCondition(ZxEqPurAccessories zxEqPurAccessories) {
        if (zxEqPurAccessories == null) {
            zxEqPurAccessories = new ZxEqPurAccessories();
        }
        // 分页查询
        PageHelper.startPage(zxEqPurAccessories.getPage(),zxEqPurAccessories.getLimit());
        // 获取数据
        List<ZxEqPurAccessories> zxEqPurAccessoriesList = zxEqPurAccessoriesMapper.selectByZxEqPurAccessoriesList(zxEqPurAccessories);
        // 得到分页信息
        PageInfo<ZxEqPurAccessories> p = new PageInfo<>(zxEqPurAccessoriesList);

        return repEntity.okList(zxEqPurAccessoriesList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqPurAccessoriesDetails(ZxEqPurAccessories zxEqPurAccessories) {
        if (zxEqPurAccessories == null) {
            zxEqPurAccessories = new ZxEqPurAccessories();
        }
        // 获取数据
        ZxEqPurAccessories dbZxEqPurAccessories = zxEqPurAccessoriesMapper.selectByPrimaryKey(zxEqPurAccessories.getId());
        // 数据存在
        if (dbZxEqPurAccessories != null) {
            return repEntity.ok(dbZxEqPurAccessories);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqPurAccessories(ZxEqPurAccessories zxEqPurAccessories) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxEqPurAccessories.setId(UuidUtil.generate());
        zxEqPurAccessories.setCreateUserInfo(userKey, realName);
        int flag = zxEqPurAccessoriesMapper.insert(zxEqPurAccessories);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqPurAccessories);
        }
    }

    @Override
    public ResponseEntity updateZxEqPurAccessories(ZxEqPurAccessories zxEqPurAccessories) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqPurAccessories dbzxEqPurAccessories = zxEqPurAccessoriesMapper.selectByPrimaryKey(zxEqPurAccessories.getId());
        if (dbzxEqPurAccessories != null && StrUtil.isNotEmpty(dbzxEqPurAccessories.getId())) {
           // 主表id
           dbzxEqPurAccessories.setMasID(zxEqPurAccessories.getMasID());
           // 随机工具及配件名称
           dbzxEqPurAccessories.setName(zxEqPurAccessories.getName());
           // 规格
           dbzxEqPurAccessories.setSpec(zxEqPurAccessories.getSpec());
           // 单位
           dbzxEqPurAccessories.setUnit(zxEqPurAccessories.getUnit());
           // 数量
           dbzxEqPurAccessories.setQty(zxEqPurAccessories.getQty());
           // 编制时间
           dbzxEqPurAccessories.setEditTime(zxEqPurAccessories.getEditTime());
           // 共通
           dbzxEqPurAccessories.setModifyUserInfo(userKey, realName);
           flag = zxEqPurAccessoriesMapper.updateByPrimaryKey(dbzxEqPurAccessories);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqPurAccessories);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqPurAccessories(List<ZxEqPurAccessories> zxEqPurAccessoriesList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqPurAccessoriesList != null && zxEqPurAccessoriesList.size() > 0) {
           ZxEqPurAccessories zxEqPurAccessories = new ZxEqPurAccessories();
           zxEqPurAccessories.setModifyUserInfo(userKey, realName);
           flag = zxEqPurAccessoriesMapper.batchDeleteUpdateZxEqPurAccessories(zxEqPurAccessoriesList, zxEqPurAccessories);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqPurAccessoriesList);
        }
    }

	@Override
	public List<ZxEqPurAccessories> ureportZxEqPurAccessoriesList(ZxEqPurAccessories zxEqPurAccessories) {
		if (zxEqPurAccessories == null) {
			zxEqPurAccessories = new ZxEqPurAccessories();
		}
		// 获取数据
		if(StrUtil.isNotEmpty(zxEqPurAccessories.getId())) {
			zxEqPurAccessories.setMasID(zxEqPurAccessories.getId());
			zxEqPurAccessories.setId("");
		}
		List<ZxEqPurAccessories> zxEqPurAccessoriesList = zxEqPurAccessoriesMapper.selectByZxEqPurAccessoriesList(zxEqPurAccessories);
		return zxEqPurAccessoriesList;
	}
}
