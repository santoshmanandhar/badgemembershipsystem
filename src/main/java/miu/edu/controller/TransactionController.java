package miu.edu.controller;

import lombok.RequiredArgsConstructor;
import miu.edu.dto.TransactionDTO;
import miu.edu.service.TransactionService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
    @Autowired
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> addTransaction(@RequestBody TransactionDTO transactionDTO){
        return new ResponseEntity<TransactionDTO>(transactionService.addTransaction(transactionDTO), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<?> getAllTransactions(){
        return new ResponseEntity<List<TransactionDTO>>(transactionService.findAllTransaction(), HttpStatus.OK);
    }

    @GetMapping("/{transaction_id}")
    public ResponseEntity<?> getTransaction(@PathVariable Long transaction_id){
        return new ResponseEntity<TransactionDTO>(transactionService.findById(transaction_id), HttpStatus.OK);
    }
    @PutMapping
    public ResponseEntity<?> updateTransaction(@RequestBody TransactionDTO transactionDTO){
        return new ResponseEntity<TransactionDTO>(transactionService.updateTransaction(transactionDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{transaction_id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long transaction_id){
        return new ResponseEntity<String>(transactionService.deleteById(transaction_id), HttpStatus.OK);
    }



}
