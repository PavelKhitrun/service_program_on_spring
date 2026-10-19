package by.pavelkht.service_program_on_spring.http.rest;

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
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;
    private final OrderService orderService;
    private final OrderPartService orderPartService;
    private final WarrantyService warrantyService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto create(@ModelAttribute @Validated CreateUserDto createUserDto) {
        return  userService.createUser(createUserDto);
    }

    @GetMapping("/user/{id}")
    public UserDto getUser(@PathVariable("id") Long id) {
        return userService.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/user/{id}/delete")
    public void delete(@PathVariable("id") Long id) {
        if (!userService.deleteUser(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/orders/{userId}")
    public List<OrderDto> orderPage(@PathVariable("userId") Long id) {
        return orderService.findByUserId(id);
    }

    @GetMapping("/order-details/{orderId}")
    public List<OrderPartDto> orderDetailsPage(@PathVariable("orderId") Long id) {
        return orderPartService.findByOrderId(id);
    }

    @GetMapping("/users")
    public Page<UserDto> findAll(UserFilter filter,  Pageable pageable) {
        return userService.findAll(filter, pageable);
    }


    @PutMapping("/user/{id}/update")
    public UserDto update(@PathVariable("id") Long id,
                         @ModelAttribute @Validated EditUserDto editUserDto) {
        return userService
                .update(id, editUserDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/warranty/{orderId}")
    public WarrantyDto warrantyPage(@PathVariable("orderId") Long id) {
        return warrantyService.getWarrantyByOrderId(id)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/user/{userId}/avatar", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public byte[] avatar(@PathVariable("userId") Long id) {;
        return userService.findAvatar(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
