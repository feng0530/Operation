package tw.idv.frank.customer.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.idv.frank.common.constant.ParameterValidation;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerDto {

    @NotNull
    private Integer id;

    @Pattern(regexp = ParameterValidation.SPECIAL_SYMBOLS)
    private String name;

    @Pattern(regexp = ParameterValidation.PHONE_VERIFY)
    private String phone;

    @Pattern(regexp = ParameterValidation.SPECIAL_SYMBOLS)
    private String location;

    public UpdateCustomerDto(Integer id) {
        this.id = id;
    }
}
