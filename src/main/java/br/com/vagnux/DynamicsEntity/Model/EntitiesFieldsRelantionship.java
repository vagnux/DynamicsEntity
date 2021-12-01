package br.com.vagnux.DynamicsEntity.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class EntitiesFieldsRelantionship implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "entities_id")
	private Entities entities;

	@ManyToOne
	@JoinColumn(name = "entities_fields_types_id")
	private EntitiesFieldsType entitiesFieldsType;

	@JsonIgnore
	@OneToMany(mappedBy = "entitiesFieldsRelantionship")
	private List<EntitiesLines> entitiesLines = new ArrayList<>();

	public EntitiesFieldsRelantionship() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EntitiesFieldsRelantionship(String id, Entities entities, EntitiesFieldsType entitiesFieldsType) {
		super();
		this.id = id;
		this.entities = entities;
		this.entitiesFieldsType = entitiesFieldsType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		EntitiesFieldsRelantionship other = (EntitiesFieldsRelantionship) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Entities getEntities() {
		return entities;
	}

	public void setEntities(Entities entities) {
		this.entities = entities;
	}

	public EntitiesFieldsType getEntitiesFieldsType() {
		return entitiesFieldsType;
	}

	public void setEntitiesFieldsType(EntitiesFieldsType entitiesFieldsType) {
		this.entitiesFieldsType = entitiesFieldsType;
	}

	public List<EntitiesLines> getEntitiesLines() {
		return entitiesLines;
	}

	public void setEntitiesLines(List<EntitiesLines> entitiesLines) {
		this.entitiesLines = entitiesLines;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
