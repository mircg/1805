package com.es.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class PagesController {
	@RequestMapping("{var}")
	public String JumpPages(@PathVariable String var) {
		return var;
	}

}
