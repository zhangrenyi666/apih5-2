package com.apih5.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzComplianceDealMapper;
import com.apih5.mybatis.dao.ZjTzComplianceDetailMapper;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.pojo.ZjTzComplianceDeal;
import com.apih5.mybatis.pojo.ZjTzComplianceDetail;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.service.ZjTzComplianceDetailService;
import com.apih5.service.ZjTzPermissionService;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import cn.hutool.poi.excel.cell.CellUtil;

@Service("zjTzComplianceDetailService")
public class ZjTzComplianceDetailServiceImpl implements ZjTzComplianceDetailService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzComplianceDetailMapper zjTzComplianceDetailMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;
    
    @Autowired(required = true)
    private ZjTzComplianceDealMapper zjTzComplianceDealMapper;
    
    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;

    @Override
    public ResponseEntity getZjTzComplianceDetailListByCondition(ZjTzComplianceDetail zjTzComplianceDetail) {
        if (zjTzComplianceDetail == null) {
            zjTzComplianceDetail = new ZjTzComplianceDetail();
        }
        if(StrUtil.isEmpty(zjTzComplianceDetail.getComplianceDealId())) {
    		return repEntity.okList(null, 0);
    	}
        
        List<ZjTzComplianceDetail> returnList = new ArrayList<>();
        
        // 分页查询
        PageHelper.startPage(zjTzComplianceDetail.getPage(),zjTzComplianceDetail.getLimit());
        // 获取数据
        List<ZjTzComplianceDetail> zjTzComplianceDetailList = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(zjTzComplianceDetail);
        
        for (ZjTzComplianceDetail zjTzComplianceDetail2 : zjTzComplianceDetailList) {

//列表页面列出前7个步骤进行预警，设置预警规则，==预警时间根据当前日期和应办理完成日期进行对比
            //绿色表示正常进行中， (当前时间25小于应办理完成29 )
            //黄色表示距离办理时间还有15天，(当前时间距离应办理时间还有15天)
            //红色表示应办理完成日期超出时间规定。(当前时间大于应办理时间)
        	
//        	办理情况 分为三种状态0 未启动，1办理中，2已完成
//        	当处于未启动和办理中状态时，且【应办理完成日期】-【今日日期】≤15天时，为黄色预警
//        	当处于未启动和办理中状态时，且【应办理完成日期】-【今日日期】＜0天时，为红色预警
//        	当处于已完成状态时，预警灯为绿色
//        	以上三种情况之外均为绿色
        	
//        	if(StrUtil.equals(zjTzComplianceDetail2.getDealSituation(), "0") || StrUtil.equals(zjTzComplianceDetail2.getDealSituation(), "1")) {
//        		if(zjTzComplianceDetail2.getShouldFinishDate() != null) {
//        			if(DateUtil.between(zjTzComplianceDetail2.getShouldFinishDate(), new Date(), DateUnit.DAY)<=15) {
//            			zjTzComplianceDetail2.setColourFlag("yellow");
//             		}else if(DateUtil.compare(zjTzComplianceDetail2.getShouldFinishDate(),new Date()) <0) {
//            			zjTzComplianceDetail2.setColourFlag("red");
//             		}else {
//             			zjTzComplianceDetail2.setColourFlag("green");
//             		}
//            	}else {
//            		zjTzComplianceDetail2.setColourFlag("green");
//            	}
//        	}else {	
//        		zjTzComplianceDetail2.setColourFlag("green");
//        	}
        	//制度时限预警
        	if (StrUtil.isNotEmpty(zjTzComplianceDetail2.getDealSituation())) {
				if (StrUtil.equals(zjTzComplianceDetail2.getDealSituation(), "0") || StrUtil.equals(zjTzComplianceDetail2.getDealSituation(), "1")) {
					if (zjTzComplianceDetail2.getShouldFinishDate() != null) {
						if (zjTzComplianceDetail2.getShouldFinishDate().compareTo(new Date()) > 0 && DateUtil.between(new Date(), zjTzComplianceDetail2.getShouldFinishDate(), DateUnit.DAY) >= 15) {
							zjTzComplianceDetail2.setColourFlag("green");
						}else if (zjTzComplianceDetail2.getShouldFinishDate().compareTo(new Date()) >= 0 && DateUtil.between(new Date(), zjTzComplianceDetail2.getShouldFinishDate(), DateUnit.DAY) >= 0 && DateUtil.between(new Date(), zjTzComplianceDetail2.getShouldFinishDate(), DateUnit.DAY) < 15) {
							zjTzComplianceDetail2.setColourFlag("yellow");
						}else if (zjTzComplianceDetail2.getShouldFinishDate().compareTo(new Date()) < 0) {
							zjTzComplianceDetail2.setColourFlag("red");
						}
					}else {
							zjTzComplianceDetail2.setColourFlag("red");
					}
				}else {
					zjTzComplianceDetail2.setColourFlag("green");
				}
			}else {
				if (StrUtil.equals(zjTzComplianceDetail2.getNum(), "008")) {
					zjTzComplianceDetail2.setColourFlag("red");
				}else {
					if (zjTzComplianceDetail2.getShouldFinishDate() != null) {
						zjTzComplianceDetail2.setColourFlag("red");
					}else {
						zjTzComplianceDetail2.setColourFlag("green");
					}
				}
			}
        	//策划时限预警
        	if (StrUtil.isNotEmpty(zjTzComplianceDetail2.getDealSituation())) {
				if (StrUtil.equals(zjTzComplianceDetail2.getDealSituation(), "0") || StrUtil.equals(zjTzComplianceDetail2.getDealSituation(), "1")) {
					if (zjTzComplianceDetail2.getApprovalShouldFinishDate() != null) {
						if (zjTzComplianceDetail2.getApprovalShouldFinishDate().compareTo(new Date()) > 0 && DateUtil.between(new Date(), zjTzComplianceDetail2.getApprovalShouldFinishDate(), DateUnit.DAY) >= 15) {
							zjTzComplianceDetail2.setColourFlag1("green");
						}else if (zjTzComplianceDetail2.getApprovalShouldFinishDate().compareTo(new Date()) >= 0 && DateUtil.between(new Date(), zjTzComplianceDetail2.getApprovalShouldFinishDate(), DateUnit.DAY) >= 0 && DateUtil.between(new Date(), zjTzComplianceDetail2.getApprovalShouldFinishDate(), DateUnit.DAY) < 15) {
							zjTzComplianceDetail2.setColourFlag1("yellow");
						}else if (zjTzComplianceDetail2.getApprovalShouldFinishDate().compareTo(new Date()) < 0) {
							zjTzComplianceDetail2.setColourFlag1("red");
						}
					}else {
							zjTzComplianceDetail2.setColourFlag1("red");
					}
				}else {
					zjTzComplianceDetail2.setColourFlag1("green");
				}
			}else {
						zjTzComplianceDetail2.setColourFlag1("red");
			}
        	
        	ZjTzFile file = new ZjTzFile();
            file.setOtherId(zjTzComplianceDetail2.getComplianceDetailId());
            List<ZjTzFile> files = zjTzFileMapper.selectByZjTzFileList(file);
            zjTzComplianceDetail2.setZjTzFileList(files);
            
        }
        //环评批复固定在第八条
//        ZjTzComplianceDetail dbzjTzComplianceDetail = zjTzComplianceDetailList.stream().filter(detail -> StrUtil.equals(detail.getComplianceBanseName(), "环评批复")).findFirst().get();
//   		zjTzComplianceDetailList.remove(dbzjTzComplianceDetail);
//    	zjTzComplianceDetailList.add(7, dbzjTzComplianceDetail);
        
        ZjTzComplianceDetail detail = new ZjTzComplianceDetail();
        detail.setComplianceDealId(zjTzComplianceDetail.getComplianceDealId());
        detail.setCompanyName(zjTzComplianceDetailList.get(0).getCompanyName());
        detail.setSubprojectName(zjTzComplianceDetailList.get(0).getSubprojectName());
        detail.setProjectShortName(zjTzComplianceDetailList.get(0).getProjectShortName());
        detail.setProjectName(zjTzComplianceDetailList.get(0).getProjectName());
        returnList.add(detail);
        returnList.get(0).setZjTzComplianceDetailList(zjTzComplianceDetailList);
        return repEntity.ok(returnList);
    }

    @Override
    public ResponseEntity getZjTzComplianceDetailDetails(ZjTzComplianceDetail zjTzComplianceDetail) {
        if (zjTzComplianceDetail == null) {
            zjTzComplianceDetail = new ZjTzComplianceDetail();
        }
        // 获取数据
        ZjTzComplianceDetail dbZjTzComplianceDetail = zjTzComplianceDetailMapper.selectByPrimaryKey(zjTzComplianceDetail.getComplianceDetailId());
        // 数据存在
        if (dbZjTzComplianceDetail != null) {
            return repEntity.ok(dbZjTzComplianceDetail);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzComplianceDetail(ZjTzComplianceDetail zjTzComplianceDetail) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzComplianceDetail.setComplianceDetailId(UuidUtil.generate());
        zjTzComplianceDetail.setCreateUserInfo(userKey, realName);
        int flag = zjTzComplianceDetailMapper.insert(zjTzComplianceDetail);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzComplianceDetail);
        }
    }

    @Override
    public ResponseEntity updateZjTzComplianceDetail(ZjTzComplianceDetail zjTzComplianceDetail) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzComplianceDetail dbzjTzComplianceDetail = zjTzComplianceDetailMapper.selectByPrimaryKey(zjTzComplianceDetail.getComplianceDetailId());
        if (dbzjTzComplianceDetail != null && StrUtil.isNotEmpty(dbzjTzComplianceDetail.getComplianceDetailId())) {
           // 办理id
           dbzjTzComplianceDetail.setComplianceDealId(zjTzComplianceDetail.getComplianceDealId());
           // 环节编号
           dbzjTzComplianceDetail.setNum(zjTzComplianceDetail.getNum());
           // 合规环节名称
           dbzjTzComplianceDetail.setComplianceBanseName(zjTzComplianceDetail.getComplianceBanseName());
           //项目公司成立时应办理环节
           dbzjTzComplianceDetail.setDealFlag(zjTzComplianceDetail.getDealFlag());
           // 应办理完成日期
           dbzjTzComplianceDetail.setShouldFinishDate(zjTzComplianceDetail.getShouldFinishDate());
           // 文件批复日期
           dbzjTzComplianceDetail.setApprovalDate(zjTzComplianceDetail.getApprovalDate());
           // 办理情况
           dbzjTzComplianceDetail.setDealSituation(zjTzComplianceDetail.getDealSituation());
           // 是否锁定
           dbzjTzComplianceDetail.setLockFlag(zjTzComplianceDetail.getLockFlag());
           // 是否是新增的数据
           dbzjTzComplianceDetail.setAddFlag(zjTzComplianceDetail.getAddFlag());
           //策划批复应办理完结日期
           dbzjTzComplianceDetail.setApprovalShouldFinishDate(zjTzComplianceDetail.getApprovalShouldFinishDate());
           //备注
           dbzjTzComplianceDetail.setBz(zjTzComplianceDetail.getBz());
           // 共通
           dbzjTzComplianceDetail.setModifyUserInfo(userKey, realName);
           flag = zjTzComplianceDetailMapper.updateByPrimaryKey(dbzjTzComplianceDetail);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzComplianceDetail);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzComplianceDetail(List<ZjTzComplianceDetail> zjTzComplianceDetailList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzComplianceDetailList != null && zjTzComplianceDetailList.size() > 0) {
           ZjTzComplianceDetail zjTzComplianceDetail = new ZjTzComplianceDetail();
           zjTzComplianceDetail.setModifyUserInfo(userKey, realName);
           flag = zjTzComplianceDetailMapper.batchDeleteUpdateZjTzComplianceDetail(zjTzComplianceDetailList, zjTzComplianceDetail);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzComplianceDetailList);
        }
    }

    @Override
	public ResponseEntity getZjTzComplianceDetailListForReport(ZjTzComplianceDetail zjTzComplianceDetail) {
    	if (zjTzComplianceDetail == null) {
    		zjTzComplianceDetail = new ZjTzComplianceDetail();
    	}
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userId = TokenUtils.getUserId(request);
    	String ext1 = TokenUtils.getExt1(request);
    	// 不是集团的人员时
    	if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
    		// 选择全部项目是，通过拼接的sql去查询
    		if(StrUtil.equals("all", zjTzComplianceDetail.getProjectId(), true)) {
    			zjTzComplianceDetail.setProjectId("");
    			zjTzComplianceDetail.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
    		}
    	} else {
    		// 集团人员时
    		if(StrUtil.equals("all", zjTzComplianceDetail.getProjectId(), true)) {
    			zjTzComplianceDetail.setProjectId("");
    		}
    	}
    	// 获取数据
    	List<ZjTzComplianceDetail> zjTzComplianceDetailList = zjTzComplianceDetailMapper.uReportZjTzComplianceDetailList(zjTzComplianceDetail);
    	return repEntity.ok(zjTzComplianceDetailList);
	}
    
	@Override
	public List<ZjTzComplianceDetail> uReportZjTzComplianceDetailList(ZjTzComplianceDetail zjTzComplianceDetail) {
		if (zjTzComplianceDetail == null) {
			zjTzComplianceDetail = new ZjTzComplianceDetail();
		}
		String userId = zjTzComplianceDetail.getUserId();
		String ext1 = zjTzComplianceDetail.getExt1();
		// 不是集团的人员时
		if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
			// 选择全部项目是，通过拼接的sql去查询
			if(StrUtil.equals("all", zjTzComplianceDetail.getProjectId(), true)) {
				zjTzComplianceDetail.setProjectId("");
				zjTzComplianceDetail.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
			}
		} else {
			// 集团人员时
			if(StrUtil.equals("all", zjTzComplianceDetail.getProjectId(), true)) {
				zjTzComplianceDetail.setProjectId("");
			}
		}
		// 获取数据
		List<ZjTzComplianceDetail> zjTzComplianceDetailList = zjTzComplianceDetailMapper.uReportZjTzComplianceDetailList(zjTzComplianceDetail);
		return zjTzComplianceDetailList;
	}

	@Override
	public ResponseEntity exportZjTzComplianceDetailList(ZjTzComplianceDetail zjTzComplianceDetail, HttpServletResponse response) {
		if (zjTzComplianceDetail == null) {
			zjTzComplianceDetail = new ZjTzComplianceDetail();
		}
		String userId = zjTzComplianceDetail.getUserId();
		String ext1 = zjTzComplianceDetail.getExt1();
		// 不是集团的人员时
		if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
			// 选择全部项目是，通过拼接的sql去查询
			if(StrUtil.equals("all", zjTzComplianceDetail.getProjectId(), true)) {
				zjTzComplianceDetail.setProjectId("");
				zjTzComplianceDetail.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
			}
		} else {
			// 集团人员时
			if(StrUtil.equals("all", zjTzComplianceDetail.getProjectId(), true)) {
				zjTzComplianceDetail.setProjectId("");
			}
		}
		
		// 获取数据
        List<ZjTzComplianceDetail> zjTzComplianceDetailList = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(zjTzComplianceDetail);
        ZjTzComplianceDeal dbZjTzComplianceDeal = zjTzComplianceDealMapper.selectByPrimaryKey(zjTzComplianceDetail.getComplianceDealId());
        ZjTzProManage dbZjTzProManage = zjTzProManageMapper.selectByPrimaryKey(dbZjTzComplianceDeal.getProjectId());
        // 表头
        List<List<?>> rowsList = Lists.newArrayList();
//        List<?> row1 = CollUtil.newArrayList("","","","","","","","","","","","");
        List<?> row2 = CollUtil.newArrayList("管理单位：" + dbZjTzProManage.getCompanyName(),"","","","","","","","","","当前日期：" + DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN),"");
        List<?> row3 = CollUtil.newArrayList("子项目名称：" + dbZjTzComplianceDeal.getSubprojectName(),"","","","","","","","","","","");
        List<?> row4 = CollUtil.newArrayList("制度时限预警","策划时限预警","序号","环节编号","合法合规名称","项目公司成立时应办理环节","制度要求应办理完结日期","策划批复应办理完结日期","文件批复日期","办理情况","附件名称","备注");
//        rowsList.add(row1);
        rowsList.add(row2);
        rowsList.add(row3);
        rowsList.add(row4);
        
        
        // 数据装载（与上面的表头对应）
        if(zjTzComplianceDetailList != null && zjTzComplianceDetailList.size()>0) {
        	int sort = 0;
        	String dealSituation = "";
        	String dealFlag = "";
        	String colourFlag = "";
        	String colourFlag1 = "";
        	
            for(ZjTzComplianceDetail dbzjTzComplianceDetail:zjTzComplianceDetailList) {
            	//制度时限预警
            	
            	if (StrUtil.isNotEmpty(dbzjTzComplianceDetail.getDealSituation())) {
    				if (StrUtil.equals(dbzjTzComplianceDetail.getDealSituation(), "0") || StrUtil.equals(dbzjTzComplianceDetail.getDealSituation(), "1")) {
    					if (dbzjTzComplianceDetail.getShouldFinishDate() != null) {
    						if (DateUtil.between(dbzjTzComplianceDetail.getShouldFinishDate(), new Date(), DateUnit.DAY) >= 15) {
    							dbzjTzComplianceDetail.setColourFlag("绿灯");
    						}else if (DateUtil.between(dbzjTzComplianceDetail.getShouldFinishDate(), new Date(), DateUnit.DAY) >= 0 && DateUtil.between(dbzjTzComplianceDetail.getShouldFinishDate(), new Date(), DateUnit.DAY) < 15) {
    							dbzjTzComplianceDetail.setColourFlag("黄灯");
    						}else if (DateUtil.compare(dbzjTzComplianceDetail.getShouldFinishDate(), new Date()) < 0) {
    							dbzjTzComplianceDetail.setColourFlag("红灯");
    						}
    					}
    				}else {
    					dbzjTzComplianceDetail.setColourFlag("绿灯");
    				}
    			}else {
    				if (StrUtil.equals(dbzjTzComplianceDetail.getNum(), "008")) {
    					dbzjTzComplianceDetail.setColourFlag("红灯");
    				}else {
    					if (dbzjTzComplianceDetail.getShouldFinishDate() != null) {
    						dbzjTzComplianceDetail.setColourFlag("红灯");
    					}else {
    						dbzjTzComplianceDetail.setColourFlag("绿灯");
    					}
    				}
    			}
            	//策划时限预警
            	if (StrUtil.isNotEmpty(dbzjTzComplianceDetail.getDealSituation())) {
    				if (StrUtil.equals(dbzjTzComplianceDetail.getDealSituation(), "0") || StrUtil.equals(dbzjTzComplianceDetail.getDealSituation(), "1")) {
    					if (dbzjTzComplianceDetail.getApprovalShouldFinishDate() != null) {
    						if (DateUtil.between(dbzjTzComplianceDetail.getApprovalShouldFinishDate(), new Date(), DateUnit.DAY) >= 15) {
    							dbzjTzComplianceDetail.setColourFlag1("绿灯");
    						}else if (DateUtil.between(dbzjTzComplianceDetail.getApprovalShouldFinishDate(), new Date(), DateUnit.DAY) >= 0 && DateUtil.between(dbzjTzComplianceDetail.getApprovalShouldFinishDate(), new Date(), DateUnit.DAY) < 15) {
    							dbzjTzComplianceDetail.setColourFlag1("黄灯");
    						}else if (DateUtil.compare(dbzjTzComplianceDetail.getApprovalShouldFinishDate(), new Date()) < 0) {
    							dbzjTzComplianceDetail.setColourFlag1("红灯");
    						}
    					}
    				}else {
    					dbzjTzComplianceDetail.setColourFlag1("绿灯");
    				}
    			}else {
    				if (StrUtil.equals(dbzjTzComplianceDetail.getNum(), "008")) {
    					dbzjTzComplianceDetail.setColourFlag1("红灯");
    				}else {
    					if (dbzjTzComplianceDetail.getApprovalShouldFinishDate() != null) {
    						dbzjTzComplianceDetail.setColourFlag1("红灯");
    					}else {
    						dbzjTzComplianceDetail.setColourFlag1("绿灯");
    					}
    				}
    			}
            	sort ++;
            	
            	if (StrUtil.equals(dbzjTzComplianceDetail.getDealSituation(), "0")) {
            		dealSituation = "未办理";
				}else if (StrUtil.equals(dbzjTzComplianceDetail.getDealSituation(), "1")) {
					dealSituation = "办理中";
				}else if (StrUtil.equals(dbzjTzComplianceDetail.getDealSituation(), "2")) {
					dealSituation = "已完成";
				}else if (StrUtil.equals(dbzjTzComplianceDetail.getDealSituation(), "3")) {
					dealSituation = "无需办理";
				}else {
					dealSituation = "";
				}
            	
            	ZjTzFile file = new ZjTzFile();
                file.setOtherId(dbzjTzComplianceDetail.getComplianceDetailId());
                List<ZjTzFile> files = zjTzFileMapper.selectByZjTzFileList(file);
                String fileName = "";
                if (files != null && files.size() > 0) {
					for (ZjTzFile dbZjTzFile : files) {
						String name = dbZjTzFile.getName();
						if (name != null) {
							fileName = name + " " + fileName;
						}else {
							fileName = "无附件";
						}
					}
				}else {
					fileName = "无附件";
				}
                
                if (StrUtil.equals(dbzjTzComplianceDetail.getDealFlag(), "1")) {
                	dealFlag = "√";
				}else {
					dealFlag = "";
				}
                
                
                if (StrUtil.equals(dbzjTzComplianceDetail.getColourFlag(), "绿灯")) {
                	colourFlag = "";
				}else if (StrUtil.equals(dbzjTzComplianceDetail.getColourFlag(), "黄灯")) {
					colourFlag = "☆";
				}else if (StrUtil.equals(dbzjTzComplianceDetail.getColourFlag(), "红灯")) {
					colourFlag = "★";
				}
                
                if (StrUtil.equals(dbzjTzComplianceDetail.getColourFlag1(), "绿灯")) {
                	colourFlag1 = "";
				}else if (StrUtil.equals(dbzjTzComplianceDetail.getColourFlag1(), "黄灯")) {
					colourFlag1 = "☆";
				}else if (StrUtil.equals(dbzjTzComplianceDetail.getColourFlag1(), "红灯")) {
					colourFlag1 = "★";
				}
                rowsList.add(CollUtil.newArrayList(colourFlag,
                		colourFlag1,
                		sort,
                		dbzjTzComplianceDetail.getNum(),
                		dbzjTzComplianceDetail.getComplianceBanseName(),
                		dealFlag,
                		DateUtil.format(dbzjTzComplianceDetail.getShouldFinishDate(), DatePattern.NORM_DATE_PATTERN),
                		DateUtil.format(dbzjTzComplianceDetail.getApprovalShouldFinishDate(), DatePattern.NORM_DATE_PATTERN),
                		DateUtil.format(dbzjTzComplianceDetail.getApprovalDate(), DatePattern.NORM_DATE_PATTERN),
                		dealSituation,
                		fileName,
                		dbzjTzComplianceDetail.getBz()

                        )
                );
            }
            // 报表名称
            String datestr = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MINUTE_PATTERN).replaceAll(":", "：");
            String fileName = "xxx报表-" + datestr + ".xlsx";
            List<List<?>> rows = CollUtil.newArrayList(rowsList);
            // 通过工具类创建writer，创建xlsx格式
            ExcelWriter writer = ExcelUtil.getWriter(true);
            writer.merge(11, dbZjTzComplianceDeal.getProjectName() + "合法合规办理情况");
            writer.setRowHeight(0, 33);
            writer.setColumnWidth(0, 20);
            writer.setColumnWidth(1, 15);
            writer.setColumnWidth(4, 30);
            writer.setColumnWidth(5, 30);
            writer.setColumnWidth(6, 30);
            writer.setColumnWidth(7, 30);
            writer.setColumnWidth(8, 30);
            writer.setColumnWidth(10, 80);
            // 设置response下载弹窗
            // response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            // out为OutputStream，需要写出到的目标流
            ServletOutputStream out = null;
//            String name = StringUtils.toUtf8String( "申请学院" );
            try {
                response.setHeader("Content-Disposition",
                        "attachment; filename=\"" + new String("".getBytes("utf-8"), "iso-8859-1") + "\"");
                out = response.getOutputStream();
                // 一次性写出内容，使用默认样式，强制输出标题
                writer.write(rows, true);
                writer.flush(out, true);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // 关闭writer，释放内存
                if (writer != null) {
                    writer.close();
                }
                // 此处记得关闭输出Servlet流
                if (out != null) {
                    IoUtil.close(out);
                }
            }

            //String url = HttpUtil.
           return null;
        } else {
            return repEntity.ok("无数据");
        }	
	}
	
}