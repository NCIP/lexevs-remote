<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ page import="org.acegisecurity.ui.AbstractProcessingFilter"%>
<%@ page
	import="org.acegisecurity.ui.webapp.AuthenticationProcessingFilter"%>
<%@ page import="org.acegisecurity.AuthenticationException"%>
<%@ page import="org.acegisecurity.context.SecurityContextHolder"%>
<%@ page import="org.acegisecurity.userdetails.UserDetails"%>
<%@ page import="gov.nih.nci.system.web.util.JSPUtils"%>
<%
			String lastUserKey = (String) session
			.getAttribute(AuthenticationProcessingFilter.ACEGI_SECURITY_LAST_USERNAME_KEY);
	if (lastUserKey == null || lastUserKey.equalsIgnoreCase("null")) {
		lastUserKey = "";
	}
	//out.println("lastUserKey: " + lastUserKey);

	String loginErrorStr = request.getParameter("login_error");
	boolean isLoginError = false;
	if (loginErrorStr != null && loginErrorStr.length() > 0) {
		isLoginError = true;
	}
	//out.println("isLoginError: " + isLoginError);
	JSPUtils jspUtils= JSPUtils.getJSPUtils(config.getServletContext());
	boolean isSecurityEnabled = jspUtils.isSecurityEnabled();

	boolean isAuthenticated = false;
	String userName = "";
	if (isSecurityEnabled){
		Object obj = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
	
		//out.println("obj: " + obj);
		if (obj instanceof UserDetails) {
			userName = ((UserDetails) obj).getUsername();
		} else {
			userName = obj.toString();
		}
	
		if (userName != null
				&& !(userName.equalsIgnoreCase("anonymousUser"))) {
			isAuthenticated = true;
		}
	}
	//out.println("userName: " + userName);	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
	<head>
		<title><s:text name="home.title" />
		</title>
		<link rel="stylesheet" type="text/css" href="styleSheet.css" />
		<script src="script.js" type="text/javascript"></script>
	</head>
	<body>
		<table summary="" cellpadding="0" cellspacing="0" border="0"
			width="100%" height="100%">

			<%@ include file="include/header.inc"%>

			<tr>
				<td height="100%" align="center" valign="top">
					<table summary="" cellpadding="0" cellspacing="0" border="0"
						height="100%" width="771">

						<!-- banner begins -->
						<tr>
							<td class="bannerHome">
								<img src="images/evsBannerHome.gif" width="200" height="85">
							</td>
						</tr>
						<tr>
							<td valign="top">
								<table summary="" cellpadding="0" cellspacing="0" border="0"
									height="100%" width="100%">
									<tr>
										<td height="20" class="mainMenu">

											<%@ include file="include/mainMenu.inc"%>

										</td>
									</tr>

									<!--_____ main content begins _____-->
									<tr>
										<td valign="top">
											<!-- target of anchor to skip menus -->
											<a name="content" />
												<table summary="" cellpadding="0" cellspacing="0" border="0"
													class="contentPage" width="100%" height="100%">

													<!-- banner begins -->

													<tr>
														<td height="100%">

															<!-- target of anchor to skip menus -->
															<a name="content" />

																<table summary="" cellpadding="0" cellspacing="0"
																	border="0" height="100%">
																	<tr>
																		<td align="left" width="75%">

																			<!-- welcome begins -->
																			<table summary="" cellpadding="0" cellspacing="0"
																				border="0" height="100%">
												
																				<tr>
																					
																					<td class="welcomeContent" valign="top">
																					
																					<h3>WELCOME TO LexEVS</h3>
																					<br>
																					
																						<br>
																						<br>
																						LexEVS is a collection of programmable interfaces that provide users with the 
																						ability to access controlled terminologies supplied by the NCI Enterprise 
																						Vocabulary Services (EVS) Project. The controlled terminologies hosted by 
																						the NCI EVS Project are published via the Open-Source LexEVS Terminology Server.  
																						<br>
																						<br>
																						
																						The LexEVS 6.0 Release includes the following components:


																						<br>
																						<br>
																						
																						<ul>
																							<li>
																								<b>LexEVS Java API</b> – A Java interface which provide the entry points for programmatic access to all system features and data. 
																							</li>
																							<li>
																								<b>LexEVS Distributed API</b> - The Distributed LexEVS Portion of LexEVS API. This interface is a framework for calling LexEVS API methods remotely, along with enforcing security measures.
																							</li>
																							<li>
																								<b>LexEVS caCORE SDK Services</b> - includes:
																									<ul>
																										<li>REST Interface
																										</li>
																										<li>SOAP Interface
																										</li>
																										<li>RMI Interface - provides:
																										</li>
																											<ul>
																												<li>Query-by-Example (QBE)
																											</li>
																											<li>HQL Interface
																											</li>
																											<li>Hibernate Detached Criteria
																											</li>
																											<li>SDK CQL
																											</li>
																											<li>caGrid CQL
																											</li>
																										</ul>
																									</ul>
																							</li>
																							<li>
																								<b>LexEVS Grid Service</b> -  An interface which uses the caGRID 
																								infrastructure to provide access to LexEVS content. 
																							</li>
																						</ul>
																						
																						References: 
																						<br>
																						<br>
																						<ul>
																							<li>
																								<a href="https://gforge.nci.nih.gov/projects/lexevs/">LexEVS API GForge site</a> - Contains news, 
																								information, documents, defects, feedback, and reports
																							</li>
																							<li>
																								<a href="http://ncicb.nci.nih.gov/download/cacoreevsapilicenseagreement.jsp">LexEVS API Download site</a> - Contains documents, information, and downloads for LexEVS. 
																							</li>
																							<li>
																								<a href="https://cabig-kc.nci.nih.gov/Vocab/KC/index.php/LexBig_and_LexEVS">caBIG Vocabulary Knowledge Center</a> - Contains LexEVS information provided by Vocabulary Knowledge Center
																							</li>
																							<li>
																								<a href="https://gforge.nci.nih.gov/docman/view.php/491/23186/lexevsapi60_notes.html">LexEVS 6.0 Release Notes</a> - Contains the release history information, highlights New 
																								Features and Updates, Bug fixes since the last release, identifies Known Issues, and 
																								provides information on documentation and other helpful reference links. 
																							</li>
																							<li>
																								<a href="http://cagrid-portal.nci.nih.gov/">caGRID Portal</a> – Link to the caGrid Portal/Browser 
																							</li>
																							<li>
																								<a href="http://lexevsapi-data60.nci.nih.gov/wsrf/services/cagrid/LexEVSDataService">LexEVS 6.0 Data Grid Service URL</a> - URL of the LexEVS Data Grid Services 
																							</li>
																							<li>
																								<a href="http://lexevsapi-analytical60.nci.nih.gov/wsrf/services/cagrid/LexEVSGridService">LexEVS 6.0 Analytical Grid Service URL</a> - URL of the LexEVS Analytical Grid Services 
																							</li>
																						</ul>
																						
																						
																						<br>
																						<b>NOTE</b>: This page allows users to conduct <i><b>simple</i></b> queries against the underlying API. Advanced 
																						level searching is NOT supported here but, is available by accessing the EVS API programmatically or by 
																						using the <a href="http://nciterms.nci.nih.gov/ncitbrowser/start.jsf">NCI Term Browser</a>.

																						
																						

																						
																						


																						
																					</td>
																				</tr>
																			</table>
																			<!-- welcome ends -->

																		</td>
																		
																		<td align="right" valign="top" width="25%">
																		
																			<!-- sidebar begins -->
																			<table summary="" cellpadding="0" cellspacing="0"
																				border="0" height="100%">

																				<!-- login/continue form begins -->
																				<tr>
																					<td valign="top">


																						<%
																						if (isSecurityEnabled && !isAuthenticated) {
																						%>

																						<table summary="" cellpadding="2" cellspacing="0"
																							border="0" width="100%" class="sidebarSection">
																							<tr>
																								<td class="sidebarTitle" height="20">
																									<s:text name="home.login" />
																								</td>
																							</tr>
																							<tr>
																								<td class="sidebarContent">
																									<s:form method="post"
																										action="j_acegi_security_check"
																										name="loginForm" theme="css_xhtml">
																										<table cellpadding="2" cellspacing="0"
																											border="0">
																											<%
																											if (isLoginError) {
																											%>
																											<tr>
																												<td class="sidebarLogin" align="left"
																													colspan="2">
																													<font color="red"> Your login
																														attempt was not successful; please try
																														again.<BR> <BR> Reason: <%=((AuthenticationException) session
												.getAttribute(AbstractProcessingFilter.ACEGI_SECURITY_LAST_EXCEPTION_KEY))
												.getMessage()%> <BR> <BR> </font>
																												</td>
																											</tr>
																											<%
																											}
																											%>

																											<tr>
																												<td class="sidebarLogin" align="left">
																													<s:text name="home.loginID" />
																												</td>
																												<td class="formFieldLogin">
																													<s:textfield name="j_username"
																														value="%{lastUserKey}"
																														cssClass="formField" size="14" />
																												</td>
																											</tr>
																											<tr>
																												<td class="sidebarLogin" align="left">
																													<s:text name="home.password" />
																												</td>
																												<td class="formFieldLogin">
																													<s:password name="j_password"
																														cssClass="formField" size="14" />
																												</td>
																											</tr>
																											<tr>
																												<td>
																													&nbsp;
																												</td>
																												<td>
																													<s:submit cssClass="actionButton"
																														type="submit" value="Login" />
																												</td>
																											</tr>
																										</table>
																									</s:form>
																								</td>
																							</tr>
																						</table>
																						<%
																						} else {
																						%>
																																									
																						<table summary="" cellpadding="2" cellspacing="0"
																																								
																							border="0" width="100%" class="sidebarSection">
																							<tr>
																								<td class="sidebarTitle" height="20">
																									SELECT CRITERIA
																								</td>
																							</tr>
																							<tr>
																								<td class="sidebarContent" align="center">
																									<s:form method="post"
																										action="ShowDynamicTree.action"
																										name="continueForm" theme="simple">
																										<s:submit cssClass="actionButton"
																											value="Continue" theme="simple" />
																									</s:form>
																								</td>
																							</tr>
																						</table>
																						<%
																						}
																						%>
																					</td>
																				</tr>
																				<!-- login ends -->

																				<!-- what's new begins -->
																				<tr>
																					<td valign="top">
																						<table summary="" cellpadding="0" cellspacing="0"
																							border="0" width="100%" class="sidebarSection">
																							
																							<!--
																							
																							<tr>
																								<td class="sidebarTitle" height="20">
																									WHAT'S NEW
																								</td>
																							</tr>
																							<tr>
																								<td class="sidebarContent">
																								   <br>
																								   <br>
																								</td>
																							</tr>
																							
																							<tr>
																								<td class="sidebarContent">
																									<ul>
																										<li>
																											New User Interface
																										</li>
																										<li>
																											New hierarchical package/class browsing
																										</li>
																										<li>
																											New single-session authentication
																										</li>
																									</ul>
																								</td>
																							</tr>
																							-->
																						</table>
																					</td>
																				</tr>
																				<!-- what's new ends -->

																				<!-- did you know? begins -->
																				<tr>
																					<td valign="top">
																						<table summary="" cellpadding="0" cellspacing="0"
																							border="0" width="100%" height="100%"
																							class="sidebarSection">
																							
																							<!--
																							
																							<tr>
																								<td class="sidebarTitle" height="20">
																									DID YOU KNOW?
																								</td>
																							</tr>
																							<tr>
																								<td class="sidebarContent" valign="top">
																									&nbsp;
																								</td>
																							</tr>
																							
																							-->
																						</table>
																					</td>
																				</tr>
																				<!-- did you know? ends -->

																				<!-- spacer cell begins (keep for dynamic expanding) -->
																				<tr>
																					<td valign="top" height="100%">
																						<table summary="" cellpadding="0" cellspacing="0"
																							border="0" width="100%" height="100%"
																							class="sidebarSection">
																							
																																														
																							<tr>
																								<td class="sidebarContent" valign="top">
																									&nbsp;
																								</td>
																							</tr>
																							
																							
																							
																						</table>
																					</td>
																				</tr>
																				<!-- spacer cell ends -->

																			</table>
																			<!-- sidebar ends -->

																		</td>
																	</tr>
																</table>
														</td>
													</tr>
												</table>
										</td>
									</tr>
									<!--_____ main content ends _____-->

									<tr>
										<td height="20" class="footerMenu">

											<!-- application ftr begins -->
											<%@ include file="include/applicationFooter.inc"%>
											<!-- application ftr ends -->

										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>

					<%@ include file="include/footer.inc"%>

				</td>
			</tr>
		</table>
	</body>
</html>
