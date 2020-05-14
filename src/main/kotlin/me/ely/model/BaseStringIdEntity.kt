package me.ely.model

import javax.persistence.Column
import javax.persistence.Id
import javax.persistence.MappedSuperclass

/**
 *
 *
 * @author  <a href="mailto:xiaochunyong@gmail.com">Ely</a>
 * @see
 * @since   2019/8/30
 */
@MappedSuperclass
open class BaseStringIdEntity : BaseNoIDEntity() {

    @Id @Column(length = 191) val id: String? = null

}
