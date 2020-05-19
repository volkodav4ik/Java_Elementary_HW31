package com.volkodav4ik;


import com.volkodav4ik.acessingdatapostgresql.UserRepository;
import com.volkodav4ik.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public String addNewUser(@RequestParam String name, @RequestParam String email, Map<String, Object> model) {
        try {
            if (userRepository.findByEmail(email) != null) {
                return "exist";
            }
            User newUser = new User();
            newUser.setName(name);
            newUser.setEmail(email);
            userRepository.save(newUser);
            Iterable<User> user = userRepository.findAll();
            model.put("user", user);
            return "main";
        } catch (Exception ex) {
            return "error";
        }
    }

    @GetMapping
    public String showAll(Map<String, Object> model) {
        Iterable<User> user = userRepository.findAll();
        model.put("user", user);
        return "main";
    }

    @GetMapping(value = "/clear")
    public String deleteAll(Map<String, Object> model) {
        userRepository.deleteAll();
        Iterable<User> user = userRepository.findAll();
        model.put("user", user);
        return "main";
    }

    @DeleteMapping(path = "/delete/{id}")
    public String delete(@PathVariable("id") String id, Map<String, Object> model) {
        try {
            userRepository.deleteById(id);
            Iterable<User> user = userRepository.findAll();
            model.put("user", user);
            return "main";
        } catch (Exception ex) {
            return "error";
        }
    }


}
