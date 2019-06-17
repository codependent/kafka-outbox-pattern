package com.codependent.outboxpattern.transfer.service

import com.codependent.outboxpattern.transfer.entity.Transfer
import com.codependent.outboxpattern.transfer.repository.TransferRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class TransferServiceImpl(private val transferRepository: TransferRepository) : TransferService {

    override fun getByTransferId(transferId: String): Transfer? {
        return transferRepository.findByTransferId(transferId)
    }

    override fun getAll(): List<Transfer> {
        return transferRepository.findAll()
    }

    override fun get(id: Int): Transfer? {
        return transferRepository.findById(id).orElse(null)
    }

    override fun save(transfer: Transfer) {
        transferRepository.save(transfer)
    }

}
