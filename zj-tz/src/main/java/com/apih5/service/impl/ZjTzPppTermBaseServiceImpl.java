package com.apih5.service.impl;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzPppTermBaseMapper;
import com.apih5.mybatis.pojo.ZjTzPppTermBase;
import com.apih5.service.ZjTzPppTermBaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

@Service("zjTzPppTermBaseService")
public class ZjTzPppTermBaseServiceImpl implements ZjTzPppTermBaseService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzPppTermBaseMapper zjTzPppTermBaseMapper;

    @Override
    public ResponseEntity getZjTzPppTermBaseListByCondition(ZjTzPppTermBase zjTzPppTermBase) {
        if (zjTzPppTermBase == null) {
            zjTzPppTermBase = new ZjTzPppTermBase();
        }
        // 分页查询
        PageHelper.startPage(zjTzPppTermBase.getPage(),zjTzPppTermBase.getLimit());
        if(StrUtil.equals(zjTzPppTermBase.getInterfaceFlag(), "0")) {
        	
        }else {
        	if(StrUtil.isEmpty(zjTzPppTermBase.getCodePid())) {
           	 return repEntity.okList(null, 0);
           }
        }
        // 获取数据
        List<ZjTzPppTermBase> zjTzPppTermBaseList = zjTzPppTermBaseMapper.selectByZjTzPppTermBaseList(zjTzPppTermBase);
        // 得到分页信息
        PageInfo<ZjTzPppTermBase> p = new PageInfo<>(zjTzPppTermBaseList);

        return repEntity.okList(zjTzPppTermBaseList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzPppTermBaseDetails(ZjTzPppTermBase zjTzPppTermBase) {
        if (zjTzPppTermBase == null) {
            zjTzPppTermBase = new ZjTzPppTermBase();
        }
        // 获取数据
        ZjTzPppTermBase dbZjTzPppTermBase = zjTzPppTermBaseMapper.selectByPrimaryKey(zjTzPppTermBase.getPppTermBaseId());
        // 数据存在
        if (dbZjTzPppTermBase != null) {
            return repEntity.ok(dbZjTzPppTermBase);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzPppTermBase(ZjTzPppTermBase zjTzPppTermBase) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzPppTermBase.setPppTermBaseId(UuidUtil.generate());
        if(StrUtil.isNotEmpty(zjTzPppTermBase.getKeyTerm())){
        	zjTzPppTermBase.setAnalysisKey(zjTzPppTermBase.getKeyTerm());
    	}
        zjTzPppTermBase.setCreateUserInfo(userKey, realName);
        int flag = zjTzPppTermBaseMapper.insert(zjTzPppTermBase);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzPppTermBase);
        }
    }

    @Override
    public ResponseEntity updateZjTzPppTermBase(ZjTzPppTermBase zjTzPppTermBase) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzPppTermBase dbzjTzPppTermBase = zjTzPppTermBaseMapper.selectByPrimaryKey(zjTzPppTermBase.getPppTermBaseId());
        if (dbzjTzPppTermBase != null && StrUtil.isNotEmpty(dbzjTzPppTermBase.getPppTermBaseId())) {
        	if(StrUtil.equals(zjTzPppTermBase.getInterfaceFlag(), "0")) {
        		dbzjTzPppTermBase.setAnalysisKey(zjTzPppTermBase.getAnalysisKey());
        	}else {
        		// 分析重点
        		dbzjTzPppTermBase.setAnalysisKey(zjTzPppTermBase.getKeyTerm());
        		// 重点条款
        		dbzjTzPppTermBase.setKeyTerm(zjTzPppTermBase.getKeyTerm());
        		// 重点分析内容
        		dbzjTzPppTermBase.setKeyAnalysisContent(zjTzPppTermBase.getKeyAnalysisContent());
        	}
        	// 共通
        	dbzjTzPppTermBase.setModifyUserInfo(userKey, realName);
        	flag = zjTzPppTermBaseMapper.updateByPrimaryKey(dbzjTzPppTermBase);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzPppTermBase);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzPppTermBase(List<ZjTzPppTermBase> zjTzPppTermBaseList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzPppTermBaseList != null && zjTzPppTermBaseList.size() > 0) {
        	for (ZjTzPppTermBase zjTzPppTermBase : zjTzPppTermBaseList) {
    			ZjTzPppTermBase base = new ZjTzPppTermBase();
    			base.setCodePid(zjTzPppTermBase.getPppTermBaseId());
    			List<ZjTzPppTermBase> bases = zjTzPppTermBaseMapper.selectByZjTzPppTermBaseList(base);
    			if(bases != null && bases.size() >0) {
    				base.setModifyUserInfo(userKey, realName);
    				zjTzPppTermBaseMapper.batchDeleteUpdateZjTzPppTermBase(bases, base);
    			}
    		}
        	
        	ZjTzPppTermBase zjTzPppTermBase = new ZjTzPppTermBase();
           zjTzPppTermBase.setModifyUserInfo(userKey, realName);
           flag = zjTzPppTermBaseMapper.batchDeleteUpdateZjTzPppTermBase(zjTzPppTermBaseList, zjTzPppTermBase);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzPppTermBaseList);
        }
    }

	@Override
	public ResponseEntity batchImportZjTzPppTermBase(ZjTzPppTermBase zjTzPppTermBase) {
		String codePid = "";
		String evalPro = "";
//		ExcelReader reader = ExcelUtil.getReader("d:/zjtxl2.xlsx");
		String filePath = Apih5Properties.getFilePath() + zjTzPppTermBase.getFileList().get(0).getRelativeUrl();
		ExcelReader reader = ExcelUtil.getReader(filePath);
		try {
			List<Map<String,Object>> readAll = reader.readAll();
	    	for(Map<String,Object> map:readAll) {
	    		JSONObject json = new JSONObject(map);
	    		String ext1 = json.getStr("序号");
	    		String ext2 = json.getStr("分析重点");
	    		String ext3 = json.getStr("重点条款");
	    		String ext4 = json.getStr("重点分析内容");
	    		if(StrUtil.equals(evalPro, ext2)) {
	    			//add==副
		    		ZjTzPppTermBase approve1 = new ZjTzPppTermBase();
		    		approve1.setPppTermBaseId(UuidUtil.generate());
		    		approve1.setCodePid(codePid);
		    		approve1.setAnalysisKey(ext3);
		    		approve1.setKeyTerm(ext3);
		    		approve1.setKeyAnalysisContent(ext4);
		    		approve1.setOrderFlag(Integer.parseInt(ext1));
		    		approve1.setDelFlag("0");
		    		zjTzPppTermBaseMapper.insert(approve1);
	    		}else {
	    			//add==主
		    		ZjTzPppTermBase base = new ZjTzPppTermBase();
		    		base.setPppTermBaseId(UuidUtil.generate());
		    		base.setCodePid("0");
		    		base.setAnalysisKey(ext2);
		    		base.setDelFlag("0");
		    		base.setOrderFlag(Integer.parseInt(ext1));
		    		codePid = base.getPppTermBaseId();
		    		evalPro = ext2;
		    		zjTzPppTermBaseMapper.insert(base);
		    		//add==副
		    		ZjTzPppTermBase approve1 = new ZjTzPppTermBase();
		    		approve1.setPppTermBaseId(UuidUtil.generate());
		    		approve1.setCodePid(codePid);
		    		approve1.setAnalysisKey(ext3);
		    		approve1.setKeyTerm(ext3);
		    		approve1.setKeyAnalysisContent(ext4);
		    		approve1.setOrderFlag(Integer.parseInt(ext1));
		    		approve1.setDelFlag("0");
		    		zjTzPppTermBaseMapper.insert(approve1);
	    		}
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reader.close();	
		}
		return repEntity.ok("ok");
	}

}
