<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@include file="/WEB-INF/views/common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>list</title>
</head>
<script type="text/javascript">

	$j(document).ready(function(){
	    $j("#btnNext").on("click",function(){
	        var allChecked = true;
	
	        $j(".answer").each(function(){
	            if($j(this).find("input[type=radio]:checked").length == 0){
	                allChecked = false;
	                return false;
	            }
	        });
	
	        if(!allChecked){
	            alert("모두 선택해주세요.");
	            return;
	        }
	
	        $j("#mbtiForm").submit();
	    });
	});

</script>
<body>
<div class="survey">	
<form id="mbtiForm" action="/mbti/mbtiAnswerAction.do" method="post">
    <input type="hidden" name="pageNo" value="${pageNo}">    
		<table  align="center">
			<tr>
				<td align="right">
					${pageNo} / ${totalPage}
				</td>
			</tr>
			<tr>
				<td>
					<table id="boardTable" border = "1">
						<tr>
							<td width="600" align="center">
								질문
							</td>
						</tr>
						<c:forEach items="${questionList}" var="q">
						    <c:set var="key" value="${q.boardType}_${q.boardNum}"/>
						    <tr>
						        <td width="600" align="center">
						            <div class="question">
						                [${q.boardType}] ${q.boardComment}
						            </div>
						            <div class="answer">
						                <span>동의</span>
											<c:forEach begin="1" end="7" var="s">
											    <input type="radio" name="answer_${key}" value="${s}"
											        <c:if test="${answers[key] == s}">
											        	checked
											        </c:if>>
											</c:forEach>
						                <span>비동의</span>
						            </div>
						        </td>
						    </tr>
						</c:forEach>
					</table>
				</td>
			</tr>
			<tr>
				<td align="left">
					<c:if test="${pageNo > 1}">
						<a href="/mbti/mbtiQuestionList.do?pageNo=${pageNo - 1}">이전</a>
					</c:if>
				</td>
				
				<td align="right">
					<input id="btnNext" type="button" value="다음">
				</td>
			</tr>
		</table>	
	</form>
	</div>
</body>
</html>