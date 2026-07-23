package com.fundtransfer.scheduler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fundtransfer.entity.ScheduledTransfer;
import com.fundtransfer.repository.ScheduledTransferRepository;
import com.fundtransfer.service.ScheduledTransferService;

@Component
public class ScheduledTransferExecutor {

    @Autowired
    private ScheduledTransferRepository repository;

    @Autowired
    private ScheduledTransferService service;

    @Scheduled(fixedRate = 10000)
    public void executePendingTransfers() {

        System.out.println("Checking Scheduled Transfers...");

        List<ScheduledTransfer> transfers =
                repository.findByStatus("PENDING");

        for (ScheduledTransfer transfer : transfers) {

            if (!transfer.getScheduleDate().isAfter(LocalDate.now())) {

                System.out.println("Executing Transfer : " + transfer.getId());

                service.executeTransfer(transfer.getId());

            }

        }

    }

}