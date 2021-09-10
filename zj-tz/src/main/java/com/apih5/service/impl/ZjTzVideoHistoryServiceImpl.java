package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzVideoHistoryMapper;
import com.apih5.mybatis.dao.ZjTzVideoMapper;
import com.apih5.mybatis.pojo.ZjTzVideo;
import com.apih5.mybatis.pojo.ZjTzVideoHistory;
import com.apih5.service.ZjTzVideoHistoryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzVideoHistoryService")
public class ZjTzVideoHistoryServiceImpl implements ZjTzVideoHistoryService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzVideoHistoryMapper zjTzVideoHistoryMapper;
    
    @Autowired(required = true)
    private ZjTzVideoMapper zjTzVideoMapper;

    @Override
    public ResponseEntity getZjTzVideoHistoryListByCondition(ZjTzVideoHistory zjTzVideoHistory) {
    	if (zjTzVideoHistory == null) {
            zjTzVideoHistory = new ZjTzVideoHistory();
        }
        // 分页查询
        PageHelper.startPage(zjTzVideoHistory.getPage(),zjTzVideoHistory.getLimit());
        // 获取数据
        List<ZjTzVideoHistory> zjTzVideoHistoryList = zjTzVideoHistoryMapper.selectByZjTzVideoHistoryList(zjTzVideoHistory);
        if (zjTzVideoHistoryList != null && zjTzVideoHistoryList.size() > 0) {
			for (ZjTzVideoHistory zjTzVideoHistory2 : zjTzVideoHistoryList) {
				if (StrUtil.isNotEmpty(zjTzVideoHistory2.getDepartmentId())) {
					ZjTzVideoHistory dbzjTzVideoHistory = zjTzVideoHistoryMapper.selectByDepartmentId(zjTzVideoHistory2.getDepartmentId());
					if (dbzjTzVideoHistory != null) {
						zjTzVideoHistory2.setDepartmentName(dbzjTzVideoHistory.getDepartmentName());
					}
				}else {
					zjTzVideoHistory2.setDepartmentName("");
				}
			}
		}
        // 得到分页信息
        PageInfo<ZjTzVideoHistory> p = new PageInfo<>(zjTzVideoHistoryList);

        return repEntity.okList(zjTzVideoHistoryList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzVideoHistoryDetails(ZjTzVideoHistory zjTzVideoHistory) {
        if (zjTzVideoHistory == null) {
            zjTzVideoHistory = new ZjTzVideoHistory();
        }
        // 获取数据
        ZjTzVideoHistory dbZjTzVideoHistory = zjTzVideoHistoryMapper.selectByPrimaryKey(zjTzVideoHistory.getHistoryId());
        // 数据存在
        if (dbZjTzVideoHistory != null) {
            return repEntity.ok(dbZjTzVideoHistory);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzVideoHistory(ZjTzVideoHistory zjTzVideoHistory) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzVideoHistory.setHistoryId(UuidUtil.generate());
        zjTzVideoHistory.setCreateUserInfo(userKey, realName);
        int flag = zjTzVideoHistoryMapper.insert(zjTzVideoHistory);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzVideoHistory);
        }
    }

    @Override
    public ResponseEntity updateZjTzVideoHistory(ZjTzVideoHistory zjTzVideoHistory) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzVideoHistory dbzjTzVideoHistory = zjTzVideoHistoryMapper.selectByPrimaryKey(zjTzVideoHistory.getHistoryId());
        if (dbzjTzVideoHistory != null && StrUtil.isNotEmpty(dbzjTzVideoHistory.getHistoryId())) {
           // 视频id
           dbzjTzVideoHistory.setVideoId(zjTzVideoHistory.getVideoId());
           // 建议
           dbzjTzVideoHistory.setSuggest(zjTzVideoHistory.getSuggest());
           // 所属项目id
           dbzjTzVideoHistory.setProjectId(zjTzVideoHistory.getProjectId());
           // 所属项目name
           dbzjTzVideoHistory.setProjectName(zjTzVideoHistory.getProjectName());
           // 共通
           dbzjTzVideoHistory.setModifyUserInfo(userKey, realName);
           flag = zjTzVideoHistoryMapper.updateByPrimaryKey(dbzjTzVideoHistory);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzVideoHistory);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzVideoHistory(List<ZjTzVideoHistory> zjTzVideoHistoryList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzVideoHistoryList != null && zjTzVideoHistoryList.size() > 0) {
           ZjTzVideoHistory zjTzVideoHistory = new ZjTzVideoHistory();
           zjTzVideoHistory.setModifyUserInfo(userKey, realName);
           flag = zjTzVideoHistoryMapper.batchDeleteUpdateZjTzVideoHistory(zjTzVideoHistoryList, zjTzVideoHistory);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzVideoHistoryList);
        }
    }
}
