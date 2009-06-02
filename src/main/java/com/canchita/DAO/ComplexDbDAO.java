package com.canchita.DAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;

import com.canchita.model.booking.Expiration;
import com.canchita.model.booking.Schedule;
import com.canchita.model.complex.Availability;
import com.canchita.model.complex.Calendar;
import com.canchita.model.complex.Complex;
import com.canchita.model.complex.DayOfWeek;
import com.canchita.model.complex.ScoreSystem;
import com.canchita.model.db.DataBaseConnection;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.location.Locatable;
import com.canchita.model.location.Place;

public class ComplexDbDAO implements ComplexDAO {
	private DataBaseConnection db = null;
	
	@Override
	public void delete(Long id) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean exists(Long idComplex) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Complex> getAll() {
		// TODO Auto-generated method stub
		Collection<Complex> data = new ArrayList<Complex>();
		Complex aux = null;
		ResultSet rCursor = null;
		
		if (db.open()){
			
			try{
				rCursor = db.executeQuery("SELECT * FROM \"COMPLEX\"");
				while(rCursor.next()){
					
					aux = new Complex(rCursor.getString("name"));
					aux.setDescription(rCursor.getString("description"));
					
					data.add(aux);
				}
			}catch(Exception e){
				//TODO: error
				System.out.println("Error:" + e.getMessage());
			}
			db.endQuery();
		}
		
		db.close();
		return data;
	}

	@Override
	public Complex getById(Long id) throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Complex> getFiltered(String name, Locatable location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Complex> getFiltered(String filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Complex complex) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Complex complex) throws PersistenceException {
		// TODO Auto-generated method stub

	}

}
