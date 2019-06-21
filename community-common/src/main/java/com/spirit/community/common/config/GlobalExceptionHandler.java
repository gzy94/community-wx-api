package com.spirit.community.common.config;

import com.spirit.community.common.model.BaseResponse;
import com.spirit.community.common.model.BaseResultCode;
import org.hibernate.validator.internal.engine.path.PathImpl;
import com.spirit.community.common.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public Object badArgumentHandler(IllegalArgumentException e) {
        e.printStackTrace();
        return ResponseUtil.badArgumentValue();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public Object badArgumentHandler(MethodArgumentTypeMismatchException e) {
        e.printStackTrace();
        return ResponseUtil.badArgumentValue();
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public Object badArgumentHandler(MissingServletRequestParameterException e) {
        e.printStackTrace();
        return ResponseUtil.badArgumentValue();
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Object badArgumentHandler(HttpMessageNotReadableException e) {
        e.printStackTrace();
        return ResponseUtil.badArgumentValue();
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public Object badArgumentHandler(ValidationException e) {
        e.printStackTrace();
        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException exs = (ConstraintViolationException) e;
            Set<ConstraintViolation<?>> violations = exs.getConstraintViolations();
            for (ConstraintViolation<?> item : violations) {
                String message = ((PathImpl) item.getPropertyPath()).getLeafNode().getName() + item.getMessage();
                return ResponseUtil.fail(402, message);
            }
        }
        return ResponseUtil.badArgumentValue();
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object seriousHandler(Exception e) {
        e.printStackTrace();
        return ResponseUtil.serious();
    }


    /**
     * 处理字段校验异常 MethodArgumentNotValidException
     *
     * @author Chaoyi.Guo
     * @date 2018/10/25 11:23
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BaseResponse handleException(MethodArgumentNotValidException e) {
        BindingResult results = e.getBindingResult();
        String errorMsg = getErrorMsg(results);
        log.error(errorMsg, e);
        return new BaseResponse(BaseResultCode.FAILED_VALIDATION.getCode(), errorMsg.substring(0, errorMsg.length() - 1));
    }

    private String getErrorMsg(BindingResult results) {
        StringBuilder sb = new StringBuilder();
        if (results.hasErrors()) {
            results.getAllErrors().stream().forEach(
                    result -> {
                        FieldError fieldError = (FieldError) result;
                        sb.append(fieldError.getDefaultMessage());
                        sb.append(",");
                    }
            );
        }
        return sb.toString();
    }
}
