package tom.smeyers.shoppingbuddybackend.model.dto

import tom.smeyers.shoppingbuddybackend.model.domain.GroupRole

class GroupMemberDTO(
        val id: Long,
        val role: GroupRole,
        val user: UserDTO,
        val group: GroupDTO?
        )
