<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/stampaFatture.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/controllaUtente.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="beanRicerca"
     scope="session"
     class="it.csi.agrc.BeanRicerca"/>

<%
  /**
   * Il codice seguente serve per gestire la parte superiore della
   * pagina: quella relativa alla ricerca
   */

  String dataDaGiorno = null;
  String dataDaMese = null;
  String dataDaAnno = null;
  String dataAGiorno = null;
  String dataAMese = null;
  String dataAAnno = null;
  String annoFatturaDa = null;
  String numeroFatturaDa= null;
  String annoFatturaA = null;
  String numeroFatturaA = null;
  String codFisPIVA= null;
  String statoPagamento= null;
  String statoFattura= null;

 /**
  * Leggo gli eventuali parametri dal file chiamante. Se sono valorizzati
  * siginifica che è già stata efettuata una ricerca e che devo rimpostare
  * i parametri scelti
  **/
  dataDaGiorno = beanRicerca.getDataDaGiorno();
  dataDaMese = beanRicerca.getDataDaMese();
  dataDaAnno = beanRicerca.getDataDaAnno();
  dataAGiorno = beanRicerca.getDataAGiorno();
  dataAMese = beanRicerca.getDataAMese();
  dataAAnno = beanRicerca.getDataAAnno();
  annoFatturaA=beanRicerca.getAnnoFatturaA();
  numeroFatturaA=beanRicerca.getNumeroFatturaA();
  annoFatturaDa=beanRicerca.getAnnoFattura();
  numeroFatturaDa=beanRicerca.getNumeroFattura();
  codFisPIVA=beanRicerca.getCodFisPIVA();
  statoPagamento=beanRicerca.getStatoPagamento();
  statoFattura=beanRicerca.getStatoFattura();

  if (dataDaGiorno== null) dataDaGiorno ="";
  if (dataDaMese== null) dataDaMese ="";
  if (dataDaAnno== null) dataDaAnno ="";
  if (dataAGiorno== null) dataAGiorno ="";
  if (dataAMese== null) dataAMese ="";
  if (dataAAnno== null) dataAAnno ="";
  if (annoFatturaA== null) annoFatturaA ="";
  if (numeroFatturaA== null) numeroFatturaA ="";
  if (annoFatturaDa== null) annoFatturaDa ="";
  if (numeroFatturaDa== null) numeroFatturaDa ="";
  if (codFisPIVA== null) codFisPIVA ="";
  if (statoPagamento== null) statoPagamento ="";
  if (statoFattura== null) statoFattura ="";

  templ.bset("dataDaGiorno",dataDaGiorno);
  templ.bset("dataDaMese",dataDaMese);
  templ.bset("dataDaAnno",dataDaAnno);
  templ.bset("dataAGiorno",dataAGiorno);
  templ.bset("dataAMese",dataAMese);
  templ.bset("dataAAnno",dataAAnno);
  templ.bset("annoFatturaA",annoFatturaA);
  templ.bset("numeroFatturaA",numeroFatturaA);
  templ.bset("annoFatturaDa",annoFatturaDa);
  templ.bset("numeroFatturaDa",numeroFatturaDa);
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
 %>

 <%-- HTMPL genera ora la pagina web e si finalizza così lo stato stazionario --%>

 <%= templ.text() %>
