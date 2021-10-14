<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/gestioneFatture.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/controllaUtente.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="beanRicerca"
     scope="session"
     class="it.csi.agrc.BeanRicerca"/>

<jsp:useBean
     id="fatture"
     scope="page"
     class="it.csi.agrc.Fatture">
    <%
      fatture.setDataSource(dataSource);
      fatture.setAut(aut);
      fatture.setPasso(beanParametriApplication.getMaxRecordXPagina());
    %>
</jsp:useBean>

<%
  /**
   * Il codice seguente serve per gestire la parte superiore della
   * pagina: quella relativa alla ricerca
   */

  String annoFattura = null;
  String numeroFattura= null;
  String dataDaGiorno = null;
  String dataDaMese = null;
  String dataDaAnno = null;
  String dataAGiorno = null;
  String dataAMese = null;
  String dataAAnno = null;
  String numeroRichiesta= null;
  String annoCampione= null;
  String numeroCampione= null;
  String codFisPIVA= null;
  String statoPagamento= null;
  String statoFattura= null;

 /**
  * Leggo gli eventuali parametri dal file chiamante. Se sono valorizzati
  * siginifica che è già stata efettuata una ricerca e che devo rimpostare
  * i parametri scelti
  **/
  annoFattura=beanRicerca.getAnnoFattura();
  numeroFattura=beanRicerca.getNumeroFattura();
  dataDaGiorno = beanRicerca.getDataDaGiorno();
  dataDaMese = beanRicerca.getDataDaMese();
  dataDaAnno = beanRicerca.getDataDaAnno();
  dataAGiorno = beanRicerca.getDataAGiorno();
  dataAMese = beanRicerca.getDataAMese();
  dataAAnno = beanRicerca.getDataAAnno();
  numeroRichiesta=beanRicerca.getNumeroRichiesta();
  annoCampione=beanRicerca.getAnnoCampione();
  numeroCampione=beanRicerca.getNumeroCampione();
  codFisPIVA=beanRicerca.getCodFisPIVA();
  statoPagamento=beanRicerca.getStatoPagamento();
  statoFattura=beanRicerca.getStatoFattura();

  if (annoFattura== null) annoFattura ="";
  if (numeroFattura== null) numeroFattura ="";
  if (dataDaGiorno== null) dataDaGiorno ="";
  if (dataDaMese== null) dataDaMese ="";
  if (dataDaAnno== null) dataDaAnno ="";
  if (dataAGiorno== null) dataAGiorno ="";
  if (dataAMese== null) dataAMese ="";
  if (dataAAnno== null) dataAAnno ="";
  if (numeroRichiesta== null) numeroRichiesta ="";
  if (annoCampione== null) annoCampione ="";
  if (numeroCampione== null) numeroCampione ="";
  if (codFisPIVA== null) codFisPIVA ="";
  if (statoPagamento== null) statoPagamento ="";
  if (statoFattura== null) statoFattura ="";

  templ.bset("annoFattura",annoFattura);
  templ.bset("numeroFattura",numeroFattura);
  templ.bset("dataDaGiorno",dataDaGiorno);
  templ.bset("dataDaMese",dataDaMese);
  templ.bset("dataDaAnno",dataDaAnno);
  templ.bset("dataAGiorno",dataAGiorno);
  templ.bset("dataAMese",dataAMese);
  templ.bset("dataAAnno",dataAAnno);
  templ.bset("numeroRichiesta",numeroRichiesta);
  templ.bset("annoCampione",annoCampione);
  templ.bset("numeroCampione",numeroCampione);
  templ.bset("codFisPIVA",codFisPIVA);

  if ("S".equals(statoFattura))
    templ.bset("selectedAnnullata","selected");
  if ("N".equals(statoFattura))
    templ.bset("selectedValida","selected");
  if ("".equals(statoFattura))
    templ.bset("selectedDefaultStato","selected");

  if ("S".equals(statoPagamento))
    templ.bset("selectedPagata","selected");
  if ("N".equals(statoPagamento))
    templ.bset("selectedDaPagare","selected");
  if ("G".equals(statoPagamento))
    templ.bset("selectedDaPagare","selected");
  if ("".equals(statoPagamento))
    templ.bset("selectedDefaultPagamento","selected");




  /**
   * La porzione di codice seguente viene utilizzata per gestire la
   * visualizzazione dei record ricerca e la loro paginazione
   **/
    int attuale,passo;
    try
    {
      String temp=request.getParameter("attuale");
      if (temp!=null) attuale=Integer.parseInt(temp);
      else attuale=1;
    }
    catch(Exception eNum)
    {
      attuale=1;
    }
   /**
    * La porzione di codice seguente permette di gestire una visione dei
    * record selezionati simile al motore di ricerca
    */
    fatture.setBaseElementi(attuale);
    fatture.fillFatture();
    int size;
    size=fatture.size();

    if ( size>0 )
    {
      passo=fatture.getPasso();
      templ.newBlock("elencoFatture");
      templ.set("elencoFatture.num",""+fatture.getNumRecord());
      if (attuale!=1)
      {
        templ.newBlock("indietro");
        templ.set("elencoFatture.indietro.attuale",""+(attuale-passo));
      }
      else
         templ.set("elencoFatture.nonIndietro.nonIndietro","");

      if ((attuale+passo) <=fatture.getNumRecord())
      {
        templ.newBlock("avanti");
        templ.set("elencoFatture.avanti.attuale",""+(attuale+passo));
      }
      else
         templ.set("elencoFatture.nonAvanti.nonAvanti","");
       if (size!=fatture.getNumRecord())
       {
         int numPag=((fatture.getNumRecord()-1)/passo)+1;
         int pagAtt=(attuale/passo)+1;
         templ.set("elencoFatture.pagine","Pagina "+pagAtt+"/"+numPag);
       }
       Fattura fatt;
       String pagata=null,annullata=null;
       for(int i=0;i<size;i++)
       {
         templ.newBlock("elencoFatturaBody");
         fatt=fatture.get(i);
         if ("S".equals(fatt.getPagata())) pagata="Si";
         if ("N".equals(fatt.getPagata())) pagata="No";
         if ("P".equals(fatt.getPagata())) pagata="Parziale";

         if ("S".equals(fatt.getAnnullata())) annullata="Annullata";
         else annullata="";

         if (i==0)
         {
           templ.newBlock("elencoSi");
           templ.set("elencoSi.annoPrimo",fatt.getAnno());
           templ.set("elencoSi.fatturaPrimo",fatt.getNumeroFattura());
           templ.set("elencoSi.dataFatturaPrimo",fatt.getDataFattura());
           templ.set("elencoSi.annullataPrimo",annullata);
           templ.set("elencoSi.pagataPrimo",pagata);
           templ.set("elencoFatture.elencoFatturaBody.checked","checked");
         }
         templ.set("elencoFatture.elencoFatturaBody.anno",fatt.getAnno());
         templ.set("elencoFatture.elencoFatturaBody.numero",fatt.getNumeroFattura());
         templ.set("elencoFatture.elencoFatturaBody.campioni",fatt.getCampioni());
         templ.set("elencoFatture.elencoFatturaBody.dataFattura",fatt.getDataFattura());
         templ.set("elencoFatture.elencoFatturaBody.pagata",pagata);
         templ.set("elencoFatture.elencoFatturaBody.annullata",annullata);
         templ.set("elencoFatture.elencoFatturaBody.partitaIvaOCf",fatt.getPartitaIvaOCf());
         templ.set("elencoFatture.elencoFatturaBody.ragioneSociale",fatt.getRagioneSociale());
       }
   }
   else
   {
      templ.newBlock("elencoFattureNo");
   }
 %>

 <%-- HTMPL genera ora la pagina web e si finalizza così lo stato stazionario --%>

 <%= templ.text() %>
