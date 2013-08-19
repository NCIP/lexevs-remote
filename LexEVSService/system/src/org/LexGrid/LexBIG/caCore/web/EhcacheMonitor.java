/*
* Copyright: (c) Mayo Foundation for Medical Education and
* Research (MFMER). All rights reserved. MAYO, MAYO CLINIC, and the
* triple-shield Mayo logo are trademarks and service marks of MFMER.
*
* Distributed under the OSI-approved BSD 3-Clause License.
* See http://ncip.github.com/lexevs-remote/LICENSE.txt for details.
*/
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
