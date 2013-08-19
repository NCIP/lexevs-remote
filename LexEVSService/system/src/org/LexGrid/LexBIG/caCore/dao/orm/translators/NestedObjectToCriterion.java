/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.dao.orm.translators;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

public class NestedObjectToCriterion {
	
	private static Logger log = Logger.getLogger(NestedObjectToCriteria.class.getName());
	
	public Criterion buildCriterionFromNestedObjects(Object object, DetachedCriteria crit) throws TranslationException {
		String alias = crit.getAlias();
		return this.buildCriterionFromNestedObjects(object, alias, crit);
	}
	
	public Criterion buildCriterionFromNestedObjects(Object object, String parentAlias, DetachedCriteria crit) throws TranslationException {
		Conjunction conjunction = Restrictions.conjunction();		
		
		Class c = object.getClass();

		while (c != null)
		{
			Field[] fields = c.getDeclaredFields();
			for (Field field : fields)
			{				
				field.setAccessible(true);
				Object fieldVal;
				try {
					fieldVal = field.get(object);
				} catch (Exception e) {
					log.error(e.getMessage());
					throw new TranslationException(this.getClass().getName(), e);
				}

				//Only process if the field value is not null
				if(fieldVal != null){
					Class fieldValClass = fieldVal.getClass();
					String fieldName = field.getName();
					if (LexEVSTranslatorsUtil.isCollectionClass(fieldValClass)){
						log.debug("Processing a Collection");
						
						//check if the List is empty
						List list = (List)fieldVal;
						if(list.size() > 0){
							log.debug("Collection associated with : "+fieldName+" is non-empty.");
							crit.createAlias(parentAlias+"."+fieldName, fieldName);
							
							//Iterate through the list
							Iterator itr = list.iterator();
							while(itr.hasNext()){
								conjunction.add(buildCriterionFromNestedObjects(itr.next(), fieldName, crit));
							}
						}
							
						} else if(!LexEVSTranslatorsUtil.isPrimitiveClass(fieldValClass)){
							log.debug("Processing a non-primitive association : "+fieldName+" "+fieldValClass);
							crit = crit.createAlias(parentAlias+"."+fieldName, fieldName);							
							conjunction.add(buildCriterionFromNestedObjects(fieldVal, fieldName, crit));
						} else {
							log.debug("Processing a primitive : "+fieldName+" value: "+fieldVal);
							
							//check for a wildcard
							if(fieldVal instanceof String){
								String value = (String)fieldVal;

								//if the String value is blank, don't add it as a restriction.
								//The LexEVS Model sometimes initializes Strings with a "" value.
								//We don't want to add these as restrictions -- treat them as null values.
								if(!value.equals("")){
									//check if it contains a wildcard
									if(value.contains("*")){
										value = value.replace("*", "%");
										conjunction.add(Restrictions.like(parentAlias+"."+fieldName, value));
									} else {
										conjunction.add(Restrictions.eq(parentAlias+"."+fieldName, fieldVal));
									}
								} else {
									log.debug("Skipping empty String value : "+fieldName+" value: "+fieldVal);
								}
							} else {
								conjunction.add(Restrictions.eq(parentAlias+"."+fieldName, fieldVal));
							}					
						}
					} 
				}
			c = c.getSuperclass();
			} 
		return conjunction;
		}
	
	private boolean isEmptyString(Object obj){
		if(obj instanceof String){
			String value = (String)obj;
			if(value.equals("")){
				return true;
			}
		}
		return false;
	}
}
