package com.apih5.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.ZxCtWorkBookMapper;
import com.apih5.mybatis.pojo.ZxCtWorkBook;
import com.apih5.service.ZxCtWorkBookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hutool.core.util.StrUtil;

@Service("zxCtWorkBookService")
public class ZxCtWorkBookServiceImpl implements ZxCtWorkBookService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxCtWorkBookMapper zxCtWorkBookMapper;

    @Override
    public ResponseEntity getZxCtWorkBookListByCondition(ZxCtWorkBook zxCtWorkBook) {
        if (zxCtWorkBook == null) {
            zxCtWorkBook = new ZxCtWorkBook();
        }
        // 分页查询
        PageHelper.startPage(zxCtWorkBook.getPage(),zxCtWorkBook.getLimit());
        // 获取数据
        List<ZxCtWorkBook> zxCtWorkBookList = zxCtWorkBookMapper.selectByZxCtWorkBookList(zxCtWorkBook);
        // 得到分页信息
        PageInfo<ZxCtWorkBook> p = new PageInfo<>(zxCtWorkBookList);

        for (ZxCtWorkBook ctWorkBook : zxCtWorkBookList) {
			if (StrUtil.isEmpty(ctWorkBook.getStatus())) {
				ctWorkBook.setStatus("0");
			}
			if (StrUtil.isEmpty(ctWorkBook.getPp1())) {
				ctWorkBook.setPp1("0");
			}
		}
        
        return repEntity.okList(zxCtWorkBookList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxCtWorkBookDetail(ZxCtWorkBook zxCtWorkBook) {
        if (zxCtWorkBook == null) {
            zxCtWorkBook = new ZxCtWorkBook();
        }
        // 获取数据
        ZxCtWorkBook dbZxCtWorkBook = zxCtWorkBookMapper.selectByPrimaryKey(zxCtWorkBook.getId());
        // 数据存在
        if (dbZxCtWorkBook != null) {
            return repEntity.ok(dbZxCtWorkBook);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxCtWorkBook(ZxCtWorkBook zxCtWorkBook) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxCtWorkBook.setId(UuidUtil.generate());
        zxCtWorkBook.setCreateUserInfo(userKey, realName);
        int flag = zxCtWorkBookMapper.insert(zxCtWorkBook);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxCtWorkBook);
        }
    }

    @Override
    public ResponseEntity updateZxCtWorkBook(ZxCtWorkBook zxCtWorkBook) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtWorkBook dbZxCtWorkBook = zxCtWorkBookMapper.selectByPrimaryKey(zxCtWorkBook.getId());
        if (dbZxCtWorkBook != null && StrUtil.isNotEmpty(dbZxCtWorkBook.getId())) {
           // 项目ID
           dbZxCtWorkBook.setOrgID(zxCtWorkBook.getOrgID());
           // 合同ID
           dbZxCtWorkBook.setContractID(zxCtWorkBook.getContractID());
           // 项目名称
           dbZxCtWorkBook.setWorkBookName(zxCtWorkBook.getWorkBookName());
           // 编制人
           dbZxCtWorkBook.setReporter(zxCtWorkBook.getReporter());
           // 编制日期
           dbZxCtWorkBook.setReportDate(zxCtWorkBook.getReportDate());
           // 审核人
           dbZxCtWorkBook.setAuditor(zxCtWorkBook.getAuditor());
           // 审核日期
           dbZxCtWorkBook.setAuditDate(zxCtWorkBook.getAuditDate());
           // 状态
           dbZxCtWorkBook.setStatus(zxCtWorkBook.getStatus());
           // 期次
           dbZxCtWorkBook.setPeriod(zxCtWorkBook.getPeriod());
           // 类型
           dbZxCtWorkBook.setSystemType(zxCtWorkBook.getSystemType());
           // combProp
           dbZxCtWorkBook.setCombProp(zxCtWorkBook.getCombProp());
           // pp1
           dbZxCtWorkBook.setPp1(zxCtWorkBook.getPp1());
           // pp2
           dbZxCtWorkBook.setPp2(zxCtWorkBook.getPp2());
           // pp3
           dbZxCtWorkBook.setPp3(zxCtWorkBook.getPp3());
           // pp4
           dbZxCtWorkBook.setPp4(zxCtWorkBook.getPp4());
           // pp5
           dbZxCtWorkBook.setPp5(zxCtWorkBook.getPp5());
           // pp6
           dbZxCtWorkBook.setPp6(zxCtWorkBook.getPp6());
           // pp7
           dbZxCtWorkBook.setPp7(zxCtWorkBook.getPp7());
           // pp8
           dbZxCtWorkBook.setPp8(zxCtWorkBook.getPp8());
           // pp9
           dbZxCtWorkBook.setPp9(zxCtWorkBook.getPp9());
           // pp10
           dbZxCtWorkBook.setPp10(zxCtWorkBook.getPp10());
           // editTime
           dbZxCtWorkBook.setEditTime(zxCtWorkBook.getEditTime());
           // srcOrgID
           dbZxCtWorkBook.setSrcOrgID(zxCtWorkBook.getSrcOrgID());
           // 备注
           dbZxCtWorkBook.setRemarks(zxCtWorkBook.getRemarks());
           // 排序
           dbZxCtWorkBook.setSort(zxCtWorkBook.getSort());
           // 共通
           dbZxCtWorkBook.setModifyUserInfo(userKey, realName);
           flag = zxCtWorkBookMapper.updateByPrimaryKey(dbZxCtWorkBook);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxCtWorkBook);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxCtWorkBook(List<ZxCtWorkBook> zxCtWorkBookList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtWorkBookList != null && zxCtWorkBookList.size() > 0) {
           ZxCtWorkBook zxCtWorkBook = new ZxCtWorkBook();
           zxCtWorkBook.setModifyUserInfo(userKey, realName);
           flag = zxCtWorkBookMapper.batchDeleteUpdateZxCtWorkBook(zxCtWorkBookList, zxCtWorkBook);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxCtWorkBookList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public ResponseEntity deleteAllZxCtWorkBook(ZxCtWorkBook zxCtWorkBook) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxCtWorkBook != null && StrUtil.isNotEmpty(zxCtWorkBook.getOrgID())) {
           zxCtWorkBook.setModifyUserInfo(userKey, realName);
           flag = zxCtWorkBookMapper.deleteAllZxCtWorkBook(zxCtWorkBook);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("删除成功！");
        }
    }

	@Override
	public ResponseEntity zxCtContractQuantity(ZxCtWorkBook zxCtWorkBook) {
		if (zxCtWorkBook == null || StrUtil.isEmpty(zxCtWorkBook.getId())) {  
			return repEntity.layerMessage("no", "主键ID不能为空！");
		}
		
		if (StrUtil.isEmpty(zxCtWorkBook.getStatus())) {
			return repEntity.layerMessage("no", "审核状态不能为空！");
		}
		
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxCtWorkBook dbZxCtWorkBook = zxCtWorkBookMapper.selectByPrimaryKey(zxCtWorkBook.getId());
        if (dbZxCtWorkBook != null && StrUtil.isNotEmpty(dbZxCtWorkBook.getId())) {
           // 状态
           dbZxCtWorkBook.setStatus(zxCtWorkBook.getStatus());
           // 共通
           dbZxCtWorkBook.setModifyUserInfo(userKey, realName);
           flag = zxCtWorkBookMapper.updateContractQuantityByPrimaryKey(dbZxCtWorkBook);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorUpdate();
        } else {
            return repEntity.ok("sys.data.update",zxCtWorkBook);
        }
	}
	
	@Override
	public ResponseEntity zxCtVerificationQuantity(ZxCtWorkBook zxCtWorkBook) {
		if (zxCtWorkBook == null || StrUtil.isEmpty(zxCtWorkBook.getId())) {  
			return repEntity.layerMessage("no", "主键ID不能为空！");
		}

		if (StrUtil.isEmpty(zxCtWorkBook.getPp1())) {
			return repEntity.layerMessage("no", "审核状态不能为空！");
		}
		
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		int flag = 0;
		ZxCtWorkBook dbZxCtWorkBook = zxCtWorkBookMapper.selectByPrimaryKey(zxCtWorkBook.getId());
		if (dbZxCtWorkBook != null && StrUtil.isNotEmpty(dbZxCtWorkBook.getId())) {
			// 状态
			dbZxCtWorkBook.setPp1(zxCtWorkBook.getPp1());
			// 共通
			dbZxCtWorkBook.setModifyUserInfo(userKey, realName);
			flag = zxCtWorkBookMapper.updateVerificationQuantityByPrimaryKey(dbZxCtWorkBook);
		}
		// 失败
		if (flag == 0) {
			return repEntity.errorUpdate();
		} else {
			return repEntity.ok("sys.data.update",zxCtWorkBook);
		}
	}
}
