package toy.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import toy.batch.company.entity.Company;
import toy.batch.member.Member;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@RequiredArgsConstructor
public class InitDummy {

    private final InitDummyService initDummyService;

    @PostConstruct
    public void init() {
        initDummyService.init();
    }

    @Component
    static class InitDummyService {

        @PersistenceContext
        private EntityManager em;

        @Transactional
        public void init() {
            for (int i = 0; i < 50; i++) {
                Company company = new Company("company_" + i);
                for (int j = 0; j < 100; j++) {
                    Member member = new Member(company.getName() + "_" + j);
                    company.addMember(member);
                }
                em.persist(company);
            }
        }

    }


}
