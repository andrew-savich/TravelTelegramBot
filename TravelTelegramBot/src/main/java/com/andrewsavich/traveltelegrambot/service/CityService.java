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

}
