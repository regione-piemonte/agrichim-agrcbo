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
     class="it.csi.agrc.OrganizzazioneProfessionale">
<%
  organizzazioneProfessionale.setDataSource(dataSource);
  organizzazioneProfessionale.setAut(aut);
%>
</jsp:useBean>

<!--
  Il file che includo serve a gestire gli errori dovuti al funzionamento non
  corretto del javascript
-->
<%@ include file="/jsp/view/problemiJavascript.inc" %>

<%
  /**
   * Valorizzo la pagina a cui deve html deve mandare l'elaborazione de dati
   * */
  templ.bset("insertUpdate","updateOrganizzazionePOP.jsp");

  String idOrganizzazione=null;
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
  String codDestinatario=null;
  String pec=null;
  String codContabilia=null;

  /**
  * Leggo l'id dell'Organizzazione da visualizzare, eseguo la select e carico i dati
  * sulla pagina html
  * */
  idOrganizzazione=request.getParameter("dettaglio");
  if (idOrganizzazione!=null)
  {
    organizzazioneProfessionale.setIdOrganizzazione(idOrganizzazione);
    organizzazioneProfessionale.select();
  }

  /**
    * Leggo i parametri che si tovano nel bean
    * */
  idOrganizzazione=organizzazioneProfessionale.getIdOrganizzazione();
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
  codDestinatario=organizzazioneProfessionale.getCodDestinatario();
  pec=organizzazioneProfessionale.getPec();
  codContabilia=organizzazioneProfessionale.getCodContabilia();

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
  if (codDestinatario==null) codDestinatario="";
  if (pec==null) pec="";
  if (codContabilia==null) codContabilia="";


  templ.bset("idOrganizzazione",idOrganizzazione);
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
  templ.bset("codDestinatario",codDestinatario);
  templ.bset("pec",pec);
  templ.bset("codContabilia",codContabilia);

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
