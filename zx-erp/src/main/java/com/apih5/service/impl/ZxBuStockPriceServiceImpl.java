package com.apih5.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.constant.SysConst;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.mybatis.dao.ZxBuStockPriceItemMapper;
import com.apih5.mybatis.pojo.ZxBuStockPriceItem;
import com.apih5.mybatis.pojo.ZxCtWorks;
import flex.messaging.util.UUIDUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxBuStockPriceMapper;
import com.apih5.mybatis.pojo.ZxBuStockPrice;
import com.apih5.service.ZxBuStockPriceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxBuStockPriceService")
public class ZxBuStockPriceServiceImpl implements ZxBuStockPriceService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxBuStockPriceMapper zxBuStockPriceMapper;

    @Autowired(required = true)
    private ZxBuStockPriceItemMapper zxBuStockPriceItemMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ResponseEntity getZxBuStockPriceListByCondition(ZxBuStockPrice zxBuStockPrice) {
        if (zxBuStockPrice == null) {
            zxBuStockPrice = new ZxBuStockPrice();
        }
        //1.组织机构权限
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxBuStockPrice.setComID("");
            zxBuStockPrice.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxBuStockPrice.setComID(zxBuStockPrice.getOrgID());
            zxBuStockPrice.setOrgID("");
        } else if (StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxBuStockPrice.setComID(zxBuStockPrice.getOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxBuStockPrice.getPage(), zxBuStockPrice.getLimit());
        // 获取数据
        List<ZxBuStockPrice> zxBuStockPriceList = zxBuStockPriceMapper.selectByZxBuStockPriceList(zxBuStockPrice);
        // 得到分页信息
        PageInfo<ZxBuStockPrice> p = new PageInfo<>(zxBuStockPriceList);
//        //查询项目下有没有明细
//        ZxBuStockPriceItem zxBuStockPriceItem = new ZxBuStockPriceItem();
//        zxBuStockPriceItem.setOrgID(zxBuStockPrice.getOrgID());
//        zxBuStockPriceItem.setMixType("11");
//        List<ZxBuStockPriceItem> zxBuStockPriceItems = zxBuStockPriceItemMapper.selectByZxBuStockPriceItemList(zxBuStockPriceItem);
//        zxBuStockPrice.setZxBuStockPriceItemList(zxBuStockPriceItems);
        return repEntity.okList(zxBuStockPriceList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxBuStockPriceDetail(ZxBuStockPrice zxBuStockPrice) {
        if (zxBuStockPrice == null) {
            zxBuStockPrice = new ZxBuStockPrice();
        }
        // 获取数据
        ZxBuStockPrice dbZxBuStockPrice = zxBuStockPriceMapper.selectByPrimaryKey(zxBuStockPrice.getZxBuStockPriceId());
        // 数据存在
        if (dbZxBuStockPrice != null) {
            return repEntity.ok(dbZxBuStockPrice);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxBuStockPrice(ZxBuStockPrice zxBuStockPrice) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxBuStockPrice.setZxBuStockPriceId(UuidUtil.generate());
        zxBuStockPrice.setCreateUserInfo(userKey, realName);
        int flag = zxBuStockPriceMapper.insert(zxBuStockPrice);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxBuStockPrice);
        }
    }

    @Override
    public ResponseEntity updateZxBuStockPrice(ZxBuStockPrice zxBuStockPrice) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxBuStockPrice dbZxBuStockPrice = zxBuStockPriceMapper.selectByPrimaryKey(zxBuStockPrice.getZxBuStockPriceId());
        if (dbZxBuStockPrice != null && StrUtil.isNotEmpty(dbZxBuStockPrice.getZxBuStockPriceId())) {
            // 项目ID
            dbZxBuStockPrice.setOrgID(zxBuStockPrice.getOrgID());
            // 项目名称
            dbZxBuStockPrice.setOrgName(zxBuStockPrice.getOrgName());
            // 编制机构ID
            dbZxBuStockPrice.setReportOrgID(zxBuStockPrice.getReportOrgID());
            // 编制机构名称
            dbZxBuStockPrice.setReportOrgName(zxBuStockPrice.getReportOrgName());
            // 编制人
            dbZxBuStockPrice.setReporter(zxBuStockPrice.getReporter());
            // 编制日期
            dbZxBuStockPrice.setReportDate(zxBuStockPrice.getReportDate());
            // 审核人
            dbZxBuStockPrice.setAuditor(zxBuStockPrice.getAuditor());
            // 审核日期
            dbZxBuStockPrice.setAuditDate(zxBuStockPrice.getAuditDate());
            // 状态
            dbZxBuStockPrice.setStatus(zxBuStockPrice.getStatus());
            // 编辑时间
            dbZxBuStockPrice.setEditTime(zxBuStockPrice.getEditTime());
            //
            dbZxBuStockPrice.setCombProp(zxBuStockPrice.getCombProp());
            //
            dbZxBuStockPrice.setPp1(zxBuStockPrice.getPp1());
            //
            dbZxBuStockPrice.setPp2(zxBuStockPrice.getPp2());
            //
            dbZxBuStockPrice.setPp3(zxBuStockPrice.getPp3());
            //
            dbZxBuStockPrice.setPp4(zxBuStockPrice.getPp4());
            //
            dbZxBuStockPrice.setPp5(zxBuStockPrice.getPp5());
            //
            dbZxBuStockPrice.setPp6(zxBuStockPrice.getPp6());
            //
            dbZxBuStockPrice.setPp7(zxBuStockPrice.getPp7());
            //
            dbZxBuStockPrice.setPp8(zxBuStockPrice.getPp8());
            //
            dbZxBuStockPrice.setPp9(zxBuStockPrice.getPp9());
            //
            dbZxBuStockPrice.setPp10(zxBuStockPrice.getPp10());
            // 期次
            dbZxBuStockPrice.setPeriod(zxBuStockPrice.getPeriod());
            // 类型
            dbZxBuStockPrice.setBudgetType(zxBuStockPrice.getBudgetType());
            // mcsgAPrice
            dbZxBuStockPrice.setMcsgAPrice(zxBuStockPrice.getMcsgAPrice());
            // 分类属性
            dbZxBuStockPrice.setBusinessType(zxBuStockPrice.getBusinessType());
            // comID
            dbZxBuStockPrice.setComID(zxBuStockPrice.getComID());
            // comName
            dbZxBuStockPrice.setComName(zxBuStockPrice.getComName());
            // 标后初次预算是否审核
            dbZxBuStockPrice.setIsBHAudit(zxBuStockPrice.getIsBHAudit());
            // 施工初次预算是否审核
            dbZxBuStockPrice.setIsSGAudit(zxBuStockPrice.getIsSGAudit());
            // 备注
            dbZxBuStockPrice.setRemarks(zxBuStockPrice.getRemarks());
            // 排序
            dbZxBuStockPrice.setSort(zxBuStockPrice.getSort());
            // 共通
            dbZxBuStockPrice.setModifyUserInfo(userKey, realName);
            flag = zxBuStockPriceMapper.updateByPrimaryKey(dbZxBuStockPrice);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update", zxBuStockPrice);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxBuStockPrice(List<ZxBuStockPrice> zxBuStockPriceList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxBuStockPriceList != null && zxBuStockPriceList.size() > 0) {
            //删除明细
            ZxBuStockPriceItem zxBuStockPriceItem = new ZxBuStockPriceItem();
            zxBuStockPriceItem.setOrgID(zxBuStockPriceList.get(0).getOrgID());
            zxBuStockPriceItem.setMixType("11");
            List<ZxBuStockPriceItem> zxBuStockPriceItemList = zxBuStockPriceItemMapper.selectByZxBuStockPriceItemList(zxBuStockPriceItem);
            if (zxBuStockPriceItemList != null && zxBuStockPriceItemList.size() > 0) {
                ZxBuStockPriceItem zxBuStockPriceItem1 = new ZxBuStockPriceItem();
                zxBuStockPriceItem1.setModifyUserInfo(userKey, realName);
                zxBuStockPriceItemMapper.batchDeleteUpdateZxBuStockPriceItem(zxBuStockPriceItemList, zxBuStockPriceItem1);
            }
            ZxBuStockPrice zxBuStockPrice = new ZxBuStockPrice();
            zxBuStockPrice.setModifyUserInfo(userKey, realName);
            flag = zxBuStockPriceMapper.batchDeleteUpdateZxBuStockPrice(zxBuStockPriceList, zxBuStockPrice);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete", zxBuStockPriceList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓

    @Override
    public ResponseEntity importZxBuStockPrice(ZxBuStockPrice zxBuStockPrice) {
        if (zxBuStockPrice == null) {
            zxBuStockPrice = new ZxBuStockPrice();
        }
        if (StrUtil.isEmpty(zxBuStockPrice.getZxBuStockPriceId()) || CollUtil.isEmpty(zxBuStockPrice.getFileList())) {
            return repEntity.layerMessage("no", "请上传导入模板！");
        }
        if (zxBuStockPrice.getFileList().size() != 1) {
            return repEntity.layerMessage("no", "只能上传一份导入模板");
        }
        //获取路径
        String filePath = Apih5Properties.getFilePath() + zxBuStockPrice.getFileList().get(0).getRelativeUrl();
        File file = FileUtil.file(filePath);
        String type = FileTypeUtil.getType(file);
        //判断文件类型
        if (!StrUtil.equals(type, "xls")) {
            return repEntity.layerMessage("no", "只能上传.xls后缀文件");
        }
        //通过excelUtil  读取excel的数据
        ExcelReader reader = ExcelUtil.getReader(filePath);

        //todo:这里是存储模板的位置
        String filePathMaster = "C:\\Users\\张三\\Desktop\\stockPriceNew.xls";
        ExcelReader readerMaster = ExcelUtil.getReader(filePathMaster);
        List<ZxBuStockPriceItem> zxBuStockPriceItems = new ArrayList<>();
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        try {
            List<Map<String, Object>> readMaster = readerMaster.read(2, 3, readerMaster.getSheet().getLastRowNum());
            List<Map<String, Object>> reads = reader.read(2, 3, reader.getSheet().getLastRowNum());
            if (reads == null || reads.size() == 0) {
                return repEntity.layerMessage("no", "excel无数据");
            }
            //比较两个数据,如果不正确抛出
            for (int i = 0; i < reads.size(); i++) {
                ZxBuStockPriceItem zxBuStockPriceItem = new ZxBuStockPriceItem();
                String resCode = String.valueOf(reads.get(i).getOrDefault("材料编号", ""));
                String resName = String.valueOf(reads.get(i).getOrDefault("材料名称", ""));
                String unit = String.valueOf(reads.get(i).getOrDefault("单位", ""));
                String spec = String.valueOf(reads.get(i).getOrDefault("规格型号", ""));
                if (i < 74) {
                    //材料编号
                    String resCodeMaster = String.valueOf(readMaster.get(i).getOrDefault("材料编号", ""));
                    if (!StrUtil.equals(resCode, resCodeMaster)) {
                        return repEntity.layerMessage("no", "导入75条数据和模板数据不整合");
                    }
                    //材料名称
                    String resNameMaster = String.valueOf(readMaster.get(i).getOrDefault("材料名称", ""));
                    if (!StrUtil.equals(resName, resNameMaster)) {
                        return repEntity.layerMessage("no", "导入75条数据和模板数据不整合");
                    }
                    //单位
//                    String unitMaster = String.valueOf(readMaster.get(i).getOrDefault("单位", ""));
//                    if (!StrUtil.equals(unit, unitMaster)) {
//                        return repEntity.layerMessage("no", "导入75条数据和模板数据不整合");
//                    }
                    //规格型号
                    String specMaster = String.valueOf(readMaster.get(i).getOrDefault("规格型号", ""));
                    if (!StrUtil.equals(spec, specMaster)) {
                        return repEntity.layerMessage("no", "导入75条数据和模板数据不整合");
                    }
                }
                //编号
                zxBuStockPriceItem.setResCode(StrUtil.isEmpty(resCode) ? "" : resCode);
                //材料名称
                zxBuStockPriceItem.setResName(StrUtil.isEmpty(resName) ? "" : resName);
                //单位
                zxBuStockPriceItem.setUnit(StrUtil.isEmpty(unit) ? "" : unit);
                //规格型号
                zxBuStockPriceItem.setSpec(StrUtil.isEmpty(spec) ? "" : spec);
                //创建id
                zxBuStockPriceItem.setZxBuStockPriceItemId(UUIDUtils.createUUID());
                //数量（含损耗）判断数量是否是数字,是数字使用,不是为0
                zxBuStockPriceItem.setQty(
                        new BigDecimal(
                                NumberUtil.isNumber(String.valueOf(reads.get(i).getOrDefault("数量(含损耗)", "")))
                                        ? String.valueOf(reads.get(i).getOrDefault("数量(含损耗)", "")) : "0"
                        )
                );
                //材料价格(不含税)
                zxBuStockPriceItem.setMcsgPrice(
                        new BigDecimal(
                                NumberUtil.isNumber(String.valueOf(reads.get(i).getOrDefault("材料价格(不含税)", "")))
                                        ? String.valueOf(reads.get(i).getOrDefault("材料价格(不含税)", "")) : "0"
                        )
                );
                //出厂税率
                zxBuStockPriceItem.setTaxRate(
                        NumberUtil.isNumber(String.valueOf(reads.get(i).getOrDefault("出厂税率", "")))
                                ? String.valueOf(reads.get(i).getOrDefault("出厂税率", "")) : "0"
                );
                //出厂税金单价
                zxBuStockPriceItem.setMcsgPriceTax(
                        new BigDecimal(
                                NumberUtil.isNumber(String.valueOf(reads.get(i).getOrDefault("出厂税金单价", "")))
                                        ? String.valueOf(reads.get(i).getOrDefault("出厂税金单价", "")) : "0"
                        )
                );
                //运费(不含税)
                zxBuStockPriceItem.setYsFee(
                        new BigDecimal(
                                NumberUtil.isNumber(String.valueOf(reads.get(i).getOrDefault("运费(不含税)", "")))
                                        ? String.valueOf(reads.get(i).getOrDefault("运费(不含税)", "")) : "0"
                        )
                );
                //运费税率
                zxBuStockPriceItem.setYsTaxRate(
                        NumberUtil.isNumber(String.valueOf(reads.get(i).getOrDefault("运费税率", "")))
                                ? String.valueOf(reads.get(i).getOrDefault("运费税率", "")) : "0"
                );
                //运费税金单价
                zxBuStockPriceItem.setYsFeeTax(
                        new BigDecimal(
                                NumberUtil.isNumber(String.valueOf(reads.get(i).getOrDefault("运费税金单价", "")))
                                        ? String.valueOf(reads.get(i).getOrDefault("运费税金单价", "")) : "0"
                        )
                );
                //不含税到场价(元)
                zxBuStockPriceItem.setScenePrice(
                        new BigDecimal(
                                NumberUtil.isNumber(String.valueOf(reads.get(i).getOrDefault("不含税到场价(元)", "")))
                                        ? String.valueOf(reads.get(i).getOrDefault("不含税到场价(元)", "")) : "0"
                        )
                );
                //进项税金(元)
                zxBuStockPriceItem.setTaxAmt(
                        new BigDecimal(
                                NumberUtil.isNumber(String.valueOf(reads.get(i).getOrDefault("进项税金(元)", "")))
                                        ? String.valueOf(reads.get(i).getOrDefault("进项税金(元)", "")) : "0"
                        )
                );
                zxBuStockPriceItem.setMixType("11");
                zxBuStockPriceItem.setOrgID(zxBuStockPrice.getOrgID());
                zxBuStockPriceItem.setStockPriceID(zxBuStockPrice.getZxBuStockPriceId());
                zxBuStockPriceItem.setCreateUserInfo(userKey, realName);
                zxBuStockPriceItem.setDelFlag("0");
                //默认是否调差为 是
                zxBuStockPriceItem.setIsAdjustment("0");
                //参考值 默认为0
                zxBuStockPriceItem.setReferValue(new BigDecimal("0"));
                //当前项目id
                zxBuStockPriceItems.add(zxBuStockPriceItem);
            }
        } catch (Exception e) {
            LoggerUtils.printLogger(logger,e.getMessage());
            System.out.println(e);
        }
        //先删后加
        ZxBuStockPriceItem zxBuStockPriceItem = new ZxBuStockPriceItem();
        zxBuStockPriceItem.setOrgID(zxBuStockPrice.getOrgID());
        zxBuStockPriceItem.setMixType("11");
        List<ZxBuStockPriceItem> zxBuStockPriceItemList = zxBuStockPriceItemMapper.selectByZxBuStockPriceItemList(zxBuStockPriceItem);
        if (zxBuStockPriceItemList != null && zxBuStockPriceItemList.size() > 0) {
            ZxBuStockPriceItem zxBuStockPriceItem1 = new ZxBuStockPriceItem();
            zxBuStockPriceItem1.setModifyUserInfo(userKey, realName);
            zxBuStockPriceItemMapper.batchDeleteUpdateZxBuStockPriceItem(zxBuStockPriceItemList, zxBuStockPriceItem1);
        }
        int flag = zxBuStockPriceItemMapper.insertAll(zxBuStockPriceItems);
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.sava", zxBuStockPrice);
        }
    }


}
