import javafx.scene.control.Button;

public class TicButton extends Button{

    public TicButton (String name)
    {
        super(name);
        this.state = States.EMPTY;
    }
    private States state;
    /**
     * @return the state
     */
    public States getState() {
        return state;
    }
    /**
     * @param state the state to set
     */
    public void setState(States state) {
        this.state = state;
    }
    public enum States {
        X,
        
        // O is used for the AI
        O,
        EMPTY
    }
}