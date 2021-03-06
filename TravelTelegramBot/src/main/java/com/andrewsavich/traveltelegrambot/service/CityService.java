package com.andrewsavich.traveltelegrambot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.andrewsavich.traveltelegrambot.model.City;
import com.andrewsavich.traveltelegrambot.repository.CityRepository;

@Service
@Transactional
public class CityService {
	@Autowired
	private CityRepository repository;

	public List<City> allCities() {
		return repository.findAll();
	}

	public void saveCity(City city) {
		repository.save(city);
	}

	public void deleteCity(City city) {
		repository.delete(city);
	}

	public City getCityById(int id) {
		return repository.findById(id).get();
	}

	public City getCityByTitle(String cityTitle) {
		return repository.getCityByTitle(cityTitle);
	}

	public String allCityTitles() {
		List<City> cities = repository.findAll();
		int countCities = 0;
		String cityTitles = "";

		for (City city : cities) {
			if (++countCities < cities.size()) {
				cityTitles += city.getTitle() + ", ";
			} else {
				cityTitles += city.getTitle();
			}
		}

		return cityTitles;
	}
	
	public boolean isExistCity(String cityTitle) {
		if(repository.getCityByTitle(cityTitle) != null) {
			return true;
		}
		return false;
	}

}
