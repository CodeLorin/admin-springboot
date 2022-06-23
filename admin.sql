/*
 Navicat Premium Data Transfer

 Source Server         : mysql
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : software

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 23/06/2022 20:33:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `module` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '模块',
  `operation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作',
  `class_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求类和方法',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '参数',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求用户',
  `request_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求方式',
  `ip` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ip地址',
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip位置',
  `browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器',
  `os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `res` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '返回值',
  `excute_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求耗时',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 590 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件名称',
  `type` tinyint(1) NOT NULL COMMENT '类型     0：目录   1：菜单   2：按钮',
  `icon` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `orderNum` int(11) NULL DEFAULT NULL COMMENT '排序',
  `create_time` datetime(0) NOT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `status` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', '', 'sys:manage', '', 0, 'icon-guanli1', 1, '2021-12-17 13:55:15', '2021-12-17 23:01:04', 1);
INSERT INTO `sys_menu` VALUES (2, 1, '用户管理', '/sys/users', 'sys:user:list', 'sys/User', 1, 'icon-zhanghao', 1, '2021-12-17 19:03:45', '2021-12-17 19:03:48', 1);
INSERT INTO `sys_menu` VALUES (3, 1, '角色管理', '/sys/roles', 'sys:role:list', 'sys/Role', 1, 'icon-jiaoseguanli', 2, '2021-12-17 19:03:45', '2021-12-17 19:03:48', 1);
INSERT INTO `sys_menu` VALUES (4, 1, '菜单管理', '/sys/menus', 'sys:menu:list', 'sys/Menu', 1, 'icon-caidan', 3, '2021-12-17 19:03:45', '2021-12-17 19:03:48', 1);
INSERT INTO `sys_menu` VALUES (5, 0, '系统工具', '', 'sys:tools', '', 0, 'icon-xitong', 2, '2021-12-17 19:06:11', '2021-12-17 14:11:35', 1);
INSERT INTO `sys_menu` VALUES (6, 5, '系统日志', '/sys/log', 'sys:log:list', 'tool/Log', 1, 'icon-rizhi', 2, '2021-12-17 19:06:11', '2021-12-17 14:11:37', 1);
INSERT INTO `sys_menu` VALUES (7, 5, '在线用户', '/sys/onlineuser', 'sys:online:list', 'tool/OnlineUser', 1, 'icon-zaixianyonghu', 2, '2021-12-17 19:06:11', '2021-12-17 14:11:39', 1);
INSERT INTO `sys_menu` VALUES (8, 3, '添加角色', NULL, 'sys:role:save', '', 2, '', 1, '2021-12-17 23:02:25', '2021-12-17 21:53:14', 1);
INSERT INTO `sys_menu` VALUES (9, 2, '添加用户', NULL, 'sys:user:save', NULL, 2, NULL, 1, '2021-12-17 21:48:32', NULL, 1);
INSERT INTO `sys_menu` VALUES (10, 2, '修改用户', NULL, 'sys:user:update', NULL, 2, NULL, 2, '2021-12-17 21:49:03', '2021-12-17 21:53:04', 1);
INSERT INTO `sys_menu` VALUES (11, 2, '删除用户', NULL, 'sys:user:delete', NULL, 2, NULL, 3, '2021-12-17 21:49:21', NULL, 1);
INSERT INTO `sys_menu` VALUES (12, 2, '分配角色', NULL, 'sys:user:role', NULL, 2, NULL, 4, '2021-12-17 21:49:58', NULL, 1);
INSERT INTO `sys_menu` VALUES (13, 2, '修改密码', NULL, 'sys:user:repass', NULL, 2, NULL, 5, '2021-12-17 21:50:36', NULL, 1);
INSERT INTO `sys_menu` VALUES (14, 3, '修改角色', NULL, 'sys:role:update', NULL, 2, NULL, 2, '2021-12-17 21:51:14', NULL, 1);
INSERT INTO `sys_menu` VALUES (15, 3, '删除角色', NULL, 'sys:role:delete', NULL, 2, NULL, 3, '2021-12-17 21:51:39', NULL, 1);
INSERT INTO `sys_menu` VALUES (16, 3, '分配权限', NULL, 'sys:role:perm', NULL, 2, NULL, 5, '2021-12-17 21:52:02', NULL, 1);
INSERT INTO `sys_menu` VALUES (17, 4, '添加菜单', NULL, 'sys:menu:save', NULL, 2, NULL, 1, '2021-12-17 21:53:53', '2021-12-17 21:55:28', 1);
INSERT INTO `sys_menu` VALUES (18, 4, '修改菜单', NULL, 'sys:menu:update', NULL, 2, NULL, 2, '2021-12-17 21:56:12', NULL, 1);
INSERT INTO `sys_menu` VALUES (19, 4, '删除菜单', NULL, 'sys:menu:delete', NULL, 2, NULL, 3, '2021-12-18 12:04:56', NULL, 1);
INSERT INTO `sys_menu` VALUES (20, 6, '删除日志', NULL, 'sys:log:delete', NULL, 2, NULL, 1, '2021-12-18 19:53:01', NULL, 1);
INSERT INTO `sys_menu` VALUES (21, 7, '下线用户', NULL, 'sys:online:logout', NULL, 2, NULL, 1, '2021-12-19 23:58:43', NULL, 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色唯一标识',
  `description` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `status` tinyint(1) NOT NULL COMMENT '角色状态',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE,
  UNIQUE INDEX `code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', '所有权限', '2021-12-15 22:03:14', '2021-12-18 00:09:50', 1);
INSERT INTO `sys_role` VALUES (2, '普通用户', 'normal', '基本权限', '2021-12-15 22:04:02', '2021-12-15 22:04:07', 1);
INSERT INTO `sys_role` VALUES (3, '测试用户', 'test', '测试', '2021-12-18 11:55:02', NULL, 1);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NOT NULL,
  `menu_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 688 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (250, 2, 1);
INSERT INTO `sys_role_menu` VALUES (251, 2, 2);
INSERT INTO `sys_role_menu` VALUES (252, 2, 9);
INSERT INTO `sys_role_menu` VALUES (253, 2, 3);
INSERT INTO `sys_role_menu` VALUES (254, 2, 8);
INSERT INTO `sys_role_menu` VALUES (255, 2, 4);
INSERT INTO `sys_role_menu` VALUES (256, 2, 17);
INSERT INTO `sys_role_menu` VALUES (257, 2, 5);
INSERT INTO `sys_role_menu` VALUES (258, 2, 7);
INSERT INTO `sys_role_menu` VALUES (655, 1, 1);
INSERT INTO `sys_role_menu` VALUES (656, 1, 2);
INSERT INTO `sys_role_menu` VALUES (657, 1, 9);
INSERT INTO `sys_role_menu` VALUES (658, 1, 10);
INSERT INTO `sys_role_menu` VALUES (659, 1, 11);
INSERT INTO `sys_role_menu` VALUES (660, 1, 12);
INSERT INTO `sys_role_menu` VALUES (661, 1, 13);
INSERT INTO `sys_role_menu` VALUES (662, 1, 3);
INSERT INTO `sys_role_menu` VALUES (663, 1, 8);
INSERT INTO `sys_role_menu` VALUES (664, 1, 14);
INSERT INTO `sys_role_menu` VALUES (665, 1, 15);
INSERT INTO `sys_role_menu` VALUES (666, 1, 16);
INSERT INTO `sys_role_menu` VALUES (667, 1, 4);
INSERT INTO `sys_role_menu` VALUES (668, 1, 17);
INSERT INTO `sys_role_menu` VALUES (669, 1, 18);
INSERT INTO `sys_role_menu` VALUES (670, 1, 19);
INSERT INTO `sys_role_menu` VALUES (671, 1, 5);
INSERT INTO `sys_role_menu` VALUES (672, 1, 6);
INSERT INTO `sys_role_menu` VALUES (673, 1, 20);
INSERT INTO `sys_role_menu` VALUES (674, 1, 7);
INSERT INTO `sys_role_menu` VALUES (675, 1, 21);
INSERT INTO `sys_role_menu` VALUES (682, 3, 1);
INSERT INTO `sys_role_menu` VALUES (683, 3, 2);
INSERT INTO `sys_role_menu` VALUES (684, 3, 3);
INSERT INTO `sys_role_menu` VALUES (685, 3, 4);
INSERT INTO `sys_role_menu` VALUES (686, 3, 5);
INSERT INTO `sys_role_menu` VALUES (687, 3, 7);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像url',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `last_login` datetime(0) NULL DEFAULT NULL COMMENT '最后一次登录',
  `status` tinyint(1) NOT NULL COMMENT '用户状态 1启动 0禁用',
  PRIMARY KEY (`id`, `username`) USING BTREE,
  UNIQUE INDEX `UK_USERNAME`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'lorin', '$2a$10$JCeX9gCxFHHiRzB0DV4V0uKqa0L15Zb/IYfGsN7P5744Au4iOetxu', 'https://s4.ax1x.com/2021/12/20/TnZ1ld.png', 'xxx@163.com', '2021-12-17 10:33:09', '2022-05-15 21:46:27', '2022-06-23 00:33:23', 1);
INSERT INTO `sys_user` VALUES (2, 'test', '$2a$10$FSKHsvvBJkHGkVENIQyaI.rfhHHby6xwbZ/BgzJ2k9Lq0icxvhx.y', 'https://s4.ax1x.com/2021/12/20/TnZ1ld.png', 'test@123.comc', '2021-12-17 14:13:20', '2021-12-23 11:18:40', '2021-12-23 12:05:32', 1);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 1);
INSERT INTO `sys_user_role` VALUES (17, 2, 3);

SET FOREIGN_KEY_CHECKS = 1;
