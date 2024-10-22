package inviteme.restfull.entiity;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LinkFilter {

    @OneToOne
    @JoinColumn(name = "id_project", referencedColumnName = "id_project")
    private Projects project;



    
}
