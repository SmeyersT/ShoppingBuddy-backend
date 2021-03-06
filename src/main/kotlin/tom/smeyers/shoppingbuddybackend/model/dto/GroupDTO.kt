package tom.smeyers.shoppingbuddybackend.model.dto

class GroupDTO(
        val id: Long,
        val name: String,
        val imgUrl: String,
        val groupMembers: MutableList<GroupMemberDTO>,
        val shoppingCart: ShoppingCartDTO
)
