package org.choongang.admin.board;

import org.choongang.commons.ExceptionProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/board")
public class boardController implements ExceptionProcessor {

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
     * 게시판 수정
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
}
