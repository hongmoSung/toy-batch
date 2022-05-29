package toy.batch.member;

import lombok.*;
import toy.batch.company.entity.Company;

import javax.persistence.*;

@ToString(exclude = {"company"})
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String name;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    public void changeCompany(Company company) {
        this.company = company;
        this.getCompany().getMembers().add(this);
    }

    public Member(String name) {
        this.name = name;
    }
}
