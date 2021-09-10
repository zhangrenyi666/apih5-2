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
import com.apih5.mybatis.dao.ZjTzContractConditionMapper;
import com.apih5.mybatis.dao.ZjTzFileMapper;
import com.apih5.mybatis.pojo.ZjTzContractCondition;
import com.apih5.mybatis.pojo.ZjTzFile;
import com.apih5.service.ZjTzContractConditionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzContractConditionService")
public class ZjTzContractConditionServiceImpl implements ZjTzContractConditionService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzContractConditionMapper zjTzContractConditionMapper;
    
    @Autowired(required = true)
	private BaseCodeService baseCodeService;
	
	@Autowired(required = true)
	private ZjTzFileMapper zjTzFileMapper;

    @Override
    public ResponseEntity getZjTzContractConditionListByCondition(ZjTzContractCondition zjTzContractCondition) {
        if (zjTzContractCondition == null) {
            zjTzContractCondition = new ZjTzContractCondition();
        }
        if(StrUtil.isEmpty(zjTzContractCondition.getSizeControlId())){
        	return repEntity.okList(null, 0);
        }
        // 分页查询
        PageHelper.startPage(zjTzContractCondition.getPage(),zjTzContractCondition.getLimit());
        // 获取数据
        List<ZjTzContractCondition> zjTzContractConditionList = zjTzContractConditionMapper.selectByZjTzContractConditionList(zjTzContractCondition);
        for (ZjTzContractCondition contractCondition : zjTzContractConditionList) {
        	ZjTzFile zjTzFileSelect = new ZjTzFile();
        	zjTzFileSelect.setOtherId(contractCondition.getContractConditionId());
        	List<ZjTzFile> zjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
        	contractCondition.setZjTzFileList(zjTzFileList);
        }
        // 得到分页信息
        PageInfo<ZjTzContractCondition> p = new PageInfo<>(zjTzContractConditionList);

        return repEntity.okList(zjTzContractConditionList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzContractConditionDetails(ZjTzContractCondition zjTzContractCondition) {
        if (zjTzContractCondition == null) {
            zjTzContractCondition = new ZjTzContractCondition();
        }
        // 获取数据
        ZjTzContractCondition dbZjTzContractCondition = zjTzContractConditionMapper.selectByPrimaryKey(zjTzContractCondition.getContractConditionId());
        // 数据存在
        if (dbZjTzContractCondition != null) {
            return repEntity.ok(dbZjTzContractCondition);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzContractCondition(ZjTzContractCondition zjTzContractCondition) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	zjTzContractCondition.setContractConditionId(UuidUtil.generate());
    	//投资收益模式
    	if (StrUtil.isNotEmpty(zjTzContractCondition.getInvestId())) {
    		String openBankName = baseCodeService.getBaseCodeItemName("touZiShouYiMoShi", zjTzContractCondition.getInvestId());
    		zjTzContractCondition.setInvestName(openBankName);
    	}
    	//一公局集团控制性地位
    	if (StrUtil.isNotEmpty(zjTzContractCondition.getJuId())) {
    		String openBankName = baseCodeService.getBaseCodeItemName("yiGongJuJiTuanKongZhiXingDiWei", zjTzContractCondition.getJuId());
    		zjTzContractCondition.setJuName(openBankName);
    	}
    	//总承包结算模式
    	if (StrUtil.isNotEmpty(zjTzContractCondition.getZcbId())) {
    		String openBankName = baseCodeService.getBaseCodeItemName("zongChengBaoJieSuanMoShi", zjTzContractCondition.getZcbId());
    		zjTzContractCondition.setZcbName(openBankName);
    	}
    	zjTzContractCondition.setCreateUserInfo(userKey, realName);
    	int flag = zjTzContractConditionMapper.insert(zjTzContractCondition);
    	//附件
        List<ZjTzFile> ZjTzFileList = zjTzContractCondition.getZjTzFileList();
        if(ZjTzFileList != null && ZjTzFileList.size()>0) {
            for(ZjTzFile zjTzFile:ZjTzFileList) {
                zjTzFile.setUid(UuidUtil.generate());
                zjTzFile.setOtherId(zjTzContractCondition.getContractConditionId());
                zjTzFile.setCreateUserInfo(userKey, realName);
                zjTzFileMapper.insert(zjTzFile);
            }
        }
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzContractCondition);
        }
    }

    @Override
    public ResponseEntity updateZjTzContractCondition(ZjTzContractCondition zjTzContractCondition) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzContractCondition dbzjTzContractCondition = zjTzContractConditionMapper.selectByPrimaryKey(zjTzContractCondition.getContractConditionId());
        if (dbzjTzContractCondition != null && StrUtil.isNotEmpty(dbzjTzContractCondition.getContractConditionId())) {
           // 投资规模控制id
           dbzjTzContractCondition.setSizeControlId(zjTzContractCondition.getSizeControlId());
           // 项目id
           dbzjTzContractCondition.setProjectId(zjTzContractCondition.getProjectId());
           // 登记日期
           dbzjTzContractCondition.setRegisterDate(zjTzContractCondition.getRegisterDate());
           // 登记人
           dbzjTzContractCondition.setRegistrant(zjTzContractCondition.getRegistrant());
           // 投资收益模式id
           dbzjTzContractCondition.setInvestId(zjTzContractCondition.getInvestId());
           // 投资收益模式name
           if (StrUtil.isNotEmpty(zjTzContractCondition.getInvestId())) {
        	   String openBankName = baseCodeService.getBaseCodeItemName("touZiShouYiMoShi", zjTzContractCondition.getInvestId());
        	   dbzjTzContractCondition.setInvestName(openBankName);
           }
           // 一公局集团股比
           dbzjTzContractCondition.setJuShare(zjTzContractCondition.getJuShare());
           // 一公局集团控制性地位id
           dbzjTzContractCondition.setJuId(zjTzContractCondition.getJuId());
           // 一公局集团控制性地位name
           if (StrUtil.isNotEmpty(zjTzContractCondition.getJuId())) {
        	   String openBankName = baseCodeService.getBaseCodeItemName("yiGongJuJiTuanKongZhiXingDiWei", zjTzContractCondition.getJuId());
        	   dbzjTzContractCondition.setJuName(openBankName);
           }
           // 总承包结算模式id
           dbzjTzContractCondition.setZcbId(zjTzContractCondition.getZcbId());
           // 总承包结算模式name
           if (StrUtil.isNotEmpty(zjTzContractCondition.getZcbId())) {
        	   String openBankName = baseCodeService.getBaseCodeItemName("zongChengBaoJieSuanMoShi", zjTzContractCondition.getZcbId());
        	   dbzjTzContractCondition.setZcbName(openBankName);
           }
           // 施工总承包比例
           dbzjTzContractCondition.setZcbShare(zjTzContractCondition.getZcbShare());
           // 设计管理情况
           dbzjTzContractCondition.setExt1(zjTzContractCondition.getExt1());
           // 合同对投资规模控制的要求
           dbzjTzContractCondition.setExt2(zjTzContractCondition.getExt2());
           // 设计变更特殊条款
           dbzjTzContractCondition.setExt3(zjTzContractCondition.getExt3());
           // 共通
           dbzjTzContractCondition.setModifyUserInfo(userKey, realName);
           flag = zjTzContractConditionMapper.updateByPrimaryKey(dbzjTzContractCondition);
           
           // 删除附件再新增
           ZjTzFile zjTzFileSelect = new ZjTzFile();
           zjTzFileSelect.setOtherId(dbzjTzContractCondition.getContractConditionId());
           List<ZjTzFile> deleteZjTzFileList = zjTzFileMapper.selectByZjTzFileList(zjTzFileSelect);
           if(deleteZjTzFileList != null && deleteZjTzFileList.size()>0) {
        	   zjTzFileSelect.setModifyUserInfo(userKey, realName);
        	   zjTzFileMapper.batchDeleteUpdateZjTzFile(deleteZjTzFileList, zjTzFileSelect);
           }
           List<ZjTzFile> ZjTzFileList = zjTzContractCondition.getZjTzFileList();
           if(ZjTzFileList != null && ZjTzFileList.size()>0) {
        	   for(ZjTzFile zjTzFile:ZjTzFileList) {
        		   zjTzFile.setUid(UuidUtil.generate());
        		   zjTzFile.setOtherId(dbzjTzContractCondition.getContractConditionId());
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
            return repEntity.ok("sys.data.update",zjTzContractCondition);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzContractCondition(List<ZjTzContractCondition> zjTzContractConditionList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzContractConditionList != null && zjTzContractConditionList.size() > 0) {
           ZjTzContractCondition zjTzContractCondition = new ZjTzContractCondition();
           zjTzContractCondition.setModifyUserInfo(userKey, realName);
           flag = zjTzContractConditionMapper.batchDeleteUpdateZjTzContractCondition(zjTzContractConditionList, zjTzContractCondition);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzContractConditionList);
        }
    }
}
