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
        
        // ????????????
        PageHelper.startPage(zjTzComplianceDetail.getPage(),zjTzComplianceDetail.getLimit());
        // ????????????
        List<ZjTzComplianceDetail> zjTzComplianceDetailList = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(zjTzComplianceDetail);
        
        for (ZjTzComplianceDetail zjTzComplianceDetail2 : zjTzComplianceDetailList) {

//?????????????????????7?????????????????????????????????????????????==??????????????????????????????????????????????????????????????????
            //?????????????????????????????? (????????????25?????????????????????29 )
            //????????????????????????????????????15??????(???????????????????????????????????????15???)
            //??????????????????????????????????????????????????????(?????????????????????????????????)
        	
//        	???????????? ??????????????????0 ????????????1????????????2?????????
//        	????????????????????????????????????????????????????????????????????????-?????????????????????15????????????????????????
//        	????????????????????????????????????????????????????????????????????????-?????????????????????0????????????????????????
//        	????????????????????????????????????????????????
//        	????????????????????????????????????
        	
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
        	//??????????????????
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
        	//??????????????????
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
        //??????????????????????????????
//        ZjTzComplianceDetail dbzjTzComplianceDetail = zjTzComplianceDetailList.stream().filter(detail -> StrUtil.equals(detail.getComplianceBanseName(), "????????????")).findFirst().get();
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
        // ????????????
        ZjTzComplianceDetail dbZjTzComplianceDetail = zjTzComplianceDetailMapper.selectByPrimaryKey(zjTzComplianceDetail.getComplianceDetailId());
        // ????????????
        if (dbZjTzComplianceDetail != null) {
            return repEntity.ok(dbZjTzComplianceDetail);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
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
           // ??????id
           dbzjTzComplianceDetail.setComplianceDealId(zjTzComplianceDetail.getComplianceDealId());
           // ????????????
           dbzjTzComplianceDetail.setNum(zjTzComplianceDetail.getNum());
           // ??????????????????
           dbzjTzComplianceDetail.setComplianceBanseName(zjTzComplianceDetail.getComplianceBanseName());
           //????????????????????????????????????
           dbzjTzComplianceDetail.setDealFlag(zjTzComplianceDetail.getDealFlag());
           // ?????????????????????
           dbzjTzComplianceDetail.setShouldFinishDate(zjTzComplianceDetail.getShouldFinishDate());
           // ??????????????????
           dbzjTzComplianceDetail.setApprovalDate(zjTzComplianceDetail.getApprovalDate());
           // ????????????
           dbzjTzComplianceDetail.setDealSituation(zjTzComplianceDetail.getDealSituation());
           // ????????????
           dbzjTzComplianceDetail.setLockFlag(zjTzComplianceDetail.getLockFlag());
           // ????????????????????????
           dbzjTzComplianceDetail.setAddFlag(zjTzComplianceDetail.getAddFlag());
           //?????????????????????????????????
           dbzjTzComplianceDetail.setApprovalShouldFinishDate(zjTzComplianceDetail.getApprovalShouldFinishDate());
           //??????
           dbzjTzComplianceDetail.setBz(zjTzComplianceDetail.getBz());
           // ??????
           dbzjTzComplianceDetail.setModifyUserInfo(userKey, realName);
           flag = zjTzComplianceDetailMapper.updateByPrimaryKey(dbzjTzComplianceDetail);
        }
        // ??????
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
        // ??????
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
    	// ????????????????????????
    	if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
    		// ???????????????????????????????????????sql?????????
    		if(StrUtil.equals("all", zjTzComplianceDetail.getProjectId(), true)) {
    			zjTzComplianceDetail.setProjectId("");
    			zjTzComplianceDetail.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
    		}
    	} else {
    		// ???????????????
    		if(StrUtil.equals("all", zjTzComplianceDetail.getProjectId(), true)) {
    			zjTzComplianceDetail.setProjectId("");
    		}
    	}
    	// ????????????
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
		// ????????????????????????
		if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
			// ???????????????????????????????????????sql?????????
			if(StrUtil.equals("all", zjTzComplianceDetail.getProjectId(), true)) {
				zjTzComplianceDetail.setProjectId("");
				zjTzComplianceDetail.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
			}
		} else {
			// ???????????????
			if(StrUtil.equals("all", zjTzComplianceDetail.getProjectId(), true)) {
				zjTzComplianceDetail.setProjectId("");
			}
		}
		// ????????????
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
		// ????????????????????????
		if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
			// ???????????????????????????????????????sql?????????
			if(StrUtil.equals("all", zjTzComplianceDetail.getProjectId(), true)) {
				zjTzComplianceDetail.setProjectId("");
				zjTzComplianceDetail.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
			}
		} else {
			// ???????????????
			if(StrUtil.equals("all", zjTzComplianceDetail.getProjectId(), true)) {
				zjTzComplianceDetail.setProjectId("");
			}
		}
		
		// ????????????
        List<ZjTzComplianceDetail> zjTzComplianceDetailList = zjTzComplianceDetailMapper.selectByZjTzComplianceDetailList(zjTzComplianceDetail);
        ZjTzComplianceDeal dbZjTzComplianceDeal = zjTzComplianceDealMapper.selectByPrimaryKey(zjTzComplianceDetail.getComplianceDealId());
        ZjTzProManage dbZjTzProManage = zjTzProManageMapper.selectByPrimaryKey(dbZjTzComplianceDeal.getProjectId());
        // ??????
        List<List<?>> rowsList = Lists.newArrayList();
//        List<?> row1 = CollUtil.newArrayList("","","","","","","","","","","","");
        List<?> row2 = CollUtil.newArrayList("???????????????" + dbZjTzProManage.getCompanyName(),"","","","","","","","","","???????????????" + DateUtil.format(new Date(), DatePattern.NORM_DATE_PATTERN),"");
        List<?> row3 = CollUtil.newArrayList("??????????????????" + dbZjTzComplianceDeal.getSubprojectName(),"","","","","","","","","","","");
        List<?> row4 = CollUtil.newArrayList("??????????????????","??????????????????","??????","????????????","??????????????????","????????????????????????????????????","?????????????????????????????????","?????????????????????????????????","??????????????????","????????????","????????????","??????");
//        rowsList.add(row1);
        rowsList.add(row2);
        rowsList.add(row3);
        rowsList.add(row4);
        
        
        // ??????????????????????????????????????????
        if(zjTzComplianceDetailList != null && zjTzComplianceDetailList.size()>0) {
        	int sort = 0;
        	String dealSituation = "";
        	String dealFlag = "";
        	String colourFlag = "";
        	String colourFlag1 = "";
        	
            for(ZjTzComplianceDetail dbzjTzComplianceDetail:zjTzComplianceDetailList) {
            	//??????????????????
            	
            	if (StrUtil.isNotEmpty(dbzjTzComplianceDetail.getDealSituation())) {
    				if (StrUtil.equals(dbzjTzComplianceDetail.getDealSituation(), "0") || StrUtil.equals(dbzjTzComplianceDetail.getDealSituation(), "1")) {
    					if (dbzjTzComplianceDetail.getShouldFinishDate() != null) {
    						if (DateUtil.between(dbzjTzComplianceDetail.getShouldFinishDate(), new Date(), DateUnit.DAY) >= 15) {
    							dbzjTzComplianceDetail.setColourFlag("??????");
    						}else if (DateUtil.between(dbzjTzComplianceDetail.getShouldFinishDate(), new Date(), DateUnit.DAY) >= 0 && DateUtil.between(dbzjTzComplianceDetail.getShouldFinishDate(), new Date(), DateUnit.DAY) < 15) {
    							dbzjTzComplianceDetail.setColourFlag("??????");
    						}else if (DateUtil.compare(dbzjTzComplianceDetail.getShouldFinishDate(), new Date()) < 0) {
    							dbzjTzComplianceDetail.setColourFlag("??????");
    						}
    					}
    				}else {
    					dbzjTzComplianceDetail.setColourFlag("??????");
    				}
    			}else {
    				if (StrUtil.equals(dbzjTzComplianceDetail.getNum(), "008")) {
    					dbzjTzComplianceDetail.setColourFlag("??????");
    				}else {
    					if (dbzjTzComplianceDetail.getShouldFinishDate() != null) {
    						dbzjTzComplianceDetail.setColourFlag("??????");
    					}else {
    						dbzjTzComplianceDetail.setColourFlag("??????");
    					}
    				}
    			}
            	//??????????????????
            	if (StrUtil.isNotEmpty(dbzjTzComplianceDetail.getDealSituation())) {
    				if (StrUtil.equals(dbzjTzComplianceDetail.getDealSituation(), "0") || StrUtil.equals(dbzjTzComplianceDetail.getDealSituation(), "1")) {
    					if (dbzjTzComplianceDetail.getApprovalShouldFinishDate() != null) {
    						if (DateUtil.between(dbzjTzComplianceDetail.getApprovalShouldFinishDate(), new Date(), DateUnit.DAY) >= 15) {
    							dbzjTzComplianceDetail.setColourFlag1("??????");
    						}else if (DateUtil.between(dbzjTzComplianceDetail.getApprovalShouldFinishDate(), new Date(), DateUnit.DAY) >= 0 && DateUtil.between(dbzjTzComplianceDetail.getApprovalShouldFinishDate(), new Date(), DateUnit.DAY) < 15) {
    							dbzjTzComplianceDetail.setColourFlag1("??????");
    						}else if (DateUtil.compare(dbzjTzComplianceDetail.getApprovalShouldFinishDate(), new Date()) < 0) {
    							dbzjTzComplianceDetail.setColourFlag1("??????");
    						}
    					}
    				}else {
    					dbzjTzComplianceDetail.setColourFlag1("??????");
    				}
    			}else {
    				if (StrUtil.equals(dbzjTzComplianceDetail.getNum(), "008")) {
    					dbzjTzComplianceDetail.setColourFlag1("??????");
    				}else {
    					if (dbzjTzComplianceDetail.getApprovalShouldFinishDate() != null) {
    						dbzjTzComplianceDetail.setColourFlag1("??????");
    					}else {
    						dbzjTzComplianceDetail.setColourFlag1("??????");
    					}
    				}
    			}
            	sort ++;
            	
            	if (StrUtil.equals(dbzjTzComplianceDetail.getDealSituation(), "0")) {
            		dealSituation = "?????????";
				}else if (StrUtil.equals(dbzjTzComplianceDetail.getDealSituation(), "1")) {
					dealSituation = "?????????";
				}else if (StrUtil.equals(dbzjTzComplianceDetail.getDealSituation(), "2")) {
					dealSituation = "?????????";
				}else if (StrUtil.equals(dbzjTzComplianceDetail.getDealSituation(), "3")) {
					dealSituation = "????????????";
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
							fileName = "?????????";
						}
					}
				}else {
					fileName = "?????????";
				}
                
                if (StrUtil.equals(dbzjTzComplianceDetail.getDealFlag(), "1")) {
                	dealFlag = "???";
				}else {
					dealFlag = "";
				}
                
                
                if (StrUtil.equals(dbzjTzComplianceDetail.getColourFlag(), "??????")) {
                	colourFlag = "";
				}else if (StrUtil.equals(dbzjTzComplianceDetail.getColourFlag(), "??????")) {
					colourFlag = "???";
				}else if (StrUtil.equals(dbzjTzComplianceDetail.getColourFlag(), "??????")) {
					colourFlag = "???";
				}
                
                if (StrUtil.equals(dbzjTzComplianceDetail.getColourFlag1(), "??????")) {
                	colourFlag1 = "";
				}else if (StrUtil.equals(dbzjTzComplianceDetail.getColourFlag1(), "??????")) {
					colourFlag1 = "???";
				}else if (StrUtil.equals(dbzjTzComplianceDetail.getColourFlag1(), "??????")) {
					colourFlag1 = "???";
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
            // ????????????
            String datestr = DateUtil.format(new Date(), DatePattern.NORM_DATETIME_MINUTE_PATTERN).replaceAll(":", "???");
            String fileName = "xxx??????-" + datestr + ".xlsx";
            List<List<?>> rows = CollUtil.newArrayList(rowsList);
            // ?????????????????????writer?????????xlsx??????
            ExcelWriter writer = ExcelUtil.getWriter(true);
            writer.merge(11, dbZjTzComplianceDeal.getProjectName() + "????????????????????????");
            writer.setRowHeight(0, 33);
            writer.setColumnWidth(0, 20);
            writer.setColumnWidth(1, 15);
            writer.setColumnWidth(4, 30);
            writer.setColumnWidth(5, 30);
            writer.setColumnWidth(6, 30);
            writer.setColumnWidth(7, 30);
            writer.setColumnWidth(8, 30);
            writer.setColumnWidth(10, 80);
            // ??????response????????????
            // response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            // out???OutputStream??????????????????????????????
            ServletOutputStream out = null;
//            String name = StringUtils.toUtf8String( "????????????" );
            try {
                response.setHeader("Content-Disposition",
                        "attachment; filename=\"" + new String("".getBytes("utf-8"), "iso-8859-1") + "\"");
                out = response.getOutputStream();
                // ???????????????????????????????????????????????????????????????
                writer.write(rows, true);
                writer.flush(out, true);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // ??????writer???????????????
                if (writer != null) {
                    writer.close();
                }
                // ????????????????????????Servlet???
                if (out != null) {
                    IoUtil.close(out);
                }
            }

            //String url = HttpUtil.
           return null;
        } else {
            return repEntity.ok("?????????");
        }	
	}
	
}