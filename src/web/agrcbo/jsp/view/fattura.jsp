<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/fattura.htm");
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
    %>
</jsp:useBean>

<jsp:useBean
     id="datiFattura"
     scope="page"
     class="it.csi.agrc.DatiFattura">
    <%
      datiFattura.setDataSource(dataSource);
      datiFattura.setAut(aut);
    %>
</jsp:useBean>

<jsp:useBean
     id="storicoIva"
     scope="page"
     class="it.csi.agrc.StoricoIva">
    <%
    	storicoIva.setDataSource(dataSource);
    	storicoIva.setAut(aut);
    %>
</jsp:useBean>

<%
	String provenienza=request.getParameter("pagina");	
	if ("ricercaCampioni.jsp".equals(provenienza))
	{	
		templ.bset("colorMenuRicCam","#FFE193");
		templ.bset("colorMenuRefEm","#FFFFFF");
		templ.bset("titoloPagina","Campioni - Fatturazione");
		templ.bset("buttonAnnulla","ricercaCampioni.jsp");
		templ.newBlock("blkMenuRicCamp");
		templ.bset("pagina","ricercaCampioni.jsp");
	}
	else
	{
		templ.bset("colorMenuRicCam","#FFFFFF");
		templ.bset("colorMenuRefEm","#FFE193");
		templ.bset("titoloPagina","Referti emessi - Fatturazione");
		templ.bset("buttonAnnulla","refertiEmessi.jsp");
		templ.newBlock("blkMenuRefEm");
		templ.bset("pagina","");
	}

   String idRichieste=request.getParameter("idRichiestaSearch");
   double importoSpedizione=etichettaCampioni.selectForFattura(idRichieste);
   boolean pagata=false;
   boolean nonPagata=false;
   double imponibile;
   double iva;
   double costo;
   double totaleImponibile=0;
   double ivaImponibile=0;
   double totale=0;
   String idRichiesta=null,fatturareA=null;

   int size=etichettaCampioni.size();
   if ( size>0 )
   {
    EtichettaCampione e;
    for(int i=0;i<size;i++)
    {
      templ.newBlock("elencoCampioneBody");
      e=etichettaCampioni.get(i);
      templ.set("elencoCampioneBody.idRichiesta",e.getIdRichiesta());
      templ.set("elencoCampioneBody.descMateriale",e.getDescMateriale());
      templ.set("elencoCampioneBody.descrizioneEtichetta",e.getDescrizioneEtichetta());
      templ.set("elencoCampioneBody.proprietario",e.getProprietario());
      try
      {
        costo=Double.parseDouble(e.getCosto());
      }
      catch(Exception eNum)
      {
        costo=0;
      }
      storicoIva.selectStoricoIvaByData(null);
      double valoreIva = (100 + Double.parseDouble(storicoIva.getValore())) / 100;
      imponibile=costo/valoreIva;
      imponibile=Double.parseDouble((Utili.nf2.format(imponibile)).replace(',','.'));
      totaleImponibile+=imponibile;
      iva=costo-imponibile;
      ivaImponibile+=iva;
      templ.set("elencoCampioneBody.imponibile",Utili.nf2.format(imponibile).replace('.',','));
      templ.set("elencoCampioneBody.iva",Utili.nf2.format(iva).replace('.',','));
      if ("N".equals(e.getPagamento()))
      {
        nonPagata=true;
      }
      else
      {
        pagata=true;
        templ.set("elencoCampioneBody.checked","checked");
      }
      idRichiesta=e.getIdRichiesta();
      fatturareA=e.getFatturareA();
    }
    templ.bset("totaleImponibile",Utili.nf2.format(totaleImponibile).replace('.',','));
    templ.bset("totaleIva",Utili.nf2.format(ivaImponibile).replace('.',','));
    templ.bset("IVA1",storicoIva.getIvaFormattata());
    templ.bset("importoSpedizione",Utili.nf2.format(importoSpedizione).replace('.',','));
    totale=totaleImponibile+ivaImponibile+importoSpedizione;
    templ.bset("totale",Utili.nf2.format(totale).replace('.',','));
    if (pagata && nonPagata)
    {
      templ.bset("checkedParzialmente","checked");
    }
    else
    {
      if (pagata)
        templ.bset("checkedPagata","checked");
      else
        templ.bset("checkedNonPagate","checked");
    }
  }

  //Leggo i dati dell'intestatario della fattura
  datiFattura.selectIntestatarioFattura(idRichiesta,fatturareA);
  templ.newBlock("intestatarioFattura");
  templ.set("intestatarioFattura.cfPartitaIva",datiFattura.getCfPartitaIva());
  templ.set("intestatarioFattura.ragioneSociale",datiFattura.getRagioneSociale());
  templ.set("intestatarioFattura.indirizzo",datiFattura.getIndirizzo());
  templ.set("intestatarioFattura.cap",datiFattura.getCap());
  templ.set("intestatarioFattura.comuneDesc",datiFattura.getComuneDesc());
  templ.set("intestatarioFattura.siglaProvincia",datiFattura.getSiglaProvincia());
  templ.set("data_incasso",datiFattura.getDataIncasso());

%>

<%-- HTMPL genera ora la pagina web e si finalizza così lo stato stazionario --%>

<%= templ.text() %>

