<%@ page import="java.io.*,java.awt.*,java.awt.image.*,javax.imageio.*" %>
<%
response.setContentType("image/png");

int width = 150;
int height = 50;

BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
Graphics2D g = image.createGraphics();

// Background
g.setColor(Color.WHITE);
g.fillRect(0, 0, width, height);

// Random text
String captcha = String.valueOf((int)(Math.random() * 9000) + 1000);

// Store in session
session.setAttribute("captcha", captcha);

// Draw text
g.setColor(Color.BLACK);
g.setFont(new Font("Arial", Font.BOLD, 30));
g.drawString(captcha, 40, 35);

g.dispose();

ImageIO.write(image, "png", response.getOutputStream());
response.getOutputStream().close();
%>
