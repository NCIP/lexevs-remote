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
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.transform.ResultTransformer;

import gov.nih.nci.cagrid.cqlquery.LogicalOperator;
import gov.nih.nci.cagrid.cqlquery.Association;
import gov.nih.nci.cagrid.cqlquery.Attribute;
import gov.nih.nci.cagrid.cqlquery.CQLQuery;
import gov.nih.nci.cagrid.cqlquery.Group;
import gov.nih.nci.cagrid.cqlquery.LogicalOperator;
import gov.nih.nci.cagrid.cqlquery.Object;
import gov.nih.nci.cagrid.cqlquery.Predicate;
import gov.nih.nci.cagrid.cqlquery.QueryModifier;

public class GridCQLToDetachedCriteria {
	
	private final String CASTOR_UNDERSCORE = "_";
	private boolean prefixCastorUnderscore = false;

	public DetachedCriteria translate(CQLQuery query) throws TranslationException {
		//Do a little housekeeping and make sure everything is in order
		if(query.getTarget() == null){
			throw new TranslationException("Target of the CQLQuery cannot be null, please specify a Target.");
		}
		
		Object target = query.getTarget();
		String targetClassName = target.getName();
		
		DetachedCriteria crit = DetachedCriteria.forEntityName(targetClassName);
	
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		//Conjunction all the properties
		Conjunction con = Restrictions.conjunction();
		
		//Process Attribute
		Attribute attribute = target.getAttribute();
		if(attribute != null){
			con.add(processAttribute(attribute, crit.getAlias()));
		}
		
		//Process Association
		Association association = target.getAssociation();
		if(association != null){
			con.add(processAssociation(association, crit.getAlias(), crit, targetClassName));
		}
		
		//Process Group
		Group group = target.getGroup();
		if(group != null){
			crit.add(processGroup(group, crit.getAlias(), crit, targetClassName));
		}
		
		crit.add(con);
		
		QueryModifier modifiers = query.getQueryModifier();
				
		crit = handleQueryOptions(crit, modifiers);
		
		return crit;	
	}
	
	private DetachedCriteria handleQueryOptions(DetachedCriteria criteria, QueryModifier modifiers){
		if(modifiers == null){
			return criteria;
		}

		ProjectionList projectionList = Projections.projectionList();
		
		String[] projectionProperties = modifiers.getAttributeNames();
		
		if(projectionProperties != null){
			for(String prop : projectionProperties){
				projectionList.add(Projections.property(prop));
			}
		}
		
		String distinctAttribute = modifiers.getDistinctAttribute();
		if(distinctAttribute != null){
			projectionList.add(Projections.distinct(Projections.property(distinctAttribute)));
		}
		
		boolean isCount = modifiers.isCountOnly();
		if(isCount){
			projectionList.add(Projections.rowCount());
		}
		
		//Only add the Projection List if it was populated with something.
		if(projectionList.getLength() > 0){
			criteria.setProjection(projectionList);	
		}
		return criteria;	
	}
	
	private Criterion processGroup(Group group, String alias, DetachedCriteria criteria, String parentClass) throws TranslationException {
		LogicalOperator groupOperator = group.getLogicRelation();
		String operator = groupOperator.getValue();
		
		Junction op = null;
		if(operator.equals(LogicalOperator.AND.getValue())){
			op = Restrictions.conjunction();
		} 
		if(operator.equals(LogicalOperator.OR.getValue())){
			op = Restrictions.disjunction();
		} 
		
		//Process the Attributes
		Attribute[] atts = group.getAttribute();
		if(atts != null){
			for(Attribute at : atts){
				op.add(processAttribute(at, alias));
			}
		}
		
		//Process the Associations
		Association[] assocs = group.getAssociation();
		if(assocs != null){
			for(Association assoc : assocs){
				op.add(processAssociation(assoc, alias, criteria, parentClass));
			}	
		}
		
		//Process Nested Groups
		Group[] groups = group.getGroup();
		if(groups != null){
			for(Group nestedGroup : groups){
				op.add(processGroup(nestedGroup, alias, criteria, parentClass));
			}
		}
		return op;		
	}
	
	private Criterion processAttribute(Attribute att, String parentAlias){
		String attName = null;
		
		if(prefixCastorUnderscore){
			attName = addCastorUnderscore(att.getName());
		} else {
			attName = att.getName();
		}
		
		String name = parentAlias+"."+attName;
		String value = att.getValue();
		Criterion restriction = null;
		
		Predicate attPredicate = att.getPredicate();
		
		if(attPredicate.equals(Predicate.EQUAL_TO)){
			restriction = Restrictions.eq(name, value);
		}		
		if(attPredicate.equals(Predicate.LIKE)){
			restriction =  Restrictions.like(name, value);
		}		
		if(attPredicate.equals(Predicate.GREATER_THAN)){
			restriction =  Restrictions.gt(name, value);
		}
		if(attPredicate.equals(Predicate.GREATER_THAN_EQUAL_TO)){
			restriction =  Restrictions.ge(name, value);
		}
		if(attPredicate.equals(Predicate.LESS_THAN)){
			restriction =  Restrictions.lt(name, value);
		}
		if(attPredicate.equals(Predicate.LESS_THAN_EQUAL_TO)){
			restriction =  Restrictions.le(name, value);
		}
		if(attPredicate.equals(Predicate.IS_NULL)){
			restriction =  Restrictions.isNull(name);
		}
		if(attPredicate.equals(Predicate.IS_NOT_NULL)){
			restriction =  Restrictions.isNotNull(name);
		}
		if(attPredicate.equals(Predicate.NOT_EQUAL_TO)){
			restriction =  Restrictions.ne(name, value);
		}		
		return restriction;	
	}
	
	private Criterion processAssociation(Association assoc, String alias, DetachedCriteria criteria, String parentClass) throws TranslationException {
		String assocClassName = assoc.getName();
		String sourceRoleName = assoc.getRoleName();
		
		String roleName = LexEVSTranslatorsUtil.getRoleName(parentClass, assocClassName, sourceRoleName);
			
		Conjunction con = Restrictions.conjunction();
		
		criteria.createAlias(alias+"."+roleName, roleName);
		
		//Process Attribute
		Attribute at = assoc.getAttribute();
		if(at != null){
			con.add(processAttribute(at, roleName));
		}
		
		//Process Group
		Group group = assoc.getGroup();
		if(group != null){
			con.add(processGroup(group, roleName, criteria, assocClassName));
		}		
		
		//Process Nested Associations
		Association nestedAssoc = assoc.getAssociation();
		if(nestedAssoc != null){
			con.add(processAssociation(nestedAssoc, roleName, criteria, assocClassName));
		}		
		return con;		
	}
	
	private String addCastorUnderscore(String value){
		if(!value.startsWith(CASTOR_UNDERSCORE)){
			return CASTOR_UNDERSCORE + value;
		} else {
			return value;
		}
	}

	public boolean isPrefixCastorUnderscore() {
		return prefixCastorUnderscore;
	}

	public void setPrefixCastorUnderscore(boolean prefixCastorUnderscore) {
		this.prefixCastorUnderscore = prefixCastorUnderscore;
	}
}
