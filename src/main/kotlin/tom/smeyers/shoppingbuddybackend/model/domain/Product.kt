package tom.smeyers.shoppingbuddybackend.model.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Product(
        @Id
        @GeneratedValue
        var id: Long = 0,
        var name: String,
        var imgUrl: String
)
