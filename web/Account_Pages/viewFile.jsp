<%--
  Created by IntelliJ IDEA.
  User: Vasken
  Date: 10/10/2019
  Time: 8:45 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="application/pdf;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ page import="DBConnection.DBConnection" %>
<%@ page import="java.sql.*" %>
    <%@ page import="java.net.URLConnection" %>
    <%@ page import="java.io.*" %>


    <html>
<head>
    <title>Title</title>
</head>
<body>

    <%!
        private static final int BUFFER_SIZE = 1024 * 100;
    %><%

String filePath = request.getParameter("pdfPath");
        File file = new File(filePath);
        OutputStream outStream = null;
        FileInputStream inputStream = null;

        if (file.exists()) {

            /**** Setting The Content Attributes For The Response Object ****/
            String mimeType = "application/octet-stream";
            response.setContentType(mimeType);

            /**** Setting The Headers For The Response Object ****/
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", file.getName());
            response.setHeader(headerKey, headerValue);

            try {

                /**** Get The Output Stream Of The Response ****/
                outStream = response.getOutputStream();
                inputStream = new FileInputStream(file);
                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead = -1;

                /**** Write Each Byte Of Data Read From The Input Stream Write Each Byte Of Data  Read From The Input Stream Into The Output Stream ****/
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }
            } catch (IOException ioExObj) {
                System.out.println("Exception While Performing The I/O Operation?= " + ioExObj.getMessage());
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                outStream.flush();
                if (outStream != null) {
                    outStream.close();
                }
            }


        }



    %>
<%--<%
    Connection con = null;
    ResultSet rs = null;
    Statement statment = null;
    PreparedStatement pstmt = null;
    String sql = "SELECT * FROM mydb.pdf_store";
    Blob file = null;
    String fileName = null;
    byte[] filedata = null;
    try {
        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);
            statment = con.createStatement();
        }catch(Exception ex){
            out.print("error");
        }
        rs = statment.executeQuery(sql);
        try {
            while (rs.next()){
                Integer wantedID = rs.getInt("pdf_store.message_id");
                String wantedFileName = rs.getString("pdf_store.file_name");
                if(wantedID == Integer.parseInt(request.getParameter("messageID")) && wantedFileName.equals(request.getParameter("pdfName")) ) {
                    file = rs.getBlob("pdf");
                    filedata = file.getBytes(1, (int) file.length());
                    fileName = rs.getString("pdf_store.file_name");
                }
            }


            response.setContentType("application/pdf");
            response.setContentLength((int) file.length());
            response.setHeader("Content-disposition", "attachment; filename="+ fileName);
            InputStream is = file.getBinaryStream();
            OutputStream os = response.getOutputStream();
            byte buf[] = new byte[(int) file.length()];
            is.read(buf);
            os.write(buf);
            os.close();




        }catch(Exception ex) {
            out.print("error3");
        }


    }catch (Exception ex){
        out.print("error 2");
    }--%>
<%--%>--%>



</body>
</html>

</body>
</html>