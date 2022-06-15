package com.projekat.inzinjering.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController  
public class ComponentSuggestionController   
{  
@RequestMapping("/")  
public String hello()   
{  
return "Hello User";  
}  
}  