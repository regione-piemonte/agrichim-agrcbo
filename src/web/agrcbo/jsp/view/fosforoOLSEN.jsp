<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/fosforoOLSEN.htm");
%>

<%
  boolean richiestaAnalisi[]=new boolean[12];
  boolean ferro=false,manganese=false,zinco=false,rame=false,boro=false;
  boolean fra4=false,fra5=false,std=false;
  boolean calcio=false,magnesio=false,potassio=false,csc=false,vBACl2=false;

  //Metalli pesanti
  boolean ferroTotale = false, manganeseTotale = false, zincoTotale = false, rameTotale = false, piomboTotale = false, cromoTotale = false,
	boroTotale = false, nichelTotale = false, cadmioTotale = false, stronzioTotale = false, altroMetalloTotale = false;
%>


<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>


<jsp:useBean
     id="etichettaCampioni"
     scope="page"
     class="it.csi.agrc.EtichettaCampioni">
    <%
      etichettaCampioni.setDataSource(dataSource);
      etichettaCampioni.setAut(aut);
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

<jsp:useBean
     id="fosforoMetodoOLSEN"
     scope="request"
     class="it.csi.agrc.FosforoMetodoOLSEN">
     <%
      fosforoMetodoOLSEN.setDataSource(dataSource);
      fosforoMetodoOLSEN.setAut(aut);
    %>
</jsp:useBean>


<%@ include file="/jsp/view/campioniLaboratorioAnalisiForm.inc" %>
<%@ include file="/jsp/view/abilitaAnalisiTer.inc" %>

<!--
  Il file che includo serve a gestire gli errori dovuti al funzionamento non
  corretto del javascript
-->
<%@ include file="/jsp/view/problemiJavascript.inc" %>

<%
  if (!richiestaAnalisi[Analisi.TER_FOSFORO_OLSEN])
  {
     /**
     * Non � stata scelta l'analisi del fosforo olsen, quindi devo solo
     * visualizzare un messaggio di avvertimento
     */
    templ.newBlock("nessuno");
  }
  else
  {
    templ.newBlock("analisi");
    /**
    * Se modifica non � valorizzato significa che sono arrivato a questa pagina
    * dal menu. Se modifica � uguale a si significa che ho appena modificato i
    * dati dell'etichetta. Se invece modifica � uguale a no significa che
    * ho tentato di modificare i dati ma qualcosa non � andato a buon fine
    * */
    boolean select=false,error=false;
    if ("si".equals(request.getParameter("modifica")))
    {
      templ.newBlock("modifica");
    }
    if (request.getParameter("errore") != null) error=true;
    fosforoMetodoOLSEN.setIdRichiesta(aut.getIdRichiestaCorrente());

    String letturaFosforo;
    String fosforoAssimilabile;
    String anidrideFosforica;

    /**
    * Se sono arrivato qui a seguito di un errore non devo leggere i parametri
    * dal database ma utilizzare quelli che sono nel bean e arrivano dalla
    * request
    */
    if (!error) select=fosforoMetodoOLSEN.select();

    templ.bset("idRichiestaCorrente",fosforoMetodoOLSEN.getIdRichiesta()+"");

    /**
     * Se sono qui in seguito ad un errore oppure ho trovato un record nel
     * database devo impostare i campi
     * */
    if (select || error)
    {
      letturaFosforo=fosforoMetodoOLSEN.getLetturaFosforo();
      fosforoAssimilabile=fosforoMetodoOLSEN.getFosforoAssimilabile();
      anidrideFosforica=fosforoMetodoOLSEN.getAnidrideFosforica();

      /**
      * Devo sostiruiire per ogni campo reale il punto decimale con la virgola
      */
      if (letturaFosforo==null) letturaFosforo="";
      else letturaFosforo=letturaFosforo.replace('.',',');
      if (fosforoAssimilabile==null) fosforoAssimilabile="";
      else fosforoAssimilabile=fosforoAssimilabile.replace('.',',');
      if (anidrideFosforica==null) anidrideFosforica="";
      else anidrideFosforica=anidrideFosforica.replace('.',',');


      templ.bset("letturaFosforo",letturaFosforo);
      templ.bset("fosforoAssimilabile",fosforoAssimilabile);
      templ.bset("anidrideFosforica",anidrideFosforica);
    }
    if (select || (error && fosforoMetodoOLSEN.select()))
    {
      /**
      * In questo caso sono in update, quindi vuol dire che ho un record che
      * eventualmente posso anche cancellare
      */
      templ.newBlock("cancella");
      templ.bset("azione","update");
    }
    else
    {
      /**
      * In questo caso sono in insert
      */
      templ.newBlock("nonCancella");
      templ.bset("azione","insert");
    }
  }
%>

<%= templ.text() %>
