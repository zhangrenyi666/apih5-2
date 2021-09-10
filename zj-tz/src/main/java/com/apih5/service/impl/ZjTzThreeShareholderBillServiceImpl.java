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
import com.apih5.mybatis.dao.ZjTzThreeDirectorBillMapper;
import com.apih5.mybatis.dao.ZjTzThreeShareholderBillMapper;
import com.apih5.mybatis.dao.ZjTzThreeSupervisorBillMapper;
import com.apih5.mybatis.pojo.ZjTzThreeDirectorBill;
import com.apih5.mybatis.pojo.ZjTzThreeShareholderBill;
import com.apih5.mybatis.pojo.ZjTzThreeSupervisorBill;
import com.apih5.service.ZjTzPermissionService;
import com.apih5.service.ZjTzThreeShareholderBillService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzThreeShareholderBillService")
public class ZjTzThreeShareholderBillServiceImpl implements ZjTzThreeShareholderBillService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzThreeShareholderBillMapper zjTzThreeShareholderBillMapper;

    @Autowired(required = true)
    private ZjTzThreeDirectorBillMapper zjTzThreeDirectorBillMapper;

    @Autowired(required = true)
    private ZjTzThreeSupervisorBillMapper zjTzThreeSupervisorBillMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
    @Override
    public ResponseEntity getZjTzThreeShareholderBillListByCondition(ZjTzThreeShareholderBill zjTzThreeShareholderBill) {
        if (zjTzThreeShareholderBill == null) {
            zjTzThreeShareholderBill = new ZjTzThreeShareholderBill();
        }
        if(StrUtil.isEmpty(zjTzThreeShareholderBill.getThreeShareholderId())) {
        	 return repEntity.okList(null, 0);
        }
        // 分页查询
        PageHelper.startPage(zjTzThreeShareholderBill.getPage(),zjTzThreeShareholderBill.getLimit());
        // 获取数据
        List<ZjTzThreeShareholderBill> zjTzThreeShareholderBillList = zjTzThreeShareholderBillMapper.selectByZjTzThreeShareholderBillList(zjTzThreeShareholderBill);
        // 得到分页信息
        PageInfo<ZjTzThreeShareholderBill> p = new PageInfo<>(zjTzThreeShareholderBillList);

        return repEntity.okList(zjTzThreeShareholderBillList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzThreeShareholderBillDetails(ZjTzThreeShareholderBill zjTzThreeShareholderBill) {
        if (zjTzThreeShareholderBill == null) {
            zjTzThreeShareholderBill = new ZjTzThreeShareholderBill();
        }
        // 获取数据
        ZjTzThreeShareholderBill dbZjTzThreeShareholderBill = zjTzThreeShareholderBillMapper.selectByPrimaryKey(zjTzThreeShareholderBill.getThreeShareholderBillId());
        // 数据存在
        if (dbZjTzThreeShareholderBill != null) {
            return repEntity.ok(dbZjTzThreeShareholderBill);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzThreeShareholderBill(ZjTzThreeShareholderBill zjTzThreeShareholderBill) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzThreeShareholderBill.setThreeShareholderBillId(UuidUtil.generate());
        //决议结果
        if (StrUtil.equals(zjTzThreeShareholderBill.getResultId(), "1")) {
        	zjTzThreeShareholderBill.setResultName("同意");
        }else if (StrUtil.equals(zjTzThreeShareholderBill.getResultId(), "2")) {
        	zjTzThreeShareholderBill.setResultName("不同意");
        }else if (StrUtil.equals(zjTzThreeShareholderBill.getResultId(), "3")) {
        	zjTzThreeShareholderBill.setResultName("其他");
        }
        //决议完成情况
        if (StrUtil.equals(zjTzThreeShareholderBill.getCompleteId(), "1")) {
        	zjTzThreeShareholderBill.setCompleteName("完成");
        }else if (StrUtil.equals(zjTzThreeShareholderBill.getCompleteId(), "2")) {
        	zjTzThreeShareholderBill.setCompleteName("未完成");
        }
        zjTzThreeShareholderBill.setCreateUserInfo(userKey, realName);
        int flag = zjTzThreeShareholderBillMapper.insert(zjTzThreeShareholderBill);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzThreeShareholderBill);
        }
    }
 
    @Override
    public ResponseEntity updateZjTzThreeShareholderBill(ZjTzThreeShareholderBill zjTzThreeShareholderBill) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzThreeShareholderBill dbzjTzThreeShareholderBill = zjTzThreeShareholderBillMapper.selectByPrimaryKey(zjTzThreeShareholderBill.getThreeShareholderBillId());
        if (dbzjTzThreeShareholderBill != null && StrUtil.isNotEmpty(dbzjTzThreeShareholderBill.getThreeShareholderBillId())) {
           // 议案名称
           dbzjTzThreeShareholderBill.setBillName(zjTzThreeShareholderBill.getBillName());
           // 决议结果id
           dbzjTzThreeShareholderBill.setResultId(zjTzThreeShareholderBill.getResultId());
           // 决议结果name
           if (StrUtil.equals(zjTzThreeShareholderBill.getResultId(), "1")) {
        	   dbzjTzThreeShareholderBill.setResultName("同意");
           }else if (StrUtil.equals(zjTzThreeShareholderBill.getResultId(), "2")) {
        	   dbzjTzThreeShareholderBill.setResultName("不同意");
           }else if (StrUtil.equals(zjTzThreeShareholderBill.getResultId(), "3")) {
        	   dbzjTzThreeShareholderBill.setResultName("其他");
           }
           // 会议提出的其他要求
           dbzjTzThreeShareholderBill.setOtherRequire(zjTzThreeShareholderBill.getOtherRequire());
           // 决议执行情况具体描述
           dbzjTzThreeShareholderBill.setSpecificDesc(zjTzThreeShareholderBill.getSpecificDesc());
           // 决议完成情况id
           dbzjTzThreeShareholderBill.setCompleteId(zjTzThreeShareholderBill.getCompleteId());
           // 决议完成情况name
           if (StrUtil.equals(zjTzThreeShareholderBill.getCompleteId(), "1")) {
        	   dbzjTzThreeShareholderBill.setCompleteName("完成");
           }else if (StrUtil.equals(zjTzThreeShareholderBill.getCompleteId(), "2")) {
        	   dbzjTzThreeShareholderBill.setCompleteName("未完成");
           }
           // 备注
           dbzjTzThreeShareholderBill.setRemarks(zjTzThreeShareholderBill.getRemarks());
           // 排序号
           dbzjTzThreeShareholderBill.setSortNumber(zjTzThreeShareholderBill.getSortNumber());
           // 共通
           dbzjTzThreeShareholderBill.setModifyUserInfo(userKey, realName);
           flag = zjTzThreeShareholderBillMapper.updateByPrimaryKey(dbzjTzThreeShareholderBill);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzThreeShareholderBill);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzThreeShareholderBill(List<ZjTzThreeShareholderBill> zjTzThreeShareholderBillList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzThreeShareholderBillList != null && zjTzThreeShareholderBillList.size() > 0) {
           ZjTzThreeShareholderBill zjTzThreeShareholderBill = new ZjTzThreeShareholderBill();
           zjTzThreeShareholderBill.setModifyUserInfo(userKey, realName);
           flag = zjTzThreeShareholderBillMapper.batchDeleteUpdateZjTzThreeShareholderBill(zjTzThreeShareholderBillList, zjTzThreeShareholderBill);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzThreeShareholderBillList);
        }
    }

    @Override
	public ResponseEntity getZjTzThreeShareholderBillListForReport(ZjTzThreeShareholderBill zjTzThreeShareholderBill) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userId = TokenUtils.getUserId(request);
    	String ext1 = TokenUtils.getExt1(request);
    	if (zjTzThreeShareholderBill == null) {
    		zjTzThreeShareholderBill = new ZjTzThreeShareholderBill();
    	}
    	
		//先判断哪儿会议项目数量最多
		int shaPro = 0;
		int dirPro = 0;
		int supPro = 0;
		boolean shaProMax = false;
		boolean dirProMax = false;
		boolean supProMax = false;
		ZjTzThreeShareholderBill shareholderBillPro = new ZjTzThreeShareholderBill();
		shareholderBillPro.setGroupByFlag("根据项目名称分组");
		if(StrUtil.isNotEmpty(zjTzThreeShareholderBill.getCompleteId())) {
			shareholderBillPro.setCompleteId(zjTzThreeShareholderBill.getCompleteId());
		}
		shareholderBillPro.setProjectId(zjTzThreeShareholderBill.getProjectId());
		// 不是集团的人员时
    	if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
    		// 选择全部项目是，通过拼接的sql去查询
    		if(StrUtil.equals("all", zjTzThreeShareholderBill.getProjectId(), true)) {
    			shareholderBillPro.setProjectId("");
    			shareholderBillPro.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
    		}
    	} else {
    		// 集团人员时
    		if(StrUtil.equals("all", zjTzThreeShareholderBill.getProjectId(), true)) {
    			shareholderBillPro.setProjectId("");
    		}
    	}
		List<ZjTzThreeShareholderBill> shareholderBillProList = zjTzThreeShareholderBillMapper.ureportZjTzThreeShareholderBillList(shareholderBillPro);
		if(shareholderBillProList != null && shareholderBillProList.size() >0) {
			shaPro = shareholderBillProList.size();
		}
		ZjTzThreeDirectorBill  directorBillPro = new ZjTzThreeDirectorBill();
		directorBillPro.setGroupByFlag("根据项目名称分组");
		if(StrUtil.isNotEmpty(zjTzThreeShareholderBill.getCompleteId())) {
			directorBillPro.setCompleteId(zjTzThreeShareholderBill.getCompleteId());
		}
		directorBillPro.setProjectId(zjTzThreeShareholderBill.getProjectId());
		// 不是集团的人员时
    	if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
    		// 选择全部项目是，通过拼接的sql去查询
    		if(StrUtil.equals("all", zjTzThreeShareholderBill.getProjectId(), true)) {
    			directorBillPro.setProjectId("");
    			directorBillPro.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
    		}
    	} else {
    		// 集团人员时
    		if(StrUtil.equals("all", zjTzThreeShareholderBill.getProjectId(), true)) {
    			directorBillPro.setProjectId("");
    		}
    	}
		List<ZjTzThreeDirectorBill> directorBillProList = zjTzThreeDirectorBillMapper.ureportZjTzThreeDirectorBillList(directorBillPro);
		if(directorBillProList != null && directorBillProList.size() >0) {
			dirPro = directorBillProList.size();
		}
		ZjTzThreeSupervisorBill supervisorBillPro = new ZjTzThreeSupervisorBill();
		supervisorBillPro.setGroupByFlag("根据项目名称分组");
		if(StrUtil.isNotEmpty(zjTzThreeShareholderBill.getCompleteId())) {
			supervisorBillPro.setCompleteId(zjTzThreeShareholderBill.getCompleteId());
		}
		supervisorBillPro.setProjectId(zjTzThreeShareholderBill.getProjectId());
		// 不是集团的人员时
    	if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
    		// 选择全部项目是，通过拼接的sql去查询
    		if(StrUtil.equals("all", zjTzThreeShareholderBill.getProjectId(), true)) {
    			supervisorBillPro.setProjectId("");
    			supervisorBillPro.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
    		}
    	} else {
    		// 集团人员时
    		if(StrUtil.equals("all", zjTzThreeShareholderBill.getProjectId(), true)) {
    			supervisorBillPro.setProjectId("");
    		}
    	}
		List<ZjTzThreeSupervisorBill> supervisorBillProList = zjTzThreeSupervisorBillMapper.ureportZjTzThreeSupervisorBillList(supervisorBillPro);
		if(supervisorBillProList != null && supervisorBillProList.size() >0) {
			supPro = supervisorBillProList.size();
		}
		int maxPro = ((maxPro=(shaPro>dirPro)?shaPro:dirPro)>supPro?maxPro:supPro);
		if(maxPro == shaPro) {
			shaProMax = true;
		}else if(maxPro == supPro) {
			supProMax = true;
		}else if(maxPro == dirPro) {
			dirProMax = true;
		}
		//
		List returnZjTzThreeBillList = new ArrayList<>();
		List<ZjTzThreeShareholderBill> shaBillListAll = new ArrayList<>();
		List<ZjTzThreeSupervisorBill> supBillListAll = new ArrayList<>();
		List<ZjTzThreeDirectorBill> dirBillListAll = new ArrayList<>();
		//再判断相同项目下哪儿会议最多
		int shareNum = 0;
		int supNum = 0;
		int dirNum = 0;
		boolean sha = false;
		boolean sup = false;
		boolean dir = false;
		//
		if(shaProMax) {
			for (ZjTzThreeShareholderBill share : shareholderBillProList) {
				ZjTzThreeShareholderBill shaBill = new ZjTzThreeShareholderBill();
				shaBill.setProjectName(share.getProjectName());
				if(StrUtil.isNotEmpty(zjTzThreeShareholderBill.getCompleteId())) {
					shaBill.setCompleteId(zjTzThreeShareholderBill.getCompleteId());
				}
				shaBill.setProjectId(zjTzThreeShareholderBill.getProjectId());
				// 不是集团的人员时
		    	if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
		    		// 选择全部项目是，通过拼接的sql去查询
		    		if(StrUtil.equals("all", zjTzThreeShareholderBill.getProjectId(), true)) {
		    			shaBill.setProjectId("");
		    			shaBill.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
		    		}
		    	} else {
		    		// 集团人员时
		    		if(StrUtil.equals("all", zjTzThreeShareholderBill.getProjectId(), true)) {
		    			shaBill.setProjectId("");
		    		}
		    	}
				List<ZjTzThreeShareholderBill> shareList = zjTzThreeShareholderBillMapper.ureportZjTzThreeShareholderBillList(shaBill);
				if(shareList != null && shareList.size() >0) {
					shareNum = shareList.size();
				}
				ZjTzThreeDirectorBill  dirBill = new ZjTzThreeDirectorBill();
				dirBill.setProjectName(share.getProjectName());
				if(StrUtil.isNotEmpty(zjTzThreeShareholderBill.getCompleteId())) {
					dirBill.setCompleteId(zjTzThreeShareholderBill.getCompleteId());
				}
				List<ZjTzThreeDirectorBill> dirBillList = zjTzThreeDirectorBillMapper.ureportZjTzThreeDirectorBillList(dirBill);
				if(dirBillList != null && dirBillList.size() >0) {
					dirNum = dirBillList.size();
				}
				ZjTzThreeSupervisorBill supBill = new ZjTzThreeSupervisorBill();
				supBill.setProjectName(share.getProjectName());
				if(StrUtil.isNotEmpty(zjTzThreeShareholderBill.getCompleteId())) {
					supBill.setCompleteId(zjTzThreeShareholderBill.getCompleteId());
				}
				List<ZjTzThreeSupervisorBill> supBillList = zjTzThreeSupervisorBillMapper.ureportZjTzThreeSupervisorBillList(supBill);
				if(supBillList != null && supBillList.size() >0) {
					supNum = supBillList.size();
				}
				int max = ((max=(shareNum>dirNum)?shareNum:dirNum)>supNum?max:supNum);
				if(max == shareNum) {
					sha = true;
				}else if(max == supNum) {
					sup = true;
				}else if(max == dirNum) {
					dir = true;
				}
				if(sha) {
					for (int i = 0; i < shareList.size(); i++) {
						if(dirBillList != null && dirBillList.size() >0 && i< dirBillList.size()) {
							shareList.get(i).setMeetNumberNameDir(dirBillList.get(i).getMeetNumberNameDir());
							shareList.get(i).setMeetDateDir(dirBillList.get(i).getMeetDateDir());
							shareList.get(i).setMeetPlaceDir(dirBillList.get(i).getMeetPlaceDir());
							shareList.get(i).setResultNameDir(dirBillList.get(i).getResultNameDir());
							shareList.get(i).setBillNameDir(dirBillList.get(i).getBillNameDir());
						}
						//
						if(supBillList != null && supBillList.size() >0 && i< supBillList.size()) {
							shareList.get(i).setMeetNumberNameSup(supBillList.get(i).getMeetNumberNameSup());
							shareList.get(i).setMeetDateSup(supBillList.get(i).getMeetDateSup());
							shareList.get(i).setMeetPlaceSup(supBillList.get(i).getMeetPlaceSup());
							shareList.get(i).setResultNameSup(supBillList.get(i).getResultNameSup());
							shareList.get(i).setBillNameSup(supBillList.get(i).getBillNameSup());
						}
						shaBillListAll.add(shareList.get(i));
					}
				}else if(sup) {
					for (int i = 0; i < supBillList.size(); i++) {
						if(dirBillList != null && dirBillList.size() >0 && i< dirBillList.size()) {
							supBillList.get(i).setMeetNumberNameDir(dirBillList.get(i).getMeetNumberNameDir());
							supBillList.get(i).setMeetDateDir(dirBillList.get(i).getMeetDateDir());
							supBillList.get(i).setMeetPlaceDir(dirBillList.get(i).getMeetPlaceDir());
							supBillList.get(i).setResultNameDir(dirBillList.get(i).getResultNameDir());
							supBillList.get(i).setBillNameDir(dirBillList.get(i).getBillNameDir());
						}
						//
						if(shareList != null && shareList.size() >0 && i< shareList.size()) {
							supBillList.get(i).setMeetNumberNameSha(shareList.get(i).getMeetNumberNameSha());
							supBillList.get(i).setMeetDateSha(shareList.get(i).getMeetDateSha());
							supBillList.get(i).setMeetPlaceSha(shareList.get(i).getMeetPlaceSha());
							supBillList.get(i).setResultNameSha(shareList.get(i).getResultNameSha());
							supBillList.get(i).setBillNameSha(shareList.get(i).getBillNameSha());
						}
						supBillListAll.add(supBillList.get(i));
					}
				}else if(dir) {
					for (int i = 0; i < dirBillList.size(); i++) {
						if(shareList != null && shareList.size() >0 &&  i< shareList.size()) {
							dirBillList.get(i).setMeetNumberNameSha(shareList.get(i).getMeetNumberNameSha());
							dirBillList.get(i).setMeetDateSha(shareList.get(i).getMeetDateSha());
							dirBillList.get(i).setMeetPlaceSha(shareList.get(i).getMeetPlaceSha());
							dirBillList.get(i).setResultNameSha(shareList.get(i).getResultNameSha());
							dirBillList.get(i).setBillNameSha(shareList.get(i).getBillNameSha());
						}
						//
						if(supBillList != null && supBillList.size() >0 && i < supBillList.size()) {
							dirBillList.get(i).setMeetNumberNameSup(supBillList.get(i).getMeetNumberNameSup());
							dirBillList.get(i).setMeetDateSup(supBillList.get(i).getMeetDateSup());
							dirBillList.get(i).setMeetPlaceSup(supBillList.get(i).getMeetPlaceSup());
							dirBillList.get(i).setResultNameSup(supBillList.get(i).getResultNameSup());
							dirBillList.get(i).setBillNameSup(supBillList.get(i).getBillNameSup());
						}
						dirBillListAll.add(dirBillList.get(i));
					}
				}
			}
		}else if(supProMax){
			for (ZjTzThreeSupervisorBill supervisor : supervisorBillProList) {
				ZjTzThreeShareholderBill shaBill = new ZjTzThreeShareholderBill();
				shaBill.setProjectName(supervisor.getProjectName());
				if(StrUtil.isNotEmpty(zjTzThreeShareholderBill.getCompleteId())) {
					shaBill.setCompleteId(zjTzThreeShareholderBill.getCompleteId());
				}
				shaBill.setProjectId(zjTzThreeShareholderBill.getProjectId());
				// 不是集团的人员时
		    	if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
		    		// 选择全部项目是，通过拼接的sql去查询
		    		if(StrUtil.equals("all", zjTzThreeShareholderBill.getProjectId(), true)) {
		    			shaBill.setProjectId("");
		    			shaBill.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
		    		}
		    	} else {
		    		// 集团人员时
		    		if(StrUtil.equals("all", zjTzThreeShareholderBill.getProjectId(), true)) {
		    			shaBill.setProjectId("");
		    		}
		    	}
				List<ZjTzThreeShareholderBill> shareList = zjTzThreeShareholderBillMapper.ureportZjTzThreeShareholderBillList(shaBill);
				if(shareList != null && shareList.size() >0) {
					shareNum = shareList.size();
				}
				ZjTzThreeDirectorBill  dirBill = new ZjTzThreeDirectorBill();
				dirBill.setProjectName(supervisor.getProjectName());
				if(StrUtil.isNotEmpty(zjTzThreeShareholderBill.getCompleteId())) {
					dirBill.setCompleteId(zjTzThreeShareholderBill.getCompleteId());
				}
				List<ZjTzThreeDirectorBill> dirBillList = zjTzThreeDirectorBillMapper.ureportZjTzThreeDirectorBillList(dirBill);
				if(dirBillList != null && dirBillList.size() >0) {
					dirNum = dirBillList.size();
				}
				ZjTzThreeSupervisorBill supBill = new ZjTzThreeSupervisorBill();
				supBill.setProjectName(supervisor.getProjectName());
				if(StrUtil.isNotEmpty(zjTzThreeShareholderBill.getCompleteId())) {
					supBill.setCompleteId(zjTzThreeShareholderBill.getCompleteId());
				}
				List<ZjTzThreeSupervisorBill> supBillList = zjTzThreeSupervisorBillMapper.ureportZjTzThreeSupervisorBillList(supBill);
				if(supBillList != null && supBillList.size() >0) {
					supNum = supBillList.size();
				}
				int max = ((max=(shareNum>dirNum)?shareNum:dirNum)>supNum?max:supNum);
				if(max == shareNum) {
					sha = true;
				}else if(max == supNum) {
					sup = true;
				}else if(max == dirNum) {
					dir = true;
				}
				if(sha) {
					for (int i = 0; i < shareList.size(); i++) {
						if(dirBillList != null && dirBillList.size() >0 && i< dirBillList.size()) {
							shareList.get(i).setMeetNumberNameDir(dirBillList.get(i).getMeetNumberNameDir());
							shareList.get(i).setMeetDateDir(dirBillList.get(i).getMeetDateDir());
							shareList.get(i).setMeetPlaceDir(dirBillList.get(i).getMeetPlaceDir());
							shareList.get(i).setResultNameDir(dirBillList.get(i).getResultNameDir());
							shareList.get(i).setBillNameDir(dirBillList.get(i).getBillNameDir());
						}
						//
						if(supBillList != null && supBillList.size() >0 && i< supBillList.size()) {
							shareList.get(i).setMeetNumberNameSup(supBillList.get(i).getMeetNumberNameSup());
							shareList.get(i).setMeetDateSup(supBillList.get(i).getMeetDateSup());
							shareList.get(i).setMeetPlaceSup(supBillList.get(i).getMeetPlaceSup());
							shareList.get(i).setResultNameSup(supBillList.get(i).getResultNameSup());
							shareList.get(i).setBillNameSup(supBillList.get(i).getBillNameSup());
						}
						shaBillListAll.add(shareList.get(i));
					}
				}else if(sup) {
					for (int i = 0; i < supBillList.size(); i++) {
						if(dirBillList != null && dirBillList.size() >0 && i< dirBillList.size()) {
							supBillList.get(i).setMeetNumberNameDir(dirBillList.get(i).getMeetNumberNameDir());
							supBillList.get(i).setMeetDateDir(dirBillList.get(i).getMeetDateDir());
							supBillList.get(i).setMeetPlaceDir(dirBillList.get(i).getMeetPlaceDir());
							supBillList.get(i).setResultNameDir(dirBillList.get(i).getResultNameDir());
							supBillList.get(i).setBillNameDir(dirBillList.get(i).getBillNameDir());
						}
						//
						if(shareList != null && shareList.size() >0 && i< shareList.size()) {
							supBillList.get(i).setMeetNumberNameSha(shareList.get(i).getMeetNumberNameSha());
							supBillList.get(i).setMeetDateSha(shareList.get(i).getMeetDateSha());
							supBillList.get(i).setMeetPlaceSha(shareList.get(i).getMeetPlaceSha());
							supBillList.get(i).setResultNameSha(shareList.get(i).getResultNameSha());
							supBillList.get(i).setBillNameSha(shareList.get(i).getBillNameSha());
						}
						supBillListAll.add(supBillList.get(i));
					}
				}else if(dir) {
					for (int i = 0; i < dirBillList.size(); i++) {
						if(shareList != null && shareList.size() >0 &&  i< shareList.size()) {
							dirBillList.get(i).setMeetNumberNameSha(shareList.get(i).getMeetNumberNameSha());
							dirBillList.get(i).setMeetDateSha(shareList.get(i).getMeetDateSha());
							dirBillList.get(i).setMeetPlaceSha(shareList.get(i).getMeetPlaceSha());
							dirBillList.get(i).setResultNameSha(shareList.get(i).getResultNameSha());
							dirBillList.get(i).setBillNameSha(shareList.get(i).getBillNameSha());
						}
						//
						if(supBillList != null && supBillList.size() >0 && i < supBillList.size()) {
							dirBillList.get(i).setMeetNumberNameSup(supBillList.get(i).getMeetNumberNameSup());
							dirBillList.get(i).setMeetDateSup(supBillList.get(i).getMeetDateSup());
							dirBillList.get(i).setMeetPlaceSup(supBillList.get(i).getMeetPlaceSup());
							dirBillList.get(i).setResultNameSup(supBillList.get(i).getResultNameSup());
							dirBillList.get(i).setBillNameSup(supBillList.get(i).getBillNameSup());
						}
						dirBillListAll.add(dirBillList.get(i));
					}
				}
			}
		}else if(dirProMax){
			for (ZjTzThreeDirectorBill director : directorBillProList) {
				ZjTzThreeShareholderBill shaBill = new ZjTzThreeShareholderBill();
				shaBill.setProjectName(director.getProjectName());
				if(StrUtil.isNotEmpty(zjTzThreeShareholderBill.getCompleteId())) {
					shaBill.setCompleteId(zjTzThreeShareholderBill.getCompleteId());
				}
				shaBill.setProjectId(zjTzThreeShareholderBill.getProjectId());
				// 不是集团的人员时
		    	if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
		    		// 选择全部项目是，通过拼接的sql去查询
		    		if(StrUtil.equals("all", zjTzThreeShareholderBill.getProjectId(), true)) {
		    			shaBill.setProjectId("");
		    			shaBill.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
		    		}
		    	} else {
		    		// 集团人员时
		    		if(StrUtil.equals("all", zjTzThreeShareholderBill.getProjectId(), true)) {
		    			shaBill.setProjectId("");
		    		}
		    	}
				List<ZjTzThreeShareholderBill> shareList = zjTzThreeShareholderBillMapper.ureportZjTzThreeShareholderBillList(shaBill);
				if(shareList != null && shareList.size() >0) {
					shareNum = shareList.size();
				}
				ZjTzThreeDirectorBill  dirBill = new ZjTzThreeDirectorBill();
				dirBill.setProjectName(director.getProjectName());
				if(StrUtil.isNotEmpty(zjTzThreeShareholderBill.getCompleteId())) {
					dirBill.setCompleteId(zjTzThreeShareholderBill.getCompleteId());
				}
				List<ZjTzThreeDirectorBill> dirBillList = zjTzThreeDirectorBillMapper.ureportZjTzThreeDirectorBillList(dirBill);
				if(dirBillList != null && dirBillList.size() >0) {
					dirNum = dirBillList.size();
				}
				ZjTzThreeSupervisorBill supBill = new ZjTzThreeSupervisorBill();
				supBill.setProjectName(director.getProjectName());
				if(StrUtil.isNotEmpty(zjTzThreeShareholderBill.getCompleteId())) {
					supBill.setCompleteId(zjTzThreeShareholderBill.getCompleteId());
				}
				List<ZjTzThreeSupervisorBill> supBillList = zjTzThreeSupervisorBillMapper.ureportZjTzThreeSupervisorBillList(supBill);
				if(supBillList != null && supBillList.size() >0) {
					supNum = supBillList.size();
				}
				int max = ((max=(shareNum>dirNum)?shareNum:dirNum)>supNum?max:supNum);
				if(max == shareNum) {
					sha = true;
				}else if(max == supNum) {
					sup = true;
				}else if(max == dirNum) {
					dir = true;
				}
				if(sha) {
					for (int i = 0; i < shareList.size(); i++) {
						if(dirBillList != null && dirBillList.size() >0 && i< dirBillList.size()) {
							shareList.get(i).setMeetNumberNameDir(dirBillList.get(i).getMeetNumberNameDir());
							shareList.get(i).setMeetDateDir(dirBillList.get(i).getMeetDateDir());
							shareList.get(i).setMeetPlaceDir(dirBillList.get(i).getMeetPlaceDir());
							shareList.get(i).setResultNameDir(dirBillList.get(i).getResultNameDir());
							shareList.get(i).setBillNameDir(dirBillList.get(i).getBillNameDir());
						}
						//
						if(supBillList != null && supBillList.size() >0 && i< supBillList.size()) {
							shareList.get(i).setMeetNumberNameSup(supBillList.get(i).getMeetNumberNameSup());
							shareList.get(i).setMeetDateSup(supBillList.get(i).getMeetDateSup());
							shareList.get(i).setMeetPlaceSup(supBillList.get(i).getMeetPlaceSup());
							shareList.get(i).setResultNameSup(supBillList.get(i).getResultNameSup());
							shareList.get(i).setBillNameSup(supBillList.get(i).getBillNameSup());
						}
						shaBillListAll.add(shareList.get(i));
					}
				}else if(sup) {
					for (int i = 0; i < supBillList.size(); i++) {
						if(dirBillList != null && dirBillList.size() >0 && i< dirBillList.size()) {
							supBillList.get(i).setMeetNumberNameDir(dirBillList.get(i).getMeetNumberNameDir());
							supBillList.get(i).setMeetDateDir(dirBillList.get(i).getMeetDateDir());
							supBillList.get(i).setMeetPlaceDir(dirBillList.get(i).getMeetPlaceDir());
							supBillList.get(i).setResultNameDir(dirBillList.get(i).getResultNameDir());
							supBillList.get(i).setBillNameDir(dirBillList.get(i).getBillNameDir());
						}
						//
						if(shareList != null && shareList.size() >0 && i< shareList.size()) {
							supBillList.get(i).setMeetNumberNameSha(shareList.get(i).getMeetNumberNameSha());
							supBillList.get(i).setMeetDateSha(shareList.get(i).getMeetDateSha());
							supBillList.get(i).setMeetPlaceSha(shareList.get(i).getMeetPlaceSha());
							supBillList.get(i).setResultNameSha(shareList.get(i).getResultNameSha());
							supBillList.get(i).setBillNameSha(shareList.get(i).getBillNameSha());
						}
						supBillListAll.add(supBillList.get(i));
					}
				}else if(dir) {
					for (int i = 0; i < dirBillList.size(); i++) {
						if(shareList != null && shareList.size() >0 &&  i< shareList.size()) {
							dirBillList.get(i).setMeetNumberNameSha(shareList.get(i).getMeetNumberNameSha());
							dirBillList.get(i).setMeetDateSha(shareList.get(i).getMeetDateSha());
							dirBillList.get(i).setMeetPlaceSha(shareList.get(i).getMeetPlaceSha());
							dirBillList.get(i).setResultNameSha(shareList.get(i).getResultNameSha());
							dirBillList.get(i).setBillNameSha(shareList.get(i).getBillNameSha());
						}
						//
						if(supBillList != null && supBillList.size() >0 && i < supBillList.size()) {
							dirBillList.get(i).setMeetNumberNameSup(supBillList.get(i).getMeetNumberNameSup());
							dirBillList.get(i).setMeetDateSup(supBillList.get(i).getMeetDateSup());
							dirBillList.get(i).setMeetPlaceSup(supBillList.get(i).getMeetPlaceSup());
							dirBillList.get(i).setResultNameSup(supBillList.get(i).getResultNameSup());
							dirBillList.get(i).setBillNameSup(supBillList.get(i).getBillNameSup());
						}
						dirBillListAll.add(dirBillList.get(i));
					}
				}
			}
		}
		returnZjTzThreeBillList.addAll(dirBillListAll);
		returnZjTzThreeBillList.addAll(supBillListAll);
		returnZjTzThreeBillList.addAll(shaBillListAll);
		 return repEntity.ok(returnZjTzThreeBillList);
	}

	@Override
	public List<ZjTzThreeShareholderBill> ureportZjTzThreeBillListFinish(ZjTzThreeShareholderBill zjTzThreeShareholderBill) {
	   	if (zjTzThreeShareholderBill == null) {
				zjTzThreeShareholderBill = new ZjTzThreeShareholderBill();
			}
			String userId = zjTzThreeShareholderBill.getUserId();
			String ext1 = zjTzThreeShareholderBill.getExt1();
			// 不是集团的人员时
			if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
				// 选择全部项目是，通过拼接的sql去查询
				if(StrUtil.equals("all", zjTzThreeShareholderBill.getProjectId(), true)) {
					zjTzThreeShareholderBill.setProjectId("");
					zjTzThreeShareholderBill.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
				}
			} else {
				// 集团人员时
				if(StrUtil.equals("all", zjTzThreeShareholderBill.getProjectId(), true)) {
					zjTzThreeShareholderBill.setProjectId("");
				}
			}
			
			
			
			
			//先判断哪儿会议项目数量最多
			int shaPro = 0;
			int dirPro = 0;
			int supPro = 0;
			boolean shaProMax = false;
			boolean dirProMax = false;
			boolean supProMax = false;
			ZjTzThreeShareholderBill shareholderBillPro = new ZjTzThreeShareholderBill();
			shareholderBillPro.setGroupByFlag("根据项目名称分组");
			if(StrUtil.isNotEmpty(zjTzThreeShareholderBill.getCompleteId())) {
				shareholderBillPro.setCompleteId(zjTzThreeShareholderBill.getCompleteId());
			}
			List<ZjTzThreeShareholderBill> shareholderBillProList = zjTzThreeShareholderBillMapper.ureportZjTzThreeShareholderBillList(shareholderBillPro);
			if(shareholderBillProList != null && shareholderBillProList.size() >0) {
				shaPro = shareholderBillProList.size();
			}
			ZjTzThreeDirectorBill  directorBillPro = new ZjTzThreeDirectorBill();
			directorBillPro.setGroupByFlag("根据项目名称分组");
			if(StrUtil.isNotEmpty(zjTzThreeShareholderBill.getCompleteId())) {
				directorBillPro.setCompleteId(zjTzThreeShareholderBill.getCompleteId());
			}
			List<ZjTzThreeDirectorBill> directorBillProList = zjTzThreeDirectorBillMapper.ureportZjTzThreeDirectorBillList(directorBillPro);
			if(directorBillProList != null && directorBillProList.size() >0) {
				dirPro = directorBillProList.size();
			}
			ZjTzThreeSupervisorBill supervisorBillPro = new ZjTzThreeSupervisorBill();
			supervisorBillPro.setGroupByFlag("根据项目名称分组");
			if(StrUtil.isNotEmpty(zjTzThreeShareholderBill.getCompleteId())) {
				supervisorBillPro.setCompleteId(zjTzThreeShareholderBill.getCompleteId());
			}
			List<ZjTzThreeSupervisorBill> supervisorBillProList = zjTzThreeSupervisorBillMapper.ureportZjTzThreeSupervisorBillList(supervisorBillPro);
			if(supervisorBillProList != null && supervisorBillProList.size() >0) {
				supPro = supervisorBillProList.size();
			}
			int maxPro = ((maxPro=(shaPro>dirPro)?shaPro:dirPro)>supPro?maxPro:supPro);
			if(maxPro == shaPro) {
				shaProMax = true;
			}else if(maxPro == supPro) {
				supProMax = true;
			}else if(maxPro == dirPro) {
				dirProMax = true;
			}
			//
			List returnZjTzThreeBillList = new ArrayList<>();
			List<ZjTzThreeShareholderBill> shaBillListAll = new ArrayList<>();
			List<ZjTzThreeSupervisorBill> supBillListAll = new ArrayList<>();
			List<ZjTzThreeDirectorBill> dirBillListAll = new ArrayList<>();
			//再判断相同项目下哪儿会议最多
			int shareNum = 0;
			int supNum = 0;
			int dirNum = 0;
			boolean sha = false;
			boolean sup = false;
			boolean dir = false;
			//
			if(shaProMax) {
				for (ZjTzThreeShareholderBill share : shareholderBillProList) {
					ZjTzThreeShareholderBill shaBill = new ZjTzThreeShareholderBill();
					shaBill.setProjectName(share.getProjectName());
					if(StrUtil.isNotEmpty(zjTzThreeShareholderBill.getCompleteId())) {
						shaBill.setCompleteId(zjTzThreeShareholderBill.getCompleteId());
					}
					List<ZjTzThreeShareholderBill> shareList = zjTzThreeShareholderBillMapper.ureportZjTzThreeShareholderBillList(shaBill);
					if(shareList != null && shareList.size() >0) {
						shareNum = shareList.size();
					}
					ZjTzThreeDirectorBill  dirBill = new ZjTzThreeDirectorBill();
					dirBill.setProjectName(share.getProjectName());
					if(StrUtil.isNotEmpty(zjTzThreeShareholderBill.getCompleteId())) {
						dirBill.setCompleteId(zjTzThreeShareholderBill.getCompleteId());
					}
					List<ZjTzThreeDirectorBill> dirBillList = zjTzThreeDirectorBillMapper.ureportZjTzThreeDirectorBillList(dirBill);
					if(dirBillList != null && dirBillList.size() >0) {
						dirNum = dirBillList.size();
					}
					ZjTzThreeSupervisorBill supBill = new ZjTzThreeSupervisorBill();
					supBill.setProjectName(share.getProjectName());
					if(StrUtil.isNotEmpty(zjTzThreeShareholderBill.getCompleteId())) {
						supBill.setCompleteId(zjTzThreeShareholderBill.getCompleteId());
					}
					List<ZjTzThreeSupervisorBill> supBillList = zjTzThreeSupervisorBillMapper.ureportZjTzThreeSupervisorBillList(supBill);
					if(supBillList != null && supBillList.size() >0) {
						supNum = supBillList.size();
					}
					int max = ((max=(shareNum>dirNum)?shareNum:dirNum)>supNum?max:supNum);
					if(max == shareNum) {
						sha = true;
					}else if(max == supNum) {
						sup = true;
					}else if(max == dirNum) {
						dir = true;
					}
					if(sha) {
						for (int i = 0; i < shareList.size(); i++) {
							if(dirBillList != null && dirBillList.size() >0 && i< dirBillList.size()) {
								shareList.get(i).setMeetNumberNameDir(dirBillList.get(i).getMeetNumberNameDir());
								shareList.get(i).setMeetDateDir(dirBillList.get(i).getMeetDateDir());
								shareList.get(i).setMeetPlaceDir(dirBillList.get(i).getMeetPlaceDir());
								shareList.get(i).setResultNameDir(dirBillList.get(i).getResultNameDir());
								shareList.get(i).setBillNameDir(dirBillList.get(i).getBillNameDir());
							}
							//
							if(supBillList != null && supBillList.size() >0 && i< supBillList.size()) {
								shareList.get(i).setMeetNumberNameSup(supBillList.get(i).getMeetNumberNameSup());
								shareList.get(i).setMeetDateSup(supBillList.get(i).getMeetDateSup());
								shareList.get(i).setMeetPlaceSup(supBillList.get(i).getMeetPlaceSup());
								shareList.get(i).setResultNameSup(supBillList.get(i).getResultNameSup());
								shareList.get(i).setBillNameSup(supBillList.get(i).getBillNameSup());
							}
							shaBillListAll.add(shareList.get(i));
						}
					}else if(sup) {
						for (int i = 0; i < supBillList.size(); i++) {
							if(dirBillList != null && dirBillList.size() >0 && i< dirBillList.size()) {
								supBillList.get(i).setMeetNumberNameDir(dirBillList.get(i).getMeetNumberNameDir());
								supBillList.get(i).setMeetDateDir(dirBillList.get(i).getMeetDateDir());
								supBillList.get(i).setMeetPlaceDir(dirBillList.get(i).getMeetPlaceDir());
								supBillList.get(i).setResultNameDir(dirBillList.get(i).getResultNameDir());
								supBillList.get(i).setBillNameDir(dirBillList.get(i).getBillNameDir());
							}
							//
							if(shareList != null && shareList.size() >0 && i< shareList.size()) {
								supBillList.get(i).setMeetNumberNameSha(shareList.get(i).getMeetNumberNameSha());
								supBillList.get(i).setMeetDateSha(shareList.get(i).getMeetDateSha());
								supBillList.get(i).setMeetPlaceSha(shareList.get(i).getMeetPlaceSha());
								supBillList.get(i).setResultNameSha(shareList.get(i).getResultNameSha());
								supBillList.get(i).setBillNameSha(shareList.get(i).getBillNameSha());
							}
							supBillListAll.add(supBillList.get(i));
						}
					}else if(dir) {
						for (int i = 0; i < dirBillList.size(); i++) {
							if(shareList != null && shareList.size() >0 &&  i< shareList.size()) {
								dirBillList.get(i).setMeetNumberNameSha(shareList.get(i).getMeetNumberNameSha());
								dirBillList.get(i).setMeetDateSha(shareList.get(i).getMeetDateSha());
								dirBillList.get(i).setMeetPlaceSha(shareList.get(i).getMeetPlaceSha());
								dirBillList.get(i).setResultNameSha(shareList.get(i).getResultNameSha());
								dirBillList.get(i).setBillNameSha(shareList.get(i).getBillNameSha());
							}
							//
							if(supBillList != null && supBillList.size() >0 && i < supBillList.size()) {
								dirBillList.get(i).setMeetNumberNameSup(supBillList.get(i).getMeetNumberNameSup());
								dirBillList.get(i).setMeetDateSup(supBillList.get(i).getMeetDateSup());
								dirBillList.get(i).setMeetPlaceSup(supBillList.get(i).getMeetPlaceSup());
								dirBillList.get(i).setResultNameSup(supBillList.get(i).getResultNameSup());
								dirBillList.get(i).setBillNameSup(supBillList.get(i).getBillNameSup());
							}
							dirBillListAll.add(dirBillList.get(i));
						}
					}
				}
			}else if(supProMax){
				for (ZjTzThreeSupervisorBill supervisor : supervisorBillProList) {
					ZjTzThreeShareholderBill shaBill = new ZjTzThreeShareholderBill();
					shaBill.setProjectName(supervisor.getProjectName());
					if(StrUtil.isNotEmpty(zjTzThreeShareholderBill.getCompleteId())) {
						shaBill.setCompleteId(zjTzThreeShareholderBill.getCompleteId());
					}
					List<ZjTzThreeShareholderBill> shareList = zjTzThreeShareholderBillMapper.ureportZjTzThreeShareholderBillList(shaBill);
					if(shareList != null && shareList.size() >0) {
						shareNum = shareList.size();
					}
					ZjTzThreeDirectorBill  dirBill = new ZjTzThreeDirectorBill();
					dirBill.setProjectName(supervisor.getProjectName());
					if(StrUtil.isNotEmpty(zjTzThreeShareholderBill.getCompleteId())) {
						dirBill.setCompleteId(zjTzThreeShareholderBill.getCompleteId());
					}
					List<ZjTzThreeDirectorBill> dirBillList = zjTzThreeDirectorBillMapper.ureportZjTzThreeDirectorBillList(dirBill);
					if(dirBillList != null && dirBillList.size() >0) {
						dirNum = dirBillList.size();
					}
					ZjTzThreeSupervisorBill supBill = new ZjTzThreeSupervisorBill();
					supBill.setProjectName(supervisor.getProjectName());
					if(StrUtil.isNotEmpty(zjTzThreeShareholderBill.getCompleteId())) {
						supBill.setCompleteId(zjTzThreeShareholderBill.getCompleteId());
					}
					List<ZjTzThreeSupervisorBill> supBillList = zjTzThreeSupervisorBillMapper.ureportZjTzThreeSupervisorBillList(supBill);
					if(supBillList != null && supBillList.size() >0) {
						supNum = supBillList.size();
					}
					int max = ((max=(shareNum>dirNum)?shareNum:dirNum)>supNum?max:supNum);
					if(max == shareNum) {
						sha = true;
					}else if(max == supNum) {
						sup = true;
					}else if(max == dirNum) {
						dir = true;
					}
					if(sha) {
						for (int i = 0; i < shareList.size(); i++) {
							if(dirBillList != null && dirBillList.size() >0 && i< dirBillList.size()) {
								shareList.get(i).setMeetNumberNameDir(dirBillList.get(i).getMeetNumberNameDir());
								shareList.get(i).setMeetDateDir(dirBillList.get(i).getMeetDateDir());
								shareList.get(i).setMeetPlaceDir(dirBillList.get(i).getMeetPlaceDir());
								shareList.get(i).setResultNameDir(dirBillList.get(i).getResultNameDir());
								shareList.get(i).setBillNameDir(dirBillList.get(i).getBillNameDir());
							}
							//
							if(supBillList != null && supBillList.size() >0 && i< supBillList.size()) {
								shareList.get(i).setMeetNumberNameSup(supBillList.get(i).getMeetNumberNameSup());
								shareList.get(i).setMeetDateSup(supBillList.get(i).getMeetDateSup());
								shareList.get(i).setMeetPlaceSup(supBillList.get(i).getMeetPlaceSup());
								shareList.get(i).setResultNameSup(supBillList.get(i).getResultNameSup());
								shareList.get(i).setBillNameSup(supBillList.get(i).getBillNameSup());
							}
							shaBillListAll.add(shareList.get(i));
						}
					}else if(sup) {
						for (int i = 0; i < supBillList.size(); i++) {
							if(dirBillList != null && dirBillList.size() >0 && i< dirBillList.size()) {
								supBillList.get(i).setMeetNumberNameDir(dirBillList.get(i).getMeetNumberNameDir());
								supBillList.get(i).setMeetDateDir(dirBillList.get(i).getMeetDateDir());
								supBillList.get(i).setMeetPlaceDir(dirBillList.get(i).getMeetPlaceDir());
								supBillList.get(i).setResultNameDir(dirBillList.get(i).getResultNameDir());
								supBillList.get(i).setBillNameDir(dirBillList.get(i).getBillNameDir());
							}
							//
							if(shareList != null && shareList.size() >0 && i< shareList.size()) {
								supBillList.get(i).setMeetNumberNameSha(shareList.get(i).getMeetNumberNameSha());
								supBillList.get(i).setMeetDateSha(shareList.get(i).getMeetDateSha());
								supBillList.get(i).setMeetPlaceSha(shareList.get(i).getMeetPlaceSha());
								supBillList.get(i).setResultNameSha(shareList.get(i).getResultNameSha());
								supBillList.get(i).setBillNameSha(shareList.get(i).getBillNameSha());
							}
							supBillListAll.add(supBillList.get(i));
						}
					}else if(dir) {
						for (int i = 0; i < dirBillList.size(); i++) {
							if(shareList != null && shareList.size() >0 &&  i< shareList.size()) {
								dirBillList.get(i).setMeetNumberNameSha(shareList.get(i).getMeetNumberNameSha());
								dirBillList.get(i).setMeetDateSha(shareList.get(i).getMeetDateSha());
								dirBillList.get(i).setMeetPlaceSha(shareList.get(i).getMeetPlaceSha());
								dirBillList.get(i).setResultNameSha(shareList.get(i).getResultNameSha());
								dirBillList.get(i).setBillNameSha(shareList.get(i).getBillNameSha());
							}
							//
							if(supBillList != null && supBillList.size() >0 && i < supBillList.size()) {
								dirBillList.get(i).setMeetNumberNameSup(supBillList.get(i).getMeetNumberNameSup());
								dirBillList.get(i).setMeetDateSup(supBillList.get(i).getMeetDateSup());
								dirBillList.get(i).setMeetPlaceSup(supBillList.get(i).getMeetPlaceSup());
								dirBillList.get(i).setResultNameSup(supBillList.get(i).getResultNameSup());
								dirBillList.get(i).setBillNameSup(supBillList.get(i).getBillNameSup());
							}
							dirBillListAll.add(dirBillList.get(i));
						}
					}
				}
			}else if(dirProMax){
				for (ZjTzThreeDirectorBill director : directorBillProList) {
					ZjTzThreeShareholderBill shaBill = new ZjTzThreeShareholderBill();
					shaBill.setProjectName(director.getProjectName());
					if(StrUtil.isNotEmpty(zjTzThreeShareholderBill.getCompleteId())) {
						shaBill.setCompleteId(zjTzThreeShareholderBill.getCompleteId());
					}
					List<ZjTzThreeShareholderBill> shareList = zjTzThreeShareholderBillMapper.ureportZjTzThreeShareholderBillList(shaBill);
					if(shareList != null && shareList.size() >0) {
						shareNum = shareList.size();
					}
					ZjTzThreeDirectorBill  dirBill = new ZjTzThreeDirectorBill();
					dirBill.setProjectName(director.getProjectName());
					if(StrUtil.isNotEmpty(zjTzThreeShareholderBill.getCompleteId())) {
						dirBill.setCompleteId(zjTzThreeShareholderBill.getCompleteId());
					}
					List<ZjTzThreeDirectorBill> dirBillList = zjTzThreeDirectorBillMapper.ureportZjTzThreeDirectorBillList(dirBill);
					if(dirBillList != null && dirBillList.size() >0) {
						dirNum = dirBillList.size();
					}
					ZjTzThreeSupervisorBill supBill = new ZjTzThreeSupervisorBill();
					supBill.setProjectName(director.getProjectName());
					if(StrUtil.isNotEmpty(zjTzThreeShareholderBill.getCompleteId())) {
						supBill.setCompleteId(zjTzThreeShareholderBill.getCompleteId());
					}
					List<ZjTzThreeSupervisorBill> supBillList = zjTzThreeSupervisorBillMapper.ureportZjTzThreeSupervisorBillList(supBill);
					if(supBillList != null && supBillList.size() >0) {
						supNum = supBillList.size();
					}
					int max = ((max=(shareNum>dirNum)?shareNum:dirNum)>supNum?max:supNum);
					if(max == shareNum) {
						sha = true;
					}else if(max == supNum) {
						sup = true;
					}else if(max == dirNum) {
						dir = true;
					}
					if(sha) {
						for (int i = 0; i < shareList.size(); i++) {
							if(dirBillList != null && dirBillList.size() >0 && i< dirBillList.size()) {
								shareList.get(i).setMeetNumberNameDir(dirBillList.get(i).getMeetNumberNameDir());
								shareList.get(i).setMeetDateDir(dirBillList.get(i).getMeetDateDir());
								shareList.get(i).setMeetPlaceDir(dirBillList.get(i).getMeetPlaceDir());
								shareList.get(i).setResultNameDir(dirBillList.get(i).getResultNameDir());
								shareList.get(i).setBillNameDir(dirBillList.get(i).getBillNameDir());
							}
							//
							if(supBillList != null && supBillList.size() >0 && i< supBillList.size()) {
								shareList.get(i).setMeetNumberNameSup(supBillList.get(i).getMeetNumberNameSup());
								shareList.get(i).setMeetDateSup(supBillList.get(i).getMeetDateSup());
								shareList.get(i).setMeetPlaceSup(supBillList.get(i).getMeetPlaceSup());
								shareList.get(i).setResultNameSup(supBillList.get(i).getResultNameSup());
								shareList.get(i).setBillNameSup(supBillList.get(i).getBillNameSup());
							}
							shaBillListAll.add(shareList.get(i));
						}
					}else if(sup) {
						for (int i = 0; i < supBillList.size(); i++) {
							if(dirBillList != null && dirBillList.size() >0 && i< dirBillList.size()) {
								supBillList.get(i).setMeetNumberNameDir(dirBillList.get(i).getMeetNumberNameDir());
								supBillList.get(i).setMeetDateDir(dirBillList.get(i).getMeetDateDir());
								supBillList.get(i).setMeetPlaceDir(dirBillList.get(i).getMeetPlaceDir());
								supBillList.get(i).setResultNameDir(dirBillList.get(i).getResultNameDir());
								supBillList.get(i).setBillNameDir(dirBillList.get(i).getBillNameDir());
							}
							//
							if(shareList != null && shareList.size() >0 && i< shareList.size()) {
								supBillList.get(i).setMeetNumberNameSha(shareList.get(i).getMeetNumberNameSha());
								supBillList.get(i).setMeetDateSha(shareList.get(i).getMeetDateSha());
								supBillList.get(i).setMeetPlaceSha(shareList.get(i).getMeetPlaceSha());
								supBillList.get(i).setResultNameSha(shareList.get(i).getResultNameSha());
								supBillList.get(i).setBillNameSha(shareList.get(i).getBillNameSha());
							}
							supBillListAll.add(supBillList.get(i));
						}
					}else if(dir) {
						for (int i = 0; i < dirBillList.size(); i++) {
							if(shareList != null && shareList.size() >0 &&  i< shareList.size()) {
								dirBillList.get(i).setMeetNumberNameSha(shareList.get(i).getMeetNumberNameSha());
								dirBillList.get(i).setMeetDateSha(shareList.get(i).getMeetDateSha());
								dirBillList.get(i).setMeetPlaceSha(shareList.get(i).getMeetPlaceSha());
								dirBillList.get(i).setResultNameSha(shareList.get(i).getResultNameSha());
								dirBillList.get(i).setBillNameSha(shareList.get(i).getBillNameSha());
							}
							//
							if(supBillList != null && supBillList.size() >0 && i < supBillList.size()) {
								dirBillList.get(i).setMeetNumberNameSup(supBillList.get(i).getMeetNumberNameSup());
								dirBillList.get(i).setMeetDateSup(supBillList.get(i).getMeetDateSup());
								dirBillList.get(i).setMeetPlaceSup(supBillList.get(i).getMeetPlaceSup());
								dirBillList.get(i).setResultNameSup(supBillList.get(i).getResultNameSup());
								dirBillList.get(i).setBillNameSup(supBillList.get(i).getBillNameSup());
							}
							dirBillListAll.add(dirBillList.get(i));
						}
					}
				}
			}
		returnZjTzThreeBillList.addAll(dirBillListAll);
		returnZjTzThreeBillList.addAll(supBillListAll);
		returnZjTzThreeBillList.addAll(shaBillListAll);
		
		return returnZjTzThreeBillList;
	}

	
}
