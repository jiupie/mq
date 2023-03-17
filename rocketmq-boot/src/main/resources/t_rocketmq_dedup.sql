/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : 127.0.0.1:3306
 Source Schema         : mybatis_study

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 17/03/2023 17:39:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_rocketmq_dedup
-- ----------------------------
DROP TABLE IF EXISTS `t_rocketmq_dedup`;
CREATE TABLE `t_rocketmq_dedup`  (
  `application_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消费的应用名（可以用消费者组名称）',
  `topic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息来源的topic（不同topic消息不会认为重复）',
  `tag` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息的tag（同一个topic不同的tag，就算去重键一样也不会认为重复），没有tag则存\"\"字符串',
  `msg_uniq_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息的唯一键（建议使用业务主键）',
  `status` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '这条消息的消费状态',
  `expire_time` bigint(0) NOT NULL COMMENT '这个去重记录的过期时间（时间戳）',
  UNIQUE INDEX `uniq_key`(`application_name`, `topic`, `tag`, `msg_uniq_key`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_rocketmq_dedup
-- ----------------------------
INSERT INTO `t_rocketmq_dedup` VALUES ('test', 'simple-tp-boot', '', '1', '0', 1667201571007);
INSERT INTO `t_rocketmq_dedup` VALUES ('test', 'simple-tp-boot', '', 'test', '0', 1667201676534);
INSERT INTO `t_rocketmq_dedup` VALUES ('test', 'simple-tp-boot', '', 'testqqq', '0', 1667201641779);

SET FOREIGN_KEY_CHECKS = 1;
