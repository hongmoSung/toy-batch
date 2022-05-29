package toy.batch.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import java.util.Objects;

@Slf4j
public class MemberJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("***************** memberJob start *****************");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        long startTime = Objects.requireNonNull(jobExecution.getStartTime()).getTime();
        long endTime = Objects.requireNonNull(jobExecution.getEndTime()).getTime();

        log.info("***************** memberJob end *****************");
        log.info("수행시간 -> {}", ((endTime - startTime) / 1000));
    }
}
