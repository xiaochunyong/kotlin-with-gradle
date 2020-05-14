package me.ely.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.data.jpa.repository.JpaRepository
import javax.persistence.EntityManager
import java.util.*
import javax.persistence.PersistenceContext

/**
 * 保持命名统一
 * 禁止Service外部使用Repository
 * */
abstract class AbstractJpaService<Resource, Id, Repository : JpaRepository<Resource, Id>> {

    @Autowired(required = false)
    open lateinit var repository: Repository

    @Autowired
    @PersistenceContext
    open lateinit var entityManager: EntityManager

    @Autowired
    open lateinit var appContext: ApplicationContext

    private val beanCache = HashMap<Class<*>, Any>()

    open fun findById(id: Id): Optional<Resource> {
        return this.repository.findById(id)
    }

    open fun findAllById(ids: List<Id>): List<Resource> {
        return this.repository.findAllById(ids)
    }

    open fun save(resource: Resource): Resource {
        return this.repository.save(resource)
    }

    open fun delete(id: Id) {
        this.repository.deleteById(id)
    }

    open fun findAll(): List<Resource> {
        return this.repository.findAll()
    }

    open fun saveAll(resources: List<Resource>): List<Resource> {
        return this.repository.saveAll(resources)
    }

    open fun <T : Any> getBean(clazz: Class<T>, cache: Boolean): T {
        var service: T? = null
        if (cache) {
            val t = beanCache[clazz]
            if (t != null) {
                service = t as T
            }
        }
        if (service == null) {
            service = appContext.getBean(clazz)
            if (cache) {
                beanCache[clazz] = service
            }
        }
        return service
    }

    fun getBeansWithAnnotation(anno: Class<out Annotation>): Map<String, Any> {
        return appContext.getBeansWithAnnotation(anno)
    }
    //    public abstract Resource get(Id id, Integer additionalItems);

}