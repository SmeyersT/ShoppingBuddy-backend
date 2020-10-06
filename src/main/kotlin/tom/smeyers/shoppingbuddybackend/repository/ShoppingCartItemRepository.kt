package tom.smeyers.shoppingbuddybackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tom.smeyers.shoppingbuddybackend.model.domain.ShoppingCartItem

@Repository
interface ShoppingCartItemRepository : JpaRepository<ShoppingCartItem, Long> {
}
