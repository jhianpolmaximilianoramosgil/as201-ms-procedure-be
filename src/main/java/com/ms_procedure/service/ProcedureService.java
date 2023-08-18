package com.ms_procedure.service;

import com.ms_procedure.model.Procedure;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.io.FileNotFoundException;

@Service
public interface ProcedureService {

    Flux<Procedure> findAll();

    Flux<Procedure> findByIdd(Long id);

    Flux<Procedure> findByBatchNull();

    Flux<Procedure> findByBatchNotNull();

    Flux<Procedure> findByBatch(Long batch);

    Mono<Procedure> save(Procedure procedure);

    Mono<Procedure> update(Procedure procedure);

    Mono<ResponseEntity<Procedure>> consolidate(Long id);

    void cambiarPhase10(Integer id);

    Flux<Procedure> findByPhaseId(Integer phaseId);

    String exportReport1(Long id) throws Exception, FileNotFoundException, JRException;

}
