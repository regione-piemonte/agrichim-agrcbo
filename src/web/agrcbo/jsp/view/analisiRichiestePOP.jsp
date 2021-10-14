<%@ page errorPage="/jsp/view/errorePopup.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*,java.util.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/analisiRichiestePOP.htm");
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
   String tipoMateriale = null;
   String cognomeProprietario = null;
   String nomeProprietario = null;
   String etichetta = null;
   String cognomeTecnico = null;
   String nomeTecnico = null;
   String organizzazione = null;
   String tipoOrganizzazione = null;
   String labConsegna = null;
   String statoPagamento = null;
   boolean tutti=false;
   String analisiFattura = null;
   String iuv = null;

   tipoOrganizzazione=request.getParameter("tipoOrganizzazione");
   if (tipoOrganizzazione!=null){
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
       tipoMateriale = request.getParameter("tipoMateriale");
       cognomeProprietario = request.getParameter("cognomeProprietario");
       nomeProprietario = request.getParameter("nomeProprietario");
       cognomeTecnico = request.getParameter("cognomeTecnico");
       nomeTecnico = request.getParameter("nomeTecnico");
       etichetta = request.getParameter("etichetta");
       organizzazione = request.getParameter("organizzazione");
       labConsegna = request.getParameter("labConsegna");
       String temp = request.getParameter("tutti");
       if (!"N".equals(temp)) tutti=true;
       statoPagamento = request.getParameter("statoPagamento");
       analisiFattura = request.getParameter("analisiFattura");	
       iuv = request.getParameter("iuv");	
   }else{
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
      tipoMateriale = beanRicerca.getTipoMateriale();
      cognomeProprietario = beanRicerca.getCognomeProprietario();
      nomeProprietario = beanRicerca.getNomeProprietario();
      cognomeTecnico = beanRicerca.getCognomeTecnico();
      nomeTecnico = beanRicerca.getNomeTecnico();
      etichetta = beanRicerca.getEtichetta();
      organizzazione = beanRicerca.getOrganizzazione();
      tipoOrganizzazione = beanRicerca.getTipoOrganizzazione();
      labConsegna = beanRicerca.getLabConsegna();
      statoPagamento = beanRicerca.getStatoPagamento();
      analisiFattura = beanRicerca.getAnalisiFattura();
      iuv = beanRicerca.getIuv();
      tutti=beanRicerca.isTutti();
   }
   if (idRichiestaDa== null) idRichiestaDa ="";
   if (idRichiestaA== null) idRichiestaA ="";
   if (dataDaGiorno== null) dataDaGiorno ="";
   if (dataDaMese== null) dataDaMese ="";
   if (dataDaAnno== null) dataDaAnno ="";
   if (dataAGiorno== null) dataAGiorno ="";
   if (dataAMese== null) dataAMese ="";
   if (dataAAnno== null) dataAAnno ="";
   if (tipoMateriale== null) tipoMateriale ="";
   if (cognomeProprietario== null) cognomeProprietario ="";
   if (nomeProprietario== null) nomeProprietario ="";
   if (cognomeTecnico== null) cognomeTecnico ="";
   if (nomeTecnico== null) nomeTecnico ="";
   if (etichetta== null) etichetta ="";
   if (organizzazione== null) organizzazione ="";
   if (tipoOrganizzazione== null) tipoOrganizzazione ="";
   if (labConsegna== null) labConsegna ="";
   if (statoPagamento== null) statoPagamento ="";
   if (iuv == null) iuv ="";

   templ.bset("idRichiestaDa",idRichiestaDa);
   templ.bset("idRichiestaA",idRichiestaA);
   templ.bset("dataDaGiorno",dataDaGiorno);
   templ.bset("dataDaMese",dataDaMese);
   templ.bset("dataDaAnno",dataDaAnno);
   templ.bset("dataAGiorno",dataAGiorno);
   templ.bset("dataAMese",dataAMese);
   templ.bset("dataAAnno",dataAAnno);
   templ.bset("cognomeProprietario",cognomeProprietario);
   templ.bset("nomeProprietario",nomeProprietario);
   templ.bset("cognomeTecnico",cognomeTecnico);
   templ.bset("nomeTecnico",nomeTecnico);
   templ.bset("etichetta",etichetta);

  /**
    Carico i dati di tutti i materiali per visualizzarli nella combo
   */
   String codStr[],descStr[];
   codStr=beanTipoCampione.getCodMateriale();
   descStr=beanTipoCampione.getDescMateriale();
   tipoMateriale = (tipoMateriale == null || "".equals(tipoMateriale)) ? Constants.MATERIALE.CODICE_MATERIALE_TERRENI : tipoMateriale;
   for(int i=0;i<codStr.length;i++){
     if (tipoMateriale.equals(codStr[i])) {
       templ.set("tipoMateriale.selected", "selected");
     } else {
       templ.set("tipoMateriale.selected", "");
     }
     templ.set("tipoMateriale.codiceMateriale",codStr[i]);
     templ.set("tipoMateriale.descrizione",descStr[i]);
   }

   /**
    Carico i dati di tutti i laboratori per visualizzarli nella combo
   */
   codStr=beanTipoCampione.getCodLaboratorio();
   descStr=beanTipoCampione.getDescLaboratorio();
   for(int i=0;i<codStr.length;i++){
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
    for(int i=0;i<size;i++){
      templ.newBlock("organizzazione");
      if (organizzazione.equals(cod.get(i)))
        templ.set("organizzazione.selected","selected");
      else
        templ.set("organizzazione.selected","");
      templ.set("organizzazione.cod",(String)cod.get(i));
      templ.set("organizzazione.desc",(String)desc.get(i));
    }
  }
  if (tutti)
    templ.bset("tutti","checked");
  else
    templ.bset("scartatiEmessi","checked");
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

