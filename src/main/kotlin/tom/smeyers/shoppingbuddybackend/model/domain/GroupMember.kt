package tom.smeyers.shoppingbuddybackend.model.domain

import com.fasterxml.jackson.annotation.*
import javax.persistence.*

@Entity
        data class GroupMember(
        @Id
        @GeneratedValue
        var id: Long,
        var role: GroupRole = GroupRole.MEMBER,
        @ManyToOne
        @JoinColumn(name = "user_id", nullable = false)
        var user: User,
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "group_id", nullable = true)
        @JsonBackReference
        val group: Group?
)
