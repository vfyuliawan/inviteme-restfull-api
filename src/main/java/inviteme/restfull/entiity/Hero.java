package inviteme.restfull.entiity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne; 
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Hero")
public class Hero {

    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "id_project", referencedColumnName = "id_project") 
    private Projects project;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "img")
    private String img;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "is_show")
    private Boolean isShow;

}
