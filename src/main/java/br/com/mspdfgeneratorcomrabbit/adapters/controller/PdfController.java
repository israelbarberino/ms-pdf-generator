package br.com.mspdfgeneratorcomrabbit.adapters.controller;

import br.com.mspdfgeneratorcomrabbit.ports.in.PdfGenService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@AllArgsConstructor
@RestController
public class PdfController {

    private PdfGenService pdfGenService;

    @GetMapping("/teste/hello")
    public String hello() {
        return "Hello World! - API PDF Generator";
    }

    @PostMapping("/api/gerar-pdf")
    public ResponseEntity<byte[]> gerarPDF(@RequestBody Map<String, String> request) {
        var conteudo = request.get("conteudo");
        return pdfGenService.getResponseEntity(conteudo);
    }
}
