package edu.dio.toDoListKotlin.models.entities

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    @Column(nullable = false, unique = true)
    var username: String = "",

    @Column(nullable = false, unique = true)
    var email: String  = "",

    @Column(nullable = false)
    var password: String  = "",

    @Column(nullable = true)
    var imageUrl: String? = null,

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade =
    [CascadeType.ALL], orphanRemoval = true)
    var tasks: MutableList<Task> = mutableListOf()
)
