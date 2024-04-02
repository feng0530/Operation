package tw.idv.frank.csv.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tw.idv.frank.common.dto.CommonResult;
import tw.idv.frank.csv.model.entity.CsvDetail;
import tw.idv.frank.csv.sevice.CsvService;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/csv")
public class CsvController {

    @Autowired
    private CsvService csvService;

//    @Autowired
//    private Job productJob;
//    @Autowired
//    private JobLauncher jobLauncher;

    @PostMapping("/upload")
    public CommonResult<List<CsvDetail>> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name) {
        return csvService.upload(file, name);
    }
}

//    @GetMapping("/test")
//    public ResponseEntity productLoad() {
//        try{
//            JobParameters parameters = new JobParametersBuilder().addLong("Start-At" ,System.currentTimeMillis())
//                    .toJobParameters();
//            jobLauncher.run(productJob,parameters);
//            return ResponseEntity.ok("job run");
//
//        }
//        catch (Exception e){
//            return new ResponseEntity("job error " , HttpStatus.EXPECTATION_FAILED);
//        }
//    }