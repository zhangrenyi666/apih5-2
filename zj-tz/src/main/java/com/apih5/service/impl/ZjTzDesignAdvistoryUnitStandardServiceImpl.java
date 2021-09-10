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
import com.apih5.mybatis.dao.ZjTzDesignAdvistoryUnitStandardMapper;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzQualityMapper;
import com.apih5.mybatis.pojo.ZjTzDesignAdvistoryUnitStandard;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzQuality;
import com.apih5.service.ZjTzDesignAdvistoryUnitStandardService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzDesignAdvistoryUnitStandardService")
public class ZjTzDesignAdvistoryUnitStandardServiceImpl implements ZjTzDesignAdvistoryUnitStandardService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzDesignAdvistoryUnitStandardMapper zjTzDesignAdvistoryUnitStandardMapper;
    
    @Autowired(required = true)
	private BaseCodeService baseCodeService;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private ZjTzQualityMapper zjTzQualityMapper;


    @Override
    public ResponseEntity getZjTzDesignAdvistoryUnitStandardListByCondition(ZjTzDesignAdvistoryUnitStandard zjTzDesignAdvistoryUnitStandard) {
        if (zjTzDesignAdvistoryUnitStandard == null) {
            zjTzDesignAdvistoryUnitStandard = new ZjTzDesignAdvistoryUnitStandard();
        }
        
        // 分页查询
        PageHelper.startPage(zjTzDesignAdvistoryUnitStandard.getPage(),zjTzDesignAdvistoryUnitStandard.getLimit());
        // 获取数据
        List<ZjTzDesignAdvistoryUnitStandard> zjTzDesignAdvistoryUnitStandardList = zjTzDesignAdvistoryUnitStandardMapper.selectByZjTzDesignAdvistoryUnitStandardList(zjTzDesignAdvistoryUnitStandard);
        // 得到分页信息
        PageInfo<ZjTzDesignAdvistoryUnitStandard> p = new PageInfo<>(zjTzDesignAdvistoryUnitStandardList);

        return repEntity.okList(zjTzDesignAdvistoryUnitStandardList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzDesignAdvistoryUnitStandardDetails(ZjTzDesignAdvistoryUnitStandard zjTzDesignAdvistoryUnitStandard) {
        if (zjTzDesignAdvistoryUnitStandard == null) {
            zjTzDesignAdvistoryUnitStandard = new ZjTzDesignAdvistoryUnitStandard();
        }
        String majorTypeName = "";
        String correspondQualityName = "";
        // 获取数据
        ZjTzDesignAdvistoryUnitStandard dbZjTzDesignAdvistoryUnitStandard = zjTzDesignAdvistoryUnitStandardMapper.selectByPrimaryKey(zjTzDesignAdvistoryUnitStandard.getDesignAdvistoryUnitStandardId());
        // 数据存在
        if (dbZjTzDesignAdvistoryUnitStandard != null) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(dbZjTzDesignAdvistoryUnitStandard.getDesignAdvistoryUnitStandardId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	dbZjTzDesignAdvistoryUnitStandard.setZjTzFileList(zjTzFileList);
        	//资质
        	ZjTzQuality zjTzQuality = new ZjTzQuality();
        	zjTzQuality.setDesignAdvistoryUnitStandardId(dbZjTzDesignAdvistoryUnitStandard.getDesignAdvistoryUnitStandardId());
        	List<ZjTzQuality> zjTzQualityList = zjTzQualityMapper.selectByZjTzQualityList(zjTzQuality);
        	for (ZjTzQuality zjTzQuality2 : zjTzQualityList) {
            	ZjTzFile zjTzFileQuality = new ZjTzFile();
            	zjTzFileQuality.setOtherId(zjTzQuality2.getQualityId());
            	List<ZjTzFile> zjTzFileQualityList = zjTzFileMapper.selectByZjTzFileList(zjTzFileQuality);
            	zjTzQuality2.setZjTzFileList(zjTzFileQualityList);
    		}
        	dbZjTzDesignAdvistoryUnitStandard.setZjTzQualityList(zjTzQualityList);
        	for (ZjTzQuality quality : zjTzQualityList) {
        		majorTypeName += "" + quality.getMajorTypeName() + ",";
        		if(majorTypeName.indexOf(",")>0) {
        			majorTypeName = majorTypeName.substring(0, majorTypeName.length()-1);
        		}
        		correspondQualityName += "" + quality.getCorrespondQualityName() + ",";
        		if(correspondQualityName.indexOf(",")>0) {
        			correspondQualityName = correspondQualityName.substring(0, correspondQualityName.length()-1);
        		}
			}
        	dbZjTzDesignAdvistoryUnitStandard.setMajorTypeName(majorTypeName);
        	dbZjTzDesignAdvistoryUnitStandard.setCorrespondQualityName(correspondQualityName);
            return repEntity.ok(dbZjTzDesignAdvistoryUnitStandard);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzDesignAdvistoryUnitStandard(ZjTzDesignAdvistoryUnitStandard zjTzDesignAdvistoryUnitStandard) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	//组织机构代码重复校验
    	ZjTzDesignAdvistoryUnitStandard advistoryUnitStandard = new ZjTzDesignAdvistoryUnitStandard();
    	advistoryUnitStandard.setOrgCode(zjTzDesignAdvistoryUnitStandard.getOrgCode());
    	List<ZjTzDesignAdvistoryUnitStandard> advistoryUnitStandards = zjTzDesignAdvistoryUnitStandardMapper.selectByZjTzDesignAdvistoryUnitStandardList(advistoryUnitStandard);
    	if(advistoryUnitStandards != null && advistoryUnitStandards.size() >0) {
    		 return repEntity.layerMessage("no", "组织机构代码已存在，请重新输入！");
    	}
    	zjTzDesignAdvistoryUnitStandard.setDesignAdvistoryUnitStandardId(UuidUtil.generate());
    	//单位类型
    	if (StrUtil.isNotEmpty(zjTzDesignAdvistoryUnitStandard.getUnitTypeId())) {
    		String openBankName = baseCodeService.getBaseCodeItemName("danWeiLeiXing", zjTzDesignAdvistoryUnitStandard.getUnitTypeId());
    		zjTzDesignAdvistoryUnitStandard.setUnitTypeName(openBankName);
    	}
    	//发内外部单位
    	if (StrUtil.isNotEmpty(zjTzDesignAdvistoryUnitStandard.getInOutUnitId())) {
    		String openBankName = baseCodeService.getBaseCodeItemName("neiWaiBuDanWei", zjTzDesignAdvistoryUnitStandard.getInOutUnitId());
    		zjTzDesignAdvistoryUnitStandard.setInOutUnitName(openBankName);
    	}
    	zjTzDesignAdvistoryUnitStandard.setCreateUserInfo(userKey, realName);
    	int flag = zjTzDesignAdvistoryUnitStandardMapper.insert(zjTzDesignAdvistoryUnitStandard);
    	//资质list
    	List<ZjTzQuality> zjTzQualityList = zjTzDesignAdvistoryUnitStandard.getZjTzQualityList();
    	if(zjTzQualityList != null && zjTzQualityList.size() >0) {
    		for (ZjTzQuality zjTzQuality : zjTzQualityList) {
        		zjTzQuality.setQualityId(UuidUtil.generate());
        		zjTzQuality.setDesignAdvistoryUnitStandardId(zjTzDesignAdvistoryUnitStandard.getDesignAdvistoryUnitStandardId());
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
        		flag = zjTzQualityMapper.insert(zjTzQuality);
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
    	}
    	// 附件list
    	List<ZjTzFile> zjTzFileList = zjTzDesignAdvistoryUnitStandard.getZjTzFileList();
    	if(zjTzFileList != null && zjTzFileList.size()>0) {
    		for(ZjTzFile zjTzFile:zjTzFileList) {
    			zjTzFile.setUid(UuidUtil.generate());
    			zjTzFile.setOtherId(zjTzDesignAdvistoryUnitStandard.getDesignAdvistoryUnitStandardId());
    			zjTzFile.setCreateUserInfo(userKey, realName);
    			zjTzFileMapper.insert(zjTzFile);
    		}
    	}
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzDesignAdvistoryUnitStandard);
        }
    }

    @Override
    public ResponseEntity updateZjTzDesignAdvistoryUnitStandard(ZjTzDesignAdvistoryUnitStandard zjTzDesignAdvistoryUnitStandard) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzDesignAdvistoryUnitStandard dbzjTzDesignAdvistoryUnitStandard = zjTzDesignAdvistoryUnitStandardMapper.selectByPrimaryKey(zjTzDesignAdvistoryUnitStandard.getDesignAdvistoryUnitStandardId());
        if (dbzjTzDesignAdvistoryUnitStandard != null && StrUtil.isNotEmpty(dbzjTzDesignAdvistoryUnitStandard.getDesignAdvistoryUnitStandardId())) {
           // 单位名称
           dbzjTzDesignAdvistoryUnitStandard.setUnitName(zjTzDesignAdvistoryUnitStandard.getUnitName());
//           // 组织机构代码
//           dbzjTzDesignAdvistoryUnitStandard.setOrgCode(zjTzDesignAdvistoryUnitStandard.getOrgCode());
           // 单位类型id
           dbzjTzDesignAdvistoryUnitStandard.setUnitTypeId(zjTzDesignAdvistoryUnitStandard.getUnitTypeId());
           // 单位类型name
           if (StrUtil.isNotEmpty(zjTzDesignAdvistoryUnitStandard.getUnitTypeId())) {
        	   String openBankName = baseCodeService.getBaseCodeItemName("danWeiLeiXing", zjTzDesignAdvistoryUnitStandard.getUnitTypeId());
        	   dbzjTzDesignAdvistoryUnitStandard.setUnitTypeName(openBankName);
           }
           // 内外部单位id
           dbzjTzDesignAdvistoryUnitStandard.setInOutUnitId(zjTzDesignAdvistoryUnitStandard.getInOutUnitId());
           // 内外部单位name
           if (StrUtil.isNotEmpty(zjTzDesignAdvistoryUnitStandard.getInOutUnitId())) {
          		String openBankName = baseCodeService.getBaseCodeItemName("neiWaiBuDanWei", zjTzDesignAdvistoryUnitStandard.getInOutUnitId());
          		dbzjTzDesignAdvistoryUnitStandard.setInOutUnitName(openBankName);
          	}
           // 备注
           dbzjTzDesignAdvistoryUnitStandard.setRemarks(zjTzDesignAdvistoryUnitStandard.getRemarks());
           // 共通
           dbzjTzDesignAdvistoryUnitStandard.setModifyUserInfo(userKey, realName);
           flag = zjTzDesignAdvistoryUnitStandardMapper.updateByPrimaryKey(dbzjTzDesignAdvistoryUnitStandard);
           
           // 删除附件再新增
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzDesignAdvistoryUnitStandard.getDesignAdvistoryUnitStandardId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
               zjTzFileSelect.setModifyUserInfo(userKey, realName);
               zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           // 附件list
           List<ZjTzFile> zjTzFileList = zjTzDesignAdvistoryUnitStandard.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
               for(ZjTzFile zjTzFile:zjTzFileList) {
                   zjTzFile.setUid(UuidUtil.generate());
                   zjTzFile.setOtherId(zjTzDesignAdvistoryUnitStandard.getDesignAdvistoryUnitStandardId());
                   zjTzFile.setCreateUserInfo(userKey, realName);
                   zjTzFileMapper.insert(zjTzFile);
               }
           }
           //资质list=先删除再新增
           ZjTzQuality delZjTzQuality = new ZjTzQuality();
           delZjTzQuality.setDesignAdvistoryUnitStandardId(dbzjTzDesignAdvistoryUnitStandard.getDesignAdvistoryUnitStandardId());
           List<ZjTzQuality> delZjTzQualityList = zjTzQualityMapper.selectByZjTzQualityList(delZjTzQuality);
           if(delZjTzQualityList != null &&delZjTzQualityList.size() >0) {
        	   delZjTzQuality.setModifyUserInfo(userKey, realName);
        	   zjTzQualityMapper.batchDeleteUpdateZjTzQuality(delZjTzQualityList, delZjTzQuality);
           }
           List<ZjTzQuality> zjTzQualityList = zjTzDesignAdvistoryUnitStandard.getZjTzQualityList();
           for (ZjTzQuality zjTzQuality : zjTzQualityList) {
        	   zjTzQuality.setQualityId(UuidUtil.generate());
        	   zjTzQuality.setDesignAdvistoryUnitStandardId(dbzjTzDesignAdvistoryUnitStandard.getDesignAdvistoryUnitStandardId());
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
        	   flag = zjTzQualityMapper.insert(zjTzQuality);
        	   // 附件list
        	   List<ZjTzFile> zjTzFileQualityList = zjTzQuality.getZjTzFileList();
        	   if(zjTzFileQualityList != null && zjTzFileQualityList.size()>0) {
        		   for(ZjTzFile zjTzFile:zjTzFileQualityList) {
        			   zjTzFile.setUid(UuidUtil.generate());
        			   zjTzFile.setOtherId(zjTzQuality.getQualityId());
        			   zjTzFile.setCreateUserInfo(userKey, realName);
        			   zjTzFileMapper.insert(zjTzFile);
        		   }
        	   }
           }
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzDesignAdvistoryUnitStandard);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzDesignAdvistoryUnitStandard(List<ZjTzDesignAdvistoryUnitStandard> zjTzDesignAdvistoryUnitStandardList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzDesignAdvistoryUnitStandardList != null && zjTzDesignAdvistoryUnitStandardList.size() > 0) {
        	for (ZjTzDesignAdvistoryUnitStandard zjTzDesignAdvistoryUnitStandard : zjTzDesignAdvistoryUnitStandardList) {
        		// 删除附件
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzDesignAdvistoryUnitStandard.getDesignAdvistoryUnitStandardId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        		//删除资质
        		ZjTzQuality quality = new ZjTzQuality();
        		quality.setDesignAdvistoryUnitStandardId(zjTzDesignAdvistoryUnitStandard.getDesignAdvistoryUnitStandardId());
        		List<ZjTzQuality> zjTzQualities = zjTzQualityMapper.selectByZjTzQualityList(quality);
        		if(zjTzQualities != null && zjTzQualities.size() >0) {
        			//删除资质的附件
        			for (ZjTzQuality tzQuality : zjTzQualities) {
        				ZjTzFile zjTzFileQuality = new ZjTzFile();
        				zjTzFileQuality.setOtherId(tzQuality.getQualityId());
                		List<ZjTzFile> delQualityZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileQuality);
                		if(delQualityZjTzFileList != null && delQualityZjTzFileList.size()>0) {
                			zjTzFileQuality.setModifyUserInfo(userKey, realName);
                			zjTzFileMapper.batchDeleteUpdateZjTzFile(delQualityZjTzFileList, zjTzFileQuality);
                		}
					}
        			quality.setModifyUserInfo(userKey, realName);
        			zjTzQualityMapper.batchDeleteUpdateZjTzQuality(zjTzQualities, quality);
        		}
        	}
        	ZjTzDesignAdvistoryUnitStandard zjTzDesignAdvistoryUnitStandard = new ZjTzDesignAdvistoryUnitStandard();
        	zjTzDesignAdvistoryUnitStandard.setModifyUserInfo(userKey, realName);
        	flag = zjTzDesignAdvistoryUnitStandardMapper.batchDeleteUpdateZjTzDesignAdvistoryUnitStandard(zjTzDesignAdvistoryUnitStandardList, zjTzDesignAdvistoryUnitStandard);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzDesignAdvistoryUnitStandardList);
        }
    }

    @Override
    public ResponseEntity setZjTzDesignAdvistoryUnitStandardLibrary(ZjTzDesignAdvistoryUnitStandard zjTzDesignAdvistoryUnitStandard) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZjTzDesignAdvistoryUnitStandard dbzjTzDesignAdvistoryUnitStandard = zjTzDesignAdvistoryUnitStandardMapper.selectByPrimaryKey(zjTzDesignAdvistoryUnitStandard.getDesignAdvistoryUnitStandardId());
    	if (dbzjTzDesignAdvistoryUnitStandard != null && StrUtil.isNotEmpty(dbzjTzDesignAdvistoryUnitStandard.getDesignAdvistoryUnitStandardId())) {
    		// 所属库id
    		dbzjTzDesignAdvistoryUnitStandard.setLibraryId(zjTzDesignAdvistoryUnitStandard.getLibraryId());
    		// 所属库name
    		if (StrUtil.isNotEmpty(zjTzDesignAdvistoryUnitStandard.getLibraryId())) {
    			String openBankName = baseCodeService.getBaseCodeItemName("suoShuKu", zjTzDesignAdvistoryUnitStandard.getLibraryId());
    			dbzjTzDesignAdvistoryUnitStandard.setLibraryName(openBankName);
    		}
    		// 共通
    		dbzjTzDesignAdvistoryUnitStandard.setModifyUserInfo(userKey, realName);
    		flag = zjTzDesignAdvistoryUnitStandardMapper.updateByPrimaryKey(dbzjTzDesignAdvistoryUnitStandard);
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zjTzDesignAdvistoryUnitStandard);
    	}
    }

	@Override
	public ResponseEntity getZjTzDesignAdvistoryUnitStandardListForHard(ZjTzDesignAdvistoryUnitStandard zjTzDesignAdvistoryUnitStandard) {
		 if (zjTzDesignAdvistoryUnitStandard == null) {
	            zjTzDesignAdvistoryUnitStandard = new ZjTzDesignAdvistoryUnitStandard();
	        }
	        
	        // 分页查询
	        PageHelper.startPage(zjTzDesignAdvistoryUnitStandard.getPage(),zjTzDesignAdvistoryUnitStandard.getLimit());
	        // 获取数据
	        List<ZjTzDesignAdvistoryUnitStandard> zjTzDesignAdvistoryUnitStandardList = zjTzDesignAdvistoryUnitStandardMapper.selectByZjTzDesignAdvistoryUnitStandardList(zjTzDesignAdvistoryUnitStandard);
	        for (ZjTzDesignAdvistoryUnitStandard zjTzDesignAdvistoryUnitStandard2 : zjTzDesignAdvistoryUnitStandardList) {
	        	//附件
	        	ZjTzFile zjTzFileSelect = new ZjTzFile();
	        	zjTzFileSelect.setOtherId(zjTzDesignAdvistoryUnitStandard2.getDesignAdvistoryUnitStandardId());
	        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
	        	zjTzDesignAdvistoryUnitStandard2.setZjTzFileList(zjTzFileList);
	        	//资质
	        	ZjTzQuality zjTzQuality = new ZjTzQuality();
	        	zjTzQuality.setDesignAdvistoryUnitStandardId(zjTzDesignAdvistoryUnitStandard2.getDesignAdvistoryUnitStandardId());
	        	List<ZjTzQuality> zjTzQualityList = zjTzQualityMapper.selectByZjTzQualityList(zjTzQuality);
	        	for (ZjTzQuality zjTzQuality2 : zjTzQualityList) {
	            	ZjTzFile zjTzFileQuality = new ZjTzFile();
	            	zjTzFileQuality.setOtherId(zjTzQuality2.getQualityId());
	            	List<ZjTzFile> zjTzFileQualityList = zjTzFileMapper.selectByZjTzFileList(zjTzFileQuality);
	            	zjTzQuality2.setZjTzFileList(zjTzFileQualityList);
	    		}
	        	zjTzDesignAdvistoryUnitStandard2.setZjTzQualityList(zjTzQualityList);
	        	String majorTypeName = "";
	            String correspondQualityName = "";
	        	for (ZjTzQuality quality : zjTzQualityList) {
	        		majorTypeName += "'" + quality.getMajorTypeName() + "',";
	        		if(majorTypeName.indexOf(",")>0) {
	        			majorTypeName = majorTypeName.substring(0, majorTypeName.length()-1);
	        		}
	        		correspondQualityName += "'" + quality.getCorrespondQualityName() + "',";
	        		if(correspondQualityName.indexOf(",")>0) {
	        			correspondQualityName = correspondQualityName.substring(0, correspondQualityName.length()-1);
	        		}
				}
	        	zjTzDesignAdvistoryUnitStandard2.setMajorTypeName(majorTypeName);
	        	zjTzDesignAdvistoryUnitStandard2.setCorrespondQualityName(correspondQualityName);
	        }
	        // 得到分页信息
	        PageInfo<ZjTzDesignAdvistoryUnitStandard> p = new PageInfo<>(zjTzDesignAdvistoryUnitStandardList);

	        return repEntity.okList(zjTzDesignAdvistoryUnitStandardList, p.getTotal());
	}
}
