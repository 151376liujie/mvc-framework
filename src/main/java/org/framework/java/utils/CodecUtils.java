package org.framework.java.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 编码相关工具类
 * 
 * @author liujie
 * 
 */
public final class CodecUtils {

    private static final Logger LOGGER = LoggerFactory
	    .getLogger(CodecUtils.class);

    /**
     * url编码
     * 
     * @param source
     * @return
     */
    public static String encodeUrl(String source) {
	try {
	    return URLEncoder.encode(source, "UTF-8");
	} catch (UnsupportedEncodingException e) {
	    LOGGER.error(e.getMessage(), e);
	}
	return source;
    }

    /**
     * url解码
     * 
     * @param source
     * @return
     */
    public static String decodeUrl(String source) {
	try {
	    return URLDecoder.decode(source, "utf-8");
	} catch (UnsupportedEncodingException e) {
	    LOGGER.error(e.getMessage(), e);
	}
	return source;
    }

}

