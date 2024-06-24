package App.Service;

import App.DTO.ClienteDTO;
import App.DTO.EnderecoDTO;
import App.Entity.ClienteEntity;
import App.Entity.ContatoEntity;
import App.Entity.EnderecoEntity;
import App.Exceptions.EntityNotFoundException;
import App.Exceptions.IllegalActionException;
import App.Exceptions.NullargumentsException;
import App.Repository.ClienteRepository;
import App.Repository.ContatoRepository;
import App.Repository.EnderecoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoRepository enderecoRepository;
    private final ContatoRepository contatoRepository;

    Locale localBrasil = new Locale("pt", "BR");

    public ClienteService(ClienteRepository clienteRepository, EnderecoRepository enderecoRepository, ContatoRepository contatoRepository) {
        this.clienteRepository = clienteRepository;
        this.enderecoRepository = enderecoRepository;
        this.contatoRepository = contatoRepository;
    }

    public ResponseEntity<List<ClienteEntity>> ListarClientes()
    {
        try
        {
            return new ResponseEntity<>(clienteRepository.findAll(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return null;
    }

    public ResponseEntity<ClienteDTO> BuscarClienteporid(Long id)
    {
        try
        {
            if(id != null)
            {
                ClienteEntity entity = clienteRepository.findById(id).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                String enderecoResponse = (entity.getEndereco().getLogradouro() +" "+
                        entity.getEndereco().getNumero() +" "+
                        entity.getEndereco().getReferencia()  +" "+
                        entity.getEndereco().getBairro() +" "+
                        entity.getEndereco().getCep()  +" "+
                        entity.getEndereco().getCidade()  +" "+
                        entity.getEndereco().getEstado()+".");
                String telefoneResponse = ("("+entity.getContato().getPrefixo()+") "+entity.getContato().getTelefone());
                ClienteDTO response = new ClienteDTO(entity.getNome(),entity.getDataNascimento(),entity.getCpf(),enderecoResponse,telefoneResponse,entity.getContato().getEmail());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else
            {throw new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return null;
    }

    public ResponseEntity<ClienteDTO> NovoCliente(String nome,
                                                  String sobrenome,
                                                  Long cpf,
                                                  LocalDate dataNascimento,
                                                  String logradouro,
                                                  String numero,
                                                  String bairro,
                                                  String referencia,
                                                  Long cep,
                                                  String cidade,
                                                  String estado,
                                                  Long prefixo,
                                                  Long telefone,
                                                  String email)
    {
        try
        {
            if(nome != null &&
               sobrenome != null &&
               cpf != null &&
               dataNascimento != null &&
               logradouro != null &&
               numero != null &&
               bairro != null &&
               referencia != null &&
               cep != null &&
               cidade != null &&
               estado != null &&
               prefixo != null &&
               telefone != null &&
               email != null)
            {
                if(prefixo <= 0) {throw new IllegalActionException("O valor do campo não pode ser negativo");}
                if(telefone <= 0) {throw new IllegalActionException("O valor do campo não pode ser negativo");}
                if(cep <= 0) {throw new IllegalActionException("O valor do campo não pode ser negativo");}
                if(cpf <= 0) {throw new IllegalActionException("O valor do campo não pode ser negativo");}
                EnderecoDTO enderecoDTO = new EnderecoDTO(logradouro,numero,bairro,referencia,cep,cidade,estado);
                EnderecoEntity endereco = new EnderecoEntity(enderecoDTO);
                endereco.setTimeStamp(LocalDateTime.now());

                ContatoEntity contato = new ContatoEntity();
                contato.setPrefixo(prefixo);
                contato.setTelefone(telefone);
                contato.setEmail(email);
                contato.setTimeStamp(LocalDateTime.now());

                ClienteEntity entity = new ClienteEntity();
                entity.setCpf(cpf);
                entity.setDataNascimento(dataNascimento);
                entity.setNome(nome);
                entity.setSobrenome(sobrenome);
                entity.setTimeStamp(LocalDateTime.now());
                contatoRepository.save(contato);
                enderecoRepository.save(endereco);
                entity.setEndereco(endereco);
                entity.setContato(contato);
                clienteRepository.save(entity);
                String enderecoResponse = (entity.getEndereco().getLogradouro() +" "+
                                           entity.getEndereco().getNumero() +" "+
                                           entity.getEndereco().getReferencia()  +" "+
                                           entity.getEndereco().getBairro() +" "+
                                           entity.getEndereco().getCep()  +" "+
                                           entity.getEndereco().getCidade()  +" "+
                                           entity.getEndereco().getEstado()+".");
                String telefoneResponse = ("("+entity.getContato().getPrefixo()+") "+entity.getContato().getTelefone());
                ClienteDTO response = new ClienteDTO(entity.getNome(),entity.getDataNascimento(),entity.getCpf(),enderecoResponse,telefoneResponse,entity.getContato().getEmail());
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            }
            else
            { throw new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return null;
    }


    public ResponseEntity<ClienteDTO> EdiarCliente(Long id,
                                                   String nome,
                                                   String sobrenome,
                                                   Long cpf,
                                                   LocalDate dataNascimento,
                                                   String logradouro,
                                                   String numero,
                                                   String bairro,
                                                   String referencia,
                                                   Long cep,
                                                   String cidade,
                                                   String estado,
                                                   Long prefixo,
                                                   Long telefone,
                                                   String email)
    {
        try
        {
            if(nome != null &&
                    sobrenome != null &&
                    cpf != null &&
                    dataNascimento != null &&
                    logradouro != null &&
                    numero != null &&
                    bairro != null &&
                    referencia != null &&
                    cep != null &&
                    cidade != null &&
                    estado != null &&
                    prefixo != null &&
                    telefone != null &&
                    email != null &&
                    id != null)
            {
                if(prefixo <= 0) {throw new IllegalActionException("O valor do campo não pode ser negativo");}
                if(telefone <= 0) {throw new IllegalActionException("O valor do campo não pode ser negativo");}
                if(cep <= 0) {throw new IllegalActionException("O valor do campo não pode ser negativo");}
                if(cpf <= 0) {throw new IllegalActionException("O valor do campo não pode ser negativo");}
                if(id <= 0) {throw new IllegalActionException("O valor do campo não pode ser negativo");}


                ClienteEntity entity = clienteRepository.findById(id).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                EnderecoEntity endereco = enderecoRepository.findById(entity.getEndereco().getId()).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                ContatoEntity contato = contatoRepository.findById(entity.getContato().getId()).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                endereco.setLogradouro(logradouro);
                endereco.setNumero(numero);
                endereco.setBairro(bairro);
                endereco.setReferencia(referencia);
                endereco.setCep(cep);
                endereco.setCidade(cidade);
                endereco.setEstado(estado);
                endereco.setTimeStamp(LocalDateTime.now());
                enderecoRepository.save(endereco);
                contato.setPrefixo(prefixo);
                contato.setTelefone(telefone);
                contato.setEmail(email);
                contato.setTimeStamp(LocalDateTime.now());
                contatoRepository.save(contato);
                entity.setNome(nome);
                entity.setSobrenome(sobrenome);
                entity.setCpf(cpf);
                entity.setDataNascimento(dataNascimento);
                entity.setTimeStamp(LocalDateTime.now());
                clienteRepository.save(entity);
                String enderecoResponse = (entity.getEndereco().getLogradouro() +" "+
                        entity.getEndereco().getNumero() +" "+
                        entity.getEndereco().getReferencia()  +" "+
                        entity.getEndereco().getBairro() +" "+
                        entity.getEndereco().getCep()  +" "+
                        entity.getEndereco().getCidade()  +" "+
                        entity.getEndereco().getEstado()+".");
                String telefoneResponse = ("("+entity.getContato().getPrefixo()+") "+entity.getContato().getTelefone());
                ClienteDTO response = new ClienteDTO(entity.getNome(),entity.getDataNascimento(),entity.getCpf(),enderecoResponse,telefoneResponse,entity.getContato().getEmail());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            else
            { throw new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return null;
    }

    public ResponseEntity<ClienteDTO> DeletarCliente(Long id)
    {
        try
        {
            if(id != null)
            {
                if(id <= 0) {throw new IllegalActionException("O valor do campo não pode ser negativo");}

                ClienteEntity entity = clienteRepository.findById(id).orElseThrow(
                        ()-> new EntityNotFoundException()
                );
                enderecoRepository.deleteById(entity.getEndereco().getId());
                contatoRepository.deleteById(entity.getContato().getId());
                clienteRepository.deleteById(entity.getId());

                return new ResponseEntity<>(HttpStatus.OK);
            }
            else
            { throw new NullargumentsException();}
        }
        catch (Exception e)
        {
            e.getMessage();
        }
        return null;
    }

}
