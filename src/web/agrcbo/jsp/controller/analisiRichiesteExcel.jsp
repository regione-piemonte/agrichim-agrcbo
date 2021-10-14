<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*" isThreadSafe="true" %>
<%@page import="java.util.Vector"%>
<%@page import="org.apache.poi.hssf.usermodel.*"%>

<%
  it.csi.jsf.htmpl.Htmpl templ = null;
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
      etichettaCampioni.setPasso(beanParametriApplication.getMaxRecordXPaginaCampLaboratorio());
    %>
</jsp:useBean>

<%
  //Identificativi richieste selezionate dall'utente
  String sIdRichiesteSelezionate = request.getParameter("idRichiestaSearch");
  boolean verificaLimiteMassimoRecord = false;
  if (! Utili.isEmpty(sIdRichiesteSelezionate))
  {
		//Dati in sessione
		aut.setIdRichieste(sIdRichiesteSelezionate);
		
		//Solo nel caso in cui l'utente abbia selezionato le richieste dall'elenco viene verificato il limite massimo di record da presentare nell'excel
		verificaLimiteMassimoRecord = true;
  }
  else if (! Utili.isEmpty(aut.getIdRichieste()))
  {
		sIdRichiesteSelezionate = aut.getIdRichieste();
  }
  else
  {
  	sIdRichiesteSelezionate = "" + aut.getIdRichiestaCorrente();
  }

	String[] aIdRichiesta = null;
 	if (sIdRichiesteSelezionate.contains(","))
 	{
 		//Più richieste
 		aIdRichiesta = sIdRichiesteSelezionate.split(",");
 	}
 	else
 	{
		//Una sola richiesta
 		aIdRichiesta = new String[1];
 		aIdRichiesta[0] = sIdRichiesteSelezionate;  	
 	}
 	
 	if (verificaLimiteMassimoRecord)
 	{
		//Limite visualizzazione numero record
		String maxRecordEstratti = beanParametriApplication.getNUMR();
		int numRecordCampioniLaboratorio = aIdRichiesta.length;
		if (maxRecordEstratti == null || "".equals(maxRecordEstratti))
		{
			maxRecordEstratti = "" + numRecordCampioniLaboratorio;
		}
		if (numRecordCampioniLaboratorio > Long.parseLong(maxRecordEstratti))
		{
			String errore = "Superato il limite massimo consentito di record da visualizzare (" + maxRecordEstratti + "). Selezionare un numero inferiore di richieste.";
		 	Utili.forward(request, response, "/jsp/view/campioniLaboratorio.jsp?errore=" + errore);//'E stato superato il limite massimo consentivo di record da visualizzare: ' + maxRecordEstratti");
		 	return;
		} 	
 	}
	
	Vector<Long> idRichieste = new Vector<Long>();
  for (String idRichiesta : aIdRichiesta)
  {
  	idRichieste.add(new Long(idRichiesta));
  }	

	//Lettura dati elenco analisi richieste
	Vector<Object[]> elenco = etichettaCampioni.esportaElencoAnalisiRichieste(idRichieste);	

  HSSFWorkbook workBook = creaExcel(elenco);
  response.reset();
  out.clear();
  response.setContentType("application/x-download");
  response.setHeader("Content-disposition", "attachment;filename=elencoAnalisiRichieste.xls");
  workBook.write(response.getOutputStream());
 // response.getOutputStream().flush();
  response.flushBuffer();
  //return;
%>

<%!
  private HSSFWorkbook creaExcel(Vector<Object[]> elenco)
  {
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
      //"Numero richiesta",							
      "Anno",														"Num. campione",
      "Etichetta campione",						
      //"Data richiesta",									
      "Data accettazione",
		//	"Comune campione",							"Sigla provincia campione",			
		"Laboratorio di consegna",
			"Coltura attuale",							
			//"Coltura prevista",								
			//"Analisi Tipo",
			//"Compl. scambio",							
			"pH",															"N tot",
			"S.O.",													"CSC",														"Ca scamb",
			"Mg scamb",											"K scamb",												"P ass",
			"CaCO3 totale",									"CaCO3 attivo",										"Gran. STD",
			"Gran. 4FRA",										"Gran. 5FRA",											"Salinità",
			//"Microelem.",										
			"Fe ass",													"Mn ass",
			"Zn ass",												"Cu ass",													"B solubile",
			"Fe tot",												"Cu tot",													"Zn tot",
			"Mn tot",												"Cd tot",													"Cr tot",									
			"Ni tot",												"Pb tot",													"Concimazione organica",																																																					
			"Note aggiuntive",							"Note del richiedente",						"Note stato richiesta"
    }
    , sheet, currentRow, styleFontBold);

    
    //Elenco
    for (Object[] riga : elenco)
    {    				
    	//Viene aggiunta una nuova riga con le colonne valorizzate
    	
			currentRow = ExcelUtils.newRowCellString(riga, sheet, currentRow, styleFontDefault);
    	
    }

		//Allineamento centrato delle colonne contenenti la X
		//int[] cols = new int[30];
		int[] cols = new int[27];   //jira 121 ho tolto 3 colonne con la X
		for (int c = 0; c < 27; c++)
		{
			//cols[c] = c + 11;
			cols[c] = c+ 6;
		}
		
		
		
		
		ExcelUtils.setCellsStyleAlignment(workBook, ExcelUtils.ALIGN_CENTER, 1, elenco.size(), cols);

    //Formato pagina
    sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
    sheet.getPrintSetup().setLandscape(true);

    //Ridimensionamento automatico colonne
    ExcelUtils.autosizeColumns(workBook);

		return workBook;
  }
%>