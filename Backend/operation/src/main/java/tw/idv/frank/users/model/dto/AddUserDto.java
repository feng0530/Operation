package tw.idv.frank.users.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import tw.idv.frank.common.constant.RoleName;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUserDto {

    @Email
    private String email;

    @NotBlank
    private String mima;

    @NotNull
    private RoleName roleName;

    @Nullable
    private Date createTime;
}
