<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/esistoTerminato.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="tipoCampione"
     scope="page"
     class="it.csi.agrc.TipoCampione">
    <%
      tipoCampione.setDataSource(dataSource);
      tipoCampione.setAut(aut);
    %>
</jsp:useBean>

<jsp:useBean
     id="etichettaCampione"
     scope="page"
     class="it.csi.agrc.EtichettaCampione">
    <%
      etichettaCampione.setDataSource(dataSource);
      etichettaCampione.setAut(aut);
    %>
</jsp:useBean>

<jsp:useBean
     id="controlloEsistoTerminato"
     scope="page"
     class="it.csi.agrc.ControlloEsistoTerminato">
    <%
      controlloEsistoTerminato.setDataSource(dataSource);
      controlloEsistoTerminato.setAut(aut);
    %>
</jsp:useBean>

<%
  String idRichiesta=request.getParameter("idRichiestaSearch");
  /**
  *  Se idRichiesta è null significa che sono arrivato in questa pagina non
  * direttamente dalla pagina di ricerca ma passando per quella di inserisci
  * esito analisi. In questo ultimo caso devo quindi andare a leggere il
  * valore memorizzato nella sessione
  */
  if (idRichiesta==null) 
	  idRichiesta=aut.getIdRichiestaCorrente()+"";
  else {
    long longRichiesta=0;
     try {
       longRichiesta=Long.parseLong(idRichiesta);
       aut.setIdRichiestaCorrente(longRichiesta);
       tipoCampione.setIdRichiesta(longRichiesta);
       tipoCampione.select();
       aut.setCodMateriale(tipoCampione.getCodMateriale());
       session.setAttribute("aut",aut);
     } catch(Exception e) { }
  }
  templ.bset("idRichiestaSearch",idRichiesta);
  controlloEsistoTerminato.setIdRichiesta(idRichiesta);
  StringBuffer errore=new StringBuffer();
  StringBuffer message=new StringBuffer();
  if(!("ZMA").equals(tipoCampione.getCodMateriale())){
	  controlloEsistoTerminato.controllaAnalisi(errore,message);
	  if (!errore.toString().equals("")){
	    templ.newBlock("messaggioErrore");
	    templ.set("messaggioErrore.erroreMsg","Dati non corretti:\\n\\n"+errore.toString());
	  } else {
	    if (!message.toString().equals(""))  {
	      templ.newBlock("messaggioAvvertimento");
	      templ.set("messaggioAvvertimento.avvertimentoMsg","Dati non completi:\\n\\n"+message.toString()+"\\nContinuare comunque?");
	    } else 
	    	templ.newBlock("tuttoOK");
	  }
  }else
	  templ.newBlock("tuttoOK");
  etichettaCampione.selectEsitoTerminato(idRichiesta);
  String pagamento=etichettaCampione.getPagamento();

  templ.bset("codiceMateriale",etichettaCampione.getCodiceMateriale());

  templ.newBlock("campione");
  if(etichettaCampione.getAnnoCampione()!=null && !"".equals(etichettaCampione.getAnnoCampione()) &&
    etichettaCampione.getNumeroCampione()!=null && !"".equals(etichettaCampione.getNumeroCampione()))
  {
    templ.set("campione.numeroAnno",etichettaCampione.getNumeroCampione()+"/"+etichettaCampione.getAnnoCampione());
  }
  templ.set("campione.idRichiesta",etichettaCampione.getIdRichiesta());
  templ.set("campione.descMateriale",etichettaCampione.getDescMateriale());
  templ.set("campione.descrizioneEtichetta",etichettaCampione.getDescrizioneEtichetta());
  templ.set("campione.descLabAnalisi",etichettaCampione.getDescLabAnalisi());
  templ.set("campione.note",etichettaCampione.getNote());
  
  if ("G".equals(pagamento)){
	  templ.set("campione.checkedGratuita","checked");
  }else{   
	  if ("S".equals(pagamento))
		  templ.set("campione.checkedPagata","checked");
	  else
		  templ.set("campione.checkedDaPagare","checked");
  }
  templ.bset("ckPagamento",pagamento);
  if(etichettaCampione.getIuv()!=null && !etichettaCampione.getIuv().equals("")){
	  if("S".equals(pagamento)){
	      templ.set("campione.iuv","Pagata con IUV: "+etichettaCampione.getIuv());
	      templ.set("campione.disabilitato","disabled");
	  }else if("N".equals(pagamento)){
          templ.set("campione.iuv","Da pagare con IUV: "+etichettaCampione.getIuv());
      }
  }
  templ.set("campione.data_incasso",etichettaCampione.getData_incasso());
  
%>

<%-- HTMPL genera ora la pagina web e si finalizza così lo stato stazionario --%>

<%= templ.text() %>

