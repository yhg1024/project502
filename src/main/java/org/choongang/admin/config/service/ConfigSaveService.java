package org.choongang.admin.config.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.choongang.admin.config.entities.Configs;
import org.choongang.admin.config.repositories.ConfigsRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigSaveService {
    private final ConfigsRepository repository;

    public void save(String code, Object data) {
        Configs configs = repository.findById(code).orElseGet(Configs::new);
        // 코드를 조회해서 있으면 configs에 넣고, 없으면 새로만든다.

        // 날짜 시간 변환
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());

        try {
            String jsonString = om.writeValueAsString(data);
            configs.setData(jsonString);
            configs.setCode(code); // 처음 만든 엔티티에 코드값 넣기
            repository.saveAndFlush(configs);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
