package br.com.vagnux.DynamicsEntity;

import java.util.Arrays;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.vagnux.DynamicsEntity.Model.Entities;
import br.com.vagnux.DynamicsEntity.Model.EntitiesFieldsRelantionship;
import br.com.vagnux.DynamicsEntity.Model.EntitiesFieldsType;
import br.com.vagnux.DynamicsEntity.Model.EntitiesFieldsValues;
import br.com.vagnux.DynamicsEntity.Model.EntitiesLines;
import br.com.vagnux.DynamicsEntity.Repository.EntitiesFieldsRelationshipRepository;
import br.com.vagnux.DynamicsEntity.Repository.EntitiesFieldsTypeRepository;
import br.com.vagnux.DynamicsEntity.Repository.EntitiesFieldsValuesRepository;
import br.com.vagnux.DynamicsEntity.Repository.EntitiesLinesRepository;
import br.com.vagnux.DynamicsEntity.Repository.EntitiesRepository;

@Component
public class DummyData implements CommandLineRunner {

	@Autowired
	private EntitiesRepository entityRepository;

	@Autowired
	private EntitiesFieldsTypeRepository entitiesFieldsTypeRepository;

	@Autowired
	private EntitiesFieldsRelationshipRepository entitiesFieldsRelationshipRepository;

	@Autowired
	private EntitiesLinesRepository entitiesLinesRepository;

	@Autowired
	private EntitiesFieldsValuesRepository entitiesFieldsValuesRepository;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Running DummyData");
		/*
		entitiesFieldsValuesRepository.deleteAll();
		entitiesLinesRepository.deleteAll();
		entitiesFieldsRelationshipRepository.deleteAll();
		entitiesFieldsTypeRepository.deleteAll();
		entityRepository.deleteAll();

		Entities e1 = new Entities(UUID.randomUUID().toString(), "PLACES");
		Entities e2 = new Entities(UUID.randomUUID().toString(), "PEOPLE");

		entityRepository.saveAll(Arrays.asList(e1, e2));

		EntitiesFieldsType f1 = new EntitiesFieldsType(UUID.randomUUID().toString(), "name", "String", "", false);
		EntitiesFieldsType f2 = new EntitiesFieldsType(UUID.randomUUID().toString(), "slug", "String", "", false);
		EntitiesFieldsType f3 = new EntitiesFieldsType(UUID.randomUUID().toString(), "cpf", "String",
				"(^\\d{3}\\x2E\\d{3}\\x2E\\d{3}\\x2D\\d{2}$)", false);
		EntitiesFieldsType f4 = new EntitiesFieldsType(UUID.randomUUID().toString(), "cnpj", "String",
				"\\d{2}.?\\d{3}.?\\d{3}/?\\d{4}-?\\d{2}", false);
		EntitiesFieldsType f5 = new EntitiesFieldsType(UUID.randomUUID().toString(), "birthdate", "LocalDateTime",
				"^\\d{1,2}\\/\\d{1,2}\\/\\d{4}$", false);

		entitiesFieldsTypeRepository.saveAll(Arrays.asList(f1, f2, f2, f3, f4, f5));

		EntitiesFieldsRelantionship r1 = new EntitiesFieldsRelantionship(UUID.randomUUID().toString(), e1, f1);
		EntitiesFieldsRelantionship r2 = new EntitiesFieldsRelantionship(UUID.randomUUID().toString(), e1, f2);
		EntitiesFieldsRelantionship r3 = new EntitiesFieldsRelantionship(UUID.randomUUID().toString(), e1, f4);

		EntitiesFieldsRelantionship r4 = new EntitiesFieldsRelantionship(UUID.randomUUID().toString(), e2, f1);
		EntitiesFieldsRelantionship r5 = new EntitiesFieldsRelantionship(UUID.randomUUID().toString(), e2, f3);
		EntitiesFieldsRelantionship r6 = new EntitiesFieldsRelantionship(UUID.randomUUID().toString(), e2, f5);

		entitiesFieldsRelationshipRepository.saveAll(Arrays.asList(r1, r2, r2, r3, r4, r5, r6));

		String line = UUID.randomUUID().toString();
		EntitiesLines ca1 = new EntitiesLines(r1, line);
		EntitiesLines ca2 = new EntitiesLines(r2, line);
		EntitiesLines ca3 = new EntitiesLines(r3, line);

		line = UUID.randomUUID().toString();
		EntitiesLines cb1 = new EntitiesLines(r1, line);
		EntitiesLines cb2 = new EntitiesLines(r2, line);
		EntitiesLines cb3 = new EntitiesLines(r3, line);

		entitiesLinesRepository.saveAll(Arrays.asList(ca1, ca2, ca3, cb1, cb2, cb3));

		EntitiesFieldsValues v1 = new EntitiesFieldsValues(ca1, UUID.randomUUID().toString(), "Padaria do Joaquim",
				null, null, null);
		EntitiesFieldsValues v2 = new EntitiesFieldsValues(ca2, UUID.randomUUID().toString(), "Padaria_do_Joaquim",
				null, null, null);
		EntitiesFieldsValues v3 = new EntitiesFieldsValues(ca3, UUID.randomUUID().toString(), "32.999.225/0001-90",
				null, null, null);

		EntitiesFieldsValues v4 = new EntitiesFieldsValues(cb1, UUID.randomUUID().toString(), "Monte arara", null, null,
				null);
		EntitiesFieldsValues v5 = new EntitiesFieldsValues(cb2, UUID.randomUUID().toString(), "monte_arara", null, null,
				null);
		EntitiesFieldsValues v6 = new EntitiesFieldsValues(cb3, UUID.randomUUID().toString(), "51.486.028/0001-11",
				null, null, null);

		entitiesFieldsValuesRepository.saveAll(Arrays.asList(v1, v2, v3, v4, v5, v6));
		*/
	}

}
