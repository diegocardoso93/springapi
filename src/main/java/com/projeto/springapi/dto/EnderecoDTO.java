import lombok.Data;

@Data
public class EnderecoDTO {
    private String tipoLogradouro;
    private String logradouro;
    private Integer numero;
    private String bairro;
    private Integer cidadeId;
}
