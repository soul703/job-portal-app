package org.example.jobportalapp.dto.ApiResponse;

public class ApiResponse <T> {
    private final String message;
    private final T data;
    private final boolean success;

   public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(message, data, true);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(message, null, false);
    }

    private ApiResponse(String message, T data, boolean success) {
        this.message = message;
        this.data = data;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public boolean isSuccess() {
        return success;
    }
}
