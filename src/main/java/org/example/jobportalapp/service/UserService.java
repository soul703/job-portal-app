package org.example.jobportalapp.service;

import org.example.jobportalapp.dto.userDTO.UserRequestDTO;
import org.example.jobportalapp.dto.userDTO.UserResponseDTO;
import org.example.jobportalapp.dto.userDTO.UserUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    /**
     * Lay danh sach tat ca nguoi dung.
     *
     * @return danh sach nguoi dung
     */
    Page<UserResponseDTO> getAllUsers(Pageable pageable);
    /**
     * Lay thong tin nguoi dung theo ID.
     * @param Pageable pageable
     * @return thong tin nguoi dung
     */
    UserResponseDTO getUserById(String userId);
    /**
     * Tao moi mot nguoi dung moi.
     *
     * @param userRequestDTO thong tin nguoi dung moi
     * @return thong tin nguoi dung da tao tra ve
     */
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);
    /**
     * Cap nhat thong tin nguoi dung.
     *
     * @param userId dia chi ID cua nguoi dung can cap nhat
     * @param userUpdateDTO thong tin cap nhat nguoi dung
     * @return thong tin nguoi dung da cap nhat tra ve
     */
    UserResponseDTO updateUser(String userId, UserUpdateDTO userUpdateDTO);

    /**
     * Xoa mot nguoi dung theo ID.
     *
     * @param userId dia chi ID cua nguoi dung can xoa
     */
    void deleteUser(String userId);
}
