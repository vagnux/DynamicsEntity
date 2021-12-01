package br.com.vagnux.DynamicsEntity.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.vagnux.DynamicsEntity.Model.Pk.EntitiesLinesPK;

@Entity
public class EntitiesLines implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "entities_fields_relationship_id", insertable = false, updatable = false)
	private EntitiesFieldsRelantionship entitiesFieldsRelantionship;

	@OneToOne(mappedBy = "entitiesLines")
	private EntitiesFieldsValues entitiesFieldsValues;

	@Id
	private EntitiesLinesPK id = new EntitiesLinesPK();
	
	private LocalDateTime createdAt  = LocalDateTime.now();
	private LocalDateTime deletedAt;

	public EntitiesLines(EntitiesFieldsRelantionship entitiesFieldsRelantionship, String id) {
		super();
		this.entitiesFieldsRelantionship = entitiesFieldsRelantionship;
		this.id.setId(id);
		this.id.setEntities_fields_relationship_id(entitiesFieldsRelantionship.getId());
		this.setCreatedAt(LocalDateTime.now());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entitiesFieldsRelantionship == null) ? 0 : entitiesFieldsRelantionship.hashCode());
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
		EntitiesLines other = (EntitiesLines) obj;
		if (entitiesFieldsRelantionship == null) {
			if (other.entitiesFieldsRelantionship != null)
				return false;
		} else if (!entitiesFieldsRelantionship.equals(other.entitiesFieldsRelantionship))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public EntitiesFieldsRelantionship getEntitiesFieldsRelantionship() {
		return entitiesFieldsRelantionship;
	}

	public void setEntitiesFieldsRelantionship(EntitiesFieldsRelantionship entitiesFieldsRelantionship) {
		this.entitiesFieldsRelantionship = entitiesFieldsRelantionship;
	}

	public EntitiesLinesPK getId() {
		return id;
	}

	public void setId(EntitiesLinesPK id) {
		this.id = id;
	}

	public EntitiesLines() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EntitiesFieldsValues getEntitiesFieldsValues() {
		return entitiesFieldsValues;
	}

	public void setEntitiesFieldsValues(EntitiesFieldsValues entitiesFieldsValues) {
		this.entitiesFieldsValues = entitiesFieldsValues;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(LocalDateTime deletedAt) {
		this.deletedAt = deletedAt;
	}


}
