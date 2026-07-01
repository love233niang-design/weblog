package com.quanxiaoha.weblog.admin.model.vo.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 接口出参类 VO
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FindTagPageListRspVO {
    // 标签 ID
    private Long id;

    // 标签名称
    private String name;

    // 创建时间
    private LocalDateTime createTime;
}
