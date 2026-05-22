package lippo.hris.system.authentication.service;

import lippo.hris.system.authentication.entity.Menu;
import lippo.hris.system.authentication.repository.MenuRepository;
import lippo.hris.system.authentication.response.MenuResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MenuService {

    @Autowired
    MenuRepository menuRepository;

    public List<MenuResponse> getMenu(List<String> roles) {
        List<Menu> menus = menuRepository.findMenusByRoles(roles);
        Map<Long, MenuResponse> map = new HashMap<>();
        List<MenuResponse> roots = new ArrayList<>();

        for (Menu menu : menus) {

            MenuResponse dto = new MenuResponse();
            dto.setId(menu.getId());
            dto.setName(menu.getName());
            dto.setPath(menu.getPath());
            dto.setChildren(new ArrayList<>());
            map.put(menu.getId(), dto);
        }

        for (Menu menu : menus) {
            MenuResponse dto = map.get(menu.getId());

            if (menu.getParent() == null) {
                roots.add(dto);
            } else {
                MenuResponse parent = map.get(menu.getParent().getId());

                if (parent != null) {
                    parent.getChildren().add(dto);
                }
            }
        }
        return roots;
    }
}
