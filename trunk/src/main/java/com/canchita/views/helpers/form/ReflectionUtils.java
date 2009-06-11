package com.canchita.views.helpers.form;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to access easily to fields and methods using reflection.
 * 
 * */
public class ReflectionUtils {

	public static void setStaticField(Class<?> classWhereToSetField,
			String fieldName, Object fieldValue) {
		try {
			boolean found = false;
			for (Field field : classWhereToSetField.getDeclaredFields()) {
				if (field.getName().equals(fieldName)) {
					if (!field.isAccessible()) {
						field.setAccessible(true);
						field.set(classWhereToSetField, fieldValue);
						field.setAccessible(false);
					} else {
						field.set(classWhereToSetField, fieldValue);
					}
					found = true;
				}
			}
			;
			if (!found) {
				throw new NoSuchFieldException("No field with the name '"
						+ fieldName + "' was found");
			}
		} catch (SecurityException e) {
			throw new RuntimeException("An SecurityException occurred: "
					+ e.getMessage());
		} catch (NoSuchFieldException e) {
			throw new RuntimeException("An NoSuchFieldException occurred: "
					+ e.getMessage());
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("An IllegalArgumentException occurred: "
					+ e.getMessage());
		} catch (IllegalAccessException e) {
			throw new RuntimeException("An IllegalAccessException occurred: "
					+ e.getMessage());
		}
	}

	public static void setField(Object target, String fieldName,
			Object fieldValue) {
		try {
			setFieldInner(target, target.getClass(), fieldName, fieldValue);
		} catch (SecurityException e) {
			throw new RuntimeException("An SecurityException occurred: "
					+ e.getMessage());
		} catch (NoSuchFieldException e) {
			throw new RuntimeException("An NoSuchFieldException occurred: "
					+ e.getMessage());
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("An IllegalArgumentException occurred: "
					+ e.getMessage());
		} catch (IllegalAccessException e) {
			throw new RuntimeException("An IllegalAccessException occurred: "
					+ e.getMessage());
		}
	}

	public static void setFieldInner(Object target, Class<?> targetClass,
			String fieldName, Object fieldValue)
			throws IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException {
		for (Field field : targetClass.getDeclaredFields()) {
			if (field.getName().equals(fieldName)) {
				if (!field.isAccessible()) {
					field.setAccessible(true);
					field.set(target, fieldValue);
					field.setAccessible(false);
				} else {
					field.set(target, fieldValue);
				}
				return;
			}
		}
		;
		if (targetClass.getSuperclass() != null) {
			setFieldInner(target, targetClass.getSuperclass(), fieldName,
					fieldValue);
		} else {
			throw new NoSuchFieldException("No field with the name '"
					+ fieldName + "' was found");
		}
	}

	public static List<String> getMethodNames(Class<?> classWhereToGetFields) {
		List<String> fieldNameList = new ArrayList<String>();
		for (Method aMethod : classWhereToGetFields.getDeclaredMethods()) {
			fieldNameList.add(aMethod.getName());
		}
		return fieldNameList;
	}

	public static List<Method> getMethods(Class<?> classWhereToGetFields) {
		List<Method> methods = new ArrayList<Method>();
		for (Method aMethod : classWhereToGetFields.getDeclaredMethods()) {
			methods.add(aMethod);
		}
		return methods;
	}

	/**
	 * @return the method of the class specified by generatorMethodName or null
	 *         if none found
	 * */
	public static Method getMethod(Class<?> classWithMethod,
			String generatorMethodName) {
		try {
			return classWithMethod.getMethod(generatorMethodName);
		} catch (Exception e) {
			return null;
		}
	}

	public static Constructor<?> getOnlyConstructorAvailable(Class<?> cClass) {
		Constructor<?> c = cClass.getDeclaredConstructors()[0];
		if (!c.isAccessible()) {
			c.setAccessible(true);
		}
		return c;
	}
}
