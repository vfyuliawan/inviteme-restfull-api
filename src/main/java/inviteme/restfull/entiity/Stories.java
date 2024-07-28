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
import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stories")
public class Stories {

    @Id
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_story", referencedColumnName = "id_story")
    private Story story;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Lob
    @Column(name = "img", columnDefinition = "LONGTEXT")
    private String img;

    @Column(name = "date")
    private LocalDateTime date;
}