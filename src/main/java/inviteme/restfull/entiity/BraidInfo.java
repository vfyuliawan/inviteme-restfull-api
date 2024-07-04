package inviteme.restfull.entiity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "braid_info")
public class BraidInfo {

    @Id
    @Column(name = "id")
    private String id;

    @OneToOne
    @JoinColumn(name = "id_project", referencedColumnName = "id_project")
    private Projects project;

    @Column(name = "male_name")
    private String maleName;

    @Column(name = "male_mom")
    private String maleMom;

    @Column(name = "male_dad")
    private String maleDad;

    @Lob
    @Column(name = "male_img", columnDefinition = "LONGTEXT")
    private String maleImg;

    @Column(name = "female_name")
    private String femaleName;

    @Column(name = "female_mom")
    private String femaleMom;

    @Column(name = "female_dad")
    private String femaleDad;

    @Lob
    @Column(name = "female_img", columnDefinition = "LONGTEXT")
    private String femaleImg;

    @Column(name = "is_show")
    private boolean isShow;


}
