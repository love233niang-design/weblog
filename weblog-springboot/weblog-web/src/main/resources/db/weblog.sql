/*
 Navicat Premium Dump SQL

 Source Server         : win
 Source Server Type    : MySQL
 Source Server Version : 50715 (5.7.15-log)
 Source Host           : localhost:3306
 Source Schema         : weblog

 Target Server Type    : MySQL
 Target Server Version : 50715 (5.7.15-log)
 File Encoding         : 65001

 Date: 17/07/2026 20:35:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_article
-- ----------------------------
DROP TABLE IF EXISTS `t_article`;
CREATE TABLE `t_article`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章id',
  `title` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文章标题',
  `cover` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '文章封面',
  `summary` varchar(160) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '文章摘要',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '删除标志位：0：未删除 1：已删除',
  `read_num` int(11) UNSIGNED NOT NULL DEFAULT 1 COMMENT '被阅读次数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_article
-- ----------------------------
INSERT INTO `t_article` VALUES (9, '测试标题', 'https://img.quanxiaoha.com/quanxiaoha/193dd1504ebb4f138085acb23619e0dd.jpg', '测试摘要', '2026-07-03 15:43:57', '2026-07-13 15:19:35', 0, 7);
INSERT INTO `t_article` VALUES (10, '代码块测试', 'http://127.0.0.1:9000/weblog/35014cc481f84996b1c855819a27df3c.png', '测试代码块', '2026-07-03 21:49:36', '2026-07-07 18:23:17', 0, 14);
INSERT INTO `t_article` VALUES (11, '测试标题2', 'http://127.0.0.1:9000/weblog/903a3cf8e63a4e45b4f08801e2d362ac.png', '测试摘要2', '2026-07-07 14:29:34', '2026-07-09 11:39:10', 0, 33);
INSERT INTO `t_article` VALUES (12, 'lucene 测试 手机', 'http://127.0.0.1:9000/weblog/17237130a89d4395af61e21780083ebc.png', '华为', '2026-07-13 22:15:52', '2026-07-13 22:15:52', 0, 11);

-- ----------------------------
-- Table structure for t_article_category_rel
-- ----------------------------
DROP TABLE IF EXISTS `t_article_category_rel`;
CREATE TABLE `t_article_category_rel`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` bigint(20) UNSIGNED NOT NULL COMMENT '文章id',
  `category_id` bigint(20) UNSIGNED NOT NULL COMMENT '分类id',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uni_article_id`(`article_id`) USING BTREE,
  INDEX `idx_category_id`(`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章所属分类关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_article_category_rel
-- ----------------------------
INSERT INTO `t_article_category_rel` VALUES (23, 10, 2);
INSERT INTO `t_article_category_rel` VALUES (26, 11, 1);
INSERT INTO `t_article_category_rel` VALUES (27, 9, 1);
INSERT INTO `t_article_category_rel` VALUES (28, 12, 2);

-- ----------------------------
-- Table structure for t_article_content
-- ----------------------------
DROP TABLE IF EXISTS `t_article_content`;
CREATE TABLE `t_article_content`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章内容id',
  `article_id` bigint(20) NOT NULL COMMENT '文章id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '教程正文',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_article_id`(`article_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章内容表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_article_content
-- ----------------------------
INSERT INTO `t_article_content` VALUES (9, 9, '内容');
INSERT INTO `t_article_content` VALUES (10, 10, '```\n  @Test\n  void testLog() {\n      log.info(\"这是一行 Info 级别日志\");\n      log.warn(\"这是一行 Warn 级别日志\");\n      log.error(\"这是一行 Error 级别日志\");\n  }\n```\n');
INSERT INTO `t_article_content` VALUES (11, 11, '# Spring Security实现登录功能\n\n# 一、Spring Security 是什么\n\n## 它是谁\n\nSpring 家族中用于安全访问控制的框架，基于 Servlet 过滤器（Filter）实现\n\n## 它做什么\n\n认证（你是谁）和授权（你能干什么）\n\n## 怎么做的\n\n底层是一系列过滤器链，请求进入后依次经过这些过滤器，全部通过才能访问到 Controller\n\n# 二、 核心架构：过滤器链\n\nSpring Security 的本质就是一条过滤器链\n\n```Java\n客户端请求\n    ↓\n① SecurityContextPersistenceFilter（恢复/清除 SecurityContext）\n    ↓\n② UsernamePasswordAuthenticationFilter（处理表单登录）\n    ↓\n③ ... 其他过滤器 ...\n    ↓\n④ FilterSecurityInterceptor（权限判断，放行或抛异常）\n    ↓\nController\n```\n\n### 本项目中：\n\n- `JwtAuthenticationFilter`（登录）替换了`UsernamePasswordAuthenticationFilter`\n\n- `TokenAuthenticationFilter`（校验 Token）是自定义插入的过滤器\n\n# 三、三大核心组件\n\n## `SecurityContextHolder`（安全上下文容器）\n\n- **作用**：存储当前线程的认证信息（`Authentication` 对象）\n\n- **底层**：`ThreadLocal`，每个请求线程独立\n\n- **项目中的用法**：`TokenAuthenticationFilter` 把用户信息存进去，Service 层通过`SecurityContextHolder.getContext().getAuthentication()` 取出来。\n\n## `Authentication`（认证对象）\n\n- **作用**：表示当前用户的身份和权限\n\n- **常态实现**：`UsernamePasswordAuthenticationToken`\n\n- **状态**：\n\n    - 未认证：`authenticated = false`（登录前）。\n\n    - 已认证：`authenticated = true`（登录成功后）。\n\n## `AuthenticationManager`（认证管理器）\n\n- **作用**：认证的核心入口，负责校验用户名、密码\n\n- **实现类**：`ProviderManager`，它会遍历多个 `AuthenticationProvider` 去干活\n\n- **项目中：**`DaoAuthenticationProvider`（配置的数据源认证提供者）。\n\n# 四、后端实现登录流程\n\n```Java\n前端发送 POST /login（JSON）\n        ↓\n① JwtAuthenticationFilter（拦截 /login）\n        ↓ 解析 JSON → 提取 username/password\n        ↓ 封装为 UsernamePasswordAuthenticationToken（未认证的 Authentication 对象）\n        ↓ 调用 getAuthenticationManager().authenticate()\n        ↓\n② DaoAuthenticationProvider（认证提供者）\n        ↓ 调用 UserDetailServiceImpl.loadUserByUsername(username)\n        ↓ 从数据库查用户信息 + 角色\n        ↓ 用 PasswordEncoder.matches(明文, 密文) 比对密码\n        ↓\n    ✅ 成功 → 创建已认证的 Authentication 对象\n        ↓ 父类回调 RestAuthenticationSuccessHandler\n        ↓ 生成 JWT Token 返回前端\n    ❌ 失败 → 抛出 BadCredentialsException\n        ↓ 父类回调 RestAuthenticationFailureHandler\n        ↓ 返回 JSON 错误信息\n        ↓\n③ 前端保存 Token（localStorage / Cookie）\n        ↓ 后续请求携带 Authorization: Bearer <Token>\n        ↓\n④ TokenAuthenticationFilter（拦截所有请求）\n        ↓ 从请求头提取 Token\n        ↓ 验签、查过期（validateToken）\n        ↓ 解析用户名 → loadUserByUsername\n        ↓ 存入 SecurityContextHolder\n        ↓\n⑤ AuthorizationFilter（Spring Security 内置）\n        ↓ 从 SecurityContextHolder 取 Authentication\n        ↓ 匹配 URL 权限规则（/admin/** 需认证）\n        ↓\n    ✅ 有权限 → 放行 → Controller\n    ❌ 无权限 → AccessDeniedException → RestAccessDeniedHandler\n    ❌ 无 Token → AuthenticationException → RestAuthenticationEntryPoint\n```\n\n# 五、关键类与职责\n\n|**类名**|**类型**|**核心职责**|\n|---|---|---|\n|`WebSecurityConfig`|配置类|总安全配置：禁用 CSRF/表单登录、注册两个过滤器、设置无状态会话、配置路径权限|\n|`JwtAuthenticationSecurityConfig`|装配类|组装 `JwtAuthenticationFilter` \\+ `DaoAuthenticationProvider` \\+ Handlers，并注入 Spring Security。|\n|`JwtAuthenticationFilter`|过滤器（登录）|拦截 `/login` POST，读取 JSON 体，调用认证管理器，开启认证流程。|\n|`TokenAuthenticationFilter`|过滤器（校验）|拦截所有请求，从请求头提取 Token，校验并存入 `SecurityContextHolder`。|\n|`UserDetailServiceImpl`|服务类|实现 `UserDetailsService`，根据用户名查数据库（用户信息 \\+ 角色），返回 `UserDetails`。|\n|`JwtTokenHelper`|工具类|生成 Token、解析 Token、验证 Token（签名 \\+ 有效期）。|\n|`PasswordEncoderConfig`|配置类|提供 BCrypt 密码编码器 Bean。|\n|`RestAuthenticationSuccessHandler`|处理器|认证成功后生成 JWT，写入响应。|\n|`RestAuthenticationFailureHandler`|处理器|认证失败后返回 JSON 错误。|\n|`RestAuthenticationEntryPoint`|处理器|未登录访问受保护资源时返回 401 JSON。|\n|`RestAccessDeniedHandler`|处理器|已登录但权限不足时返回 403 JSON。|\n|`GlobalExceptionHandler`|全局异常处理器|统一处理 Controller 层抛出的业务异常（`BizException`）、参数校验异常、系统异常。|\n|`Response<T>`|响应封装类|统一所有接口的返回格式：`{ success, message, errorCode, data }`。|\n\n\n\n');
INSERT INTO `t_article_content` VALUES (12, 12, '苹果');

-- ----------------------------
-- Table structure for t_article_tag_rel
-- ----------------------------
DROP TABLE IF EXISTS `t_article_tag_rel`;
CREATE TABLE `t_article_tag_rel`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `article_id` bigint(20) UNSIGNED NOT NULL COMMENT '文章id',
  `tag_id` bigint(20) UNSIGNED NOT NULL COMMENT '标签id',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_article_id`(`article_id`) USING BTREE,
  INDEX `idx_tag_id`(`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 95 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章对应标签关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_article_tag_rel
-- ----------------------------
INSERT INTO `t_article_tag_rel` VALUES (71, 10, 8);
INSERT INTO `t_article_tag_rel` VALUES (72, 10, 30);
INSERT INTO `t_article_tag_rel` VALUES (73, 10, 29);
INSERT INTO `t_article_tag_rel` VALUES (84, 11, 7);
INSERT INTO `t_article_tag_rel` VALUES (85, 11, 8);
INSERT INTO `t_article_tag_rel` VALUES (86, 11, 17);
INSERT INTO `t_article_tag_rel` VALUES (87, 9, 29);
INSERT INTO `t_article_tag_rel` VALUES (88, 9, 8);
INSERT INTO `t_article_tag_rel` VALUES (89, 9, 9);
INSERT INTO `t_article_tag_rel` VALUES (90, 9, 7);
INSERT INTO `t_article_tag_rel` VALUES (91, 9, 25);
INSERT INTO `t_article_tag_rel` VALUES (92, 12, 24);
INSERT INTO `t_article_tag_rel` VALUES (93, 12, 25);
INSERT INTO `t_article_tag_rel` VALUES (94, 12, 28);

-- ----------------------------
-- Table structure for t_blog_settings
-- ----------------------------
DROP TABLE IF EXISTS `t_blog_settings`;
CREATE TABLE `t_blog_settings`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `logo` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '博客Logo',
  `name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '博客名称',
  `author` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '作者名',
  `introduction` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '介绍语',
  `avatar` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '作者头像',
  `github_homepage` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'GitHub 主页访问地址',
  `csdn_homepage` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'CSDN 主页访问地址',
  `gitee_homepage` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'Gitee 主页访问地址',
  `zhihu_homepage` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '知乎主页访问地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '博客设置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_blog_settings
-- ----------------------------
INSERT INTO `t_blog_settings` VALUES (1, 'http://127.0.0.1:9000/weblog/750eb3b0fc3d4e46b12ffd2ad2fe1f2a.png', 'hq的博客', 'hq', '热枕之心不可泯灭', 'http://127.0.0.1:9000/weblog/037285e16d114f0da6fe86405a6f95ec.jpg', 'https://github.com/love233niang-design', 'https://blog.csdn.net/2401_83600218?spm=1000.2115.3001.5343', 'https://gitee.com/invincible-genshin-impact-daw666666 ', 'https://www.zhihu.com/people/null-72-64-25');

-- ----------------------------
-- Table structure for t_category
-- ----------------------------
DROP TABLE IF EXISTS `t_category`;
CREATE TABLE `t_category`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '分类名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志位：0：未删除 1：已删除',
  `articles_total` int(11) NOT NULL DEFAULT 0 COMMENT '此分类下文章总数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`name`) USING BTREE,
  INDEX `idx_create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_category
-- ----------------------------
INSERT INTO `t_category` VALUES (1, '测试分类', '2026-06-20 15:51:22', '2026-06-20 15:51:22', 0, 2);
INSERT INTO `t_category` VALUES (2, 'test1', '2026-06-20 17:25:29', '2026-06-20 17:25:29', 0, 2);
INSERT INTO `t_category` VALUES (3, 'test2', '2026-06-20 17:25:35', '2026-06-20 17:25:35', 0, 0);
INSERT INTO `t_category` VALUES (5, 'test3', '2026-06-21 10:06:10', '2026-06-21 10:06:10', 0, 0);

-- ----------------------------
-- Table structure for t_statistics_article_pv
-- ----------------------------
DROP TABLE IF EXISTS `t_statistics_article_pv`;
CREATE TABLE `t_statistics_article_pv`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pv_date` date NOT NULL COMMENT '被统计的日期',
  `pv_count` bigint(20) UNSIGNED NOT NULL COMMENT 'pv访问量',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_pv_date`(`pv_date`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '统计表 - 文章 PV (访问量)' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_statistics_article_pv
-- ----------------------------
INSERT INTO `t_statistics_article_pv` VALUES (1, '2026-07-14', 39, '2026-07-13 23:00:00', '2026-07-13 23:00:00');

-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '标签id',
  `name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '标签名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '逻辑删除标志位：0：未删除 1：已删除',
  `articles_total` int(11) NOT NULL DEFAULT 0 COMMENT '此标签下文章总数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_name`(`name`) USING BTREE,
  INDEX `idx_create_time`(`create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 31 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文章标签表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_tag
-- ----------------------------
INSERT INTO `t_tag` VALUES (1, '标签1', '2026-07-01 14:12:45', '2026-07-01 14:12:45', 0, 0);
INSERT INTO `t_tag` VALUES (2, '标签2', '2026-07-01 14:12:45', '2026-07-01 14:12:45', 0, 0);
INSERT INTO `t_tag` VALUES (7, 'JAVA', '2026-07-01 16:34:25', '2026-07-01 16:34:25', 0, 2);
INSERT INTO `t_tag` VALUES (8, 'C++', '2026-07-01 16:34:25', '2026-07-01 16:34:25', 0, 3);
INSERT INTO `t_tag` VALUES (9, 'Python', '2026-07-01 16:34:25', '2026-07-01 16:34:25', 0, 1);
INSERT INTO `t_tag` VALUES (16, 'Java1', '2026-07-01 16:36:38', '2026-07-01 16:36:38', 0, 0);
INSERT INTO `t_tag` VALUES (17, 'html', '2026-07-01 19:55:37', '2026-07-01 19:55:37', 0, 1);
INSERT INTO `t_tag` VALUES (19, '222', '2026-07-01 19:55:51', '2026-07-01 19:55:51', 0, 0);
INSERT INTO `t_tag` VALUES (24, '新的标签1', '2026-07-03 15:43:04', '2026-07-03 15:43:04', 0, 1);
INSERT INTO `t_tag` VALUES (25, '新的标签2', '2026-07-03 15:43:04', '2026-07-03 15:43:04', 0, 2);
INSERT INTO `t_tag` VALUES (26, '新的标签3', '2026-07-03 15:43:57', '2026-07-03 15:43:57', 0, 0);
INSERT INTO `t_tag` VALUES (28, '新的更新标签3', '2026-07-03 20:32:03', '2026-07-03 20:32:03', 0, 1);
INSERT INTO `t_tag` VALUES (29, '新的更新标签4', '2026-07-03 20:32:03', '2026-07-03 20:32:03', 0, 2);
INSERT INTO `t_tag` VALUES (30, '10', '2026-07-03 21:53:01', '2026-07-03 21:53:01', 0, 1);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次更新时间',
  `is_deleted` tinyint(2) NOT NULL DEFAULT 0 COMMENT '逻辑删除：0：未删除 1：已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uk_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (2, 'admin', '$2a$10$UGrUGkyf1RjSWnVlnaCDWOKRLPz9KXV2FzfTwJMsLO8we9AnWUdPK', '2026-06-17 10:18:49', '2026-06-17 10:18:49', 0);
INSERT INTO `t_user` VALUES (3, 'test', '$2a$10$UGrUGkyf1RjSWnVlnaCDWOKRLPz9KXV2FzfTwJMsLO8we9AnWUdPK', '2026-06-17 20:29:18', '2026-06-17 20:29:18', 0);

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `role` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (1, 'admin', 'ROLE_ADMIN', '2023-07-07 01:21:15');
INSERT INTO `t_user_role` VALUES (2, 'test', 'ROLE_VISITOR', '2023-07-07 01:23:33');

SET FOREIGN_KEY_CHECKS = 1;
