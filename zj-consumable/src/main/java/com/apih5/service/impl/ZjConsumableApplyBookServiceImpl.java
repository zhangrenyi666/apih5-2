package com.apih5.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjConsumableApplyBookMapper;
import com.apih5.mybatis.pojo.ZjConsumableApplyBook;
import com.apih5.service.ZjConsumableApplyBookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjConsumableApplyBookService")
public class ZjConsumableApplyBookServiceImpl implements ZjConsumableApplyBookService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjConsumableApplyBookMapper zjConsumableApplyBookMapper;

    @Override
    public ResponseEntity getZjConsumableApplyBookListByCondition(ZjConsumableApplyBook zjConsumableApplyBook) {
        if (zjConsumableApplyBook == null) {
            zjConsumableApplyBook = new ZjConsumableApplyBook();
        }
        
        if(zjConsumableApplyBook.getAppDateSearch() != null && zjConsumableApplyBook.getAppDateSearch().size() >1) {
        	zjConsumableApplyBook.setAppStartTime(zjConsumableApplyBook.getAppDateSearch().get(0));
        	zjConsumableApplyBook.setAppEndTime(zjConsumableApplyBook.getAppDateSearch().get(1));
        }
        if(zjConsumableApplyBook.getApplyDateSearch() != null && zjConsumableApplyBook.getApplyDateSearch().size() >1) {
        	zjConsumableApplyBook.setApplyStartTime(zjConsumableApplyBook.getApplyDateSearch().get(0));
        	zjConsumableApplyBook.setApplyEndTime(zjConsumableApplyBook.getApplyDateSearch().get(1));
        }
        if(zjConsumableApplyBook.getUseDateSearch() != null && zjConsumableApplyBook.getUseDateSearch().size() >1) {
        	zjConsumableApplyBook.setUseStartTime(zjConsumableApplyBook.getUseDateSearch().get(0));
        	zjConsumableApplyBook.setUseEndTime(zjConsumableApplyBook.getUseDateSearch().get(1));
        }
        
        // 分页查询
        PageHelper.startPage(zjConsumableApplyBook.getPage(),zjConsumableApplyBook.getLimit());
        // 获取数据
        List<ZjConsumableApplyBook> zjConsumableApplyBookList = zjConsumableApplyBookMapper.selectByZjConsumableApplyBookList(zjConsumableApplyBook);
        
        // 得到分页信息
        PageInfo<ZjConsumableApplyBook> p = new PageInfo<>(zjConsumableApplyBookList);

        return repEntity.okList(zjConsumableApplyBookList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjConsumableApplyBookDetails(ZjConsumableApplyBook zjConsumableApplyBook) {
        if (zjConsumableApplyBook == null) {
            zjConsumableApplyBook = new ZjConsumableApplyBook();
        }
        // 获取数据
        ZjConsumableApplyBook dbZjConsumableApplyBook = zjConsumableApplyBookMapper.selectByPrimaryKey(zjConsumableApplyBook.getApplyBookId());
        // 数据存在
        if (dbZjConsumableApplyBook != null) {
            return repEntity.ok(dbZjConsumableApplyBook);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjConsumableApplyBook(ZjConsumableApplyBook zjConsumableApplyBook) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjConsumableApplyBook.setApplyBookId(UuidUtil.generate());
        zjConsumableApplyBook.setCreateUserInfo(userKey, realName);
        int flag = zjConsumableApplyBookMapper.insert(zjConsumableApplyBook);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjConsumableApplyBook);
        }
    }

    @Override
    public ResponseEntity updateZjConsumableApplyBook(ZjConsumableApplyBook zjConsumableApplyBook) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjConsumableApplyBook dbzjConsumableApplyBook = zjConsumableApplyBookMapper.selectByPrimaryKey(zjConsumableApplyBook.getApplyBookId());
        if (dbzjConsumableApplyBook != null && StrUtil.isNotEmpty(dbzjConsumableApplyBook.getApplyBookId())) {
           // 设置id
           dbzjConsumableApplyBook.setSetId(zjConsumableApplyBook.getSetId());
           // 申请领用id
           dbzjConsumableApplyBook.setApplyId(zjConsumableApplyBook.getApplyId());
           // 申请部门
           dbzjConsumableApplyBook.setDeptName(zjConsumableApplyBook.getDeptName());
           // 申请人
           dbzjConsumableApplyBook.setName (zjConsumableApplyBook.getName ());
           // 申请时间
           dbzjConsumableApplyBook.setAppDate(zjConsumableApplyBook.getAppDate());
           // 类别
           dbzjConsumableApplyBook.setCategory(zjConsumableApplyBook.getCategory());
           // 品牌
           dbzjConsumableApplyBook.setBrand(zjConsumableApplyBook.getBrand());
           // 型号
           dbzjConsumableApplyBook.setModel(zjConsumableApplyBook.getModel());
           // 颜色
           dbzjConsumableApplyBook.setColour(zjConsumableApplyBook.getColour());
           // 申领数量
           dbzjConsumableApplyBook.setApplyNum(zjConsumableApplyBook.getApplyNum());
           // 审批状态
           dbzjConsumableApplyBook.setStatus(zjConsumableApplyBook.getStatus());
           // 审批时间
           dbzjConsumableApplyBook.setApplyDate(zjConsumableApplyBook.getApplyDate());
           // 领用状态
           dbzjConsumableApplyBook.setUseStatus(zjConsumableApplyBook.getUseStatus());
           // 领用时间
           dbzjConsumableApplyBook.setUseDate(zjConsumableApplyBook.getUseDate());
           // 共通
           dbzjConsumableApplyBook.setModifyUserInfo(userKey, realName);
           flag = zjConsumableApplyBookMapper.updateByPrimaryKey(dbzjConsumableApplyBook);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjConsumableApplyBook);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjConsumableApplyBook(List<ZjConsumableApplyBook> zjConsumableApplyBookList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjConsumableApplyBookList != null && zjConsumableApplyBookList.size() > 0) {
           ZjConsumableApplyBook zjConsumableApplyBook = new ZjConsumableApplyBook();
           zjConsumableApplyBook.setModifyUserInfo(userKey, realName);
           flag = zjConsumableApplyBookMapper.batchDeleteUpdateZjConsumableApplyBook(zjConsumableApplyBookList, zjConsumableApplyBook);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjConsumableApplyBookList);
        }
    }

	@Override
	public List<ZjConsumableApplyBook> ureportZjConsumableApplyBookList(ZjConsumableApplyBook zjConsumableApplyBook) {
		 if (zjConsumableApplyBook == null) {
	            zjConsumableApplyBook = new ZjConsumableApplyBook();
	        }
	        
	        if(zjConsumableApplyBook.getAppDateSearch() != null && zjConsumableApplyBook.getAppDateSearch().size() >1) {
	        	zjConsumableApplyBook.setAppStartTime(zjConsumableApplyBook.getAppDateSearch().get(0));
	        	zjConsumableApplyBook.setAppEndTime(zjConsumableApplyBook.getAppDateSearch().get(1));
	        }
	        if(zjConsumableApplyBook.getApplyDateSearch() != null && zjConsumableApplyBook.getApplyDateSearch().size() >1) {
	        	zjConsumableApplyBook.setApplyStartTime(zjConsumableApplyBook.getApplyDateSearch().get(0));
	        	zjConsumableApplyBook.setApplyEndTime(zjConsumableApplyBook.getApplyDateSearch().get(1));
	        }
	        if(zjConsumableApplyBook.getUseDateSearch() != null && zjConsumableApplyBook.getUseDateSearch().size() >1) {
	        	zjConsumableApplyBook.setUseStartTime(zjConsumableApplyBook.getUseDateSearch().get(0));
	        	zjConsumableApplyBook.setUseEndTime(zjConsumableApplyBook.getUseDateSearch().get(1));
	        }
	        // 获取数据
	        List<ZjConsumableApplyBook> zjConsumableApplyBookList = zjConsumableApplyBookMapper.selectByZjConsumableApplyBookList(zjConsumableApplyBook);
	        return zjConsumableApplyBookList;
	}
	
}
