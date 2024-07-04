package inviteme.restfull.entiity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "story")
public class Story {

    @Id
    @Column(name = "id_story")
    private String id;

    @OneToOne
    @JoinColumn(name = "id_project", referencedColumnName = "id_project")
    private Projects project;

    @OneToMany(mappedBy = "story")
    private List<Stories> stories;

    @Column(name = "is_show")
    private boolean isShow;
}
