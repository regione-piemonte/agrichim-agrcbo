function salva(form)
{
    var msg='';
    form.letturaDensita115.value=form.letturaDensita115.value.trim();
    var letturaDensita115 =form.letturaDensita115.value.replace(",",".");

    form.letturaDensita130.value=form.letturaDensita130.value.trim();
    var letturaDensita130 =form.letturaDensita130.value.replace(",",".");

    form.letturaDensita145.value=form.letturaDensita145.value.trim();
    var letturaDensita145 =form.letturaDensita145.value.replace(",",".");

    form.densitaSoluzFondo1.value=form.densitaSoluzFondo1.value.trim();
    var densitaSoluzFondo1 =form.densitaSoluzFondo1.value.replace(",",".");

    form.temperatura1.value=form.temperatura1.value.trim();
    var temperatura1 =form.temperatura1.value.replace(",",".");

    form.letturaDensita930.value=form.letturaDensita930.value.trim();
    var letturaDensita930 =form.letturaDensita930.value.replace(",",".");

    form.letturaDensita10.value=form.letturaDensita10.value.trim();
    var letturaDensita10 =form.letturaDensita10.value.replace(",",".");

    form.letturaDensita1045.value=form.letturaDensita1045.value.trim();
    var letturaDensita1045 =form.letturaDensita1045.value.replace(",",".");

    form.densitaSoluzFondo2.value=form.densitaSoluzFondo2.value.trim();
    var densitaSoluzFondo2 =form.densitaSoluzFondo2.value.replace(",",".");

    form.temperatura2.value=form.temperatura2.value.trim();
    var temperatura2 =form.temperatura2.value.replace(",",".");

    form.letturaDensita17.value=form.letturaDensita17.value.trim();
    var letturaDensita17 =form.letturaDensita17.value.replace(",",".");

    form.letturaDensita1830.value=form.letturaDensita1830.value.trim();
    var letturaDensita1830 =form.letturaDensita1830.value.replace(",",".");

    form.letturaDensita20.value=form.letturaDensita20.value.trim();
    var letturaDensita20 =form.letturaDensita20.value.replace(",",".");

    form.densitaBianco17.value=form.densitaBianco17.value.trim();
    var densitaBianco17 =form.densitaBianco17.value.replace(",",".");

    form.densitaBianco1830.value=form.densitaBianco1830.value.trim();
    var densitaBianco1830 =form.densitaBianco1830.value.replace(",",".");

    form.densitaBianco20.value=form.densitaBianco20.value.trim();
    var densitaBianco20 =form.densitaBianco20.value.replace(",",".");

    form.temperatura17.value=form.temperatura17.value.trim();
    var temperatura17 =form.temperatura17.value.replace(",",".");

    form.temperatura1830.value=form.temperatura1830.value.trim();
    var temperatura1830 =form.temperatura1830.value.replace(",",".");

    form.temperatura20.value=form.temperatura20.value.trim();
    var temperatura20 =form.temperatura20.value.replace(",",".");

    var d115=0,d130=0,d145=0,d930=0,d10=0,d1045=0,denSolfondo2=0;
    var d17=0,d1830=0,d20=0,t17=-1,t1830=-1,t20=-1;

    if (letturaDensita115 != '' && (!isFloat(letturaDensita115,3) || (letturaDensita115<0) || (letturaDensita115>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura densità a 1\'15" "\n';
    if (letturaDensita130 != '' && (!isFloat(letturaDensita130,3) || (letturaDensita130<0) || (letturaDensita130>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura densità a 1\'30" "\n';
    if (letturaDensita145 != '' && (!isFloat(letturaDensita145,3) || (letturaDensita145<0) || (letturaDensita145>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura densità a 1\'45" "\n';
    if (!isFloat(densitaSoluzFondo1,3) || (densitaSoluzFondo1<=0) || (densitaSoluzFondo1>999999.999))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Densità soluzione di fondo 1"\n';
    if (!isFloat(temperatura1,3) || (temperatura1<10) || (temperatura1>30))
        msg=msg+'Inserire un numero maggiore o uguale a  10 e minore o uguale a 30 in "Temperatura 1 in °C"\n';
    if (letturaDensita930 != '' && (!isFloat(letturaDensita930,3) || (letturaDensita930<0) || (letturaDensita930>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura densità a 9\'30" "\n';
    if (letturaDensita10 != '' && (!isFloat(letturaDensita10,3) || (letturaDensita10<0) || (letturaDensita10>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura densità a 10\'"\n';
    if (letturaDensita1045 != '' && (!isFloat(letturaDensita1045,3) || (letturaDensita1045<0) || (letturaDensita1045>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura densità a 10\'45" "\n';
    if (densitaSoluzFondo2 != '' && (!isFloat(densitaSoluzFondo2,3) || (densitaSoluzFondo2<0) || (densitaSoluzFondo2>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Densità soluzione di fondo 2"\n';
    if (!isFloat(temperatura2,3) || (temperatura2<0) || (temperatura2>999999.999))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Temperatura 2 in °C"\n';
    if (letturaDensita17 != '' && (!isFloat(letturaDensita17,3) || (letturaDensita17<0) || (letturaDensita17>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura densità a 17 h"\n';
    if (letturaDensita1830 != '' && (!isFloat(letturaDensita1830,3) || (letturaDensita1830<0) || (letturaDensita1830>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura densità a 18 h 30\'"\n';
    if (letturaDensita20 != '' && (!isFloat(letturaDensita20,3) || (letturaDensita20<0) || (letturaDensita20>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Lettura densità a 20 h"\n';
    if (densitaBianco17 != '' && (!isFloat(densitaBianco17,3) || (densitaBianco17<0) || (densitaBianco17>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Densità bianco a 17 h"\n';
    if (densitaBianco1830 != '' && (!isFloat(densitaBianco1830,3) || (densitaBianco1830<0) || (densitaBianco1830>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Densità bianco a 18 h 30\'"\n';
    if (densitaBianco20 != '' && (!isFloat(densitaBianco20,3) || (densitaBianco20<0) || (densitaBianco20>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Densità bianco a 20 h"\n';
    if (temperatura17 != '' && (!isFloat(temperatura17,3) || (temperatura17<0) || (temperatura17>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Temperatura a 17 h in °C"\n';
    if (temperatura1830 != '' && (!isFloat(temperatura1830,3) || (temperatura1830<0) || (temperatura1830>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Temperatura a 18 h 30\' in °C"\n';
    if (temperatura20 != '' && (!isFloat(temperatura20,3) || (temperatura20<0) || (temperatura20>999999.999)))
        msg=msg+'Inserire un numero nel formato ######,### nel campo "Temperatura a 20 h in °C"\n';

    if (msg=='')
    {
      if (letturaDensita115!='') d115=parseFloat(letturaDensita115);
      if (letturaDensita130!='') d130=parseFloat(letturaDensita130);
      if (letturaDensita145!='') d145=parseFloat(letturaDensita145);
      if (letturaDensita930!='') d930=parseFloat(letturaDensita930);
      if (letturaDensita10!='') d10=parseFloat(letturaDensita10);
      if (letturaDensita1045!='') d1045=parseFloat(letturaDensita1045);
      if (densitaSoluzFondo2!='') denSolfondo2=parseFloat(densitaSoluzFondo2);
      if (letturaDensita17!='') d17=parseFloat(letturaDensita17);
      if (letturaDensita1830!='') d1830=parseFloat(letturaDensita1830);
      if (letturaDensita20!='') d20=parseFloat(letturaDensita20);
      if (temperatura17!='') t17=parseFloat(temperatura17);
      if (temperatura1830!='') t1830=parseFloat(temperatura1830);
      if (temperatura20!='') t20=parseFloat(temperatura20);


      var t2=parseFloat(temperatura2);

      if (!((d115>0 && d115>=d130) || d130>0))
        msg=msg+'Questo vincolo non è stato rispettato: ([Lettura a 1\'15"] > 0 and >= [Lettura a 1\'30"]) or [Lettura a 1\'30"] > 0\n\n';
      if (!( (d130>0 && d115>=d130)
                           ||
            (d145>0 && d115>d145)
                           ||
            (d145>0 && d130>=d145)))
        msg=msg+'Questo vincolo non è stato rispettato: ([Lettura a 1\'30"] > 0 and [Lettura a 1\'15"] >= [Lettura a 1\'30"]) or([Lettura a 1\'45"] > 0" and [Lettura a 1\'15"] > [Lettura a 1\'45"]) or([Lettura a 1\'45"] > 0" and [Lettura a 1\'30"] >= [Lettura a 1\'45"])\n\n';

      if (!((d145==0 || d145>=d930) && (d130==0 || d130>d930))       )
        msg=msg+'Questo vincolo non è stato rispettato:  ([Lettura densità a 1\'45"] = 0 or [Lettura densità a 1\'45"] >= [Lettura densità a 9\'30"]) and ([Lettura densità a 1\'30"] = 0 or [Lettura densità a 1\'30"] >= [Lettura densità a 9\'30"])\n\n';
      if (!((d930>0 && d930>=d10) || d10>=0))
        msg=msg+'Questo vincolo non è stato rispettato:  ([Lettura densità a 9\'30"] > 0 and [Lettura densità a 9\'30"] >= [Lettura densità a 10\']) or [Lettura densità a 10\'] >= 0)\n\n';
      if (!( (d10>0 && d930>=d10)
                           ||
            (d1045>=0 && d930>=d1045)
                           ||
            (d1045>=0 && d10>=d1045)
         )
      )
        msg=msg+'Questo vincolo non è stato rispettato:  ([Lettura densità a 10\'] > 0 and [Lettura densità a 9\'30"] >= [Lettura densità a 10\']) or ([Lettura densità a 10\'45"] >= 0 and [Lettura densità a 9\'30"] >= [Lettura densità a 10\'45"]) or ([Lettura densità a 10\'45"] >= 0 and [Lettura densità a 10\'] >= [Lettura densità a 10\'45"])\n\n';

      if (!(((d930>0 || (d10>0 && d1045>0)) && denSolfondo2 >0) || (d930==0 && d10==0 && denSolfondo2==0)))
        msg=msg+'Questo vincolo non è stato rispettato:  (([Lettura densità a 9\'30"] > 0 or ([Lettura densità a 10\'] > 0 and [Lettura densità a 10\'45"] > 0)) and [Densità soluzione di fondo 2] > 0) or ([Lettura densità a 9\'30"] = 0 and [Lettura densità a 10\'] = 0 and [Densità soluzione di fondo 2] = 0)\n\n';

      if (!((denSolfondo2 == 0 && t2==0) || (denSolfondo2 > 0 && t2>=10 && t2<=30)))
        msg=msg+'Questo vincolo non è stato rispettato:  ([Densità soluzione di fondo 2] = 0 and [Temperatura 2] = 0) or ([Densità soluzione di fondo 2] > 0 and ([Temperatura 2] >= 10 and <= 30))\n\n';


      if (!((d1045==0 || d1045>=d17) && (d10==0 || d10>=d17) && (d145 == 0 || d145>=d17)))
        msg=msg+'Questo vincolo non è stato rispettato:  ([Lettura densità a 10\'45"] = 0 or [Lettura densità a 10\'45"] >= [Lettura densità a 17 h]) and ([Lettura densità a 10\'] = 0 or [Lettura densità a 10\'] >= [Lettura densità a 17 h]) and ([Lettura densità a 1\'45"] = 0 or [Lettura densità a 1\'45"] >= [Lettura densità a 17 h])\n\n';
      if (!( (d17>0 && d17>=d1830) || d1830>0)  )
        msg=msg+'Questo vincolo non è stato rispettato:  ([Lettura densità a 17 h] > 0 and [Lettura densità a 17 h] >= [Lettura densità a 18 h 30\']) or [Lettura densità a 18 h 30\'] > 0\n\n';
      if (!( (d1830>0 &&d17>=d1830) || (d20>0 && d17>d20) || (d20>0 &&d1830>=d20)           )
         )
        msg=msg+'Questo vincolo non è stato rispettato:  ([Lettura densità a 18 h 30\'] > 0 and [Lettura densità a 17 h] >= [Lettura densità a 18 h 30\']) or ([Lettura densità a 20 h] > 0 and [Lettura densità a 17 h] > [Lettura densità a 20 h]) or ([Lettura densità a 20 h] > 0 and [Lettura densità a 18 h 30\'] >= [Lettura densità a 20 h])\n\n';


      if (((densitaBianco17=='' || parseFloat(densitaBianco17)==0)
                  &&
           (densitaBianco1830=='' || parseFloat(densitaBianco1830)==0)
                  &&
           (densitaBianco20=='' || parseFloat(densitaBianco20)==0)
           ))
      {
        msg=msg+'Questo vincolo non è stato rispettato:  [Densità bianco a 17 h] > 0 or [Densità bianco a 18 h 30\'] > 0 or [Densità bianco a 20 h] > 0\n\n';
      }

      if (!((t17>=10 && t17<=30) || t17==0))
        msg=msg+'Questo vincolo non è stato rispettato:  ([Temperatura a 17 h] >= 10 and <= 30) or [Temperatura a 17 h] = 0\n\n';
      if (!((t1830>=10 && t1830<=30) || t1830==0))
        msg=msg+'Questo vincolo non è stato rispettato:  ([Temperatura a 18 h 30\'] >= 10 and <= 30) or [Temperatura a 18 h 30\'] = 0\n\n';
      if (!(t17>0 || t1830>0 || (t20>=10 && t20<=30)))
        msg=msg+'Questo vincolo non è stato rispettato:  [Temperatura a 17 h] > 0 or [Temperatura a 18 h 30\'] > 0 or ([Temperatura a 20 h] >= 10 or <= 30)\n\n';

      if (msg!='')
      {
        alert( "Dati non corretti\n\n"+msg );
        return false;
      }
    }
    else
    {
        alert( "Dati incompleti\n\n"+msg );
        return false;
    }
    return true;
}

