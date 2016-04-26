package org.framework.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json序列化工具类
 *
 * @author liujie
 */
public final class JsonUtils {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 对象转换为 json string
     *
     * @param o
     * @return
     * @throws JsonProcessingException
     */
    public static String toJsonString(Object o) throws JsonProcessingException {
        return MAPPER.writeValueAsString(o);
    }

    /**
     * json 字符串转对象
     *
     * @param clazz
     * @param jsonString
     * @return
     * @throws Exception
     */
    public static <T> T readJson(Class<T> clazz, String jsonString)
            throws Exception {
        return MAPPER.readValue(jsonString, clazz);
    }

}
