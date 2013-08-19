/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.dao.orm.translators;

import gov.nih.nci.system.dao.orm.ORMDAOImpl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

public class NestedObjectToCriteria {
	private static Logger log = Logger.getLogger(NestedObjectToCriteria.class.getName());
	
	private NestedObjectToCriterion translator;
	
	public DetachedCriteria translate(Class searchClass, Object object) throws TranslationException {
		return translate(searchClass, object, null);
	}
	
	public DetachedCriteria translate(Class searchClass, Object object, String eagerFetchAssociation) throws TranslationException {
		List list = new ArrayList();
		list.add(object);
		return translate(searchClass, list, eagerFetchAssociation);
	}
	
	public DetachedCriteria translate(Class searchClass, List searchObjectList) throws TranslationException {
		return translate(searchClass, searchObjectList, null);
	}

	public DetachedCriteria translate(Class searchClass, List searchObjectList, String eagerFetchAssociation) throws TranslationException {
		DetachedCriteria crit = DetachedCriteria.forClass(searchClass);
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
		
		if(eagerFetchAssociation != null){
			crit.setFetchMode(eagerFetchAssociation, FetchMode.JOIN);
		}
		
		//Apply the List of Associated Objects (OR'ed)
		Disjunction disjunction = Restrictions.disjunction();
		for(Object searchObject : searchObjectList){
			Criterion criterion = translator.buildCriterionFromNestedObjects(searchObject, crit);
			disjunction.add(criterion);
		}
		crit.add(disjunction);
		
		return crit;
	}

	public NestedObjectToCriterion getTranslator() {
		return translator;
	}

	public void setTranslator(NestedObjectToCriterion translator) {
		this.translator = translator;
	}
}
