package com.ms_procedure.service.impl;

import com.ms_procedure.feignclient.PersonFeignClient;
import com.ms_procedure.feignclient.StudentFeignClient;
import com.ms_procedure.model.Person;
import com.ms_procedure.model.Procedure;
import com.ms_procedure.model.Student;
import com.ms_procedure.repository.ProcedureRepository;
import com.ms_procedure.service.ProcedureService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import reactor.core.scheduler.Schedulers;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import lombok.extern.slf4j.Slf4j;
import lombok.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.*;
import java.io.File;
import java.io.FileNotFoundException;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProcedureServiceImpl extends Conexion implements ProcedureService {

    @Autowired
    private ProcedureRepository procedureRepository;


    @Autowired
    private StudentFeignClient studentFeignClient;

    @Autowired
    private PersonFeignClient personFeignClient;

    @Override
    public Flux<Procedure> findAll() {
        log.info("Mostrando todas los procedimientos");
        //return procedureRepository.findAll();
        Flux<Procedure> list = procedureRepository.findAll().publishOn(Schedulers.boundedElastic());
        return findProcedureTransacction(list);
    }


    @Override
    public Flux<Procedure> findByIdd(Long id) {
        log.info("Procedimiento encontrado con el ID = " + id);
        //return procedureRepository.findById(id);
        Flux<Procedure> list = procedureRepository.findByIdd(id).publishOn(Schedulers.boundedElastic());
        return findProcedureTransacction(list);
    }


    @Override
    public Flux<Procedure> findByBatchNull() {
        log.info("Mostrando todas los procedimientos NULL");
        return procedureRepository.findByBatchNull();
    }

    @Override
    public Flux<Procedure> findByBatchNotNull() {
        log.info("Mostrando todas los procedimientos NOT NULL");
        return procedureRepository.findByBatchNotNull();
    }

    @Override
    public Flux<Procedure> findByBatch(Long batch) {
        log.info("Mostrando todas los procedimientos por campo batcj");
        return procedureRepository.findByBatch(batch);
    }

    @Override
    public Mono<Procedure> save(Procedure procedure) {
        log.info("Procedimiento creado = " + procedure.toString());
        //  procedure.setProcedure_config_id(1);
        return procedureRepository.save(procedure);
    }

    @Override
    public Mono<Procedure> update(Procedure procedure) {
        log.info("Procedimiento actualizado = " + procedure.toString());
        return procedureRepository.save(procedure);
    }

    @Override
    public Mono<ResponseEntity<Procedure>> consolidate(Long id) {
        log.info("Procedimiento consolidado = " + id);
        return procedureRepository.findById(id).flatMap(newProcedure -> {
            Long batch = 0L;
            //++batch;
            //Procedure procedure = new Procedure();
            //procedure.setBatch(++batch);
            newProcedure.setBatch(++batch);
            return procedureRepository.save(newProcedure);
        }).map(updatedDocument -> new ResponseEntity<>(updatedDocument, HttpStatus.OK)).defaultIfEmpty(new ResponseEntity<>(HttpStatus.OK));
    }

    @Override
    public void cambiarPhase10(Integer id) {
        String sql = "UPDATE procedure SET phase_id=8, link='https://ipfs.io/ipfs/QmYTZMXyV7NMVpvYGRZRg71HVsfp5ASgBZBaApt6pkwh9z' WHERE id=?";
        try {
            PreparedStatement ps = Conexion.conectar().prepareStatement(sql);
            // ps.setString(1, "https://ipfs.io/ipfs/QmdzCsgJNgDVsZxS2sMYcK5eSrJVuAUvtY3PvQU1Ew2Tqu?filename=titulo.jpg");
            ps.setInt(1, 13);
            ps.executeUpdate();
            ps.close();
        } catch (Exception e) {
            System.out.println("Error en cambiarPhase8" + e.getMessage());
        } finally {
            this.cerrar();
        }
    }

    @Override
    public Flux<Procedure> findByPhaseId(Integer phaseId) {
        log.info("Procedimiento encontrado con el phase_id = " + phaseId);
        //return procedureRepository.findByPhaseId(phase_id);
        Flux<Procedure> list = procedureRepository.findByPhaseId(phaseId).publishOn(Schedulers.boundedElastic());
        return findProcedureTransacction(list);
    }

    public String exportReport1(Long id) throws Exception, FileNotFoundException, JRException {
        //String path = "C:\\Users\\Jhianpol\\JaspersoftWorkspace\\MyReports";
        //String path = "D:\\Intellij_IDEA\\SUSCRIBIR\\ms-procedure\\src\\main\\java\\com\\ms_procedure\\reports";
        //String path = "D:\\Intellij_IDEA\\SUSCRIBIR\\ms-procedure\\src\\main\\java\\com\\ms_procedure\\reports";
        String path = "D:\\Intellij_IDEA\\SUSCRIBIR\\ms-procedure\\src\\main\\java\\com\\ms_procedure\\reports";
        List<Procedure> procedures = listarTodos(id);
        //load file and compile it
        File file = ResourceUtils.getFile("classpath:PostgresProcedure2.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(procedures);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Java Techie");
        parameters.put("titulo", id);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
 /*       if (reportFormat.equalsIgnoreCase("html")) {
            JasperExportManager.exportReportToHtmlFile(jasperPrint, path + "\\titi.html");
        }
        if (reportFormat.equalsIgnoreCase("pdf")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\titi.pdf");
        }
        if (reportFormat.equalsIgnoreCase("id")) {
            JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\titi.pdf");
        }*/
        JasperExportManager.exportReportToPdfFile(jasperPrint, path + "\\EgresadoTítulo.pdf");
        return "report generated in path : " + path;
    }




    public List listarTodos(Long id) throws Exception {
        List<Procedure> listado = null;
        Procedure pers;
        /*    String sql = "select * from Student";*/
        String sql = "SELECT * FROM Procedure WHERE id = '" + id + "'";
        try {
            listado = new ArrayList();
            Statement st = this.conectar().createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                pers = new Procedure();
                pers.setId(rs.getLong("id"));
   /*             pers.setNombre(rs.getString("NOMPER"));
                pers.setApellido(rs.getString("APEPER"));
                pers.setDni(rs.getString("DNIPER"));
                pers.setCelular(rs.getString("CELPER"));
                pers.setEmail(rs.getString("EMAPER"));
                pers.setSexo(rs.getString("SEXPER"));
                pers.setCargo(rs.getString("CARPER"));*/
                listado.add(pers);
            }
            rs.close();
            st.close();

        } catch (Exception e) {
            System.out.println("Error en listarTodosD:" + e.getMessage());
        } finally {
            this.cerrar();
        }
        return listado;
    }

    public Flux<Procedure> findProcedureTransacction(Flux<Procedure> list) {
        return list.concatMap(Flux::just).publishOn(Schedulers.boundedElastic()).flatMap(dataProcedure -> {
          //  Flux<DocumentsAttachments> documentsAttachments = documentsAttachmentsFeignClient.findDocumentsAttachmentsByProcedureId(dataProcedure.getId());

            Mono<Student> student = studentFeignClient.findStudentById(dataProcedure.getStudent_id());

            Mono<Person> person = personFeignClient.findPersonById(dataProcedure.getPerson_id());

            return Flux.zip(student, person).flatMap(tuple -> {
                /*dataProcedure.setAttached1(tuple.getT1().getAttached());
                dataProcedure.setAttached2(tuple.getT1().getAttached());
                dataProcedure.setAttached3(tuple.getT1().getAttached());
                dataProcedure.setAttached4(tuple.getT1().getAttached());*/

                dataProcedure.setInstitutionalEmail(tuple.getT1().getInstitutional_email());
                dataProcedure.setCareer_name(tuple.getT1().getInstitutional_email()+", "+ tuple.getT1().getCareer_name());

                dataProcedure.setPersonName(tuple.getT2().getName()+", "+ tuple.getT2().getLastname());
                return Flux.just(dataProcedure);
            });
        } );
    }

}
