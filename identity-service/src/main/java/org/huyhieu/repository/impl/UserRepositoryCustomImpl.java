package org.huyhieu.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.huyhieu.entity.IdentityUser;
import org.huyhieu.repository.UserRepositoryCustom;
import org.huyhieu.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    private static final Logger LOG = LogManager.getLogger(UserRepositoryCustomImpl.class);

    @Override
    public IdentityUser saveUser(IdentityUser identityUser) {
        Transaction transaction = null;
        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(identityUser);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error(e);
        }
        return identityUser;
    }

    @Override
    public List<IdentityUser> getUsers() {
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM User", IdentityUser.class).list();
        }
    }

    @Override
    public IdentityUser updateUser(IdentityUser identityUser) {
        Transaction transaction = null;
        IdentityUser updatedIdentityUser = null;
        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            updatedIdentityUser = (IdentityUser) session.merge(identityUser);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error(e.getStackTrace());
        }

        return updatedIdentityUser;
    }

    @Override
    public void deleteUser(Long userId) {
        Transaction transaction = null;
        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(userId);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error(e.getStackTrace());
        }
    }

    @Override
    public IdentityUser findUserById(Integer userId) {
        Transaction transaction = null;
        IdentityUser identityUser = null;

        try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            identityUser = session.load(IdentityUser.class, userId);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            LOG.error(e.getStackTrace());
        }

        return identityUser;
    }
}
