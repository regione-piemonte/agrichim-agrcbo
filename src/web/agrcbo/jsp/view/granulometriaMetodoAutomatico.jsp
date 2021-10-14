<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/granulometriaMetodoAutomatico.htm");
%>

<%
  boolean ferro=false,manganese=false,zinco=false,rame=false,boro=false;
  boolean fra4=false,fra5=false,std=false;
  boolean calcio=false,magnesio=false,potassio=false,csc=false,vBACl2=false;
  boolean richiestaAnalisi[]=new boolean[12];

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
     id="granulometriaA4Frazioni"
     scope="request"
     class="it.csi.agrc.GranulometriaA4Frazioni">
     <%
      granulometriaA4Frazioni.setDataSource(dataSource);
      granulometriaA4Frazioni.setAut(aut);
    %>
</jsp:useBean>

<jsp:useBean
     id="granulometriaA5Frazioni"
     scope="request"
     class="it.csi.agrc.GranulometriaA5Frazioni">
     <%
      granulometriaA5Frazioni.setDataSource(dataSource);
      granulometriaA5Frazioni.setAut(aut);
    %>
</jsp:useBean>

<jsp:useBean
     id="granulometriaStandard"
     scope="request"
     class="it.csi.agrc.GranulometriaStandard">
     <%
      granulometriaStandard.setDataSource(dataSource);
      granulometriaStandard.setAut(aut);
    %>
</jsp:useBean>

<%@ include file="/jsp/view/campioniLaboratorioAnalisiForm.inc" %>
<%@ include file="/jsp/view/abilitaAnalisiTer.inc" %>



<%

if (!richiestaAnalisi[Analisi.TER_GRANULOMETRIA_METODO_AUTOMATICO])
{
   /**
   * Non è stata scelta nessuna analisi, quindi devo solo visualizzare un
   * messaggio di avvertimento
   */
   templ.newBlock("nessuno");
}
else
{
%>

<!--
  Il file che includo serve a gestire gli errori dovuti al funzionamento non
  corretto del javascript
-->
<%@ include file="/jsp/view/problemiJavascript.inc" %>

<%
  templ.newBlock("analisi");
  boolean select=false,error=false;
  String argilla;
  String sabbiaTotale;
  String sabbiaGrossa;
  String limoTotale;
  String limoFine;
  String sabbiaFine;
  String limoGrosso;
  /**
  * Se modifica non è valorizzato significa che sono arrivato a questa pagina
  * dal menu. Se modifica è uguale a si significa che ho appena modificato i
  * dati dell'etichetta. Se invece modifica è uguale a no significa che
  * ho tentato di modificare i dati ma qualcosa non è andato a buon fine
  * */
  if ("si".equals(request.getParameter("modifica")))
  {
    templ.newBlock("modifica");
  }
  if (request.getParameter("errore") != null) error=true;
  if (std)
  {
    templ.newBlock("analisi.standard");
    granulometriaStandard.setIdRichiesta(aut.getIdRichiestaCorrente());

    /**
    * Se sono arrivato qui a seguito di un errore non devo leggere i parametri
    * dal database ma utilizzare quelli che sono nel bean e arrivano dalla
    * request
    */
    if (!error) select=granulometriaStandard.select();

    templ.bset("idRichiestaCorrente",granulometriaStandard.getIdRichiesta()+"");

    /**
      * Valorizzo questo campo per dire al controller su che tipo di analisi
      * andrà a lavorare
      */
    templ.bset("analisi",it.csi.agrc.Analisi.ANA_STANDARD);

    /**
     * Se sono qui in seguito ad un errore oppure ho trovato un record nel
     * database devo impostare i campi
     * */

    if (select || error)
    {
      argilla=granulometriaStandard.getArgilla();
      sabbiaTotale=granulometriaStandard.getSabbiaTotale();
      limoTotale=granulometriaStandard.getLimoTotale();

      /**
      * Devo sostiruiire per ogni campo reale il punto decimale con la virgola
      */
      if (argilla==null) argilla="";
      else argilla=argilla.replace('.',',');
      if (sabbiaTotale==null) sabbiaTotale="";
      else sabbiaTotale=sabbiaTotale.replace('.',',');
      if (limoTotale==null) limoTotale="";
      else limoTotale=limoTotale.replace('.',',');

      templ.bset("argilla",argilla);
      templ.bset("sabbiaTotale",sabbiaTotale);
      templ.bset("limoTotale",limoTotale);
    }
    if (select || (error && granulometriaStandard.select()))
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
  if (fra4)
  {
    templ.newBlock("analisi.quattroFra");
    granulometriaA4Frazioni.setIdRichiesta(aut.getIdRichiestaCorrente());

    /**
    * Se sono arrivato qui a seguito di un errore non devo leggere i parametri
    * dal database ma utilizzare quelli che sono nel bean e arrivano dalla
    * request
    */
    if (!error) select=granulometriaA4Frazioni.select();

    templ.bset("idRichiestaCorrente",granulometriaA4Frazioni.getIdRichiesta()+"");

    /**
      * Valorizzo questo campo per dire al controller su che tipo di analisi
      * andrà a lavorare
      */
    templ.bset("analisi",it.csi.agrc.Analisi.ANA_A4FRAZIONI);

    /**
     * Se sono qui in seguito ad un errore oppure ho trovato un record nel
     * database devo impostare i campi
     * */
    if (select || error)
    {
      argilla=granulometriaA4Frazioni.getArgilla();
      sabbiaTotale=granulometriaA4Frazioni.getSabbiaTotale();
      limoTotale=granulometriaA4Frazioni.getLimoTotale();
      limoFine=granulometriaA4Frazioni.getLimoFine();
      limoGrosso=granulometriaA4Frazioni.getLimoGrosso();

      /**
      * Devo sostiruiire per ogni campo reale il punto decimale con la virgola
      */
      if (argilla==null) argilla="";
      else argilla=argilla.replace('.',',');
      if (sabbiaTotale==null) sabbiaTotale="";
      else sabbiaTotale=sabbiaTotale.replace('.',',');
      if (limoTotale==null) limoTotale="";
      else limoTotale=limoTotale.replace('.',',');
      if (limoFine==null) limoFine="";
      else limoFine=limoFine.replace('.',',');
      if (limoGrosso==null) limoGrosso="";
      else limoGrosso=limoGrosso.replace('.',',');

      templ.bset("argilla",argilla);
      templ.bset("sabbiaTotale",sabbiaTotale);
      templ.bset("limoTotale",limoTotale);
      templ.bset("limoFine",limoFine);
      templ.bset("limoGrosso",limoGrosso);
    }
    if (select || (error && granulometriaA4Frazioni.select()))
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
  if (fra5)
  {
    templ.newBlock("analisi.cinqueFra");
    granulometriaA5Frazioni.setIdRichiesta(aut.getIdRichiestaCorrente());

    /**
    * Se sono arrivato qui a seguito di un errore non devo leggere i parametri
    * dal database ma utilizzare quelli che sono nel bean e arrivano dalla
    * request
    */
    if (!error) select=granulometriaA5Frazioni.select();

    templ.bset("idRichiestaCorrente",granulometriaA5Frazioni.getIdRichiesta()+"");

    /**
      * Valorizzo questo campo per dire al controller su che tipo di analisi
      * andrà a lavorare
      */
    templ.bset("analisi",it.csi.agrc.Analisi.ANA_A5FRAZIONI);

    /**
     * Se sono qui in seguito ad un errore oppure ho trovato un record nel
     * database devo impostare i campi
     * */
    if (select || error)
    {
      argilla=granulometriaA5Frazioni.getArgilla();
      sabbiaTotale=granulometriaA5Frazioni.getSabbiaTotale();
      sabbiaGrossa=granulometriaA5Frazioni.getSabbiaGrossa();
      limoTotale=granulometriaA5Frazioni.getLimoTotale();
      limoFine=granulometriaA5Frazioni.getLimoFine();
      sabbiaFine=granulometriaA5Frazioni.getSabbiaFine();
      limoGrosso=granulometriaA5Frazioni.getLimoGrosso();

      /**
      * Devo sostiruiire per ogni campo reale il punto decimale con la virgola
      */
      if (argilla==null) argilla="";
      else argilla=argilla.replace('.',',');
      if (sabbiaTotale==null) sabbiaTotale="";
      else sabbiaTotale=sabbiaTotale.replace('.',',');
      if (sabbiaGrossa==null) sabbiaGrossa="";
      else sabbiaGrossa=sabbiaGrossa.replace('.',',');
      if (limoTotale==null) limoTotale="";
      else limoTotale=limoTotale.replace('.',',');
      if (limoFine==null) limoFine="";
      else limoFine=limoFine.replace('.',',');
      if (sabbiaFine==null) sabbiaFine="";
      else sabbiaFine=sabbiaFine.replace('.',',');
      if (limoGrosso==null) limoGrosso="";
      else limoGrosso=limoGrosso.replace('.',',');

      templ.bset("argilla",argilla);
      templ.bset("sabbiaTotale",sabbiaTotale);
      templ.bset("sabbiaGrossa",sabbiaGrossa);
      templ.bset("limoTotale",limoTotale);
      templ.bset("limoFine",limoFine);
      templ.bset("sabbiaFine",sabbiaFine);
      templ.bset("limoGrosso",limoGrosso);
    }
    if (select || (error && granulometriaA5Frazioni.select()))
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
}
%>





<%= templ.text() %>
