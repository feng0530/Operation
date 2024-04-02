//package tw.idv.frank.common.config;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.batch.MyBatisBatchItemWriter;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
//import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
//import org.springframework.batch.item.file.mapping.FieldSetMapper;
//import org.springframework.batch.item.file.transform.FieldSet;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.transaction.PlatformTransactionManager;
//import tw.idv.frank.csv.model.entity.CsvDetail;
//import tw.idv.frank.csv.model.mapper.CsvDetailMapper;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//@EnableBatchProcessing
//public class BatchConfig {
//
//    @Autowired
//    private CsvDetailMapper csvDetailMapper;
//
//    @Autowired
//    private SqlSessionFactory sqlSessionFactory;
//
//    @Bean
//    public Step stepProduct(JobRepository jobRepository , PlatformTransactionManager transactionManage) {
//        Step step = new StepBuilder("stepProduct", jobRepository)
//                .<List<CsvDetail>, CsvDetail>chunk(10 , transactionManage)
//                .reader(csvReader())
//                .writer(csvWriter())
//                .build();
//        return step;
//    }
//
//    @Bean
//    public Job productJob(JobRepository jobRepository , Step step1 ) {
//        return new JobBuilder("productJob", jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(step1)
//                .build();
//    }
//
//    @Bean
//    public FlatFileItemReader<List<CsvDetail>> csvReader() {
//        return new FlatFileItemReaderBuilder<List<CsvDetail>>().name("productItemReader")
//                .resource(new ClassPathResource("/static/file.csv"))
//                .linesToSkip(1)
//                .delimited()
//                .names(new String[]{"csvId", "name", "gender", "age"})
//                .fieldSetMapper(new CsvDetailFieldSetMapper())
//                .build();
//    }
//
//
//    @Bean
//    public MyBatisBatchItemWriter<CsvDetail> csvWriter() {
//        MyBatisBatchItemWriter<CsvDetail> myBatisBatchItemWriter = new MyBatisBatchItemWriter<>();
//        myBatisBatchItemWriter.setSqlSessionFactory(sqlSessionFactory);
//        myBatisBatchItemWriter.setStatementId("insertCsvDetail");
//        return myBatisBatchItemWriter;
//    }
//}
// class CsvDetailFieldSetMapper implements FieldSetMapper<List<CsvDetail>> {
//    @Override
//    public List<CsvDetail> mapFieldSet(FieldSet fieldSet) {
//        List<CsvDetail> csvDetails = new ArrayList<>();
//        CsvDetail csvDetail = new CsvDetail();
//        csvDetail.setCsvId(fieldSet.readInt("csvId"));
//        csvDetail.setName(fieldSet.readString("name"));
//        csvDetail.setGender(fieldSet.readString("gender"));
//        csvDetail.setAge(fieldSet.readInt("age"));
//        csvDetails.add(csvDetail);
//        return csvDetails;
//    }
//}