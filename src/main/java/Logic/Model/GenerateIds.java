package Logic.Model;

import Logic.Model.GenerateId;

public class GenerateIds implements GenerateId {

    private Long lastId=0L;

    public GenerateIds(){
        lastId=0L;
    }

    @Override
    public String generateId() {
        lastId++;
        return lastId+"";
    }

}
