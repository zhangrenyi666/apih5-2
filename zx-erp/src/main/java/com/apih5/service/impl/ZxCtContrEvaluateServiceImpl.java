package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtContrEvaluateMapper;
import com.apih5.mybatis.pojo.ZxCtContrEvaluate;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxCtContrEvaluateService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxCtContrEvaluateService")
public class ZxCtContrEvaluateServiceImpl implements ZxCtContrEvaluateService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtContrEvaluateMapper zxCtContrEvaluateMapper;

    @Autowired(required = true)
    private ZxErpFileServiceImpl zxErpFileServiceImpl;
    
    @SuppressWarnings("unchecked")
	@Override
    public ResponseEntity getZxCtContrEvaluateListByCondition(ZxCtContrEvaluate zxCtContrEvaluate) {
        if (zxCtContrEvaluate == null) {
            zxCtContrEvaluate = new ZxCtContrEvaluate();
        }
        // 分页查询
        PageHelper.startPage(zxCtContrEvaluate.getPage(),zxCtContrEvaluate.getLimit());
        // 获取数据
        List<ZxCtContrEvaluate> zxCtContrEvaluateList = zxCtContrEvaluateMapper.selectByZxCtContrEvaluateList(zxCtContrEvaluate);
        // 得到分页信息
        PageInfo<ZxCtContrEvaluate> p = new PageInfo<>(zxCtContrEvaluateList);
        
        for (ZxCtContrEvaluate ctContrEvaluate : zxCtContrEvaluateList) {
        	// 附件
        	ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(ctContrEvaluate.getId());
        	zxErpFile.setOtherType("1");
        	List<ZxErpFile> zxErpFileList = (List<ZxErpFile>) zxErpFileServiceImpl.getZxErpFileListByCondition(zxErpFile).getData();
        	ctContrEvaluate.setAttachment(zxErpFileList);
		}

        return repEntity.okList(zxCtContrEvaluateList, p.getTotal());
    }

    @SuppressWarnings("unchecked")
	@Override
    public ResponseEntity getZxCtContrEvaluateDetail(ZxCtContrEvaluate zxCtContrEvaluate) {
        if (zxCtContrEvaluate == null) {
            zxCtContrEvaluate = new ZxCtContrEvaluate();
        }
        // 获取数据
        ZxCtContrEvaluate dbZxCtContrEvaluate = zxCtContrEvaluateMapper.selectByPrimaryKey(zxCtContrEvaluate.getId());
        // 数据存在
        if (dbZxCtContrEvaluate != null) {
        	// 附件
        	ZxErpFile zxErpFile = new ZxErpFile();
        	zxErpFile.setOtherId(dbZxCtContrEvaluate.getId());
        	zxErpFile.setOtherType("1");
        	List<ZxErpFile> zxErpFileList = (List<ZxErpFile>) zxErpFileServiceImpl.getZxErpFileListByCondition(zxErpFile).getData();
        	dbZxCtContrEvaluate.setAttachment(zxErpFileList);
        	
            return repEntity.ok(dbZxCtContrEvaluate);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtContrEvaluate(ZxCtContrEvaluate zxCtContrEvaluate) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtContrEvaluate.setId(UuidUtil.generate());
        zxCtContrEvaluate.setCreateUserInfo(userKey, realName);
        int flag = zxCtContrEvaluateMapper.insert(zxCtContrEvaluate);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// 附件
        	List<ZxErpFile> zxErpFileList = zxCtContrEvaluate.getAttachment();
        	if (zxErpFileList != null && zxErpFileList.size() > 0) {
				for (ZxErpFile zxErpFile : zxErpFileList) {
		        	zxErpFile.setOtherId(zxCtContrEvaluate.getId());
		        	zxErpFile.setOtherType("1");
		        	zxErpFileServiceImpl.saveZxErpFile(zxErpFile);
				}
			}
        	
            return repEntity.ok("sys.data.sava", zxCtContrEvaluate);
        }
    }

    @Override
    public ResponseEntity updateZxCtContrEvaluate(ZxCtContrEvaluate zxCtContrEvaluate) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtContrEvaluate dbZxCtContrEvaluate = zxCtContrEvaluateMapper.selectByPrimaryKey(zxCtContrEvaluate.getId());
        if (dbZxCtContrEvaluate != null && StrUtil.isNotEmpty(dbZxCtContrEvaluate.getId())) {
           // 合同ID
           dbZxCtContrEvaluate.setContractID(zxCtContrEvaluate.getContractID());
           // 评价结果
           dbZxCtContrEvaluate.setEvalLevel(zxCtContrEvaluate.getEvalLevel());
           // 履行开始日期
           dbZxCtContrEvaluate.setStartDate(zxCtContrEvaluate.getStartDate());
           // 履行结束日期
           dbZxCtContrEvaluate.setEndDate(zxCtContrEvaluate.getEndDate());
           // 期次
           dbZxCtContrEvaluate.setPeriod(zxCtContrEvaluate.getPeriod());
           // 评价内容
           dbZxCtContrEvaluate.setContent(zxCtContrEvaluate.getContent());
           // 检查方
           dbZxCtContrEvaluate.setCheckunit(zxCtContrEvaluate.getCheckunit());
           // 检查方代表
           dbZxCtContrEvaluate.setChecker(zxCtContrEvaluate.getChecker());
           // 被检方
           dbZxCtContrEvaluate.setByCheckunit(zxCtContrEvaluate.getByCheckunit());
           // 被检方代表
           dbZxCtContrEvaluate.setByChecker(zxCtContrEvaluate.getByChecker());
           // 质量
           dbZxCtContrEvaluate.setQuanlityInfo(zxCtContrEvaluate.getQuanlityInfo());
           // 职业健康安全维护
           dbZxCtContrEvaluate.setSafeInfo(zxCtContrEvaluate.getSafeInfo());
           // 进度
           dbZxCtContrEvaluate.setProcessInfo(zxCtContrEvaluate.getProcessInfo());
           // 计量支付
           dbZxCtContrEvaluate.setMeasureInfo(zxCtContrEvaluate.getMeasureInfo());
           // 机械业主合同管理-业主合同台账-信用评价(原表iect_ContrEvaluate
           dbZxCtContrEvaluate.setEquipInfo(zxCtContrEvaluate.getEquipInfo());
           // 人员配备
           dbZxCtContrEvaluate.setLabourInfo(zxCtContrEvaluate.getLabourInfo());
           // 物资配备
           dbZxCtContrEvaluate.setMaterialInfo(zxCtContrEvaluate.getMaterialInfo());
           // 当地纠纷
           dbZxCtContrEvaluate.setDissensionInfo(zxCtContrEvaluate.getDissensionInfo());
           // 民工工资
           dbZxCtContrEvaluate.setWageInfo(zxCtContrEvaluate.getWageInfo());
           // 反馈信息
           dbZxCtContrEvaluate.setFeebackInfo(zxCtContrEvaluate.getFeebackInfo());
           // 评价日期
           dbZxCtContrEvaluate.setRecordDate(zxCtContrEvaluate.getRecordDate());
           // 记录人
           dbZxCtContrEvaluate.setRecordMan(zxCtContrEvaluate.getRecordMan());
           // 审核人
           dbZxCtContrEvaluate.setAuditor(zxCtContrEvaluate.getAuditor());
           // 评价结果
           dbZxCtContrEvaluate.setAuditOption(zxCtContrEvaluate.getAuditOption());
           // combProp
           dbZxCtContrEvaluate.setCombProp(zxCtContrEvaluate.getCombProp());
           // pp1
           dbZxCtContrEvaluate.setPp1(zxCtContrEvaluate.getPp1());
           // pp2
           dbZxCtContrEvaluate.setPp2(zxCtContrEvaluate.getPp2());
           // pp3
           dbZxCtContrEvaluate.setPp3(zxCtContrEvaluate.getPp3());
           // pp4
           dbZxCtContrEvaluate.setPp4(zxCtContrEvaluate.getPp4());
           // pp5
           dbZxCtContrEvaluate.setPp5(zxCtContrEvaluate.getPp5());
           // pp6
           dbZxCtContrEvaluate.setPp6(zxCtContrEvaluate.getPp6());
           // pp7
           dbZxCtContrEvaluate.setPp7(zxCtContrEvaluate.getPp7());
           // pp8
           dbZxCtContrEvaluate.setPp8(zxCtContrEvaluate.getPp8());
           // pp9
           dbZxCtContrEvaluate.setPp9(zxCtContrEvaluate.getPp9());
           // pp10
           dbZxCtContrEvaluate.setPp10(zxCtContrEvaluate.getPp10());
           // 最后编辑时间
           dbZxCtContrEvaluate.setEditTime(zxCtContrEvaluate.getEditTime());
           // 排序
           dbZxCtContrEvaluate.setSort(zxCtContrEvaluate.getSort());
           // 信用类型
           dbZxCtContrEvaluate.setType(zxCtContrEvaluate.getType());
           // 备注
           dbZxCtContrEvaluate.setRemarks(zxCtContrEvaluate.getRemarks());
           // 共通
           dbZxCtContrEvaluate.setModifyUserInfo(userKey, realName);
           flag = zxCtContrEvaluateMapper.updateByPrimaryKey(dbZxCtContrEvaluate);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	// 附件
        	ZxErpFile delErpFile = new ZxErpFile();
        	delErpFile.setOtherId(dbZxCtContrEvaluate.getId());
        	delErpFile.setOtherType("1");
        	zxErpFileServiceImpl.deleteAllZxErpFile(delErpFile);
        	
        	List<ZxErpFile> zxErpFileList = zxCtContrEvaluate.getAttachment();
        	if (zxErpFileList != null && zxErpFileList.size() > 0) {
				for (ZxErpFile zxErpFile : zxErpFileList) {
		        	zxErpFile.setOtherId(dbZxCtContrEvaluate.getId());
		        	zxErpFile.setOtherType("1");
		        	zxErpFileServiceImpl.saveZxErpFile(zxErpFile);
				}
			}
        	
            return repEntity.ok("sys.data.update",zxCtContrEvaluate);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtContrEvaluate(List<ZxCtContrEvaluate> zxCtContrEvaluateList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtContrEvaluateList != null && zxCtContrEvaluateList.size() > 0) {
           ZxCtContrEvaluate zxCtContrEvaluate = new ZxCtContrEvaluate();
           zxCtContrEvaluate.setModifyUserInfo(userKey, realName);
           flag = zxCtContrEvaluateMapper.batchDeleteUpdateZxCtContrEvaluate(zxCtContrEvaluateList, zxCtContrEvaluate);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
        	for (ZxCtContrEvaluate zxCtContrEvaluate : zxCtContrEvaluateList) {
        		// 附件
            	ZxErpFile delErpFile = new ZxErpFile();
            	delErpFile.setOtherId(zxCtContrEvaluate.getId());
            	delErpFile.setOtherType("1");
            	zxErpFileServiceImpl.deleteAllZxErpFile(delErpFile);
			}
        	
            return repEntity.ok("sys.data.delete",zxCtContrEvaluateList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
