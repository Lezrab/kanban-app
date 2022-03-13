package fr.istic.sir.kanban.aarzel.kanbanapp.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GenericDeleteResponse {

    private String title;

    private String message;
}
