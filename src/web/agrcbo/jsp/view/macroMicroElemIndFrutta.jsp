<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/macroMicroElemIndFrutta.htm");
  boolean richiestaAnalisi[]=new boolean[2];
  boolean bCalcio=false,bMagnesio=false,bPotassio=false,bFerro=false,bManganese=false;
  boolean bZinco=false,bRame=false,bBoro=false,bFosforo=false,bAzoto=false;
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
     id="macroMicroElemIndFrutta"
     scope="request"
     class="it.csi.agrc.MacroMicroElemIndFrutta">
     <%
      macroMicroElemIndFrutta.setDataSource(dataSource);
      macroMicroElemIndFrutta.setAut(aut);
    %>
</jsp:useBean>


<%@ include file="/jsp/view/campioniLaboratorioAnalisiForm.inc" %>
<%@ include file="/jsp/view/abilitaAnalisiVegFru.inc" %>

<!--
  Il file che includo serve a gestire gli errori dovuti al funzionamento non
  corretto del javascript
-->
<%@ include file="/jsp/view/problemiJavascript.inc" %>

<%
  if (!richiestaAnalisi[Analisi.VEGFRU_MACRO_MICRO])
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
    if (bCalcio)
    {
      templ.newBlock("analisi.analisCalcioBlocco");
      templ.bset("analisiCalcio","true");
    }
    else templ.bset("analisiCalcio","false");
    if (bMagnesio)
    {
      templ.newBlock("analisi.analisMagnesioBlocco");
      templ.bset("analisiMagnesio","true");
    }
    else templ.bset("analisiMagnesio","false");
    if (bPotassio)
    {
      templ.newBlock("analisi.analisPotassioBlocco");
      templ.bset("analisiPotassio","true");
    }
    else templ.bset("analisiPotassio","false");
    if (bAzoto)
    {
      templ.newBlock("analisi.analisAzotoBlocco");
      templ.bset("analisiAzoto","true");
    }
    else templ.bset("analisiAzoto","false");
    if (bFosforo)
    {
      templ.newBlock("analisi.analisFosforoBlocco");
      templ.bset("analisiFosforo","true");
    }
    else templ.bset("analisiFosforo","false");
    if (bFerro)
    {
      templ.newBlock("analisi.analisFerroBlocco");
      templ.bset("analisiFerro","true");
    }
    else templ.bset("analisiFerro","false");
    if (bManganese)
    {
      templ.newBlock("analisi.analisManganeseBlocco");
      templ.bset("analisiManganese","true");
    }
    else templ.bset("analisiManganese","false");
    if (bZinco)
    {
      templ.newBlock("analisi.analisZincoBlocco");
      templ.bset("analisiZinco","true");
    }
    else templ.bset("analisiZinco","false");
    if (bRame)
    {
      templ.newBlock("analisi.analisRameBlocco");
      templ.bset("analisiRame","true");
    }
    else templ.bset("analisiRame","false");
    if (bBoro)
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
    macroMicroElemIndFrutta.setIdRichiesta(aut.getIdRichiestaCorrente());
    String pesoCampione;
    String volumePrimaDiluizione;
    String secondaDiluizioneCa;
    String calcioPpm;
    String letturaCaPpm;
    String calcio;
    String secondaDiluizioneMg;
    String magnesioPpm;
    String letturaMgPpm;
    String magnesio;
    String secondaDiluizioneK;
    String potassioPpm;
    String letturaKPpm;
    String potassio;
    String azoto;
    String azotoPpm;
    String fosforoPpm;
    String fosforo;
    String diluizioneFe;
    String ferroPpm;
    String letturaFePpm;
    String diluizioneMn;
    String manganesePpm;
    String letturaMnPpm;
    String diluizioneZn;
    String zincoPpm;
    String letturaZnPpm;
    String diluizioneCu;
    String ramePpm;
    String letturaCuPpm;
    String diluizioneB;
    String boroPpm;
    String letturaBPpm;

    /**
    * Se sono arrivato qui a seguito di un errore non devo leggere i parametri
    * dal database ma utilizzare quelli che sono nel bean e arrivano dalla
    * request
    */
    if (!error) select=macroMicroElemIndFrutta.select();

    templ.bset("idRichiestaCorrente",macroMicroElemIndFrutta.getIdRichiesta()+"");

    /**
     * Se sono qui in seguito ad un errore oppure ho trovato un record nel
     * database devo impostare i campi
     * */
    if (select || error)
    {
      pesoCampione=macroMicroElemIndFrutta.getPesoCampione();
      volumePrimaDiluizione=macroMicroElemIndFrutta.getVolumePrimaDiluizione();
      secondaDiluizioneCa=macroMicroElemIndFrutta.getSecondaDiluizioneCa();
      calcioPpm=macroMicroElemIndFrutta.getCalcioPpm();
      letturaCaPpm=macroMicroElemIndFrutta.getLetturaCaPpm();
      calcio=macroMicroElemIndFrutta.getCalcio();
      secondaDiluizioneMg=macroMicroElemIndFrutta.getSecondaDiluizioneMg();
      magnesioPpm=macroMicroElemIndFrutta.getMagnesioPpm();
      letturaMgPpm=macroMicroElemIndFrutta.getLetturaMgPpm();
      magnesio=macroMicroElemIndFrutta.getMagnesio();
      secondaDiluizioneK=macroMicroElemIndFrutta.getSecondaDiluizioneK();
      potassioPpm=macroMicroElemIndFrutta.getPotassioPpm();
      letturaKPpm=macroMicroElemIndFrutta.getLetturaKPpm();
      potassio=macroMicroElemIndFrutta.getPotassio();
      azoto=macroMicroElemIndFrutta.getAzoto();
      azotoPpm=macroMicroElemIndFrutta.getAzotoPpm();
      fosforoPpm=macroMicroElemIndFrutta.getFosforoPpm();
      fosforo=macroMicroElemIndFrutta.getFosforo();
      diluizioneFe=macroMicroElemIndFrutta.getDiluizioneFe();
      ferroPpm=macroMicroElemIndFrutta.getFerroPpm();
      letturaFePpm=macroMicroElemIndFrutta.getLetturaFePpm();
      diluizioneMn=macroMicroElemIndFrutta.getDiluizioneMn();
      manganesePpm=macroMicroElemIndFrutta.getManganesePpm();
      letturaMnPpm=macroMicroElemIndFrutta.getLetturaMnPpm();
      diluizioneZn=macroMicroElemIndFrutta.getDiluizioneZn();
      zincoPpm=macroMicroElemIndFrutta.getZincoPpm();
      letturaZnPpm=macroMicroElemIndFrutta.getLetturaZnPpm();
      diluizioneCu=macroMicroElemIndFrutta.getDiluizioneCu();
      ramePpm=macroMicroElemIndFrutta.getRamePpm();
      letturaCuPpm=macroMicroElemIndFrutta.getLetturaCuPpm();
      diluizioneB=macroMicroElemIndFrutta.getDiluizioneB();
      boroPpm=macroMicroElemIndFrutta.getBoroPpm();
      letturaBPpm=macroMicroElemIndFrutta.getLetturaBPpm();


      /**
      * Devo sostiruiire per ogni campo reale il punto decimale con la virgola
      */
      if (pesoCampione==null) pesoCampione="";
      else pesoCampione=pesoCampione.replace('.',',');
      if (volumePrimaDiluizione==null) volumePrimaDiluizione="";
      else volumePrimaDiluizione=volumePrimaDiluizione.replace('.',',');
      if (secondaDiluizioneCa==null) secondaDiluizioneCa="";
      else secondaDiluizioneCa=secondaDiluizioneCa.replace('.',',');
      if (calcioPpm==null) calcioPpm="";
      else calcioPpm=calcioPpm.replace('.',',');
      if (letturaCaPpm==null) letturaCaPpm="";
      else letturaCaPpm=letturaCaPpm.replace('.',',');
      if (calcio==null) calcio="";
      else calcio=calcio.replace('.',',');
      if (secondaDiluizioneMg==null) secondaDiluizioneMg="";
      else secondaDiluizioneMg=secondaDiluizioneMg.replace('.',',');
      if (magnesioPpm==null) magnesioPpm="";
      else magnesioPpm=magnesioPpm.replace('.',',');
      if (letturaMgPpm==null) letturaMgPpm="";
      else letturaMgPpm=letturaMgPpm.replace('.',',');
      if (magnesio==null) magnesio="";
      else magnesio=magnesio.replace('.',',');
      if (secondaDiluizioneK==null) secondaDiluizioneK="";
      else secondaDiluizioneK=secondaDiluizioneK.replace('.',',');
      if (potassioPpm==null) potassioPpm="";
      else potassioPpm=potassioPpm.replace('.',',');
      if (letturaKPpm==null) letturaKPpm="";
      else letturaKPpm=letturaKPpm.replace('.',',');
      if (potassio==null) potassio="";
      else potassio=potassio.replace('.',',');
      if (azoto==null) azoto="";
      else azoto=azoto.replace('.',',');
      if (azotoPpm==null) azotoPpm="";
      else azotoPpm=azotoPpm.replace('.',',');
      if (fosforoPpm==null) fosforoPpm="";
      else fosforoPpm=fosforoPpm.replace('.',',');
      if (fosforo==null) fosforo="";
      else fosforo=fosforo.replace('.',',');
      if (diluizioneFe==null) diluizioneFe="";
      else diluizioneFe=diluizioneFe.replace('.',',');
      if (ferroPpm==null) ferroPpm="";
      else ferroPpm=ferroPpm.replace('.',',');
      if (letturaFePpm==null) letturaFePpm="";
      else letturaFePpm=letturaFePpm.replace('.',',');
      if (diluizioneMn==null) diluizioneMn="";
      else diluizioneMn=diluizioneMn.replace('.',',');
      if (manganesePpm==null) manganesePpm="";
      else manganesePpm=manganesePpm.replace('.',',');
      if (letturaMnPpm==null) letturaMnPpm="";
      else letturaMnPpm=letturaMnPpm.replace('.',',');
      if (diluizioneZn==null) diluizioneZn="";
      else diluizioneZn=diluizioneZn.replace('.',',');
      if (zincoPpm==null) zincoPpm="";
      else zincoPpm=zincoPpm.replace('.',',');
      if (letturaZnPpm==null) letturaZnPpm="";
      else letturaZnPpm=letturaZnPpm.replace('.',',');
      if (diluizioneCu==null) diluizioneCu="";
      else diluizioneCu=diluizioneCu.replace('.',',');
      if (ramePpm==null) ramePpm="";
      else ramePpm=ramePpm.replace('.',',');
      if (letturaCuPpm==null) letturaCuPpm="";
      else letturaCuPpm=letturaCuPpm.replace('.',',');
      if (diluizioneB==null) diluizioneB="";
      else diluizioneB=diluizioneB.replace('.',',');
      if (boroPpm==null) boroPpm="";
      else boroPpm=boroPpm.replace('.',',');
      if (letturaBPpm==null) letturaBPpm="";
      else letturaBPpm=letturaBPpm.replace('.',',');


      templ.bset("pesoCampione",pesoCampione);
      templ.bset("volumePrimaDiluizione",volumePrimaDiluizione);
      templ.bset("secondaDiluizioneCa",secondaDiluizioneCa);
      templ.bset("calcioPpm",calcioPpm);
      templ.bset("letturaCaPpm",letturaCaPpm);
      templ.bset("calcio",calcio);
      templ.bset("secondaDiluizioneMg",secondaDiluizioneMg);
      templ.bset("magnesioPpm",magnesioPpm);
      templ.bset("letturaMgPpm",letturaMgPpm);
      templ.bset("magnesio",magnesio);
      templ.bset("secondaDiluizioneK",secondaDiluizioneK);
      templ.bset("potassioPpm",potassioPpm);
      templ.bset("letturaKPpm",letturaKPpm);
      templ.bset("potassio",potassio);
      templ.bset("azoto",azoto);
      templ.bset("azotoPpm",azotoPpm);
      templ.bset("fosforoPpm",fosforoPpm);
      templ.bset("fosforo",fosforo);
      templ.bset("diluizioneFe",diluizioneFe);
      templ.bset("ferroPpm",ferroPpm);
      templ.bset("letturaFePpm",letturaFePpm);
      templ.bset("diluizioneMn",diluizioneMn);
      templ.bset("manganesePpm",manganesePpm);
      templ.bset("letturaMnPpm",letturaMnPpm);
      templ.bset("diluizioneZn",diluizioneZn);
      templ.bset("zincoPpm",zincoPpm);
      templ.bset("letturaZnPpm",letturaZnPpm);
      templ.bset("diluizioneCu",diluizioneCu);
      templ.bset("ramePpm",ramePpm);
      templ.bset("letturaCuPpm",letturaCuPpm);
      templ.bset("diluizioneB",diluizioneB);
      templ.bset("boroPpm",boroPpm);
      templ.bset("letturaBPpm",letturaBPpm);
    }
    else
    {
      templ.bset("pesoCampione","5,000");
      templ.bset("volumePrimaDiluizione","50,000");
      templ.bset("secondaDiluizioneCa","100,000");
      templ.bset("secondaDiluizioneMg","100,000");
      templ.bset("secondaDiluizioneK","400,000");
      templ.bset("diluizioneFe","100,000");
      templ.bset("diluizioneMn","100,000");
      templ.bset("diluizioneZn","100,000");
      templ.bset("diluizioneCu","100,000");
      templ.bset("diluizioneB","100,000");
    }
    if (select || (error && macroMicroElemIndFrutta.select()))
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
