package services;

import models.Abonent;

import java.util.List;

/**
 * Interface for the CDR (Call Data Record) service.
 */
public interface ICdrService {

    /**
     * Generates Call Data Records (CDR) entries.
     */
    void generateCdr();

    /**
     * Generates abonents.
     */
    List<Abonent> generateAbonents(int count);
}
