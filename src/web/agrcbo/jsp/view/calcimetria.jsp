<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/calcimetria.htm");
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
     id="calcimetria"
     scope="request"
     class="it.csi.agrc.Calcimetria">
     <%
      calcimetria.setDataSource(dataSource);
      calcimetria.setAut(aut);
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
  if (!richiestaAnalisi[Analisi.TER_CALCIMETRIA])
  {
     /**
     * Non è stata scelta l'analisi della calcimetria, quindi devo solo
     * visualizzare un messaggio di avvertimento
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
    calcimetria.setIdRichiesta(aut.getIdRichiestaCorrente());
    String letturaCalcimetro;
    String pressioneAtmosferica;
    String temperatura;
    String tensioneDiVapore;
    String carbonatoCalcioTotale;
    String calcareAttivo;
    String letturaFerroOssalato;
    String diluizioneDeterminaFerro;
    String ferroOssalato;
    String indicePotereClorosante;

    /**
    * Se sono arrivato qui a seguito di un errore non devo leggere i parametri
    * dal database ma utilizzare quelli che sono nel bean e arrivano dalla
    * request
    */
    if (!error) select=calcimetria.select();

    templ.bset("idRichiestaCorrente",calcimetria.getIdRichiesta()+"");

    /**
     * Se sono qui in seguito ad un errore oppure ho trovato un record nel
     * database devo impostare i campi
     * */
    if (select || error)
    {
      letturaCalcimetro=calcimetria.getLetturaCalcimetro();
      pressioneAtmosferica=calcimetria.getPressioneAtmosferica();
      temperatura=calcimetria.getTemperatura();
      tensioneDiVapore=calcimetria.getTensioneDiVapore();
      carbonatoCalcioTotale=calcimetria.getCarbonatoCalcioTotale();
      calcareAttivo=calcimetria.getCalcareAttivo();
      letturaFerroOssalato=calcimetria.getLetturaFerroOssalato();
      diluizioneDeterminaFerro=calcimetria.getDiluizioneDeterminaFerro();
      ferroOssalato=calcimetria.getFerroOssalato();
      indicePotereClorosante=calcimetria.getIndicePotereClorosante();

      /**
      * Devo sostiruiire per ogni campo reale il punto decimale con la virgola
      */
      if (letturaCalcimetro==null) letturaCalcimetro="";
      else letturaCalcimetro=letturaCalcimetro.replace('.',',');
      if (pressioneAtmosferica==null) pressioneAtmosferica="";
      else pressioneAtmosferica=pressioneAtmosferica.replace('.',',');
      if (temperatura==null) temperatura="";
      else temperatura=temperatura.replace('.',',');
      if (tensioneDiVapore==null) tensioneDiVapore="";
      else tensioneDiVapore=tensioneDiVapore.replace('.',',');
      if (carbonatoCalcioTotale==null) carbonatoCalcioTotale="";
      else carbonatoCalcioTotale=carbonatoCalcioTotale.replace('.',',');
      if (calcareAttivo==null) calcareAttivo="";
      else calcareAttivo=calcareAttivo.replace('.',',');
      if (letturaFerroOssalato==null) letturaFerroOssalato="";
      else letturaFerroOssalato=letturaFerroOssalato.replace('.',',');
      if (diluizioneDeterminaFerro==null) diluizioneDeterminaFerro="";
      else diluizioneDeterminaFerro=diluizioneDeterminaFerro.replace('.',',');
      if (ferroOssalato==null) ferroOssalato="";
      else ferroOssalato=ferroOssalato.replace('.',',');
      if (indicePotereClorosante==null) indicePotereClorosante="";
      else indicePotereClorosante=indicePotereClorosante.replace('.',',');

      templ.bset("letturaCalcimetro",letturaCalcimetro);
      templ.bset("pressioneAtmosferica",pressioneAtmosferica);
      templ.bset("temperatura",temperatura);
      templ.bset("tensioneDiVapore",tensioneDiVapore);
      templ.bset("carbonatoCalcioTotale",carbonatoCalcioTotale);
      templ.bset("calcareAttivo",calcareAttivo);
      templ.bset("letturaFerroOssalato",letturaFerroOssalato);
      templ.bset("diluizioneDeterminaFerro",diluizioneDeterminaFerro);
      templ.bset("ferroOssalato",ferroOssalato);
      templ.bset("indicePotereClorosante",indicePotereClorosante);
    }
    if (select || (error && calcimetria.select()))
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
