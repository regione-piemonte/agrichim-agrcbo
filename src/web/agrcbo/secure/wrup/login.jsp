<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*, it.csi.cuneo.*" isThreadSafe="true" %>
<%@ page import="it.csi.iride2.policy.entity.Identita" %>
<%@ page import="it.csi.csi.porte.InfoPortaDelegata" %>
<%@ page import="it.csi.csi.util.xml.PDConfigReader" %>
<%@ page import="it.csi.csi.porte.proxy.PDProxy" %>
<%@ page import="it.csi.iride2.policy.interfaces.PolicyEnforcerBaseService" %>
<%@ page import="it.csi.iride2.policy.entity.Application"%>
<%@ page import="it.csi.iride2.iridefed.entity.Ruolo"%>

<%
	CuneoLogger.debug(this,"Sono in /secure/wrup/login.jsp");
  
  response.sendRedirect("../../jsp/controller/login.jsp");
%>
