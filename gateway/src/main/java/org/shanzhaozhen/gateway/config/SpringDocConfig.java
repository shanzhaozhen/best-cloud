package org.shanzhaozhen.gateway.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class SpringDocConfig {

    private final SwaggerUiConfigParameters swaggerUiConfigParameters;
    private final RouteDefinitionLocator locator;

    @Bean
    @Lazy(false)
    public List<GroupedOpenApi> apis() {
        List<GroupedOpenApi> groups = new ArrayList<>();
        List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();

        for (RouteDefinition definition : definitions) {
            log.info("id: {}  {}", definition.getId(), definition.getUri().toString());
        }

        Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> urls = swaggerUiConfigParameters.getUrls();

        definitions.stream().filter(routeDefinition -> !routeDefinition.getId().startsWith("ReactiveCompositeDiscoveryClient_") && !"openapi".equals(routeDefinition.getId())).forEach(routeDefinition -> {
//            String name = routeDefinition.getId().replaceAll("-service", "");
            String name = routeDefinition.getId();
//            swaggerUiConfigParameters.addGroup(name);
            GroupedOpenApi build = GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(name).build();
            groups.add(build);
            urls.add(new AbstractSwaggerUiConfigProperties.SwaggerUrl("/v3/api-docs/" + name, name, name));
//            swaggerUiConfigParameters.addUrl(name);
        });
        swaggerUiConfigParameters.setUrls(urls);
        return groups;
    }

}
