<%@page import="com.centit.sys.model.UserBean,java.util.Map"%>
<%
 UserBean aUserBean = (UserBean) session.getAttribute("UserBean");
 Map qxMap=(Map)aUserBean.getQXMap();
 request.setAttribute("qxcom",qxMap);
 %>