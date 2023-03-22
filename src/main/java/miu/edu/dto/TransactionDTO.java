package miu.edu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import miu.edu.domain.Location;
import miu.edu.domain.Membership;
import miu.edu.domain.enums.TransactionStatusType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TransactionDTO {
    private long id;
    private TransactionStatusType status;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTime;
    private MembershipDTO membershipDTO;
    private LocationDTO locationDTO;

    public TransactionDTO(TransactionStatusType status, LocalDateTime dateTime, MembershipDTO membershipDTO, LocationDTO locationDTO) {
        this.status = status;
        this.dateTime = dateTime;
        this.membershipDTO = membershipDTO;
        this.locationDTO = locationDTO;
    }

    public TransactionDTO(TransactionStatusType status, LocalDateTime dateTime, MembershipDTO membershipDTO) {
        this.status = status;
        this.dateTime = dateTime;
        this.membershipDTO = membershipDTO;
    }
}
