package miu.edu.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Audit {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt = LocalDateTime.now();
    @Column(name = "created_by", nullable = true)
    private long createdBy;
    @Column(name = "updated_by", nullable = true)
    private long updatedBy;

    public Audit(LocalDateTime createdAt, long createdBy, long updatedBy) {
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public Audit(LocalDateTime updatedAt, long updatedBy) {
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public Audit(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
