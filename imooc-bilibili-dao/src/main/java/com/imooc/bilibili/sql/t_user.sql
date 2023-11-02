CREATE TABLE `t_user` (
                          `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                          `phone` varchar(100) DEFAULT NULL COMMENT '手机号',
                          `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
                          `password` varchar(255) DEFAULT NULL COMMENT '密码',
                          `salt` varchar(50) DEFAULT NULL COMMENT '盐值',
                          `createTime` datetime DEFAULT NULL COMMENT '创建时间',
                          `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;