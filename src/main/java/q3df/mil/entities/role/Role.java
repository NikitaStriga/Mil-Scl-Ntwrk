package q3df.mil.entities.role;


import lombok.*;
import q3df.mil.entities.enums.SystemRoles;
import q3df.mil.entities.user.User;

import javax.persistence.*;


@Entity
@Table(name = "roles")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role")
    @Enumerated(value=EnumType.STRING)
    private SystemRoles role=SystemRoles.ROLE_USER;


    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne(cascade = {CascadeType.DETACH,
                    CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;


}
