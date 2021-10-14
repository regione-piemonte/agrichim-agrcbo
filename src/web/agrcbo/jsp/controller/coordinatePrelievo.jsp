<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.cuneo.*" isThreadSafe="true" %>
<%@ page import="it.csi.agrc.*" isThreadSafe="true" %>
<%@ page import="it.csi.iride2.policy.entity.Identita" %>



<%
  it.csi.jsf.htmpl.Htmpl templ=null ;
%>

<%@ include file="/jsp/controllaLogin.inc" %>
<%@ include file="/jsp/dataSource.inc" %>

<jsp:useBean
     id="datiAppezzamento"
     scope="request"
     class="it.csi.agrc.DatiAppezzamento">
    <%
      datiAppezzamento.setDataSource(dataSource);
      datiAppezzamento.setAut(aut);
    %>
</jsp:useBean>
<jsp:setProperty name="datiAppezzamento" property="*"/>


<%
    String metodo = request.getParameter("metodo");
    
    Identita identita = (Identita) session.getAttribute("identita"); 

	//Se sono state impostate le coordinate in gradi mi ricavo quelle utm
	if (!Utili.isEmpty(datiAppezzamento.getGradiNord()))
	{
		  it.csi.cuneo.Coordinate coord=null;
		  if (DatiAppezzamento.GRADI_DECIMALI.equals(datiAppezzamento.getCoordGradi()))
		  {
			  double phiGradi=Double.parseDouble(datiAppezzamento.getGradiNord()+"."+datiAppezzamento.getDecimaliNord());
			  double landaGradi=Double.parseDouble(datiAppezzamento.getGradiEst()+"."+datiAppezzamento.getDecimaliEst());
			  coord=new it.csi.cuneo.Coordinate(phiGradi,landaGradi);
		  }
		  if (DatiAppezzamento.GRADI_MINUTI_DECIMALI.equals(datiAppezzamento.getCoordGradi()))
		  {
			  double phiGradi=Double.parseDouble(datiAppezzamento.getGradiNord());
			  double phiMinuti=Double.parseDouble(datiAppezzamento.getMinutiNord()+"."+datiAppezzamento.getDecimaliNord());
			  double landaGradi=Double.parseDouble(datiAppezzamento.getGradiEst());
			  double landaMinuti=Double.parseDouble(datiAppezzamento.getMinutiEst()+"."+datiAppezzamento.getDecimaliEst());
			  coord=new it.csi.cuneo.Coordinate(phiGradi, phiMinuti, landaGradi, landaMinuti);
		  }
		  if (DatiAppezzamento.GRADI_MINUTI_SECONDI.equals(datiAppezzamento.getCoordGradi()))
		  {
			  double phiGradi=Double.parseDouble(datiAppezzamento.getGradiNord());
			  double phiMinuti=Double.parseDouble(datiAppezzamento.getMinutiNord());
			  double phpSecondi=Double.parseDouble(datiAppezzamento.getSecondiNord()+"."+datiAppezzamento.getDecimaliNord());
			  double landaGradi=Double.parseDouble(datiAppezzamento.getGradiEst());
			  double landaMinuti=Double.parseDouble(datiAppezzamento.getMinutiEst());
			  double landaSecondi=Double.parseDouble(datiAppezzamento.getSecondiEst()+"."+datiAppezzamento.getDecimaliEst());
			  coord=new it.csi.cuneo.Coordinate(phiGradi, phiMinuti, phpSecondi, landaGradi, landaMinuti, landaSecondi);
		  }
		  coord.convertiRadiantiToUtm();
		  datiAppezzamento.setCoordinataNordUtm(""+ new Double(coord.getUtmNord()).longValue());
		  datiAppezzamento.setCoordinataEstUtm(""+ new Double(coord.getUtmEst()).longValue());
	}

	String errore = null;
  /**
  * Controllo gli eventuali errori tramite jsp
  * */
  System.out.println("metodo ---> " + metodo);
  
    errore=datiAppezzamento.ControllaDati(metodo);
  
  /**
  * Se c'è un errore vuol dire che non è stato eseguito corretamente il
  * javascript
  */
  if (errore!=null)
  {
    Utili.forwardConParametri(request, response, "/jsp/view/coordinatePrelievo.jsp?modifica=no&errore="+errore);
    return;
  }
  
  /*
	Alla conferma da parte dell'utente, nel caso in cui il comune indicato sia un comune piemontese e nel caso in cui siano stati 
	inseriti i dati catastali ma non le coordinate geografiche, il sistema calcola il centroide della particella in coordinate UTM.
  */
  System.out.println("idRegione " + datiAppezzamento.getPiemonte());
	if (datiAppezzamento.isRegionePiemonte())
	{
		System.out.println("la regione è Piemonte");
	  if (datiAppezzamento.getCoordinataEstUtm()==null && datiAppezzamento.getCoordinataNordUtm()==null)	  
	  {
		    boolean trovato = datiAppezzamento.ricavaUTMFromDatiCatastali(datiAppezzamento.getIstatComune(), beanParametriApplication, identita.getCodFiscale());
	    
	      if(!trovato)
	      {
	    	  //jira 106
	    	  //I dati catastali inseriti non sono coerenti con il comune di prelievo indicato
	    	  if(metodo.equals(""))
	    	  {
	    	  	errore = ";2;3";
	    	  	Utili.forwardConParametri(request, response, "/jsp/view/coordinatePrelievo.jsp?modifica=no&erroreCongruenzaSigmaterCatastali="+errore);
			 	return;
	    	  }
	      }
	  }
	  else
	  {
		  if(metodo.equals(""))
		  {
			  errore=datiAppezzamento.verificaUTMInserite(datiAppezzamento.getIstatComune(), beanParametriApplication, identita.getCodFiscale());
			  System.out.println("ERRORE " + errore);
			  if (errore!=null)
			  {
				  //coordinate non appartenenti al comune
				  Utili.forwardConParametri(request, response, "/jsp/view/coordinatePrelievo.jsp?modifica=no&erroreCongruenzaSigmater="+errore);
				  return;
			  }
		  }
	  }
	}
  
  if(metodo.equals("calcolaUTM"))
  {
	  String p="";
	  Utili.forwardConParametri(request, response, "/jsp/view/coordinatePrelievo.jsp?parametro="+p);
	  return;
  }

  datiAppezzamento.update();
  Utili.forwardConParametri(request, response, "/jsp/view/coordinatePrelievo.jsp?modifica=si");

%>


