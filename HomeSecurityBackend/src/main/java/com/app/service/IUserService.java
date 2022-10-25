package com.app.service;

import com.app.dto.RealEstateDTO;
import com.app.dto.UserDTO;
import com.app.dto.UserRealEstateDTO;
import com.app.model.RealEstate;
import com.app.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {

    List<User> findAll();
    User findById(Integer id);
    User findByUsername(String username);
    void delete(String username);
    User save(User user);
    User createUser(UserDTO userDTO);
    User updateUser(UserDTO userDTO);
    User lockUser(String username);
    String hashPassword(String pass);
    List<UserRealEstateDTO> findUserRealEstates(Integer id);
    void editAccessToRealEstate(UserRealEstateDTO userRealEstateDTO);
    List<User> findUsersInRealEstate(Integer id);
    void editUserRealEstates(Integer ownerId, RealEstate realEstate);
    List<User> findAllByRole(String role);
    User addRealEstateToUser(Integer userId, Integer realEstateId);
    User removeUserFromRealEstate(Integer userId, Integer realEstateId);
}
