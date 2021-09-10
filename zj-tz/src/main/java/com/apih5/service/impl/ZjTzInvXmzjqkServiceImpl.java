package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.ConvertUtil;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzInvXmzjqkMapper;
import com.apih5.mybatis.pojo.ZjTzInvXmzjqk;
import com.apih5.service.ZjTzInvXmzjqkService;
import com.apih5.service.ZjTzPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

@Service("zjTzInvXmzjqkService")
public class ZjTzInvXmzjqkServiceImpl implements ZjTzInvXmzjqkService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzInvXmzjqkMapper zjTzInvXmzjqkMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
    @Override
    public ResponseEntity getZjTzInvXmzjqkListByCondition(ZjTzInvXmzjqk zjTzInvXmzjqk) {
        if (zjTzInvXmzjqk == null) {
            zjTzInvXmzjqk = new ZjTzInvXmzjqk();
        }
        // 分页查询
        PageHelper.startPage(zjTzInvXmzjqk.getPage(),zjTzInvXmzjqk.getLimit());
        // 获取数据
        List<ZjTzInvXmzjqk> zjTzInvXmzjqkList = zjTzInvXmzjqkMapper.selectByZjTzInvXmzjqkList(zjTzInvXmzjqk);
        // 得到分页信息
        PageInfo<ZjTzInvXmzjqk> p = new PageInfo<>(zjTzInvXmzjqkList);

        return repEntity.okList(zjTzInvXmzjqkList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzInvXmzjqkDetails(ZjTzInvXmzjqk zjTzInvXmzjqk) {
        if (zjTzInvXmzjqk == null) {
            zjTzInvXmzjqk = new ZjTzInvXmzjqk();
        }
        // 获取数据
        ZjTzInvXmzjqk dbZjTzInvXmzjqk = zjTzInvXmzjqkMapper.selectByPrimaryKey(zjTzInvXmzjqk.getId());
        // 数据存在
        if (dbZjTzInvXmzjqk != null) {
            return repEntity.ok(dbZjTzInvXmzjqk);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzInvXmzjqk(ZjTzInvXmzjqk zjTzInvXmzjqk) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzInvXmzjqk.setId(UuidUtil.generate());
        zjTzInvXmzjqk.setCreateUserInfo(userKey, realName);
        int flag = zjTzInvXmzjqkMapper.insert(zjTzInvXmzjqk);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzInvXmzjqk);
        }
    }

    @Override
    public ResponseEntity updateZjTzInvXmzjqk(ZjTzInvXmzjqk zjTzInvXmzjqk) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzInvXmzjqk dbzjTzInvXmzjqk = zjTzInvXmzjqkMapper.selectByPrimaryKey(zjTzInvXmzjqk.getId());
        if (dbzjTzInvXmzjqk != null && StrUtil.isNotEmpty(dbzjTzInvXmzjqk.getId())) {
           // 
           dbzjTzInvXmzjqk.setInvProId(zjTzInvXmzjqk.getInvProId());
           // 
           dbzjTzInvXmzjqk.setZjxqqkZyzj(zjTzInvXmzjqk.getZjxqqkZyzj());
           // 
           dbzjTzInvXmzjqk.setZjxqqkQzjj(zjTzInvXmzjqk.getZjxqqkQzjj());
           // 
           dbzjTzInvXmzjqk.setZjxqqkQtgdzbj(zjTzInvXmzjqk.getZjxqqkQtgdzbj());
           // 
           dbzjTzInvXmzjqk.setZjxqqkYhjk(zjTzInvXmzjqk.getZjxqqkYhjk());
           // 
           dbzjTzInvXmzjqk.setZjxqqkXt(zjTzInvXmzjqk.getZjxqqkXt());
           // 
           dbzjTzInvXmzjqk.setZjxqqkJj(zjTzInvXmzjqk.getZjxqqkJj());
           // 
           dbzjTzInvXmzjqk.setZjxqqkXskztr(zjTzInvXmzjqk.getZjxqqkXskztr());
           // 
           dbzjTzInvXmzjqk.setZjxqqkQtzj(zjTzInvXmzjqk.getZjxqqkQtzj());
           // 
           dbzjTzInvXmzjqk.setBqdwzjZyzj(zjTzInvXmzjqk.getBqdwzjZyzj());
           // 
           dbzjTzInvXmzjqk.setBqdwzjQzjj(zjTzInvXmzjqk.getBqdwzjQzjj());
           // 
           dbzjTzInvXmzjqk.setBqdwzjQtgdzbj(zjTzInvXmzjqk.getBqdwzjQtgdzbj());
           // 
           dbzjTzInvXmzjqk.setBqdwzjYhjk(zjTzInvXmzjqk.getBqdwzjYhjk());
           // 
           dbzjTzInvXmzjqk.setBqdwzjXt(zjTzInvXmzjqk.getBqdwzjXt());
           // 
           dbzjTzInvXmzjqk.setBqdwzjJj(zjTzInvXmzjqk.getBqdwzjJj());
           // 
           dbzjTzInvXmzjqk.setBqdwzjXskztr(zjTzInvXmzjqk.getBqdwzjXskztr());
           // 
           dbzjTzInvXmzjqk.setBqdwzjQtzj(zjTzInvXmzjqk.getBqdwzjQtzj());
           // 
           dbzjTzInvXmzjqk.setYjwltrzjZyzj(zjTzInvXmzjqk.getYjwltrzjZyzj());
           // 
           dbzjTzInvXmzjqk.setYjwltrzjQzjj(zjTzInvXmzjqk.getYjwltrzjQzjj());
           // 
           dbzjTzInvXmzjqk.setYjwltrzjQtgdzbj(zjTzInvXmzjqk.getYjwltrzjQtgdzbj());
           // 
           dbzjTzInvXmzjqk.setYjwltrzjYhjk(zjTzInvXmzjqk.getYjwltrzjYhjk());
           // 
           dbzjTzInvXmzjqk.setYjwltrzjXt(zjTzInvXmzjqk.getYjwltrzjXt());
           // 
           dbzjTzInvXmzjqk.setYjwltrzjJj(zjTzInvXmzjqk.getYjwltrzjJj());
           // 
           dbzjTzInvXmzjqk.setYjwltrzjXskztr(zjTzInvXmzjqk.getYjwltrzjXskztr());
           // 
           dbzjTzInvXmzjqk.setYjwltrzjQtzj(zjTzInvXmzjqk.getYjwltrzjQtzj());
           // 
           dbzjTzInvXmzjqk.setJsqzbyzJzbqmTzjc(zjTzInvXmzjqk.getJsqzbyzJzbqmTzjc());
           // 
           dbzjTzInvXmzjqk.setJsqzbyzJzbqmJsqzjhb(zjTzInvXmzjqk.getJsqzbyzJzbqmJsqzjhb());
           // 
           dbzjTzInvXmzjqk.setJsqzbyzJzbqmHgk(zjTzInvXmzjqk.getJsqzbyzJzbqmHgk());
           // 
           dbzjTzInvXmzjqk.setJsqzbyzJzbqmXsk(zjTzInvXmzjqk.getJsqzbyzJzbqmXsk());
           // 
           dbzjTzInvXmzjqk.setJsqzbyzXmyjljTzjc(zjTzInvXmzjqk.getJsqzbyzXmyjljTzjc());
           // 
           dbzjTzInvXmzjqk.setJsqzbyzXmyjljJsqzjhb(zjTzInvXmzjqk.getJsqzbyzXmyjljJsqzjhb());
           // 
           dbzjTzInvXmzjqk.setJsqzbyzXmyjljHgk(zjTzInvXmzjqk.getJsqzbyzXmyjljHgk());
           // 
           dbzjTzInvXmzjqk.setJsqzbyzXmyjljXsk(zjTzInvXmzjqk.getJsqzbyzXmyjljXsk());
           // 
           dbzjTzInvXmzjqk.setSjtrzyzjJzbqm(zjTzInvXmzjqk.getSjtrzyzjJzbqm());
           // 
           dbzjTzInvXmzjqk.setSjtrzyzjXmyjlj(zjTzInvXmzjqk.getSjtrzyzjXmyjlj());
           // 
           dbzjTzInvXmzjqk.setSjtrzyzjzwctzzedbl(zjTzInvXmzjqk.getSjtrzyzjzwctzzedbl());
           // 
           dbzjTzInvXmzjqk.setBqmxjjk(zjTzInvXmzjqk.getBqmxjjk());
           // 
           dbzjTzInvXmzjqk.setBqmzjye(zjTzInvXmzjqk.getBqmzjye());
           // 
           dbzjTzInvXmzjqk.setLjjs(zjTzInvXmzjqk.getLjjs());
           // 
           dbzjTzInvXmzjqk.setLjzf(zjTzInvXmzjqk.getLjzf());
           // 
           dbzjTzInvXmzjqk.setPeriodType(zjTzInvXmzjqk.getPeriodType());
           // 
           dbzjTzInvXmzjqk.setPeriodValue(zjTzInvXmzjqk.getPeriodValue());
           // 
           dbzjTzInvXmzjqk.setOrg(zjTzInvXmzjqk.getOrg());
           // 
           dbzjTzInvXmzjqk.setCurrency(zjTzInvXmzjqk.getCurrency());
           // 
           dbzjTzInvXmzjqk.setSort(zjTzInvXmzjqk.getSort());
           // 
           dbzjTzInvXmzjqk.setCreateBy(zjTzInvXmzjqk.getCreateBy());
           // 
           dbzjTzInvXmzjqk.setCreateOrg(zjTzInvXmzjqk.getCreateOrg());
           // 
           dbzjTzInvXmzjqk.setCreateDate(zjTzInvXmzjqk.getCreateDate());
           // 
           dbzjTzInvXmzjqk.setUpdateBy(zjTzInvXmzjqk.getUpdateBy());
           // 
           dbzjTzInvXmzjqk.setUpdateOrg(zjTzInvXmzjqk.getUpdateOrg());
           // 
           dbzjTzInvXmzjqk.setUpdateDate(zjTzInvXmzjqk.getUpdateDate());
           // 
           dbzjTzInvXmzjqk.setRemarks(zjTzInvXmzjqk.getRemarks());
           // 
           dbzjTzInvXmzjqk.setZjxqqkHj(zjTzInvXmzjqk.getZjxqqkHj());
           // 
           dbzjTzInvXmzjqk.setBqdwzjHj(zjTzInvXmzjqk.getBqdwzjHj());
           // 
           dbzjTzInvXmzjqk.setYjwltrzjHj(zjTzInvXmzjqk.getYjwltrzjHj());
           // 
           dbzjTzInvXmzjqk.setBndwzjZyzj(zjTzInvXmzjqk.getBndwzjZyzj());
           // 
           dbzjTzInvXmzjqk.setBndwzjQzjj(zjTzInvXmzjqk.getBndwzjQzjj());
           // 
           dbzjTzInvXmzjqk.setBndwzjQtgdzbj(zjTzInvXmzjqk.getBndwzjQtgdzbj());
           // 
           dbzjTzInvXmzjqk.setBndwzjYhjk(zjTzInvXmzjqk.getBndwzjYhjk());
           // 
           dbzjTzInvXmzjqk.setBndwzjXt(zjTzInvXmzjqk.getBndwzjXt());
           // 
           dbzjTzInvXmzjqk.setBndwzjJj(zjTzInvXmzjqk.getBndwzjJj());
           // 
           dbzjTzInvXmzjqk.setBndwzjXskztr(zjTzInvXmzjqk.getBndwzjXskztr());
           // 
           dbzjTzInvXmzjqk.setBndwzjQtzj(zjTzInvXmzjqk.getBndwzjQtzj());
           // 
           dbzjTzInvXmzjqk.setLjdwzjZyzj(zjTzInvXmzjqk.getLjdwzjZyzj());
           // 
           dbzjTzInvXmzjqk.setLjdwzjQzjj(zjTzInvXmzjqk.getLjdwzjQzjj());
           // 
           dbzjTzInvXmzjqk.setLjdwzjQtgdzbj(zjTzInvXmzjqk.getLjdwzjQtgdzbj());
           // 
           dbzjTzInvXmzjqk.setLjdwzjYhjk(zjTzInvXmzjqk.getLjdwzjYhjk());
           // 
           dbzjTzInvXmzjqk.setLjdwzjXt(zjTzInvXmzjqk.getLjdwzjXt());
           // 
           dbzjTzInvXmzjqk.setLjdwzjJj(zjTzInvXmzjqk.getLjdwzjJj());
           // 
           dbzjTzInvXmzjqk.setLjdwzjXskztr(zjTzInvXmzjqk.getLjdwzjXskztr());
           // 
           dbzjTzInvXmzjqk.setLjdwzjQtzj(zjTzInvXmzjqk.getLjdwzjQtzj());
           // 
           dbzjTzInvXmzjqk.setBndwzjHj(zjTzInvXmzjqk.getBndwzjHj());
           // 
           dbzjTzInvXmzjqk.setLjdwzjHj(zjTzInvXmzjqk.getLjdwzjHj());
           // 
           dbzjTzInvXmzjqk.setZjxqqkYgjrgjj(zjTzInvXmzjqk.getZjxqqkYgjrgjj());
           // 
           dbzjTzInvXmzjqk.setZjxqqkZfbt(zjTzInvXmzjqk.getZjxqqkZfbt());
           // 
           dbzjTzInvXmzjqk.setBqdwzjYgjrgjj(zjTzInvXmzjqk.getBqdwzjYgjrgjj());
           // 
           dbzjTzInvXmzjqk.setBqdwzjZfbt(zjTzInvXmzjqk.getBqdwzjZfbt());
           // 
           dbzjTzInvXmzjqk.setBndwzjYgjrgjj(zjTzInvXmzjqk.getBndwzjYgjrgjj());
           // 
           dbzjTzInvXmzjqk.setBndwzjZfbt(zjTzInvXmzjqk.getBndwzjZfbt());
           // 
           dbzjTzInvXmzjqk.setLjdwzjYgjrgjj(zjTzInvXmzjqk.getLjdwzjYgjrgjj());
           // 
           dbzjTzInvXmzjqk.setLjdwzjZfbt(zjTzInvXmzjqk.getLjdwzjZfbt());
           // 
           dbzjTzInvXmzjqk.setYjwltrzjYgjrgjj(zjTzInvXmzjqk.getYjwltrzjYgjrgjj());
           // 
           dbzjTzInvXmzjqk.setYjwltrzjZfbt(zjTzInvXmzjqk.getYjwltrzjZfbt());
           // 
           dbzjTzInvXmzjqk.setSjtrzyzjZtzwcebl(zjTzInvXmzjqk.getSjtrzyzjZtzwcebl());
           // 
           dbzjTzInvXmzjqk.setSjtrzyzjZtzzebl(zjTzInvXmzjqk.getSjtrzyzjZtzzebl());
           // 
           dbzjTzInvXmzjqk.setSfrzls(zjTzInvXmzjqk.getSfrzls());
           // 
           dbzjTzInvXmzjqk.setZjxqqkZbjygjrgjj(zjTzInvXmzjqk.getZjxqqkZbjygjrgjj());
           // 
           dbzjTzInvXmzjqk.setBqdwzjZbjygjrgjj(zjTzInvXmzjqk.getBqdwzjZbjygjrgjj());
           // 
           dbzjTzInvXmzjqk.setBndwzjZbjygjrgjj(zjTzInvXmzjqk.getBndwzjZbjygjrgjj());
           // 
           dbzjTzInvXmzjqk.setLjdwzjZbjygjrgjj(zjTzInvXmzjqk.getLjdwzjZbjygjrgjj());
           // 
           dbzjTzInvXmzjqk.setYjwltrzjZbjygjrgjj(zjTzInvXmzjqk.getYjwltrzjZbjygjrgjj());
           // 
           dbzjTzInvXmzjqk.setBz(zjTzInvXmzjqk.getBz());
           // 共通
           dbzjTzInvXmzjqk.setModifyUserInfo(userKey, realName);
           flag = zjTzInvXmzjqkMapper.updateByPrimaryKey(dbzjTzInvXmzjqk);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzInvXmzjqk);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzInvXmzjqk(List<ZjTzInvXmzjqk> zjTzInvXmzjqkList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzInvXmzjqkList != null && zjTzInvXmzjqkList.size() > 0) {
           ZjTzInvXmzjqk zjTzInvXmzjqk = new ZjTzInvXmzjqk();
           zjTzInvXmzjqk.setModifyUserInfo(userKey, realName);
           flag = zjTzInvXmzjqkMapper.batchDeleteUpdateZjTzInvXmzjqk(zjTzInvXmzjqkList, zjTzInvXmzjqk);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzInvXmzjqkList);
        }
    }
    /**
     * 资金基础数据详情
     * 
     */
	@Override
	public ResponseEntity getZjTzInvXmzjqkMonthlyReportList(ZjTzInvXmzjqk zjTzInvXmzjqk) {
		if (zjTzInvXmzjqk == null) {
			zjTzInvXmzjqk = new ZjTzInvXmzjqk();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	//公司身份
        	if(StrUtil.equals("2", ext1)) {
            
        	}else {
//            	zjTzAnnualAssess.setExt1SeeFlag("项目和投资事业部应该看不到未上报的数据");
            }
        	// 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzInvXmzjqk.getProjectId(), true)) {
            	zjTzInvXmzjqk.setProjectId("");
            	zjTzInvXmzjqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
//        	zjTzAnnualAssess.setExt1SeeFlag("项目和投资事业部应该看不到未上报的数据");
            if(StrUtil.equals("all", zjTzInvXmzjqk.getProjectId(), true)) {
            	zjTzInvXmzjqk.setProjectId("");
            }
        }
        List<String> periodList = Lists.newArrayList();
		if (zjTzInvXmzjqk.getPeriod() != null) {
			String period = DateUtil.format(zjTzInvXmzjqk.getPeriod(), "yyyyMM");
			zjTzInvXmzjqk.setPeriodValue(period);
			switch (period.substring(4)) {
        	case "01":
        		periodList.add(period);
        		break;
        	case "02":
        		periodList.add(period.substring(0, 4) + "01");
        		periodList.add(period);
        		break;
			case "03":
				periodList.add(period.substring(0, 4) + "01");
				periodList.add(period.substring(0, 4) + "02");
        		periodList.add(period);
				break;
			case "04":
				periodList.add(period);
				break;
			case "05":
				periodList.add(period.substring(0, 4) + "04");
        		periodList.add(period);
				break;
			case "06":
				periodList.add(period.substring(0, 4) + "04");
				periodList.add(period.substring(0, 4) + "05");
        		periodList.add(period);
				break;
			case "07":
				periodList.add(period);
				break;
			case "08":
				periodList.add(period.substring(0, 4) + "07");
        		periodList.add(period);
				break;
			case "09":
				periodList.add(period.substring(0, 4) + "07");
				periodList.add(period.substring(0, 4) + "08");
        		periodList.add(period);
				break;
			case "10":
				periodList.add(period);
				break;
			case "11":
				periodList.add(period.substring(0, 4) + "10");
        		periodList.add(period);
				break;
			case "12":
				periodList.add(period.substring(0, 4) + "10");
				periodList.add(period.substring(0, 4) + "11");
        		periodList.add(period);
				break;
			default:
				break;
			}	
		//生成月报
		List<ZjTzInvXmzjqk> zjTzInvXmzjqkMonthlyReportList = zjTzInvXmzjqkMapper.selectZjTzInvXmzjqkMonthlyReportListByPeriodValue(zjTzInvXmzjqk, periodList);
		List<ZjTzInvXmzjqk> newzjTzInvXmzjqkMonthlyReportList = Lists.newArrayList();
		for (ZjTzInvXmzjqk dbzjTzInvXmzjqk : zjTzInvXmzjqkMonthlyReportList) {
			ZjTzInvXmzjqk newZjTzInvXmzjqk = new ZjTzInvXmzjqk();
			// 单位名称
			newZjTzInvXmzjqk.setGldw(dbzjTzInvXmzjqk.getComname());
			// 项目名称
			newZjTzInvXmzjqk.setProName(dbzjTzInvXmzjqk.getProName());
			//项目类型
			newZjTzInvXmzjqk.setProInvCategory(dbzjTzInvXmzjqk.getTypeName());
			// 批复类型
			newZjTzInvXmzjqk.setPfname(dbzjTzInvXmzjqk.getPfname());
			// 股权比例
			newZjTzInvXmzjqk.setSzgq(dbzjTzInvXmzjqk.getSzgq());
			// 资本金比例
			newZjTzInvXmzjqk.setZbjbl(dbzjTzInvXmzjqk.getZbjbl());
			// 实际投入自有资金占完成权益投资比例
			newZjTzInvXmzjqk.setSjtrzyzjZwcqytzbl(dbzjTzInvXmzjqk.getSjtrzyzjZwcqytzbl());
			// 实际投入自有资金占投资总额比例
			newZjTzInvXmzjqk.setSjtrzyzjZtzzebl(dbzjTzInvXmzjqk.getSjtrzyzjZtzzebl());
			// 资本金占用考核指标
			newZjTzInvXmzjqk.setZbjzykhzb(dbzjTzInvXmzjqk.getZbjzykhzb());
			//可占用局资本金
			BigDecimal kzyjzbj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getKzyjzbj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setKzyjzbj(kzyjzbj);
			//应回流资金
			BigDecimal yhlzj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getYhlzj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setYhlzj(yhlzj);
			//尚需回流的资金
			BigDecimal sxhlzj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getSxhlzj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setSxhlzj(sxhlzj);
			// 融资是否落实
			newZjTzInvXmzjqk.setSfrzls(dbzjTzInvXmzjqk.getSfrzls());
			// 备注
			newZjTzInvXmzjqk.setBz(dbzjTzInvXmzjqk.getBz());
			// 总投资额
			BigDecimal ztze = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getZtze(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setZtze(ztze);
			// 资金结构情况-资本金-小计
			BigDecimal zbjtotal = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getZbjtotal(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setZbjtotal(zbjtotal);
			// 资金结构情况-资本金-自有资金
			BigDecimal zjxqqkZyzj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getZjxqqkZyzj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setZjxqqkZyzj(zjxqqkZyzj);
			// 资金结构情况-资本金-基金
			BigDecimal zjxqqkQzjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getZjxqqkQzjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setZjxqqkQzjj(zjxqqkQzjj);
			// 资金结构情况-资本金-一公局认购基金
			BigDecimal zjxqqkZbjygjrgjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getZjxqqkZbjygjrgjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setZjxqqkZbjygjrgjj(zjxqqkZbjygjrgjj);
			// 资金结构情况-资本金-其他股东
			BigDecimal zjxqqkQtgdzbj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getZjxqqkQtgdzbj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setZjxqqkQtgdzbj(zjxqqkQtgdzbj);
			// 资金结构情况-融资-小计
			BigDecimal rztotal = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getRztotal(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setRztotal(rztotal);
			// 资金结构情况-融资-银行借款
			BigDecimal zjxqqkYhjk = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getZjxqqkYhjk(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setZjxqqkYhjk(zjxqqkYhjk);
			// 资金结构情况-融资-信托
			BigDecimal zjxqqkXt = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getZjxqqkXt(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setZjxqqkXt(zjxqqkXt);
			// 资金结构情况-融资-基金
			BigDecimal zjxqqkJj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getZjxqqkJj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setZjxqqkJj(zjxqqkJj);
			// 资金结构情况-融资-一公局认购基金
			BigDecimal zjxqqkYgjrgjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getZjxqqkYgjrgjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setZjxqqkYgjrgjj(zjxqqkYgjrgjj);
			// 资金结构情况-政府补贴
			BigDecimal zjxqqkZfbt = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getZjxqqkZfbt(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setZjxqqkZfbt(zjxqqkZfbt);
			// 资金结构情况-其他资金
			BigDecimal zjxqqkQtzj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getZjxqqkQtzj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setZjxqqkQtzj(zjxqqkQtzj);
			// 本期到位资金-资本金-小计
			BigDecimal bqzbjtotal = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBqzbjtotal(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBqzbjtotal(bqzbjtotal);
			// 本期到位资金-资本金-自有资金
			BigDecimal bqdwzjZyzj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBqdwzjZyzj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBqdwzjZyzj(bqdwzjZyzj);
			// 本期到位资金-资本金-基金
			BigDecimal bqdwzjQzjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBqdwzjQzjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBqdwzjQzjj(bqdwzjQzjj);
			// 本期到位资金-资本金-一公局认购基金
			BigDecimal bqdwzjZbjygjrgjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBqdwzjZbjygjrgjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBqdwzjZbjygjrgjj(bqdwzjZbjygjrgjj);
			// 本期到位资金-资本金-其他股东等
			BigDecimal bqdwzjQtgdzbj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBqdwzjQtgdzbj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBqdwzjQtgdzbj(bqdwzjQtgdzbj);
			// 本期到位资金-融资-小计
			BigDecimal bqrztotal = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBqrztotal(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBqrztotal(bqrztotal);
			// 本期到位资金-融资-银行借款
			BigDecimal bqdwzjYhjk = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBqdwzjYhjk(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBqdwzjYhjk(bqdwzjYhjk);
			// 本期到位资金-融资-基金
			BigDecimal bqdwzjJj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBqdwzjJj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBqdwzjJj(bqdwzjJj);
			// 本期到位资金-融资-一公局认购基金
			BigDecimal bqdwzjYgjrgjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBqdwzjYgjrgjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBqdwzjYgjrgjj(bqdwzjYgjrgjj);
			// 本期到位资金-融资-信托
			BigDecimal bqdwzjXt = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBqdwzjXt(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBqdwzjXt(bqdwzjXt);
			// 本期到位资金-政府补贴
			BigDecimal bqdwzjZfbt = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBqdwzjZfbt(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBqdwzjZfbt(bqdwzjZfbt);
			// 本期到位资金-其他资金
			BigDecimal bqdwzjQtzj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBqdwzjQtzj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBqdwzjQtzj(bqdwzjQtzj);
			// 本季到位资金-资本金-小计
			BigDecimal bjzbjtotal = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBjzbjtotal(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBjzbjtotal(bjzbjtotal);
			// 本季到位资金-资本金-自有资金
			BigDecimal bjdwzjZyzj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBjdwzjZyzj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBjdwzjZyzj(bjdwzjZyzj);
			// 本季到位资金-资本金-基金
			BigDecimal bjdwzjQzjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBjdwzjQzjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBjdwzjQzjj(bjdwzjQzjj);
			// 本季到位资金-资本金-一公局认购基金
			BigDecimal bjdwzjZbjygjrgjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBjdwzjZbjygjrgjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBjdwzjZbjygjrgjj(bjdwzjZbjygjrgjj);
			// 本季到位资金-资本金-其他股东等
			BigDecimal bjdwzjQtgdzbj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBjdwzjQtgdzbj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBjdwzjQtgdzbj(bjdwzjQtgdzbj);
			// 本季到位资金-融资-小计
			BigDecimal bjrztotal = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBjrztotal(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBjrztotal(bjrztotal);
			// 本季到位资金-融资-银行借款
			BigDecimal bjdwzjYhjk = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBjdwzjYhjk(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBjdwzjYhjk(bjdwzjYhjk);
			// 本季到位资金-融资-基金
			BigDecimal bjdwzjJj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBjdwzjJj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBjdwzjJj(bjdwzjJj);
			// 本季到位资金-融资-一公局认购基金
			BigDecimal bjdwzjYgjrgjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBjdwzjYgjrgjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBjdwzjYgjrgjj(bjdwzjYgjrgjj);
			// 本季到位资金-融资-信托
			BigDecimal bjdwzjXt = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBjdwzjXt(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBjdwzjXt(bjdwzjXt);
			// 本季到位资金-政府补贴
			BigDecimal bjdwzjZfbt = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBjdwzjZfbt(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBjdwzjZfbt(bjdwzjZfbt);
			// 本季到位资金-其他资金
			BigDecimal bjdwzjQtzj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBjdwzjQtzj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBjdwzjQtzj(bjdwzjQtzj);
			// 本年到位资金-资本金-小计
			BigDecimal bnzbjtotal = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBnzbjtotal(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBnzbjtotal(bnzbjtotal);
			// 本年到位资金-资本金-自有资金
			BigDecimal bndwzjZyzj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBndwzjZyzj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBndwzjZyzj(bndwzjZyzj);
			// 本年到位资金-资本金-基金
			BigDecimal bndwzjQzjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBndwzjQzjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBndwzjQzjj(bndwzjQzjj);
			// 本年到位资金-资本金-一公局认购基金
			BigDecimal bndwzjZbjygjrgjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBndwzjZbjygjrgjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBndwzjZbjygjrgjj(bndwzjZbjygjrgjj);
			// 本年到位资金-资本金-其他股东等
			BigDecimal bndwzjQtgdzbj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBndwzjQtgdzbj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBndwzjQtgdzbj(bndwzjQtgdzbj);
			// 本年到位资金-融资-小计
			BigDecimal bnrztotal = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBnrztotal(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBnrztotal(bnrztotal);
			// 本年到位资金-融资-银行借款
			BigDecimal bndwzjYhjk = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBndwzjYhjk(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBndwzjYhjk(bndwzjYhjk);
			// 本年到位资金-融资-基金
			BigDecimal bndwzjJj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBndwzjJj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBndwzjJj(bndwzjJj);
			// 本年到位资金-融资-一公局认购基金
			BigDecimal bndwzjYgjrgjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBndwzjYgjrgjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBndwzjYgjrgjj(bndwzjYgjrgjj);
			// 本年到位资金-融资-信托
			BigDecimal bndwzjXt = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBndwzjXt(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBndwzjXt(bndwzjXt);
			// 本年到位资金-政府补贴
			BigDecimal bndwzjZfbt = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBndwzjZfbt(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBndwzjZfbt(bndwzjZfbt);
			// 本年到位资金-其他资金
			BigDecimal bndwzjQtzj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBndwzjQtzj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBndwzjQtzj(bndwzjQtzj);
			// 累计到位资金-资本金-小计
			BigDecimal ljzbjtotal = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getLjzbjtotal(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setLjzbjtotal(ljzbjtotal);
			// 累计到位资金-资本金-自有资金
			BigDecimal ljdwzjZyzj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getLjdwzjZyzj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setLjdwzjZyzj(ljdwzjZyzj);
			// 累计到位资金-资本金-基金
			BigDecimal ljdwzjQzjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getLjdwzjQzjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setLjdwzjQzjj(ljdwzjQzjj);
			// 累计到位资金-资本金-一公局认购基金
			BigDecimal ljdwzjZbjygjrgjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getLjdwzjZbjygjrgjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setLjdwzjZbjygjrgjj(ljdwzjZbjygjrgjj);
			// 累计到位资金-资本金-其他股东等
			BigDecimal ljdwzjQtgdzbj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getLjdwzjQtgdzbj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setLjdwzjQtgdzbj(ljdwzjQtgdzbj);
			// 累计到位资金-融资-小计
			BigDecimal ljrztotal = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getLjrztotal(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setLjrztotal(ljrztotal);
			// 累计到位资金-融资-银行借款
			BigDecimal ljdwzjYhjk = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getLjdwzjYhjk(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setLjdwzjYhjk(ljdwzjYhjk);
			// 累计到位资金-融资-基金
			BigDecimal ljdwzjJj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getLjdwzjJj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setLjdwzjJj(ljdwzjJj);
			// 累计到位资金-融资-一公局认购基金
			BigDecimal ljdwzjYgjrgjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getLjdwzjYgjrgjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setLjdwzjYgjrgjj(ljdwzjYgjrgjj);
			// 累计到位资金-融资-信托
			BigDecimal ljdwzjXt = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getLjdwzjXt(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setLjdwzjXt(ljdwzjXt);
			// 累计到位资金-政府补贴
			BigDecimal ljdwzjZfbt = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getLjdwzjZfbt(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setLjdwzjZfbt(ljdwzjZfbt);
			// 累计到位资金-其他资金
			BigDecimal ljdwzjQtzj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getLjdwzjQtzj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setLjdwzjQtzj(ljdwzjQtzj);
			// 建设期的资本运作（回流资金）本期
			BigDecimal jsqzbyzJzbqmJsqzjhb = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getJsqzbyzJzbqmJsqzjhb(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setJsqzbyzJzbqmJsqzjhb(jsqzbyzJzbqmJsqzjhb);
			// 建设期的资本运作（回流资金）本季
			BigDecimal jsqzbyzJzbjmJsqzjhb = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getJsqzbyzJzbjmJsqzjhb(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setJsqzbyzJzbjmJsqzjhb(jsqzbyzJzbjmJsqzjhb);
			// 建设期的资本运作（回流资金）本年
			BigDecimal jsqzbyzJzbnmJsqzjhb = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getJsqzbyzJzbnmJsqzjhb(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setJsqzbyzJzbnmJsqzjhb(jsqzbyzJzbnmJsqzjhb);
			// 建设期的资本运作（回流资金）开累
			BigDecimal jsqzbyzXmklJsqzjhb = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getJsqzbyzXmklJsqzjhb(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setJsqzbyzXmklJsqzjhb(jsqzbyzXmklJsqzjhb);
			// 实际投入自有资金本期
			BigDecimal sjtrzyzjJzbqm = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getSjtrzyzjJzbqm(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setSjtrzyzjJzbqm(sjtrzyzjJzbqm);
			// 实际投入自有资金本季
			BigDecimal sjtrzyzjJzbjm = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getSjtrzyzjJzbjm(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setSjtrzyzjJzbjm(sjtrzyzjJzbjm);
			// 实际投入自有资金本年
			BigDecimal sjtrzyzjJzbnm = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getSjtrzyzjJzbnm(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setSjtrzyzjJzbnm(sjtrzyzjJzbnm);
			// 实际投入自有资金开累
			BigDecimal sjtrzyzjXmkl = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getSjtrzyzjXmkl(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setSjtrzyzjXmkl(sjtrzyzjXmkl);
			newzjTzInvXmzjqkMonthlyReportList.add(newZjTzInvXmzjqk);
		}
			return repEntity.ok(newzjTzInvXmzjqkMonthlyReportList);
		}else {
			//资金基础数据详情
			String periodValue = zjTzInvXmzjqk.getPeriodValue();
			switch (periodValue.substring(4)) {
        	case "01":
        		periodList.add(periodValue);
        		break;
        	case "02":
        		periodList.add(periodValue.substring(0, 4) + "01");
        		periodList.add(periodValue);
        		break;
			case "03":
				periodList.add(periodValue.substring(0, 4) + "01");
				periodList.add(periodValue.substring(0, 4) + "02");
        		periodList.add(periodValue);
				break;
			case "04":
				periodList.add(periodValue);
				break;
			case "05":
				periodList.add(periodValue.substring(0, 4) + "04");
        		periodList.add(periodValue);
				break;
			case "06":
				periodList.add(periodValue.substring(0, 4) + "04");
				periodList.add(periodValue.substring(0, 4) + "05");
        		periodList.add(periodValue);
				break;
			case "07":
				periodList.add(periodValue);
				break;
			case "08":
				periodList.add(periodValue.substring(0, 4) + "07");
        		periodList.add(periodValue);
				break;
			case "09":
				periodList.add(periodValue.substring(0, 4) + "07");
				periodList.add(periodValue.substring(0, 4) + "08");
        		periodList.add(periodValue);
				break;
			case "10":
				periodList.add(periodValue);
				break;
			case "11":
				periodList.add(periodValue.substring(0, 4) + "10");
        		periodList.add(periodValue);
				break;
			case "12":
				periodList.add(periodValue.substring(0, 4) + "10");
				periodList.add(periodValue.substring(0, 4) + "11");
        		periodList.add(periodValue);
				break;
			default:
				break;
			}
			List<ZjTzInvXmzjqk> zjTzInvXmzjqkMonthlyReportList = zjTzInvXmzjqkMapper.selectZjTzInvXmzjqkMonthlyReportListByPeriodValue(zjTzInvXmzjqk, periodList);
			JSONObject returnJSONObject = new JSONObject();
			for (ZjTzInvXmzjqk dbzjTzInvXmzjqk : zjTzInvXmzjqkMonthlyReportList) {
			JSONArray jsonArray = new JSONArray();
			
    		
    		// 资金结构情况
    		JSONObject jsonObject_zjjgqk = new JSONObject();
    		// 资金结构情况-资本金-小计
    		BigDecimal zbjtotal = dbzjTzInvXmzjqk.getZbjtotal();
    		// 资金结构情况-资本金-自有资金
    		BigDecimal zjxqqkZyzj = dbzjTzInvXmzjqk.getZjxqqkZyzj();
    		// 资金结构情况-资本金-基金
    		BigDecimal zjxqqkQzjj = dbzjTzInvXmzjqk.getZjxqqkQzjj();
    		// 资金结构情况-资本金-一公局认购基金
    		BigDecimal zjxqqkZbjygjrgjj = dbzjTzInvXmzjqk.getZjxqqkZbjygjrgjj();
    		// 资金结构情况-资本金-其他股东
    		BigDecimal zjxqqkQtgdzbj = dbzjTzInvXmzjqk.getZjxqqkQtgdzbj();
    		// 资金结构情况-融资-小计
    		BigDecimal rztotal = dbzjTzInvXmzjqk.getRztotal();
    		// 资金结构情况-融资-银行借款
    		BigDecimal zjxqqkYhjk = dbzjTzInvXmzjqk.getZjxqqkYhjk();
    		// 资金结构情况-融资-基金
    		BigDecimal zjxqqkJj = dbzjTzInvXmzjqk.getZjxqqkJj();
    		// 资金结构情况-融资-一公局认购基金
    		BigDecimal zjxqqkYgjrgjj = dbzjTzInvXmzjqk.getZjxqqkYgjrgjj();
    		// 资金结构情况-融资-信托
    		BigDecimal zjxqqkXt = dbzjTzInvXmzjqk.getZjxqqkXt();
    		// 资金结构情况-政府补贴
    		BigDecimal zjxqqkZfbt = dbzjTzInvXmzjqk.getZjxqqkZfbt();
    		// 资金结构情况-其他资金
    		BigDecimal zjxqqkQtzj = dbzjTzInvXmzjqk.getZjxqqkQtzj();
    		jsonObject_zjjgqk.set("id", UuidUtil.generate());
    		jsonObject_zjjgqk.set("parentID", "0");
    		jsonObject_zjjgqk.set("workName", "资金结构情况");
    		jsonObject_zjjgqk.set("zbjtotal", zbjtotal == null ? new BigDecimal(0) : zbjtotal);
    		jsonObject_zjjgqk.set("zbjzyzj", zjxqqkZyzj == null ? new BigDecimal(0) : zjxqqkZyzj);
    		jsonObject_zjjgqk.set("zbjjj", zjxqqkQzjj == null ? new BigDecimal(0) : zjxqqkQzjj);
    		jsonObject_zjjgqk.set("zbjygjrgjj", zjxqqkZbjygjrgjj == null ? new BigDecimal(0) : zjxqqkZbjygjrgjj);
    		jsonObject_zjjgqk.set("zbjqtgd", zjxqqkQtgdzbj == null ? new BigDecimal(0) : zjxqqkQtgdzbj);
    		jsonObject_zjjgqk.set("rztotal", rztotal == null ? new BigDecimal(0) : rztotal);
    		jsonObject_zjjgqk.set("rzyhjk", zjxqqkYhjk == null ? new BigDecimal(0) : zjxqqkYhjk);
    		jsonObject_zjjgqk.set("rzjj", zjxqqkJj == null ? new BigDecimal(0) : zjxqqkJj);
    		jsonObject_zjjgqk.set("rzygjrgjj", zjxqqkYgjrgjj == null ? new BigDecimal(0) : zjxqqkYgjrgjj);
    		jsonObject_zjjgqk.set("rzxt", zjxqqkXt == null ? new BigDecimal(0) : zjxqqkXt);
    		jsonObject_zjjgqk.set("zfbt", zjxqqkZfbt == null ? new BigDecimal(0) : zjxqqkZfbt);
    		jsonObject_zjjgqk.set("qtzj", zjxqqkQtzj == null ? new BigDecimal(0) : zjxqqkQtzj);
    		jsonArray.set(jsonObject_zjjgqk);
    		
    		// 本期到位资金
    		JSONObject jsonObject_bqdwzj = new JSONObject();
    		// 本期到位资金-资本金-小计
    		BigDecimal bqzbjtotal = dbzjTzInvXmzjqk.getBqzbjtotal();
    		// 本期到位资金-资本金-自有资金
    		BigDecimal bqdwzjZyzj = dbzjTzInvXmzjqk.getBqdwzjZyzj();
    		// 本期到位资金-资本金-基金
    		BigDecimal bqdwzjQzjj = dbzjTzInvXmzjqk.getBqdwzjQzjj();
    		// 本期到位资金-资本金-一公局认购基金
    		BigDecimal bqdwzjZbjygjrgjj = dbzjTzInvXmzjqk.getBqdwzjZbjygjrgjj();
    		// 本期到位资金-资本金-其他股东等
    		BigDecimal bqdwzjQtgdzbj = dbzjTzInvXmzjqk.getBqdwzjQtgdzbj();
    		// 本期到位资金-融资-小计
    		BigDecimal bqrztotal = dbzjTzInvXmzjqk.getBqrztotal();
    		// 本期到位资金-融资-银行借款
    		BigDecimal bqdwzjYhjk = dbzjTzInvXmzjqk.getBqdwzjYhjk();
    		// 本期到位资金-融资-基金
    		BigDecimal bqdwzjJj = dbzjTzInvXmzjqk.getBqdwzjJj();
    		// 本期到位资金-融资-一公局认购基金
    		BigDecimal bqdwzjYgjrgjj = dbzjTzInvXmzjqk.getBqdwzjYgjrgjj();
    		// 本期到位资金-融资-信托
    		BigDecimal bqdwzjXt = dbzjTzInvXmzjqk.getBqdwzjXt();
    		// 本期到位资金-政府补贴
    		BigDecimal bqdwzjZfbt = dbzjTzInvXmzjqk.getBqdwzjZfbt();
    		// 本期到位资金-其他资金
    		BigDecimal bqdwzjQtzj = dbzjTzInvXmzjqk.getBqdwzjQtzj();
    		jsonObject_bqdwzj.set("id", UuidUtil.generate());
    		jsonObject_bqdwzj.set("parentID", "0");
    		jsonObject_bqdwzj.set("workName", "本期到位资金");
    		jsonObject_bqdwzj.set("zbjtotal", bqzbjtotal == null ? new BigDecimal(0) : bqzbjtotal);
    		jsonObject_bqdwzj.set("zbjzyzj", bqdwzjZyzj == null ? new BigDecimal(0) : bqdwzjZyzj);
    		jsonObject_bqdwzj.set("zbjjj", bqdwzjQzjj == null ? new BigDecimal(0) : bqdwzjQzjj);
    		jsonObject_bqdwzj.set("zbjygjrgjj", bqdwzjZbjygjrgjj == null ? new BigDecimal(0) : bqdwzjZbjygjrgjj);
    		jsonObject_bqdwzj.set("zbjqtgd", bqdwzjQtgdzbj == null ? new BigDecimal(0) : bqdwzjQtgdzbj);
    		jsonObject_bqdwzj.set("rztotal", bqrztotal == null ? new BigDecimal(0) : bqrztotal);
    		jsonObject_bqdwzj.set("rzyhjk", bqdwzjYhjk == null ? new BigDecimal(0) : bqdwzjYhjk);
    		jsonObject_bqdwzj.set("rzjj", bqdwzjJj == null ? new BigDecimal(0) : bqdwzjJj);
    		jsonObject_bqdwzj.set("rzygjrgjj", bqdwzjYgjrgjj == null ? new BigDecimal(0) : bqdwzjYgjrgjj);
    		jsonObject_bqdwzj.set("rzxt", bqdwzjXt == null ? new BigDecimal(0) : bqdwzjXt);
    		jsonObject_bqdwzj.set("zfbt", bqdwzjZfbt == null ? new BigDecimal(0) : bqdwzjZfbt);
    		jsonObject_bqdwzj.set("qtzj", bqdwzjQtzj == null ? new BigDecimal(0) : bqdwzjQtzj);
    		jsonArray.set(jsonObject_bqdwzj);
    		
    		
    		//本季到位资金
    		JSONObject jsonObject_bjdwzj = new JSONObject();
    		// 本季到位资金-资本金-小计
    		BigDecimal bjzbjtotal = dbzjTzInvXmzjqk.getBjzbjtotal();
    		// 本季到位资金-资本金-自有资金
    		BigDecimal bjdwzjZyzj = dbzjTzInvXmzjqk.getBjdwzjZyzj();
    		// 本季到位资金-资本金-基金
    		BigDecimal bjdwzjQzjj = dbzjTzInvXmzjqk.getBjdwzjQzjj();
    		// 本季到位资金-资本金-一公局认购基金
    		BigDecimal bjdwzjZbjygjrgjj = dbzjTzInvXmzjqk.getBjdwzjZbjygjrgjj();
    		// 本季到位资金-资本金-其他股东等
    		BigDecimal bjdwzjQtgdzbj = dbzjTzInvXmzjqk.getBjdwzjQtgdzbj();
    		// 本季到位资金-融资-小计
    		BigDecimal bjrztotal = dbzjTzInvXmzjqk.getBjrztotal();
    		// 本季到位资金-融资-银行借款
    		BigDecimal bjdwzjYhjk = dbzjTzInvXmzjqk.getBjdwzjYhjk();
    		// 本季到位资金-融资-基金
    		BigDecimal bjdwzjJj = dbzjTzInvXmzjqk.getBjdwzjJj();
    		// 本季到位资金-融资-一公局认购基金
    		BigDecimal bjdwzjYgjrgjj = dbzjTzInvXmzjqk.getBjdwzjYgjrgjj();
    		// 本季到位资金-融资-信托
    		BigDecimal bjdwzjXt = dbzjTzInvXmzjqk.getBjdwzjXt();
    		// 本季到位资金-政府补贴
    		BigDecimal bjdwzjZfbt = dbzjTzInvXmzjqk.getBjdwzjZfbt();
    		// 本季到位资金-其他资金
    		BigDecimal bjdwzjQtzj = dbzjTzInvXmzjqk.getBjdwzjQtzj();
    		jsonObject_bjdwzj.set("id", UuidUtil.generate());
    		jsonObject_bjdwzj.set("parentID", "0");
    		jsonObject_bjdwzj.set("workName", "本季到位资金");
    		jsonObject_bjdwzj.set("zbjtotal", bjzbjtotal == null ? new BigDecimal(0) : bjzbjtotal);
    		jsonObject_bjdwzj.set("zbjzyzj", bjdwzjZyzj == null ? new BigDecimal(0) : bjdwzjZyzj);
    		jsonObject_bjdwzj.set("zbjjj", bjdwzjQzjj == null ? new BigDecimal(0) : bjdwzjQzjj);
    		jsonObject_bjdwzj.set("zbjygjrgjj", bjdwzjZbjygjrgjj == null ? new BigDecimal(0) : bjdwzjZbjygjrgjj);
    		jsonObject_bjdwzj.set("zbjqtgd", bjdwzjQtgdzbj == null ? new BigDecimal(0) : bjdwzjQtgdzbj);
    		jsonObject_bjdwzj.set("rztotal", bjrztotal == null ? new BigDecimal(0) : bjrztotal);
    		jsonObject_bjdwzj.set("rzyhjk", bjdwzjYhjk == null ? new BigDecimal(0) : bjdwzjYhjk);
    		jsonObject_bjdwzj.set("rzjj", bjdwzjJj == null ? new BigDecimal(0) : bjdwzjJj);
    		jsonObject_bjdwzj.set("rzygjrgjj", bjdwzjYgjrgjj == null ? new BigDecimal(0) : bjdwzjYgjrgjj);
    		jsonObject_bjdwzj.set("rzxt", bjdwzjXt == null ? new BigDecimal(0) : bjdwzjXt);
    		jsonObject_bjdwzj.set("zfbt", bjdwzjZfbt == null ? new BigDecimal(0) : bjdwzjZfbt);
    		jsonObject_bjdwzj.set("qtzj", bjdwzjQtzj == null ? new BigDecimal(0) : bjdwzjQtzj);
    		jsonArray.set(jsonObject_bjdwzj);
    		
    		// 本年到位资金
    		JSONObject jsonObject_bndwzj = new JSONObject();
    		// 本年到位资金-资本金-小计
    		BigDecimal bnzbjtotal = dbzjTzInvXmzjqk.getBnzbjtotal();
    		// 本年到位资金-资本金-自有资金
    		BigDecimal bndwzjZyzj = dbzjTzInvXmzjqk.getBndwzjZyzj();
    		// 本年到位资金-资本金-基金
    		BigDecimal bndwzjQzjj = dbzjTzInvXmzjqk.getBndwzjQzjj();
    		// 本年到位资金-资本金-一公局认购基金
    		BigDecimal bndwzjZbjygjrgjj = dbzjTzInvXmzjqk.getBndwzjZbjygjrgjj();
    		// 本年到位资金-资本金-其他股东等
    		BigDecimal bndwzjQtgdzbj = dbzjTzInvXmzjqk.getBndwzjQtgdzbj();
    		// 本年到位资金-融资-小计
    		BigDecimal bnrztotal = dbzjTzInvXmzjqk.getBnrztotal();
    		// 本年到位资金-融资-银行借款
    		BigDecimal bndwzjYhjk = dbzjTzInvXmzjqk.getBndwzjYhjk();
    		// 本年到位资金-融资-基金
    		BigDecimal bndwzjJj = dbzjTzInvXmzjqk.getBndwzjJj();
    		// 本年到位资金-融资-一公局认购基金
    		BigDecimal bndwzjYgjrgjj = dbzjTzInvXmzjqk.getBndwzjYgjrgjj();
    		// 本年到位资金-融资-信托
    		BigDecimal bndwzjXt = dbzjTzInvXmzjqk.getBndwzjXt();
    		// 本年到位资金-政府补贴
    		BigDecimal bndwzjZfbt = dbzjTzInvXmzjqk.getBndwzjZfbt();
    		// 本年到位资金-其他资金
    		BigDecimal bndwzjQtzj = dbzjTzInvXmzjqk.getBndwzjQtzj();
    		jsonObject_bndwzj.set("id", UuidUtil.generate());
    		jsonObject_bndwzj.set("parentID", "0");
    		jsonObject_bndwzj.set("workName", "本年到位资金");
    		jsonObject_bndwzj.set("zbjtotal", bnzbjtotal == null ? new BigDecimal(0) : bnzbjtotal);
    		jsonObject_bndwzj.set("zbjzyzj", bndwzjZyzj == null ? new BigDecimal(0) : bndwzjZyzj);
    		jsonObject_bndwzj.set("zbjjj", bndwzjQzjj == null ? new BigDecimal(0) : bndwzjQzjj);
    		jsonObject_bndwzj.set("zbjygjrgjj", bndwzjZbjygjrgjj == null ? new BigDecimal(0) : bndwzjZbjygjrgjj);
    		jsonObject_bndwzj.set("zbjqtgd", bndwzjQtgdzbj == null ? new BigDecimal(0) : bndwzjQtgdzbj);
    		jsonObject_bndwzj.set("rztotal", bnrztotal == null ? new BigDecimal(0) : bnrztotal);
    		jsonObject_bndwzj.set("rzyhjk", bndwzjYhjk == null ? new BigDecimal(0) : bndwzjYhjk);
    		jsonObject_bndwzj.set("rzjj", bndwzjJj == null ? new BigDecimal(0) : bndwzjJj);
    		jsonObject_bndwzj.set("rzygjrgjj", bndwzjYgjrgjj == null ? new BigDecimal(0) : bndwzjYgjrgjj);
    		jsonObject_bndwzj.set("rzxt", bndwzjXt == null ? new BigDecimal(0) : bndwzjXt);
    		jsonObject_bndwzj.set("zfbt", bndwzjZfbt == null ? new BigDecimal(0) : bndwzjZfbt);
    		jsonObject_bndwzj.set("qtzj", bndwzjQtzj == null ? new BigDecimal(0) : bndwzjQtzj);
    		jsonArray.set(jsonObject_bndwzj);
    		
    		
    		// 累计到位资金
    		JSONObject jsonObject_ljdwzj = new JSONObject();
    		// 累计到位资金-资本金-小计
    		BigDecimal ljzbjtotal = dbzjTzInvXmzjqk.getLjzbjtotal();
    		// 累计到位资金-资本金-自有资金
    		BigDecimal ljdwzjZyzj = dbzjTzInvXmzjqk.getLjdwzjZyzj();
    		// 累计到位资金-资本金-基金
    		BigDecimal ljdwzjQzjj = dbzjTzInvXmzjqk.getLjdwzjQzjj();
    		// 累计到位资金-资本金-一公局认购基金
    		BigDecimal ljdwzjZbjygjrgjj = dbzjTzInvXmzjqk.getLjdwzjZbjygjrgjj();
    		// 累计到位资金-资本金-其他股东等
    		BigDecimal ljdwzjQtgdzbj = dbzjTzInvXmzjqk.getLjdwzjQtgdzbj();
    		// 累计到位资金-融资-小计
    		BigDecimal ljrztotal = dbzjTzInvXmzjqk.getLjrztotal();
    		// 累计到位资金-融资-银行借款
    		BigDecimal ljdwzjYhjk = dbzjTzInvXmzjqk.getLjdwzjYhjk();
    		// 累计到位资金-融资-基金
    		BigDecimal ljdwzjJj = dbzjTzInvXmzjqk.getLjdwzjJj();
    		// 累计到位资金-融资-一公局认购基金
    		BigDecimal ljdwzjYgjrgjj = dbzjTzInvXmzjqk.getLjdwzjYgjrgjj();
    		// 累计到位资金-融资-信托
    		BigDecimal ljdwzjXt = dbzjTzInvXmzjqk.getLjdwzjXt();
    		// 累计到位资金-政府补贴
    		BigDecimal ljdwzjZfbt = dbzjTzInvXmzjqk.getLjdwzjZfbt();
    		// 累计到位资金-其他资金
    		BigDecimal ljdwzjQtzj = dbzjTzInvXmzjqk.getLjdwzjQtzj();
    		jsonObject_ljdwzj.set("id", UuidUtil.generate());
    		jsonObject_ljdwzj.set("parentID", "0");
    		jsonObject_ljdwzj.set("workName", "累计到位资金");
    		jsonObject_ljdwzj.set("zbjtotal", ljzbjtotal == null ? new BigDecimal(0) : ljzbjtotal);
    		jsonObject_ljdwzj.set("zbjzyzj", ljdwzjZyzj == null ? new BigDecimal(0) : ljdwzjZyzj);
    		jsonObject_ljdwzj.set("zbjjj", ljdwzjQzjj == null ? new BigDecimal(0) : ljdwzjQzjj);
    		jsonObject_ljdwzj.set("zbjygjrgjj", ljdwzjZbjygjrgjj == null ? new BigDecimal(0) : ljdwzjZbjygjrgjj);
    		jsonObject_ljdwzj.set("zbjqtgd", ljdwzjQtgdzbj == null ? new BigDecimal(0) : ljdwzjQtgdzbj);
    		jsonObject_ljdwzj.set("rztotal", ljrztotal == null ? new BigDecimal(0) : ljrztotal);
    		jsonObject_ljdwzj.set("rzyhjk", ljdwzjYhjk == null ? new BigDecimal(0) : ljdwzjYhjk);
    		jsonObject_ljdwzj.set("rzjj", ljdwzjJj == null ? new BigDecimal(0) : ljdwzjJj);
    		jsonObject_ljdwzj.set("rzygjrgjj", ljdwzjYgjrgjj == null ? new BigDecimal(0) : ljdwzjYgjrgjj);
    		jsonObject_ljdwzj.set("rzxt", ljdwzjXt == null ? new BigDecimal(0) : ljdwzjXt);
    		jsonObject_ljdwzj.set("zfbt", ljdwzjZfbt == null ? new BigDecimal(0) : ljdwzjZfbt);
    		jsonObject_ljdwzj.set("qtzj", ljdwzjQtzj == null ? new BigDecimal(0) : ljdwzjQtzj);
    		jsonArray.set(jsonObject_ljdwzj);
    		
    		JSONArray jsonArraySecond = new JSONArray();
    		// 建设期的资本运作（回流资金）
    		JSONObject jsonObject_jsqzbyz = new JSONObject();
    		// 建设期的资本运作（回流资金）本期
    		BigDecimal jsqzbyzJzbqmJsqzjhb = dbzjTzInvXmzjqk.getJsqzbyzJzbqmJsqzjhb();
    		// 建设期的资本运作（回流资金）本季
    		BigDecimal jsqzbyzJzbjmJsqzjhb = dbzjTzInvXmzjqk.getJsqzbyzJzbjmJsqzjhb();
    		// 建设期的资本运作（回流资金）本年
    		BigDecimal jsqzbyzJzbnmJsqzjhb = dbzjTzInvXmzjqk.getJsqzbyzJzbnmJsqzjhb();
    		// 建设期的资本运作（回流资金）开累
    		BigDecimal jsqzbyzXmklJsqzjhb = dbzjTzInvXmzjqk.getJsqzbyzXmklJsqzjhb();
    		// 建设期的资本运作（回流资金）
    		jsonObject_jsqzbyz.set("id", UuidUtil.generate());
    		jsonObject_jsqzbyz.set("parentID", "0");
    		jsonObject_jsqzbyz.set("workName", "建设期的资本运作（回流资金）");
    		jsonObject_jsqzbyz.set("bq", jsqzbyzJzbqmJsqzjhb == null ? new BigDecimal(0) : jsqzbyzJzbqmJsqzjhb);
    		jsonObject_jsqzbyz.set("bj", jsqzbyzJzbjmJsqzjhb == null ? new BigDecimal(0) : jsqzbyzJzbjmJsqzjhb);
    		jsonObject_jsqzbyz.set("bn", jsqzbyzJzbnmJsqzjhb == null ? new BigDecimal(0) : jsqzbyzJzbnmJsqzjhb);
    		jsonObject_jsqzbyz.set("kl", jsqzbyzXmklJsqzjhb == null ? new BigDecimal(0) : jsqzbyzXmklJsqzjhb);
    		jsonArraySecond.set(jsonObject_jsqzbyz);
    		
    		// 实际投入自有资金
    		JSONObject jsonObject_sjtrzyzj = new JSONObject();
    		// 实际投入自有资金本期
    		BigDecimal sjtrzyzjJzbqm = dbzjTzInvXmzjqk.getSjtrzyzjJzbqm();
    		// 实际投入自有资金本季
    		BigDecimal sjtrzyzjJzbjm = dbzjTzInvXmzjqk.getSjtrzyzjJzbjm();
    		// 实际投入自有资金本年
    		BigDecimal sjtrzyzjJzbnm = dbzjTzInvXmzjqk.getSjtrzyzjJzbnm();
    		// 实际投入自有资金开累
    		BigDecimal sjtrzyzjXmkl = dbzjTzInvXmzjqk.getSjtrzyzjXmkl();
    		// 实际投入自有资金
    		jsonObject_sjtrzyzj.set("id", UuidUtil.generate());
    		jsonObject_sjtrzyzj.set("parentID", "0");
    		jsonObject_sjtrzyzj.set("workName", "实际投入自有资金");
    		jsonObject_sjtrzyzj.set("bq", sjtrzyzjJzbqm == null ? new BigDecimal(0) : sjtrzyzjJzbqm);
    		jsonObject_sjtrzyzj.set("bj", sjtrzyzjJzbjm == null ? new BigDecimal(0) : sjtrzyzjJzbjm);
    		jsonObject_sjtrzyzj.set("bn", sjtrzyzjJzbnm == null ? new BigDecimal(0) : sjtrzyzjJzbnm);
    		jsonObject_sjtrzyzj.set("kl", sjtrzyzjXmkl == null ? new BigDecimal(0) : sjtrzyzjXmkl);
    		jsonArraySecond.set(jsonObject_sjtrzyzj);
    		
    		returnJSONObject.set("children",jsonArray);
    		returnJSONObject.set("children2",jsonArraySecond);
			
			// 基础数据上半部分
			// 项目编号
    		returnJSONObject.set("proNum", StrUtil.isEmpty(dbzjTzInvXmzjqk.getProNum()) ? "" : dbzjTzInvXmzjqk.getProNum());
    		// 项目名称
    		returnJSONObject.set("proName", StrUtil.isEmpty(dbzjTzInvXmzjqk.getProName()) ? "" : dbzjTzInvXmzjqk.getProName());
    		// 管理单位
    		returnJSONObject.set("comname", StrUtil.isEmpty(dbzjTzInvXmzjqk.getComname()) ? "" : dbzjTzInvXmzjqk.getComname());
    		// 项目类型
    		returnJSONObject.set("typeName", StrUtil.isEmpty(dbzjTzInvXmzjqk.getTypeName()) ? "" : dbzjTzInvXmzjqk.getTypeName());
    		// 项目类别
    		returnJSONObject.set("categoryName", StrUtil.isEmpty(dbzjTzInvXmzjqk.getCategoryName()) ? "" : dbzjTzInvXmzjqk.getCategoryName());
    		// 股权比例
    		returnJSONObject.set("szgq", dbzjTzInvXmzjqk.getSzgq() == null ? new BigDecimal(0) : dbzjTzInvXmzjqk.getSzgq());
    		// 批复名称
    		returnJSONObject.set("pfname", StrUtil.isEmpty(dbzjTzInvXmzjqk.getPfname()) ? "" : dbzjTzInvXmzjqk.getPfname());
    		// 投资总额
    		returnJSONObject.set("ztze", dbzjTzInvXmzjqk.getZtze() == null ? new BigDecimal(0) : dbzjTzInvXmzjqk.getZtze());
    		// 资本金比例
    		returnJSONObject.set("zbjbl", dbzjTzInvXmzjqk.getZbjbl() == null ? new BigDecimal(0) : dbzjTzInvXmzjqk.getZbjbl());
    		// 可占用局资本金
    		returnJSONObject.set("kzyjzbj", dbzjTzInvXmzjqk.getKzyjzbj() == null ? new BigDecimal(0) : dbzjTzInvXmzjqk.getKzyjzbj());
    		// 实际投入自有资金占完成权益投资比例
    		returnJSONObject.set("sjtrzyzjZwcqytzbl", dbzjTzInvXmzjqk.getSjtrzyzjZwcqytzbl() == null ? new BigDecimal(0) : dbzjTzInvXmzjqk.getSjtrzyzjZwcqytzbl());
    		// 实际投入自有资金占投资总额比例
    		returnJSONObject.set("sjtrzyzjZtzzebl", dbzjTzInvXmzjqk.getSjtrzyzjZtzzebl() == null ? new BigDecimal(0) : dbzjTzInvXmzjqk.getSjtrzyzjZtzzebl());
    		// 资本金占用考核指标
    		returnJSONObject.set("zbjzykhzb", dbzjTzInvXmzjqk.getZbjzykhzb() == null ? new BigDecimal(0) : dbzjTzInvXmzjqk.getZbjzykhzb());
    		// 应回流资金
    		returnJSONObject.set("yhlzj", dbzjTzInvXmzjqk.getYhlzj() == null ? new BigDecimal(0) : dbzjTzInvXmzjqk.getYhlzj());
    		// 尚需回流资金
    		returnJSONObject.set("sxhlzj", dbzjTzInvXmzjqk.getSxhlzj() == null ? new BigDecimal(0) : dbzjTzInvXmzjqk.getSxhlzj());
    		//融资是否落实
    		returnJSONObject.set("sfrzls", StrUtil.isEmpty(dbzjTzInvXmzjqk.getSfrzls()) ? "" : dbzjTzInvXmzjqk.getSfrzls());
    		// 填报时间
    		returnJSONObject.set("createDate", dbzjTzInvXmzjqk.getCreateDate() == null ? new Date() : dbzjTzInvXmzjqk.getCreateDate());
    		// 备注
    		returnJSONObject.set("bz", StrUtil.isEmpty(dbzjTzInvXmzjqk.getBz()) ? "" : dbzjTzInvXmzjqk.getBz());
    		//报表年月
    		returnJSONObject.set("periodValue", StrUtil.isEmpty(dbzjTzInvXmzjqk.getPeriodValue()) ? "" : dbzjTzInvXmzjqk.getPeriodValue());
    		

		}
			return repEntity.ok(returnJSONObject);
		}
	}
	/**
	 * 
	 * 资金情况基础数据列表
	 */
	@Override
	public ResponseEntity getZjTzInvXmzjqkMonthlyReportListBasicData(ZjTzInvXmzjqk zjTzInvXmzjqk) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	//公司身份
        	if(StrUtil.equals("2", ext1)) {
            
        	}else {
//            	zjTzAnnualAssess.setExt1SeeFlag("项目和投资事业部应该看不到未上报的数据");
            }
        	// 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzInvXmzjqk.getProjectId(), true)) {
            	zjTzInvXmzjqk.setProjectId("");
            	zjTzInvXmzjqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
//        	zjTzAnnualAssess.setExt1SeeFlag("项目和投资事业部应该看不到未上报的数据");
            if(StrUtil.equals("all", zjTzInvXmzjqk.getProjectId(), true)) {
            	zjTzInvXmzjqk.setProjectId("");
            }
        }
		List<ZjTzInvXmzjqk> zjTzInvXmzjqkMonthlyReportListBasicData = zjTzInvXmzjqkMapper.selectZjTzInvXmzjqkMonthlyReportListBasicData(zjTzInvXmzjqk);
		if (zjTzInvXmzjqkMonthlyReportListBasicData == null) {
			zjTzInvXmzjqkMonthlyReportListBasicData = Lists.newArrayList();
		}
		return repEntity.ok(zjTzInvXmzjqkMonthlyReportListBasicData);
	}
	/**
	 * 
	 * 资金月报导出
	 */
	@Override
	public List<ZjTzInvXmzjqk> exportZjTzInvXmzjqkMonthlyReportList(ZjTzInvXmzjqk zjTzInvXmzjqk) {
		if (zjTzInvXmzjqk == null) {
			zjTzInvXmzjqk = new ZjTzInvXmzjqk();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	//公司身份
        	if(StrUtil.equals("2", ext1)) {
            
        	}else {
//            	zjTzAnnualAssess.setExt1SeeFlag("项目和投资事业部应该看不到未上报的数据");
            }
        	// 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzInvXmzjqk.getProjectId(), true)) {
            	zjTzInvXmzjqk.setProjectId("");
            	zjTzInvXmzjqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
//        	zjTzAnnualAssess.setExt1SeeFlag("项目和投资事业部应该看不到未上报的数据");
            if(StrUtil.equals("all", zjTzInvXmzjqk.getProjectId(), true)) {
            	zjTzInvXmzjqk.setProjectId("");
            }
        }
		int orderNum = 0;
		List<ZjTzInvXmzjqk> newzjTzInvXmzjqkMonthlyReportList = Lists.newArrayList();
		String period = DateUtil.format(zjTzInvXmzjqk.getPeriod(), "yyyyMM");
		if (period != null) {
		zjTzInvXmzjqk.setPeriodValue(period);
        List<String> periodList = Lists.newArrayList();
			switch (period.substring(4)) {
        	case "01":
        		periodList.add(period);
        		break;
        	case "02":
        		periodList.add(period.substring(0, 4) + "01");
        		periodList.add(period);
        		break;
			case "03":
				periodList.add(period.substring(0, 4) + "01");
				periodList.add(period.substring(0, 4) + "02");
        		periodList.add(period);
				break;
			case "04":
				periodList.add(period);
				break;
			case "05":
				periodList.add(period.substring(0, 4) + "04");
        		periodList.add(period);
				break;
			case "06":
				periodList.add(period.substring(0, 4) + "04");
				periodList.add(period.substring(0, 4) + "05");
        		periodList.add(period);
				break;
			case "07":
				periodList.add(period);
				break;
			case "08":
				periodList.add(period.substring(0, 4) + "07");
        		periodList.add(period);
				break;
			case "09":
				periodList.add(period.substring(0, 4) + "07");
				periodList.add(period.substring(0, 4) + "08");
        		periodList.add(period);
				break;
			case "10":
				periodList.add(period);
				break;
			case "11":
				periodList.add(period.substring(0, 4) + "10");
        		periodList.add(period);
				break;
			case "12":
				periodList.add(period.substring(0, 4) + "10");
				periodList.add(period.substring(0, 4) + "11");
        		periodList.add(period);
				break;
			default:
				break;
			}
		List<ZjTzInvXmzjqk> zjTzInvXmzjqkMonthlyReportList = zjTzInvXmzjqkMapper.selectZjTzInvXmzjqkMonthlyReportListByPeriodValue(zjTzInvXmzjqk, periodList);
		for (ZjTzInvXmzjqk dbzjTzInvXmzjqk : zjTzInvXmzjqkMonthlyReportList) {
			ZjTzInvXmzjqk newZjTzInvXmzjqk = new ZjTzInvXmzjqk();
			// 序号
			orderNum = orderNum + 1;
			newZjTzInvXmzjqk.setOrderNum(String.valueOf(orderNum));
			// 报表周期
			newZjTzInvXmzjqk.setPeriodValue("报表周期："+ dbzjTzInvXmzjqk.getPeriodValue());
			// 单位名称
			newZjTzInvXmzjqk.setComname(dbzjTzInvXmzjqk.getComname());
			// 项目名称
			newZjTzInvXmzjqk.setProName(dbzjTzInvXmzjqk.getProName());
			//项目类型
			newZjTzInvXmzjqk.setTypeName(dbzjTzInvXmzjqk.getTypeName());;
			// 批复类型
			newZjTzInvXmzjqk.setPfname(dbzjTzInvXmzjqk.getPfname());
			// 股权比例
			newZjTzInvXmzjqk.setSzgq(dbzjTzInvXmzjqk.getSzgq());
			// 资本金比例
			newZjTzInvXmzjqk.setZbjbl(dbzjTzInvXmzjqk.getZbjbl());
			// 实际投入自有资金占完成权益投资比例
			newZjTzInvXmzjqk.setSjtrzyzjZwcqytzbl(dbzjTzInvXmzjqk.getSjtrzyzjZwcqytzbl());
			// 实际投入自有资金占投资总额比例
			newZjTzInvXmzjqk.setSjtrzyzjZtzzebl(dbzjTzInvXmzjqk.getSjtrzyzjZtzzebl());
			// 资本金占用考核指标
			newZjTzInvXmzjqk.setZbjzykhzb(dbzjTzInvXmzjqk.getZbjzykhzb());
			//可占用局资本金
			BigDecimal kzyjzbj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getKzyjzbj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setKzyjzbj(kzyjzbj);
			//应回流资金
			BigDecimal yhlzj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getYhlzj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setYhlzj(yhlzj);
			//尚需回流的资金
			BigDecimal sxhlzj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getSxhlzj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setSxhlzj(sxhlzj);
			// 融资是否落实
			newZjTzInvXmzjqk.setSfrzls(dbzjTzInvXmzjqk.getSfrzls());
			// 备注
			newZjTzInvXmzjqk.setBz(dbzjTzInvXmzjqk.getBz());
			// 总投资额
			BigDecimal ztze = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getZtze(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setZtze(ztze);
			// 资金结构情况-资本金-小计
			BigDecimal zbjtotal = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getZbjtotal(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setZbjtotal(zbjtotal);
			// 资金结构情况-资本金-自有资金
			BigDecimal zjxqqkZyzj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getZjxqqkZyzj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setZjxqqkZyzj(zjxqqkZyzj);
			// 资金结构情况-资本金-基金
			BigDecimal zjxqqkQzjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getZjxqqkQzjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setZjxqqkQzjj(zjxqqkQzjj);
			// 资金结构情况-资本金-一公局认购基金
			BigDecimal zjxqqkZbjygjrgjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getZjxqqkZbjygjrgjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setZjxqqkZbjygjrgjj(zjxqqkZbjygjrgjj);
			// 资金结构情况-资本金-其他股东
			BigDecimal zjxqqkQtgdzbj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getZjxqqkQtgdzbj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setZjxqqkQtgdzbj(zjxqqkQtgdzbj);
			// 资金结构情况-融资-小计
			BigDecimal rztotal = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getRztotal(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setRztotal(rztotal);
			// 资金结构情况-融资-银行借款
			BigDecimal zjxqqkYhjk = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getZjxqqkYhjk(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setZjxqqkYhjk(zjxqqkYhjk);
			// 资金结构情况-融资-信托
			BigDecimal zjxqqkXt = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getZjxqqkXt(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setZjxqqkXt(zjxqqkXt);
			// 资金结构情况-融资-基金
			BigDecimal zjxqqkJj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getZjxqqkJj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setZjxqqkJj(zjxqqkJj);
			// 资金结构情况-融资-一公局认购基金
			BigDecimal zjxqqkYgjrgjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getZjxqqkYgjrgjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setZjxqqkYgjrgjj(zjxqqkYgjrgjj);
			// 资金结构情况-政府补贴
			BigDecimal zjxqqkZfbt = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getZjxqqkZfbt(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setZjxqqkZfbt(zjxqqkZfbt);
			// 资金结构情况-其他资金
			BigDecimal zjxqqkQtzj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getZjxqqkQtzj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setZjxqqkQtzj(zjxqqkQtzj);
			// 本期到位资金-资本金-小计
			BigDecimal bqzbjtotal = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBqzbjtotal(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBqzbjtotal(bqzbjtotal);
			// 本期到位资金-资本金-自有资金
			BigDecimal bqdwzjZyzj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBqdwzjZyzj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBqdwzjZyzj(bqdwzjZyzj);
			// 本期到位资金-资本金-基金
			BigDecimal bqdwzjQzjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBqdwzjQzjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBqdwzjQzjj(bqdwzjQzjj);
			// 本期到位资金-资本金-一公局认购基金
			BigDecimal bqdwzjZbjygjrgjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBqdwzjZbjygjrgjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBqdwzjZbjygjrgjj(bqdwzjZbjygjrgjj);
			// 本期到位资金-资本金-其他股东等
			BigDecimal bqdwzjQtgdzbj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBqdwzjQtgdzbj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBqdwzjQtgdzbj(bqdwzjQtgdzbj);
			// 本期到位资金-融资-小计
			BigDecimal bqrztotal = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBqrztotal(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBqrztotal(bqrztotal);
			// 本期到位资金-融资-银行借款
			BigDecimal bqdwzjYhjk = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBqdwzjYhjk(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBqdwzjYhjk(bqdwzjYhjk);
			// 本期到位资金-融资-基金
			BigDecimal bqdwzjJj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBqdwzjJj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBqdwzjJj(bqdwzjJj);
			// 本期到位资金-融资-一公局认购基金
			BigDecimal bqdwzjYgjrgjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBqdwzjYgjrgjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBqdwzjYgjrgjj(bqdwzjYgjrgjj);
			// 本期到位资金-融资-信托
			BigDecimal bqdwzjXt = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBqdwzjXt(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBqdwzjXt(bqdwzjXt);
			// 本期到位资金-政府补贴
			BigDecimal bqdwzjZfbt = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBqdwzjZfbt(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBqdwzjZfbt(bqdwzjZfbt);
			// 本期到位资金-其他资金
			BigDecimal bqdwzjQtzj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBqdwzjQtzj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBqdwzjQtzj(bqdwzjQtzj);
			// 本季到位资金-资本金-小计
			BigDecimal bjzbjtotal = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBjzbjtotal(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBjzbjtotal(bjzbjtotal);
			// 本季到位资金-资本金-自有资金
			BigDecimal bjdwzjZyzj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBjdwzjZyzj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBjdwzjZyzj(bjdwzjZyzj);
			// 本季到位资金-资本金-基金
			BigDecimal bjdwzjQzjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBjdwzjQzjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBjdwzjQzjj(bjdwzjQzjj);
			// 本季到位资金-资本金-一公局认购基金
			BigDecimal bjdwzjZbjygjrgjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBjdwzjZbjygjrgjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBjdwzjZbjygjrgjj(bjdwzjZbjygjrgjj);
			// 本季到位资金-资本金-其他股东等
			BigDecimal bjdwzjQtgdzbj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBjdwzjQtgdzbj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBjdwzjQtgdzbj(bjdwzjQtgdzbj);
			// 本季到位资金-融资-小计
			BigDecimal bjrztotal = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBjrztotal(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBjrztotal(bjrztotal);
			// 本季到位资金-融资-银行借款
			BigDecimal bjdwzjYhjk = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBjdwzjYhjk(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBjdwzjYhjk(bjdwzjYhjk);
			// 本季到位资金-融资-基金
			BigDecimal bjdwzjJj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBjdwzjJj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBjdwzjJj(bjdwzjJj);
			// 本季到位资金-融资-一公局认购基金
			BigDecimal bjdwzjYgjrgjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBjdwzjYgjrgjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBjdwzjYgjrgjj(bjdwzjYgjrgjj);
			// 本季到位资金-融资-信托
			BigDecimal bjdwzjXt = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBjdwzjXt(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBjdwzjXt(bjdwzjXt);
			// 本季到位资金-政府补贴
			BigDecimal bjdwzjZfbt = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBjdwzjZfbt(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBjdwzjZfbt(bjdwzjZfbt);
			// 本季到位资金-其他资金
			BigDecimal bjdwzjQtzj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBjdwzjQtzj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBjdwzjQtzj(bjdwzjQtzj);
			// 本年到位资金-资本金-小计
			BigDecimal bnzbjtotal = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBnzbjtotal(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBnzbjtotal(bnzbjtotal);
			// 本年到位资金-资本金-自有资金
			BigDecimal bndwzjZyzj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBndwzjZyzj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBndwzjZyzj(bndwzjZyzj);
			// 本年到位资金-资本金-基金
			BigDecimal bndwzjQzjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBndwzjQzjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBndwzjQzjj(bndwzjQzjj);
			// 本年到位资金-资本金-一公局认购基金
			BigDecimal bndwzjZbjygjrgjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBndwzjZbjygjrgjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBndwzjZbjygjrgjj(bndwzjZbjygjrgjj);
			// 本年到位资金-资本金-其他股东等
			BigDecimal bndwzjQtgdzbj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBndwzjQtgdzbj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBndwzjQtgdzbj(bndwzjQtgdzbj);
			// 本年到位资金-融资-小计
			BigDecimal bnrztotal = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBnrztotal(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBnrztotal(bnrztotal);
			// 本年到位资金-融资-银行借款
			BigDecimal bndwzjYhjk = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBndwzjYhjk(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBndwzjYhjk(bndwzjYhjk);
			// 本年到位资金-融资-基金
			BigDecimal bndwzjJj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBndwzjJj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBndwzjJj(bndwzjJj);
			// 本年到位资金-融资-一公局认购基金
			BigDecimal bndwzjYgjrgjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBndwzjYgjrgjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBndwzjYgjrgjj(bndwzjYgjrgjj);
			// 本年到位资金-融资-信托
			BigDecimal bndwzjXt = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBndwzjXt(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBndwzjXt(bndwzjXt);
			// 本年到位资金-政府补贴
			BigDecimal bndwzjZfbt = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBndwzjZfbt(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBndwzjZfbt(bndwzjZfbt);
			// 本年到位资金-其他资金
			BigDecimal bndwzjQtzj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getBndwzjQtzj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setBndwzjQtzj(bndwzjQtzj);
			// 累计到位资金-资本金-小计
			BigDecimal ljzbjtotal = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getLjzbjtotal(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setLjzbjtotal(ljzbjtotal);
			// 累计到位资金-资本金-自有资金
			BigDecimal ljdwzjZyzj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getLjdwzjZyzj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setLjdwzjZyzj(ljdwzjZyzj);
			// 累计到位资金-资本金-基金
			BigDecimal ljdwzjQzjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getLjdwzjQzjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setLjdwzjQzjj(ljdwzjQzjj);
			// 累计到位资金-资本金-一公局认购基金
			BigDecimal ljdwzjZbjygjrgjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getLjdwzjZbjygjrgjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setLjdwzjZbjygjrgjj(ljdwzjZbjygjrgjj);
			// 累计到位资金-资本金-其他股东等
			BigDecimal ljdwzjQtgdzbj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getLjdwzjQtgdzbj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setLjdwzjQtgdzbj(ljdwzjQtgdzbj);
			// 累计到位资金-融资-小计
			BigDecimal ljrztotal = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getLjrztotal(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setLjrztotal(ljrztotal);
			// 累计到位资金-融资-银行借款
			BigDecimal ljdwzjYhjk = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getLjdwzjYhjk(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setLjdwzjYhjk(ljdwzjYhjk);
			// 累计到位资金-融资-基金
			BigDecimal ljdwzjJj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getLjdwzjJj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setLjdwzjJj(ljdwzjJj);
			// 累计到位资金-融资-一公局认购基金
			BigDecimal ljdwzjYgjrgjj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getLjdwzjYgjrgjj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setLjdwzjYgjrgjj(ljdwzjYgjrgjj);
			// 累计到位资金-融资-信托
			BigDecimal ljdwzjXt = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getLjdwzjXt(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setLjdwzjXt(ljdwzjXt);
			// 累计到位资金-政府补贴
			BigDecimal ljdwzjZfbt = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getLjdwzjZfbt(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setLjdwzjZfbt(ljdwzjZfbt);
			// 累计到位资金-其他资金
			BigDecimal ljdwzjQtzj = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getLjdwzjQtzj(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setLjdwzjQtzj(ljdwzjQtzj);
			// 建设期的资本运作（回流资金）本期
			BigDecimal jsqzbyzJzbqmJsqzjhb = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getJsqzbyzJzbqmJsqzjhb(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setJsqzbyzJzbqmJsqzjhb(jsqzbyzJzbqmJsqzjhb);
			// 建设期的资本运作（回流资金）本季
			BigDecimal jsqzbyzJzbjmJsqzjhb = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getJsqzbyzJzbjmJsqzjhb(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setJsqzbyzJzbjmJsqzjhb(jsqzbyzJzbjmJsqzjhb);
			// 建设期的资本运作（回流资金）本年
			BigDecimal jsqzbyzJzbnmJsqzjhb = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getJsqzbyzJzbnmJsqzjhb(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setJsqzbyzJzbnmJsqzjhb(jsqzbyzJzbnmJsqzjhb);
			// 建设期的资本运作（回流资金）开累
			BigDecimal jsqzbyzXmklJsqzjhb = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getJsqzbyzXmklJsqzjhb(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setJsqzbyzXmklJsqzjhb(jsqzbyzXmklJsqzjhb);
			// 实际投入自有资金本期
			BigDecimal sjtrzyzjJzbqm = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getSjtrzyzjJzbqm(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setSjtrzyzjJzbqm(sjtrzyzjJzbqm);
			// 实际投入自有资金本季
			BigDecimal sjtrzyzjJzbjm = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getSjtrzyzjJzbjm(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setSjtrzyzjJzbjm(sjtrzyzjJzbjm);
			// 实际投入自有资金本年
			BigDecimal sjtrzyzjJzbnm = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getSjtrzyzjJzbnm(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setSjtrzyzjJzbnm(sjtrzyzjJzbnm);
			// 实际投入自有资金开累
			BigDecimal sjtrzyzjXmkl = CalcUtils.calcDivide(dbzjTzInvXmzjqk.getSjtrzyzjXmkl(), new BigDecimal(10000), 2);
			newZjTzInvXmzjqk.setSjtrzyzjXmkl(sjtrzyzjXmkl);
			newzjTzInvXmzjqkMonthlyReportList.add(newZjTzInvXmzjqk);
		}
        
	}
		return newzjTzInvXmzjqkMonthlyReportList;
}
	/**
	 * 
	 * 首页图表数据资金情况
	 */
	@Override
	public ResponseEntity getHomeChartCapitalStatus(ZjTzInvXmzjqk zjTzInvXmzjqk) {
		if (zjTzInvXmzjqk == null) {
			zjTzInvXmzjqk = new ZjTzInvXmzjqk();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	//其他身份
        	zjTzInvXmzjqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSqlForHome());
        }
		if (zjTzInvXmzjqk.getPeriod() != null) {
			String period = DateUtil.format(zjTzInvXmzjqk.getPeriod(), "yyyyMM");
			zjTzInvXmzjqk.setPeriodValue(period);
		}
		List<ZjTzInvXmzjqk> chartCapitalStatusList = zjTzInvXmzjqkMapper.selectHomeChartCapitalStatus(zjTzInvXmzjqk);
		if (chartCapitalStatusList != null && chartCapitalStatusList.size() > 0) {
			for (ZjTzInvXmzjqk zjTzInvXmzjqk2 : chartCapitalStatusList) {
				BigDecimal hte = CalcUtils.calcDivide(zjTzInvXmzjqk2.getHte(), new BigDecimal(10000));
				zjTzInvXmzjqk2.setHte(hte.setScale(2, BigDecimal.ROUND_HALF_UP));
				BigDecimal zbjzykhzb = zjTzInvXmzjqk2.getZbjzykhzb();
				zjTzInvXmzjqk2.setZbjzykhzb(zbjzykhzb.setScale(2, BigDecimal.ROUND_HALF_UP));
				BigDecimal sjtrzyzjztzzebl = zjTzInvXmzjqk2.getSjtrzyzjZtzzebl();
				zjTzInvXmzjqk2.setSjtrzyzjZtzzebl(sjtrzyzjztzzebl.setScale(2, BigDecimal.ROUND_HALF_UP));
			}
		}
		return repEntity.ok(chartCapitalStatusList);
}
	/**
	 * 
	 * 建设页资金情况
	 */
	@Override
	public ResponseEntity getConstructPageCapital(ZjTzInvXmzjqk zjTzInvXmzjqk) {
		if (zjTzInvXmzjqk == null) {
			zjTzInvXmzjqk = new ZjTzInvXmzjqk();
		}
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	//公司身份
        	if(StrUtil.equals("2", ext1)) {
            
        	}else {
//            	zjTzAnnualAssess.setExt1SeeFlag("项目和投资事业部应该看不到未上报的数据");
            }
        	// 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzInvXmzjqk.getProjectId(), true)) {
            	zjTzInvXmzjqk.setProjectId("");
            	zjTzInvXmzjqk.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
//        	zjTzAnnualAssess.setExt1SeeFlag("项目和投资事业部应该看不到未上报的数据");
            if(StrUtil.equals("all", zjTzInvXmzjqk.getProjectId(), true)) {
            	zjTzInvXmzjqk.setProjectId("");
            }
        }
        
        if (StrUtil.equals(zjTzInvXmzjqk.getProProcessId(), "2")) {
			
        	ZjTzInvXmzjqk dbzjTzInvXmzjqk = zjTzInvXmzjqkMapper.selectConstructPageCapital(zjTzInvXmzjqk);
        	if (dbzjTzInvXmzjqk == null) {
        		dbzjTzInvXmzjqk = new ZjTzInvXmzjqk();
			}
        	// 
        	JSONArray jsonArray =  new JSONArray();
        	JSONObject jsonItem = new JSONObject();
        	// 资本金
        	jsonItem = new JSONObject();
        	jsonItem.set("name", "资本金");
        	jsonItem.set("value", dbzjTzInvXmzjqk.getZbjtotal());
        	jsonArray.add(jsonItem);
        	// 融资
        	jsonItem = new JSONObject();
        	jsonItem.set("name", "融资");
        	jsonItem.set("value", dbzjTzInvXmzjqk.getRztotal());
        	jsonArray.add(jsonItem);
        	// 政府补贴
        	jsonItem = new JSONObject();
        	jsonItem.set("name", "政府补贴");
        	jsonItem.set("value", dbzjTzInvXmzjqk.getZjxqqkZfbt());
        	jsonArray.add(jsonItem);
        	// 其他资金
        	jsonItem = new JSONObject();
        	jsonItem.set("name", "其他资金");
        	jsonItem.set("value", dbzjTzInvXmzjqk.getZjxqqkQtzj());
        	jsonArray.add(jsonItem);
        	// ['班车','包车','危险品','货运','出租','公交','驾培','泥头车','其他']
        	
        	JSONArray jsonArrayTitle =  new JSONArray();
        	jsonArrayTitle.add("资本金");
        	jsonArrayTitle.add("融资");
        	jsonArrayTitle.add("政府补贴");
        	jsonArrayTitle.add("其他资金");
        	
        	
        	JSONObject json = new JSONObject();
        	// ￥投资总额
        	json.set("ztze", dbzjTzInvXmzjqk.getZtze());
        	// 资金占用指标
        	json.set("zbjzykhzb", dbzjTzInvXmzjqk.getZbjzykhzb().setScale(2, BigDecimal.ROUND_HALF_UP));
        	// 实际占用指标
        	BigDecimal sjtrzyzjztzzebl = CalcUtils.calcMultiply(dbzjTzInvXmzjqk.getSjtrzyzjZtzzebl(), new BigDecimal(100));
        	json.set("sjzjzyzb", sjtrzyzjztzzebl.setScale(2, BigDecimal.ROUND_HALF_UP));
        	// 一公局投入资本金
        	json.set("ljdwzjZyzj", dbzjTzInvXmzjqk.getLjdwzjZyzj());
        	// 项目回流资金
        	json.set("jsqzbyzXmklJsqzjhb", dbzjTzInvXmzjqk.getJsqzbyzXmklJsqzjhb());
        	json.set("list", jsonArray);
        	json.set("listTitle", jsonArrayTitle);
        	return repEntity.ok(json);

		}else {
        	ZjTzInvXmzjqk dbzjTzInvXmzjqk = zjTzInvXmzjqkMapper.selectConstructPageCapital(zjTzInvXmzjqk);
        	
        	// 
        	JSONArray jsonArray =  new JSONArray();
        	JSONObject jsonItem = new JSONObject();
        	if (dbzjTzInvXmzjqk == null) {
        		dbzjTzInvXmzjqk = new ZjTzInvXmzjqk();
			}
        	// 资本金
        	jsonItem = new JSONObject();
        	jsonItem.set("name", "资本金");
        	jsonItem.set("value", dbzjTzInvXmzjqk.getZbjtotal());
        	jsonArray.add(jsonItem);
        	// 融资
        	jsonItem = new JSONObject();
        	jsonItem.set("name", "融资");
        	jsonItem.set("value", dbzjTzInvXmzjqk.getRztotal());
        	jsonArray.add(jsonItem);
        	// 政府补贴
        	jsonItem = new JSONObject();
        	jsonItem.set("name", "政府补贴");
        	jsonItem.set("value", dbzjTzInvXmzjqk.getZjxqqkZfbt());
        	jsonArray.add(jsonItem);
        	// 其他资金
        	jsonItem = new JSONObject();
        	jsonItem.set("name", "其他资金");
        	jsonItem.set("value", dbzjTzInvXmzjqk.getZjxqqkQtzj());
        	jsonArray.add(jsonItem);
        	// ['班车','包车','危险品','货运','出租','公交','驾培','泥头车','其他']
        	
        	JSONArray jsonArrayTitle =  new JSONArray();
        	jsonArrayTitle.add("资本金");
        	jsonArrayTitle.add("融资");
        	jsonArrayTitle.add("政府补贴");
        	jsonArrayTitle.add("其他资金");
        	
        	
        	JSONObject json = new JSONObject();
        	// ￥投资总额
        	json.set("ztze", dbzjTzInvXmzjqk.getZtze());
        	json.set("list", jsonArray);
        	json.set("listTitle", jsonArrayTitle);
        	return repEntity.ok(json);
		}
		
	}
}
