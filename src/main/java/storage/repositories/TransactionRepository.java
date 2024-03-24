package storage.repositories;

import models.Abonent;
import models.CdrModel;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.Instant;

public class TransactionRepository implements ITransactionRepository {
    private EntityManagerFactory entityManagerFactory;

    public TransactionRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("Entity manager");
    }

    @Override
    public CdrModel addTransaction(String typeCall, Abonent abonent, Instant startTime, Instant endTime) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        if (abonent == null || typeCall == null || startTime == null || endTime == null) {
            return null;
        }

        CdrModel cdrModel = new CdrModel();
        cdrModel.setTypeCall(typeCall);
        cdrModel.setAbonent(abonent);
        cdrModel.setStartTime(startTime);
        cdrModel.setEndTime(endTime);

        entityManager.persist(cdrModel);

        transaction.commit();
        entityManager.close();

        return cdrModel;
    }
}
