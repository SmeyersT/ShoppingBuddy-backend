package tom.smeyers.shoppingbuddybackend.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import tom.smeyers.shoppingbuddybackend.model.domain.Group
import tom.smeyers.shoppingbuddybackend.model.dto.GroupDTO
import tom.smeyers.shoppingbuddybackend.services.interfaces.GroupService
import tom.smeyers.shoppingbuddybackend.services.interfaces.UserService

@RestController
@RequestMapping("/api/group")
class GroupController {

    private final val logger: Logger = LoggerFactory.getLogger(GroupController::class.java)

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var groupService: GroupService

    @Autowired
    private lateinit var userService: UserService

    @GetMapping("/getGroup")
    fun getGroupById(@RequestHeader("Authorization") authHeader: String, @RequestBody id: Long): ResponseEntity<GroupDTO> {
        logger.info("getGroup method called.")
        val group = groupService.findGroupById(id)
        val groupDto = objectMapper.convertValue(group, GroupDTO::class.java)
        return ResponseEntity.ok(groupDto)
    }

    @PostMapping("/createNewGroup")
    fun createNewGroup(@RequestHeader("Authorization") authHeader: String, @RequestBody groupDto: GroupDTO): ResponseEntity<GroupDTO> {
        logger.info("createNewGroup method called.")
        val group = objectMapper.convertValue(groupDto, Group::class.java)
        val savedGroup = groupService.createGroup(group)
        return ResponseEntity.ok(objectMapper.convertValue(savedGroup, GroupDTO::class.java))
    }

    @PostMapping("/deleteGroup")
    fun deleteGroup(@RequestHeader("Authorization") authHeader: String, @RequestBody groupDto: GroupDTO): ResponseEntity<GroupDTO> {
        logger.info("deleteGroup method called.")
        val group = objectMapper.convertValue(groupDto, Group::class.java)
        val user = userService.getCurrentUser(authHeader.substring(7))
        groupService.deleteGroup(group, user)
        return ResponseEntity.ok(groupDto)
    }


    @GetMapping("/getGroupsByUser")
    fun getGroupsByUser(@RequestHeader("Authorization") authHeader: String): ResponseEntity<MutableList<GroupDTO>> {
        logger.info("getGroupsByUser method called.")
        val user = userService.getCurrentUser(authHeader.substring(7))
        val groups = groupService.getGroupsByUser(user)
        val groupsDTO = mutableListOf<GroupDTO>()
        groups.forEach { groupsDTO.add(objectMapper.convertValue(it, GroupDTO::class.java)) }
        return ResponseEntity.ok(groupsDTO)
    }

    @GetMapping("/searchGroups")
    fun searchGroups(@RequestHeader("Authorization") authHeader: String, @RequestBody searchInput: String): ResponseEntity<MutableList<GroupDTO>> {
        logger.info("searchGroups method called. Search input is: $searchInput")
        val groups = groupService.searchGroups(searchInput)
        val groupsDTO = mutableListOf<GroupDTO>()
        groups.forEach { groupsDTO.add(objectMapper.convertValue(it, GroupDTO::class.java)) }
        return ResponseEntity.ok(groupsDTO)
    }

}
