package com.ms_procedure.repository;

import com.ms_procedure.model.Procedure;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProcedureRepository extends ReactiveCrudRepository<Procedure, Long> {

    @Query("SELECT * FROM procedure WHERE batch IS NULL ORDER BY id DESC")
    Flux<Procedure> findByBatchNull();

    @Query("SELECT * FROM procedure WHERE id = :id")
    Flux<Procedure> findByIdd(@Param("id") Long id);


    @Query("SELECT * FROM procedure WHERE batch IS NOT NULL ORDER BY id DESC")
    Flux<Procedure> findByBatchNotNull();

    @Query("SELECT * FROM procedure WHERE batch = :batch ORDER BY id DESC")
    Flux<Procedure> findByBatch(@Param("batch") Long batch);
    /*Flux<Procedure> findByBatch();*/

    @Query("SELECT * FROM procedure WHERE phase_id = :phase_id ORDER BY id DESC")
    Flux<Procedure> findByPhaseId(@Param("phase_id") Integer phaseId);

 /*   @Query("SELECT * FROM document_phase WHERE document_type_id = :document_type_id ORDER BY id DESC")
    Flux<Document_phase> findDocumentTypeById(@Param("document_type_id") Long document_type_id);
*/
}