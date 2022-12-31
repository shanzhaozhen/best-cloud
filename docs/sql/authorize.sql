/*
 数据库建表脚本 —— UAA 服务
*/
CREATE SCHEMA IF NOT EXISTS `authorize` DEFAULT CHARACTER SET utf8mb4;

USE `authorize` ;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- 用户表
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_user`;
CREATE TABLE `oauth2_user`
(
    `id`                      VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键ID',
    `username`                VARCHAR(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL UNIQUE COMMENT '用户名',
    `password`                VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
    `phone`                   VARCHAR(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号码',
    `account_non_expired`     bit(1)                                                        NOT NULL COMMENT '账户是否过期,过期无法验证',
    `account_non_locked`      bit(1)                                                        NOT NULL COMMENT '指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证',
    `credentials_non_expired` bit(1)                                                        NOT NULL COMMENT '指示是否已过期的用户的凭据(密码),过期的凭据防止认证',
    `enabled`                 bit(1)                                                        NOT NULL COMMENT '是否被禁用,禁用的用户不能身份验证',
    `version`                 INT NULL DEFAULT NULL COMMENT '版本号',
    `created_by`              VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `created_date`            datetime NULL DEFAULT NULL COMMENT '创建时间',
    `last_modified_by`        VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
    `last_modified_date`      datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'oauth2用户表'
  ROW_FORMAT = Dynamic;


-- ----------------------------
-- 用户第三方授权表
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_user_social`;
CREATE TABLE `oauth2_user_social`
(
    `id`                 VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键ID',
    `user_id`            VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户ID',
    `identity_type`       VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL UNIQUE COMMENT '第三方类型',
    `identifier`         VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '第三方的唯一标识',
    `bind_date`          datetime NULL DEFAULT NULL COMMENT '绑定时间',
    `name`               VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
    `avatar_url`         VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
    `other`              json NULL DEFAULT NULL COMMENT '其他信息',
    `version`            INT NULL DEFAULT NULL COMMENT '版本号',
    `created_by`         VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `created_date`       datetime NULL DEFAULT NULL COMMENT '创建时间',
    `last_modified_by`   VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
    `last_modified_date` datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'oauth2用户授权记录表'
  ROW_FORMAT = Dynamic;


-- ----------------------------
-- github 用户表
-- ----------------------------
DROP TABLE IF EXISTS oauth2_user_github;
CREATE TABLE oauth2_user_github
(
    id                 VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键ID',
    username           VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL UNIQUE COMMENT '用户名',
    user_id            VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户ID',
    bind_date          datetime NULL DEFAULT NULL COMMENT '绑定时间',
    login              VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登陆名',
    github_id          VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'github用户ID',
    node_id            VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '节点ID',
    avatar_url         VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
    email              VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
    name               VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
    other              json NULL DEFAULT NULL COMMENT '其他信息',
    version            INT NULL DEFAULT NULL COMMENT '版本号',
    created_by         VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    created_date       datetime NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by   VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'oauth2绑定github信息表'
  ROW_FORMAT = Dynamic;


-- ----------------------------
-- 用户信息表
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_user_info`;
CREATE TABLE `oauth2_user_info`
(
    id                 VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键ID',
    pid                VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '关联的用户ID',
    name               VARCHAR(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
    nickname           VARCHAR(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
    sex                INT NULL DEFAULT NULL COMMENT '性别',
    birthday           DATE NULL DEFAULT NULL COMMENT '生日',
    avatar             VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
    email              VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
    phone_number       VARCHAR(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号码',
    address_code       VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址编号',
    detailed_address   VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详细地址',
    introduction       VARCHAR(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '个人介绍',
    version            INT NULL DEFAULT NULL COMMENT '版本号',
    created_by         VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    created_date       datetime NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by   VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'oauth2用户信息表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth2_authorization
-- 授权信息表
-- 当前客户端的授权范围，但不是所有应用的范围
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_authorization`;
CREATE TABLE `oauth2_authorization`
(
    `id`                            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `registered_client_id`          varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `principal_name`                varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `authorization_grant_type`      varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `attributes`                    json                                                           DEFAULT NULL,
    `state`                         varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL,
    `authorization_code_value`      varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `authorization_code_issued_at`  timestamp NULL DEFAULT NULL,
    `authorization_code_expires_at` timestamp NULL DEFAULT NULL,
    `authorization_code_metadata`   json                                                           DEFAULT NULL,
    `access_token_value`            varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `access_token_issued_at`        timestamp NULL DEFAULT NULL,
    `access_token_expires_at`       timestamp NULL DEFAULT NULL,
    `access_token_metadata`         varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `access_token_type`             varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL,
    `access_token_scopes`           varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `oidc_id_token_value`           varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `oidc_id_token_issued_at`       timestamp NULL DEFAULT NULL,
    `oidc_id_token_expires_at`      timestamp NULL DEFAULT NULL,
    `oidc_id_token_metadata`        json                                                           DEFAULT NULL,
    `refresh_token_value`           varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `refresh_token_issued_at`       timestamp NULL DEFAULT NULL,
    `refresh_token_expires_at`      timestamp NULL DEFAULT NULL,
    `refresh_token_metadata`        json                                                           DEFAULT NULL,
    `version`                       INT NULL DEFAULT NULL COMMENT '版本号',
    `created_by`                    VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `created_date`                  datetime NULL DEFAULT NULL COMMENT '创建时间',
    `last_modified_by`              VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
    `last_modified_date`            datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '授权信息表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth2_authorization_consent
-- 授权信息表
-- 您应用的所有范围
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_authorization_consent`;
CREATE TABLE `oauth2_authorization_consent`
(
    `id`                   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT 'ID',
    `registered_client_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `principal_name`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `authorities`          varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `version`              INT NULL DEFAULT NULL COMMENT '版本号',
    `created_by`           VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `created_date`         datetime NULL DEFAULT NULL COMMENT '创建时间',
    `last_modified_by`     VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
    `last_modified_date`   datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '授权信息表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth2_registered_client
-- client用户表
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_registered_client`;
CREATE TABLE `oauth2_registered_client`
(
    `id`                            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `client_id`                     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL UNIQUE COMMENT 'oauth2客户端id',
    `client_id_issued_at`           timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '客户端创建时间',
    `client_secret`                 varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端密码',
    `client_secret_expires_at`      timestamp NULL DEFAULT NULL COMMENT '客户端密码过期时间',
    `client_name`                   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端名称',
    `client_authentication_methods` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端认证方式',
    `authorization_grant_types`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端授权方式',
    `redirect_uris`                 varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端允许重定向的uri',
    `scopes`                        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端允许的scope 来自role表',
    `description`                   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
    `version`                       INT NULL DEFAULT NULL COMMENT '版本号',
    `created_by`                    VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `created_date`                  datetime NULL DEFAULT NULL COMMENT '创建时间',
    `last_modified_by`              VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
    `last_modified_date`            datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'oauth2客户端基础信息表'
  ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth2_client_settings
-- oauth2客户端配置
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_client_settings`;
CREATE TABLE `oauth2_client_settings`
(
    `id`                                              varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `registered_client_id`                            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'oauth2客户端id',
    `require_proof_key`                               tinyint(1) NULL DEFAULT 0 COMMENT '客户端是否需要证明密钥',
    `require_authorization_consent`                   tinyint(1) NULL DEFAULT 0 COMMENT '客户端是否需要授权确认页面',
    `jwk_set_url`                                     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'jwkSet url',
    `token_endpoint_authentication_signing_algorithm` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支持的签名算法',
    `version`                                         INT NULL DEFAULT NULL COMMENT '版本号',
    `created_by`                                      VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `created_date`                                    datetime NULL DEFAULT NULL COMMENT '创建时间',
    `last_modified_by`                                VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
    `last_modified_date`                              datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'oauth2客户端配置'
  ROW_FORMAT = Dynamic;



-- ----------------------------
-- Table structure for oauth2_token_settings
-- oauth2客户端的token配置项
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_token_settings`;
CREATE TABLE `oauth2_token_settings`
(
    `id`                           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `registered_client_id`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'oauth2客户端id',
    `access_token_time_to_live`    bigint NULL DEFAULT NULL COMMENT 'access_token 有效时间',
    `access_token_format`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'token 格式  jwt、opaque',
    `reuse_refresh_tokens`         tinyint(1) NULL DEFAULT 1 COMMENT '是否重用 refresh_token',
    `refresh_token_time_to_live`   bigint NULL DEFAULT NULL COMMENT 'refresh_token 有效时间',
    `id_token_signature_algorithm` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'oidc id_token 签名算法',
    `version`                      INT NULL DEFAULT NULL COMMENT '版本号',
    `created_by`                   VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建人',
    `created_date`                 datetime NULL DEFAULT NULL COMMENT '创建时间',
    `last_modified_by`             VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '修改人',
    `last_modified_date`           datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'oauth2客户端的token配置项'
  ROW_FORMAT = Dynamic;


SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `oauth2_user`(`id`, `username`, `password`, `account_non_expired`, `account_non_locked`,
                       `credentials_non_expired`, `enabled`, `created_by`, `created_date`,
                       `last_modified_by`, `last_modified_date`)
VALUES (1378349825706942465, 'admin', '$2a$10$ZHloNREZMCnmeSqGlPL4tudSt4QdR4JnFwODJnVsXoWoxAkNMaqda', b'1', b'1', b'1',
        b'1', NULL, NULL, NULL, NULL);

