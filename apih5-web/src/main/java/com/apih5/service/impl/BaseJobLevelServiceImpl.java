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
import com.apih5.mybatis.dao.BaseJobLevelMapper;
import com.apih5.mybatis.dao.BaseJobLevelSubMapper;
import com.apih5.mybatis.pojo.BaseJobLevel;
import com.apih5.mybatis.pojo.BaseJobLevelSub;
import com.apih5.service.BaseJobLevelService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

@Service("baseJobLevelService")
public class BaseJobLevelServiceImpl implements BaseJobLevelService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private BaseJobLevelMapper baseJobLevelMapper;
    
    @Autowired(required = true)
    private BaseJobLevelSubMapper baseJobLevelSubMapper;

    @Override
    public ResponseEntity getBaseJobLevelListByCondition(BaseJobLevel baseJobLevel) {
        if (baseJobLevel == null) {
            baseJobLevel = new BaseJobLevel();
        }
        // 分页查询
        PageHelper.startPage(baseJobLevel.getPage(),baseJobLevel.getLimit());
        // 获取数据
        List<BaseJobLevel> baseJobLevelList = baseJobLevelMapper.selectByBaseJobLevelList(baseJobLevel);
        if(baseJobLevelList != null && baseJobLevelList.size()>0) {
            for(BaseJobLevel dbBaseJobLevel:baseJobLevelList) {
                BaseJobLevelSub baseJobLevelSub = new BaseJobLevelSub();
                baseJobLevelSub.setBaseJobLevelId(dbBaseJobLevel.getBaseJobLevelId());
                List<BaseJobLevelSub> baseJobLevelSubList = baseJobLevelSubMapper.selectByBaseJobLevelSubList(baseJobLevelSub);
                dbBaseJobLevel.setBaseJobLevelSubList(baseJobLevelSubList);
            }
        }
        // 得到分页信息
        PageInfo<BaseJobLevel> p = new PageInfo<>(baseJobLevelList);

        return repEntity.okList(baseJobLevelList, p.getTotal());
    }

    @Override
    public ResponseEntity getBaseJobLevelDetail(BaseJobLevel baseJobLevel) {
        if (baseJobLevel == null) {
            baseJobLevel = new BaseJobLevel();
        }
        // 获取数据
        BaseJobLevel dbBaseJobLevel = baseJobLevelMapper.selectByPrimaryKey(baseJobLevel.getBaseJobLevelId());
        // 数据存在
        if (dbBaseJobLevel != null) {
            return repEntity.ok(dbBaseJobLevel);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveBaseJobLevel(BaseJobLevel baseJobLevel) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        baseJobLevel.setBaseJobLevelId(IdUtil.getSnowflake(1, 1).nextIdStr());
        baseJobLevel.setCreateUserInfo(userKey, realName);
        int flag = baseJobLevelMapper.insert(baseJobLevel);
        
        List<BaseJobLevelSub> baseJobLevelSubList = baseJobLevel.getBaseJobLevelSubList();
        if(baseJobLevelSubList != null && baseJobLevelSubList.size()>0) {
            for(BaseJobLevelSub baseJobLevelSub:baseJobLevelSubList) {
                baseJobLevelSub.setBaseJobLevelSubId(IdUtil.getSnowflake(1, 1).nextIdStr());
                baseJobLevelSub.setBaseJobLevelId(baseJobLevel.getBaseJobLevelId());
                baseJobLevelSub.setCreateUserInfo(userKey, realName);
                baseJobLevelSubMapper.insert(baseJobLevelSub);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", baseJobLevel);
        }
    }

    @Override
    public ResponseEntity updateBaseJobLevel(BaseJobLevel baseJobLevel) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        BaseJobLevel dbBaseJobLevel = baseJobLevelMapper.selectByPrimaryKey(baseJobLevel.getBaseJobLevelId());
        if (dbBaseJobLevel != null && StrUtil.isNotEmpty(dbBaseJobLevel.getBaseJobLevelId())) {
           // 公司类型
           dbBaseJobLevel.setCompanyId(baseJobLevel.getCompanyId());
           // 项目薪酬类型（级别）
           dbBaseJobLevel.setPayLevelType(baseJobLevel.getPayLevelType());
           // 岗位级别名称
           dbBaseJobLevel.setJobLevelName(baseJobLevel.getJobLevelName());
           // 备注
           dbBaseJobLevel.setRemarks(baseJobLevel.getRemarks());
           // 排序
           dbBaseJobLevel.setSort(baseJobLevel.getSort());
           // 共通
           dbBaseJobLevel.setModifyUserInfo(userKey, realName);
           flag = baseJobLevelMapper.updateByPrimaryKey(dbBaseJobLevel);
           
           // 删除后，新增
           BaseJobLevelSub baseJobLevelSubSelect = new BaseJobLevelSub(); 
           baseJobLevelSubSelect.setBaseJobLevelId(dbBaseJobLevel.getBaseJobLevelId());
           List<BaseJobLevelSub> dbBaseJobLevelSubList = baseJobLevelSubMapper.selectByBaseJobLevelSubList(baseJobLevelSubSelect);
           if(dbBaseJobLevelSubList != null && dbBaseJobLevelSubList.size()>0) {
               baseJobLevelSubMapper.batchDeleteUpdateBaseJobLevelSub(dbBaseJobLevelSubList, null);
           }
           
           List<BaseJobLevelSub> baseJobLevelSubList = baseJobLevel.getBaseJobLevelSubList();
           if(baseJobLevelSubList != null && baseJobLevelSubList.size()>0) {
               for(BaseJobLevelSub baseJobLevelSub:baseJobLevelSubList) {
                   baseJobLevelSub.setBaseJobLevelSubId(IdUtil.getSnowflake(1, 1).nextIdStr());
                   baseJobLevelSub.setBaseJobLevelId(dbBaseJobLevel.getBaseJobLevelId());
                   baseJobLevelSub.setCreateUserInfo(userKey, realName);
                   baseJobLevelSubMapper.insert(baseJobLevelSub);
               }
           }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",baseJobLevel);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateBaseJobLevel(List<BaseJobLevel> baseJobLevelList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (baseJobLevelList != null && baseJobLevelList.size() > 0) {
           BaseJobLevel baseJobLevel = new BaseJobLevel();
           baseJobLevel.setModifyUserInfo(userKey, realName);
           flag = baseJobLevelMapper.batchDeleteUpdateBaseJobLevel(baseJobLevelList, baseJobLevel);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",baseJobLevelList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
