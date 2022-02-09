package org.shanzhaozhen.common.mybatis.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.shanzhaozhen.common.web.utils.JwtUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        Long userId = JwtUtils.getUserIdWithoutError();
        this.strictInsertFill(metaObject, "createdDate", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "createdBy", () -> userId, Long.class);
        this.strictInsertFill(metaObject, "lastModifiedDate", LocalDateTime::now, LocalDateTime.class);
        this.strictInsertFill(metaObject, "lastModifiedBy", () -> userId, Long.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Long userId = JwtUtils.getUserIdWithoutError();
        this.strictUpdateFill(metaObject, "createdDate", LocalDateTime::now, LocalDateTime.class);
        this.strictUpdateFill(metaObject, "createdBy", () -> userId, Long.class);
        this.strictUpdateFill(metaObject, "lastModifiedDate", LocalDateTime::now, LocalDateTime.class);
        this.strictUpdateFill(metaObject, "lastModifiedBy", () -> userId, Long.class);
    }
}
