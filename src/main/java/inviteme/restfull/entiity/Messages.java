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

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class Messages {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_message", referencedColumnName = "id_message")
    private Message message;

    @Column(name = "name")
    private String name;

    @Column(name = "text")
    private String text;

    @Column(name = "present")
    private String present;

}
