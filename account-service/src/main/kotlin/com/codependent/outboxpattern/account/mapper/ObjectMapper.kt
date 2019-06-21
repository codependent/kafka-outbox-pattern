package com.codependent.outboxpattern.account.mapper

interface ObjectMapper {

    fun <D> map(o: Any, clazz: Class<D>): D
    fun <D> map(oList: Collection<*>, clazzDestino: Class<D>): List<D>
    fun <D> map(oList: List<*>, clazzDestino: Class<D>): List<D>

}
