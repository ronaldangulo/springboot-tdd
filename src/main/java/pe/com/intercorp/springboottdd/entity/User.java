package pe.com.intercorp.springboottdd.entity;
import lombok.*;
import javax.persistence.*;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="USER")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name="USERNAME")
    private String username;

    @Column(name="PASSWORD")
    private  String password;
}
