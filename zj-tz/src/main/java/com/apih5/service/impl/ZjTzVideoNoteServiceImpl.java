package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzVideoNoteMapper;
import com.apih5.mybatis.pojo.ZjTzVideoNote;
import com.apih5.service.ZjTzVideoNoteService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzVideoNoteService")
public class ZjTzVideoNoteServiceImpl implements ZjTzVideoNoteService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzVideoNoteMapper zjTzVideoNoteMapper;

    @Override
    public ResponseEntity getZjTzVideoNoteListByCondition(ZjTzVideoNote zjTzVideoNote) {
        if (zjTzVideoNote == null) {
            zjTzVideoNote = new ZjTzVideoNote();
        }
        // 分页查询
        PageHelper.startPage(zjTzVideoNote.getPage(),zjTzVideoNote.getLimit());
        if(StrUtil.isEmpty(zjTzVideoNote.getVideoId())) {
        	 return repEntity.okList(null, 0);
        }
        // 获取数据
        List<ZjTzVideoNote> zjTzVideoNoteList = zjTzVideoNoteMapper.selectByZjTzVideoNoteList(zjTzVideoNote);
        // 得到分页信息
        PageInfo<ZjTzVideoNote> p = new PageInfo<>(zjTzVideoNoteList);

        return repEntity.okList(zjTzVideoNoteList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzVideoNoteDetails(ZjTzVideoNote zjTzVideoNote) {
        if (zjTzVideoNote == null) {
            zjTzVideoNote = new ZjTzVideoNote();
        }
        // 获取数据
        ZjTzVideoNote dbZjTzVideoNote = zjTzVideoNoteMapper.selectByPrimaryKey(zjTzVideoNote.getNoteId());
        // 数据存在
        if (dbZjTzVideoNote != null) {
            return repEntity.ok(dbZjTzVideoNote);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzVideoNote(ZjTzVideoNote zjTzVideoNote) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzVideoNote.setNoteId(UuidUtil.generate());
        zjTzVideoNote.setCreateUserInfo(userKey, realName);
        int flag = zjTzVideoNoteMapper.insert(zjTzVideoNote);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzVideoNote);
        }
    }

    @Override
    public ResponseEntity updateZjTzVideoNote(ZjTzVideoNote zjTzVideoNote) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzVideoNote dbzjTzVideoNote = zjTzVideoNoteMapper.selectByPrimaryKey(zjTzVideoNote.getNoteId());
        if (dbzjTzVideoNote != null && StrUtil.isNotEmpty(dbzjTzVideoNote.getNoteId())) {
           // 视频id
           dbzjTzVideoNote.setVideoId(zjTzVideoNote.getVideoId());
           // 留言
           dbzjTzVideoNote.setNote(zjTzVideoNote.getNote());
           // 所属项目id
           dbzjTzVideoNote.setProjectId(zjTzVideoNote.getProjectId());
           // 所属项目name
           dbzjTzVideoNote.setProjectName(zjTzVideoNote.getProjectName());
           // 共通
           dbzjTzVideoNote.setModifyUserInfo(userKey, realName);
           flag = zjTzVideoNoteMapper.updateByPrimaryKey(dbzjTzVideoNote);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzVideoNote);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzVideoNote(List<ZjTzVideoNote> zjTzVideoNoteList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzVideoNoteList != null && zjTzVideoNoteList.size() > 0) {
           ZjTzVideoNote zjTzVideoNote = new ZjTzVideoNote();
           zjTzVideoNote.setModifyUserInfo(userKey, realName);
           flag = zjTzVideoNoteMapper.batchDeleteUpdateZjTzVideoNote(zjTzVideoNoteList, zjTzVideoNote);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzVideoNoteList);
        }
    }
}
