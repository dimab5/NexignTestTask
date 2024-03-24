package storage.repositories;

import models.Abonent;
import models.CdrModel;

import java.time.Instant;

/**
 * Interface for the transaction repository.
 */
public interface ITransactionRepository {

    /**
     * Adds a new transaction to the database.
     *
     * @param typeCall   The type of call (incoming, outgoing).
     * @param abonent    The subscriber making the call.
     * @param startTime  The start time of the call.
     * @param endTime    The end time of the call.
     * @return The CdrModel data model representing the added transaction.
     */
    CdrModel addTransaction(
            String typeCall,
            Abonent abonent,
            Instant startTime,
            Instant endTime);
}
