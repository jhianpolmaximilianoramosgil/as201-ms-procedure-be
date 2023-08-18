package com.ms_procedure.controller;

import com.ms_procedure.model.Procedure;
import com.ms_procedure.service.ProcedureService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.FileNotFoundException;

@RestController
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin("*")
@RequestMapping("/v1/procedure")
public class ProcedureController {

    @Autowired
    private ProcedureService procedureService;

    @GetMapping
    public Flux<Procedure> findAll(){ return procedureService.findAll(); }

    @GetMapping("/id/{id}")
    public Flux<Procedure> findByIdd(@PathVariable Long id){
        return procedureService.findByIdd(id); }

    @GetMapping("/null")
    public Flux<Procedure> findByBatchNull(){ return procedureService.findByBatchNull(); }

    @GetMapping("/notnull")
    public Flux<Procedure> findByBatchNotNull(){
        return procedureService.findByBatchNotNull(); }

    @GetMapping("/batch/{batch}")
    public Flux<Procedure> findByBatch(@PathVariable Long batch){
        return procedureService.findByBatch(batch); }

    @PostMapping
    public Mono<Procedure> save(@RequestBody Procedure procedure){
        return procedureService.save(procedure);
    }

    @PutMapping
    public Mono<Procedure> update(@RequestBody Procedure procedure){
        return procedureService.update(procedure);
    }

    @PostMapping("/consolidate/{id}")
    public Mono<ResponseEntity<Procedure>> consolidate(@PathVariable Long id){ return procedureService.consolidate(id); }

    @PostMapping("/phase10/{id}")
    public void cambiarPhase10(@PathVariable Integer id) {
        procedureService.cambiarPhase10(id); }

    @GetMapping("/report1/{id}")
    public String generateReport(@PathVariable Long id) throws Exception, FileNotFoundException, JRException {
        return procedureService.exportReport1(id);
    }

    @GetMapping("/phase_id/{phase_id}")
    public Flux<Procedure> findByPhaseId(@PathVariable Integer phase_id)
    { return procedureService.findByPhaseId(phase_id); }




}
