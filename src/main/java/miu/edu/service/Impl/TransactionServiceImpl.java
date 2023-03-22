package miu.edu.service.Impl;

import lombok.RequiredArgsConstructor;
import miu.edu.adapter.TransactionAdapter;
import miu.edu.domain.Audit;
import miu.edu.domain.Transaction;
import miu.edu.dto.TransactionDTO;
import miu.edu.repository.TransactionRepository;
import miu.edu.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private final TransactionAdapter transactionAdapter;

    @Autowired
    private final TransactionRepository transactionRepository;

    @Override
    public TransactionDTO addTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = transactionAdapter.DtoToEntity(transactionDTO);
       // transaction.setAudit(new Audit(LocalDateTime.now()));
        try{
            transactionRepository.save(transaction);
        }
        catch(RuntimeException e){
            throw new RuntimeException(("Failed to add the transaction"));
        }
        return transactionAdapter.entityToDTO(transaction);

    }

    @Override
    public List<TransactionDTO> findAllTransaction() {
        return transactionAdapter.entityToDtoAll(transactionRepository.findAll());
    }

    @Override
    public TransactionDTO findById(Long id) {
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        Transaction transaction = transactionOptional.orElseThrow(() -> new EntityNotFoundException(("Transaction with id " + id + " is not found")));
        return transactionAdapter.entityToDTO(transaction);
    }

    @Override
    public TransactionDTO updateTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = transactionAdapter.DtoToEntity(transactionDTO);
        transaction.setAudit(new Audit(LocalDateTime.now()));
        try{
            transactionRepository.save(transaction);
        }
        catch(RuntimeException e){
            throw new RuntimeException(("Failed to update the transaction"));
        }
        return transactionAdapter.entityToDTO(transaction);
    }

    @Override
    public String deleteById(Long id) {
        try{
            transactionRepository.deleteById(id);
            return ("Transaction deleted successfully");
        }
        catch(EntityNotFoundException e){
            throw new EntityNotFoundException("Failed to delete the transaction.");
        }
    }
}
