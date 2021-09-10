package com.apih5.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxGcsgCtContrProcessGuajieMapper;
import com.apih5.mybatis.pojo.ZxGcsgCtContrProcessGuajie;
import com.apih5.service.ZxGcsgCtContrProcessGuajieService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxGcsgCtContrProcessGuajieService")
public class ZxGcsgCtContrProcessGuajieServiceImpl implements ZxGcsgCtContrProcessGuajieService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxGcsgCtContrProcessGuajieMapper zxGcsgCtContrProcessGuajieMapper;

    @Override
    public ResponseEntity getZxGcsgCtContrProcessGuajieListByCondition(ZxGcsgCtContrProcessGuajie zxGcsgCtContrProcessGuajie) {
        if (zxGcsgCtContrProcessGuajie == null) {
            zxGcsgCtContrProcessGuajie = new ZxGcsgCtContrProcessGuajie();
        }
        // 分页查询
        PageHelper.startPage(zxGcsgCtContrProcessGuajie.getPage(),zxGcsgCtContrProcessGuajie.getLimit());
        // 获取数据
        List<ZxGcsgCtContrProcessGuajie> zxGcsgCtContrProcessGuajieList = zxGcsgCtContrProcessGuajieMapper.selectByZxGcsgCtContrProcessGuajieList(zxGcsgCtContrProcessGuajie);
        // 得到分页信息
        PageInfo<ZxGcsgCtContrProcessGuajie> p = new PageInfo<>(zxGcsgCtContrProcessGuajieList);

        return repEntity.okList(zxGcsgCtContrProcessGuajieList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxGcsgCtContrProcessGuajieDetail(ZxGcsgCtContrProcessGuajie zxGcsgCtContrProcessGuajie) {
        if (zxGcsgCtContrProcessGuajie == null) {
            zxGcsgCtContrProcessGuajie = new ZxGcsgCtContrProcessGuajie();
        }
        // 获取数据
        ZxGcsgCtContrProcessGuajie dbZxGcsgCtContrProcessGuajie = zxGcsgCtContrProcessGuajieMapper.selectByPrimaryKey(zxGcsgCtContrProcessGuajie.getCtContrProcessGuajieId());
        // 数据存在
        if (dbZxGcsgCtContrProcessGuajie != null) {
            return repEntity.ok(dbZxGcsgCtContrProcessGuajie);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxGcsgCtContrProcessGuajie(ZxGcsgCtContrProcessGuajie zxGcsgCtContrProcessGuajie) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxGcsgCtContrProcessGuajie.setCtContrProcessGuajieId(UuidUtil.generate());
        zxGcsgCtContrProcessGuajie.setCreateUserInfo(userKey, realName);
        int flag = zxGcsgCtContrProcessGuajieMapper.insert(zxGcsgCtContrProcessGuajie);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxGcsgCtContrProcessGuajie);
        }
    }

    @Override
    public ResponseEntity updateZxGcsgCtContrProcessGuajie(ZxGcsgCtContrProcessGuajie zxGcsgCtContrProcessGuajie) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxGcsgCtContrProcessGuajie dbZxGcsgCtContrProcessGuajie = zxGcsgCtContrProcessGuajieMapper.selectByPrimaryKey(zxGcsgCtContrProcessGuajie.getCtContrProcessGuajieId());
        if (dbZxGcsgCtContrProcessGuajie != null && StrUtil.isNotEmpty(dbZxGcsgCtContrProcessGuajie.getCtContrProcessGuajieId())) {
           // 合同清单ID(workID)
           dbZxGcsgCtContrProcessGuajie.setCcWorksId(zxGcsgCtContrProcessGuajie.getCcWorksId());
           // 合同评审清单ID、合同补充协议清单ID(applyWorkID)
           dbZxGcsgCtContrProcessGuajie.setApplyAlterWorksId(zxGcsgCtContrProcessGuajie.getApplyAlterWorksId());
           // 合同评审ID补充协议ID(contrApplyID)
           dbZxGcsgCtContrProcessGuajie.setCtContrApplyId(zxGcsgCtContrProcessGuajie.getCtContrApplyId());
           // 合同管理ID(contractID)
           dbZxGcsgCtContrProcessGuajie.setCtContractId(zxGcsgCtContrProcessGuajie.getCtContractId());
           // 工序ID
           dbZxGcsgCtContrProcessGuajie.setProcessID(zxGcsgCtContrProcessGuajie.getProcessID());
           // 挂接工序编号
           dbZxGcsgCtContrProcessGuajie.setProcessNo(zxGcsgCtContrProcessGuajie.getProcessNo());
           // 最后编辑时间
           dbZxGcsgCtContrProcessGuajie.setEditTime(zxGcsgCtContrProcessGuajie.getEditTime());
           // 工序名称
           dbZxGcsgCtContrProcessGuajie.setProcessName(zxGcsgCtContrProcessGuajie.getProcessName());
           // 计量单位
           dbZxGcsgCtContrProcessGuajie.setProcessUnit(zxGcsgCtContrProcessGuajie.getProcessUnit());
           // 工序库类型
           dbZxGcsgCtContrProcessGuajie.setBaseType(zxGcsgCtContrProcessGuajie.getBaseType());
           // 备注
           dbZxGcsgCtContrProcessGuajie.setRemarks(zxGcsgCtContrProcessGuajie.getRemarks());
           // 排序
           dbZxGcsgCtContrProcessGuajie.setSort(zxGcsgCtContrProcessGuajie.getSort());
           // 共通
           dbZxGcsgCtContrProcessGuajie.setModifyUserInfo(userKey, realName);
           flag = zxGcsgCtContrProcessGuajieMapper.updateByPrimaryKey(dbZxGcsgCtContrProcessGuajie);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxGcsgCtContrProcessGuajie);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxGcsgCtContrProcessGuajie(List<ZxGcsgCtContrProcessGuajie> zxGcsgCtContrProcessGuajieList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxGcsgCtContrProcessGuajieList != null && zxGcsgCtContrProcessGuajieList.size() > 0) {
           ZxGcsgCtContrProcessGuajie zxGcsgCtContrProcessGuajie = new ZxGcsgCtContrProcessGuajie();
           zxGcsgCtContrProcessGuajie.setModifyUserInfo(userKey, realName);
           flag = zxGcsgCtContrProcessGuajieMapper.batchDeleteUpdateZxGcsgCtContrProcessGuajie(zxGcsgCtContrProcessGuajieList, zxGcsgCtContrProcessGuajie);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxGcsgCtContrProcessGuajieList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓
}
