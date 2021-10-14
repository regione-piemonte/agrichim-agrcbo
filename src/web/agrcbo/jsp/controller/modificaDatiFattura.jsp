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

<jsp:useBean
     id="datiFattura"
     scope="page"
     class="it.csi.agrc.DatiFattura">
    <%
      datiFattura.setDataSource(dataSource);
      datiFattura.setAut(aut);
    %>
</jsp:useBean>

<%
  /**
   * Controllo che le etichette campione selezionate appartengano tutte allo
   * stesso utente ed allo stesso laboratorio e che abbiano tutte stato di
   * anomalia blank
   * */
  String richieste = request.getParameter("idRichiestaSearch");
  if (etichettaCampione.controllaFatturaMultipla(richieste, false)){
	  String funzione = request.getParameter("funzione");
		if ("conferma".equals(funzione)){
			//Utili.forwardConParametri(request, response, "/jsp/view/refertiEmessi.jsp");
			datiFattura.setFatturare(request.getParameter("fatturare"));
			datiFattura.setCfPartitaIva(request.getParameter("cfPartitaIva"));
			datiFattura.setRagioneSociale(request.getParameter("ragioneSociale"));
			datiFattura.setIndirizzo(request.getParameter("indirizzo"));
			datiFattura.setCap(request.getParameter("cap"));
			datiFattura.setComune(request.getParameter("comune"));
			datiFattura.setComuneDesc(request.getParameter("comuneDesc"));
			datiFattura.setPec(request.getParameter("pec"));
			datiFattura.setCod_destinatario(request.getParameter("cod_destinatario"));
		  
			//Modifica dati fattura per le richieste selezionate dall'utente
			datiFattura.updateDatiFatturaByIdRichiesteRange(richieste);
			CuneoLogger.debug(this, "modificaDatiFattura richieste " + richieste);
			request.setAttribute("idRichiestaSearch", richieste);
			//Utili.forward(request, response, "/jsp/view/refertiEmessi.jsp");
			Utili.forwardConParametri(request, response, "/jsp/view/refertiEmessi.jsp");
		}else{ 
			Utili.forwardConParametri(request, response, "/jsp/view/modificaDatiFattura.jsp");
    }
  }
  else{
    if (richieste.indexOf(",") == -1){
        Utili.forward(request, response, "/jsp/view/refertiEmessi.jsp?multiploModificaDatiFattura=no");
    }else{
        Utili.forward(request, response, "/jsp/view/refertiEmessi.jsp?multiploModificaDatiFattura=si");
    }
  }
%>