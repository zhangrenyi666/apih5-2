package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.api.sysdb.service.BaseCodeService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzQualityMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzQuality;
import com.apih5.service.ZjTzQualityService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzQualityService")
public class ZjTzQualityServiceImpl implements ZjTzQualityService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzQualityMapper zjTzQualityMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
	private BaseCodeService baseCodeService;


    @Override
    public ResponseEntity getZjTzQualityListByCondition(ZjTzQuality zjTzQuality) {
        if (zjTzQuality == null) {
            zjTzQuality = new ZjTzQuality();
        }
        // 分页查询
        PageHelper.startPage(zjTzQuality.getPage(),zjTzQuality.getLimit());
        // 获取数据
        List<ZjTzQuality> zjTzQualityList = zjTzQualityMapper.selectByZjTzQualityList(zjTzQuality);
        for (ZjTzQuality zjTzQuality2 : zjTzQualityList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(zjTzQuality2.getQualityId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	zjTzQuality2.setZjTzFileList(zjTzFileList);
		}
        
        // 得到分页信息
        PageInfo<ZjTzQuality> p = new PageInfo<>(zjTzQualityList);

        return repEntity.okList(zjTzQualityList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzQualityDetails(ZjTzQuality zjTzQuality) {
        if (zjTzQuality == null) {
            zjTzQuality = new ZjTzQuality();
        }
        // 获取数据
        ZjTzQuality dbZjTzQuality = zjTzQualityMapper.selectByPrimaryKey(zjTzQuality.getQualityId());
        // 数据存在
        if (dbZjTzQuality != null) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(dbZjTzQuality.getQualityId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzQuality.setZjTzFileList(zjTzFileList);
            return repEntity.ok(dbZjTzQuality);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzQuality(ZjTzQuality zjTzQuality) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzQuality.setQualityId(UuidUtil.generate());
        //专业类型
        if (StrUtil.isNotEmpty(zjTzQuality.getMajorTypeId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("zhuanYeLeiXing", zjTzQuality.getMajorTypeId());
        	zjTzQuality.setMajorTypeName(openBankName);
        }
        //对应资质
        if (StrUtil.isNotEmpty(zjTzQuality.getCorrespondQualityId())) {
        	String openBankName = baseCodeService.getBaseCodeItemName("duiYingZiZhi", zjTzQuality.getCorrespondQualityId());
        	zjTzQuality.setCorrespondQualityName(openBankName);
        }
        zjTzQuality.setCreateUserInfo(userKey, realName);
        int flag = zjTzQualityMapper.insert(zjTzQuality);
        // 附件list
        List<ZjTzFile> zjTzFileList = zjTzQuality.getZjTzFileList();
        if(zjTzFileList != null && zjTzFileList.size()>0) {
        	for(ZjTzFile zjTzFile:zjTzFileList) {
        		zjTzFile.setUid(UuidUtil.generate());
        		zjTzFile.setOtherId(zjTzQuality.getQualityId());
        		zjTzFile.setCreateUserInfo(userKey, realName);
        		zjTzFileMapper.insert(zjTzFile);
        	}
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzQuality);
        }
    }

    @Override
    public ResponseEntity updateZjTzQuality(ZjTzQuality zjTzQuality) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzQuality dbzjTzQuality = zjTzQualityMapper.selectByPrimaryKey(zjTzQuality.getQualityId());
        if (dbzjTzQuality != null && StrUtil.isNotEmpty(dbzjTzQuality.getQualityId())) {
           // 设计、咨询单位标准化id
           dbzjTzQuality.setDesignAdvistoryUnitStandardId(zjTzQuality.getDesignAdvistoryUnitStandardId());
           // 专业类型id
           dbzjTzQuality.setMajorTypeId(zjTzQuality.getMajorTypeId());
           // 专业类型name
           if (StrUtil.isNotEmpty(zjTzQuality.getMajorTypeId())) {
        	   String openBankName = baseCodeService.getBaseCodeItemName("zhuanYeLeiXing", zjTzQuality.getMajorTypeId());
        	   dbzjTzQuality.setMajorTypeName(openBankName);
           }
           // 对应资质id
           dbzjTzQuality.setCorrespondQualityId(zjTzQuality.getCorrespondQualityId());
           // 对应资质name
           if (StrUtil.isNotEmpty(zjTzQuality.getCorrespondQualityId())) {
        	   String openBankName = baseCodeService.getBaseCodeItemName("duiYingZiZhi", zjTzQuality.getCorrespondQualityId());
        	   dbzjTzQuality.setCorrespondQualityName(openBankName);
           }
           // 登记用户
           dbzjTzQuality.setRegisteredUser(zjTzQuality.getRegisteredUser());
           // 共通
           dbzjTzQuality.setModifyUserInfo(userKey, realName);
           flag = zjTzQualityMapper.updateByPrimaryKey(dbzjTzQuality);
           
           // 删除附件再新增
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzQuality.getQualityId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
               zjTzFileSelect.setModifyUserInfo(userKey, realName);
               zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           // 附件list
           List<ZjTzFile> zjTzFileList = zjTzQuality.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
               for(ZjTzFile zjTzFile:zjTzFileList) {
                   zjTzFile.setUid(UuidUtil.generate());
                   zjTzFile.setOtherId(zjTzQuality.getQualityId());
                   zjTzFile.setCreateUserInfo(userKey, realName);
                   zjTzFileMapper.insert(zjTzFile);
               }
           }
           
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzQuality);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzQuality(List<ZjTzQuality> zjTzQualityList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzQualityList != null && zjTzQualityList.size() > 0) {
        	for (ZjTzQuality zjTzQuality : zjTzQualityList) {
        		// 删除附件
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzQuality.getQualityId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
			}
           ZjTzQuality zjTzQuality = new ZjTzQuality();
           zjTzQuality.setModifyUserInfo(userKey, realName);
           flag = zjTzQualityMapper.batchDeleteUpdateZjTzQuality(zjTzQualityList, zjTzQuality);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzQualityList);
        }
    }
}
