package com.quanxiaoha.weblog.admin.model.vo.tag;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "标签模糊查询 VO")
public class SearchTagReqVo {
    @NotEmpty(message = "标签查询关键词不能为空")
    private String  key;
}
