package tom.smeyers.shoppingbuddybackend.model.domain


import com.fasterxml.jackson.annotation.JsonManagedReference
import java.time.LocalDateTime
import javax.persistence.*

@Entity(name = "groups")
data class Group(
        @Id
        @GeneratedValue
        var id: Long = 0,
        var name: String,
        var imgUrl: String,
        @OneToMany(mappedBy = "group", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
        @JsonManagedReference
        var groupMembers: MutableList<GroupMember> = mutableListOf<GroupMember>(),
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(referencedColumnName = "id")
        var shoppingCart: ShoppingCart = ShoppingCart(0, false,
                mutableListOf<ShoppingCartItem>(), isPersonalCart = false)
)
