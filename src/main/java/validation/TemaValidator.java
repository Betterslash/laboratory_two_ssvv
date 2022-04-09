package validation;

import domain.Tema;

public class TemaValidator implements Validator<Tema> {

    /**
     * Valideaza o tema
     * @param entity - tema pe care o valideaza
     * @throws ValidationException daca tema nu e valida
     */
    @Override
    public void validate(Tema entity) throws ValidationException {
        //Reverse order of condition because couldn't check if null is equal to something
        if(entity.getID() == null ||  entity.getID().equals("") ) {
            throw new ValidationException("Numar tema invalid!");
        }
        //Null check for description added
        if(entity.getDescriere() == null || entity.getDescriere().equals("")){
            throw new ValidationException("Descriere invalida!");
        }
        if(entity.getDeadline() < 1 || entity.getDeadline() > 14) {
            throw new ValidationException("Deadlineul trebuie sa fie intre 1-14.");
        }
        if(entity.getPrimire() < 1 || entity.getPrimire() > 14) {
            throw new ValidationException("Saptamana primirii trebuie sa fie intre 1-14.");
        }
    }
}
