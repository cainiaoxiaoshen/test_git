<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<ul id="myTab" class="nav nav-tabs tab-hd">
		<li class="active"><a href="#local" data-toggle="tab">本地文件</a></li>
		<li><a href="#online" data-toggle="tab">在线网页</a></li>
	</ul>
	<div id="myTabContent" class="tab-content">
		<div class="tab-pane fade in active tab-local" id="local">
			<form id="uploadDocumentForm">
			<div class="row bd_m">
				<div class="col-lg-2 col-md-3 col-sm-4">
					<ul class="nav nav-stacked nav_m">
						<li class="active" id="doctype_1"><a href="javascript:selectDocType(1, 2, 3)">标准文档</a></li>
						<li id="doctype_2"><a href="javascript:selectDocType(2, 1, 3)">法规文档</a></li>
						<li id="doctype_3"><a href="javascript:selectDocType(3, 2, 1)">研究报告</a></li>
					</ul>
				</div>
				<div class="col-lg-10 col-md-9 col-sm-8">
					<div class="file-upload">
						<span class="file-selected">
							<input class="form-control" type="text" id="selectedFile" disabled="disabled">
						</span>
						<span class="browse"><b>选择文件</b>
							<input size="3" class="file-preview" type="file" id="fileUpload" name="fileUpload" onchange="document.getElementById('selectedFile').value=this.value">
						</span>
						<input type="hidden" name="type" id="docType" value="1">
						<input value="导入"
							class="subbtn " type="button" onclick="uploadDocument()">
						<p class="tipsInfo">注：支持文件格式为pdf、html,请导入单个文件或者把多个文件压缩为zip后导入</p>
					</div>
				</div>
			</div>
			</form>
		</div>
		<div class="tab-pane fade tab-online" id="online">
			<div class="row bd_m">
				<div class="col-lg-2 col-md-3 col-sm-4">
					<ul class="nav nav-stacked nav_m">
						<li id="htmlUrltype_1" class="active"><a href="javascript:selectHtmlUrlType(1, 2, 3)">标准文档</a></li>
						<li id="htmlUrltype_2"><a href="javascript:selectHtmlUrlType(2, 1, 3)">法规文档</a></li>
						<li id="htmlUrltype_3"><a href="javascript:selectHtmlUrlType(3, 2, 1)">研究报告</a></li>
					</ul>
				</div>
			<div class="col-lg-10 col-md-9 col-sm-8">
				<div class="doc_table">
					<div class="single-upload">
						<span class="label-upload">1. 单条输入</span><span
							class="file-selected" style="width: 160px;"> <input id="uploadHtmlStandardNo"
							class="form-control" type="text" placeholder="请输入文档编号">
						</span><span class="file-selected"> <input class="form-control" id="uploadHtmlReqUrl"
							type="text" placeholder="请输入在线网页">
						</span> <input value="导入" class="subbtn " type="button" onclick="uploadHtmlUrl()">
						<p class="tips-error" id="singleUploadTips"></p>
					</div>
						<form id="uploadUrlExcelForm">
						<input type="hidden" id="htmlUrltype" name="typeExcel" value="1">
						<div class="file-upload online-upload">
							<span class="label-upload">2. 批量输入</span><span
								class="file-selected"> <input class="form-control" id="excelFilename"
								type="text">
							</span><span class="browse"><b>选择文件</b> <input size="3" id="excelFile" name="excelFile" onchange="document.getElementById('excelFilename').value=this.value"
								class="file-preview" type="file"> </span>
								<input value="导入" class="subbtn " type="button" onclick="uploadExcel()">
							<p class="tips-error" id="excelUploadTips"></p>
							<p class="tipsInfo">注：请上传包含文档编号、网址两列数据的Excel文件，无需写表头，一次数量不超过1万条</p>
						</div>
					</form>
				</div>
			</div>
			</div>
		</div>
	</div>