/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost
 Source Database       : shiro-server

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : utf-8

 Date: 09/29/2018 23:51:38 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_menu`
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) NOT NULL COMMENT '菜单名称',
  `url` varchar(255) NOT NULL COMMENT 'url',
  `perms` varchar(255) NOT NULL COMMENT '权限标识',
  `type` int(11) NOT NULL COMMENT '类型：0-菜单，1-角色',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='菜单权限表';

-- ----------------------------
--  Records of `t_menu`
-- ----------------------------
BEGIN;
INSERT INTO `t_menu` VALUES ('1', '查询用户列表信息', '/user/security/list', 'user:list', '1');
COMMIT;

-- ----------------------------
--  Table structure for `t_role`
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
--  Records of `t_role`
-- ----------------------------
BEGIN;
INSERT INTO `t_role` VALUES ('1', 'dev');
COMMIT;

-- ----------------------------
--  Table structure for `t_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `menu_id` int(11) NOT NULL COMMENT '权限id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='权限角色表';

-- ----------------------------
--  Records of `t_role_menu`
-- ----------------------------
BEGIN;
INSERT INTO `t_role_menu` VALUES ('1', '1', '1');
COMMIT;

-- ----------------------------
--  Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_name` varchar(255) NOT NULL COMMENT '登录用户名',
  `password` varchar(255) NOT NULL COMMENT '登录密码，md2次加密',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
--  Records of `t_user`
-- ----------------------------
BEGIN;
INSERT INTO `t_user` VALUES ('1', 'dev', '123456', '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
