package tw.idv.frank.common.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tw.idv.frank.common.constant.CommonCode;
import tw.idv.frank.common.constant.RoleName;
import tw.idv.frank.common.dto.LoginRequest;
import tw.idv.frank.common.dto.LoginResponse;
import tw.idv.frank.common.exception.BaseException;
import tw.idv.frank.users.model.dto.LoginUser;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenService {

    @Autowired
    private AuthenticationProvider authenticationProvider;

    // JWT 金鑰
    private Key secretKey;

    // JWT 解析器
    private JwtParser jwtParser;

    @PostConstruct
    private void init() {

        String key = "VincentIsRunningBlogForProgrammingLearner";
        secretKey = Keys.hmacShaKeyFor(key.getBytes());

        jwtParser = Jwts.parserBuilder()
                        .setSigningKey(secretKey)
                        .build();
    }

    public Claims parseToken(String token) {

        try {
            return jwtParser.parseClaimsJws(token).getBody();
        }catch (SignatureException e) {
            System.out.println("JWT 的簽名無效，表示JWT 被竄改或者使用了錯誤的密鑰驗證");
        }
        catch (MalformedJwtException e) {
            System.out.println("JWT 的格式錯誤，可能是JWT 字符串被修改或者製作 JWT 的過程中發生了錯誤");
        }
        catch (ExpiredJwtException e) {
            System.out.println("JWT 已過期");
        }
        catch (UnsupportedJwtException e) {
            System.out.println("不支持的JWT，表示header 中指定的加密算法不被支持");
        }
        catch (IllegalArgumentException e) {
            System.out.println("表示提供了無效的JWT 字串");
        }
        return Jwts.claims();
    }

    public LoginResponse createToken(LoginRequest request) {

        // 封裝帳密
        Authentication authToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        // 執行帳密認證
        authToken = authenticationProvider.authenticate(authToken);

        // 認證成功後取得結果
        LoginUser loginUser = (LoginUser) authToken.getPrincipal();

        String accessToken = createAccessToken(loginUser.getUsername(), loginUser.getUsers().getRoleName());
        String refreshToken = createRefreshToken(loginUser.getUsername(), loginUser.getUsers().getRoleName());

        LoginResponse res = new LoginResponse();
        res.setAccessToken(accessToken);
        res.setRefreshToken(refreshToken);
        res.setId(loginUser.getUsers().getId());
        res.setEmail(loginUser.getUsers().getEmail());
        res.setRoleName(loginUser.getUsers().getRoleName());

        return res;
    }

    private String createRefreshToken(String username, RoleName roleName) {

        long expirationMillis = 6 * 10 * 1000L;

        return Jwts.builder()
                .claim("username", username)
                .claim("roleName", roleName)
                .setSubject("Refresh Token")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(secretKey)
                .compact();
    }

    private String createAccessToken(String username, RoleName roleName) {

        long expirationMillis = 6 * 2 * 1000L;

        return Jwts.builder()
                .claim("username", username)
                .claim("roleName", roleName)
                .setSubject("Access Token")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(secretKey)
                .compact();
    }

    public Claims refreshAccessToken(String refreshToken) {

        Claims payload = parseToken(refreshToken);

        if(payload.isEmpty()){
            return Jwts.claims();
        }else{
            String username = (String) payload.get("username");
            String roleName = (String) payload.get("roleName");
            Claims newToken = Jwts.claims();
            newToken.put("accessToken", createAccessToken(username, RoleName.valueOf(roleName)));
            newToken.put("refreshToken", createRefreshToken(username, RoleName.valueOf(roleName)));
            return newToken;
        }
    }
}
