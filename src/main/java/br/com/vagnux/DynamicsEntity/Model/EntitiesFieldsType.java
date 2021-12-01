package br.com.vagnux.DynamicsEntity.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class EntitiesFieldsType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String fieldName;
	private String fieldType;
	private String fieldregexValidation;
	private boolean fieldRequired;

	@JsonIgnore
	@OneToMany(mappedBy = "entitiesFieldsType")
	private List<EntitiesFieldsRelantionship> entitiesFieldsRelantionship = new ArrayList<>();

	public EntitiesFieldsType(String id, String fieldName, String fieldType, String fieldregexValidation,
			boolean fieldRequired) {
		super();
		this.id = id;
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.fieldregexValidation = fieldregexValidation;
		this.fieldRequired = fieldRequired;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getFieldregexValidation() {
		return fieldregexValidation;
	}

	public void setFieldregexValidation(String fieldregexValidation) {
		this.fieldregexValidation = fieldregexValidation;
	}

	public boolean isFieldRequired() {
		return fieldRequired;
	}

	public void setFieldRequired(boolean fieldRequired) {
		this.fieldRequired = fieldRequired;
	}

	public List<EntitiesFieldsRelantionship> getEntitiesFieldsRelantionship() {
		return entitiesFieldsRelantionship;
	}

	public void setEntitiesFieldsRelantionship(List<EntitiesFieldsRelantionship> entitiesFieldsRelantionship) {
		this.entitiesFieldsRelantionship = entitiesFieldsRelantionship;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fieldName == null) ? 0 : fieldName.hashCode());
		result = prime * result + (fieldRequired ? 1231 : 1237);
		result = prime * result + ((fieldType == null) ? 0 : fieldType.hashCode());
		result = prime * result + ((fieldregexValidation == null) ? 0 : fieldregexValidation.hashCode());
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
		EntitiesFieldsType other = (EntitiesFieldsType) obj;
		if (fieldName == null) {
			if (other.fieldName != null)
				return false;
		} else if (!fieldName.equals(other.fieldName))
			return false;
		if (fieldRequired != other.fieldRequired)
			return false;
		if (fieldType == null) {
			if (other.fieldType != null)
				return false;
		} else if (!fieldType.equals(other.fieldType))
			return false;
		if (fieldregexValidation == null) {
			if (other.fieldregexValidation != null)
				return false;
		} else if (!fieldregexValidation.equals(other.fieldregexValidation))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public EntitiesFieldsType() {
		super();
		// TODO Auto-generated constructor stub
	}

}
