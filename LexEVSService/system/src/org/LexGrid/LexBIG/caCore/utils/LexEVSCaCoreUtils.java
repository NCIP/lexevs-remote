/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.utils;

import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.proxy.ProxyHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.LexGrid.LexBIG.Impl.pagedgraph.paging.callback.CycleDetectingCallback;
import org.LexGrid.LexBIG.caCore.client.proxy.LexEVSBeanProxy;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

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
        return className.startsWith("org.lexgrid") 
        	|| className.startsWith("org.lexevs");
    }
    
    public static interface DoInReflectionCallback {
    	
    	public boolean actionRequired(Object obj); 
        
        public <T> T doInReflection(T obj);  
    }
    
    
    public static Object createProxy(
    		Object objectToProxy, 
    		ApplicationService advice,
    		ProxyHelper proxyHelper){
    	 ProxyFactory pf = new ProxyFactory(objectToProxy);
         pf.setProxyTargetClass(true);
         pf.addAdvice(new LexEVSBeanProxy(advice, proxyHelper));
         pf.setExposeProxy(true);
         return pf.getProxy();
    }

    public static <T> T recurseReflect(final T obj, final DoInReflectionCallback callback) {
    	if(obj == null){
    		return null;
    	}
        ReflectionUtils.doWithFields(
                obj.getClass(), new FieldCallback(){

                    public void doWith(Field arg0)
                    throws IllegalArgumentException,
                    IllegalAccessException {

                        if(!ClassUtils.isPrimitiveOrWrapper(arg0.getType())
                        		&& !ClassUtils.isPrimitiveArray(arg0.getType())
                        		&& !ClassUtils.isPrimitiveWrapperArray(arg0.getType())
                        		&& !arg0.getType().isEnum()
                        		&& ( isLexBigClass(arg0.getType())
                        				||
                        			Collection.class.isAssignableFrom(arg0.getType())
                        				||
                        			Map.class.isAssignableFrom(arg0.getType())
                        				)
                        ){
                        	
                        	
                        			 
                            arg0.setAccessible(true);
                            Object recurse = arg0.get(obj);
                            
                          

                            if(recurse != null) {
                            	
                            	  if (CycleDetectingCallback.class.isAssignableFrom(recurse.getClass())){
                                  	System.out.println("ere");
                             	 	}

                                if (Collection.class.isAssignableFrom(recurse.getClass()))  {
                                    Collection collection = (Collection)recurse;
                                    for(Object o : collection) {
                                    	if(callback.actionRequired(o)){
                                    		collection.remove(o);
                                    		collection.add(recurseReflect(o, callback));
                                    	} else {
                                    		recurseReflect(o, callback);
                                    	}
                                    }
                                } else  if (Map.class.isAssignableFrom(recurse.getClass()))  {
                                	Map map = (Map)recurse;
                                    for(Object key : map.keySet()) {
                                    	Object value = map.get(key);
                                    	if(callback.actionRequired(key) ||
                                    			callback.actionRequired(value)){
                                    		map.remove(key);
                                    		map.put(recurseReflect(key, callback), recurseReflect(value, callback));
                                    	} else {
                                    		recurseReflect(key, callback);
                                    		recurseReflect(value, callback);
                                    	}
                                    }
                                } else {
                                	if(callback.actionRequired(recurse)){
                                		Object newObject = recurseReflect(recurse, callback);   
                                		arg0.set(obj, newObject);
                                	} else {
                                		recurseReflect(recurse, callback);
                                	}
                                }
                            }
                        }
                    }
                });
        
        return callback.doInReflection(obj);
    }
	
}
