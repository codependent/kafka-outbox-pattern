package com.codependent.outboxpattern.account.mapper

import com.codependent.outboxpattern.account.dto.Account
import com.codependent.outboxpattern.account.dto.Movement
import com.codependent.outboxpattern.account.entity.AccountEntity
import ma.glasnost.orika.MapperFacade
import ma.glasnost.orika.MapperFactory
import ma.glasnost.orika.impl.DefaultMapperFactory
import java.util.*

class OrikaObjectMapper(mapperFactory: Optional<MapperFactory>) : ObjectMapper {

    val mapperFacade: MapperFacade

    init {
        val selectedMapperFactory = mapperFactory.orElseGet { DefaultMapperFactory.Builder().build() }
        this.mapperFacade = selectedMapperFactory.mapperFacade
    }

    override fun <D> map(o: Any, clazz: Class<D>): D {
        return mapperFacade.map(o, clazz)
    }

    override fun <D> map(oList: Collection<*>, clazzDestino: Class<D>): List<D> {
        return mapCollection(oList, clazzDestino)
    }

    override fun <D> map(oList: List<*>, clazzDestino: Class<D>): List<D> {
        return mapCollection(oList, clazzDestino)
    }

    private fun <D> mapCollection(oList: Collection<*>, clazzDestino: Class<D>): List<D> {
        val convertedList = ArrayList<D>()
        for (or in oList) {
            convertedList.add(map(or as Any, clazzDestino))
        }
        return convertedList
    }

}
/*
fun main() {

    val mapper = OrikaObjectMapper(Optional.empty())

    val account = Account(1,"as", "we", 12.0f, mutableListOf(Movement("12", 1, 2f, Date())))

    val ae = mapper.map(account, AccountEntity::class.java)

    println(ae)
}
*/
