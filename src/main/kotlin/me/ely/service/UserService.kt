package me.ely.service

import me.ely.dto.UserSearchRequestModel
import me.ely.model.User
import me.ely.model.User_
import me.ely.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root
import kotlin.math.max

/**
 *
 *
 * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
 * @see
 * @since   2019-08-05
 */
@Service
class UserService : AbstractJpaService<User, Long, UserRepository>() {

    fun search(requestModel: UserSearchRequestModel): Page<User> {
        val cb = entityManager.criteriaBuilder
        val dataCq = cb.createQuery(User::class.java)
        val dataRoot = dataCq.from(User::class.java)
        val dataPredicates = buildSearchPredicates(requestModel, cb, dataRoot)
        dataCq.where(*dataPredicates)
        dataCq.orderBy(cb.desc(dataRoot.get(User_.createDt)))
        val typedQuery = entityManager.createQuery(dataCq)
        typedQuery.firstResult = requestModel.pageSize * max(requestModel.pageIndex - 1, 0)
        typedQuery.maxResults = requestModel.pageSize
        val data = typedQuery.resultList

        val countCq = cb.createQuery(Long::class.java)
        val countRoot = countCq.from(User::class.java)
        val countPredicates = buildSearchPredicates(requestModel, cb, countRoot)
        countCq.select(cb.count(countRoot)).where(*countPredicates)
        val count = entityManager.createQuery(countCq).singleResult



        return PageImpl(data, PageRequest.of(max(requestModel.pageIndex - 1, 0), requestModel.pageSize, Sort(Sort.Direction.DESC, "id")), count)
    }

    private fun buildSearchPredicates(requestModel: UserSearchRequestModel, cb: CriteriaBuilder, dataRoot: Root<User>): Array<Predicate> {
        val conditions = ArrayList<Predicate>()
        if (!requestModel.username.isNullOrEmpty()) {
            conditions.add(cb.like(dataRoot.get(User_.username), "%" + requestModel.username + "%"))
        }
        return conditions.toTypedArray()
    }
}
