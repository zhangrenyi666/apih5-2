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
import com.apih5.mybatis.dao.BaseProjectConfMapper;
import com.apih5.mybatis.pojo.BaseProjectConf;
import com.apih5.mybatis.pojo.BaseProjectConfJob;
import com.apih5.service.BaseProjectConfService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;

@Service("baseProjectConfService")
public class BaseProjectConfServiceImpl implements BaseProjectConfService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private BaseProjectConfMapper baseProjectConfMapper;

    @Autowired(required = true)
    private BaseProjectConfJobMapper baseProjectConfJobMapper;

    @Override
    public ResponseEntity getBaseProjectConfListByCondition(BaseProjectConf baseProjectConf) {
        if (baseProjectConf == null) {
            baseProjectConf = new BaseProjectConf();
        }
        // 分页查询
        PageHelper.startPage(baseProjectConf.getPage(),baseProjectConf.getLimit());
        // 获取数据
        List<BaseProjectConf> baseProjectConfList = baseProjectConfMapper.selectByBaseProjectConfList(baseProjectConf);
        if(baseProjectConfList != null && baseProjectConfList.size()>0) {
            for(BaseProjectConf dbBaseProjectConf:baseProjectConfList) {
                BaseProjectConfJob baseProjectConfJob = new BaseProjectConfJob();
                baseProjectConfJob.setBaseProjectConfId(dbBaseProjectConf.getBaseProjectConfId());
                List<BaseProjectConfJob> baseProjectConfJobList = baseProjectConfJobMapper.selectByBaseProjectConfJobList(baseProjectConfJob);
                dbBaseProjectConf.setBaseProjectConfJobList(baseProjectConfJobList);
            }
        }
        // 得到分页信息
        PageInfo<BaseProjectConf> p = new PageInfo<>(baseProjectConfList);

        return repEntity.okList(baseProjectConfList, p.getTotal());
    }

    @Override
    public ResponseEntity getBaseProjectConfDetail(BaseProjectConf baseProjectConf) {
        if (baseProjectConf == null) {
            baseProjectConf = new BaseProjectConf();
        }
        // 获取数据
        BaseProjectConf dbBaseProjectConf = baseProjectConfMapper.selectByPrimaryKey(baseProjectConf.getBaseProjectConfId());
        // 数据存在
        if (dbBaseProjectConf != null) {
            return repEntity.ok(dbBaseProjectConf);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveBaseProjectConf(BaseProjectConf baseProjectConf) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        // 工程类型\项目级别\部门名称不能同时重复
        BaseProjectConf baseProjectConfCheck = new BaseProjectConf();
        baseProjectConfCheck.setProjType(baseProjectConf.getProjType());
        baseProjectConfCheck.setProLevel(baseProjectConf.getProLevel());
        baseProjectConfCheck.setDepartmentName(baseProjectConf.getDepartmentName());
        List<BaseProjectConf> baseProjectConfList = baseProjectConfMapper.selectByBaseProjectConfList(baseProjectConfCheck);
        if(baseProjectConfList != null && baseProjectConfList.size()>0) {
            return repEntity.layerMessage("no", "数据已存在，不允许重复添加");
        }
        
        baseProjectConf.setBaseProjectConfId(UuidUtil.generate());
        baseProjectConf.setCreateUserInfo(userKey, realName);
        int flag = baseProjectConfMapper.insert(baseProjectConf);
        List<BaseProjectConfJob> baseProjectConfJobList = baseProjectConf.getBaseProjectConfJobList();
        if(baseProjectConfJobList != null && baseProjectConfJobList.size()>0) {
            for(BaseProjectConfJob baseProjectConfJob:baseProjectConfJobList) {
                baseProjectConfJob.setBaseProjectJobConfId(IdUtil.getSnowflake(1, 1).nextIdStr());
                baseProjectConfJob.setBaseProjectConfId(baseProjectConf.getBaseProjectConfId());
                baseProjectConfJob.setCreateUserInfo(userKey, realName);
                baseProjectConfJobMapper.insert(baseProjectConfJob);
            }
        }
        
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", baseProjectConf);
        }
    }

    @Override
    public ResponseEntity updateBaseProjectConf(BaseProjectConf baseProjectConf) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        BaseProjectConf dbBaseProjectConf = baseProjectConfMapper.selectByPrimaryKey(baseProjectConf.getBaseProjectConfId());
        if (dbBaseProjectConf != null && StrUtil.isNotEmpty(dbBaseProjectConf.getBaseProjectConfId())) {
           // 工程类型
           dbBaseProjectConf.setProjType(baseProjectConf.getProjType());
           // 项目级别
           dbBaseProjectConf.setProLevel(baseProjectConf.getProLevel());
           // 部门名称
           dbBaseProjectConf.setDepartmentName(baseProjectConf.getDepartmentName());
           // 部门人数
           dbBaseProjectConf.setDepartmentNum(baseProjectConf.getDepartmentNum());
           // 岗位项目人员配置字典表明细
           dbBaseProjectConf.setBaseProjectConfJobList(baseProjectConf.getBaseProjectConfJobList());
           // 备注
           dbBaseProjectConf.setRemarks(baseProjectConf.getRemarks());
           // 排序
           dbBaseProjectConf.setSort(baseProjectConf.getSort());
           // 共通
           dbBaseProjectConf.setModifyUserInfo(userKey, realName);
           flag = baseProjectConfMapper.updateByPrimaryKey(dbBaseProjectConf);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",baseProjectConf);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateBaseProjectConf(List<BaseProjectConf> baseProjectConfList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (baseProjectConfList != null && baseProjectConfList.size() > 0) {
           BaseProjectConf baseProjectConf = new BaseProjectConf();
           baseProjectConf.setModifyUserInfo(userKey, realName);
           flag = baseProjectConfMapper.batchDeleteUpdateBaseProjectConf(baseProjectConfList, baseProjectConf);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",baseProjectConfList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
