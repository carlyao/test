package com.upchina.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.upchina.service.LiveContentService;

/**
 * Created by Administrator on 2015-12-11 15:08:32
 */
@Controller
@RequestMapping("/liveContent")
public class LiveContentController {

    @Autowired
    private LiveContentService liveContentService;

   
}
