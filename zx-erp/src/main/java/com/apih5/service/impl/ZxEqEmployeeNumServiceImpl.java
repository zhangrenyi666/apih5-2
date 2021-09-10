package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxEqEmployeeNumMapper;
import com.apih5.mybatis.pojo.ZxEqEmployeeNum;
import com.apih5.service.ZxEqEmployeeNumService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zxEqEmployeeNumService")
public class ZxEqEmployeeNumServiceImpl implements ZxEqEmployeeNumService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxEqEmployeeNumMapper zxEqEmployeeNumMapper;

    @Override
    public ResponseEntity getZxEqEmployeeNumListByCondition(ZxEqEmployeeNum zxEqEmployeeNum) {
        if (zxEqEmployeeNum == null) {
            zxEqEmployeeNum = new ZxEqEmployeeNum();
        }
        
        //年份检索
        if(zxEqEmployeeNum.getPeriodDate() != null) {
        	zxEqEmployeeNum.setPeriod(DateUtil.format(zxEqEmployeeNum.getPeriodDate(), "yyyy"));
        	zxEqEmployeeNum.setPeriodDate(null);
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxEqEmployeeNum.setCompanyId("");
        	zxEqEmployeeNum.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxEqEmployeeNum.setCompanyId(zxEqEmployeeNum.getOrgID());
        	zxEqEmployeeNum.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxEqEmployeeNum.setOrgID(zxEqEmployeeNum.getOrgID());
        }
        
        // 分页查询
        PageHelper.startPage(zxEqEmployeeNum.getPage(),zxEqEmployeeNum.getLimit());
        // 获取数据
        List<ZxEqEmployeeNum> zxEqEmployeeNumList = zxEqEmployeeNumMapper.selectByZxEqEmployeeNumList(zxEqEmployeeNum);
        // 得到分页信息
        PageInfo<ZxEqEmployeeNum> p = new PageInfo<>(zxEqEmployeeNumList);

        return repEntity.okList(zxEqEmployeeNumList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxEqEmployeeNumDetails(ZxEqEmployeeNum zxEqEmployeeNum) {
        if (zxEqEmployeeNum == null) {
            zxEqEmployeeNum = new ZxEqEmployeeNum();
        }
        // 获取数据
        ZxEqEmployeeNum dbZxEqEmployeeNum = zxEqEmployeeNumMapper.selectByPrimaryKey(zxEqEmployeeNum.getId());
        // 数据存在
        if (dbZxEqEmployeeNum != null) {
            return repEntity.ok(dbZxEqEmployeeNum);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZxEqEmployeeNum(ZxEqEmployeeNum zxEqEmployeeNum) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        if(zxEqEmployeeNum.getPeriodDate() != null) {
        	zxEqEmployeeNum.setPeriod(DateUtil.format(zxEqEmployeeNum.getPeriodDate(), "yyyy"));
        	ZxEqEmployeeNum numSelect = new ZxEqEmployeeNum();
        	numSelect.setOrgID(zxEqEmployeeNum.getOrgID());
        	numSelect.setPeriod(zxEqEmployeeNum.getPeriod());
        	List<ZxEqEmployeeNum> numList = zxEqEmployeeNumMapper.selectByZxEqEmployeeNumList(numSelect);
        	if(numList != null && numList.size() >0) {
        		return repEntity.layerMessage("no", "该年份已经添加，请重新选择！");
        	}
        }else {
        	return repEntity.layerMessage("no", "请选择添加的年份");
        }
        zxEqEmployeeNum.setId(UuidUtil.generate());
        zxEqEmployeeNum.setCreateUserInfo(userKey, realName);
        int flag = zxEqEmployeeNumMapper.insert(zxEqEmployeeNum);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zxEqEmployeeNum);
        }
    }

    @Override
    public ResponseEntity updateZxEqEmployeeNum(ZxEqEmployeeNum zxEqEmployeeNum) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxEqEmployeeNum dbzxEqEmployeeNum = zxEqEmployeeNumMapper.selectByPrimaryKey(zxEqEmployeeNum.getId());
        if (dbzxEqEmployeeNum != null && StrUtil.isNotEmpty(dbzxEqEmployeeNum.getId())) {
           // 年末职工数（个）
           dbzxEqEmployeeNum.setEmployeenum(zxEqEmployeeNum.getEmployeenum());
           // 其中工人数(个）
           dbzxEqEmployeeNum.setLabournum(zxEqEmployeeNum.getLabournum());
           // 共通
           dbzxEqEmployeeNum.setModifyUserInfo(userKey, realName);
           flag = zxEqEmployeeNumMapper.updateByPrimaryKey(dbzxEqEmployeeNum);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zxEqEmployeeNum);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxEqEmployeeNum(List<ZxEqEmployeeNum> zxEqEmployeeNumList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxEqEmployeeNumList != null && zxEqEmployeeNumList.size() > 0) {
           ZxEqEmployeeNum zxEqEmployeeNum = new ZxEqEmployeeNum();
           zxEqEmployeeNum.setModifyUserInfo(userKey, realName);
           flag = zxEqEmployeeNumMapper.batchDeleteUpdateZxEqEmployeeNum(zxEqEmployeeNumList, zxEqEmployeeNum);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zxEqEmployeeNumList);
        }
    }
}
