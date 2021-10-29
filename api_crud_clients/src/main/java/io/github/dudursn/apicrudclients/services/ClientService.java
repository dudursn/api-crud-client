package io.github.dudursn.apicrudclients.services;

import io.github.dudursn.apicrudclients.dtos.ClientDTO;
import io.github.dudursn.apicrudclients.entities.Client;
import io.github.dudursn.apicrudclients.repositories.ClientRepository;
import io.github.dudursn.apicrudclients.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository repository;

    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(PageRequest pageRequest){

        Page<Client> result = repository.findAll(pageRequest);
        return result.map(data -> new ClientDTO(data));
    }

    @Transactional(readOnly = true)
    public ClientDTO findById(Long id){

        Optional<Client> obj = repository.findById(id);
        Client entity = obj.orElseThrow( () -> new ResourceNotFoundException("Data not found"));
        return new ClientDTO(entity);
    }

    @Transactional
    public ClientDTO insert(ClientDTO dto) {

        Client entity = new Client();
        entity = repository.save(this.copyDtoToEntity(dto, entity));

        return new ClientDTO(entity);
    }

    @Transactional
    public ClientDTO update(Long id, ClientDTO dto) {
        try{
            Client entity = repository.getOne(id);
            entity = this.copyDtoToEntity(dto, entity);
            entity.setId(id);
            entity = repository.save(entity);

            return new ClientDTO(entity);
        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id "+ id +" not found");
        }
    }

    @Transactional
    public void delete(Long id) {

        try {

            repository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id "+ id +" not found");
        }
    }

    private Client copyDtoToEntity(ClientDTO dto, Client entity){

        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setCpf(dto.getCpf());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());
        return entity;
    }
}
