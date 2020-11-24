package tom.smeyers.shoppingbuddybackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import tom.smeyers.shoppingbuddybackend.model.domain.GroupMember
import java.util.*

@Repository
interface GroupMemberRepository : JpaRepository<GroupMember, Long> {
    fun findAllByUser_Id(user_id: Long): MutableList<GroupMember>
    fun findAllByGroup_Id(group_id: Long): MutableList<GroupMember>
    @Query(
            value = "SELECT * FROM shoppingbuddy.GROUP_MEMBER WHERE GROUP_ID = :group_id AND USER_ID = :user_id",
            nativeQuery = true
    )
    fun findByUserAndGroup(@Param("user_id") user_id: Long, @Param("group_id") group_id: Long): Optional<GroupMember>
}
