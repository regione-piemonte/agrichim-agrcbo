<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/ricercaOrganizzazione.htm");
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
     id="beanOrgTecnico"
     scope="application"
     class="it.csi.agrc.BeanOrganizzazioniTecnico"/>

<jsp:useBean
     id="beanRicerca"
     scope="session"
     class="it.csi.agrc.BeanRicerca"/>

<jsp:useBean
     id="organizzazioneProfessionali"
     scope="page"
     class="it.csi.agrc.OrganizzazioneProfessionali">
    <%
      organizzazioneProfessionali.setDataSource(dataSource);
      organizzazioneProfessionali.setAut(aut);
      organizzazioneProfessionali.setPasso(beanParametriApplication.getMaxRecordXPagina());
    %>
</jsp:useBean>


<%
          /**
           * Il codice seguente serve per gestire la parte superiore della
           * pagina: quella relativa alla ricerca
           */
  /**
   * Leggo gli eventuali parametri dal file chiamante. Se sono valorizzati
   * siginifica che è già stata efettuata una ricerca e che devo rimpostare
   * i parametri scelti
   **/
   String tipoOrganizzazione = beanRicerca.getTipoOrganizzazione();
   String ragioneSociale = beanRicerca.getRagioneSociale();
   String istat = beanRicerca.getIstat();

   if (tipoOrganizzazione== null) tipoOrganizzazione ="";
   if (ragioneSociale== null) ragioneSociale ="";
   if (istat== null) istat ="";
   else
   {
     String comune=comuni.getDescrizioneComune(istat);
     templ.bset("comune",comune);
   }
   templ.bset("tipoOrganizzazione",tipoOrganizzazione);
   templ.bset("ragioneSociale",ragioneSociale);
   templ.bset("istat",istat);


   /**
   * Carico la combo dei tipi di organizzazione
   **/
  String codStr[]=beanOrgTecnico.getCod();
  String descStr[]=beanOrgTecnico.getDesc();
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
   organizzazioneProfessionali.setBaseElementi(attuale);
   organizzazioneProfessionali.fillOrganizzazioneProfessionali();
   int size=organizzazioneProfessionali.size();

   if ( size>0 )
   {
     passo=organizzazioneProfessionali.getPasso();
     templ.newBlock("elencoOrgProfessionali");
     templ.set("elencoOrgProfessionali.num",""+organizzazioneProfessionali.getNumRecord());
     if (attuale!=1)
     {
       templ.newBlock("indietro");
       templ.set("elencoOrgProfessionali.indietro.attuale",""+(attuale-passo));
     }
     else
        templ.set("elencoOrgProfessionali.nonIndietro.nonIndietro","");

     if ((attuale+passo) <=organizzazioneProfessionali.getNumRecord())
     {
       templ.newBlock("avanti");
       templ.set("elencoOrgProfessionali.avanti.attuale",""+(attuale+passo));
     }
     else
        templ.set("elencoOrgProfessionali.nonAvanti.nonAvanti","");
      if (size!=organizzazioneProfessionali.getNumRecord())
      {
        int numPag=((organizzazioneProfessionali.getNumRecord()-1)/passo)+1;
        int pagAtt=(attuale/passo)+1;
        templ.set("elencoOrgProfessionali.pagine","Pagina "+pagAtt+"/"+numPag);
      }
      OrganizzazioneProfessionale orgProf;
      String cfPartitaIva=null, sedeTerritoriale=null, idOrganizzazione=null;
      String indirizzo=null, comune=null;
      for(int i=0;i<size;i++)
      {
        templ.newBlock("elencoOrgProfessionaleBody");
        orgProf=organizzazioneProfessionali.get(i);
        idOrganizzazione=orgProf.getIdOrganizzazione();
        if (i==0)
        {
          templ.set("elencoOrgProfessionali.elencoOrgProfessionaleBody.checked","checked");
          templ.newBlock("elencoSi");
          templ.set("elencoSi.idOrganizzazionePrimo",idOrganizzazione);
        }
        cfPartitaIva= orgProf.getCfPartitaIva();
        ragioneSociale= orgProf.getRagioneSociale();
        sedeTerritoriale= orgProf.getSedeTerritoriale();
        indirizzo= orgProf.getIndirizzo();
        comune= orgProf.getComune();
        templ.set("elencoOrgProfessionali.elencoOrgProfessionaleBody.idOrganizzazione",idOrganizzazione);
        templ.set("elencoOrgProfessionali.elencoOrgProfessionaleBody.cfPartitaIva",cfPartitaIva);
        templ.set("elencoOrgProfessionali.elencoOrgProfessionaleBody.ragioneSociale",ragioneSociale);
        templ.set("elencoOrgProfessionali.elencoOrgProfessionaleBody.sedeTerritoriale",sedeTerritoriale);
        templ.set("elencoOrgProfessionali.elencoOrgProfessionaleBody.indirizzo",indirizzo);
        templ.set("elencoOrgProfessionali.elencoOrgProfessionaleBody.comune",comune);
      }
  }
  else
  {
     templ.newBlock("elencoOrgProfessionaliNo");
  }

%>


<%= templ.text() %>


