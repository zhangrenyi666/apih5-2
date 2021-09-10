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
import com.apih5.mybatis.dao.ZxSkMosResMovStatRepMapper;
import com.apih5.mybatis.pojo.ZxSkMosResMovStatRep;
import com.apih5.service.ZxSkMosResMovStatRepService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxSkMosResMovStatRepService")
public class ZxSkMosResMovStatRepServiceImpl implements ZxSkMosResMovStatRepService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxSkMosResMovStatRepMapper zxSkMosResMovStatRepMapper;

    @Override
    public ResponseEntity getZxSkMosResMovStatRepListByCondition(ZxSkMosResMovStatRep zxSkMosResMovStatRep) {
        if (zxSkMosResMovStatRep == null) {
            zxSkMosResMovStatRep = new ZxSkMosResMovStatRep();
        }
        // 分页查询
        PageHelper.startPage(zxSkMosResMovStatRep.getPage(),zxSkMosResMovStatRep.getLimit());
        // 获取数据
        List<ZxSkMosResMovStatRep> zxSkMosResMovStatRepList = zxSkMosResMovStatRepMapper.selectByZxSkMosResMovStatRepList(zxSkMosResMovStatRep);
        // 得到分页信息
        PageInfo<ZxSkMosResMovStatRep> p = new PageInfo<>(zxSkMosResMovStatRepList);

        return repEntity.okList(zxSkMosResMovStatRepList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxSkMosResMovStatRepDetail(ZxSkMosResMovStatRep zxSkMosResMovStatRep) {
        if (zxSkMosResMovStatRep == null) {
            zxSkMosResMovStatRep = new ZxSkMosResMovStatRep();
        }
        // 获取数据
        ZxSkMosResMovStatRep dbZxSkMosResMovStatRep = zxSkMosResMovStatRepMapper.selectByPrimaryKey(zxSkMosResMovStatRep.getZxSkMosResMovStatRepId());
        // 数据存在
        if (dbZxSkMosResMovStatRep != null) {
            return repEntity.ok(dbZxSkMosResMovStatRep);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxSkMosResMovStatRep(ZxSkMosResMovStatRep zxSkMosResMovStatRep) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxSkMosResMovStatRep.setZxSkMosResMovStatRepId(UuidUtil.generate());
        zxSkMosResMovStatRep.setCreateUserInfo(userKey, realName);
        int flag = zxSkMosResMovStatRepMapper.insert(zxSkMosResMovStatRep);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxSkMosResMovStatRep);
        }
    }

    @Override
    public ResponseEntity updateZxSkMosResMovStatRep(ZxSkMosResMovStatRep zxSkMosResMovStatRep) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxSkMosResMovStatRep dbZxSkMosResMovStatRep = zxSkMosResMovStatRepMapper.selectByPrimaryKey(zxSkMosResMovStatRep.getZxSkMosResMovStatRepId());
        if (dbZxSkMosResMovStatRep != null && StrUtil.isNotEmpty(dbZxSkMosResMovStatRep.getZxSkMosResMovStatRepId())) {
           // 物资id
           dbZxSkMosResMovStatRep.setResourceID(zxSkMosResMovStatRep.getResourceID());
           // catCode
           dbZxSkMosResMovStatRep.setCatCode(zxSkMosResMovStatRep.getCatCode());
           // 物资名称
           dbZxSkMosResMovStatRep.setResName(zxSkMosResMovStatRep.getResName());
           // 单位
           dbZxSkMosResMovStatRep.setUnit(zxSkMosResMovStatRep.getUnit());
           // 物资编码
           dbZxSkMosResMovStatRep.setResCode(zxSkMosResMovStatRep.getResCode());
           // 年初数量
           dbZxSkMosResMovStatRep.setStockQty(zxSkMosResMovStatRep.getStockQty());
           // 年初金额
           dbZxSkMosResMovStatRep.setStockAmt(zxSkMosResMovStatRep.getStockAmt());
           // 自行采购数量
           dbZxSkMosResMovStatRep.setSerQty(zxSkMosResMovStatRep.getSerQty());
           // 自行采购金额
           dbZxSkMosResMovStatRep.setSerAmt(zxSkMosResMovStatRep.getSerAmt());
           // 自行采购不含税金额
           dbZxSkMosResMovStatRep.setSerAmtNoTax(zxSkMosResMovStatRep.getSerAmtNoTax());
           // 甲供数量
           dbZxSkMosResMovStatRep.setOrsQty(zxSkMosResMovStatRep.getOrsQty());
           // 甲供金额
           dbZxSkMosResMovStatRep.setOrsAmt(zxSkMosResMovStatRep.getOrsAmt());
           // 甲供不含税金额
           dbZxSkMosResMovStatRep.setOrsAmtNoTax(zxSkMosResMovStatRep.getOrsAmtNoTax());
           // 其他数量
           dbZxSkMosResMovStatRep.setOtrQty(zxSkMosResMovStatRep.getOtrQty());
           // 其他金额
           dbZxSkMosResMovStatRep.setOtrAmt(zxSkMosResMovStatRep.getOtrAmt());
           // 其他不含税金额
           dbZxSkMosResMovStatRep.setOtrAmtNoTax(zxSkMosResMovStatRep.getOtrAmtNoTax());
           // 甲控数量
           dbZxSkMosResMovStatRep.setOcsQty(zxSkMosResMovStatRep.getOcsQty());
           // 甲控金额
           dbZxSkMosResMovStatRep.setOcsAmt(zxSkMosResMovStatRep.getOcsAmt());
           // 甲控不含税金额
           dbZxSkMosResMovStatRep.setOcsAmtNoTax(zxSkMosResMovStatRep.getOcsAmtNoTax());
           // 预收数量
           dbZxSkMosResMovStatRep.setObuQty(zxSkMosResMovStatRep.getObuQty());
           // 工程耗用数量
           dbZxSkMosResMovStatRep.setOswQty(zxSkMosResMovStatRep.getOswQty());
           // 工程耗用金额
           dbZxSkMosResMovStatRep.setOswAmt(zxSkMosResMovStatRep.getOswAmt());
           // 调拨数量
           dbZxSkMosResMovStatRep.setOtkQty(zxSkMosResMovStatRep.getOtkQty());
           // 调拨金额
           dbZxSkMosResMovStatRep.setOtkAmt(zxSkMosResMovStatRep.getOtkAmt());
           // 盈亏数量
           dbZxSkMosResMovStatRep.setVinQty(zxSkMosResMovStatRep.getVinQty());
           // 盈亏金额
           dbZxSkMosResMovStatRep.setVinAmt(zxSkMosResMovStatRep.getVinAmt());
           // 数量
           dbZxSkMosResMovStatRep.setQty(zxSkMosResMovStatRep.getQty());
           // 不含税单价
           dbZxSkMosResMovStatRep.setPriceNoTax(zxSkMosResMovStatRep.getPriceNoTax());
           // 含税单价
           dbZxSkMosResMovStatRep.setPrice(zxSkMosResMovStatRep.getPrice());
           // 不含税金额
           dbZxSkMosResMovStatRep.setAmtNoTax(zxSkMosResMovStatRep.getAmtNoTax());
           // 含税金额
           dbZxSkMosResMovStatRep.setAmt(zxSkMosResMovStatRep.getAmt());
           // 统计字母或代码
           dbZxSkMosResMovStatRep.setCode(zxSkMosResMovStatRep.getCode());
           // 期末库存量
           dbZxSkMosResMovStatRep.setEndQty(zxSkMosResMovStatRep.getEndQty());
           // 项目ID
           dbZxSkMosResMovStatRep.setOrgID(zxSkMosResMovStatRep.getOrgID());
           // 项目名称
           dbZxSkMosResMovStatRep.setOrgName(zxSkMosResMovStatRep.getOrgName());
           // 公司ID
           dbZxSkMosResMovStatRep.setComID(zxSkMosResMovStatRep.getComID());
           // 公司名称
           dbZxSkMosResMovStatRep.setComName(zxSkMosResMovStatRep.getComName());
           // 集团ID
           dbZxSkMosResMovStatRep.setBlocID(zxSkMosResMovStatRep.getBlocID());
           // 集团名称
           dbZxSkMosResMovStatRep.setBlocName(zxSkMosResMovStatRep.getBlocName());
           // 备注
           dbZxSkMosResMovStatRep.setRemarks(zxSkMosResMovStatRep.getRemarks());
           // 排序
           dbZxSkMosResMovStatRep.setSort(zxSkMosResMovStatRep.getSort());
           // 共通
           dbZxSkMosResMovStatRep.setModifyUserInfo(userKey, realName);
           flag = zxSkMosResMovStatRepMapper.updateByPrimaryKey(dbZxSkMosResMovStatRep);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxSkMosResMovStatRep);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxSkMosResMovStatRep(List<ZxSkMosResMovStatRep> zxSkMosResMovStatRepList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxSkMosResMovStatRepList != null && zxSkMosResMovStatRepList.size() > 0) {
           ZxSkMosResMovStatRep zxSkMosResMovStatRep = new ZxSkMosResMovStatRep();
           zxSkMosResMovStatRep.setModifyUserInfo(userKey, realName);
           flag = zxSkMosResMovStatRepMapper.batchDeleteUpdateZxSkMosResMovStatRep(zxSkMosResMovStatRepList, zxSkMosResMovStatRep);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxSkMosResMovStatRepList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public ResponseEntity filtrateZxSkMosResMovStatRep(ZxSkMosResMovStatRep zxSkMosResMovStatRep) {
        //删选条件
        //项目or公司or集团
        //期次
        //是否完工
        //通过临时表查询
        List<ZxSkMosResMovStatRep> zxSkMosResMovStatRepList =  zxSkMosResMovStatRepMapper.filtrateZxSkMosResMovStatRep(zxSkMosResMovStatRep);
        for (ZxSkMosResMovStatRep skMosResMovStatRep : zxSkMosResMovStatRepList) {
            switch (skMosResMovStatRep.getCatCode()) {
                // '厘', '分', '角', '圆', '拾', '佰', '仟', '万', '亿'
                case "01":
                    skMosResMovStatRep.setUnit("吨");
                    skMosResMovStatRep.setStatisCode("01");
                    switch(skMosResMovStatRep.getOrderNo()){
                        case "1":
                            skMosResMovStatRep.setResName("其中:(1)大型型钢");
                            skMosResMovStatRep.setStatisCode("a");
                            break;
                        case "2":
                            skMosResMovStatRep.setResName("(2)中小型型钢");
                            skMosResMovStatRep.setStatisCode("b");
                            break;
                        case "3":
                            skMosResMovStatRep.setResName("(3)棒 材");
                            skMosResMovStatRep.setStatisCode("c");
                            break;
                        case "4":
                            skMosResMovStatRep.setResName("(4)砼用钢筋");
                            skMosResMovStatRep.setStatisCode("e");
                            break;
                        case "5":
                            skMosResMovStatRep.setResName("(5)铁道用钢材");
                            skMosResMovStatRep.setStatisCode("d");
                            break;
                        case "6":
                            skMosResMovStatRep.setResName("(6)非砼用钢材");
                            skMosResMovStatRep.setStatisCode("g");
                            break;
                        case "7":
                            skMosResMovStatRep.setResName("(7)薄钢板");
                            skMosResMovStatRep.setStatisCode("p");
                            break;
                        case "8":
                            skMosResMovStatRep.setResName("(8)厚钢板");
                            skMosResMovStatRep.setStatisCode("h");
                            break;
                        case "9":
                            skMosResMovStatRep.setResName("(9)钢带");
                            skMosResMovStatRep.setStatisCode("f");
                            break;
                        case "10":
                            skMosResMovStatRep.setResName("(10)宽扁钢");
                            skMosResMovStatRep.setStatisCode("q");
                            break;
                        case "11":
                            skMosResMovStatRep.setResName("(11)钢板桩");
                            skMosResMovStatRep.setStatisCode("n");
                            break;
                        case "12":
                            skMosResMovStatRep.setResName("(12)钢管");
                            skMosResMovStatRep.setStatisCode("m");
                            break;
                        case "13":
                            skMosResMovStatRep.setResName("(13)中空型钢及中空棒材");
                            skMosResMovStatRep.setStatisCode("t");
                            break;
                        case "14":
                            skMosResMovStatRep.setResName("(14)特殊棒材及特殊中小型钢");
                            skMosResMovStatRep.setStatisCode("u");
                            break;
                        case "15":
                            skMosResMovStatRep.setResName("(15)钢绞线");
                            skMosResMovStatRep.setStatisCode("s");
                            break;
                    }
                    break;
                case "02":
                    skMosResMovStatRep.setUnit("吨");
                    skMosResMovStatRep.setStatisCode("s");
                    break;
                case "03":
                    skMosResMovStatRep.setUnit("吨");
                    break;
                case "04":
                    skMosResMovStatRep.setUnit("吨");
                    break;
                case "05":
                    skMosResMovStatRep.setUnit("吨");
                    break;
                case "06":
                    skMosResMovStatRep.setUnit("吨");
                    break;
                case "07":
                    skMosResMovStatRep.setUnit("立方米");
                    break;
                case "08":
                    skMosResMovStatRep.setUnit("立方米");
                    break;
                case "09":
                    skMosResMovStatRep.setUnit("吨");
                    break;
                case "10":
                    skMosResMovStatRep.setUnit("立方米");
                    break;
                case "11":
                    skMosResMovStatRep.setUnit("吨");
                    break;
                case "12":
                    skMosResMovStatRep.setUnit("吨");
                    break;
                case "13":
                    skMosResMovStatRep.setUnit("吨");
                    break;
                case "14":
                    skMosResMovStatRep.setUnit("吨");
                    break;
                case "15":
                    skMosResMovStatRep.setUnit("吨");
                    break;
                case "16":
                    skMosResMovStatRep.setUnit("吨");
                    break;
            }
        }
        return repEntity.ok(zxSkMosResMovStatRepList);
    }
}
