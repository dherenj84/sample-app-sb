package com.yourorg.sampleapp.core;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = { "id" })
public class Role {
	private int id;
	private String name;
}
