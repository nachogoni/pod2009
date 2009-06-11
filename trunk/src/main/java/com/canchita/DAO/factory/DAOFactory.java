package com.canchita.DAO.factory;

import java.lang.reflect.Method;

import com.canchita.DAO.db.RegisterDB;
import com.canchita.DAO.db.ScoreSystemDB;
import com.canchita.DAO.db.UserDB;
import com.canchita.DAO.memorymock.BookingMemoryMock;
import com.canchita.DAO.memorymock.ComplexMemoryMock;
import com.canchita.DAO.memorymock.FieldMemoryMock;
import com.canchita.model.exception.PersistenceException;

/**
 * 
 * Class which acts as an entry point for all DAOs.
 * Each DAO must implement a static factory method
 * and annotate it with the {@code FactoryMethod} annotation,
 * which will be invoked by the DAOFactory.
 */
public class DAOFactory {

	/**
	 * Enum in charge of representing the DAO 
	 * classes.
	 */
	public static enum DAO {
		
		BOOKING(BookingMemoryMock.class),
		FIELD(FieldMemoryMock.class),
		COMPLEX(ComplexMemoryMock.class),
		USER(UserDB.class),
		REGISTER(RegisterDB.class),
		SCORE_SYSTEM(ScoreSystemDB.class);
		
		private Method factoryMethod;
		
		DAO(Class<?> clazz) {
			this.factoryMethod = this.getFactoryMethod(clazz);
		}
		
		private Method getFactoryMethod(Class<?> clazz) {
			
			for (Method m : clazz.getDeclaredMethods()) {
				if( m.isAnnotationPresent(FactoryMethod.class) ) {
					return m;
				}
			}
			return null;
		}

		public Method getFactoryMethod() {
			return this.factoryMethod;
		}
	}
	
	@SuppressWarnings("unchecked")
	/**
	 * This method returns a static factory indicated by
	 * the dao enum. It suppresses warnings as the line
	 * {@code instance = (E) m.invoke(null, (Object[])null);}
	 * returns an E instance.
	 * 
	 * @param dao Enum which indicates the DAO to load
	 */
	public static <E> E get(DAO dao) throws PersistenceException {

		E instance;
		Method m = dao.getFactoryMethod();
		try {
			instance = (E) m.invoke(null);
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new PersistenceException("Error al intentar acceder a la Base de Datos");
		}
		
		return instance;
	}
}
