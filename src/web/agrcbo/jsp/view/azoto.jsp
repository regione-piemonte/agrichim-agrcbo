<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/azoto.htm");
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
     id="azoto"
     scope="request"
     class="it.csi.agrc.Azoto">
     <%
      azoto.setDataSource(dataSource);
      azoto.setAut(aut);
    %>
</jsp:useBean>

<%
  boolean richiestaAnalisi[]=new boolean[12];
  boolean ferro=false,manganese=false,zinco=false,rame=false,boro=false;
  boolean fra4=false,fra5=false,std=false;
  boolean calcio=false,magnesio=false,potassio=false,csc=false,vBACl2=false;

  //Metalli pesanti
  boolean ferroTotale = false, manganeseTotale = false, zincoTotale = false, rameTotale = false, piomboTotale = false, cromoTotale = false,
	boroTotale = false, nichelTotale = false, cadmioTotale = false, stronzioTotale = false, altroMetalloTotale = false; 
%>

<%@ include file="/jsp/view/campioniLaboratorioAnalisiForm.inc" %>
<%@ include file="/jsp/view/abilitaAnalisiTer.inc" %>

<!--
  Il file che includo serve a gestire gli errori dovuti al funzionamento non
  corretto del javascript
-->
<%@ include file="/jsp/view/problemiJavascript.inc" %>

<%


  if (!richiestaAnalisi[Analisi.TER_AZOTO])
  {
     /**
     * Non è stata scelta l'analisi dell'azoto, quindi devo solo visualizzare un
     * messaggio di avvertimento
     */
    templ.newBlock("nessuno");
  }
  else
  {
    templ.newBlock("analisi");

    /**
    * Se modifica non è valorizzato significa che sono arrivato a questa pagina
    * dal menu. Se modifica è uguale a si significa che ho appena modificato i
    * dati dell'etichetta. Se invece modifica è uguale a no significa che
    * ho tentato di modificare i dati ma qualcosa non è andato a buon fine
    * */

    boolean select=false,error=false;
    if ("si".equals(request.getParameter("modifica")))
    {
      templ.newBlock("modifica");
    }
    if (request.getParameter("errore") != null) error=true;
    azoto.setIdRichiesta(aut.getIdRichiestaCorrente());
    String azotoTotaleMetodoAna;
    String azotoKjeldahl;
    String rapportoCN;

    /**
    * Se sono arrivato qui a seguito di un errore non devo leggere i parametri
    * dal database ma utilizzare quelli che sono nel bean e arrivano dalla
    * request
    */
    if (!error) select=azoto.select();

    templ.bset("idRichiestaCorrente",azoto.getIdRichiesta()+"");

    /**
     * Se sono qui in seguito ad un errore oppure ho trovato un record nel
     * database devo impostare i campi
     * */
    if (select || error)
    {
      azotoTotaleMetodoAna=azoto.getAzotoTotaleMetodoAna();
      azotoKjeldahl=azoto.getAzotoKjeldahl();
      rapportoCN=azoto.getRapportoCN();

      /**
      * Devo sostiruiire per ogni campo reale il punto decimale con la virgola
      */
      if (azotoTotaleMetodoAna==null) azotoTotaleMetodoAna="";
      else azotoTotaleMetodoAna=azotoTotaleMetodoAna.replace('.',',');
      if (azotoKjeldahl==null) azotoKjeldahl="";
      else azotoKjeldahl=azotoKjeldahl.replace('.',',');
      if (rapportoCN==null) rapportoCN="";
      else rapportoCN=rapportoCN.replace('.',',');


      templ.bset("azotoTotaleMetodoAna",azotoTotaleMetodoAna);
      templ.bset("azotoKjeldahl",azotoKjeldahl);
      templ.bset("rapportoCN",rapportoCN);
    }
    if (select || (error && azoto.select()))
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
