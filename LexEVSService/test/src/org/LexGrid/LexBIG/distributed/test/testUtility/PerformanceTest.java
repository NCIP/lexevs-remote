/*
 * Copyright: (c) 2004-2006 Mayo Foundation for Medical Education and
 * Research (MFMER).  All rights reserved.  MAYO, MAYO CLINIC, and the
 * triple-shield Mayo logo are trademarks and service marks of MFMER.
 *
 * Except as contained in the copyright notice above, the trade names, 
 * trademarks, service marks, or product names of the copyright holder shall
 * not be used in advertising, promotion or otherwise in connection with
 * this Software without prior written authorization of the copyright holder.
 * 
 * Licensed under the Eclipse Public License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at 
 * 
 * 		http://www.eclipse.org/legal/epl-v10.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.LexGrid.LexBIG.distributed.test.testUtility;

import java.util.Arrays;

import org.LexGrid.LexBIG.DataModel.Collections.AssociationList;
import org.LexGrid.LexBIG.DataModel.Collections.ResolvedConceptReferenceList;
import org.LexGrid.LexBIG.DataModel.Core.Association;
import org.LexGrid.LexBIG.DataModel.Core.ConceptReference;
import org.LexGrid.LexBIG.DataModel.Core.ResolvedConceptReference;
import org.LexGrid.LexBIG.Impl.LexBIGServiceImpl;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeGraph;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet;
import org.LexGrid.LexBIG.LexBIGService.LexBIGService;
import org.LexGrid.LexBIG.LexBIGService.CodedNodeSet.SearchDesignationOption;
import org.LexGrid.LexBIG.Utility.Constructors;
import org.LexGrid.LexBIG.Utility.Iterators.ResolvedConceptReferencesIterator;
import org.LexGrid.LexBIG.testUtil.LexEVSServiceHolder;
import org.springframework.util.Assert;


public class PerformanceTest {

	private LexBIGService lbs;
	
	private static String LOCAL_ARG = "local";
	
	private static int RUN_TIMES = 4;
	
	private static String META_URN = "urn:oid:2.16.840.1.113883.3.26.1.2";
	private static String LOINC_URN = "urn:oid:2.16.840.1.113883.3.26.1.2";
	private static String NCITHES_URN = "urn:oid:2.16.840.1.113883.3.26.1.2";
	private static String MEDDRA_URN = "urn:oid:2.16.840.1.113883.3.26.1.2";
	private static String SNOMED_URN = "urn:oid:2.16.840.1.113883.3.26.1.2";
	private static String MAPPING_URN = "urn:oid:NCIt_to_ICD9CM_Mapping";
	
	private static String[] URIS = new String[] {META_URN, LOINC_URN, 
		NCITHES_URN, MEDDRA_URN, SNOMED_URN, MAPPING_URN};

	private static String[] searchTerms = new String[] {"heart attack", "gene abnormality", "abnormal", 
		"breast cancer", "disease finding", "big toe", "of the ear"};
	
	private long totalTime = 0;

	private static String[] matchAlgorithms = new String[]{"LuceneQuery", "exactMatch", 
		"startsWith", "contains", "literal"
	};
	
	private PerformanceTest(LexBIGService lbs){
		this.lbs = lbs;
	}

	public static void main(String[] args) throws Exception {
		LexBIGService lbs;
		if(args != null && args.length == 1 && args[0].equals(LOCAL_ARG)){
			lbs = LexBIGServiceImpl.defaultInstance();
		} else {
			lbs = LexEVSServiceHolder.instance().getLexEVSAppService();
		}
		new PerformanceTest(lbs).run();		
	}
	
	private void run() throws Exception {
		printMemoryStatistics();
		//test.warmUp();
					
		getSingleCode(META_URN, "C1281570");
		getSingleCode(META_URN, "C0006080");
		
		for(String uri : URIS){
			for(String algorithm : matchAlgorithms){
				for(String searchTerm : searchTerms) {
					searchWithAlgorithm(uri, algorithm, searchTerm);
				}
			}
		}

		for(String searchTerm : searchTerms){
			for(String uri :URIS){
				CodedNodeSet cnsTest = lbs.getNodeSet(uri, null, null);
				cnsTest = cnsTest.restrictToMatchingDesignations(searchTerm, SearchDesignationOption.ALL, "exactMatch", null);

				ResolvedConceptReferencesIterator itr = cnsTest.resolve(null, null, null, null, false);

				while(itr.hasNext()){
					for(
							ResolvedConceptReference ref :
								itr.next(100).getResolvedConceptReference()){

						String cui = ref.getCode();
						getParents(uri, cui, 1);
					}
				}
				
				itr.release();
			}
		}
		
		for(String searchTerm : searchTerms){
			for(String uri :URIS){
				CodedNodeSet cnsTest = lbs.getNodeSet(uri, null, null);
				cnsTest = cnsTest.restrictToMatchingDesignations(searchTerm, SearchDesignationOption.ALL, "exactMatch", null);

				ResolvedConceptReferencesIterator itr = cnsTest.resolve(null, null, null, null, false);

				while(itr.hasNext()){
					for(
							ResolvedConceptReference ref :
								itr.next(100).getResolvedConceptReference()){

						String cui = ref.getCode();
						getChildren(uri, cui, 1);
					}
				}
				
				itr.release();
			}
		}

		for(String algorithm : matchAlgorithms){
			for(String searchTerm : searchTerms) {
				searchMultipleOntologies(searchTerm, algorithm);
			}
		}

		
		for(String uri : URIS){
			for(String searchTerm : searchTerms) {
				advanceIterator(uri, searchTerm, false, 50);
			}
		}

		for(String uri : URIS){
			for(String searchTerm : searchTerms) {
				advanceIterator(uri, searchTerm, true, 50);
			}
		}
	
		System.out.println("==================================");
		System.out.println("Total Time: " + totalTime + " (ms)");
		System.out.println("==================================");
	}
	
	private void warmUp() throws Exception {
		System.out.println("Sleeping 20 seconds to allow LexEVS Warmup");
		Thread.sleep(20000);
	}
	
	private void getSingleCode(final String uri, final String code) {
		this.timeMethod(new TimedCallback() {

			public String getName() {
				return "Get a single Code (" + code + ") from " + uri;
			}

			public int run() throws Exception {
				CodedNodeSet cns = lbs.getNodeSet(uri, null, null);
				cns = cns.restrictToCodes(Constructors.createConceptReferenceList(code));
				ResolvedConceptReferencesIterator itr = cns.resolve(null, null, null, null, false);
				Assert.state(code.equals(itr.next().getCode()));
				itr.release();
				return 1;
			}
		});
	}
	
	private void searchMultipleOntologies(final String searchString, final String algorithm) {
		this.timeMethod(new TimedCallback() {

			public String getName() {
				return "Search Multiple Ontologies (Search String: " + searchString + ", Algorithm: " + algorithm + ")";
			}

			public int run() throws Exception {
				CodedNodeSet cns = null;
				for(String uri : URIS){
					if(cns == null){
						cns = lbs.getNodeSet(uri, null, null);
					} else {
						cns = cns.union(lbs.getNodeSet(uri, null, null));
					}
				}
				cns = cns.restrictToMatchingDesignations(searchString, SearchDesignationOption.ALL, algorithm, null);
				ResolvedConceptReferencesIterator itr = cns.resolve(null, null, null);
				
				int numRemaining = itr.numberRemaining();
				
				itr.release();
				return numRemaining;
			}
		});
	}
	
	private void advanceIterator(final String uri, final String searchString, final boolean resolve, final int max) {
		this.timeMethod(new TimedCallback() {

			public String getName() {
				return "Advancing an Iterator " + max + (resolve ? " Resolved " : " Unresolved ") + "results with search (" + searchString + ") from " + uri;
			}

			public int run() throws Exception {
				CodedNodeSet cns = lbs.getNodeSet(uri, null, null);
				cns = cns.restrictToMatchingDesignations(searchString, SearchDesignationOption.ALL, "LuceneQuery", null);
				ResolvedConceptReferencesIterator itr = cns.resolve(null, null, null, null, resolve);
				
				int counter = 0;
				while(itr.hasNext() && counter < max) {
					ResolvedConceptReferenceList list = itr.next(max);
					counter += list.getResolvedConceptReferenceCount();
				}
				
				itr.release();
				return counter;
			}
		});
	}

	private void searchWithAlgorithm(final String uri, final String algorithm, final String searchString) {
		this.timeMethod(new TimedCallback() {

			public String getName() {
				return "Testing '" + algorithm + "' (" + searchString + ") from " + uri;
			}

			public int run() throws Exception {
				CodedNodeSet cns = lbs.getNodeSet(uri, null, null);
				cns = cns.restrictToMatchingDesignations(searchString, SearchDesignationOption.ALL, "contains", null);
				ResolvedConceptReferencesIterator itr = cns.resolve(null, null, null, null, false);
				
				int numRemaining = itr.numberRemaining();
				
				itr.release();
				return numRemaining;
			}
		});
	}
	
	private void getChildren(final String uri, final String code, final int levels) {
		this.timeMethod(new TimedCallback() {

			public String getName() {
				return "Getting Children of'" + code + "' (" + levels + " Levels) from " + uri;
			}

			public int run() throws Exception {
				CodedNodeGraph cng = lbs.getNodeGraph(uri, null, null);
				
				ConceptReference ref = new ConceptReference();
				ref.setCode(code);
				
				ResolvedConceptReferenceList list = 
					cng.resolveAsList(ref, true, false, 0, levels, null, null, null, -1);
				
				return traverseResolvedConceptReferenceList(list);
			}
		});
	}
	
	private void getParents(final String uri, final String code, final int levels) {
		this.timeMethod(new TimedCallback() {

			public String getName() {
				return "Getting Parents of'" + code + "' (" + levels + " Levels) from " + uri;
			}

			public int run() throws Exception {
				CodedNodeGraph cng = lbs.getNodeGraph(uri, null, null);
				
				ConceptReference ref = new ConceptReference();
				ref.setCode(code);
				
				ResolvedConceptReferenceList list = 
					cng.resolveAsList(ref, false, true, 0, levels, null, null, null, -1);
				
				return traverseResolvedConceptReferenceList(list);
			}
		});
	}

	private int traverseResolvedConceptReferenceList(ResolvedConceptReferenceList list){
		int total = 0;
		for(ResolvedConceptReference node : list.getResolvedConceptReference()){
			total++;
			
			if(node.getSourceOf() != null){
				total += this.traverseAssociationList(node.getSourceOf());
			}
			if(node.getTargetOf() != null){
				total += this.traverseAssociationList(node.getTargetOf());
			}
		}
		return total;
	}
	
	private int traverseAssociationList(AssociationList list){
		int total = 0;
		for(Association assoc : list.getAssociation()){
			ResolvedConceptReferenceList refList = new ResolvedConceptReferenceList();
			for(ResolvedConceptReference node : assoc.getAssociatedConcepts().getAssociatedConcept()){
				refList.addResolvedConceptReference(node);
			}
			total += this.traverseResolvedConceptReferenceList(refList);
		}
		
		return total;
	}
	
	private void timeMethod(TimedCallback callback) {
		System.out.println();
		System.out.println("Running: " + callback.getName());
		
		long[] times = new long[RUN_TIMES];
		
		for(int i=0;i<RUN_TIMES;i++) {
			long startTime = System.currentTimeMillis();
			
			int resultsReturned = 0;
			
			try {
				resultsReturned = callback.run();
			} catch (Exception e) {
				System.out.println("Exception Thrown:");
				e.printStackTrace();
			}
			long runTime = System.currentTimeMillis() - startTime;
			System.out.println(" - Run " + i + ":" + runTime + "ms (Results returnted: " + resultsReturned + ")");
			times[i] = runTime;
		}
		
		times = this.removeShortestAndLongestTime(times);
		System.out.println("Summary For: " + callback.getName());
		System.out.println(" - Total Time:" + addArray(times) + "ms");
		System.out.println(" - Average Time:" + averageArray(times) + "ms");
		System.out.println();
		
		this.totalTime += addArray(times);
	}
	
	private long addArray(long[] times) {
		long totalTime = 0;
		for(long time : times) {
			totalTime += time;
		}
		return totalTime;
	}
	
	private long averageArray(long[] times) {
		long totalTime = addArray(times);
		return totalTime / times.length;
	}
	
	private long[] removeShortestAndLongestTime(long[] times) {
		if(RUN_TIMES <= 3) {
			return times;
		}
		
		Arrays.sort(times);
		
		long[] returnTimes = new long[times.length-2];
		for(int i=1;i<times.length-1;i++) {
			returnTimes[i-1] = times[i];
		}
		
		return returnTimes;
	}
	
	private interface TimedCallback {
		
		public int run() throws Exception;
		
		public String getName();
	}
	
	public void printMemoryStatistics() {

		int mb = 1024*1024;

		//Getting the runtime reference from system
		Runtime runtime = Runtime.getRuntime();

		System.out.println("##### Heap utilization statistics [MB] #####");

		//Print used memory
		System.out.println("Used Memory:"
			+ (runtime.totalMemory() - runtime.freeMemory()) / mb);

		//Print free memory
		System.out.println("Free Memory:"
			+ runtime.freeMemory() / mb);

		//Print total available memory
		System.out.println("Total Memory:" + runtime.totalMemory() / mb);

		//Print Maximum available memory
		System.out.println("Max Memory:" + runtime.maxMemory() / mb);
	}
}
