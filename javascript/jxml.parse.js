var category = "";
var depth2_category = "";
var depth2_category_plan = "";
var depth3_category = "";
var hdpt_category = "";
var hdpt2_category = "";
var hdpt2_category_plan = "";
var hdpt3_category = "";
var idx = 0;
var idx2 = 0;
var idx_plan = 0;
var hdpt_idx = 0;
var hdpt_idx2 = 0;
var depth_1, depth_1_url, depth_2, depth_2_url, depth_2_plan_url, depth_2_plan_banner, depth_2_special, depth_2_plan_img, depth_3, depth_3_url, depth_3_plan_banner, depth_3_special;
var DEFAULT_LINK = ""+HM_CONTEXT_ROOT+"/shSectR.do?SectID=";
var this_2, this_3, this_h2, this_h3;
var src;

// Hmall �Ϲݸ���
dp_category1 = function(){
	$.ajax({
		url: 'http://www.hyundaihmall.com/static/2008web/xml/shop/category1.xml',
		type: 'GET',
		dataType: 'xml',
		success: function(xml) {
			if ($.isXMLDoc(xml)) {	//�Լ� �μ��� XML ���� ��ü�̸� True�� ��ȯ
			
				// depth2_category, depth3_category Border
				$("#depth2_category, #depth3_category").mouseenter(function(){
					$(this).css("border","2px solid #FA6F15");
				}).mouseleave(function(){
					$(this).css("border","2px solid #CCC");
				});
				
				// depth2_category, depth3_category hidden
				$("#category").mouseleave(function(){
					$("#depth2_category").hide();
					$("#depth3_category").hide();
				});
				
				// ���� ���� ���� �̵��ϱ� ����ó��
				$("#briefing_div_01, #briefing_div_02").mouseleave(function(){
					$("#briefing_div_01").hide();
					$("#briefing_div_02").hide();
				});
	
				// 1Depth Start
				$(xml).find('category').find('d1').each(function(index) {
					depth_1	= $.trim($(this).attr('n1'));		// 1���� �޴�
					depth_1_url	= $.trim($(this).attr('s1'));	// 1���� ��ũ
	
					// �޴��� ����
					if (depth_1 == 'Ʈ�����') depth_1 = 'Ʈ���弥';
					if (depth_1 == '��ǰ��') depth_1 = '��ǰ|�ؿܼ���';
					if (depth_1 == '������|AV|�޴���') depth_1 = '�����б��|�޴���';
					if (depth_1 == '�Ǳ�|��ȭ|����|��') depth_1 = '�Ǳ�|����|��';
					if (depth_1 == '����|����') depth_1 = '����|����ũ';
	
					category += "<p class=\"category_gap\" onmouseover=\"dp2("+ idx +");\"><a href=\""+ DEFAULT_LINK +""+ depth_1_url +"&MainpageGroup=CategoryFlash\">"+ depth_1 +"</" + "a></" + "p>";
					if (index == 8 || index == 17 || index == 20) category += "<p class=\"category_gap_underline\"></" + "p>";	// ī�װ� ���� �������
	
					idx++;
									
					// ����/���� ���� break!!
					if( index == 23 ) return false;
				});
	
				// �޴�ǥ�� append
				if (category != "") $("#category").append(category);
				
				// 2���� ��ġ ���
				$("#category > p").each(function(index){
					$(this).mouseenter(function(){
						var depth1_height = document.getElementById('category').offsetHeight;	// 1���� ���� ���ϱ�
						var pos = $(this).position();
						
						if(index == 12){	//���Ƶ�����/����
							document.getElementById('depth2_category').style.top = depth1_height - depth1_height + 'px';
						} if(index == 26){	//����/����
							document.getElementById('depth2_category').style.top = pos.top - (depth1_height - pos.top) - 115 + 'px';
						} else {
							document.getElementById('depth2_category').style.top = -1 + 'px';
						}
					});
				});
				
				// 2Depth Start
				dp2 = function(eq){
					$("#depth3_category").hide();	// 1������ �̵��� 3���� ����
	
					depth2_category = "";
					depth2_category_plan = "";
					// ���� ������ ������� #depth2_category�� ǥ������ �ʴ´�.
					var menucount = $(xml).find('category').find('d1:eq('+ eq +')').find('d2').size();
					
					// ���� �޴� ǥ�� �ð� 0.3�� ������ ����
					time = setTimeout("time()", 300);
					time = function(){
						if(menucount != 0) $("#depth2_category").show();
						else $("#depth2_category").hide();
					}
						
					$("#category").mouseleave(function(){
						time = setTimeout("time()", 50);
						time = function(){
							$("#depth2_category").hide();
						}
					});
					
					$(xml).find('category').find('d1:eq('+ eq +')').find('d2').each(function(index) {
						depth_2 = $.trim($(this).attr('n1'));				// 2���� �޴�
						depth_2_url = $.trim($(this).attr('s1'));			// 2���� ��ũ
						depth_2_plan_banner = $.trim($(this).attr('ty1'));	// 2���� �޴� ���� üũ ( default = �ؽ�Ʈ, banner = ��� )
						depth_2_plan_url = $.trim($(this).attr('l1'));		// 2���� ��ȹ�� ��ũ
						depth_2_plan_img = $.trim($(this).attr('img1'));	// 2���� ��ȹ�� �̹���
						depth_2_special = $.trim($(this).attr('ta1'));		// 2���� ���� �̹���
						
						if(depth_2_plan_banner == "title") depth2_category += "<p onmouseover=\"dp3("+ index +", "+ eq +");\" style=\"margin-top:8px;\"><strong>"+depth_2+"</" + "strong></" + "p>";	// 2���� �޴� Ÿ��Ʋ
						else if(depth_2_plan_banner == "default") depth2_category += "<p style=\"background:url('"+ depth_2_special +"') no-repeat right; cursor:pointer;\" onmouseover=\"dp3("+ index +", "+ eq +");\"><a href=\""+ DEFAULT_LINK +""+ depth_2_url +"&MainpageGroup=CategoryFlash\">"+depth_2+"</" + "a></" + "p>";	// 2���� �Ϲ� �޴�
						else if(depth_2_plan_banner == "banner" || depth_2_plan_banner == "text") depth2_category_plan += "<p class=\"cate_plan\" onmouseover=\"dp3("+ index +", "+ eq +");\"><a href=\""+ depth_2_plan_url +"&MainpageGroup=CategoryFlash\">"+depth_2+"</" + "a></" + "p>";	// 2���� ��ȹ�� �޴�

						idx2++;
					});
		
					// �޴�ǥ�� innerHTML
					if (depth2_category != "") document.getElementById('depth2_category_ilban').innerHTML = depth2_category;
					if (depth2_category_plan != ""){	// ��ȹ���� ��츸 ������ ǥ���ϰų� �����.
						$("#depth2_category_plan").show();
						document.getElementById('depth2_category_plan').innerHTML = depth2_category_plan;
					} else {
						$("#depth2_category_plan").hide();
						document.getElementById('depth2_category_plan').innerHTML = "";
					}
					
					// 2���� ���Խ� 1���� �޴� Ȱ��ȭ str
					$("#depth2_category > #depth2_category_ilban > p > a").mouseenter(function(){
						$(this).css("color", "#FF7800").css("text-decoration", "underline");
						this_2.css("color", "#FF7800").css("text-decoration", "underline");
					}).mouseleave(function(){	// 2���� �ƿ��� 1���� �޴� ��Ȱ��ȭ
						$(this).css("color", "#7C7C7C").css("text-decoration", "none");
						this_3 = $(this);
					});
					
					// 2���� �̵��� ������ fix
					$("#depth2_category").mouseleave(function(){
						this_2.css("color", "#7C7C7C").css("text-decoration", "none");
					});
				},
	
				// 3Depth Start
				dp3 = function(eq, node2){
					$("#depth3_category").width(150);	//3���� �⺻ ���� ������
					
					depth3_category = "<div style=\"float:left; width:150px;\">";
					// ���� ������ ������� #depth3_category�� ǥ������ �ʴ´�.
					var menucount = $(xml).find('category').find('d1:eq('+ node2 +')').find('d2:eq('+ eq +')').find('d3').size();
					if(menucount != 0) $("#depth3_category").show();
					else $("#depth3_category").hide();
					
					$(xml).find('category').find('d1:eq('+ node2 +')').find('d2:eq('+ eq +')').find('d3').each(function(index) {
						depth_3 = $.trim($(this).attr('n1'));				// 3���� �޴�
						depth_3_url = $.trim($(this).attr('s1'));			// 3���� ��ũ 
						depth_3_plan_banner = $.trim($(this).attr('ty1'));	// 3���� �޴� ���� üũ ( title = Ÿ��Ʋ, line = ��׷� ī�װ�, default = �ؽ�Ʈ, banner, text = ��� )
						depth_3_special = $.trim($(this).attr('ta1'));		// 3���� ���� �̹���
	
						// 3���� ������ ���� #depth3_category width ����
						if(index != 0 && index%23 == 0){
							var w;
							if(index == 23){
								w = 331;
							} else if(index == 46){
								w = 512;
							} else if(index == 69){
								w = 693;
							}
							$("#depth3_category").width(w);
							depth3_category += "</" + "div><div style=\"float:left; width:150px; margin-left:15px; padding-left:15px; border-left:1px solid #E5E5E5;\">";
						}

						if(depth_3_plan_banner == "title"){
							depth3_category += "<p style=\"margin-top:8px;\"><strong>"+depth_3+"</" + "strong></" + "p>";	// 3���� �޴� Ÿ��Ʋ
						} else {
							if(depth_3_plan_banner == "line"){	// ��׷� ī�װ� ó��
								depth3_category += "<div style=\"width:100%; border-top:1px dotted #CCC;\">";
							}
							
							if(depth_3_special != null && depth_3_special != "")
								depth3_category += "<p style=\"padding-right:30px; background:url('"+ depth_3_special +"') no-repeat right;\"><a href=\""+ DEFAULT_LINK +""+ depth_3_url +"&MainpageGroup=CategoryFlash\">"+ depth_3 +"</" + "a></" + "p>";	// 3���� �޴�
							else
								depth3_category += "<p><a href=\""+ DEFAULT_LINK +""+ depth_3_url +"&MainpageGroup=CategoryFlash\">"+ depth_3 +"</" + "a></" + "p>";	// 3���� �޴�

							if(depth_3_plan_banner == "line"){	// ��׷� ī�װ� ó��
								depth3_category += "</div>";
							}
						}
					});
					depth3_category += "</" + "div>";
					
					// �޴�ǥ�� innerHTML
					if (depth3_category != "") document.getElementById('depth3_category').innerHTML = depth3_category;
					
					// depth2_category a link & depth3_category pos style
					$("#depth2_category > #depth2_category_ilban > p").bind('mouseenter mousemove', function(){
						depth2_height = document.getElementById('depth2_category').offsetHeight;	// 2���� ���� ���ϱ�
						depth3_height = document.getElementById('depth3_category').offsetHeight;	// 3���� ���� ���ϱ�
						var pos = $(this).position();	// mouseenter�� ��ü�� #depth2_category�κ����� Top ���ϱ�
						var top = 0;

						// 3���� ��ġ ���
						if(depth2_height < (pos.top + depth3_height)){
							if(depth2_height < depth3_height){
								document.getElementById('depth3_category').style.top = depth2_height - depth2_height - 1 + 'px';
							} else {
								document.getElementById('depth3_category').style.top = depth2_height - depth3_height + 'px';
							}
						} else {
							document.getElementById('depth3_category').style.top = pos.top + 'px';
						}
					});
				}
				
				// str : �ѿ����� �޴� ��� ��Ű��
				$("#category > p > a").mouseenter(function(){
					$(this).css("color", "#FF7800").css("text-decoration", "underline");
				}).mouseleave(function(){
					this_2 = $(this);
					$(this).css("color", "#7C7C7C").css("text-decoration", "none");
				});

				// 3���� ���Խ� 2���� �޴� Ȱ��ȭ
				$("#depth3_category").mouseenter(function(){
					this_2.css("color", "#FF7800").css("text-decoration", "underline");
					this_3.css("color", "#FF7800").css("text-decoration", "underline");
				}).mouseleave(function(){	// 3���� ��Ż�� 1���� & 2���� �޴� ��Ȱ��ȭ
					this_2.css("color", "#7C7C7C").css("text-decoration", "none");
					this_3.css("color", "#7C7C7C").css("text-decoration", "none");
				});
				// end : �ѿ����� �޴� ��� ��Ű��
			}
		}
	});
}

// �����ȭ��
dp_store1 = function(){
	$.ajax({
		url: 'http://www.hyundaihmall.com/static/2008web/xml/shop/store1.xml',
		type: 'GET',
		dataType: 'xml',
		success: function(xml) {
			if ($.isXMLDoc(xml)) {	//�Լ� �μ��� XML ���� ��ü�̸� True�� ��ȯ
			
				// hdpt2_category, hdpt3_category Border
				$("#hdpt2_category, #hdpt3_category").mouseenter(function(){
					$(this).css("border","2px solid #925605");
				}).mouseleave(function(){
					$(this).css("border","2px solid #CCC");
				});
				
				// hdpt2_category, hdpt3_category hidden
				$("#hdpt_category").mouseleave(function(){
					$("#hdpt2_category").hide();
					$("#hdpt3_category").hide();
					$("#broadDiv").hide();
					$("#catalogDiv").hide();

					document.getElementById('catalogA').style.textDecoration = 'none';
					document.getElementById('broadA').style.textDecoration = 'none';
				});
	
				// �����ȭ�� �ٷΰ���
				hdpt_category += "<a href=\"http://www.hyundaihmall.com/front/dsMainR.do?MainpageGroup=CategoryFlash\" onfocus=\"blur()\"><img src=\"http://image.hyundaihmall.com/static/2008img/common/go_hdpt.gif\" alt=\"\" onmouseover=\"hidden_hdpt_cate();\" style=\"margin-bottom:3px;\"></a>";
	
				// 1Depth Start
				$(xml).find('category').find('d1').each(function(index) {
					depth_1	= $.trim($(this).attr('n1'));		// 1���� �޴�
					depth_1_url	= $.trim($(this).attr('s1'));	// 1���� ��ũ
					
					if(depth_1 == '�м������̾� I') depth_1 = '�м������̾� I<br><strong class="hdpt_sub">H-Fashion</strong>';
					if(depth_1 == '�м������̾� II') depth_1 = '�м������̾� II<br><strong class="hdpt_sub">Fashion Estar</strong>';
//					if(depth_1 == '���ö���(U-PLEX)') depth_1 = '���ö���<br><strong class="hdpt_sub">(U-PLEX)</strong>';
//					if(depth_1 == '����Ƽ��') depth_1 = '����Ƽ��<br><strong class="hdpt_sub">(Kids&Sports)</strong>';
					if(depth_1 == 'U-PLEX��(���ö���)') depth_1 = '<strong>U-PLEX I</strong><br><span style="font-size:11px;">���ö���</span>';
					if(depth_1 == 'U-PLEX��(��/�Ƶ�������)') depth_1 = '<strong>U-PLEX II</strong><br><span style="font-size:11px;">��/�Ƶ�������</span>';
					if(depth_1 == '�׸��ö���|�ƿ﷿') depth_1 = '�׸��ö���/<br>�ƿ﷿';
					if(depth_1 == '�Ѱ������� ��������') depth_1 = '<img src="http://image.hyundaihmall.com/static/2008img/common/seol.gif" alt="" />';	// ���� ������ ��ü 20110120
					
					if(index != 16){	// ��òٶٸ� PB�귣�� ���ܵ� ������
						if(index == 12 || index == 13 || index == 14){
							hdpt_category += "<p class=\"hdpt_category_gap2\" onmouseover=\"hdpt_dp2("+ hdpt_idx +");\">";
						} else if(index == 15 || index == 17){
							hdpt_category += "<p class=\"hdpt_category_gap3\" onmouseover=\"hdpt_dp2("+ hdpt_idx +");\">";
						} else {
							hdpt_category += "<p class=\"hdpt_category_gap\" onmouseover=\"hdpt_dp2("+ hdpt_idx +");\">";
						}
						
						// Ȩ���� �Ʒ� e���۸��� �߰� ����
						if(index==11){
							hdpt_category += "<a href=\""+ DEFAULT_LINK +""+ depth_1_url +"&MainpageGroup=CategoryFlash\">"+ depth_1 +"</" + "a></" + "p>";
							hdpt_category += "<p onmouseover='hidden_hdpt_cate();'><a href=\"http://esuper.ehyundai.com/esuper/index.jsp?MainpageGroup=CategoryFlash\" target=\"_blank\">e���۸���</" + "a></" + "p>";
						} else {
							hdpt_category += "<a href=\""+ DEFAULT_LINK +""+ depth_1_url +"&MainpageGroup=CategoryFlash\">"+ depth_1 +"</" + "a></" + "p>";
						}
					}
					
					hdpt_idx++;
					
					// �׸��ö���/�ƿ﷿ ���� break!! - 20110201
					if( index == 15 ) return false;
				});

				// 110906 - ��ۻ�ǰ & īŻ�α� ���� ���� �߰�!!
				goTvCategory = function(cate,bannername) {
					params = "?menuType=category";
					params += "&menuNum=" + cate;
					params += "&TVHomepageGroup=category";	
					params += "&Groupbannername=" + bannername;;		
					url = "/front/tvSubR.do";
					
					window.location.href = url + params;
				}

				broadDivShow = function(){
					$("#hdpt2_category").hide();
					if(document.getElementById('catalogDiv').style.display == 'inline'){
						document.getElementById('catalogDiv').style.display = 'none'
					}
					document.getElementById('broadDiv').style.display = 'inline';

					if(document.getElementById('broadDiv').style.display == 'inline'){
						document.getElementById('broadA').style.textDecoration = 'underline';
					}
				}

				catalogDivShow = function(){
					$("#hdpt2_category").hide();
					if(document.getElementById('broadDiv').style.display == 'inline'){
						document.getElementById('broadDiv').style.display = 'none'
					}
					document.getElementById('catalogDiv').style.display = 'inline';

					if(document.getElementById('catalogDiv').style.display == 'inline'){
						document.getElementById('catalogA').style.textDecoration = 'underline';
					}
				}

				hidden_catalogDiv = function(){
					document.getElementById('catalogDiv').style.display = 'none';
				}
				hidden_broadDiv = function(){
					document.getElementById('broadDiv').style.display = 'none';
				}

				// ��ۻ�ǰ & īŻ�α� 20110314 �߰�
				hdpt_category += "<p onmouseover='broadDivShow();' style='margin-top:13px;'><span><a id='broadA' href='http://www.hyundaihmall.com/front/tvMainR.do?MainpageGroup=CategoryFlashLive' style='color:#54a970;' onmouseover=\"this.style.textDecoration='underline';\" onmouseout=\"this.style.textDecoration='none';\">��ۻ�ǰ</a></span>";
					hdpt_category += "<div id='broadDiv' onmouseover='broadDivShow();' style='position:absolute; z-index:999999; bottom:0; left:200px; width:167px; display:none; border:2px solid #925605; padding:10px; background:#FFF;'>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><strong style='color:#925605'>ȭ��ǰ|�м�|�Ƿ�</strong></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('1','makeup');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>ȭ��ǰ|�̹̿�ǰ</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('2','beauty');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>�м���ȭ|����|��ǰ</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('3','jewelry');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>����|�׼�����</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('4','clothes');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>����|����|�������Ƿ�</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('5','underwear');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>�������</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><strong style='color:#925605'>����|��ǰ|��Ȱ|ħ��</strong></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('6','kids');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>���Ƶ�|����</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('7','food');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>��ǰ|�ǰ���ǰ</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('8','Kitchen');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>�ֹ�|��Ȱ|�ǰ�</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('9','leports');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>������|�ڵ�����ǰ</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('10','interior');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>����|���׸���</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('11','bed');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>ħ��|Ŀư|ī��Ʈ</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><strong style='color:#925605'>����|����|����</strong></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('12','electronics');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>������|��Ȱ����</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('13','computer');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>��ǻ��|����|�繫���</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('14','digital');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>��ī|�ڵ���|ķ�ڴ�</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('15','travel');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>����|��ȭ|����</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('16','insurance');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>����|����ũ</a></span></p>";
					hdpt_category += "</div>";
				hdpt_category += "</p>";
				hdpt_category += "<p onmouseover='catalogDivShow();' style='margin-top:6px;'><span><a id='catalogA' href='http://www.hyundaihmall.com/front/shSectR.do?SectID=117881&MainpageGroup=CategoryFlashClick' style='color:#54a970;' onmouseover=\"this.style.textDecoration='underline';\" onmouseout=\"this.style.textDecoration='none';\">īŻ�α�</a></span>";
					hdpt_category += "<div id='catalogDiv' onmouseover='catalogDivShow();' style='position:absolute; z-index:999999; bottom:0; left:200px; width:167px; display:none; border:2px solid #925605; padding:10px; background:#FFF;'>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><strong style='color:#925605'>Fashion</strong></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=164299' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>��ǰ</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=146771' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>�Ƿ��귣��</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=146772' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>�������</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=146773' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>�м���ȭ</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=148093' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>����|�ð�</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=146775' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>ȭ��ǰ|�̿�</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><strong style='color:#925605'>Living</strong></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=147143' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>���׸���|ħ��</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=147094' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>����</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=147148' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>����</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=147145' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>�ֹ氡��</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=147146' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>�ֹ��ǰ</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=147147' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>��Ȱ��ȭ</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=147144' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>��ǰ|�ǰ���ǰ</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=147093' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>���Ƶ�|����|����</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><strong style='color:#925605'>Health</strong></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=467521' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>�ǰ���ǰ</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=147092' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>������|�ｺ�ⱸ</a></span></p>";
					hdpt_category += "</div>";
				hdpt_category += "</p>";

				// ��òٶ縣 & Furs Story
				//hdpt_category += "<div id='hdpt_slidebn' onmouseover='hidden_hdpt_cate();'>";
				//hdpt_category += "<a href='http://www.hyundaihmall.com/front/shSectR.do?SectID=256554&MainpageGroup=CategoryFlash' class='active'><img src='http://image.hyundaihmall.com/static/2008img/common/juicy.gif' alt=''></a>";
				//hdpt_category += "<a href='http://www.hyundaihmall.com/front/shSectR.do?SectID=490367&MainpageGroup=CategoryFlash'><img src='http://image.hyundaihmall.com/static/2008img/common/furs.gif' alt=''></a>";
				//hdpt_category += "</div>";
				
				// �޴�ǥ�� append
				if (hdpt_category != "") $("#hdpt_category").append(hdpt_category);

				// ��òٶ縣 ������ ���� ���̾� ����
				hidden_hdpt_cate = function(){
					time = setTimeout("time()", 50);
					time = function(){
						$("#hdpt2_category").hide();
						$("#hdpt3_category").hide();
					}
				}
				
				// ����Ƽ�� & �׸��ö���/�ƿ﷿ & �Ѱ��������� ���

				$("#hdpt_category > p").each(function(index){
					$(this).mouseenter(function(){
						var depth1_height = document.getElementById('hdpt_category').offsetHeight;	// 1���� ���� ���ϱ�
						var pos = $(this).position();
						
						if(index == 13){
							document.getElementById('hdpt2_category').style.top = pos.top - pos.top + (depth1_height - pos.top) - 123 + 'px';
						} else if(index == 14){
							document.getElementById('hdpt2_category').style.top = pos.top - pos.top + (depth1_height - pos.top) - 105 + 'px';
						} else if(index >= 15 && index <= 16){
							document.getElementById('hdpt2_category').style.top = pos.top - pos.top + (depth1_height - pos.top) - 20 + 'px';
						} else if(index == 17){	// 20110120 �ӽ� ���� ����
							document.getElementById('hdpt2_category').style.top = pos.top - pos.top + (depth1_height - pos.top) + 50 + 'px';
						} else {
							document.getElementById('hdpt2_category').style.top = -1 + 'px';
						}
					});
				});
				
				// 2Depth Start
				hdpt_dp2 = function(eq){
					$("#hdpt3_category").hide();	// 1������ �̵��� 3���� ����
					$("#broadDiv").hide();
					$("#catalogDiv").hide();
					document.getElementById('catalogA').style.textDecoration = 'none';
					document.getElementById('broadA').style.textDecoration = 'none';

					hdpt2_category = "";
					hdpt2_category_plan = "";
					// ���� ������ ������� #hdpt2_category�� ǥ������ �ʴ´�.
					var menucount = $(xml).find('category').find('d1:eq('+ eq +')').find('d2').size();
					
					// ���� �޴� ǥ�� �ð� 0.3�� ������ ����
					time = setTimeout("time()", 300);
					time = function(){
						if(menucount != 0) $("#hdpt2_category").show();
						else $("#hdpt2_category").hide();
					}
						
					$("#hdpt_category").mouseleave(function(){
						time = setTimeout("time()", 50);
						time = function(){
							$("#hdpt2_category").hide();
						}
					});
					
					$(xml).find('category').find('d1:eq('+ eq +')').find('d2').each(function(index) {
						depth_2 = $.trim($(this).attr('n1'));				// 2���� �޴�
						depth_2_url = $.trim($(this).attr('s1'));			// 2���� ��ũ
						depth_2_plan_banner = $.trim($(this).attr('ty1'));	// 2���� �޴� ���� üũ ( default = �ؽ�Ʈ, banner = ��� )
						depth_2_plan_url = $.trim($(this).attr('l1'));		// 2���� ��ȹ�� ��ũ
						depth_2_plan_img = $.trim($(this).attr('img1'));	// 2���� ��ȹ�� �̹���
						depth_2_special = $.trim($(this).attr('ta1'));		// 2���� ���� �̹���
						
						if(depth_2_plan_banner == "title") hdpt2_category += "<p onmouseover=\"hdpt_dp3("+ index +", "+ eq +");\" style=\"margin-top:8px;\"><strong>"+depth_2+"</" + "strong></" + "p>";	// 2���� �޴� Ÿ��Ʋ
						else if(depth_2_plan_banner == "default") hdpt2_category += "<p style=\"background:url('"+ depth_2_special +"') no-repeat right; cursor:pointer;\" onmouseover=\"hdpt_dp3("+ index +", "+ eq +");\"><a href=\""+ DEFAULT_LINK +""+ depth_2_url +"&MainpageGroup=CategoryFlash\">"+depth_2+"</" + "a></" + "p>";	// 2���� �Ϲ� �޴�
						else if(depth_2_plan_banner == "banner" || depth_2_plan_banner == "text") hdpt2_category_plan += "<p class=\"cate_plan\" onmouseover=\"hdpt_dp3("+ index +", "+ eq +");\"><a href=\""+ depth_2_plan_url +"&MainpageGroup=CategoryFlash\">"+depth_2+"</" + "a></" + "p>";	// 2���� ��ȹ�� �޴�
						
						hdpt_idx2++;
					});

					// �޴�ǥ�� innerHTML
					if (hdpt2_category != "") document.getElementById('hdpt2_category_ilban').innerHTML = hdpt2_category;
					if (hdpt2_category_plan != ""){	// ��ȹ���� ��츸 ������ ǥ���ϰų� �����.
						$("#hdpt2_category_plan").show();
						document.getElementById('hdpt2_category_plan').innerHTML = hdpt2_category_plan;
					} else {
						$("#hdpt2_category_plan").hide();
						document.getElementById('hdpt2_category_plan').innerHTML = "";
					}
					
					
					// 2���� ���Խ� 1���� �޴� Ȱ��ȭ str
					$("#hdpt2_category > #hdpt2_category_ilban > p > a").mouseenter(function(){
						$(this).css("color", "#925605").css("text-decoration", "underline");
						this_h2.css("color", "#925605").css("text-decoration", "underline");
					}).mouseleave(function(){	// 2���� �ƿ��� 1���� �޴� ��Ȱ��ȭ
						$(this).css("color", "#7C7C7C").css("text-decoration", "none");
						//this_h2.css("color", "#7C7C7C").css("text-decoration", "none");
						this_h3 = $(this);
					});
					
					// 2���� �̵��� ������ fix
					$("#hdpt2_category").mouseleave(function(){
						this_h2.css("color", "#7C7C7C").css("text-decoration", "none");
					});
				},
	
				// 3Depth Start
				hdpt_dp3 = function(eq, node2){
					$("#hdpt3_category").width(150);	//3���� �⺻ ���� ������
					
					hdpt3_category = "<div style=\"float:left; width:150px;\">";
					// ���� ������ ������� #hdpt3_category�� ǥ������ �ʴ´�.
					var menucount = $(xml).find('category').find('d1:eq('+ node2 +')').find('d2:eq('+ eq +')').find('d3').size();
					if(menucount != 0) $("#hdpt3_category").show();
					else $("#hdpt3_category").hide();

					$(xml).find('category').find('d1:eq('+ node2 +')').find('d2:eq('+ eq +')').find('d3').each(function(index) {
						depth_3 = $.trim($(this).attr('n1'));				// 3���� �޴�
						depth_3_url = $.trim($(this).attr('s1'));			// 3���� ��ũ 
						depth_3_plan_banner = $.trim($(this).attr('ty1'));	// 3���� �޴� ���� üũ ( title = Ÿ��Ʋ, line = ��׷� ī�װ�, default = �ؽ�Ʈ, banner, text = ��� )
						depth_3_special = $.trim($(this).attr('ta1'));		// 3���� ���� �̹���
	
						// 3���� ������ ���� #hdpt3_category width ����
						if(index != 0 && index%23 == 0){
							var w;
							if(index == 23){
								w = 331;
							} else if(index == 46){
								w = 512;
							} else if(index == 69){
								w = 693;
							}
							$("#hdpt3_category").width(w);
							hdpt3_category += "</" + "div><div style=\"float:left; width:150px; margin-left:15px; padding-left:15px; border-left:1px solid #E5E5E5;\">";
						}
						
						if(depth_3_plan_banner == "title"){
							hdpt3_category += "<p style=\"margin-top:8px;\"><strong>"+depth_3+"</" + "strong></" + "p>";	// 3���� �޴� Ÿ��Ʋ
						} else {
							if(depth_3_plan_banner == "line"){	// ��׷� ī�װ� ó��
								hdpt3_category += "<div style=\"width:100%; border-top:1px dotted #CCC;\">";
							}
							
							if(depth_3_special != null && depth_3_special != "")
								hdpt3_category += "<p style=\"padding-right:30px; background:url('"+ depth_3_special +"') no-repeat right;\"><a href=\""+ DEFAULT_LINK +""+ depth_3_url +"&MainpageGroup=CategoryFlash\">"+ depth_3 +"</" + "a></" + "p>";	// 3���� �޴�
							else
								hdpt3_category += "<p><a href=\""+ DEFAULT_LINK +""+ depth_3_url +"&MainpageGroup=CategoryFlash\">"+ depth_3 +"</" + "a></" + "p>";	// 3���� �޴�

							if(depth_3_plan_banner == "line"){	// ��׷� ī�װ� ó��
								hdpt3_category += "</div>";
							}
						}
					});
					hdpt3_category += "</" + "div>";
					
					// �޴�ǥ�� innerHTML
					if (hdpt3_category != "") document.getElementById('hdpt3_category').innerHTML = hdpt3_category;
					
					// hdpt2_category a link & hdpt3_category pos style
					$("#hdpt2_category > #hdpt2_category_ilban > p").bind('mouseenter mousemove', function(){
						var depth2_height = document.getElementById('hdpt2_category').offsetHeight;	// 2���� ���� ���ϱ�
						var depth2_offsetTop = document.getElementById('hdpt2_category').offsetTop;	// 2���� ���� ���ϱ�
						var depth3_height = document.getElementById('hdpt3_category').offsetHeight;	// 3���� ���� ���ϱ�
						var pos = $(this).position();	// mouseenter�� ��ü�� #hdpt2_category�κ����� Top ���ϱ�
						
						// 3���� ��ġ ���
						if(depth2_height > (pos.top + depth3_height)){
							if(depth2_offsetTop != 0){
								document.getElementById('hdpt3_category').style.top = pos.top + depth2_offsetTop + 'px';
							} else {
								document.getElementById('hdpt3_category').style.top = pos.top + 'px';
							}
						} else {
							if(depth2_offsetTop != 0){
								if(depth2_height < depth3_height){
									document.getElementById('hdpt3_category').style.top = depth2_height - depth2_height - 1 + 'px';
								} else {
									document.getElementById('hdpt3_category').style.top = depth2_height - depth3_height + depth2_offsetTop + 'px';
								}
							} else {
								if(depth2_height < depth3_height){
									document.getElementById('hdpt3_category').style.top = depth2_height - depth2_height - 1 + 'px';
								} else {
									document.getElementById('hdpt3_category').style.top = depth2_height - depth3_height + 'px';
								}
							}
						}
					});
				}
									
				// str : �ѿ����� �޴� ��� ��Ű��
				$("#hdpt_category > p > a").mouseenter(function(){
					$(this).css("color", "#925605").css("text-decoration", "underline");
				}).mouseleave(function(){
					this_h2 = $(this);
					$(this).css("color", "#7C7C7C").css("text-decoration", "none");
				});

				// 3���� ���Խ� 2���� �޴� Ȱ��ȭ
				$("#hdpt3_category").mouseenter(function(){
					this_h2.css("color", "#925605").css("text-decoration", "underline");
					this_h3.css("color", "#925605").css("text-decoration", "underline");
				}).mouseleave(function(){	// 3���� ��Ż�� 1���� & 2���� �޴� ��Ȱ��ȭ
					this_h2.css("color", "#7C7C7C").css("text-decoration", "none");
					this_h3.css("color", "#7C7C7C").css("text-decoration", "none");
				});
				// end : �ѿ����� �޴� ��� ��Ű��
			}
		}
	});
}

// ��ȭ�� ī�װ� �ϴ� �Ѹ� ���
function slideSwitch() {
    var $active = $('#hdpt_slidebn a.active');
    if ( $active.length == 0 ) $active = $('#hdpt_slidebn a:last');
    var $next =  $active.next().length ? $active.next()
        : $('#hdpt_slidebn a:first');

    $active.addClass('last-active');

    $next.css({opacity: 0.0})
        .addClass('active')
        .animate({opacity: 1.0}, 1000, function() {
            $active.removeClass('active last-active');
        });
}

$(document).ready(function() {
	dp_category1();	// Hmall �Ϲݸ��� ī�װ�
	dp_store1();	// �����ȭ�� ī�װ�
	//setInterval("slideSwitch()", 5000);	// ī�װ� ��ȭ�� �ϴ� �Ѹ� ���
});