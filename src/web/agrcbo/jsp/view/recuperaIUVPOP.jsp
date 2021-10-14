<%@page import="java.math.BigDecimal"%>
<%@page import="it.csi.smrcomms.agripagopasrv.business.pagopa.ElencoIuv"%>
<%@page import="it.csi.smrcomms.agripagopasrv.business.pagopa.EsitoLeggiIuv"%>
<%@page import="it.csi.smrcomms.agripagopasrv.business.pagopa.ParametriLeggiIuv"%>
<%@page import="org.apache.cxf.transports.http.configuration.HTTPClientPolicy"%>
<%@page import="org.apache.cxf.frontend.ClientProxy"%>
<%@page import="org.apache.cxf.transport.http.HTTPConduit"%>
<%@page import="javax.xml.ws.BindingProvider"%>
<%@page import="java.net.URL"%>
<%@page import="it.csi.smrcomms.agripagopasrv.business.pagopa.PagoPAService"%>
<%@page import="javax.xml.namespace.QName"%>
<%@page import="java.net.URI"%>
<%@page import="it.csi.smrcomms.agripagopasrv.business.pagopa.PagoPAWS"%>
<%@ page errorPage="/jsp/view/errorePopup.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/recuperaIUVPOP.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>


<jsp:useBean
     id="etichettaCampione"
     scope="request"
     class="it.csi.agrc.EtichettaCampione">
    <%
      etichettaCampione.setDataSource(dataSource);
      etichettaCampione.setAut(aut);
    %>
</jsp:useBean>
<jsp:useBean
     id="datiFattura"
     scope="request"
     class="it.csi.agrc.DatiFattura">
    <%
    datiFattura.setDataSource(dataSource);
    datiFattura.setAut(aut);
    %>
</jsp:useBean>

<jsp:useBean
     id="pagamento"
     scope="request"
     class="it.csi.agrc.Pagamento">
    <%
    pagamento.setDataSource(dataSource);
    pagamento.setAut(aut);
    %>
</jsp:useBean>

<%
  String funzione = request.getParameter("funzione");
	final String THIS_METHOD = "[::recuperaIUVPOP.jsp]"; 
	CuneoLogger.debug("/view/recuperaIUVPOP.jsp",THIS_METHOD + " BEGIN.");

  if(funzione!=null && funzione.equals("confermaiuv"))
  {
	  CuneoLogger.debug("/view/recuperaIUVPOP.jsp"," Inizio funzione confermaiuv");
	  
	  String iuvSelezionato = request.getParameter("iuvSelezionato");
	  String idRichiesta = request.getParameter("idRichiesta");
	  
	  CuneoLogger.debug("/view/recuperaIUVPOP.jsp"," iuvSelezionato: "+iuvSelezionato); 
	  CuneoLogger.debug("/view/recuperaIUVPOP.jsp"," idRichiesta: "+idRichiesta);
	  
	  
	  if(idRichiesta!=null && idRichiesta.endsWith("A"))
		  idRichiesta = idRichiesta.substring(0, idRichiesta.length()-1);
	  
	  etichettaCampione.select(idRichiesta);
	  String dataInserimento = etichettaCampione.getDataInserimentoRichiesta();
	  etichettaCampione.ricevutaPagamento(idRichiesta);
	  pagamento = pagamento.selectMultiploCompleto(idRichiesta, null);
	  CuneoLogger.debug("/view/recuperaIUVPOP.jsp"," esito selectMultiploCompleto: "+(pagamento==null ? "null" : "ok")); 
	  pagamento.setDataSource(dataSource);
	  pagamento.setAut(aut);
	  String codiceFiscaleFinale = (pagamento.getPagatore_codiceFiscale()!=null && pagamento.getPagatore_codiceFiscale().trim().length()>0) ? pagamento.getPagatore_codiceFiscale() : pagamento.getPagatore_piva();
	  
	  ParametriLeggiIuv leggiIuv = new ParametriLeggiIuv();
	  leggiIuv.setCodiceFiscalePIVA(codiceFiscaleFinale);
	  leggiIuv.setIdApplicativo("58");
	  leggiIuv.setDataInizioSelezione(dataInserimento);
	  PagoPAWS ppaWS = getPagoPAWS(beanParametriApplication.getAgripagopaWSDL());
	  CuneoLogger.debug("/view/recuperaIUVPOP.jsp"," effettuo chiamata a ppaWS.leggiIuv: cf:"+pagamento.getCf()+" , dataInserimento:"+dataInserimento); 
	  EsitoLeggiIuv esitoLeggiIuv = ppaWS.leggiIuv(leggiIuv);
	  CuneoLogger.debug("/view/recuperaIUVPOP.jsp"," chiamata a ppaWS.leggiIuv terminata"); 
	  String msgEsito = "Non è stato trovato nessuno IUV da recuperare per la richiesta selezionata, operazione annullata.";
	  boolean trovatoIUV = false;
	 
	  if(esitoLeggiIuv!=null && esitoLeggiIuv.getElencoIuv()!=null && !esitoLeggiIuv.getElencoIuv().isEmpty())
	  {
		  for(ElencoIuv item: esitoLeggiIuv.getElencoIuv())
		  {
			  CuneoLogger.debug("/view/recuperaIUVPOP.jsp"," elaborazione IUV:"+item.getIuv()); 
			  if(item.getIuv().equals(iuvSelezionato)){
				  
				  try{
					  CuneoLogger.debug("/view/recuperaIUVPOP.jsp"," richiamo inserimentoRisultatoPagamentoIUV ");
				  pagamento.inserimentoRisultatoPagamentoIUV(idRichiesta, item.getTipoPagamento(), iuvSelezionato, codiceFiscaleFinale, "OK", 
						  item.getDataEsito().toGregorianCalendar().getTime(), item.getDataRichiestaPagamento().toGregorianCalendar().getTime());
				  CuneoLogger.debug("/view/recuperaIUVPOP.jsp"," chiamata a inserimentoRisultatoPagamentoIUV terminata");
				  msgEsito = "Operazione conclusa correttamente.";
				  }catch(Exception e){
					  msgEsito = "Non è stato trovato nessuno IUV da recuperare per la richiesta selezionata, operazione annullata.";
				  }
				  break;
			  }
		  }
	  }
	  
	  templ.set("msgesito",msgEsito);
  }

  else
  {
	  String msgEsito = "Non è stato trovato nessuno IUV da recuperare per la richiesta selezionata.";
	  String idRichiesta = request.getParameter("dettaglio");
	  CuneoLogger.debug("/view/recuperaIUVPOP.jsp"," Inizio funzione ricerca IUV");
	  CuneoLogger.debug("/view/recuperaIUVPOP.jsp"," idRichiesta: "+idRichiesta);
	  	
	  if(idRichiesta!=null && idRichiesta.endsWith("A"))
		  idRichiesta = idRichiesta.substring(0, idRichiesta.length()-1);
	  
	  
	  etichettaCampione.select(idRichiesta);
	  String dataInserimento = etichettaCampione.getDataInserimentoRichiesta();
	  CuneoLogger.debug("/view/recuperaIUVPOP.jsp"," chiamo ricevutaPagamento");
	  etichettaCampione.ricevutaPagamento(idRichiesta);
	  CuneoLogger.debug("/view/recuperaIUVPOP.jsp"," chiamo pagamento.selectMultiploCompleto");
	  pagamento = pagamento.selectMultiploCompleto(idRichiesta, null);
	  if(pagamento!=null)
	  {
		  CuneoLogger.debug("/view/recuperaIUVPOP.jsp"," pagamento != null");
		  pagamento.setDataSource(dataSource);
		  pagamento.setAut(aut);
		  String codiceFiscaleFinale = (pagamento.getPagatore_codiceFiscale()!=null && pagamento.getPagatore_codiceFiscale().trim().length()>0) ? pagamento.getPagatore_codiceFiscale() : pagamento.getPagatore_piva();
		  
		  ParametriLeggiIuv leggiIuv = new ParametriLeggiIuv();
		  leggiIuv.setCodiceFiscalePIVA(codiceFiscaleFinale);
		  leggiIuv.setIdApplicativo("58");
		  leggiIuv.setDataInizioSelezione(dataInserimento);
		  PagoPAWS ppaWS = getPagoPAWS(beanParametriApplication.getAgripagopaWSDL());
		  CuneoLogger.debug("/view/recuperaIUVPOP.jsp"," effettuo chiamata a ppaWS.leggiIuv: cf:"+pagamento.getCf()+" , dataInserimento:"+dataInserimento); 
		  EsitoLeggiIuv esitoLeggiIuv = ppaWS.leggiIuv(leggiIuv);
		  CuneoLogger.debug("/view/recuperaIUVPOP.jsp"," chiamata a ppaWS.leggiIuv terminata"); 
		  boolean trovatoIUV = false;
		  
		  if(esitoLeggiIuv!=null && esitoLeggiIuv.getElencoIuv()!=null && !esitoLeggiIuv.getElencoIuv().isEmpty())
		  {
			  for(ElencoIuv item: esitoLeggiIuv.getElencoIuv())
			  {
				  if(item==null)
				  {
					  CuneoLogger.debug("/view/recuperaIUVPOP.jsp"," IUV NULL su ppa");
					  continue; 
				  }
				  
				  if(("Annullato").equalsIgnoreCase(item.getEsito()) ||  item.getImportoPagato()==null || item.getImportoPagato().compareTo(BigDecimal.ZERO) == 0)
				  {
					  continue;
				  }
				  
				  //controllo se lo iuv è presente a sistema
				  CuneoLogger.debug("/view/recuperaIUVPOP.jsp"," datiFattura.selectImportoPagamento()"); 
				  DatiFattura datiFatturaImporto = datiFattura.selectImportoPagamento(item.getIuv());
				  
				  if(datiFatturaImporto==null || datiFatturaImporto.getImportoBD()==null || datiFatturaImporto.getImportoBD().compareTo(item.getImportoPagato()) < 0)
				  {
					  CuneoLogger.debug("/view/recuperaIUVPOP.jsp"," trovato IUV"); 
					  //Se lo IUV non è presente in agrichim per niente, oppure se è presente ma pagato parzialmente allora lo aggiungo in elenco
					  if(!trovatoIUV){
						  templ.newBlock("tastoConferma");
						  templ.set("idRichiesta", idRichiesta);
						  msgEsito = "Sono stati trovati i seguenti IUV, selezionare quello che si desidera recuperare:";
					  }
					  trovatoIUV = true;
					  templ.newBlock("RigaIUV");
					  templ.set("RigaIUV.iuv_id", item.getIuv());
					  templ.set("RigaIUV.iuv", item.getIuv()+" - Codice Fiscale/PIVA: "+esitoLeggiIuv.getCodiceFiscalePIVA()+" - importo dovuto: "+ Utili.formatCurrency(item.getImportoDovuto())+" Euro");
				  }
			  }
		  }
	  }
	  
	  templ.set("msgesito",msgEsito);
  }
  
  
%>
<%! 
public static PagoPAWS getPagoPAWS(String urlWSDL) throws Exception {
    final String THIS_METHOD = "[::getPagoPAWS]";
    CuneoLogger.debug("/controller/verificaPagamento.jsp",THIS_METHOD + " BEGIN.");
    CuneoLogger.debug("/controller/verificaPagamento.jsp",THIS_METHOD + " PAGOPA_WSDL = " + urlWSDL);
    //Client client = null;
    PagoPAWS pagoPAWS = null;
    try {
        URI wsdlURI = new URI(urlWSDL);
        URL wsdlURL = wsdlURI.toURL();
        QName SERVICE_NAME = new QName("http://it/csi/smrcomms/agripagopasrv/business/pagopa", "PagoPAService");
        PagoPAService service = new PagoPAService(wsdlURL, SERVICE_NAME);
        // Recupero lo stub
        pagoPAWS = service.getPagoPAWSPort();
        BindingProvider bp = (BindingProvider) pagoPAWS;
        String url = urlWSDL.substring(0,urlWSDL.indexOf("?"));
        java.util.Map<String, Object> context = bp.getRequestContext();
        context.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url);
        org.apache.cxf.endpoint.Client client = ClientProxy.getClient(pagoPAWS);
        HTTPConduit conduit = (HTTPConduit) client.getConduit();
        HTTPClientPolicy policy = conduit.getClient();
        policy.setConnectionTimeout(120000);
        policy.setReceiveTimeout(120000);
    }catch(Exception e){
        CuneoLogger.error("/view/recuperaIUVPOP.jsp", "Exception during the invocation of getPagoPAWS method in AgrichimBO view/recuperaIUVPO.jsp "+e);
        throw e;
    }finally{
        CuneoLogger.debug("/view/recuperaIUVPOP.jsp",THIS_METHOD + " END");
        return pagoPAWS;
    }
}
%> 
<%= templ.text() %>
