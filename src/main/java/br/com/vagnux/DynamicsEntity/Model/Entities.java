package br.com.vagnux.DynamicsEntity.Model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Entities implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String name;

	
	@OneToMany(mappedBy = "entities")
	private Set<EntitiesFieldsRelantionship> entitiesFieldsRelantionship;

	public Entities(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entities other = (Entities) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<EntitiesFieldsRelantionship> getEntitiesFieldsRelantionship() {
		return entitiesFieldsRelantionship;
	}

	public void setEntitiesFieldsRelantionship(Set<EntitiesFieldsRelantionship> entitiesFieldsRelantionship) {
		this.entitiesFieldsRelantionship = entitiesFieldsRelantionship;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Entities() {
		super();
		// TODO Auto-generated constructor stub
	}

}
