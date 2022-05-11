//package ru.renett.controllers.admin;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import ru.renett.service.impl.UserServiceImpl;
//
//
//public class AdminController {
//    private UserServiceImpl userService;
//
//    @Autowired
//    public AdminController(UserServiceImpl userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping("/admin")
//    public String userList(Model model) {
//        model.addAttribute("allUsers", userService.getAllUsers());
//        return "/WEB-INF/old/admin.jsp";
//    }
//
//    // http://localhost:8080/admin?userId=24&action=delete
//    @PostMapping("/admin")
//    public String deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
//                              @RequestParam(required = true, defaultValue = "" ) String action,
//                              Model model) {
//        if (action.equals("delete")){
//            userService.deleteUser(userId);
//        }
//        return "/WEB-INF/old/admin.jsp";
//    }
//
//    // http://localhost:8080/admin/gt/24 - список всех юзеров с id>24
//    @GetMapping("/admin/gt/{userId}")
//    public String  gtUser(@PathVariable("userId") Long userId, Model model) {
//        model.addAttribute("allUsers", userService.usergtList(userId));
//        return "/WEB-INF/old/admin.jsp";
//    }
//}
