package tw.idv.frank.common.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tw.idv.frank.common.constant.CommonCode;
import tw.idv.frank.common.dto.CommonResult;
import tw.idv.frank.common.util.JsonTool;

import java.io.IOException;
import java.io.PrintWriter;


@Component
public class AuthenticationEntryPointHandlerImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException, ServletException {

        // 設置響應狀態碼為 403 Forbidden
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        // 設置響應的內容類型為 JSON
        response.setContentType("application/json");
        // 設置響應的內容類型為 UTF-8
        response.setCharacterEncoding("UTF-8");

        CommonResult commonResult = new CommonResult<>(CommonCode.RE_LOGIN);
        String jsonString = JsonTool.toJson(commonResult);

        // 將 JSON 字符串寫入響應主體
        PrintWriter writer = response.getWriter();
        writer.write(jsonString);
        writer.flush(); // 將緩衝區的內容寫入到響應主體中
        writer.close(); // 關閉 PrintWriter 物件，釋放相關的資源
    }
}
