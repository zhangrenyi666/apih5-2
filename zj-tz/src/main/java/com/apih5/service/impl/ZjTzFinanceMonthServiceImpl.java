package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.dao.ZjTzFinanceMonthMapper;
import com.apih5.mybatis.dao.ZjTzProManageMapper;
import com.apih5.mybatis.pojo.ZjTzFinanceMonth;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzProManage;
import com.apih5.service.ZjTzFinanceMonthService;
import com.apih5.service.ZjTzPermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zjTzFinanceMonthService")
public class ZjTzFinanceMonthServiceImpl implements ZjTzFinanceMonthService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzFinanceMonthMapper zjTzFinanceMonthMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;
    
    @Autowired(required = true)
    private ZjTzProManageMapper zjTzProManageMapper;
    
    @Autowired(required = true)
    private ZjTzPermissionService zjTzPermissionService;

    @Override
    public ResponseEntity getZjTzFinanceMonthListByCondition(ZjTzFinanceMonth zjTzFinanceMonth) {
        if (zjTzFinanceMonth == null) {
            zjTzFinanceMonth = new ZjTzFinanceMonth();
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 不是集团的人员时
        if (!StrUtil.equals("admin", userId) && !StrUtil.equals("1", ext1)) {
        	// 选择全部项目是，通过拼接的sql去查询
            if(StrUtil.equals("all", zjTzFinanceMonth.getProjectId(), true)) {
            	zjTzFinanceMonth.setProjectId("");
            	zjTzFinanceMonth.setProjectIdSql(zjTzPermissionService.getZjtzProjectSql());
            }
        } else {
            // 集团人员时
            if(StrUtil.equals("all", zjTzFinanceMonth.getProjectId(), true)) {
            	zjTzFinanceMonth.setProjectId("");
            }
        }
        // 分页查询
        PageHelper.startPage(zjTzFinanceMonth.getPage(),zjTzFinanceMonth.getLimit());
        // 获取数据
        List<ZjTzFinanceMonth> zjTzFinanceMonthList = zjTzFinanceMonthMapper.selectByZjTzFinanceMonthList(zjTzFinanceMonth);
        for (ZjTzFinanceMonth zjTzFinanceMonth2 : zjTzFinanceMonthList) {
        	ZjTzFile file = new ZjTzFile();
        	file.setOtherId(zjTzFinanceMonth2.getFinanceMonthId());
        	List<ZjTzFile> files = zjTzFileMapper.selectByZjTzFileList(file);
        	zjTzFinanceMonth2.setZjTzFileList(files);
		}
        // 得到分页信息
        PageInfo<ZjTzFinanceMonth> p = new PageInfo<>(zjTzFinanceMonthList);

        return repEntity.okList(zjTzFinanceMonthList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzFinanceMonthDetails(ZjTzFinanceMonth zjTzFinanceMonth) {
    	 if (zjTzFinanceMonth == null) {
             zjTzFinanceMonth = new ZjTzFinanceMonth();
         }
    	 zjTzFinanceMonth = zjTzFinanceMonthMapper.selectByPrimaryKey(zjTzFinanceMonth.getFinanceMonthId());
         // 数据存在
         if (zjTzFinanceMonth != null && StrUtil.isNotEmpty(zjTzFinanceMonth.getFinanceMonthId())) {
         	ZjTzFile zjTzFileSelect = new ZjTzFile();
         	zjTzFileSelect.setOtherId(zjTzFinanceMonth.getFinanceMonthId());
         	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
         	zjTzFinanceMonth.setZjTzFileList(zjTzFileList);
             return repEntity.ok(zjTzFinanceMonth);
         }
         else {
             return repEntity.layerMessage("no", "无数据！");
         }
    }
    @Override
    public ResponseEntity saveZjTzFinanceMonth(ZjTzFinanceMonth zjTzFinanceMonth) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        
        if(zjTzFinanceMonth.getYearMonthDate() != null) {
        	zjTzFinanceMonth.setYearMonthStr(DateUtil.format(zjTzFinanceMonth.getYearMonthDate(), "yyyy-MM"));
        	//重复校验
        	ZjTzFinanceMonth financeMonth = new ZjTzFinanceMonth();
        	financeMonth.setProjectId(zjTzFinanceMonth.getProjectId());
        	financeMonth.setYearMonthStr(zjTzFinanceMonth.getYearMonthStr());
        	List<ZjTzFinanceMonth> financeMonths = zjTzFinanceMonthMapper.selectByZjTzFinanceMonthList(financeMonth);
        	if(financeMonths != null && financeMonths.size() >0) {
        		return repEntity.layerMessage("no", "该年月本项目已经添加。请重新选择！");
        	}
        }
        ZjTzProManage manage = zjTzProManageMapper.selectByPrimaryKey(zjTzFinanceMonth.getProjectId());
        if(manage != null && StrUtil.isNotEmpty(manage.getProjectId())) {
        	zjTzFinanceMonth.setProjectName(manage.getProjectName());
        }
        zjTzFinanceMonth.setFinanceMonthId(UuidUtil.generate());
        zjTzFinanceMonth.setReleaseId("0");
        zjTzFinanceMonth.setReleaseName("未上报");
        zjTzFinanceMonth.setCreateUserInfo(userKey, realName);
        int flag = zjTzFinanceMonthMapper.insert(zjTzFinanceMonth);
        // 附件list
    	List<ZjTzFile> zjTzFileList = zjTzFinanceMonth.getZjTzFileList();
    	if(zjTzFileList != null && zjTzFileList.size()>0) {
    		for(ZjTzFile zjTzFile:zjTzFileList) {
    			zjTzFile.setUid(UuidUtil.generate());
    			zjTzFile.setOtherId(zjTzFinanceMonth.getFinanceMonthId());
    			zjTzFile.setCreateUserInfo(userKey, realName);
    			zjTzFileMapper.insert(zjTzFile);
    		}
    	}
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzFinanceMonth);
        }
    }

    @Override
    public ResponseEntity updateZjTzFinanceMonth(ZjTzFinanceMonth zjTzFinanceMonth) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzFinanceMonth dbzjTzFinanceMonth = zjTzFinanceMonthMapper.selectByPrimaryKey(zjTzFinanceMonth.getFinanceMonthId());
        if (dbzjTzFinanceMonth != null && StrUtil.isNotEmpty(dbzjTzFinanceMonth.getFinanceMonthId())) {
        	if(StrUtil.equals(dbzjTzFinanceMonth.getReleaseId(), "1")) {
        		return repEntity.layerMessage("no", "已上报的数据不能修改");
        	}
//           // 年月date
//           dbzjTzFinanceMonth.setYearMonthDate(zjTzFinanceMonth.getYearMonthDate());
//           // 年月str
//           dbzjTzFinanceMonth.setYearMonthStr(zjTzFinanceMonth.getYearMonthStr());
//           // 项目id
//           dbzjTzFinanceMonth.setProjectId(zjTzFinanceMonth.getProjectId());
//           // 项目name
//           dbzjTzFinanceMonth.setProjectName(zjTzFinanceMonth.getProjectName());
           // 登记日期
           dbzjTzFinanceMonth.setRegisterDate(zjTzFinanceMonth.getRegisterDate());
           // 登记用户
           dbzjTzFinanceMonth.setRegisterPerson(zjTzFinanceMonth.getRegisterPerson());
           //过程
           dbzjTzFinanceMonth.setProcess(zjTzFinanceMonth.getProcess());
        
           // 共通
           dbzjTzFinanceMonth.setModifyUserInfo(userKey, realName);
           flag = zjTzFinanceMonthMapper.updateByPrimaryKey(dbzjTzFinanceMonth);
        // 删除附件再新增
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzFinanceMonth.getFinanceMonthId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        	   zjTzFileSelect.setModifyUserInfo(userKey, realName);
        	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           List<ZjTzFile> zjTzFileList = zjTzFinanceMonth.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
        	   for(ZjTzFile zjTzFile:zjTzFileList) {
        		   zjTzFile.setUid(UuidUtil.generate());
        		   zjTzFile.setOtherId(dbzjTzFinanceMonth.getFinanceMonthId());
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
            return repEntity.ok("sys.data.update",zjTzFinanceMonth);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzFinanceMonth(List<ZjTzFinanceMonth> zjTzFinanceMonthList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzFinanceMonthList != null && zjTzFinanceMonthList.size() > 0) {
        	for (ZjTzFinanceMonth zjTzFinanceMonth : zjTzFinanceMonthList) {
        		if(StrUtil.equals(zjTzFinanceMonth.getReleaseId(), "1")) {
        			return repEntity.layerMessage("no", "已上报的数据不能删除！");
        		}
        	}
        	for (ZjTzFinanceMonth zjTzFinanceMonth : zjTzFinanceMonthList) {
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzFinanceMonth.getFinanceMonthId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        	}
        	ZjTzFinanceMonth zjTzFinanceMonth = new ZjTzFinanceMonth();
           zjTzFinanceMonth.setModifyUserInfo(userKey, realName);
           flag = zjTzFinanceMonthMapper.batchDeleteUpdateZjTzFinanceMonth(zjTzFinanceMonthList, zjTzFinanceMonth);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzFinanceMonthList);
        }
    }

	@Override
	public ResponseEntity batchReleaseZjTzFinanceMonth(List<ZjTzFinanceMonth> zjTzFinanceMonthList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzFinanceMonthList != null && zjTzFinanceMonthList.size() > 0) {
        	for (ZjTzFinanceMonth zjTzRules : zjTzFinanceMonthList) {
				if(StrUtil.equals(zjTzRules.getReleaseId(), "1")) {
					 return repEntity.layerMessage("no", "包含已上报的数据，请重新选择！");
				}
			}
        	ZjTzFinanceMonth zjTzRules = new ZjTzFinanceMonth();
        	zjTzRules.setReleaseId("1");
        	zjTzRules.setReleaseName("已上报");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzFinanceMonthMapper.batchRecallZjTzFinanceMonth(zjTzFinanceMonthList, zjTzRules);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzFinanceMonthList);
        }
	}

	@Override
	public ResponseEntity batchRecallZjTzFinanceMonth(List<ZjTzFinanceMonth> zjTzFinanceMonthList) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzFinanceMonthList != null && zjTzFinanceMonthList.size() > 0) {
        	for (ZjTzFinanceMonth zjTzRules : zjTzFinanceMonthList) {
        		if(StrUtil.equals(zjTzRules.getReleaseId(), "0") || StrUtil.equals(zjTzRules.getReleaseId(), "2")) {
        			return repEntity.layerMessage("no", "包含未上报或被退回的数据，请重新选择！");
        		}
        	}
        	ZjTzFinanceMonth zjTzRules = new ZjTzFinanceMonth();
        	zjTzRules.setReleaseId("2");
        	zjTzRules.setReleaseName("被退回");
        	zjTzRules.setModifyUserInfo(userKey, realName);
        	flag = zjTzFinanceMonthMapper.batchRecallZjTzFinanceMonth(zjTzFinanceMonthList, zjTzRules);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzFinanceMonthList);
        }
	}
}
