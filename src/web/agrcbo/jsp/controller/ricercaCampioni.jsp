<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="beanRicerca"
     scope="session"
     class="it.csi.agrc.BeanRicerca"/>

<jsp:useBean
     id="etichettaCampioni"
     scope="page"
     class="it.csi.agrc.EtichettaCampioni">
    <%
      etichettaCampioni.setDataSource(dataSource);
      etichettaCampioni.setAut(aut);
      etichettaCampioni.setPasso(beanParametriApplication.getMaxRecordXPagina());
    %>
</jsp:useBean>

<%
 /**
  * Facendo così quando un utente va ad effettuare una ricerca non
  * si trova precaricato nessun dato
  **/
  beanRicerca.setNonValido();
  session.setAttribute("beanRicerca", beanRicerca);

  aut.ricercaElencoCampioni(Autenticazione.RICERCA_CAMPIONI);
  session.setAttribute("aut", aut); 
	  
  Utili.forward(request, response, "/jsp/view/ricercaCampioni.jsp");
%>