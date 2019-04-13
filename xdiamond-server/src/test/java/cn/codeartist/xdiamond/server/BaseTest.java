package cn.codeartist.xdiamond.server;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Map;
import java.util.Objects;

import static java.util.Collections.emptyMap;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 基础测试类
 */
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XDiamondApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MockMvc mockMvc;

    /**
     * GET方式的REST接口测试
     *
     * @param uri    URI
     * @param params 参数
     * @return 结果处理
     * @throws Exception 异常
     */
    protected ResultActions doGet(String uri, Map<String, Object> params) throws Exception {
        return getResultActions(get(uri), params);
    }

    /**
     * POST方式的REST接口测试
     *
     * @param uri    URI
     * @param params 参数
     * @return 结果处理
     * @throws Exception 异常
     */
    protected ResultActions doPost(String uri, Map<String, Object> params) throws Exception {
        return getResultActions(post(uri), params);
    }

    /**
     * POST方式的REST接口测试（RequestBody）
     *
     * @param uri  URI
     * @param json JSON请求体
     * @return 结果处理
     * @throws Exception 异常
     */
    protected ResultActions doPost(String uri, String json) throws Exception {
        return getResultActions(post(uri).contentType(MediaType.APPLICATION_JSON_UTF8).content(json), emptyMap());
    }

    /**
     * 构建Mock方法
     *
     * @param builder 构建器
     * @param params  参数
     * @return 结果处理
     * @throws Exception 异常
     */
    private ResultActions getResultActions(MockHttpServletRequestBuilder builder, Map<String, Object> params) throws Exception {
        params.forEach((key, value) -> {
            Objects.requireNonNull(value);
            builder.param(key, value.toString());
        });
        return mockMvc.perform(builder).andDo(print()).andExpect(status().isOk());
    }
}