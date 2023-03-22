package miu.edu.adapter;

import miu.edu.domain.Transaction;
import miu.edu.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionAdapter {
    @Autowired
    MembershipAdapter membershipAdapter;
    @Autowired
    LocationAdapter locationAdapter;
    public TransactionDTO entityToDTO(Transaction transaction){
        return new TransactionDTO(transaction.getId(), transaction.getTransactionStatusType(), transaction.getTransactionDateTime(), membershipAdapter.entityToDTO(transaction.getMembership()), locationAdapter.entityToDto(transaction.getLocation()));
    }

    public List<TransactionDTO> entityToDtoAll(List<Transaction> transactions){
        return transactions.stream().map(transaction -> entityToDTO(transaction)).collect(Collectors.toList());
    }

    public Transaction DtoToEntity(TransactionDTO transactionDTO){
        return new Transaction(transactionDTO.getId(), transactionDTO.getStatus(), transactionDTO.getDateTime(), membershipAdapter.dtoToEntity(transactionDTO.getMembershipDTO()), locationAdapter.dtoToEntity(transactionDTO.getLocationDTO()));
    }

    public List<Transaction> DtoToEntityAll(List<TransactionDTO> transactionDTOList){
        return transactionDTOList.stream().map(transactionDTO -> DtoToEntity(transactionDTO)).collect(Collectors.toList());
    }

}
