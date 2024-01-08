package org.choongang.admin.board;

import org.choongang.admin.menus.Menu;
import org.choongang.admin.menus.MenuDetail;
import org.choongang.commons.ExceptionProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller("adminBoardController")
@RequestMapping("/admin/board")
public class boardController implements ExceptionProcessor {

    @ModelAttribute("menuCode")
    public String getMenuCode() { // 주 메뉴 코드
        return "board";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> getSubMenus() { // 서브 메뉴
        return Menu.getMenus("board");
    }

    /**
     * 게시판 목록
     * @return
     */
    @GetMapping
    public String list() {

        return "admin/board/list";
    }

    /**
     * 게시판 등록
     * @return
     */
    @GetMapping("/add")
    public String add() {

        return "admin/board/add";
    }

    /**
     * 게시판 등록/수정 처리
     * @return
     */
    @PostMapping("/save")
    public String save() {
        return "redirect:/admin/board";
    }

    /**
     * 게시글 관리
     * @return
     */
    @GetMapping("/posts")
    public String posts() {
        return "admin/board/posts";
    }

    /**
     * 공통 처리
     * @param mode
     * @param model
     */
    private void commonProcess(String mode, Model model) {
        String pageTitle = "게시판 목록";
        mode = StringUtils.hasText(mode) ? mode : "list";

        if (mode.equals("add")) {
            pageTitle = "게시판 등록";
        } else if (mode.equals("edit")) {
            pageTitle = "게시판 수정";
        } else if (mode.equals("post")) {
            pageTitle = "게시글 관리";
        }

        model.addAttribute("pageTitle", pageTitle);
    }
}
