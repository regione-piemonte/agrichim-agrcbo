<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*" isThreadSafe="true" %>
<%
  it.csi.jsf.htmpl.Htmpl templ=null ;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/controllaUtente.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="campioneFatturato"
     scope="page"
     class="it.csi.agrc.CampioneFatturato">
    <%
      campioneFatturato.setDataSource(dataSource);
      campioneFatturato.setAut(aut);
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
     id="analisi"
     scope="page"
     class="it.csi.agrc.Analisi">
    <%
      analisi.setDataSource(dataSource);
      analisi.setAut(aut);
      analisi.setIdRichiesta(aut.getIdRichiestaCorrente());
    %>
</jsp:useBean>

<%
	request.setAttribute("iva0", beanParametriApplication.getIVA0());
	request.setAttribute("iva1", beanParametriApplication.getIVA1());
	String costoSpedizione = analisi.getCostoSpedizione();
	if (costoSpedizione == null)
	{
		costoSpedizione = "0.00";
	}
	request.setAttribute("costoSpedizione", costoSpedizione.replace('.', ','));

	int[] fattura = null;
  String funzione = request.getParameter("funzione");
	if ("conferma".equals(funzione))
	{
		datiFattura.setDescrizione(request.getParameter("descrizione"));
	  datiFattura.setCfPartitaIva(Utili.checkNull(request.getParameter("cfPartitaIva")).toUpperCase());
	  datiFattura.setRagioneSociale(request.getParameter("ragioneSociale"));
	  datiFattura.setIndirizzo(request.getParameter("indirizzo"));
	  datiFattura.setCap(request.getParameter("cap"));
	  datiFattura.setComune(request.getParameter("comune"));
	  datiFattura.setComuneDesc(request.getParameter("comuneDesc"));
	  datiFattura.setSiglaProvincia(request.getParameter("siglaProvincia"));
	  datiFattura.setPagata(request.getParameter("pagata"));
	  campioneFatturato.setImportoSpedizione(request.getParameter("costoSpedizione"));
	  campioneFatturato.setImportoImponibile(request.getParameter("importoImponibile"));
	  campioneFatturato.setImportoIva(request.getParameter("importoIva"));

		//Modifica dati fattura per le richieste selezionate dall'utente
		fattura = datiFattura.creaFatturaInBianco(datiFattura.getCfPartitaIva(),
                          										datiFattura.getRagioneSociale(),
                          										datiFattura.getIndirizzo(),
										                          datiFattura.getCap(),
										                          datiFattura.getComuneDesc(),
										                          datiFattura.getSiglaProvincia(),
										                          datiFattura.getPagata(),
										                          datiFattura.getDescrizione(),
										                          campioneFatturato.getImportoSpedizione(),
										                          campioneFatturato.getImportoImponibile(),
										                          campioneFatturato.getImportoIva()
                         );
	}
	else
	{
   	Utili.forwardConParametri(request, response, "/jsp/view/fatturaInBianco.jsp");
	}
%>

<html>
<head>
<script src="../layout/js/gestione.js"></script>
<script>
	<% if (fattura != null) { %>
			  pop('../report/esempioFattura.pdf?idFatturaPDF=<%= fattura[0]%>&annoPDF=<%= fattura[1]%>',647,480,'pdf');
			  window.location.replace("../view/gestioneFatture.jsp");
  <% } %>
</script>
</head>
<body>
</body>
</html>