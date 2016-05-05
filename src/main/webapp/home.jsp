<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>工作平台</title>
	<link rel="stylesheet" type="text/css" href="/css/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="/css/icon.css">
	<script type="text/javascript" src="/js/jquery-1.11.3.min.js"></script>
	<script type="text/javascript" src="/js/jquery.easyui.min.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'south',split:true," style="height:400px;">
		<div id="report-detail" style="display:none;">
			<div style="background-color: #eee;">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="returnDetail()">返回</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="saveReportDetail()">保存</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="cancelReportDetail()">取消</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" onclick="collapseAll()">折叠所有</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" onclick="expandAll()">展开所有</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true" onclick="downloadFile()">下载</a>
			</div>
			<table id="tg2" class="easyui-treegrid" title="报告详情 "
				data-options="
					rownumbers: true,
					fitColumns: true,
					idField: 'id',
					showFooter: true,
					onContextMenu: function(e,row){
						if (row){
							e.preventDefault();
							$(this).treegrid('select', row.id);
							$('#mm2').menu('show',{
								left: e.pageX,
								top: e.pageY
							});				
						}
					}
				">
			</table>
			<div id="mm2" class="easyui-menu" style="width:120px;">
				<div onclick="appendDetail()" data-options="iconCls:'icon-add'">添加</div>
				<div href="javascript:void(0)" data-options="iconCls:'icon-edit'" onclick="editReportDetail()">修改</div>
				<div onclick="removeDetail()" data-options="iconCls:'icon-remove'">删除</div>
			</div>
		</div>
		<div id="report-list">
			<table id="dg2" class="easyui-datagrid" title='报告管理'
				data-options="
					toolbar: '#tb2',
					url: '/report_list.action',
					method: 'get',
					singleSelect:true
				">
				<thead>
					<tr>
						<th data-options="field:'id'">编号</th>
						<th data-options="field:'name',width:300">报告名称</th>
						<th data-options="field:'createTime',width:120">报告时间</th>
						<th data-options="field:'link',width:120,align:'center'">操作</th>
					</tr>
				</thead>
			</table>
			<div id="tb2" style="height:auto;">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendReport()">添加</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeReport()">删除</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onclick="importReport()">导入报告</a>
			</div>
		</div>
	</div>
	<div data-options="region:'west',split:true,title:'分类管理'" style="width:250px;padding:10px;">
		<ul id="tt" class="easyui-tree" data-options="
				animate: true,
				lines:true,
				onContextMenu: function(e,node){
					e.preventDefault();
					$(this).tree('select',node.target);
					$('#mm').menu('show',{
						left: e.pageX,
						top: e.pageY
					});
				}
		"></ul>
	</div>
	<div id="mm" class="easyui-menu" style="width:120px;">
		<div onclick="append()" data-options="iconCls:'icon-add'">添加</div>
		<div onclick="move()" data-options="iconCls:'icon-redo'">移动</div>
		<div onclick="editTree()" data-options="iconCls:'icon-edit'">修改</div>
		<div onclick="removeit()" data-options="iconCls:'icon-remove'">删除</div>
	</div>
	<div data-options="region:'center',title:'设备区'">
		<input type="hidden" id="catId" value="${result.catId}"/>
		<table id="dg" class="easyui-datagrid"
				data-options="
					iconCls: 'icon-edit',
					toolbar: '#tb',
					url: '/product_list.action',
					method: 'get',
					onClickCell: onClickCell
				">
			<thead>
				<tr>
					<th data-options="field:'id',checkbox:true"></th>
					<th data-options="field:'name',width:150,editor:{type: 'textbox',options: {required: true}}">设备名称</th>
					<th data-options="field:'model',width:120,editor:{type: 'textbox',options: {required: true}}">型号</th>
					<th data-options="field:'brand',width:150,editor:{type: 'textbox',options: {required: true}}">品牌</th>
					<th data-options="field:'unit',width:30,editor:{type: 'textbox',options: {required: true}}">单位</th>
					<th data-options="field:'price1',width:40,align:'right',editor:{type: 'numberbox',options: {precision:2}}">批发价</th>
					<th data-options="field:'price2',width:40,align:'right',editor:{type: 'numberbox',options: {precision:2}}">集成商价</th>
					<th data-options="field:'price3',width:40,align:'right',editor:{type: 'numberbox',options: {precision:2}}">工程价</th>
					<th data-options="field:'price4',width:60,align:'right',editor:{type: 'numberbox',options: {required : true,precision:2}}">设计价</th>
					<th data-options="field:'param',width:210,editor:'textbox'">参数</th>
					<th data-options="field:'remark',width:100,editor:'textbox'">备注</th>
				</tr>
			</thead>
		</table>
		<div id="tb" style="height:auto">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendProduct()">添加</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeProduct()">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">取消</a>
		</div>
	</div>
	
	<div id="addTreeDialog" class="easyui-dialog" title="增加分类" style="width:355px;height:120px;padding:10px" closed="true" modal="true" data-options="iconCls: 'icon-add',buttons: '#addTreeDialog-buttons' ">
		<input id="name" name="name" type="text" maxlength="50" required="required" class="easyui-textbox" style="width:320px;" data-options="prompt:'请输入分类名称'" />
	</div>
	<div id="addTreeDialog-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:addTree()">增加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#addTreeDialog').dialog('close')">关闭</a>
	</div>

	<div id="addReportDialog" class="easyui-dialog" title="导入报告" style="width:355px;height:120px;padding:10px" closed="true" modal="true" data-options="iconCls: 'icon-add',buttons: '#addReportDialog-buttons' ">
		<form id="importForm" action="/import.action" method="post" enctype="multipart/form-data">
			<input name="attachment" type="file" style="width:320px;" />
		</form>
	</div>
	<div id="addReportDialog-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:importSubmit()">导入</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$('#addReportDialog').dialog('close')">关闭</a>
	</div>
	<script type="text/javascript">
		var beforeEditNodeText;
	 	$("#tt").tree({
		    data:${result.treeData},
		    onClick:function(node){
		      if(node.parentId && node.parentId > 0) {
		    	  $('#dg').datagrid('loading');
		    	  $.get("/product_list.action", {catId:node.id},
						function(result) {
							$('#dg').datagrid('loadData', result);
							$("#catId").val(node.id);
							$('#dg').datagrid('loaded');
						}
					);
		      }
		    },
		    onBeforeEdit: function(node) {
		    	beforeEditNodeText = node.text;
		    },
		    onAfterEdit: function(node) {
		    	if (beforeEditNodeText != node.text) {
		    		var id = node.id;
		    		var name = $.trim(node.text);
		    		if (name == "") {
		    			alert("分类不能为空！");
		    			return;
		    		}
					$.get("/page_category_edit.action", {id:id,name:encodeURI(name)},
						function(result) {
							if (result.tip == "fail") {
								alert("修改失败");
							}
						}
					);
		    	}
		     }
		  });
	 
		function append(){
			var tt = $('#tt');
			var node = tt.tree('getSelected');
			var parentNode = tt.tree('getParent', node.target);
			if(!parentNode || parentNode.id == 0){
				$('#addTreeDialog').dialog('open');
			}else{
				$.messager.alert("提示", "不能添加子分组了。");
			}
		}
		function removeit(){
			var tt = $('#tt');
			var node = tt.tree('getSelected');
			if(node.id !== 0){
				$.messager.confirm("操作提示", "您确定要删除该分类吗？", function (data) {
					if (data) {
						var id = node.id;
						$.get("/page_category_delete.action", {id: id},
							function(result) {
								if (result.tip == "fail") {
									alert("删除失败！");
								} else {
									var parentNode = tt.tree('getParent', node.target);
									tt.tree('select', parentNode.target);
									tt.tree('remove', node.target);
								}
							}
						);
					}
				});
			}else{
				alert("根分类不能删除");
			}
		}
		function editTree() {
			var tt = $('#tt');
			var node = tt.tree('getSelected');
			if(node.id !== 0){
				tt.tree('beginEdit', node.target);
			}else{
				alert("根分类不能修改");
			}
		}
		
		function move(){
			var checked = $('#dg').datagrid('getChecked');
			if(checked.length <= 0){
				$.messager.alert('警告','请选择一个设备！');
				return;
			}
			
			var pids = "";
			var ids = [];
			for(var i =0; i < checked.length;i++){
				ids.push(checked[i].id); 
			}
			pids = ids.join(',');

			var oldCatId = $("#catId").val();
			var tt = $('#tt');
			var node = tt.tree('getSelected');
			
			$.ajax({ 
		        type:'get', 
		        url: '/page_category_move.action', 
		        data: {id:node.id, oldCatId: oldCatId, pids: pids},
		        success:function (result){ 
		        	if (result.tip == "fail") {
						alert("修改失败！");
					} else {
						$('#dg').datagrid('loading');
				    	 $.get("/product_list.action", {catId:node.id},
							function(result) {
								$('#dg').datagrid('loadData', result);
								$("#catId").val(node.id);
								$('#dg').datagrid('loaded');
							}
						);
					}
		        } 
		    });
		}
		
		//增加分组
		function addTree() {
			var name = $.trim($('#name').val());
			if (name != "") {
				var tt = $('#tt');
				var node = tt.tree('getSelected');
				var parentId = node.id;
				$.get("/page_category_add.action", {name:encodeURI(name), parentId:parentId},
					function(result) {
						if (result.tip == "fail") {
							alert("添加失败！");
						} else {
							$('#addTreeDialog').dialog('close');
							tt.tree('append', {
								parent: (node?node.target:null),
								data: [{
										id: result.id,
										text: name
								}]
							});
						}
					}
				);
			}
		}
		
		/** 设备管理
		*********************************************************************/
		var editIndex = undefined;
		function endEditing(){
			if (editIndex == undefined){return true}
			if ($('#dg').datagrid('validateRow', editIndex)){
				$('#dg').datagrid('endEdit', editIndex);
				editIndex = undefined;
				return true;
			} else {
				return false;
			}
		}
		function onClickCell(index, field){
			if (editIndex != index){
				if (endEditing()){
					$('#dg').datagrid('selectRow', index)
							.datagrid('beginEdit', index);
					var ed = $('#dg').datagrid('getEditor', {index:index,field:field});
					if (ed){
						($(ed.target).data('textbox') ? $(ed.target).textbox('textbox') : $(ed.target)).focus();
					}
					editIndex = index;
				} else {
					$('#dg').datagrid('selectRow', editIndex);
				}
			}
		}
		function appendProduct(){
			if (endEditing()){
				$('#dg').datagrid('appendRow',{status:'P'});
				editIndex = $('#dg').datagrid('getRows').length-1;
				$('#dg').datagrid('selectRow', editIndex)
						.datagrid('beginEdit', editIndex);
			}
		}
		function removeProduct(){
			var checked = $('#dg').datagrid('getChecked');
			
			if(checked.length <= 0){
				$.messager.alert("提示", "请选择一个设备！");
				return;
			}

			$.messager.confirm("操作提示", "您确定要删除该设备吗？", function (data) {
				if (data) {
					var pids = "";
					var ids = [];
					for(var i =0; i < checked.length;i++){
						var row = checked[i];
						ids.push(checked[i].id);
					}
					pids = ids.join(',');
					$.ajax({ 
				        type:'get', 
				        url: '/product_delete.action', 
				        data: {ids:pids}, 
				        success:function (result){ 
				        	if (result.tip == "fail") {
								alert("添加失败！");
							} else {
								 $('#dg').datagrid('loading');
						    	  $.get("/product_list.action", {catId:$("#catId").val()},
										function(result) {
											$('#dg').datagrid('loadData', result);
											$('#dg').datagrid('loaded');
										}
								);
							}
				        } 
				    });
				}
			});
		}
		
		function accept(){
			var index = editIndex;
			if (endEditing()){
				
				var data = $('#dg').datagrid('getData');
				var queryString = toQueryString(data.rows[index]);
				if(queryString.indexOf("catId=") == -1){
					queryString += "&catId=" + $("#catId").val();
				}
				
				$.get("/product_add.action", queryString,
					function(result) {
						if (result.tip == "fail") {
							alert(result.msg);
						} else {
							$('#dg').datagrid('beginEdit', index);
							$('#dg').datagrid('getRows')[index]['id'] = result.id;
							$('#dg').datagrid('acceptChanges');
						}
					}
				);
			}
		}
		function reject(){
			$('#dg').datagrid('rejectChanges');
			editIndex = undefined;
		}
		
		/** 报告管理
		*********************************************************************/
		function removeReport(){
			var row = $("#dg2").datagrid("getSelected");
			var index = $('#dg2').datagrid('getRowIndex', row);
			$.ajax({ 
		        type:'get', 
		        url: '/report_delete.action', 
		        data: {id:row.id}, 
		        success:function (result){ 
		        	if (result.tip == "fail") {
						alert("添加失败！");
					} else {
						$('#dg2').datagrid('deleteRow', index);
					}
		        } 
		    });
		}
		
		function editReport(id){
			$("#report-detail").show();
			$('#tg2').treegrid({
			    url:'/report_detail.action?id=' + id,
			    treeField:'name',
			    columns:[[
			        {field:'name',title:'模块名称',width:180, editor:'text'},
			        {field:'model',title:'型号',width:60},
			        {field:'brand',title:'品牌',width:80},
			        {field:'num',title:'数量',width:80, editor:'numberbox'},
			        {field:'unit',title:'单位',width:80},
			        {field:'price4',title:'单价',width:80},
			        {field:'price',title:'合价',width:80}
			    ]]
			});
			
			$("#report-list").hide();
		}
		
		function appendReport(){
			$("#report-detail").show();
			$('#tg2').treegrid({
			    url:'/report_detail.action',
			    treeField:'name',
			    columns:[[
			        {field:'name',title:'模块名称',width:180, editor:'text'},
			        {field:'model',title:'型号',width:60},
			        {field:'brand',title:'品牌',width:80},
			        {field:'num',title:'数量',width:80, editor:'numberbox'},
			        {field:'unit',title:'单位',width:80},
			        {field:'price4',title:'单价',width:80},
			        {field:'price',title:'合价',width:80}
			    ]]
			});
			
			$("#report-list").hide();
		}
		
		function importReport(){
			$("#addReportDialog").dialog('open');
		}
		
		function importSubmit(){
			$('#importForm').form('submit', {
			    onSubmit: function(){
			    	if($("[name='attachment']").size() <= 0){
			    		alert("请选择一个文件！");
			    		return false;
			    	}
			       
			    	return true;
			    },
			    success:function(data){
			    	eval("var result = (" + data + ")");
			    	if(result.tip != "success"){
			    		$.messager.alert("提示", result.tip);
			    	}else{
			    		$("#addReportDialog").dialog('close');
			    		$("#dg2").datagrid("load")
			    	}
			    }
			});
		}

		/** 报告详情管理
		*********************************************************************/
		function returnDetail(){
			$("#report-detail,#report-list").toggle();
		}
		
		var editingId;
		function editReportDetail(){
			if (editingId != undefined){
				$('#tg2').treegrid('select', editingId);
				return;
			}
			var row = $('#tg2').treegrid('getSelected');
			if (row){
				editingId = row.id
				$('#tg2').treegrid('beginEdit', editingId);
			}
		}
		function saveReportDetail(){
			if (editingId != undefined){
				var t = $('#tg2');
				t.treegrid('endEdit', editingId);
				
				var data = t.treegrid('find', editingId);	

				var name = data.depth == 3 ? "" : data.name;
				var num = data.depth == 3 ? data.num : 0;
				$.ajax({ 
			        type:'get', 
			        url: '/report_edit.action', 
			        data: {id:data.id, name: name, num: num, depth: data.depth}, 
			        async:false,
			        success:function (result){ 
			        	if (result.tip == "fail") {
							alert("修改失败！");
						} else {
							if(data.depth == 3){
								t.treegrid('beginEdit', editingId);
								data.price = data.num * data.price4;
								t.treegrid('endEdit', editingId);
							}
						}
			        } 
			    });
				editingId = undefined;
			}
		}
		function cancelReportDetail(){
			if (editingId != undefined){
				$('#tg2').treegrid('cancelEdit', editingId);
				editingId = undefined;
			}
		}

		function appendDetail(){
			var node = $('#tg2').treegrid('getSelected');
			if(node.depth == 3){
				$.messager.alert('警告','没有更多级别了！');
				return;
			}
			
			var pids = "";
			if(node.depth == 2){
				var checked = $('#dg').datagrid('getChecked');
				if(checked.length <= 0){
					$.messager.alert('警告','请选择一个设备！');
					return;
				}
				
				var ids = [];
				for(var i =0; i < checked.length;i++){
					ids.push(checked[i].id); 
				}
				pids = ids.join(',');
			}
			
			
			var root = $('#tg2').treegrid('getRoot');
			$.ajax({ 
		        type:'get', 
		        url: '/report_add.action', 
		        data: {id:node.id, depth: node.depth, ids: pids},
		        success:function (result){ 
		        	if (result.tip == "fail") {
						alert("修改失败！");
					} else {
						$('#tg2').treegrid({
						    url:'/report_detail.action?id=' + root.id,
						    treeField:'name',
						    columns:[[
						        {field:'name',title:'模块名称',width:180, editor:'text'},
						        {field:'model',title:'型号',width:60},
						        {field:'brand',title:'品牌',width:80},
						        {field:'num',title:'数量',width:80, editor:'numberbox'},
						        {field:'unit',title:'单位',width:80},
						        {field:'price4',title:'单价',width:80},
						        {field:'price',title:'合价',width:80}
						    ]]
						});
					}
		        } 
		    });
		}
		
		function removeDetail(){
			var node = $('#tg2').treegrid('getSelected');
			if (node){
				$.ajax({ 
			        type:'get', 
			        url: '/report_remove.action', 
			        data: {id:node.id},
			        success:function (result){ 
			        	if (result.tip == "fail") {
							alert("删除失败！");
						} else {
							$('#tg2').treegrid('remove', node.id);
						}
			        } 
			    });
			}
		}
		function collapseAll(){
			$('#tg2').treegrid('collapseAll');
		}
		function expandAll(){
			$('#tg2').treegrid('expandAll');
		}
		
		function downloadFile(){
			var root = $('#tg2').treegrid('getRoot');
			var url = "/download.action?id=" + root.id;
			
			window.location.href= url; 
		}

		function toQueryPair(key, value) { 
			if (typeof value == 'undefined' || value === null || $.trim(value) == ''){ 
				return ""; 
			}
			
			return key + '=' + encodeURIComponent(value); 
		}
		function toQueryString(obj) { 
			var ret = []; 
			for(var key in obj){ 
				key = encodeURIComponent(key);
				var values = obj[key]; 
				if(values && values.constructor == Array){//数组 
					var queryValues = []; 
					for (var i = 0, len = values.length, value; i < len; i++) { 
						value = values[i]; 
						queryValues.push(toQueryPair(key, value)); 
					} 
					ret = ret.concat(queryValues); 
				}else{ //字符串 
					ret.push(toQueryPair(key, values)); 
				} 
			} 
			return ret.join('&'); 
		}
	</script>
</body>
</html>