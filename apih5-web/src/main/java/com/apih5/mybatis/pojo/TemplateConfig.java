package com.apih5.mybatis.pojo;

//import org.hibernate.validator.constraints.NotBlank;

/**
 * 文章配置entity
 */
public class TemplateConfig {

    private String id; // 文章配置id

//    @NotBlank(message = "{url.not.null}")
    private String url; // 文章配置url

//    @NotBlank(message = "{firstLevelPattern.not.null}")
    private String firstLevelPattern; // url指定区域pattern

//    @NotBlank(message = "{titlePattern.not.null}")
    private String titlePattern; // 子页面标题pattern

//    @NotBlank(message = "{timePattern.not.null}")
    private String timePattern; // 子页面发布时间pattern

//    @NotBlank(message = "{contentPattern.not.null}")
    private String contentPattern; // 子页面内容pattern

    private int version; // 版本号（乐观锁）

    private int userId; // 关联的用户id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFirstLevelPattern() {
        return firstLevelPattern;
    }

    public void setFirstLevelPattern(String firstLevelPattern) {
        this.firstLevelPattern = firstLevelPattern;
    }

    public String getTitlePattern() {
        return titlePattern;
    }

    public void setTitlePattern(String titlePattern) {
        this.titlePattern = titlePattern;
    }

    public String getTimePattern() {
        return timePattern;
    }

    public void setTimePattern(String timePattern) {
        this.timePattern = timePattern;
    }

    public String getContentPattern() {
        return contentPattern;
    }

    public void setContentPattern(String contentPattern) {
        this.contentPattern = contentPattern;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
