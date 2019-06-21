package com.spirit.community.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 一些json与对象转换的工具集合类
 *
 * @author Yi.Wang2
 */
public class JsonUtils {

    /**
     * 日志服务类
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

    /**
     * JACKSON配置信息
     */
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        OBJECT_MAPPER = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.USE_DEFAULTS);
    }

    private JsonUtils() {
    }

    public static ObjectMapper getInstance() {
        return OBJECT_MAPPER;
    }

    /**
     * 使用Jackson 数据绑定 将对象转换为 json字符串
     * <p/>
     * 还可以 直接使用 JsonUtils.getInstance().writeValueAsString(Object obj)方式
     *
     * @param obj obj
     * @return String
     */
    public static String toJsonString(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (IOException e) {
            LOGGER.error("转换为json字符串失败,{}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * json字符串转化为 JavaBean
     * <p/>
     * 还可以直接JsonUtils.getInstance().readValue(String content,Class valueType)用这种方式
     *
     * @param <T>       T
     * @param content   content
     * @param valueType valueType
     * @return T
     */
    public static <T> T toJavaBean(String content, Class<T> valueType) {
        try {
            if (StringUtils.isEmpty(content)) {
                return null;
            }
            return OBJECT_MAPPER.readValue(content, valueType);
        } catch (IOException e) {
            LOGGER.error("json字符串转化为 javabean失败,{}", e.getMessage(), e);
        }
        return null;
    }

    public static <T> T toJavaBean(String content, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(content, typeReference);
        } catch (IOException var3) {
            LOGGER.error("json字符串转化为 javabean失败,{}", var3.getMessage(), var3);
            return null;
        }
    }

    /**
     * json字符串转化为list
     * <p/>
     * 还可以 直接使用 JsonUtils.getInstance().readValue(String content, new
     * TypeReference<List<T>>(){})方式
     *
     * @param <T>           T
     * @param content       content
     * @param typeReference List泛型类
     * @return List<T>
     */
    public static <T> List<T> toJavaBeanList(String content, TypeReference<List<T>> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(content, typeReference);
        } catch (IOException e) {
            LOGGER.error("json字符串转化为 list失败,原因:{}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 日期转换为时间戳输出（用于响应结果）
     */
    public static class TimestampSerializer extends JsonSerializer<Date> {

        @Override
        public void serialize(Date value, JsonGenerator jsonGenerator, SerializerProvider provider)
                throws IOException {
            if (value != null) {
                jsonGenerator.writeNumber(value.getTime());
            }
        }
    }

    /**
     * 时间戳转换为日期类型输入（用于请求参数）
     */
    public static class DateDeserializer extends JsonDeserializer<Date> {

        @Override
        public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            JsonNode node = jsonParser.getCodec().readTree(jsonParser);
            if (!node.isNull()) {
                return new Date(node.longValue());
            }
            return null;
        }
    }
}
