package inviteme.restfull.entiity;

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

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "theme")
public class Theme {

    @Id
    @Column(name = "id")
    private String id;

    @OneToOne
    @JoinColumn(name = "id_project", referencedColumnName = "id_project") 
    private Projects project;
    
    @Column(name = "slug")
    private String slug;

    @Column(name = "alamat")
    private String alamat;

    @Column(name = "embeded")
    private String embeded;

    @Column(name = "theme")
    private String theme;

    @Column(name = "music")
    private String music;

    @Column(name = "primary_color")
    private String primaryColor;

    @Column(name = "secondary_color")
    private String secondaryColor;

    @Column(name = "text_color1")
    private String textColor1;

    @Column(name = "text_color2")
    private String textColor2;

}
