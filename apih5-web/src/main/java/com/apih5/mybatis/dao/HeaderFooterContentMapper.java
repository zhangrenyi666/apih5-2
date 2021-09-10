package com.apih5.mybatis.dao;

import org.springframework.stereotype.Repository;

import com.apih5.mybatis.pojo.SysHeaderFooterContent;

/**
 * 页眉页脚管理mapper
 */
@Repository
public interface HeaderFooterContentMapper {

    /**
     * 获得页眉页脚内容
     * @return
     */
    SysHeaderFooterContent getHeaderFooterContent();

    /**
     * 修改页眉页脚内容
     * @param sysHeaderFooterContent
     */
    void editHeaderFooterContent(SysHeaderFooterContent sysHeaderFooterContent);
}