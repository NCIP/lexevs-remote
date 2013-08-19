/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.dao.orm.translators;

import gov.nih.nci.cagrid.cqlquery.Association;
import gov.nih.nci.system.util.ClassCache;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;

public class LexEVSTranslatorsUtil {
	
	
	public static String getRoleName(String parentName, String assocName) throws TranslationException {
		return getRoleName(parentName, assocName, null);
	}

	public static String getRoleName(String parentClassName, String assocName, String sourceRoleName) throws TranslationException {
		if(sourceRoleName != null){
			return sourceRoleName;
		}	
		Class c = null;
		try {
			c = Class.forName(parentClassName);
		} catch (ClassNotFoundException e) {
			return null;
		}
		while(c != null){
			Field[] field = c.getDeclaredFields();
			for (int i = 0; i < field.length; i++) {
				Class fieldType = field[i].getType();

				String fieldTypeName = null;

				//if this is a Collection, get what it is a Collection of
				if(isCollectionClass(fieldType)){
					Type type = field[i].getGenericType();

					//if its a Parameterized List, see if we can dig out the type
					if (type != null && type instanceof ParameterizedType) {
						ParameterizedType pt = (ParameterizedType) type;	          
						if(pt.getActualTypeArguments().length > 0){
							Type collectionType = pt.getActualTypeArguments()[0];
							if (collectionType instanceof Class) {
								fieldTypeName = ((Class) collectionType).getName();
							}        	
						} 
					}			
				} else {
					fieldTypeName = field[i].getType().getName();
				}
				if(fieldTypeName.equals(assocName)){
					return field[i].getName();
				}
			}
			c = c.getSuperclass();
		}

		//can't find it, throw TranslationException
		throw new TranslationException("No association exits between: " +
				parentClassName + " and: " + assocName);
	}	

	public static boolean isCollectionClass(Class<?> clazz) {
		return clazz == java.util.Collection.class
				|| clazz == java.util.List.class
				|| clazz == java.util.ArrayList.class
				|| clazz == java.util.Set.class
				|| clazz == java.util.Map.class
				|| clazz == java.util.SortedSet.class // extension to the specs
				|| clazz == java.util.SortedMap.class; // extension to the specs
	}
	
	public static boolean isPrimitiveClass(Class<?> clazz) {
		return clazz.getName().startsWith("java.lang") || clazz.getName().equals("java.util.Date");
	}	
}
