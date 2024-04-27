package com.nairi.githubrepofinder.domain.mapper

interface Mapper<I, O> {
    fun mapTo(input: I): O

}