<%@ page errorPage="/jsp/view/errorePopup.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*,java.util.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/newUserTecnicoPOP.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/controllaUtente.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="beanOrgTecnico"
     scope="application"
     class="it.csi.agrc.BeanOrganizzazioniTecnico"/>

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
     class="it.csi.agrc.Anagrafica"/>

<jsp:useBean
     id="organizzazioniTecnico"
     scope="page"
     class="it.csi.agrc.OrganizzazioniTecnico">
<%
  organizzazioniTecnico.setDataSource(dataSource);
  organizzazioniTecnico.setAut(aut);
%>
</jsp:useBean>

<!--
  Il file che includo serve a gestire gli errori dovuti al funzionamento non
  corretto del javascript
-->
<%@ include file="/jsp/view/problemiJavascript.inc" %>

<%

  String organizzazione = null;
  String tipoOrganizzazione = null;
  String cognome=null;
  String nome=null;
  String codiceFiscale=null;
  String indirizzo=null;
  String cap=null;
  String istat=null;
  String comune=null;
  String eMail=null;
  String telefono=null;
  String cellulare=null;

  tipoOrganizzazione=request.getParameter("tipoOrganizzazione");
  if (tipoOrganizzazione!=null)
  {
    /**
    * Sono in questa pagina perché è stata ricaricata la combo dei tipi
    * di organizzazione, quindi devo leggere tutti i parametri passati
    */
      organizzazione = request.getParameter("organizzazione");
      cognome= request.getParameter("cognome");
      nome= request.getParameter("nome");
      codiceFiscale= request.getParameter("codiceFiscale");
      indirizzo= request.getParameter("indirizzo");
      cap= request.getParameter("cap");
      istat= request.getParameter("istat");
      comune= request.getParameter("comune");
      eMail= request.getParameter("eMail");
      telefono= request.getParameter("telefono");
      cellulare= request.getParameter("cellulare");
  }
  /**
  * Se sono in questa pagina perché c'è stato un errore non devo leggere i
  * dati che si trovano memorizzati dentro al database ma devo far vedere
  * quelli che l'utente ha inserito, che si trovano nella request
  */
  if ("codFisEs".equals(request.getParameter("codFisEs")))
  {
    templ.newBlock("codFisEsistente");
    errore="";
  }
  if (errore != null)
  {
    /**
      * Leggo i parametri che si tovano nel bean
      * */
    cognome=anagUte.getCognomeRagioneSociale();
    nome=anagUte.getNome();
    codiceFiscale=anagUte.getCodiceIdentificativo();
    indirizzo=anagUte.getIndirizzo();
    cap=anagUte.getCap();
    istat=anagUte.getComuneResidenza();
    eMail=anagUte.getEmail();
    telefono=anagUte.getTelefono();
    cellulare=anagUte.getCellulare();
  }

  /**
    * Se qualche parametro è nullo devo impostarlo a vuoto altrimenti
    * htmpl si arrabbia
    * */
  if (organizzazione==null) organizzazione="";
  if (tipoOrganizzazione==null) tipoOrganizzazione="";
  if (cognome==null) cognome="";
  if (nome==null) nome="";
  if (codiceFiscale==null) codiceFiscale="";
  if (indirizzo==null) indirizzo="";
  if (cap==null) cap="";
  if (istat==null) istat="";
  else comune=comuni.getDescrizioneComune(istat);
  if (comune==null) comune="";
  if (eMail==null) eMail="";
  if (telefono==null) telefono="";
  if (cellulare==null) cellulare="";

  templ.bset("cognome",cognome);
  templ.bset("nome",nome);
  templ.bset("codiceFiscale",codiceFiscale);
  templ.bset("indirizzo",indirizzo);
  templ.bset("cap",cap);
  templ.bset("istat",istat);
  templ.bset("comune",comune);
  templ.bset("eMail",eMail);
  templ.bset("telefono",telefono);
  templ.bset("cellulare",cellulare);

  /**
  * Carico la combo dei tipi di organizzazione
  **/
  String codStr[],descStr[];
  codStr=beanOrgTecnico.getCod();
  descStr=beanOrgTecnico.getDesc();
  for(int i=0;i<codStr.length;i++)
  {
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
  if (!"".equals(tipoOrganizzazione))
  {
   cod.clear();
   desc.clear();
   organizzazioniTecnico.getOrganizzazione(cod,desc,tipoOrganizzazione);
   size=cod.size();
   for(int i=0;i<size;i++)
   {
     templ.newBlock("organizzazione");
     if (organizzazione.equals(cod.get(i)))
       templ.set("organizzazione.selected","selected");
     else
       templ.set("organizzazione.selected","");
     templ.set("organizzazione.cod",(String)cod.get(i));
     templ.set("organizzazione.desc",(String)desc.get(i));
   }
  }

%>

<%= templ.text() %>
