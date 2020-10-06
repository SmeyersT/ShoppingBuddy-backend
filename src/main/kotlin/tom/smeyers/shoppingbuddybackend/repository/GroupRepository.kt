package tom.smeyers.shoppingbuddybackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import tom.smeyers.shoppingbuddybackend.model.domain.Group

@Repository
interface GroupRepository : JpaRepository<Group, Long> {
    @Query(
            value = "SELECT * FROM GROUPS WHERE UPPER(NAME) LIKE CONCAT('%', :searchInput, '%')",
            nativeQuery = true
    )
    fun findByName(@Param("searchInput") searchInput: String): MutableList<Group>
}
