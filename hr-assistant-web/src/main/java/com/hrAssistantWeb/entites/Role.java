package com.hrAssistantWeb.entites;


import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	public Role() {
	}
}
