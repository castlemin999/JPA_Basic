package jpacrud;

import jpacrud.entity.UserEntity;
import jpacrud.factory.CEntityManagerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;
import java.util.Scanner;

public class JpaCrudApplication {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String commandLine = sc.next();
        String[] commandArr = commandLine.split("/");
        String action = commandArr[0];

        CEntityManagerFactory.initialization();
        EntityManager entityManager = CEntityManagerFactory.createEntityManager();
        UserEntity user = new UserEntity(commandArr[1], commandArr[2], LocalDateTime.now(), LocalDateTime.now());

        switch (action){
            case "create":
                create(entityManager, user);
                break;
        }

    }

    public static void create(EntityManager entityManager, UserEntity user){
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
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
