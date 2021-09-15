/*
Navicat MySQL Data Transfer

Source Server         : spring
Source Server Version : 50726
Source Host           : localhost:3306
Source Database       : jblog

Target Server Type    : MYSQL
Target Server Version : 50726
File Encoding         : 65001

Date: 2021-09-16 07:08:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `bgms_blog`
-- ----------------------------
DROP TABLE IF EXISTS `bgms_blog`;
CREATE TABLE `bgms_blog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `appreciation` bit(1) NOT NULL COMMENT '是否需要赞赏：0-否，1-是',
  `commentabled` bit(1) NOT NULL COMMENT '是否推荐：默认1，0-否，1-是',
  `title` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL COMMENT '摘要',
  `first_picture` varchar(255) DEFAULT NULL COMMENT '摘要图片',
  `content` text,
  `create_time` datetime(6) DEFAULT NULL,
  `update_time` datetime(6) DEFAULT NULL COMMENT '最后一次更新日期',
  `flag` bit(1) NOT NULL COMMENT '原创还是转载',
  `flag_url` varchar(255) DEFAULT NULL COMMENT '转载地址',
  `recommend` bit(1) NOT NULL COMMENT '是否可以评论：0-否，1-是',
  `share_statement` bit(1) NOT NULL COMMENT '是否可以分享',
  `state` int(11) NOT NULL COMMENT '状态：0-草稿，1-审核中，2-已发布，3-未通过，4-已删除',
  `ums_id` bigint(20) DEFAULT NULL COMMENT 'ums_member的ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bgms_blog
-- ----------------------------
INSERT INTO `bgms_blog` VALUES ('4', '', '', '111', '', null, '<h1><a id=\"_0\"></a>一级标题</h1>\n', '2021-09-04 21:37:20.777000', '2021-09-05 11:25:40.669000', '', null, '', '', '1', '0');
INSERT INTO `bgms_blog` VALUES ('5', '', '', '11111111111', '111111111111111111', 'https://img2.baidu.com/it/u=1696060229,985969149&fm=26&fmt=auto&gp=0.jpg', '<p><strong>粗体</strong></p>\n<h2><a id=\"_1\"></a>二级标题</h2>\n<p><ins>下划线</ins></p>\n<p><img src=\"https://img2.baidu.com/it/u=1696060229,985969149&amp;fm=26&amp;fmt=auto&amp;gp=0.jpg\" alt=\"https://img2.baidu.com/it/u=1696060229,985969149&amp;fm=26&amp;fmt=auto&amp;gp=0.jpg\" /></p>\n<p><a href=\"https://img2.baidu.com/it/u=1696060229,985969149&amp;fm=26&amp;fmt=auto&amp;gp=0.jpg\" target=\"_blank\">https://img2.baidu.com/it/u=1696060229,985969149&amp;fm=26&amp;fmt=auto&amp;gp=0.jpg</a></p>\n', null, '2021-09-04 23:52:29.139000', '', '1111', '', '', '0', '0');
INSERT INTO `bgms_blog` VALUES ('6', '', '', '111', '111', '1111', '<h2><a id=\"_0\"></a>二级标题</h2>\n<p><img src=\"https://img2.baidu.com/it/u=1696060229,985969149&amp;fm=26&amp;fmt=auto&amp;gp=0.jpg\" alt=\"https://img2.baidu.com/it/u=1696060229,985969149&amp;fm=26&amp;fmt=auto&amp;gp=0.jpg\" /></p>\n', '2021-09-04 21:44:19.004000', '2021-09-05 11:19:09.274000', '', null, '', '', '1', '0');
INSERT INTO `bgms_blog` VALUES ('7', '', '', '2', '22', '22', '22222', null, '2021-09-04 23:41:44.448000', '', null, '', '', '1', '0');
INSERT INTO `bgms_blog` VALUES ('8', '', '', '333', '4', '4', '3333# 一级标题\n\n*斜体*\n\n\n### \n\n**粗体**\n\n#### 四级标题\n\n', '2021-09-04 23:45:59.754000', '2021-09-04 23:45:59.754000', '', '111111', '', '', '0', '1');
INSERT INTO `bgms_blog` VALUES ('9', '', '', '111', '111', '111', '11*斜体*\n## 二级标题\n\n![https://tse2-mm.cn.bing.net/th/id/OIP-C.xsA-3qUw6cqmd8nRfxk6TQHaEK?w=287&h=180&c=7&r=0&o=5&dpr=1.25&pid=1.7](https://tse2-mm.cn.bing.net/th/id/OIP-C.xsA-3qUw6cqmd8nRfxk6TQHaEK?w=287&h=180&c=7&r=0&o=5&dpr=1.25&pid=1.7)\n\n“”\n“”\n> 段落引用\n', '2021-09-05 10:39:00.413000', '2021-09-05 10:40:23.070000', '', null, '', '', '0', '1');
INSERT INTO `bgms_blog` VALUES ('10', '', '', '11', '', null, '11', '2021-09-05 11:22:13.366000', '2021-09-05 11:23:12.683000', '', '', '', '', '1', '1');

-- ----------------------------
-- Table structure for `bgms_blog_classify_relation`
-- ----------------------------
DROP TABLE IF EXISTS `bgms_blog_classify_relation`;
CREATE TABLE `bgms_blog_classify_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `blog_id` bigint(20) DEFAULT NULL,
  `classify_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='博文与博文分类关系表';

-- ----------------------------
-- Records of bgms_blog_classify_relation
-- ----------------------------
INSERT INTO `bgms_blog_classify_relation` VALUES ('14', '7', '6');
INSERT INTO `bgms_blog_classify_relation` VALUES ('15', '7', '5');
INSERT INTO `bgms_blog_classify_relation` VALUES ('16', '8', '7');
INSERT INTO `bgms_blog_classify_relation` VALUES ('17', '8', '8');
INSERT INTO `bgms_blog_classify_relation` VALUES ('30', '5', '7');
INSERT INTO `bgms_blog_classify_relation` VALUES ('31', '5', '8');
INSERT INTO `bgms_blog_classify_relation` VALUES ('32', '5', '5');
INSERT INTO `bgms_blog_classify_relation` VALUES ('41', '9', '6');
INSERT INTO `bgms_blog_classify_relation` VALUES ('42', '9', '7');
INSERT INTO `bgms_blog_classify_relation` VALUES ('49', '6', '6');
INSERT INTO `bgms_blog_classify_relation` VALUES ('50', '6', '7');
INSERT INTO `bgms_blog_classify_relation` VALUES ('51', '6', '8');

-- ----------------------------
-- Table structure for `bgms_classify`
-- ----------------------------
DROP TABLE IF EXISTS `bgms_classify`;
CREATE TABLE `bgms_classify` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ums_id` bigint(20) NOT NULL COMMENT 'ums_member的ID',
  `name` varchar(255) DEFAULT NULL COMMENT '分类名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bgms_classify
-- ----------------------------
INSERT INTO `bgms_classify` VALUES ('5', '1', '11');
INSERT INTO `bgms_classify` VALUES ('6', '2', '11111');
INSERT INTO `bgms_classify` VALUES ('7', '3', '3333');
INSERT INTO `bgms_classify` VALUES ('8', '4', '44444');

-- ----------------------------
-- Table structure for `bgms_tag`
-- ----------------------------
DROP TABLE IF EXISTS `bgms_tag`;
CREATE TABLE `bgms_tag` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `blog_id` bigint(20) NOT NULL COMMENT '博文id',
  `name` varchar(255) DEFAULT NULL COMMENT '标签内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of bgms_tag
-- ----------------------------
INSERT INTO `bgms_tag` VALUES ('30', '7', '2');
INSERT INTO `bgms_tag` VALUES ('31', '7', '3');
INSERT INTO `bgms_tag` VALUES ('32', '7', '4');
INSERT INTO `bgms_tag` VALUES ('33', '7', '5');
INSERT INTO `bgms_tag` VALUES ('34', '8', '44');
INSERT INTO `bgms_tag` VALUES ('35', '8', '444');
INSERT INTO `bgms_tag` VALUES ('36', '8', '4444');
INSERT INTO `bgms_tag` VALUES ('45', '5', '111');
INSERT INTO `bgms_tag` VALUES ('46', '5', '122');
INSERT INTO `bgms_tag` VALUES ('52', '9', '1');
INSERT INTO `bgms_tag` VALUES ('57', '6', '1');
INSERT INTO `bgms_tag` VALUES ('58', '6', '2');
INSERT INTO `bgms_tag` VALUES ('68', '4', '1');
INSERT INTO `bgms_tag` VALUES ('69', '4', '1');
INSERT INTO `bgms_tag` VALUES ('70', '4', '2');

-- ----------------------------
-- Table structure for `ums_admin`
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin`;
CREATE TABLE `ums_admin` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `icon` varchar(500) DEFAULT NULL COMMENT '头像',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `nick_name` varchar(200) DEFAULT NULL COMMENT '昵称',
  `note` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `status` int(11) DEFAULT '1' COMMENT '帐号启用状态：0->禁用；1->启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='后台用户表';

-- ----------------------------
-- Records of ums_admin
-- ----------------------------
INSERT INTO `ums_admin` VALUES ('1', 'admin', '$2a$10$bje8m5P.aitwEcgvYD4qnOw8jvUHPFgjoeUfN0f1c/Xq0qx1yVN7a', 'https://img0.baidu.com/it/u=2039975083,449836607&fm=26&fmt=auto&gp=0.jpg', 'string', 'admin', 'admin', '2021-08-29 10:04:32', null, '1');
INSERT INTO `ums_admin` VALUES ('2', 'umsgl', '$2a$10$9eULkk9lLrnXLV2xm8ZMWeoBO0o.1EP4y2ziCDPvjYcdGF0DGmjT2', 'string', 'string', 'umsgl', 'umsgl', '2021-08-29 15:02:44', null, '1');
INSERT INTO `ums_admin` VALUES ('3', 'string', '$2a$10$suAvMXOhiPVA0bkoviQD3udWVGE9rQKKB2zwlrufUJ5bqiSBQTE/6', 'string', 'string', 'string', 'string', '2021-08-29 16:26:11', null, '1');

-- ----------------------------
-- Table structure for `ums_admin_role_relation`
-- ----------------------------
DROP TABLE IF EXISTS `ums_admin_role_relation`;
CREATE TABLE `ums_admin_role_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `admin_id` bigint(20) DEFAULT NULL,
  `role_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='后台用户和角色关系表';

-- ----------------------------
-- Records of ums_admin_role_relation
-- ----------------------------
INSERT INTO `ums_admin_role_relation` VALUES ('3', '1', '1');
INSERT INTO `ums_admin_role_relation` VALUES ('4', '1', '2');

-- ----------------------------
-- Table structure for `ums_member`
-- ----------------------------
DROP TABLE IF EXISTS `ums_member`;
CREATE TABLE `ums_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `nickname` varchar(64) NOT NULL COMMENT '昵称',
  `phone` varchar(64) NOT NULL COMMENT '手机号码',
  `status` int(1) DEFAULT '1' COMMENT '帐号启用状态:0->禁用；1->启用',
  `create_time` datetime DEFAULT NULL COMMENT '注册时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `icon` varchar(500) DEFAULT NULL COMMENT '头像',
  `gender` int(1) DEFAULT '0' COMMENT '性别：0->未知；1->男；2->女',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `city` varchar(64) DEFAULT NULL COMMENT '所做城市',
  `job` varchar(100) DEFAULT NULL COMMENT '职业',
  `personalized_signature` varchar(300) DEFAULT NULL COMMENT '个性签名',
  `integration` int(11) DEFAULT NULL COMMENT '积分',
  `growth` int(11) DEFAULT NULL COMMENT '成长值',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`),
  UNIQUE KEY `idx_phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='会员表';

-- ----------------------------
-- Records of ums_member
-- ----------------------------
INSERT INTO `ums_member` VALUES ('1', 'ceshi1', '$2a$10$BU5KghBWrmCbNVwHLdAaa.Uq.tAYvj8bnmjg0kYGr8fTPw0MB9j2a', '测试1', '18803260895', '1', '2021-09-09 21:25:00', null, 'https://img2.baidu.com/it/u=871084579,2353160254&fm=26&fmt=auto&gp=0.jpg', '0', '2021-09-05', '测试1测试1', '测试1测试1测试1测试1', '测试1测试1测试1', '0', '1');

-- ----------------------------
-- Table structure for `ums_menu`
-- ----------------------------
DROP TABLE IF EXISTS `ums_menu`;
CREATE TABLE `ums_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `title` varchar(100) DEFAULT NULL COMMENT '菜单名称',
  `level` int(11) DEFAULT NULL COMMENT '菜单级数',
  `sort` int(11) DEFAULT NULL COMMENT '菜单排序',
  `name` varchar(100) DEFAULT NULL COMMENT '前端名称',
  `icon` varchar(200) DEFAULT NULL COMMENT '前端图标',
  `hidden` int(11) DEFAULT NULL COMMENT '前端隐藏',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='后台菜单表';

-- ----------------------------
-- Records of ums_menu
-- ----------------------------
INSERT INTO `ums_menu` VALUES ('1', '0', '2021-08-27 22:02:40', '权限', '0', '0', 'ums', 'ums', '0');
INSERT INTO `ums_menu` VALUES ('2', '1', '2021-08-27 22:04:28', '人员列表', '1', '0', 'admin', 'ums-admin', '0');
INSERT INTO `ums_menu` VALUES ('3', '1', '2021-08-27 22:06:18', '角色列表', '1', '0', 'role', 'ums-role', '0');
INSERT INTO `ums_menu` VALUES ('4', '1', '2021-08-27 22:06:54', '菜单列表', '1', '0', 'menu', 'ums-menu', '0');
INSERT INTO `ums_menu` VALUES ('5', '1', '2021-08-27 22:07:30', '资源列表', '1', '0', 'resource', 'ums-resource', '0');
INSERT INTO `ums_menu` VALUES ('6', '0', '2021-08-29 19:20:54', '博文', '0', '0', 'bgms', 'bgms', '0');
INSERT INTO `ums_menu` VALUES ('7', '1', '2021-08-29 19:20:54', '博文列表', '1', '0', 'bgblogs', 'bgblogs', '0');
INSERT INTO `ums_menu` VALUES ('8', '1', '2021-08-29 19:20:54', '博文分类', '1', '0', 'bgclassify', 'bgclassify', '0');

-- ----------------------------
-- Table structure for `ums_resource`
-- ----------------------------
DROP TABLE IF EXISTS `ums_resource`;
CREATE TABLE `ums_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `name` varchar(200) DEFAULT NULL COMMENT '资源名称',
  `url` varchar(200) DEFAULT NULL COMMENT '资源URL',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `category_id` bigint(20) DEFAULT NULL COMMENT '资源分类ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='后台资源表';

-- ----------------------------
-- Records of ums_resource
-- ----------------------------
INSERT INTO `ums_resource` VALUES ('1', '2021-08-27 21:38:44', '后台人员管理', '/admin/**', '后台增删改查人员账号', '1');
INSERT INTO `ums_resource` VALUES ('2', '2021-08-27 21:40:09', '后台菜单管理', '/menu/**', '后台增删改查菜单', '1');
INSERT INTO `ums_resource` VALUES ('3', '2021-08-27 21:45:37', '后台资源管理', '/resource/**', '后台增删改查资源账号', '1');
INSERT INTO `ums_resource` VALUES ('4', '2021-08-27 21:46:09', '后台资源分类管理', '/resourceCategory/**', '后台资源分类管理', '1');
INSERT INTO `ums_resource` VALUES ('5', '2021-08-29 19:32:47', '后台博文管理', '/bolg/**', '可以管理所有博文', '2');
INSERT INTO `ums_resource` VALUES ('6', '2021-08-29 19:32:47', '后台博文分类管理', '/classify/**', '可以管理所有博文分类', '2');
INSERT INTO `ums_resource` VALUES ('7', '2021-08-29 22:07:40', '后台角色管理', '/role/**', '后台增删改查角色', '1');

-- ----------------------------
-- Table structure for `ums_resource_category`
-- ----------------------------
DROP TABLE IF EXISTS `ums_resource_category`;
CREATE TABLE `ums_resource_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `name` varchar(200) DEFAULT NULL COMMENT '分类名称',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='资源分类表';

-- ----------------------------
-- Records of ums_resource_category
-- ----------------------------
INSERT INTO `ums_resource_category` VALUES ('1', '2021-08-27 21:34:40', '权限模块', '1');
INSERT INTO `ums_resource_category` VALUES ('2', '2021-08-29 19:26:06', '博文模块', '0');

-- ----------------------------
-- Table structure for `ums_role`
-- ----------------------------
DROP TABLE IF EXISTS `ums_role`;
CREATE TABLE `ums_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL COMMENT '名称',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `admin_count` int(11) DEFAULT NULL COMMENT '后台用户数量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int(11) DEFAULT '1' COMMENT '启用状态：0->禁用；1->启用',
  `sort` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='后台用户角色表';

-- ----------------------------
-- Records of ums_role
-- ----------------------------
INSERT INTO `ums_role` VALUES ('1', '后台权限管理员', '后台权限管理员', '0', '2021-08-27 22:08:38', '1', '0');
INSERT INTO `ums_role` VALUES ('2', '后台博文管理员', '后台博文管理员', '0', '2021-08-29 21:59:54', '1', '0');

-- ----------------------------
-- Table structure for `ums_role_menu_relation`
-- ----------------------------
DROP TABLE IF EXISTS `ums_role_menu_relation`;
CREATE TABLE `ums_role_menu_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='后台角色菜单关系表';

-- ----------------------------
-- Records of ums_role_menu_relation
-- ----------------------------
INSERT INTO `ums_role_menu_relation` VALUES ('1', '1', '1');
INSERT INTO `ums_role_menu_relation` VALUES ('2', '1', '2');
INSERT INTO `ums_role_menu_relation` VALUES ('3', '1', '3');
INSERT INTO `ums_role_menu_relation` VALUES ('4', '1', '4');
INSERT INTO `ums_role_menu_relation` VALUES ('5', '1', '5');
INSERT INTO `ums_role_menu_relation` VALUES ('6', '2', '6');
INSERT INTO `ums_role_menu_relation` VALUES ('7', '2', '7');
INSERT INTO `ums_role_menu_relation` VALUES ('8', '2', '8');

-- ----------------------------
-- Table structure for `ums_role_resource_relation`
-- ----------------------------
DROP TABLE IF EXISTS `ums_role_resource_relation`;
CREATE TABLE `ums_role_resource_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `resource_id` bigint(20) DEFAULT NULL COMMENT '资源ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='后台角色资源关系表';

-- ----------------------------
-- Records of ums_role_resource_relation
-- ----------------------------
INSERT INTO `ums_role_resource_relation` VALUES ('1', '1', '1');
INSERT INTO `ums_role_resource_relation` VALUES ('2', '1', '2');
INSERT INTO `ums_role_resource_relation` VALUES ('3', '1', '3');
INSERT INTO `ums_role_resource_relation` VALUES ('4', '1', '4');
INSERT INTO `ums_role_resource_relation` VALUES ('5', '2', '5');
INSERT INTO `ums_role_resource_relation` VALUES ('6', '2', '6');
INSERT INTO `ums_role_resource_relation` VALUES ('7', '1', '7');
