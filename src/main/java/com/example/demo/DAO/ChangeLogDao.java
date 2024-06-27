package com.example.demo.DAO;

import com.example.demo.model.ChangeLog;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class ChangeLogDao {

    private final EntityManagerFactory entityManagerFactory;

    public ChangeLogDao() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("demo");
    }

    public List<ChangeLog> getAllChangeLogs() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<ChangeLog> query = entityManager.createQuery("SELECT c FROM ChangeLog c", ChangeLog.class);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }

    public ChangeLog getChangeLogById(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return entityManager.find(ChangeLog.class, id);
        } finally {
            entityManager.close();
        }
    }

    public void addChangeLog(ChangeLog changeLog) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(changeLog);
            entityManager.getTransaction().commit();
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            entityManager.close();
        }
    }

    public void updateChangeLog(ChangeLog changeLog) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(changeLog);
            entityManager.getTransaction().commit();
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            entityManager.close();
        }
    }

    public void deleteChangeLog(Long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            ChangeLog changeLog = entityManager.find(ChangeLog.class, id);
            if (changeLog != null) {
                entityManager.remove(changeLog);
            }
            entityManager.getTransaction().commit();
        } finally {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            entityManager.close();
        }
    }

    public void close() {
        entityManagerFactory.close();
    }
}
