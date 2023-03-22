package miu.edu.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import miu.edu.domain.enums.TransactionStatusType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue
    @Column (name = "transaction_id")
    private long id;


    @Column (name = "transaction_status_type", nullable = true)
//    @NotBlank(message = "Transaction_status_type is required")
    private TransactionStatusType transactionStatusType;

    @Column (name = "transaction_date_time", nullable = false)
//    @NotBlank(message = "Transaction_date_time is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime transactionDateTime;

    @ManyToOne()
    @JoinColumn(name = "membership_id")
    private Membership membership;

    @ManyToOne()
    @JoinColumn(name = "location_id")
    private Location location;
    @Embedded
    private Audit audit;

    public Transaction(long id, TransactionStatusType transactionStatusType, LocalDateTime transactionDateTime, Membership membership, Location location) {
        this.id = id;
        this.transactionStatusType = transactionStatusType;
        this.transactionDateTime = transactionDateTime;
        this.membership = membership;
        this.location = location;
    }
}
