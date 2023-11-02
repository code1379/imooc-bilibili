CREATE TABLE `t_user_info` (
                               `id` bigint NOT NULL COMMENT '主键',
                               `userId` bigint DEFAULT NULL COMMENT '用户id',
                               `nick` varchar(100) DEFAULT NULL COMMENT '昵称',
                               `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
                               `sign` text COMMENT '个性签名',
                               `gender` varchar(2) DEFAULT NULL COMMENT '性别：0男 1女 2未知',
                               `birth` varchar(20) DEFAULT NULL COMMENT '生日',
                               `createTime` datetime DEFAULT NULL COMMENT '创建日期',
                               `updateTime` datetime DEFAULT NULL COMMENT '更新日期',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户基本信息表';