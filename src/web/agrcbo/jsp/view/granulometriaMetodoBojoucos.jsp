<%@ page errorPage="/jsp/view/errore.jsp" %>
<%@ page import="it.csi.agrc.*,it.csi.jsf.htmpl.*,it.csi.cuneo.*" isThreadSafe="true" %>

<%
  Htmpl templ =
  HtmplFactory.getInstance(application).getHtmpl("/jsp/layout/granulometriaMetodoBojoucos.htm");
%>

<%
  boolean ferro=false,manganese=false,zinco=false,rame=false,boro=false;
  boolean fra4=false,fra5=false,std=false;
  boolean calcio=false,magnesio=false,potassio=false,csc=false,vBACl2=false;
  boolean richiestaAnalisi[]=new boolean[12];

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
     id="granulometriaMetodoBojoucos"
     scope="request"
     class="it.csi.agrc.GranulometriaMetodoBojoucos">
     <%
      granulometriaMetodoBojoucos.setDataSource(dataSource);
      granulometriaMetodoBojoucos.setAut(aut);
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

  if (!richiestaAnalisi[Analisi.TER_GRANULOMETRIA_METODO_AUTOMATICO])
  {
     /**
     * Non è stata scelta nessuna analisi, quindi devo solo visualizzare un
     * messaggio di avvertimento
     */
     templ.newBlock("nessuno");
  }
  else
  {
    templ.newBlock("analisi");
    /**
    * Se modifica non è valorizzato significa che sono arrivato a questa pagina
    * dal menu. Se modifica è uguale a si significa che ho appena modificato i
    * dati dell'etichetta. Se invece modifica è uguale a no significa che
    * ho tentato di modificare i dati ma qualcosa non è andato a buon fine
    * */

    boolean select=false,error=false;
    if ("si".equals(request.getParameter("modifica")))
    {
      templ.newBlock("modifica");
    }
    if (request.getParameter("errore") != null) error=true;
    granulometriaMetodoBojoucos.setIdRichiesta(aut.getIdRichiestaCorrente());
    String letturaDensita115;
    String letturaDensita130;
    String letturaDensita145;
    String densitaSoluzFondo1;
    String temperatura1;
    String fattoreTempGranulare1;
    String diametro115;
    String diametro130;
    String diametro145;
    String densitaLorda5012;
    String densitaLorda5013;
    String densitaLorda5023;
    String densitaLorda50;
    String sabbia;
    String letturaDensita930;
    String letturaDensita10;
    String letturaDensita1045;
    String densitaSoluzFondo2;
    String temperatura2;
    String fattoreTempGranulare2;
    String diametro930;
    String diametro10;
    String diametro1045;
    String densitaLorda2045;
    String densitaLorda2046;
    String densitaLorda2056;
    String densitaLorda20;
    String limoGrosso;
    String letturaDensita17;
    String letturaDensita1830;
    String letturaDensita20;
    String densitaBianco17;
    String densitaBianco1830;
    String densitaBianco20;
    String densitaMediaBianco3;
    String temperatura17;
    String temperatura1830;
    String temperatura20;
    String temperaturaMedia3;
    String fattoreTempGranulare3;
    String diametro17;
    String diametro1830;
    String diametro20;
    String densitaLorda278;
    String densitaLorda279;
    String densitaLorda289;
    String densitaLorda2;
    String limoFine;
    String argilla1;
    String limo;
    String argilla2;


    /**
    * Se sono arrivato qui a seguito di un errore non devo leggere i parametri
    * dal database ma utilizzare quelli che sono nel bean e arrivano dalla
    * request
    */
    if (!error) select=granulometriaMetodoBojoucos.select();

    templ.bset("idRichiestaCorrente",granulometriaMetodoBojoucos.getIdRichiesta()+"");

    /**
     * Se sono qui in seguito ad un errore oppure ho trovato un record nel
     * database devo impostare i campi
     * */
    if (select || error)
    {
      letturaDensita115=granulometriaMetodoBojoucos.getLetturaDensita115();
      letturaDensita130=granulometriaMetodoBojoucos.getLetturaDensita130();
      letturaDensita145=granulometriaMetodoBojoucos.getLetturaDensita145();
      densitaSoluzFondo1=granulometriaMetodoBojoucos.getDensitaSoluzFondo1();
      temperatura1=granulometriaMetodoBojoucos.getTemperatura1();
      fattoreTempGranulare1=granulometriaMetodoBojoucos.getFattoreTempGranulare1();
      diametro115=granulometriaMetodoBojoucos.getDiametro115();
      diametro130=granulometriaMetodoBojoucos.getDiametro130();
      diametro145=granulometriaMetodoBojoucos.getDiametro145();
      densitaLorda5012=granulometriaMetodoBojoucos.getDensitaLorda5012();
      densitaLorda5013=granulometriaMetodoBojoucos.getDensitaLorda5013();
      densitaLorda5023=granulometriaMetodoBojoucos.getDensitaLorda5023();
      densitaLorda50=granulometriaMetodoBojoucos.getDensitaLorda50();
      sabbia=granulometriaMetodoBojoucos.getSabbia();
      letturaDensita930=granulometriaMetodoBojoucos.getLetturaDensita930();
      letturaDensita10=granulometriaMetodoBojoucos.getLetturaDensita10();
      letturaDensita1045=granulometriaMetodoBojoucos.getLetturaDensita1045();
      densitaSoluzFondo2=granulometriaMetodoBojoucos.getDensitaSoluzFondo2();
      temperatura2=granulometriaMetodoBojoucos.getTemperatura2();
      fattoreTempGranulare2=granulometriaMetodoBojoucos.getFattoreTempGranulare2();
      diametro930=granulometriaMetodoBojoucos.getDiametro930();
      diametro10=granulometriaMetodoBojoucos.getDiametro10();
      diametro1045=granulometriaMetodoBojoucos.getDiametro1045();
      densitaLorda2045=granulometriaMetodoBojoucos.getDensitaLorda2045();
      densitaLorda2046=granulometriaMetodoBojoucos.getDensitaLorda2046();
      densitaLorda2056=granulometriaMetodoBojoucos.getDensitaLorda2056();
      densitaLorda20=granulometriaMetodoBojoucos.getDensitaLorda20();
      limoGrosso=granulometriaMetodoBojoucos.getLimoGrosso();
      letturaDensita17=granulometriaMetodoBojoucos.getLetturaDensita17();
      letturaDensita1830=granulometriaMetodoBojoucos.getLetturaDensita1830();
      letturaDensita20=granulometriaMetodoBojoucos.getLetturaDensita20();
      densitaBianco17=granulometriaMetodoBojoucos.getDensitaBianco17();
      densitaBianco1830=granulometriaMetodoBojoucos.getDensitaBianco1830();
      densitaBianco20=granulometriaMetodoBojoucos.getDensitaBianco20();
      densitaMediaBianco3=granulometriaMetodoBojoucos.getDensitaMediaBianco3();
      temperatura17=granulometriaMetodoBojoucos.getTemperatura17();
      temperatura1830=granulometriaMetodoBojoucos.getTemperatura1830();
      temperatura20=granulometriaMetodoBojoucos.getTemperatura20();
      temperaturaMedia3=granulometriaMetodoBojoucos.getTemperaturaMedia3();
      fattoreTempGranulare3=granulometriaMetodoBojoucos.getFattoreTempGranulare3();
      diametro17=granulometriaMetodoBojoucos.getDiametro17();
      diametro1830=granulometriaMetodoBojoucos.getDiametro1830();
      diametro20=granulometriaMetodoBojoucos.getDiametro20();
      densitaLorda278=granulometriaMetodoBojoucos.getDensitaLorda278();
      densitaLorda279=granulometriaMetodoBojoucos.getDensitaLorda279();
      densitaLorda289=granulometriaMetodoBojoucos.getDensitaLorda289();
      densitaLorda2=granulometriaMetodoBojoucos.getDensitaLorda2();
      limoFine=granulometriaMetodoBojoucos.getLimoFine();
      argilla1=granulometriaMetodoBojoucos.getArgilla1();
      limo=granulometriaMetodoBojoucos.getLimo();
      argilla2=granulometriaMetodoBojoucos.getArgilla2();


      /**
      * Devo sostiruiire per ogni campo reale il punto decimale con la virgola
      */
      if (letturaDensita115==null) letturaDensita115="";
      else letturaDensita115=letturaDensita115.replace('.',',');
      if (letturaDensita130==null) letturaDensita130="";
      else letturaDensita130=letturaDensita130.replace('.',',');
      if (letturaDensita145==null) letturaDensita145="";
      else letturaDensita145=letturaDensita145.replace('.',',');
      if (densitaSoluzFondo1==null) densitaSoluzFondo1="";
      else densitaSoluzFondo1=densitaSoluzFondo1.replace('.',',');
      if (temperatura1==null) temperatura1="";
      else temperatura1=temperatura1.replace('.',',');
      if (fattoreTempGranulare1==null) fattoreTempGranulare1="";
      else fattoreTempGranulare1=fattoreTempGranulare1.replace('.',',');
      if (diametro115==null) diametro115="";
      else diametro115=diametro115.replace('.',',');
      if (diametro130==null) diametro130="";
      else diametro130=diametro130.replace('.',',');
      if (diametro145==null) diametro145="";
      else diametro145=diametro145.replace('.',',');
      if (densitaLorda5012==null) densitaLorda5012="";
      else densitaLorda5012=densitaLorda5012.replace('.',',');


      if (densitaLorda5013==null) densitaLorda5013="";
      else densitaLorda5013=densitaLorda5013.replace('.',',');
      if (densitaLorda5023==null) densitaLorda5023="";
      else densitaLorda5023=densitaLorda5023.replace('.',',');
      if (densitaLorda50==null) densitaLorda50="";
      else densitaLorda50=densitaLorda50.replace('.',',');
      if (sabbia==null) sabbia="";
      else sabbia=sabbia.replace('.',',');
      if (letturaDensita930==null) letturaDensita930="";
      else letturaDensita930=letturaDensita930.replace('.',',');
      if (letturaDensita10==null) letturaDensita10="";
      else letturaDensita10=letturaDensita10.replace('.',',');
      if (letturaDensita1045==null) letturaDensita1045="";
      else letturaDensita1045=letturaDensita1045.replace('.',',');
      if (densitaSoluzFondo2==null) densitaSoluzFondo2="";
      else densitaSoluzFondo2=densitaSoluzFondo2.replace('.',',');
      if (temperatura2==null) temperatura2="";
      else temperatura2=temperatura2.replace('.',',');
      if (fattoreTempGranulare2==null) fattoreTempGranulare2="";
      else fattoreTempGranulare2=fattoreTempGranulare2.replace('.',',');

      if (diametro930==null) diametro930="";
      else diametro930=diametro930.replace('.',',');
      if (diametro10==null) diametro10="";
      else diametro10=diametro10.replace('.',',');
      if (diametro1045==null) diametro1045="";
      else diametro1045=diametro1045.replace('.',',');
      if (densitaLorda2045==null) densitaLorda2045="";
      else densitaLorda2045=densitaLorda2045.replace('.',',');
      if (densitaLorda2046==null) densitaLorda2046="";
      else densitaLorda2046=densitaLorda2046.replace('.',',');
      if (densitaLorda2056==null) densitaLorda2056="";
      else densitaLorda2056=densitaLorda2056.replace('.',',');
      if (densitaLorda20==null) densitaLorda20="";
      else densitaLorda20=densitaLorda20.replace('.',',');
      if (limoGrosso==null) limoGrosso="";
      else limoGrosso=limoGrosso.replace('.',',');
      if (letturaDensita17==null) letturaDensita17="";
      else letturaDensita17=letturaDensita17.replace('.',',');
      if (letturaDensita1830==null) letturaDensita1830="";
      else letturaDensita1830=letturaDensita1830.replace('.',',');

      if (letturaDensita20==null) letturaDensita20="";
      else letturaDensita20=letturaDensita20.replace('.',',');
      if (densitaBianco17==null) densitaBianco17="";
      else densitaBianco17=densitaBianco17.replace('.',',');
      if (densitaBianco1830==null) densitaBianco1830="";
      else densitaBianco1830=densitaBianco1830.replace('.',',');
      if (densitaBianco20==null) densitaBianco20="";
      else densitaBianco20=densitaBianco20.replace('.',',');
      if (densitaMediaBianco3==null) densitaMediaBianco3="";
      else densitaMediaBianco3=densitaMediaBianco3.replace('.',',');
      if (temperatura17==null) temperatura17="";
      else temperatura17=temperatura17.replace('.',',');
      if (temperatura1830==null) temperatura1830="";
      else temperatura1830=temperatura1830.replace('.',',');
      if (temperatura20==null) temperatura20="";
      else temperatura20=temperatura20.replace('.',',');
      if (temperaturaMedia3==null) temperaturaMedia3="";
      else temperaturaMedia3=temperaturaMedia3.replace('.',',');
      if (fattoreTempGranulare3==null) fattoreTempGranulare3="";
      else fattoreTempGranulare3=fattoreTempGranulare3.replace('.',',');

      if (diametro17==null) diametro17="";
      else diametro17=diametro17.replace('.',',');
      if (diametro1830==null) diametro1830="";
      else diametro1830=diametro1830.replace('.',',');
      if (diametro20==null) diametro20="";
      else diametro20=diametro20.replace('.',',');
      if (densitaLorda278==null) densitaLorda278="";
      else densitaLorda278=densitaLorda278.replace('.',',');
      if (densitaLorda279==null) densitaLorda279="";
      else densitaLorda279=densitaLorda279.replace('.',',');
      if (densitaLorda289==null) densitaLorda289="";
      else densitaLorda289=densitaLorda289.replace('.',',');
      if (densitaLorda2==null) densitaLorda2="";
      else densitaLorda2=densitaLorda2.replace('.',',');
      if (limoFine==null) limoFine="";
      else limoFine=limoFine.replace('.',',');
      if (argilla1==null) argilla1="";
      else argilla1=argilla1.replace('.',',');
      if (limo==null) limo="";
      else limo=limo.replace('.',',');

      if (argilla2==null) argilla2="";
      else argilla2=argilla2.replace('.',',');

      templ.bset("letturaDensita115",letturaDensita115);
      templ.bset("letturaDensita130",letturaDensita130);
      templ.bset("letturaDensita145",letturaDensita145);
      templ.bset("densitaSoluzFondo1",densitaSoluzFondo1);
      templ.bset("temperatura1",temperatura1);
      templ.bset("fattoreTempGranulare1",fattoreTempGranulare1);
      templ.bset("diametro115",diametro115);
      templ.bset("diametro130",diametro130);
      templ.bset("diametro145",diametro145);
      templ.bset("densitaLorda5012",densitaLorda5012);

      templ.bset("densitaLorda5013",densitaLorda5013);
      templ.bset("densitaLorda5023",densitaLorda5023);
      templ.bset("densitaLorda50",densitaLorda50);
      templ.bset("sabbia",sabbia);
      templ.bset("letturaDensita930",letturaDensita930);
      templ.bset("letturaDensita10",letturaDensita10);
      templ.bset("letturaDensita1045",letturaDensita1045);
      templ.bset("densitaSoluzFondo2",densitaSoluzFondo2);
      templ.bset("temperatura2",temperatura2);
      templ.bset("fattoreTempGranulare2",fattoreTempGranulare2);

      templ.bset("diametro930",diametro930);
      templ.bset("diametro10",diametro10);
      templ.bset("diametro1045",diametro1045);
      templ.bset("densitaLorda2045",densitaLorda2045);
      templ.bset("densitaLorda2046",densitaLorda2046);
      templ.bset("densitaLorda2056",densitaLorda2056);
      templ.bset("densitaLorda20",densitaLorda20);
      templ.bset("limoGrosso",limoGrosso);
      templ.bset("letturaDensita17",letturaDensita17);
      templ.bset("letturaDensita1830",letturaDensita1830);

      templ.bset("letturaDensita20",letturaDensita20);
      templ.bset("densitaBianco17",densitaBianco17);
      templ.bset("densitaBianco1830",densitaBianco1830);
      templ.bset("densitaBianco20",densitaBianco20);
      templ.bset("densitaMediaBianco3",densitaMediaBianco3);
      templ.bset("temperatura17",temperatura17);
      templ.bset("temperatura1830",temperatura1830);
      templ.bset("temperatura20",temperatura20);
      templ.bset("temperaturaMedia3",temperaturaMedia3);
      templ.bset("fattoreTempGranulare3",fattoreTempGranulare3);

      templ.bset("diametro17",diametro17);
      templ.bset("diametro1830",diametro1830);
      templ.bset("diametro20",diametro20);
      templ.bset("densitaLorda278",densitaLorda278);
      templ.bset("densitaLorda279",densitaLorda279);
      templ.bset("densitaLorda289",densitaLorda289);
      templ.bset("densitaLorda2",densitaLorda2);
      templ.bset("limoFine",limoFine);
      templ.bset("argilla1",argilla1);
      templ.bset("limo",limo);

      templ.bset("argilla2",argilla2);
    }
    if (select || (error && granulometriaMetodoBojoucos.select()))
    {
      /**
      * In questo caso sono in update, quindi vuol dire che ho un record che
      * eventualmente posso anche cancellare
      */
      templ.newBlock("cancella");
      templ.bset("azione","update");
    }
    else
    {
      /**
      * In questo caso sono in insert
      */
      templ.newBlock("nonCancella");
      templ.bset("azione","insert");
    }
  }
%>

<%= templ.text() %>
