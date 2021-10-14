<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/macroMicroElemIndFoglie.htm");
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
     id="macroMicroElemIndFoglie"
     scope="request"
     class="it.csi.agrc.MacroMicroElemIndFoglie">
     <%
      macroMicroElemIndFoglie.setDataSource(dataSource);
      macroMicroElemIndFoglie.setAut(aut);
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
    if (bFerro)
    {
      templ.newBlock("analisi.analisFerroBlocco");
      templ.bset("analisiFerro","true");
    }
    else templ.bset("analisiFerro","false");
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
    macroMicroElemIndFoglie.setIdRichiesta(aut.getIdRichiestaCorrente());

    String pesoCampione;
    String volumeDiluizione;
    String letturaCaPpm;
    String calcioPpm;
    String calcio;
    String letturaMgPpm;
    String magnesioPpm;
    String magnesio;
    String letturaKPpm;
    String potassioPpm;
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
    if (!error) select=macroMicroElemIndFoglie.select();

    templ.bset("idRichiestaCorrente",macroMicroElemIndFoglie.getIdRichiesta()+"");

    /**
     * Se sono qui in seguito ad un errore oppure ho trovato un record nel
     * database devo impostare i campi
     * */
    if (select || error)
    {
      pesoCampione=macroMicroElemIndFoglie.getPesoCampione();
      volumeDiluizione=macroMicroElemIndFoglie.getVolumeDiluizione();
      letturaCaPpm=macroMicroElemIndFoglie.getLetturaCaPpm();
      calcioPpm=macroMicroElemIndFoglie.getCalcioPpm();
      calcio=macroMicroElemIndFoglie.getCalcio();
      letturaMgPpm=macroMicroElemIndFoglie.getLetturaMgPpm();
      magnesioPpm=macroMicroElemIndFoglie.getMagnesioPpm();
      magnesio=macroMicroElemIndFoglie.getMagnesio();
      letturaKPpm=macroMicroElemIndFoglie.getLetturaKPpm();
      potassioPpm=macroMicroElemIndFoglie.getPotassioPpm();
      potassio=macroMicroElemIndFoglie.getPotassio();
      azoto=macroMicroElemIndFoglie.getAzoto();
      azotoPpm=macroMicroElemIndFoglie.getAzotoPpm();
      fosforoPpm=macroMicroElemIndFoglie.getFosforoPpm();
      fosforo=macroMicroElemIndFoglie.getFosforo();
      diluizioneFe=macroMicroElemIndFoglie.getDiluizioneFe();
      ferroPpm=macroMicroElemIndFoglie.getFerroPpm();
      letturaFePpm=macroMicroElemIndFoglie.getLetturaFePpm();
      diluizioneMn=macroMicroElemIndFoglie.getDiluizioneMn();
      manganesePpm=macroMicroElemIndFoglie.getManganesePpm();
      letturaMnPpm=macroMicroElemIndFoglie.getLetturaMnPpm();
      diluizioneZn=macroMicroElemIndFoglie.getDiluizioneZn();
      zincoPpm=macroMicroElemIndFoglie.getZincoPpm();
      letturaZnPpm=macroMicroElemIndFoglie.getLetturaZnPpm();
      diluizioneCu=macroMicroElemIndFoglie.getDiluizioneCu();
      ramePpm=macroMicroElemIndFoglie.getRamePpm();
      letturaCuPpm=macroMicroElemIndFoglie.getLetturaCuPpm();
      diluizioneB=macroMicroElemIndFoglie.getDiluizioneB();
      boroPpm=macroMicroElemIndFoglie.getBoroPpm();
      letturaBPpm=macroMicroElemIndFoglie.getLetturaBPpm();

      /**
      * Devo sostiruiire per ogni campo reale il punto decimale con la virgola
      */
      if (pesoCampione==null) pesoCampione="";
      else pesoCampione=pesoCampione.replace('.',',');
      if (volumeDiluizione==null) volumeDiluizione="";
      else volumeDiluizione=volumeDiluizione.replace('.',',');
      if (letturaCaPpm==null) letturaCaPpm="";
      else letturaCaPpm=letturaCaPpm.replace('.',',');
      if (calcioPpm==null) calcioPpm="";
      else calcioPpm=calcioPpm.replace('.',',');
      if (calcio==null) calcio="";
      else calcio=calcio.replace('.',',');
      if (letturaMgPpm==null) letturaMgPpm="";
      else letturaMgPpm=letturaMgPpm.replace('.',',');
      if (magnesioPpm==null) magnesioPpm="";
      else magnesioPpm=magnesioPpm.replace('.',',');
      if (magnesio==null) magnesio="";
      else magnesio=magnesio.replace('.',',');
      if (letturaKPpm==null) letturaKPpm="";
      else letturaKPpm=letturaKPpm.replace('.',',');
      if (potassioPpm==null) potassioPpm="";
      else potassioPpm=potassioPpm.replace('.',',');
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
      templ.bset("volumeDiluizione",volumeDiluizione);
      templ.bset("letturaCaPpm",letturaCaPpm);
      templ.bset("calcioPpm",calcioPpm);
      templ.bset("calcio",calcio);
      templ.bset("letturaMgPpm",letturaMgPpm);
      templ.bset("magnesioPpm",magnesioPpm);
      templ.bset("magnesio",magnesio);
      templ.bset("letturaKPpm",letturaKPpm);
      templ.bset("potassioPpm",potassioPpm);
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
      templ.bset("pesoCampione","2,000");
      templ.bset("volumeDiluizione","200,000");
      templ.bset("diluizioneFe","100,000");
      templ.bset("diluizioneMn","100,000");
      templ.bset("diluizioneZn","100,000");
      templ.bset("diluizioneCu","100,000");
      templ.bset("diluizioneB","100,000");


    }
    if (select || (error && macroMicroElemIndFoglie.select()))
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
