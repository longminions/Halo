package com.longtv.halo.controller;

import com.longtv.halo.dto.UserBean;
import com.longtv.halo.entity.User;
import com.longtv.halo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

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
        return userService.updateUser(id, userBean)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
