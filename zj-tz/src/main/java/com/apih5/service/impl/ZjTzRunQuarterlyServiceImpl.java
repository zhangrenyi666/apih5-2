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
import com.apih5.mybatis.dao.ZjTzRunQuarterlyMapper;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.mybatis.pojo.ZjTzRunQuarterly;
import com.apih5.service.ZjTzRunQuarterlyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;

@Service("zjTzRunQuarterlyService")
public class ZjTzRunQuarterlyServiceImpl implements ZjTzRunQuarterlyService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzRunQuarterlyMapper zjTzRunQuarterlyMapper;
    
    @Autowired(required = true)
    private ZjTzFileMapper zjTzFileMapper;

    @Override
    public ResponseEntity getZjTzRunQuarterlyListByCondition(ZjTzRunQuarterly zjTzRunQuarterly) {
        if (zjTzRunQuarterly == null) {
            zjTzRunQuarterly = new ZjTzRunQuarterly();
        }
        // 分页查询
        PageHelper.startPage(zjTzRunQuarterly.getPage(),zjTzRunQuarterly.getLimit());
        // 获取数据
        List<ZjTzRunQuarterly> zjTzRunQuarterlyList = zjTzRunQuarterlyMapper.selectByZjTzRunQuarterlyList(zjTzRunQuarterly);
        for (ZjTzRunQuarterly zjTzRunQuarterly2 : zjTzRunQuarterlyList) {
        	ZjTzFile file = new ZjTzFile();
        	file.setOtherId(zjTzRunQuarterly2.getRunQuarterlyId());
        	List<ZjTzFile> files = zjTzFileMapper.selectByZjTzFileList(file);
        	zjTzRunQuarterly2.setZjTzFileList(files);
		}
        // 得到分页信息
        PageInfo<ZjTzRunQuarterly> p = new PageInfo<>(zjTzRunQuarterlyList);

        return repEntity.okList(zjTzRunQuarterlyList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzRunQuarterlyDetails(ZjTzRunQuarterly zjTzRunQuarterly) {
        if (zjTzRunQuarterly == null) {
            zjTzRunQuarterly = new ZjTzRunQuarterly();
        }
        // 获取数据
        ZjTzRunQuarterly dbZjTzRunQuarterly = zjTzRunQuarterlyMapper.selectByPrimaryKey(zjTzRunQuarterly.getRunQuarterlyId());
        // 数据存在
        if (dbZjTzRunQuarterly != null) {
        	ZjTzFile file = new ZjTzFile();
        	file.setOtherId(dbZjTzRunQuarterly.getRunQuarterlyId());
        	List<ZjTzFile> files = zjTzFileMapper.selectByZjTzFileList(file);
        	dbZjTzRunQuarterly.setZjTzFileList(files);
            return repEntity.ok(dbZjTzRunQuarterly);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzRunQuarterly(ZjTzRunQuarterly zjTzRunQuarterly) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        String yearStr = "";
        //重复校验
        
        if(zjTzRunQuarterly.getYearDate() != null) {
        	yearStr = DateUtil.format(zjTzRunQuarterly.getYearDate(), "yyyy");
        	ZjTzRunQuarterly quarterlySelect = new ZjTzRunQuarterly();
        	quarterlySelect.setYearStr(yearStr);
        	quarterlySelect.setQuarter(zjTzRunQuarterly.getQuarter());
        	List<ZjTzRunQuarterly> quarterlies = zjTzRunQuarterlyMapper.selectByZjTzRunQuarterlyList(quarterlySelect);
        	if(quarterlies != null && quarterlies.size() >0) {
        		return repEntity.layerMessage("no", "该季度已经添加，请重新选择");
        	}
        	//按顺序添加（不用依次，可跳过）
        	ZjTzRunQuarterly quarterly = new ZjTzRunQuarterly();
        	quarterly.setYearStr(yearStr);
             if(StrUtil.equals(zjTzRunQuarterly.getQuarter(), "1") || StrUtil.equals(zjTzRunQuarterly.getQuarter(), "2") || StrUtil.equals(zjTzRunQuarterly.getQuarter(), "3")) {
            	 quarterly.setQuarterFlag(zjTzRunQuarterly.getQuarter());
            	 quarterly.setSequenceFlag("是1.2.3季度的时候判断一下是够有大于前台传的这个季度的数据");
            	 List<ZjTzRunQuarterly> quarterlyList = zjTzRunQuarterlyMapper.selectByZjTzRunQuarterlyList(quarterly);
            	 if(quarterlyList != null &&quarterlyList.size() >0) {
            		 return repEntity.layerMessage("no", "请按顺序添加季度!");
            	 }
        	}
        }
        
        
        
        zjTzRunQuarterly.setRunQuarterlyId(UuidUtil.generate());
        zjTzRunQuarterly.setYearStr(yearStr);
        zjTzRunQuarterly.setCreateUserInfo(userKey, realName);
        int flag = zjTzRunQuarterlyMapper.insert(zjTzRunQuarterly);
        // 附件list
    	List<ZjTzFile> zjTzFileList = zjTzRunQuarterly.getZjTzFileList();
    	if(zjTzFileList != null && zjTzFileList.size()>0) {
    		for(ZjTzFile zjTzFile:zjTzFileList) {
    			zjTzFile.setUid(UuidUtil.generate());
    			zjTzFile.setOtherId(zjTzRunQuarterly.getRunQuarterlyId());
    			zjTzFile.setCreateUserInfo(userKey, realName);
    			zjTzFileMapper.insert(zjTzFile);
    		}
    	}
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzRunQuarterly);
        }
    }

    @Override
    public ResponseEntity updateZjTzRunQuarterly(ZjTzRunQuarterly zjTzRunQuarterly) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzRunQuarterly dbzjTzRunQuarterly = zjTzRunQuarterlyMapper.selectByPrimaryKey(zjTzRunQuarterly.getRunQuarterlyId());
        if (dbzjTzRunQuarterly != null && StrUtil.isNotEmpty(dbzjTzRunQuarterly.getRunQuarterlyId())) {
//           // 年度date
//           dbzjTzRunQuarterly.setYearDate(zjTzRunQuarterly.getYearDate());
//           // 年度str
//           dbzjTzRunQuarterly.setYearStr(zjTzRunQuarterly.getYearStr());
//           // 季度
//           dbzjTzRunQuarterly.setQuarter(zjTzRunQuarterly.getQuarter());
           // 运营情况说明
           dbzjTzRunQuarterly.setRunDesc(zjTzRunQuarterly.getRunDesc());
           // 登记日期
           dbzjTzRunQuarterly.setRegisterDate(zjTzRunQuarterly.getRegisterDate());
           // 登记用户
           dbzjTzRunQuarterly.setRegisterPerson(zjTzRunQuarterly.getRegisterPerson());
           // 共通
           dbzjTzRunQuarterly.setModifyUserInfo(userKey, realName);
           flag = zjTzRunQuarterlyMapper.updateByPrimaryKey(dbzjTzRunQuarterly);
           
        // 删除附件再新增
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzRunQuarterly.getRunQuarterlyId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        	   zjTzFileSelect.setModifyUserInfo(userKey, realName);
        	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           List<ZjTzFile> zjTzFileList = zjTzRunQuarterly.getZjTzFileList();
           if(zjTzFileList != null && zjTzFileList.size()>0) {
        	   for(ZjTzFile zjTzFile:zjTzFileList) {
        		   zjTzFile.setUid(UuidUtil.generate());
        		   zjTzFile.setOtherId(dbzjTzRunQuarterly.getRunQuarterlyId());
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
            return repEntity.ok("sys.data.update",zjTzRunQuarterly);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzRunQuarterly(List<ZjTzRunQuarterly> zjTzRunQuarterlyList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzRunQuarterlyList != null && zjTzRunQuarterlyList.size() > 0) {
        	for (ZjTzRunQuarterly zjTzRunQuarterly : zjTzRunQuarterlyList) {
        		ZjTzFile zjTzFileSelect = new ZjTzFile();
        		zjTzFileSelect.setOtherId(zjTzRunQuarterly.getRunQuarterlyId());
        		List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        		if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        			zjTzFileSelect.setModifyUserInfo(userKey, realName);
        			zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
        		}
        	}
        	ZjTzRunQuarterly zjTzRunQuarterly = new ZjTzRunQuarterly();
           zjTzRunQuarterly.setModifyUserInfo(userKey, realName);
           flag = zjTzRunQuarterlyMapper.batchDeleteUpdateZjTzRunQuarterly(zjTzRunQuarterlyList, zjTzRunQuarterly);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzRunQuarterlyList);
        }
    }
}
