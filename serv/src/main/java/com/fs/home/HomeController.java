package com.fs.home;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : com.fs
 * fileName       : HomeController
 * author         : issac
 * date           : 2/17/24
 * description    : 
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2/17/24        issac               최초 생성
 */

@RestController
public class HomeController {

    @RequestMapping("/")
    public String home() {
        return "fs-server";
    }

}
