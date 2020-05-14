package me.ely.model

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.Column
import javax.persistence.MappedSuperclass
import javax.persistence.Version

@MappedSuperclass
open class BaseNoIDEntity {

    // 各业务表结构不一
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // var id: Long? = null  //主键
    // @Id @Column(length = 191) val id: String? = null

    /**
     * 创建时间
     */
    @field:CreationTimestamp
    @Column(name = "create_dt", nullable = false, updatable = false)
    var createDt: Date = Date() // 默认值在做插入时会被覆盖

    /**
     * 更新时间
     */
    @field:UpdateTimestamp
    @Column(name = "update_dt", nullable = false)
    var updateDt: Date = Date()

    /**
     * 逻辑删除标记
     */
    var deleted: Boolean = false

    /**
     * 乐观锁版本号
     */
    @Version
    var version: Long = 0

}
