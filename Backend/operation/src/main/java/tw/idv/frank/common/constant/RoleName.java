package tw.idv.frank.common.constant;

import org.springframework.security.core.GrantedAuthority;

public enum RoleName implements GrantedAuthority{

    ADMIN,

    MANAGE,

    USER;

    @Override
    public String getAuthority() {
        return name();
    }
}
