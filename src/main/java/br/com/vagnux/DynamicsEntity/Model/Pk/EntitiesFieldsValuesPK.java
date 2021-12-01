package br.com.vagnux.DynamicsEntity.Model.Pk;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class EntitiesFieldsValuesPK implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;

	private String entities_fields_relationship_id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEntities_fields_relationship_id() {
		return entities_fields_relationship_id;
	}

	public void setEntities_fields_relationship_id(String entities_fields_relationship_id) {
		this.entities_fields_relationship_id = entities_fields_relationship_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((entities_fields_relationship_id == null) ? 0 : entities_fields_relationship_id.hashCode());
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
		EntitiesFieldsValuesPK other = (EntitiesFieldsValuesPK) obj;
		if (entities_fields_relationship_id == null) {
			if (other.entities_fields_relationship_id != null)
				return false;
		} else if (!entities_fields_relationship_id.equals(other.entities_fields_relationship_id))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	

}
