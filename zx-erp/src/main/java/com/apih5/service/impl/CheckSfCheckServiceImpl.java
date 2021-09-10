package com.apih5.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.date.DateUtil;
import com.apih5.framework.utils.CalcUtils;
import org.apache.commons.collections.list.LazyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.CheckSfCheckMapper;
import com.apih5.mybatis.pojo.CheckSfCheck;
import com.apih5.service.CheckSfCheckService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("checkSfCheckService")
public class CheckSfCheckServiceImpl implements CheckSfCheckService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private CheckSfCheckMapper checkSfCheckMapper;

    @Override
    public ResponseEntity getCheckSfCheckListByCondition(CheckSfCheck checkSfCheck) {
        if (checkSfCheck == null) {
            checkSfCheck = new CheckSfCheck();
        }
        // 分页查询
        PageHelper.startPage(checkSfCheck.getPage(),checkSfCheck.getLimit());
        // 获取数据
        List<CheckSfCheck> checkSfCheckList = checkSfCheckMapper.selectByCheckSfCheckList(checkSfCheck);
        // 得到分页信息
        PageInfo<CheckSfCheck> p = new PageInfo<>(checkSfCheckList);

        return repEntity.okList(checkSfCheckList, p.getTotal());
    }

    @Override
    public ResponseEntity getCheckSfCheckDetail(CheckSfCheck checkSfCheck) {
        if (checkSfCheck == null) {
            checkSfCheck = new CheckSfCheck();
        }
        // 获取数据
        CheckSfCheck dbCheckSfCheck = checkSfCheckMapper.selectByPrimaryKey(checkSfCheck.getCheckSfCheckId());
        // 数据存在
        if (dbCheckSfCheck != null) {
            return repEntity.ok(dbCheckSfCheck);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveCheckSfCheck(CheckSfCheck checkSfCheck) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        checkSfCheck.setCheckSfCheckId(UuidUtil.generate());
        checkSfCheck.setCreateUserInfo(userKey, realName);
        int flag = checkSfCheckMapper.insert(checkSfCheck);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", checkSfCheck);
        }
    }

    @Override
    public ResponseEntity updateCheckSfCheck(CheckSfCheck checkSfCheck) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        CheckSfCheck dbCheckSfCheck = checkSfCheckMapper.selectByPrimaryKey(checkSfCheck.getCheckSfCheckId());
        if (dbCheckSfCheck != null && StrUtil.isNotEmpty(dbCheckSfCheck.getCheckSfCheckId())) {
           // 项目/机构名称
           dbCheckSfCheck.setOrgName(checkSfCheck.getOrgName());
           // 项目/机构ID
           dbCheckSfCheck.setOrgID(checkSfCheck.getOrgID());
           // 项目总数
           dbCheckSfCheck.setOrgNum(checkSfCheck.getOrgNum());
           // 已检查项目数
           dbCheckSfCheck.setCheckNum(checkSfCheck.getCheckNum());
           // 未检查项目数
           dbCheckSfCheck.setNoCheckNum(checkSfCheck.getNoCheckNum());
           // 检查覆盖率
           dbCheckSfCheck.setCheckFGL(checkSfCheck.getCheckFGL());
           // 开始日期
           dbCheckSfCheck.setStartDate(checkSfCheck.getStartDate());
           // 结束日期
           dbCheckSfCheck.setEndDate(checkSfCheck.getEndDate());
           // 公司ID
           dbCheckSfCheck.setCompanyId(checkSfCheck.getCompanyId());
           // 公司名称
           dbCheckSfCheck.setCompanyName(checkSfCheck.getCompanyName());
           // 共通
           dbCheckSfCheck.setModifyUserInfo(userKey, realName);
           flag = checkSfCheckMapper.updateByPrimaryKey(dbCheckSfCheck);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",checkSfCheck);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateCheckSfCheck(List<CheckSfCheck> checkSfCheckList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (checkSfCheckList != null && checkSfCheckList.size() > 0) {
           CheckSfCheck checkSfCheck = new CheckSfCheck();
           checkSfCheck.setModifyUserInfo(userKey, realName);
           flag = checkSfCheckMapper.batchDeleteUpdateCheckSfCheck(checkSfCheckList, checkSfCheck);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",checkSfCheckList);
        }
    }



    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    /**
     * 查询公司
     * @suncg
     * @param checkSfCheck
     * */
    @Override
    public ResponseEntity getCompany(CheckSfCheck checkSfCheck) {
        CheckSfCheck dbcheck= checkSfCheckMapper.getCompany(checkSfCheck);
        dbcheck.setCheckFGL(CalcUtils.calcDivide(new BigDecimal(dbcheck.getOrgNum()),new BigDecimal(dbcheck.getCheckNum()),2));
        return repEntity.ok(dbcheck);
    }


    /**
     * 查询项目(状态区分)
     * @suncg
     * @param checkSfCheck
     * */
    @Override
    public ResponseEntity getCheckList(CheckSfCheck checkSfCheck) {
        List<CheckSfCheck> checklist= new ArrayList<CheckSfCheck>();

        String today = DateUtil.format(new Date(),"yyyyMMdd");
        //归档查询
        CheckSfCheck gdcheck= checkSfCheckMapper.getGuiDang(checkSfCheck,today);
        gdcheck.setCheckFGL(CalcUtils.calcDivide(new BigDecimal(gdcheck.getOrgNum()),new BigDecimal(gdcheck.getCheckNum()),2));
        //交工查询
        CheckSfCheck jgcheck = checkSfCheckMapper.getJiaoGong(checkSfCheck,today);
        jgcheck.setCheckFGL(CalcUtils.calcDivide(new BigDecimal(jgcheck.getOrgNum()),new BigDecimal(jgcheck.getCheckNum()),2));
        //完工
        CheckSfCheck wgcheck=checkSfCheckMapper.getWanGong(checkSfCheck,today);
        wgcheck.setCheckFGL(CalcUtils.calcDivide(new BigDecimal(wgcheck.getOrgNum()),new BigDecimal(wgcheck.getCheckNum()),2));
        //
        CheckSfCheck kgcheck=checkSfCheckMapper.getKaiGong(checkSfCheck,today);
        kgcheck.setCheckFGL(CalcUtils.calcDivide(new BigDecimal(kgcheck.getOrgNum()),new BigDecimal(kgcheck.getCheckNum()),2));

        checklist.add(gdcheck);
        checklist.add(jgcheck);
        checklist.add(wgcheck);
        checklist.add(kgcheck);

        return repEntity.ok(checklist);
    }

    /**
     * 查询归档项目数据
     * @suncg
     * @param checkSfCheck
     * */
    public ResponseEntity getGuiDangList(CheckSfCheck checkSfCheck){
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        List<CheckSfCheck> guiDangList=checkSfCheckMapper.getGuiDangList(checkSfCheck,today);
        return repEntity.ok(guiDangList);
    }

    /**
     * 查询交工数据
     * @suncg
     * @param checkSfCheck
     * */
    public ResponseEntity getJiaoGongList(CheckSfCheck checkSfCheck){
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        List<CheckSfCheck> jiaoGongList=checkSfCheckMapper.getJiaoGongList(checkSfCheck,today);
        return repEntity.ok(jiaoGongList);
    }

    /**
     * 查询完工数据
     * @suncg
     * @param checkSfCheck
     * */
    public ResponseEntity getWanGongList(CheckSfCheck checkSfCheck){
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        List<CheckSfCheck> wanGongList=checkSfCheckMapper.getWanGongList(checkSfCheck,today);
        return repEntity.ok(wanGongList);
    }

    /**
     * 查询开工数据
     * @suncg
     * @param checkSfCheck
     * */
    public ResponseEntity getKaiGongList(CheckSfCheck checkSfCheck){
        String today = DateUtil.format(new Date(),"yyyyMMdd");
        List<CheckSfCheck> kaiGongList=checkSfCheckMapper.getKaiGongList(checkSfCheck,today);
        return repEntity.ok(kaiGongList);
    }

    public ResponseEntity getComList(CheckSfCheck checkSfCheck){
        List<CheckSfCheck> comList=checkSfCheckMapper.getComList(checkSfCheck);
        return repEntity.ok(comList);
    }

}
