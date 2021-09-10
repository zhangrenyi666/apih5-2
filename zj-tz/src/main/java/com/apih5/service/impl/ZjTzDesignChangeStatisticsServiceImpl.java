package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzDesignChangeStatisticsMapper;
import com.apih5.mybatis.pojo.ZjTzDesignChangeStatistics;
import com.apih5.service.ZjTzDesignChangeStatisticsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzDesignChangeStatisticsService")
public class ZjTzDesignChangeStatisticsServiceImpl implements ZjTzDesignChangeStatisticsService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzDesignChangeStatisticsMapper zjTzDesignChangeStatisticsMapper;

    @Override
    public ResponseEntity getZjTzDesignChangeStatisticsListByCondition(ZjTzDesignChangeStatistics zjTzDesignChangeStatistics) {
        if (zjTzDesignChangeStatistics == null) {
            zjTzDesignChangeStatistics = new ZjTzDesignChangeStatistics();
        }
        // 分页查询
        PageHelper.startPage(zjTzDesignChangeStatistics.getPage(),zjTzDesignChangeStatistics.getLimit());
        // 获取数据
        List<ZjTzDesignChangeStatistics> zjTzDesignChangeStatisticsList = zjTzDesignChangeStatisticsMapper.selectByZjTzDesignChangeStatisticsList(zjTzDesignChangeStatistics);
        // 得到分页信息
        PageInfo<ZjTzDesignChangeStatistics> p = new PageInfo<>(zjTzDesignChangeStatisticsList);

        return repEntity.okList(zjTzDesignChangeStatisticsList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzDesignChangeStatisticsDetails(ZjTzDesignChangeStatistics zjTzDesignChangeStatistics) {
        if (zjTzDesignChangeStatistics == null) {
            zjTzDesignChangeStatistics = new ZjTzDesignChangeStatistics();
        }
        // 获取数据
        ZjTzDesignChangeStatistics dbZjTzDesignChangeStatistics = zjTzDesignChangeStatisticsMapper.selectByPrimaryKey(zjTzDesignChangeStatistics.getDesignChangeStatisticsId());
        // 数据存在
        if (dbZjTzDesignChangeStatistics != null) {
            return repEntity.ok(dbZjTzDesignChangeStatistics);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzDesignChangeStatistics(ZjTzDesignChangeStatistics zjTzDesignChangeStatistics) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzDesignChangeStatistics.setDesignChangeStatisticsId(UuidUtil.generate());
        zjTzDesignChangeStatistics.setCreateUserInfo(userKey, realName);
        int flag = zjTzDesignChangeStatisticsMapper.insert(zjTzDesignChangeStatistics);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzDesignChangeStatistics);
        }
    }

    @Override
    public ResponseEntity updateZjTzDesignChangeStatistics(ZjTzDesignChangeStatistics zjTzDesignChangeStatistics) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzDesignChangeStatistics dbzjTzDesignChangeStatistics = zjTzDesignChangeStatisticsMapper.selectByPrimaryKey(zjTzDesignChangeStatistics.getDesignChangeStatisticsId());
        if (dbzjTzDesignChangeStatistics != null && StrUtil.isNotEmpty(dbzjTzDesignChangeStatistics.getDesignChangeStatisticsId())) {
           // 设计变更主键id
           dbzjTzDesignChangeStatistics.setDesignChangeId(zjTzDesignChangeStatistics.getDesignChangeId());
           // 项目id
           dbzjTzDesignChangeStatistics.setProjectId(zjTzDesignChangeStatistics.getProjectId());
           // 项目name
           dbzjTzDesignChangeStatistics.setProjectName(zjTzDesignChangeStatistics.getProjectName());
           // 子项目id
           dbzjTzDesignChangeStatistics.setSubprojectInfoId(zjTzDesignChangeStatistics.getSubprojectInfoId());
           // 子项目name
           dbzjTzDesignChangeStatistics.setSubprojectName(zjTzDesignChangeStatistics.getSubprojectName());
           // a主动数量
           dbzjTzDesignChangeStatistics.setExtA1(zjTzDesignChangeStatistics.getExtA1());
           // a主动金额
           dbzjTzDesignChangeStatistics.setExtA2(zjTzDesignChangeStatistics.getExtA2());
           // a被动数量
           dbzjTzDesignChangeStatistics.setExtA3(zjTzDesignChangeStatistics.getExtA3());
           // a被动金额
           dbzjTzDesignChangeStatistics.setExtA4(zjTzDesignChangeStatistics.getExtA4());
           // a总数量
           dbzjTzDesignChangeStatistics.setExtA5(zjTzDesignChangeStatistics.getExtA5());
           // a总金额
           dbzjTzDesignChangeStatistics.setExtA6(zjTzDesignChangeStatistics.getExtA6());
           // a已完成内部审核数量
           dbzjTzDesignChangeStatistics.setExtA7(zjTzDesignChangeStatistics.getExtA7());
           // a已完成合法合规数量
           dbzjTzDesignChangeStatistics.setExtA8(zjTzDesignChangeStatistics.getExtA8());
           // b主动数量
           dbzjTzDesignChangeStatistics.setExtB1(zjTzDesignChangeStatistics.getExtB1());
           // b主动金额
           dbzjTzDesignChangeStatistics.setExtB2(zjTzDesignChangeStatistics.getExtB2());
           // b被动数量
           dbzjTzDesignChangeStatistics.setExtB3(zjTzDesignChangeStatistics.getExtB3());
           // b被动金额
           dbzjTzDesignChangeStatistics.setExtB4(zjTzDesignChangeStatistics.getExtB4());
           // b总数量
           dbzjTzDesignChangeStatistics.setExtB5(zjTzDesignChangeStatistics.getExtB5());
           // b总金额
           dbzjTzDesignChangeStatistics.setExtB6(zjTzDesignChangeStatistics.getExtB6());
           // b已完成内部审核数量
           dbzjTzDesignChangeStatistics.setExtB7(zjTzDesignChangeStatistics.getExtB7());
           // b已完成合法合规数量
           dbzjTzDesignChangeStatistics.setExtB8(zjTzDesignChangeStatistics.getExtB8());
           // c1设计数量
           dbzjTzDesignChangeStatistics.setExtC11(zjTzDesignChangeStatistics.getExtC11());
           // c1设计金额
           dbzjTzDesignChangeStatistics.setExtC12(zjTzDesignChangeStatistics.getExtC12());
           // c1动态管控数量
           dbzjTzDesignChangeStatistics.setExtC13(zjTzDesignChangeStatistics.getExtC13());
           // c1动态管控金额
           dbzjTzDesignChangeStatistics.setExtC14(zjTzDesignChangeStatistics.getExtC14());
           // c1已完成内部审核数量
           dbzjTzDesignChangeStatistics.setExtC15(zjTzDesignChangeStatistics.getExtC15());
           // c1已完成合法合规数量
           dbzjTzDesignChangeStatistics.setExtC16(zjTzDesignChangeStatistics.getExtC16());
           // c2设计数量
           dbzjTzDesignChangeStatistics.setExtC21(zjTzDesignChangeStatistics.getExtC21());
           // c2设计金额
           dbzjTzDesignChangeStatistics.setExtC22(zjTzDesignChangeStatistics.getExtC22());
           // c2已完成内部审核数量
           dbzjTzDesignChangeStatistics.setExtC23(zjTzDesignChangeStatistics.getExtC23());
           // c2已完成合法合规数量
           dbzjTzDesignChangeStatistics.setExtC24(zjTzDesignChangeStatistics.getExtC24());
           // d1设计数量
           dbzjTzDesignChangeStatistics.setExtD11(zjTzDesignChangeStatistics.getExtD11());
           // d1设计金额
           dbzjTzDesignChangeStatistics.setExtD12(zjTzDesignChangeStatistics.getExtD12());
           // d1动态管控数量
           dbzjTzDesignChangeStatistics.setExtD13(zjTzDesignChangeStatistics.getExtD13());
           // d1动态管控金额
           dbzjTzDesignChangeStatistics.setExtD14(zjTzDesignChangeStatistics.getExtD14());
           // d1已完成内部审核数量
           dbzjTzDesignChangeStatistics.setExtD15(zjTzDesignChangeStatistics.getExtD15());
           // d1已完成合法合规数量
           dbzjTzDesignChangeStatistics.setExtD16(zjTzDesignChangeStatistics.getExtD16());
           // d2设计数量
           dbzjTzDesignChangeStatistics.setExtD21(zjTzDesignChangeStatistics.getExtD21());
           // d2设计金额
           dbzjTzDesignChangeStatistics.setExtD22(zjTzDesignChangeStatistics.getExtD22());
           // d2已完成合法合规数量
           dbzjTzDesignChangeStatistics.setExtD23(zjTzDesignChangeStatistics.getExtD23());
           // 总主动数量
           dbzjTzDesignChangeStatistics.setExtAll1(zjTzDesignChangeStatistics.getExtAll1());
           // 总主动金额
           dbzjTzDesignChangeStatistics.setExtAll2(zjTzDesignChangeStatistics.getExtAll2());
           // 总被动数量
           dbzjTzDesignChangeStatistics.setExtAll3(zjTzDesignChangeStatistics.getExtAll3());
           // 总被动金额
           dbzjTzDesignChangeStatistics.setExtAll4(zjTzDesignChangeStatistics.getExtAll4());
           // 总动态设计数量
           dbzjTzDesignChangeStatistics.setExtAll5(zjTzDesignChangeStatistics.getExtAll5());
           // 总动态设计金额
           dbzjTzDesignChangeStatistics.setExtAll6(zjTzDesignChangeStatistics.getExtAll6());
           // 总数量
           dbzjTzDesignChangeStatistics.setExtAll7(zjTzDesignChangeStatistics.getExtAll7());
           // 总金额
           dbzjTzDesignChangeStatistics.setExtAll8(zjTzDesignChangeStatistics.getExtAll8());
           // 备注
           dbzjTzDesignChangeStatistics.setRemarks(zjTzDesignChangeStatistics.getRemarks());
           // 共通
           dbzjTzDesignChangeStatistics.setModifyUserInfo(userKey, realName);
           flag = zjTzDesignChangeStatisticsMapper.updateByPrimaryKey(dbzjTzDesignChangeStatistics);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzDesignChangeStatistics);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzDesignChangeStatistics(List<ZjTzDesignChangeStatistics> zjTzDesignChangeStatisticsList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzDesignChangeStatisticsList != null && zjTzDesignChangeStatisticsList.size() > 0) {
           ZjTzDesignChangeStatistics zjTzDesignChangeStatistics = new ZjTzDesignChangeStatistics();
           zjTzDesignChangeStatistics.setModifyUserInfo(userKey, realName);
           flag = zjTzDesignChangeStatisticsMapper.batchDeleteUpdateZjTzDesignChangeStatistics(zjTzDesignChangeStatisticsList, zjTzDesignChangeStatistics);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzDesignChangeStatisticsList);
        }
    }

	@Override
	public List<ZjTzDesignChangeStatistics> reportZjTzDesignChangeStatisticsList(ZjTzDesignChangeStatistics zjTzDesignChangeStatistics) {
		if (zjTzDesignChangeStatistics == null) {
            zjTzDesignChangeStatistics = new ZjTzDesignChangeStatistics();
        }
        // 获取数据
        List<ZjTzDesignChangeStatistics> zjTzDesignChangeStatisticsList = zjTzDesignChangeStatisticsMapper.selectByZjTzDesignChangeStatisticsList(zjTzDesignChangeStatistics);
        return zjTzDesignChangeStatisticsList;
	}
}
