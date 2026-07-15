package com.fundtransfer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fundtransfer.entity.ScheduledTransfer;

public interface ScheduledTransferRepository extends JpaRepository<ScheduledTransfer, Long> {

    List<ScheduledTransfer> findByStatus(String status);

}