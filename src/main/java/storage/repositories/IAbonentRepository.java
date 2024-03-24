package storage.repositories;

import models.Abonent;

/**
 * Interface for working with the subscriber repository.
 */
public interface IAbonentRepository {

    /**
     * Adds a new subscriber with the specified phone number to the database.
     *
     * @param phoneNumber The phone number of the new subscriber.
     * @return The subscriber object added to the repository.
     */
    Abonent addAbonent(String phoneNumber);

    /**
     * Finds a subscriber by the specified phone number in the repository.
     *
     * @param phoneNumber The phone number to search for the subscriber.
     * @return The found subscriber or null if no subscriber with the specified phone number is found.
     */
    Abonent findByPhoneNumber(String phoneNumber);
}
