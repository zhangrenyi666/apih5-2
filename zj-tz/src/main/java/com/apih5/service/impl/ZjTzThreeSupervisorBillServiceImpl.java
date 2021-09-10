package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzThreeSupervisorBillMapper;
import com.apih5.mybatis.pojo.ZjTzThreeSupervisorBill;
import com.apih5.service.ZjTzThreeSupervisorBillService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzThreeSupervisorBillService")
public class ZjTzThreeSupervisorBillServiceImpl implements ZjTzThreeSupervisorBillService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzThreeSupervisorBillMapper zjTzThreeSupervisorBillMapper;

    @Override
    public ResponseEntity getZjTzThreeSupervisorBillListByCondition(ZjTzThreeSupervisorBill zjTzThreeSupervisorBill) {
    	if (zjTzThreeSupervisorBill == null) {
    		zjTzThreeSupervisorBill = new ZjTzThreeSupervisorBill();
    	}
    	if(StrUtil.isEmpty(zjTzThreeSupervisorBill.getThreeSupervisorId())) {
    		return repEntity.okList(null, 0);
    	}
        // 分页查询
        PageHelper.startPage(zjTzThreeSupervisorBill.getPage(),zjTzThreeSupervisorBill.getLimit());
        // 获取数据
        List<ZjTzThreeSupervisorBill> zjTzThreeSupervisorBillList = zjTzThreeSupervisorBillMapper.selectByZjTzThreeSupervisorBillList(zjTzThreeSupervisorBill);
        // 得到分页信息
        PageInfo<ZjTzThreeSupervisorBill> p = new PageInfo<>(zjTzThreeSupervisorBillList);

        return repEntity.okList(zjTzThreeSupervisorBillList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzThreeSupervisorBillDetails(ZjTzThreeSupervisorBill zjTzThreeSupervisorBill) {
        if (zjTzThreeSupervisorBill == null) {
            zjTzThreeSupervisorBill = new ZjTzThreeSupervisorBill();
        }
        // 获取数据
        ZjTzThreeSupervisorBill dbZjTzThreeSupervisorBill = zjTzThreeSupervisorBillMapper.selectByPrimaryKey(zjTzThreeSupervisorBill.getThreeSupervisorBillId());
        // 数据存在
        if (dbZjTzThreeSupervisorBill != null) {
            return repEntity.ok(dbZjTzThreeSupervisorBill);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzThreeSupervisorBill(ZjTzThreeSupervisorBill zjTzThreeSupervisorBill) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzThreeSupervisorBill.setThreeSupervisorBillId(UuidUtil.generate());
        //决议结果
        if (StrUtil.equals(zjTzThreeSupervisorBill.getResultId(), "1")) {
        	zjTzThreeSupervisorBill.setResultName("同意");
        }else if (StrUtil.equals(zjTzThreeSupervisorBill.getResultId(), "2")) {
        	zjTzThreeSupervisorBill.setResultName("不同意");
        }else if (StrUtil.equals(zjTzThreeSupervisorBill.getResultId(), "3")) {
        	zjTzThreeSupervisorBill.setResultName("其他");
        }
        //决议完成情况
        if (StrUtil.equals(zjTzThreeSupervisorBill.getCompleteId(), "1")) {
        	zjTzThreeSupervisorBill.setCompleteName("完成");
        }else if (StrUtil.equals(zjTzThreeSupervisorBill.getCompleteId(), "2")) {
        	zjTzThreeSupervisorBill.setCompleteName("未完成");
        }
        zjTzThreeSupervisorBill.setCreateUserInfo(userKey, realName);
        int flag = zjTzThreeSupervisorBillMapper.insert(zjTzThreeSupervisorBill);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzThreeSupervisorBill);
        }
    }

    @Override
    public ResponseEntity updateZjTzThreeSupervisorBill(ZjTzThreeSupervisorBill zjTzThreeSupervisorBill) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzThreeSupervisorBill dbzjTzThreeSupervisorBill = zjTzThreeSupervisorBillMapper.selectByPrimaryKey(zjTzThreeSupervisorBill.getThreeSupervisorBillId());
        if (dbzjTzThreeSupervisorBill != null && StrUtil.isNotEmpty(dbzjTzThreeSupervisorBill.getThreeSupervisorBillId())) {
           // 董事会id
           dbzjTzThreeSupervisorBill.setThreeSupervisorId(zjTzThreeSupervisorBill.getThreeSupervisorId());
           // 议案名称
           dbzjTzThreeSupervisorBill.setBillName(zjTzThreeSupervisorBill.getBillName());
           // 决议结果id
           dbzjTzThreeSupervisorBill.setResultId(zjTzThreeSupervisorBill.getResultId());
           // 决议结果name
           // 决议结果name
           if (StrUtil.equals(zjTzThreeSupervisorBill.getResultId(), "1")) {
        	   dbzjTzThreeSupervisorBill.setResultName("同意");
           }else if (StrUtil.equals(zjTzThreeSupervisorBill.getResultId(), "2")) {
        	   dbzjTzThreeSupervisorBill.setResultName("不同意");
           }else if (StrUtil.equals(zjTzThreeSupervisorBill.getResultId(), "3")) {
        	   dbzjTzThreeSupervisorBill.setResultName("其他");
           }
           // 会议提出的其他要求
           dbzjTzThreeSupervisorBill.setOtherRequire(zjTzThreeSupervisorBill.getOtherRequire());
           // 决议执行情况具体描述
           dbzjTzThreeSupervisorBill.setSpecificDesc(zjTzThreeSupervisorBill.getSpecificDesc());
           // 决议完成情况id
           dbzjTzThreeSupervisorBill.setCompleteId(zjTzThreeSupervisorBill.getCompleteId());
           // 决议完成情况name
           if (StrUtil.equals(zjTzThreeSupervisorBill.getCompleteId(), "1")) {
        	   dbzjTzThreeSupervisorBill.setCompleteName("完成");
           }else if (StrUtil.equals(zjTzThreeSupervisorBill.getCompleteId(), "2")) {
        	   dbzjTzThreeSupervisorBill.setCompleteName("未完成");
           }
           // 备注
           dbzjTzThreeSupervisorBill.setRemarks(zjTzThreeSupervisorBill.getRemarks());
           // 排序号
           dbzjTzThreeSupervisorBill.setSortNumber(zjTzThreeSupervisorBill.getSortNumber());
           // 共通
           dbzjTzThreeSupervisorBill.setModifyUserInfo(userKey, realName);
           flag = zjTzThreeSupervisorBillMapper.updateByPrimaryKey(dbzjTzThreeSupervisorBill);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzThreeSupervisorBill);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzThreeSupervisorBill(List<ZjTzThreeSupervisorBill> zjTzThreeSupervisorBillList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzThreeSupervisorBillList != null && zjTzThreeSupervisorBillList.size() > 0) {
           ZjTzThreeSupervisorBill zjTzThreeSupervisorBill = new ZjTzThreeSupervisorBill();
           zjTzThreeSupervisorBill.setModifyUserInfo(userKey, realName);
           flag = zjTzThreeSupervisorBillMapper.batchDeleteUpdateZjTzThreeSupervisorBill(zjTzThreeSupervisorBillList, zjTzThreeSupervisorBill);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzThreeSupervisorBillList);
        }
    }
}
