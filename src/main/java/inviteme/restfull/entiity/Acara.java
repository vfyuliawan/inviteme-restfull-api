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

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "acara")
public class Acara {

    @Id
    @Column(name = "id")
    private String id;

    @OneToOne
    @JoinColumn(name = "id_project", referencedColumnName = "id_project")
    private Projects project;

    @Column(name = "title_akad")
    private String titleAkad;

    @Column(name = "map_akad")
    private String mapAkad;

    @Column(name = "lokasi_akad")
    private String lokasiAkad;


    @Column(name = "img_akad")
    private String imgAkad;

    @Column(name = "date_akad")
    private LocalDateTime dateAkad;


    @Column(name = "title_resepsi")
    private String titleResepsi;

    @Column(name = "map_resepsi")
    private String mapResepsi;

    @Column(name = "lokasi_resepsi")
    private String lokasiResepsi;


    @Column(name = "img_resepsi")
    private String imgResepsi;

    @Column(name = "date_resepsi")
    private LocalDateTime dateResepsi;


}
