package miu.edu.service.Impl;

import miu.edu.adapter.LocationAdapter;
import miu.edu.adapter.TransactionAdapter;
import miu.edu.domain.*;
import miu.edu.domain.enums.TransactionStatusType;
import miu.edu.dto.TransactionDTO;
import miu.edu.repository.LocationRepository;
import miu.edu.repository.MemberRepository;
import miu.edu.repository.MembershipRepository;
import miu.edu.repository.TransactionRepository;
import miu.edu.service.LocationService;
import miu.edu.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static miu.edu.domain.enums.RoleType.STUDENT;

@Service
@Transactional
public class ScanServiceImpl implements ScanService {
    @Autowired
    LocationService locationService;
    @Autowired
    LocationAdapter locationAdapter;
    @Autowired
    MembershipRepository membershipRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    TransactionAdapter transactionAdapter;
    @Autowired
    MemberRepository memberRepository;
    @Override
    public TransactionDTO processRequest(Long checker_id, Long plan_id, Long location_id, Long member_id) {
        Location location = locationAdapter.dtoToEntity(locationService.findById(location_id));
        Transaction transaction = new Transaction();
        transaction.setAudit(new Audit(LocalDateTime.now()));
        transaction.setLocation(location);
        try {
            Membership membership = membershipRepository.findFirstByMemberAndPlanAndLocation(member_id, plan_id, location_id);
            transaction.setMembership(membership);
            transaction.setTransactionDateTime(LocalDateTime.now());
            Optional<TimeSlot> timeSlot = Optional.ofNullable(locationRepository.findFirstByTimeSlotsByLocationAndTime(location.getId(), LocalDateTime.now()));
            if(timeSlot.isPresent()){
                switch (membership.getMembershipType()){
                    case LIMITED:
                        Integer transactionCount;
                        switch (membership.getDurationType()){
                            case DAILY -> transactionCount = transactionRepository.countTotalTransactionToday(location_id, membership.getId());
                            case WEEKLY -> transactionCount = transactionRepository.countTotalTransactionThisWeek(location_id, membership.getId());
                            case MONTHLY -> transactionCount = transactionRepository.countTotalTransactionThisMonth(location_id, membership.getId());
                            default -> transactionCount = 0;
                        }
                        if (transactionCount >= membership.getLimit()){
                            transaction = declinedTransaction(transaction);
                        }else {
                            transaction = allowTransaction(transaction, membership, timeSlot);
                        }
                        break;
                    default:
                        transaction = allowTransaction(transaction, membership, timeSlot);
                }
            }else {
                transaction = declinedTransaction(transaction);
            }
        }catch (Exception e){
            System.out.println(e);
            transaction = declinedTransaction(transaction);
        }finally {
            transactionRepository.save(transaction);
            return transactionAdapter.entityToDTO(transaction);
        }
    }

    public Transaction allowTransaction(Transaction transaction, Membership membership, Optional<TimeSlot> timeSlot){
        Optional<Member> member = memberRepository.findById(membership.getMember().getId());
        if (!member.isPresent()) {
            return declinedTransaction(transaction);
        }

        if (membership.getAllowMultiple() == false && timeSlot.isPresent()) {
            Integer countTransactionInTimeSlot = transactionRepository.countTotalTransactionByTimeSlot(timeSlot.get().getId());
            if (countTransactionInTimeSlot > 0) {
                return declinedTransaction(transaction);
            }
        }

        transaction.setTransactionStatusType(TransactionStatusType.ALLOWED);
        return transaction;
    }


    public Transaction declinedTransaction(Transaction transaction){
        transaction.setTransactionStatusType(TransactionStatusType.DECLINED);
        return transaction;
    }
}
