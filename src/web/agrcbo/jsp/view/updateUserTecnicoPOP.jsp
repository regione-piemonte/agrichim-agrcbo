<%@ page errorPage="/jsp/view/errorePopup.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/updateUserTecnicoPOP.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/controllaUtente.inc" %>
<%@ include file="/jsp/dataSource.inc" %>


<jsp:useBean
     id="comuni"
     scope="page"
     class="it.csi.agrc.Comuni">
<%
  comuni.setDataSource(dataSource);
  comuni.setAut(aut);
%>
</jsp:useBean>

<jsp:useBean
     id="anagUte"
     scope="request"
     class="it.csi.agrc.Anagrafica">
<%
  anagUte.setDataSource(dataSource);
  anagUte.setAut(aut);
%>
</jsp:useBean>


<!--
  Il file che includo serve a gestire gli errori dovuti al funzionamento non
  corretto del javascript
-->
<%@ include file="/jsp/view/problemiJavascript.inc" %>

<%

  /**
  * Se sono in questa pagina perché c'è stato un errore non devo leggere i
  * dati che si trovano memorizzati dentro al database ma devo far vedere
  * quelli che l'utente ha inserito, che si trovano nella request
  */
  if (request.getParameter("errore") == null)
  {
    String idAnagrafica=request.getParameter("dettaglio");
    anagUte.select(idAnagrafica);
  }

  String tipoPersona=null;
  String tipoUtente=null;
  String codiceIdentificativo=null;
  String cognome=null;
  String nome=null;
  String indirizzo=null;
  String cap=null;
  String istat=null;
  String comune=null;
  String eMail=null;
  String telefono=null;
  String cellulare=null;
  String fax=null;
  String codDestinatario=null;
  String pec=null;
  String codContabilia=null;


  tipoPersona=anagUte.getTipoPersona();
  tipoUtente=anagUte.getTipoUtente();
  codiceIdentificativo=anagUte.getCodiceIdentificativo();
  cognome=anagUte.getCognomeRagioneSociale();
  nome=anagUte.getNome();
  indirizzo=anagUte.getIndirizzo();
  cap=anagUte.getCap();
  istat=anagUte.getComuneResidenza();
  eMail=anagUte.getEmail();
  telefono=anagUte.getTelefono();
  cellulare=anagUte.getCellulare();
  fax=anagUte.getFax();
  codDestinatario=anagUte.getCodDestinatario();
  pec=anagUte.getPec();
  codContabilia=anagUte.getCodContabilia();


  /**
    * Se qualche parametro è nullo devo impostarlo a vuoto altrimenti
    * htmpl si arrabbia
    * */
  if (tipoPersona==null) tipoPersona="";
  if (tipoUtente==null) tipoUtente="";
  if (codiceIdentificativo==null) codiceIdentificativo="";
  if (cognome==null) cognome="";
  if (nome==null) nome="";
  if (indirizzo==null) indirizzo="";
  if (cap==null) cap="";
  if (istat==null) istat="";
  else comune=comuni.getDescrizioneComune(istat);
  if (comune==null) comune="";
  if (eMail==null) eMail="";
  if (telefono==null) telefono="";
  if (cellulare==null) cellulare="";
  if (fax==null) fax="";
  if (codDestinatario==null) codDestinatario="";
  if (pec==null) pec="";
  if (codContabilia==null) codContabilia="";

  templ.bset("tipoPersona",tipoPersona);
  templ.bset("tipoUtente",tipoUtente);
  templ.bset("codiceIdentificativo",codiceIdentificativo);
  templ.bset("cognome",cognome);
  templ.bset("nome",nome);
  templ.bset("indirizzo",indirizzo);
  templ.bset("cap",cap);
  templ.bset("istat",istat);
  templ.bset("comune",comune);
  templ.bset("eMail",eMail);
  templ.bset("telefono",telefono);
  templ.bset("cellulare",cellulare);
  templ.bset("fax",fax);
  templ.bset("codDestinatario",codDestinatario);
  templ.bset("pec",pec);
  templ.bset("codContabilia",codContabilia);

%>

<%= templ.text() %>
