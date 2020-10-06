package tom.smeyers.shoppingbuddybackend.services.implementations

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import tom.smeyers.shoppingbuddybackend.model.domain.Group
import tom.smeyers.shoppingbuddybackend.model.domain.User
import tom.smeyers.shoppingbuddybackend.repository.GroupRepository
import tom.smeyers.shoppingbuddybackend.services.interfaces.GroupMemberService
import tom.smeyers.shoppingbuddybackend.services.interfaces.GroupService
import tom.smeyers.shoppingbuddybackend.services.interfaces.ShoppingCartService
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

    override fun save(group: Group): Group {
        return groupRepo.save(group)
    }

    override fun deleteGroup(group: Group): Group {
        val toDeleteGroup = groupRepo.findById(group.id)
        if (toDeleteGroup.isPresent) groupRepo.deleteById(toDeleteGroup.get().id)
        return toDeleteGroup.get()
    }

    override fun createGroup(group: Group): Group {
        val savedShoppingCart = shoppingCartService.save(group.shoppingCart)
        group.shoppingCart = savedShoppingCart
        return save(group)
    }

    override fun findGroupById(id: Long): Group {
        return groupRepo.findById(id).get()
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
