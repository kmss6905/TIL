package home;

import javax.persistence.*;

@Entity
public class Team {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;

  public void setName(String name) {
    this.name = name;
  }
}
