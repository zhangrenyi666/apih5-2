package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzProSubprojectInfoMapper;
import com.apih5.mybatis.dao.ZjTzSizeControlMapper;
import com.apih5.mybatis.pojo.ZjTzProSubprojectInfo;
import com.apih5.mybatis.pojo.ZjTzSizeControl;
import com.apih5.service.ZjTzProSubprojectInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzProSubprojectInfoService")
public class ZjTzProSubprojectInfoServiceImpl implements ZjTzProSubprojectInfoService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzProSubprojectInfoMapper zjTzProSubprojectInfoMapper;
    
    @Autowired(required = true)
    private ZjTzSizeControlMapper zjTzSizeControlMapper;

    @Override
    public ResponseEntity getZjTzProSubprojectInfoListByCondition(ZjTzProSubprojectInfo zjTzProSubprojectInfo) {
        if (zjTzProSubprojectInfo == null) {
            zjTzProSubprojectInfo = new ZjTzProSubprojectInfo();
        }
        if(StrUtil.isEmpty(zjTzProSubprojectInfo.getProjectId())){
        	return repEntity.okList(null, 0);
        }
        
        if(StrUtil.equals("all", zjTzProSubprojectInfo.getProjectId(), true)) {
        	zjTzProSubprojectInfo.setProjectId("");
        }
        
        // 分页查询
        PageHelper.startPage(zjTzProSubprojectInfo.getPage(),zjTzProSubprojectInfo.getLimit());
        // 获取数据
        List<ZjTzProSubprojectInfo> zjTzProSubprojectInfoList = zjTzProSubprojectInfoMapper.selectByZjTzProSubprojectInfoList(zjTzProSubprojectInfo);
        // 得到分页信息
        PageInfo<ZjTzProSubprojectInfo> p = new PageInfo<>(zjTzProSubprojectInfoList);

        return repEntity.okList(zjTzProSubprojectInfoList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzProSubprojectInfoDetails(ZjTzProSubprojectInfo zjTzProSubprojectInfo) {
        if (zjTzProSubprojectInfo == null) {
            zjTzProSubprojectInfo = new ZjTzProSubprojectInfo();
        }
        // 获取数据
        ZjTzProSubprojectInfo dbZjTzProSubprojectInfo = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzProSubprojectInfo.getSubprojectInfoId());
        // 数据存在
        if (dbZjTzProSubprojectInfo != null) {
            return repEntity.ok(dbZjTzProSubprojectInfo);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzProSubprojectInfo(ZjTzProSubprojectInfo zjTzProSubprojectInfo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzProSubprojectInfo.setSubprojectInfoId(UuidUtil.generate());
        zjTzProSubprojectInfo.setCreateUserInfo(userKey, realName);
        int flag = zjTzProSubprojectInfoMapper.insert(zjTzProSubprojectInfo);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzProSubprojectInfo);
        }
    }

    @Override
    public ResponseEntity updateZjTzProSubprojectInfo(ZjTzProSubprojectInfo zjTzProSubprojectInfo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzProSubprojectInfo dbzjTzProSubprojectInfo = zjTzProSubprojectInfoMapper.selectByPrimaryKey(zjTzProSubprojectInfo.getSubprojectInfoId());
        if (dbzjTzProSubprojectInfo != null && StrUtil.isNotEmpty(dbzjTzProSubprojectInfo.getSubprojectInfoId())) {
           // 项目id
           dbzjTzProSubprojectInfo.setProjectId(zjTzProSubprojectInfo.getProjectId());
           // 子项目编号
           dbzjTzProSubprojectInfo.setSubprojectNumber(zjTzProSubprojectInfo.getSubprojectNumber());
           // 子项目名称
           dbzjTzProSubprojectInfo.setSubprojectName(zjTzProSubprojectInfo.getSubprojectName());
           // 建设期结束标志
           dbzjTzProSubprojectInfo.setConstructEnd(zjTzProSubprojectInfo.getConstructEnd());
           // 建设期
           dbzjTzProSubprojectInfo.setConstructPeriod(zjTzProSubprojectInfo.getConstructPeriod());
           // 实际开工时间
           dbzjTzProSubprojectInfo.setStartDateActual(zjTzProSubprojectInfo.getStartDateActual());
           // 实际交工时间
           dbzjTzProSubprojectInfo.setHandoverDateActual(zjTzProSubprojectInfo.getHandoverDateActual());
           // 实际竣工时间
           dbzjTzProSubprojectInfo.setComplateDateActual(zjTzProSubprojectInfo.getComplateDateActual());
           // 应交工时间
           dbzjTzProSubprojectInfo.setHandoverDatePlan(zjTzProSubprojectInfo.getHandoverDatePlan());
           // 应竣工时间
           dbzjTzProSubprojectInfo.setComplateDatePlan(zjTzProSubprojectInfo.getComplateDatePlan());
           // 策划批复交工时间
           dbzjTzProSubprojectInfo.setApprovalHandoverDate(zjTzProSubprojectInfo.getApprovalHandoverDate());
           // 策划批复竣工时间
           dbzjTzProSubprojectInfo.setApprovalCompleteDate(zjTzProSubprojectInfo.getApprovalCompleteDate());
           // 策划批复开工时间
           dbzjTzProSubprojectInfo.setApprovalStartDate(zjTzProSubprojectInfo.getApprovalStartDate());
           // 合同约定开工时间
           dbzjTzProSubprojectInfo.setSignDate3(zjTzProSubprojectInfo.getSignDate3());
           // 合同约定运营/回购开始时间
           dbzjTzProSubprojectInfo.setRunDate1(zjTzProSubprojectInfo.getRunDate1());
           // 合同约定运营/回购结束时间
           dbzjTzProSubprojectInfo.setRunDate3(zjTzProSubprojectInfo.getRunDate3());
           // 实际运营/回购开始时间
           dbzjTzProSubprojectInfo.setRunDate2(zjTzProSubprojectInfo.getRunDate2());
           // 实际运营/回购结束时间
           dbzjTzProSubprojectInfo.setRunDate4(zjTzProSubprojectInfo.getRunDate4());
           // 中标投资额（元）
           dbzjTzProSubprojectInfo.setAmount1(zjTzProSubprojectInfo.getAmount1());
           // 中标建安费（元）
           dbzjTzProSubprojectInfo.setAmount3(zjTzProSubprojectInfo.getAmount3());
           // 共通
           dbzjTzProSubprojectInfo.setModifyUserInfo(userKey, realName);
           flag = zjTzProSubprojectInfoMapper.updateByPrimaryKey(dbzjTzProSubprojectInfo);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzProSubprojectInfo);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzProSubprojectInfo(List<ZjTzProSubprojectInfo> zjTzProSubprojectInfoList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0; 
        if (zjTzProSubprojectInfoList != null && zjTzProSubprojectInfoList.size() > 0) {
        	for (ZjTzProSubprojectInfo subprojectInfo : zjTzProSubprojectInfoList) {
        		ZjTzSizeControl record = new ZjTzSizeControl();
        		record.setProjectId(subprojectInfo.getProjectId()) ;
        		record.setSubprojectInfoId(subprojectInfo.getSubprojectInfoId()) ;
        		List<ZjTzSizeControl> controls = zjTzSizeControlMapper.selectByZjTzSizeControlList(record);
        		if(controls != null && controls.size() >0) {
        			return repEntity.layerMessage("no", "该子项目已经填写投资规模控制信息，不能删除！！");
        		}
        	}
        	ZjTzProSubprojectInfo zjTzProSubprojectInfo = new ZjTzProSubprojectInfo();
        	zjTzProSubprojectInfo.setModifyUserInfo(userKey, realName);
        	flag = zjTzProSubprojectInfoMapper.batchDeleteUpdateZjTzProSubprojectInfo(zjTzProSubprojectInfoList, zjTzProSubprojectInfo);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzProSubprojectInfoList);
        }
    }
}
