package inviteme.restfull.entiity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "theme_example")
// id VARCHAR(255) PRIMARY KEY,
// themeName VARCHAR(100),
// themeColor VARCHAR(100),
// bgimg VARCHAR(255),
// fgimg VARCHAR(255),
// colors VARCHAR(100)
public class ThemeExample {
    @Id
    private String id;

    @Column(name = "theme_name")
    private String name;

    @Column(name = "theme_color")
    private String color;

    @Column
    private String bgimg;

    @Column
    private String fgimg;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
