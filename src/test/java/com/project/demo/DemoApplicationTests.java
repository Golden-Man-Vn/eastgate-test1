package com.project.demo;

import com.project.demo.entity.Event;
import com.project.demo.framework.TenantContext;
import com.project.demo.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class })
class DemoApplicationTests {
	@Autowired
	EventRepository eventRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void testSaveSchema() {
		Event event = new Event();
		event.setTimestamp(new Timestamp((new Date()).getTime()));
		event.setEventNumber(new Random().nextInt(255));

		TenantContext.setTenant("public");
		var test = eventRepository.saveAndFlush(event);
		assert(test.getEventNumber() == event.getEventNumber());


		TenantContext.setTenant("test");
		var test2 = eventRepository.saveAndFlush(event);
		assert(test.getEventNumber() == event.getEventNumber());
	}
}
