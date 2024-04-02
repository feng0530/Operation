package tw.idv.frank.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonCode {

    SUCCESS(200, "Success"),

    CREATE(200,"新增成功!"),

    READ(200,"查詢成功!"),

    UPDATE(200,"修改成功!"),

    DELETE(200,"刪除成功!"),

    PARAMETER_ERROR(400,"請求的參數未通過檢查規則!"),

    PARAMETER_TYPE_ERROR(400,"請求的參數型別有誤!"),

    Required_Parameter_Missing(400, "缺少必填的參數"),

    UPDATE_CONTENT_IS_NULL(400,"修改內容不得為空!"),

    DELETE_CONTENT_IS_NULL(400,"刪除內容不得為空!"),

    LOGIN_ERROR(400, "帳號或密碼錯誤!"),

    NOT_FOUND(400, "查無此資料"),

    INSUFFICIENT_PERMISSIONS(403, "權限不足!"),

    TOKEN_ERROR(403, "Token 解析失敗!"),

    RE_LOGIN(403,"請重新登入!"),

    N999(999,"發生未知的錯誤!");

    private final Integer code;
    private String msg;
}
