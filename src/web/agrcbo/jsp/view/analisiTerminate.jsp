<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/analisiTerminate.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/controllaUtente.inc" %>
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
      etichettaCampioni.setPasso(beanParametriApplication.getMaxRecordXPagina());
    %>
</jsp:useBean>

<%
	String errore = request.getParameter("errore");
	if (errore != null && ! "".equals(errore)){
		templ.set("err_error", errore);
	}
	if(request.getParameter("messaggi")!=null && !"".equals(request.getParameter("messaggi"))){
	     templ.newBlock("avvisoControlli");
	     templ.set("avvisoControlli.alertAvvisoControlli",request.getParameter("messaggi"));
	}
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
   etichettaCampioni.setBaseElementi(attuale);
   etichettaCampioni.fillElencoCampioni(null);
   int size=etichettaCampioni.size();
   String codiceMateriale = null;
   if ( size>0 ){
     passo=etichettaCampioni.getPasso();
     templ.newBlock("elencoCampioni");
     templ.set("elencoCampioni.num",""+etichettaCampioni.getNumRecord());
     if (attuale!=1){
       templ.newBlock("indietro");
       templ.set("elencoCampioni.indietro.attuale",""+(attuale-passo));
     }else
        templ.set("elencoCampioni.nonIndietro.nonIndietro","");

     if ((attuale+passo) <=etichettaCampioni.getNumRecord()){
       templ.newBlock("avanti");
       templ.set("elencoCampioni.avanti.attuale",""+(attuale+passo));
     } else
        templ.set("elencoCampioni.nonAvanti.nonAvanti","");
     if (size!=etichettaCampioni.getNumRecord()) {
        int numPag=((etichettaCampioni.getNumRecord()-1)/passo)+1;
        int pagAtt=(attuale/passo)+1;
        templ.set("elencoCampioni.pagine","Pagina "+pagAtt+"/"+numPag);
     }
	EtichettaCampione e;
	String idRichiesta=null, statoAttuale=null,note=null, iuv=null,
	      tipo_pagamento=null,pagamento=null,descStatoPagamento=null;
	for(int i=0;i<size;i++){
        /**
           * Le due righe seguenti servono per gestire i checkBox
           **/
        templ.newBlock("ricercaJavascript");
        templ.set("ricercaJavascript.idCheck",""+i);
      
        templ.newBlock("elencoCampioneBody");
        e=etichettaCampioni.get(i);
        idRichiesta = e.getIdRichiesta();
        codiceMateriale = e.getCodiceMateriale();
        statoAttuale=e.getStatoAttuale();
        note=e.getNote();
        iuv = e.getIuv();
        tipo_pagamento = e.getTipoPagamento();
        pagamento = e.getPagamento();
        if(pagamento!=null && !pagamento.equals("")){
	        if(pagamento.equals("S"))
	            descStatoPagamento = "Pagato";
	        else if(pagamento.equals("G"))
	            descStatoPagamento = "Gratuito";
	        else
	            descStatoPagamento = "Non Pagato";
        }
        if (i==0){
          templ.newBlock("elencoSi");
          templ.set("elencoSi.idRichiestaPrimo",idRichiesta);
          templ.set("elencoSi.codiceMaterialePrimo",codiceMateriale);
          templ.set("elencoSi.statoCampione",statoAttuale);
          //templ.set("elencoCampioni.elencoCampioneBody.checked","checked");
        }
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
        templ.set("elencoCampioni.elencoCampioneBody.iuv",iuv);
        templ.set("elencoCampioni.elencoCampioneBody.pagamento",pagamento);
        templ.set("elencoCampioni.elencoCampioneBody.descStatoPagamento",descStatoPagamento);
        templ.set("elencoCampioni.elencoCampioneBody.tipo_pagamento",tipo_pagamento);
        templ.set("elencoCampioni.elencoCampioneBody.note",note);
      }
  } else {
     templ.newBlock("elencoCampioniNo");
  }

  /**
   * *Carico i dati di tutti i materiali per visualizzarli nella combo
   */
  String codStr[],descStr[];
  codStr=beanTipoCampione.getCodMateriale();
  descStr=beanTipoCampione.getDescMateriale();
  BeanRicerca br = (BeanRicerca)session.getAttribute("beanRicerca");
  if(br!=null && br.getTipoMateriale().equals(""))
      codiceMateriale = br.getTipoMateriale();
  //codiceMateriale = (codiceMateriale == null || "".equals(codiceMateriale)) ? Constants.MATERIALE.CODICE_MATERIALE_TERRENI : codiceMateriale;
  for(int i=0;i<codStr.length;i++){
    if (codiceMateriale!=null && codiceMateriale.equals(codStr[i])){
      templ.set("tipoMateriale.selected", "selected");
    } else {
      templ.set("tipoMateriale.selected", "");
    }
  
    templ.set("tipoMateriale.codiceMateriale",codStr[i]);
    templ.set("tipoMateriale.descrizione",descStr[i]);
  }
   /**
    * Controllo se la persona collegata può emettere un referto o meno
    * */
    if (aut.getResponsabile()==null) {
      //Non può emettere un referto in quanto non è stato trovato il suo
      //responsabile
      templ.newBlock("noresp");
    } else {
      //può emettere un referto
      templ.newBlock("resp");
    }
%>

<%-- HTMPL genera ora la pagina web e si finalizza così lo stato stazionario --%>

<%= templ.text() %>

