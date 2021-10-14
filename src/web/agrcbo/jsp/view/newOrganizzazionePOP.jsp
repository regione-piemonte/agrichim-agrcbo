<%@ page errorPage="/jsp/view/errorePopup.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/organizzazionePOP.htm");
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
     id="organizzazioneProfessionale"
     scope="request"
     class="it.csi.agrc.OrganizzazioneProfessionale"/>

<!--
  Il file che includo serve a gestire gli errori dovuti al funzionamento non
  corretto del javascript
-->
<%@ include file="/jsp/view/problemiJavascript.inc" %>

<%
  /**
   * Valorizzo la pagina a cui deve html deve mandare l'elaborazione de dati
   * */
  templ.bset("insertUpdate","newOrganizzazionePOP.jsp");

  String idTipoOrganizzazione=null;
  String cfPartitaIva=null;
  String ragioneSociale=null;
  String sedeTerritoriale=null;
  String indirizzo=null;
  String cap=null;
  String comune=null;
  String comuneDesc=null;
  String telefono=null;
  String fax=null;
  String email=null;

  /**
  * Se sono in questa pagina perché c'è stato un errore non devo leggere i
  * dati che si trovano memorizzati dentro al database ma devo far vedere
  * quelli che l'utente ha inserito, che si trovano nella request
  */
  if (errore != null)
  {
    /**
      * Leggo i parametri che si tovano nel bean
      * */
    idTipoOrganizzazione=organizzazioneProfessionale.getIdTipoOrganizzazione();
    cfPartitaIva=organizzazioneProfessionale.getCfPartitaIva();
    ragioneSociale=organizzazioneProfessionale.getRagioneSociale();
    sedeTerritoriale=organizzazioneProfessionale.getSedeTerritoriale();
    indirizzo=organizzazioneProfessionale.getIndirizzo();
    cap=organizzazioneProfessionale.getCap();
    comune=organizzazioneProfessionale.getComune();
    telefono=organizzazioneProfessionale.getTelefono();
    fax=organizzazioneProfessionale.getFax();
    email=organizzazioneProfessionale.getEmail();
  }

  /**
    * Se qualche parametro è nullo devo impostarlo a vuoto altrimenti
    * htmpl si arrabbia
    * */
  if (idTipoOrganizzazione==null) idTipoOrganizzazione="";
  if (cfPartitaIva==null) cfPartitaIva="";
  if (ragioneSociale==null) ragioneSociale="";
  if (sedeTerritoriale==null) sedeTerritoriale="";
  if (indirizzo==null) indirizzo="";
  if (cap==null) cap="";

  if (comune==null) comune="";
  else comuneDesc=comuni.getDescrizioneComune(comune);
  if (comuneDesc==null) comuneDesc="";

  if (telefono==null) telefono="";
  if (fax==null) fax="";
  if (email==null) email="";


  templ.bset("cfPartitaIva",cfPartitaIva);
  templ.bset("ragioneSociale",ragioneSociale);
  templ.bset("sedeTerritoriale",sedeTerritoriale);
  templ.bset("indirizzo",indirizzo);
  templ.bset("cap",cap);
  templ.bset("comune",comune);
  templ.bset("comuneDesc",comuneDesc);
  templ.bset("telefono",telefono);
  templ.bset("fax",fax);
  templ.bset("email",email);

  /**
  * Carico la combo dei tipi di organizzazione
  **/
  String codStr[],descStr[];
  codStr=beanOrgTecnico.getCod();
  descStr=beanOrgTecnico.getDesc();
  for(int i=0;i<codStr.length;i++)
  {
   templ.newBlock("tipoOrganizzazione");
   if (idTipoOrganizzazione.equals(codStr[i]))
     templ.set("tipoOrganizzazione.selected","selected");
   else
     templ.set("tipoOrganizzazione.selected","");
   templ.set("tipoOrganizzazione.cod",codStr[i]);
   templ.set("tipoOrganizzazione.desc",descStr[i]);
  }
%>

<%= templ.text() %>
