/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
package org.LexGrid.LexBIG.caCore.applicationservice.resource;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;

import org.LexGrid.LexBIG.caCore.utils.LexEVSCaCoreUtils;
import org.LexGrid.annotations.LgClientSideSafe;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

public class RemoteResourceManager {

	private Map<String,Object> resourceMap = new TimedMap<String,Object>();

	private boolean enableRemoteShell;
	
	public Object getResource(String uuid){
		Object resource = this.resourceMap.get(uuid);
		return resource;
	}
	
	public Object replaceWithShell(Object result) {
		if(enableRemoteShell
				&&
				!(result instanceof RemoteShell)
				&&
				LexEVSCaCoreUtils.isLexBigClass(result.getClass())
				&&
				!result.getClass().isAnnotationPresent(LgClientSideSafe.class)
				&&
				!doMethodsContainClientSideSafeAnnotation(result.getClass())
				){
			Class<?>[] classes = ClassUtils.getAllInterfaces(result);
			if(classes.length > 0){
		        String resourceUuid = UUID.randomUUID().toString();
		        
		        resourceMap.put(resourceUuid, result);
		        RemoteShell shell = new RemoteShell(classes, result.getClass(), resourceUuid);
		        
		        result = shell;
			}
		}
		return result;
	}

	public Object unWrapShell(Object obj) {
		if(obj instanceof RemoteShell){
			RemoteShell shell = (RemoteShell)obj;
			Object resource = this.getResource(shell.getResourceUuid());

			if(resource == null){
				throw new RuntimeException("Remote Resource has timed out on the Server -- please re-execute your query.");
			}
			
			return resource;
		}
		return obj;
	}
	
	private boolean doMethodsContainClientSideSafeAnnotation(Class<?> clazz){
		for(Method method : clazz.getMethods()){
			if(method.isAnnotationPresent(LgClientSideSafe.class)){
				LgClientSideSafe css = method.getAnnotation(LgClientSideSafe.class);
				if(css.force()){
					return true;
				}
			}
		}
		return false;
	}
	
	public void setResourceMap(Map<String,Object> resourceMap) {
		this.resourceMap = resourceMap;
	}

	public Map<String,Object> getResourceMap() {
		return resourceMap;
	}

	public void setEnableRemoteShell(boolean enableRemoteShell) {
		this.enableRemoteShell = enableRemoteShell;
	}

	public boolean isEnableRemoteShell() {
		return enableRemoteShell;
	}

	
}
