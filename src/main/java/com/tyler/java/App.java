package com.tyler.java;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.TextAlignment;
import com.tyler.java.borders.MyDashedBorder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
//    private static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) {
        PdfWriter pdfWriter = null;
        registerFonts();
        try {
            logger.debug("start...");
            pdfWriter = new PdfWriter("./app.pdf");
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            PdfPage page = pdfDocument.addNewPage();
            Document document = new Document(pdfDocument);
            document.setMargins(45, 30, 45, 30);
            PdfFont font1 = PdfFontFactory.createRegisteredFont("MicrosoftYaHei", PdfEncodings.IDENTITY_H, true);
            PdfFont font2 = PdfFontFactory.createRegisteredFont("SimSun", PdfEncodings.IDENTITY_H, true);
            document.add(new Paragraph("招商银行交易流水")
                    .setFont(font2)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(10)
                    .setMarginBottom(2)
            );

            document.add(new Paragraph("Transaction Statement of China Merchants Bank")
                    .setFont(font2)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(10)
                    .setMarginTop(2)
                    .setFontColor(ColorConstants.BLACK)
            );

            document.add(new Paragraph("2018-08-26 -- 2019-02-26")
                    .setFont(font2)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(10)
                    .setMarginTop(2)
                    .setFontColor(ColorConstants.BLACK)
            );


            Table t = addTable(font2);
            document.add(t);

            Table table = new Table(new float[]{1, 1, 1, 1, 2, 2});
            table.setWidth(document.getPdfDocument().getDefaultPageSize().getWidth() - 60);
            String[][] header = {{"记账日期", "Date"},{"货币", "Currency"}, {"交易金额", "Transaction Amount"}, {"联机金额", "Balance"}, {"交易摘要", "Transaction Type"}, {"对手信息", "Counter Party"}};
            for (String[] c: header) {
                Cell headerCell = new Cell().setBorder(Border.NO_BORDER).setBorderTop(new MyDashedBorder(0.4f)).setBorderBottom(new MyDashedBorder(0.4f));
                Paragraph p = new Paragraph().setFont(font2);
                for (String v:  c) {
                    p.add(v);
                }
                headerCell.add(p);
                table.addHeaderCell(headerCell);
            }
            String[] data = {"2018-09-04", "人民币", "-339.00", "0.43", "银联无卡自助消费", "待清算电子交换会查一卡通银联交易资金"};
            for (String d: data) {
                table.addCell(new Cell().add(new Paragraph(d).setFont(font2)).setBorder(Border.NO_BORDER));
            }
            logger.debug(UUID.randomUUID().toString());
            document.add(table);
            document.close();
            logger.debug("pdf created");
        } catch (IOException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                pdfWriter.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }

        }
    }

   private static void registerFonts() {
        String p1 = Thread.currentThread().getContextClassLoader().getResource("static/fonts/msyh.ttf").getPath();
        PdfFontFactory.register(p1);
        String p2 = Thread.currentThread().getContextClassLoader().getResource("static/fonts/simsun.ttc").getPath() + ",0";
        PdfFontFactory.register(p2);
   }

   private static void canvas(PdfPage page) {
       PdfCanvas canvas = new PdfCanvas(page);
       canvas.saveState()
               .setLineWidth(0.4f)
               .setLineDash(5, 2, 0)
               .moveTo(100, 800)
               .lineTo(400, 800)
               .stroke()
               .restoreState();
   }

   private static Table addTable(PdfFont font) throws IOException {
        PdfFont bold = PdfFontFactory.createRegisteredFont("MicrosoftYaHei", PdfEncodings.IDENTITY_H, true);
       Table t = new Table(new float[]{1, 1});
       Cell c1 = new Cell().setBorder(Border.NO_BORDER);
       c1.add(new Paragraph("户   名:  王梦涛").setFont(font)).add(new Paragraph("Name").setFont(bold));
       Cell c2 = new Cell().setBorder(Border.NO_BORDER);
       c2.add(new Paragraph("账号: 6214851251198698").setFont(font)).add(new Paragraph("Account No").setFont(bold));
       Cell c3 = new Cell().setBorder(Border.NO_BORDER);
       c3.add(new Paragraph("账户类型:  ALL/全币种").setFont(font)).add(new Paragraph("Account Type").setFont(bold));
       Cell c4 = new Cell().setBorder(Border.NO_BORDER);
       c4.add(new Paragraph("电子流水号: b6b6583f-31eb-42ba-95c9-f39e7101ccc5").setFont(font)).add(new Paragraph("Serial No").setFont(bold));
       Cell c5 = new Cell().setBorder(Border.NO_BORDER);
       c5.add(new Paragraph("开 户 行:  南京分行南昌路支行(本部)").setFont(font)).add(new Paragraph("Sub Branch").setFont(bold));
       t.addCell(c1).addCell(c2).addCell(c3).addCell(c4).addCell(c5);
       return t;
   }
}
