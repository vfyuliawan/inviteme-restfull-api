package inviteme.restfull.entiity;

import java.util.List;

import jakarta.persistence.CascadeType;
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

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "galery")
public class Galery {
    @Id
    @Column(name = "id_galery")
    private String id;

    @OneToOne
    @JoinColumn(name = "id_project", referencedColumnName = "id_project")
    private Projects project;

    @OneToMany(mappedBy = "galery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Galeries> galeries;

    @Column(name = "is_show")
    private boolean isShow;
}
