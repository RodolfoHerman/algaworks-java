package br.com.rodolfo.algafood.domain.models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FotoProduto {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "produto_id")
    private Long id;

    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    private Produto produto;

    public Long getRestauranteId() {
        if(Objects.nonNull(produto)) {
            return getProduto().getRestaurante().getId();
        }

        return null;
    }
}
