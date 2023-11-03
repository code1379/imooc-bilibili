CREATE TABLE `t_user_following` (
                                    `id` bigint NOT NULL COMMENT '主键id',
                                    `userId` bigint DEFAULT NULL COMMENT '用户id',
                                    `followingId` bigint DEFAULT NULL COMMENT '关注用户id',
                                    `groupId` int DEFAULT NULL COMMENT '关注分组id',
                                    `createTime` datetime DEFAULT NULL COMMENT '创建时间',
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户关注表';