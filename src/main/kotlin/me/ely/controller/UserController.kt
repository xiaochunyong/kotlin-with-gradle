package me.ely.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import me.ely.auth.USER_MANAGER
import me.ely.dto.UserCreateRequestModel
import me.ely.dto.UserSearchRequestModel
import me.ely.dto.UserUpdateRequestModel
import me.ely.extension.merge
import me.ely.extension.new
import me.ely.model.User
import me.ely.response.ItemResponse
import me.ely.response.ResponseCode
import me.ely.response.SearchResponse
import me.ely.response.SimpleResponse
import me.ely.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*
import java.security.Principal

/**
 *
 * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
 * @see
 * @since   2019-08-05
 */
@Api(tags=["User Module"])
@RestController
@RequestMapping("/user")
class UserController(val userService: UserService) {

    private val logger = LoggerFactory.getLogger(this.javaClass)
    
    @GetMapping("/")
    // @PreAuthorize("permitAll()")
    @ApiOperation(nickname = "SearchUser", value = "List User")
    fun list(model: UserSearchRequestModel): SearchResponse<User> {
        logger.info("search user params: $model")
        val result = userService.search(model)
        return SearchResponse(result.content, result.pageable.pageNumber, result.pageable.pageSize, result.totalElements)
    }
    
    @GetMapping("{id}")
    // @PreAuthorize("hasAuthority('$USER_MANAGER')")
    @ApiOperation(nickname = "GetUser", value = "User Detail")
    fun get(@PathVariable id: Long): ItemResponse<User> {
        logger.info("get user params: $id")
        val opt = userService.findById(id)
        if (opt.isPresent) {
            return ItemResponse(opt.get())
        }
        return ResponseCode.NOT_FOUND()
    }
    
    @PostMapping("/")
    // @PreAuthorize("hasAuthority('$USER_MANAGER')")
    @ApiOperation(nickname = "CreateUser", value = "Create User")
    fun create(@RequestBody model: UserCreateRequestModel): SimpleResponse {
        logger.info("create user params: $model")
        userService.save(new(model))
        return ResponseCode.SUCCESS()
    }
    
    @PutMapping("{id}")
    // @PreAuthorize("hasAuthority('$USER_MANAGER')")
    @ApiOperation(nickname = "UpdateUser", value = "Update User")
    fun update(@PathVariable id: Long, @RequestBody model: UserUpdateRequestModel): SimpleResponse {
        logger.info("update user params: $id - $model")
        val opt = userService.findById(id)
        if (opt.isPresent) {
            val item = opt.get()
            userService.save(item.merge(model))
            return ResponseCode.SUCCESS()
        }
        return ResponseCode.NOT_FOUND()
    }
    
    @DeleteMapping("{id}")
    // @PreAuthorize("hasAuthority('$USER_MANAGER')")
    @ApiOperation(nickname = "DeleteUser", value = "Delete User")
    fun delete(@PathVariable id: Long): SimpleResponse {
        logger.info("delete user params: $id")
        userService.delete(id)
        return ResponseCode.SUCCESS()
    }

}