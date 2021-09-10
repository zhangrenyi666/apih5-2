package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtProduceAmtCalMapper;
import com.apih5.mybatis.pojo.ZxCtProduceAmtCal;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxCtProduceAmtCalService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zxCtProduceAmtCalService")
public class ZxCtProduceAmtCalServiceImpl implements ZxCtProduceAmtCalService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtProduceAmtCalMapper zxCtProduceAmtCalMapper;

    @Autowired(required = true)
    private ZxErpFileServiceImpl zxErpFileServiceImpl;

    @SuppressWarnings("unchecked")
	@Override
    public ResponseEntity getZxCtProduceAmtCalListByCondition(ZxCtProduceAmtCal zxCtProduceAmtCal) {
        if (zxCtProduceAmtCal == null) {
            zxCtProduceAmtCal = new ZxCtProduceAmtCal();
        }
        // 分页查询
        PageHelper.startPage(zxCtProduceAmtCal.getPage(),zxCtProduceAmtCal.getLimit());
        // 获取数据
        List<ZxCtProduceAmtCal> zxCtProduceAmtCalList = zxCtProduceAmtCalMapper.selectByZxCtProduceAmtCalList(zxCtProduceAmtCal);
        // 得到分页信息
        PageInfo<ZxCtProduceAmtCal> p = new PageInfo<>(zxCtProduceAmtCalList);
        
        for (ZxCtProduceAmtCal ctProduceAmtCal : zxCtProduceAmtCalList) {
        	// 附件
        	ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(ctProduceAmtCal.getId());
        	zxErpFile.setOtherType("1");
        	List<ZxErpFile> zxErpFileList = (List<ZxErpFile>) zxErpFileServiceImpl.getZxErpFileListByCondition(zxErpFile).getData();
        	ctProduceAmtCal.setAttachment(zxErpFileList);
		}
        
        return repEntity.okList(zxCtProduceAmtCalList, p.getTotal());
    }

    @SuppressWarnings("unchecked")
	@Override
    public ResponseEntity getZxCtProduceAmtCalDetail(ZxCtProduceAmtCal zxCtProduceAmtCal) {
        if (zxCtProduceAmtCal == null) {
            zxCtProduceAmtCal = new ZxCtProduceAmtCal();
        }
        // 获取数据
        ZxCtProduceAmtCal dbZxCtProduceAmtCal = zxCtProduceAmtCalMapper.selectByPrimaryKey(zxCtProduceAmtCal.getId());
        // 数据存在
        if (dbZxCtProduceAmtCal != null) {
        	// 附件
        	ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(dbZxCtProduceAmtCal.getId());
        	zxErpFile.setOtherType("1");
        	List<ZxErpFile> zxErpFileList = (List<ZxErpFile>) zxErpFileServiceImpl.getZxErpFileListByCondition(zxErpFile).getData();
        	dbZxCtProduceAmtCal.setAttachment(zxErpFileList);
        	
            return repEntity.ok(dbZxCtProduceAmtCal);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtProduceAmtCal(ZxCtProduceAmtCal zxCtProduceAmtCal) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtProduceAmtCal.setId(UuidUtil.generate());
        zxCtProduceAmtCal.setCreateUserInfo(userKey, realName);
        if (zxCtProduceAmtCal.getPeriodDate() != null) {
        	zxCtProduceAmtCal.setPeriod(DateUtil.format(zxCtProduceAmtCal.getPeriodDate(), "yyyyMM"));
		}
        
        int flag = zxCtProduceAmtCalMapper.insert(zxCtProduceAmtCal);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// 附件
        	List<ZxErpFile> zxErpFileList = zxCtProduceAmtCal.getAttachment();
        	if (zxErpFileList != null && zxErpFileList.size() > 0) {
				for (ZxErpFile zxErpFile : zxErpFileList) {
		        	zxErpFile.setOtherId(zxCtProduceAmtCal.getId());
		        	zxErpFile.setOtherType("1");
		        	zxErpFileServiceImpl.saveZxErpFile(zxErpFile);
				}
			}
        	
            return repEntity.ok("sys.data.sava", zxCtProduceAmtCal);
        }
    }

    @Override
    public ResponseEntity updateZxCtProduceAmtCal(ZxCtProduceAmtCal zxCtProduceAmtCal) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtProduceAmtCal dbZxCtProduceAmtCal = zxCtProduceAmtCalMapper.selectByPrimaryKey(zxCtProduceAmtCal.getId());
        if (dbZxCtProduceAmtCal != null && StrUtil.isNotEmpty(dbZxCtProduceAmtCal.getId())) {
           // 项目ID（关联ID）
           dbZxCtProduceAmtCal.setOrgID(zxCtProduceAmtCal.getOrgID());
           // 项目名称
           dbZxCtProduceAmtCal.setOrgName(zxCtProduceAmtCal.getOrgName());
           dbZxCtProduceAmtCal.setPeriodDate(zxCtProduceAmtCal.getPeriodDate());
           // 期次
           if (zxCtProduceAmtCal.getPeriodDate() != null) {
        	   dbZxCtProduceAmtCal.setPeriod(DateUtil.format(zxCtProduceAmtCal.getPeriodDate(), "yyyyMM"));
	   		} else {
	   			dbZxCtProduceAmtCal.setPeriod(null);
	   		}
           // 产值
           dbZxCtProduceAmtCal.setProduceAmt(zxCtProduceAmtCal.getProduceAmt());
           // 登记人
           dbZxCtProduceAmtCal.setReporter(zxCtProduceAmtCal.getReporter());
           // 附件
           dbZxCtProduceAmtCal.setAttachment(zxCtProduceAmtCal.getAttachment());
           // 备注
           dbZxCtProduceAmtCal.setRemarks(zxCtProduceAmtCal.getRemarks());
           // 排序
           dbZxCtProduceAmtCal.setSort(zxCtProduceAmtCal.getSort());
           // 共通
           dbZxCtProduceAmtCal.setModifyUserInfo(userKey, realName);
           flag = zxCtProduceAmtCalMapper.updateByPrimaryKey(dbZxCtProduceAmtCal);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// 附件
        	ZxErpFile delErpFile = new ZxErpFile();
        	delErpFile.setOtherId(dbZxCtProduceAmtCal.getId());
        	delErpFile.setOtherType("1");
        	zxErpFileServiceImpl.deleteAllZxErpFile(delErpFile);
        	
        	List<ZxErpFile> zxErpFileList = zxCtProduceAmtCal.getAttachment();
        	if (zxErpFileList != null && zxErpFileList.size() > 0) {
				for (ZxErpFile zxErpFile : zxErpFileList) {
		        	zxErpFile.setOtherId(dbZxCtProduceAmtCal.getId());
		        	zxErpFile.setOtherType("1");
		        	zxErpFileServiceImpl.saveZxErpFile(zxErpFile);
				}
			}
        	
            return repEntity.ok("sys.data.update",zxCtProduceAmtCal);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtProduceAmtCal(List<ZxCtProduceAmtCal> zxCtProduceAmtCalList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtProduceAmtCalList != null && zxCtProduceAmtCalList.size() > 0) {
           ZxCtProduceAmtCal zxCtProduceAmtCal = new ZxCtProduceAmtCal();
           zxCtProduceAmtCal.setModifyUserInfo(userKey, realName);
           flag = zxCtProduceAmtCalMapper.batchDeleteUpdateZxCtProduceAmtCal(zxCtProduceAmtCalList, zxCtProduceAmtCal);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	for (ZxCtProduceAmtCal zxCtProduceAmtCal : zxCtProduceAmtCalList) {
        		// 附件
            	ZxErpFile delErpFile = new ZxErpFile();
            	delErpFile.setOtherId(zxCtProduceAmtCal.getId());
            	delErpFile.setOtherType("1");
            	zxErpFileServiceImpl.deleteAllZxErpFile(delErpFile);
			}
        	
            return repEntity.ok("sys.data.delete",zxCtProduceAmtCalList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
