package org.example.backendjava.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
@ConditionalOnProperty(prefix = "app", name = "logging.enabled", havingValue = "true", matchIfMissing = true)
public class LoggingAspect {

    // ANSI-коды цветов
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";

    private final ObjectMapper mapper = new ObjectMapper();

    public LoggingAspect() {
        mapper.findAndRegisterModules();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Pointcut("execution(* org.example.backendjava..controller..*(..))")
    public void controllerMethods() {}

    @Around("controllerMethods()")
    public Object logRequestResponse(ProceedingJoinPoint joinPoint) throws Throwable {

        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();

        // --- 1. LOG REQUEST ---
        StringBuilder reqLog = new StringBuilder();
        reqLog.append(CYAN).append("\n========== [REQUEST] ==========\n");
        reqLog.append("Controller: ").append(className).append("\n");
        reqLog.append("Method:     ").append(methodName).append("\n");

        if (args.length > 0) {
            String argsString = Arrays.stream(args)
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            reqLog.append("Arguments:  ").append(argsString).append("\n");
        } else {
            reqLog.append("Arguments:  No arguments\n");
        }
        reqLog.append("===============================").append(RESET);
        log.info(reqLog.toString());

        long startTime = System.currentTimeMillis();
        Object result;

        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            log.error(RED + "\n========== [EXCEPTION] ==========\n" +
                            "Method:  {}\n" +
                            "Error:   {}\n" +
                            "Message: {}\n" +
                            "=================================" + RESET + "\n\n",
                    methodName, e.getClass().getSimpleName(), e.getMessage());
            throw e;
        }

        long duration = System.currentTimeMillis() - startTime;

        // --- 2. LOG RESPONSE ---
        StringBuilder respLog = new StringBuilder();
        respLog.append(GREEN).append("\n========== [RESPONSE] ==========\n");
        respLog.append("Time taken: ").append(duration).append(" ms\n");

        Object body = result;
        if (result instanceof ResponseEntity) {
            body = ((ResponseEntity<?>) result).getBody();
        }

        if (body instanceof Collection) {
            Collection<?> collection = (Collection<?>) body;
            int size = collection.size();
            respLog.append("Result Type: List (Total: ").append(size).append(")\n");

            // Берем первые 10 элементов
            List<?> limitedList = collection.stream().limit(10).toList();

            // --- ЛОГИКА ОКРАШИВАНИЯ СПИСКА ---
            try {
                respLog.append("Data (First 10):\n[\n");

                List<String> jsonItems = new ArrayList<>();

                for (Object item : limitedList) {
                    // Превращаем отдельный элемент в JSON
                    String itemJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(item);

                    // Если JSON содержит слово "ERROR" (регистрозависимо), красим в КРАСНЫЙ
                    // Иначе оставляем стандартный цвет (или можно явно задать зеленый)
                    if (itemJson.contains("ERROR")) {
                        jsonItems.add(RED + itemJson + GREEN); // Возвращаем зеленый цвет после красного блока
                    } else {
                        jsonItems.add(itemJson);
                    }
                }

                // Собираем всё обратно через запятую
                respLog.append(String.join(",\n", jsonItems));
                respLog.append("\n]");

            } catch (Exception e) {
                respLog.append("Data error: ").append(e.getMessage());
            }

            if (size > 10) {
                respLog.append(YELLOW).append("\n   ... and ").append(size - 10).append(" more items hidden.").append(GREEN);
            }

        } else if (body != null) {
            try {
                String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(body);
                respLog.append("Result:\n").append(prettyJson);
            } catch (Exception e) {
                respLog.append("Result:     ").append(body);
            }
        } else {
            respLog.append("Result:     null");
        }

        respLog.append("\n=================================").append(RESET);
        respLog.append("\n\n\n");

        log.info(respLog.toString());

        return result;
    }
}