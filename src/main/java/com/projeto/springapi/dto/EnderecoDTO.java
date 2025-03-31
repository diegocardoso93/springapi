import lombok.Data;

@Data
public class EnderecoDTO {
    private String endTipoLogradouro;
    private String endLogradouro;
    private Integer endNumero;
    private String endBairro;
    private Integer cidadeId;
}
