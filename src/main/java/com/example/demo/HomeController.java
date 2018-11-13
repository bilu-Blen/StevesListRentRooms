package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    RoomRepository roomRepository;
    @Autowired
    private UserService userService;


    @RequestMapping("/")
    public String listRooms(Model model){
        model.addAttribute("rooms", roomRepository.findAll());
        return  "list";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/add")
    public String add(Model model){
        model.addAttribute("room", new Room());
        return "addroom";
    }

    @RequestMapping("/edit/{id}")
    public String editRoom(@PathVariable("id") long id, Model model){
        model.addAttribute("room",roomRepository.findById(id).get());
        return "addroom";
    }

    @PostMapping("/process")
    public String processForm(@Valid @ModelAttribute("room") Room room, BindingResult result){
        if(result.hasErrors()){
            return "addroom";
        }
        roomRepository.save(room);
        return "redirect:/";
    }

    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String showRegistrationPage(Model model){
        model.addAttribute("user", new User());
        return "register";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public String processRegistrationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model){
        model.addAttribute("user", user);
        if (result.hasErrors()){
            return "register";
        }
        else {
            userService.saveUser(user);
            model.addAttribute("message", "User Account Created");
        }
        return "list";
    }



/*
    public String admin()
*/


}
