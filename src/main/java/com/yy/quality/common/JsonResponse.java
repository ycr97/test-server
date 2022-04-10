package com.yy.quality.common;

import lombok.Data;

/**
 * @author ycr
 * @date 2022/4/8 22:32
 */
@Data
public class JsonResponse<T> {
    private static final String SUCCESS_MSG = "success";

    private static final String FAIL_MSG = "fail";

    private int code;

    private String msg;

    private T data;

    private JsonResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JsonResponse() {
    }

    public static <T> JsonResponse<T> success(T data) {
        JsonResponse<T> result = new JsonResponse<>();
        result.setCode(200);
        result.setMsg(SUCCESS_MSG);
        result.setData(data);
        return result;
    }

    public static JsonResponse<String> fail() {
        JsonResponse<String> result = new JsonResponse<>();
        result.setCode(500);
        result.setMsg(FAIL_MSG);
        return result;
    }
}
