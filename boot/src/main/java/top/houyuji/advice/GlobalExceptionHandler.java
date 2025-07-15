package top.houyuji.advice;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import top.houyuji.common.base.R;
import top.houyuji.common.base.exception.ServiceException;
import top.houyuji.common.base.exception.UsernameNotFoundException;

import java.util.List;

import static top.houyuji.common.base.enums.ErrorCodeEnums.*;

/**
 * 全局异常处理
 * Created by Aurora on 2020/2/27.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = HandlerMethodValidationException.class)
    public R<Object> handleMethodValidationException(HandlerMethodValidationException e) {
        StringBuilder messageBuilder = new StringBuilder("参数错误: ");

        if (e.hasErrors()) {
            List<ParameterValidationResult> allValidationResults = e.getValueResults();

            // 遍历所有的验证结果
            for (ParameterValidationResult validationResult : allValidationResults) {
                List<MessageSourceResolvable> resolvableErrors = validationResult.getResolvableErrors();
                for (MessageSourceResolvable messageSourceResolvable : resolvableErrors) {
                    String defaultMessage = messageSourceResolvable.getDefaultMessage();
                    Object[] arguments = messageSourceResolvable.getArguments();

                    if (arguments != null && arguments.length > 0) {
                        DefaultMessageSourceResolvable argument = (DefaultMessageSourceResolvable) arguments[0];
                        messageBuilder.append(argument.getDefaultMessage()).append(", 错误信息: ").append(defaultMessage).append("; ");
                    } else {
                        messageBuilder.append("错误信息: ").append(defaultMessage).append("; ");
                    }
                }
            }
        } else {
            messageBuilder.append("未知验证错误");
        }

        String finalMessage = messageBuilder.toString().trim();
        return R.ok(finalMessage);
    }


    @ResponseBody
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R<Object> handleValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                String defaultMessage = fieldError.getDefaultMessage();
                if (defaultMessage != null && defaultMessage.startsWith("Failed to convert property value")) {
                    message = "参数" + fieldError.getField() + "类型错误";
                } else {
                    message = "参数" + fieldError.getField() + defaultMessage;
                }
            }
        }
        return R.error(message);
    }

    @ResponseBody
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public R<Object> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return R.error("参数" + e.getParameterName() + "必填");
    }


    @ResponseBody
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public R<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return R.error(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = NoResourceFoundException.class)
    public R<Object> handleNoResourceFoundException(NoResourceFoundException e) {
        return R.error(404, "路径" + e.getResourcePath() + "不存在");
    }

    @ResponseBody
    @ExceptionHandler(value = UsernameNotFoundException.class)
    public R<Object> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return R.error(USER_NOT_FOUND, null);
    }

    @ResponseBody
    @ExceptionHandler(value = NotLoginException.class)
    public R<Object> handleNotLoginException(NotLoginException e) {
        return R.error(USER_NOT_LOGIN, null);
    }

    @ResponseBody
    @ExceptionHandler(value = {NotPermissionException.class, NotRoleException.class})
    public R<Object> handleNotPermissionException(Exception e) {
        return R.error(PERMISSION_DENIED, null);
    }

    @ResponseBody
    @ExceptionHandler(value = ServiceException.class)
    public R<Object> handleSerialException(ServiceException e) {
        return R.error(e.getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public R<Object> handleUnknownException(Exception e) {
        log.error("未知异常", e);
        return R.error("未知异常，请联系管理员");
    }
}
