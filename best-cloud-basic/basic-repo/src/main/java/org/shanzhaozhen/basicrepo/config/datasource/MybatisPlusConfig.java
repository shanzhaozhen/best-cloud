package org.shanzhaozhen.basicrepo.config.datasource;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
@MapperScan("org.shanzhaozhen.basicrepo.mapper")
public class MybatisPlusConfig {

    private final CustomMetaObjectHandler customMetaObjectHandler;

    public MybatisPlusConfig(CustomMetaObjectHandler customMetaObjectHandler) {
        this.customMetaObjectHandler = customMetaObjectHandler;
    }

    /**
     * 配置审计功能
     * @return
     */
    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(customMetaObjectHandler);
        return globalConfig;
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        // .setLimit(你的最大单页限制数量，默认 500 条，小于 0 如 -1 不受限制);
        return new PaginationInterceptor();
    }

}
