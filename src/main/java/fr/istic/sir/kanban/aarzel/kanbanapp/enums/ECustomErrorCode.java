package fr.istic.sir.kanban.aarzel.kanbanapp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ECustomErrorCode {

    DATABASE_FETCHIN_ERROR(500, "", "Error with the database connection", "Error with the database connexion"),
    NOT_FOUND_ERROR(404, "", "The resource was not found in database", "The resource was not found in database"),
    ALREADY_EXIST_ERROR(409, "","The resource already exist in database", "The resource already exist in database");

    private final int errorCode;
    private final String timeStamp;
    private final String message;
    private final String description;

}
