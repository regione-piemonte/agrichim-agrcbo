<%@ page errorPage="/jsp/view/errorePopup.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="anagUte"
     scope="request"
     class="it.csi.agrc.Anagrafica">
<%
  anagUte.setDataSource(dataSource);
  anagUte.setAut(aut);
%>
</jsp:useBean>


<%
  anagUte.setCognomeRagioneSociale(request.getParameter("cognome"));
  anagUte.setNome(request.getParameter("nome"));
  anagUte.setCodiceIdentificativo(request.getParameter("codiceFiscale"));
  anagUte.setIndirizzo(request.getParameter("indirizzo"));
  anagUte.setCap(request.getParameter("cap"));
  anagUte.setComuneResidenza(request.getParameter("istat"));
  anagUte.setEmail(request.getParameter("eMail"));
  anagUte.setTelefono(request.getParameter("telefono"));
  anagUte.setCellulare(request.getParameter("cellulare"));
  anagUte.setIdOrganizzazione(request.getParameter("organizzazione"));
  /**
   * Controllo gli eventuali errori tramite jsp
   * */
  String errore=anagUte.ControllaDati(Anagrafica.ANAGRAFICA_TECNICO_INSERIMENTO);
  String codFisEs=null;
  if (errore==null)
  {
    if (anagUte.controllaAnagrafica()!=null) codFisEs="codFisEs";
  }
  /**
  * Se codFisEs è diverso da null significa che il codice fiscale inserito
  * appartiene già ad una anagrafcia memorizzata in tabella
  */
  if (codFisEs!=null)
  {
    Utili.forwardConParametri(request, response, "/jsp/view/newUserTecnicoPOP.jsp?codFisEs="+codFisEs);
    return;
  }
  /**
  * Se c'è un errore vuol dire che non è stato eseguito corretamente il
  * javascript
  */
  CuneoLogger.debug(this, "Errore "+errore);

  if (errore!=null)
  {
    Utili.forwardConParametri(request, response, "/jsp/view/newUserTecnicoPOP.jsp?errore="+errore);
    return;
  }
  else
  {
    anagUte.insertTecnico();
  }
%>


<html>
<head></head>
<body onLoad="window.opener.focus(); window.close(); window.opener.location=window.opener.location"></body>
</html>
