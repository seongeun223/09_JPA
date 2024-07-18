package com.ohgiraffers.springdatajpa.menu.model.repository;

import com.ohgiraffers.springdatajpa.menu.model.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// EntityMangerFactory, EntityManger, EntityTransaction -> 자동 구현
// JpaRepository<엔티티명, PK 타입>
public interface MenuRepository extends JpaRepository<Menu, Integer>{
}
