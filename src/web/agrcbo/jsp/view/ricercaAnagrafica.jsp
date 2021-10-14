<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*,java.util.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/ricercaAnagrafica.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/controllaUtente.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="beanOrgTecnico"
     scope="application"
     class="it.csi.agrc.BeanOrganizzazioniTecnico"/>

<jsp:useBean
     id="beanRicerca"
     scope="session"
     class="it.csi.agrc.BeanRicerca"/>

<jsp:useBean
     id="organizzazioniTecnico"
     scope="page"
     class="it.csi.agrc.OrganizzazioniTecnico">
<%
  organizzazioniTecnico.setDataSource(dataSource);
  organizzazioniTecnico.setAut(aut);
%>
</jsp:useBean>

<jsp:useBean
     id="anagrafiche"
     scope="page"
     class="it.csi.agrc.Anagrafiche">
    <%
      anagrafiche.setDataSource(dataSource);
      anagrafiche.setAut(aut);
      anagrafiche.setPasso(beanParametriApplication.getMaxRecordXPagina());
    %>
</jsp:useBean>

<%
                    /**
                     * Il codice seguente serve per gestire la parte superiore della
                     * pagina: quella relativa alla ricerca
                     */

  String organizzazione = null;
  String tipoOrganizzazione = null;
  String tipoUtente= null;
  String codFisPIVA= null;
  String ragioneSociale= null;

  tipoOrganizzazione=request.getParameter("tipoOrganizzazione");
  if (tipoOrganizzazione!=null)
  {
    /**
    * Sono in questa pagina perché è stata ricaricata la combo dei tipi
    * di organizzazione, quindi devo leggere tutti i parametri passati
    */
      organizzazione = request.getParameter("organizzazione");
      tipoUtente = request.getParameter("tipoUtente");
      codFisPIVA = request.getParameter("codFisPIVA");
      ragioneSociale = request.getParameter("ragioneSociale");
  }
  else
  {
 /**
  * Leggo gli eventuali parametri dal file chiamante. Se sono valorizzati
  * siginifica che è già stata efettuata una ricerca e che devo rimpostare
  * i parametri scelti
  **/
     tipoOrganizzazione = beanRicerca.getTipoOrganizzazione();
     organizzazione = beanRicerca.getOrganizzazione();
     codFisPIVA = beanRicerca.getCodFisPIVA();
     tipoUtente = beanRicerca.getTipoUtente();
     ragioneSociale = beanRicerca.getRagioneSociale();
  }
  if (tipoOrganizzazione== null) tipoOrganizzazione ="";
  if (organizzazione== null) organizzazione ="";
  if (codFisPIVA== null) codFisPIVA ="";
  if (tipoUtente== null) tipoUtente ="";
  if (ragioneSociale== null) ragioneSociale ="";

  templ.bset("codFisPIVA",codFisPIVA);
  templ.bset("ragioneSociale",ragioneSociale);

  if ("L".equals(tipoUtente)) templ.bset("selectedLab","selected");
  else if ("T".equals(tipoUtente)) templ.bset("selectedTec","selected");
  else if ("P".equals(tipoUtente)) templ.bset("selectedPriv","selected");
  else templ.bset("selectedDef","selected");


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
   anagrafiche.setBaseElementi(attuale);
   anagrafiche.fillAnagrafiche();
   size=anagrafiche.size();

   if ( size>0 )
   {
     passo=anagrafiche.getPasso();
     templ.newBlock("elencoAnagrafiche");
     templ.set("elencoAnagrafiche.num",""+anagrafiche.getNumRecord());
     if (attuale!=1)
     {
       templ.newBlock("indietro");
       templ.set("elencoAnagrafiche.indietro.attuale",""+(attuale-passo));
     }
     else
        templ.set("elencoAnagrafiche.nonIndietro.nonIndietro","");

     if ((attuale+passo) <=anagrafiche.getNumRecord())
     {
       templ.newBlock("avanti");
       templ.set("elencoAnagrafiche.avanti.attuale",""+(attuale+passo));
     }
     else
        templ.set("elencoAnagrafiche.nonAvanti.nonAvanti","");
      if (size!=anagrafiche.getNumRecord())
      {
        int numPag=((anagrafiche.getNumRecord()-1)/passo)+1;
        int pagAtt=(attuale/passo)+1;
        templ.set("elencoAnagrafiche.pagine","Pagina "+pagAtt+"/"+numPag);
      }
      Anagrafica anag;
      String idAnagrafica=null,cognomeRagioneSociale=null,nome=null;
      String codiceIdentificativo=null,comuneResidenza=null;
      for(int i=0;i<size;i++)
      {
        templ.newBlock("elencoAnagraficaBody");
        anag=anagrafiche.get(i);
        idAnagrafica=anag.getIdAnagrafica();
        cognomeRagioneSociale=anag.getCognomeRagioneSociale();
        nome=anag.getNome();
        codiceIdentificativo=anag.getCodiceIdentificativo();
        tipoUtente=anag.getTipoUtente();
        comuneResidenza=anag.getComuneResidenza();
        if (i==0)
        {
          templ.newBlock("elencoSi");
          templ.set("elencoSi.idAnagraficaPrimo",idAnagrafica);
          templ.set("elencoAnagrafiche.elencoAnagraficaBody.checked","checked");
        }
        templ.set("elencoAnagrafiche.elencoAnagraficaBody.idAnagrafica",idAnagrafica);
        templ.set("elencoAnagrafiche.elencoAnagraficaBody.cognomeRagioneSociale",cognomeRagioneSociale);
        templ.set("elencoAnagrafiche.elencoAnagraficaBody.nome",nome);
        templ.set("elencoAnagrafiche.elencoAnagraficaBody.codiceIdentificativo",codiceIdentificativo);
        templ.set("elencoAnagrafiche.elencoAnagraficaBody.tipoUtente",tipoUtente);
        templ.set("elencoAnagrafiche.elencoAnagraficaBody.comuneResidenza",comuneResidenza);
      }
  }
  else
  {
     templ.newBlock("elencoAnagraficheNo");
  }
%>

<%-- HTMPL genera ora la pagina web e si finalizza così lo stato stazionario --%>

<%= templ.text() %>
