/**
 * 
 */
package org.LexGrid.LexBIG.caCore.applicationservice.resource;

import java.io.Serializable;

public class RemoteShell implements Serializable {
	private static final long serialVersionUID = -7534638346041169930L;
	private Class<?>[] targetInterfaces;
	private Class<?> targetClass;
	private String resourceUuid;
	
	public RemoteShell(){
		super();
	}

	public RemoteShell(Class<?>[] targetInterfaces, Class<?> targetClass,
			String resourceUuid) {
		super();
		this.targetInterfaces = targetInterfaces;
		this.targetClass = targetClass;
		this.resourceUuid = resourceUuid;
	}

	public Class<?>[] getTargetInterfaces() {
		return targetInterfaces;
	}

	public void setTargetInterfaces(Class<?>[] targetInterfaces) {
		this.targetInterfaces = targetInterfaces;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(Class<?> targetClass) {
		this.targetClass = targetClass;
	}

	public String getResourceUuid() {
		return resourceUuid;
	}

	public void setResourceUuid(String resourceUuid) {
		this.resourceUuid = resourceUuid;
	}
}