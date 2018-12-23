package com.ausichenko.github.data.models

data class Repository(
    var id: Long,
    var name: String,
    var fullName: String,
    var description: String?,
    var stars: Int,
    var language: String?
) {
    override fun equals(other: Any?): Boolean {
        if (other === this)
            return true

        val article = other as Repository?
        return article!!.id == this.id
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + fullName.hashCode()
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + stars
        result = 31 * result + (language?.hashCode() ?: 0)
        return result
    }
}