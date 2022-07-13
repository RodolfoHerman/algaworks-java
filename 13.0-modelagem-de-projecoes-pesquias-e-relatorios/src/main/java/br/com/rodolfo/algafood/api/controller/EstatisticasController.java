package br.com.rodolfo.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.rodolfo.algafood.api.assembler.VendaDiariaFilterInputDisassembler;
import br.com.rodolfo.algafood.api.assembler.VendaDiariaModelAssembler;
import br.com.rodolfo.algafood.api.model.VendaDiariaModel;
import br.com.rodolfo.algafood.api.model.input.VendaDiariaFilterInput;
import br.com.rodolfo.algafood.domain.filter.VendaDiariaFilter;
import br.com.rodolfo.algafood.domain.service.VendaQueryService;
import br.com.rodolfo.algafood.domain.service.VendaReportService;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticasController {

    @Autowired
    private VendaQueryService vendaQueryService;

    @Autowired
    private VendaDiariaModelAssembler vendaDiariaModelAssembler;

    @Autowired
    private VendaDiariaFilterInputDisassembler vendaDiariaFilterInputDisassembler;

    @Autowired
    private VendaReportService vendaReportService;

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VendaDiariaModel> consultarVendasDiarias(
        VendaDiariaFilterInput vendaDiariaFilterInput,
        @RequestParam(required = false, defaultValue = "+00:00") String timeOffset
    ) {
        VendaDiariaFilter filtro = vendaDiariaFilterInputDisassembler.toDomainObject(vendaDiariaFilterInput);

        return vendaDiariaModelAssembler.toCollection(vendaQueryService.consultarVendasDiarias(filtro, timeOffset));
    }

    @GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultarVendasDiariasPdf(
        VendaDiariaFilterInput vendaDiariaFilterInput,
        @RequestParam(required = false, defaultValue = "+00:00") String timeOffset
    ) {
        VendaDiariaFilter filtro = vendaDiariaFilterInputDisassembler.toDomainObject(vendaDiariaFilterInput);

        byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filtro, timeOffset);

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .headers(headers)
            .body(bytesPdf);
    }
}
