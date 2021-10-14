<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*" isThreadSafe="true" %>
<%
  it.csi.jsf.htmpl.Htmpl templ=null ;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
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
  if (richieste.indexOf(",")==-1 || etichettaCampione.controllaAccettazioneScartoMultiplo(richieste,EtichettaCampione.MULTIPLO_ANALISI_RICHIESTE))
    Utili.forwardConParametri(request, response, "/jsp/view/accettazioneScarto.jsp");
  else
    Utili.forwardConParametri(request, response, "/jsp/view/analisiRichieste.jsp?multiplo=no");
%>


