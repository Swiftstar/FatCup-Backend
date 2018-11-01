package com.fatcup.backend;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.simpleflatmapper.csv.CsvParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fatcup.backend.data.Drink;
import com.fatcup.backend.data.DrinkRepository;

@Component
public class CsvService {
	
	@Autowired
	DrinkRepository drinkRepository;
	
	@EventListener(ApplicationStartedEvent.class)
	public void InitDrinkData() throws Exception {
		InputStream inputStream = new ClassPathResource("csv/drinkdata.csv").getInputStream();
		Reader reader = new InputStreamReader(inputStream);
		
		CsvParser.mapTo(Drink.class).forEach(reader, (s) -> {
			drinkRepository.save(s);
		});
	}
}
