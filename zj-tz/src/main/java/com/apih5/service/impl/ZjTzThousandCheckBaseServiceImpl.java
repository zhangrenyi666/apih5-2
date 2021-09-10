package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.CalcUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzThousandCheckBaseMapper;
import com.apih5.mybatis.pojo.ZjTzThousandCheckBase;
import com.apih5.service.ZjTzThousandCheckBaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

@Service("zjTzThousandCheckBaseService")
public class ZjTzThousandCheckBaseServiceImpl implements ZjTzThousandCheckBaseService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzThousandCheckBaseMapper zjTzThousandCheckBaseMapper;
    
    @Override
    public ResponseEntity getZjTzThousandCheckBaseListByCondition(ZjTzThousandCheckBase zjTzThousandCheckBase) {
        if (zjTzThousandCheckBase == null) {
            zjTzThousandCheckBase = new ZjTzThousandCheckBase();
        }
        // 分页查询
        PageHelper.startPage(zjTzThousandCheckBase.getPage(),zjTzThousandCheckBase.getLimit());
        
        if(StrUtil.equals(zjTzThousandCheckBase.getInterfaceFlag(), "0")) {
        	
        }else {
        	if(StrUtil.isEmpty(zjTzThousandCheckBase.getCodePid())) {
           	 return repEntity.okList(null, 0);
           }
        }
        // 获取数据
        List<ZjTzThousandCheckBase> zjTzThousandCheckBaseList = zjTzThousandCheckBaseMapper.selectByZjTzThousandCheckBaseList(zjTzThousandCheckBase);
        // 得到分页信息
        PageInfo<ZjTzThousandCheckBase> p = new PageInfo<>(zjTzThousandCheckBaseList);

        return repEntity.okList(zjTzThousandCheckBaseList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzThousandCheckBaseDetails(ZjTzThousandCheckBase zjTzThousandCheckBase) {
        if (zjTzThousandCheckBase == null) {
            zjTzThousandCheckBase = new ZjTzThousandCheckBase();
        }
        // 获取数据
        ZjTzThousandCheckBase dbZjTzThousandCheckBase = zjTzThousandCheckBaseMapper.selectByPrimaryKey(zjTzThousandCheckBase.getThousandCheckBaseId());
        // 数据存在
        if (dbZjTzThousandCheckBase != null) {
            return repEntity.ok(dbZjTzThousandCheckBase);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzThousandCheckBase(ZjTzThousandCheckBase zjTzThousandCheckBase) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zjTzThousandCheckBase != null) {
    		if(StrUtil.equals(zjTzThousandCheckBase.getInterfaceFlag(), "0")) {
    		}else {
    			if(StrUtil.isEmpty(zjTzThousandCheckBase.getCodePid())) {
    				return repEntity.layerMessage("no", "请选择你要新增的数据字典类别");
    			} 
    			BigDecimal scoreSubtotal = new BigDecimal("0");
    			ZjTzThousandCheckBase base = zjTzThousandCheckBaseMapper.selectByPrimaryKey(zjTzThousandCheckBase.getCodePid());
    			if(base != null && StrUtil.isNotEmpty(base.getThousandCheckBaseId())) {
    				ZjTzThousandCheckBase base2 = new ZjTzThousandCheckBase();
    				base2.setCodePid(base.getThousandCheckBaseId());
    				List<ZjTzThousandCheckBase> basesList = zjTzThousandCheckBaseMapper.selectByZjTzThousandCheckBaseList(base2);
    				if(basesList != null && basesList.size() >0) {
    					for (ZjTzThousandCheckBase zjTzThousandCheckBase2 : basesList) {
    						scoreSubtotal = CalcUtils.calcAdd(scoreSubtotal, zjTzThousandCheckBase2.getScore());
    					}
    				}
    				scoreSubtotal = CalcUtils.calcAdd(scoreSubtotal, zjTzThousandCheckBase.getScore());
    				base.setScoreSubtotal(scoreSubtotal);
    				base.setModifyUserInfo(userKey, realName);
    				zjTzThousandCheckBaseMapper.updateByPrimaryKey(base);
    			}
    		}
    	}
    	zjTzThousandCheckBase.setThousandCheckBaseId(UuidUtil.generate());
    	zjTzThousandCheckBase.setCreateUserInfo(userKey, realName);
    	if(StrUtil.isNotEmpty(zjTzThousandCheckBase.getEvalIndex())){
    		zjTzThousandCheckBase.setEvalPro(zjTzThousandCheckBase.getEvalIndex());
    	}
    	flag = zjTzThousandCheckBaseMapper.insert(zjTzThousandCheckBase);
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.sava", zjTzThousandCheckBase);
    	}
    }

    @Override
    public ResponseEntity updateZjTzThousandCheckBase(ZjTzThousandCheckBase zjTzThousandCheckBase) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	ZjTzThousandCheckBase dbzjTzThousandCheckBase = zjTzThousandCheckBaseMapper.selectByPrimaryKey(zjTzThousandCheckBase.getThousandCheckBaseId());
    	if (dbzjTzThousandCheckBase != null && StrUtil.isNotEmpty(dbzjTzThousandCheckBase.getThousandCheckBaseId())) {
    		if(StrUtil.equals(zjTzThousandCheckBase.getInterfaceFlag(), "0")) {
    			dbzjTzThousandCheckBase.setEvalPro(zjTzThousandCheckBase.getEvalPro());
    		}else {
    			dbzjTzThousandCheckBase.setEvalPro(zjTzThousandCheckBase.getEvalIndex());
    			// 评价指标
    			dbzjTzThousandCheckBase.setEvalIndex(zjTzThousandCheckBase.getEvalIndex());
    			// 评价内容及扣分标准
    			dbzjTzThousandCheckBase.setEvalContent(zjTzThousandCheckBase.getEvalContent());
    			// 标准配分
    			dbzjTzThousandCheckBase.setScore(zjTzThousandCheckBase.getScore());
    		}
    		//排序
    		dbzjTzThousandCheckBase.setOrderFlag(zjTzThousandCheckBase.getOrderFlag());
    		// 共通
    		dbzjTzThousandCheckBase.setModifyUserInfo(userKey, realName);
    		flag = zjTzThousandCheckBaseMapper.updateByPrimaryKey(dbzjTzThousandCheckBase);

    		// 配分小计
    		if(StrUtil.equals(zjTzThousandCheckBase.getInterfaceFlag(), "0")) {
    		}else {
    			BigDecimal scoreSubtotal = new BigDecimal("0");
    			ZjTzThousandCheckBase base = zjTzThousandCheckBaseMapper.selectByPrimaryKey(dbzjTzThousandCheckBase.getCodePid());
    			if(base != null && StrUtil.isNotEmpty(base.getThousandCheckBaseId())) {
    				ZjTzThousandCheckBase base2 = new ZjTzThousandCheckBase();
    				base2.setCodePid(base.getThousandCheckBaseId());
    				List<ZjTzThousandCheckBase> basesList = zjTzThousandCheckBaseMapper.selectByZjTzThousandCheckBaseList(base2);
    				if(basesList != null && basesList.size() >0) {
    					for (ZjTzThousandCheckBase zjTzThousandCheckBase2 : basesList) {
    						scoreSubtotal = CalcUtils.calcAdd(scoreSubtotal, zjTzThousandCheckBase2.getScore());
    					}
    				}
    				base.setScoreSubtotal(scoreSubtotal);
    				base.setModifyUserInfo(userKey, realName);
    				zjTzThousandCheckBaseMapper.updateByPrimaryKey(base);
    			}
    		}
    	}
    	// 失败
    	if (flag == 0) {
    		return repEntity.errorSave();
    	}
    	else {
    		return repEntity.ok("sys.data.update",zjTzThousandCheckBase);
    	}
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzThousandCheckBase(List<ZjTzThousandCheckBase> zjTzThousandCheckBaseList) {
    	HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	String userKey = TokenUtils.getUserKey(request);
    	String realName = TokenUtils.getRealName(request);
    	int flag = 0;
    	if (zjTzThousandCheckBaseList != null && zjTzThousandCheckBaseList.size() > 0) {
    		for (ZjTzThousandCheckBase zjTzThousandCheckBase : zjTzThousandCheckBaseList) {
    			ZjTzThousandCheckBase base = new ZjTzThousandCheckBase();
    			base.setCodePid(zjTzThousandCheckBase.getThousandCheckBaseId());
    			List<ZjTzThousandCheckBase> bases = zjTzThousandCheckBaseMapper.selectByZjTzThousandCheckBaseList(base);
    			if(bases != null && bases.size() >0) {
    				base.setModifyUserInfo(userKey, realName);
    				zjTzThousandCheckBaseMapper.batchDeleteUpdateZjTzThousandCheckBase(bases, base);
    			}
    		}
    		ZjTzThousandCheckBase zjTzThousandCheckBase = new ZjTzThousandCheckBase();
    		zjTzThousandCheckBase.setModifyUserInfo(userKey, realName);
    		flag = zjTzThousandCheckBaseMapper.batchDeleteUpdateZjTzThousandCheckBase(zjTzThousandCheckBaseList, zjTzThousandCheckBase);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzThousandCheckBaseList);
        }
    }

	@Override
	public ResponseEntity batchImportZjTzThousandCheckBase(ZjTzThousandCheckBase zjTzThousandCheckBase) {
		String codePid = "";
		String evalPro = "";
//		ExcelReader reader = ExcelUtil.getReader("d:/zjtxl2.xlsx");
		String filePath = Apih5Properties.getFilePath() + zjTzThousandCheckBase.getFileList().get(0).getRelativeUrl();
		ExcelReader reader = ExcelUtil.getReader(filePath);
		try {
			List<Map<String,Object>> readAll = reader.readAll();
	    	for(Map<String,Object> map:readAll) {
	    		JSONObject json = new JSONObject(map);
	    		String ext1 = json.getStr("序号");//排序
	    		String ext2 = json.getStr("评价项目");
	    		String ext3 = json.getStr("评价指标");
	    		String ext4 = json.getStr("评价内容及扣分标准");
	    		String ext5 = json.getStr("标准分配");
	    		String ext6 = json.getStr("配分小计");
	    		if(StrUtil.equals(evalPro, ext2)) {
	    			//add==副
		    		ZjTzThousandCheckBase approve1 = new ZjTzThousandCheckBase();
		    		approve1.setThousandCheckBaseId(UuidUtil.generate());
		    		approve1.setCodePid(codePid);
		    		approve1.setEvalPro(ext3);
		    		approve1.setEvalIndex(ext3);
		    		approve1.setEvalContent(ext4);
		    		approve1.setScore(new BigDecimal(ext5));
		    		approve1.setOrderFlag(Integer.parseInt(ext1));
		    		approve1.setDelFlag("0");
		    		zjTzThousandCheckBaseMapper.insert(approve1);
	    		}else {
	    			//add==主
		    		ZjTzThousandCheckBase base = new ZjTzThousandCheckBase();
		    		base.setThousandCheckBaseId(UuidUtil.generate());
		    		base.setCodePid("0");
		    		base.setEvalPro(ext2);
		    		base.setScoreSubtotal(new BigDecimal(ext6));
		    		base.setOrderFlag(Integer.parseInt(ext1));
		    		base.setDelFlag("0");
		    		codePid = base.getThousandCheckBaseId();
		    		evalPro = ext2;
		    		zjTzThousandCheckBaseMapper.insert(base);
		    		//add==副
		    		ZjTzThousandCheckBase approve1 = new ZjTzThousandCheckBase();
		    		approve1.setThousandCheckBaseId(UuidUtil.generate());
		    		approve1.setCodePid(codePid);
		    		approve1.setEvalPro(ext3);
		    		approve1.setEvalIndex(ext3);
		    		approve1.setEvalContent(ext4);
		    		approve1.setScore(new BigDecimal(ext5));
		    		approve1.setOrderFlag(Integer.parseInt(ext1));
		    		approve1.setDelFlag("0");
		    		zjTzThousandCheckBaseMapper.insert(approve1);
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
