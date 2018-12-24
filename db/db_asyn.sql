/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.1.99
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : 192.168.1.99:3306
 Source Schema         : db_asyn

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 23/12/2018 22:58:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_clinic_log
-- ----------------------------
DROP TABLE IF EXISTS `t_clinic_log`;
CREATE TABLE `t_clinic_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '操作员ID',
  `user_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作员名称',
  `clinic_id` bigint(20) NOT NULL COMMENT '诊所ID',
  `clinic_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '诊所名称',
  `menu_id` int(11) NOT NULL COMMENT '页面ID',
  `menu_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '页面名称',
  `model_id` int(11) NOT NULL COMMENT '模块ID',
  `model_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '模块名称',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '操作内容',
  `client_ip` char(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'IP',
  `timestamp` bigint(20) NOT NULL COMMENT '时间戮',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '诊所日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_clinic_log
-- ----------------------------
INSERT INTO `t_clinic_log` VALUES (8, 123654487, '先峰', 0, '', 1, '登入', 1, '登入注册', '登入', '0:0:0:0:0:0:0:1', 1545565912390);
INSERT INTO `t_clinic_log` VALUES (9, 123654487, '先峰', 0, '', 1, '登入', 1, '登入注册', '登入', '0:0:0:0:0:0:0:1', 1545565924110);
INSERT INTO `t_clinic_log` VALUES (10, 123654487, '先峰', 0, '', 1, '登入', 1, '登入注册', '登入', '0:0:0:0:0:0:0:1', 1545565924913);
INSERT INTO `t_clinic_log` VALUES (11, 123654487, '先峰', 0, '', 1, '登入', 1, '登入注册', '登入', '0:0:0:0:0:0:0:1', 1545565925609);
INSERT INTO `t_clinic_log` VALUES (12, 123654487, '先峰', 0, '', 1, '登入', 1, '登入注册', '登入', '0:0:0:0:0:0:0:1', 1545565926284);

SET FOREIGN_KEY_CHECKS = 1;
