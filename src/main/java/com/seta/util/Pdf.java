/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seta.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.seta.activity.Action;
import com.seta.entity.B3;
import com.seta.entity.Politica;
import com.seta.entity.PrgFormativo;
import com.seta.entity.PrgFormativoDt;
import com.seta.entity.Voucher;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 *
 * @author agodino
 */
public class Pdf {

    private static void addTableHeaderPrg(PdfPTable table) {
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
        Stream.of(" Nome", " Cognome", " Codice Fiscale", " Data Inizo", " Data Fine", " Profiling", " Rimborso")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPaddingLeft(0);
                    header.setPhrase(new Phrase(columnTitle, font));
                    table.addCell(header);
                });
    }

    public static void writePrgPDF(String path, ArrayList<PrgFormativo> prg, String cod_pol) throws FileNotFoundException, DocumentException, ParseException {

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();

        HashMap<String, double[]> prezziario = Action.getPrezziario("2");

//        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.BLACK);
        Font f = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.BLACK);
//        Chunk chunk = new Chunk("Lista completa delle politiche", font);
//        document.add(chunk);
//        Font font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.WHITE);

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        float[] columnWidths = {5, 5, 8, 4, 4, 5, 4};
        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100);
        addTableHeaderPrg(table);
        double tot = 0;
        double rimborso;//va fatta query per prendere quello giusto
        for (PrgFormativo p : prg) {
            rimborso = prezziario.get(p.getCodazioneformcal())[p.getProfiling() - 1];
            table.addCell(new PdfPCell(new Phrase(p.getNome(), f)));
            table.addCell(new PdfPCell(new Phrase(p.getCognome(), f)));
            table.addCell(new PdfPCell(new Phrase(p.getCf(), f)));
            table.addCell(new PdfPCell(new Phrase(sdf2.format(sdf1.parse(p.getDataavvio())), f)));
            table.addCell(new PdfPCell(new Phrase(sdf2.format(sdf1.parse(p.getDatafine())), f)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(p.getProfiling()), f)));
            table.addCell(new PdfPCell(new Phrase("€ " + String.format("%1$,.2f", rimborso), f)));
            tot += rimborso;
        }
//        chunk = new Chunk(". Totale richiesto: € " + String.format("%1$,.2f", (double) tot), font);
//        document.add(chunk);
//        document.add(new Paragraph("-", font2));

        document.add(table);

        setFooter(document, tot);

        document.close();
    }

    private static void addTableHeaderPrgDt(PdfPTable table) {
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
        Stream.of("Nome", "Cognome", "Codice Fiscale", "Data Inizo", "Data Fine", "Profiling", "Rimborso")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPaddingLeft(1);
                    header.setPhrase(new Phrase(columnTitle, font));
                    table.addCell(header);
                });
    }

    public static void writePrgPDF_Dt(String path, ArrayList<PrgFormativoDt> prg, String cod_pol) throws FileNotFoundException, DocumentException, ParseException {

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();

//        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.BLACK);
        Font f = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.BLACK);
//        Chunk chunk = new Chunk("Lista completa delle politiche", font);
//        document.add(chunk);
//        Font font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.WHITE);

        HashMap<String, double[]> prezziario = Action.getPrezziario("1");

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        float[] columnWidths = {5, 5, 8, 4, 4, 5, 4};
        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100);
        addTableHeaderPrgDt(table);
        double tot = 0;
        double rimborso;//va fatta query per prendere quello giusto
        for (PrgFormativoDt p : prg) {
            rimborso = prezziario.get(p.getCodazioneformcal())[p.getProfiling() - 1];
            table.addCell(new PdfPCell(new Phrase(p.getNome(), f)));
            table.addCell(new PdfPCell(new Phrase(p.getCognome(), f)));
            table.addCell(new PdfPCell(new Phrase(p.getCf(), f)));
            table.addCell(new PdfPCell(new Phrase(sdf2.format(sdf1.parse(p.getDataavvio())), f)));
            table.addCell(new PdfPCell(new Phrase(sdf2.format(sdf1.parse(p.getDatafine())), f)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(p.getProfiling()), f)));
            table.addCell(new PdfPCell(new Phrase("€ " + String.format("%1$,.2f", rimborso), f)));
            tot += rimborso;
        }
//        chunk = new Chunk(". Totale richiesto: € " + String.format("%1$,.2f", (double) (tot)), font);
//        document.add(chunk);
//        document.add(new Paragraph("-", font2));

        document.add(table);

        setFooter(document, tot);

        document.close();
    }

    private static void addTableHeader(PdfPTable table, String politica) {
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
        if (politica.equals("D2") || politica.equals("D5") || politica.equals("B03")) {
            Stream.of(" Nome", " Cognome", " Codice Fiscale", " Data \n Assunz.", " Data \n Scadenza", " Profiling", " Rimborso")
                    .forEach(columnTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setBorderWidth(1);
                        header.setPaddingLeft(0);
                        header.setPhrase(new Phrase(columnTitle, font));
                        table.addCell(header);
                    });
        } else {
            Stream.of(" Nome", " Cognome", " Codice Fiscale", " Ore", " Data", " Rimborso")
                    .forEach(columnTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                        header.setBorderWidth(1);
                        header.setPaddingLeft(0);
                        header.setPhrase(new Phrase(columnTitle, font));
                        table.addCell(header);
                    });
        }
    }

    public static void writePDF(String path, ArrayList<Politica> pol, String cod_pol) throws FileNotFoundException, DocumentException, ParseException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();

        HashMap<String, double[]> prezziario = Action.getPrezziario("2");

//        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.BLACK);
        Font f = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, BaseColor.BLACK);
//        Chunk chunk = new Chunk("Lista completa delle politiche", font);
//        document.add(chunk);
//        Font font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.WHITE);

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        float[] columnWidths = {5, 5, 8, 2, 4, 4};
        PdfPTable table;
        if (cod_pol.equals("B03")) {
            float[] columnWidths2 = {5, 5, 8, 4, 4, 4, 4};
            table = new PdfPTable(columnWidths2);
        } else {
            table = new PdfPTable(columnWidths);
        }
        //PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100);
        addTableHeader(table, cod_pol);

        double tot = 0;
        double rimborso = 0;
        int mesi = 0;
        int mesi2 = 0;
        for (Politica p : pol) {
            if (!cod_pol.equals("B03")) {
                rimborso = prezziario.get(cod_pol)[1];
            }
            table.addCell(new PdfPCell(new Phrase(p.getNome(), f)));
            table.addCell(new PdfPCell(new Phrase(p.getCognome(), f)));
            table.addCell(new PdfPCell(new Phrase(p.getCf(), f)));
            if (!cod_pol.equals("B03")) {
                table.addCell(new PdfPCell(new Phrase(String.valueOf(p.getDurataeffettiva()), f)));
            }
            table.addCell(new PdfPCell(new Phrase(sdf2.format(sdf1.parse(p.getDataavvio())), f)));
            if (cod_pol.equals("B03")) {
                String datafine = "ind.";
                if (p.getDatafine() != null) {
                    mesi = (int) Math.round((double) (Utility.getDayDfference(p.getDataavvio(), p.getDatafine(), "yyyy-MM-dd")) / 30);
                    datafine = sdf2.format(sdf1.parse(p.getDatafine()));
                }else{
                    mesi=12;
                }
                if (p.getDatafine() == null) {
                    rimborso = prezziario.get("M3 indeterminato")[p.getProfiling() - 1];
                } else if (mesi >= 12) {
                    rimborso = prezziario.get("M3 >=12")[p.getProfiling() - 1];
                } else if (mesi > 5 && mesi < 12) {
                    rimborso = prezziario.get("M3 <12")[p.getProfiling() - 1];
                }
                if(p.getNew_datafine()!=null){
                    datafine = sdf2.format(sdf1.parse(p.getNew_datafine()));
                    mesi2 = (int) Math.round((double) (Utility.getDayDfference(p.getDataavvio(), p.getNew_datafine(), "yyyy-MM-dd")) / 30);
                    rimborso=((rimborso/(double)mesi))*(double)mesi2;
                }
                
                table.addCell(new PdfPCell(new Phrase(datafine, f)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(p.getProfiling()), f)));
                table.addCell(new PdfPCell(new Phrase("€ " + String.format("%1$,.2f", rimborso), f)));
                tot += rimborso;
            } else {
                table.addCell(new PdfPCell(new Phrase("€ " + String.format("%1$,.2f", (double) (p.getDurataeffettiva() * rimborso)), f)));
                tot += p.getDurataeffettiva() * rimborso;
            }
        }
//        chunk = new Chunk(". Totale richiesto: € " + String.format("%1$,.2f", (double) (tot)), font);
//        document.add(chunk);
//        document.add(new Paragraph("-", font2));

        document.add(table);

        setFooter(document, tot);

        document.close();
    }

    public static void writePDFDt(String path, ArrayList<Politica> pol, String cod_pol) throws FileNotFoundException, DocumentException, ParseException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();

        HashMap<String, double[]> prezziario = Action.getPrezziario("1");

//        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.BLACK);
        Font f = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, BaseColor.BLACK);
//        Chunk chunk = new Chunk("Lista completa delle politiche", font);
//        document.add(chunk);
//        Font font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.WHITE);

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        float[] columnWidths = {5, 5, 8, 2, 4, 4};
        PdfPTable table;
        if (cod_pol.equals("D2") || cod_pol.equals("D5")) {
            float[] columnWidths2 = {6, 5, 8, 4, 4, 4, 4};
            table = new PdfPTable(columnWidths2);
        } else {
            table = new PdfPTable(columnWidths);
        }
        //PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(105);
        addTableHeader(table, cod_pol);

        double tot = 0;
        double rimborso = 0;
//        int mesi = 0;
        for (Politica p : pol) {
            table.addCell(new PdfPCell(new Phrase(p.getNome(), f)));
            table.addCell(new PdfPCell(new Phrase(p.getCognome(), f)));
            table.addCell(new PdfPCell(new Phrase(p.getCf(), f)));
            if (cod_pol.equals("B1") || cod_pol.equals("C1")) {
                rimborso = prezziario.get(cod_pol)[0];
                table.addCell(new PdfPCell(new Phrase(String.valueOf(p.getDurataeffettiva()), f)));
            }
            if (p.getDataavvio() != null) {
                table.addCell(new PdfPCell(new Phrase(sdf2.format(sdf1.parse(p.getDataavvio())), f)));
            } else {
                table.addCell(new PdfPCell(new Phrase("ind.", f)));
            }
            if (cod_pol.equals("D2") || cod_pol.equals("D5")) {
                String datafine = "ind.";
                if (p.getDatafine() != null) {
                    datafine = sdf2.format(sdf1.parse(p.getDatafine()));
                }
                table.addCell(new PdfPCell(new Phrase(datafine, f)));
                table.addCell(new PdfPCell(new Phrase(String.valueOf(p.getProfiling()), f)));
                rimborso = prezziario.get(p.getCodazioneformcal())[p.getProfiling() - 1];
                table.addCell(new PdfPCell(new Phrase("€ " + String.format("%1$,.2f", rimborso), f)));
                tot += rimborso;
            } else {
                table.addCell(new PdfPCell(new Phrase("€ " + String.format("%1$,.2f", (double) (p.getDurataeffettiva() * rimborso)), f)));
                tot += p.getDurataeffettiva() * rimborso;
            }
        }
//        chunk = new Chunk(". Totale richiesto: € " + String.format("%1$,.2f", (double) (tot)), font);
//        document.add(chunk);
//        document.add(new Paragraph("-", font2));

        document.add(table);

        setFooter(document, tot);

        document.close();
    }

    private static void addTableHeaderB3Dt(PdfPTable table) {
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLACK);
        Stream.of("Nome", "Cognome", "Codice Fiscale", "Profiling", "Data Erogaz.", "Rimborso")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPaddingLeft(1);
                    header.setPhrase(new Phrase(columnTitle, font));
                    table.addCell(header);
                });
    }

    public static void writePDFB3Dt(String path, ArrayList<B3> pol, String cod_pol) throws FileNotFoundException, DocumentException, ParseException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();

        HashMap<String, double[]> prezziario = Action.getPrezziario("1");

        Font f = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, BaseColor.BLACK);

        Chunk chunk = new Chunk();
//        Chunk chunk = new Chunk("Lista completa delle politiche", font);
//        document.add(chunk);

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        float[] columnWidths = {5, 5, 8, 3, 4, 4};
        PdfPTable table;

        table = new PdfPTable(columnWidths);

        //PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100);
        addTableHeaderB3Dt(table);

        double tot = 0;
        double rimborso = 0;

        for (B3 p : pol) {
            table.addCell(new PdfPCell(new Phrase(p.getNome(), f)));
            table.addCell(new PdfPCell(new Phrase(p.getCognome(), f)));
            table.addCell(new PdfPCell(new Phrase(p.getCf(), f)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(p.getProfiling()), f)));

            rimborso = prezziario.get(cod_pol)[p.getProfiling() - 1];

            if (p.getDataavvio() != null) {
                table.addCell(new PdfPCell(new Phrase(sdf2.format(sdf1.parse(p.getDataavvio())), f)));
            } else {
                table.addCell(new PdfPCell(new Phrase("ind.", f)));
            }

            table.addCell(new PdfPCell(new Phrase("€ " + String.format("%1$,.2f", (double) (rimborso)), f)));
            tot += rimborso;

        }
        document.add(table);

        setFooter(document, tot);

        document.close();
    }

    private static void addTableHeaderVoucherDt(PdfPTable table) {
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, Font.BOLD, BaseColor.BLACK);
        Stream.of("Nome", "Cognome", "Codice Fiscale", "Profiling", "Data Erogaz.", "Ore", "Voucher", "Rimborso")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1);
                    header.setPaddingLeft(1);
                    header.setPhrase(new Phrase(columnTitle, font));
                    table.addCell(header);
                });
    }

    public static void writePDFVoucherDt(String path, ArrayList<Voucher> pol, String cod_pol) throws FileNotFoundException, DocumentException, ParseException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(path));
        document.open();

        HashMap<String, double[]> prezziario = Action.getPrezziario("1");

        Font f = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.BLACK);

        Chunk chunk = new Chunk();
//        Chunk chunk = new Chunk("Lista completa delle politiche", font);
//        document.add(chunk);

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

        float[] columnWidths = {5, 5, 8, 3, 4, 3, 4, 4};
        PdfPTable table;

        table = new PdfPTable(columnWidths);

        //PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(105);
        addTableHeaderVoucherDt(table);

        double tot = 0;
        double rimborso = 0;

        for (Voucher p : pol) {
            table.addCell(new PdfPCell(new Phrase(p.getNome(), f)));
            table.addCell(new PdfPCell(new Phrase(p.getCognome(), f)));
            table.addCell(new PdfPCell(new Phrase(p.getCf(), f)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(p.getProfiling()), f)));
            
            rimborso = prezziario.get(cod_pol)[p.getProfiling() - 1];
            if (p.getDataavvio() != null) {
                table.addCell(new PdfPCell(new Phrase(sdf2.format(sdf1.parse(p.getDataavvio())), f)));
            } else {
                table.addCell(new PdfPCell(new Phrase("ind.", f)));
            }
            table.addCell(new PdfPCell(new Phrase(String.valueOf(p.getOre()), f)));
            table.addCell(new PdfPCell(new Phrase("€ " + String.format("%1$,.2f", (double) (p.getVoucher())), f)));
            table.addCell(new PdfPCell(new Phrase("€ " + String.format("%1$,.2f", (double) (rimborso + p.getVoucher())), f)));
            tot += rimborso + p.getVoucher();
        }
        document.add(table);

        setFooter(document, tot);

        document.close();
    }

    private static void setFooter(Document document, double tot) throws DocumentException {
        Chunk chunk = new Chunk();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.BLACK);
        Font f2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, BaseColor.BLACK);
        Font f3 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8, Font.BOLD, BaseColor.BLACK);
        Font font2 = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, BaseColor.WHITE);

        float[] columnWidths3 = {20, 3};
        PdfPTable table3 = new PdfPTable(columnWidths3);
        table3.setWidthPercentage(100);
        table3.setPaddingTop(0);
        table3.getDefaultCell().setBorder(0);

        float[] columnWidths2 = {10, 3};
        PdfPTable table2 = new PdfPTable(columnWidths2);
        table2.setWidthPercentage(10);
        table2.setPaddingTop(0);
        table2.getDefaultCell().setBorder(0);

        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
//        table.getDefaultCell().setBorder(0);

        PdfPCell tmp = new PdfPCell();
        tmp.setBorderColor(BaseColor.WHITE);
        chunk = new Chunk("Lista completa delle politiche. Totale richiesto: € " + String.format("%1$,.2f", (double) (tot)), font);
        tmp.setPhrase(new Phrase(chunk));
        table3.addCell(tmp);

        tmp.setPhrase(new Phrase("Basso", f2));
        table2.addCell(tmp);
        tmp.setPhrase(new Phrase("1", f2));
        table2.addCell(tmp);
        tmp.setPhrase(new Phrase("Medio", f2));
        table2.addCell(tmp);
        tmp.setPhrase(new Phrase("2", f2));
        table2.addCell(tmp);
        tmp.setPhrase(new Phrase("Alto", f2));
        table2.addCell(tmp);
        tmp.setPhrase(new Phrase("3", f2));
        table2.addCell(tmp);
        tmp.setPhrase(new Phrase("Molto Alto", f2));
        table2.addCell(tmp);
        tmp.setPhrase(new Phrase("4", f2));
        table2.addCell(tmp);

        tmp.setPhrase(new Phrase("Profiling:", f3));
        table.addCell(tmp);
        table.addCell(table2);
        table3.addCell(table);
        document.add(new Paragraph("-", font2));

        document.add(table3);
    }
}
