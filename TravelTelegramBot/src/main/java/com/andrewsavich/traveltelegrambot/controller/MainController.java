package com.andrewsavich.traveltelegrambot.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.andrewsavich.traveltelegrambot.model.City;
import com.andrewsavich.traveltelegrambot.service.CityService;

@Controller
public class MainController {
	@Autowired
	private CityService cityService;

	@GetMapping("/adminpanel")
	public String viewAdminPage(Model model) {
		List<City> cities = cityService.allCities();
		model.addAttribute("cities", cities);
		return "adminPanel";
	}

	@GetMapping("/adminpanel/addedit")
	public String viewAddCity(Model model) {
		City city = new City();
		model.addAttribute("city", city);
		return "addedit";
	}

	@PostMapping(value = "/save")
	public String saveCity(@Valid @ModelAttribute("city") City city, BindingResult bindingResult) {
		if (cityService.isExistCity(city.getTitle())
				&& (city.getId() != cityService.getCityByTitle(city.getTitle()).getId())) {
			bindingResult.rejectValue("title", "unique", "введенный город уже есть в базе");
		}

		if (bindingResult.hasErrors()) {
			return "addedit";
		}

		cityService.saveCity(city);
		return "redirect:/adminpanel";
	}

	@GetMapping(value = "/delete/{id}")
	public String deleteCity(@PathVariable("id") int id) {
		City city = cityService.getCityById(id);
		cityService.deleteCity(city);
		return "redirect:/adminpanel";
	}

	@GetMapping("/addedit/{id}")
	public ModelAndView viewEditCity(@PathVariable(name = "id") int id) {
		ModelAndView modelAndView = new ModelAndView("addedit");
		City city = cityService.getCityById(id);
		modelAndView.addObject("city", city);
		return modelAndView;
	}

}
