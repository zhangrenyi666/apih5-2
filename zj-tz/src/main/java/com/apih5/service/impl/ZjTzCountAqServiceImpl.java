package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZjTzCountAqMapper;
import com.apih5.mybatis.pojo.ZjTzCountAq;
import com.apih5.service.ZjTzCountAqService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zjTzCountAqService")
public class ZjTzCountAqServiceImpl implements ZjTzCountAqService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZjTzCountAqMapper zjTzCountAqMapper;

    @Override
    public ResponseEntity getZjTzCountAqListByCondition(ZjTzCountAq zjTzCountAq) {
        if (zjTzCountAq == null) {
            zjTzCountAq = new ZjTzCountAq();
        }
        // 分页查询
        PageHelper.startPage(zjTzCountAq.getPage(),zjTzCountAq.getLimit());
        // 获取数据
        List<ZjTzCountAq> zjTzCountAqList = zjTzCountAqMapper.selectByZjTzCountAqList(zjTzCountAq);
        // 得到分页信息
        PageInfo<ZjTzCountAq> p = new PageInfo<>(zjTzCountAqList);

        return repEntity.okList(zjTzCountAqList, p.getTotal());
    }

    @Override
    public ResponseEntity getZjTzCountAqDetails(ZjTzCountAq zjTzCountAq) {
        if (zjTzCountAq == null) {
            zjTzCountAq = new ZjTzCountAq();
        }
        // 获取数据
        ZjTzCountAq dbZjTzCountAq = zjTzCountAqMapper.selectByPrimaryKey(zjTzCountAq.getCountAqId());
        // 数据存在
        if (dbZjTzCountAq != null) {
            return repEntity.ok(dbZjTzCountAq);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveZjTzCountAq(ZjTzCountAq zjTzCountAq) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zjTzCountAq.setCountAqId(UuidUtil.generate());
        zjTzCountAq.setCreateUserInfo(userKey, realName);
        int flag = zjTzCountAqMapper.insert(zjTzCountAq);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", zjTzCountAq);
        }
    }

    @Override
    public ResponseEntity updateZjTzCountAq(ZjTzCountAq zjTzCountAq) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZjTzCountAq dbzjTzCountAq = zjTzCountAqMapper.selectByPrimaryKey(zjTzCountAq.getCountAqId());
        if (dbzjTzCountAq != null && StrUtil.isNotEmpty(dbzjTzCountAq.getCountAqId())) {
           // 管理单位
           dbzjTzCountAq.setManagerUnit(zjTzCountAq.getManagerUnit());
           // 项目id
           dbzjTzCountAq.setProjectId(zjTzCountAq.getProjectId());
           // 项目name
           dbzjTzCountAq.setProjectName(zjTzCountAq.getProjectName());
           // 项目单位名称
           dbzjTzCountAq.setProUnitName(zjTzCountAq.getProUnitName());
           // 累计检查数量
           dbzjTzCountAq.setTotalNum(zjTzCountAq.getTotalNum());
           // 已解决数量
           dbzjTzCountAq.setFinishNum(zjTzCountAq.getFinishNum());
           // 未解决数量
           dbzjTzCountAq.setUnfinishNum(zjTzCountAq.getUnfinishNum());
           // 已解决百分比
           dbzjTzCountAq.setFinishRate(zjTzCountAq.getFinishRate());
           // 共通
           dbzjTzCountAq.setModifyUserInfo(userKey, realName);
           flag = zjTzCountAqMapper.updateByPrimaryKey(dbzjTzCountAq);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",zjTzCountAq);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZjTzCountAq(List<ZjTzCountAq> zjTzCountAqList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zjTzCountAqList != null && zjTzCountAqList.size() > 0) {
           ZjTzCountAq zjTzCountAq = new ZjTzCountAq();
           zjTzCountAq.setModifyUserInfo(userKey, realName);
           flag = zjTzCountAqMapper.batchDeleteUpdateZjTzCountAq(zjTzCountAqList, zjTzCountAq);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",zjTzCountAqList);
        }
    }
}
