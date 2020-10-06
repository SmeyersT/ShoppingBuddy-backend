package tom.smeyers.shoppingbuddybackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tom.smeyers.shoppingbuddybackend.model.domain.GroupMember

@Repository
interface GroupMemberRepository : JpaRepository<GroupMember, Long> {
    fun findAllByUser_Id(user_id: Long): MutableList<GroupMember>
    fun findAllByGroup_Id(group_id: Long): MutableList<GroupMember>
}
