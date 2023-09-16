package com.iam.backendCouture.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "Mesure")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Mesure {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_MESURE")
	protected Long idMesure;
	@Column(length = 4)
	protected Float tete;
	@Column(length = 4)
	protected Float cou;
	@Column(length = 4)
	protected Float epaule;
	@Column(length = 4)
	protected Float lBras;
	@Column(length = 4)
	protected Float poitrine;
	@Column(length = 4)
	protected Float hanches;
	@Column(length = 4)
	protected Float lCorps;
	@Column(length = 4)
	protected Float cuisse;
	@Column(length = 4)
	protected Float genou;
	@Column(length = 4)
	protected Float mollet;
	@Column(length = 4)
	protected Float cheville;
	@Column(length = 4)
	protected Float bicepts;
	@Column(length = 4)
	protected Float coude;
	@Column(length = 4)
	protected Float avantBras;
	@Column(length = 4)
	protected Float poignetCoude;
	@Column(length = 4)
	protected Float entreJambe;
	@Column(length = 4)
	protected Float coutureExt;
	@Column(length = 4)
	protected Float hTotale;
	@OneToOne
	@JoinColumn(name = "client")
	@JsonIgnore
	protected Client client;
}
