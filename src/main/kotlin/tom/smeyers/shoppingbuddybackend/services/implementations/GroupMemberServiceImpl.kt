package tom.smeyers.shoppingbuddybackend.services.implementations

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tom.smeyers.shoppingbuddybackend.model.domain.Group
import tom.smeyers.shoppingbuddybackend.model.domain.GroupMember
import tom.smeyers.shoppingbuddybackend.model.domain.User
import tom.smeyers.shoppingbuddybackend.repository.GroupMemberRepository
import tom.smeyers.shoppingbuddybackend.services.interfaces.GroupMemberService
import tom.smeyers.shoppingbuddybackend.services.interfaces.GroupService
import javax.transaction.Transactional

@Transactional
@Service
class GroupMemberServiceImpl : GroupMemberService {

    @Autowired
    private lateinit var groupMemberRepo: GroupMemberRepository

    @Autowired
    private lateinit var groupService: GroupService

    override fun save(groupMember: GroupMember): GroupMember {
        //todo: throw custom exception if a user is already a member of a group.
        val group = groupService.findGroupById(groupMember.group!!.id)
        if (group.groupMembers.find { it.user.id == groupMember.user.id } != null) throw Exception("User is already a member of this group.")
        return groupMemberRepo.save(groupMember)
    }

    override fun getGroupMembersByUser(user: User): MutableList<GroupMember> {
        return groupMemberRepo.findAllByUser_Id(user.id)
    }

    override fun getGroupMembersByGroup(group: Group): MutableList<GroupMember> {
        return groupMemberRepo.findAllByGroup_Id(group.id)
    }

    override fun deleteGroupMember(id: Long): GroupMember {
        val groupMember = groupMemberRepo.findById(id)
        groupMemberRepo.deleteById(groupMember.get().id)
        return groupMember.get()
    }
}
