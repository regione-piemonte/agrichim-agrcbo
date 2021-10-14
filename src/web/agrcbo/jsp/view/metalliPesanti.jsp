<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/metalliPesanti.htm");
%>

<%
  boolean richiestaAnalisi[]=new boolean[12];
  boolean ferro=false,manganese=false,zinco=false,rame=false,boro=false;
  boolean fra4=false,fra5=false,std=false;
  boolean calcio=false,magnesio=false,potassio=false,csc=false,vBACl2=false;

  //Metalli pesanti
  boolean ferroTotale = false, manganeseTotale = false, zincoTotale = false, rameTotale = false, piomboTotale = false, cromoTotale = false,
	boroTotale = false, nichelTotale = false, cadmioTotale = false, stronzioTotale = false, altroMetalloTotale = false;         
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
     id="metalliPesanti"
     scope="request"
     class="it.csi.agrc.MetalliPesanti">
     <%
      metalliPesanti.setDataSource(dataSource);
      metalliPesanti.setAut(aut);
    %>
</jsp:useBean>

<%@ include file="/jsp/view/campioniLaboratorioAnalisiForm.inc" %>
<%@ include file="/jsp/view/abilitaAnalisiTer.inc" %>



<!--
  Il file che includo serve a gestire gli errori dovuti al funzionamento non
  corretto del javascript
-->
<%@ include file="/jsp/view/problemiJavascript.inc" %>

<%
  if (! richiestaAnalisi[Analisi.TER_METALLI_PESANTI])
  {
		/**
		 * Non è stata scelta nessuna analisi, quindi devo solo visualizzare un messaggio di avvertimento
		 */
    templ.newBlock("nessuno");
  }
  else
  {
    templ.newBlock("analisi");

    if (ferroTotale)
    {
      templ.newBlock("analisi.analisFerroTotaleBlocco");
      templ.bset("analisiFerroTotale", "true");
    }
    else
    {
    	templ.bset("analisiFerroTotale", "false");
    }
    
    if (manganeseTotale)
    {
      templ.newBlock("analisi.analisManganeseTotaleBlocco");
      templ.bset("analisiManganeseTotale", "true");
    }
    else
    {
    	templ.bset("analisiManganeseTotale", "false");
    }

    if (zincoTotale)
    {
      templ.newBlock("analisi.analisZincoTotaleBlocco");
      templ.bset("analisiZincoTotale", "true");
    }
    else
    {
    	templ.bset("analisiZincoTotale", "false");
    }

    if (rameTotale)
    {
      templ.newBlock("analisi.analisRameTotaleBlocco");
      templ.bset("analisiRameTotale", "true");
    }
    else
    {
    	templ.bset("analisiRameTotale", "false");
    }

    if (piomboTotale)
    {
      templ.newBlock("analisi.analisPiomboTotaleBlocco");
      templ.bset("analisiPiomboTotale", "true");
    }
    else
    {
    	templ.bset("analisiPiomboTotale", "false");
    }

    if (cromoTotale)
    {
      templ.newBlock("analisi.analisCromoTotaleBlocco");
      templ.bset("analisiCromoTotale", "true");
    }
    else
    {
    	templ.bset("analisiCromoTotale", "false");
    }

    if (boroTotale)
    {
      templ.newBlock("analisi.analisBoroTotaleBlocco");
      templ.bset("analisiBoroTotale", "true");
    }
    else
    {
    	templ.bset("analisiBoroTotale", "false");
    }

    if (nichelTotale)
    {
      templ.newBlock("analisi.analisNichelTotaleBlocco");
      templ.bset("analisiNichelTotale", "true");
    }
    else
    {
    	templ.bset("analisiNichelTotale", "false");
    }

    if (cadmioTotale)
    {
      templ.newBlock("analisi.analisCadmioTotaleBlocco");
      templ.bset("analisiCadmioTotale", "true");
    }
    else
    {
    	templ.bset("analisiCadmioTotale", "false");
    }

    if (stronzioTotale)
    {
      templ.newBlock("analisi.analisStronzioTotaleBlocco");
      templ.bset("analisiStronzioTotale", "true");
    }
    else
    {
    	templ.bset("analisiStronzioTotale", "false");
    }

    if (altroMetalloTotale)
    {
      templ.newBlock("analisi.analisAltroMetalloTotaleBlocco");
      templ.bset("analisiAltroMetalloTotale", "true");
    }
    else
    {
    	templ.bset("analisiAltroMetalloTotale", "false");
    }

    /**
    * Se modifica non è valorizzato significa che sono arrivato a questa pagina
    * dal menu. Se modifica è uguale a si significa che ho appena modificato i
    * dati dell'etichetta. Se invece modifica è uguale a no significa che
    * ho tentato di modificare i dati ma qualcosa non è andato a buon fine
    * */

    boolean select = false, error = false;
    if ("si".equals(request.getParameter("modifica")))
    {
      templ.newBlock("modifica");
    }
    if (request.getParameter("errore") != null)
    {
    	error = true;
    }
    metalliPesanti.setIdRichiesta(aut.getIdRichiestaCorrente());

    String inputFerroTotale;
    String inputManganeseTotale;
    String inputZincoTotale;
    String inputRameTotale;
    String inputPiomboTotale;
    String inputCromoTotale;
    String inputBoroTotale;
    String inputNichelTotale;
    String inputCadmioTotale;
    String inputStronzioTotale;
    String inputAltroMetalloTotale;

    /**
    * Se sono arrivato qui a seguito di un errore non devo leggere i parametri
    * dal database ma utilizzare quelli che sono nel bean e arrivano dalla
    * request
    */
    if (! error)
    {
    	select = metalliPesanti.select();
    }

    templ.bset("idRichiestaCorrente", metalliPesanti.getIdRichiesta() + "");

    /**
     * Se sono qui in seguito ad un errore oppure ho trovato un record nel
     * database devo impostare i campi
     */
    if (select || error)
    {
    	inputFerroTotale = metalliPesanti.getFerroTotale();
    	inputManganeseTotale = metalliPesanti.getManganeseTotale();
    	inputZincoTotale = metalliPesanti.getZincoTotale();
    	inputRameTotale = metalliPesanti.getRameTotale();
    	inputPiomboTotale = metalliPesanti.getPiomboTotale();
    	inputCromoTotale = metalliPesanti.getCromoTotale();
    	inputBoroTotale = metalliPesanti.getBoroTotale();
    	inputNichelTotale = metalliPesanti.getNichelTotale();
    	inputCadmioTotale = metalliPesanti.getCadmioTotale();
    	inputStronzioTotale = metalliPesanti.getStronzioTotale();
    	inputAltroMetalloTotale = metalliPesanti.getAltroMetalloTotale();

      /**
      * Devo sostiruiire per ogni campo reale il punto decimale con la virgola
      */
      if (inputFerroTotale == null)
      {
      	inputFerroTotale = "";
      }
      else
      {
      	inputFerroTotale = inputFerroTotale.replace('.',',');
      }
      if (inputManganeseTotale == null)
      {
      	inputManganeseTotale = "";
      }
      else
      {
      	inputManganeseTotale = inputManganeseTotale .replace('.',',');
      }
      if (inputZincoTotale == null)
      {
      	inputZincoTotale = "";
      }
      else
      {
      	inputZincoTotale = inputZincoTotale.replace('.',',');
      }
      if (inputRameTotale == null)
      {
      	inputRameTotale = "";
      }
      else
      {
      	inputRameTotale = inputRameTotale.replace('.',',');
      }
			if (inputPiomboTotale == null)
      {
      	inputPiomboTotale = "";
      }
      else
      {
      	inputPiomboTotale = inputPiomboTotale.replace('.',',');
      }
      if (inputCromoTotale == null)
      {
      	inputCromoTotale = "";
      }
      else
      {
      	inputCromoTotale = inputCromoTotale.replace('.',',');
      }
			if (inputBoroTotale == null)
      {
      	inputBoroTotale = "";
      }
      else
      {
      	inputBoroTotale = inputBoroTotale.replace('.',',');
      }
      if (inputNichelTotale == null)
      {
      	inputNichelTotale = "";
      }
      else
      {
      	inputNichelTotale = inputNichelTotale.replace('.',',');
      }
      if (inputCadmioTotale == null)
      {
      	inputCadmioTotale = "";
      }
      else
      {
      	inputCadmioTotale = inputCadmioTotale.replace('.',',');
      }
      if (inputStronzioTotale == null)
      {
      	inputStronzioTotale = "";
      }
      else
      {
      	inputStronzioTotale = inputStronzioTotale.replace('.',',');
      }
      if (inputAltroMetalloTotale == null)
      {
      	inputAltroMetalloTotale = "";
      }
      else
      {
      	inputAltroMetalloTotale = inputAltroMetalloTotale.replace('.',',');
      }
      
      templ.bset("ferroTotale", inputFerroTotale);
      templ.bset("manganeseTotale" , inputManganeseTotale);
      templ.bset("zincoTotale", inputZincoTotale);
      templ.bset("rameTotale", inputRameTotale);
      templ.bset("piomboTotale", inputPiomboTotale);
      templ.bset("cromoTotale", inputCromoTotale);
      templ.bset("boroTotale", inputBoroTotale);
      templ.bset("nichelTotale", inputNichelTotale);
      templ.bset("cadmioTotale", inputCadmioTotale);
      templ.bset("stronzioTotale", inputStronzioTotale);
      templ.bset("altroMetalloTotale", inputAltroMetalloTotale);
    }

    if (select || (error && metalliPesanti.select()))
    {
      /**
      * In questo caso sono in update, quindi vuol dire che ho un record che
      * eventualmente posso anche cancellare
      */
      templ.newBlock("cancella");
      templ.bset("azione", "update");
    }
    else
    {
      /**
      * In questo caso sono in insert
      */
      templ.newBlock("nonCancella");
      templ.bset("azione", "insert");
    }
  }
%>

<%= templ.text() %>