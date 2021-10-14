<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>
<%@page import="java.util.Vector"%>
<%@page import="org.apache.poi.hssf.usermodel.*"%>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/campioniLaboratorio.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>


<jsp:useBean
     id="beanTipoCampione"
     scope="application"
     class="it.csi.agrc.BeanTipoCampione"/>

<jsp:useBean
     id="etichettaCampioni"
     scope="page"
     class="it.csi.agrc.EtichettaCampioni">
    <%
      etichettaCampioni.setDataSource(dataSource);
      etichettaCampioni.setAut(aut);
      etichettaCampioni.setPasso(beanParametriApplication.getMaxRecordXPaginaCampLaboratorio());
    %>
</jsp:useBean>

<%
	String errore = request.getParameter("errore");
	if (errore != null && ! "".equals(errore))
	{
		templ.set("err_error", errore);
	}

  int attuale,passo;
  if ("no".equals(request.getParameter("multiplo")))
  {
    templ.newBlock("multiploErrore");
  }
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
  etichettaCampioni.setBaseElementi(attuale);
  //etichettaCampioni.fillElencoCampioni(null);
  etichettaCampioni.fillElencoCampioni(Autenticazione.RICERCA_CAMPIONI_LABORATORIO,null);
  int size=etichettaCampioni.size();
  String codiceMateriale = null;
  if ( size>0 )
  {
    passo=etichettaCampioni.getPasso();
    templ.newBlock("elencoCampioni");
    templ.set("elencoCampioni.num",""+etichettaCampioni.getNumRecord());
    if (attuale!=1)
    {
      templ.newBlock("indietro");
      templ.set("elencoCampioni.indietro.attuale",""+(attuale-passo));
    }
    else
       templ.set("elencoCampioni.nonIndietro.nonIndietro","");

    if ((attuale+passo) <=etichettaCampioni.getNumRecord())
    {
      templ.newBlock("avanti");
      templ.set("elencoCampioni.avanti.attuale",""+(attuale+passo));
    }
    else
       templ.set("elencoCampioni.nonAvanti.nonAvanti","");
     if (size!=etichettaCampioni.getNumRecord())
     {
       int numPag=((etichettaCampioni.getNumRecord()-1)/passo)+1;
       int pagAtt=(attuale/passo)+1;
       templ.set("elencoCampioni.pagine","Pagina "+pagAtt+"/"+numPag);
     }
     EtichettaCampione e;
     String idRichiesta=null, statoAttuale=null,note=null;
     for(int i=0;i<size;i++)
     {
      /**
       * Le due righe seguenti servono per gestire i checkBox
      **/
      templ.newBlock("@@ricercaJavascript");
      templ.set("ricercaJavascript.idCheck",""+i);

      templ.newBlock("elencoCampioneBody");
      e=etichettaCampioni.get(i);
      idRichiesta = e.getIdRichiesta();
      codiceMateriale = e.getCodiceMateriale();
      statoAttuale=e.getStatoAttuale();
      note=e.getNote();
      templ.set("elencoCampioni.elencoCampioneBody.idCheck",""+i);
      templ.set("elencoCampioni.elencoCampioneBody.idRichiesta",idRichiesta);
      templ.set("elencoCampioni.elencoCampioneBody.data",e.getDataInserimentoRichiesta());
      templ.set("elencoCampioni.elencoCampioneBody.descMateriale",e.getDescMateriale());
      if(e.getAnnoCampione()!=null && !"".equals(e.getAnnoCampione()) &&
        e.getNumeroCampione()!=null && !"".equals(e.getNumeroCampione()))
      {
        templ.set("elencoCampioni.elencoCampioneBody.numeroAnno",e.getNumeroCampione()+"/"+e.getAnnoCampione());
      }
      templ.set("elencoCampioni.elencoCampioneBody.statoCampione",statoAttuale);
      templ.set("elencoCampioni.elencoCampioneBody.descrizioneEtichetta",e.getDescrizioneEtichetta());
      templ.set("elencoCampioni.elencoCampioneBody.proprietario",e.getProprietario());
      templ.set("elencoCampioni.elencoCampioneBody.richiedente",e.getRichiedente());
      templ.set("elencoCampioni.elencoCampioneBody.codiceMateriale",codiceMateriale);
      templ.set("elencoCampioni.elencoCampioneBody.note",note);
      templ.set("elencoCampioni.elencoCampioneBody.stato_pagamento",e.getPagamento());
    }
 }
 else
 {
    templ.newBlock("elencoCampioniNo");
 }

 /**
   Carico i dati di tutti i materiali per visualizzarli nella combo
  */
  String codStr[],descStr[];
  codStr=beanTipoCampione.getCodMateriale();
  descStr=beanTipoCampione.getDescMateriale();
  BeanRicerca br = (BeanRicerca)session.getAttribute("beanRicerca");
  if(br!=null && br.getTipoMateriale().equals(""))
      codiceMateriale = br.getTipoMateriale();
  //codiceMateriale = (codiceMateriale == null || "".equals(codiceMateriale)) ? Constants.MATERIALE.CODICE_MATERIALE_TERRENI : codiceMateriale;
  for(int i=0;i<codStr.length;i++){
    if (codiceMateriale!=null && codiceMateriale.equals(codStr[i])) 
      templ.set("tipoMateriale.selected", "selected");
    else
      templ.set("tipoMateriale.selected", "");
  
    templ.set("tipoMateriale.codiceMateriale",codStr[i]);
    templ.set("tipoMateriale.descrizione",descStr[i]);
  }
%>

<%-- HTMPL genera ora la pagina web e si finalizza così lo stato stazionario --%>

<%= templ.text() %>