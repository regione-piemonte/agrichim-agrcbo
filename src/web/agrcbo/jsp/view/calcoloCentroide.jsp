<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/calcoloCentroide.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/controllaUtente.inc" %>
<%@ include file="/jsp/dataSource.inc" %>


<jsp:useBean
     id="centroide"
     scope="session"
     class="it.csi.agrc.CentroideParticelle"/>
      <%
      centroide.setDataSource(dataSource);
      centroide.setAut(aut);
    %>

<%@ include file="/jsp/view/problemiJavascript.inc" %>
<%

  String codiceVerificaElab = null;

   codiceVerificaElab = centroide.getCodiceVerificaElab();
	//System.out.println("codiceVerificaElab " + codiceVerificaElab);
	if (codiceVerificaElab== null) codiceVerificaElab ="";
	
	
	templ.bset("codiceVerificaElab",codiceVerificaElab);
	
	if(request.getParameter("avvio")!=null)
	{
		templ.newBlock("avviso");
	}
	
%>
  <%= templ.text() %>