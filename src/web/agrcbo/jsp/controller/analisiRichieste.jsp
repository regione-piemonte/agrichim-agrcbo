<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>

<jsp:useBean
     id="beanRicerca"
     scope="session"
     class="it.csi.agrc.BeanRicerca"/>



<%
 /**
  * Facendo cos� quando un utente va ad effettuare una ricerca non
  * si trova precaricato nessun dato
  **/
  beanRicerca.setNonValido();
  session.setAttribute("beanRicerca",beanRicerca);

  aut.ricercaElencoCampioni(Autenticazione.RICERCA_ANALISI_RICHIESTE);
  session.setAttribute("aut",aut);
  session.setAttribute("tipoRicerca", new Integer(Autenticazione.RICERCA_ANALISI_RICHIESTE));
  Utili.forward(request, response, "/jsp/view/analisiRichieste.jsp");
%>

