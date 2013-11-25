package com.google.sandbox.infrastructure.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.powerup.domain.model.Exercise;
import br.com.powerup.domain.model.Training;
import br.com.powerup.domain.model.TrainingRepository;
import br.com.powerup.infrastructure.persistence.ObjectifyTrainingRepository;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;

public class ObjectifyTreinoRepositoryTest {

	final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig(),
			new LocalMemcacheServiceTestConfig());
	
	private TrainingRepository repository = new ObjectifyTrainingRepository();
	
	@Before
	public void helperSetUp() {
		helper.setUp();		
		new TrainingFixture();
	}


	@After
	public void helperTearDown() {
		helper.tearDown();
	}
	
	@Test
	public void simpleSave() {
		Training treino = treino();
		repository.save(treino);
	}

	private Training treino() {
		Training treino = new Training(TrainingFixture.U);
		treino.adicionaExercicio(new Exercise(TrainingFixture.LEG_PRESS));
		treino.adicionaExercicio(new Exercise(TrainingFixture.EX3));
		treino.adicionaExercicio(new Exercise(TrainingFixture.SUPINO_RETO));
		return treino;
	}
	
	@Test
	public void getByAncestorAfterSave() {
		Key<Training> k = repository.save(treino());
		Training t = repository.get(TrainingFixture.U);
		assertNotNull(t);
		assertEquals(3, t.getExercicios().size());
		assertEquals("Leg press", t.getExercicios().get(0).getName());
	}
	
	@Test
	public void list() {
		Key<Training> k = repository.save(treino());
		repository.save(treino());
		repository.save(treino());
		
		List<Training> t = repository.list(TrainingFixture.U);
		assertNotNull(t);
		assertEquals(4, t.size());
	}
}
