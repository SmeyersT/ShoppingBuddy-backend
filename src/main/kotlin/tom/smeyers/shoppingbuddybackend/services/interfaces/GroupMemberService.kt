package tom.smeyers.shoppingbuddybackend.services.interfaces

import org.springframework.stereotype.Service
import tom.smeyers.shoppingbuddybackend.model.domain.Group
import tom.smeyers.shoppingbuddybackend.model.domain.GroupMember
import tom.smeyers.shoppingbuddybackend.model.domain.User

@Service
interface GroupMemberService {
    fun save(groupMember: GroupMember): GroupMember
    fun getGroupMembersByUser(user: User): MutableList<GroupMember>
    fun getGroupMembersByGroup(group: Group): MutableList<GroupMember>
    fun deleteGroupMember(id: Long): GroupMember
}
