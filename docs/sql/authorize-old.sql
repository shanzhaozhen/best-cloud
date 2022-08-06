/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : localhost:3306
 Source Schema         : authorize

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 12/11/2021 16:24:52
*/

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

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
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oauth2_registered_client
-- client用户表
-- ----------------------------
DROP TABLE IF EXISTS `oauth2_registered_client`;
CREATE TABLE `oauth2_registered_client`
(
    `id`                            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `client_id`                     varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `client_id_issued_at`           timestamp                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `client_secret`                 varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `client_secret_expires_at`      timestamp NULL DEFAULT NULL,
    `client_name`                   varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL,
    `client_authentication_methods` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `authorization_grant_types`     varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `redirect_uris`                 varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
    `scopes`                        varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `client_settings`               varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `token_settings`                varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET
FOREIGN_KEY_CHECKS = 1;


INSERT INTO `authorize`.`oauth2_registered_client` (`id`, `client_id`, `client_id_issued_at`, `client_secret`, `client_secret_expires_at`, `client_name`, `client_authentication_methods`, `authorization_grant_types`, `redirect_uris`, `scopes`, `client_settings`, `token_settings`) VALUES ('0088119e-bdba-4ccd-8f7f-7c7faa0d2479', 'auth', '2021-12-01 16:06:09', '{bcrypt}$2a$10$IGy1PVV56E71GSce5CHwLO5flTo3C0hGZ.VMa9TKKeCPhbp4Bm0u2', NULL, '0088119e-bdba-4ccd-8f7f-7c7faa0d2479', 'client_secret_basic', 'refresh_token,client_credentials,account,authorization_code,account', 'http://127.0.0.1:8080/authorized,http://www.baidu.com,http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc', 'all,openid,message.read,message.write', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.client.require-proof-key\":false,\"settings.client.require-authorization-consent\":true}', '{\"@class\":\"java.util.Collections$UnmodifiableMap\",\"settings.token.reuse-refresh-tokens\":true,\"settings.token.id-token-signature-algorithm\":[\"org.springframework.security.oauth2.jose.jws.SignatureAlgorithm\",\"RS256\"],\"settings.token.access-token-time-to-live\":[\"java.time.Duration\",3600.000000000],\"settings.token.refresh-token-time-to-live\":[\"java.time.Duration\",259200.000000000]}');
