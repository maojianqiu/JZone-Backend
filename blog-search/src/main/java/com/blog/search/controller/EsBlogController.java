package com.blog.search.controller;


import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 搜索博文管理Controller
 */
@Controller
@Api(tags = "EsBLOGController", description = "搜索博文管理")
@RequestMapping("/esBlog")
public class EsBlogController {
}
