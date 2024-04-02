package tw.idv.frank.common.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import tw.idv.frank.common.constant.CommonCode;
import tw.idv.frank.common.dto.CommonResult;
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public CommonResult BadCredentialsExceptionHandler(BadCredentialsException e) {
        boolean isPasswordError = "Bad credentials".equals(e.getMessage());
        return new CommonResult<>(CommonCode.LOGIN_ERROR, isPasswordError ? "密碼錯誤!" : e.getMessage());
    }

    @ExceptionHandler(BaseException.class)
    public CommonResult BaseExceptionHandler(BaseException e) {
        return new CommonResult<>(e.getCommonCode());
    }

    /**
     * 未通過 @valid驗證的Exception
     *
     * @param e
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, HandlerMethodValidationException.class})
    public CommonResult ValidExceptionHandler (Exception e) {
        return new CommonResult(CommonCode.PARAMETER_ERROR);
    }

    /**
     * 請求參數格式錯誤的Exception
     *
     * @param e
     * @return
     */
    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public CommonResult ParameterTypeExceptionHandler (RuntimeException e) {
        return new CommonResult(CommonCode.PARAMETER_TYPE_ERROR);
    }
}
