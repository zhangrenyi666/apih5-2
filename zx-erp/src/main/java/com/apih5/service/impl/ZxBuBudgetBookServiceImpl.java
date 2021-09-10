package com.apih5.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import com.apih5.framework.constant.SysConst;
import com.apih5.mybatis.dao.ZxBuBudgetDetailsMapper;
import com.apih5.mybatis.dao.ZxErpFileMapper;
import com.apih5.mybatis.pojo.ZxBuBudgetDetails;
import com.apih5.mybatis.pojo.ZxBuStockPrice;
import com.apih5.mybatis.pojo.ZxErpFile;
import com.apih5.service.ZxBuBudgetDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.mybatis.dao.ZxBuBudgetBookMapper;
import com.apih5.mybatis.pojo.ZxBuBudgetBook;
import com.apih5.service.ZxBuBudgetBookService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import cn.hutool.core.util.StrUtil;

@Service("zxBuBudgetBookService")
public class ZxBuBudgetBookServiceImpl implements ZxBuBudgetBookService {

    @Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private ZxBuBudgetBookMapper zxBuBudgetBookMapper;

    @Autowired(required = true)
    private ZxErpFileMapper zxErpFileMapper;

    @Autowired(required = true)
    private ZxBuBudgetDetailsMapper zxBuBudgetDetailsMapper;

    @Override
    public ResponseEntity getZxBuBudgetBookListByCondition(ZxBuBudgetBook zxBuBudgetBook) {
        if (zxBuBudgetBook == null) {
            zxBuBudgetBook = new ZxBuBudgetBook();
        }
        //1.组织机构权限
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userId = TokenUtils.getUserId(request);
        String ext1 = TokenUtils.getExt1(request);
        // 集团全部可见
        if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP, ext1)
                || StrUtil.equals("admin", userId)) {
            zxBuBudgetBook.setReportOrgID("");
            zxBuBudgetBook.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_COMPANY, ext1)) {
            // 公司只看见自己的
            zxBuBudgetBook.setReportOrgID(zxBuBudgetBook.getOrgID());
            zxBuBudgetBook.setOrgID("");
        } else if(StrUtil.equals(SysConst.SYS_USER_TYPE_TOP_PRO, ext1)
                || StrUtil.equals(SysConst.SYS_USER_TYPE_PRO, ext1)) {
            // 项目通过右上角数据
            zxBuBudgetBook.setOrgID(zxBuBudgetBook.getOrgID());
        }
        // 分页查询
        PageHelper.startPage(zxBuBudgetBook.getPage(),zxBuBudgetBook.getLimit());
        // 获取数据
        List<ZxBuBudgetBook> zxBuBudgetBookList = zxBuBudgetBookMapper.selectByZxBuBudgetBookList(zxBuBudgetBook);

        //附件
        for (ZxBuBudgetBook zxBuBudgetBook1 : zxBuBudgetBookList) {
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(zxBuBudgetBook1.getZxBuBudgetBookId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            zxBuBudgetBook1.setFileList(zxErpFiles);
        }
        // 得到分页信息
        PageInfo<ZxBuBudgetBook> p = new PageInfo<>(zxBuBudgetBookList);

        return repEntity.okList(zxBuBudgetBookList, p.getTotal());
    }

    @Override
    public ResponseEntity getZxBuBudgetBookDetail(ZxBuBudgetBook zxBuBudgetBook) {
        if (zxBuBudgetBook == null) {
            zxBuBudgetBook = new ZxBuBudgetBook();
        }
        // 获取数据
        ZxBuBudgetBook dbZxBuBudgetBook = zxBuBudgetBookMapper.selectByPrimaryKey(zxBuBudgetBook.getZxBuBudgetBookId());
        // 数据存在
        if (dbZxBuBudgetBook != null) {
            //附件
            ZxErpFile zxErpFile = new ZxErpFile();
            zxErpFile.setOtherId(dbZxBuBudgetBook.getZxBuBudgetBookId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
            dbZxBuBudgetBook.setFileList(zxErpFiles);
            return repEntity.ok(dbZxBuBudgetBook);
        } else {
            return repEntity.layerMessage("no", "无数据！");
        }
    }

    @Override
    public ResponseEntity saveZxBuBudgetBook(ZxBuBudgetBook zxBuBudgetBook) {
        //看项目是否存在,如果存在则不允许在新增
        if(StrUtil.isNotEmpty(zxBuBudgetBook.getOrgID()) && NumberUtil.isNumber(String.valueOf(zxBuBudgetBook.getBudgetType()))){
            ZxBuBudgetBook zxBuBudgetBook1 = new ZxBuBudgetBook();
            zxBuBudgetBook1.setOrgID(zxBuBudgetBook.getOrgID());
            zxBuBudgetBook1.setBudgetType(zxBuBudgetBook.getBudgetType());
            //查询是否存在
            List<ZxBuBudgetBook> zxBuBudgetBooks = zxBuBudgetBookMapper.selectByZxBuBudgetBookList(zxBuBudgetBook1);
            if(CollectionUtil.isNotEmpty(zxBuBudgetBooks)){
                return repEntity.layerMessage(false,null,"本项目已存在不允许多次创建");
            }
        }
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        zxBuBudgetBook.setZxBuBudgetBookId(UuidUtil.generate());
        zxBuBudgetBook.setCreateUserInfo(userKey, realName);
        //添加附件
        List<ZxErpFile> fileList = zxBuBudgetBook.getFileList();
        if(fileList != null && fileList.size()>0) {
            for (ZxErpFile zxErpFile : fileList) {
                zxErpFile.setOtherId(zxBuBudgetBook.getZxBuBudgetBookId());
                zxErpFile.setUid((UuidUtil.generate()));
                zxErpFile.setCreateUserInfo(userKey, realName);
                zxErpFileMapper.insert(zxErpFile);
            }
        }
        int flag = zxBuBudgetBookMapper.insert(zxBuBudgetBook);
        //todo:期次问题
        if(NumberUtil.equals(new BigDecimal(zxBuBudgetBook.getBudgetType()),new BigDecimal(20))){
            //在新建一条200的数据
            zxBuBudgetBook.setZxBuBudgetBookId(UuidUtil.generate()+"_202004");
            zxBuBudgetBook.setBudgetType(200);
            flag = zxBuBudgetBookMapper.insert(zxBuBudgetBook);
        }
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            //如果成功了在这添加预算书的详细数据 - 标后预算
            if(NumberUtil.equals(new BigDecimal(zxBuBudgetBook.getBudgetType()),new BigDecimal(10))){
                //1
                ZxBuBudgetDetails zxBuBudgetDetails1 = new ZxBuBudgetDetails();
                //序号
                zxBuBudgetDetails1.setSerialNumber("1");
                //费用名称
                zxBuBudgetDetails1.setBudgetElement("工程量清单综合基价费用");
                //费用(元)
                zxBuBudgetDetails1.setBudgetElementFirstFree(new BigDecimal("0"));
                //费用所占建筑安装工程费比例(%)
                zxBuBudgetDetails1.setNeedDeduct(0);
                //预算书id
                zxBuBudgetDetails1.setBudgetBookID(zxBuBudgetBook.getZxBuBudgetBookId());
                //类型
                zxBuBudgetDetails1.setBudgetType("标后预算");
                //元素类型
                zxBuBudgetDetails1.setBudgetElementType("signed_zh");
                //元素单位
                zxBuBudgetDetails1.setBudgetElementUnit("元");
                zxBuBudgetDetails1.setZxBuBudgetDetailsId((UuidUtil.generate()));
                zxBuBudgetDetails1.setCreateUserInfo(userKey, realName);
                zxBuBudgetDetailsMapper.insert(zxBuBudgetDetails1);

                //2
                ZxBuBudgetDetails zxBuBudgetDetails2 = new ZxBuBudgetDetails();
                //序号
                zxBuBudgetDetails2.setSerialNumber("2");
                //费用名称
                zxBuBudgetDetails2.setBudgetElement("现场文明施工费");
                //费用(元)
                zxBuBudgetDetails2.setBudgetElementFirstFree(new BigDecimal("0"));
                //费用所占建筑安装工程费比例(%)
                zxBuBudgetDetails2.setNeedDeduct(0);
                //预算书id
                zxBuBudgetDetails2.setBudgetBookID(zxBuBudgetBook.getZxBuBudgetBookId());
                //类型
                zxBuBudgetDetails2.setBudgetType("标后预算");
                //元素类型
                zxBuBudgetDetails2.setBudgetElementType("signed_manager");
                //元素单位
                zxBuBudgetDetails2.setBudgetElementUnit("元");
                zxBuBudgetDetails2.setZxBuBudgetDetailsId((UuidUtil.generate()));
                zxBuBudgetDetails2.setCreateUserInfo(userKey, realName);
                zxBuBudgetDetailsMapper.insert(zxBuBudgetDetails2);

                //3
                ZxBuBudgetDetails zxBuBudgetDetails3 = new ZxBuBudgetDetails();
                //序号
                zxBuBudgetDetails3.setSerialNumber("3");
                //费用名称
                zxBuBudgetDetails3.setBudgetElement("临时设施费");
                //费用(元)
                zxBuBudgetDetails3.setBudgetElementFirstFree(new BigDecimal("0"));
                //费用所占建筑安装工程费比例(%)
                zxBuBudgetDetails3.setNeedDeduct(0);
                //预算书id
                zxBuBudgetDetails3.setBudgetBookID(zxBuBudgetBook.getZxBuBudgetBookId());
                //类型
                zxBuBudgetDetails3.setBudgetType("标后预算");
                //元素类型
                zxBuBudgetDetails3.setBudgetElementType("signed_manager");
                //元素单位
                zxBuBudgetDetails3.setBudgetElementUnit("元");
                zxBuBudgetDetails3.setZxBuBudgetDetailsId((UuidUtil.generate()));
                zxBuBudgetDetails3.setCreateUserInfo(userKey, realName);
                zxBuBudgetDetailsMapper.insert(zxBuBudgetDetails3);

                //4
                ZxBuBudgetDetails zxBuBudgetDetails4 = new ZxBuBudgetDetails();
                //序号
                zxBuBudgetDetails4.setSerialNumber("4");
                //费用名称
                zxBuBudgetDetails4.setBudgetElement("经理部管理费");
                //费用(元)
                zxBuBudgetDetails4.setBudgetElementFirstFree(new BigDecimal("0"));
                //费用所占建筑安装工程费比例(%)
                zxBuBudgetDetails4.setNeedDeduct(0);
                //预算书id
                zxBuBudgetDetails4.setBudgetBookID(zxBuBudgetBook.getZxBuBudgetBookId());
                //类型
                zxBuBudgetDetails4.setBudgetType("标后预算");
                //元素类型
                zxBuBudgetDetails4.setBudgetElementType("signed_manager");
                //元素单位
                zxBuBudgetDetails4.setBudgetElementUnit("元");
                zxBuBudgetDetails4.setZxBuBudgetDetailsId((UuidUtil.generate()));
                zxBuBudgetDetails4.setCreateUserInfo(userKey, realName);
                zxBuBudgetDetailsMapper.insert(zxBuBudgetDetails4);

                //5
                ZxBuBudgetDetails zxBuBudgetDetails5 = new ZxBuBudgetDetails();
                //序号
                zxBuBudgetDetails5.setSerialNumber("5");
                //费用名称
                zxBuBudgetDetails5.setBudgetElement("安全生产费");
                //费用(元)
                zxBuBudgetDetails5.setBudgetElementFirstFree(new BigDecimal("0"));
                //费用所占建筑安装工程费比例(%)
                zxBuBudgetDetails5.setNeedDeduct(0);
                //预算书id
                zxBuBudgetDetails5.setBudgetBookID(zxBuBudgetBook.getZxBuBudgetBookId());
                //类型
                zxBuBudgetDetails5.setBudgetType("标后预算");
                //元素类型
                zxBuBudgetDetails5.setBudgetElementType("signed_others");
                //元素单位
                zxBuBudgetDetails5.setBudgetElementUnit("元");
                zxBuBudgetDetails5.setZxBuBudgetDetailsId((UuidUtil.generate()));
                zxBuBudgetDetails5.setCreateUserInfo(userKey, realName);
                zxBuBudgetDetailsMapper.insert(zxBuBudgetDetails5);

                //6
                ZxBuBudgetDetails zxBuBudgetDetails6 = new ZxBuBudgetDetails();
                //序号
                zxBuBudgetDetails6.setSerialNumber("6");
                //费用名称
                zxBuBudgetDetails6.setBudgetElement("税金(销项税金)");
                //费用(元)
                zxBuBudgetDetails6.setBudgetElementFirstFree(new BigDecimal("0"));
                //费用所占建筑安装工程费比例(%)
                zxBuBudgetDetails6.setNeedDeduct(0);
                //预算书id
                zxBuBudgetDetails6.setBudgetBookID(zxBuBudgetBook.getZxBuBudgetBookId());
                //类型
                zxBuBudgetDetails6.setBudgetType("标后预算");
                //元素类型
                zxBuBudgetDetails6.setBudgetElementType("signed_tax");
                //元素单位
                zxBuBudgetDetails6.setBudgetElementUnit("元");
                zxBuBudgetDetails6.setZxBuBudgetDetailsId((UuidUtil.generate()));
                zxBuBudgetDetails6.setCreateUserInfo(userKey, realName);
                zxBuBudgetDetailsMapper.insert(zxBuBudgetDetails6);

                //7
                ZxBuBudgetDetails zxBuBudgetDetails7 = new ZxBuBudgetDetails();
                //序号
                zxBuBudgetDetails7.setSerialNumber("7");
                //费用名称
                zxBuBudgetDetails7.setBudgetElement("财务费用");
                //费用(元)
                zxBuBudgetDetails7.setBudgetElementFirstFree(new BigDecimal("0"));
                //费用所占建筑安装工程费比例(%)
                zxBuBudgetDetails7.setNeedDeduct(0);
                //预算书id
                zxBuBudgetDetails7.setBudgetBookID(zxBuBudgetBook.getZxBuBudgetBookId());
                //类型
                zxBuBudgetDetails7.setBudgetType("标后预算");
                //元素类型
                zxBuBudgetDetails7.setBudgetElementType("signed_others");
                //元素单位
                zxBuBudgetDetails7.setBudgetElementUnit("元");
                zxBuBudgetDetails7.setZxBuBudgetDetailsId((UuidUtil.generate()));
                zxBuBudgetDetails7.setCreateUserInfo(userKey, realName);
                zxBuBudgetDetailsMapper.insert(zxBuBudgetDetails7);

                //8
                ZxBuBudgetDetails zxBuBudgetDetails8 = new ZxBuBudgetDetails();
                //序号
                zxBuBudgetDetails8.setSerialNumber("8");
                //费用名称
                zxBuBudgetDetails8.setBudgetElement("暂列金额");
                //费用(元)
                zxBuBudgetDetails8.setBudgetElementFirstFree(new BigDecimal("0"));
                //费用所占建筑安装工程费比例(%)
                zxBuBudgetDetails8.setNeedDeduct(0);
                //预算书id
                zxBuBudgetDetails8.setBudgetBookID(zxBuBudgetBook.getZxBuBudgetBookId());
                //类型
                zxBuBudgetDetails8.setBudgetType("标后预算");
                //元素类型
                zxBuBudgetDetails8.setBudgetElementType("signed_others");
                //元素单位
                zxBuBudgetDetails8.setBudgetElementUnit("元");
                zxBuBudgetDetails8.setZxBuBudgetDetailsId((UuidUtil.generate()));
                zxBuBudgetDetails8.setCreateUserInfo(userKey, realName);
                zxBuBudgetDetailsMapper.insert(zxBuBudgetDetails8);

                //9
                ZxBuBudgetDetails zxBuBudgetDetails9 = new ZxBuBudgetDetails();
                //序号
                zxBuBudgetDetails9.setSerialNumber("9");
                //费用名称
                zxBuBudgetDetails9.setBudgetElement("计日工");
                //费用(元)
                zxBuBudgetDetails9.setBudgetElementFirstFree(new BigDecimal("0"));
                //费用所占建筑安装工程费比例(%)
                zxBuBudgetDetails9.setNeedDeduct(0);
                //预算书id
                zxBuBudgetDetails9.setBudgetBookID(zxBuBudgetBook.getZxBuBudgetBookId());
                //类型
                zxBuBudgetDetails9.setBudgetType("标后预算");
                //元素类型
                zxBuBudgetDetails9.setBudgetElementType("signed_others");
                //元素单位
                zxBuBudgetDetails9.setBudgetElementUnit("元");
                zxBuBudgetDetails9.setZxBuBudgetDetailsId((UuidUtil.generate()));
                zxBuBudgetDetails9.setCreateUserInfo(userKey, realName);
                zxBuBudgetDetailsMapper.insert(zxBuBudgetDetails9);

                //10
                ZxBuBudgetDetails zxBuBudgetDetails10 = new ZxBuBudgetDetails();
                //序号
                zxBuBudgetDetails10.setSerialNumber("10");
                //费用名称
                zxBuBudgetDetails10.setBudgetElement("中国交建/公司管理费");
                //费用(元)
                zxBuBudgetDetails10.setBudgetElementFirstFree(new BigDecimal("0"));
                //费用所占建筑安装工程费比例(%)
                zxBuBudgetDetails10.setNeedDeduct(0);
                //预算书id
                zxBuBudgetDetails10.setBudgetBookID(zxBuBudgetBook.getZxBuBudgetBookId());
                //类型
                zxBuBudgetDetails10.setBudgetType("标后预算");
                //元素类型
                zxBuBudgetDetails10.setBudgetElementType("signed_others");
                //元素单位
                zxBuBudgetDetails10.setBudgetElementUnit("元");
                zxBuBudgetDetails10.setZxBuBudgetDetailsId((UuidUtil.generate()));
                zxBuBudgetDetails10.setCreateUserInfo(userKey, realName);
                zxBuBudgetDetailsMapper.insert(zxBuBudgetDetails10);

                //11 中标合同价（元）
                ZxBuBudgetDetails zxBuBudgetDetails11 = new ZxBuBudgetDetails();
                //序号
                zxBuBudgetDetails11.setSerialNumber("11");
                //费用名称
                zxBuBudgetDetails11.setBudgetElement("中标合同价(元)");
                //费用(元)
                zxBuBudgetDetails11.setBudgetElementFirstFree(new BigDecimal("0"));
                //费用所占建筑安装工程费比例(%)
                zxBuBudgetDetails11.setNeedDeduct(0);
                //预算书id
                zxBuBudgetDetails11.setBudgetBookID(zxBuBudgetBook.getZxBuBudgetBookId());
                //类型
                zxBuBudgetDetails11.setBudgetType("标后预算");
                //元素类型
                zxBuBudgetDetails11.setBudgetElementType("signed_project");
                //元素单位
                zxBuBudgetDetails11.setBudgetElementUnit("元");
                zxBuBudgetDetails11.setZxBuBudgetDetailsId((UuidUtil.generate()));
                zxBuBudgetDetails11.setCreateUserInfo(userKey, realName);
                zxBuBudgetDetailsMapper.insert(zxBuBudgetDetails11);
            }else if(NumberUtil.equals(new BigDecimal(zxBuBudgetBook.getBudgetType()),new BigDecimal(200))){
                //todo:标后预算费用,施工预算费用.差值的字段待确认.
                //1
                ZxBuBudgetDetails zxBuBudgetDetails1 = new ZxBuBudgetDetails();
                //序号
                zxBuBudgetDetails1.setSerialNumber("1");
                //费用名称
                zxBuBudgetDetails1.setBudgetElement("工程量清单费用/施工成本");
                //标后预算费用(元)
                zxBuBudgetDetails1.setBudgetElementFirstFree(new BigDecimal(0));
                //施工预算费用(元)
                zxBuBudgetDetails1.setBudgetElementCurrFree(new BigDecimal(0));
                //差值
                zxBuBudgetDetails1.setAudited(0);
                //费用所占建筑安装工程费比例(%)
                zxBuBudgetDetails1.setNeedDeduct(0);
                //预算书id
                zxBuBudgetDetails1.setBudgetBookID(zxBuBudgetBook.getZxBuBudgetBookId());
                //类型
                zxBuBudgetDetails1.setBudgetType("施工预算");
                //元素类型
                zxBuBudgetDetails1.setBudgetElementType("signed_zh");
                //元素单位
                zxBuBudgetDetails1.setBudgetElementUnit("元");
                zxBuBudgetDetails1.setZxBuBudgetDetailsId((UuidUtil.generate()));
                zxBuBudgetDetails1.setCreateUserInfo(userKey, realName);
                zxBuBudgetDetailsMapper.insert(zxBuBudgetDetails1);


                //2
                ZxBuBudgetDetails zxBuBudgetDetails2 = new ZxBuBudgetDetails();
                //序号
                zxBuBudgetDetails2.setSerialNumber("2");
                //费用名称
                zxBuBudgetDetails2.setBudgetElement("现场文明施工费");
                //标后预算费用(元)
                zxBuBudgetDetails2.setBudgetElementFirstFree(new BigDecimal(0));
                //施工预算费用(元)
                zxBuBudgetDetails2.setBudgetElementCurrFree(new BigDecimal(0));
                //差值
                zxBuBudgetDetails2.setAudited(0);
                //费用所占建筑安装工程费比例(%)
                zxBuBudgetDetails2.setNeedDeduct(0);
                //预算书id
                zxBuBudgetDetails2.setBudgetBookID(zxBuBudgetBook.getZxBuBudgetBookId());
                //类型
                zxBuBudgetDetails2.setBudgetType("施工预算");
                //元素类型
                zxBuBudgetDetails2.setBudgetElementType("signed_manager");
                //元素单位
                zxBuBudgetDetails2.setBudgetElementUnit("元");
                zxBuBudgetDetails2.setZxBuBudgetDetailsId((UuidUtil.generate()));
                zxBuBudgetDetails2.setCreateUserInfo(userKey, realName);
                zxBuBudgetDetailsMapper.insert(zxBuBudgetDetails2);


                //3
                ZxBuBudgetDetails zxBuBudgetDetails3 = new ZxBuBudgetDetails();
                //序号
                zxBuBudgetDetails3.setSerialNumber("3");
                //费用名称
                zxBuBudgetDetails3.setBudgetElement("临时设施费");
                //标后预算费用(元)
                zxBuBudgetDetails3.setBudgetElementFirstFree(new BigDecimal(0));
                //施工预算费用(元)
                zxBuBudgetDetails3.setBudgetElementCurrFree(new BigDecimal(0));
                //差值
                zxBuBudgetDetails3.setAudited(0);
                //费用所占建筑安装工程费比例(%)
                zxBuBudgetDetails3.setNeedDeduct(0);
                //预算书id
                zxBuBudgetDetails3.setBudgetBookID(zxBuBudgetBook.getZxBuBudgetBookId());
                //类型
                zxBuBudgetDetails3.setBudgetType("施工预算");
                //元素类型
                zxBuBudgetDetails3.setBudgetElementType("signed_manager");
                //元素单位
                zxBuBudgetDetails3.setBudgetElementUnit("元");
                zxBuBudgetDetails3.setZxBuBudgetDetailsId((UuidUtil.generate()));
                zxBuBudgetDetails3.setCreateUserInfo(userKey, realName);
                zxBuBudgetDetailsMapper.insert(zxBuBudgetDetails3);

                //4
                ZxBuBudgetDetails zxBuBudgetDetails4 = new ZxBuBudgetDetails();
                //序号
                zxBuBudgetDetails4.setSerialNumber("4");
                //费用名称
                zxBuBudgetDetails4.setBudgetElement("经理部管理费");
                //标后预算费用(元)
                zxBuBudgetDetails4.setBudgetElementFirstFree(new BigDecimal(0));
                //施工预算费用(元)
                zxBuBudgetDetails4.setBudgetElementCurrFree(new BigDecimal(0));
                //差值
                zxBuBudgetDetails4.setAudited(0);
                //费用所占建筑安装工程费比例(%)
                zxBuBudgetDetails4.setNeedDeduct(0);
                //预算书id
                zxBuBudgetDetails4.setBudgetBookID(zxBuBudgetBook.getZxBuBudgetBookId());
                //类型
                zxBuBudgetDetails4.setBudgetType("施工预算");
                //元素类型
                zxBuBudgetDetails4.setBudgetElementType("signed_manager");
                //元素单位
                zxBuBudgetDetails4.setBudgetElementUnit("元");
                zxBuBudgetDetails4.setZxBuBudgetDetailsId((UuidUtil.generate()));
                zxBuBudgetDetails4.setCreateUserInfo(userKey, realName);
                zxBuBudgetDetailsMapper.insert(zxBuBudgetDetails4);

                //5
                ZxBuBudgetDetails zxBuBudgetDetails5 = new ZxBuBudgetDetails();
                //序号
                zxBuBudgetDetails5.setSerialNumber("5");
                //费用名称
                zxBuBudgetDetails5.setBudgetElement("安全生产费");
                //标后预算费用(元)
                zxBuBudgetDetails5.setBudgetElementFirstFree(new BigDecimal(0));
                //施工预算费用(元)
                zxBuBudgetDetails5.setBudgetElementCurrFree(new BigDecimal(0));
                //差值
                zxBuBudgetDetails5.setAudited(0);
                //费用所占建筑安装工程费比例(%)
                zxBuBudgetDetails5.setNeedDeduct(0);
                //预算书id
                zxBuBudgetDetails5.setBudgetBookID(zxBuBudgetBook.getZxBuBudgetBookId());
                //类型
                zxBuBudgetDetails5.setBudgetType("施工预算");
                //元素类型
                zxBuBudgetDetails5.setBudgetElementType("signed_others");
                //元素单位
                zxBuBudgetDetails5.setBudgetElementUnit("元");
                zxBuBudgetDetails5.setZxBuBudgetDetailsId((UuidUtil.generate()));
                zxBuBudgetDetails5.setCreateUserInfo(userKey, realName);
                zxBuBudgetDetailsMapper.insert(zxBuBudgetDetails5);

                //6
                ZxBuBudgetDetails zxBuBudgetDetails6 = new ZxBuBudgetDetails();
                //序号
                zxBuBudgetDetails6.setSerialNumber("6");
                //费用名称
                zxBuBudgetDetails6.setBudgetElement("税金(销项税金)");
                //标后预算费用(元)
                zxBuBudgetDetails6.setBudgetElementFirstFree(new BigDecimal(0));
                //施工预算费用(元)
                zxBuBudgetDetails6.setBudgetElementCurrFree(new BigDecimal(0));
                //差值
                zxBuBudgetDetails6.setAudited(0);
                //费用所占建筑安装工程费比例(%)
                zxBuBudgetDetails6.setNeedDeduct(0);
                //预算书id
                zxBuBudgetDetails6.setBudgetBookID(zxBuBudgetBook.getZxBuBudgetBookId());
                //类型
                zxBuBudgetDetails6.setBudgetType("施工预算");
                //元素类型
                zxBuBudgetDetails6.setBudgetElementType("signed_tax");
                //元素单位
                zxBuBudgetDetails6.setBudgetElementUnit("元");
                zxBuBudgetDetails6.setZxBuBudgetDetailsId((UuidUtil.generate()));
                zxBuBudgetDetails6.setCreateUserInfo(userKey, realName);
                zxBuBudgetDetailsMapper.insert(zxBuBudgetDetails6);

                //7
                ZxBuBudgetDetails zxBuBudgetDetails7 = new ZxBuBudgetDetails();
                //序号
                zxBuBudgetDetails7.setSerialNumber("7");
                //费用名称
                zxBuBudgetDetails7.setBudgetElement("财务费用");
                //标后预算费用(元)
                zxBuBudgetDetails7.setBudgetElementFirstFree(new BigDecimal(0));
                //施工预算费用(元)
                zxBuBudgetDetails7.setBudgetElementCurrFree(new BigDecimal(0));
                //差值
                zxBuBudgetDetails7.setAudited(0);
                //费用所占建筑安装工程费比例(%)
                zxBuBudgetDetails7.setNeedDeduct(0);
                //预算书id
                zxBuBudgetDetails7.setBudgetBookID(zxBuBudgetBook.getZxBuBudgetBookId());
                //类型
                zxBuBudgetDetails7.setBudgetType("施工预算");
                //元素类型
                zxBuBudgetDetails7.setBudgetElementType("signed_others");
                //元素单位
                zxBuBudgetDetails7.setBudgetElementUnit("元");
                zxBuBudgetDetails7.setZxBuBudgetDetailsId((UuidUtil.generate()));
                zxBuBudgetDetails7.setCreateUserInfo(userKey, realName);
                zxBuBudgetDetailsMapper.insert(zxBuBudgetDetails7);

                //8
                ZxBuBudgetDetails zxBuBudgetDetails8 = new ZxBuBudgetDetails();
                //序号
                zxBuBudgetDetails8.setSerialNumber("8");
                //费用名称
                zxBuBudgetDetails8.setBudgetElement("暂列金额");
                //标后预算费用(元)
                zxBuBudgetDetails8.setBudgetElementFirstFree(new BigDecimal(0));
                //施工预算费用(元)
                zxBuBudgetDetails8.setBudgetElementCurrFree(new BigDecimal(0));
                //差值
                zxBuBudgetDetails8.setAudited(0);
                //费用所占建筑安装工程费比例(%)
                zxBuBudgetDetails8.setNeedDeduct(0);
                //预算书id
                zxBuBudgetDetails8.setBudgetBookID(zxBuBudgetBook.getZxBuBudgetBookId());
                //类型
                zxBuBudgetDetails8.setBudgetType("施工预算");
                //元素类型
                zxBuBudgetDetails8.setBudgetElementType("signed_others");
                //元素单位
                zxBuBudgetDetails8.setBudgetElementUnit("元");
                zxBuBudgetDetails8.setZxBuBudgetDetailsId((UuidUtil.generate()));
                zxBuBudgetDetails8.setCreateUserInfo(userKey, realName);
                zxBuBudgetDetailsMapper.insert(zxBuBudgetDetails8);

                //9
                ZxBuBudgetDetails zxBuBudgetDetails9 = new ZxBuBudgetDetails();
                //序号
                zxBuBudgetDetails9.setSerialNumber("9");
                //费用名称
                zxBuBudgetDetails9.setBudgetElement("计日工");
                //标后预算费用(元)
                zxBuBudgetDetails9.setBudgetElementFirstFree(new BigDecimal(0));
                //施工预算费用(元)
                zxBuBudgetDetails9.setBudgetElementCurrFree(new BigDecimal(0));
                //差值
                zxBuBudgetDetails9.setAudited(0);
                //费用所占建筑安装工程费比例(%)
                zxBuBudgetDetails9.setNeedDeduct(0);
                //预算书id
                zxBuBudgetDetails9.setBudgetBookID(zxBuBudgetBook.getZxBuBudgetBookId());
                //类型
                zxBuBudgetDetails9.setBudgetType("施工预算");
                //元素类型
                zxBuBudgetDetails9.setBudgetElementType("signed_others");
                //元素单位
                zxBuBudgetDetails9.setBudgetElementUnit("元");
                zxBuBudgetDetails9.setZxBuBudgetDetailsId((UuidUtil.generate()));
                zxBuBudgetDetails9.setCreateUserInfo(userKey, realName);
                zxBuBudgetDetailsMapper.insert(zxBuBudgetDetails9);

                //10
                ZxBuBudgetDetails zxBuBudgetDetails10 = new ZxBuBudgetDetails();
                //序号
                zxBuBudgetDetails10.setSerialNumber("10");
                //费用名称
                zxBuBudgetDetails10.setBudgetElement("中国交建/公司管理费");
                //标后预算费用(元)
                zxBuBudgetDetails10.setBudgetElementFirstFree(new BigDecimal(0));
                //施工预算费用(元)
                zxBuBudgetDetails10.setBudgetElementCurrFree(new BigDecimal(0));
                //差值
                zxBuBudgetDetails10.setAudited(0);
                //费用所占建筑安装工程费比例(%)
                zxBuBudgetDetails10.setNeedDeduct(0);
                //预算书id
                zxBuBudgetDetails10.setBudgetBookID(zxBuBudgetBook.getZxBuBudgetBookId());
                //类型
                zxBuBudgetDetails10.setBudgetType("施工预算");
                //元素类型
                zxBuBudgetDetails10.setBudgetElementType("signed_others");
                //元素单位
                zxBuBudgetDetails10.setBudgetElementUnit("元");
                zxBuBudgetDetails10.setZxBuBudgetDetailsId((UuidUtil.generate()));
                zxBuBudgetDetails10.setCreateUserInfo(userKey, realName);
                zxBuBudgetDetailsMapper.insert(zxBuBudgetDetails10);


                //11
                ZxBuBudgetDetails zxBuBudgetDetails11 = new ZxBuBudgetDetails();
                //序号
                zxBuBudgetDetails11.setSerialNumber("11");
                //费用名称
                zxBuBudgetDetails11.setBudgetElement("其他费用");
                //标后预算费用(元)
                zxBuBudgetDetails11.setBudgetElementFirstFree(new BigDecimal(0));
                //施工预算费用(元)
                zxBuBudgetDetails11.setBudgetElementCurrFree(new BigDecimal(0));
                //差值
                zxBuBudgetDetails11.setAudited(0);
                //费用所占建筑安装工程费比例(%)
                zxBuBudgetDetails11.setNeedDeduct(0);
                //预算书id
                zxBuBudgetDetails11.setBudgetBookID(zxBuBudgetBook.getZxBuBudgetBookId());
                //类型
                zxBuBudgetDetails11.setBudgetType("施工预算");
                //元素类型
                zxBuBudgetDetails11.setBudgetElementType("signed_others");
                //元素单位
                zxBuBudgetDetails11.setBudgetElementUnit("元");
                zxBuBudgetDetails11.setZxBuBudgetDetailsId((UuidUtil.generate()));
                zxBuBudgetDetails11.setCreateUserInfo(userKey, realName);
                zxBuBudgetDetailsMapper.insert(zxBuBudgetDetails11);

                //12
                ZxBuBudgetDetails zxBuBudgetDetails12 = new ZxBuBudgetDetails();
                //序号
                zxBuBudgetDetails12.setSerialNumber("12");
                //费用名称
                zxBuBudgetDetails12.setBudgetElement("合同收入");
                //标后预算费用(元)
                zxBuBudgetDetails12.setBudgetElementFirstFree(new BigDecimal(0));
                //施工预算费用(元)
                zxBuBudgetDetails12.setBudgetElementCurrFree(new BigDecimal(0));
                //差值
                zxBuBudgetDetails12.setAudited(0);
                //费用所占建筑安装工程费比例(%)
                zxBuBudgetDetails12.setNeedDeduct(0);
                //预算书id
                zxBuBudgetDetails12.setBudgetBookID(zxBuBudgetBook.getZxBuBudgetBookId());
                //类型
                zxBuBudgetDetails12.setBudgetType("施工预算");
                //元素类型
                zxBuBudgetDetails12.setBudgetElementType("signed_project");
                //元素单位
                zxBuBudgetDetails12.setBudgetElementUnit("元");
                zxBuBudgetDetails12.setZxBuBudgetDetailsId((UuidUtil.generate()));
                zxBuBudgetDetails12.setCreateUserInfo(userKey, realName);
                zxBuBudgetDetailsMapper.insert(zxBuBudgetDetails12);

                //13
                ZxBuBudgetDetails zxBuBudgetDetails13 = new ZxBuBudgetDetails();
                //序号
                zxBuBudgetDetails13.setSerialNumber("13");
                //费用名称
                zxBuBudgetDetails13.setBudgetElement("增收节支预算");
                //标后预算费用(元)
                zxBuBudgetDetails13.setBudgetElementFirstFree(new BigDecimal(0));
                //施工预算费用(元)
                zxBuBudgetDetails13.setBudgetElementCurrFree(new BigDecimal(0));
                //差值
                zxBuBudgetDetails13.setAudited(0);
                //费用所占建筑安装工程费比例(%)
                zxBuBudgetDetails13.setNeedDeduct(0);
                //预算书id
                zxBuBudgetDetails13.setBudgetBookID(zxBuBudgetBook.getZxBuBudgetBookId());
                //类型
                zxBuBudgetDetails13.setBudgetType("施工预算");
                //元素类型
                zxBuBudgetDetails13.setBudgetElementType("signed_others");
                //元素单位
                zxBuBudgetDetails13.setBudgetElementUnit("元");
                zxBuBudgetDetails13.setZxBuBudgetDetailsId((UuidUtil.generate()));
                zxBuBudgetDetails13.setCreateUserInfo(userKey, realName);
                zxBuBudgetDetailsMapper.insert(zxBuBudgetDetails13);



            }


            return repEntity.ok("sys.data.sava", zxBuBudgetBook);
        }
    }

    @Override
    public ResponseEntity updateZxBuBudgetBook(ZxBuBudgetBook zxBuBudgetBook) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        ZxBuBudgetBook dbZxBuBudgetBook = zxBuBudgetBookMapper.selectByPrimaryKey(zxBuBudgetBook.getZxBuBudgetBookId());
        if (dbZxBuBudgetBook != null && StrUtil.isNotEmpty(dbZxBuBudgetBook.getZxBuBudgetBookId())) {
           // 项目
           dbZxBuBudgetBook.setOrgID(zxBuBudgetBook.getOrgID());
           // 项目
           dbZxBuBudgetBook.setWorkBookID(zxBuBudgetBook.getWorkBookID());
           // 编制机构
           dbZxBuBudgetBook.setReportOrgID(zxBuBudgetBook.getReportOrgID());
           // 编制机构
           dbZxBuBudgetBook.setReportOrgName(zxBuBudgetBook.getReportOrgName());
           // 编制人
           dbZxBuBudgetBook.setReporter(zxBuBudgetBook.getReporter());
           // 编制日期
           dbZxBuBudgetBook.setReportDate(zxBuBudgetBook.getReportDate());
           // 审核人
           dbZxBuBudgetBook.setAuditor(zxBuBudgetBook.getAuditor());
           // 审核日期
           dbZxBuBudgetBook.setAuditDate(zxBuBudgetBook.getAuditDate());
           // 预算类型
           dbZxBuBudgetBook.setBudgetType(zxBuBudgetBook.getBudgetType());
           // 预算级别
           dbZxBuBudgetBook.setBudgetLevel(zxBuBudgetBook.getBudgetLevel());
           // 状态
           dbZxBuBudgetBook.setStatus(zxBuBudgetBook.getStatus());
           // 类型
           dbZxBuBudgetBook.setSystemType(zxBuBudgetBook.getSystemType());
           // null
           dbZxBuBudgetBook.setCombProp(zxBuBudgetBook.getCombProp());
           // null
           dbZxBuBudgetBook.setPp1(zxBuBudgetBook.getPp1());
           // null
           dbZxBuBudgetBook.setPp2(zxBuBudgetBook.getPp2());
           // null
           dbZxBuBudgetBook.setPp3(zxBuBudgetBook.getPp3());
           // null
           dbZxBuBudgetBook.setPp4(zxBuBudgetBook.getPp4());
           // null
           dbZxBuBudgetBook.setPp5(zxBuBudgetBook.getPp5());
           // null
           dbZxBuBudgetBook.setPp6(zxBuBudgetBook.getPp6());
           // 
           dbZxBuBudgetBook.setPp7(zxBuBudgetBook.getPp7());
           // 
           dbZxBuBudgetBook.setPp8(zxBuBudgetBook.getPp8());
           // 
           dbZxBuBudgetBook.setPp9(zxBuBudgetBook.getPp9());
           // 
           dbZxBuBudgetBook.setPp10(zxBuBudgetBook.getPp10());
           // 是否历史项目
           dbZxBuBudgetBook.setIsHistory(zxBuBudgetBook.getIsHistory());
           // 
           dbZxBuBudgetBook.setEditTime(zxBuBudgetBook.getEditTime());
           // 期次
           dbZxBuBudgetBook.setPeriod(zxBuBudgetBook.getPeriod());
           // 项目所属省份
           dbZxBuBudgetBook.setProjFea(zxBuBudgetBook.getProjFea());
           // 项目所属区域
           dbZxBuBudgetBook.setArea(zxBuBudgetBook.getArea());
           // 是否首次
           dbZxBuBudgetBook.setIsFirst(zxBuBudgetBook.getIsFirst());
           // 备注
           dbZxBuBudgetBook.setRemarks(zxBuBudgetBook.getRemarks());
           // 排序
           dbZxBuBudgetBook.setSort(zxBuBudgetBook.getSort());
           // 共通
           dbZxBuBudgetBook.setModifyUserInfo(userKey, realName);
            //删除在新增(附件)
            ZxErpFile zxErpFileSelect = new ZxErpFile();
            zxErpFileSelect.setOtherId(zxBuBudgetBook.getZxBuBudgetBookId());
            List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFileSelect);
            if(zxErpFiles != null && zxErpFiles.size()>0) {
                zxErpFileSelect.setModifyUserInfo(userKey, realName);
                zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFileSelect);
            }
            //附件list
            List<ZxErpFile> fileList = zxBuBudgetBook.getFileList();
            if(fileList != null && fileList.size()>0) {
                for(ZxErpFile zxErpFile : fileList) {
                    zxErpFile.setUid(UuidUtil.generate());
                    zxErpFile.setOtherId(zxBuBudgetBook.getZxBuBudgetBookId());
                    zxErpFile.setCreateUserInfo(userKey, realName);
                    zxErpFileMapper.insert(zxErpFile);
                }
            }
           flag = zxBuBudgetBookMapper.updateByPrimaryKey(dbZxBuBudgetBook);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.update",zxBuBudgetBook);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateZxBuBudgetBook(List<ZxBuBudgetBook> zxBuBudgetBookList) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        if (zxBuBudgetBookList != null && zxBuBudgetBookList.size() > 0) {
            for (ZxBuBudgetBook zxBuBudgetBook : zxBuBudgetBookList) {
                //删除附件
                ZxErpFile zxErpFile = new ZxErpFile();
                zxErpFile.setOtherId(zxBuBudgetBook.getZxBuBudgetBookId());
                List<ZxErpFile> zxErpFiles = zxErpFileMapper.selectByZxErpFileList(zxErpFile);
                if(zxErpFiles != null && zxErpFiles.size()>0) {
                    zxErpFile.setModifyUserInfo(userKey, realName);
                    zxErpFileMapper.batchDeleteUpdateZxErpFile(zxErpFiles, zxErpFile);
                }
                //批量删除所有明细数据
                if(NumberUtil.equals(new BigDecimal(zxBuBudgetBook.getBudgetType()),new BigDecimal(10))){
                    //标后的删除
                    ZxBuBudgetDetails zxBuBudgetDetails = new ZxBuBudgetDetails();
                    zxBuBudgetDetails.setBudgetBookID(zxBuBudgetBook.getZxBuBudgetBookId());
                    List<ZxBuBudgetDetails> zxBuBudgetDetails1 = zxBuBudgetDetailsMapper.selectByZxBuBudgetDetailsList(zxBuBudgetDetails);
                    if(zxBuBudgetDetails1 != null && zxBuBudgetDetails1.size()>0) {
                        zxBuBudgetDetails.setModifyUserInfo(userKey, realName);
                        zxBuBudgetDetailsMapper.batchDeleteUpdateZxBuBudgetDetails(zxBuBudgetDetails1, zxBuBudgetDetails);
                    }
                }else if(NumberUtil.equals(new BigDecimal(zxBuBudgetBook.getBudgetType()),new BigDecimal(20))){
                    //施工的删除
                    ZxBuBudgetBook zxBuBudgetBook1 = new ZxBuBudgetBook();
                    zxBuBudgetBook1.setOrgID(zxBuBudgetBook.getOrgID());
                    zxBuBudgetBook1.setBudgetType(200);
                    List<ZxBuBudgetBook> zxBuBudgetBooks = zxBuBudgetBookMapper.selectByZxBuBudgetBookList(zxBuBudgetBook1);
                    //删除所有期次数据
                    //删除所有期次内的详细数据
                    if(CollectionUtil.isNotEmpty(zxBuBudgetBooks)){
                        for (ZxBuBudgetBook buBudgetBook : zxBuBudgetBooks) {
                            ZxBuBudgetDetails zxBuBudgetDetails = new ZxBuBudgetDetails();
                            zxBuBudgetDetails.setBudgetBookID(buBudgetBook.getZxBuBudgetBookId());
                            List<ZxBuBudgetDetails> zxBuBudgetDetails1 = zxBuBudgetDetailsMapper.selectByZxBuBudgetDetailsList(zxBuBudgetDetails);
                            if(zxBuBudgetDetails1 != null && zxBuBudgetDetails1.size()>0) {
                                zxBuBudgetDetails.setModifyUserInfo(userKey, realName);
                                zxBuBudgetDetailsMapper.batchDeleteUpdateZxBuBudgetDetails(zxBuBudgetDetails1, zxBuBudgetDetails);
                            }
                        }
                    }
                }else if(NumberUtil.equals(new BigDecimal(zxBuBudgetBook.getBudgetType()),new BigDecimal(200))){
                    //删除当前期次下的所有详细数据
                    ZxBuBudgetDetails zxBuBudgetDetails = new ZxBuBudgetDetails();
                    zxBuBudgetDetails.setBudgetBookID(zxBuBudgetBook.getZxBuBudgetBookId());
                    List<ZxBuBudgetDetails> zxBuBudgetDetails1 = zxBuBudgetDetailsMapper.selectByZxBuBudgetDetailsList(zxBuBudgetDetails);
                    if(zxBuBudgetDetails1 != null && zxBuBudgetDetails1.size()>0) {
                        zxBuBudgetDetails.setModifyUserInfo(userKey, realName);
                        zxBuBudgetDetailsMapper.batchDeleteUpdateZxBuBudgetDetails(zxBuBudgetDetails1, zxBuBudgetDetails);
                    }
                }
            }
           ZxBuBudgetBook zxBuBudgetBook = new ZxBuBudgetBook();
           zxBuBudgetBook.setModifyUserInfo(userKey, realName);
           flag = zxBuBudgetBookMapper.batchDeleteUpdateZxBuBudgetBook(zxBuBudgetBookList, zxBuBudgetBook);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        } else {
            return repEntity.ok("sys.data.delete",zxBuBudgetBookList);
        }
    }

    // ↓↓↓----扩展-（命名格式：export导出；import导入；sync同步；）----↓↓↓


}
