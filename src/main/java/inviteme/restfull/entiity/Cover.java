package inviteme.restfull.entiity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cover")
public class Cover {

    @Id
    @Column(name = "id")
    private String id;

    @OneToOne
    @JoinColumn(name = "id_project", referencedColumnName = "id_project")
    private Projects project;

    @Column(name = "title")
    private String title;

    @Column(name = "img")
    private String img;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "is_show")
    private Boolean isShow;


}
