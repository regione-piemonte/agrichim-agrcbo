<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="etichettaCampione"
     scope="page"
     class="it.csi.agrc.EtichettaCampione">
    <%
      etichettaCampione.setDataSource(dataSource);
      etichettaCampione.setAut(aut);
    %>
</jsp:useBean>

<jsp:useBean
     id="tipoCampione"
     scope="page"
     class="it.csi.agrc.TipoCampione">
    <%
      tipoCampione.setDataSource(dataSource);
      tipoCampione.setAut(aut);
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
   long idRichieste[]=Utili.longIdTokenize(richieste,",");
  /**
    * Imposto il numero della richiesta ed il materiale usato nella sessione
    * per poterlo utilizzare all'interno delle altre voci inerenti la modifica
    * del campione. Inoltre imposta la variabile coordinateGeografiche
    */
    tipoCampione.setIdRichiesta(idRichieste[0]);
    tipoCampione.select();
    aut.setCodMateriale(tipoCampione.getCodMateriale());
    aut.setIdRichieste(richieste);
    session.setAttribute("aut",aut);
    /*A seconda che il tipo di materiale da analizzare sia terreno o altro dovrò
      andare in due pagine diverse*/
    String codMateriale = aut.getCodMateriale();
    if (Analisi.MAT_TERRENO.equals(codMateriale))
        Utili.forward(request, response, "/jsp/view/analisiTerreni.jsp");
    else Utili.forward(request, response, "/jsp/view/analisiVegetali.jsp");
  }
  else
      Utili.forwardConParametri(request, response, "/jsp/view/campioniLaboratorio.jsp?multiplo=no");

%>
