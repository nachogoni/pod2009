package com.canchita.DAO;

import java.lang.reflect.Method;

import com.canchita.DAO.memorymock.BookingMemoryMock;
import com.canchita.DAO.memorymock.ComplexMemoryMock;
import com.canchita.DAO.memorymock.FieldMemoryMock;
import com.canchita.model.exception.PersistenceException;

/**
 * 
 * Class which acts as an entry point for all DAOs.
 * Each DAO must implement a static factory method
 * called getInstance which will be invoked by
 * the DAOFactory.
 */
public class DAOFactory {

	/**
	 * Enum in charge of representing the DAO 
	 * classes.
	 */
	public static enum DAO {
		
		BOOKING(BookingMemoryMock.class),
		FIELD(FieldMemoryMock.class),
		COMPLEX(ComplexMemoryMock.class);
		
		private Class<?> clazz;
		
		DAO(Class<?> clazz) {
			this.clazz = clazz;
		}
		
		public Class<?> getDAOClass() {
			return this.clazz;
		}
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * This method returns a static factory indicated by
	 * the dao enum. It supresses warnings as the line
	 * {code}instance = (E) m.invoke(null, (Object[])null);{/code}
	 * returns an E instance.
	 * 
	 * @param dao Enum which indicates the DAO to load
	 */
	public static <E> E get(DAO dao) throws PersistenceException {

		E instance;
		Class<? extends E> clazz = (Class<? extends E>) dao.getDAOClass();
		try {
			
			Method m = clazz.getDeclaredMethod("getInstance");
		
			instance = (E) m.invoke(null);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new PersistenceException("Error al intentar acceder a la Base de Datos");
		}
		
		return instance;
	}
}
