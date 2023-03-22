package miu.edu.service;

import miu.edu.dto.TransactionDTO;

public interface ScanService {
    public TransactionDTO processRequest(Long checker_id, Long plan_id, Long location_id, Long member_id);
}
