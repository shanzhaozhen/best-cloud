/*
 数据库建表脚本 —— UAA 服务
*/

CREATE SCHEMA IF NOT EXISTS `uaa` DEFAULT CHARACTER SET utf8mb4;
USE
`uaa` ;

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- 用户表
-- ----------------------------
DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user
(
    id                      BIGINT       NOT NULL COMMENT '主键ID',
    username                VARCHAR(30)  NOT NULL UNIQUE COMMENT '用户名',
    password                VARCHAR(255) NOT NULL COMMENT '密码',
    account_non_expired     bit(1)       NOT NULL COMMENT '账户是否过期,过期无法验证',
    account_non_locked      bit(1)       NOT NULL COMMENT '指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证',
    credentials_non_expired bit(1)       NOT NULL COMMENT '指示是否已过期的用户的凭据(密码),过期的凭据防止认证',
    enabled                 bit(1)       NOT NULL COMMENT '是否被禁用,禁用的用户不能身份验证',
    name                    VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    nickname                VARCHAR(30) NULL DEFAULT NULL COMMENT '昵称',
    sex                     INT NULL DEFAULT NULL COMMENT '性别',
    birthday                DATE NULL DEFAULT NULL COMMENT '生日',
    avatar                  VARCHAR(255) NULL DEFAULT NULL COMMENT '头像',
    email                   VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    phone_number            VARCHAR(11) NULL DEFAULT NULL COMMENT '手机号码',
    address_code            VARCHAR(255) NULL DEFAULT NULL COMMENT '地址编号',
    detailed_address        VARCHAR(255) NULL DEFAULT NULL COMMENT '详细地址',
    introduction            VARCHAR(255) NULL DEFAULT NULL COMMENT '个人介绍',
    created_by              BIGINT NULL DEFAULT NULL COMMENT '创建人',
    created_date            datetime NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by        BIGINT NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date      datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (id)
);


-- ----------------------------
-- 用户信息表
-- ----------------------------
DROP TABLE IF EXISTS sys_user_info;

CREATE TABLE sys_user_info
(
    id                 VARCHAR(30) NOT NULL COMMENT '主键ID',
    pid                VARCHAR(30) NOT NULL COMMENT '关联的用户ID',
    name               VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
    nickname           VARCHAR(30) NULL DEFAULT NULL COMMENT '昵称',
    sex                INT NULL DEFAULT NULL COMMENT '性别',
    birthday           DATE NULL DEFAULT NULL COMMENT '生日',
    avatar             VARCHAR(255) NULL DEFAULT NULL COMMENT '头像',
    email              VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    phone_number       VARCHAR(11) NULL DEFAULT NULL COMMENT '手机号码',
    address_code       VARCHAR(255) NULL DEFAULT NULL COMMENT '地址编号',
    detailed_address   VARCHAR(255) NULL DEFAULT NULL COMMENT '详细地址',
    introduction       VARCHAR(255) NULL DEFAULT NULL COMMENT '个人介绍',
    created_by         BIGINT NULL DEFAULT NULL COMMENT '创建人',
    created_date       datetime NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by   BIGINT NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (id)
);


-- ----------------------------
-- 角色表
-- ----------------------------
DROP TABLE IF EXISTS sys_role;

CREATE TABLE sys_role
(
    id                 VARCHAR(30) NOT NULL COMMENT '主键ID',
    name               VARCHAR(30) NOT NULL COMMENT '名称',
    code               VARCHAR(30) NOT NULL UNIQUE COMMENT '角色编码',
    description        VARCHAR(255) COMMENT '描述',
    created_by         BIGINT NULL DEFAULT NULL COMMENT '创建人',
    created_date       datetime NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by   BIGINT NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (id)
);

-- ----------------------------
-- 菜单表
-- ----------------------------
DROP TABLE IF EXISTS sys_menu;

CREATE TABLE sys_menu
(
    id                    BIGINT      NOT NULL COMMENT '主键ID',
    name                  VARCHAR(30) NOT NULL COMMENT '菜单名称',
    locale                VARCHAR(255) COMMENT '菜单名称（本地化）',
    path                  VARCHAR(255) COMMENT '菜单路由',
    pid                   BIGINT NULL DEFAULT NULL COMMENT '上级ID',
    icon                  VARCHAR(255) COMMENT '图标',
    priority              INT(11) NULL DEFAULT NULL COMMENT '排序等级',
    hide_in_menu          bit(1)      NOT NULL DEFAULT 0 COMMENT '是否隐藏菜单',
    hide_children_in_menu bit(1)      NOT NULL DEFAULT 0 COMMENT '是否隐藏子节点',
    props                 VARCHAR(255) COMMENT '参数',
    description           VARCHAR(255) COMMENT '菜单描述',
    created_by            BIGINT NULL DEFAULT NULL COMMENT '创建人',
    created_date          datetime NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by      BIGINT NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date    datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (id)
);


-- ----------------------------
-- 权限表
-- ----------------------------
DROP TABLE IF EXISTS sys_permission;

CREATE TABLE sys_permission
(
    id                 VARCHAR(30) NOT NULL COMMENT '主键ID',
    name               VARCHAR(30) NOT NULL COMMENT '权限名称',
    path               VARCHAR(255) COMMENT '权限路由',
    type               INT(11) NOT NULL COMMENT '权限类型',
    pid                VARCHAR(30) NULL DEFAULT NULL COMMENT '上级ID',
    priority           INT(11) NULL DEFAULT NULL COMMENT '排序等级',
    description        VARCHAR(255) COMMENT '权限描述',
    created_by         BIGINT NULL DEFAULT NULL COMMENT '创建人',
    created_date       datetime NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by   BIGINT NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (id)
);

-- ----------------------------
-- 用户-角色关系表
-- ----------------------------
DROP TABLE IF EXISTS sys_user_role;

CREATE TABLE sys_user_role
(
    id                 VARCHAR(30) NOT NULL COMMENT '主键ID',
    user_id            BIGINT      NOT NULL COMMENT '用户ID',
    role_id            BIGINT      NOT NULL COMMENT '角色ID',
    created_by         BIGINT NULL DEFAULT NULL COMMENT '创建人',
    created_date       datetime NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by   BIGINT NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (id)
);

-- ----------------------------
-- 角色-菜单关系表
-- ----------------------------
DROP TABLE IF EXISTS sys_role_menu;

CREATE TABLE sys_role_menu
(
    id                 VARCHAR(30) NOT NULL COMMENT '主键ID',
    role_id            BIGINT      NOT NULL COMMENT '角色ID',
    menu_id            BIGINT      NOT NULL COMMENT '权限ID',
    created_by         BIGINT NULL DEFAULT NULL COMMENT '创建人',
    created_date       datetime NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by   BIGINT NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (id)
);

-- ----------------------------
-- 角色-权限关系表
-- ----------------------------
DROP TABLE IF EXISTS sys_role_permission;

CREATE TABLE sys_role_permission
(
    id                 VARCHAR(30) NOT NULL COMMENT '主键ID',
    role_id            BIGINT      NOT NULL COMMENT '角色ID',
    permission_id      VARCHAR(30) NOT NULL COMMENT '权限ID',
    created_by         BIGINT NULL DEFAULT NULL COMMENT '创建人',
    created_date       datetime NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by   BIGINT NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (id)
);

-- ----------------------------
-- 部门表
-- ----------------------------
DROP TABLE IF EXISTS sys_department;

CREATE TABLE sys_department
(
    id                 VARCHAR(30) NOT NULL COMMENT '主键ID',
    pid                VARCHAR(30) NULL DEFAULT NULL COMMENT '上级ID',
    name               VARCHAR(30) NOT NULL COMMENT '部门名称',
    code               VARCHAR(30) NOT NULL COMMENT '部门编码',
    priority           INT(11) NULL DEFAULT NULL COMMENT '排序等级',
    description        VARCHAR(255) COMMENT '部门描述',
    created_by         BIGINT NULL DEFAULT NULL COMMENT '创建人',
    created_date       datetime NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by   BIGINT NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (id)
);

-- ----------------------------
-- 部门-用户关系表
-- ----------------------------
DROP TABLE IF EXISTS sys_department_user;

CREATE TABLE sys_department_user
(
    id                 VARCHAR(30) NOT NULL COMMENT '主键ID',
    department_id      VARCHAR(30) NOT NULL COMMENT '部门ID',
    user_id            BIGINT      NOT NULL COMMENT '用户ID',
    created_by         BIGINT NULL DEFAULT NULL COMMENT '创建人',
    created_date       datetime NULL DEFAULT NULL COMMENT '创建时间',
    last_modified_by   BIGINT NULL DEFAULT NULL COMMENT '修改人',
    last_modified_date datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (id)
);


SET
FOREIGN_KEY_CHECKS = 1;

INSERT INTO `sys_user`(`id`, `username`, `password`, `account_non_expired`, `account_non_locked`,
                       `credentials_non_expired`, `enabled`, `name`, `nickname`, `sex`, `birthday`, `avatar`, `email`,
                       `phone_number`, `address_code`, `detailed_address`, `introduction`, `created_by`, `created_date`,
                       `last_modified_by`, `last_modified_date`)
VALUES (1378349825706942465, 'admin', '$2a$10$ZHloNREZMCnmeSqGlPL4tudSt4QdR4JnFwODJnVsXoWoxAkNMaqda', b'1', b'1', b'1',
        b'1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

