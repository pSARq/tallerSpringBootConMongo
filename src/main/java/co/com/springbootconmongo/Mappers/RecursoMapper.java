package co.com.springbootconmongo.Mappers;

import co.com.springbootconmongo.DTOs.RecursoDTO;
import co.com.springbootconmongo.Collections.Recurso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecursoMapper {

    public Recurso fromDTO(RecursoDTO dto){
        Recurso recurso = new Recurso();
        recurso.setId(dto.getId());
        recurso.setNombre(dto.getNombre());
        recurso.setTipo(dto.getTipo());
        recurso.setTematica(dto.getTematica());
        recurso.setPrestado(dto.isPrestado());
        recurso.setFechaPrestamo(dto.getFechaPrestamo());
        return recurso;
    }

    public RecursoDTO fromCollection(Recurso collection){
        RecursoDTO recursoDTO = new RecursoDTO();
        recursoDTO.setId(collection.getId());
        recursoDTO.setNombre(collection.getNombre());
        recursoDTO.setTipo(collection.getTipo());
        recursoDTO.setTematica(collection.getTematica());
        recursoDTO.setPrestado(collection.isPrestado());
        recursoDTO.setFechaPrestamo(collection.getFechaPrestamo());
        return recursoDTO;
    }

    public List<RecursoDTO> fromCollectionList(List<Recurso> collection){
        if (collection == null){
            return null;
        }

        List<RecursoDTO> list = new ArrayList<>(collection.size());
        Iterator listTracks = collection.iterator();

        while (listTracks.hasNext()){
            Recurso recurso = (Recurso) listTracks.next();
            list.add(fromCollection(recurso));
        }

        return list;
    }
}
