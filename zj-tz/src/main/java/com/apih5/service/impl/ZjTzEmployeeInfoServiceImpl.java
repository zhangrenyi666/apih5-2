package com.apih5.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.ZipUtil;
import com.apih5.framework.api.sysdb.service.BaseCodeService;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.constant.ConfigConst;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.mybatis.dao.*;
import com.apih5.mybatis.pojo.*;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.service.ZjTzEmployeeInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;


@Service("zjTzEmployeeInfoService")
public class ZjTzEmployeeInfoServiceImpl implements ZjTzEmployeeInfoService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired(required = true)
    private ResponseEntity repEntity;
    
    @ApolloConfig
    private Config config;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzEmployeeInfoMapper zjTzEmployeeInfoMapper;

    @Autowired(required = true)
    private ZjTzEmployeeAwardMapper zjTzEmployeeAwardMapper;

    @Autowired(required = true)
    private ZjTzEmployeeEducationMapper zjTzEmployeeEducationMapper;

    @Autowired(required = true)
    private ZjTzEmployeeLanguageMapper zjTzEmployeeLanguageMapper;

    @Autowired(required = true)
    private ZjTzEmployeePoliticsMapper zjTzEmployeePoliticsMapper;

    @Autowired(required = true)
    private ZjTzEmployeeProfessionalMapper zjTzEmployeeProfessionalMapper;

    @Autowired(required = true)
    private ZjTzEmployeeQualificationMapper zjTzEmployeeQualificationMapper;

    @Autowired(required = true)
    private ZjTzEmployeeWorkMapper zjTzEmployeeWorkMapper;

    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;

    @Autowired(required = true)
    private ZjTzProjectAndEmployeeMapper zjTzProjectAndEmployeeMapper;

    @Autowired(required = true)
    private ZjTzProjectEmployeeMapper zjTzProjectEmployeeMapper;

    @ApolloConfig(ConfigConst.PUBLIC_OTHER_API)
    private Config publicConfig;

    @Autowired(required = true)
    private BaseCodeService baseCodeService;

    @Override
    public ResponseEntity getZjTzEmployeeInfoListByCondition(ZjTzEmployeeInfo zjTzEmployeeInfo) {
        if (zjTzEmployeeInfo == null) {
            zjTzEmployeeInfo = new ZjTzEmployeeInfo();
        }
        // 分页查询
        PageHelper.startPage(zjTzEmployeeInfo.getPage(),zjTzEmployeeInfo.getLimit());
        // 获取数据
        List<ZjTzEmployeeInfo> zjTzEmployeeInfoList = zjTzEmployeeInfoMapper.selectByZjTzEmployeeInfoListAll(zjTzEmployeeInfo);
        for (ZjTzEmployeeInfo zjTzEmployeeInfo2 : zjTzEmployeeInfoList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzEmployeeInfo2.getEmployeeInfoId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzEmployeeInfo2.setZjTzFileList(zjTzFileList);
		}
        // 得到分页信息
        PageInfo<ZjTzEmployeeInfo> p = new PageInfo<>(zjTzEmployeeInfoList);
        return repEntity.okList(zjTzEmployeeInfoList, p.getTotal());
    }

    //目前用不上(未修改)
    @Override
    public ResponseEntity getZjTzEmployeeInfoDetails(ZjTzEmployeeInfo zjTzEmployeeInfo) {
        if (zjTzEmployeeInfo == null) {
            zjTzEmployeeInfo = new ZjTzEmployeeInfo();
        }
        // 获取数据
        ZjTzEmployeeInfo dbZjTzEmployeeInfo = zjTzEmployeeInfoMapper.selectByPrimaryKey(zjTzEmployeeInfo.getEmployeeInfoId());
        // 数据存在
        if (dbZjTzEmployeeInfo != null) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(dbZjTzEmployeeInfo.getEmployeeInfoId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzEmployeeInfo.setZjTzFileList(zjTzFileList);
            return repEntity.ok(dbZjTzEmployeeInfo);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZjTzEmployeeInfo(ZjTzEmployeeInfo zjTzEmployeeInfo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        //先根据人员身份证查询是否存在
        ZjTzEmployeeInfo zjTzEmployeeInfo1 = zjTzEmployeeInfoMapper.selectByIdCard(zjTzEmployeeInfo.getIdCard());
        if(zjTzEmployeeInfo1!=null){
            return repEntity.layerMessage("no","该人员已存在");
        }
        zjTzEmployeeInfo.setEmployeeInfoId(UuidUtil.generate());
        zjTzEmployeeInfo.setCreateUserInfo(userKey, realName);
        int flag = zjTzEmployeeInfoMapper.insert(zjTzEmployeeInfo);
        // 附件list
        List<ZjTzFile> zjTzFileList = zjTzEmployeeInfo.getZjTzFileList();
        if(zjTzFileList != null && zjTzFileList.size()>0) {
        	for(ZjTzFile zjTzFile:zjTzFileList) {
        		zjTzFile.setUid(UuidUtil.generate());
        		zjTzFile.setOtherId(zjTzEmployeeInfo.getEmployeeInfoId());
        		zjTzFile.setCreateUserInfo(userKey, realName);
        		zjTzFileMapper.insert(zjTzFile);
        	}
        }
        //职(执)业资格列表↓(从最先获得的执业资格开始填写)
        List<ZjTzEmployeeQualification> qualificationList = zjTzEmployeeInfo.getQualificationList();
        if(qualificationList != null && qualificationList.size()>0) {
            for(ZjTzEmployeeQualification zjTzEmployeeQualification : qualificationList) {
                zjTzEmployeeQualification.setQualificationId(UuidUtil.generate());
                zjTzEmployeeQualification.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                zjTzEmployeeQualification.setCreateUserInfo(userKey, realName);
                zjTzEmployeeQualificationMapper.insert(zjTzEmployeeQualification);
            }
        }
        //职称情况列表↓(从最先获得的职称开始填写)
        List<ZjTzEmployeeProfessional> professionalList = zjTzEmployeeInfo.getProfessionalList();
        if(professionalList != null && professionalList.size()>0) {
            for(ZjTzEmployeeProfessional zjTzEmployeeProfessional : professionalList) {
                zjTzEmployeeProfessional.setProfessionalId(UuidUtil.generate());
                zjTzEmployeeProfessional.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                zjTzEmployeeProfessional.setCreateUserInfo(userKey, realName);
                zjTzEmployeeProfessionalMapper.insert(zjTzEmployeeProfessional);
            }
        }
        //工作经历列表↓(从刚参加的工作开始填写)
        List<ZjTzEmployeeWork> workList = zjTzEmployeeInfo.getWorkList();
        if(workList != null && workList.size()>0) {
            for(ZjTzEmployeeWork zjTzEmployeeWork : workList) {
                zjTzEmployeeWork.setWorkId(UuidUtil.generate());
                zjTzEmployeeWork.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                zjTzEmployeeWork.setCreateUserInfo(userKey, realName);
                zjTzEmployeeWorkMapper.insert(zjTzEmployeeWork);
            }
        }
        //政治经历列表↓(从高中开始填写)
//        List<ZjTzEmployeePolitics> politicsList = zjTzEmployeeInfo.getPoliticsList();
//        if(politicsList != null && politicsList.size()>0) {
//            for(ZjTzEmployeePolitics zjTzEmployeePolitics : politicsList) {
//                zjTzEmployeePolitics.setPoliticsId(UuidUtil.generate());
//                zjTzEmployeePolitics.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
//                zjTzEmployeePolitics.setCreateUserInfo(userKey, realName);
//                zjTzEmployeePoliticsMapper.insert(zjTzEmployeePolitics);
//            }
//        }
        //学历学位列表↓(按获得奖励的先后顺序填写)
        List<ZjTzEmployeeEducation> educationList = zjTzEmployeeInfo.getEducationList();
        if(educationList != null && educationList.size()>0) {
            for(ZjTzEmployeeEducation zjTzEmployeeEducation : educationList) {
                zjTzEmployeeEducation.setEducationId(UuidUtil.generate());
                zjTzEmployeeEducation.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                zjTzEmployeeEducation.setCreateUserInfo(userKey, realName);
                zjTzEmployeeEducationMapper.insert(zjTzEmployeeEducation);
            }
        }
        //奖励情况列表↓
        List<ZjTzEmployeeAward> awardList = zjTzEmployeeInfo.getAwardList();
        if(awardList != null && awardList.size()>0) {
            for(ZjTzEmployeeAward zjTzEmployeeAward : awardList) {
                zjTzEmployeeAward.setAwardId(UuidUtil.generate());
                zjTzEmployeeAward.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                zjTzEmployeeAward.setCreateUserInfo(userKey, realName);
                zjTzEmployeeAwardMapper.insert(zjTzEmployeeAward);
            }
        }
        //语言情况列表↓
//        List<ZjTzEmployeeLanguage> languageList = zjTzEmployeeInfo.getLanguageList();
//        if(languageList != null && languageList.size()>0) {
//            for(ZjTzEmployeeLanguage zjTzEmployeeLanguage : languageList) {
//                zjTzEmployeeLanguage.setLanguageId(UuidUtil.generate());
//                zjTzEmployeeLanguage.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
//                zjTzEmployeeLanguage.setCreateUserInfo(userKey, realName);
//                zjTzEmployeeLanguageMapper.insert(zjTzEmployeeLanguage);
//            }
//        }
        if(zjTzEmployeeInfo.getProjectEmployeeId()!=null){
            //如果有项目人员表的信息的话,往项目人员表中添加
            ZjTzProjectAndEmployee zjTzProjectAndEmployee = new ZjTzProjectAndEmployee();
            //人员id
            zjTzProjectAndEmployee.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
            //项目id
            zjTzProjectAndEmployee.setProjectId(zjTzEmployeeInfo.getProjectId());
            //项目人员id
            zjTzProjectAndEmployee.setProjectEmployeeId(zjTzEmployeeInfo.getProjectEmployeeId());
            //项目人员关联id
            zjTzProjectAndEmployee.setProjectAndEmployeeId(UuidUtil.generate());
            zjTzProjectAndEmployee.setCreateUserInfo(userKey, realName);
            zjTzProjectAndEmployeeMapper.insert(zjTzProjectAndEmployee);
            //查询人数
            ZjTzProjectAndEmployee zjTzProjectAndEmployee1 = new ZjTzProjectAndEmployee();
            zjTzProjectAndEmployee1.setProjectEmployeeId(zjTzEmployeeInfo.getProjectEmployeeId());
            List<ZjTzProjectAndEmployee> zjTzProjectAndEmployees = zjTzProjectAndEmployeeMapper.selectByZjTzProjectAndEmployeeList(zjTzProjectAndEmployee1);
            //修改人数
            ZjTzProjectEmployee zjTzProjectEmployee = new ZjTzProjectEmployee();
            zjTzProjectEmployee.setEmployeeNumber(zjTzProjectAndEmployees.size());
            zjTzProjectEmployee.setProjectEmployeeId(zjTzEmployeeInfo.getProjectEmployeeId());
            zjTzProjectEmployeeMapper.updataPersonNumber(zjTzProjectEmployee);
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzEmployeeInfo);
        }
    }

    @Override
    public ResponseEntity updateZjTzEmployeeInfo(ZjTzEmployeeInfo zjTzEmployeeInfo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzEmployeeInfo dbzjTzEmployeeInfo = zjTzEmployeeInfoMapper.selectByPrimaryKey(zjTzEmployeeInfo.getEmployeeInfoId());
        if (dbzjTzEmployeeInfo != null && StrUtil.isNotEmpty(dbzjTzEmployeeInfo.getEmployeeInfoId())) {
           // 单位名称
           dbzjTzEmployeeInfo.setCompanyName(zjTzEmployeeInfo.getCompanyName());
           // 人事关系所在单位
           dbzjTzEmployeeInfo.setPersonnelUnit(zjTzEmployeeInfo.getPersonnelUnit());
           // 部门名称
           dbzjTzEmployeeInfo.setDepartmentName(zjTzEmployeeInfo.getDepartmentName());
           // 调入本项目时间
           dbzjTzEmployeeInfo.setJoinTime(zjTzEmployeeInfo.getJoinTime());
           // 姓名
           dbzjTzEmployeeInfo.setEmployeeName(zjTzEmployeeInfo.getEmployeeName());
           // 身份证号码
           dbzjTzEmployeeInfo.setIdCard(zjTzEmployeeInfo.getIdCard());
           // 出生日期
           dbzjTzEmployeeInfo.setBirthDate(zjTzEmployeeInfo.getBirthDate());
           // 籍贯
           dbzjTzEmployeeInfo.setNativePlace(zjTzEmployeeInfo.getNativePlace());
           // 性别
           dbzjTzEmployeeInfo.setSex(zjTzEmployeeInfo.getSex());
           // 民族
           dbzjTzEmployeeInfo.setNation(zjTzEmployeeInfo.getNation());
           // 参加工作时间
           dbzjTzEmployeeInfo.setJobTime(zjTzEmployeeInfo.getJobTime());
           // 到本企业时间
           dbzjTzEmployeeInfo.setEnterPriseTime(zjTzEmployeeInfo.getEnterPriseTime());
           // 婚姻状况
           dbzjTzEmployeeInfo.setMarital(zjTzEmployeeInfo.getMarital());
           // 健康状况
           dbzjTzEmployeeInfo.setHealth(zjTzEmployeeInfo.getHealth());
           // 户口所在地
           dbzjTzEmployeeInfo.setRegisteredResidence(zjTzEmployeeInfo.getRegisteredResidence());
           // 岗位职务
           dbzjTzEmployeeInfo.setPost(zjTzEmployeeInfo.getPost());
           // 人才类别id
           dbzjTzEmployeeInfo.setEmployeeTypeId(zjTzEmployeeInfo.getEmployeeTypeId());
           // 用工类型id
           dbzjTzEmployeeInfo.setLaborTypeId(zjTzEmployeeInfo.getLaborTypeId());
           // 移动电话
           dbzjTzEmployeeInfo.setPhone(zjTzEmployeeInfo.getPhone());
           // 电子邮箱
           dbzjTzEmployeeInfo.setMailBox(zjTzEmployeeInfo.getMailBox());
           // 合同性质id
           dbzjTzEmployeeInfo.setContractCharacterId(zjTzEmployeeInfo.getContractCharacterId());
           // 政治面貌id
           dbzjTzEmployeeInfo.setPoliticalId(zjTzEmployeeInfo.getPoliticalId());
           // 领导班子类别id
           dbzjTzEmployeeInfo.setLeaderCategoryId(zjTzEmployeeInfo.getLeaderCategoryId());
           // 参加党派时间
           dbzjTzEmployeeInfo.setAttendPartyTime(zjTzEmployeeInfo.getAttendPartyTime());
           // 项目id
           dbzjTzEmployeeInfo.setProjectId(zjTzEmployeeInfo.getProjectId());
           // 项目名称
           dbzjTzEmployeeInfo.setProjectName(zjTzEmployeeInfo.getProjectName());
           // 原项目id
           dbzjTzEmployeeInfo.setPreProjectId(zjTzEmployeeInfo.getPreProjectId());
           // 原项目名称
           dbzjTzEmployeeInfo.setPreProjectName(zjTzEmployeeInfo.getPreProjectName());
           // 调出上一项目时间
           dbzjTzEmployeeInfo.setPreJoinTime(zjTzEmployeeInfo.getPreJoinTime());
           // 共通
           dbzjTzEmployeeInfo.setModifyUserInfo(userKey, realName);
           flag = zjTzEmployeeInfoMapper.updateByPrimaryKey(dbzjTzEmployeeInfo);
            // 删除附件再新增
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzEmployeeInfo.getEmployeeInfoId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
               zjTzFileSelect.setModifyUserInfo(userKey, realName);
               zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           // 附件list
           List<ZjTzFile> zjTzFileList = zjTzEmployeeInfo.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
               for(ZjTzFile zjTzFile:zjTzFileList) {
                   zjTzFile.setUid(UuidUtil.generate());
                   zjTzFile.setOtherId(zjTzEmployeeInfo.getEmployeeInfoId());
                   zjTzFile.setCreateUserInfo(userKey, realName);
                   zjTzFileMapper.insert(zjTzFile);
               }
           }
            //修改内部数据(先删后增)
            //职(执)业资格列表↓(从最先获得的执业资格开始填写)
            ZjTzEmployeeQualification zjTzEmployeeQualification = new ZjTzEmployeeQualification();
            zjTzEmployeeQualification.setEmployeeInfoId(dbzjTzEmployeeInfo.getEmployeeInfoId());
            List<ZjTzEmployeeQualification> zjTzEmployeeQualifications = zjTzEmployeeQualificationMapper.selectByZjTzEmployeeQualificationList(zjTzEmployeeQualification);
            if(zjTzEmployeeQualifications != null && zjTzEmployeeQualifications.size()>0) {
                zjTzEmployeeQualification.setModifyUserInfo(userKey, realName);
                zjTzEmployeeQualificationMapper.batchDeleteUpdateZjTzEmployeeQualification(zjTzEmployeeQualifications, zjTzEmployeeQualification);
            }
            // 职(执)业资格列表list
            List<ZjTzEmployeeQualification> zjTzEmployeeInfoQualificationList = zjTzEmployeeInfo.getQualificationList();
            if(zjTzEmployeeInfoQualificationList != null && zjTzEmployeeInfoQualificationList.size()>0) {
                for(ZjTzEmployeeQualification zjTzEmployeeQualification1:zjTzEmployeeInfoQualificationList) {
                    zjTzEmployeeQualification1.setQualificationId(UuidUtil.generate());
                    zjTzEmployeeQualification1.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                    zjTzEmployeeQualification1.setCreateUserInfo(userKey, realName);
                    zjTzEmployeeQualificationMapper.insert(zjTzEmployeeQualification1);
                }
            }
            //职称情况列表↓(从最先获得的职称开始填写)professionalList
            ZjTzEmployeeProfessional zjTzEmployeeProfessional = new ZjTzEmployeeProfessional();
            zjTzEmployeeProfessional.setEmployeeInfoId(dbzjTzEmployeeInfo.getEmployeeInfoId());
            List<ZjTzEmployeeProfessional> zjTzEmployeeProfessionals = zjTzEmployeeProfessionalMapper.selectByZjTzEmployeeProfessionalList(zjTzEmployeeProfessional);
            if(zjTzEmployeeProfessionals != null && zjTzEmployeeProfessionals.size()>0) {
                zjTzEmployeeProfessional.setModifyUserInfo(userKey, realName);
                zjTzEmployeeProfessionalMapper.batchDeleteUpdateZjTzEmployeeProfessional(zjTzEmployeeProfessionals, zjTzEmployeeProfessional);
            }
            //职称情况列表↓
            List<ZjTzEmployeeProfessional> professionalList = zjTzEmployeeInfo.getProfessionalList();
            if(professionalList != null && professionalList.size()>0) {
                for(ZjTzEmployeeProfessional zjTzEmployeeProfessional1:professionalList) {
                    zjTzEmployeeProfessional1.setProfessionalId(UuidUtil.generate());
                    zjTzEmployeeProfessional1.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                    zjTzEmployeeProfessional1.setCreateUserInfo(userKey, realName);
                    zjTzEmployeeProfessionalMapper.insert(zjTzEmployeeProfessional1);
                }
            }
            //工作经历列表↓(从刚参加的工作开始填写)
            ZjTzEmployeeWork zjTzEmployeeWork1 = new ZjTzEmployeeWork();
            zjTzEmployeeWork1.setEmployeeInfoId(dbzjTzEmployeeInfo.getEmployeeInfoId());
            List<ZjTzEmployeeWork> zjTzEmployeeWorks = zjTzEmployeeWorkMapper.selectByZjTzEmployeeWorkList(zjTzEmployeeWork1);
            if(zjTzEmployeeWorks != null && zjTzEmployeeWorks.size()>0) {
                zjTzEmployeeWork1.setModifyUserInfo(userKey, realName);
                zjTzEmployeeWorkMapper.batchDeleteUpdateZjTzEmployeeWork(zjTzEmployeeWorks, zjTzEmployeeWork1);
            }
            //工作经历列表list
            List<ZjTzEmployeeWork> workList = zjTzEmployeeInfo.getWorkList();
            if(workList != null && workList.size()>0) {
                for(ZjTzEmployeeWork zjTzEmployeeWork : workList) {
                    zjTzEmployeeWork.setWorkId(UuidUtil.generate());
                    zjTzEmployeeWork.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                    zjTzEmployeeWork.setCreateUserInfo(userKey, realName);
                    zjTzEmployeeWorkMapper.insert(zjTzEmployeeWork);
                }
            }
            //政治经历列表↓
//            ZjTzEmployeePolitics zjTzEmployeePolitics1 = new ZjTzEmployeePolitics();
//            zjTzEmployeePolitics1.setEmployeeInfoId(dbzjTzEmployeeInfo.getEmployeeInfoId());
//            List<ZjTzEmployeePolitics> zjTzEmployeePolitics2 = zjTzEmployeePoliticsMapper.selectByZjTzEmployeePoliticsList(zjTzEmployeePolitics1);
//            if(zjTzEmployeePolitics2 != null && zjTzEmployeePolitics2.size()>0) {
//                zjTzEmployeePolitics1.setModifyUserInfo(userKey, realName);
//                zjTzEmployeePoliticsMapper.batchDeleteUpdateZjTzEmployeePolitics(zjTzEmployeePolitics2, zjTzEmployeePolitics1);
//            }
            //政治经历列表↓(从高中开始填写)
//            List<ZjTzEmployeePolitics> politicsList = zjTzEmployeeInfo.getPoliticsList();
//            if(politicsList != null && politicsList.size()>0) {
//                for(ZjTzEmployeePolitics zjTzEmployeePolitics : politicsList) {
//                    zjTzEmployeePolitics.setPoliticsId(UuidUtil.generate());
//                    zjTzEmployeePolitics.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
//                    zjTzEmployeePolitics.setCreateUserInfo(userKey, realName);
//                    zjTzEmployeePoliticsMapper.insert(zjTzEmployeePolitics);
//                }
//            }
            //学历学位列表↓(按获得奖励的先后顺序填写)
            ZjTzEmployeeEducation zjTzEmployeeEducation1 = new ZjTzEmployeeEducation();
            zjTzEmployeeEducation1.setEmployeeInfoId(dbzjTzEmployeeInfo.getEmployeeInfoId());
            List<ZjTzEmployeeEducation> zjTzEmployeeEducations = zjTzEmployeeEducationMapper.selectByZjTzEmployeeEducationList(zjTzEmployeeEducation1);
            if(zjTzEmployeeEducations != null && zjTzEmployeeEducations.size()>0) {
                zjTzEmployeeEducation1.setModifyUserInfo(userKey, realName);
                zjTzEmployeeEducationMapper.batchDeleteUpdateZjTzEmployeeEducation(zjTzEmployeeEducations, zjTzEmployeeEducation1);
            }
            //学历学位列表↓(按获得奖励的先后顺序填写)
            List<ZjTzEmployeeEducation> educationList = zjTzEmployeeInfo.getEducationList();
            if(educationList != null && educationList.size()>0) {
                for(ZjTzEmployeeEducation zjTzEmployeeEducation : educationList) {
                    zjTzEmployeeEducation.setEducationId(UuidUtil.generate());
                    zjTzEmployeeEducation.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                    zjTzEmployeeEducation.setCreateUserInfo(userKey, realName);
                    zjTzEmployeeEducationMapper.insert(zjTzEmployeeEducation);
                }
            }
            //奖励情况列表↓
            ZjTzEmployeeAward zjTzEmployeeAward1 = new ZjTzEmployeeAward();
            zjTzEmployeeAward1.setEmployeeInfoId(dbzjTzEmployeeInfo.getEmployeeInfoId());
            List<ZjTzEmployeeAward> zjTzEmployeeAwards = zjTzEmployeeAwardMapper.selectByZjTzEmployeeAwardList(zjTzEmployeeAward1);
            if(zjTzEmployeeAwards != null && zjTzEmployeeAwards.size()>0) {
                zjTzEmployeeAward1.setModifyUserInfo(userKey, realName);
                zjTzEmployeeAwardMapper.batchDeleteUpdateZjTzEmployeeAward(zjTzEmployeeAwards, zjTzEmployeeAward1);
            }
            //奖励情况列表↓
            List<ZjTzEmployeeAward> awardList = zjTzEmployeeInfo.getAwardList();
            if(awardList != null && awardList.size()>0) {
                for(ZjTzEmployeeAward zjTzEmployeeAward : awardList) {
                    zjTzEmployeeAward.setAwardId(UuidUtil.generate());
                    zjTzEmployeeAward.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                    zjTzEmployeeAward.setCreateUserInfo(userKey, realName);
                    zjTzEmployeeAwardMapper.insert(zjTzEmployeeAward);
                }
            }
            //语言情况列表↓
//            ZjTzEmployeeLanguage zjTzEmployeeLanguage1 = new ZjTzEmployeeLanguage();
//            zjTzEmployeeLanguage1.setEmployeeInfoId(dbzjTzEmployeeInfo.getEmployeeInfoId());
//            List<ZjTzEmployeeLanguage> zjTzEmployeeLanguages = zjTzEmployeeLanguageMapper.selectByZjTzEmployeeLanguageList(zjTzEmployeeLanguage1);
//            if(zjTzEmployeeLanguages != null && zjTzEmployeeLanguages.size()>0) {
//                zjTzEmployeeLanguage1.setModifyUserInfo(userKey, realName);
//                zjTzEmployeeLanguageMapper.batchDeleteUpdateZjTzEmployeeLanguage(zjTzEmployeeLanguages, zjTzEmployeeLanguage1);
//            }
            //语言情况列表↓
//            List<ZjTzEmployeeLanguage> languageList = zjTzEmployeeInfo.getLanguageList();
//            if(languageList != null && languageList.size()>0) {
//                for(ZjTzEmployeeLanguage zjTzEmployeeLanguage : languageList) {
//                    zjTzEmployeeLanguage.setLanguageId(UuidUtil.generate());
//                    zjTzEmployeeLanguage.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
//                    zjTzEmployeeLanguage.setCreateUserInfo(userKey, realName);
//                    zjTzEmployeeLanguageMapper.insert(zjTzEmployeeLanguage);
//                }
//            }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzEmployeeInfo);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzEmployeeInfo(List<ZjTzEmployeeInfo> zjTzEmployeeInfoList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzEmployeeInfoList != null && zjTzEmployeeInfoList.size() > 0) {
        	for (ZjTzEmployeeInfo zjTzEmployeeInfo : zjTzEmployeeInfoList) {
        		// 删除附件
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzEmployeeInfo.getEmployeeInfoId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
                //职(执)业资格列表↓(从最先获得的执业资格开始填写)
                ZjTzEmployeeQualification zjTzEmployeeQualification = new ZjTzEmployeeQualification();
                zjTzEmployeeQualification.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                List<ZjTzEmployeeQualification> zjTzEmployeeQualifications = zjTzEmployeeQualificationMapper.selectByZjTzEmployeeQualificationList(zjTzEmployeeQualification);
                if(zjTzEmployeeQualifications != null && zjTzEmployeeQualifications.size()>0) {
                    zjTzEmployeeQualification.setModifyUserInfo(userKey, realName);
                    zjTzEmployeeQualificationMapper.batchDeleteUpdateZjTzEmployeeQualification(zjTzEmployeeQualifications, zjTzEmployeeQualification);
                }
                //职称情况列表↓(从最先获得的职称开始填写)professionalList
                ZjTzEmployeeProfessional zjTzEmployeeProfessional = new ZjTzEmployeeProfessional();
                zjTzEmployeeProfessional.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                List<ZjTzEmployeeProfessional> zjTzEmployeeProfessionals = zjTzEmployeeProfessionalMapper.selectByZjTzEmployeeProfessionalList(zjTzEmployeeProfessional);
                if(zjTzEmployeeProfessionals != null && zjTzEmployeeProfessionals.size()>0) {
                    zjTzEmployeeProfessional.setModifyUserInfo(userKey, realName);
                    zjTzEmployeeProfessionalMapper.batchDeleteUpdateZjTzEmployeeProfessional(zjTzEmployeeProfessionals, zjTzEmployeeProfessional);
                }
                //工作经历列表↓(从刚参加的工作开始填写)
                ZjTzEmployeeWork zjTzEmployeeWork1 = new ZjTzEmployeeWork();
                zjTzEmployeeWork1.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                List<ZjTzEmployeeWork> zjTzEmployeeWorks = zjTzEmployeeWorkMapper.selectByZjTzEmployeeWorkList(zjTzEmployeeWork1);
                if(zjTzEmployeeWorks != null && zjTzEmployeeWorks.size()>0) {
                    zjTzEmployeeWork1.setModifyUserInfo(userKey, realName);
                    zjTzEmployeeWorkMapper.batchDeleteUpdateZjTzEmployeeWork(zjTzEmployeeWorks, zjTzEmployeeWork1);
                }
                //政治经历列表↓
//                ZjTzEmployeePolitics zjTzEmployeePolitics1 = new ZjTzEmployeePolitics();
//                zjTzEmployeePolitics1.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
//                List<ZjTzEmployeePolitics> zjTzEmployeePolitics2 = zjTzEmployeePoliticsMapper.selectByZjTzEmployeePoliticsList(zjTzEmployeePolitics1);
//                if(zjTzEmployeePolitics2 != null && zjTzEmployeePolitics2.size()>0) {
//                    zjTzEmployeePolitics1.setModifyUserInfo(userKey, realName);
//                    zjTzEmployeePoliticsMapper.batchDeleteUpdateZjTzEmployeePolitics(zjTzEmployeePolitics2, zjTzEmployeePolitics1);
//                }
                //学历学位列表↓(按获得奖励的先后顺序填写)
                ZjTzEmployeeEducation zjTzEmployeeEducation1 = new ZjTzEmployeeEducation();
                zjTzEmployeeEducation1.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                List<ZjTzEmployeeEducation> zjTzEmployeeEducations = zjTzEmployeeEducationMapper.selectByZjTzEmployeeEducationList(zjTzEmployeeEducation1);
                if(zjTzEmployeeEducations != null && zjTzEmployeeEducations.size()>0) {
                    zjTzEmployeeEducation1.setModifyUserInfo(userKey, realName);
                    zjTzEmployeeEducationMapper.batchDeleteUpdateZjTzEmployeeEducation(zjTzEmployeeEducations, zjTzEmployeeEducation1);
                }
                //奖励情况列表↓
                ZjTzEmployeeAward zjTzEmployeeAward1 = new ZjTzEmployeeAward();
                zjTzEmployeeAward1.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                List<ZjTzEmployeeAward> zjTzEmployeeAwards = zjTzEmployeeAwardMapper.selectByZjTzEmployeeAwardList(zjTzEmployeeAward1);
                if(zjTzEmployeeAwards != null && zjTzEmployeeAwards.size()>0) {
                    zjTzEmployeeAward1.setModifyUserInfo(userKey, realName);
                    zjTzEmployeeAwardMapper.batchDeleteUpdateZjTzEmployeeAward(zjTzEmployeeAwards, zjTzEmployeeAward1);
                }
                //语言情况列表↓
//                ZjTzEmployeeLanguage zjTzEmployeeLanguage1 = new ZjTzEmployeeLanguage();
//                zjTzEmployeeLanguage1.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
//                List<ZjTzEmployeeLanguage> zjTzEmployeeLanguages = zjTzEmployeeLanguageMapper.selectByZjTzEmployeeLanguageList(zjTzEmployeeLanguage1);
//                if(zjTzEmployeeLanguages != null && zjTzEmployeeLanguages.size()>0) {
//                    zjTzEmployeeLanguage1.setModifyUserInfo(userKey, realName);
//                    zjTzEmployeeLanguageMapper.batchDeleteUpdateZjTzEmployeeLanguage(zjTzEmployeeLanguages, zjTzEmployeeLanguage1);
//                }
			}
           ZjTzEmployeeInfo zjTzEmployeeInfo = new ZjTzEmployeeInfo();
           zjTzEmployeeInfo.setModifyUserInfo(userKey, realName);
           flag = zjTzEmployeeInfoMapper.batchDeleteUpdateZjTzEmployeeInfo(zjTzEmployeeInfoList, zjTzEmployeeInfo);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzEmployeeInfoList);
        }
    }

    //获取个人信息
    @Override
    public ZjTzEmployeeInfo exportZjTzEmployeeInfo(ZjTzEmployeeInfo zjTzEmployeeInfo) {
        if(zjTzEmployeeInfo == null){
            return null;
        }
        if(StrUtil.isNotEmpty(zjTzEmployeeInfo.getEmployeeInfoId())){
            ZjTzEmployeeInfo dbzjTzEmployeeInfo = zjTzEmployeeInfoMapper.selectByPrimaryKey(zjTzEmployeeInfo.getEmployeeInfoId());
            // 政治面貌
            // politicalId
            // private String politicalName;
            if (StrUtil.isNotEmpty(dbzjTzEmployeeInfo.getPoliticalId())) {
                String politicalName = baseCodeService.getBaseCodeItemName("zhengZhiMianMao", dbzjTzEmployeeInfo.getPoliticalId());
                dbzjTzEmployeeInfo.setPoliticalName(politicalName);
            }
            //人才类别
            //employeeTypeId
            //private String employeeTypeName;
            if (StrUtil.isNotEmpty(dbzjTzEmployeeInfo.getEmployeeTypeId())) {
                String employeeTypeName = baseCodeService.getBaseCodeItemName("renCaiLeiBie", dbzjTzEmployeeInfo.getEmployeeTypeId());
                dbzjTzEmployeeInfo.setEmployeeTypeName(employeeTypeName);
            }
            //用工类型
            //laborTypeId
            //private String laborTypeName;
            if (StrUtil.isNotEmpty(dbzjTzEmployeeInfo.getLaborTypeId())) {
                String laborTypeName = baseCodeService.getBaseCodeItemName("yongGongLeiXing", dbzjTzEmployeeInfo.getLaborTypeId());
                dbzjTzEmployeeInfo.setLaborTypeName(laborTypeName);
            }
            //合同性质
            //contractCharacterId
            //private String contractCharacterName;
            if (StrUtil.isNotEmpty(dbzjTzEmployeeInfo.getContractCharacterId())) {
                String contractCharacterName = baseCodeService.getBaseCodeItemName("heTongXingZhi", dbzjTzEmployeeInfo.getContractCharacterId());
                dbzjTzEmployeeInfo.setContractCharacterName(contractCharacterName);
            }
            //zj_tz_employee_professional
            //职称情况表 - 专业技术资格名称 逗号隔开
            //private String professionalName;
            ZjTzEmployeeProfessional zjTzEmployeeProfessional = new ZjTzEmployeeProfessional();
            zjTzEmployeeProfessional.setEmployeeInfoId(dbzjTzEmployeeInfo.getEmployeeInfoId());
            //获取职称表数据
            List<ZjTzEmployeeProfessional> zjTzEmployeeProfessionals = zjTzEmployeeProfessionalMapper.selectByZjTzEmployeeProfessionalList(zjTzEmployeeProfessional);
            //获取职称聚合
            String professionalName = zjTzEmployeeProfessionals.stream().filter(o -> StrUtil.isNotEmpty(o.getProfessionalNameId())).map(
                    o ->baseCodeService.getBaseCodeItemName("zhuanYeJiShuZiGeMingCheng", o.getProfessionalNameId())
            ).collect(Collectors.joining(","));
            dbzjTzEmployeeInfo.setProfessionalName(professionalName);

            //出生日期 -> 字符串类型的
            if(dbzjTzEmployeeInfo.getBirthDate()!=null){
                dbzjTzEmployeeInfo.setBirthDateString(DateUtil.format(dbzjTzEmployeeInfo.getBirthDate(),"yyyy-MM-dd"));
            }
            return dbzjTzEmployeeInfo;
        }
        return null;
    }

    //获取教育经历
    @Override
    public List<ZjTzEmployeeEducation> exportZjTzEmployeeInfoEducation(ZjTzEmployeeInfo zjTzEmployeeInfo) {
        if(zjTzEmployeeInfo == null){
            return new ArrayList<>();
        }
        if(StrUtil.isNotEmpty(zjTzEmployeeInfo.getEmployeeInfoId())){
            ZjTzEmployeeEducation zjTzEmployeeEducation = new ZjTzEmployeeEducation();
            zjTzEmployeeEducation.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
            //获取这个人的教育经历
            List<ZjTzEmployeeEducation> zjTzEmployeeEducations = zjTzEmployeeEducationMapper.selectByZjTzEmployeeEducationList(zjTzEmployeeEducation);
            if(zjTzEmployeeEducations !=null && zjTzEmployeeEducations.size()>0){
                for (ZjTzEmployeeEducation tzEmployeeEducation : zjTzEmployeeEducations) {
                    //入学时间 -> 字符串类型
                    if(tzEmployeeEducation.getStratTime()!=null){
                        tzEmployeeEducation.setStratTimeString(DateUtil.format(tzEmployeeEducation.getStratTime(),"yyyy-MM-dd"));
                    }
                    //毕业时间 -> 字符串类型
                    if(tzEmployeeEducation.getGraduateTime()!=null){
                        tzEmployeeEducation.setGraduateTimeString(DateUtil.format(tzEmployeeEducation.getGraduateTime(),"yyyy-MM-dd"));
                    }
                    //学历
                    //educationName
                    //xueLi
                    if (StrUtil.isNotEmpty(tzEmployeeEducation.getEducation())) {
                        String educationName = baseCodeService.getBaseCodeItemName("xueLi", tzEmployeeEducation.getEducation());
                        tzEmployeeEducation.setEducationName(educationName);
                    }
                    //学位
                    //degreeName
                    //xueWei
                    if (StrUtil.isNotEmpty(tzEmployeeEducation.getDegree())) {
                        String degreeName = baseCodeService.getBaseCodeItemName("xueWei", tzEmployeeEducation.getDegree());
                        tzEmployeeEducation.setDegreeName(degreeName);
                    }
                }
            }
            return zjTzEmployeeEducations;
        }
        return new ArrayList<>();
    }

    //获取工作经历
    @Override
    public List<ZjTzEmployeeWork> exportZjTzEmployeeInfoWork(ZjTzEmployeeInfo zjTzEmployeeInfo) {
        if(zjTzEmployeeInfo == null){
            return new ArrayList<>();
        }
        if(StrUtil.isNotEmpty(zjTzEmployeeInfo.getEmployeeInfoId())){
            ZjTzEmployeeWork zjTzEmployeeWork = new ZjTzEmployeeWork();
            zjTzEmployeeWork.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
            List<ZjTzEmployeeWork> zjTzEmployeeWorks = zjTzEmployeeWorkMapper.selectByZjTzEmployeeWorkList(zjTzEmployeeWork);
            if(zjTzEmployeeWorks!=null && zjTzEmployeeWorks.size()>0){
                for (ZjTzEmployeeWork tzEmployeeWork : zjTzEmployeeWorks) {
                    //开始时间 -> 开始时间字符串
                    if(tzEmployeeWork.getStartTime()!=null){
                        tzEmployeeWork.setStartTimeString(DateUtil.format(tzEmployeeWork.getStartTime(),"yyyy-MM-dd"));
                    }
                    //结束时间 -> 结束时间字符串
                    if(tzEmployeeWork.getEndTime()!=null){
                        tzEmployeeWork.setEndTimeString(DateUtil.format(tzEmployeeWork.getEndTime(),"yyyy-MM-dd"));
                    }
                }
            }
            return zjTzEmployeeWorks;
        }
        return new ArrayList<>();
    }

    //导出zip
    @Override
    public ResponseEntity printZjTzEmployeeInfo(List<ZjTzEmployeeInfo> zjTzEmployeeInfoList) {
        if(zjTzEmployeeInfoList!=null && zjTzEmployeeInfoList.size()>0){
            HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
            // 要生成的压缩文件地址和文件名称
            String zipName = "人员资料-" + DateUtil.format(new Date(), "yyyyMMdd") + ".zip";
            //实际文件
            File[] files = new File[zjTzEmployeeInfoList.size()];
            for (int i = 0; i < zjTzEmployeeInfoList.size(); i++) {
                //先查询要导出人员的信息
                ZjTzEmployeeInfo zjTzEmployeeInfo = zjTzEmployeeInfoMapper.selectByPrimaryKey(zjTzEmployeeInfoList.get(i).getEmployeeInfoId());
                String downUrl = config.getProperty("ng.web.url", "") + "ureport/excel?"
                        + "_u=file:employeeInformation.ureport.xml&url=" + Apih5Properties.getWebUrl()
                        + "&employeeInfoId="+zjTzEmployeeInfo.getEmployeeInfoId();
                String returnNameFile = HttpUtil.getUploadPath("temp") + zjTzEmployeeInfo.getEmployeeName() + "-" + zjTzEmployeeInfo.getIdCard() +".xlsx";
                HttpUtil.httpDownload(downUrl, returnNameFile);
                files[i] = FileUtil.file(returnNameFile);
            }
            // zip文件输出
            ZipUtil.zip(FileUtil.file(HttpUtil.getUploadPath("temp") + zipName), false, files);//D:/workspace-wd/apih5/apih5-web/target/upload/zj_tz/
            String returnPath = HttpUtil.getUploadPathWeb(request, "temp") + zipName;
            return repEntity.ok(returnPath);
        }else {
            return repEntity.layerMessage("no", "无数据导出！");
        }
    }

    @Override
    public ResponseEntity getZjTzEmployeeInfoListSelectPerson(ZjTzEmployeeInfo zjTzEmployeeInfo) {
        if(StrUtil.isEmpty(zjTzEmployeeInfo.getIdCard())){
            return repEntity.ok(new ArrayList<>());
        }
        // 获取数据
        List<ZjTzEmployeeInfo> zjTzEmployeeInfoListSelectPerson = zjTzEmployeeInfoMapper.selectZjTzEmployeeInfoListSelectPerson(zjTzEmployeeInfo);
        return repEntity.ok(zjTzEmployeeInfoListSelectPerson);
    }

	@Override
	public ResponseEntity updateZjTzEmployeeInfoPersonDepart(ZjTzEmployeeInfo zjTzEmployeeInfo) {
		// TODO Auto-generated method stub
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        //获取数据库人员信息 (主表)
        ZjTzEmployeeInfo dbzjTzEmployeeInfo = zjTzEmployeeInfoMapper.selectByPrimaryKey(zjTzEmployeeInfo.getEmployeeInfoId());
        if (dbzjTzEmployeeInfo != null && StrUtil.isNotEmpty(dbzjTzEmployeeInfo.getEmployeeInfoId())) {
        	//原单位为表中存储的现单位
        	dbzjTzEmployeeInfo.setPreProjectId(dbzjTzEmployeeInfo.getProjectId());
        	dbzjTzEmployeeInfo.setPreProjectName(dbzjTzEmployeeInfo.getCompanyName());
        	//现单位为前端传递来的单位
        	dbzjTzEmployeeInfo.setProjectId(zjTzEmployeeInfo.getProjectId());
        	dbzjTzEmployeeInfo.setCompanyName(zjTzEmployeeInfo.getCompanyName());
        	// 共通
            dbzjTzEmployeeInfo.setModifyUserInfo(userKey, realName);
        }
        flag = zjTzEmployeeInfoMapper.updateByPrimaryKey(dbzjTzEmployeeInfo);
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzEmployeeInfo);
        }
	}

    @Override
    public ResponseEntity getZjTzEmployeeInfoByIdCard(ZjTzEmployeeInfo zjTzEmployeeInfo) {
        if(zjTzEmployeeInfo != null){
            if(StrUtil.isNotEmpty(zjTzEmployeeInfo.getIdCard())){
                if(IdcardUtil.isValidCard(zjTzEmployeeInfo.getIdCard())){
                    // 获取数据
                    ZjTzEmployeeInfo dbzjTzEmployeeInfo = zjTzEmployeeInfoMapper.getZjTzEmployeeInfoByIdCard(zjTzEmployeeInfo);
                    if(dbzjTzEmployeeInfo != null){
                        if(StrUtil.equals(dbzjTzEmployeeInfo.getProjectId(),"000000")){
                            dbzjTzEmployeeInfo.setType(1);
                            return repEntity.layerMessage(true,dbzjTzEmployeeInfo,"已检测到以下相关人员，是否调入？");
                        }
                        //项目相同
                        if(StrUtil.equals(dbzjTzEmployeeInfo.getProjectId(),zjTzEmployeeInfo.getProjectId())){
                            dbzjTzEmployeeInfo.setType(4);
                            return repEntity.layerMessage(false,dbzjTzEmployeeInfo,"该人员已在本项目，请勿重复添加");
                        }else {
                            dbzjTzEmployeeInfo.setType(3);
                            return repEntity.layerMessage(false,dbzjTzEmployeeInfo,"已有重复人员，需要对方项目调出后才可调入该人员");
                        }
                    }
                    ZjTzEmployeeInfo zjTzEmployeeInfo1 = new ZjTzEmployeeInfo();
                    zjTzEmployeeInfo1.setType(2);
                    return repEntity.layerMessage(true,zjTzEmployeeInfo1,"没有检测到重复人员，请继续录入");
                }
            }
        }
        ZjTzEmployeeInfo zjTzEmployeeInfoError = new ZjTzEmployeeInfo();
        zjTzEmployeeInfoError.setType(5);
        return repEntity.layerMessage(false,zjTzEmployeeInfoError,"请输入正确的身份证号");
    }

    @Override
    public ResponseEntity importProjectZjTzEmployeeInfo(ZjTzEmployeeInfo zjTzEmployeeInfo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if(zjTzEmployeeInfo.getProjectEmployeeId()!=null){
            //如果有项目人员表的信息的话,往项目人员表中添加
            ZjTzProjectAndEmployee zjTzProjectAndEmployee = new ZjTzProjectAndEmployee();
            //人员id
            zjTzProjectAndEmployee.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
            //项目id
            zjTzProjectAndEmployee.setProjectId(zjTzEmployeeInfo.getProjectId());
            //项目人员id
            zjTzProjectAndEmployee.setProjectEmployeeId(zjTzEmployeeInfo.getProjectEmployeeId());
            //项目人员关联id
            zjTzProjectAndEmployee.setProjectAndEmployeeId(UuidUtil.generate());
            zjTzProjectAndEmployee.setCreateUserInfo(userKey, realName);
            //添加项目人员关联数据
            flag += zjTzProjectAndEmployeeMapper.insert(zjTzProjectAndEmployee);
            //查询人数
            ZjTzProjectAndEmployee zjTzProjectAndEmployee1 = new ZjTzProjectAndEmployee();
            zjTzProjectAndEmployee1.setProjectEmployeeId(zjTzEmployeeInfo.getProjectEmployeeId());
            List<ZjTzProjectAndEmployee> zjTzProjectAndEmployees = zjTzProjectAndEmployeeMapper.selectByZjTzProjectAndEmployeeList(zjTzProjectAndEmployee1);
            //修改人数
            ZjTzProjectEmployee zjTzProjectEmployee = new ZjTzProjectEmployee();
            zjTzProjectEmployee.setEmployeeNumber(zjTzProjectAndEmployees.size());
            zjTzProjectEmployee.setProjectEmployeeId(zjTzEmployeeInfo.getProjectEmployeeId());
            flag += zjTzProjectEmployeeMapper.updataPersonNumber(zjTzProjectEmployee);
            //修改人员项目信息
            ZjTzEmployeeInfo dbzjTzEmployeeInfo = zjTzEmployeeInfoMapper.selectByPrimaryKey(zjTzEmployeeInfo.getEmployeeInfoId());
            dbzjTzEmployeeInfo.setProjectId(zjTzEmployeeInfo.getProjectId());
            dbzjTzEmployeeInfo.setProjectName(zjTzEmployeeInfo.getProjectName());
            //调入时间
            dbzjTzEmployeeInfo.setJoinTime(new Date());
            this.updateZjTzEmployeeInfo(dbzjTzEmployeeInfo);
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.layerMessage(true,zjTzEmployeeInfo,"已经成功调入人员至本项目");
        }
    }

    @Override
    public ResponseEntity importProjectZjTzEmployeeInfoList(List<ZjTzEmployeeInfo> zjTzEmployeeInfoList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if(zjTzEmployeeInfoList != null && zjTzEmployeeInfoList.size()>0){
            //刷选一下人员是否有项目
            List<ZjTzEmployeeInfo> dbzjTzEmployeeInfoList = zjTzEmployeeInfoMapper.isProject(zjTzEmployeeInfoList);
            if(dbzjTzEmployeeInfoList!=null && dbzjTzEmployeeInfoList.size()>0){
                return repEntity.layerMessage(false,dbzjTzEmployeeInfoList,"无法调入以下人员，其已存在于本项目内或未从其他项目调出");
            }
            for (ZjTzEmployeeInfo zjTzEmployeeInfo : zjTzEmployeeInfoList) {
                if(zjTzEmployeeInfo.getProjectEmployeeId() != null){
                    //如果有项目人员表的信息的话,往项目人员表中添加
                    ZjTzProjectAndEmployee zjTzProjectAndEmployee = new ZjTzProjectAndEmployee();
                    //人员id
                    zjTzProjectAndEmployee.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                    //项目id
                    zjTzProjectAndEmployee.setProjectId(zjTzEmployeeInfo.getProjectId());
                    //项目人员id
                    zjTzProjectAndEmployee.setProjectEmployeeId(zjTzEmployeeInfo.getProjectEmployeeId());
                    //项目人员关联id
                    zjTzProjectAndEmployee.setProjectAndEmployeeId(UuidUtil.generate());
                    zjTzProjectAndEmployee.setCreateUserInfo(userKey, realName);
                    //添加项目人员关联数据
                    flag += zjTzProjectAndEmployeeMapper.insert(zjTzProjectAndEmployee);
                    //查询人数
                    ZjTzProjectAndEmployee zjTzProjectAndEmployee1 = new ZjTzProjectAndEmployee();
                    zjTzProjectAndEmployee1.setProjectEmployeeId(zjTzEmployeeInfo.getProjectEmployeeId());
                    List<ZjTzProjectAndEmployee> zjTzProjectAndEmployees = zjTzProjectAndEmployeeMapper.selectByZjTzProjectAndEmployeeList(zjTzProjectAndEmployee1);
                    //修改人数
                    ZjTzProjectEmployee zjTzProjectEmployee = new ZjTzProjectEmployee();
                    zjTzProjectEmployee.setEmployeeNumber(zjTzProjectAndEmployees.size());
                    zjTzProjectEmployee.setProjectEmployeeId(zjTzEmployeeInfo.getProjectEmployeeId());
                    flag += zjTzProjectEmployeeMapper.updataPersonNumber(zjTzProjectEmployee);
                    //修改人员项目信息
                    ZjTzEmployeeInfo dbzjTzEmployeeInfo = zjTzEmployeeInfoMapper.selectByPrimaryKey(zjTzEmployeeInfo.getEmployeeInfoId());
                    dbzjTzEmployeeInfo.setProjectId(zjTzEmployeeInfo.getProjectId());
                    dbzjTzEmployeeInfo.setProjectName(zjTzEmployeeInfo.getProjectName());
                    //调入时间
                    dbzjTzEmployeeInfo.setJoinTime(new Date());
                    this.updateZjTzEmployeeInfo(dbzjTzEmployeeInfo);
                }
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.layerMessage(true,zjTzEmployeeInfoList,"已经成功调入人员至本项目");
        }
    }

    //在项目中人员离职
    //  1.项目修改人数
    //  2.项目人员关联 数据删除
    //  3.人员数据删除
    @Override
    public ResponseEntity leaveOfficeZjTzEmployeeInfoList(List<ZjTzProjectAndEmployee> zjTzProjectAndEmployeeList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzProjectAndEmployee dbZjTzProjectAndEmployee = new ZjTzProjectAndEmployee();
        if (zjTzProjectAndEmployeeList != null && zjTzProjectAndEmployeeList.size() > 0) {
            //先查询Id
            dbZjTzProjectAndEmployee = zjTzProjectAndEmployeeMapper.selectByPrimaryKey(zjTzProjectAndEmployeeList.get(0).getProjectAndEmployeeId());
            //删除 项目和人员的关联
            ZjTzProjectAndEmployee zjTzProjectAndEmployee = new ZjTzProjectAndEmployee();
            zjTzProjectAndEmployee.setModifyUserInfo(userKey, realName);
            flag = zjTzProjectAndEmployeeMapper.batchDeleteUpdateZjTzProjectAndEmployee(zjTzProjectAndEmployeeList, zjTzProjectAndEmployee);
            //删除 人员数据
            ZjTzEmployeeInfo zjTzEmployeeInfo = new ZjTzEmployeeInfo();
            zjTzEmployeeInfo.setModifyUserInfo(userKey, realName);
            List<ZjTzEmployeeInfo> zjTzEmployeeInfoList = zjTzProjectAndEmployeeList.stream().map(o -> {
                ZjTzEmployeeInfo zjTzEmployeeInfo1 = new ZjTzEmployeeInfo();
                zjTzEmployeeInfo1.setEmployeeInfoId(o.getEmployeeInfoId());
                return zjTzEmployeeInfo1;
            }).collect(Collectors.toList());
            flag = zjTzEmployeeInfoMapper.batchDeleteUpdateZjTzEmployeeInfo(zjTzEmployeeInfoList, zjTzEmployeeInfo);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            if(dbZjTzProjectAndEmployee != null){
                //查询人数
                ZjTzProjectAndEmployee zjTzProjectAndEmployee = new ZjTzProjectAndEmployee();
                zjTzProjectAndEmployee.setProjectEmployeeId(dbZjTzProjectAndEmployee.getProjectEmployeeId());
                List<ZjTzProjectAndEmployee> zjTzProjectAndEmployees = zjTzProjectAndEmployeeMapper.selectByZjTzProjectAndEmployeeList(zjTzProjectAndEmployee);
                //修改人数
                ZjTzProjectEmployee zjTzProjectEmployee = new ZjTzProjectEmployee();
                zjTzProjectEmployee.setEmployeeNumber(zjTzProjectAndEmployees.size());
                zjTzProjectEmployee.setProjectEmployeeId(dbZjTzProjectAndEmployee.getProjectEmployeeId());
                zjTzProjectEmployeeMapper.updataPersonNumber(zjTzProjectEmployee);
            }
            return repEntity.ok("sys.data.delete",zjTzProjectAndEmployeeList);
        }
    }
}
