package tw.idv.frank.customer.model.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import tw.idv.frank.common.constant.ParameterValidation;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Pattern(regexp = ParameterValidation.SPECIAL_SYMBOLS)
    private String name;

    @Pattern(regexp = ParameterValidation.PHONE_VERIFY)
    private String phone;

    @Pattern(regexp = ParameterValidation.SPECIAL_SYMBOLS)
    private String location;

    public Customer(Integer id){
        this.id = id;
    }
}
