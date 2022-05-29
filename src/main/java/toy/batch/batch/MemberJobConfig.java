package toy.batch.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import toy.batch.company.entity.Company;

import javax.persistence.EntityManagerFactory;

@Configuration
@RequiredArgsConstructor
public class MemberJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory emf;

    @Bean
    public Job memberJob() {
        return jobBuilderFactory.get("memberJob")
                .incrementer(new RunIdIncrementer())
                .start(memberStep())
                .listener(new MemberJobListener())
                .build();
    }

    @Bean
    public Step memberStep() {
        return stepBuilderFactory.get("memberStep")
                .<Company, Company>chunk(100)
                .reader(CompanyItemReader())
                .processor(CompanyItemProcessor())
                .writer(CompanyItemWriter())
                .build();
    }

    @Bean
    public ItemReader<? extends Company> CompanyItemReader() {
        return new JpaPagingItemReaderBuilder<Company>()
                .name("companyReader")
                .entityManagerFactory(emf)
                .pageSize(10)
                .queryString("select c from Company c join fetch c.members ")
                .build();
    }

    @Bean
    public ItemProcessor<? super Company, ? extends Company> CompanyItemProcessor() {
        return item -> {
            item.getMembers().forEach(m -> {
                String name = m.getName();
                m.setName("update_" + name);
            });
            return item;
        };
    }

    @Bean
    public ItemWriter<? super Company> CompanyItemWriter() {
        return new JpaItemWriterBuilder<Company>()
                .entityManagerFactory(emf)
                .build();
    }

}
