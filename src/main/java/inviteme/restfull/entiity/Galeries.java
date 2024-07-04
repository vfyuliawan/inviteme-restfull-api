package inviteme.restfull.entiity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
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
@Table(name = "galeries")
public class Galeries {
    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_galery", referencedColumnName = "id_galery")
    private Galery galery;

    @Lob
    @Column(name = "img", columnDefinition = "LONGTEXT")
    private String img;
}