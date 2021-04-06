package br.com.test.swatintel.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import br.com.test.swatintel.entity.TrainRoute;

@Service
public class TrainService {

	@Value("classpath:data/iTrain.csv")
	private Resource iTrainFile;

	@Cacheable("TrainService.findAll")
	public List<TrainRoute> findAll() throws IOException {		
		CsvSchema bootstrapSchema = CsvSchema.builder().
					addColumn("tripNumber").
					addColumn("originStation").
					addColumn("destinyStation").
					addColumn("date").
					addColumn("departureTime").
					addColumn("arrivalTime").
					addColumn("price").
					setUseHeader(true)
					.build();
		CsvMapper mapper = new CsvMapper();
		
		JavaTimeModule module = new JavaTimeModule();
		module.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
		module.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy/MM/dd")));		
		mapper.registerModule(module);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		MappingIterator<TrainRoute> readValues = mapper.readerFor(TrainRoute.class).with(bootstrapSchema).readValues(iTrainFile.getInputStream());
		return readValues.readAll();
	}
	
}
