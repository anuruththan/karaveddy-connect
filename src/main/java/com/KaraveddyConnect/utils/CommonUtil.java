package com.KaraveddyConnect.utils;

import com.KaraveddyConnect.aspects.interfaces.LogException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;

/**
 * @author Anuruththan-local
 * @org Smartzi
 * @since 2026-09-22 17:08 PM
 **/
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtil {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @LogException
    public static String toJSON(Object payload) {
        String jsonResponse = null;
        ObjectWriter objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
        try {
            jsonResponse = objectWriter.writeValueAsString(payload);
        } catch (JacksonException e) {
            log.error("Error while converting object to JSON: {}", e.getMessage());
//            throw new BusinessException(e.toString());
        }
        return jsonResponse;
    }

}
