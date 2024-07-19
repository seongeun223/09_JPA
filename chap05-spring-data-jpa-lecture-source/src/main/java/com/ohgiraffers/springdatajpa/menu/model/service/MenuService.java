package com.ohgiraffers.springdatajpa.menu.model.service;

import com.ohgiraffers.springdatajpa.menu.model.dto.CategoryDTO;
import com.ohgiraffers.springdatajpa.menu.model.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.model.entity.Category;
import com.ohgiraffers.springdatajpa.menu.model.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.model.repository.CategoryRepository;
import com.ohgiraffers.springdatajpa.menu.model.repository.MenuRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    public MenuService(MenuRepository menuRepository, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.menuRepository = menuRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    public MenuDTO findMenuByCode(int menuCode) {

        // MenuDTO -> 일반 클래스
        // Menu -> Entity

        /*
         * findById 메소드는 이미 구현이 되어있다.
         * 반환 값은 Optional 타입이고 Optional Type 은 NPE 방지하기 위해 다양한 기능을 제공
         * */

        Menu menu = menuRepository.findById(menuCode)
                .orElseThrow(IllegalArgumentException::new);
        // 찾을 수 없을 때 발생하는 Exception

        log.info("menu ============ {}", menu);

        return modelMapper.map(menu, MenuDTO.class);

    }

    public List<MenuDTO> findMenuList() {

        List<Menu> menuList =
                menuRepository.findAll();
        //menuRepository.findAll(Sort.by("menuPrice").descending());

        return menuList.stream()
                .map(menu -> modelMapper.map(menu, MenuDTO.class))
                .collect(Collectors.toList());
    }

    public Page<MenuDTO> findAllMenus(Pageable pageable) {

        // page 파라미터가 Pageable의 number로 넘어옴
        // 조회했을 때는 인덱스 기준이 되기 때문에 -1 처리
        // PageRequest.of -> Pageable 객체 조작
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize(), Sort.by("menuCode").descending());

        Page<Menu> menuList = menuRepository.findAll(pageable);

        return menuList.map(menu -> modelMapper.map(menu, MenuDTO.class));
    }

    public List<MenuDTO> findByMenuPrice(Integer menuPrice) {

        List<Menu> menuList = null;

        if (menuPrice == 0) {
            menuList = menuRepository.findAll();
        } else if (menuPrice > 0) {
            menuList = menuRepository.findMenuByMenuPriceEquals(menuPrice);
//                    Sort.by("menuPrice").descending());
        }

        return menuList.stream()
                .map(menu -> modelMapper.map(menu, MenuDTO.class))
                .collect(Collectors.toList());
    }

    public List<CategoryDTO> findAllCategory() {

        List<Category> categoryList = categoryRepository.findAll();

        return categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public void registNewMenu(MenuDTO newMenu) {

//        Menu menu = modelMapper.map(newMenu, Menu.class);

//        Menu menu = new Menu().builder()
//                .menuName(newMenu.getMenuName())
//                .menuPrice(newMenu.getMenuPrice())
//                .categoryCode(newMenu.getCategoryCode())
//                .orderableStatus(newMenu.getOrderableStatus())
//                .build();
//
//        menuRepository.save(menu);
    }

    @Transactional
    public void modifyMenu(MenuDTO modifymenu) {

        // modifyMenu -> 비영속
        // 영속

        Menu foundMenu = menuRepository.findById(modifymenu.getMenuCode())
                .orElseThrow(() -> new IllegalArgumentException("Menu not found"));

        foundMenu.setMenuName(modifymenu.getMenuName());

//        foundMenu.toBuilder()
//                .menuName(modifymenu.getMenuName())
//                .build();

        log.info("foundMenu === {}", foundMenu);
    }

    @Transactional
    public void deleteMenu(Integer menuCode) {

        menuRepository.deleteById(menuCode);
    }
}
