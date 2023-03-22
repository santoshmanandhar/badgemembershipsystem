package miu.edu.service;

import miu.edu.dto.TransactionDTO;

import java.util.List;

public interface TransactionService {
    //CRUD Operations for Transaction
    public TransactionDTO addTransaction(TransactionDTO transactionDTO);
    public List<TransactionDTO> findAllTransaction();
    public TransactionDTO findById(Long id);
    public TransactionDTO updateTransaction(TransactionDTO transactionDTO);
    public String deleteById(Long id);
}
