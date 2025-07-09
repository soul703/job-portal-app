package org.example.jobportalapp.dto.ApiResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private String status; // SUCCESS hoặc ERROR
    private String message; // Mô tả kết quả xử lý
    private T data;         // Dữ liệu thành công (nếu có)
    private Object errors;  // Lỗi chi tiết (nếu có)
    private final LocalDateTime timestamp = LocalDateTime.now(); // Tự động gán thời điểm phản hồi

    /**
     * Phương thức static để tạo response thành công có kèm dữ liệu.
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>("SUCCESS", message, data, null);
    }

    /**
     * Phương thức static để tạo response thành công không kèm dữ liệu.
     */
    public static <T> ApiResponse<T> success(String message) {
        return new ApiResponse<>("SUCCESS", message, null, null);
    }

    /**
     * Phương thức static để tạo response thất bại kèm lỗi chi tiết.
     */
    public static <T> ApiResponse<T> error(String message, Object errors) {
        return new ApiResponse<>("ERROR", message, null, errors);
    }

    /**
     * Phương thức static để tạo response thất bại không kèm lỗi chi tiết.
     */
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>("ERROR", message, null, null);
    }
}