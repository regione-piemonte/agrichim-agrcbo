<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>
<%@page import="java.util.Vector"%>
<%@page import="org.apache.poi.hssf.usermodel.*"%>
<%@page import="org.apache.poi.ss.util.*"%>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/ricercaCampioni.htm");
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="beanRicerca"
     scope="session"
     class="it.csi.agrc.BeanRicerca"/>
     
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

  String funzione = (String) request.getParameter("funzione");
  if ("excel".equals(funzione) || "excel_contabile".equals(funzione)){
	   //Limite visualizzazione numero record
	   String maxRecordEstratti = null;
	  if("excel".equals(funzione))
		  maxRecordEstratti = beanParametriApplication.getNUMR();
	  else
		  maxRecordEstratti = beanParametriApplication.getNUMC();
		CuneoLogger.debug("view/ricercaCampioni.jsp", "NUMR="+maxRecordEstratti);
		int numRecordRicercaCampioni = session.getAttribute("numRecordRicercaCampioni") != null ? ((Integer) session.getAttribute("numRecordRicercaCampioni")).intValue() : 0;
		session.removeAttribute("numRecordRicercaCampioni");
		if (maxRecordEstratti == null || "".equals(maxRecordEstratti)){
			maxRecordEstratti = "" + numRecordRicercaCampioni;
		}
		if (numRecordRicercaCampioni > Long.parseLong(maxRecordEstratti)){
			errore = "Superato il limite massimo consentito di record da visualizzare (" + maxRecordEstratti + "). Impostare un filtro di ricerca più restrittivo.";
		 	Utili.forward(request, response, "/jsp/view/ricercaCampioni.jsp?errore=" + errore);//'E stato superato il limite massimo consentivo di record da visualizzare: ' + maxRecordEstratti");
		 	  return;
		}

	    //Esporta richieste
		etichettaCampioni.fillElencoCampioni(Boolean.TRUE);
		int size = etichettaCampioni.size();
		//Lettura dati esporta richieste
		Vector<Long> idRichieste = new Vector<Long>();
	    for (int i = 0; i < size; i++){
	      templ.newBlock("elencoCampioneBody");
	      idRichieste.add(Long.parseLong(etichettaCampioni.get(i).getIdRichiesta()));
	    }
	    HSSFWorkbook workBook = null;
	    if("excel".equals(funzione)){
	    	Vector<Object[]> elenco = etichettaCampioni.esportaRichieste(idRichieste);
	    	workBook = creaExcel(elenco);
	    	response.reset();
	        response.setContentType("application/x-download");
	        response.setHeader("Content-disposition", "attachment;filename=elencoRichiesteAnalisi.xls");
	        workBook.write(response.getOutputStream());
	        response.getOutputStream().flush();
	    }else if("excel_contabile".equals(funzione)){
	    	Vector<Object[]> elenco = etichettaCampioni.esportaContabile(beanParametriApplication, idRichieste);
	    	workBook = creaExcelContabile(elenco);
	    	response.reset();
	        response.setContentType("application/x-download");
	        response.setHeader("Content-disposition", "attachment;filename=ScaricoContabile.xls");
	        workBook.write(response.getOutputStream());
	        response.getOutputStream().flush();
	    }
    return;
  }

   int attuale,passo;
   if ("no".equals(request.getParameter("multiplo"))) {
     templ.newBlock("singolo");
     templ.newBlock("multiploErrore");
   }
   if ("si".equals(request.getParameter("multiplo"))) {
     templ.newBlock("multipli");
     templ.newBlock("multiploErrore");
   }
   try{
     String temp=request.getParameter("attuale");
     if (temp!=null) attuale=Integer.parseInt(temp);
     else attuale=1;
   }catch(Exception eNum) {
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
   String  iuv=null, tipo_pagamento=null,pagamento=null,descStatoPagamento=null;
   if ( size>0 ){
     passo=etichettaCampioni.getPasso();
     templ.newBlock("elencoCampioni");
     templ.set("elencoCampioni.num",""+etichettaCampioni.getNumRecord());
     session.setAttribute("numRecordRicercaCampioni", etichettaCampioni.getNumRecord());
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
      if (size!=etichettaCampioni.getNumRecord()) {
        int numPag=((etichettaCampioni.getNumRecord()-1)/passo)+1;
        int pagAtt=(attuale/passo)+1;
        templ.set("elencoCampioni.pagine","Pagina "+pagAtt+"/"+numPag);
      }
      EtichettaCampione e;
      String idRichiesta=null, statoAttuale=null;
      for(int i=0;i<size;i++){
        templ.newBlock("elencoCampioneBody");
        e=etichettaCampioni.get(i);
        idRichiesta = e.getIdRichiesta();
        codiceMateriale = e.getCodiceMateriale();
        statoAttuale=e.getStatoAttuale();
        iuv = e.getIuv();
        tipo_pagamento = e.getTipoPagamento();
        pagamento = e.getPagamento()!=null?e.getPagamento():"";
        /*
        if (i==0)
        {
          templ.newBlock("elencoSi");
          templ.set("elencoSi.idRichiestaPrimo",idRichiesta);
          templ.set("elencoSi.codiceMaterialePrimo",codiceMateriale);
          templ.set("elencoSi.statoCampione",statoAttuale);
          templ.set("elencoCampioni.elencoCampioneBody.checked","checked");
        }
        */
        if(e.getCodiceMateriale().equals("ZMA"))
        	templ.set("elencoCampioni.elencoCampioneBody.idRichiesta",idRichiesta+"A");
        else
        	templ.set("elencoCampioni.elencoCampioneBody.idRichiesta",idRichiesta);
        templ.set("elencoCampioni.elencoCampioneBody.data",e.getDataInserimentoRichiesta());
        templ.set("elencoCampioni.elencoCampioneBody.descMateriale",e.getDescMateriale());
       
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
        
        if(e.getAnnoCampione()!=null && !"".equals(e.getAnnoCampione()) &&
          e.getNumeroCampione()!=null && !"".equals(e.getNumeroCampione()))
        {
          templ.set("elencoCampioni.elencoCampioneBody.numeroAnno",e.getNumeroCampione()+"/"+e.getAnnoCampione());
        }
        templ.set("elencoCampioni.elencoCampioneBody.statoCampione",statoAttuale);
        templ.set("elencoCampioni.elencoCampioneBody.descrizioneEtichetta",e.getDescrizioneEtichetta());
        templ.set("elencoCampioni.elencoCampioneBody.descStatoAttuale",e.getDescStatoAttuale());
        templ.set("elencoCampioni.elencoCampioneBody.proprietario",e.getProprietario());
        templ.set("elencoCampioni.elencoCampioneBody.richiedente",e.getRichiedente());
        templ.set("elencoCampioni.elencoCampioneBody.codiceMateriale",codiceMateriale);
        templ.set("elencoCampioni.elencoCampioneBody.iuv",iuv);
        templ.set("elencoCampioni.elencoCampioneBody.tipo_pagamento",tipo_pagamento);
        templ.set("elencoCampioni.elencoCampioneBody.pagamento",pagamento);
        templ.set("elencoCampioni.elencoCampioneBody.descStatoPagamento",descStatoPagamento);
      }
  }else{
     templ.newBlock("elencoCampioniNo");
  }
  //Carico i dati di tutti i materiali per visualizzarli nella combo
   String codStr[],descStr[];
   codStr=beanTipoCampione.getCodMateriale();
   descStr=beanTipoCampione.getDescMateriale();
   BeanRicerca br = (BeanRicerca)session.getAttribute("beanRicerca");
   if(br!=null && br.getTipoMateriale().equals(""))
	   codiceMateriale = br.getTipoMateriale();
   //codiceMateriale = (codiceMateriale == null || "".equals(codiceMateriale)) ? Constants.MATERIALE.CODICE_MATERIALE_TERRENI : codiceMateriale;
   for(int i=0;i<codStr.length;i++){
     if (codStr[i].equals(codiceMateriale)){
       templ.set("tipoMateriale.selected", "selected");
     }else{
       templ.set("tipoMateriale.selected", "");
     }
     templ.set("tipoMateriale.codiceMateriale",codStr[i]);
     templ.set("tipoMateriale.descrizione",descStr[i]);
   }
%>

<%-- HTMPL genera ora la pagina web e si finalizza così lo stato stazionario --%>

<%= templ.text() %>

<%!
  private HSSFWorkbook creaExcel(Vector<Object[]> elenco){
    HSSFWorkbook workBook = new HSSFWorkbook();
    HSSFSheet sheet = workBook.createSheet("ElencoRichiesteAnalisi");
    int currentRow = 0;
    //Larghezza default colonne
    sheet.setDefaultColumnWidth(10);
    //Font normale
    HSSFCellStyle styleFontDefault = ExcelUtils.styleFontDefault(workBook);
    //Font grassetto
    HSSFCellStyle styleFontBold = ExcelUtils.styleFontBold(workBook);
    //Intestazione
    currentRow = ExcelUtils.newRowCellString(new String[]
    {
			"Num. richiesta",		"Anno",		"Num. campione",
			"Etichetta",	"Laboratorio consegna", "Tipo consegna",
			"Analisi svolta per adempimenti PSR", "Motivazione adempimento PSR", "Note del richiedente",
      "Data accettazione", "Data emissione referto", "Stato richiesta",
      "Note stato richiesta", "Organizzazione", "Tecnico",
      "Utente inserim. dati", "Pagamento analisi", "Anomalia scarto/sospensione",
      "Fattura richiesta", "Spedizione fattura", "Intestazione fattura",
      "Fattura emessa", "Numero fattura", "Comune campione",
      "Sigla provincia campione", "Sezione", "Foglio",
      "Particella catastale principale", "Altre particelle secondarie", "Coord. Nord UTM",
      "Coord. Est UTM", "Coord. Nord Boaga", "Coord. Est Boaga",
      "Tipo coord. geografiche", "Coord. Geogr. Nord Gradi", "Coord. Geogr. Nord Minuti",
      "Coord. Geogr. Nord Secondi", "Coord. Geogr. Est Gradi", "Coord. Geogr. Est Minuti",
      "Coord. Geogr. Est Secondi", "Proprietario", "Profondità prelievo",
       "Giacitura", "Coltura attuale", "Coltura prevista",
      "Presenza pietre o ghiaie", "Interramento stoppie", "Concimazione organica",
      "Concime utilizzato", "Stato del terreno", "Irrigazione",
      "Modalità di coltivazione",
      //"Analisi Tipo",	"Compl. scambio",
      "Analisi pH",	"Analisi Ca", "Analisi Mg",
      "Analisi K", "Analisi N", "Analisi P",
      "Analisi CSC", "Analisi sost. org.", "Analisi CaCO3",
      "Analisi CaAtt", "Analisi Std", "Analisi 4Fra",
      "Analisi 5Fra", "Analisi Sal", 
      //"Microelementi",
      "Analisi Fe assimilabile", "Analisi Mn assimilabile", "Analisi Zn assimilabile",
      "Analisi Cu assimilabile", "Analisi B solubile", 
      //"Analisi umidità",
      "Ferro totale", "Rame totale", "Zinco totale",
      "Manganese totale", "Cadmio totale", "Cromo totale",
      "Nichel totale", "Piombo totale", "Note aggiuntive",
     // "Umidità (%)", "Sostanza secca (%)", 
      "Argilla (%)",
      "Limo totale (%)", "Limo grosso (%)", "Limo fine (%)",
      "Sabbia totale (%)", "Sabbia grossa (%)", "Sabbia fine (%)",
      "pH (acqua)", "pH cloruro potassio", "pH tampone",
      "Conduc. (salinità ) (µS/cm)", "Calcare totale (%)", "Calcare attivo (%)",
      "Sostanza org. (%)", "Carbonio org. (%)", "Azoto totale (%)",
      "Rapporto C/N", "CSC (meq/100g)", "Calcio scamb. (ppm)",
      "Calcio scamb. (meq/100g)", "Calcio scamb. su CSC (%)", "Magnesio scamb. (ppm)",
      "Magnesio scamb. (meq/100g)", "Magnesio scamb. su CSC (%)", "Potassio scamb. (ppm)",
      "Potassio scamb. (meq/100g)", "Potassio scamb. su CSC (%)", "Sodio scamb. (ppm)",
      "Sodio scamb. (meq_100g)", "Sodio scamb. su CSC (%)", "Rapporto Ca/Mg",
      "Rapporto Ca/K", "Rapporto Mg/K", "Saturaz. basica",
      "Fosforo assim. (ppm)", "Anidride fosforica assim. (ppm)", "Ferro assim. (ppm)",
      "Manganese assim. (ppm)", "Zinco assim. (ppm)", "Rame assim. (ppm)",
      "Boro solubile (ppm)", "Ferro totale (ppm)", "Manganese totale (ppm)",
      "Zinco totale (ppm)", "Rame totale (ppm)", "Piombo totale (ppm)",
      "Cromo totale (ppm)", "Nichel totale (ppm)", "Cadmio totale (ppm)",
      "Note laboratorio"
    }
    , sheet, currentRow, styleFontBold);
    //Elenco
    for (Object[] riga : elenco){    				
    	//Viene aggiunta una nuova riga con le colonne valorizzate
			currentRow = ExcelUtils.newRowCellString(riga, sheet, currentRow, styleFontDefault);
    }
	//Allineamento centrato delle colonne contenenti la X
	int[] cols = new int[31];
	for (int c = 0; c < 31; c++){
		cols[c] = c + 52;
	}
	ExcelUtils.setCellsStyleAlignment(workBook, ExcelUtils.ALIGN_CENTER, 1, elenco.size(), cols);
    //Formato pagina
    sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
    //Ridimensionamento automatico colonne
    ExcelUtils.autosizeColumns(workBook);

		return workBook;
  };
%>
<%! private HSSFWorkbook creaExcelContabile(Vector<Object[]> elenco){
	  HSSFWorkbook workBook = new HSSFWorkbook();
	  HSSFSheet sheet = workBook.createSheet("ScaricoContabile");
	  int currentRow = 0;
	
	  //Larghezza default colonne
	  sheet.setDefaultColumnWidth(10);
	  //Font normale
	  HSSFCellStyle styleFontDefault = ExcelUtils.styleFontDefault(workBook);
	  //Font grassetto
	  HSSFCellStyle styleFontBold = ExcelUtils.styleFontBold(workBook);
	  //Intestazione
	  currentRow = ExcelUtils.newRowCellString(new String[]{
			  "Materiale",
			  "Data pagamento IUV", 
			  "Importo da pagare", 
			  "Imponibile",
			  "IVA",
			  "Num. richiesta",
			  "IUV", 
			  "CF-PIVA pagatore IUV",
			  "Fattura richiesta",
			  "Denominazione in fattura",
			  "Partita Iva o CF", 
			  "CUAA SIAP",
			  "Partita Iva SIAP",
              "CF SIAP",
              "Codice Contabilia in anagrafica",
              "Denominazione SIAP",
			  "Codice Destinatario in fattura",
			  "Codice Destinatario in anagrafica",
			  "PEC in fattura",
			  "Indirizzo in fattura",
			  "Comune",
              "CAP",
              "Provincia",
              "Indirizzo SIAP",
              "Comune SIAP",
              "CAP SIAP",
              "Provincia SIAP",
			  "Organizzazione",
			  "Tecnico",
			  "Utente inserim. dati", 
			  "Pagamento analisi",
			  "Data Incasso",
			  "Esito generazione IUV",
			  "Tipo pagamento IUV",
			  "Data inserimento IUV",
			  "Data annullamento IUV",
			  "Num. campione", 
			  "Anno",
			  "Stato richiesta",
			  "Data Richiesta",
			  "Laboratorio consegna",
			  "Data accettazione",
			  "Etichetta",
			  "Data emissione referto",
			  "Anomalia scarto/sospensione",
			  "Fattura emessa",
			  "Numero fattura",
			  "Intestazione fattura",
			  "PEC in anagrafica",
			  "PEC SIAP",
			  "Note del richiedente",
			  "Note stato richiesta",
			  "Note laboratorio",
			  "Numero preventivo","Codice fiscale","Importo preventivo","Note del LAR"
	  }, sheet, currentRow, styleFontBold);
	  //Elenco
	  for (Object[] riga : elenco) {                   
	      //Viene aggiunta una nuova riga con le colonne valorizzate
          currentRow = ExcelUtils.newRowCellString(riga, sheet, currentRow, styleFontDefault);
	  }
      //Allineamento centrato delle colonne contenenti la X
      int[] cols = new int[31];
      for (int c = 0; c < 31; c++){
          cols[c] = c + 52;
      }
      //ExcelUtils.setCellsStyleAlignment(workBook, ExcelUtils.ALIGN_CENTER, 1, elenco.size(), cols);
	  //Formato pagina
	  sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
	  //Ridimensionamento automatico colonne
	  ExcelUtils.autosizeColumns(workBook);
      return workBook;
   };
%>