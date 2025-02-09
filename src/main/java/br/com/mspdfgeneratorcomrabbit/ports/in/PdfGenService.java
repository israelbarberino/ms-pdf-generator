package br.com.mspdfgeneratorcomrabbit.ports.in;

import org.springframework.http.ResponseEntity;

public interface PdfGenService {

    ResponseEntity<byte[]> getResponseEntity(String conteudo);
}
