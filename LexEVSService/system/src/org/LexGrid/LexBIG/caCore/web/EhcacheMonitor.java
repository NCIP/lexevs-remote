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
package org.LexGrid.LexBIG.caCore.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.CacheManager;

public class EhcacheMonitor extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter out = resp.getWriter();
		
		List<CacheManager> managers = CacheManager.ALL_CACHE_MANAGERS;
		for(CacheManager manager : managers){
			String[] names = manager.getCacheNames();
			for(String name : names){
				out.println("Cache: " + name);
				out.println("Cache Hits: " + manager.getEhcache(name).getStatistics().getCacheHits());
				out.println("Cache Misses: " + manager.getEhcache(name).getStatistics().getCacheMisses());
				out.println("Cache In Memory Hits: " + manager.getEhcache(name).getStatistics().getInMemoryHits());			
				out.println("Cache On Disk Hits: " + manager.getEhcache(name).getStatistics().getOnDiskHits());
				out.println();
			}
		}
		
		
	}

}
