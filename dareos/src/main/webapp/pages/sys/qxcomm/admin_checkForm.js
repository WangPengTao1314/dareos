/*
 * �����ݼ�麯��
 * @auther dybine
 * @last update date 2004-06-15
 **/

/*
 * У��form��Ԫ��Ԫ���Ƿ�Ϊ��
 * @param     formName		form������
 * @param		  itemName		Ԫ�ص�����
 * @param     itemMessage    alert����Ϣ
 * @return    true     ����������
 *						false    ����������
 */
function CheckIsNull(formName,itemName,itemMessage){
	var tempStr = trim(formName,itemName);
	if (tempStr.length>0){
		return true;
	}else{
		if(itemMessage!=null){
			alert(itemMessage);
		}
		return false;
	}
}

/*
 * ȥ�ַ����ո�
 * @param formName ��ѡ��.����
 * @param itemName ��ѡ��.Ԫ����
 * @param flag     ��ѡ��.�������.Ĭ��:ȥ��ͷ����β���ո�
 *                          0.ȥ�����пո�
 *                          1.ȥ��ͷ���ո�
 *                          2.ȥ��β���ո�
 *                          3.ȥ��ͷ����β���ո�
 * @return trim֮����ַ���
 */
function trim(formName,itemName,flag){
	  var str = eval("document."+formName+"." +itemName+".value");
	  if(str=="")  return "";
		switch(flag){
			case 0://ȥ�����пո�
				str = str.replace(/ /g, "");
				break;

		  case 1://ȥ��ͷ���ո�
				while(str.indexOf(" ")==0){
					str = str.substring(1);
				}
				break;
		  case 2://ȥ��β���ո�
				while(str.lastIndexOf(" ")==str.length-1){
					str = str.substring(0, str.length-1);
				}
				break;
		  case 3://ȥ��ͷ����β���ո�
				while(str.indexOf(" ")==0){
					str = str.substring(1);
				}
				while(str!=""&&str.lastIndexOf(" ")==str.length-1){
					str = str.substring(0, str.length-1);
				}
				break;
			default://Ĭ��ȥͷ����β���ո�
				while(str.indexOf(" ")==0){
					str = str.substring(1);
				}
				while(str!=""&&str.lastIndexOf(" ")==str.length-1){
					str = str.substring(0, str.length-1);
				}
				break;
		}
		return str;
}

/*
 * ���ĳ��Ԫ�ص�ֵ�ĳ���
 * @param formName  form������
 * @param itemName  Ԫ�ص�����
 * @return value���ַ������ȣ������������ַ�
 */
function getItemLength(formName, itemName){
	var theValue=eval("document." + formName+"."+ itemName + ".value");
	if(theValue.length==0) return 0;
	var bytelen=0;
	var re=/[^\x00-\xff]/g;
	//re.compile();
	for(var i=0;i<theValue.length;i++){
		var bl = re.test(theValue.charAt(i));
		re.test(theValue.charAt(i));
		if(bl){
		 	bytelen=bytelen+2;
		}else{
		 	bytelen+=1;
		}
	}
	return bytelen;
}

/*
 * ���radio�����
 * @param formName ��ѡ��.����
 * @param itemName ��ѡ��.Ԫ����
 * @return ѡ���radio������ֵ�����δѡ���򷵻�null
 */
function checkRadio(formName,itemName){
		var radioObjs = eval("document."+formName+"." +itemName);
		if(radioObjs.length==null){
			if(radioObjs.checked){
				return radioObjs.value;
			}else{
				return null;
			}
		}else{
			for(var i=0;i<radioObjs.length;i++){
				if(radioObjs[i].checked){
					return radioObjs[i].value;
				}
			}
			return null;
		}
}

/*
 * ���checkbox�����
 * @param formName ��ѡ��.����
 * @param itemName ��ѡ��.Ԫ����
 * @return a Array ѡ���checkbox������ֵ��ɵ�����(0~n),���δѡ���򷵻�һ������Ϊ0�Ŀ�����
 */
function checkCheckbox(formName,itemName){
	var checkboxObjs = eval("document."+formName+"." +itemName);
	var returnArr = new Array();
	var num = 0;
	if(checkboxObjs==null) return returnArr;
	if(checkboxObjs.length==null){
		if(checkboxObjs.checked){
			returnArr[num++] = checkboxObjs.value;
		}
	}else{
		for(var i=0;i<checkboxObjs.length;i++){
			if(checkboxObjs[i].checked){
				returnArr[num++] = checkboxObjs[i].value;
			}
		}
	}
	return returnArr;
}

/*
 *ReMark:��Ϊɾ����ť���ڿ���ϣ����Ե���ʱ���£�checkTopCheckbox(topcontent.document.forms[0],itemName)  Add by Wangjin on:2004-07-17
 * ���checkbox�����
 * @param formName ��ѡ��.����
 * @param itemName ��ѡ��.Ԫ����
 * @return a Array ѡ���checkbox������ֵ��ɵ�����(0~n),���δѡ���򷵻�һ������Ϊ0�Ŀ�����
 */
function checkTopCheckbox(formName,itemName){
	var checkboxObjs = eval(formName+"." +itemName);
	var returnArr = new Array();
	var num = 0;
	if(checkboxObjs==null) return returnArr;
	if(checkboxObjs.length==null){
		if(checkboxObjs.checked){
			returnArr[num++] = checkboxObjs.value;
		}
	}else{
		for(var i=0;i<checkboxObjs.length;i++){
			if(checkboxObjs[i].checked){
				returnArr[num++] = checkboxObjs[i].value;
			}
		}
	}
	return returnArr;
}

/*
 *ReMark:����ɾ���ǵ��ô˷������ж�״̬�� checkValid(topcontent.document.forms[0],itemName,state)  Add by Wangjin on:2004-07-17
 * ���checkbox�����
 * @param formName ��ѡ��.����
 * @param itemName ��ѡ��.Ԫ����
 * @param state    ��ѡ��.Ԫ���� ReMark:���ǵ��е�������ƶ����͡������������ɾ�������ԣ�stste�������ж��״̬��ɣ��硰�ƶ��������;
 * @return 
 */
function checkValid(formName,itemName,state){
	var checkboxObjs = eval(formName+"." +itemName);
	var formCaption = eval(formName);
	var returnArr = new Array();
	if(checkboxObjs.length == null)
	{
	  if(state.indexOf(formCaption.state.value) < 0 )
      {
         alert("ѡ�еļ�¼���ַǡ�"+state+"��״̬,����ʧ�ܣ�");              
         return false;
       } 
	   //return returnArr;
	}
	if(checkboxObjs!=null)
    { 
    for(var i=0;i<checkboxObjs.length;i++)
    {   
      if(checkboxObjs[i].checked==true)
      {          
        if(state.indexOf(formCaption.state[i].value) < 0 )
         {
           alert("ѡ�еļ�¼���ַǡ�"+state+"��״̬,����ʧ�ܣ�");              
           return false;
         } 
      }
    }
  }
}

/*
 *ReMark:����ɾ���ǵ��ô˷������ж�״̬�� getItemOfState(topcontent.document.forms[0],itemName,state)  Add by Wangjin on:2004-07-17
 * ���checkbox�����
 * @param formName ��ѡ��.����
 * @param itemName ��ѡ��.Ԫ����
 * @param state    ��ѡ��.Ԫ���� ReMark:���ǵ��е�������ƶ����͡������������ɾ�������ԣ�stste�������ж��״̬��ɣ��硰�ƶ��������;
 * @return 
 */
function getItemOfState(formName,itemName,state){
	var checkboxObjs = eval(formName+"." +itemName);
	var formCaption = eval(formName);
    if(checkboxObjs.length == null)
    {
      if(state.indexOf(formCaption.state.value) < 0)
      {
         formCaption.checkbox.checked = false;         
      } 
    }  
    if(checkboxObjs!=null)
    { 
      for(var i=0;i<checkboxObjs.length;i++)
      {   
        if(formCaption.checkbox[i].checked==true)
        {          
          if(state.indexOf(formCaption.state[i].value) < 0)
          {
            formCaption.checkbox[i].checked=false;         
          } 
        }
      }
  }
}