package com.app.service.implementation;

import com.app.dto.RealEstateDTO;
import com.app.dto.UserDTO;
import com.app.dto.UserRealEstateDTO;
import com.app.model.RealEstate;
import com.app.model.Role;
import com.app.model.User;
import com.app.repository.UserRepository;
import com.app.service.IRealEstateService;
import com.app.service.IRoleService;
import com.app.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final IRoleService roleService;
    private final IRealEstateService realEstateService;

    @Autowired
    public UserService(UserRepository userRepository, IRoleService roleService, IRealEstateService realEstateService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.realEstateService = realEstateService;
    }

    @Override
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User findById(Integer id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(String username) {
        User user = findByUsername(username);
        user.setDeleted(true);
        this.save(user);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User createUser(UserDTO userDTO) {

        Role role = this.getUserRole(userDTO.getRole());
        User user = new User(userDTO.getFirstName(), userDTO.getLastName(), userDTO.getUsername(), userDTO.getEmail(),
                hashPassword(userDTO.getPassword()), false, false, role);
        return this.save(user);
    }

    @Override
    public User updateUser(UserDTO userDTO) {

        User loadedUser = this.findByUsername(userDTO.getUsername());
        loadedUser.setFirstName(userDTO.getFirstName());
        loadedUser.setLastName(userDTO.getLastName());

        Role role = this.getUserRole(userDTO.getRole());

        loadedUser.setRole(role);
        return this.save(loadedUser);
    }

    @Override
    public User lockUser(String username) {
        User user = this.findByUsername(username);
        if (user != null) {
            user.setAccountLocked(true);
            this.save(user);
        }
        return user;
    }

    public Role getUserRole(String role) {

        switch (role) {
            case "admin":
                role = "ROLE_ADMIN";
                break;
            case "owner":
                role = "ROLE_OWNER";
                break;
            case "tenant":
                role = "ROLE_TENANT";
                break;
        }
        return roleService.findRoleByName(role);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s);
    }

    @Override
    public String hashPassword(String pass) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(pass);
    }

    @Override
    public List<UserRealEstateDTO> findUserRealEstates(Integer id) {
        List<UserRealEstateDTO> userRealEstateDTOS = new ArrayList<>();

        List<RealEstate> realEstates = this.realEstateService.findAll();
        User user = this.findById(id);

        for (RealEstate realEstate: realEstates) {

            UserRealEstateDTO userRealEstateDTO;

            if (user.getRealEstates().stream().anyMatch(re -> re.getId().equals(realEstate.getId()))) {
                userRealEstateDTO = new UserRealEstateDTO(realEstate.getId(), realEstate.getName(),
                        true, user.getId());
            } else {
                userRealEstateDTO = new UserRealEstateDTO(realEstate.getId(), realEstate.getName(),
                        false, user.getId());
            }

            userRealEstateDTOS.add(userRealEstateDTO);
        }

        return userRealEstateDTOS;
    }

    @Override
    public void editAccessToRealEstate(UserRealEstateDTO userRealEstateDTO) {
        User user = this.findById(userRealEstateDTO.getUserId());
        List<RealEstate> userRealEstates = user.getRealEstates();

        RealEstate realEstate = this.realEstateService.findById(userRealEstateDTO.getId());

        if (userRealEstates.stream().anyMatch(re -> re.getId().equals(realEstate.getId()))) {
            if (!userRealEstateDTO.getAccess()) {

                int index = 0;
                for (int i = 0; i < userRealEstates.size(); i++) {
                    if (userRealEstates.get(i).getId().equals(realEstate.getId())) {
                        index = i;
                    }
                }
                userRealEstates.remove(index);
            }
        } else {
            if (userRealEstateDTO.getAccess()) {
                userRealEstates.add(realEstate);
            }
        }
        user.setRealEstates(userRealEstates);
        this.save(user);
    }

    @Override
    public List<User> findUsersInRealEstate(Integer id) {
        List<User> users = new ArrayList<>();
        for (User user: this.findAll()) {
            for (RealEstate userRealEstate: user.getRealEstates()) {
                if (userRealEstate.getId().equals(id)) {
                    users.add(user);
                }
            }
        }
        return users;
    }

    @Override
    public void editUserRealEstates(Integer ownerId, RealEstate realEstate) {
        User user = this.findById(ownerId);
        List<RealEstate> realEstates = user.getRealEstates();
        realEstates.add(realEstate);
        this.save(user);
    }

    @Override
    public List<User> findAllByRole(String role) {
        return this.userRepository.findAllByRole(role);
    }

    @Override
    public User addRealEstateToUser(Integer userId, Integer realEstateId) {
        User user = this.findById(userId);
        List<RealEstate> realEstates = user.getRealEstates();
        RealEstate realEstate = this.realEstateService.findById(realEstateId);
        realEstates.add(realEstate);
        return this.save(user);
    }

    @Override
    public User removeUserFromRealEstate(Integer userId, Integer realEstateId) {
        User user = this.findById(userId);
        RealEstate realEstate = new RealEstate();

        List<RealEstate> userRealEstates = user.getRealEstates();

        for (RealEstate userRealEstate: userRealEstates) {
            if (userRealEstate.getId().equals(realEstateId)) {
                realEstate = userRealEstate;
            }
        }
        userRealEstates.remove(realEstate);
        user.setRealEstates(userRealEstates);

        return this.save(user);
    }
}
