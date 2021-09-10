package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCrCustomerExtAttrMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxCrCustomerExtAttr;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxCrCustomerExtAttrService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxCrCustomerExtAttrService")
public class ZxCrCustomerExtAttrServiceImpl implements ZxCrCustomerExtAttrService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCrCustomerExtAttrMapper zxCrCustomerExtAttrMapper;
    
    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Override
    public ResponseEntity getZxCrCustomerExtAttrListByCondition(ZxCrCustomerExtAttr zxCrCustomerExtAttr) {
        if (zxCrCustomerExtAttr == null) {
            zxCrCustomerExtAttr = new ZxCrCustomerExtAttr();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxCrCustomerExtAttr.setCompanyId("");
        	zxCrCustomerExtAttr.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxCrCustomerExtAttr.setCompanyId(zxCrCustomerExtAttr.getProjectId());
        	zxCrCustomerExtAttr.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxCrCustomerExtAttr.setProjectId(zxCrCustomerExtAttr.getProjectId());
        }
        // 分页查询
        PageHelper.startPage(zxCrCustomerExtAttr.getPage(),zxCrCustomerExtAttr.getLimit());
        // 获取数据
        List<ZxCrCustomerExtAttr> zxCrCustomerExtAttrList = zxCrCustomerExtAttrMapper.selectByZxCrCustomerExtAttrList(zxCrCustomerExtAttr);
        for(ZxCrCustomerExtAttr zxCrCustomerExtAttrListOne :zxCrCustomerExtAttrList) {
        	if(zxCrCustomerExtAttrListOne.getType().equals("coo")) {
        		zxCrCustomerExtAttrListOne.setType(zxCrCustomerExtAttrListOne.getType());
        		// 得到分页信息
                PageInfo<ZxCrCustomerExtAttr> p = new PageInfo<>(zxCrCustomerExtAttrList);

                return repEntity.okList(zxCrCustomerExtAttrList, p.getTotal());
        	}else if(zxCrCustomerExtAttrListOne.getType().equals("eqf")) {
        		zxCrCustomerExtAttrListOne.setType(zxCrCustomerExtAttrListOne.getType());
        		// 得到分页信息
                PageInfo<ZxCrCustomerExtAttr> p = new PageInfo<>(zxCrCustomerExtAttrList);

                return repEntity.okList(zxCrCustomerExtAttrList, p.getTotal());
        	}else if(zxCrCustomerExtAttrListOne.getType().equals("eqr")) {
        		zxCrCustomerExtAttrListOne.setType(zxCrCustomerExtAttrListOne.getType());
        		// 得到分页信息
                PageInfo<ZxCrCustomerExtAttr> p = new PageInfo<>(zxCrCustomerExtAttrList);

                return repEntity.okList(zxCrCustomerExtAttrList, p.getTotal());
        	}else if(zxCrCustomerExtAttrListOne.getType().equals("eqs")) {
        		zxCrCustomerExtAttrListOne.setType(zxCrCustomerExtAttrListOne.getType());
        		// 得到分页信息
                PageInfo<ZxCrCustomerExtAttr> p = new PageInfo<>(zxCrCustomerExtAttrList);

                return repEntity.okList(zxCrCustomerExtAttrList, p.getTotal());
        	}else if(zxCrCustomerExtAttrListOne.getType().equals("mtf")) {
        		zxCrCustomerExtAttrListOne.setType(zxCrCustomerExtAttrListOne.getType());
        		// 得到分页信息
                PageInfo<ZxCrCustomerExtAttr> p = new PageInfo<>(zxCrCustomerExtAttrList);

                return repEntity.okList(zxCrCustomerExtAttrList, p.getTotal());
        	}else if(zxCrCustomerExtAttrListOne.getType().equals("mtr")) {
        		zxCrCustomerExtAttrListOne.setType(zxCrCustomerExtAttrListOne.getType());
        		// 得到分页信息
                PageInfo<ZxCrCustomerExtAttr> p = new PageInfo<>(zxCrCustomerExtAttrList);

                return repEntity.okList(zxCrCustomerExtAttrList, p.getTotal());
        	}else if(zxCrCustomerExtAttrListOne.getType().equals("mts")) {
        		zxCrCustomerExtAttrListOne.setType(zxCrCustomerExtAttrListOne.getType());
        		// 得到分页信息
                PageInfo<ZxCrCustomerExtAttr> p = new PageInfo<>(zxCrCustomerExtAttrList);

                return repEntity.okList(zxCrCustomerExtAttrList, p.getTotal());
        	}else if(zxCrCustomerExtAttrListOne.getType().equals("ots")) {
        		zxCrCustomerExtAttrListOne.setType(zxCrCustomerExtAttrListOne.getType());
        		//查询附件
                for (ZxCrCustomerExtAttr zxCrCustomerExtAttr1 : zxCrCustomerExtAttrList) {
                    ZxErpFile zxErpFile = new ZxErpFile();
                    zxErpFile.setOtherId(zxCrCustomerExtAttr1.getZxCrCustomerExtAttrId());
                    List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                    zxCrCustomerExtAttr1.setFileList(zxErpFiles);
                }
        		// 得到分页信息
                PageInfo<ZxCrCustomerExtAttr> p = new PageInfo<>(zxCrCustomerExtAttrList);

                return repEntity.okList(zxCrCustomerExtAttrList, p.getTotal());
        	}
        }
       
        //查询附件
        for (ZxCrCustomerExtAttr zxCrCustomerExtAttr1 : zxCrCustomerExtAttrList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxCrCustomerExtAttr1.getZxCrCustomerExtAttrId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxCrCustomerExtAttr1.setFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxCrCustomerExtAttr> p = new PageInfo<>(zxCrCustomerExtAttrList);

        return repEntity.okList(zxCrCustomerExtAttrList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCrCustomerExtAttrDetail(ZxCrCustomerExtAttr zxCrCustomerExtAttr) {
        if (zxCrCustomerExtAttr == null) {
            zxCrCustomerExtAttr = new ZxCrCustomerExtAttr();
        }
        // 获取数据
        ZxCrCustomerExtAttr dbZxCrCustomerExtAttr = zxCrCustomerExtAttrMapper.selectByPrimaryKey(zxCrCustomerExtAttr.getZxCrCustomerExtAttrId());
        // 数据存在
        if (dbZxCrCustomerExtAttr != null) {
        	//附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxCrCustomerExtAttr.getZxCrCustomerExtAttrId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxCrCustomerExtAttr.setFileList(zxErpFiles);
            return repEntity.ok(dbZxCrCustomerExtAttr);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCrCustomerExtAttr(ZxCrCustomerExtAttr zxCrCustomerExtAttr) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCrCustomerExtAttr.setZxCrCustomerExtAttrId(UuidUtil.generate());
        zxCrCustomerExtAttr.setCreateUserInfo(userKey, realName);
        //张:修改type 8
        zxCrCustomerExtAttr.setType("ots");
        int flag = zxCrCustomerExtAttrMapper.insert(zxCrCustomerExtAttr);
        //添加附件
        List<ZxErpFile> fileList = zxCrCustomerExtAttr.getFileList();
        if(fileList != null && fileList.size()>0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxCrCustomerExtAttr.getZxCrCustomerExtAttrId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCrCustomerExtAttr);
        }
    }

    @Override
    public ResponseEntity updateZxCrCustomerExtAttr(ZxCrCustomerExtAttr zxCrCustomerExtAttr) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCrCustomerExtAttr dbZxCrCustomerExtAttr = zxCrCustomerExtAttrMapper.selectByPrimaryKey(zxCrCustomerExtAttr.getZxCrCustomerExtAttrId());
        if (dbZxCrCustomerExtAttr != null && StrUtil.isNotEmpty(dbZxCrCustomerExtAttr.getZxCrCustomerExtAttrId())) {
           // 客商信誉
           dbZxCrCustomerExtAttr.setCreditStanding(zxCrCustomerExtAttr.getCreditStanding());
           // 负责人手机
           dbZxCrCustomerExtAttr.setPricinpalMobile(zxCrCustomerExtAttr.getPricinpalMobile());
           // 法人单位联系电话
           dbZxCrCustomerExtAttr.setPricinpalTel(zxCrCustomerExtAttr.getPricinpalTel());
           // 法人单位传真
           dbZxCrCustomerExtAttr.setPricinpalFax(zxCrCustomerExtAttr.getPricinpalFax());
           // 负责人邮箱
           dbZxCrCustomerExtAttr.setPricinpalEmail(zxCrCustomerExtAttr.getPricinpalEmail());
           // 法人单位邮编
           dbZxCrCustomerExtAttr.setPricinpalPostCode(zxCrCustomerExtAttr.getPricinpalPostCode());
           // 法人单位通讯地址
           dbZxCrCustomerExtAttr.setPricinpalAddr(zxCrCustomerExtAttr.getPricinpalAddr());
           // 客商ID
           dbZxCrCustomerExtAttr.setCustomerID(zxCrCustomerExtAttr.getCustomerID());
           // 编号
           dbZxCrCustomerExtAttr.setCustomerNO(zxCrCustomerExtAttr.getCustomerNO());
           // 客商产生的机构ID
           dbZxCrCustomerExtAttr.setProOrgID(zxCrCustomerExtAttr.getProOrgID());
           // 性质
           dbZxCrCustomerExtAttr.setKind(zxCrCustomerExtAttr.getKind());
           // 负责人身份证
           dbZxCrCustomerExtAttr.setPersonInChargeIdNumber(zxCrCustomerExtAttr.getPersonInChargeIdNumber());
           // 注册单位(个人)名称
           dbZxCrCustomerExtAttr.setCustomerName(zxCrCustomerExtAttr.getCustomerName());
           // 组织机构代码证
           dbZxCrCustomerExtAttr.setOrganizationCode(zxCrCustomerExtAttr.getOrganizationCode());
           // 内部单位
           dbZxCrCustomerExtAttr.setRelat(zxCrCustomerExtAttr.getRelat());
           // 法人代表
           dbZxCrCustomerExtAttr.setLegalRepresentative(zxCrCustomerExtAttr.getLegalRepresentative());
           // 法人委托人
           dbZxCrCustomerExtAttr.setLegalPersonClient(zxCrCustomerExtAttr.getLegalPersonClient());
           // 单位注册时间
           dbZxCrCustomerExtAttr.setRegistrationTime(zxCrCustomerExtAttr.getRegistrationTime());
           // 单位注册地区
           dbZxCrCustomerExtAttr.setUnitRegistrationArea(zxCrCustomerExtAttr.getUnitRegistrationArea());
           // 注册资本金
           dbZxCrCustomerExtAttr.setRegisteredCapital(zxCrCustomerExtAttr.getRegisteredCapital());
           // 资质证书编号
           dbZxCrCustomerExtAttr.setQualificationCertificateNo(zxCrCustomerExtAttr.getQualificationCertificateNo());
           // 安全生产许可证编号
           dbZxCrCustomerExtAttr.setSafetyProductionLicenseNo(zxCrCustomerExtAttr.getSafetyProductionLicenseNo());
           // 资质范围等级
           dbZxCrCustomerExtAttr.setScopeAndGradeOfQualification(zxCrCustomerExtAttr.getScopeAndGradeOfQualification());
           // 经营范围
           dbZxCrCustomerExtAttr.setNatureOfBusiness(zxCrCustomerExtAttr.getNatureOfBusiness());
           // 法人单位手机
           dbZxCrCustomerExtAttr.setMobilePhoneOfLegalEntity(zxCrCustomerExtAttr.getMobilePhoneOfLegalEntity());
           // 公司类型
           dbZxCrCustomerExtAttr.setCompanyType(zxCrCustomerExtAttr.getCompanyType());
           // 局内协作经历
           dbZxCrCustomerExtAttr.setExperienceOfInhouseCollaboration(zxCrCustomerExtAttr.getExperienceOfInhouseCollaboration());
           // 正在服务项目
           dbZxCrCustomerExtAttr.setProjectInService(zxCrCustomerExtAttr.getProjectInService());
           // 客商固话
           dbZxCrCustomerExtAttr.setFixedLineTelephoneOfMerchants(zxCrCustomerExtAttr.getFixedLineTelephoneOfMerchants());
           // 客商传真
           dbZxCrCustomerExtAttr.setFaxOfMerchants(zxCrCustomerExtAttr.getFaxOfMerchants());
           // 营业执照编号
           dbZxCrCustomerExtAttr.setBusinessLicenseNumber(zxCrCustomerExtAttr.getBusinessLicenseNumber());
           // 客户类型
           dbZxCrCustomerExtAttr.setType("ots");
           // 联系人
           dbZxCrCustomerExtAttr.setLinkmanName(zxCrCustomerExtAttr.getLinkmanName());
           // 联系人电话
           dbZxCrCustomerExtAttr.setLinkmanTel(zxCrCustomerExtAttr.getLinkmanTel());
           // 商客名称
           dbZxCrCustomerExtAttr.setSearchTheCustomerName(zxCrCustomerExtAttr.getSearchTheCustomerName());
           // 项目名称
           dbZxCrCustomerExtAttr.setEntryName(zxCrCustomerExtAttr.getEntryName());
           // 备注
           dbZxCrCustomerExtAttr.setRemarks(zxCrCustomerExtAttr.getRemarks());
           // 排序
           dbZxCrCustomerExtAttr.setSort(zxCrCustomerExtAttr.getSort());
           // 共通
           dbZxCrCustomerExtAttr.setModifyUserInfo(userKey, realName);
           flag = zxCrCustomerExtAttrMapper.updateByPrimaryKey(dbZxCrCustomerExtAttr);
        
           //修改在新增(附件)
           ZxErpFile zxErpFileSelect = new ZxErpFile();
           zxErpFileSelect.setOtherId(dbZxCrCustomerExtAttr.getZxCrCustomerExtAttrId());
           List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
           if(zxErpFiles != null && zxErpFiles.size()>0) {
               zxErpFileSelect.setModifyUserInfo(userKey, realName);
               zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
           }
           List<ZxErpFile> fileList = zxCrCustomerExtAttr.getFileList();
           if(fileList != null && fileList.size()>0) {
               for (ZxErpFile zxErpFile : fileList) {
                   zxErpFile.setOtherId(zxCrCustomerExtAttr.getZxCrCustomerExtAttrId());
                   zxErpFile.setUid((UuidUtil.generate()));
                   zxErpFile.setCreateUserInfo(userKey, realName);
                   zxErpFileMapper.insert(zxErpFile);
               }
           }
           
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCrCustomerExtAttr);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCrCustomerExtAttr(List<ZxCrCustomerExtAttr> zxCrCustomerExtAttrList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCrCustomerExtAttrList != null && zxCrCustomerExtAttrList.size() > 0) {
        	for (ZxCrCustomerExtAttr zxCrCustomerExtAttr2 : zxCrCustomerExtAttrList) {
                //删除附件
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxCrCustomerExtAttr2.getZxCrCustomerExtAttrId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if(zxErpFiles != null && zxErpFiles.size()>0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
        	}
           ZxCrCustomerExtAttr zxCrCustomerExtAttr = new ZxCrCustomerExtAttr();
           zxCrCustomerExtAttr.setModifyUserInfo(userKey, realName);
           flag = zxCrCustomerExtAttrMapper.batchDeleteUpdateZxCrCustomerExtAttr(zxCrCustomerExtAttrList, zxCrCustomerExtAttr);
            
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCrCustomerExtAttrList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
    //检索客商名称
    @Override
    public ResponseEntity getZxCrCustomerExtAttrDetailRetrieval(ZxCrCustomerExtAttr zxCrCustomerExtAttr) {
        if (zxCrCustomerExtAttr == null) {
            zxCrCustomerExtAttr = new ZxCrCustomerExtAttr();
        }
        // 获取数据
        List<ZxCrCustomerExtAttr> dbZxCrCustomerExtAttr = zxCrCustomerExtAttrMapper.selectByPrimaryKeyall(zxCrCustomerExtAttr);
        for(ZxCrCustomerExtAttr zxCrCustomerExtAttrListOne : dbZxCrCustomerExtAttr) {
            //隶属机构
            if(StrUtil.isNotEmpty(zxCrCustomerExtAttrListOne.getProjectName())) {
                zxCrCustomerExtAttrListOne.setEntryName(zxCrCustomerExtAttrListOne.getProjectName());
            }
        }
        // 数据存在
        if (dbZxCrCustomerExtAttr != null) {
            return repEntity.ok(dbZxCrCustomerExtAttr);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    //检索全部的组织机构代码证
    @Override
    public ResponseEntity getZxCrCustomerExtAttrAll(ZxCrCustomerExtAttr zxCrCustomerExtAttr) {
        if (zxCrCustomerExtAttr == null) {
            zxCrCustomerExtAttr = new ZxCrCustomerExtAttr();
        }
        // 获取数据
        List<ZxCrCustomerExtAttr> dbZxCrCustomerExtAttr = zxCrCustomerExtAttrMapper.selectAll(zxCrCustomerExtAttr);
        for(ZxCrCustomerExtAttr zxCrCustomerExtAttrListOne : dbZxCrCustomerExtAttr) {
        	//隶属机构
    		if(StrUtil.isNotEmpty(zxCrCustomerExtAttrListOne.getProjectName())) {
    			zxCrCustomerExtAttrListOne.setEntryName(zxCrCustomerExtAttrListOne.getProjectName());
    		}
        }
        
        // 数据存在
        if (dbZxCrCustomerExtAttr != null) {
            return repEntity.ok(dbZxCrCustomerExtAttr);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    
    @Override
    public ResponseEntity getZxCrCustomerExtAttrListAll(ZxCrCustomerExtAttr zxCrCustomerExtAttr) {
        if (zxCrCustomerExtAttr == null) {
            zxCrCustomerExtAttr = new ZxCrCustomerExtAttr();
        }
       
        // 分页查询
        PageHelper.startPage(zxCrCustomerExtAttr.getPage(),zxCrCustomerExtAttr.getLimit());
        
        // 获取数据
        List<ZxCrCustomerExtAttr> zxCrCustomerExtAttrList = zxCrCustomerExtAttrMapper.selectByZxCrCustomerExtAttrList(zxCrCustomerExtAttr);
       List<ZxCrCustomerExtAttr> zxCrCustomerExtAttrList1 = new ArrayList<ZxCrCustomerExtAttr>();
      
        for(ZxCrCustomerExtAttr zxCrCustomerExtAttrListOne :zxCrCustomerExtAttrList) {
        	if(StrUtil.isNotEmpty(zxCrCustomerExtAttr.getTypeList())){
        		if(zxCrCustomerExtAttrListOne.getType().equals(zxCrCustomerExtAttr.getTypeList().substring(0,1))){
           		 zxCrCustomerExtAttr.setType(zxCrCustomerExtAttrListOne.getType());
           	} 
           	if(zxCrCustomerExtAttrListOne.getType().equals(zxCrCustomerExtAttr.getTypeList().substring(1,2))) {
           		zxCrCustomerExtAttr.setType1(zxCrCustomerExtAttrListOne.getType());
           	}	
           	if(zxCrCustomerExtAttrListOne.getType().equals(zxCrCustomerExtAttr.getTypeList().substring(2,3))) {
           		zxCrCustomerExtAttr.setType2(zxCrCustomerExtAttrListOne.getType());
           	}
        	}
        	
        		zxCrCustomerExtAttrList1 = zxCrCustomerExtAttrMapper.selectByZxCrCustomerExtAttrListAll(zxCrCustomerExtAttr);
        	
        }

		// 得到分页信息
        PageInfo<ZxCrCustomerExtAttr> p = new PageInfo<>(zxCrCustomerExtAttrList1);

        return repEntity.okList(zxCrCustomerExtAttrList1, p.getTotal());
        
    }

    @Override
    public ResponseEntity addZxCrCustomerExtAttrPicking(ZxCrCustomerExtAttr zxCrCustomerExtAttr) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCrCustomerExtAttr.setZxCrCustomerExtAttrId(UuidUtil.generate());
        zxCrCustomerExtAttr.setCreateUserInfo(userKey, realName);
        //张:领料单位使用coo
        zxCrCustomerExtAttr.setType("coo");
        int flag = zxCrCustomerExtAttrMapper.insert(zxCrCustomerExtAttr);
        //添加附件
        List<ZxErpFile> fileList = zxCrCustomerExtAttr.getFileList();
        if(fileList != null && fileList.size()>0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxCrCustomerExtAttr.getZxCrCustomerExtAttrId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCrCustomerExtAttr);
        }
    }

    @Override
    public ResponseEntity updateZxCrCustomerExtAttrPicking(ZxCrCustomerExtAttr zxCrCustomerExtAttr) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCrCustomerExtAttr dbZxCrCustomerExtAttr = zxCrCustomerExtAttrMapper.selectByPrimaryKey(zxCrCustomerExtAttr.getZxCrCustomerExtAttrId());
        if (dbZxCrCustomerExtAttr != null && StrUtil.isNotEmpty(dbZxCrCustomerExtAttr.getZxCrCustomerExtAttrId())) {
            // 客商信誉
            dbZxCrCustomerExtAttr.setCreditStanding(zxCrCustomerExtAttr.getCreditStanding());
            // 负责人手机
            dbZxCrCustomerExtAttr.setPricinpalMobile(zxCrCustomerExtAttr.getPricinpalMobile());
            // 法人单位联系电话
            dbZxCrCustomerExtAttr.setPricinpalTel(zxCrCustomerExtAttr.getPricinpalTel());
            // 法人单位传真
            dbZxCrCustomerExtAttr.setPricinpalFax(zxCrCustomerExtAttr.getPricinpalFax());
            // 负责人邮箱
            dbZxCrCustomerExtAttr.setPricinpalEmail(zxCrCustomerExtAttr.getPricinpalEmail());
            // 法人单位邮编
            dbZxCrCustomerExtAttr.setPricinpalPostCode(zxCrCustomerExtAttr.getPricinpalPostCode());
            // 法人单位通讯地址
            dbZxCrCustomerExtAttr.setPricinpalAddr(zxCrCustomerExtAttr.getPricinpalAddr());
            // 客商ID
            dbZxCrCustomerExtAttr.setCustomerID(zxCrCustomerExtAttr.getCustomerID());
            // 编号
            dbZxCrCustomerExtAttr.setCustomerNO(zxCrCustomerExtAttr.getCustomerNO());
            // 客商产生的机构ID
            dbZxCrCustomerExtAttr.setProOrgID(zxCrCustomerExtAttr.getProOrgID());
            // 性质
            dbZxCrCustomerExtAttr.setKind(zxCrCustomerExtAttr.getKind());
            // 负责人身份证
            dbZxCrCustomerExtAttr.setPersonInChargeIdNumber(zxCrCustomerExtAttr.getPersonInChargeIdNumber());
            // 注册单位(个人)名称
            dbZxCrCustomerExtAttr.setCustomerName(zxCrCustomerExtAttr.getCustomerName());
            // 组织机构代码证
            dbZxCrCustomerExtAttr.setOrganizationCode(zxCrCustomerExtAttr.getOrganizationCode());
            // 内部单位
            dbZxCrCustomerExtAttr.setRelat(zxCrCustomerExtAttr.getRelat());
            // 法人代表
            dbZxCrCustomerExtAttr.setLegalRepresentative(zxCrCustomerExtAttr.getLegalRepresentative());
            // 法人委托人
            dbZxCrCustomerExtAttr.setLegalPersonClient(zxCrCustomerExtAttr.getLegalPersonClient());
            // 单位注册时间
            dbZxCrCustomerExtAttr.setRegistrationTime(zxCrCustomerExtAttr.getRegistrationTime());
            // 单位注册地区
            dbZxCrCustomerExtAttr.setUnitRegistrationArea(zxCrCustomerExtAttr.getUnitRegistrationArea());
            // 注册资本金
            dbZxCrCustomerExtAttr.setRegisteredCapital(zxCrCustomerExtAttr.getRegisteredCapital());
            // 资质证书编号
            dbZxCrCustomerExtAttr.setQualificationCertificateNo(zxCrCustomerExtAttr.getQualificationCertificateNo());
            // 安全生产许可证编号
            dbZxCrCustomerExtAttr.setSafetyProductionLicenseNo(zxCrCustomerExtAttr.getSafetyProductionLicenseNo());
            // 资质范围等级
            dbZxCrCustomerExtAttr.setScopeAndGradeOfQualification(zxCrCustomerExtAttr.getScopeAndGradeOfQualification());
            // 经营范围
            dbZxCrCustomerExtAttr.setNatureOfBusiness(zxCrCustomerExtAttr.getNatureOfBusiness());
            // 法人单位手机
            dbZxCrCustomerExtAttr.setMobilePhoneOfLegalEntity(zxCrCustomerExtAttr.getMobilePhoneOfLegalEntity());
            // 公司类型
            dbZxCrCustomerExtAttr.setCompanyType(zxCrCustomerExtAttr.getCompanyType());
            // 局内协作经历
            dbZxCrCustomerExtAttr.setExperienceOfInhouseCollaboration(zxCrCustomerExtAttr.getExperienceOfInhouseCollaboration());
            // 正在服务项目
            dbZxCrCustomerExtAttr.setProjectInService(zxCrCustomerExtAttr.getProjectInService());
            // 客商固话
            dbZxCrCustomerExtAttr.setFixedLineTelephoneOfMerchants(zxCrCustomerExtAttr.getFixedLineTelephoneOfMerchants());
            // 客商传真
            dbZxCrCustomerExtAttr.setFaxOfMerchants(zxCrCustomerExtAttr.getFaxOfMerchants());
            // 营业执照编号
            dbZxCrCustomerExtAttr.setBusinessLicenseNumber(zxCrCustomerExtAttr.getBusinessLicenseNumber());
            // 客户类型
            dbZxCrCustomerExtAttr.setType("coo");
            // 联系人
            dbZxCrCustomerExtAttr.setLinkmanName(zxCrCustomerExtAttr.getLinkmanName());
            // 联系人电话
            dbZxCrCustomerExtAttr.setLinkmanTel(zxCrCustomerExtAttr.getLinkmanTel());
            // 商客名称
            dbZxCrCustomerExtAttr.setSearchTheCustomerName(zxCrCustomerExtAttr.getSearchTheCustomerName());
            // 项目名称
            dbZxCrCustomerExtAttr.setEntryName(zxCrCustomerExtAttr.getEntryName());
            // 备注
            dbZxCrCustomerExtAttr.setRemarks(zxCrCustomerExtAttr.getRemarks());
            // 排序
            dbZxCrCustomerExtAttr.setSort(zxCrCustomerExtAttr.getSort());
            // 共通
            dbZxCrCustomerExtAttr.setModifyUserInfo(userKey, realName);
            flag = zxCrCustomerExtAttrMapper.updateByPrimaryKey(dbZxCrCustomerExtAttr);

            //修改在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(dbZxCrCustomerExtAttr.getZxCrCustomerExtAttrId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            List<ZxErpFile> fileList = zxCrCustomerExtAttr.getFileList();
            if(fileList != null && fileList.size()>0) {
                for (ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setOtherId(zxCrCustomerExtAttr.getZxCrCustomerExtAttrId());
                    zxErpFile.setUid((UuidUtil.generate()));
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }

        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCrCustomerExtAttr);
        }
    }

    @Override
    public ResponseEntity getZxCrCustomerExtAttrPickingList(ZxCrCustomerExtAttr zxCrCustomerExtAttr) {
        if (zxCrCustomerExtAttr == null) {
            zxCrCustomerExtAttr = new ZxCrCustomerExtAttr();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxCrCustomerExtAttr.setCompanyId("");
            zxCrCustomerExtAttr.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxCrCustomerExtAttr.setCompanyId(zxCrCustomerExtAttr.getProjectId());
            zxCrCustomerExtAttr.setProjectId("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxCrCustomerExtAttr.setProjectId(zxCrCustomerExtAttr.getProjectId());
        }
        // 分页查询
        PageHelper.startPage(zxCrCustomerExtAttr.getPage(),zxCrCustomerExtAttr.getLimit());
        // 获取数据
        List<ZxCrCustomerExtAttr> zxCrCustomerExtAttrList = zxCrCustomerExtAttrMapper.selectByZxCrCustomerExtAttrList(zxCrCustomerExtAttr);
        for(ZxCrCustomerExtAttr zxCrCustomerExtAttrListOne :zxCrCustomerExtAttrList) {
            if(zxCrCustomerExtAttrListOne.getType().equals("coo")) {
                zxCrCustomerExtAttrListOne.setType(zxCrCustomerExtAttrListOne.getType());
                //查询附件
                for (ZxCrCustomerExtAttr zxCrCustomerExtAttr1 : zxCrCustomerExtAttrList) {
                    ZxErpFile zxErpFile = new ZxErpFile();
                    zxErpFile.setOtherId(zxCrCustomerExtAttr1.getZxCrCustomerExtAttrId());
                    List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                    zxCrCustomerExtAttr1.setFileList(zxErpFiles);
                }
                // 得到分页信息
                PageInfo<ZxCrCustomerExtAttr> p = new PageInfo<>(zxCrCustomerExtAttrList);

                return repEntity.okList(zxCrCustomerExtAttrList, p.getTotal());
            }else if(zxCrCustomerExtAttrListOne.getType().equals("eqf")) {
                zxCrCustomerExtAttrListOne.setType(zxCrCustomerExtAttrListOne.getType());
                // 得到分页信息
                PageInfo<ZxCrCustomerExtAttr> p = new PageInfo<>(zxCrCustomerExtAttrList);

                return repEntity.okList(zxCrCustomerExtAttrList, p.getTotal());
            }else if(zxCrCustomerExtAttrListOne.getType().equals("eqr")) {
                zxCrCustomerExtAttrListOne.setType(zxCrCustomerExtAttrListOne.getType());
                // 得到分页信息
                PageInfo<ZxCrCustomerExtAttr> p = new PageInfo<>(zxCrCustomerExtAttrList);

                return repEntity.okList(zxCrCustomerExtAttrList, p.getTotal());
            }else if(zxCrCustomerExtAttrListOne.getType().equals("eqs")) {
                zxCrCustomerExtAttrListOne.setType(zxCrCustomerExtAttrListOne.getType());
                // 得到分页信息
                PageInfo<ZxCrCustomerExtAttr> p = new PageInfo<>(zxCrCustomerExtAttrList);

                return repEntity.okList(zxCrCustomerExtAttrList, p.getTotal());
            }else if(zxCrCustomerExtAttrListOne.getType().equals("mtf")) {
                zxCrCustomerExtAttrListOne.setType(zxCrCustomerExtAttrListOne.getType());
                // 得到分页信息
                PageInfo<ZxCrCustomerExtAttr> p = new PageInfo<>(zxCrCustomerExtAttrList);

                return repEntity.okList(zxCrCustomerExtAttrList, p.getTotal());
            }else if(zxCrCustomerExtAttrListOne.getType().equals("mtr")) {
                zxCrCustomerExtAttrListOne.setType(zxCrCustomerExtAttrListOne.getType());
                // 得到分页信息
                PageInfo<ZxCrCustomerExtAttr> p = new PageInfo<>(zxCrCustomerExtAttrList);

                return repEntity.okList(zxCrCustomerExtAttrList, p.getTotal());
            }else if(zxCrCustomerExtAttrListOne.getType().equals("mts")) {
                zxCrCustomerExtAttrListOne.setType(zxCrCustomerExtAttrListOne.getType());
                // 得到分页信息
                PageInfo<ZxCrCustomerExtAttr> p = new PageInfo<>(zxCrCustomerExtAttrList);

                return repEntity.okList(zxCrCustomerExtAttrList, p.getTotal());
            }else if(zxCrCustomerExtAttrListOne.getType().equals("ots")) {
                zxCrCustomerExtAttrListOne.setType(zxCrCustomerExtAttrListOne.getType());

                //查询附件
                for (ZxCrCustomerExtAttr zxCrCustomerExtAttr1 : zxCrCustomerExtAttrList) {
                    ZxErpFile zxErpFile = new ZxErpFile();
                    zxErpFile.setOtherId(zxCrCustomerExtAttr1.getZxCrCustomerExtAttrId());
                    List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                    zxCrCustomerExtAttr1.setFileList(zxErpFiles);
                }
                // 得到分页信息
                PageInfo<ZxCrCustomerExtAttr> p = new PageInfo<>(zxCrCustomerExtAttrList);

                return repEntity.okList(zxCrCustomerExtAttrList, p.getTotal());
            }

        }
        //查询附件
        for (ZxCrCustomerExtAttr zxCrCustomerExtAttr1 : zxCrCustomerExtAttrList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxCrCustomerExtAttr1.getZxCrCustomerExtAttrId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxCrCustomerExtAttr1.setFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxCrCustomerExtAttr> p = new PageInfo<>(zxCrCustomerExtAttrList);

        return repEntity.okList(zxCrCustomerExtAttrList, p.getTotal());
    }
    @Override
    public ResponseEntity getZxCrCustomerExtAttrEngineeringList(ZxCrCustomerExtAttr zxCrCustomerExtAttr) {
        if (zxCrCustomerExtAttr == null) {
            zxCrCustomerExtAttr = new ZxCrCustomerExtAttr();
        }
     // 分页查询
        PageHelper.startPage(zxCrCustomerExtAttr.getPage(),zxCrCustomerExtAttr.getLimit());
        zxCrCustomerExtAttr.setType("coo");
        // 获取数据
        List<ZxCrCustomerExtAttr> zxCrCustomerExtAttrList = zxCrCustomerExtAttrMapper.selectByZxCrCustomerExtAttrEngineeringList(zxCrCustomerExtAttr);
       
        PageInfo<ZxCrCustomerExtAttr> p = new PageInfo<>(zxCrCustomerExtAttrList);

        return repEntity.okList(zxCrCustomerExtAttrList, p.getTotal());
    }
}
