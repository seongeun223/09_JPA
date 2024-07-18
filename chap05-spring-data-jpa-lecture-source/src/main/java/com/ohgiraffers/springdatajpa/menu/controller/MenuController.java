package com.ohgiraffers.springdatajpa.menu.controller;

import com.ohgiraffers.springdatajpa.menu.model.dto.MenuDTO;
import com.ohgiraffers.springdatajpa.menu.model.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    // 메뉴 단일 조회 기능
    @GetMapping("/{menuCode}")
    public String findMenuByCode(@PathVariable("menuCode") int menuCode, Model model) {

        log.info("menuCode = {}", menuCode);

        MenuDTO menu = menuService.findMenuByCode(menuCode);

        model.addAttribute("menu", menu);

        return "menu/detail";
    }
}
