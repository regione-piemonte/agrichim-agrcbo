<%@page import="java.util.Iterator"%>
<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>
<%@page import="java.util.HashMap"%>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/firmaScarto.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/controllaUtente.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

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
     id="etichettaCampioni"
     scope="page"
     class="it.csi.agrc.EtichettaCampioni">
    <%
      etichettaCampioni.setDataSource(dataSource);
      etichettaCampioni.setAut(aut);
    %>
</jsp:useBean>

<%
	String idRichieste = request.getParameter("idRichiestaSearch");
	templ.set("idRichiestaSearch", idRichieste);
	etichettaCampioni.selectRichiesteByIdRange(idRichieste);
  int numeroRichiesteSelezionate = etichettaCampioni.size();
  
  if (numeroRichiesteSelezionate > 0)
  {
  	HashMap<String, String> hmVerifica = new HashMap<String, String>();
		EtichettaCampione e;
		for (int i = 0; i < numeroRichiesteSelezionate; i++)
		{
			templ.newBlock("elencoCampioneBody");
			e = etichettaCampioni.get(i);
			templ.set("elencoCampioneBody.idRichiesta", e.getIdRichiesta());
			templ.set("elencoCampioneBody.descMateriale", e.getDescMateriale());
			templ.set("elencoCampioneBody.annoCampione", e.getAnnoCampione());
			templ.set("elencoCampioneBody.numero", e.getNumeroCampione());
			templ.set("elencoCampioneBody.etichetta", e.getDescrizioneEtichetta());
			templ.set("elencoCampioneBody.descLabAnalisi", e.getDescLabAnalisi());
			hmVerifica.put(e.getCodLabAnalisi(), e.getCodLabAnalisi());
		}
		
		if (hmVerifica.size() > 1)
		{
			String error = "Impossibile procedere: tutte le richieste selezionate devono avere lo stesso laboratorio di analisi.";
      Utili.forward(request, response, "/jsp/view/analisiTerminate.jsp?errore=" + error);
      return;
		}
	}

  /**
   * Imposto i campi del firmatario delle'esito
   * */
  String nomeFir, cogFirm, titoloFirm;
  nomeFir = aut.getResponsabile().getNome();
  cogFirm = aut.getResponsabile().getCognome();
  titoloFirm = aut.getResponsabile().getTitoloOnorifico();
  if (nomeFir == null) nomeFir = "";
  if (cogFirm == null) cogFirm = "";
  if (titoloFirm == null) titoloFirm = "";
  templ.bset("firmatario", titoloFirm + " " + cogFirm + " "  +nomeFir);

  String email = "";
  if (numeroRichiesteSelezionate == 1){
  	//E' stata selezionata una sola richiesta
  	email = etichettaCampione.selectForFirmaScarto(idRichieste);
  	
  	templ.bset("codiceMateriale", etichettaCampione.getCodiceMateriale());
  	
	  String statoAnomalia = etichettaCampione.getStatoAnomalia();
	  String pagamento = etichettaCampione.getPagamento();
	  String note = etichettaCampione.getNote();
		if (note == null) note = "";
	
	  templ.bset("note", note);
	  String iuv=etichettaCampione.getIuv();
	  templ.bset("data_incasso", etichettaCampione.getData_incasso()!=null?etichettaCampione.getData_incasso():"");
      if(iuv!=null && !iuv.equals("")){
            if("S".equals(pagamento)){
                templ.bset("iuv","Pagata con IUV: "+etichettaCampione.getIuv());
            }else if ("N".equals(pagamento)){
                templ.bset("iuv","Da pagare con IUV: "+etichettaCampione.getIuv());
            }
        }   
      templ.bset("ckPagamento",pagamento);
	  if ("G".equals(pagamento)){
	  	templ.bset("checkedGratuita", "checked");
	  }else{
			if ("N".equals(pagamento)){
			  templ.bset("checkedDaPagare", "checked");
			}
			else{
			  templ.bset("checkedPagata", "checked");
			  templ.bset("disabled", "disabled");
			}
	  }

	  /**
	  * Se è stato selezionato solo un campione devo anche caricare i dati
	  * relativi allo scarto sospensione, lo stato pagamento dell'analisi e le
	  * note
	  */
	  if (note!=null)  templ.bset("note", note);
	  if ("A".equals(statoAnomalia)) {
	  	templ.bset("checkedAnomalo", "checked");
	  }else {
	   if ("B".equals(statoAnomalia))  {
	     templ.bset("checkedBloccante", "checked");
	   } else {
	     templ.bset("checkedNo", "checked");
	   }
	  }
  }
  else
  {
  	//Sono state richieste più richieste, viene fatta soltanto la verifica che tutti i campi email siano valorizzati, tutti gli altri valori non vengono preimpostati
  	HashMap<String, String> hmEmail = etichettaCampioni.selectEmailForFirmaScartoByIdRichiestaRange(idRichieste); 	    
 		int numeroIndirizziEmail = 0;
 
		Iterator<String> iteratorEmail = hmEmail.keySet().iterator();
		String richiesteIndirizzoEmail = "";
		while (iteratorEmail.hasNext())
		{
			String idRichiesta = iteratorEmail.next();
			String sEmail = hmEmail.get(idRichiesta);
			if (sEmail != null && sEmail != "")
			{
				richiesteIndirizzoEmail += idRichiesta + ",";
				email += sEmail + ",";
				//Vengono conteggiati solo gli indirizzi email valorizzati
				numeroIndirizziEmail++;
			}
		}

 		if (numeroIndirizziEmail < numeroRichiesteSelezionate)
 		{
 			//Nel caso in cui siano state selezionate più richieste di analisi il sistema verifica che il campo email sia valorizzato per le anagrafiche di tutte le richieste selezionate
 			email = null;
 		}
 		else
 		{
 			richiesteIndirizzoEmail = richiesteIndirizzoEmail.substring(0, richiesteIndirizzoEmail.lastIndexOf(","));
 			templ.set("richiesteIndirizzoEmail", richiesteIndirizzoEmail);
 			email = email.substring(0, email.lastIndexOf(","));
 		}
  }

  if (email == null || "".equals(email))
  {
   templ.bset("controlloEmail", "true");
   templ.bset("mailCheckedNo", "checked");
  }
  else
  {
   templ.bset("controlloEmail", "false");
   templ.bset("indirizzoEmail", email);
   templ.bset("mailCheckedSi", "checked");
  }
%>

<%-- HTMPL genera ora la pagina web e si finalizza così lo stato stazionario --%>

<%= templ.text() %>