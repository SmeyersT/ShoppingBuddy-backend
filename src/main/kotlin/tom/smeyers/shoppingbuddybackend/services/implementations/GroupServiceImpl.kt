package tom.smeyers.shoppingbuddybackend.services.implementations

import javassist.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import tom.smeyers.shoppingbuddybackend.CustomException
import tom.smeyers.shoppingbuddybackend.model.domain.Group
import tom.smeyers.shoppingbuddybackend.model.domain.GroupMember
import tom.smeyers.shoppingbuddybackend.model.domain.GroupRole
import tom.smeyers.shoppingbuddybackend.model.domain.User
import tom.smeyers.shoppingbuddybackend.repository.GroupRepository
import tom.smeyers.shoppingbuddybackend.services.interfaces.GroupMemberService
import tom.smeyers.shoppingbuddybackend.services.interfaces.GroupService
import tom.smeyers.shoppingbuddybackend.services.interfaces.ShoppingCartService
import tom.smeyers.shoppingbuddybackend.services.interfaces.UserService
import javax.transaction.Transactional

@Transactional
@Service
class GroupServiceImpl : GroupService {

    @Autowired
    private lateinit var groupRepo: GroupRepository

    @Autowired
    private lateinit var groupMemberService: GroupMemberService

    @Autowired
    private lateinit var shoppingCartService: ShoppingCartService

    @Autowired
    private lateinit var userService: UserService

    override fun save(group: Group): Group {
        return groupRepo.save(group)
    }

    override fun deleteGroup(group: Group, user: User) {
        try {
            val toDeleteGroup = findGroupById(group.id)
            val userGroupMember = groupMemberService.getGroupMemberByUserAndGroup(group.id, user.id)

            if(userGroupMember.role == GroupRole.OWNER) {
                groupRepo.delete(toDeleteGroup)
            }
            else throw CustomException("User has no privileges to delete this group!", HttpStatus.FORBIDDEN)
        } catch (ex: NotFoundException) {
            throw NotFoundException(ex.message)
        }
    }

    override fun createGroup(group: Group): Group {
        val savedShoppingCart = shoppingCartService.save(group.shoppingCart)
        group.shoppingCart = savedShoppingCart
        return save(group)
    }

    @Throws(NotFoundException::class)
    override fun findGroupById(id: Long): Group {
        val group = groupRepo.findById(id)
        return if(group.isPresent) {
            group.get()
        }
        else throw NotFoundException("No group found with id: $id.")
    }

    override fun getAllGroups(): MutableList<Group> {
        return groupRepo.findAll()
    }

    override fun getGroupsByUser(user: User): MutableList<Group?> {
        val groupMembers = groupMemberService.getGroupMembersByUser(user)
        val groups = mutableListOf<Group?>()
        groupMembers.forEach { groups.add(it.group) }
        return groups
    }

    override fun searchGroups(searchInput: String): MutableList<Group> {
        return groupRepo.findByName(searchInput.substring(1, searchInput.length-1).toUpperCase())
    }
}
