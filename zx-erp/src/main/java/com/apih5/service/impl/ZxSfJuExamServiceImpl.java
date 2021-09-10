package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.dao.ZxSfJuExamItemMapper;
import com.apih5.mybatis.dao.ZxSfJuExamMapper;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.mybatis.pojo.ZxSfExamItem;
import com.apih5.mybatis.pojo.ZxSfJuExam;
import com.apih5.mybatis.pojo.ZxSfJuExamItem;
import com.apih5.service.ZxSfJuExamService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxSfJuExamService")
public class ZxSfJuExamServiceImpl implements ZxSfJuExamService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfJuExamMapper zxSfJuExamMapper;
    
    @Autowired(required = true)
	private ZxErpFileMapper zxErpFileMapper;
    
    @Autowired(required = true)
    private ZxSfJuExamItemMapper zxSfJuExamItemMapper;

    @Override
    public ResponseEntity getZxSfJuExamListByCondition(ZxSfJuExam zxSfJuExam) {
        if (zxSfJuExam == null) {
            zxSfJuExam = new ZxSfJuExam();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
        	zxSfJuExam.setCompanyId("");
        	zxSfJuExam.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
        	zxSfJuExam.setCompanyId(zxSfJuExam.getOrgID());
        	zxSfJuExam.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
        	zxSfJuExam.setOrgID(zxSfJuExam.getOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxSfJuExam.getPage(),zxSfJuExam.getLimit());
        // 获取数据
        List<ZxSfJuExam> zxSfJuExamList = zxSfJuExamMapper.selectByZxSfJuExamList(zxSfJuExam);
        // 查询明细
 		for (ZxSfJuExam zxSfExam1 : zxSfJuExamList) {
 			ZxSfJuExamItem zxSfJuExamItem = new ZxSfJuExamItem();
 			zxSfJuExamItem.setExamID(zxSfExam1.getZxSfJuExamId());
 			List<ZxSfJuExamItem> zxSfExamItemListAll = zxSfJuExamItemMapper.selectByZxSfJuExamItemList(zxSfJuExamItem);
 					
 			zxSfExam1.setZxSfJuExamItemList(zxSfExamItemListAll);
 		}
        
        // 查询附件
 		for (ZxSfJuExam zxSfJuExam2 : zxSfJuExamList) {
 			ZxErpFile zxErpFile = new ZxErpFile();
 			zxErpFile.setOtherId(zxSfJuExam2.getZxSfJuExamId());
 			List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
 			zxSfJuExam2.setFileList(zxErpFiles);
 		}
        // 得到分页信息
        PageInfo<ZxSfJuExam> p = new PageInfo<>(zxSfJuExamList);

        return repEntity.okList(zxSfJuExamList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfJuExamDetail(ZxSfJuExam zxSfJuExam) {
        if (zxSfJuExam == null) {
            zxSfJuExam = new ZxSfJuExam();
        }
        // 获取数据
        ZxSfJuExam dbZxSfJuExam = zxSfJuExamMapper.selectByPrimaryKey(zxSfJuExam.getZxSfJuExamId());
        // 数据存在
        if (dbZxSfJuExam != null) {
        	// 附件
        	ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(zxSfJuExam.getZxSfJuExamId());
        	List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
        	zxSfJuExam.setFileList(zxErpFiles);
            return repEntity.ok(dbZxSfJuExam);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfJuExam(ZxSfJuExam zxSfJuExam) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfJuExam.setZxSfJuExamId(UuidUtil.generate());
        zxSfJuExam.setCreateUserInfo(userKey, realName);
        //明细追加
        List<ZxSfJuExamItem> zxSfExamItemAll = zxSfJuExam.getZxSfJuExamItemList();		
		if (zxSfExamItemAll != null && zxSfExamItemAll.size() > 0) {
			for (ZxSfJuExamItem zxSfJuExamItem1 : zxSfExamItemAll) {
				zxSfJuExamItem1.setExamID(zxSfJuExam.getZxSfJuExamId());
				zxSfJuExamItem1.setZxSfJuExamItemId(UuidUtil.generate());
				zxSfJuExamItem1.setCreateUserInfo(userKey, realName);
				zxSfJuExamItemMapper.insert(zxSfJuExamItem1);
			}
		}
        // 添加附件
		List<ZxErpFile> fileList = zxSfJuExam.getFileList();
		if (fileList != null && fileList.size() > 0) {
			for (ZxErpFile zxErpFile : fileList) {
				zxErpFile.setOtherId(zxSfJuExam.getZxSfJuExamId());
				zxErpFile.setUid((UuidUtil.generate()));
				zxErpFile.setCreateUserInfo(userKey, realName);
				zxErpFileMapper.insert(zxErpFile);
			}
		}
        
        int flag = zxSfJuExamMapper.insert(zxSfJuExam);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfJuExam);
        }
    }

    @Override
    public ResponseEntity updateZxSfJuExam(ZxSfJuExam zxSfJuExam) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfJuExam dbZxSfJuExam = zxSfJuExamMapper.selectByPrimaryKey(zxSfJuExam.getZxSfJuExamId());
        if (dbZxSfJuExam != null && StrUtil.isNotEmpty(dbZxSfJuExam.getZxSfJuExamId())) {
           // 单位ID
           dbZxSfJuExam.setExamID(zxSfJuExam.getExamID());
           // 总分
           dbZxSfJuExam.setScore(zxSfJuExam.getScore());
           // 被考核单位领导
           dbZxSfJuExam.setUnitManage(zxSfJuExam.getUnitManage());
           // 填报人
           dbZxSfJuExam.setCreator(zxSfJuExam.getCreator());
           // 单位名称
           dbZxSfJuExam.setExamName(zxSfJuExam.getExamName());
           // 机构ID
           dbZxSfJuExam.setOrgID(zxSfJuExam.getOrgID());
           // 考核日期
           dbZxSfJuExam.setExamDate(zxSfJuExam.getExamDate());
           // 机构名称
           dbZxSfJuExam.setOrgName(zxSfJuExam.getOrgName());
           // 编辑时间
           dbZxSfJuExam.setEditTime(zxSfJuExam.getEditTime());
           // 状态
           dbZxSfJuExam.setAuditStatus(zxSfJuExam.getAuditStatus());
           // 公司
           dbZxSfJuExam.setIsCompany(zxSfJuExam.getIsCompany());
           // 集团
           dbZxSfJuExam.setIsGroup(zxSfJuExam.getIsGroup());
           // 所属公司ID
           dbZxSfJuExam.setCompanyId(zxSfJuExam.getCompanyId());
           // 所属公司名称
           dbZxSfJuExam.setCompanyName(zxSfJuExam.getCompanyName());
           // 备注
           dbZxSfJuExam.setRemarks(zxSfJuExam.getRemarks());
           // 排序
           dbZxSfJuExam.setSort(zxSfJuExam.getSort());
           // 共通
           dbZxSfJuExam.setModifyUserInfo(userKey, realName);
           flag = zxSfJuExamMapper.updateByPrimaryKey(dbZxSfJuExam);
           // 修改再新增(明细)
           ZxSfJuExamItem zxSfJuExamItem = new ZxSfJuExamItem();
           zxSfJuExamItem.setExamID(dbZxSfJuExam.getZxSfJuExamId());
			List<ZxSfJuExamItem> zxSfJuExamItemlist = zxSfJuExamItemMapper.selectByZxSfJuExamItemList(zxSfJuExamItem);
					
			if (zxSfJuExamItemlist != null && zxSfJuExamItemlist.size() > 0) {
				zxSfJuExamItem.setModifyUserInfo(userKey, realName);
				zxSfJuExamItemMapper.batchDeleteUpdateZxSfJuExamItem(zxSfJuExamItemlist, zxSfJuExamItem);
			}
			List<ZxSfJuExamItem> zxSfExamItemAll = zxSfJuExam.getZxSfJuExamItemList();		
			if (zxSfExamItemAll != null && zxSfExamItemAll.size() > 0) {
				for (ZxSfJuExamItem zxSfJuExamItem1 : zxSfExamItemAll) {
					zxSfJuExamItem1.setExamID(zxSfJuExam.getZxSfJuExamId());
					zxSfJuExamItem1.setZxSfJuExamItemId(UuidUtil.generate());
					zxSfJuExamItem1.setCreateUserInfo(userKey, realName);
					zxSfJuExamItemMapper.insert(zxSfJuExamItem1);
				}
			}
           
           // 修改在新增(附件)
		   ZxErpFile zxErpFileSelect = new ZxErpFile();
		   zxErpFileSelect.setOtherId(zxSfJuExam.getZxSfJuExamId());
		   List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
		   if (zxErpFiles != null && zxErpFiles.size() > 0) {
				zxErpFileSelect.setModifyUserInfo(userKey, realName);
				zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
			}
			List<ZxErpFile> fileList = zxSfJuExam.getFileList();
			if (fileList != null && fileList.size() > 0) {
				for (ZxErpFile zxErpFile : fileList) {
					zxErpFile.setOtherId(zxSfJuExam.getZxSfJuExamId());
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
            return repEntity.ok("sys.data.update",zxSfJuExam);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfJuExam(List<ZxSfJuExam> zxSfJuExamList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfJuExamList != null && zxSfJuExamList.size() > 0) {
        	for (ZxSfJuExam zxSfJuExam2 : zxSfJuExamList) {
        		ZxSfJuExamItem zxSfJuExamItem = new ZxSfJuExamItem();
                zxSfJuExamItem.setExamID(zxSfJuExam2.getZxSfJuExamId());
     			List<ZxSfJuExamItem> zxSfJuExamItemlist = zxSfJuExamItemMapper.selectByZxSfJuExamItemList(zxSfJuExamItem);
     					
     			if (zxSfJuExamItemlist != null && zxSfJuExamItemlist.size() > 0) {
     				zxSfJuExamItem.setModifyUserInfo(userKey, realName);
     				zxSfJuExamItemMapper.batchDeleteUpdateZxSfJuExamItem(zxSfJuExamItemlist, zxSfJuExamItem);
     			}

				// 删除附件
				ZxErpFile zxErpFile = new ZxErpFile();
				zxErpFile.setOtherId(zxSfJuExam2.getZxSfJuExamId());
				List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
				if (zxErpFiles != null && zxErpFiles.size() > 0) {
					zxErpFile.setModifyUserInfo(userKey, realName);
					zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
				}
			}
           ZxSfJuExam zxSfJuExam = new ZxSfJuExam();
           zxSfJuExam.setModifyUserInfo(userKey, realName);
           flag = zxSfJuExamMapper.batchDeleteUpdateZxSfJuExam(zxSfJuExamList, zxSfJuExam);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSfJuExamList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
