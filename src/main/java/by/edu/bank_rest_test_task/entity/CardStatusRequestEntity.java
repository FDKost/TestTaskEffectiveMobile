package by.edu.bank_rest_test_task.entity;

import by.edu.bank_rest_test_task.util.RequestState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "card_status_request")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CardStatusRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "card_id", nullable = false)
    private CardEntity card;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id", nullable = false)
    private OwnerEntity requester;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requested_status_id", nullable = false)
    private StatusEntity requestedStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private RequestState state = RequestState.NEW;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
