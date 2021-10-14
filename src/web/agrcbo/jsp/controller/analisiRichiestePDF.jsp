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
   * Controllo che tutti i campioni selezionati appartengano allo stesso tipo
   * di materiale
   * */
  String richieste=request.getParameter("idRichiestaSearch");
  if (richieste.indexOf(",")==-1 || etichettaCampione.controllaAccettazioneScartoMultiplo(richieste,EtichettaCampione.MULTIPLO_CAMPIONI_LABORATORIO))
  {
%>

  <html>
  <head>
  <script src="../layout/js/gestione.js"></script>
  <script>
    pop('../report/analisiRichieste.pdf?idRichiestaPDF=<%= richieste%>',647,480,'pdf');
    window.location.replace("../view/campioniLaboratorio.jsp");
  </script>
  </head>
  <body>
  </body>
  </html>

<%
  }
  else
    Utili.forwardConParametri(request, response, "/jsp/view/campioniLaboratorio.jsp?multiplo=no");
%>


