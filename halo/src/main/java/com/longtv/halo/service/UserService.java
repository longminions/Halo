package com.longtv.halo.service;

import com.longtv.halo.dto.UserBean;
import com.longtv.halo.entity.User;
import com.longtv.halo.handle.UserNotFoundException;
import com.longtv.halo.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository  userRepository;

    public void createUser(UserBean userBean) {
        User user = new User();
        user.setId(null);
        user.setFirstName(userBean.getFirstName());
        user.setFirstName(user.getLastName());
        user.setEmail(userBean.getEmail());
        userRepository.save(user);
    }

    public List<UserBean> getUserByEmail(String email) {
        List<User> userList = userRepository.findUsersByEmail(email);
        List<UserBean> userBeansList = new ArrayList<>();

        for(User user : userList) {
            UserBean userBean = new UserBean();
            userBean.setLastName(user.getLastName());
            userBean.setFirstName(user.getFirstName());
            userBean.setId(user.getId());
            userBeansList.add(userBean);
        }

        return userBeansList;
    }

    public UserBean updateUser(Long id, UserBean userBean) {
        User user = userRepository.findOneUsersById(id).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));;
        if(user == null) {

        }
    }
}
