package tom.smeyers.shoppingbuddybackend.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import tom.smeyers.shoppingbuddybackend.model.domain.Group
import tom.smeyers.shoppingbuddybackend.model.domain.User
import tom.smeyers.shoppingbuddybackend.model.dto.GroupDTO
import tom.smeyers.shoppingbuddybackend.model.dto.UserDTO
import tom.smeyers.shoppingbuddybackend.services.interfaces.GroupMemberService
import tom.smeyers.shoppingbuddybackend.services.interfaces.GroupService

@RestController
@RequestMapping("/api/group")
class GroupController {

    private final val logger: Logger = LoggerFactory.getLogger(GroupController::class.java)

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var groupService: GroupService

    @GetMapping("/getGroup")
    fun getGroupById(@RequestBody id: Long): ResponseEntity<GroupDTO> {
        logger.info("getGroup method called.")
        val group = groupService.findGroupById(id)
        val groupDto = objectMapper.convertValue(group, GroupDTO::class.java)
        return ResponseEntity.ok(groupDto)
    }

    @PostMapping("/createNewGroup")
    fun createNewGroup(@RequestBody groupDto: GroupDTO): ResponseEntity<GroupDTO> {
        logger.info("createNewGroup method called.")
        val group = objectMapper.convertValue(groupDto, Group::class.java)
        val savedGroup = groupService.createGroup(group)
        return ResponseEntity.ok(objectMapper.convertValue(savedGroup, GroupDTO::class.java))
    }

    @PostMapping("/deleteGroup")
    fun deleteGroup(@RequestBody groupDto: GroupDTO): ResponseEntity<GroupDTO> {
        logger.info("deleteGroup method called.")
        val group = objectMapper.convertValue(groupDto, Group::class.java)
        val deletedGroup = groupService.deleteGroup(group)
        return ResponseEntity.ok(objectMapper.convertValue(deletedGroup, GroupDTO::class.java))
    }

    @GetMapping("/getAllGroups")
    fun getAllGroups(): ResponseEntity<MutableList<GroupDTO>> {
        val groups = groupService.getAllGroups()
        val groupsDTO = mutableListOf<GroupDTO>()
        groups.forEach { groupsDTO.add(objectMapper.convertValue(it, GroupDTO::class.java)) }
        return ResponseEntity.ok(groupsDTO)
    }

    @GetMapping("/getGroupsByUser")
    fun getGroupsByUser(@RequestBody userDto: UserDTO): ResponseEntity<MutableList<GroupDTO>> {
        logger.info("getGroupsByUser method called.")
        val groups = groupService.getGroupsByUser(objectMapper.convertValue(userDto, User::class.java))
        val groupsDTO = mutableListOf<GroupDTO>()
        groups.forEach { groupsDTO.add(objectMapper.convertValue(it, GroupDTO::class.java)) }
        return ResponseEntity.ok(groupsDTO)
    }

    @GetMapping("/searchGroups")
    fun searchGroups(@RequestBody searchInput: String): ResponseEntity<MutableList<GroupDTO>> {
        logger.info("searchGroups method called. Search input is: $searchInput")
        val groups = groupService.searchGroups(searchInput)
        val groupsDTO = mutableListOf<GroupDTO>()
        groups.forEach { groupsDTO.add(objectMapper.convertValue(it, GroupDTO::class.java)) }
        return ResponseEntity.ok(groupsDTO)
    }

}
