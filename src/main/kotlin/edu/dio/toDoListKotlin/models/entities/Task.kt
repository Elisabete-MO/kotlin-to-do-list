package edu.dio.toDoListKotlin.models.entities

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDate.now

@Entity
@Table(name = "tasks")
data class Task (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var date: LocalDate = now(),

    var title: String? = null,

    @Column(nullable = false)
    var description: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var status: StatusEnum = StatusEnum.TODO,

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    var user: User
) {
    enum class StatusEnum {
        TODO, IN_PROGRESS, COMPLETED
    }
}
