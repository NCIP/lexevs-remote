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
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import gov.nih.nci.cagrid.cqlquery.LogicalOperator;
import gov.nih.nci.cagrid.cqlquery.Predicate;
import gov.nih.nci.system.query.cql.CQLAssociation;
import gov.nih.nci.system.query.cql.CQLAttribute;
import gov.nih.nci.system.query.cql.CQLGroup;
import gov.nih.nci.system.query.cql.CQLLogicalOperator;
import gov.nih.nci.system.query.cql.CQLObject;
import gov.nih.nci.system.query.cql.CQLPredicate;
import gov.nih.nci.system.query.cql.CQLQuery;

public class SDKCQLToDetachedCriteria {

	public DetachedCriteria translate(CQLQuery query) throws TranslationException {
		CQLObject target = query.getTarget();
		String targetClassName = target.getName();
		
		DetachedCriteria crit = DetachedCriteria.forEntityName(targetClassName);
		crit.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY); 
		
		//Conjunction all the properties
		Conjunction con = Restrictions.conjunction();
		
		//Process Attribute
		CQLAttribute attribute = target.getAttribute();
		if(attribute != null){
			con.add(processAttribute(attribute, crit.getAlias()));
		}
		
		//Process Association
		CQLAssociation association = target.getAssociation();
		if(association != null){
			con.add(processAssociation(association, crit.getAlias(), crit, targetClassName));
		}
		
		//Process Group
		CQLGroup group = target.getGroup();
		if(group != null){
			crit.add(processGroup(group, crit.getAlias(), crit, targetClassName));
		}
		
		crit.add(con);
				
		return crit;	
	}
	
	private Criterion processGroup(CQLGroup group, String alias, DetachedCriteria criteria, String parentClass) throws TranslationException {
		CQLLogicalOperator groupOperator = group.getLogicOperator();
		String operator = groupOperator.getValue();
		
		Junction op = null;
		if(operator.equals(CQLLogicalOperator.AND.getValue())){
			op = Restrictions.conjunction();
		} 
		if(operator.equals(CQLLogicalOperator.OR.getValue())){
			op = Restrictions.disjunction();
		} 
		
		//Process the Attributes
		Collection<CQLAttribute> atts = group.getAttributeCollection();
		if(atts != null){
			for(CQLAttribute at : atts){
				op.add(processAttribute(at, alias));
			}
		}
		
		//Process the Associations
		Collection<CQLAssociation> assocs = group.getAssociationCollection();
		if(assocs != null){
			for(CQLAssociation assoc : assocs){
				op.add(processAssociation(assoc, alias, criteria, parentClass));
			}	
		}
		
		//Process Nested Groups
		Collection<CQLGroup> groups = group.getGroupCollection();
		if(groups != null){
			for(CQLGroup nestedGroup : groups){
				op.add(processGroup(nestedGroup, alias, criteria, parentClass));
			}
		}
		return op;		
	}
	
	private Criterion processAttribute(CQLAttribute att, String parentAlias){
		String name = parentAlias+"."+att.getName();
		String value = att.getValue();
		Criterion restriction = null;
		
		CQLPredicate attPredicate = att.getPredicate();
		
		if(attPredicate.equals(CQLPredicate.EQUAL_TO)){
			restriction = Restrictions.eq(name, value);
		}		
		if(attPredicate.equals(CQLPredicate.LIKE)){
			restriction =  Restrictions.like(name, value);
		}		
		if(attPredicate.equals(CQLPredicate.GREATER_THAN)){
			restriction =  Restrictions.gt(name, value);
		}
		if(attPredicate.equals(CQLPredicate.GREATER_THAN_EQUAL_TO)){
			restriction =  Restrictions.ge(name, value);
		}
		if(attPredicate.equals(CQLPredicate.LESS_THAN)){
			restriction =  Restrictions.lt(name, value);
		}
		if(attPredicate.equals(CQLPredicate.LESS_THAN_EQUAL_TO)){
			restriction =  Restrictions.le(name, value);
		}
		if(attPredicate.equals(CQLPredicate.IS_NULL)){
			restriction =  Restrictions.isNull(name);
		}
		if(attPredicate.equals(CQLPredicate.IS_NOT_NULL)){
			restriction =  Restrictions.isNotNull(name);
		}
		if(attPredicate.equals(CQLPredicate.NOT_EQUAL_TO)){
			restriction =  Restrictions.ne(name, value);
		}		
		return restriction;	
	}
	
	private Criterion processAssociation(CQLAssociation assoc, String alias, DetachedCriteria criteria, String parentClass) throws TranslationException {
		String assocClassName = assoc.getName();
		String sourceRoleName = assoc.getSourceRoleName();
		
		String roleName = LexEVSTranslatorsUtil.getRoleName(parentClass, assocClassName, sourceRoleName);
			
		Conjunction con = Restrictions.conjunction();
		
		criteria.createAlias(alias+"."+roleName, roleName);
		
		//Process Attribute
		CQLAttribute at = assoc.getAttribute();
		if(at != null){
			con.add(processAttribute(at, roleName));
		}
		
		//Process Group
		CQLGroup group = assoc.getGroup();
		if(group != null){
			con.add(processGroup(group, roleName, criteria, assocClassName));
		}		
		
		//Process Nested Associations
		CQLAssociation nestedAssoc = assoc.getAssociation();
		if(nestedAssoc != null){
			con.add(processAssociation(nestedAssoc, roleName, criteria, assocClassName));
		}		
		return con;		
	}

	/*
	private Criterion processAssociations(Collection<CQLAssociation> assocs, String operator, String alias, DetachedCriteria criteria){	
		Junction junction = null;
		
		for(CQLAssociation assoc : assocs){	
			if(operator.equals(CQLLogicalOperator.AND.getValue())){
				junction = Restrictions.conjunction();
			} 
			if(operator.equals(CQLLogicalOperator.OR.getValue())){
				junction = Restrictions.disjunction();
			} 
			
			String assocName = assoc.getName();
			String sourceRoleName = assoc.getSourceRoleName();
			criteria.createAlias(alias+"."+sourceRoleName, sourceRoleName);			
			
			//Process the Attribute
			junction.add(processAttribute(assoc.getAttribute(), sourceRoleName));
			
			//Process the Group
			junction.add(processGroup(assoc.getGroup(), sourceRoleName, criteria));
			
			//Process the Nested Association
			junction.add(processAssociation(assoc, sourceRoleName, criteria));	
		}
		return junction;
	}
	*/
	
	

}
