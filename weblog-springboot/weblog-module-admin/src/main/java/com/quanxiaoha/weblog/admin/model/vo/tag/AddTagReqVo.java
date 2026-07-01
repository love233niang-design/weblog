package com.quanxiaoha.weblog.admin.model.vo.tag;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "添加标签 VO")
public class AddTagReqVo {
    @NotEmpty(message = "标签集合名称不能为空")
    private List<String> tags;
}
