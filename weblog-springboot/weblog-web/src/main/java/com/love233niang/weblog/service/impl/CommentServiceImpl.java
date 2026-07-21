package com.love233niang.weblog.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.love233niang.weblog.common.enums.ResponseCodeEnum;
import com.love233niang.weblog.common.exception.BizException;
import com.love233niang.weblog.common.utils.Response;
import com.love233niang.weblog.model.vo.comment.FindQQUserInfoReqVO;
import com.love233niang.weblog.model.vo.comment.FindQQUserInfoRspVO;
import com.love233niang.weblog.service.CommentService;
import com.love233niang.weblog.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${api-key}")
    private String apiKey;

    @Override
    public Response findQQUserInfo(FindQQUserInfoReqVO findQQUserInfoReqVO) {
        String qq = findQQUserInfoReqVO.getQq();

        // 校验 QQ 号
        if (!StringUtil.isPureNumber(qq)) {
            log.warn("昵称输入的格式不是 QQ 号: {}", qq);
            throw new BizException(ResponseCodeEnum.NOT_QQ_NUMBER);
        }

        String url = String.format("http://api.guiguiya.com/api/qq_info?qq=%s&apiKey=%s", qq, apiKey);
        String result = restTemplate.getForObject(url, String.class);

        log.info("通过 QQ 号获取用户信息: {}", result);

        // 解析响应参数
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> map = objectMapper.readValue(result, Map.class);
            if (Objects.equals(map.get("code"), HttpStatus.OK.value())) {
                // 获取相应参数中 data 节点下的数据
                Map<String, Object> data = (Map<String, Object>) map.get("data");

                if (!CollectionUtils.isEmpty(data)) {
                    // 获取用户头像、昵称、邮箱
                    return Response.success(FindQQUserInfoRspVO.builder()
                            .avatar(String.valueOf(data.get("avatar_apiurl_1")))
                            .nickname(String.valueOf(data.get("name")))
                            .mail(qq.trim() + "@qq.com") // 组合邮箱地址
                            .build());
                }
            }

            return Response.fail();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
