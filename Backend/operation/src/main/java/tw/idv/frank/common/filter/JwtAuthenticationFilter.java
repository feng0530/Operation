package tw.idv.frank.common.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tw.idv.frank.common.constant.CommonCode;
import tw.idv.frank.common.constant.RoleName;
import tw.idv.frank.common.exception.BaseException;
import tw.idv.frank.common.service.TokenService;
import tw.idv.frank.users.model.dto.LoginUser;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

//        String requestURI = request.getRequestURI();
//        // 如果是某些特定的 URL，則不執行驗證
//        if("/".equals(requestURI)) {
//            filterChain.doFilter(request, response);
//            return;
//        }

        // 取得 request header 的值
        String access = request.getHeader(HttpHeaders.AUTHORIZATION);
        String refresh = request.getHeader("refreshToken");

        if (access != null) {
            String accessToken = access.replace("Bearer ", "");
            String refreshToken = refresh.replace("Bearer ", "");

            // 解析 token
            Claims tokenPayload = tokenService.parseToken(accessToken);

            if(!tokenPayload.isEmpty()){
                String roleName = (String) tokenPayload.get("roleName");

                // 將使用者身份與權限傳遞給 Spring Security
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        null,
                        null,
                        List.of(RoleName.valueOf(roleName))
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {
                Claims newToken = tokenService.refreshAccessToken(refreshToken);

                String newAccess = (String) newToken.get("accessToken");
                String newRefresh = (String) newToken.get("refreshToken");

                // 瀏覽器中為 "Accesstoken","Refreshtoken"
                response.setHeader("accessToken", newAccess);
                response.setHeader("refreshToken", newRefresh);

                // 增加 CORS的安全清單，未在安全清單中的 Header，無法在跨域請求中被 JS獲取
                response.setHeader("Access-Control-Expose-Headers","Accesstoken,Refreshtoken");

                tokenPayload = tokenService.parseToken(newAccess);

                if(!tokenPayload.isEmpty()){
                    String roleName = (String) tokenPayload.get("roleName");

                    // 將使用者身份與權限傳遞給 Spring Security
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            null,
                            null,
                            List.of(RoleName.valueOf(roleName))
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        // 將 request 送往 Controller 或下一個 filter
        filterChain.doFilter(request, response);
    }
}