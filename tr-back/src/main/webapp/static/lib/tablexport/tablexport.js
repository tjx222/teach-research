(function( factory ) {
	if ( typeof define === "function" && define.amd ) {
		define(["jquery",'./base64'], factory);
	} else {
		factory( jQuery );
	}
}(function($) {
	$.fn.extend({
		tableExport : function(options, e) {
			var defaults = {
				separator : ',',
				ignoreColumn : [],
				tableName : 'sqlTableName',
				fileName : '',
				type : 'csv',
				pdfFontSize : 14,
				pdfLeftMargin : 20,
				escape : 'true',
				htmlContent : 'false',
				consoleLog : 'false'
			};
			var event = e;
			var options = $.extend(defaults, options);
			var el = this;

			if (defaults.fileName == "") {
				defaults.fileName = getExcelFileName();
			}
			if (defaults.fileName.split('.', 2).length <= 1) {
				switch (defaults.type) {
				case 'sql':
					defaults.fileName += ".sql";
					break;
				case 'json':
					defaults.fileName += ".json";
					break;
				case 'xml':
					defaults.fileName += ".xml";
					break;
				case 'excel':
					defaults.fileName += ".xls";
					break;
				case 'doc':
					defaults.fileName += ".doc";
					break;
				case 'powerpoint':
					defaults.fileName += ".ppt";
					break;
				case 'pdf':
					defaults.fileName += ".pdf";
					break;
				default:
					defaults.fileName += ".txt";
					break;
				}
			}
			if (defaults.type == 'csv' || defaults.type == 'txt') {
				// Header
				var tdData = "";
				$(el).find('thead').find('tr').each(function() {
					tdData += "\n";
					$(this).filter(':visible').find('th').each(function(index, data) {
						if ($(this).css('display') != 'none') {
							if (defaults.ignoreColumn.indexOf(index) == -1) {
								tdData += '"' + parseString($(this)) + '"' + defaults.separator;
							}
						}
					});
					tdData = $.trim(tdData);
					tdData = $.trim(tdData).substring(0, tdData.length - 1);
				});
				// Row vs Column
				$(el).find('tbody').find('tr').each(function() {
					tdData += "\n";
					$(this).filter(':visible').find('td').each(function(index, data) {
						if ($(this).css('display') != 'none') {
							if (defaults.ignoreColumn.indexOf(index) == -1) {
								tdData += '"' + parseString($(this)) + '"' + defaults.separator;
							}
						}
					});
					// tdData = $.trim(tdData);
					tdData = $.trim(tdData).substring(0, tdData.length - 1);
				});
				// output
				if (defaults.consoleLog == 'true') {
					console.log(tdData);
				}
				var base64data = "base64," + $.mybase64({
					data : tdData,
					type : 0
				});
				window.open('data:text/plain;filename=' + defaults.fileName + ';' + base64data);
			} else if (defaults.type == 'sql') {
				// Header
				var tdData = "INSERT INTO `" + defaults.tableName + "` (";
				$(el).find('thead').find('tr').each(function() {
					$(this).filter(':visible').find('th').each(function(index, data) {
						if ($(this).css('display') != 'none') {
							if (defaults.ignoreColumn.indexOf(index) == -1) {
								tdData += '`' + parseString($(this)) + '`,';
							}
						}
					});
					tdData = $.trim(tdData);
					tdData = $.trim(tdData).substring(0, tdData.length - 1);
				});
				tdData += ") VALUES ";
				// Row vs Column
				$(el).find('tbody').find('tr').each(function() {
					tdData += "(";
					$(this).filter(':visible').find('td').each(function(index, data) {
						if ($(this).css('display') != 'none') {
							if (defaults.ignoreColumn.indexOf(index) == -1) {
								tdData += '"' + parseString($(this)) + '",';
							}
						}
					});
					tdData = $.trim(tdData).substring(0, tdData.length - 1);
					tdData += "),";
				});
				tdData = $.trim(tdData).substring(0, tdData.length - 1);
				tdData += ";";
				// output
				if (defaults.consoleLog == 'true') {
					console.log(tdData);
				}
				var base64data = "base64," + $.mybase64({
					data : tdData,
					type : 0
				});
				window.open('data:application/sql;filename=' + defaults.fileName + ';' + base64data);
			} else if (defaults.type == 'json') {
				var jsonHeaderArray = [];
				$(el).find('thead').find('tr').each(function() {
					var tdData = "";
					var jsonArrayTd = [];
					$(this).filter(':visible').find('th').each(function(index, data) {
						if ($(this).css('display') != 'none') {
							if (defaults.ignoreColumn.indexOf(index) == -1) {
								jsonArrayTd.push(parseString($(this)));
							}
						}
					});
					jsonHeaderArray.push(jsonArrayTd);
				});
				var jsonArray = [];
				$(el).find('tbody').find('tr').each(function() {
					var tdData = "";
					var jsonArrayTd = [];
					$(this).filter(':visible').find('td').each(function(index, data) {
						if ($(this).css('display') != 'none') {
							if (defaults.ignoreColumn.indexOf(index) == -1) {
								jsonArrayTd.push(parseString($(this)));
							}
						}
					});
					jsonArray.push(jsonArrayTd);
				});
				var jsonExportArray = [];
				jsonExportArray.push({
					header : jsonHeaderArray,
					data : jsonArray
				});
				// Return as JSON
				if (defaults.consoleLog == 'true') {
					console.log(JSON.stringify(jsonExportArray));
				}
				var base64data = "base64," + $.mybase64({
					data : JSON.stringify(jsonExportArray),
					type : 0
				});
				window.open('data:application/json;filename=' + defaults.fileName + ';' + base64data);
			} else if (defaults.type == 'xml') {
				var xml = '<?xml version="1.0" encoding="utf-8"?>';
				xml += '<tabledata><fields>';
				// Header
				$(el).find('thead').find('tr').each(function() {
					$(this).filter(':visible').find('th').each(function(index, data) {
						if ($(this).css('display') != 'none') {
							if (defaults.ignoreColumn.indexOf(index) == -1) {
								xml += "<field>" + parseString($(this)) + "</field>";
							}
						}
					});
				});
				xml += '</fields><data>';
				// Row Vs Column
				var rowCount = 1;
				$(el).find('tbody').find('tr').each(function() {
					xml += '<row id="' + rowCount + '">';
					var colCount = 0;
					$(this).filter(':visible').find('td').each(function(index, data) {
						if ($(this).css('display') != 'none') {
							if (defaults.ignoreColumn.indexOf(index) == -1) {
								xml += "<column-" + colCount + ">" + parseString($(this)) + "</column-" + colCount + ">";
							}
						}
						colCount++;
					});
					rowCount++;
					xml += '</row>';
				});
				xml += '</data></tabledata>'
				if (defaults.consoleLog == 'true') {
					console.log(xml);
				}
				var base64data = "base64," + $.mybase64({
					data : xml,
					type : 0
				});
				window.open('data:application/xml;filename=' + defaults.fileName + ';' + base64data);
			} else if (defaults.type == 'excel' || defaults.type == 'doc' || defaults.type == 'powerpoint') {
				var excel = "<table>";
				// Header
				$(el).find('thead').find('tr').each(function() {
					excel += "<tr>";
					$(this).filter(':visible').find('th').each(function(index, data) {
						if ($(this).css('display') != 'none') {
							if (defaults.ignoreColumn.indexOf(index) == -1) {
								excel += "<td>" + parseString($(this)) + "</td>";
							}
						}
					});
					excel += '</tr>';
				});
				// Row Vs Column
				var rowCount = 1;
				$(el).find('tbody').find('tr').each(function() {
					excel += "<tr>";
					var colCount = 0;
					$(this).filter(':visible').find('td').each(function(index, data) {
						if ($(this).css('display') != 'none') {
							if (defaults.ignoreColumn.length == 0 || defaults.ignoreColumn.indexOf(index) == -1) {
								excel += "<td>" + parseString($(this)) + "</td>";
							}
						}
						colCount++;
					});
					rowCount++;
					excel += '</tr>';
				});
				excel += '</table>'
				if (defaults.consoleLog == 'true') {
					console.log(excel);
				}
				var excelFile = "<html xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:x='urn:schemas-microsoft-com:office:" + defaults.type + "' xmlns='http://www.w3.org/TR/REC-html40'>";
				excelFile += "<head>";
				excelFile += "<!--[if gte mso 9]>";
				excelFile += "<xml>";
				excelFile += "<x:ExcelWorkbook>";
				excelFile += "<x:ExcelWorksheets>";
				excelFile += "<x:ExcelWorksheet>";
				excelFile += "<x:Name>";
				excelFile += "{worksheet}";
				excelFile += "</x:Name>";
				excelFile += "<x:WorksheetOptions>";
				excelFile += "<x:DisplayGridlines/>";
				excelFile += "</x:WorksheetOptions>";
				excelFile += "</x:ExcelWorksheet>";
				excelFile += "</x:ExcelWorksheets>";
				excelFile += "</x:ExcelWorkbook>";
				excelFile += "</xml>";
				excelFile += "<![endif]-->";
				excelFile += "</head>";
				excelFile += "<body>";
				excelFile += excel;
				excelFile += "</body>";
				excelFile += "</html>";
				if (ieDownload(el, defaults.fileName,excelFile)) {
					var base64data = "base64," + $.mybase64({
						data : excelFile,
						type : 0
					});
					noIeDownloadFile(defaults.type, defaults.fileName, base64data);
				}
			} else if (defaults.type == 'png') {
				html2canvas($(el), {
					onrendered : function(canvas) {
						var img = canvas.toDataURL("image/png");
						window.open(img);
					}
				});
			} else if (defaults.type == 'pdf') {
				var doc = new jsPDF('p', 'pt', 'a4', true);
				doc.setFontSize(defaults.pdfFontSize);
				// Header
				var startColPosition = defaults.pdfLeftMargin;
				$(el).find('thead').find('tr').each(function() {
					$(this).filter(':visible').find('th').each(function(index, data) {
						if ($(this).css('display') != 'none') {
							if (defaults.ignoreColumn.indexOf(index) == -1) {
								var colPosition = startColPosition + (index * 50);
								doc.text(colPosition, 20, parseString($(this)));
							}
						}
					});
				});
				// Row Vs Column
				var startRowPosition = 20;
				var page = 1;
				var rowPosition = 0;
				$(el).find('tbody').find('tr').each(function(index, data) {
					rowCalc = index + 1;
					if (rowCalc % 26 == 0) {
						doc.addPage();
						page++;
						startRowPosition = startRowPosition + 10;
					}
					rowPosition = (startRowPosition + (rowCalc * 10)) - ((page - 1) * 280);
					$(this).filter(':visible').find('td').each(function(index, data) {
						if ($(this).css('display') != 'none') {
							if (defaults.ignoreColumn.length <= 0 || defaults.ignoreColumn.indexOf(index) == -1) {
								var colPosition = startColPosition + (index * 50);
								doc.text(colPosition, rowPosition, parseString($(this)));
							}
						}
					});
				});
				// Output as Data URI
				doc.output('datauri', defaults);
			}

			function parseString(data) {
				if (defaults.htmlContent == 'true') {
					content_data = $.trim(data.html());
				} else {
					content_data = $.trim(data.text());
				}
				if (defaults.escape == 'true') {
					content_data = escape(content_data);
				}
				return content_data;
			}
			
			function ieDownload(el, fileName,TblData) {
				var inTblId = $(el).attr("id");
				var userAgent = navigator.userAgent; // 取得浏览器的userAgent字符串
				if (userAgent.search(/Trident/i)
						&& (userAgent.indexOf("rv:11.0") > -1 || userAgent.indexOf("MSIE 10.0") > -1 || userAgent.indexOf("MSIE 9.0") > -1 || userAgent.indexOf("MSIE 8.0") > -1 || userAgent.indexOf("MSIE 7.0") > -1)) {
					// 如果是IE浏览器
					try {
						var allStr = "";
						var curStr = "";
						if (inTblId != null && inTblId != "" && inTblId != "null") {
							curStr = TblData;
						}
						if (curStr != null) {
							allStr += curStr;
						} else {
							alert("你要导出的表不存在！");
							return;
						}
						doFileExport(fileName, allStr);
					} catch (e) {
						alert("导出发生异常:" + e.name + "->" + e.description + "!");
					}
					return false;
				} else {
					return true;//非ie浏览器
				}
			}
			
			//ie下提取表格数据
//			function getTblData(inTbl, inWindow) {
//				var rows = 0;
//				var tblDocument = document;
//				if (!!inWindow && inWindow != "") {
//					if (!document.all(inWindow)) {
//						return null;
//					} else {
//						tblDocument = eval(inWindow).document;
//					}
//				}
//				var curTbl = tblDocument.getElementById(inTbl);
//				if (curTbl.rows.length > 65000) {
//					alert('源行数不能大于65000行');
//					return false;
//				}
//				if (curTbl.rows.length <= 1) {
//					alert('数据源没有数据');
//					return false;
//				}
//				var outStr = "";
//				if (curTbl != null) {
//					for (var j = 0; j < curTbl.rows.length; j++) {
//						for (var i = 0; i < curTbl.rows[j].cells.length; i++) {
//							if (i == 0 && rows > 0) {
//								outStr += " \t";
//								rows -= 1;
//							}
//							var tc = curTbl.rows[j].cells[i];
//							if (j > 0 && tc.hasChildNodes() && tc.firstChild.nodeName.toLowerCase() == "input") {
//								if (tc.firstChild.type.toLowerCase() == "checkbox") {
//									if (tc.firstChild.checked == true) {
//										outStr += "是" + "\t";
//									} else {
//										outStr += "否" + "\t";
//									}
//								}
//							} else {
//								outStr += " " + curTbl.rows[j].cells[i].innerText + "\t";
//							}
//							if (curTbl.rows[j].cells[i].colSpan > 1) {
//								for (var k = 0; k < curTbl.rows[j].cells[i].colSpan - 1; k++) {
//									outStr += " \t";
//								}
//							}
//							if (i == 0) {
//								if (rows == 0 && curTbl.rows[j].cells[i].rowSpan > 1) {
//									rows = curTbl.rows[j].cells[i].rowSpan - 1;
//								}
//							}
//						}
//						outStr += "\r\n";
//					}
//				} else {
//					outStr = null;
//					alert(inTbl + "不存在!");
//				}
//				return outStr;
//			}
			// 默认文件名-年月日时分秒
			function getExcelFileName() {
				var d = new Date();
				var curYear = d.getYear();
				var curMonth = "" + (d.getMonth() + 1);
				var curDate = "" + d.getDate();
				var curHour = "" + d.getHours();
				var curMinute = "" + d.getMinutes();
				var curSecond = "" + d.getSeconds();
				if (curMonth.length == 1) {
					curMonth = "0" + curMonth;
				}
				if (curDate.length == 1) {
					curDate = "0" + curDate;
				}
				if (curHour.length == 1) {
					curHour = "0" + curHour;
				}
				if (curMinute.length == 1) {
					curMinute = "0" + curMinute;
				}
				if (curSecond.length == 1) {
					curSecond = "0" + curSecond;
				}
				var fileName = curYear + curMonth + curDate + curHour + curMinute + curSecond;
				return fileName;
			}
			// ie浏览器下下载文件
			function doFileExport(inName, inStr) {
				var xlsWin = document.createElement("IFRAME");
				document.body.insertBefore(xlsWin);
				var url = _STATIC_BASEPATH_ + "/lib/tableExport/ieIframe.html";
				xlsWin.outerHTML = "<iframe   name='a1'   style='width:0;hieght:0'   src='" + url + "'></iframe>";
				xlsWin = window.open(url, "a1");
				xlsWin.document.write(inStr);
				xlsWin.document.execCommand('Saveas', true, inName);
				$("iframe[name='a1']").remove();
			}
			// 非ie浏览器下下载文件
			function noIeDownloadFile(type, fileName, content) {
				var a = $("<a/>");
				var data_type = 'data:application/vnd.ms-' + type;
				a.attr("href", data_type + ';' + content);
				a.attr("download", fileName);
				$(el).append(a);
				a[0].click();
				event.preventDefault();
			}
		}
	});
}));
