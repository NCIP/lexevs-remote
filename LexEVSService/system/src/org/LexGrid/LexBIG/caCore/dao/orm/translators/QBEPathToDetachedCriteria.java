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
package org.LexGrid.LexBIG.caCore.dao.orm.translators;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.hibernate.Criteria;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

public class QBEPathToDetachedCriteria {
	
	private NestedObjectToCriterion translator;

	public DetachedCriteria translate(String path, Object searchObject) throws TranslationException {
		List searchObjectList = new ArrayList();
		searchObjectList.add(searchObject);
		return translate(path, searchObjectList);
	}
	
	public DetachedCriteria translate(String path, List searchObjectList) throws TranslationException {
		List<String> pathList = new ArrayList<String>();
		StringTokenizer tokens = new StringTokenizer(path, ",");
		while (tokens.hasMoreTokens()) {
			pathList.add(tokens.nextToken().trim());
		}
		String rootClass = pathList.remove(0);
		DetachedCriteria dCrit = DetachedCriteria.forEntityName(rootClass);
		dCrit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
		
		
		//Set the Association Path
		String alias = dCrit.getAlias();
		for(String pathElement : pathList){
			String roleName = LexEVSTranslatorsUtil.getRoleName(rootClass, pathElement);
			dCrit.createAlias(alias+"."+roleName, roleName);
			
			rootClass = pathElement;
			alias = roleName;
		}
		
		//Apply the List of Associated Objects (OR'ed)
		Disjunction disjunction = Restrictions.disjunction();
		for(Object searchObject : searchObjectList){
			Criterion crit = translator.buildCriterionFromNestedObjects(searchObject, alias, dCrit);
			disjunction.add(crit);
		}
		dCrit.add(disjunction);
		
		return dCrit;
	}

	public NestedObjectToCriterion getTranslator() {
		return translator;
	}

	public void setTranslator(NestedObjectToCriterion translator) {
		this.translator = translator;
	}
	
	
	
}
