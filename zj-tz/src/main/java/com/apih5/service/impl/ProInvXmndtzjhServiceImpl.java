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
import com.apih5.mybatis.dao.ProInvXmndtzjhMapper;
import com.apih5.mybatis.pojo.ProInvXmndtzjh;
import com.apih5.service.ProInvXmndtzjhService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("proInvXmndtzjhService")
public class ProInvXmndtzjhServiceImpl implements ProInvXmndtzjhService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ProInvXmndtzjhMapper proInvXmndtzjhMapper;

    @Override
    public ResponseEntity getProInvXmndtzjhListByCondition(ProInvXmndtzjh proInvXmndtzjh) {
        if (proInvXmndtzjh == null) {
            proInvXmndtzjh = new ProInvXmndtzjh();
        }
        // 分页查询
        PageHelper.startPage(proInvXmndtzjh.getPage(),proInvXmndtzjh.getLimit());
        // 获取数据
        List<ProInvXmndtzjh> proInvXmndtzjhList = proInvXmndtzjhMapper.selectByProInvXmndtzjhList(proInvXmndtzjh);
        // 得到分页信息
        PageInfo<ProInvXmndtzjh> p = new PageInfo<>(proInvXmndtzjhList);

        return repEntity.okList(proInvXmndtzjhList, p.getTotal());
    }

    @Override
    public ResponseEntity getProInvXmndtzjhDetails(ProInvXmndtzjh proInvXmndtzjh) {
        if (proInvXmndtzjh == null) {
            proInvXmndtzjh = new ProInvXmndtzjh();
        }
        // 获取数据
        ProInvXmndtzjh dbProInvXmndtzjh = proInvXmndtzjhMapper.selectByPrimaryKey(proInvXmndtzjh.getId());
        // 数据存在
        if (dbProInvXmndtzjh != null) {
            return repEntity.ok(dbProInvXmndtzjh);
        }
        else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }
    @Override
    public ResponseEntity saveProInvXmndtzjh(ProInvXmndtzjh proInvXmndtzjh) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        proInvXmndtzjh.setId(UuidUtil.generate());
        proInvXmndtzjh.setCreateUserInfo(userKey, realName);
        int flag = proInvXmndtzjhMapper.insert(proInvXmndtzjh);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", proInvXmndtzjh);
        }
    }

    @Override
    public ResponseEntity updateProInvXmndtzjh(ProInvXmndtzjh proInvXmndtzjh) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ProInvXmndtzjh dbproInvXmndtzjh = proInvXmndtzjhMapper.selectByPrimaryKey(proInvXmndtzjh.getId());
        if (dbproInvXmndtzjh != null && StrUtil.isNotEmpty(dbproInvXmndtzjh.getId())) {
           // 项目id
           dbproInvXmndtzjh.setXmid(proInvXmndtzjh.getXmid());
           // 项目名称
           dbproInvXmndtzjh.setXmmc(proInvXmndtzjh.getXmmc());
           // 事业部
           dbproInvXmndtzjh.setSyb(proInvXmndtzjh.getSyb());
           // 周期
           dbproInvXmndtzjh.setPeriodValue(proInvXmndtzjh.getPeriodValue());
           // 版本名称
           dbproInvXmndtzjh.setBbname(proInvXmndtzjh.getBbname());
           // 版本号 
           dbproInvXmndtzjh.setBbh(proInvXmndtzjh.getBbh());
           // 币种
           dbproInvXmndtzjh.setCurrency(proInvXmndtzjh.getCurrency());
           // 投资完成一季度
           dbproInvXmndtzjh.setTzwc1(proInvXmndtzjh.getTzwc1());
           // 投资完成二季度
           dbproInvXmndtzjh.setTzwc2(proInvXmndtzjh.getTzwc2());
           // 投资完成三季度
           dbproInvXmndtzjh.setTzwc3(proInvXmndtzjh.getTzwc3());
           // 投资完成四季度
           dbproInvXmndtzjh.setTzwc4(proInvXmndtzjh.getTzwc4());
           // 项目整体完成建安小计
           dbproInvXmndtzjh.setXmztjawcjaxj(proInvXmndtzjh.getXmztjawcjaxj());
           // 项目整体完成建安一季度
           dbproInvXmndtzjh.setXmztwcja1(proInvXmndtzjh.getXmztwcja1());
           // 项目整体完成建安一季度
           dbproInvXmndtzjh.setXmztwcja2(proInvXmndtzjh.getXmztwcja2());
           // 项目整体完成建安三季度
           dbproInvXmndtzjh.setXmztwcja3(proInvXmndtzjh.getXmztwcja3());
           // 项目整体完成建安四季度
           dbproInvXmndtzjh.setXmztwcja4(proInvXmndtzjh.getXmztwcja4());
           // 项目整体完成计量小计
           dbproInvXmndtzjh.setXmztwcjlxj(proInvXmndtzjh.getXmztwcjlxj());
           // 项目整体完成计量第一季度
           dbproInvXmndtzjh.setXmztwcjl1(proInvXmndtzjh.getXmztwcjl1());
           // 项目整体完成计量第二季度
           dbproInvXmndtzjh.setXmztwcjl2(proInvXmndtzjh.getXmztwcjl2());
           // 项目整体完成计量第三季度
           dbproInvXmndtzjh.setXmztwcjl3(proInvXmndtzjh.getXmztwcjl3());
           // 项目整体完成计量第四季度
           dbproInvXmndtzjh.setXmztwcjl4(proInvXmndtzjh.getXmztwcjl4());
           // 一公局集团完成建安小计
           dbproInvXmndtzjh.setYjgjtwcjaxj(proInvXmndtzjh.getYjgjtwcjaxj());
           // 一公局集团完成建安第一季度
           dbproInvXmndtzjh.setYjgjtwcja1(proInvXmndtzjh.getYjgjtwcja1());
           // 一公局集团完成建安第二季度
           dbproInvXmndtzjh.setYjgjtwcja2(proInvXmndtzjh.getYjgjtwcja2());
           // 一公局集团完成建安第三季度
           dbproInvXmndtzjh.setYjgjtwcja3(proInvXmndtzjh.getYjgjtwcja3());
           // 一公局集团完成建安第四季度
           dbproInvXmndtzjh.setYjgjtwcja4(proInvXmndtzjh.getYjgjtwcja4());
           // 一公局集团完成计量小计
           dbproInvXmndtzjh.setYgjjtwcjlxj(proInvXmndtzjh.getYgjjtwcjlxj());
           // 一公局完成计量第一季度
           dbproInvXmndtzjh.setYgjjtwcjl1(proInvXmndtzjh.getYgjjtwcjl1());
           // 一公局完成计量第二季度
           dbproInvXmndtzjh.setYgjjtwcjl2(proInvXmndtzjh.getYgjjtwcjl2());
           // 一公局完成计量第三季度
           dbproInvXmndtzjh.setYgjjtwcjl3(proInvXmndtzjh.getYgjjtwcjl3());
           // 一公局完成计量第四季度
           dbproInvXmndtzjh.setYgjjtwcjl4(proInvXmndtzjh.getYgjjtwcjl4());
           // 征地拆迁小计
           dbproInvXmndtzjh.setZdcqxj(proInvXmndtzjh.getZdcqxj());
           // 征地拆迁一季度
           dbproInvXmndtzjh.setZdcq1(proInvXmndtzjh.getZdcq1());
           // 征地拆迁二季度
           dbproInvXmndtzjh.setZdcq2(proInvXmndtzjh.getZdcq2());
           // 征地拆迁三季度
           dbproInvXmndtzjh.setZdcq3(proInvXmndtzjh.getZdcq3());
           // 征地拆迁四季度
           dbproInvXmndtzjh.setZdcq4(proInvXmndtzjh.getZdcq4());
           // 贡献营业收入小计
           dbproInvXmndtzjh.setGxyysrxj(proInvXmndtzjh.getGxyysrxj());
           // 贡献营业收入第一季度
           dbproInvXmndtzjh.setGxyysrxj1(proInvXmndtzjh.getGxyysrxj1());
           // 贡献营业收入第二季度
           dbproInvXmndtzjh.setGxyysrxj2(proInvXmndtzjh.getGxyysrxj2());
           // 贡献营业收入第三季度
           dbproInvXmndtzjh.setGxyysrxj3(proInvXmndtzjh.getGxyysrxj3());
           // 贡献营业收入第四季度
           dbproInvXmndtzjh.setGxyysrxj4(proInvXmndtzjh.getGxyysrxj4());
           // 项目公司贡献利润总额（财报利润）小计
           dbproInvXmndtzjh.setXmgsrlxj(proInvXmndtzjh.getXmgsrlxj());
           // 项目公司利润总额（财报利润）第一季度
           dbproInvXmndtzjh.setXmgsrl1(proInvXmndtzjh.getXmgsrl1());
           // 项目公司利润总额（财报利润）第二季度
           dbproInvXmndtzjh.setXmgsrl2(proInvXmndtzjh.getXmgsrl2());
           // 项目公司利润总额（财报利润）第三季度
           dbproInvXmndtzjh.setXmgsrl3(proInvXmndtzjh.getXmgsrl3());
           // 项目公司利润总额（财报利润）第四季度
           dbproInvXmndtzjh.setXmgsrl4(proInvXmndtzjh.getXmgsrl4());
           // 一公局集团总承包部贡献利润总额（财报利润）
           dbproInvXmndtzjh.setYgjjtzcblrxj(proInvXmndtzjh.getYgjjtzcblrxj());
           // 一公局集团总承包部利润总额（财报利润）第一季度
           dbproInvXmndtzjh.setYgjjtzcblr1(proInvXmndtzjh.getYgjjtzcblr1());
           // 一公局集团总承包部利润总额（财报利润）第二季度
           dbproInvXmndtzjh.setYgjjtzcblr2(proInvXmndtzjh.getYgjjtzcblr2());
           // 一公局集团总承包部利润总额（财报利润）第三季度
           dbproInvXmndtzjh.setYgjjtzcblr3(proInvXmndtzjh.getYgjjtzcblr3());
           // 一公局集团总承包部利润总额（财报利润）第四季度
           dbproInvXmndtzjh.setYgjjtzcblr4(proInvXmndtzjh.getYgjjtzcblr4());
           // 一公局集团各分部贡献利润总额（财报利润）小计
           dbproInvXmndtzjh.setYgjjtgfblrxj(proInvXmndtzjh.getYgjjtgfblrxj());
           // 一公局集团各分部贡献利润总额（财报利润）第一季度
           dbproInvXmndtzjh.setYgjjtgfblr1(proInvXmndtzjh.getYgjjtgfblr1());
           // 一公局集团各分部贡献利润总额（财报利润）第二季度
           dbproInvXmndtzjh.setYgjjtgfblr2(proInvXmndtzjh.getYgjjtgfblr2());
           // 一公局集团各分部贡献利润总额（财报利润）第三季度
           dbproInvXmndtzjh.setYgjjtgfblr3(proInvXmndtzjh.getYgjjtgfblr3());
           // 一公局集团各分部贡献利润总额（财报利润）第四季度
           dbproInvXmndtzjh.setYgjjtgfblr4(proInvXmndtzjh.getYgjjtgfblr4());
           // 备注
           dbproInvXmndtzjh.setBz(proInvXmndtzjh.getBz());
           // 是否总部排序
           dbproInvXmndtzjh.setSfzb(proInvXmndtzjh.getSfzb());
           // 单位类别  1=一公局   2=隧道局
           dbproInvXmndtzjh.setDwlb(proInvXmndtzjh.getDwlb());
           // 单位排序
           dbproInvXmndtzjh.setDwsort(proInvXmndtzjh.getDwsort());
           // 项目排序
           dbproInvXmndtzjh.setXmpx(proInvXmndtzjh.getXmpx());
           // 共通
           dbproInvXmndtzjh.setModifyUserInfo(userKey, realName);
           flag = proInvXmndtzjhMapper.updateByPrimaryKey(dbproInvXmndtzjh);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",proInvXmndtzjh);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateProInvXmndtzjh(List<ProInvXmndtzjh> proInvXmndtzjhList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (proInvXmndtzjhList != null && proInvXmndtzjhList.size() > 0) {
           ProInvXmndtzjh proInvXmndtzjh = new ProInvXmndtzjh();
           proInvXmndtzjh.setModifyUserInfo(userKey, realName);
           flag = proInvXmndtzjhMapper.batchDeleteUpdateProInvXmndtzjh(proInvXmndtzjhList, proInvXmndtzjh);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",proInvXmndtzjhList);
        }
    }
}
