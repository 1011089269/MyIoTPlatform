/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50731
 Source Host           : localhost:3306
 Source Schema         : myiotplatform

 Target Server Type    : MySQL
 Target Server Version : 50731
 File Encoding         : 65001

 Date: 05/06/2022 16:27:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `age` int(4) NULL DEFAULT NULL,
  `role` int(1) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'zhangsan', '123456', 'zhangsan@163.com', 18, 1);
INSERT INTO `user` VALUES (2, '2', '123456', 'lisi@163.com', 11, 2);
INSERT INTO `user` VALUES (11, '8', '8', '8', 8, 2);
INSERT INTO `user` VALUES (12, '张三', '123456', 'zhangsan@163.com', 25, 0);
INSERT INTO `user` VALUES (13, '张三', '123456', 'zhangsan@163.com', 11, 0);
INSERT INTO `user` VALUES (14, 'lisi', '123456', '2941563986@qq.com', 24, 1);
INSERT INTO `user` VALUES (15, 'wangwu', '123', 'wangwu@qq.com', 99, 0);

SET FOREIGN_KEY_CHECKS = 1;
