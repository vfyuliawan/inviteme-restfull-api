package inviteme.restfull.entiity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "gifts")
public class Gifts {
    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_gift", referencedColumnName = "id_gift")
    private Gift gift;

    @Column(name = "image")
    private String image;

    @Column(name = "name")
    private String name;

    @Column(name = "no_rek")
    private String norek;

    
}
