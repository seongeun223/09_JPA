package com.ohgiraffers.springdatajpa.menu.model.service;

import com.ohgiraffers.springdatajpa.menu.model.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.model.entity.Menu;
import com.ohgiraffers.springdatajpa.menu.model.repository.MenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final ModelMapper modelMapper;

    public MenuService(MenuRepository menuRepository, ModelMapper modelMapper) {
        this.menuRepository = menuRepository;
        this.modelMapper = modelMapper;
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
}
