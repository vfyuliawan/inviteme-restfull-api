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
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "gift")
public class Gift {
    @Id
    @Column(name = "id_gift")
    private String id;

    @OneToOne
    @JoinColumn(name = "id_project", referencedColumnName = "id_project")
    private Projects project;

    @OneToMany(mappedBy = "gift", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Gifts> gifts;

    @Column(name = "is_show")
    private boolean isShow;
}
