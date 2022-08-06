CREATE SCHEMA IF NOT EXISTS `authorize` DEFAULT CHARACTER SET utf8mb4;

USE
`authorize`;

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

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
    `attributes`                    varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `state`                         varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `authorization_code_value`      blob NULL,
    `authorization_code_issued_at`  timestamp NULL DEFAULT NULL,
    `authorization_code_expires_at` timestamp NULL DEFAULT NULL,
    `authorization_code_metadata`   varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `access_token_value`            blob NULL,
    `access_token_issued_at`        timestamp NULL DEFAULT NULL,
    `access_token_expires_at`       timestamp NULL DEFAULT NULL,
    `access_token_metadata`         varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `access_token_type`             varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `access_token_scopes`           varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `oidc_id_token_value`           blob NULL,
    `oidc_id_token_issued_at`       timestamp NULL DEFAULT NULL,
    `oidc_id_token_expires_at`      timestamp NULL DEFAULT NULL,
    `oidc_id_token_metadata`        varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `refresh_token_value`           blob NULL,
    `refresh_token_issued_at`       timestamp NULL DEFAULT NULL,
    `refresh_token_expires_at`      timestamp NULL DEFAULT NULL,
    `refresh_token_metadata`        varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
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
    `registered_client_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `principal_name`       varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `authorities`          varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    PRIMARY KEY (`registered_client_id`, `principal_name`) USING BTREE
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
    `id`                       varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `client_id`                varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL UNIQUE COMMENT 'oauth2客户端id',
    `client_id_issued_at`      timestamp                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '客户端创建时间',
    `client_secret`            varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端密码',
    `client_secret_expires_at` timestamp NULL DEFAULT NULL COMMENT '客户端密码过期时间',
    `client_name`              varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端名称',
    `version`                  INT NULL DEFAULT NULL COMMENT '版本号',
    `created_by`               VARCHAR(20) NULL DEFAULT NULL COMMENT '创建人',
    `created_date`             datetime NULL DEFAULT NULL COMMENT '创建时间',
    `last_modified_by`         VARCHAR(20) NULL DEFAULT NULL COMMENT '修改人',
    `last_modified_date`       datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'oauth2客户端基础信息表'
  ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for oauth2_client_auth_method
-- oauth2客户端认证方式表
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_client_authentication_method`;
CREATE TABLE `oauth2_client_authentication_method`
(
    `id`                           varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `registered_client_id`         varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'oauth2客户端id',
    `client_authentication_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端认证方式',
    `version`                      INT NULL DEFAULT NULL COMMENT '版本号',
    `created_by`                   VARCHAR(20) NULL DEFAULT NULL COMMENT '创建人',
    `created_date`                 datetime NULL DEFAULT NULL COMMENT '创建时间',
    `last_modified_by`             VARCHAR(20) NULL DEFAULT NULL COMMENT '修改人',
    `last_modified_date`           datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'oauth2客户端认证方式表'
  ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for oauth2_grant_type
-- oauth2客户端授权方式表
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_authorization_grant_type`;
CREATE TABLE `oauth2_authorization_grant_type`
(
    `id`                   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `registered_client_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'oauth2客户端id',
    `grant_type_name`      varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端授权方式',
    `version`              INT NULL DEFAULT NULL COMMENT '版本号',
    `created_by`           VARCHAR(20) NULL DEFAULT NULL COMMENT '创建人',
    `created_date`         datetime NULL DEFAULT NULL COMMENT '创建时间',
    `last_modified_by`     VARCHAR(20) NULL DEFAULT NULL COMMENT '修改人',
    `last_modified_date`   datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'oauth2客户端授权方式表'
  ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for oauth2_redirect_uri
-- oauth2客户端重定向uri表
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_redirect_uri`;
CREATE TABLE `oauth2_redirect_uri`
(
    `id`                   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `registered_client_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'oauth2客户端id',
    `redirect_uri`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端允许重定向的uri',
    `version`              INT NULL DEFAULT NULL COMMENT '版本号',
    `created_by`           VARCHAR(20) NULL DEFAULT NULL COMMENT '创建人',
    `created_date`         datetime NULL DEFAULT NULL COMMENT '创建时间',
    `last_modified_by`     VARCHAR(20) NULL DEFAULT NULL COMMENT '修改人',
    `last_modified_date`   datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'oauth2客户端重定向uri表'
  ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for oauth2_scope
-- oauth2客户端授权范围
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_scope`;
CREATE TABLE `oauth2_scope`
(
    `id`                   varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `registered_client_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'oauth2客户端id',
    `scope`                varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '客户端允许的scope 来自role表',
    `description`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `version`              INT NULL DEFAULT NULL COMMENT '版本号',
    `created_by`           VARCHAR(20) NULL DEFAULT NULL COMMENT '创建人',
    `created_date`         datetime NULL DEFAULT NULL COMMENT '创建时间',
    `last_modified_by`     VARCHAR(20) NULL DEFAULT NULL COMMENT '修改人',
    `last_modified_date`   datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'oauth2客户端授权范围'
  ROW_FORMAT = Dynamic;


-- ----------------------------
-- Table structure for oauth2_client_settings
-- oauth2客户端配置
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_client_settings`;
CREATE TABLE `oauth2_client_settings`
(
    `id`                            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ID',
    `registered_client_id`          varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'oauth2客户端id',
    `require_proof_key`             tinyint(1) NULL DEFAULT 0 COMMENT '客户端是否需要证明密钥',
    `require_authorization_consent` tinyint(1) NULL DEFAULT 0 COMMENT '客户端是否需要授权确认页面',
    `jwk_set_url`                   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'jwkSet url',
    `signing_algorithm`             varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支持的签名算法',
    `version`                       INT NULL DEFAULT NULL COMMENT '版本号',
    `created_by`                    VARCHAR(20) NULL DEFAULT NULL COMMENT '创建人',
    `created_date`                  datetime NULL DEFAULT NULL COMMENT '创建时间',
    `last_modified_by`              VARCHAR(20) NULL DEFAULT NULL COMMENT '修改人',
    `last_modified_date`            datetime NULL DEFAULT NULL COMMENT '修改时间',
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
    `token_format`                 varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'token 格式  jwt、opaque',
    `reuse_refresh_tokens`         tinyint(1) NULL DEFAULT 1 COMMENT '是否重用 refresh_token',
    `refresh_token_time_to_live`   bigint NULL DEFAULT NULL COMMENT 'refresh_token 有效时间',
    `id_token_signature_algorithm` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'oidc id_token 签名算法',
    `version`                      INT NULL DEFAULT NULL COMMENT '版本号',
    `created_by`                   VARCHAR(20) NULL DEFAULT NULL COMMENT '创建人',
    `created_date`                 datetime NULL DEFAULT NULL COMMENT '创建时间',
    `last_modified_by`             VARCHAR(20) NULL DEFAULT NULL COMMENT '修改人',
    `last_modified_date`           datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = 'oauth2客户端的token配置项'
  ROW_FORMAT = Dynamic;

SET
FOREIGN_KEY_CHECKS = 1;
