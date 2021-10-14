<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/complessoScambio.htm");
%>

<%
  boolean ferro=false,manganese=false,zinco=false,rame=false,boro=false;
  boolean calcio=false,magnesio=false,potassio=false,csc=false,vBACl2=false;
  boolean richiestaAnalisi[]=new boolean[12];
  boolean fra4=false,fra5=false,std=false;

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
     id="complessoScambio"
     scope="request"
     class="it.csi.agrc.ComplessoScambio">
     <%
      complessoScambio.setDataSource(dataSource);
      complessoScambio.setAut(aut);
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
  if (!richiestaAnalisi[Analisi.TER_COMPLESSO_SCAMBIO])
  {
     /**
     * Non è stata scelta nessuna analisi, quindi devo solo visualizzare un
     * messaggio di avvertimento
     */
    templ.newBlock("nessuno");
  }
  else
  {
    if (calcio || magnesio || potassio) vBACl2=true;
    templ.newBlock("analisi");
    if (vBACl2)
    {
      templ.newBlock("analisi.analisvBACl2Blocco");
      templ.bset("analisivBACl2","true");
    }
    else templ.bset("analisivBACl2","false");
    if (calcio)
    {
      templ.newBlock("analisi.analisCalcioBlocco");
      templ.bset("analisiCalcio","true");
    }
    else templ.bset("analisiCalcio","false");
    if (magnesio)
    {
      templ.newBlock("analisi.analisMagnesioBlocco");
      templ.bset("analisiMagnesio","true");
    }
    else templ.bset("analisiMagnesio","false");
    if (potassio)
    {
      templ.newBlock("analisi.analisPotassioBlocco");
      templ.bset("analisiPotassio","true");
    }
    else templ.bset("analisiPotassio","false");
    if (csc)
    {
      templ.newBlock("analisi.analisCscBlocco");
      templ.bset("analisiCsc","true");
    }
    else templ.bset("analisiCsc","false");

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
    complessoScambio.setIdRichiesta(aut.getIdRichiestaCorrente());
    String pesoSeccoProvetta;
    String pesoSeccoAcquaProvetta;
    String pesoTerreno;
    String letturaMagnesioEdta;
    String letturaBiancoEdta;
    String capacitaScambioCationico;
    String letturaCalcio;
    String letturaMagnesio;
    String letturaPotassio;
    String letturaSodio;
    String vbacl2PerEstrazione;
    String diluizioneCalcio;
    String diluizioneMagnesio;
    String diluizionePotassio;
    String diluizioneSodio;
    String calcioScambiabile;
    String magnesioScambiabile;
    String potassioScambiabile;
    String sodioScambiabile;
    String calcioScambiabileMeq100;
    String magnesioScambiabileMeq100;
    String potassioScambiabileMeq100;
    String sodioScambiabileMeq100;
    String calcioScambiabileCsc;
    String magnesioScambiabileCsc;
    String potassioScambiabileCsc;
    String sodioScambiabileCsc;
    String saturazioneBasica;
    String caMg;
    String caK;
    String mgK;

    /**
    * Se sono arrivato qui a seguito di un errore non devo leggere i parametri
    * dal database ma utilizzare quelli che sono nel bean e arrivano dalla
    * request
    */
    if (!error) select=complessoScambio.select();

    templ.bset("idRichiestaCorrente",complessoScambio.getIdRichiesta()+"");

    /**
     * Se sono qui in seguito ad un errore oppure ho trovato un record nel
     * database devo impostare i campi
     * */
    if (select || error)
    {
      pesoSeccoProvetta=complessoScambio.getPesoSeccoProvetta();
      pesoSeccoAcquaProvetta=complessoScambio.getPesoSeccoAcquaProvetta();
      pesoTerreno=complessoScambio.getPesoTerreno();
      letturaMagnesioEdta=complessoScambio.getLetturaMagnesioEdta();
      letturaBiancoEdta=complessoScambio.getLetturaBiancoEdta();
      capacitaScambioCationico=complessoScambio.getCapacitaScambioCationico();
      letturaCalcio=complessoScambio.getLetturaCalcio();
      letturaMagnesio=complessoScambio.getLetturaMagnesio();
      letturaPotassio=complessoScambio.getLetturaPotassio();
      letturaSodio=complessoScambio.getLetturaSodio();
      vbacl2PerEstrazione=complessoScambio.getVbacl2PerEstrazione();
      diluizioneCalcio=complessoScambio.getDiluizioneCalcio();
      diluizioneMagnesio=complessoScambio.getDiluizioneMagnesio();
      diluizionePotassio=complessoScambio.getDiluizionePotassio();
      diluizioneSodio=complessoScambio.getDiluizioneSodio();
      calcioScambiabile=complessoScambio.getCalcioScambiabile();
      magnesioScambiabile=complessoScambio.getMagnesioScambiabile();
      potassioScambiabile=complessoScambio.getPotassioScambiabile();
      sodioScambiabile=complessoScambio.getSodioScambiabile();
      calcioScambiabileMeq100=complessoScambio.getCalcioScambiabileMeq100();
      magnesioScambiabileMeq100=complessoScambio.getMagnesioScambiabileMeq100();
      potassioScambiabileMeq100=complessoScambio.getPotassioScambiabileMeq100();
      sodioScambiabileMeq100=complessoScambio.getSodioScambiabileMeq100();
      calcioScambiabileCsc=complessoScambio.getCalcioScambiabileCsc();
      magnesioScambiabileCsc=complessoScambio.getMagnesioScambiabileCsc();
      potassioScambiabileCsc=complessoScambio.getPotassioScambiabileCsc();
      sodioScambiabileCsc=complessoScambio.getSodioScambiabileCsc();
      saturazioneBasica=complessoScambio.getSaturazioneBasica();
      caMg=complessoScambio.getCaMg();
      caK=complessoScambio.getCaK();
      mgK=complessoScambio.getMgK();

      /**
      * Devo sostiruiire per ogni campo reale il punto decimale con la virgola
      */
      if (pesoSeccoProvetta==null) pesoSeccoProvetta="";
      else pesoSeccoProvetta=pesoSeccoProvetta.replace('.',',');
      if (pesoSeccoAcquaProvetta==null) pesoSeccoAcquaProvetta="";
      else pesoSeccoAcquaProvetta=pesoSeccoAcquaProvetta.replace('.',',');
      if (pesoTerreno==null) pesoTerreno="";
      else pesoTerreno=pesoTerreno.replace('.',',');
      if (letturaMagnesioEdta==null) letturaMagnesioEdta="";
      else letturaMagnesioEdta=letturaMagnesioEdta.replace('.',',');
      if (letturaBiancoEdta==null) letturaBiancoEdta="";
      else letturaBiancoEdta=letturaBiancoEdta.replace('.',',');
      if (capacitaScambioCationico==null) capacitaScambioCationico="";
      else capacitaScambioCationico=capacitaScambioCationico.replace('.',',');
      if (letturaCalcio==null) letturaCalcio="";
      else letturaCalcio=letturaCalcio.replace('.',',');
      if (letturaMagnesio==null) letturaMagnesio="";
      else letturaMagnesio=letturaMagnesio.replace('.',',');
      if (letturaPotassio==null) letturaPotassio="";
      else letturaPotassio=letturaPotassio.replace('.',',');
      if (letturaSodio==null) letturaSodio="";
      else letturaSodio=letturaSodio.replace('.',',');

      if (vbacl2PerEstrazione==null) vbacl2PerEstrazione="";
      else vbacl2PerEstrazione=vbacl2PerEstrazione.replace('.',',');
      if (diluizioneCalcio==null) diluizioneCalcio="";
      else diluizioneCalcio=diluizioneCalcio.replace('.',',');
      if (diluizioneMagnesio==null) diluizioneMagnesio="";
      else diluizioneMagnesio=diluizioneMagnesio.replace('.',',');
      if (diluizionePotassio==null) diluizionePotassio="";
      else diluizionePotassio=diluizionePotassio.replace('.',',');
      if (diluizioneSodio==null) diluizioneSodio="";
      else diluizioneSodio=diluizioneSodio.replace('.',',');
      if (calcioScambiabile==null) calcioScambiabile="";
      else calcioScambiabile=calcioScambiabile.replace('.',',');
      if (magnesioScambiabile==null) magnesioScambiabile="";
      else magnesioScambiabile=magnesioScambiabile.replace('.',',');
      if (potassioScambiabile==null) potassioScambiabile="";
      else potassioScambiabile=potassioScambiabile.replace('.',',');
      if (sodioScambiabile==null) sodioScambiabile="";
      else sodioScambiabile=sodioScambiabile.replace('.',',');
      if (calcioScambiabileMeq100==null) calcioScambiabileMeq100="";
      else calcioScambiabileMeq100=calcioScambiabileMeq100.replace('.',',');
      if (magnesioScambiabileMeq100==null) magnesioScambiabileMeq100="";
      else magnesioScambiabileMeq100=magnesioScambiabileMeq100.replace('.',',');
      if (potassioScambiabileMeq100==null) potassioScambiabileMeq100="";
      else potassioScambiabileMeq100=potassioScambiabileMeq100.replace('.',',');
      if (sodioScambiabileMeq100==null) sodioScambiabileMeq100="";
      else sodioScambiabileMeq100=sodioScambiabileMeq100.replace('.',',');
      if (calcioScambiabileCsc==null) calcioScambiabileCsc="";
      else calcioScambiabileCsc=calcioScambiabileCsc.replace('.',',');
      if (magnesioScambiabileCsc==null) magnesioScambiabileCsc="";
      else magnesioScambiabileCsc=magnesioScambiabileCsc.replace('.',',');
      if (potassioScambiabileCsc==null) potassioScambiabileCsc="";
      else potassioScambiabileCsc=potassioScambiabileCsc.replace('.',',');
      if (sodioScambiabileCsc==null) sodioScambiabileCsc="";
      else sodioScambiabileCsc=sodioScambiabileCsc.replace('.',',');
      if (saturazioneBasica==null) saturazioneBasica="";
      else saturazioneBasica=saturazioneBasica.replace('.',',');
      if (caMg==null) caMg="";
      else caMg=caMg.replace('.',',');
      if (caK==null) caK="";
      else caK=caK.replace('.',',');
      if (mgK==null) mgK="";
      else mgK=mgK.replace('.',',');

      templ.bset("pesoSeccoProvetta",pesoSeccoProvetta);
      templ.bset("pesoSeccoAcquaProvetta",pesoSeccoAcquaProvetta);
      templ.bset("pesoTerreno",pesoTerreno);
      templ.bset("letturaMagnesioEdta",letturaMagnesioEdta);
      templ.bset("letturaBiancoEdta",letturaBiancoEdta);
      templ.bset("capacitaScambioCationico",capacitaScambioCationico);
      templ.bset("letturaCalcio",letturaCalcio);
      templ.bset("letturaMagnesio",letturaMagnesio);
      templ.bset("letturaPotassio",letturaPotassio);
      templ.bset("letturaSodio",letturaSodio);
      templ.bset("diluizioneCalcio",diluizioneCalcio);
      templ.bset("diluizioneMagnesio",diluizioneMagnesio);
      templ.bset("diluizionePotassio",diluizionePotassio);
      templ.bset("diluizioneSodio",diluizioneSodio);
      templ.bset("calcioScambiabile",calcioScambiabile);
      templ.bset("magnesioScambiabile",magnesioScambiabile);
      templ.bset("potassioScambiabile",potassioScambiabile);
      templ.bset("sodioScambiabile",sodioScambiabile);
      templ.bset("calcioScambiabileMeq100",calcioScambiabileMeq100);
      templ.bset("magnesioScambiabileMeq100",magnesioScambiabileMeq100);
      templ.bset("potassioScambiabileMeq100",potassioScambiabileMeq100);
      templ.bset("sodioScambiabileMeq100",sodioScambiabileMeq100);
      templ.bset("calcioScambiabileCsc",calcioScambiabileCsc);
      templ.bset("magnesioScambiabileCsc",magnesioScambiabileCsc);
      templ.bset("potassioScambiabileCsc",potassioScambiabileCsc);
      templ.bset("sodioScambiabileCsc",sodioScambiabileCsc);
      templ.bset("saturazioneBasica",saturazioneBasica);
      templ.bset("caMg",caMg);
      templ.bset("caK",caK);
      templ.bset("mgK",mgK);
      templ.bset("vbacl2PerEstrazione",vbacl2PerEstrazione);
    }
    else
    {
      /* @@TODO */
      /* FORSE QUESTI PARAMETRI VANNO PERDUTI SE NEL FORM NON VENGONO VISUALIZZATI? */
      templ.bset("pesoTerreno","2,000");
     //scommentati jira 141 Elisa
      templ.bset("diluizioneCalcio","50");
     templ.bset("diluizioneMagnesio","50");
     templ.bset("diluizionePotassio","50");
      templ.bset("vbacl2PerEstrazione","25");
    }
    if (select || (error && complessoScambio.select()))
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
