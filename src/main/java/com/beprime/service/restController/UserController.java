package com.beprime.service.restController;

import com.beprime.persistance.entities.User;
import com.beprime.service.services.interfaceService.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {
    private final IUserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserController(IUserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping(value = "/users")
    public List<User> findAll() {

        return userService.findAllUsers();
    }

    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping(value = "/getUsersByPrivilege/{privilege}")
    public List<User> getAllUsersByPriviliege(@PathVariable String privilege) {
        return userService.findUsersByPrivilege(privilege);
    }

    @PostMapping("")
    public User save(@RequestBody @Valid User userDto) {
        return userService.save(userDto);
    }

    @PutMapping()
    public User update(@RequestBody @Valid User userDto) {
        return userService.update(userDto);
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable Long id) {
        userService.delete(id);
        return true;
    }

    @PostMapping(value = "/verifierPassword/{password}")
    public boolean verifierPassword(@PathVariable String password, @RequestBody User userDto) {
        User userDtoFound = this.userService.findById(userDto.getId());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        boolean isPasswordMatches = bCryptPasswordEncoder.matches(password, userDtoFound.getPassword());
        if (isPasswordMatches) {
            return true;
        } else return false;
    }

    @GetMapping("/getUserActive")
    List<User> getAllUserActive() {
        List<User> listUsers = userService.findAllUsers();
        List<User> resultUsers = new ArrayList<>();
        listUsers.forEach(userDto -> {
            if (userDto.isActive() == true) {
                resultUsers.add(userDto);
            }
        });
        return resultUsers;
    }

    @GetMapping("/getUserNoActive")
    List<User> getAllUserNoActive() {
        List<User> listUsers = userService.findAllUsers();
        List<User> resultUsers = new ArrayList<>();
        listUsers.forEach(userDto -> {
            if (userDto.isActive() == false) {
                resultUsers.add(userDto);
            }
        });
        return resultUsers;
    }

    @GetMapping("/findUserByUserName/{userName}")
    User findUserByUserName(@PathVariable String userName) {
        User user = userService.findUserByName(userName);
        if (user != null) {
            return user;
        } else {
            return null;
        }
    }

    @GetMapping("/existUserName/{userName}")
    boolean existUserName(@PathVariable String userName) {
        User user = userService.findUserByName(userName);
        int counter = 0;
        List<User> userDtoList = this.userService.findAllUsers();
        for (int i = 0; i < userDtoList.size(); i++) {
            if (userDtoList.get(i).getUserName().equals(userName) && userDtoList.get(i).getId() == user.getId()) {
                counter++;
            }
        }
        if (counter == 1) {

            return true;
        } else {
            return false;
        }
    }

    @GetMapping("/getCurretnUser")
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        User user;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
            System.out.println("***" + username);
            user = this.userService.findUserByName(username);
        } else {
            username = principal.toString();
            user = this.userService.findUserByName(username);
        }

        return user;
    }
}
