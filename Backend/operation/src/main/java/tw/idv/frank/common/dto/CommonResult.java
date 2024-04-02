package tw.idv.frank.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tw.idv.frank.common.constant.CommonCode;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {

    private Integer code;

    private String message;

    private T result;

    public CommonResult(CommonCode commonCode) {
        this.code = commonCode.getCode();
        this.message = commonCode.getMsg();
    }

    public CommonResult(CommonCode commonCode, T result) {
        this.code = commonCode.getCode();
        this.message = commonCode.getMsg();
        this.result = result;
    }
}
