package tom.smeyers.shoppingbuddybackend.services.interfaces

import javassist.NotFoundException
import org.springframework.stereotype.Service
import tom.smeyers.shoppingbuddybackend.exceptions.CustomException
import tom.smeyers.shoppingbuddybackend.model.domain.Group
import tom.smeyers.shoppingbuddybackend.model.domain.User

@Service
interface GroupService {
    fun save(group: Group): Group
    @Throws(NotFoundException::class, CustomException::class)
    fun deleteGroup(group: Group, user: User)
    fun createGroup(group: Group): Group
    @Throws(NotFoundException::class)
    fun findGroupById(id: Long): Group
    fun getGroupsByUser(user: User): MutableList<Group?>
    fun searchGroups(searchInput: String): MutableList<Group>
}
