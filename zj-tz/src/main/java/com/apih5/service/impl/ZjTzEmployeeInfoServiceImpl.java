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
        // ????????????
        PageHelper.startPage(zjTzEmployeeInfo.getPage(),zjTzEmployeeInfo.getLimit());
        // ????????????
        List<ZjTzEmployeeInfo> zjTzEmployeeInfoList = zjTzEmployeeInfoMapper.selectByZjTzEmployeeInfoListAll(zjTzEmployeeInfo);
        for (ZjTzEmployeeInfo zjTzEmployeeInfo2 : zjTzEmployeeInfoList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzEmployeeInfo2.getEmployeeInfoId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzEmployeeInfo2.setZjTzFileList(zjTzFileList);
		}
        // ??????????????????
        PageInfo<ZjTzEmployeeInfo> p = new PageInfo<>(zjTzEmployeeInfoList);
        return repEntity.okList(zjTzEmployeeInfoList, p.getTotal());
    }

    //???????????????(?????????)
    @Override
    public ResponseEntity getZjTzEmployeeInfoDetails(ZjTzEmployeeInfo zjTzEmployeeInfo) {
        if (zjTzEmployeeInfo == null) {
            zjTzEmployeeInfo = new ZjTzEmployeeInfo();
        }
        // ????????????
        ZjTzEmployeeInfo dbZjTzEmployeeInfo = zjTzEmployeeInfoMapper.selectByPrimaryKey(zjTzEmployeeInfo.getEmployeeInfoId());
        // ????????????
        if (dbZjTzEmployeeInfo != null) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(dbZjTzEmployeeInfo.getEmployeeInfoId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzEmployeeInfo.setZjTzFileList(zjTzFileList);
            return repEntity.ok(dbZjTzEmployeeInfo);
        }
        else {
            return repEntity.layerMessage("no", "????????????");
        }
    }

    @Override
    public ResponseEntity saveZjTzEmployeeInfo(ZjTzEmployeeInfo zjTzEmployeeInfo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        //??????????????????????????????????????????
        ZjTzEmployeeInfo zjTzEmployeeInfo1 = zjTzEmployeeInfoMapper.selectByIdCard(zjTzEmployeeInfo.getIdCard());
        if(zjTzEmployeeInfo1!=null){
            return repEntity.layerMessage("no","??????????????????");
        }
        zjTzEmployeeInfo.setEmployeeInfoId(UuidUtil.generate());
        zjTzEmployeeInfo.setCreateUserInfo(userKey, realName);
        int flag = zjTzEmployeeInfoMapper.insert(zjTzEmployeeInfo);
        // ??????list
        List<ZjTzFile> zjTzFileList = zjTzEmployeeInfo.getZjTzFileList();
        if(zjTzFileList != null && zjTzFileList.size()>0) {
        	for(ZjTzFile zjTzFile:zjTzFileList) {
        		zjTzFile.setUid(UuidUtil.generate());
        		zjTzFile.setOtherId(zjTzEmployeeInfo.getEmployeeInfoId());
        		zjTzFile.setCreateUserInfo(userKey, realName);
        		zjTzFileMapper.insert(zjTzFile);
        	}
        }
        //???(???)??????????????????(??????????????????????????????????????????)
        List<ZjTzEmployeeQualification> qualificationList = zjTzEmployeeInfo.getQualificationList();
        if(qualificationList != null && qualificationList.size()>0) {
            for(ZjTzEmployeeQualification zjTzEmployeeQualification : qualificationList) {
                zjTzEmployeeQualification.setQualificationId(UuidUtil.generate());
                zjTzEmployeeQualification.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                zjTzEmployeeQualification.setCreateUserInfo(userKey, realName);
                zjTzEmployeeQualificationMapper.insert(zjTzEmployeeQualification);
            }
        }
        //?????????????????????(????????????????????????????????????)
        List<ZjTzEmployeeProfessional> professionalList = zjTzEmployeeInfo.getProfessionalList();
        if(professionalList != null && professionalList.size()>0) {
            for(ZjTzEmployeeProfessional zjTzEmployeeProfessional : professionalList) {
                zjTzEmployeeProfessional.setProfessionalId(UuidUtil.generate());
                zjTzEmployeeProfessional.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                zjTzEmployeeProfessional.setCreateUserInfo(userKey, realName);
                zjTzEmployeeProfessionalMapper.insert(zjTzEmployeeProfessional);
            }
        }
        //?????????????????????(?????????????????????????????????)
        List<ZjTzEmployeeWork> workList = zjTzEmployeeInfo.getWorkList();
        if(workList != null && workList.size()>0) {
            for(ZjTzEmployeeWork zjTzEmployeeWork : workList) {
                zjTzEmployeeWork.setWorkId(UuidUtil.generate());
                zjTzEmployeeWork.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                zjTzEmployeeWork.setCreateUserInfo(userKey, realName);
                zjTzEmployeeWorkMapper.insert(zjTzEmployeeWork);
            }
        }
        //?????????????????????(?????????????????????)
//        List<ZjTzEmployeePolitics> politicsList = zjTzEmployeeInfo.getPoliticsList();
//        if(politicsList != null && politicsList.size()>0) {
//            for(ZjTzEmployeePolitics zjTzEmployeePolitics : politicsList) {
//                zjTzEmployeePolitics.setPoliticsId(UuidUtil.generate());
//                zjTzEmployeePolitics.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
//                zjTzEmployeePolitics.setCreateUserInfo(userKey, realName);
//                zjTzEmployeePoliticsMapper.insert(zjTzEmployeePolitics);
//            }
//        }
        //?????????????????????(????????????????????????????????????)
        List<ZjTzEmployeeEducation> educationList = zjTzEmployeeInfo.getEducationList();
        if(educationList != null && educationList.size()>0) {
            for(ZjTzEmployeeEducation zjTzEmployeeEducation : educationList) {
                zjTzEmployeeEducation.setEducationId(UuidUtil.generate());
                zjTzEmployeeEducation.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                zjTzEmployeeEducation.setCreateUserInfo(userKey, realName);
                zjTzEmployeeEducationMapper.insert(zjTzEmployeeEducation);
            }
        }
        //?????????????????????
        List<ZjTzEmployeeAward> awardList = zjTzEmployeeInfo.getAwardList();
        if(awardList != null && awardList.size()>0) {
            for(ZjTzEmployeeAward zjTzEmployeeAward : awardList) {
                zjTzEmployeeAward.setAwardId(UuidUtil.generate());
                zjTzEmployeeAward.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                zjTzEmployeeAward.setCreateUserInfo(userKey, realName);
                zjTzEmployeeAwardMapper.insert(zjTzEmployeeAward);
            }
        }
        //?????????????????????
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
            //???????????????????????????????????????,???????????????????????????
            ZjTzProjectAndEmployee zjTzProjectAndEmployee = new ZjTzProjectAndEmployee();
            //??????id
            zjTzProjectAndEmployee.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
            //??????id
            zjTzProjectAndEmployee.setProjectId(zjTzEmployeeInfo.getProjectId());
            //????????????id
            zjTzProjectAndEmployee.setProjectEmployeeId(zjTzEmployeeInfo.getProjectEmployeeId());
            //??????????????????id
            zjTzProjectAndEmployee.setProjectAndEmployeeId(UuidUtil.generate());
            zjTzProjectAndEmployee.setCreateUserInfo(userKey, realName);
            zjTzProjectAndEmployeeMapper.insert(zjTzProjectAndEmployee);
            //????????????
            ZjTzProjectAndEmployee zjTzProjectAndEmployee1 = new ZjTzProjectAndEmployee();
            zjTzProjectAndEmployee1.setProjectEmployeeId(zjTzEmployeeInfo.getProjectEmployeeId());
            List<ZjTzProjectAndEmployee> zjTzProjectAndEmployees = zjTzProjectAndEmployeeMapper.selectByZjTzProjectAndEmployeeList(zjTzProjectAndEmployee1);
            //????????????
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
           // ????????????
           dbzjTzEmployeeInfo.setCompanyName(zjTzEmployeeInfo.getCompanyName());
           // ????????????????????????
           dbzjTzEmployeeInfo.setPersonnelUnit(zjTzEmployeeInfo.getPersonnelUnit());
           // ????????????
           dbzjTzEmployeeInfo.setDepartmentName(zjTzEmployeeInfo.getDepartmentName());
           // ?????????????????????
           dbzjTzEmployeeInfo.setJoinTime(zjTzEmployeeInfo.getJoinTime());
           // ??????
           dbzjTzEmployeeInfo.setEmployeeName(zjTzEmployeeInfo.getEmployeeName());
           // ???????????????
           dbzjTzEmployeeInfo.setIdCard(zjTzEmployeeInfo.getIdCard());
           // ????????????
           dbzjTzEmployeeInfo.setBirthDate(zjTzEmployeeInfo.getBirthDate());
           // ??????
           dbzjTzEmployeeInfo.setNativePlace(zjTzEmployeeInfo.getNativePlace());
           // ??????
           dbzjTzEmployeeInfo.setSex(zjTzEmployeeInfo.getSex());
           // ??????
           dbzjTzEmployeeInfo.setNation(zjTzEmployeeInfo.getNation());
           // ??????????????????
           dbzjTzEmployeeInfo.setJobTime(zjTzEmployeeInfo.getJobTime());
           // ??????????????????
           dbzjTzEmployeeInfo.setEnterPriseTime(zjTzEmployeeInfo.getEnterPriseTime());
           // ????????????
           dbzjTzEmployeeInfo.setMarital(zjTzEmployeeInfo.getMarital());
           // ????????????
           dbzjTzEmployeeInfo.setHealth(zjTzEmployeeInfo.getHealth());
           // ???????????????
           dbzjTzEmployeeInfo.setRegisteredResidence(zjTzEmployeeInfo.getRegisteredResidence());
           // ????????????
           dbzjTzEmployeeInfo.setPost(zjTzEmployeeInfo.getPost());
           // ????????????id
           dbzjTzEmployeeInfo.setEmployeeTypeId(zjTzEmployeeInfo.getEmployeeTypeId());
           // ????????????id
           dbzjTzEmployeeInfo.setLaborTypeId(zjTzEmployeeInfo.getLaborTypeId());
           // ????????????
           dbzjTzEmployeeInfo.setPhone(zjTzEmployeeInfo.getPhone());
           // ????????????
           dbzjTzEmployeeInfo.setMailBox(zjTzEmployeeInfo.getMailBox());
           // ????????????id
           dbzjTzEmployeeInfo.setContractCharacterId(zjTzEmployeeInfo.getContractCharacterId());
           // ????????????id
           dbzjTzEmployeeInfo.setPoliticalId(zjTzEmployeeInfo.getPoliticalId());
           // ??????????????????id
           dbzjTzEmployeeInfo.setLeaderCategoryId(zjTzEmployeeInfo.getLeaderCategoryId());
           // ??????????????????
           dbzjTzEmployeeInfo.setAttendPartyTime(zjTzEmployeeInfo.getAttendPartyTime());
           // ??????id
           dbzjTzEmployeeInfo.setProjectId(zjTzEmployeeInfo.getProjectId());
           // ????????????
           dbzjTzEmployeeInfo.setProjectName(zjTzEmployeeInfo.getProjectName());
           // ?????????id
           dbzjTzEmployeeInfo.setPreProjectId(zjTzEmployeeInfo.getPreProjectId());
           // ???????????????
           dbzjTzEmployeeInfo.setPreProjectName(zjTzEmployeeInfo.getPreProjectName());
           // ????????????????????????
           dbzjTzEmployeeInfo.setPreJoinTime(zjTzEmployeeInfo.getPreJoinTime());
           // ??????
           dbzjTzEmployeeInfo.setModifyUserInfo(userKey, realName);
           flag = zjTzEmployeeInfoMapper.updateByPrimaryKey(dbzjTzEmployeeInfo);
            // ?????????????????????
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzEmployeeInfo.getEmployeeInfoId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
               zjTzFileSelect.setModifyUserInfo(userKey, realName);
               zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           // ??????list
           List<ZjTzFile> zjTzFileList = zjTzEmployeeInfo.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
               for(ZjTzFile zjTzFile:zjTzFileList) {
                   zjTzFile.setUid(UuidUtil.generate());
                   zjTzFile.setOtherId(zjTzEmployeeInfo.getEmployeeInfoId());
                   zjTzFile.setCreateUserInfo(userKey, realName);
                   zjTzFileMapper.insert(zjTzFile);
               }
           }
            //??????????????????(????????????)
            //???(???)??????????????????(??????????????????????????????????????????)
            ZjTzEmployeeQualification zjTzEmployeeQualification = new ZjTzEmployeeQualification();
            zjTzEmployeeQualification.setEmployeeInfoId(dbzjTzEmployeeInfo.getEmployeeInfoId());
            List<ZjTzEmployeeQualification> zjTzEmployeeQualifications = zjTzEmployeeQualificationMapper.selectByZjTzEmployeeQualificationList(zjTzEmployeeQualification);
            if(zjTzEmployeeQualifications != null && zjTzEmployeeQualifications.size()>0) {
                zjTzEmployeeQualification.setModifyUserInfo(userKey, realName);
                zjTzEmployeeQualificationMapper.batchDeleteUpdateZjTzEmployeeQualification(zjTzEmployeeQualifications, zjTzEmployeeQualification);
            }
            // ???(???)???????????????list
            List<ZjTzEmployeeQualification> zjTzEmployeeInfoQualificationList = zjTzEmployeeInfo.getQualificationList();
            if(zjTzEmployeeInfoQualificationList != null && zjTzEmployeeInfoQualificationList.size()>0) {
                for(ZjTzEmployeeQualification zjTzEmployeeQualification1:zjTzEmployeeInfoQualificationList) {
                    zjTzEmployeeQualification1.setQualificationId(UuidUtil.generate());
                    zjTzEmployeeQualification1.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                    zjTzEmployeeQualification1.setCreateUserInfo(userKey, realName);
                    zjTzEmployeeQualificationMapper.insert(zjTzEmployeeQualification1);
                }
            }
            //?????????????????????(????????????????????????????????????)professionalList
            ZjTzEmployeeProfessional zjTzEmployeeProfessional = new ZjTzEmployeeProfessional();
            zjTzEmployeeProfessional.setEmployeeInfoId(dbzjTzEmployeeInfo.getEmployeeInfoId());
            List<ZjTzEmployeeProfessional> zjTzEmployeeProfessionals = zjTzEmployeeProfessionalMapper.selectByZjTzEmployeeProfessionalList(zjTzEmployeeProfessional);
            if(zjTzEmployeeProfessionals != null && zjTzEmployeeProfessionals.size()>0) {
                zjTzEmployeeProfessional.setModifyUserInfo(userKey, realName);
                zjTzEmployeeProfessionalMapper.batchDeleteUpdateZjTzEmployeeProfessional(zjTzEmployeeProfessionals, zjTzEmployeeProfessional);
            }
            //?????????????????????
            List<ZjTzEmployeeProfessional> professionalList = zjTzEmployeeInfo.getProfessionalList();
            if(professionalList != null && professionalList.size()>0) {
                for(ZjTzEmployeeProfessional zjTzEmployeeProfessional1:professionalList) {
                    zjTzEmployeeProfessional1.setProfessionalId(UuidUtil.generate());
                    zjTzEmployeeProfessional1.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                    zjTzEmployeeProfessional1.setCreateUserInfo(userKey, realName);
                    zjTzEmployeeProfessionalMapper.insert(zjTzEmployeeProfessional1);
                }
            }
            //?????????????????????(?????????????????????????????????)
            ZjTzEmployeeWork zjTzEmployeeWork1 = new ZjTzEmployeeWork();
            zjTzEmployeeWork1.setEmployeeInfoId(dbzjTzEmployeeInfo.getEmployeeInfoId());
            List<ZjTzEmployeeWork> zjTzEmployeeWorks = zjTzEmployeeWorkMapper.selectByZjTzEmployeeWorkList(zjTzEmployeeWork1);
            if(zjTzEmployeeWorks != null && zjTzEmployeeWorks.size()>0) {
                zjTzEmployeeWork1.setModifyUserInfo(userKey, realName);
                zjTzEmployeeWorkMapper.batchDeleteUpdateZjTzEmployeeWork(zjTzEmployeeWorks, zjTzEmployeeWork1);
            }
            //??????????????????list
            List<ZjTzEmployeeWork> workList = zjTzEmployeeInfo.getWorkList();
            if(workList != null && workList.size()>0) {
                for(ZjTzEmployeeWork zjTzEmployeeWork : workList) {
                    zjTzEmployeeWork.setWorkId(UuidUtil.generate());
                    zjTzEmployeeWork.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                    zjTzEmployeeWork.setCreateUserInfo(userKey, realName);
                    zjTzEmployeeWorkMapper.insert(zjTzEmployeeWork);
                }
            }
            //?????????????????????
//            ZjTzEmployeePolitics zjTzEmployeePolitics1 = new ZjTzEmployeePolitics();
//            zjTzEmployeePolitics1.setEmployeeInfoId(dbzjTzEmployeeInfo.getEmployeeInfoId());
//            List<ZjTzEmployeePolitics> zjTzEmployeePolitics2 = zjTzEmployeePoliticsMapper.selectByZjTzEmployeePoliticsList(zjTzEmployeePolitics1);
//            if(zjTzEmployeePolitics2 != null && zjTzEmployeePolitics2.size()>0) {
//                zjTzEmployeePolitics1.setModifyUserInfo(userKey, realName);
//                zjTzEmployeePoliticsMapper.batchDeleteUpdateZjTzEmployeePolitics(zjTzEmployeePolitics2, zjTzEmployeePolitics1);
//            }
            //?????????????????????(?????????????????????)
//            List<ZjTzEmployeePolitics> politicsList = zjTzEmployeeInfo.getPoliticsList();
//            if(politicsList != null && politicsList.size()>0) {
//                for(ZjTzEmployeePolitics zjTzEmployeePolitics : politicsList) {
//                    zjTzEmployeePolitics.setPoliticsId(UuidUtil.generate());
//                    zjTzEmployeePolitics.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
//                    zjTzEmployeePolitics.setCreateUserInfo(userKey, realName);
//                    zjTzEmployeePoliticsMapper.insert(zjTzEmployeePolitics);
//                }
//            }
            //?????????????????????(????????????????????????????????????)
            ZjTzEmployeeEducation zjTzEmployeeEducation1 = new ZjTzEmployeeEducation();
            zjTzEmployeeEducation1.setEmployeeInfoId(dbzjTzEmployeeInfo.getEmployeeInfoId());
            List<ZjTzEmployeeEducation> zjTzEmployeeEducations = zjTzEmployeeEducationMapper.selectByZjTzEmployeeEducationList(zjTzEmployeeEducation1);
            if(zjTzEmployeeEducations != null && zjTzEmployeeEducations.size()>0) {
                zjTzEmployeeEducation1.setModifyUserInfo(userKey, realName);
                zjTzEmployeeEducationMapper.batchDeleteUpdateZjTzEmployeeEducation(zjTzEmployeeEducations, zjTzEmployeeEducation1);
            }
            //?????????????????????(????????????????????????????????????)
            List<ZjTzEmployeeEducation> educationList = zjTzEmployeeInfo.getEducationList();
            if(educationList != null && educationList.size()>0) {
                for(ZjTzEmployeeEducation zjTzEmployeeEducation : educationList) {
                    zjTzEmployeeEducation.setEducationId(UuidUtil.generate());
                    zjTzEmployeeEducation.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                    zjTzEmployeeEducation.setCreateUserInfo(userKey, realName);
                    zjTzEmployeeEducationMapper.insert(zjTzEmployeeEducation);
                }
            }
            //?????????????????????
            ZjTzEmployeeAward zjTzEmployeeAward1 = new ZjTzEmployeeAward();
            zjTzEmployeeAward1.setEmployeeInfoId(dbzjTzEmployeeInfo.getEmployeeInfoId());
            List<ZjTzEmployeeAward> zjTzEmployeeAwards = zjTzEmployeeAwardMapper.selectByZjTzEmployeeAwardList(zjTzEmployeeAward1);
            if(zjTzEmployeeAwards != null && zjTzEmployeeAwards.size()>0) {
                zjTzEmployeeAward1.setModifyUserInfo(userKey, realName);
                zjTzEmployeeAwardMapper.batchDeleteUpdateZjTzEmployeeAward(zjTzEmployeeAwards, zjTzEmployeeAward1);
            }
            //?????????????????????
            List<ZjTzEmployeeAward> awardList = zjTzEmployeeInfo.getAwardList();
            if(awardList != null && awardList.size()>0) {
                for(ZjTzEmployeeAward zjTzEmployeeAward : awardList) {
                    zjTzEmployeeAward.setAwardId(UuidUtil.generate());
                    zjTzEmployeeAward.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                    zjTzEmployeeAward.setCreateUserInfo(userKey, realName);
                    zjTzEmployeeAwardMapper.insert(zjTzEmployeeAward);
                }
            }
            //?????????????????????
//            ZjTzEmployeeLanguage zjTzEmployeeLanguage1 = new ZjTzEmployeeLanguage();
//            zjTzEmployeeLanguage1.setEmployeeInfoId(dbzjTzEmployeeInfo.getEmployeeInfoId());
//            List<ZjTzEmployeeLanguage> zjTzEmployeeLanguages = zjTzEmployeeLanguageMapper.selectByZjTzEmployeeLanguageList(zjTzEmployeeLanguage1);
//            if(zjTzEmployeeLanguages != null && zjTzEmployeeLanguages.size()>0) {
//                zjTzEmployeeLanguage1.setModifyUserInfo(userKey, realName);
//                zjTzEmployeeLanguageMapper.batchDeleteUpdateZjTzEmployeeLanguage(zjTzEmployeeLanguages, zjTzEmployeeLanguage1);
//            }
            //?????????????????????
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
        // ??????
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
        		// ????????????
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzEmployeeInfo.getEmployeeInfoId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
                //???(???)??????????????????(??????????????????????????????????????????)
                ZjTzEmployeeQualification zjTzEmployeeQualification = new ZjTzEmployeeQualification();
                zjTzEmployeeQualification.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                List<ZjTzEmployeeQualification> zjTzEmployeeQualifications = zjTzEmployeeQualificationMapper.selectByZjTzEmployeeQualificationList(zjTzEmployeeQualification);
                if(zjTzEmployeeQualifications != null && zjTzEmployeeQualifications.size()>0) {
                    zjTzEmployeeQualification.setModifyUserInfo(userKey, realName);
                    zjTzEmployeeQualificationMapper.batchDeleteUpdateZjTzEmployeeQualification(zjTzEmployeeQualifications, zjTzEmployeeQualification);
                }
                //?????????????????????(????????????????????????????????????)professionalList
                ZjTzEmployeeProfessional zjTzEmployeeProfessional = new ZjTzEmployeeProfessional();
                zjTzEmployeeProfessional.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                List<ZjTzEmployeeProfessional> zjTzEmployeeProfessionals = zjTzEmployeeProfessionalMapper.selectByZjTzEmployeeProfessionalList(zjTzEmployeeProfessional);
                if(zjTzEmployeeProfessionals != null && zjTzEmployeeProfessionals.size()>0) {
                    zjTzEmployeeProfessional.setModifyUserInfo(userKey, realName);
                    zjTzEmployeeProfessionalMapper.batchDeleteUpdateZjTzEmployeeProfessional(zjTzEmployeeProfessionals, zjTzEmployeeProfessional);
                }
                //?????????????????????(?????????????????????????????????)
                ZjTzEmployeeWork zjTzEmployeeWork1 = new ZjTzEmployeeWork();
                zjTzEmployeeWork1.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                List<ZjTzEmployeeWork> zjTzEmployeeWorks = zjTzEmployeeWorkMapper.selectByZjTzEmployeeWorkList(zjTzEmployeeWork1);
                if(zjTzEmployeeWorks != null && zjTzEmployeeWorks.size()>0) {
                    zjTzEmployeeWork1.setModifyUserInfo(userKey, realName);
                    zjTzEmployeeWorkMapper.batchDeleteUpdateZjTzEmployeeWork(zjTzEmployeeWorks, zjTzEmployeeWork1);
                }
                //?????????????????????
//                ZjTzEmployeePolitics zjTzEmployeePolitics1 = new ZjTzEmployeePolitics();
//                zjTzEmployeePolitics1.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
//                List<ZjTzEmployeePolitics> zjTzEmployeePolitics2 = zjTzEmployeePoliticsMapper.selectByZjTzEmployeePoliticsList(zjTzEmployeePolitics1);
//                if(zjTzEmployeePolitics2 != null && zjTzEmployeePolitics2.size()>0) {
//                    zjTzEmployeePolitics1.setModifyUserInfo(userKey, realName);
//                    zjTzEmployeePoliticsMapper.batchDeleteUpdateZjTzEmployeePolitics(zjTzEmployeePolitics2, zjTzEmployeePolitics1);
//                }
                //?????????????????????(????????????????????????????????????)
                ZjTzEmployeeEducation zjTzEmployeeEducation1 = new ZjTzEmployeeEducation();
                zjTzEmployeeEducation1.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                List<ZjTzEmployeeEducation> zjTzEmployeeEducations = zjTzEmployeeEducationMapper.selectByZjTzEmployeeEducationList(zjTzEmployeeEducation1);
                if(zjTzEmployeeEducations != null && zjTzEmployeeEducations.size()>0) {
                    zjTzEmployeeEducation1.setModifyUserInfo(userKey, realName);
                    zjTzEmployeeEducationMapper.batchDeleteUpdateZjTzEmployeeEducation(zjTzEmployeeEducations, zjTzEmployeeEducation1);
                }
                //?????????????????????
                ZjTzEmployeeAward zjTzEmployeeAward1 = new ZjTzEmployeeAward();
                zjTzEmployeeAward1.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                List<ZjTzEmployeeAward> zjTzEmployeeAwards = zjTzEmployeeAwardMapper.selectByZjTzEmployeeAwardList(zjTzEmployeeAward1);
                if(zjTzEmployeeAwards != null && zjTzEmployeeAwards.size()>0) {
                    zjTzEmployeeAward1.setModifyUserInfo(userKey, realName);
                    zjTzEmployeeAwardMapper.batchDeleteUpdateZjTzEmployeeAward(zjTzEmployeeAwards, zjTzEmployeeAward1);
                }
                //?????????????????????
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
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzEmployeeInfoList);
        }
    }

    //??????????????????
    @Override
    public ZjTzEmployeeInfo exportZjTzEmployeeInfo(ZjTzEmployeeInfo zjTzEmployeeInfo) {
        if(zjTzEmployeeInfo == null){
            return null;
        }
        if(StrUtil.isNotEmpty(zjTzEmployeeInfo.getEmployeeInfoId())){
            ZjTzEmployeeInfo dbzjTzEmployeeInfo = zjTzEmployeeInfoMapper.selectByPrimaryKey(zjTzEmployeeInfo.getEmployeeInfoId());
            // ????????????
            // politicalId
            // private String politicalName;
            if (StrUtil.isNotEmpty(dbzjTzEmployeeInfo.getPoliticalId())) {
                String politicalName = baseCodeService.getBaseCodeItemName("zhengZhiMianMao", dbzjTzEmployeeInfo.getPoliticalId());
                dbzjTzEmployeeInfo.setPoliticalName(politicalName);
            }
            //????????????
            //employeeTypeId
            //private String employeeTypeName;
            if (StrUtil.isNotEmpty(dbzjTzEmployeeInfo.getEmployeeTypeId())) {
                String employeeTypeName = baseCodeService.getBaseCodeItemName("renCaiLeiBie", dbzjTzEmployeeInfo.getEmployeeTypeId());
                dbzjTzEmployeeInfo.setEmployeeTypeName(employeeTypeName);
            }
            //????????????
            //laborTypeId
            //private String laborTypeName;
            if (StrUtil.isNotEmpty(dbzjTzEmployeeInfo.getLaborTypeId())) {
                String laborTypeName = baseCodeService.getBaseCodeItemName("yongGongLeiXing", dbzjTzEmployeeInfo.getLaborTypeId());
                dbzjTzEmployeeInfo.setLaborTypeName(laborTypeName);
            }
            //????????????
            //contractCharacterId
            //private String contractCharacterName;
            if (StrUtil.isNotEmpty(dbzjTzEmployeeInfo.getContractCharacterId())) {
                String contractCharacterName = baseCodeService.getBaseCodeItemName("heTongXingZhi", dbzjTzEmployeeInfo.getContractCharacterId());
                dbzjTzEmployeeInfo.setContractCharacterName(contractCharacterName);
            }
            //zj_tz_employee_professional
            //??????????????? - ???????????????????????? ????????????
            //private String professionalName;
            ZjTzEmployeeProfessional zjTzEmployeeProfessional = new ZjTzEmployeeProfessional();
            zjTzEmployeeProfessional.setEmployeeInfoId(dbzjTzEmployeeInfo.getEmployeeInfoId());
            //?????????????????????
            List<ZjTzEmployeeProfessional> zjTzEmployeeProfessionals = zjTzEmployeeProfessionalMapper.selectByZjTzEmployeeProfessionalList(zjTzEmployeeProfessional);
            //??????????????????
            String professionalName = zjTzEmployeeProfessionals.stream().filter(o -> StrUtil.isNotEmpty(o.getProfessionalNameId())).map(
                    o ->baseCodeService.getBaseCodeItemName("zhuanYeJiShuZiGeMingCheng", o.getProfessionalNameId())
            ).collect(Collectors.joining(","));
            dbzjTzEmployeeInfo.setProfessionalName(professionalName);

            //???????????? -> ??????????????????
            if(dbzjTzEmployeeInfo.getBirthDate()!=null){
                dbzjTzEmployeeInfo.setBirthDateString(DateUtil.format(dbzjTzEmployeeInfo.getBirthDate(),"yyyy-MM-dd"));
            }
            return dbzjTzEmployeeInfo;
        }
        return null;
    }

    //??????????????????
    @Override
    public List<ZjTzEmployeeEducation> exportZjTzEmployeeInfoEducation(ZjTzEmployeeInfo zjTzEmployeeInfo) {
        if(zjTzEmployeeInfo == null){
            return new ArrayList<>();
        }
        if(StrUtil.isNotEmpty(zjTzEmployeeInfo.getEmployeeInfoId())){
            ZjTzEmployeeEducation zjTzEmployeeEducation = new ZjTzEmployeeEducation();
            zjTzEmployeeEducation.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
            //??????????????????????????????
            List<ZjTzEmployeeEducation> zjTzEmployeeEducations = zjTzEmployeeEducationMapper.selectByZjTzEmployeeEducationList(zjTzEmployeeEducation);
            if(zjTzEmployeeEducations !=null && zjTzEmployeeEducations.size()>0){
                for (ZjTzEmployeeEducation tzEmployeeEducation : zjTzEmployeeEducations) {
                    //???????????? -> ???????????????
                    if(tzEmployeeEducation.getStratTime()!=null){
                        tzEmployeeEducation.setStratTimeString(DateUtil.format(tzEmployeeEducation.getStratTime(),"yyyy-MM-dd"));
                    }
                    //???????????? -> ???????????????
                    if(tzEmployeeEducation.getGraduateTime()!=null){
                        tzEmployeeEducation.setGraduateTimeString(DateUtil.format(tzEmployeeEducation.getGraduateTime(),"yyyy-MM-dd"));
                    }
                    //??????
                    //educationName
                    //xueLi
                    if (StrUtil.isNotEmpty(tzEmployeeEducation.getEducation())) {
                        String educationName = baseCodeService.getBaseCodeItemName("xueLi", tzEmployeeEducation.getEducation());
                        tzEmployeeEducation.setEducationName(educationName);
                    }
                    //??????
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

    //??????????????????
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
                    //???????????? -> ?????????????????????
                    if(tzEmployeeWork.getStartTime()!=null){
                        tzEmployeeWork.setStartTimeString(DateUtil.format(tzEmployeeWork.getStartTime(),"yyyy-MM-dd"));
                    }
                    //???????????? -> ?????????????????????
                    if(tzEmployeeWork.getEndTime()!=null){
                        tzEmployeeWork.setEndTimeString(DateUtil.format(tzEmployeeWork.getEndTime(),"yyyy-MM-dd"));
                    }
                }
            }
            return zjTzEmployeeWorks;
        }
        return new ArrayList<>();
    }

    //??????zip
    @Override
    public ResponseEntity printZjTzEmployeeInfo(List<ZjTzEmployeeInfo> zjTzEmployeeInfoList) {
        if(zjTzEmployeeInfoList!=null && zjTzEmployeeInfoList.size()>0){
            HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
            // ?????????????????????????????????????????????
            String zipName = "????????????-" + DateUtil.format(new Date(), "yyyyMMdd") + ".zip";
            //????????????
            File[] files = new File[zjTzEmployeeInfoList.size()];
            for (int i = 0; i < zjTzEmployeeInfoList.size(); i++) {
                //?????????????????????????????????
                ZjTzEmployeeInfo zjTzEmployeeInfo = zjTzEmployeeInfoMapper.selectByPrimaryKey(zjTzEmployeeInfoList.get(i).getEmployeeInfoId());
                String downUrl = config.getProperty("ng.web.url", "") + "ureport/excel?"
                        + "_u=file:employeeInformation.ureport.xml&url=" + Apih5Properties.getWebUrl()
                        + "&employeeInfoId="+zjTzEmployeeInfo.getEmployeeInfoId();
                String returnNameFile = HttpUtil.getUploadPath("temp") + zjTzEmployeeInfo.getEmployeeName() + "-" + zjTzEmployeeInfo.getIdCard() +".xlsx";
                HttpUtil.httpDownload(downUrl, returnNameFile);
                files[i] = FileUtil.file(returnNameFile);
            }
            // zip????????????
            ZipUtil.zip(FileUtil.file(HttpUtil.getUploadPath("temp") + zipName), false, files);//D:/workspace-wd/apih5/apih5-web/target/upload/zj_tz/
            String returnPath = HttpUtil.getUploadPathWeb(request, "temp") + zipName;
            return repEntity.ok(returnPath);
        }else {
            return repEntity.layerMessage("no", "??????????????????");
        }
    }

    @Override
    public ResponseEntity getZjTzEmployeeInfoListSelectPerson(ZjTzEmployeeInfo zjTzEmployeeInfo) {
        if(StrUtil.isEmpty(zjTzEmployeeInfo.getIdCard())){
            return repEntity.ok(new ArrayList<>());
        }
        // ????????????
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
        //??????????????????????????? (??????)
        ZjTzEmployeeInfo dbzjTzEmployeeInfo = zjTzEmployeeInfoMapper.selectByPrimaryKey(zjTzEmployeeInfo.getEmployeeInfoId());
        if (dbzjTzEmployeeInfo != null && StrUtil.isNotEmpty(dbzjTzEmployeeInfo.getEmployeeInfoId())) {
        	//????????????????????????????????????
        	dbzjTzEmployeeInfo.setPreProjectId(dbzjTzEmployeeInfo.getProjectId());
        	dbzjTzEmployeeInfo.setPreProjectName(dbzjTzEmployeeInfo.getCompanyName());
        	//????????????????????????????????????
        	dbzjTzEmployeeInfo.setProjectId(zjTzEmployeeInfo.getProjectId());
        	dbzjTzEmployeeInfo.setCompanyName(zjTzEmployeeInfo.getCompanyName());
        	// ??????
            dbzjTzEmployeeInfo.setModifyUserInfo(userKey, realName);
        }
        flag = zjTzEmployeeInfoMapper.updateByPrimaryKey(dbzjTzEmployeeInfo);
        // ??????
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
                    // ????????????
                    ZjTzEmployeeInfo dbzjTzEmployeeInfo = zjTzEmployeeInfoMapper.getZjTzEmployeeInfoByIdCard(zjTzEmployeeInfo);
                    if(dbzjTzEmployeeInfo != null){
                        if(StrUtil.equals(dbzjTzEmployeeInfo.getProjectId(),"000000")){
                            dbzjTzEmployeeInfo.setType(1);
                            return repEntity.layerMessage(true,dbzjTzEmployeeInfo,"????????????????????????????????????????????????");
                        }
                        //????????????
                        if(StrUtil.equals(dbzjTzEmployeeInfo.getProjectId(),zjTzEmployeeInfo.getProjectId())){
                            dbzjTzEmployeeInfo.setType(4);
                            return repEntity.layerMessage(false,dbzjTzEmployeeInfo,"?????????????????????????????????????????????");
                        }else {
                            dbzjTzEmployeeInfo.setType(3);
                            return repEntity.layerMessage(false,dbzjTzEmployeeInfo,"?????????????????????????????????????????????????????????????????????");
                        }
                    }
                    ZjTzEmployeeInfo zjTzEmployeeInfo1 = new ZjTzEmployeeInfo();
                    zjTzEmployeeInfo1.setType(2);
                    return repEntity.layerMessage(true,zjTzEmployeeInfo1,"?????????????????????????????????????????????");
                }
            }
        }
        ZjTzEmployeeInfo zjTzEmployeeInfoError = new ZjTzEmployeeInfo();
        zjTzEmployeeInfoError.setType(5);
        return repEntity.layerMessage(false,zjTzEmployeeInfoError,"??????????????????????????????");
    }

    @Override
    public ResponseEntity importProjectZjTzEmployeeInfo(ZjTzEmployeeInfo zjTzEmployeeInfo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if(zjTzEmployeeInfo.getProjectEmployeeId()!=null){
            //???????????????????????????????????????,???????????????????????????
            ZjTzProjectAndEmployee zjTzProjectAndEmployee = new ZjTzProjectAndEmployee();
            //??????id
            zjTzProjectAndEmployee.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
            //??????id
            zjTzProjectAndEmployee.setProjectId(zjTzEmployeeInfo.getProjectId());
            //????????????id
            zjTzProjectAndEmployee.setProjectEmployeeId(zjTzEmployeeInfo.getProjectEmployeeId());
            //??????????????????id
            zjTzProjectAndEmployee.setProjectAndEmployeeId(UuidUtil.generate());
            zjTzProjectAndEmployee.setCreateUserInfo(userKey, realName);
            //??????????????????????????????
            flag += zjTzProjectAndEmployeeMapper.insert(zjTzProjectAndEmployee);
            //????????????
            ZjTzProjectAndEmployee zjTzProjectAndEmployee1 = new ZjTzProjectAndEmployee();
            zjTzProjectAndEmployee1.setProjectEmployeeId(zjTzEmployeeInfo.getProjectEmployeeId());
            List<ZjTzProjectAndEmployee> zjTzProjectAndEmployees = zjTzProjectAndEmployeeMapper.selectByZjTzProjectAndEmployeeList(zjTzProjectAndEmployee1);
            //????????????
            ZjTzProjectEmployee zjTzProjectEmployee = new ZjTzProjectEmployee();
            zjTzProjectEmployee.setEmployeeNumber(zjTzProjectAndEmployees.size());
            zjTzProjectEmployee.setProjectEmployeeId(zjTzEmployeeInfo.getProjectEmployeeId());
            flag += zjTzProjectEmployeeMapper.updataPersonNumber(zjTzProjectEmployee);
            //????????????????????????
            ZjTzEmployeeInfo dbzjTzEmployeeInfo = zjTzEmployeeInfoMapper.selectByPrimaryKey(zjTzEmployeeInfo.getEmployeeInfoId());
            dbzjTzEmployeeInfo.setProjectId(zjTzEmployeeInfo.getProjectId());
            dbzjTzEmployeeInfo.setProjectName(zjTzEmployeeInfo.getProjectName());
            //????????????
            dbzjTzEmployeeInfo.setJoinTime(new Date());
            this.updateZjTzEmployeeInfo(dbzjTzEmployeeInfo);
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.layerMessage(true,zjTzEmployeeInfo,"????????????????????????????????????");
        }
    }

    @Override
    public ResponseEntity importProjectZjTzEmployeeInfoList(List<ZjTzEmployeeInfo> zjTzEmployeeInfoList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if(zjTzEmployeeInfoList != null && zjTzEmployeeInfoList.size()>0){
            //?????????????????????????????????
            List<ZjTzEmployeeInfo> dbzjTzEmployeeInfoList = zjTzEmployeeInfoMapper.isProject(zjTzEmployeeInfoList);
            if(dbzjTzEmployeeInfoList!=null && dbzjTzEmployeeInfoList.size()>0){
                return repEntity.layerMessage(false,dbzjTzEmployeeInfoList,"?????????????????????????????????????????????????????????????????????????????????");
            }
            for (ZjTzEmployeeInfo zjTzEmployeeInfo : zjTzEmployeeInfoList) {
                if(zjTzEmployeeInfo.getProjectEmployeeId() != null){
                    //???????????????????????????????????????,???????????????????????????
                    ZjTzProjectAndEmployee zjTzProjectAndEmployee = new ZjTzProjectAndEmployee();
                    //??????id
                    zjTzProjectAndEmployee.setEmployeeInfoId(zjTzEmployeeInfo.getEmployeeInfoId());
                    //??????id
                    zjTzProjectAndEmployee.setProjectId(zjTzEmployeeInfo.getProjectId());
                    //????????????id
                    zjTzProjectAndEmployee.setProjectEmployeeId(zjTzEmployeeInfo.getProjectEmployeeId());
                    //??????????????????id
                    zjTzProjectAndEmployee.setProjectAndEmployeeId(UuidUtil.generate());
                    zjTzProjectAndEmployee.setCreateUserInfo(userKey, realName);
                    //??????????????????????????????
                    flag += zjTzProjectAndEmployeeMapper.insert(zjTzProjectAndEmployee);
                    //????????????
                    ZjTzProjectAndEmployee zjTzProjectAndEmployee1 = new ZjTzProjectAndEmployee();
                    zjTzProjectAndEmployee1.setProjectEmployeeId(zjTzEmployeeInfo.getProjectEmployeeId());
                    List<ZjTzProjectAndEmployee> zjTzProjectAndEmployees = zjTzProjectAndEmployeeMapper.selectByZjTzProjectAndEmployeeList(zjTzProjectAndEmployee1);
                    //????????????
                    ZjTzProjectEmployee zjTzProjectEmployee = new ZjTzProjectEmployee();
                    zjTzProjectEmployee.setEmployeeNumber(zjTzProjectAndEmployees.size());
                    zjTzProjectEmployee.setProjectEmployeeId(zjTzEmployeeInfo.getProjectEmployeeId());
                    flag += zjTzProjectEmployeeMapper.updataPersonNumber(zjTzProjectEmployee);
                    //????????????????????????
                    ZjTzEmployeeInfo dbzjTzEmployeeInfo = zjTzEmployeeInfoMapper.selectByPrimaryKey(zjTzEmployeeInfo.getEmployeeInfoId());
                    dbzjTzEmployeeInfo.setProjectId(zjTzEmployeeInfo.getProjectId());
                    dbzjTzEmployeeInfo.setProjectName(zjTzEmployeeInfo.getProjectName());
                    //????????????
                    dbzjTzEmployeeInfo.setJoinTime(new Date());
                    this.updateZjTzEmployeeInfo(dbzjTzEmployeeInfo);
                }
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.layerMessage(true,zjTzEmployeeInfoList,"????????????????????????????????????");
        }
    }

    //????????????????????????
    //  1.??????????????????
    //  2.?????????????????? ????????????
    //  3.??????????????????
    @Override
    public ResponseEntity leaveOfficeZjTzEmployeeInfoList(List<ZjTzProjectAndEmployee> zjTzProjectAndEmployeeList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzProjectAndEmployee dbZjTzProjectAndEmployee = new ZjTzProjectAndEmployee();
        if (zjTzProjectAndEmployeeList != null && zjTzProjectAndEmployeeList.size() > 0) {
            //?????????Id
            dbZjTzProjectAndEmployee = zjTzProjectAndEmployeeMapper.selectByPrimaryKey(zjTzProjectAndEmployeeList.get(0).getProjectAndEmployeeId());
            //?????? ????????????????????????
            ZjTzProjectAndEmployee zjTzProjectAndEmployee = new ZjTzProjectAndEmployee();
            zjTzProjectAndEmployee.setModifyUserInfo(userKey, realName);
            flag = zjTzProjectAndEmployeeMapper.batchDeleteUpdateZjTzProjectAndEmployee(zjTzProjectAndEmployeeList, zjTzProjectAndEmployee);
            //?????? ????????????
            ZjTzEmployeeInfo zjTzEmployeeInfo = new ZjTzEmployeeInfo();
            zjTzEmployeeInfo.setModifyUserInfo(userKey, realName);
            List<ZjTzEmployeeInfo> zjTzEmployeeInfoList = zjTzProjectAndEmployeeList.stream().map(o -> {
                ZjTzEmployeeInfo zjTzEmployeeInfo1 = new ZjTzEmployeeInfo();
                zjTzEmployeeInfo1.setEmployeeInfoId(o.getEmployeeInfoId());
                return zjTzEmployeeInfo1;
            }).collect(Collectors.toList());
            flag = zjTzEmployeeInfoMapper.batchDeleteUpdateZjTzEmployeeInfo(zjTzEmployeeInfoList, zjTzEmployeeInfo);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            if(dbZjTzProjectAndEmployee != null){
                //????????????
                ZjTzProjectAndEmployee zjTzProjectAndEmployee = new ZjTzProjectAndEmployee();
                zjTzProjectAndEmployee.setProjectEmployeeId(dbZjTzProjectAndEmployee.getProjectEmployeeId());
                List<ZjTzProjectAndEmployee> zjTzProjectAndEmployees = zjTzProjectAndEmployeeMapper.selectByZjTzProjectAndEmployeeList(zjTzProjectAndEmployee);
                //????????????
                ZjTzProjectEmployee zjTzProjectEmployee = new ZjTzProjectEmployee();
                zjTzProjectEmployee.setEmployeeNumber(zjTzProjectAndEmployees.size());
                zjTzProjectEmployee.setProjectEmployeeId(dbZjTzProjectAndEmployee.getProjectEmployeeId());
                zjTzProjectEmployeeMapper.updataPersonNumber(zjTzProjectEmployee);
            }
            return repEntity.ok("sys.data.delete",zjTzProjectAndEmployeeList);
        }
    }
}
