/*******************************************************************************
 * Copyright: (c) 2004-2009 Mayo Foundation for Medical Education and 
 * Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
 * triple-shield Mayo logo are trademarks and service marks of MFMER.
 * 
 * Except as contained in the copyright notice above, or as used to identify 
 * MFMER as the author of this software, the trade names, trademarks, service
 * marks, or product names of the copyright holder shall not be used in
 * advertising, promotion or otherwise in connection with this software without
 * prior written authorization of the copyright holder.
 *   
 * Licensed under the Eclipse Public License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 *   
 *  		http://www.eclipse.org/legal/epl-v10.html
 * 
 *  		
 *******************************************************************************/
package org.LexGrid.LexBIG.caCore.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;

public class LexEVSCaCoreUtils {

	public static Object setFieldValue(Object input, Object value, String fieldName)
	throws Exception {
		Class searchClass = input.getClass();

		while(searchClass != null){
			Field[] fields = searchClass.getDeclaredFields();
			for(Field field : fields){
				if(field.getName().equals(fieldName)){
					field.setAccessible(true);
					//Check to see if we're trying to set a int, long, etc with a String
					if(field.getType().getName().equals("java.lang.Long")){
						if(value instanceof String){
							field.set(input, Long.getLong((String)value));
						} else {
							field.set(input, value);
						}
					} else if(field.getType().getName().equals("java.lang.Integer")){
						if(value instanceof String){
							field.set(input, Integer.getInteger((String)value));
						} else {
							field.set(input, value);
						}
					} else {
						field.set(input, value);
					}
				}
			}		
			searchClass = searchClass.getSuperclass();
		}
		return input;
	}
	
	private static boolean isPrimitiveObject(Object obj) {
		if (obj == null || obj instanceof Integer || obj instanceof Float
				|| obj instanceof Double || obj instanceof Character
				|| obj instanceof Long || obj instanceof Boolean
				|| obj instanceof Byte || obj instanceof Short
				|| obj instanceof String || obj instanceof Date) {
			return true;
		} else
			return false;
	}
	
	public static ArrayList<Field> getAllFields(Class clazz){
		ArrayList<Field> returnFields = new ArrayList<Field>();	
		while(clazz != null){
				Field[] fields  = clazz.getDeclaredFields();
				for(Field field : fields){
					returnFields.add(field);					
				}
				clazz = clazz.getSuperclass();
		}
		return returnFields;
	}

	public static Field getField(Class clazz, String fieldName) throws NoSuchFieldException{
		ArrayList<Field> allFields = getAllFields(clazz);
		for(Field field : allFields){
			if(field.getName().equals(fieldName)){
				return field;
			}
		}
		throw new NoSuchFieldException("The Field: " + fieldName +
				" was not found.");
	}
	
	 /**
     * Returns true if the given invocation is for a LexBig object.
     *
     * @param clazz the clazz
     *
     * @return true, if checks if is lex big class
     */
    public static boolean isLexBigClass(Class clazz) {
    	String className = clazz.getName().toLowerCase();
        return className.startsWith("org.lexgrid") || className.startsWith("org.lexevs");
    }
	
}
