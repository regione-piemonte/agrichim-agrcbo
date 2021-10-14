<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*" isThreadSafe="true" %>
<%@ page import="org.apache.cxf.endpoint.Client" %> 
<%@ page import="org.apache.cxf.frontend.ClientProxy" %>
<%@ page import="it.csi.smrcomms.agripagopasrv.business.pagopa.*" %>
<%@ page import="org.apache.cxf.message.Message" %>
<%@ page import="java.net.*" %>
<%@ page import="javax.xml.namespace.QName" %>
<%@ page import="javax.xml.ws.BindingProvider" %>
<%@ page import="org.apache.cxf.transport.http.HTTPConduit" %>
<%@ page import="org.apache.cxf.transports.http.configuration.HTTPClientPolicy" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.io.OutputStream" %>
<%
  it.csi.jsf.htmpl.Htmpl templ=null ;
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


<%
  /**
   * Controllo che le etichette campione selezionate appartengano tutte allo
   * stesso utente ed allo stesso laboratorio e che abbiano tutte stato di
   * anomalia blank
   * */
  String richieste=request.getParameter("idRichiestaSearch");
  aut.setIdRichiestaChecked(richieste);
String ritorno=request.getParameter("ritorno");
etichettaCampione.verificaPagamento(richieste);
PagoPAWS ppaWS = getPagoPAWS(beanParametriApplication.getAgripagopaWSDL());
String messaggio_alert = "";
if(etichettaCampione.getMessaggioErrore()!=null){
	messaggio_alert = etichettaCampione.getMessaggioErrore();
}else {
    //verificaPagamento
    ParametriVerificaDatiPagamentoSingolo pvdps = new ParametriVerificaDatiPagamentoSingolo();
    pvdps.setCodiceFiscalePIVA(etichettaCampione.getCf());
    CuneoLogger.debug("/controller/verificaPagamento.jsp","ParametriVerificaDatiPagamentoSingolo setCodiceFiscalePIVA "+pvdps.getCodiceFiscalePIVA());
    pvdps.setIuv(etichettaCampione.getIuv());
    CuneoLogger.debug("/controller/verificaPagamento.jsp","ParametriVerificaDatiPagamentoSingolo setIuv "+pvdps.getIuv());
    pvdps.setApplicationId("58");
    CuneoLogger.debug("/controller/verificaPagamento.jsp","ParametriVerificaDatiPagamentoSingolo setApplicationId "+pvdps.getApplicationId());
    EsitoVerificaDatiPagamentoSingolo esitovdps = ppaWS.verificaDatiPagamentoSingolo(pvdps);
    if(esitovdps.getDescErrore() == null && esitovdps.getEsito().equals("Pagamento eseguito")){
        CuneoLogger.debug("/controller/verificaPagamento.jsp","ritorno EsitoVerificaDatiPagamentoSingolo positivo : esito -> "+esitovdps.getEsito());
        etichettaCampione.setDataPagamento(esitovdps.getDataEsito());
        boolean ok = etichettaCampione.updateFlagPagamento(richieste,etichettaCampione.getDataPagamento());
        CuneoLogger.debug("/controller/verificaPagamento.jsp","ritorno dall'update di ETICHETTA_CAMPIONE con esito "+(ok?"positivo":"negativo"));
        messaggio_alert = "Pagamento aggiornato";
    }else if(esitovdps.getDescErrore() == null && !esitovdps.getEsito().equals("Pagamento eseguito")){
        if(etichettaCampione.getTipoPagamento().equals("M3"))
        	messaggio_alert = "Esiste un avviso di pagamento da pagare, devono ristamparlo e pagare con i canali di PagoPA oppure annullarlo";
        else
        	messaggio_alert = "Il pagamento on line e' in attesa, non e' ancora pervenuto; se il problema persiste contatta l'assistenza";
    }else{
        CuneoLogger.debug("/controller/verificaPagamento.jsp","ritorno EsitoVerificaDatiPagamentoSingolo negativo : iuv -> "+esitovdps.getIuv());
        CuneoLogger.debug("/controller/verificaPagamento.jsp","ritorno EsitoVerificaDatiPagamentoSingolo negativo : esito -> "+esitovdps.getEsito());
        CuneoLogger.debug("/controller/verificaPagamento.jsp","ritorno EsitoVerificaDatiPagamentoSingolo negativo : id errore -> "+esitovdps.getIdErrore()!=null?esitovdps.getIdErrore():"");
        CuneoLogger.debug("/controller/verificaPagamento.jsp","ritorno EsitoVerificaDatiPagamentoSingolo negativo : desc errore -> "+esitovdps.getDescErrore()!=null?esitovdps.getDescErrore():"");
        messaggio_alert = esitovdps.getEsito()!=null && !esitovdps.getEsito().equals("")?esitovdps.getEsito():"Impossibile aggiornare il pagamento";
        CuneoLogger.debug("/controller/verificaPagamento.jsp","EsitoVerificaDatiPagamentoSingolo messaggio_alert "+messaggio_alert);
        messaggio_alert = "Errore in fase di richiesta dati pagamento, contatta l'assistenza";
    }
}
if(ritorno==null||ritorno.equals(""))
	Utili.forwardConParametri(request, response, "/jsp/view/analisiRichieste.jsp?multiplo="+messaggio_alert);
else//provengo da identificazioneCampione
	Utili.forwardConParametri(request, response, ritorno+"?messaggi="+messaggio_alert);
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
        CuneoLogger.error("/controller/verificaPagamento.jsp", "Exception during the invocation of getPagoPAWS method in AgrichimBO /controller/verificaPagamento.jsp "+e);
        throw e;
    }finally{
        CuneoLogger.debug("/controller/verificaPagamento.jsp",THIS_METHOD + " END");
        return pagoPAWS;
    }
}
%> 

