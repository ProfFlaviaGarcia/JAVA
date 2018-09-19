
package Controle;

import dao.ClienteJpaController;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.swing.JOptionPane;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Flavia
 */
@Entity
@Table(name = "cliente", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"CPF"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cliente.findAll", query = "SELECT c FROM Cliente c")
    , @NamedQuery(name = "Cliente.findById", query = "SELECT c FROM Cliente c WHERE c.id = :id")
    , @NamedQuery(name = "Cliente.findByCidade", query = "SELECT c FROM Cliente c WHERE c.cidade = :cidade")
    , @NamedQuery(name = "Cliente.findByCpf", query = "SELECT c FROM Cliente c WHERE c.cpf = :cpf")
    , @NamedQuery(name = "Cliente.findByEndereco", query = "SELECT c FROM Cliente c WHERE c.endereco = :endereco")
    , @NamedQuery(name = "Cliente.findByEstado", query = "SELECT c FROM Cliente c WHERE c.estado = :estado")
    , @NamedQuery(name = "Cliente.findByNome", query = "SELECT c FROM Cliente c WHERE c.nome = :nome")})
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    @Basic(optional = false)
    @Column(name = "Cidade", nullable = false, length = 15)
    private String cidade;
    @Basic(optional = false)
    @Column(name = "CPF", nullable = false, length = 14)
    private String cpf;
    @Basic(optional = false)
    @Column(name = "Endereco", nullable = false, length = 80)
    private String endereco;
    @Basic(optional = false)
    @Column(name = "Estado", nullable = false, length = 2)
    private String estado;
    @Basic(optional = false)
    @Column(name = "Nome", nullable = false, length = 40)
    private String nome;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCliente")
    private Collection<Compra> compraCollection;

    public Cliente() {
    }

    public Cliente(Long id) {
        this.id = id;
    }

    public Cliente(Long id, String cidade, String cpf, String endereco, String estado, String nome) {
        this.id = id;
        this.cidade = cidade;
        this.cpf = cpf;
        this.endereco = endereco;
        this.estado = estado;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @XmlTransient
    public Collection<Compra> getCompraCollection() {
        return compraCollection;
    }

    public void setCompraCollection(Collection<Compra> compraCollection) {
        this.compraCollection = compraCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cliente)) {
            return false;
        }
        Cliente other = (Cliente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Controle.Cliente[ id=" + id + " ]";
    }
    public boolean armazenado() {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CadClientePU");
            ClienteJpaController clienteJpaController = new ClienteJpaController(emf);
            clienteJpaController.create(this);
            JOptionPane.showMessageDialog(null, "Cliente incluído");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getCause());
            return false;
        }
    }
 
    public boolean atualizado() {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CadClientePU");
            ClienteJpaController clienteJpaController = new ClienteJpaController(emf);
            clienteJpaController.edit(this);
            JOptionPane.showMessageDialog(null, "Cliente atualizado");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getCause());
            return false;
        }
    }
 
    public boolean desarmazenado() {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CadClientePU");
            ClienteJpaController clienteJpaController = new ClienteJpaController(emf);
            clienteJpaController.destroy(this.getId());
            JOptionPane.showMessageDialog(null, "Cliente excluído");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getCause());
            return false;
        }
    }
 
    public boolean encontradoId(Long id) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CadClientePU");
            ClienteJpaController clienteJpaController = new ClienteJpaController(emf);
            Cliente clienteAux = clienteJpaController.findCliente(id);
            if (clienteAux != null) {
                this.setId(clienteAux.getId());
                this.setNome(clienteAux.getNome());
                this.setCpf(clienteAux.getCpf());
                this.setEndereco(clienteAux.getEndereco());
                this.setCidade(clienteAux.getCidade());
                this.setEstado(clienteAux.getEstado());
                this.setCompraCollection(clienteAux.getCompraCollection());
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Cliente não encontrado");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getCause());
            return false;
        }
    }
 
    public Cliente encontradoCPF(String cpf) {
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("CadClientePU");
            ClienteJpaController clienteJpaController = new ClienteJpaController(emf);
            Cliente clienteAux = (Cliente) clienteJpaController.findCPF(cpf);
            if (clienteAux != null) {
                this.setId(clienteAux.getId());
                this.setNome(clienteAux.getNome());
                this.setCpf(clienteAux.getCpf());
                this.setEndereco(clienteAux.getEndereco());
                this.setCidade(clienteAux.getCidade());
                this.setEstado(clienteAux.getEstado());
                this.setCompraCollection(clienteAux.getCompraCollection());
                return clienteAux;
            } else {
                JOptionPane.showMessageDialog(null, "Cliente não encontrado");
                return null;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            return null;
        }
    }
}
