package com.apih5.mybatis.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.apih5.mybatis.pojo.Apih5Content;
import com.apih5.mybatis.pojo.TemplateConfig;

/**
 * 文章mapper
 */
@Repository
public interface ArticleMapper {

    /**
     * 保存文章配置
     * @param tc
     */
    void saveTemplateConfig(TemplateConfig tc);

    /**
     * 修改文章配置
     * @param tc
     */
    void editTemplateConfig(TemplateConfig tc);

    /**
     * 根据id删除文章配置
     * @param id
     */
    void removeTemplateConfig(String id);

    /**
     * 列出所有文章配置
     * @return
     */
    List<TemplateConfig> listAllTemplateConfig(int userId);

    /**
     * 根据id获得文章配置
     * @param id
     * @return
     */
    TemplateConfig getTemplateConfig(String id);

    /**
     * 列出所有查询出的文章
     * @return
     */
    List<Apih5Content> listAllApih5Contents(int userId);

    /**
     * 根据id获得指定文章
     * @param id
     * @return
     */
    Apih5Content getApih5Content(String id);

    /**
     * 添加文章
     * @param cc
     */
    void saveApih5Content(Apih5Content cc);

    /**
     * 批量添加文章
     * @param cList
     */
    void batchSaveApih5Content(List<Apih5Content> cList);

    /**
     * 获取所有数据库文章urls
     * @return
     */
    List<String> fetchAllArticleUrls();
    
    /**
     * 判断是否存在url
     * @return
     */
    Integer isExistUrl(Apih5Content content);

    /**
     * 判断文章配置否存在
     * @param templateConfig
     * @return
     */
    int checkTemplateConfigExists(TemplateConfig templateConfig);

    /**
     * 得到文章数量
     * @return
     */
    int getApih5ContentSize(int userId);

    /**
     * 根据用户id删除文章配置
     * @param id
     */
    void removeTemplateConfigByUserId(int id);

    /**
     * 根据用户id删除文章
     * @param id
     */
    void removeApih5ContentByUserId(int id);

    /**
     * 得到文章配置表里所有不重复的userId
     * @return
     */
    List<Integer> fetchAllUserIdFromTemplateConfig();
}