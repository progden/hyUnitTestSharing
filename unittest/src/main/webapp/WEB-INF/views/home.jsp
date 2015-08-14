<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" />
<link
	href='http://fonts.googleapis.com/css?family=Inconsolata&subset=latin,latin-ext'
	rel='stylesheet' type='text/css' />
<script type="text/javascript" src="js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/angular.min.js"></script>

<script type="text/javascript">
	var fDate = function(date){
		return date.getFullYear() + '/'  + (date.getMonth() + 1) + '/' + date.getDate();
	}
	
	// main function here
	$(function() {
		reloadData();
		$("#addIssueForm").submit(function(e) {
			e.preventDefault();
			submitIssue($('#addIssueForm'), "POST");
		});

		$("#updateIssueForm").submit(function(e) {
			e.preventDefault();
			submitIssue($('#updateIssueForm'), "PUT");
			$('#addIssueForm').trigger('reset');
			renderAdd();
		});
	});
	$("#updateCancel").click(renderAdd);
	
	/* Ajax*/
	var reloadData = function(){
		$.get("/unittest/issue").success(renderList);
	};

	var update = function(id){
		renderUpdate();
		$.get("issue/"+id).success(renderUpdateIssue);
	};
	
	var submitIssue = function(_form, _type) {
		$.ajax({
			url : "issue",
			type : _type,
			data : _form.serialize(),
			mimeType : "multipart/form-data",
			cache : false,
			processData : false
		}).success(renderAfterSubmit);
	};
	
	/* Render */
	var renderUpdate = function(){
		$("#updateForm").show();
		$("#addForm").hide();
	};
	
	var renderAdd = function(){
		$("#updateForm").hide();
		$("#addForm").show();
	};
	
	var renderUpdateIssue = function(data){
		$("#updateIssueForm [name=id]").val(data.id);
		$("#updateIssueForm [name=title]").val(data.title);
		$("#updateIssueForm [name=content]").val(data.content);
		$("#updateIssueForm [name=owner]").val(data.owner);
		$("#updateIssueForm [name=creater]").val(data.creater);
		$("#updateIssueForm [name=status]").val(data.status);
		
	};
	
	var renderList = function(datas) {
		var issueList = $("#issueList");
		issueList.html("");
		for (idx in datas) {
			var data = datas[idx];
			issueList.append("<tr><td>" + data.id + "</td><td>"
					+ data.status + "</td><td>" + data.title
					+ "</td><td>" + data.content + "</td><td>"
					+ data.owner + "</td><td>"
					+ fDate(new Date(data.dtCreate)) + "</td><td>"
					+ fDate(new Date(data.dtLastupdate)) + "</td><td>"
					+  "<button class=\"btn btn-default\" onclick=\"update("+data.id+")\">update</button></td></tr>")
		}
	};

	var renderAfterSubmit = function(datas) {
		reloadData();
		$('#addIssueForm').trigger('reset');
	};
</script>
</head>
<body>

	<div class="container">
		<div class="row">
			<div class="col-md-6">

				<h1>Issue List</h1>

				<table class="table">
					<thead>
						<tr>
							<th>id</th>
							<th>status</th>
							<th>title</th>
							<th>creater</th>
							<th>owner</th>
							<th>create</th>
							<th>lastupdate</th>
							<th>func</th>
						</tr>
					</thead>
					<tbody id="issueList">

					</tbody>
				</table>
			</div>
			
			<div class="col-md-4" id="addForm">
				<h2>Add Issue</h2>
				<form id="addIssueForm" action="issue" method="post">
					<table class="table ">
						<tr>
							<th>key</th>
							<th>value</th>
						</tr>
						<tr>
							<td>id</td>
							<td><input name="id" /></td>
						</tr>
						<tr>
							<td>status</td>
							<td>新建立 <input type="hidden" name="status" value="新建立" /></td>
						</tr>
						<tr>
							<td>title</td>
							<td><input name="title" /></td>
						</tr>
						<tr>
							<td>content</td>
							<td><input name="content" /></td>
						</tr>
						<tr>
							<td>creater</td>
							<td><input name="creater" /></td>
						</tr>
						<tr>
							<td>owner</td>
							<td><input name="owner" /></td>
						</tr>
						<tr>
							<td colspan="2"><input type="submit" /> <input type="reset" /></td>
						</tr>
					</table>
				</form>
			</div>
			<div class="col-md-4" id="updateForm" style="display: none;" >
				<h2>Update Issue</h2>
				<form id="updateIssueForm" action="issue" method="put">
					<table class="table ">
						<tr>
							<th>key</th>
							<th>value</th>
						</tr>
						<tr>
							<td>id</td>
							<td><input name="id" /></td>
						</tr>
						<tr>
							<td>status</td>
							<td>
								<select name="status">
									<option value="新建立">新建立</option>
									<option value="實作中">實作中</option>
									<option value="已解決">已解決</option>
									<option value="已完成">已完成</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>title</td>
							<td><input name="title" /></td>
						</tr>
						<tr>
							<td>content</td>
							<td><input name="content" /></td>
						</tr>
						<tr>
							<td>creater</td>
							<td><input name="creater" /></td>
						</tr>
						<tr>
							<td>owner</td>
							<td><input name="owner" /></td>
						</tr>
						<tr>
							<td colspan="2"><input type="submit" /> <input type="reset" /> <button id="updateCancel">取消</button></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
