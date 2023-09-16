package com.iam.backendCouture.entities;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MesureF")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MesureF extends Mesure{
	private Float dessousPoit;
	private Float cretesIliaque;
	private Float lDos;
}
