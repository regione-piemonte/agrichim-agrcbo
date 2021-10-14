<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*" isThreadSafe="true" %>
<%@ page import="it.csi.iride2.policy.entity.Identita"%>
<%@ page import="it.csi.csi.porte.InfoPortaDelegata" %>
<%@ page import="it.csi.csi.util.xml.PDConfigReader" %>
<%@ page import="it.csi.csi.porte.proxy.PDProxy" %>
<%@ page import="it.csi.iride2.policy.interfaces.PolicyEnforcerBaseService" %>
<%@ page import="it.csi.iride2.policy.entity.Application"%>
<%@ page import="it.csi.papua.papuaserv.dto.gestioneutenti.Ruolo"%>
<%@ page import="it.csi.papua.papuaserv.presentation.rest.profilazione.client.PapuaservRESTClient" %>
<%@ page import="it.csi.papua.papuaserv.presentation.rest.profilazione.client.PapuaservProfilazioneServiceFactory" %>

<%
    // Elimina la sessione eventualmente già esistente
    session.removeAttribute("aut");
%>

<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
  id="beanParametriApplication"
  scope="application"
  class="it.csi.agrc.BeanParametri">
</jsp:useBean>

<%
  it.csi.agrc.Autenticazione aut=new it.csi.agrc.Autenticazione();
  it.csi.agrc.AutenticazioneQry autQry=new it.csi.agrc.AutenticazioneQry();
  autQry.setDataSource(dataSource);

  Identita identita = (Identita) session.getAttribute("identita");

  //InfoPortaDelegata infoPDPEP = PDConfigReader.read(session.getServletContext().getResourceAsStream("/WEB-INF/defPDPEPEJB.xml"));
  //InfoPortaDelegata infoPDUtility = PDConfigReader.read(session.getServletContext().getResourceAsStream("/WEB-INF/defPDUtilityEJB.xml"));
  //PolicyEnforcerBaseService iridePEPClient = (PolicyEnforcerBaseService) PDProxy.newInstance(infoPDPEP);
  //PolicyEnforcerHelperService irideUtilityClient = (PolicyEnforcerHelperService) PDProxy.newInstance(infoPDUtility);
  
  PapuaservRESTClient papuaClient =  bindingPapua();
  CuneoLogger.debug(this,"papuaClientBO = "+papuaClient!=null?"ok":"null!");
  CuneoLogger.debug(this,"identita.getCodFiscale() = "+identita.getCodFiscale());
  CuneoLogger.debug(this,"identita.getLivelloAutenticazione() = "+identita.getLivelloAutenticazione());
  Ruolo[] ruoli = papuaClient.findRuoliForPersonaInApplicazione(identita.getCodFiscale(), identita.getLivelloAutenticazione(), Constants.ID_PROCEDIMENTO_AGCRBO);
  session.setAttribute("ruoli",ruoli);
  CuneoLogger.debug(this,"ruoli.length = "+ruoli.length);
  String ruolo = null;
  boolean utenteEsperto=false;
  int nRuoli = 0;

  //Se c'è l'utente monitoraggio, passo direttamente alla pagina di scelta ruoli. 
  for (; nRuoli<ruoli.length; nRuoli++)
  {
    ruolo = ruoli[nRuoli].getCodice();
    if ("MONITORAGGIO@AGRICOLTURA".equals(ruolo)) {
    	Utili.forward(request, response, "/jsp/controller/ruoli.jsp");
	}
  }
  
  nRuoli = 0;  
  for (; nRuoli<ruoli.length; nRuoli++)
  {
    ruolo = ruoli[nRuoli].getCodice();
  	CuneoLogger.debug(this, "ruolo ["+nRuoli+"]: "+ruolo);
  	/* Accesso permesso solo se l'utente ha tra i ruoli il ruolo LAR_x@AGRICOLTURA */
		utenteEsperto = "LAR_E@AGRICOLTURA".equals(ruolo); 
    if ( utenteEsperto || "LAR_B@AGRICOLTURA".equals(ruolo))
    	break;
  }

  if (ruoli.length == 0 || nRuoli >= ruoli.length)
  {
    CuneoLogger.debug(this,"Accesso Back Office NON CONVALIDATO");
    response.sendRedirect(beanParametriApplication.getUrlStartApplication());
    return;
  }

  String codiceFiscale = identita.getCodFiscale();
  aut.setUtenteEsperto(autQry.caricaDati(codiceFiscale,utenteEsperto));
  aut.setResponsabile(autQry.getResponsabile());
  aut.setCognome(autQry.getCognome());
  aut.setNome(autQry.getNome());
  aut.setIdAnagrafica(autQry.getIdAnagrafica());
  
  if (aut.isUtenteEsperto())
  {
    session.setAttribute("aut",aut);
    CuneoLogger.debug(this,"Accesso Back Office convalidato - "+codiceFiscale);
    Utili.forward(request, response, "/jsp/view/menu.jsp");
  }
  else
  {
    /**
     * A differenza del front office, se l'anagrafica utente non esiste,
     * l'utente non può entrare e basta
     */
    CuneoLogger.debug(this, "Anagrafica Back Office NON ESISTENTE");
    response.sendRedirect(beanParametriApplication.getUrlStartApplication());
    return;
  }
%>
<%!

private PapuaservRESTClient bindingPapua() throws Exception {
    PapuaservRESTClient client = null;
    CuneoLogger.debug(this, "agrichimBO login.jsp -- bindingPapua BEGIN");
    try {
        client = PapuaservProfilazioneServiceFactory.getRestServiceClient();
    } catch (Exception e) {
        CuneoLogger.error(this, "Exception during the invocation of bindingPapua method in AgrichimBO login.jsp");
        throw e;
    }
    CuneoLogger.debug(this, "agrichimBO login.jsp -- bindingPapua END");

    return client;

}
%>