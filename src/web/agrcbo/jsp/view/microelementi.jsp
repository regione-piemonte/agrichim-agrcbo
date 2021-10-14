<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/microelementi.htm");
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
     id="microelementi"
     scope="request"
     class="it.csi.agrc.MicroelementiMetodoDTPA">
     <%
      microelementi.setDataSource(dataSource);
      microelementi.setAut(aut);
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
  if (!richiestaAnalisi[Analisi.TER_MICROELEMENTI])
  {
     /**
     * Non è stata scelta nessuna analisi, quindi devo solo visualizzare un
     * messaggio di avvertimento
     */
    templ.newBlock("nessuno");
  }
  else
  {
    templ.newBlock("analisi");
    if (ferro)
    {
      templ.newBlock("analisi.analisFerroBlocco");
      templ.bset("analisiFerro","true");
    }
    else templ.bset("analisiFerro","false");
    if (manganese)
    {
      templ.newBlock("analisi.analisManganeseBlocco");
      templ.bset("analisiManganese","true");
    }
    else templ.bset("analisiManganese","false");
    if (zinco)
    {
      templ.newBlock("analisi.analisZincoBlocco");
      templ.bset("analisiZinco","true");
    }
    else templ.bset("analisiZinco","false");
    if (rame)
    {
      templ.newBlock("analisi.analisRameBlocco");
      templ.bset("analisiRame","true");
    }
    else templ.bset("analisiRame","false");
    if (boro)
    {
      templ.newBlock("analisi.analisBoroBlocco");
      templ.bset("analisiBoro","true");
    }
    else templ.bset("analisiBoro","false");

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
    microelementi.setIdRichiesta(aut.getIdRichiestaCorrente());

    String letturaFerro;
    String diluizioneFerro;
    String ferroAssimilabile;
    String letturaManganese;
    String diluizioneManganese;
    String manganeseAssimilabile;
    String letturaZinco;
    String diluizioneZinco;
    String zincoAssimilabile;
    String letturaRame;
    String diluizioneRame;
    String rameAssimilabile;
    String letturaBoro;
    String diluizioneBoro;
    String boroAssimilabile;

    /**
    * Se sono arrivato qui a seguito di un errore non devo leggere i parametri
    * dal database ma utilizzare quelli che sono nel bean e arrivano dalla
    * request
    */
    if (!error) select=microelementi.select();

    templ.bset("idRichiestaCorrente",microelementi.getIdRichiesta()+"");

    /**
     * Se sono qui in seguito ad un errore oppure ho trovato un record nel
     * database devo impostare i campi
     * */
    if (select || error)
    {
      letturaFerro=microelementi.getLetturaFerro();
      diluizioneFerro=microelementi.getDiluizioneFerro();
      ferroAssimilabile=microelementi.getFerroAssimilabile();
      letturaManganese=microelementi.getLetturaManganese();
      diluizioneManganese=microelementi.getDiluizioneManganese();
      manganeseAssimilabile=microelementi.getManganeseAssimilabile();
      letturaZinco=microelementi.getLetturaZinco();
      diluizioneZinco=microelementi.getDiluizioneZinco();
      zincoAssimilabile=microelementi.getZincoAssimilabile();
      letturaRame=microelementi.getLetturaRame();
      diluizioneRame=microelementi.getDiluizioneRame();
      rameAssimilabile=microelementi.getRameAssimilabile();
      letturaBoro=microelementi.getLetturaBoro();
      diluizioneBoro=microelementi.getDiluizioneBoro();
      boroAssimilabile=microelementi.getBoroAssimilabile();

      /**
      * Devo sostiruiire per ogni campo reale il punto decimale con la virgola
      */
      if (letturaFerro==null) letturaFerro="";
      else letturaFerro=letturaFerro.replace('.',',');
      if (diluizioneFerro==null) diluizioneFerro="";
      else diluizioneFerro=diluizioneFerro.replace('.',',');
      if (ferroAssimilabile==null) ferroAssimilabile="";
      else ferroAssimilabile=ferroAssimilabile.replace('.',',');
      if (letturaManganese==null) letturaManganese="";
      else letturaManganese=letturaManganese.replace('.',',');
      if (diluizioneManganese==null) diluizioneManganese="";
      else diluizioneManganese=diluizioneManganese.replace('.',',');
      if (manganeseAssimilabile==null) manganeseAssimilabile="";
      else manganeseAssimilabile=manganeseAssimilabile.replace('.',',');
      if (letturaZinco==null) letturaZinco="";
      else letturaZinco=letturaZinco.replace('.',',');
      if (diluizioneZinco==null) diluizioneZinco="";
      else diluizioneZinco=diluizioneZinco.replace('.',',');
      if (zincoAssimilabile==null) zincoAssimilabile="";
      else zincoAssimilabile=zincoAssimilabile.replace('.',',');
      if (letturaRame==null) letturaRame="";
      else letturaRame=letturaRame.replace('.',',');
      if (diluizioneRame==null) diluizioneRame="";
      else diluizioneRame=diluizioneRame.replace('.',',');
      if (rameAssimilabile==null) rameAssimilabile="";
      else rameAssimilabile=rameAssimilabile.replace('.',',');
      if (letturaBoro==null) letturaBoro="";
      else letturaBoro=letturaBoro.replace('.',',');
      if (diluizioneBoro==null) diluizioneBoro="";
      else diluizioneBoro=diluizioneBoro.replace('.',',');
      if (boroAssimilabile==null) boroAssimilabile="";
      else boroAssimilabile=boroAssimilabile.replace('.',',');

      templ.bset("letturaFerro",letturaFerro);
      templ.bset("diluizioneFerro",diluizioneFerro);
      templ.bset("ferroAssimilabile",ferroAssimilabile);
      templ.bset("letturaManganese",letturaManganese);
      templ.bset("diluizioneManganese",diluizioneManganese);
      templ.bset("manganeseAssimilabile",manganeseAssimilabile);
      templ.bset("letturaZinco",letturaZinco);
      templ.bset("diluizioneZinco",diluizioneZinco);
      templ.bset("zincoAssimilabile",zincoAssimilabile);
      templ.bset("letturaRame",letturaRame);
      templ.bset("diluizioneRame",diluizioneRame);
      templ.bset("rameAssimilabile",rameAssimilabile);
      templ.bset("letturaBoro",letturaBoro);
      templ.bset("diluizioneBoro",diluizioneBoro);
      templ.bset("boroAssimilabile",boroAssimilabile);
    }
    else
    {
      templ.bset("diluizioneFerro","10,000");
      templ.bset("diluizioneManganese","10,000");
      templ.bset("diluizioneZinco","10,000");
      templ.bset("diluizioneRame","10,000");
      templ.bset("diluizioneBoro","1,000");
    }
    if (select || (error && microelementi.select()))
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
