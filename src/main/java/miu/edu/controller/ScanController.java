package miu.edu.controller;

import miu.edu.dto.TransactionDTO;
import miu.edu.service.ScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/scan")
public class ScanController {
    @Autowired
    ScanService scanService;
    @PostMapping
    public ResponseEntity<?> accessRequest(@RequestBody Map<String, String> data){
        Long checker_id = Long.valueOf(data.get("checker_id"));
        Long plan_id = Long.valueOf(data.get("plan_id"));
        Long location_id = Long.valueOf(data.get("location_id"));
        Long member_id = Long.valueOf(data.get("member_id"));
        return new ResponseEntity<>(scanService.processRequest(checker_id, plan_id, location_id, member_id), HttpStatus.OK);
    }
}
