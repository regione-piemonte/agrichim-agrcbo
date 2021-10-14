<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/annullaRichiestaCamp.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="tipoCampione"
     scope="page"
     class="it.csi.agrc.TipoCampione">
    <%
      tipoCampione.setDataSource(dataSource);
      tipoCampione.setAut(aut);
    %>
</jsp:useBean>

<jsp:useBean
     id="etichettaCampione"
     scope="page"
     class="it.csi.agrc.EtichettaCampione">
    <%
      etichettaCampione.setDataSource(dataSource);
      etichettaCampione.setAut(aut);
    %>
</jsp:useBean>

<%
  String idRichiesta = request.getParameter("idRichiestaSearch");
  /**
  *  Se idRichiesta è null significa che sono arrivato in questa pagina non
  * direttamente dalla pagina di ricerca ma passando per quella di inserisci
  * esito analisi. In questo ultimo caso devo quindi andare a leggere il
  * valore memorizzato nella sessione
  */
  if (idRichiesta == null)
  {
  	idRichiesta = aut.getIdRichiestaCorrente()+"";
  }
  else
  {
    long longRichiesta = 0;

     try
     {
       longRichiesta = Long.parseLong(idRichiesta);
       aut.setIdRichiestaCorrente(longRichiesta);
       tipoCampione.setIdRichiesta(longRichiesta);
       tipoCampione.select();
       aut.setCodMateriale(tipoCampione.getCodMateriale());
       session.setAttribute("aut",aut);
     }
     catch(Exception e)
     {}
  }
  templ.bset("idRichiestaSearch", idRichiesta);

  etichettaCampione.selectForCampioniLab();

  templ.newBlock("campione");

  templ.set("campione.idRichiesta", etichettaCampione.getIdRichiesta());
  templ.set("campione.statoRichiesta", etichettaCampione.getStatoAttuale());
  templ.set("campione.descMateriale",etichettaCampione.getDescMateriale());
  templ.set("campione.descrizioneEtichetta",etichettaCampione.getDescrizioneEtichetta());

  templ.set("campione.note",etichettaCampione.getNote());
%>

<%-- HTMPL genera ora la pagina web e si finalizza così lo stato stazionario --%>

<%= templ.text() %>