<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*, it.csi.cuneo.*" isThreadSafe="true" %>
<%@ page import="it.csi.iride2.policy.entity.Identita" %>
<%@ page import="it.csi.csi.porte.InfoPortaDelegata" %>
<%@ page import="it.csi.csi.util.xml.PDConfigReader" %>
<%@ page import="it.csi.csi.porte.proxy.PDProxy" %>
<%@ page import="it.csi.iride2.policy.interfaces.PolicyEnforcerBaseService" %>
<%@ page import="it.csi.iride2.policy.entity.Application"%>
<%@ page import="it.csi.papua.papuaserv.dto.gestioneutenti.Ruolo"%>
<%@ page import="java.util.StringTokenizer" %>

<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
  id="beanParametriApplication"
  scope="application"
  class="it.csi.agrc.BeanParametri">
</jsp:useBean>


<%
	CuneoLogger.debug(this,"####################### Sono in /jsp/controller/ruoli");	 
	CuneoLogger.debug(this, "Salto alla pagina di scelta dei ruoli ...");
	Utili.forward(request, response, "/jsp/view/ruoli.jsp");
%>