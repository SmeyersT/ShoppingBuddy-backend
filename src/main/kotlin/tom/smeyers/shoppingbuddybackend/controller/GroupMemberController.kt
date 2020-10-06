package tom.smeyers.shoppingbuddybackend.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import tom.smeyers.shoppingbuddybackend.model.domain.GroupMember
import tom.smeyers.shoppingbuddybackend.model.dto.GroupDTO
import tom.smeyers.shoppingbuddybackend.model.dto.GroupMemberDTO
import tom.smeyers.shoppingbuddybackend.model.dto.UserDTO
import tom.smeyers.shoppingbuddybackend.services.interfaces.GroupMemberService
import tom.smeyers.shoppingbuddybackend.services.interfaces.GroupService
import tom.smeyers.shoppingbuddybackend.services.interfaces.UserService

@RestController
@RequestMapping("/api/groupMember")
class GroupMemberController {

    private final val logger: Logger = LoggerFactory.getLogger(GroupMemberController::class.java)

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var groupMemberService: GroupMemberService

    @PostMapping("/createGroupMember")
    fun createNewGroupMember(@RequestBody groupMemberDto: GroupMemberDTO): ResponseEntity<GroupMemberDTO> {
        logger.info("createGroupMember method called.")
        val savedGroupMember = groupMemberService.save(objectMapper.convertValue(groupMemberDto, GroupMember::class.java))
        return ResponseEntity.ok(objectMapper.convertValue(savedGroupMember, GroupMemberDTO::class.java))
    }

    @PostMapping("/deleteGroupMember")
    fun deleteGroupMember(@RequestBody groupMemberId: Long): ResponseEntity<GroupMemberDTO> {
        logger.info("deleteGroupMember method called. GroupMember id: $groupMemberId")
        val deletedGroupMember = groupMemberService.deleteGroupMember(groupMemberId)
        return ResponseEntity.ok(objectMapper.convertValue(deletedGroupMember, GroupMemberDTO::class.java))
    }

}
