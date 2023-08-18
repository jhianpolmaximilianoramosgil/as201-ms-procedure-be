package com.ms_procedure.feignclient;

import com.ms_procedure.model.Person;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@ReactiveFeignClient(value = "ms-person", url = "${client.ms-person.url}", configuration = FeignConfig.class)
public interface PersonFeignClient {

    @GetMapping
    Flux<Person> findAllPerson();

    @GetMapping("/id/{person_id}")
    Mono<Person> findPersonById(@PathVariable("person_id") Integer person_id);

}

