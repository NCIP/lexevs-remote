package org.LexGrid.LexBIG.caCore.applicationservice.resource;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;

import org.LexGrid.LexBIG.caCore.utils.LexEVSCaCoreUtils;
import org.LexGrid.annotations.LgClientSideSafe;
import org.springframework.util.ClassUtils;

public class RemoteResourceManager {

	private Map<String,Object> resourceMap = new TimedMap<String,Object>();

	private boolean enableRemoteShell;
	
	public Object getResource(String uuid){
		Object resource = this.resourceMap.get(uuid);
		this.resourceMap.remove(uuid);
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
				!doMethodsContainClientSideSafeAnnotation(result.getClass())){
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
	
	private boolean doMethodsContainClientSideSafeAnnotation(Class<?> clazz){
		for(Method method : clazz.getMethods()){
			if(method.isAnnotationPresent(LgClientSideSafe.class)){
				return true;
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
