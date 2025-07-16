-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: 47.95.214.75    Database: flexi_admin
-- ------------------------------------------------------
-- Server version	8.4.5

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

--
-- Table structure for table `bas_organization`
--

DROP TABLE IF EXISTS `bas_organization`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bas_organization`
(
    `id`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `parent_id`     varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '父类机构ID',
    `name`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机构名称',
    `logo`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `product_id`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '产品ID',
    `link_man`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '联系人',
    `link_tel`      varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '联系电话',
    `link_email`    varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '联系邮箱',
    `address`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '所在地',
    `used_end_time` datetime                                                      DEFAULT NULL COMMENT '使用截止时间',
    `level`         int                                                           DEFAULT NULL COMMENT '等级',
    `path`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路径',
    `type`          int                                                           DEFAULT NULL COMMENT '类型',
    `memo`          varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
    `is_system`     bit(1)                                                        DEFAULT b'0' COMMENT '是否总部',
    `is_saas`       bit(1)                                                        DEFAULT NULL COMMENT '是否saas',
    `is_enabled`    bit(1)                                                        DEFAULT NULL COMMENT '是否可用',
    `sys_code`      varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户编码',
    `created_by`    varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '创建者',
    `created`       datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `modified_by`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '更新者',
    `modified`      datetime                                                      DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='履约端：机构管理';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bas_organization`
--

LOCK TABLES `bas_organization` WRITE;
/*!40000 ALTER TABLE `bas_organization`
    DISABLE KEYS */;
INSERT INTO `bas_organization`
VALUES ('1785229829122285568', NULL, 'pure-admin科技有限公司',
        'https://hk-oss.hb0730.me/Y4E3RFXLbAgS-Bh_1715311088593.png', '1785229367733444609', '测试联系人',
        '13111111111', '', '', NULL, 1, '1785229829122285568', 1, '', _binary '', _binary '', _binary '', 'PA001',
        'superadmin', '2024-04-30 16:48:54', 'superadmin', '2024-05-10 11:18:13');
/*!40000 ALTER TABLE `bas_organization`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_notice`
--

DROP TABLE IF EXISTS `sys_notice`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_notice`
(
    `id`                varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `title`             varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
    `content`           text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci        NOT NULL COMMENT '内容',
    `notice_time_start` datetime                                                      DEFAULT NULL COMMENT '公告日期',
    `notice_time_end`   datetime                                                      DEFAULT NULL COMMENT '公共结束时间',
    `is_enabled`        bit(1)                                                        DEFAULT b'1' COMMENT '是否启用',
    `org_id`            varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '网点ID',
    `created`           datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `created_by`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '创建者',
    `modified`          datetime                                                      DEFAULT NULL COMMENT '修改时间',
    `modified_by`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='管理端：系统公告';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_notice`
--

LOCK TABLES `sys_notice` WRITE;
/*!40000 ALTER TABLE `sys_notice`
    DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_notice`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_permission`
--

DROP TABLE IF EXISTS sys_menu;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_permission`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `parent_id`   varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '父类id',
    `path`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路由地址',
    `route_name`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '路由名称',
    `redirect`    varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路由重定向',
    `component`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路由组件',
    `title`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
    `icon`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '菜单图标',
    `show_link`   bit(1)                                                        DEFAULT b'1' COMMENT '是否展示',
    `rank`        int                                                           DEFAULT '99' COMMENT '菜单排序',
    `show_parent` bit(1)                                                        DEFAULT b'1' COMMENT '是否显示父菜单',
    `keep_alive`  bit(1)                                                        DEFAULT b'0' COMMENT '是否缓存',
    `frame_src`   varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '需要内嵌的iframe链接地址',
    `menu_type`   int                                                           DEFAULT NULL COMMENT '菜单类型 \n1 菜单 2 iframe 3 外链 4 按钮\n',
    `permission`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '权限表示',
    `is_enabled`  bit(1)                                                        DEFAULT b'1' COMMENT '是否启用',
    `created_by`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '创建者',
    `created`     datetime                                                      DEFAULT NULL COMMENT '创建时间',
    `modified_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  DEFAULT NULL COMMENT '更新者',
    `modified`    datetime                                                      DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='管理端：菜单与权限';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_permission`
--

LOCK TABLES sys_menu WRITE;
/*!40000 ALTER TABLE sys_menu
    DISABLE KEYS */;
INSERT INTO sys_menu
VALUES ('1', NULL, '/sys', NULL, NULL, NULL, '系统管理', 'ri:settings-3-line', _binary '', 1, _binary '',
        _binary '\0', NULL, 1, NULL, _binary '', 'admin', '2024-03-25 09:36:20', 'admin', '2024-03-25 09:36:20'),
       ('10', '8', NULL, NULL, NULL, NULL, '角色新增', NULL, _binary '', 2, _binary '', _binary '\0', NULL, 4,
        'sys:role:add', _binary '', 'admin', '2024-03-25 13:12:48', 'admin', '2024-03-25 13:12:48'),
       ('11', '8', NULL, NULL, NULL, NULL, '角色修改', NULL, _binary '', 3, _binary '', _binary '\0', NULL, 4,
        'sys:role:update', _binary '', 'admin', '2024-03-25 13:13:15', 'admin', '2024-03-25 13:13:15'),
       ('12', '8', NULL, NULL, NULL, NULL, '角色删除', NULL, _binary '', 4, _binary '', _binary '\0', NULL, 4,
        'sys:role:delete', _binary '', 'admin', '2024-03-25 13:13:42', 'admin', '2024-03-25 13:13:42'),
       ('13', '8', NULL, NULL, NULL, NULL, '角色赋权', NULL, _binary '', 5, _binary '', _binary '\0', NULL, 4,
        'sys:role:assignPermission', _binary '', 'admin', '2024-03-25 13:14:12', 'admin', '2024-03-25 13:14:12'),
       ('1785218829636894721', '2', '', '', '', '', '用户查询', '', _binary '', 1, _binary '', _binary '\0', '', 4,
        'sys:user:query', _binary '', 'superadmin', '2024-04-30 16:05:11', 'superadmin', '2024-04-30 16:05:11'),
       ('1785219038316101633', '2', '', '', '', '', '用户新增', '', _binary '', 2, _binary '', _binary '\0', '', 4,
        'sys:user:add', _binary '', 'superadmin', '2024-04-30 16:06:01', 'superadmin', '2024-04-30 16:06:01'),
       ('1785219103000657921', '2', '', '', '', '', '用户修改', '', _binary '', 3, _binary '', _binary '\0', '', 4,
        'sys:user:update', _binary '', 'superadmin', '2024-04-30 16:06:17', 'superadmin', '2024-04-30 16:06:17'),
       ('1785219231749013505', '2', '', '', '', '', '用户删除', '', _binary '', 4, _binary '', _binary '\0', '', 4,
        'sys:user:delete', _binary '', 'superadmin', '2024-04-30 16:06:47', 'superadmin', '2024-04-30 16:06:47'),
       ('1785219294940397569', '2', '', '', '', '', '重置密码', '', _binary '', 5, _binary '', _binary '\0', '', 4,
        'sys:user:resetPassword', _binary '', 'superadmin', '2024-04-30 16:07:02', 'superadmin',
        '2024-04-30 16:07:02'),
       ('1785225857788874754', '1', '/sys/notice/index', 'noticeManager', '', 'sys/notice/index', '系统公告',
        'ep:bell-filled', _binary '', 5, _binary '', _binary '\0', '', 1, '', _binary '', 'superadmin',
        '2024-04-30 16:33:07', 'superadmin', '2024-04-30 16:33:07'),
       ('1785226155672539138', '1785225857788874754', '', '', '', '', '公告查询', '', _binary '', 1, _binary '',
        _binary '\0', '', 4, 'sys:notice:query', _binary '', 'superadmin', '2024-04-30 16:34:18', 'superadmin',
        '2024-04-30 16:34:18'),
       ('1785226275357003777', '1785225857788874754', '', '', '', '', '公告新增', '', _binary '', 2, _binary '',
        _binary '\0', '', 4, 'sys:notice:add', _binary '', 'superadmin', '2024-04-30 16:34:47', 'superadmin',
        '2024-04-30 16:34:47'),
       ('1785226339936702465', '1785225857788874754', '', '', '', '', '公告修改', '', _binary '', 3, _binary '',
        _binary '\0', '', 4, 'sys:notice:update', _binary '', 'superadmin', '2024-04-30 16:35:02', 'superadmin',
        '2024-04-30 16:35:02'),
       ('1785226422149255170', '1785225857788874754', '', '', '', '', '公告删除', '', _binary '', 4, _binary '',
        _binary '\0', '', 4, 'sys:notice:delete', _binary '', 'superadmin', '2024-04-30 16:35:22', 'superadmin',
        '2024-04-30 16:35:22'),
       ('2', '1', '/sys/user/index', 'userManger', NULL, 'sys/user/index', '用户管理', 'ri:admin-line', _binary '', 1,
        _binary '', _binary '\0', NULL, 1, NULL, _binary '', 'admin', '2024-03-25 09:37:45', 'admin',
        '2024-03-25 09:37:45'),
       ('3', '1', '/sys/menu/index', 'menuManger', NULL, 'sys/menu/index', '菜单管理', 'ep:menu', _binary '', 3,
        _binary '', _binary '\0', NULL, 1, NULL, _binary '', 'admin', '2024-03-25 09:40:13', 'admin',
        '2024-03-25 09:40:13'),
       ('4', '3', NULL, NULL, NULL, NULL, '菜单查询', NULL, _binary '', 1, _binary '', _binary '\0', NULL, 4,
        'sys:permission:query', _binary '', 'admin', '2024-03-25 13:07:00', 'admin', '2024-03-25 13:07:00'),
       ('5', '3', NULL, NULL, NULL, NULL, '菜单新增', NULL, _binary '', 2, _binary '', _binary '\0', NULL, 4,
        'sys:permission:add', _binary '', 'admin', '2024-03-25 13:07:37', 'admin', '2024-03-25 13:07:37'),
       ('6', '3', NULL, NULL, NULL, NULL, '菜单修改', NULL, _binary '', 3, _binary '', _binary '\0', NULL, 4,
        'sys:permission:update', _binary '', 'admin', '2024-03-25 13:08:00', 'admin', '2024-03-25 13:08:00'),
       ('7', '3', NULL, NULL, NULL, NULL, '菜单删除', NULL, _binary '', 4, _binary '', _binary '\0', NULL, 4,
        'sys:permission:delete', _binary '', 'admin', '2024-03-25 13:08:35', 'admin', '2024-03-25 13:08:35'),
       ('8', '1', '/sys/role/index', 'roleManger', NULL, 'sys/role/index', '角色管理', 'ri:admin-fill', _binary '', 2,
        _binary '', _binary '\0', NULL, 1, NULL, _binary '', 'admin', '2024-03-25 13:11:42', 'admin',
        '2024-03-25 13:11:42'),
       ('9', '8', NULL, NULL, NULL, NULL, '角色查询', NULL, _binary '', 1, _binary '', _binary '\0', NULL, 4,
        'sys:role:query', _binary '', 'admin', '2024-03-25 13:12:18', 'admin', '2024-03-25 13:12:18');
/*!40000 ALTER TABLE sys_menu
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role`
(
    `id`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
    `name`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
    `code`        varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色标识',
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         DEFAULT NULL COMMENT '描述',
    `is_system`   tinyint(1)                                                            DEFAULT '0' COMMENT '是否内置',
    `is_enabled`  tinyint(1)                                                   NOT NULL DEFAULT '1' COMMENT '状态',
    `created`     datetime                                                              DEFAULT NULL COMMENT '创建时间',
    `created_by`  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '创建者',
    `modified`    datetime                                                              DEFAULT NULL COMMENT '修改时间',
    `modified_by` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='管理端：角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role`
    DISABLE KEYS */;
INSERT INTO `sys_role`
VALUES ('1', '超级管理员', 'admin', '超级管理员', 1, 1, '2024-03-25 09:40:43', 'admin', '2024-05-10 14:23:31',
        'superadmin');
/*!40000 ALTER TABLE `sys_role`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_permission`
--

DROP TABLE IF EXISTS sys_role_menu;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_permission`
(
    `role_id`       varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `permission_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    PRIMARY KEY (`role_id`, `permission_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='管理端： 角色权限';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_permission`
--

LOCK TABLES sys_role_menu WRITE;
/*!40000 ALTER TABLE sys_role_menu
    DISABLE KEYS */;
INSERT INTO sys_role_menu
VALUES ('1', '1'),
       ('1', '10'),
       ('1', '11'),
       ('1', '12'),
       ('1', '13'),
       ('1', '1785218829636894721'),
       ('1', '1785219038316101633'),
       ('1', '1785219103000657921'),
       ('1', '1785219231749013505'),
       ('1', '1785219294940397569'),
       ('1', '1785225857788874754'),
       ('1', '1785226155672539138'),
       ('1', '1785226275357003777'),
       ('1', '1785226339936702465'),
       ('1', '1785226422149255170'),
       ('1', '2'),
       ('1', '3'),
       ('1', '4'),
       ('1', '5'),
       ('1', '6'),
       ('1', '7'),
       ('1', '8'),
       ('1', '9');
/*!40000 ALTER TABLE sys_role_menu
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user`
(
    `id`                  varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT 'id',
    `username`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '用户名',
    `nickname`            varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NOT NULL COMMENT '昵称',
    `password`            varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
    `gender`              tinyint                                                                DEFAULT '0' COMMENT '性别 0 未知 1 男 2 女',
    `email`               varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci           DEFAULT NULL COMMENT '邮箱',
    `phone`               varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci           DEFAULT NULL COMMENT '手机号',
    `avatar`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '头像',
    `last_login_time`     datetime                                                               DEFAULT NULL COMMENT '最后登录时间',
    `last_login_ip`       varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci           DEFAULT NULL COMMENT '最后登录ip',
    `last_pwd_reset_time` datetime                                                               DEFAULT NULL COMMENT '最后修改密码的时间',
    `description`         varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci          DEFAULT NULL COMMENT '描述',
    `is_system`           tinyint(1)                                                    NOT NULL DEFAULT '0' COMMENT '是否内置',
    `is_enabled`          tinyint(1)                                                    NOT NULL DEFAULT '1' COMMENT '状态',
    `created`             datetime                                                               DEFAULT NULL COMMENT '创建时间',
    `created_by`          varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci           DEFAULT NULL COMMENT '创建者',
    `modified`            datetime                                                               DEFAULT NULL COMMENT '修改时间',
    `modified_by`         varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci           DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='管理端： 用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user`
    DISABLE KEYS */;
INSERT INTO `sys_user`
VALUES ('1', 'admin', '管理员', '$2a$10$8FPJCvoFt7YvzVKSWUSg1.tnDDkQGJ5da/F2c2EDfzsbOFAzypQqu', 0,
        'example@example.com', '13800000000', 'https://avatars.githubusercontent.com/u/52290618', '2024-10-28 08:52:05',
        NULL, NULL, '租户-超级管理员', 1, 1, '2024-03-23 09:17:46', 'admin', '2024-12-02 09:24:07', 'superadmin');
/*!40000 ALTER TABLE `sys_user`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role`
(
    `user_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    `role_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
    PRIMARY KEY (`user_id`, `role_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='管理端：用户角色';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role`
    DISABLE KEYS */;
INSERT INTO `sys_user_role`
VALUES ('1', '1');
/*!40000 ALTER TABLE `sys_user_role`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'flexi_admin'
--
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2025-07-15  9:58:11
