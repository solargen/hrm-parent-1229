package cn.itsource.hrm.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author solargen
 * @version v1.0.0
 * @date 2020-06-17
 */
@ConfigurationProperties(prefix = "hrm.swagger")
public class SwaggerProperties {

    private String packageName = "cn.itsource.hrm.controller";
    private String title;
    private String description;
    private String name = "solargen";
    private String url = "";
    private String email = "solargen@itsource.cn";
    private String version = "1.0";

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
