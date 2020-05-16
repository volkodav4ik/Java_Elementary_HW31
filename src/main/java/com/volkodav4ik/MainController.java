package com.volkodav4ik;


import com.volkodav4ik.acessingdatapostgresql.UserRepository;
import com.volkodav4ik.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public String addNewUser(@RequestParam String name, @RequestParam String email,  Map<String, Object> model) {
        Users newUser = new Users();
        newUser.setName(name);
        newUser.setEmail(email);
        userRepository.save(newUser);
        Iterable<Users> users = userRepository.findAll();
        model.put("users", users);
        return "main";
    }

    @GetMapping
    public String showAll(Map<String, Object> model) {
        Iterable<Users> users = userRepository.findAll();
        model.put("users", users);
        return "main";
    }

    @GetMapping(value = "/clear")
    public String deleteAll(Map<String, Object> model) {
        userRepository.deleteAll();
        Iterable<Users> users = userRepository.findAll();
        model.put("users", users);
        return "main";
    }

    @GetMapping(value = "/{id}")
    public String getMessageById(@PathVariable("id") Integer id,
                                 Map<String, Object> model) {
        Optional<Users> user = userRepository.findById(id);
        model.put("users", user.get());
        return "user";
    }
}
