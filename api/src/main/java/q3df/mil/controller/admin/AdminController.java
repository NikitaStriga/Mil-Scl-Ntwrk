package q3df.mil.controller.admin;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import q3df.mil.repository.custom.impl.CustomRepositoryImpl;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final CustomRepositoryImpl customRepository;

    @Autowired
    public AdminController(CustomRepositoryImpl customRepository) {
        this.customRepository = customRepository;
    }

    @ApiOperation(value = "Show the number of users in countries and cities")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", defaultValue = "token", required = true, paramType = "header", dataType = "string"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of users"),
    })
    @GetMapping
    public ResponseEntity<List<?>> showCountOfUserByCountryAndCity() {
        return ResponseEntity.ok(customRepository.showCountOfUserByCityAndCountry());
    }


}
