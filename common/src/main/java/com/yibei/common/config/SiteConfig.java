package com.yibei.common.config;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 *
 * @author
 */

@Data
@NoArgsConstructor
@Accessors(chain = true)
@Component
@ConfigurationProperties(prefix = "yibei")
public class SiteConfig
{
    /** 项目名称 */
    private String name;

    /** 版本 */
    private String version;

    /** 版权年份 */
    private String copyrightYear;

    /** 实例演示开关 */
    private boolean demoEnabled;

    /** 获取地址开关 */
    @Getter
    private static boolean addressEnabled;

    public void setAddressEnabled(boolean addressEnabled)
    {
        SiteConfig.addressEnabled = addressEnabled;
    }

}
