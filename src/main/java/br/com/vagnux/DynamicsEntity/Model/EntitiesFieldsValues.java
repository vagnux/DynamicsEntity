package br.com.vagnux.DynamicsEntity.Model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class EntitiesFieldsValues implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@OneToOne
	@JoinColumns({
	@JoinColumn(name = "entities_line_id"),
	@JoinColumn(name = "entities_fields_relationship_id" )
	})
	private EntitiesLines entitiesLines;

	@Id
	private String id;

	private String stringValue;
	private Integer integerValue;
	private Float floatValue;
	private LocalDateTime LocalDateTimeValue;
	private LocalDateTime createdAt  = LocalDateTime.now();

	public EntitiesFieldsValues() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EntitiesFieldsValues(EntitiesLines entitiesLines, String id, String stringValue, Integer integerValue,
			Float floatValue, LocalDateTime localDateTimeValue) {
		super();
		this.entitiesLines = entitiesLines;
		this.id = id;
		this.stringValue = stringValue;
		this.integerValue = integerValue;
		this.floatValue = floatValue;
		LocalDateTimeValue = localDateTimeValue;
		this.setCreatedAt(LocalDateTime.now());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entitiesLines == null) ? 0 : entitiesLines.hashCode());
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
		EntitiesFieldsValues other = (EntitiesFieldsValues) obj;
		if (entitiesLines == null) {
			if (other.entitiesLines != null)
				return false;
		} else if (!entitiesLines.equals(other.entitiesLines))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public EntitiesLines getEntitiesLines() {
		return entitiesLines;
	}

	public void setEntitiesLines(EntitiesLines entitiesLines) {
		this.entitiesLines = entitiesLines;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStringValue() {
		return stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public Integer getIntegerValue() {
		return integerValue;
	}

	public void setIntegerValue(Integer integerValue) {
		this.integerValue = integerValue;
	}

	public Float getFloatValue() {
		return floatValue;
	}

	public void setFloatValue(Float floatValue) {
		this.floatValue = floatValue;
	}

	public LocalDateTime getLocalDateTimeValue() {
		return LocalDateTimeValue;
	}

	public void setLocalDateTimeValue(LocalDateTime localDateTimeValue) {
		LocalDateTimeValue = localDateTimeValue;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	

}
