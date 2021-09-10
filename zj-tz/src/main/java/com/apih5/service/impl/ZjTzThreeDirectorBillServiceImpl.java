package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzThreeDirectorBillMapper;
import com.apih5.mybatis.pojo.ZjTzThreeDirectorBill;
import com.apih5.service.ZjTzThreeDirectorBillService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzThreeDirectorBillService")
public class ZjTzThreeDirectorBillServiceImpl implements ZjTzThreeDirectorBillService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzThreeDirectorBillMapper zjTzThreeDirectorBillMapper;
    
    @Override
    public ResponseEntity getZjTzThreeDirectorBillListByCondition(ZjTzThreeDirectorBill zjTzThreeDirectorBill) {
        if (zjTzThreeDirectorBill == null) {
            zjTzThreeDirectorBill = new ZjTzThreeDirectorBill();
        }
        if(StrUtil.isEmpty(zjTzThreeDirectorBill.getThreeDirectorId())) {
       	 return repEntity.okList(null, 0);
       }
        // 分页查询
        PageHelper.startPage(zjTzThreeDirectorBill.getPage(),zjTzThreeDirectorBill.getLimit());
        // 获取数据
        List<ZjTzThreeDirectorBill> zjTzThreeDirectorBillList = zjTzThreeDirectorBillMapper.selectByZjTzThreeDirectorBillList(zjTzThreeDirectorBill);
        // 得到分页信息
        PageInfo<ZjTzThreeDirectorBill> p = new PageInfo<>(zjTzThreeDirectorBillList);

        return repEntity.okList(zjTzThreeDirectorBillList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzThreeDirectorBillDetails(ZjTzThreeDirectorBill zjTzThreeDirectorBill) {
        if (zjTzThreeDirectorBill == null) {
            zjTzThreeDirectorBill = new ZjTzThreeDirectorBill();
        }
        // 获取数据
        ZjTzThreeDirectorBill dbZjTzThreeDirectorBill = zjTzThreeDirectorBillMapper.selectByPrimaryKey(zjTzThreeDirectorBill.getThreeDirectorBillId());
        // 数据存在
        if (dbZjTzThreeDirectorBill != null) {
            return repEntity.ok(dbZjTzThreeDirectorBill);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzThreeDirectorBill(ZjTzThreeDirectorBill zjTzThreeDirectorBill) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzThreeDirectorBill.setThreeDirectorBillId(UuidUtil.generate());
        //决议结果
        if (StrUtil.equals(zjTzThreeDirectorBill.getResultId(), "1")) {
        	zjTzThreeDirectorBill.setResultName("同意");
        }else if (StrUtil.equals(zjTzThreeDirectorBill.getResultId(), "2")) {
        	zjTzThreeDirectorBill.setResultName("不同意");
        }else if (StrUtil.equals(zjTzThreeDirectorBill.getResultId(), "3")) {
        	zjTzThreeDirectorBill.setResultName("其他");
        }
        //决议完成情况
        if (StrUtil.equals(zjTzThreeDirectorBill.getCompleteId(), "1")) {
        	zjTzThreeDirectorBill.setCompleteName("完成");
        }else if (StrUtil.equals(zjTzThreeDirectorBill.getCompleteId(), "2")) {
        	zjTzThreeDirectorBill.setCompleteName("未完成");
        }
        zjTzThreeDirectorBill.setCreateUserInfo(userKey, realName);
        int flag = zjTzThreeDirectorBillMapper.insert(zjTzThreeDirectorBill);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzThreeDirectorBill);
        }
    }

    @Override
    public ResponseEntity updateZjTzThreeDirectorBill(ZjTzThreeDirectorBill zjTzThreeDirectorBill) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzThreeDirectorBill dbzjTzThreeDirectorBill = zjTzThreeDirectorBillMapper.selectByPrimaryKey(zjTzThreeDirectorBill.getThreeDirectorBillId());
        if (dbzjTzThreeDirectorBill != null && StrUtil.isNotEmpty(dbzjTzThreeDirectorBill.getThreeDirectorBillId())) {
           // 董事会id
           dbzjTzThreeDirectorBill.setThreeDirectorId(zjTzThreeDirectorBill.getThreeDirectorId());
           // 议案名称
           dbzjTzThreeDirectorBill.setBillName(zjTzThreeDirectorBill.getBillName());
           // 决议结果id
           dbzjTzThreeDirectorBill.setResultId(zjTzThreeDirectorBill.getResultId());
           // 决议结果name
           // 决议结果name
           if (StrUtil.equals(zjTzThreeDirectorBill.getResultId(), "1")) {
        	   dbzjTzThreeDirectorBill.setResultName("同意");
           }else if (StrUtil.equals(zjTzThreeDirectorBill.getResultId(), "2")) {
        	   dbzjTzThreeDirectorBill.setResultName("不同意");
           }else if (StrUtil.equals(zjTzThreeDirectorBill.getResultId(), "3")) {
        	   dbzjTzThreeDirectorBill.setResultName("其他");
           }
           // 会议提出的其他要求
           dbzjTzThreeDirectorBill.setOtherRequire(zjTzThreeDirectorBill.getOtherRequire());
           // 决议执行情况具体描述
           dbzjTzThreeDirectorBill.setSpecificDesc(zjTzThreeDirectorBill.getSpecificDesc());
           // 决议完成情况id
           dbzjTzThreeDirectorBill.setCompleteId(zjTzThreeDirectorBill.getCompleteId());
           // 决议完成情况name
           if (StrUtil.equals(zjTzThreeDirectorBill.getCompleteId(), "1")) {
        	   dbzjTzThreeDirectorBill.setCompleteName("完成");
           }else if (StrUtil.equals(zjTzThreeDirectorBill.getCompleteId(), "2")) {
        	   dbzjTzThreeDirectorBill.setCompleteName("未完成");
           }
           // 备注
           dbzjTzThreeDirectorBill.setRemarks(zjTzThreeDirectorBill.getRemarks());
           // 排序号
           dbzjTzThreeDirectorBill.setSortNumber(zjTzThreeDirectorBill.getSortNumber());
           // 共通
           dbzjTzThreeDirectorBill.setModifyUserInfo(userKey, realName);
           flag = zjTzThreeDirectorBillMapper.updateByPrimaryKey(dbzjTzThreeDirectorBill);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzThreeDirectorBill);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzThreeDirectorBill(List<ZjTzThreeDirectorBill> zjTzThreeDirectorBillList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzThreeDirectorBillList != null && zjTzThreeDirectorBillList.size() > 0) {
           ZjTzThreeDirectorBill zjTzThreeDirectorBill = new ZjTzThreeDirectorBill();
           zjTzThreeDirectorBill.setModifyUserInfo(userKey, realName);
           flag = zjTzThreeDirectorBillMapper.batchDeleteUpdateZjTzThreeDirectorBill(zjTzThreeDirectorBillList, zjTzThreeDirectorBill);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzThreeDirectorBillList);
        }
    }
}
