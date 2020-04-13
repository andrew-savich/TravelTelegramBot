package com.andrewsavich.traveltelegrambot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.andrewsavich.traveltelegrambot.model.City;
import com.andrewsavich.traveltelegrambot.service.CityService;

@Controller
public class MainController {
	@Autowired
	private CityService cityService;
	
	@RequestMapping("/adminpanel")
	public String viewAdminPage(Model model) {
		List<City> cities = cityService.allCities();
		model.addAttribute("cities", cities);
		
		return "adminPanel";
	}
	
//	@RequestMapping("/login")
//	public String viewLogin(Model model) {
//		return "login";
//	}
}
