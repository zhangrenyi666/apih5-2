package com.apih5.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxSfSourceOfDangerMapper;
import com.apih5.mybatis.pojo.ZxSfSourceOfDanger;
import com.apih5.service.ZxSfSourceOfDangerService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSfSourceOfDangerService")
public class ZxSfSourceOfDangerServiceImpl implements ZxSfSourceOfDangerService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSfSourceOfDangerMapper zxSfSourceOfDangerMapper;

    @Override
    public ResponseEntity getZxSfSourceOfDangerListByCondition(ZxSfSourceOfDanger zxSfSourceOfDanger) {
        if (zxSfSourceOfDanger == null) {
            zxSfSourceOfDanger = new ZxSfSourceOfDanger();
        }
        // 分页查询
        PageHelper.startPage(zxSfSourceOfDanger.getPage(),zxSfSourceOfDanger.getLimit());
        // 获取数据
        List<ZxSfSourceOfDanger> zxSfSourceOfDangerList = zxSfSourceOfDangerMapper.selectByZxSfSourceOfDangerList(zxSfSourceOfDanger);
        // 得到分页信息
        PageInfo<ZxSfSourceOfDanger> p = new PageInfo<>(zxSfSourceOfDangerList);

        return repEntity.okList(zxSfSourceOfDangerList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSfSourceOfDangerDetail(ZxSfSourceOfDanger zxSfSourceOfDanger) {
        if (zxSfSourceOfDanger == null) {
            zxSfSourceOfDanger = new ZxSfSourceOfDanger();
        }
        // 获取数据
        ZxSfSourceOfDanger dbZxSfSourceOfDanger = zxSfSourceOfDangerMapper.selectByPrimaryKey(zxSfSourceOfDanger.getZxSfSourceOfDangerId());
        // 数据存在
        if (dbZxSfSourceOfDanger != null) {
            return repEntity.ok(dbZxSfSourceOfDanger);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSfSourceOfDanger(ZxSfSourceOfDanger zxSfSourceOfDanger) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSfSourceOfDanger.setZxSfSourceOfDangerId(UuidUtil.generate());
        zxSfSourceOfDanger.setCreateUserInfo(userKey, realName);
        int flag = zxSfSourceOfDangerMapper.insert(zxSfSourceOfDanger);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSfSourceOfDanger);
        }
    }

    @Override
    public ResponseEntity updateZxSfSourceOfDanger(ZxSfSourceOfDanger zxSfSourceOfDanger) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSfSourceOfDanger dbZxSfSourceOfDanger = zxSfSourceOfDangerMapper.selectByPrimaryKey(zxSfSourceOfDanger.getZxSfSourceOfDangerId());
        if (dbZxSfSourceOfDanger != null && StrUtil.isNotEmpty(dbZxSfSourceOfDanger.getZxSfSourceOfDangerId())) {
           // 父id
           dbZxSfSourceOfDanger.setPId(zxSfSourceOfDanger.getPId());
           // 类型
           dbZxSfSourceOfDanger.setType(zxSfSourceOfDanger.getType());
           // 名称
           dbZxSfSourceOfDanger.setName(zxSfSourceOfDanger.getName());
           // 作业条件危险性评价（D）
           dbZxSfSourceOfDanger.setDee(zxSfSourceOfDanger.getDee());
           // 作业条件危险性评价（C）
           dbZxSfSourceOfDanger.setCee(zxSfSourceOfDanger.getCee());
           // 作业条件危险性评价（B）
           dbZxSfSourceOfDanger.setBee(zxSfSourceOfDanger.getBee());
           // 作业条件危险性评价（L）
           dbZxSfSourceOfDanger.setLee(zxSfSourceOfDanger.getLee());
           // 风险等级
           dbZxSfSourceOfDanger.setRiskLevel(zxSfSourceOfDanger.getRiskLevel());
           // 状态
           dbZxSfSourceOfDanger.setStatus(zxSfSourceOfDanger.getStatus());
           // 备注
           dbZxSfSourceOfDanger.setRemarks(zxSfSourceOfDanger.getRemarks());
           // 排序
           dbZxSfSourceOfDanger.setSort(zxSfSourceOfDanger.getSort());
           // 共通
           dbZxSfSourceOfDanger.setModifyUserInfo(userKey, realName);
           flag = zxSfSourceOfDangerMapper.updateByPrimaryKey(dbZxSfSourceOfDanger);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSfSourceOfDanger);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSfSourceOfDanger(List<ZxSfSourceOfDanger> zxSfSourceOfDangerList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSfSourceOfDangerList != null && zxSfSourceOfDangerList.size() > 0) {
           ZxSfSourceOfDanger zxSfSourceOfDanger = new ZxSfSourceOfDanger();
           zxSfSourceOfDanger.setModifyUserInfo(userKey, realName);
           flag = zxSfSourceOfDangerMapper.batchDeleteUpdateZxSfSourceOfDanger(zxSfSourceOfDangerList, zxSfSourceOfDanger);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSfSourceOfDangerList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    /**
     * 获取树
     * @return
     */
    private ResponseEntity getTree() {
        ZxSfSourceOfDanger query = new ZxSfSourceOfDanger();
        List<ZxSfSourceOfDanger> allList = zxSfSourceOfDangerMapper.selectByZxSfSourceOfDangerList(query);
        Map<Integer, List<ZxSfSourceOfDanger>> map = allList.stream().collect(Collectors.groupingBy(ZxSfSourceOfDanger::getType));
        List<Integer> keys = map.keySet().stream().collect(Collectors.toList());
        keys.sort(Comparator.reverseOrder());
        for (Integer key : keys) {
            if (key > 1) {
                List<ZxSfSourceOfDanger> list =  map.get(key);
                List<ZxSfSourceOfDanger> parent =  map.get(key - 1);
                for (ZxSfSourceOfDanger pNode : parent) {
                    for (ZxSfSourceOfDanger node : list) {
                        List<ZxSfSourceOfDanger> sfSourceOfDangers = new ArrayList<>();
                        if (pNode.getZxSfSourceOfDangerId().equals(node.getPId())) {
                            sfSourceOfDangers.add(node);
                        }
                        pNode.setChildren(sfSourceOfDangers);
                    }
                }
            }

        }
        return repEntity.ok(map.get(keys.get(keys.size() - 1)));
    }
}
