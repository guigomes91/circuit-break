package br.com.gomes.circuitbreaker.scheduler;

import br.com.gomes.circuitbreaker.service.ConsultaCepService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@EnableScheduling
@Component
public class SchedulerService implements Scheduler {

    @Value("${scheduler.fixedRate}")
    private int SCHEDULER_TIME_IN_MS;

    private int execuctions = 0;

    private final AtomicBoolean schedulerAtivo = new AtomicBoolean(true);

    @Autowired
    private ConsultaCepService cepService;

    @Scheduled(fixedRateString = "${scheduler.fixedRate}")
    @Override
    public void getCepScheduler() {
        if (!schedulerAtivo.get()) {
            log.warn("Scheduler desativated");
            return;
        }

        if (execuctions >= 5) {
            if (schedulerAtivo.compareAndSet(true, false)) {
                log.warn("Scheduler time exceeded");
                return;
            }
        }

        log.info("Executing scheduler at {} ms", SCHEDULER_TIME_IN_MS);
        var cep = cepService.consultarCep("13481040");
        log.info("Step {} -> Response {}", execuctions, cep);

        execuctions++;
    }
}
