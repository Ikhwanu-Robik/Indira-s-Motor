/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import database.Database;

import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.HashMap;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

import com.itextpdf.html2pdf.HtmlConverter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author MyBook Hype AMD
 */
public class PrintController {

    public void print(ArrayList<HashMap<String, String>> data) throws IOException {
        Database db = new Database();
        ResultSet rs = null;

        if (data.get(0).get("type").equals("receipt")) {
            //delete the first data as it is only for checking type
            data.remove(0);

            //create HTML
            File dir = new File("C:/IndiraMotorKasir/html_templates");
            if (!dir.exists()) {
            	dir.mkdirs();
            }
            File html = new File("C:/IndiraMotorKasir/html_templates/receipt.html");
            html.delete();
            html.createNewFile();

            //erasing leftover data from HTML, idk I cannot recreate the problem
            //try running this, open the result file, while it is still open, run this again, ERROR
            //close the file, try again, there might be a leftover char(s) at the end of your receipt
            BufferedWriter htmlBufferedWriterClear = Files.newBufferedWriter(html.toPath(), StandardOpenOption.WRITE);
            htmlBufferedWriterClear.write("<!-- previous contents cleared -->");
            htmlBufferedWriterClear.flush();
            htmlBufferedWriterClear.close();

            //write data into HTML
            BufferedWriter htmlBufferedWriter = Files.newBufferedWriter(html.toPath(), StandardOpenOption.WRITE);
            String header = """
                            <!DOCTYPE html><html><head><style>* {font-family: Monospace}</style><title>Laporan Transaksi Indira Motor</title></head><body>""";

            String body = "<h1 style=\"text-align: center;\">Struk Pembayaran Indira Motor</h1><table style=\"width: 100%; margin-bottom: 20px;\">";

            HashMap<String, String> cart = new CartController().findWhere("id", data.getFirst().get("cart_id")).getFirst();
            HashMap<String, String> cashier = new CashierController().findWhere("id", cart.get("cashier_id")).getFirst();
            body += "<tr><td style=\"text-align: left;\">Nama Kasir : </td><td>" + cashier.get("username") + "</td></tr>";
            body += "<tr><td style=\"text-align: left;\">Tanggal Transaksi : </td><td>" + data.getFirst().get("date") + "</td></tr>";

            String query = """
                       SELECT COUNT(`cart_product`.`id`) AS product_types FROM `orders`
                       JOIN `carts` ON `orders`.`cart_id` = `carts`.`id`
                       JOIN `cashiers` ON `carts`.`cashier_id` = `cashiers`.id
                       JOIN `cart_product` ON `carts`.`id` = `cart_product`.`cart_id`
                       WHERE `cart_product`.`cart_id` = """;
            query += data.getFirst().get("cart_id");

            try {
                Statement stmt = db.connect().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                rs = stmt.executeQuery(query);
            } catch (SQLException er) {
                System.out.println(er);
            }

            try {
//                rs.first();
                body += "<tr><td style=\"text-align: left;\">Banyak Produk : </td><td>" + rs.getInt("product_types") + "</td></tr>";

                db.close();
            } catch (SQLException ex) {
                Logger.getLogger(PrintController.class.getName()).log(Level.SEVERE, null, ex);
            }
            body += "</table>";
            body += "<table style=\"width: 80%; margin: 0 auto;\"><tr><th style=\"text-align: left;\">Nama Produk</th><th style=\"text-align: left;\">Jumlah</th><th style=\"text-align: left;\">Harga</th><th style=\"text-align: left;\">Total</th></tr>";

            ArrayList<HashMap<String, String>> products = new CartController().getCartProducts(Integer.parseInt(data.getFirst().get("cart_id")));
            for (HashMap<String, String> product : products) {
                HashMap<String, String> product_data = new ProductController().findWhere("id", product.get("product_id")).getFirst();
                body += "<tr><td>" + product_data.get("name") + "</td><td>" + product.get("qty") + "</td><td>" + product_data.get("price") + "</td><td>" + Integer.parseInt(product.get("qty")) * Integer.parseInt(product_data.get("price")) + "</td></tr>";
            }

            body += "</table>";
            body += "<table style=\\\"margin-top: 20px\\\"><tr><td style=\"text-align: right;\">Jasa : </td><td>" + data.getFirst().get("fee") + "</td></tr>";
            body += "<tr><td style=\"text-align: right;\">Total : </td><td>" + data.getFirst().get("total") + "</td></tr></table>";

            String footer = "</body></html>";

            String content = header + body + footer;
            htmlBufferedWriter.write(content, 0, content.length());

            htmlBufferedWriter.flush();
            htmlBufferedWriter.close();
            //make pdf from HTML
            //get the directory to save the file
            final JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Choose directory to save the file");
            fc.setSelectedFile(new File("."));
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            File saveDir = null;

            int returnVal = fc.showOpenDialog(fc);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                saveDir = fc.getSelectedFile();
            }

            //write the pdf
            String date = LocalDate.now().toString();
            String time = LocalTime.now().toString();
            time = time.replaceAll(":", "-");
            System.out.println(time);
            String datetime = date + time;
            File pdf = new File(saveDir, "receipt" + datetime + ".pdf");
            //itextpdf require slf4j
            HtmlConverter.convertToPdf(html, pdf);
        } else if (data.get(0).get("type").equals("report")) {
            //delete the first data as it is only for checking type
            data.remove(0);
            
            //create HTML
            File dir = new File("C:/IndiraMotorKasir/html_templates");
            if (!dir.exists()) {
            	dir.mkdirs();
            }
            File html = new File("C:/IndiraMotorKasir/html_templates/report.html");
            html.delete();
            html.createNewFile();

            //erasing leftover data from HTML, idk I cannot recreate the problem
            //try running this, open the result file, while it is still open, run this again, ERROR
            //close the file, try again, there might be a leftover char(s) at the end of your receipt
            BufferedWriter htmlBufferedWriterClear = Files.newBufferedWriter(html.toPath(), StandardOpenOption.WRITE);
            htmlBufferedWriterClear.write("<!-- previous contents cleared -->");
            htmlBufferedWriterClear.flush();
            htmlBufferedWriterClear.close();

            //write data into HTML
            BufferedWriter htmlBufferedWriter = Files.newBufferedWriter(html.toPath(), StandardOpenOption.WRITE);
            String header = "<!DOCTYPE html><html lang=\"en\"><head><title>Laporan Transaksi Indira Motor</title><style>table, tr, td, th {border: 1px solid;border-collapse: collapse;padding: 0.2em;}</style></head><body>";

            String body = "<h1>Laporan Transaksi Indira Motor</h1><table><tr><th>No</th><th>Kasir</th><th>Tgl</th><th>Produk</th><th>Harga</th><th>Jumlah</th><th>Biaya Jasa</th><th>Total</th></tr>";
            for (HashMap<String, String> row : data) {
                body += "    <tr>\n";
                body += "      <td rowspan=\"" + row.get("product_types") + "\">" + row.get("id") + "</td>\n";
                body += "      <td rowspan=\"" + row.get("product_types") + "\">" + row.get("username") + "</td>\n";
                body += "      <td rowspan=\"" + row.get("product_types") + "\">" + row.get("date") + "</td>\n";

                ArrayList<HashMap<String, String>> cart_products = new CartController().getCartProducts(Integer.parseInt(row.get("cart_id")));
                int product_counter = 1;
                for (HashMap<String, String> cart_product : cart_products) {
                    if (product_counter == 1) {
                        body += "      <td>" + cart_product.get("name") + "</td>\n";
                        body += "      <td>" + cart_product.get("price") + "</td>\n";
                        body += "      <td>" + cart_product.get("qty") + "</td>\n";
                        body += "      <td rowspan=\"" + row.get("product_types") + "\">" + row.get("fee") + "</td>\n";
                        body += "      <td rowspan=\"" + row.get("product_types") + "\">" + row.get("total") + "</td>\n";
                        body += "    </tr>\n";

                        product_counter++;
                        continue;
                    }

                    body += "    <tr>\n";
                    body += "      <td>" + cart_product.get("name") + "</td>\n";
                    body += "      <td>" + cart_product.get("price") + "</td>\n";
                    body += "      <td>" + cart_product.get("qty") + "</td>\n";
                    body += "    </tr>";
                }
            }

            String footer = "</table></body></html>";

            String content = header + body + footer;
            htmlBufferedWriter.write(content, 0, content.length());

            htmlBufferedWriter.flush();
            htmlBufferedWriter.close();
            //make pdf from HTML
            //get the directory to save the file
            final JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Choose directory to save the file");
            fc.setSelectedFile(new File("."));
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            File saveDir = null;

            int returnVal = fc.showOpenDialog(fc);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                saveDir = fc.getSelectedFile();
            }

            //write the pdf
            String date = LocalDate.now().toString();
            String time = LocalTime.now().toString();
            time = time.replaceAll(":", "-");
            String datetime = date + time;
            File pdf = new File(saveDir, "report" + datetime + ".pdf");
            //itextpdf require slf4j
            HtmlConverter.convertToPdf(html, pdf);
        }
    }
}
