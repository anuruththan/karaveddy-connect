package com.KaraveddyConnect.utils;

import com.KaraveddyConnect.aspects.interfaces.LogException;
import com.KaraveddyConnect.exception.BadRequestException;
import com.KaraveddyConnect.exception.BusinessException;
import com.KaraveddyConnect.exception.constant.ExceptionConstant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectWriter;

import java.util.Optional;

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
            throw new BusinessException(e.toString());
        }
        return jsonResponse;
    }

    @LogException
    public static <T> Optional<T> fromJSON(String payload, Class<T> targetClass) {
        T response;
        try {
            response = objectMapper.readerFor(targetClass)
                    .without(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .without(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
                    .readValue(payload);
        } catch (JacksonException e) {
            throw new BadRequestException(ExceptionConstant.COMMON_EXCEPTION);
        }
        return Optional.ofNullable(response);
    }

}
