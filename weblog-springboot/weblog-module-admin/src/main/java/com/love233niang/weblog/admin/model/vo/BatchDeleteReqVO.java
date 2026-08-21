package com.love233niang.weblog.admin.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "批量删除 VO")
public class BatchDeleteReqVO {

    @NotEmpty(message = "删除 ID 集合不能为空")
    private List<Long> ids;
}
