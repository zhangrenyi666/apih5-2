package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.BaseJobLevelSubMapper;
import com.apih5.mybatis.pojo.BaseJobLevelSub;
import com.apih5.service.BaseJobLevelSubService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("baseJobLevelSubService")
public class BaseJobLevelSubServiceImpl implements BaseJobLevelSubService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private BaseJobLevelSubMapper baseJobLevelSubMapper;

    @Override
    public ResponseEntity getBaseJobLevelSubListByCondition(BaseJobLevelSub baseJobLevelSub) {
        if (baseJobLevelSub == null) {
            baseJobLevelSub = new BaseJobLevelSub();
        }
        // 分页查询
        PageHelper.startPage(baseJobLevelSub.getPage(),baseJobLevelSub.getLimit());
        // 获取数据
        List<BaseJobLevelSub> baseJobLevelSubList = baseJobLevelSubMapper.selectByBaseJobLevelSubList(baseJobLevelSub);
        // 得到分页信息
        PageInfo<BaseJobLevelSub> p = new PageInfo<>(baseJobLevelSubList);

        return repEntity.okList(baseJobLevelSubList, p.getTotal());
    }

    @Override
    public ResponseEntity getBaseJobLevelSubDetail(BaseJobLevelSub baseJobLevelSub) {
        if (baseJobLevelSub == null) {
            baseJobLevelSub = new BaseJobLevelSub();
        }
        // 获取数据
        BaseJobLevelSub dbBaseJobLevelSub = baseJobLevelSubMapper.selectByPrimaryKey(baseJobLevelSub.getBaseJobLevelSubId());
        // 数据存在
        if (dbBaseJobLevelSub != null) {
            return repEntity.ok(dbBaseJobLevelSub);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveBaseJobLevelSub(BaseJobLevelSub baseJobLevelSub) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        baseJobLevelSub.setBaseJobLevelSubId(UuidUtil.generate());
        baseJobLevelSub.setCreateUserInfo(userKey, realName);
        int flag = baseJobLevelSubMapper.insert(baseJobLevelSub);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", baseJobLevelSub);
        }
    }

    @Override
    public ResponseEntity updateBaseJobLevelSub(BaseJobLevelSub baseJobLevelSub) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        BaseJobLevelSub dbBaseJobLevelSub = baseJobLevelSubMapper.selectByPrimaryKey(baseJobLevelSub.getBaseJobLevelSubId());
        if (dbBaseJobLevelSub != null && StrUtil.isNotEmpty(dbBaseJobLevelSub.getBaseJobLevelSubId())) {
           // 主表id
           dbBaseJobLevelSub.setBaseJobLevelId(baseJobLevelSub.getBaseJobLevelId());
           // 档位
           dbBaseJobLevelSub.setGear(baseJobLevelSub.getGear());
           // 岗薪
           dbBaseJobLevelSub.setSalary(baseJobLevelSub.getSalary());
           // 备注
           dbBaseJobLevelSub.setRemarks(baseJobLevelSub.getRemarks());
           // 排序
           dbBaseJobLevelSub.setSort(baseJobLevelSub.getSort());
           // 共通
           dbBaseJobLevelSub.setModifyUserInfo(userKey, realName);
           flag = baseJobLevelSubMapper.updateByPrimaryKey(dbBaseJobLevelSub);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",baseJobLevelSub);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateBaseJobLevelSub(List<BaseJobLevelSub> baseJobLevelSubList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (baseJobLevelSubList != null && baseJobLevelSubList.size() > 0) {
           BaseJobLevelSub baseJobLevelSub = new BaseJobLevelSub();
           baseJobLevelSub.setModifyUserInfo(userKey, realName);
           flag = baseJobLevelSubMapper.batchDeleteUpdateBaseJobLevelSub(baseJobLevelSubList, baseJobLevelSub);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",baseJobLevelSubList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
