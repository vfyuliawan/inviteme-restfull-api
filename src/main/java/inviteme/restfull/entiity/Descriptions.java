package inviteme.restfull.entiity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "descriptions")
public class Descriptions {
    
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "quotes_by")
    private String quotesBy;

    @Column(name = "quotes")
    private String quotes;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
