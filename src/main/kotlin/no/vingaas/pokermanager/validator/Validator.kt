package no.vingaas.pokermanager.validator

interface Validator<T> {
    fun validate(t: T): Set<String>
}