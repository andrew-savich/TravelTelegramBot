package com.andrewsavich.traveltelegrambot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
	@RequestMapping("/adminpanel/addedit")
	public String viewAddCity(Model model) {
		City city = new City();
		model.addAttribute("city",  city);
		
		return "addedit";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("city") City city) {
	    cityService.saveCity(city);
	     
	    return "redirect:/adminpanel";
	}
	
	@RequestMapping(value = "/delete/{id}")
	public String deleteCity(@PathVariable("id") int id) {
		City city = cityService.getCityById(id);
		cityService.deleteCity(city);
		return "redirect:/adminpanel";
	}
}
