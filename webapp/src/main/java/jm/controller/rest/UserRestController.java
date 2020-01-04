package jm.controller.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import jm.UserService;
import jm.dto.UserDTO;
import jm.dto.UserDtoService;
import jm.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/api/users")
@Api(value = "User rest",description = "Shows the User info")
public class UserRestController {

    private UserService userService;
    private UserDtoService userDtoService;

    private static final Logger logger = LoggerFactory.getLogger(
            UserRestController.class);

    UserRestController(UserService userService, UserDtoService userDtoService) {
        this.userService = userService;
        this.userDtoService = userDtoService;
    }

    // DTO compliant
    @ApiOperation(value = "Returns users")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful")
            }
    )
    @GetMapping
    public ResponseEntity<List<UserDTO>> getUsers() {
        logger.info("Список пользователей : ");
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            logger.info(user.toString());
        }
        List<UserDTO> userDTOList = userDtoService.toDto(users);
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    // DTO compliant
    @ApiOperation(value = "Create user")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful")
            }
    )
    @PostMapping(value = "/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDto) {
        User user = userDtoService.toEntity(userDto);
        userService.createUser(user);
        logger.info("Созданный пользователь : {}", user);
        return new ResponseEntity<>(userDtoService.toDto(user), HttpStatus.OK);
    }

    // DTO compliant
    @ApiOperation(value = "Returns user by ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id) {
        logger.info("Польщователь с id = {}", id);
        User user = userService.getUserById(id);
        logger.info(user.toString());
        UserDTO userDTO = userDtoService.toDto(user);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    // DTO compliant
    @ApiOperation(value = "Update user")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful")
            }
    )
    @PutMapping(value = "/update")
    @PreAuthorize("#userDTO.login == authentication.principal.username or hasRole('ROLE_OWNER')")
    public ResponseEntity updateUser(@RequestBody UserDTO userDTO) {
        User user = userDtoService.toEntity(userDTO);
        User existingUser = userService.getUserById(user.getId());
        if (existingUser == null) {
            logger.warn("Пользователь не найден");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        userService.updateUser(user);
        logger.info("Обновленный пользователь: {}", user);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "Delete user by ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful")
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        logger.info("Удален польщователь с id = {}", id);
        return ResponseEntity.ok(true);
    }

    // DTO compliant
    @ApiOperation(value = "Returns users by channel ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful")
            }
    )
    @GetMapping(value = "/channel/{id}")
    public ResponseEntity<List<UserDTO>> getAllUsersInThisChannel(@PathVariable("id") Long id) {
        logger.info("Список пользователей канала с id = {}", id);
        List<User> users = userService.getAllUsersInThisChannel(id);
        for (User user : users) {
            logger.info(user.toString());
        }
        List<UserDTO> userDTOList = userDtoService.toDto(users);
        return ResponseEntity.ok(userDTOList);
    }

    // DTO compliant
    @ApiOperation(value = "Returns loogged user")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful")
            }
    )
    @GetMapping(value = "/loggedUser")
    public ResponseEntity<UserDTO> getLoggedUserId(Principal principal) {
        User user = userService.getUserByLogin(principal.getName());
        logger.info("Залогированный пользователь : {}", user);
        UserDTO userDTO = userDtoService.toDto(user);
        return ResponseEntity.ok(userDTO);
    }

    // DTO compliant
    @ApiOperation(value = "Returns users by workspace ID")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successful")
            }
    )
    @GetMapping(value = "/workspace/{id}")
    public ResponseEntity<List<UserDTO>> getAllUsersInWorkspace(@PathVariable("id") Long id) {
        logger.info("Список пользователей Workspace с id = {}", id);
        List<UserDTO> userDTOsList = userService.getAllUsersInWorkspace(id);
        for (UserDTO user : userDTOsList) {
            logger.info(user.toString());
        }
        return ResponseEntity.ok(userDTOsList);
    }
}
