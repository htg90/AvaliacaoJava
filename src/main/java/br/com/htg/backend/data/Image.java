package br.com.htg.backend.data;

/**
 * Classe que representa uma determinada imagem.
 *
 * @author Helbert Gomes
 */
public class Image {

    /**
     * ID da imagem.
     */
    private int id;

    /**
     * Caminho da imagem a ser salvo no banco de dados.
     */
    private String caminho;

    /**
     * Tipo da imagem.
     */
    private String type;

    /**
     * ID referente ao produto que está ligado a imagem
     */
    private int produtoId;

    /**
     * Retorna o id da imagem.
     *
     * @return O id da imagem.
     */
    public int getId() {
        return id;
    }

    /**
     * Insere o id da imagem.
     *
     * @param id O id da imagem.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o caminho da imagem.
     *
     * @return O caminho da imagem.
     */
    public String getCaminho() {
        return caminho;
    }

    /**
     * Insere o caminho da imagem.
     *
     * @param caminho
     */
    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    /**
     * Retorna o tipo da imagem.
     *
     * @return O tipo da imagem.
     */
    public String getType() {
        return type;
    }

    /**
     * Insere o tipo da imagem.
     *
     * @param type O tipo da imagem.
     */
    public void setType(String type) {
        if (type != null) {
            this.type = type.substring(type.indexOf("."));
        } else {
            this.type = null;
        }
    }

    /**
     * Retorna o id do produto que a imagem está ligada.
     *
     * @return O id do produto que a imagem está ligada.
     */
    public int getProdutoId() {
        return produtoId;
    }

    /**
     * Insere o id do produto que a imagem está ligada.
     *
     * @param produtoId O id do produto que a imagem está ligada.
     */
    public void setProdutoId(int produtoId) {
        this.produtoId = produtoId;
    }

    /**
     * Métodp sobrescrito da classe Object. É aqui onde é feito o calculo do
     * hash, em relação ao id da imagem.
     *
     * @return Número de hash.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
        return hash;
    }

    /**
     * Método sobrescrito da classe Object. Método que diferencia dois objetos.
     *
     * @param obj Objeto a ser comparado.
     * @return true caso seja igual, false caso contrário.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Image other = (Image) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return caminho;
    }
}
