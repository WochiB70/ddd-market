package xyz.wochib70.infrastructure.credential;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import xyz.wochib70.domain.UserId;
import xyz.wochib70.domain.credential.CredentialStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "credential")
public class CredentialEntity {
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private CredentialStatus status;

    private LocalDateTime startTime;

    private LocalDateTime expiredTime;

    @Column(unique = true)
    private String usageCode;

    private int unusedCount;

    private Long userId;
}
