package inviteme.restfull.entiity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Projects {

    @Id
    @Column(name = "id_project")
    private String id;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;

    @Column(name = "title", nullable = false)
    private String title;

    @OneToOne(mappedBy = "project")
    private Hero hero;

    @OneToOne(mappedBy = "project")
    private Home home;

    @OneToOne(mappedBy = "project")
    private Cover cover;

    @OneToOne(mappedBy = "project")
    private Theme theme;

    @OneToOne(mappedBy = "project")
    private Acara acara;

    @OneToOne(mappedBy = "project")
    private BraidInfo braidInfo;
}
