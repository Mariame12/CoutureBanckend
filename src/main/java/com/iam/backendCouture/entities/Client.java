package com.iam.backendCouture.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Set;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Client")
public class Client {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_CLIENT")
	private Long idClient ;
	@Column(length = 50, nullable = false)
	private String nom;
	@Column(length = 100, nullable = false)
	private String prenom;
	@Column(length = 30, unique = true)
	private String cni;
	@Column(length = 50)
	private String adresse;
	@Column(length = 10, nullable = false)
	private String sexe;
	@Column(length = 50 )
	private String email;
	@Column(length = 20, unique = true,nullable = false)
	private String telephone;
	@OneToOne
	private  Mesure mesure;
}
