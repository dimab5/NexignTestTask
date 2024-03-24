package storage.repositories;

import models.Abonent;

import javax.persistence.*;

public class AbonentRepository implements IAbonentRepository {
    private EntityManagerFactory entityManagerFactory;

    public AbonentRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("Entity manager");
    }

    @Override
    public Abonent addAbonent(String phoneNumber) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        if (phoneNumber == null) {
            return null;
        }

        Abonent abonent = new Abonent();
        abonent.setPhoneNumber(phoneNumber);

        entityManager.persist(abonent);

        transaction.commit();
        entityManager.close();

        return abonent;
    }

    @Override
    public Abonent findByPhoneNumber(String phoneNumber) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Abonent> query = entityManager.createQuery(
                    "SELECT a FROM Abonent a WHERE a.phoneNumber = :phoneNumber", Abonent.class);
            query.setParameter("phoneNumber", phoneNumber);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            entityManager.close();
        }
    }
}
