package tom.smeyers.shoppingbuddybackend.services.interfaces

import org.springframework.stereotype.Service
import tom.smeyers.shoppingbuddybackend.model.domain.Group
import tom.smeyers.shoppingbuddybackend.model.domain.User

@Service
interface GroupService {
    fun save(group: Group): Group
    fun deleteGroup(group: Group): Group
    fun createGroup(group: Group): Group
    fun findGroupById(id: Long): Group
    fun getAllGroups(): MutableList<Group>
    fun getGroupsByUser(user: User): MutableList<Group?>
    fun searchGroups(searchInput: String): MutableList<Group>
}
