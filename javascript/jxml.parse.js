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

// Hmall 일반매장
dp_category1 = function(){
	$.ajax({
		url: 'http://www.hyundaihmall.com/static/2008web/xml/shop/category1.xml',
		type: 'GET',
		dataType: 'xml',
		success: function(xml) {
			if ($.isXMLDoc(xml)) {	//함수 인수가 XML 문서 객체이면 True를 반환
			
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
				
				// 서브 뎁스 매장 이동하기 숨김처리
				$("#briefing_div_01, #briefing_div_02").mouseleave(function(){
					$("#briefing_div_01").hide();
					$("#briefing_div_02").hide();
				});
	
				// 1Depth Start
				$(xml).find('category').find('d1').each(function(index) {
					depth_1	= $.trim($(this).attr('n1'));		// 1뎁스 메뉴
					depth_1_url	= $.trim($(this).attr('s1'));	// 1뎁스 링크
	
					// 메뉴명 변경
					if (depth_1 == '트렌드몰') depth_1 = '트렌드샵';
					if (depth_1 == '명품관') depth_1 = '명품|해외쇼핑';
					if (depth_1 == '디지털|AV|휴대폰') depth_1 = '디지털기기|휴대폰';
					if (depth_1 == '악기|문화|서비스|꽃') depth_1 = '악기|서비스|꽃';
					if (depth_1 == '보험|금융') depth_1 = '보험|재테크';
	
					category += "<p class=\"category_gap\" onmouseover=\"dp2("+ idx +");\"><a href=\""+ DEFAULT_LINK +""+ depth_1_url +"&MainpageGroup=CategoryFlash\">"+ depth_1 +"</" + "a></" + "p>";
					if (index == 8 || index == 17 || index == 20) category += "<p class=\"category_gap_underline\"></" + "p>";	// 카테고리 구분 언더라인
	
					idx++;
									
					// 보험/금융 이후 break!!
					if( index == 23 ) return false;
				});
	
				// 메뉴표시 append
				if (category != "") $("#category").append(category);
				
				// 2뎁스 위치 잡기
				$("#category > p").each(function(index){
					$(this).mouseenter(function(){
						var depth1_height = document.getElementById('category').offsetHeight;	// 1뎁스 길이 구하기
						var pos = $(this).position();
						
						if(index == 12){	//유아동도서/교육
							document.getElementById('depth2_category').style.top = depth1_height - depth1_height + 'px';
						} if(index == 26){	//보험/금융
							document.getElementById('depth2_category').style.top = pos.top - (depth1_height - pos.top) - 115 + 'px';
						} else {
							document.getElementById('depth2_category').style.top = -1 + 'px';
						}
					});
				});
				
				// 2Depth Start
				dp2 = function(eq){
					$("#depth3_category").hide();	// 1뎁스로 이동시 3뎁스 숨김
	
					depth2_category = "";
					depth2_category_plan = "";
					// 하위 뎁스가 없을경우 #depth2_category를 표시하지 않는다.
					var menucount = $(xml).find('category').find('d1:eq('+ eq +')').find('d2').size();
					
					// 하위 메뉴 표시 시간 0.3초 딜레이 설정
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
						depth_2 = $.trim($(this).attr('n1'));				// 2뎁스 메뉴
						depth_2_url = $.trim($(this).attr('s1'));			// 2뎁스 링크
						depth_2_plan_banner = $.trim($(this).attr('ty1'));	// 2뎁스 메뉴 형태 체크 ( default = 텍스트, banner = 배너 )
						depth_2_plan_url = $.trim($(this).attr('l1'));		// 2뎁스 기획전 링크
						depth_2_plan_img = $.trim($(this).attr('img1'));	// 2뎁스 기획전 이미지
						depth_2_special = $.trim($(this).attr('ta1'));		// 2뎁스 혜택 이미지
						
						if(depth_2_plan_banner == "title") depth2_category += "<p onmouseover=\"dp3("+ index +", "+ eq +");\" style=\"margin-top:8px;\"><strong>"+depth_2+"</" + "strong></" + "p>";	// 2뎁스 메뉴 타이틀
						else if(depth_2_plan_banner == "default") depth2_category += "<p style=\"background:url('"+ depth_2_special +"') no-repeat right; cursor:pointer;\" onmouseover=\"dp3("+ index +", "+ eq +");\"><a href=\""+ DEFAULT_LINK +""+ depth_2_url +"&MainpageGroup=CategoryFlash\">"+depth_2+"</" + "a></" + "p>";	// 2뎁스 일반 메뉴
						else if(depth_2_plan_banner == "banner" || depth_2_plan_banner == "text") depth2_category_plan += "<p class=\"cate_plan\" onmouseover=\"dp3("+ index +", "+ eq +");\"><a href=\""+ depth_2_plan_url +"&MainpageGroup=CategoryFlash\">"+depth_2+"</" + "a></" + "p>";	// 2뎁스 기획전 메뉴

						idx2++;
					});
		
					// 메뉴표시 innerHTML
					if (depth2_category != "") document.getElementById('depth2_category_ilban').innerHTML = depth2_category;
					if (depth2_category_plan != ""){	// 기획전일 경우만 내용을 표시하거나 감춘다.
						$("#depth2_category_plan").show();
						document.getElementById('depth2_category_plan').innerHTML = depth2_category_plan;
					} else {
						$("#depth2_category_plan").hide();
						document.getElementById('depth2_category_plan').innerHTML = "";
					}
					
					// 2뎁스 진입시 1뎁스 메뉴 활성화 str
					$("#depth2_category > #depth2_category_ilban > p > a").mouseenter(function(){
						$(this).css("color", "#FF7800").css("text-decoration", "underline");
						this_2.css("color", "#FF7800").css("text-decoration", "underline");
					}).mouseleave(function(){	// 2뎁스 아웃시 1뎁스 메뉴 비활성화
						$(this).css("color", "#7C7C7C").css("text-decoration", "none");
						this_3 = $(this);
					});
					
					// 2뎁스 이동간 깜빡임 fix
					$("#depth2_category").mouseleave(function(){
						this_2.css("color", "#7C7C7C").css("text-decoration", "none");
					});
				},
	
				// 3Depth Start
				dp3 = function(eq, node2){
					$("#depth3_category").width(150);	//3뎁스 기본 가로 사이즈
					
					depth3_category = "<div style=\"float:left; width:150px;\">";
					// 하위 뎁스가 없을경우 #depth3_category를 표시하지 않는다.
					var menucount = $(xml).find('category').find('d1:eq('+ node2 +')').find('d2:eq('+ eq +')').find('d3').size();
					if(menucount != 0) $("#depth3_category").show();
					else $("#depth3_category").hide();
					
					$(xml).find('category').find('d1:eq('+ node2 +')').find('d2:eq('+ eq +')').find('d3').each(function(index) {
						depth_3 = $.trim($(this).attr('n1'));				// 3뎁스 메뉴
						depth_3_url = $.trim($(this).attr('s1'));			// 3뎁스 링크 
						depth_3_plan_banner = $.trim($(this).attr('ty1'));	// 3뎁스 메뉴 형태 체크 ( title = 타이틀, line = 비그룹 카테고리, default = 텍스트, banner, text = 배너 )
						depth_3_special = $.trim($(this).attr('ta1'));		// 3뎁스 혜택 이미지
	
						// 3뎁스 갯수에 따른 #depth3_category width 변경
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
							depth3_category += "<p style=\"margin-top:8px;\"><strong>"+depth_3+"</" + "strong></" + "p>";	// 3뎁스 메뉴 타이틀
						} else {
							if(depth_3_plan_banner == "line"){	// 비그룹 카테고리 처리
								depth3_category += "<div style=\"width:100%; border-top:1px dotted #CCC;\">";
							}
							
							if(depth_3_special != null && depth_3_special != "")
								depth3_category += "<p style=\"padding-right:30px; background:url('"+ depth_3_special +"') no-repeat right;\"><a href=\""+ DEFAULT_LINK +""+ depth_3_url +"&MainpageGroup=CategoryFlash\">"+ depth_3 +"</" + "a></" + "p>";	// 3뎁스 메뉴
							else
								depth3_category += "<p><a href=\""+ DEFAULT_LINK +""+ depth_3_url +"&MainpageGroup=CategoryFlash\">"+ depth_3 +"</" + "a></" + "p>";	// 3뎁스 메뉴

							if(depth_3_plan_banner == "line"){	// 비그룹 카테고리 처리
								depth3_category += "</div>";
							}
						}
					});
					depth3_category += "</" + "div>";
					
					// 메뉴표시 innerHTML
					if (depth3_category != "") document.getElementById('depth3_category').innerHTML = depth3_category;
					
					// depth2_category a link & depth3_category pos style
					$("#depth2_category > #depth2_category_ilban > p").bind('mouseenter mousemove', function(){
						depth2_height = document.getElementById('depth2_category').offsetHeight;	// 2뎁스 길이 구하기
						depth3_height = document.getElementById('depth3_category').offsetHeight;	// 3뎁스 길이 구하기
						var pos = $(this).position();	// mouseenter된 객체의 #depth2_category로부터의 Top 구하기
						var top = 0;

						// 3뎁스 위치 잡기
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
				
				// str : 롤오버된 메뉴 기억 시키기
				$("#category > p > a").mouseenter(function(){
					$(this).css("color", "#FF7800").css("text-decoration", "underline");
				}).mouseleave(function(){
					this_2 = $(this);
					$(this).css("color", "#7C7C7C").css("text-decoration", "none");
				});

				// 3뎁스 진입시 2뎁스 메뉴 활성화
				$("#depth3_category").mouseenter(function(){
					this_2.css("color", "#FF7800").css("text-decoration", "underline");
					this_3.css("color", "#FF7800").css("text-decoration", "underline");
				}).mouseleave(function(){	// 3뎁스 이탈시 1뎁스 & 2뎁스 메뉴 비활성화
					this_2.css("color", "#7C7C7C").css("text-decoration", "none");
					this_3.css("color", "#7C7C7C").css("text-decoration", "none");
				});
				// end : 롤오버된 메뉴 기억 시키기
			}
		}
	});
}

// 현대백화점
dp_store1 = function(){
	$.ajax({
		url: 'http://www.hyundaihmall.com/static/2008web/xml/shop/store1.xml',
		type: 'GET',
		dataType: 'xml',
		success: function(xml) {
			if ($.isXMLDoc(xml)) {	//함수 인수가 XML 문서 객체이면 True를 반환
			
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
	
				// 현대백화점 바로가기
				hdpt_category += "<a href=\"http://www.hyundaihmall.com/front/dsMainR.do?MainpageGroup=CategoryFlash\" onfocus=\"blur()\"><img src=\"http://image.hyundaihmall.com/static/2008img/common/go_hdpt.gif\" alt=\"\" onmouseover=\"hidden_hdpt_cate();\" style=\"margin-bottom:3px;\"></a>";
	
				// 1Depth Start
				$(xml).find('category').find('d1').each(function(index) {
					depth_1	= $.trim($(this).attr('n1'));		// 1뎁스 메뉴
					depth_1_url	= $.trim($(this).attr('s1'));	// 1뎁스 링크
					
					if(depth_1 == '패션프리미엄 I') depth_1 = '패션프리미엄 I<br><strong class="hdpt_sub">H-Fashion</strong>';
					if(depth_1 == '패션프리미엄 II') depth_1 = '패션프리미엄 II<br><strong class="hdpt_sub">Fashion Estar</strong>';
//					if(depth_1 == '영플라자(U-PLEX)') depth_1 = '영플라자<br><strong class="hdpt_sub">(U-PLEX)</strong>';
//					if(depth_1 == '영시티몰') depth_1 = '영시티몰<br><strong class="hdpt_sub">(Kids&Sports)</strong>';
					if(depth_1 == 'U-PLEXⅠ(영플라자)') depth_1 = '<strong>U-PLEX I</strong><br><span style="font-size:11px;">영플라자</span>';
					if(depth_1 == 'U-PLEXⅡ(영/아동스포츠)') depth_1 = '<strong>U-PLEX II</strong><br><span style="font-size:11px;">영/아동스포츠</span>';
					if(depth_1 == '테마플라자|아울렛') depth_1 = '테마플라자/<br>아울렛';
					if(depth_1 == '한가위선물 예약할인') depth_1 = '<img src="http://image.hyundaihmall.com/static/2008img/common/seol.gif" alt="" />';	// 설날 선물로 대체 20110120
					
					if(index != 16){	// 쥬시꾸뛰르 PB브랜드 제외된 나머지
						if(index == 12 || index == 13 || index == 14){
							hdpt_category += "<p class=\"hdpt_category_gap2\" onmouseover=\"hdpt_dp2("+ hdpt_idx +");\">";
						} else if(index == 15 || index == 17){
							hdpt_category += "<p class=\"hdpt_category_gap3\" onmouseover=\"hdpt_dp2("+ hdpt_idx +");\">";
						} else {
							hdpt_category += "<p class=\"hdpt_category_gap\" onmouseover=\"hdpt_dp2("+ hdpt_idx +");\">";
						}
						
						// 홈리빙 아래 e수퍼마켓 추가 구문
						if(index==11){
							hdpt_category += "<a href=\""+ DEFAULT_LINK +""+ depth_1_url +"&MainpageGroup=CategoryFlash\">"+ depth_1 +"</" + "a></" + "p>";
							hdpt_category += "<p onmouseover='hidden_hdpt_cate();'><a href=\"http://esuper.ehyundai.com/esuper/index.jsp?MainpageGroup=CategoryFlash\" target=\"_blank\">e수퍼마켓</" + "a></" + "p>";
						} else {
							hdpt_category += "<a href=\""+ DEFAULT_LINK +""+ depth_1_url +"&MainpageGroup=CategoryFlash\">"+ depth_1 +"</" + "a></" + "p>";
						}
					}
					
					hdpt_idx++;
					
					// 테마플라자/아울렛 이후 break!! - 20110201
					if( index == 15 ) return false;
				});

				// 110906 - 방송상품 & 카탈로그 뎁스 강제 추가!!
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

				// 방송상품 & 카탈로그 20110314 추가
				hdpt_category += "<p onmouseover='broadDivShow();' style='margin-top:13px;'><span><a id='broadA' href='http://www.hyundaihmall.com/front/tvMainR.do?MainpageGroup=CategoryFlashLive' style='color:#54a970;' onmouseover=\"this.style.textDecoration='underline';\" onmouseout=\"this.style.textDecoration='none';\">방송상품</a></span>";
					hdpt_category += "<div id='broadDiv' onmouseover='broadDivShow();' style='position:absolute; z-index:999999; bottom:0; left:200px; width:167px; display:none; border:2px solid #925605; padding:10px; background:#FFF;'>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><strong style='color:#925605'>화장품|패션|의류</strong></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('1','makeup');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>화장품|이미용품</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('2','beauty');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>패션잡화|슈즈|명품</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('3','jewelry');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>보석|액세서리</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('4','clothes');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>여성|남성|레포츠의류</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('5','underwear');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>언더웨어</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><strong style='color:#925605'>유아|식품|생활|침구</strong></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('6','kids');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>유아동|도서</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('7','food');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>식품|건강식품</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('8','Kitchen');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>주방|생활|건강</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('9','leports');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>스포츠|자동차용품</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('10','interior');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>가구|인테리어</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('11','bed');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>침구|커튼|카페트</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><strong style='color:#925605'>가전|여행|보험</strong></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('12','electronics');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>영상가전|생활가전</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('13','computer');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>컴퓨터|게임|사무기기</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('14','digital');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>디카|핸드폰|캠코더</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('15','travel');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>여행|문화|서비스</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='broadDivShow();'><span><a href=\"javascript:goTvCategory('16','insurance');\" style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>보험|재테크</a></span></p>";
					hdpt_category += "</div>";
				hdpt_category += "</p>";
				hdpt_category += "<p onmouseover='catalogDivShow();' style='margin-top:6px;'><span><a id='catalogA' href='http://www.hyundaihmall.com/front/shSectR.do?SectID=117881&MainpageGroup=CategoryFlashClick' style='color:#54a970;' onmouseover=\"this.style.textDecoration='underline';\" onmouseout=\"this.style.textDecoration='none';\">카탈로그</a></span>";
					hdpt_category += "<div id='catalogDiv' onmouseover='catalogDivShow();' style='position:absolute; z-index:999999; bottom:0; left:200px; width:167px; display:none; border:2px solid #925605; padding:10px; background:#FFF;'>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><strong style='color:#925605'>Fashion</strong></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=164299' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>명품</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=146771' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>의류브랜드</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=146772' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>언더웨어</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=146773' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>패션잡화</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=148093' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>보석|시계</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=146775' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>화장품|미용</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><strong style='color:#925605'>Living</strong></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=147143' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>인테리어|침구</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=147094' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>가구</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=147148' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>가전</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=147145' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>주방가전</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=147146' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>주방용품</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=147147' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>생활잡화</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=147144' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>식품|건강식품</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=147093' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>유아동|도서|교육</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><strong style='color:#925605'>Health</strong></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=467521' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>건강용품</a></span></p>";
					hdpt_category += "<p style='margin-top:6px;' onmouseover='catalogDivShow();'><span><a href='/front/shSectR.do?SectID=147092' style='color:#54a970;' onmouseover='this.style.color=\"#FF6400\";' onmouseout='this.style.color=\"#54a970\";'>레포츠|헬스기구</a></span></p>";
					hdpt_category += "</div>";
				hdpt_category += "</p>";

				// 쥬시꾸띄르 & Furs Story
				//hdpt_category += "<div id='hdpt_slidebn' onmouseover='hidden_hdpt_cate();'>";
				//hdpt_category += "<a href='http://www.hyundaihmall.com/front/shSectR.do?SectID=256554&MainpageGroup=CategoryFlash' class='active'><img src='http://image.hyundaihmall.com/static/2008img/common/juicy.gif' alt=''></a>";
				//hdpt_category += "<a href='http://www.hyundaihmall.com/front/shSectR.do?SectID=490367&MainpageGroup=CategoryFlash'><img src='http://image.hyundaihmall.com/static/2008img/common/furs.gif' alt=''></a>";
				//hdpt_category += "</div>";
				
				// 메뉴표시 append
				if (hdpt_category != "") $("#hdpt_category").append(hdpt_category);

				// 쥬시꾸띄르 오버시 하위 레이어 감춤
				hidden_hdpt_cate = function(){
					time = setTimeout("time()", 50);
					time = function(){
						$("#hdpt2_category").hide();
						$("#hdpt3_category").hide();
					}
				}
				
				// 영시티몰 & 테마플라자/아울렛 & 한가위매장일 경우

				$("#hdpt_category > p").each(function(index){
					$(this).mouseenter(function(){
						var depth1_height = document.getElementById('hdpt_category').offsetHeight;	// 1뎁스 길이 구하기
						var pos = $(this).position();
						
						if(index == 13){
							document.getElementById('hdpt2_category').style.top = pos.top - pos.top + (depth1_height - pos.top) - 123 + 'px';
						} else if(index == 14){
							document.getElementById('hdpt2_category').style.top = pos.top - pos.top + (depth1_height - pos.top) - 105 + 'px';
						} else if(index >= 15 && index <= 16){
							document.getElementById('hdpt2_category').style.top = pos.top - pos.top + (depth1_height - pos.top) - 20 + 'px';
						} else if(index == 17){	// 20110120 임시 설날 매장
							document.getElementById('hdpt2_category').style.top = pos.top - pos.top + (depth1_height - pos.top) + 50 + 'px';
						} else {
							document.getElementById('hdpt2_category').style.top = -1 + 'px';
						}
					});
				});
				
				// 2Depth Start
				hdpt_dp2 = function(eq){
					$("#hdpt3_category").hide();	// 1뎁스로 이동시 3뎁스 숨김
					$("#broadDiv").hide();
					$("#catalogDiv").hide();
					document.getElementById('catalogA').style.textDecoration = 'none';
					document.getElementById('broadA').style.textDecoration = 'none';

					hdpt2_category = "";
					hdpt2_category_plan = "";
					// 하위 뎁스가 없을경우 #hdpt2_category를 표시하지 않는다.
					var menucount = $(xml).find('category').find('d1:eq('+ eq +')').find('d2').size();
					
					// 하위 메뉴 표시 시간 0.3초 딜레이 설정
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
						depth_2 = $.trim($(this).attr('n1'));				// 2뎁스 메뉴
						depth_2_url = $.trim($(this).attr('s1'));			// 2뎁스 링크
						depth_2_plan_banner = $.trim($(this).attr('ty1'));	// 2뎁스 메뉴 형태 체크 ( default = 텍스트, banner = 배너 )
						depth_2_plan_url = $.trim($(this).attr('l1'));		// 2뎁스 기획전 링크
						depth_2_plan_img = $.trim($(this).attr('img1'));	// 2뎁스 기획전 이미지
						depth_2_special = $.trim($(this).attr('ta1'));		// 2뎁스 혜택 이미지
						
						if(depth_2_plan_banner == "title") hdpt2_category += "<p onmouseover=\"hdpt_dp3("+ index +", "+ eq +");\" style=\"margin-top:8px;\"><strong>"+depth_2+"</" + "strong></" + "p>";	// 2뎁스 메뉴 타이틀
						else if(depth_2_plan_banner == "default") hdpt2_category += "<p style=\"background:url('"+ depth_2_special +"') no-repeat right; cursor:pointer;\" onmouseover=\"hdpt_dp3("+ index +", "+ eq +");\"><a href=\""+ DEFAULT_LINK +""+ depth_2_url +"&MainpageGroup=CategoryFlash\">"+depth_2+"</" + "a></" + "p>";	// 2뎁스 일반 메뉴
						else if(depth_2_plan_banner == "banner" || depth_2_plan_banner == "text") hdpt2_category_plan += "<p class=\"cate_plan\" onmouseover=\"hdpt_dp3("+ index +", "+ eq +");\"><a href=\""+ depth_2_plan_url +"&MainpageGroup=CategoryFlash\">"+depth_2+"</" + "a></" + "p>";	// 2뎁스 기획전 메뉴
						
						hdpt_idx2++;
					});

					// 메뉴표시 innerHTML
					if (hdpt2_category != "") document.getElementById('hdpt2_category_ilban').innerHTML = hdpt2_category;
					if (hdpt2_category_plan != ""){	// 기획전일 경우만 내용을 표시하거나 감춘다.
						$("#hdpt2_category_plan").show();
						document.getElementById('hdpt2_category_plan').innerHTML = hdpt2_category_plan;
					} else {
						$("#hdpt2_category_plan").hide();
						document.getElementById('hdpt2_category_plan').innerHTML = "";
					}
					
					
					// 2뎁스 진입시 1뎁스 메뉴 활성화 str
					$("#hdpt2_category > #hdpt2_category_ilban > p > a").mouseenter(function(){
						$(this).css("color", "#925605").css("text-decoration", "underline");
						this_h2.css("color", "#925605").css("text-decoration", "underline");
					}).mouseleave(function(){	// 2뎁스 아웃시 1뎁스 메뉴 비활성화
						$(this).css("color", "#7C7C7C").css("text-decoration", "none");
						//this_h2.css("color", "#7C7C7C").css("text-decoration", "none");
						this_h3 = $(this);
					});
					
					// 2뎁스 이동간 깜빡임 fix
					$("#hdpt2_category").mouseleave(function(){
						this_h2.css("color", "#7C7C7C").css("text-decoration", "none");
					});
				},
	
				// 3Depth Start
				hdpt_dp3 = function(eq, node2){
					$("#hdpt3_category").width(150);	//3뎁스 기본 가로 사이즈
					
					hdpt3_category = "<div style=\"float:left; width:150px;\">";
					// 하위 뎁스가 없을경우 #hdpt3_category를 표시하지 않는다.
					var menucount = $(xml).find('category').find('d1:eq('+ node2 +')').find('d2:eq('+ eq +')').find('d3').size();
					if(menucount != 0) $("#hdpt3_category").show();
					else $("#hdpt3_category").hide();

					$(xml).find('category').find('d1:eq('+ node2 +')').find('d2:eq('+ eq +')').find('d3').each(function(index) {
						depth_3 = $.trim($(this).attr('n1'));				// 3뎁스 메뉴
						depth_3_url = $.trim($(this).attr('s1'));			// 3뎁스 링크 
						depth_3_plan_banner = $.trim($(this).attr('ty1'));	// 3뎁스 메뉴 형태 체크 ( title = 타이틀, line = 비그룹 카테고리, default = 텍스트, banner, text = 배너 )
						depth_3_special = $.trim($(this).attr('ta1'));		// 3뎁스 혜택 이미지
	
						// 3뎁스 갯수에 따른 #hdpt3_category width 변경
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
							hdpt3_category += "<p style=\"margin-top:8px;\"><strong>"+depth_3+"</" + "strong></" + "p>";	// 3뎁스 메뉴 타이틀
						} else {
							if(depth_3_plan_banner == "line"){	// 비그룹 카테고리 처리
								hdpt3_category += "<div style=\"width:100%; border-top:1px dotted #CCC;\">";
							}
							
							if(depth_3_special != null && depth_3_special != "")
								hdpt3_category += "<p style=\"padding-right:30px; background:url('"+ depth_3_special +"') no-repeat right;\"><a href=\""+ DEFAULT_LINK +""+ depth_3_url +"&MainpageGroup=CategoryFlash\">"+ depth_3 +"</" + "a></" + "p>";	// 3뎁스 메뉴
							else
								hdpt3_category += "<p><a href=\""+ DEFAULT_LINK +""+ depth_3_url +"&MainpageGroup=CategoryFlash\">"+ depth_3 +"</" + "a></" + "p>";	// 3뎁스 메뉴

							if(depth_3_plan_banner == "line"){	// 비그룹 카테고리 처리
								hdpt3_category += "</div>";
							}
						}
					});
					hdpt3_category += "</" + "div>";
					
					// 메뉴표시 innerHTML
					if (hdpt3_category != "") document.getElementById('hdpt3_category').innerHTML = hdpt3_category;
					
					// hdpt2_category a link & hdpt3_category pos style
					$("#hdpt2_category > #hdpt2_category_ilban > p").bind('mouseenter mousemove', function(){
						var depth2_height = document.getElementById('hdpt2_category').offsetHeight;	// 2뎁스 길이 구하기
						var depth2_offsetTop = document.getElementById('hdpt2_category').offsetTop;	// 2뎁스 길이 구하기
						var depth3_height = document.getElementById('hdpt3_category').offsetHeight;	// 3뎁스 길이 구하기
						var pos = $(this).position();	// mouseenter된 객체의 #hdpt2_category로부터의 Top 구하기
						
						// 3뎁스 위치 잡기
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
									
				// str : 롤오버된 메뉴 기억 시키기
				$("#hdpt_category > p > a").mouseenter(function(){
					$(this).css("color", "#925605").css("text-decoration", "underline");
				}).mouseleave(function(){
					this_h2 = $(this);
					$(this).css("color", "#7C7C7C").css("text-decoration", "none");
				});

				// 3뎁스 진입시 2뎁스 메뉴 활성화
				$("#hdpt3_category").mouseenter(function(){
					this_h2.css("color", "#925605").css("text-decoration", "underline");
					this_h3.css("color", "#925605").css("text-decoration", "underline");
				}).mouseleave(function(){	// 3뎁스 이탈시 1뎁스 & 2뎁스 메뉴 비활성화
					this_h2.css("color", "#7C7C7C").css("text-decoration", "none");
					this_h3.css("color", "#7C7C7C").css("text-decoration", "none");
				});
				// end : 롤오버된 메뉴 기억 시키기
			}
		}
	});
}

// 백화점 카테고리 하단 롤링 배너
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
	dp_category1();	// Hmall 일반매장 카테고리
	dp_store1();	// 현대백화점 카테고리
	//setInterval("slideSwitch()", 5000);	// 카테고리 백화점 하단 롤링 배너
});