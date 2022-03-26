package com.cos.photogramstart.handler.aop;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import java.util.Arrays;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
@Aspect
@Slf4j
public class ValidationAdvice {

    /**
     * [1] * => 접근 제어자 관련 설정(public,private 등등)
     * [2] * => Controller의 접두사 부분 설정
     * [3] * => 각 컨트롤러 메소드 부분 설정
     * [4] (..) => 메소드 매개변수가 뭐든 상관 없다는 설정
     */

    /**
     * proceedingJointPoint : profile 함수의 모든 곳에 접근할 수 있는 변수 profile 함수보다 먼저 실행됨
     */

    @Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        HashMap<String, String> errorMap = new HashMap<>();

        Object[] args = proceedingJoinPoint.getArgs();

        Arrays.stream(args)
            .filter(arg -> arg instanceof BindingResult)
            .map(arg -> (BindingResult) arg)
            .filter(BindingResult::hasErrors)
            .map(BindingResult::getFieldErrors)
            .forEach(list -> list.forEach(error -> errorMap.put(error.getField(), error.getDefaultMessage())));

        if (!errorMap.isEmpty()) {
            throw new CustomValidationException("유효성 검사 실패", errorMap);
        }

        return proceedingJoinPoint.proceed();
    }

    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        return proceedingJoinPoint.proceed();
    }
}
