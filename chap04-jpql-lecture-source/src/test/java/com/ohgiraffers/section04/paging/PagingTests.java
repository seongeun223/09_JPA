package com.ohgiraffers.section04.paging;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

public class PagingTests {

    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    @BeforeAll
    public static void initFactory() {
        entityManagerFactory = Persistence.createEntityManagerFactory("jpatest");
    }

    @BeforeEach
    public void initManager() {
        entityManager = entityManagerFactory.createEntityManager();
    }

    @AfterAll
    public static void closeFactory() {
        entityManagerFactory.close();
    }

    @AfterEach
    public void closeManager() {
        entityManager.close();
    }

    /*
    * JPA는 페이징을 API를 통해 추상화하여 간단하게 처리할 수 있다.
    *
    * - setFirstResult(int startPosition) : 조회를 시작할 위치
    * - setMaxResults(int maxResult) : 조회를 할 데이터의 수
    * */

    @Test
    @DisplayName("페이징 API를 이용한 조회 테스트")
    void test() {

        // given

    }
}
