package com.canchita.model.complex;


public enum DayOfWeek {

	MONDAY {
		@Override
		public String toString() {
			return "Lunes";
		}
	},
	
	TUESDAY {
		@Override
		public String toString() {
			return "Martes";
		}
	},
	
	WEDNESDAY {
		@Override
		public String toString() {
			return "Miércoles";
		}
	},
	
	THURSDAY {
		@Override
		public String toString() {
			return "Jueves";
		}
	},
	
	FRIDAY {
		@Override
		public String toString() {
			return "Viernes";
		}
	},
	
	SATURDAY {
		@Override
		public String toString() {
			return "Sábado";
		}
	},
	
	SUNDAY  {
		@Override
		public String toString() {
			return "Domingo";
		}
	};

	public static DayOfWeek fromId(long id) {
		if (DayOfWeek.values().length < id)
			throw new RuntimeException("DayOfWeek invalid");
		
		return DayOfWeek.values()[(int)id];
	}
}


