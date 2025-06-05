package com.project.demo.service;

import com.project.demo.entity.Event;
import com.project.demo.framework.TenantContext;
import com.project.demo.framework.TenantIdentifierResolver;
import com.project.demo.repository.EventRepository;
import com.project.demo.util.Util;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class EventService {
    public static final int NUMBER_OF_BATCH = 1000; // event is small data so 1000 records is OK!

    @Autowired
    private EventRepository eventRepository;

    //@Transactional
    public void importCSV(String fileName, InputStream inputStream) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            List<Event> list = new ArrayList<>();
            String line;
            int total = 0;
            while ((line = bufferedReader.readLine()) != null) {
                String[] fields = line.split(",");

                var timestamp = Util.from(fields[0], "yyyy-MM-dd HH:mm:ss");
                Integer value = null;
                if(fields.length >= 2){
                    value = Util.parse(fields[1]);
                }

                if(timestamp == null || fields.length < 2 || value == null || value < 0 || value > 255){
                    log.warn("importCSV() - invalid record: file: " + fileName + ", line: " + line);
                    continue;
                }

                var event = new Event(timestamp, value);
                list.add(event);

                if (list.size() == NUMBER_OF_BATCH) {
                    total += list.size();
                    log.info("importCSV() - save context: file: " + fileName + ", context: " + TenantContext.getContext().toString() + ", total: " + total);
                    //eventRepository.saveAll(list);
                    saveBatch(list);
                    list.clear();
                }
            }

            if (!list.isEmpty()) {
                total += list.size();
                log.info("importCSV() - save context: file: " + fileName + ", context: " + TenantContext.getContext().toString() + ", total: " + total);
                saveBatch(list);
                eventRepository.saveAll(list);
            }
        } catch (Exception ex) {
            log.error("importCSV(): file: " + fileName, ex);
        }
    }

    @Transactional
    public void saveBatch(List<Event> events) {
        eventRepository.saveAll(events);
    }
}

