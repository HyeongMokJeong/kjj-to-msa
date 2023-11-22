package com.hanbat.zanbanzero.service.menu;

import com.kjj.api.entity.menu.Menu;
import com.kjj.api.repository.menu.MenuRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MenuServiceTest {

    @Autowired
    MenuRepository menuRepository;

    private String name = "치킨너겟";

    @BeforeEach
    void setup() {
        final Menu menu = new Menu(null, null, null, name, 2000, "imageURI", true, false);
        menuRepository.save(menu);
    }
}