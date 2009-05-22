package com.canchita.controller.helper;

public enum UrlMapperType {

	GET {

		@Override
		public String toString() {
			return "GET";
		}

	},

	POST {

		@Override
		public String toString() {
			return "POST";
		}

	}

}
