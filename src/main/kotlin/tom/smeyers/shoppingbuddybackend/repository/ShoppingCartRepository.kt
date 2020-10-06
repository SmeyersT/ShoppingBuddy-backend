package tom.smeyers.shoppingbuddybackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import tom.smeyers.shoppingbuddybackend.model.domain.ShoppingCart

@Repository
interface ShoppingCartRepository : JpaRepository<ShoppingCart, Long> {
}
