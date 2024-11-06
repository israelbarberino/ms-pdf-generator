package br.com.mspdfgeneratorcomrabbit.controller;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

@RestController
public class PdfController {

    @GetMapping("/teste/hello")
    public String hello() {
        return "Hello World! - API PDF Generator";
    }

    @PostMapping("/api/gerar-pdf")
    public ResponseEntity<byte[]> gerarPDF(@RequestBody Map<String, String> request) {
        var conteudo = request.get("conteudo");

        try (var baos = new ByteArrayOutputStream()) {
            var writer = new PdfWriter(baos);
            var pdfDoc = new PdfDocument(writer);
            var document = new Document(pdfDoc);

            var titulo = new Paragraph("Resultados do Sorteio")
                    .setBold()
                    .setFontSize(16)
                    .setFontColor(ColorConstants.BLUE)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20);
            document.add(titulo);

            var paragrafoConteudo = new Paragraph(conteudo)
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.JUSTIFIED)
                    .setMarginBottom(10);
            document.add(paragrafoConteudo);

            document.close();

            var headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=relatorio_resultado.pdf");
            return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
