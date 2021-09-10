package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzCompSupContentMapper;
import com.apih5.mybatis.pojo.ZjTzCompSupContent;
import com.apih5.service.ZjTzCompSupContentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzCompSupContentService")
public class ZjTzCompSupContentServiceImpl implements ZjTzCompSupContentService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzCompSupContentMapper zjTzCompSupContentMapper;

    @Override
    public ResponseEntity getZjTzCompSupContentListByCondition(ZjTzCompSupContent zjTzCompSupContent) {
        if (zjTzCompSupContent == null) {
            zjTzCompSupContent = new ZjTzCompSupContent();
        }
        // 分页查询
        PageHelper.startPage(zjTzCompSupContent.getPage(),zjTzCompSupContent.getLimit());
        // 获取数据
        List<ZjTzCompSupContent> zjTzCompSupContentList = zjTzCompSupContentMapper.selectByZjTzCompSupContentList(zjTzCompSupContent);
        // 得到分页信息
        PageInfo<ZjTzCompSupContent> p = new PageInfo<>(zjTzCompSupContentList);

        return repEntity.okList(zjTzCompSupContentList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzCompSupContentDetails(ZjTzCompSupContent zjTzCompSupContent) {
        if (zjTzCompSupContent == null) {
            zjTzCompSupContent = new ZjTzCompSupContent();
        }
        // 获取数据
        ZjTzCompSupContent dbZjTzCompSupContent = zjTzCompSupContentMapper.selectByPrimaryKey(zjTzCompSupContent.getSupContentId());
        // 数据存在
        if (dbZjTzCompSupContent != null) {
            return repEntity.ok(dbZjTzCompSupContent);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzCompSupContent(ZjTzCompSupContent zjTzCompSupContent) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzCompSupContent.setSupContentId(UuidUtil.generate());
        zjTzCompSupContent.setCreateUserInfo(userKey, realName);
        int flag = zjTzCompSupContentMapper.insert(zjTzCompSupContent);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzCompSupContent);
        }
    }

    @Override
    public ResponseEntity updateZjTzCompSupContent(ZjTzCompSupContent zjTzCompSupContent) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzCompSupContent dbzjTzCompSupContent = zjTzCompSupContentMapper.selectByPrimaryKey(zjTzCompSupContent.getSupContentId());
        if (dbzjTzCompSupContent != null && StrUtil.isNotEmpty(dbzjTzCompSupContent.getSupContentId())) {
//           // 综合督查报告
//           dbzjTzCompSupContent.setCompSupReportId(zjTzCompSupContent.getCompSupReportId());
//           // 类型id
//           dbzjTzCompSupContent.setTypeId(zjTzCompSupContent.getTypeId());
//           // 类型name
//           dbzjTzCompSupContent.setTypeName(zjTzCompSupContent.getTypeName());
//           // 细目
//           dbzjTzCompSupContent.setDetail(zjTzCompSupContent.getDetail());
//           // 是否需要整改id
//           dbzjTzCompSupContent.setNeedCorrectiveId(zjTzCompSupContent.getNeedCorrectiveId());
//           // 是否需要整改name
//           dbzjTzCompSupContent.setNeedCorrectiveName(zjTzCompSupContent.getNeedCorrectiveName());
           // 整改落实情况
           dbzjTzCompSupContent.setCorrectiveCase(zjTzCompSupContent.getCorrectiveCase());
           // 整改完成时间
           dbzjTzCompSupContent.setCorrectiveDate(zjTzCompSupContent.getCorrectiveDate());
           // 共通
           dbzjTzCompSupContent.setModifyUserInfo(userKey, realName);
           flag = zjTzCompSupContentMapper.updateByPrimaryKey(dbzjTzCompSupContent);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzCompSupContent);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzCompSupContent(List<ZjTzCompSupContent> zjTzCompSupContentList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzCompSupContentList != null && zjTzCompSupContentList.size() > 0) {
           ZjTzCompSupContent zjTzCompSupContent = new ZjTzCompSupContent();
           zjTzCompSupContent.setModifyUserInfo(userKey, realName);
           flag = zjTzCompSupContentMapper.batchDeleteUpdateZjTzCompSupContent(zjTzCompSupContentList, zjTzCompSupContent);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzCompSupContentList);
        }
    }
}
