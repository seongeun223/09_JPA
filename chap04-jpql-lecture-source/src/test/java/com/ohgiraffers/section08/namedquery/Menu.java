package com.ohgiraffers.section08.namedquery;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

@Entity(name = "menu_section08")
@Table(name = "tbl_menu")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
// @NamedQuery 정적쿼리를 정의하는데 사용되는 어노테이션
// JPQL을 미리 엔티티 클래스에 정의를 해둠으로써 재사용성을 높일 수 있다.
@NamedQueries({
        @NamedQuery(
                name = "section08.namedquery.Menu.findAll", // 쿼리 이름
                query = """
                        select m from menu_section08 m
                        """ // 쿼리 정의
        ),
        @NamedQuery(
                name = "section08.namedquery.Menu.findByMenuName",
                query = """
                        select m from menu_section08 m where m.menuName = :menuName
                        """
        )
})
public class Menu {

    @Id
    @Column(name = "menu_code")
    private int menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "orderable_status")
    private String orderableStatus;
}
