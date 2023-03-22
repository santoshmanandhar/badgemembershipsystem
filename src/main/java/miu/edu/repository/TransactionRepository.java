package miu.edu.repository;

import miu.edu.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT COUNT(t) FROM Transaction t WHERE WEEK(t.transactionDateTime) = WEEK(NOW()) AND YEAR(t.transactionDateTime) = YEAR(NOW()) AND t.location.id = :location_id AND t.membership.id = :membership_id AND t.transactionStatusType = 'ALLOWED'")
    Integer countTotalTransactionThisWeek(@Param("location_id") Long location_id, @Param("membership_id") Long membership_id);

    @Query("SELECT COUNT(t) FROM Transaction t WHERE MONTH(t.transactionDateTime) = MONTH(NOW()) AND YEAR(t.transactionDateTime) = YEAR(NOW()) AND t.location.id = :location_id AND t.membership.id = :membership_id AND t.transactionStatusType = 'ALLOWED'")
    Integer countTotalTransactionThisMonth(@Param("location_id") Long location_id, @Param("membership_id") Long membership_id);

    @Query("SELECT COUNT(t) FROM Transaction t WHERE DATE(t.transactionDateTime) = DATE(NOW()) AND t.location.id = :location_id AND t.membership.id = :membership_id AND t.transactionStatusType = 'ALLOWED'")
    Integer countTotalTransactionToday(@Param("location_id") Long location_id, @Param("membership_id") Long membership_id);

    @Query("SELECT COUNT(t) FROM Transaction t JOIN t.location.timeSlots ts WHERE ts.id = :time_slot_id")
    Integer countTotalTransactionByTimeSlot(@Param("time_slot_id") Long time_slot_id);
}
