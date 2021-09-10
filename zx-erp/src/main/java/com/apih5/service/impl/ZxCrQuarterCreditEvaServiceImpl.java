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
import com.apih5.mybatis.dao.ZxCrQuarterCreditEvaMapper;
import com.apih5.mybatis.pojo.ZxCrQuarterCreditEva;
import com.apih5.service.ZxCrQuarterCreditEvaService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxCrQuarterCreditEvaService")
public class ZxCrQuarterCreditEvaServiceImpl implements ZxCrQuarterCreditEvaService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCrQuarterCreditEvaMapper zxCrQuarterCreditEvaMapper;

    @Override
    public ResponseEntity getZxCrQuarterCreditEvaListByCondition(ZxCrQuarterCreditEva zxCrQuarterCreditEva) {
        if (zxCrQuarterCreditEva == null) {
            zxCrQuarterCreditEva = new ZxCrQuarterCreditEva();
        }
        // 分页查询
        PageHelper.startPage(zxCrQuarterCreditEva.getPage(),zxCrQuarterCreditEva.getLimit());
        // 获取数据
        List<ZxCrQuarterCreditEva> zxCrQuarterCreditEvaList = zxCrQuarterCreditEvaMapper.selectByZxCrQuarterCreditEvaList(zxCrQuarterCreditEva);
        // 得到分页信息
        PageInfo<ZxCrQuarterCreditEva> p = new PageInfo<>(zxCrQuarterCreditEvaList);

        return repEntity.okList(zxCrQuarterCreditEvaList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCrQuarterCreditEvaDetail(ZxCrQuarterCreditEva zxCrQuarterCreditEva) {
        if (zxCrQuarterCreditEva == null) {
            zxCrQuarterCreditEva = new ZxCrQuarterCreditEva();
        }
        // 获取数据
        ZxCrQuarterCreditEva dbZxCrQuarterCreditEva = zxCrQuarterCreditEvaMapper.selectByPrimaryKey(zxCrQuarterCreditEva.getZxCrQuarterCreditEvaId());
        // 数据存在
        if (dbZxCrQuarterCreditEva != null) {
            return repEntity.ok(dbZxCrQuarterCreditEva);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCrQuarterCreditEva(ZxCrQuarterCreditEva zxCrQuarterCreditEva) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCrQuarterCreditEva.setZxCrQuarterCreditEvaId(UuidUtil.generate());
        zxCrQuarterCreditEva.setCreateUserInfo(userKey, realName);
        int flag = zxCrQuarterCreditEvaMapper.insert(zxCrQuarterCreditEva);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCrQuarterCreditEva);
        }
    }

    @Override
    public ResponseEntity updateZxCrQuarterCreditEva(ZxCrQuarterCreditEva zxCrQuarterCreditEva) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCrQuarterCreditEva dbZxCrQuarterCreditEva = zxCrQuarterCreditEvaMapper.selectByPrimaryKey(zxCrQuarterCreditEva.getZxCrQuarterCreditEvaId());
        if (dbZxCrQuarterCreditEva != null && StrUtil.isNotEmpty(dbZxCrQuarterCreditEva.getZxCrQuarterCreditEvaId())) {
           // 机构ID
           dbZxCrQuarterCreditEva.setOrgID(zxCrQuarterCreditEva.getOrgID());
           // 机构名称
           dbZxCrQuarterCreditEva.setOrgName(zxCrQuarterCreditEva.getOrgName());
           // 评价期次
           dbZxCrQuarterCreditEva.setPeriod(zxCrQuarterCreditEva.getPeriod());
           // 评价日期
           dbZxCrQuarterCreditEva.setDateTime(zxCrQuarterCreditEva.getDateTime());
           // 评审状态
           dbZxCrQuarterCreditEva.setStatusName(zxCrQuarterCreditEva.getStatusName());
           // 评审标识
           dbZxCrQuarterCreditEva.setStatusFlag(zxCrQuarterCreditEva.getStatusFlag());
           // 最后编辑时间
           dbZxCrQuarterCreditEva.setEditTime(zxCrQuarterCreditEva.getEditTime());
           // 所属公司ID
           dbZxCrQuarterCreditEva.setComID(zxCrQuarterCreditEva.getComID());
           // 所属公司名称
           dbZxCrQuarterCreditEva.setComName(zxCrQuarterCreditEva.getComName());
           // 所属公司排序
           dbZxCrQuarterCreditEva.setComOrders(zxCrQuarterCreditEva.getComOrders());
           // 发起人
           dbZxCrQuarterCreditEva.setBeginPer(zxCrQuarterCreditEva.getBeginPer());
           // 进度ID
           dbZxCrQuarterCreditEva.setInstProcessId(zxCrQuarterCreditEva.getInstProcessId());
           // 审批进度ID
           dbZxCrQuarterCreditEva.setWorkitemID(zxCrQuarterCreditEva.getWorkitemID());
           // 评价编号
           dbZxCrQuarterCreditEva.setEvaluationNo(zxCrQuarterCreditEva.getEvaluationNo());
           // 备注
           dbZxCrQuarterCreditEva.setRemarks(zxCrQuarterCreditEva.getRemarks());
           // 排序
           dbZxCrQuarterCreditEva.setSort(zxCrQuarterCreditEva.getSort());
           // 共通
           dbZxCrQuarterCreditEva.setModifyUserInfo(userKey, realName);
           flag = zxCrQuarterCreditEvaMapper.updateByPrimaryKey(dbZxCrQuarterCreditEva);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCrQuarterCreditEva);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCrQuarterCreditEva(List<ZxCrQuarterCreditEva> zxCrQuarterCreditEvaList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCrQuarterCreditEvaList != null && zxCrQuarterCreditEvaList.size() > 0) {
           ZxCrQuarterCreditEva zxCrQuarterCreditEva = new ZxCrQuarterCreditEva();
           zxCrQuarterCreditEva.setModifyUserInfo(userKey, realName);
           flag = zxCrQuarterCreditEvaMapper.batchDeleteUpdateZxCrQuarterCreditEva(zxCrQuarterCreditEvaList, zxCrQuarterCreditEva);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCrQuarterCreditEvaList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
