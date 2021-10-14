<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*,it.csi.jsf.htmpl.*" isThreadSafe="true" %>

<%
  Htmpl templ = null;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
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

<%
  String pagamento = request.getParameter("pagamento");
  String pagamento_hidden=request.getParameter("pagamento_h");
  if(pagamento==null||pagamento.equals(""))
	  pagamento = pagamento_hidden;
  String note = request.getParameter("note");
  String funzione = request.getParameter("funzione");
  String data_incasso = request.getParameter("data_incasso");
  String data_incasso_hidden=request.getParameter("data_incasso_h");
  if(data_incasso==null||data_incasso.equals(""))
	  data_incasso = data_incasso_hidden;

	if ("modifica".equals(funzione))
	{
		//AGRICHIM-20
		//Funzione che permette che alcuni dati della richiesta di analisi possano essere modificati prima di confermare il completamento del caricamento dell'esito delle analisi sul campione
		etichettaCampione.modificaAnalisi(pagamento,note, data_incasso);
	}
	else
	{
	  /**
	  *  Gestisco il passaggio dei campioni dallo stato di campione di laboratorio
	  * allo stato di referto da emettere
	  */
	  etichettaCampione.accettazioneScartoCampLab(pagamento, note, data_incasso);
	}

  //Utili.forward(request, response, "/jsp/controller/analisiTerminate.jsp");
  // Patch 19/10/2004
  // Quando si mandano avanti delle analisi, si deve restare in "campioni in laboratorio"
  Utili.forward(request, response, "/jsp/view/campioniLaboratorio.jsp");
%>
