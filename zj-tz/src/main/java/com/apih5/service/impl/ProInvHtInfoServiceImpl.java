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
import com.apih5.mybatis.dao.ProInvHtInfoMapper;
import com.apih5.mybatis.pojo.ProInvHtInfo;
import com.apih5.service.ProInvHtInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("proInvHtInfoService")
public class ProInvHtInfoServiceImpl implements ProInvHtInfoService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ProInvHtInfoMapper proInvHtInfoMapper;

    @Override
    public ResponseEntity getProInvHtInfoListByCondition(ProInvHtInfo proInvHtInfo) {
        if (proInvHtInfo == null) {
            proInvHtInfo = new ProInvHtInfo();
        }
        // 分页查询
        PageHelper.startPage(proInvHtInfo.getPage(),proInvHtInfo.getLimit());
        // 获取数据
        List<ProInvHtInfo> proInvHtInfoList = proInvHtInfoMapper.selectByProInvHtInfoList(proInvHtInfo);
        // 得到分页信息
        PageInfo<ProInvHtInfo> p = new PageInfo<>(proInvHtInfoList);

        return repEntity.okList(proInvHtInfoList, p.getTotal());
    }

    @Override
    public ResponseEntity getProInvHtInfoDetails(ProInvHtInfo proInvHtInfo) {
        if (proInvHtInfo == null) {
            proInvHtInfo = new ProInvHtInfo();
        }
        // 获取数据
        ProInvHtInfo dbProInvHtInfo = proInvHtInfoMapper.selectByPrimaryKey(proInvHtInfo.getId());
        // 数据存在
        if (dbProInvHtInfo != null) {
            return repEntity.ok(dbProInvHtInfo);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveProInvHtInfo(ProInvHtInfo proInvHtInfo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        proInvHtInfo.setId(UuidUtil.generate());
        proInvHtInfo.setCreateUserInfo(userKey, realName);
        int flag = proInvHtInfoMapper.insert(proInvHtInfo);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", proInvHtInfo);
        }
    }

    @Override
    public ResponseEntity updateProInvHtInfo(ProInvHtInfo proInvHtInfo) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ProInvHtInfo dbproInvHtInfo = proInvHtInfoMapper.selectByPrimaryKey(proInvHtInfo.getId());
        if (dbproInvHtInfo != null && StrUtil.isNotEmpty(dbproInvHtInfo.getId())) {
           // 排序号order
           dbproInvHtInfo.setInvProId(proInvHtInfo.getInvProId());
           // 公司id
           dbproInvHtInfo.setAgreementName(proInvHtInfo.getAgreementName());
           // 公司name
           dbproInvHtInfo.setAgreementNum(proInvHtInfo.getAgreementNum());
           // 数据采集平台项目名称
           dbproInvHtInfo.setDyqyBrand(proInvHtInfo.getDyqyBrand());
           // 项目编号
           dbproInvHtInfo.setInvXyjf(proInvHtInfo.getInvXyjf());
           // 项目名称
           dbproInvHtInfo.setInvXyyf(proInvHtInfo.getInvXyyf());
           // 项目简称
           dbproInvHtInfo.setHtbf(proInvHtInfo.getHtbf());
           // 项目类别id
           dbproInvHtInfo.setHtdf(proInvHtInfo.getHtdf());
           // 项目类别name
           dbproInvHtInfo.setHtqdDate(proInvHtInfo.getHtqdDate());
           // 项目类型id
           dbproInvHtInfo.setSbjtsj(proInvHtInfo.getSbjtsj());
           // 项目类型name
           dbproInvHtInfo.setNrxtglsj(proInvHtInfo.getNrxtglsj());
           // 项目进展id
           dbproInvHtInfo.setKgDate(proInvHtInfo.getKgDate());
           // 项目进展name
           dbproInvHtInfo.setSjkgrq(proInvHtInfo.getSjkgrq());
           // 管理单位id
           dbproInvHtInfo.setJafMoney(proInvHtInfo.getJafMoney());
           // 管理单位name
           dbproInvHtInfo.setBcjafMoney(proInvHtInfo.getBcjafMoney());
           // 投资模式
           dbproInvHtInfo.setZcfMoney(proInvHtInfo.getZcfMoney());
           // 排序号
           dbproInvHtInfo.setBczcfMoney(proInvHtInfo.getBczcfMoney());
           // 建设期（年）
           dbproInvHtInfo.setGlfMoney(proInvHtInfo.getGlfMoney());
           // 运营期/回购期（年）
           dbproInvHtInfo.setBcglfMoney(proInvHtInfo.getBcglfMoney());
           // 投资协议签订时间
           dbproInvHtInfo.setJlfMoney(proInvHtInfo.getJlfMoney());
           // 特权合同签订时间
           dbproInvHtInfo.setBcjlfMoney(proInvHtInfo.getBcjlfMoney());
           // 合同约定开工时间
           dbproInvHtInfo.setOthMoney(proInvHtInfo.getOthMoney());
           // 实际开工时间
           dbproInvHtInfo.setBcothMoney(proInvHtInfo.getBcothMoney());
           // 交工验收时间
           dbproInvHtInfo.setZtze(proInvHtInfo.getZtze());
           // 计划完工日期
           dbproInvHtInfo.setHtgq(proInvHtInfo.getHtgq());
           // 合同约定运营/回购开始时间
           dbproInvHtInfo.setBchhte(proInvHtInfo.getBchhte());
           // 实际运营/回购开始时间
           dbproInvHtInfo.setBchhtgq(proInvHtInfo.getBchhtgq());
           // 合同约定运营/回购结束时间
           dbproInvHtInfo.setZbjbl(proInvHtInfo.getZbjbl());
           // 实际运营/回购结束时间
           dbproInvHtInfo.setZyzjbl(proInvHtInfo.getZyzjbl());
           // 是否已获取中交集团批复
           dbproInvHtInfo.setYhjdbl(proInvHtInfo.getYhjdbl());
           // 是否并入一公局集团财务报表
           dbproInvHtInfo.setBuildDate(proInvHtInfo.getBuildDate());
           // 所在区域id
           dbproInvHtInfo.setYyhgDate(proInvHtInfo.getYyhgDate());
           // 所在区域name
           dbproInvHtInfo.setYylc(proInvHtInfo.getYylc());
           // 中交批复时间
           dbproInvHtInfo.setCurrency(proInvHtInfo.getCurrency());
           // 中交批复投资额（元）
           dbproInvHtInfo.setCreateBy(proInvHtInfo.getCreateBy());
           // 其中：建安费 （元）
           dbproInvHtInfo.setCreateOrg(proInvHtInfo.getCreateOrg());
           // 中交集团批复文号 
           dbproInvHtInfo.setCreateDate(proInvHtInfo.getCreateDate());
           // 政府批复概算（元）
           dbproInvHtInfo.setUpdateBy(proInvHtInfo.getUpdateBy());
           // 工程概况
           dbproInvHtInfo.setUpdateOrg(proInvHtInfo.getUpdateOrg());
           // 纳入国家、省发改委PPP项目库或财政部、省财政厅PPP综合信息平台项目库情况（具体阶段）
           dbproInvHtInfo.setUpdateDate(proInvHtInfo.getUpdateDate());
           // 政府承诺给予的优惠、补贴等
           dbproInvHtInfo.setRemarks(proInvHtInfo.getRemarks());
           // 跳转参数例子以及说明：
           dbproInvHtInfo.setCcsjfMoney(proInvHtInfo.getCcsjfMoney());
           // 是否签约
           dbproInvHtInfo.setBcccsjfMoney(proInvHtInfo.getBcccsjfMoney());
           // 合同额（元）
           dbproInvHtInfo.setHtjgrq(proInvHtInfo.getHtjgrq());
           // 回购总额
           dbproInvHtInfo.setYdkshgrq(proInvHtInfo.getYdkshgrq());
           // 期中：建安费
           dbproInvHtInfo.setJjbl(proInvHtInfo.getJjbl());
           // 征拆费
           dbproInvHtInfo.setQtgdzjbl(proInvHtInfo.getQtgdzjbl());
           // 管理费
           dbproInvHtInfo.setQtzjbl(proInvHtInfo.getQtzjbl());
           // 监理费
           dbproInvHtInfo.setRzbl(proInvHtInfo.getRzbl());
           // 勘察设计费（元）
           dbproInvHtInfo.setRzJjbl(proInvHtInfo.getRzJjbl());
           // 其他
           dbproInvHtInfo.setRzYgjrgjjbl(proInvHtInfo.getRzYgjrgjjbl());
           // 资本金（元）
           dbproInvHtInfo.setRzXtdqt(proInvHtInfo.getRzXtdqt());
           // 自有资金
           dbproInvHtInfo.setZfbtbl(proInvHtInfo.getZfbtbl());
           // 基金
           dbproInvHtInfo.setZbjYgjrgjjbl(proInvHtInfo.getZbjYgjrgjjbl());
           // 共通
           dbproInvHtInfo.setModifyUserInfo(userKey, realName);
           flag = proInvHtInfoMapper.updateByPrimaryKey(dbproInvHtInfo);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",proInvHtInfo);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateProInvHtInfo(List<ProInvHtInfo> proInvHtInfoList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (proInvHtInfoList != null && proInvHtInfoList.size() > 0) {
           ProInvHtInfo proInvHtInfo = new ProInvHtInfo();
           proInvHtInfo.setModifyUserInfo(userKey, realName);
           flag = proInvHtInfoMapper.batchDeleteUpdateProInvHtInfo(proInvHtInfoList, proInvHtInfo);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",proInvHtInfoList);
        }
    }
}
