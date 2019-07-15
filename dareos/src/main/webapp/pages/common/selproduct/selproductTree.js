var setting = treeSetting();

$(function(){
	var treeObj = treePageInit();
	var nodes = treeObj.getNodes();
	treeObj.expandNode(nodes[0]);
});

function zTreeOnClick(event, treeId, treeNode) {
	/*console.log("event:", event);
	console.log("treeId:", treeId);*/
	//console.log("treeNode:", treeNode);
	if ('0' == treeNode.level) {
		var treeObj = $.fn.zTree.getZTreeObj("tree");
		treeObj.expandNode(treeNode);
	} else {
		//点击后页面跳转
		//parent.document.getElementById("topcontent").src="toList?productid="+utf8(treeNode.id);
		parent.window.topcontent.queryPrd(treeNode.id);
		
	}
};

