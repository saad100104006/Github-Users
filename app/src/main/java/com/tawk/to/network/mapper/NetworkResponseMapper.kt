package com.tawk.to.network.mapper

interface NetworkResponseMapper<Model, Entity> {
    fun modelFromEntity(entity: Entity): Model
}