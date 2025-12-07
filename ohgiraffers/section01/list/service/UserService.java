package com.ohgiraffers.section01.list.service;

import com.ohgiraffers.section01.list.dto.UserDTO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {

    private List<UserDTO> userList = new ArrayList<>();
private Map<String, UserDTO> userMap = new HashMap<>();// Map를 만들긴 했는데 어떻게 쓰는지 잘모르겠..

    public boolean register(UserDTO user) {
        if (findUserById(user.getUserId()) != null) return false;
        userList.add(user);
        return true;
    }

    public UserDTO login(String id, String pw) {

       /*  (UserDTO findUser = userMap.get(id);
         if (findUser == null ) return null;*/  // Map구문 적다 실패해서 주석으로만 남김

        for (UserDTO user : userList) {
            if (user.getUserId().equals(id) &&
                    user.getPassword().equals(pw)) {
                return user;
            }
        }
        return null;
    }

    public UserDTO findUserById(String id) {
        for (UserDTO user : userList) {
            if (user.getUserId().equals(id)) return user;
        }
        return null;
    }

    public UserDTO findUserID(String ID){
        return userMap.get(ID); // 여기 Map user id 조회 추가
    }



    public List<UserDTO> getAllUsers() {
        return userList;
    }
}
