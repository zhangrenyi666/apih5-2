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
import com.apih5.mybatis.dao.BaseProjectConfJobMapper;
import com.apih5.mybatis.pojo.BaseProjectConfJob;
import com.apih5.service.BaseProjectConfJobService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("baseProjectConfJobService")
public class BaseProjectConfJobServiceImpl implements BaseProjectConfJobService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private BaseProjectConfJobMapper baseProjectConfJobMapper;

    @Override
    public ResponseEntity getBaseProjectConfJobListByCondition(BaseProjectConfJob baseProjectConfJob) {
        if (baseProjectConfJob == null) {
            baseProjectConfJob = new BaseProjectConfJob();
        }
        // 分页查询
        PageHelper.startPage(baseProjectConfJob.getPage(),baseProjectConfJob.getLimit());
        // 获取数据
        List<BaseProjectConfJob> baseProjectConfJobList = baseProjectConfJobMapper.selectByBaseProjectConfJobList(baseProjectConfJob);
        // 得到分页信息
        PageInfo<BaseProjectConfJob> p = new PageInfo<>(baseProjectConfJobList);

        return repEntity.okList(baseProjectConfJobList, p.getTotal());
    }

    @Override
    public ResponseEntity getBaseProjectConfJobDetail(BaseProjectConfJob baseProjectConfJob) {
        if (baseProjectConfJob == null) {
            baseProjectConfJob = new BaseProjectConfJob();
        }
        // 获取数据
        BaseProjectConfJob dbBaseProjectConfJob = baseProjectConfJobMapper.selectByPrimaryKey(baseProjectConfJob.getBaseProjectJobConfId());
        // 数据存在
        if (dbBaseProjectConfJob != null) {
            return repEntity.ok(dbBaseProjectConfJob);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveBaseProjectConfJob(BaseProjectConfJob baseProjectConfJob) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        baseProjectConfJob.setBaseProjectJobConfId(UuidUtil.generate());
        baseProjectConfJob.setCreateUserInfo(userKey, realName);
        int flag = baseProjectConfJobMapper.insert(baseProjectConfJob);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", baseProjectConfJob);
        }
    }

    @Override
    public ResponseEntity updateBaseProjectConfJob(BaseProjectConfJob baseProjectConfJob) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        BaseProjectConfJob dbBaseProjectConfJob = baseProjectConfJobMapper.selectByPrimaryKey(baseProjectConfJob.getBaseProjectJobConfId());
        if (dbBaseProjectConfJob != null && StrUtil.isNotEmpty(dbBaseProjectConfJob.getBaseProjectJobConfId())) {
           // 项目人员项目人员配置岗位表表主键
           dbBaseProjectConfJob.setBaseProjectConfId(baseProjectConfJob.getBaseProjectConfId());
           // 岗位
           dbBaseProjectConfJob.setJobType(baseProjectConfJob.getJobType());
           // 人数类型
           dbBaseProjectConfJob.setNumType(baseProjectConfJob.getNumType());
           // 人数最小
           dbBaseProjectConfJob.setNumMin(baseProjectConfJob.getNumMin());
           // 人数最大
           dbBaseProjectConfJob.setNumMax(baseProjectConfJob.getNumMax());
           // 是否兼职
           dbBaseProjectConfJob.setJobFlag(baseProjectConfJob.getJobFlag());
           // 备注
           dbBaseProjectConfJob.setRemarks(baseProjectConfJob.getRemarks());
           // 排序
           dbBaseProjectConfJob.setSort(baseProjectConfJob.getSort());
           // 共通
           dbBaseProjectConfJob.setModifyUserInfo(userKey, realName);
           flag = baseProjectConfJobMapper.updateByPrimaryKey(dbBaseProjectConfJob);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",baseProjectConfJob);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateBaseProjectConfJob(List<BaseProjectConfJob> baseProjectConfJobList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (baseProjectConfJobList != null && baseProjectConfJobList.size() > 0) {
           BaseProjectConfJob baseProjectConfJob = new BaseProjectConfJob();
           baseProjectConfJob.setModifyUserInfo(userKey, realName);
           flag = baseProjectConfJobMapper.batchDeleteUpdateBaseProjectConfJob(baseProjectConfJobList, baseProjectConfJob);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",baseProjectConfJobList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
