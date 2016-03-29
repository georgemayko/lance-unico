package br.com.triadworks.lanceunico.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.triadworks.lanceunico.modelo.Lance;
import br.com.triadworks.lanceunico.modelo.Promocao;
import br.com.triadworks.lanceunico.modelo.Status;
import br.com.triadworks.lanceunico.util.JPAUtil;

public class PromocaoDao {
	
	private EntityManager entityManager;
	
	/**
	 * Não utilizado pelo CDI
	 */
	public PromocaoDao() {
		this(new JPAUtil().getEntityManager());
	}
	
	@Inject
	public PromocaoDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public List<Promocao> lista() {
		List<Promocao> promocoes = entityManager
				.createQuery("select p from Promocao p", Promocao.class)
				.getResultList();
		
		return promocoes;
	}
	
	public List<Promocao> abertas() {
		List<Promocao> promocoes = entityManager
				.createQuery("select p from Promocao p "
						+ "where p.status = :status", Promocao.class)
				.setParameter("status", Status.ABERTA)
				.getResultList();
		
		return promocoes;
	}

	public Promocao carrega(Integer id) {
		return entityManager.find(Promocao.class, id);
	}

	public void salva(Promocao promocao) {
		entityManager.persist(promocao);		
	}
	
	public void atualiza(Promocao promocao) {
		entityManager.merge(promocao);
	}
	
	/**
	 * Registra novo lance numa promoção existente
	 */
	public void registraLance(Integer id, Lance lance) {
		
		Promocao promocao = carrega(id);
		promocao.registra(lance);
		
		entityManager.merge(promocao);
	}

}
