//Ŀǰ�������֧�������Ӳ˵�,��������������Ӧ�ĺ����Ͷ���
var oldSystemItem = null;
var oldMenuItem = null;
var oldMenuHref = null;//OAר��

//���Ƶ�һ���Ӳ˵������غ���ʾ
//�������ʾ���Ӳ˵�������,�����µ��Ӳ˵�չ��
function switchMenu(objectID) {
	if (objectID.style.display == "none") {
		if (oldSystemItem != null) {
			oldSystemItem.style.display = "none";
		}
		oldSystemItem = objectID;
		objectID.style.display = "inline";
	} else {
		objectID.style.display = "none";
	}
}

//���Ƶڶ����Ӳ˵������غ���ʾ
//�������ʾ���Ӳ˵�������,�����µ��Ӳ˵�չ��
function switchSubmenu(objectID) {
	var hrefobj = event.srcElement;
	if (objectID.style.display == "none") {
		if (oldMenuItem != null) {
			oldMenuItem.style.display = "none";
			oldMenuHref.className="12dark";//OAר��
		}
		oldMenuItem = objectID;
		oldMenuHref = hrefobj;//OAר��
		objectID.style.display = "inline";
		hrefobj.className="12dark2";//OAר��
	} else {
		objectID.style.display = "none";
	}
}

//���Ƶײ�˵�����class,����б�Ĳ˵�����ѡ��,��ı���Ϊ��ʼ״̬
//�����еĲ˵�����class�ı�(����onClick�¼�)
var oldObj = null;

function switchItem() {
	if(oldObj != null) {
		oldObj.className = "12blue";
	}
	newObj = event.srcElement;
	newObj.className = "clicked";
	oldObj = newObj;
}

function openIcon(obj,url1,url2){
  if(obj.style.display=="none"){
    window.parent.frames[2].location = url1;
  }else{
    window.parent.frames[2].location = url2;
  }  
}