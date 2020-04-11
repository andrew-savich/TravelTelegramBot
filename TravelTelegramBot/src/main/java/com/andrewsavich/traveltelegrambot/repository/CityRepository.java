package com.andrewsavich.traveltelegrambot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andrewsavich.traveltelegrambot.model.City;


public interface CityRepository extends JpaRepository<City, Integer> {
	City getCityByTitle(String cityTitle);
}
