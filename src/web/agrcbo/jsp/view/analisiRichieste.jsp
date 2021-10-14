<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>
<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/analisiRichieste.htm");
%>
<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>
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
   int attuale,passo;
   if ("no".equals(request.getParameter("multiplo"))){
     templ.newBlock("multiploErrore");
   }else if(request.getParameter("multiplo")!=null && !"".equals(request.getParameter("multiplo"))){
	 templ.newBlock("avvisoControlli");
	 templ.set("avvisoControlli.alertAvvisoControlli",request.getParameter("multiplo"));
   }
   
   try{
     String temp=request.getParameter("attuale");
     if (temp!=null) attuale=Integer.parseInt(temp);
     else attuale=1;
   }catch(Exception eNum){
     attuale=1;
   }
  /**
   * La porzione di codice seguente permette di gestire una visione dei
   * record selezionati simile al motore di ricerca
   */
   Integer tipoRicercaObj = (Integer)session.getAttribute("tipoRicerca");
   session.removeAttribute("tipoRicerca");
   int tipoRicerca = -1;
   if (tipoRicercaObj != null) tipoRicerca = tipoRicercaObj.intValue();
   etichettaCampioni.setBaseElementi(attuale);
   etichettaCampioni.fillElencoCampioni(tipoRicerca, null);
   int size=etichettaCampioni.size();
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
     }else
        templ.set("elencoCampioni.nonAvanti.nonAvanti","");
      if (size!=etichettaCampioni.getNumRecord()){
        int numPag=((etichettaCampioni.getNumRecord()-1)/passo)+1;
        int pagAtt=(attuale/passo)+1;
        templ.set("elencoCampioni.pagine","Pagina "+pagAtt+"/"+numPag);
      }
      EtichettaCampione e;
      String idRichiesta=null, codiceMateriale=null,statoAttuale=null,note=null, iuv=null,
    		  tipo_pagamento=null,pagamento=null,descStatoPagamento=null;
      for(int i=0;i<size;i++){
        /**
           * Le due righe seguenti servono per gestire i checkBox
           **/
           
        templ.newBlock("@@ricercaJavascript");
        templ.set("ricercaJavascript.idCheck",""+i);

        templ.newBlock("elencoCampioneBody");
        e=etichettaCampioni.get(i);
        idRichiesta = e.getIdRichiesta();
        codiceMateriale = e.getCodiceMateriale();
        iuv = e.getIuv();
        tipo_pagamento = e.getTipoPagamento();
        pagamento = e.getPagamento();
        if(pagamento==null)
        	pagamento="";
        if(pagamento.equals("S"))
            if(e.getTipoPagamento()!=null && e.getTipoPagamento().equals("M1"))
                descStatoPagamento = "Pagata Online";
            else if(e.getTipoPagamento()!=null && e.getTipoPagamento().equals("M3"))
                descStatoPagamento = "Pagata con avviso";
            else
                descStatoPagamento = "Pagata";
        else if(pagamento.equals("G"))
            descStatoPagamento = "Gratuito";
        else
            if(e.getTipoPagamento()!=null && e.getTipoPagamento().equals("M3")
            		&& (e.getDataAnnullamento()==null ||e.getDataAnnullamento().equals("")))
                descStatoPagamento = "Da Pagare - avviso emesso";
            else
                descStatoPagamento = "Da Pagare - avviso da emettere";
        statoAttuale=e.getStatoAttuale();
        note=e.getNote();
        templ.set("elencoCampioni.elencoCampioneBody.idCheck",""+i);
        if(codiceMateriale.equals("ZMA"))
            templ.set("elencoCampioni.elencoCampioneBody.idRichiestaVista",idRichiesta+"A");
        else
        	templ.set("elencoCampioni.elencoCampioneBody.idRichiestaVista",idRichiesta);
        templ.set("elencoCampioni.elencoCampioneBody.idRichiesta",idRichiesta);
        if(idRichiesta.equals(aut.getIdRichiestaChecked())){
        	templ.set("elencoCampioni.elencoCampioneBody.checked","checked");
        	aut.setIdRichiestaChecked("");
        }
        templ.set("elencoCampioni.elencoCampioneBody.data",e.getDataInserimentoRichiesta());
        templ.set("elencoCampioni.elencoCampioneBody.descMateriale",e.getDescMateriale());
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
  }else{
     templ.newBlock("elencoCampioniNo");
  }
%>

<%-- HTMPL genera ora la pagina web e si finalizza così lo stato stazionario --%>

<%= templ.text() %>

