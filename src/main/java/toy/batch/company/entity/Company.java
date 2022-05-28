package toy.batch.company.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import toy.batch.member.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Member> members = new ArrayList<>();

    public void addMember(Member member) {
        this.members.add(member);
        member.setCompany(this);
    }

    public Company(String name) {
        this.name = name;
    }
}
