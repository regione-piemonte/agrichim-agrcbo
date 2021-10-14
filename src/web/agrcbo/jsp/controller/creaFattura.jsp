<%@page import="java.util.Iterator"%>
<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*,it.csi.agrc.*" isThreadSafe="true" %>

<%
  it.csi.jsf.htmpl.Htmpl templ=null ;
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

<%
  long idRichieste[]=null;
  boolean val[]=null;
  double imponibili[]=null;
  double iva[]=null;
  String richieste = null;

  try
  {
    richieste = request.getParameter("richieste");
    String valori=request.getParameter("valori");
    if (richieste!=null)
     richieste=richieste.substring(0,richieste.length()-1);
    if (valori!=null)
     valori=valori.substring(0,valori.length()-1);
    idRichieste=Utili.longIdTokenize(richieste,",");
    val=Utili.boolIdTokenize(valori,",");
    imponibili=new double[idRichieste.length];
    iva=new double[idRichieste.length];
    String temp;
    for (int i=0;i<imponibili.length;i++)
    {
      temp=request.getParameter("imponibile"+idRichieste[i]);
      temp=temp.replace(',','.');
      imponibili[i]=Double.parseDouble(temp);
      temp=request.getParameter("iva"+idRichieste[i]);
      temp=temp.replace(',','.');
      iva[i]=Double.parseDouble(temp);
    }
  }
  catch(Exception e) {}

  String cfPartitaIva=request.getParameter("cfPartitaIva");
  String ragioneSociale=request.getParameter("ragioneSociale");
  String indirizzo=request.getParameter("indirizzo");
  String cap=request.getParameter("cap");
  String comuneDesc=request.getParameter("comuneDesc");
  String siglaProvincia=request.getParameter("siglaProvincia");
  String importoSpedizione=request.getParameter("importoSpedizione");
  String data_incasso = request.getParameter("data_incasso");
  
  String url="../view/refertiEmessi.jsp";
  String provenienza=request.getParameter("pagina");	
	if ("ricercaCampioni.jsp".equals(provenienza))
		url="../view/ricercaCampioni.jsp";

  int fattura[] = etichettaCampione.creaFattura(idRichieste, val, cfPartitaIva, ragioneSociale, indirizzo, cap, comuneDesc, siglaProvincia, importoSpedizione, imponibili, iva, data_incasso);
%>

<html>
<head>
<script src="../layout/js/gestione.js"></script>
<script>
  pop('../report/esempioFattura.pdf?idFatturaPDF=<%= fattura[0]%>&annoPDF=<%= fattura[1]%>&richieste=<%=richieste%>',647,480,'pdf');
  window.location.replace("<%= url%>");
</script>
</head>
<body>
</body>
</html>


