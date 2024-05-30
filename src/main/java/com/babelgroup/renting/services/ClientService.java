package com.babelgroup.renting.services;

import com.babelgroup.renting.entities.*;
import com.babelgroup.renting.entities.dtos.ClientDto;
import com.babelgroup.renting.entities.dtos.ClientUpdateDto;

public interface ClientService {

    Client createClient(ClientDto clientDto);

    Boolean updateClient(long clientId, ClientUpdateDto client);

    Boolean deleteClient(long clientId);

    Boolean clientExists(String dni);

}