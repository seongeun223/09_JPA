package com.ohgiraffers.section02.crud;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;
public class A_EntityManagerCRUDTests {

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

    @DisplayName("메뉴코드로 메뉴 조회 테스트")
    @Test
    public void selectMenuByMenuCodeTest() {

        // given
        int menuCode = 2;
        // when
        Menu menu = entityManager.find(Menu.class, menuCode);

        // then
        Assertions.assertNotNull(menu);

        Assertions.assertEquals(menuCode, menu.getMenuCode());
        System.out.println("menu = " + menu);

        Menu menu2 = entityManager.find(Menu.class, 2);
        System.out.println("menu2 = " + menu2);

    }

    @DisplayName("새로운 메뉴 추가 테스트")
    @Test
    // 해당 메서드가 테스트 메서드임을 나타낸다.
    public void insertNewMenuTest() {
        //given
        // 테스트의 사전 조건을 설정하는 부분 여기서는 새로운 Menu 객체를 생성하고 필요한 속성들을 설정
        Menu menu = new Menu();
        menu.setMenuName("JPA 테스트용 신규 메뉴");
        menu.setMenuPrice(50000);
        menu.setCategoryCode(4);
        menu.setOrderableStatus("Y");

        // when
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin(); // 트렌젝션 시작을 선언
        try {
            entityManager.persist(menu); // 영속성 컨텍스트에 등록
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        // then
        // 영속성 컨테스트에 menu가 있는지 확인
        // 테스트의 결과를 검증하는 부분
        Assertions.assertTrue(entityManager.contains(menu));
        // entityManager.contains(menu)을 사용하여 객체가 영속성 컨텍스트에 포함되어 있는지 확인
    }

    @DisplayName("메뉴 이름 수정 테스트")
    @Test
    public void modifyMenuNameTest() {
        // given
        Menu menu = entityManager.find(Menu.class, 23);
        System.out.println("menu = " + menu);

        String menuNameChange = "생갈치스무디";

        // when
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            menu.setMenuName(menuNameChange);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        // then
        Assertions.assertEquals(menuNameChange, menu.getMenuName());

        Assertions.assertEquals(menuNameChange, entityManager.find(Menu.class, 23).getMenuName());
    }

    @DisplayName("메뉴 삭제하기 테스트")
    @Test
    public void deleteMenu() {
        //given
        Menu menuToRemove = entityManager.find(Menu.class, 23); // 영속화
        System.out.println("menuToRemove = " + menuToRemove);

        // when
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            entityManager.remove(menuToRemove);
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }

        // then
        Menu removedMenu = entityManager.find(Menu.class, 23);
        Assertions.assertEquals(null, removedMenu);
    }


}
