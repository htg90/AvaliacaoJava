package br.com.htg.backend.data;

/**
 * Classe que representa um determinado produto.
 *
 * @author Helbert Gomes
 */
public class Produto {

    /**
     * ID do produto.
     */
    private int id;

    /**
     * Nome do produto.
     */
    private String name;

    /**
     * Descrição do produto.
     */
    private String description;

    /**
     * Imagem do produto.
     */
    private Image image;

    /**
     * ID do produto pai.
     */
    private Integer idParent;

    /**
     * Retorna o id do produto.
     *
     * @return O id do produto.
     */
    public int getId() {
        return id;
    }

    /**
     * Insere o id do produto.
     *
     * @param id O id do produto.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retorna o nome do produto.
     *
     * @return O nome do produto.
     */
    public String getName() {
        return name;
    }

    /**
     * Insere o nome do produto.
     *
     * @param name O nome do produto.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retorna a descrição do produto.
     *
     * @return A descrição do produto.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Insere a descrição do produto.
     *
     * @param description A descrição do produto.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retorna a imagem do produto.
     *
     * @return A imagem do produto.
     */
    public Image getImage() {
        return image;
    }

    /**
     * Insere a imagem do produto.
     *
     * @param image A imagem do produto.
     */
    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * Retorna o id do pai do produto.
     *
     * @return O id do pai do produto.
     */
    public Integer getIdParent() {
        return idParent;
    }

    /**
     * Insere o id do pai do produto.
     *
     * @param idParent O id do pai do produto.
     */
    public void setIdParent(int idParent) {
        this.idParent = idParent;
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
        hash = 89 * hash + this.id;
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
        final Produto other = (Produto) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Produto{" + "id=" + id + ", name=" + name + ", description=" + description + ", image=" + image + ", idParent=" + idParent + '}';
    }
}
