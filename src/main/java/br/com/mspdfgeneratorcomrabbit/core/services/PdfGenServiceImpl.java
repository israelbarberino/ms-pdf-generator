package br.com.mspdfgeneratorcomrabbit.core.services;

import br.com.mspdfgeneratorcomrabbit.ports.in.PdfGenService;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class PdfGenServiceImpl implements PdfGenService {

    @Override
    public ResponseEntity<byte[]> getResponseEntity(String content) {
        try (var baos = new ByteArrayOutputStream()) {
            dodContentFormatter(content, baos);
            final var headers = getHttpHeadersConfig();
            return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private static void dodContentFormatter(String content, ByteArrayOutputStream baos) {
        var writer = new PdfWriter(baos);
        var pdfDoc = new PdfDocument(writer);
        var document = new Document(pdfDoc);

        documentFormatter(document);
        contentFormatter(content, document);
        document.close();
    }

    private static HttpHeaders getHttpHeadersConfig() {
        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=sorter_result.pdf");
        return headers;
    }

    private static void contentFormatter(String content, Document document) {
        var contentParagraph = new Paragraph(content)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.JUSTIFIED)
                .setMarginBottom(10);
        document.add(contentParagraph);
    }

    private static void documentFormatter(Document document) {
        var tittle = new Paragraph("Resultados do Sorteio")
                .setBold()
                .setFontSize(16)
                .setFontColor(ColorConstants.BLUE)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20);
        document.add(tittle);
    }
}
