package by.pavelkht.service_program_on_spring.http.controller;

import by.pavelkht.service_program_on_spring.database.entity.UserRole;
import by.pavelkht.service_program_on_spring.dto.*;
import by.pavelkht.service_program_on_spring.service.OrderPartService;
import by.pavelkht.service_program_on_spring.service.OrderService;
import by.pavelkht.service_program_on_spring.service.UserService;
import by.pavelkht.service_program_on_spring.service.WarrantyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController implements UserDetailsService {
    private final UserService userService;
    private final OrderService orderService;
    private final OrderPartService orderPartService;
    private final WarrantyService warrantyService;

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model, @ModelAttribute CreateUserDto createUserDto) {
        model.addAttribute("createUser", createUserDto);
        return "user/registration";
    }

    @PostMapping("/registration")
    public String create(@ModelAttribute @Validated CreateUserDto createUserDto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/registration";
        }
        var userDto = userService.createUser(createUserDto);
        return "redirect:user/" + userDto.getId();
    }

    @GetMapping("/user")
    public String redirectToUserPage(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails.getAuthorities().contains(UserRole.ADMIN)) {
            return "redirect:/users";
        }else  {
            var user = userService
                    .findByUsername(userDetails.getUsername())
                    .orElseThrow(()->new UsernameNotFoundException("User not found"));
            return "redirect:/user/" + user.getId();
        }
    }

    @GetMapping("/user/{id}")
    public String userPage(@PathVariable("id") Long id,
                           @AuthenticationPrincipal UserDetails userDetails,
                           Model model) {

        return userService.findById(id).map(userDto -> {
            if (userDetails.getAuthorities().contains(UserRole.ADMIN)) {
                model.addAttribute("user", userDto);
                return "/user/useraccount";
            }
            if (userDetails.getUsername().equals(userDto.getLogin()) && userDetails.getAuthorities().contains(UserRole.USER)) {
                model.addAttribute("user", userDto);
                return "/user/useraccount";
            }else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id,
                         @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails.getAuthorities().contains(UserRole.ADMIN)) {
            if (!userService.deleteUser(id)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            return "redirect:/users";
        }

        var userDto = userService.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (userDetails.getAuthorities().contains(UserRole.USER) && userDetails.getUsername().equals(userDto.getLogin())) {
            if (!userService.deleteUser(id)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            return "redirect:/users";
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

    }

    @GetMapping("/orders/{id}")
    public String orderPage(@PathVariable("id") Long userId,
                            @AuthenticationPrincipal UserDetails userDetails,
                            Model model) {
        Long userDetailsId = userService.findByUsername(userDetails.getUsername())
                .map(user -> user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (userDetails.getAuthorities().contains(UserRole.ADMIN)) {
            model.addAttribute("orders", orderService.findByUserId(userId));
            model.addAttribute("userId", userId);
            return "user/orders";
        }
        if (userDetails.getAuthorities().contains(UserRole.USER) && userDetailsId == userId) {
            model.addAttribute("orders", orderService.findByUserId(userId));
            model.addAttribute("userId", userId);
            return "user/orders";
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/order-details/{orderId}")
    public String orderDetailsPage(@PathVariable("orderId") Long id,
                                   @AuthenticationPrincipal UserDetails userDetails,
                                   Model model) {

        var userDto = orderService
                .getUserByOrderId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (userDetails.getAuthorities().contains(UserRole.ADMIN)) {
            model.addAttribute("items", orderPartService.findByOrderId(id));
            model.addAttribute("userId", userDto.getId());
            return "user/orderdetails";
        }
        if (userDetails.getAuthorities().contains(UserRole.USER) && userDto.getLogin().equals(userDetails.getUsername())) {
            model.addAttribute("items", orderPartService.findByOrderId(id));
            model.addAttribute("userId", userDto.getId());
            return "user/orderdetails";
        } else  {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/users")
    public String findAll(Model model, UserFilter filter,  Pageable pageable) {

        Page<UserDto> pages = userService.findAll(filter, pageable);

        var fields = Arrays.stream(pages.getContent().get(0).getClass().getDeclaredFields())
                .map(f -> f.getName()).toList();

        Map<String, String> directions = Map.of(
                "По возрастанию", "asc",
                "По убыванию", "desc"
        );

        model.addAttribute("roles", UserRole.values());
        model.addAttribute("fields", fields);
        model.addAttribute("filter", filter);
        model.addAttribute("directions", directions);
        model.addAttribute("users", PageResponse.of(pages));

        return "/user/users";
    }

    @GetMapping("/profile/edit/{id}")
    public String profileEditPage(@PathVariable("id") Long userId,
                                  @AuthenticationPrincipal UserDetails userDetails,
                                  Model model) {
        return  userService.findById(userId).map(userDto -> {
            if (userDetails.getAuthorities().contains(UserRole.ADMIN)) {
                model.addAttribute("user", userDto);
                return "/user/editprofile";
            }
            if (userDetails.getUsername().equals(userDto.getLogin()) && userDetails.getAuthorities().contains(UserRole.USER)){
                model.addAttribute("user", userDto);
                return "/user/editprofile";
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/profile/edit/{id}/update")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute @Validated EditUserDto editUserDto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/profile/edit/" + id;
        }
        return userService
                .update(id, editUserDto)
                .map(it -> "redirect:/user/" + it.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/warranty/{orderId}")
    public String warrantyPage(@PathVariable("orderId") Long id,
                               @AuthenticationPrincipal UserDetails userDetails,
                               Model model) {

        var userDto = orderService
                .getUserByOrderId(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (userDetails.getAuthorities().contains(UserRole.ADMIN)) {
            model.addAttribute("defects", warrantyService.getWarrantyByOrderId(id).get());
            model.addAttribute("userId", userDto.getId());
            return "/user/warranty";
        }
        if (userDetails.getAuthorities().contains(UserRole.USER) && userDto.getLogin().equals(userDetails.getUsername())) {
            model.addAttribute("defects", warrantyService.getWarrantyByOrderId(id).get());
            model.addAttribute("userId", userDto.getId());
            return "/user/warranty";
        } else  {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService
                .findByUsername(username)
                .map(userDto ->
                        new org.springframework.security.core.userdetails.User(
                                userDto.getLogin(),
                                userDto.getPassword(),
                                Collections.singleton(userDto.getRole())
                )).orElseThrow(() -> new UsernameNotFoundException("Ошибка авторизации пользователя " + username));
    }
}
