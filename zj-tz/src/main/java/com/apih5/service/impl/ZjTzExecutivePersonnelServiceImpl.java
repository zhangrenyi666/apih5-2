package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzExecutivePersonnelMapper;
import com.apih5.mybatis.pojo.ZjTzExecutivePersonnel;
import com.apih5.service.ZjTzExecutivePersonnelService;
import com.apih5.service.ZjTzPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzExecutivePersonnelService")
public class ZjTzExecutivePersonnelServiceImpl implements ZjTzExecutivePersonnelService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzExecutivePersonnelMapper zjTzExecutivePersonnelMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;


    @Override
    public ResponseEntity getZjTzExecutivePersonnelListByCondition(ZjTzExecutivePersonnel zjTzExecutivePersonnel) {
        if (zjTzExecutivePersonnel == null) {
            zjTzExecutivePersonnel = new ZjTzExecutivePersonnel();
        }
        // 分页查询
        PageHelper.startPage(zjTzExecutivePersonnel.getPage(),zjTzExecutivePersonnel.getLimit());
        // 获取数据
        List<ZjTzExecutivePersonnel> zjTzExecutivePersonnelList = zjTzExecutivePersonnelMapper.selectByZjTzExecutivePersonnelList(zjTzExecutivePersonnel);
        // 得到分页信息
        PageInfo<ZjTzExecutivePersonnel> p = new PageInfo<>(zjTzExecutivePersonnelList);

        return repEntity.okList(zjTzExecutivePersonnelList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzExecutivePersonnelDetails(ZjTzExecutivePersonnel zjTzExecutivePersonnel) {
        if (zjTzExecutivePersonnel == null) {
            zjTzExecutivePersonnel = new ZjTzExecutivePersonnel();
        }
        // 获取数据
        ZjTzExecutivePersonnel dbZjTzExecutivePersonnel = zjTzExecutivePersonnelMapper.selectByPrimaryKey(zjTzExecutivePersonnel.getExecutivePersonnelId());
        // 数据存在
        if (dbZjTzExecutivePersonnel != null) {
            return repEntity.ok(dbZjTzExecutivePersonnel);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzExecutivePersonnel(ZjTzExecutivePersonnel zjTzExecutivePersonnel) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzExecutivePersonnel.setExecutivePersonnelId(UuidUtil.generate());
        zjTzExecutivePersonnel.setCreateUserInfo(userKey, realName);
        int flag = zjTzExecutivePersonnelMapper.insert(zjTzExecutivePersonnel);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzExecutivePersonnel);
        }
    }

    @Override
    public ResponseEntity updateZjTzExecutivePersonnel(ZjTzExecutivePersonnel zjTzExecutivePersonnel) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzExecutivePersonnel dbzjTzExecutivePersonnel = zjTzExecutivePersonnelMapper.selectByPrimaryKey(zjTzExecutivePersonnel.getExecutivePersonnelId());
        if (dbzjTzExecutivePersonnel != null && StrUtil.isNotEmpty(dbzjTzExecutivePersonnel.getExecutivePersonnelId())) {
           // 董监高会id
           dbzjTzExecutivePersonnel.setExecutiveMeetId(zjTzExecutivePersonnel.getExecutiveMeetId());
           // 股东名称
           dbzjTzExecutivePersonnel.setShareholderName(zjTzExecutivePersonnel.getShareholderName());
           // 股比
           dbzjTzExecutivePersonnel.setProportion(zjTzExecutivePersonnel.getProportion());
           // 董事长
           dbzjTzExecutivePersonnel.setDirectorz(zjTzExecutivePersonnel.getDirectorz());
           // 董事长旧
           dbzjTzExecutivePersonnel.setDirectorzOld(zjTzExecutivePersonnel.getDirectorzOld());
           // 董事
           dbzjTzExecutivePersonnel.setDirector(zjTzExecutivePersonnel.getDirector());
           // 董事旧
           dbzjTzExecutivePersonnel.setDirectorOld(zjTzExecutivePersonnel.getDirectorOld());
           // 监事长
           dbzjTzExecutivePersonnel.setSupervisorz(zjTzExecutivePersonnel.getSupervisorz());
           // 监事长旧
           dbzjTzExecutivePersonnel.setSupervisorzOld(zjTzExecutivePersonnel.getSupervisorzOld());
           // 监事
           dbzjTzExecutivePersonnel.setSupervisor(zjTzExecutivePersonnel.getSupervisor());
           // 监事旧
           dbzjTzExecutivePersonnel.setSupervisorOld(zjTzExecutivePersonnel.getSupervisorOld());
           // 项目公司总经理
           dbzjTzExecutivePersonnel.setManager(zjTzExecutivePersonnel.getManager());
           // 项目公司总经理旧
           dbzjTzExecutivePersonnel.setManagerOld(zjTzExecutivePersonnel.getManagerOld());
           // 备注
           dbzjTzExecutivePersonnel.setRemarks(zjTzExecutivePersonnel.getRemarks());
           // 排序号
           dbzjTzExecutivePersonnel.setOrderNum(zjTzExecutivePersonnel.getOrderNum());
           // 总工
           dbzjTzExecutivePersonnel.setChief1(zjTzExecutivePersonnel.getChief1());
           // 总会
           dbzjTzExecutivePersonnel.setChief2(zjTzExecutivePersonnel.getChief2());
           // 总经
           dbzjTzExecutivePersonnel.setChief3(zjTzExecutivePersonnel.getChief3());
           //股东类型id
           dbzjTzExecutivePersonnel.setShareTypeId(zjTzExecutivePersonnel.getShareTypeId());
           //股东类型
           dbzjTzExecutivePersonnel.setShareType(zjTzExecutivePersonnel.getShareType());
           // 共通
           dbzjTzExecutivePersonnel.setModifyUserInfo(userKey, realName);
           flag = zjTzExecutivePersonnelMapper.updateByPrimaryKey(dbzjTzExecutivePersonnel);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzExecutivePersonnel);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzExecutivePersonnel(List<ZjTzExecutivePersonnel> zjTzExecutivePersonnelList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzExecutivePersonnelList != null && zjTzExecutivePersonnelList.size() > 0) {
           ZjTzExecutivePersonnel zjTzExecutivePersonnel = new ZjTzExecutivePersonnel();
           zjTzExecutivePersonnel.setModifyUserInfo(userKey, realName);
           flag = zjTzExecutivePersonnelMapper.batchDeleteUpdateZjTzExecutivePersonnel(zjTzExecutivePersonnelList, zjTzExecutivePersonnel);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzExecutivePersonnelList);
        }
    }
    
    @Override
	public ResponseEntity getZjTzExecutivePersonnelListForReport(ZjTzExecutivePersonnel zjTzExecutivePersonnel) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userId = TokenUtils.getUserId(request);
    	String ext1 = TokenUtils.getExt1(request);
    	if (zjTzExecutivePersonnel == null) {
    		zjTzExecutivePersonnel = new ZjTzExecutivePersonnel();
    	}
    	// 不是集团的人员时
    	if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
    		// 选择全部项目是，通过拼接的sql去查询
    		if(StrUtil.equals("all", zjTzExecutivePersonnel.getProjectId(), true)) {
    			zjTzExecutivePersonnel.setProjectId("");
    			zjTzExecutivePersonnel.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
    		}
    	} else {
    		// 集团人员时
    		if(StrUtil.equals("all", zjTzExecutivePersonnel.getProjectId(), true)) {
    			zjTzExecutivePersonnel.setProjectId("");
    		}
    	}
		List<ZjTzExecutivePersonnel> returnList = zjTzExecutivePersonnelMapper.uReportZjTzExecutivePersonnelList(zjTzExecutivePersonnel);
 		 return repEntity.ok(returnList);
	}

	@Override
	public List<ZjTzExecutivePersonnel> uReportZjTzExecutivePersonnelList(ZjTzExecutivePersonnel zjTzExecutivePersonnel) {
 		if (zjTzExecutivePersonnel == null) {
			zjTzExecutivePersonnel = new ZjTzExecutivePersonnel();
		}
 		String userId = zjTzExecutivePersonnel.getUserId();
		String ext1 = zjTzExecutivePersonnel.getExt1();
		// 不是集团的人员时
    	if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
    		// 选择全部项目是，通过拼接的sql去查询
    		if(StrUtil.equals("all", zjTzExecutivePersonnel.getProjectId(), true)) {
    			zjTzExecutivePersonnel.setProjectId("");
    			zjTzExecutivePersonnel.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
    		}
    	} else {
    		// 集团人员时
    		if(StrUtil.equals("all", zjTzExecutivePersonnel.getProjectId(), true)) {
    			zjTzExecutivePersonnel.setProjectId("");
    		}
    	}
 		
		int order = 0;
		List<ZjTzExecutivePersonnel> returnList = new ArrayList<>();
		ZjTzExecutivePersonnel personnel = new ZjTzExecutivePersonnel();
		personnel.setGroupByFlag("分组生成报表不能实现的序号");
		List<ZjTzExecutivePersonnel> personnelGroupByList = zjTzExecutivePersonnelMapper.uReportZjTzExecutivePersonnelList(personnel);
 		if(personnelGroupByList != null && personnelGroupByList.size() >0) {
			returnList = zjTzExecutivePersonnelMapper.uReportZjTzExecutivePersonnelList(zjTzExecutivePersonnel);
			for (ZjTzExecutivePersonnel personnelGroupBy : personnelGroupByList) {
				order ++;
				for (ZjTzExecutivePersonnel personnelOrder : returnList) {
					if(StrUtil.equals(personnelGroupBy.getProjectId(), personnelOrder.getProjectId())) {
						personnelOrder.setOrderNum(order);
					}
				}
			}
		}
		return returnList;
	}

	
}