<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*" isThreadSafe="true" %>
<%
  it.csi.jsf.htmpl.Htmpl templ=null ;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/controllaUtente.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="etichettaCampione"
     scope="request"
     class="it.csi.agrc.EtichettaCampione">
    <%
      etichettaCampione.setDataSource(dataSource);
      etichettaCampione.setAut(aut);
    %>
</jsp:useBean>


<%
  /**
   * Controllo che le etichette campione selezionate appartengano tutte allo
   * stesso utente ed allo stesso laboratorio e che abbiano tutte stato di
   * anomalia blank
   * */
  String richieste=request.getParameter("idRichiestaSearch");
  String provenienza=request.getParameter("pagina");	
  if ("ricercaCampioni.jsp".equals(provenienza))
  {
  	  if (etichettaCampione.controllaFatturaMultipla(richieste, true))
	  {
	    Utili.forwardConParametri(request, response, "/jsp/view/fattura.jsp");
	  }
	  else
	  {
	    if (richieste.indexOf(",")==-1)
	      Utili.forwardConParametri(request, response, "/jsp/view/ricercaCampioni.jsp?multiplo=no");
	    else
	      Utili.forwardConParametri(request, response, "/jsp/view/ricercaCampioni.jsp?multiplo=si");
	  }
  }
  else
  {
	  if (etichettaCampione.controllaFatturaMultipla(richieste, true))
	  {
	    Utili.forwardConParametri(request, response, "/jsp/view/fattura.jsp");
	  }
	  else
	  {
	    if (richieste.indexOf(",")==-1)
	      Utili.forwardConParametri(request, response, "/jsp/view/refertiEmessi.jsp?multiplo=no");
	    else
	      Utili.forwardConParametri(request, response, "/jsp/view/refertiEmessi.jsp?multiplo=si");
	  }
  }
%>


