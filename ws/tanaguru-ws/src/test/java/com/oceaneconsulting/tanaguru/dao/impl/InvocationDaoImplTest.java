package com.oceaneconsulting.tanaguru.dao.impl;

import com.oceaneconsulting.tanaguru.dao.AbstractDaoTest;
import com.oceaneconsulting.tanaguru.entity.WsUser;
import com.oceaneconsulting.tanaguru.entity.impl.WsRoleImpl;
import com.oceaneconsulting.tanaguru.entity.impl.WsUserImpl;

public class InvocationDaoImplTest extends AbstractDaoTest {
	
	 /**
     * Nom du fichier xml contenant le jeu de données à importer
     */
    private static final String INPUT_DATA_SET_FILENAME = "src/test/resources/dataSets/invocationDaoTest.xml";
    
    private WsInvocationDaoImpl invocationDao;

    /**
     * Constructeur
     * @param testName le nom du test 
     */
	public InvocationDaoImplTest(String testName) {
		super(testName);
		setInputDataFileName(INPUT_DATA_SET_FILENAME);
		invocationDao = (WsInvocationDaoImpl)springBeanFactory.getBean("invocationDao");
	}
	
	/**
	 * Test de la m�thode qui r�cup�re le nombre d'invocations faites par un utilisateur
	 */
	public void testGetCountInvocByUser(){
		WsRoleImpl role = new WsRoleImpl();
		role.setId(new Long(2));
		role.setRole("USER");
		role.setLabel("Known user");
		
		WsUser user = new WsUserImpl();
		user.setId(new Long(3));
		user.setName("INCONNU");
		user.setFirstName("INCONNU");
		user.setActive(true);
		user.setRole(role);
		int nbrInvocationByUser = invocationDao.getCountInvocByUser(user);
		assertEquals(5, nbrInvocationByUser);
	}
	
	/**
	 * Test de la m�thode qui r�cup�re le nombre d'invocations faites par un utilisateur pour un host donn�
	 */
	public void testGetCountInvocByUserHostNameHostIpRole(){
		WsRoleImpl role = new WsRoleImpl();
		role.setId(new Long(2));
		role.setRole("USER");
		role.setLabel("Known user");
		
		WsUser user = new WsUserImpl();
		user.setId(new Long(3));
		user.setName("INCONNU");
		user.setFirstName("INCONNU");
		user.setActive(true);
		user.setRole(role);
		int nbrInvocationByUser = invocationDao.getCountInvocByUser(user,"www.oceaneconsulting.com","192.168.15.2",role);
		assertEquals(3, nbrInvocationByUser);
	}
	
	/**
	 * Test de la m�thode qui r�cup�re le nombre d'invocations faites par un utilisateur via son identifiant
	 */
	public void testGetCountInvocByUserId(){
		Long userId = new Long(3);
		int nbrInvocationByUser = invocationDao.getCountInvocByUserId(userId);
		assertEquals(5, nbrInvocationByUser);
	}

}
