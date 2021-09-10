package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzSupContentMapper;
import com.apih5.mybatis.pojo.ZjTzSupContent;
import com.apih5.service.ZjTzSupContentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzSupContentService")
public class ZjTzSupContentServiceImpl implements ZjTzSupContentService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzSupContentMapper zjTzSupContentMapper;

    @Override
    public ResponseEntity getZjTzSupContentListByCondition(ZjTzSupContent zjTzSupContent) {
        if (zjTzSupContent == null) {
            zjTzSupContent = new ZjTzSupContent();
        }
        // 分页查询
        PageHelper.startPage(zjTzSupContent.getPage(),zjTzSupContent.getLimit());
        // 获取数据
        List<ZjTzSupContent> zjTzSupContentList = zjTzSupContentMapper.selectByZjTzSupContentList(zjTzSupContent);
        // 得到分页信息
        PageInfo<ZjTzSupContent> p = new PageInfo<>(zjTzSupContentList);

        return repEntity.okList(zjTzSupContentList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzSupContentDetails(ZjTzSupContent zjTzSupContent) {
        if (zjTzSupContent == null) {
            zjTzSupContent = new ZjTzSupContent();
        }
        // 获取数据
        ZjTzSupContent dbZjTzSupContent = zjTzSupContentMapper.selectByPrimaryKey(zjTzSupContent.getSupContentId());
        // 数据存在
        if (dbZjTzSupContent != null) {
            return repEntity.ok(dbZjTzSupContent);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzSupContent(ZjTzSupContent zjTzSupContent) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzSupContent.setSupContentId(UuidUtil.generate());
        zjTzSupContent.setCreateUserInfo(userKey, realName);
        int flag = zjTzSupContentMapper.insert(zjTzSupContent);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzSupContent);
        }
    }

    @Override
    public ResponseEntity updateZjTzSupContent(ZjTzSupContent zjTzSupContent) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzSupContent dbzjTzSupContent = zjTzSupContentMapper.selectByPrimaryKey(zjTzSupContent.getSupContentId());
        if (dbzjTzSupContent != null && StrUtil.isNotEmpty(dbzjTzSupContent.getSupContentId())) {
//           // 项目综合督、查综合督导
//           dbzjTzSupContent.setComprehensiveSupId(zjTzSupContent.getComprehensiveSupId());
//           // 类型id
//           dbzjTzSupContent.setTypeId(zjTzSupContent.getTypeId());
//           // 类型name
//           dbzjTzSupContent.setTypeName(zjTzSupContent.getTypeName());
//           // 细目
//           dbzjTzSupContent.setDetail(zjTzSupContent.getDetail());
//           // 是否需要整改id
//           dbzjTzSupContent.setNeedCorrectiveId(zjTzSupContent.getNeedCorrectiveId());
//           // 是否需要整改name
//           dbzjTzSupContent.setNeedCorrectiveName(zjTzSupContent.getNeedCorrectiveName());
           // 整改落实情况
           dbzjTzSupContent.setCorrectiveCase(zjTzSupContent.getCorrectiveCase());
           // 整改完成时间
           dbzjTzSupContent.setCorrectiveDate(zjTzSupContent.getCorrectiveDate());
           // 共通
           dbzjTzSupContent.setModifyUserInfo(userKey, realName);
           flag = zjTzSupContentMapper.updateByPrimaryKey(dbzjTzSupContent);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzSupContent);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzSupContent(List<ZjTzSupContent> zjTzSupContentList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzSupContentList != null && zjTzSupContentList.size() > 0) {
           ZjTzSupContent zjTzSupContent = new ZjTzSupContent();
           zjTzSupContent.setModifyUserInfo(userKey, realName);
           flag = zjTzSupContentMapper.batchDeleteUpdateZjTzSupContent(zjTzSupContentList, zjTzSupContent);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzSupContentList);
        }
    }

}
