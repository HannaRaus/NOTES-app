package ua.goit.goitnotes.interfaces;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public interface Convertor<DAO, DTO> {

    DAO fromDTO(DTO dto);

    DTO toDTO(DAO dao);

    default Set<DAO> toDAOSet(Set<DTO> dtoSet) {
        Set<DAO> daoSet = new HashSet<>();

        if (Objects.isNull(dtoSet) || dtoSet.isEmpty()) {
            return daoSet;
        }

        dtoSet.forEach(dto -> {
            DAO dao = fromDTO(dto);
            daoSet.add(dao);
        });

        return daoSet;
    }

    default Set<DTO> toDTOSet(Set<DAO> daoSet) {
        Set<DTO> dtoSet = new HashSet<>();

        if (Objects.isNull(daoSet) || daoSet.isEmpty()) {
            return dtoSet;
        }

        daoSet.forEach(dao -> {
            DTO dto = toDTO(dao);
            dtoSet.add(dto);
        });

        return dtoSet;
    }

}
