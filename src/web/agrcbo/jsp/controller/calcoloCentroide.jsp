<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>
<%@ page import="it.csi.iride2.policy.entity.Identita" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>


<jsp:useBean
     id="centroide"
     scope="request"
     class="it.csi.agrc.CentroideParticelle">
    <%
      centroide.setDataSource(dataSource);
      centroide.setAut(aut);
    %>
</jsp:useBean>
<jsp:setProperty name="centroide" property="*"/>

<%
 /**
  * Facendo così quando un utente va ad effettuare una ricerca non
  * si trova precaricato nessun dato
  **/

  //System.out.println("Elisa devi avviare l'elaborazione dei dati " + centroide.getCodiceVerificaElab());
  Identita identita = (Identita) session.getAttribute("identita"); 
  String errore = centroide.verificaCodice();
 // System.out.println("errore 1" + errore);
  if (errore!=null)
  {	
    Utili.forwardConParametri(request, response, "/jsp/view/calcoloCentroide.jsp?errore="+errore);
    return;
  }
  //se il codice verifica è corretto bisogna verificare che non ci sia un'elaborazione in corso
  errore = centroide.verificaElabInCorsoUpdate();
  //System.out.println("errore 2" + errore);
  if(errore!=null)
  {
	  Utili.forwardConParametri(request, response, "/jsp/view/calcoloCentroide.jsp?erroreElabInCorso="+errore);
	    return;
  }
    
  //se tutto è andato bene devo cercare quanti sono i record 
  Utili.forward(request, response, "/jsp/view/calcoloCentroide.jsp?avvio=OK");
  
 // System.out.println("Elisa dopo forward");
  //devo iniziare l'elaborazione
  
  centroide.avviaElab(beanParametriApplication, identita.getCodFiscale());
  
  
%>

