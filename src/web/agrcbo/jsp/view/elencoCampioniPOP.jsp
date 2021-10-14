<%@ page errorPage="/jsp/view/errorePopup.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*,java.util.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/elencoCampioniPOP.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="beanTipoCampione"
     scope="application"
     class="it.csi.agrc.BeanTipoCampione"/>

<jsp:useBean
     id="beanOrgTecnico"
     scope="application"
     class="it.csi.agrc.BeanOrganizzazioniTecnico"/>

<jsp:useBean
     id="beanRicerca"
     scope="session"
     class="it.csi.agrc.BeanRicerca"/>

<jsp:useBean
     id="organizzazioniTecnico"
     scope="page"
     class="it.csi.agrc.OrganizzazioniTecnico">
<%
  organizzazioniTecnico.setDataSource(dataSource);
  organizzazioniTecnico.setAut(aut);
%>
</jsp:useBean>


<%
   String idRichiestaDa = null;
   String idRichiestaA = null;
   
   String dataDaGiorno = null;
   String dataDaMese = null;
   String dataDaAnno = null;
   String dataAGiorno = null;
   String dataAMese = null;
   String dataAAnno = null;
   
   String dataDaGiornoPag = null;
   String dataDaMesePag = null;
   String dataDaAnnoPag = null;
   String dataAGiornoPag = null;
   String dataAMesePag = null;
   String dataAAnnoPag = null;
   
   String tipoMateriale = null;
   String cognomeProprietario = null;
   String nomeProprietario = null;
   String etichetta = null;
   String cognomeTecnico = null;
   String nomeTecnico = null;
   String annoCampione = null;
   String numeroCampioneDa = null;
   String numeroCampioneA = null;
   String organizzazione = null;
   String tipoOrganizzazione = null;
   String labConsegna = null;
   String stato = null;
   String statoPagamento = null;
   String analisiFattura = null;
   String iuv = null;

   tipoOrganizzazione=request.getParameter("tipoOrganizzazione");
   if (tipoOrganizzazione!=null) {
     /**
     * Sono in questa pagina perché è stata ricaricata la combo dei tipi
     * di organizzazione, quindi devo leggere tutti i parametri passati
     */
       idRichiestaDa = request.getParameter("idRichiestaDa");
       idRichiestaA = request.getParameter("idRichiestaA");
       
       dataDaGiorno = request.getParameter("dataDaGiorno");
       dataDaMese = request.getParameter("dataDaMese");
       dataDaAnno = request.getParameter("dataDaAnno");
       dataAGiorno = request.getParameter("dataAGiorno");
       dataAMese = request.getParameter("dataAMese");
       dataAAnno = request.getParameter("dataAAnno");
       
       dataDaGiornoPag = request.getParameter("dataDaGiornoPag");
       dataDaMesePag = request.getParameter("dataDaMesePag");
       dataDaAnnoPag = request.getParameter("dataDaAnnoPag");
       dataAGiornoPag = request.getParameter("dataAGiornoPag");
       dataAMesePag = request.getParameter("dataAMesePag");
       dataAAnnoPag = request.getParameter("dataAAnnoPag");
       
       tipoMateriale = request.getParameter("tipoMateriale");
       cognomeProprietario = request.getParameter("cognomeProprietario");
       nomeProprietario = request.getParameter("nomeProprietario");
       cognomeTecnico = request.getParameter("cognomeTecnico");
       nomeTecnico = request.getParameter("nomeTecnico");
       etichetta = request.getParameter("etichetta");
       annoCampione = request.getParameter("annoCampione");
       numeroCampioneDa = request.getParameter("numeroCampioneDa");
       numeroCampioneA = request.getParameter("numeroCampioneA");
       organizzazione = request.getParameter("organizzazione");
       labConsegna = request.getParameter("labConsegna");
       stato = request.getParameter("stato");
       statoPagamento = request.getParameter("statoPagamento");
       analisiFattura = request.getParameter("analisiFattura");	
       iuv = request.getParameter("iuv");	
   } else {
  /**
   * Leggo gli eventuali parametri dal file chiamante. Se sono valorizzati
   * siginifica che è già stata efettuata una ricerca e che devo rimpostare
   * i parametri scelti
   **/
      idRichiestaDa = beanRicerca.getIdRichiestaDa();
      idRichiestaA = beanRicerca.getIdRichiestaA();
      
      dataDaGiorno = beanRicerca.getDataDaGiorno();
      dataDaMese = beanRicerca.getDataDaMese();
      dataDaAnno = beanRicerca.getDataDaAnno();
      dataAGiorno = beanRicerca.getDataAGiorno();
      dataAMese = beanRicerca.getDataAMese();
      dataAAnno = beanRicerca.getDataAAnno();
      
      dataDaGiornoPag = beanRicerca.getDataDaGiornoPag();
      dataDaMesePag = beanRicerca.getDataDaMesePag();
      dataDaAnnoPag = beanRicerca.getDataDaAnnoPag();
      dataAGiornoPag = beanRicerca.getDataAGiornoPag();
      dataAMesePag = beanRicerca.getDataAMesePag();
      dataAAnnoPag = beanRicerca.getDataAAnnoPag();
      
      tipoMateriale = beanRicerca.getTipoMateriale();
      cognomeProprietario = beanRicerca.getCognomeProprietario();
      nomeProprietario = beanRicerca.getNomeProprietario();
      cognomeTecnico = beanRicerca.getCognomeTecnico();
      nomeTecnico = beanRicerca.getNomeTecnico();
      etichetta = beanRicerca.getEtichetta();
      annoCampione = beanRicerca.getAnnoCampione();
      numeroCampioneDa = beanRicerca.getNumeroCampioneDa();
      numeroCampioneA = beanRicerca.getNumeroCampioneA();
      organizzazione = beanRicerca.getOrganizzazione();
      tipoOrganizzazione = beanRicerca.getTipoOrganizzazione();
      labConsegna = beanRicerca.getLabConsegna();
      stato = beanRicerca.getStato();
      statoPagamento = beanRicerca.getStatoPagamento();
      analisiFattura = beanRicerca.getAnalisiFattura();
      iuv = beanRicerca.getIuv();
   }
   if (idRichiestaDa== null) idRichiestaDa ="";
   if (idRichiestaA== null) idRichiestaA ="";
   
   if (dataDaGiorno== null) dataDaGiorno ="";
   if (dataDaMese== null) dataDaMese ="";
   if (dataDaAnno== null) dataDaAnno ="";
   if (dataAGiorno== null) dataAGiorno ="";
   if (dataAMese== null) dataAMese ="";
   if (dataAAnno== null) dataAAnno ="";
   
   if (dataDaGiornoPag== null) dataDaGiornoPag ="";
   if (dataDaMesePag== null) dataDaMesePag ="";
   if (dataDaAnnoPag== null) dataDaAnnoPag ="";
   if (dataAGiornoPag== null) dataAGiornoPag ="";
   if (dataAMesePag== null) dataAMesePag ="";
   if (dataAAnnoPag== null) dataAAnnoPag ="";
   
   if (tipoMateriale== null) tipoMateriale ="";
   if (cognomeProprietario== null) cognomeProprietario ="";
   if (nomeProprietario== null) nomeProprietario ="";
   if (cognomeTecnico== null) cognomeTecnico ="";
   if (nomeTecnico== null) nomeTecnico ="";
   if (etichetta== null) etichetta ="";
   if (annoCampione == null) annoCampione ="";
   if (numeroCampioneDa== null) numeroCampioneDa ="";
   if (numeroCampioneA== null) numeroCampioneA ="";
   if (organizzazione== null) organizzazione ="";
   if (tipoOrganizzazione== null) tipoOrganizzazione ="";
   if (labConsegna== null) labConsegna ="";
   if (stato== null) stato ="";
   if (statoPagamento== null) statoPagamento ="";
   if (iuv== null) iuv ="";

   templ.bset("idRichiestaDa",idRichiestaDa);
   templ.bset("idRichiestaA",idRichiestaA);
   
   templ.bset("dataDaGiorno",dataDaGiorno);
   templ.bset("dataDaMese",dataDaMese);
   templ.bset("dataDaAnno",dataDaAnno);
   templ.bset("dataAGiorno",dataAGiorno);
   templ.bset("dataAMese",dataAMese);
   templ.bset("dataAAnno",dataAAnno);
   
   templ.bset("dataDaGiornoPag",dataDaGiornoPag);
   templ.bset("dataDaMesePag",dataDaMesePag);
   templ.bset("dataDaAnnoPag",dataDaAnnoPag);
   templ.bset("dataAGiornoPag",dataAGiornoPag);
   templ.bset("dataAMesePag",dataAMesePag);
   templ.bset("dataAAnnoPag",dataAAnnoPag);
   
   templ.bset("cognomeProprietario",cognomeProprietario);
   templ.bset("nomeProprietario",nomeProprietario);
   templ.bset("cognomeTecnico",cognomeTecnico);
   templ.bset("nomeTecnico",nomeTecnico);
   templ.bset("etichetta",etichetta);
   templ.bset("annoCampione",annoCampione);
   templ.bset("numeroCampioneDa",numeroCampioneDa);
   templ.bset("numeroCampioneA",numeroCampioneA);

  /**
    Carico i dati di tutti i materiali per visualizzarli nella combo
   */
   String codStr[],descStr[];
   codStr=beanTipoCampione.getCodMateriale();
   descStr=beanTipoCampione.getDescMateriale();
   tipoMateriale = (tipoMateriale == null || "".equals(tipoMateriale)) ? Constants.MATERIALE.CODICE_MATERIALE_TERRENI : tipoMateriale;
   for(int i=0;i<codStr.length;i++) {
     if (tipoMateriale.equals(codStr[i]))
       templ.set("tipoMateriale.selected", "selected");
     else
       templ.set("tipoMateriale.selected", "");
     templ.set("tipoMateriale.codiceMateriale",codStr[i]);
     templ.set("tipoMateriale.descrizione",descStr[i]);
   }

   /**
    Carico i dati di tutti i laboratori per visualizzarli nella combo
   */
   codStr=beanTipoCampione.getCodLaboratorio();
   descStr=beanTipoCampione.getDescLaboratorio();
   for(int i=0;i<codStr.length;i++) {
     if (labConsegna.equals(codStr[i]))
       templ.set("laboratorio.selected","selected");
     else
       templ.set("laboratorio.selected","");
     templ.set("laboratorio.codice",codStr[i]);
     templ.set("laboratorio.descrizione",descStr[i]);
   }

   /**
   * Carico la combo dei tipi di organizzazione
   **/
  codStr=beanOrgTecnico.getCod();
  descStr=beanOrgTecnico.getDesc();
  for(int i=0;i<codStr.length;i++){
    templ.newBlock("tipoOrganizzazione");
    if (tipoOrganizzazione.equals(codStr[i]))
      templ.set("tipoOrganizzazione.selected","selected");
    else
      templ.set("tipoOrganizzazione.selected","");
    templ.set("tipoOrganizzazione.cod",codStr[i]);
    templ.set("tipoOrganizzazione.desc",descStr[i]);
  }

  /**
   * Carico la combo delle organizzazioni se è già stato scelto un tipo
   * di organizzazione
   **/
  Vector cod=new Vector(),desc=new Vector();
  int size;
  if (!"".equals(tipoOrganizzazione)){
    cod.clear();
    desc.clear();
    organizzazioniTecnico.getOrganizzazione(cod,desc,tipoOrganizzazione);
    size=cod.size();
    for(int i=0;i<size;i++) {
      templ.newBlock("organizzazione");
      if (organizzazione.equals(cod.get(i)))
        templ.set("organizzazione.selected","selected");
      else
        templ.set("organizzazione.selected","");
      templ.set("organizzazione.cod",(String)cod.get(i));
      templ.set("organizzazione.desc",(String)desc.get(i));
    }
  }

  /**
    Carico gli stati in cui può trovarsi un'etichetta
   */
   codStr=beanTipoCampione.getCodStatoCampione();
   descStr=beanTipoCampione.getDescStatoCampione();
   for(int i=0;i<codStr.length;i++) {
     if (!"00".equals(codStr[i])) {
       if (stato.equals(codStr[i]))
         templ.set("stato.selected","selected");
       else
         templ.set("stato.selected","");
       templ.set("stato.codice",codStr[i]);
       templ.set("stato.descrizione",descStr[i]);
     }
   }
   templ.bset("iuv",iuv);
  if ("S".equals(statoPagamento))
    templ.bset("selectedPagata","selected");
  if ("N".equals(statoPagamento))
    templ.bset("selectedDaPagare","selected");
  if ("G".equals(statoPagamento))
    templ.bset("selectedGratuita","selected");
  
  if ("D".equals(analisiFattura))
    templ.bset("selectedDaEmettere","selected");
  if ("E".equals(analisiFattura))
    templ.bset("selectedEmessa","selected");
  if ("N".equals(analisiFattura))
    templ.bset("selectedNonRichiesta","selected");

%>

<%= templ.text() %>

