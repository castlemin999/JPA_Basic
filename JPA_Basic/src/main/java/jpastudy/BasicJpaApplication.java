package jpastudy;

import jpastudy.entity.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;

public class BasicJpaApplication {

    public static void main(String[] args) {

        // EntityManagerFactory는 EntityManager를 생성하기 위한 팩토리 인터페이스로 persistence 단위별로 팩토리를 생성
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(
                "basicjpa"); // persistence.xml 파일에 기입한 <persistence-unit> 이름을 적어줘야 한다.

        // EntityManager 객체 생성
        // EntityManager는 PersistenceContext와 Entity를 관리
        // 하나의 connection이라고 보면 됨
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // EntityManager에서 트랜잭션을 가져와 관리하기 위한 객체 생성
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try{
            // 트랜잭션을 시작해야 DB를 조작할 수 있음
            entityTransaction.begin();

            // 저장하고자 하는 엔티티 객체를 생성
            UserEntity userEntity = new UserEntity("test@gmail.com", "test1", LocalDateTime.now(), LocalDateTime.now());

            // UserEntity 객체를 Persistence Context에 추가
            entityManager.persist(userEntity);

            // 실제 DB에 반영 (쿼리문 실행)
            entityTransaction.commit();

        } catch (Exception e) {
            e.printStackTrace();

            // 예외가 발생 했을 경우 트랜잭션 롤백 진행
            entityTransaction.rollback();
        } finally {
            // 엔티티 매니저 종료. JDBC에서 Connection 종료하는 것과 동일한 기능으로 보면 됨.
            entityManager.close();
        }
        // 팩토리 종료. 커넥션 풀 자원을 반환
        entityManagerFactory.close();
    }
}