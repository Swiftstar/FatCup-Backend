package com.fatcup.backend;

import java.io.File;

import org.simpleflatmapper.csv.CsvParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.fatcup.backend.data.Drink;
import com.fatcup.backend.data.DrinkRepository;

@Component
public class CsvService {
	
	@Autowired
	DrinkRepository drinkRepository;
	
	@EventListener(ApplicationStartedEvent.class)
	public void InitDrinkData() throws Exception {
		File file = ResourceUtils.getFile("classpath:csv/drinkdata.csv");
		
		CsvParser.mapTo(Drink.class).forEach(file, (s) -> {
			drinkRepository.save(s);
		});
	}
}
