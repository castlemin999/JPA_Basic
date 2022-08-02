package entitymanagerFactory;

import entitymanagerFactory.entity.UserEntity;
import entitymanagerFactory.factory.CEntityManagerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;

public class EntityManagerFactoryApplication {

    public static void main(String[] args) {

        CEntityManagerFactory.initialization();

        EntityManager entityManager = CEntityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();

            UserEntity user = new UserEntity("test3@naver.com", "test3", LocalDateTime.now(), LocalDateTime.now());
            entityManager.persist(user);

            entityTransaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            entityTransaction.rollback();
        } finally {
            entityManager.close();
        }

        CEntityManagerFactory.close();
    }
}
