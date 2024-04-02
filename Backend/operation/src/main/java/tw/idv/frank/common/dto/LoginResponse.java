package tw.idv.frank.common.dto;

import lombok.Data;
import tw.idv.frank.common.constant.RoleName;

@Data
public class LoginResponse {

    private String accessToken;

    private String refreshToken;

    private Integer id;

    private String email;

    private RoleName roleName;
}
