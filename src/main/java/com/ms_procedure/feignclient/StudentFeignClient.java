package com.ms_procedure.feignclient;

import com.ms_procedure.model.Student;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@ReactiveFeignClient(value = "ms-student", url = "${client.ms-student.url}", configuration = FeignConfig.class)
public interface StudentFeignClient {

    @GetMapping
    Flux<Student> findAllStudent();

    @GetMapping("/id/{student_id}")
    Mono<Student> findStudentById(@PathVariable("student_id") Integer student_id);

}
