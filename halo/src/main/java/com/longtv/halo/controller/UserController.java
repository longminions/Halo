package com.longtv.halo.controller;

import com.longtv.halo.dto.UserBean;
import com.longtv.halo.entity.User;
import com.longtv.halo.service.UserService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registerUser")
    public HttpStatus createUser(@RequestBody UserBean userBean) {
        userService.createUser(userBean);
        return HttpStatus.CREATED ;
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<List<UserBean>> getUserByEmail(@PathVariable String email) {
        List<UserBean> userBeans = userService.getUserByEmail(email);
        if (userBeans.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(userBeans);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserBean> updateUser(@PathVariable Long id, @RequestBody UserBean userBean) {
            userService.updateUser(id, userBean);
            return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.accepted().build();
    }
}
