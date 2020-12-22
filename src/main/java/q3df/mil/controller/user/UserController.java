package q3df.mil.controller.user;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import q3df.mil.dto.user.UserDto;
import q3df.mil.dto.user.UserPreview;
import q3df.mil.dto.user.UserUpdateDto;
import q3df.mil.entities.contacts.Contact;
import q3df.mil.myfeatures.SupClass;
import q3df.mil.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final SupClass supClass;

    @Autowired
    public UserController(UserService userService, SupClass supClass) {
        this.userService = userService;
        this.supClass = supClass;
    }


    @ApiOperation(value = "Find users with pagination")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @GetMapping(params = {"page", "size"})
    public ResponseEntity<List<UserPreview>> findUsers(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "15") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "firstName"));
        return ResponseEntity.ok(userService.findAll(pageRequest));
    }


    @ApiOperation(value = "Find user by id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "User successful found! "),
            @ApiResponse(code = 404, message = "User not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }


    // this operation exist in security /registration controller
//    @PostMapping
//    public ResponseEntity<?> saveUser(@Valid @RequestBody UserRegistrationDto userRegistrationDto)  {
//        UserDto userDto = userService.saveUser(userRegistrationDto);
//        URI location=
//                ServletUriComponentsBuilder
//                        .fromCurrentRequest()
//                        .path("/{id}")
//                        .buildAndExpand(userDto.getId())
//                        .toUri();
//        return ResponseEntity.created(location).build();
//    }


    @ApiOperation(value = "Update user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "User was successfully updated"),
            @ApiResponse(code = 403, message = "No permission to execute the operation"),
            @ApiResponse(code = 404, message = "If user with this id doesn't exist")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id,
                                              @Valid @RequestBody UserUpdateDto userUpdateDto,
                                              HttpServletRequest request) {
        supClass.checkPermission(request, id);
        return ResponseEntity.ok(userService.updateUser(userUpdateDto));
    }


    @ApiOperation(value = "Delete user", notes = "Marks a user as deleted")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 204, message = "User was successfully deleted"),
            @ApiResponse(code = 403, message = "No permission to execute the operation"),
            @ApiResponse(code = 404, message = "If user with this id doesn't exist")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, HttpServletRequest request) {
        supClass.checkPermission(request, id);
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @ApiOperation(value = "Find user in date range",
            notes = "Enter correct date (like in default values) or it will be thrown parse exception")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad date! Parse exception!")
    })
    @GetMapping(value = "/findByDate", params = {"page", "size", "before", "after"})
    public ResponseEntity<List<UserPreview>> findByBirthday(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "15") Integer size,
            @RequestParam(defaultValue = "1930-01-01") String before,
            @RequestParam(defaultValue = "2100-01-01") String after) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "birthday"));
        return ResponseEntity.ok(userService.findUserBetweenDates(before, after, pageRequest));
    }


    @ApiOperation(value = "Find user by country and city")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @GetMapping(value = "/findByCountryAndCity", params = {"page", "size", "country", "city"})
    public ResponseEntity<List<UserPreview>> findByCountryAndCity(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "15") Integer size,
            @RequestParam(defaultValue = "Belarus") String country,
            @RequestParam(defaultValue = "Minsk") String city) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "firstName"));
        return ResponseEntity.ok(userService.findByCountryAndCity(country, city, pageRequest));
    }


    @ApiOperation(value = "Find user by country and city",
            notes = "You can enter an approximate name, it is based on the like operator")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @GetMapping(value = "/findByFirstAndLastName", params = {"page", "size", "country", "city"})
    public ResponseEntity<List<UserPreview>> findByFirstAndLastName(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "15") Integer size,
            @RequestParam(defaultValue = "Mike") String firstName,
            @RequestParam(defaultValue = "Shinoda") String lastName) {
        PageRequest pageRequest = PageRequest
                .of(page, size, Sort.by(Sort.Direction.ASC, "firstName")
                        .and(Sort.by(Sort.Direction.ASC, "lastName")));
        return ResponseEntity.ok(userService.findByFirstNameAndLastName(firstName, lastName, pageRequest));
    }


    ///hasty method just for show embeddable
    @ApiOperation(value = "User contacts")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "User contacts fined"),
            @ApiResponse(code = 403, message = "No permission to execute the operation"),
            @ApiResponse(code = 404, message = "User not found (bad path variable id)")
    })
    @GetMapping(value = "/{id}/contacts")
    public ResponseEntity<Contact> getContact(@PathVariable Long id, HttpServletRequest request) {
        supClass.checkPermission(request, id);
        return ResponseEntity.ok(userService.getUserContacts(id));
    }


    //hasty method just for show embeddable
    @ApiOperation(value = "Update user contacts")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "User contacts was successfully updated"),
            @ApiResponse(code = 403, message = "No permission to execute the operation"),
            @ApiResponse(code = 404, message = "User not found (bad path variable id)")
    })
    @PutMapping(value = "/{id}/contacts")
    public String updateContact(@PathVariable Long id, @Valid @RequestBody Contact contact, HttpServletRequest request) {
        supClass.checkPermission(request, id);
        userService.updateContact(id, contact);
        return "The user's contacts have been successfully updated!";
    }


    //search users by param Criteria (all method define
    //this method combines all the methods defined above that used Jpa Repository + Pagination
    @ApiOperation(value = "Find user by params")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of users"),
            @ApiResponse(code = 400, message = "Incorrect input data"),
    })
    @GetMapping("/criteria")
    public ResponseEntity<List<UserPreview>> findUserByParams(
            @RequestParam(defaultValue = "1900-01-01") String afterDate,
            @RequestParam(defaultValue = "2100-01-01") String beforeDate,
            @RequestParam(defaultValue = "") String country,
            @RequestParam(defaultValue = "") String city,
            @RequestParam(defaultValue = "") String firstName,
            @RequestParam(defaultValue = "") String lastName,
            @RequestParam(defaultValue = "") String gender,
            @RequestParam(defaultValue = "1") String page,
            @RequestParam(defaultValue = "15") String size) {
        return ResponseEntity.ok(
                userService.
                        showUsersByParamsWithPaginationByCriteria(
                                afterDate, beforeDate, country,
                                city, firstName, lastName,
                                gender, page, size)
        );
    }


}
