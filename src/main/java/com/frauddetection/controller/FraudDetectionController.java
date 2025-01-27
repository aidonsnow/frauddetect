package com.frauddetection.controller;

import com.frauddetection.model.Transaction;
import com.frauddetection.service.AlertService;
import com.frauddetection.service.FraudDetectionService;
import com.frauddetection.logging.GcpLoggingService;  // 引入 GcpLoggingService
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fraud")
public class FraudDetectionController {

    private final FraudDetectionService fraudDetectionService;
    private final AlertService alertService;
    private final GcpLoggingService gcpLoggingService;  // 添加 GcpLoggingService

    // 修改构造函数，注入 GcpLoggingService
    public FraudDetectionController(FraudDetectionService fraudDetectionService,
                                    AlertService alertService,
                                    GcpLoggingService gcpLoggingService) {
        this.fraudDetectionService = fraudDetectionService;
        this.alertService = alertService;
        this.gcpLoggingService = gcpLoggingService;  // 初始化 GcpLoggingService
    }

    @PostMapping("/check")
    public String checkFraud(@RequestBody Transaction transaction) {
        // 在处理交易时记录日志
        gcpLoggingService.info("Received transaction for fraud check: " + transaction.toString());

        String result = fraudDetectionService.detectFraud(transaction);

        // 如果检测到欺诈交易，发送告警
        if (result.startsWith("Fraudulent")) {
            alertService.sendAlert(transaction, result);
            gcpLoggingService.warn("Fraudulent transaction detected: " + transaction.toString());  // 记录欺诈交易日志
        }

        return result;
    }
}
