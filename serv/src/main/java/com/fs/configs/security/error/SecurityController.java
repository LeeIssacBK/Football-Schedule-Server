package com.fs.configs.security.error;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class SecurityController extends AbstractErrorController {

    public SecurityController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping
    public ResponseEntity<String> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        return new ResponseEntity<>(status.toString(), status);
    }

}
